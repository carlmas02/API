package com.courseservice.course_service.service;

import com.courseservice.course_service.model.Embedding;
import com.courseservice.course_service.repository.EmbeddingRepository;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

  private final ChatModel chatModel;

  private final EmbeddingModel embeddingModel;

  private final EmbeddingRepository embeddingRepository;

  public QuestionServiceImpl(
      ChatModel chatModel, EmbeddingModel embeddingModel, EmbeddingRepository embeddingRepository) {
    this.chatModel = chatModel;
    this.embeddingModel = embeddingModel;
    this.embeddingRepository = embeddingRepository;
  }

  @Override
  public String askQuestion(String question, int lessonId) {
    List<String> words = List.of(question);
    var embeddingResponse = this.embeddingModel.embedForResponse(words);
    // System.out.println(embeddingResponse.getResult().getOutput().toString());
    List<Embedding> embeddingList =
        embeddingRepository.findNearestNeighbors(
            embeddingResponse.getResult().getOutput().toString(), lessonId);

    StringBuilder context = new StringBuilder();
    for (Embedding embedding : embeddingList) {
      context.append(embedding.getEmbedChunk());
    }

    String initialPrompt =
        "You are a professional instructor. Your task is to give a well explained answer based on the data mentioned below. The question is present after the end of the data provided ...";

    String query = initialPrompt + context.toString() + " " + question;

    ChatResponse response =
        chatModel.call(
            new Prompt(
                query,
                MistralAiChatOptions.builder()
                    .withModel(MistralAiApi.ChatModel.SMALL.getValue())
                    .withTemperature(0.5f)
                    .build()));

    return response.getResult().getOutput().getContent();
  }
}
