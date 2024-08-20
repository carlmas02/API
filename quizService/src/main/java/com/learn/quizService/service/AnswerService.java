package com.learn.quizService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.quizService.exception.InvalidQuizDataException;
import com.learn.quizService.model.Answers;
import com.learn.quizService.repository.AnswerRepository;

@Service
public class AnswerService {

	
	@Autowired
	private AnswerRepository answerRepository;
	
	// Get all answers
	public List<Answers> getAllAnswers() {
        return answerRepository.findAll();
    }
	
	// Get all answers to a given question id
	public List<Answers> getAnswersByQuestionId(String questionId) {
		List<Answers> answers = answerRepository.findByQuestionId(questionId);
		if(answers.isEmpty())
		{
			throw new InvalidQuizDataException("No answers found for the given question id.");
		}
		return answers;
	}
	
	// Get answer by answer id
	public Answers getAnswerById(String answerId){
		Optional<Answers> answer = answerRepository.findById(answerId);
		if(answer.isEmpty())
		{
			throw new InvalidQuizDataException("No answer with given id.");
		}
		return answer.get();
	}
	
	// Get correct answer to a given question
    public Optional<Answers> getCorrectAnswer(String questionId) {
        Optional<Answers> answer = answerRepository.findByQuestionId(questionId).stream()
                               .filter(Answers::getIsCorrect)
                               .findFirst();
        if(answer.isEmpty())
        {
        	throw new InvalidQuizDataException("No correct answer found for the given question id.");
        }
        return answer;
    }
	
	// Save answer
	public Answers saveAnswer(Answers answer) {
		return answerRepository.save(answer);
	}
	
	// Delete answer
	public boolean deleteAnswer(String answerId) {
		boolean isDeleted = false;
		if(answerRepository.existsById(answerId))
		{
			answerRepository.deleteById(answerId);
			isDeleted = true;
		}
		else {
			throw new InvalidQuizDataException("Answer not found to delete.");
		}
		return isDeleted;
	}
	
	
	// Update answer
	public boolean updateAnswer(Answers answer) {
		boolean isUpdated = false;
		Optional<Answers> optional = answerRepository.findById(answer.getAnswerId());
		if(optional.isPresent())
		{
			Answers existingAnswer = optional.get();
			existingAnswer.setAnswerText(answer.getAnswerText());
			existingAnswer.setIsCorrect(answer.getIsCorrect());
			answerRepository.save(existingAnswer);
			isUpdated = true;
		}
		else {
			throw new InvalidQuizDataException("Answer not found to update.");
		}
		return isUpdated;
	}
}
