package com.courseservice.course_service.service;

import com.assemblyai.api.AssemblyAI;
import com.courseservice.course_service.exceptions.TranscriptNotCreatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UploadServiceImpl implements UploadService {

  private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

  @Value("${assemblyai.api-key}")
  private String assemblyApiKey;

  @Async
  public CompletableFuture<String> performTranscript(String url) {
    return CompletableFuture.supplyAsync(
        () -> {
          String transcript =
              AssemblyAI.builder()
                  .apiKey(assemblyApiKey)
                  .build()
                  .transcripts()
                  .transcribe(url)
                  .getText()
                  .orElse(null);

          if (transcript == null) {
            throw new TranscriptNotCreatedException(
                "Transcript could not be generated for the provided URL. Seems like an error from the AssemblyAI API");
          }

          logger.info(String.format("Transcript : %s", transcript));
          return transcript;
        });
  }
}

//
//  @Async
//  public CompletableFuture<Void> performTranscript(String url) {
//    return CompletableFuture.runAsync(
//        () -> {
//          String transcript =
//              AssemblyAI.builder()
//                  .apiKey("2a85ecebf9bb4534b6c036754064be39")
//                  .build()
//                  .transcripts()
//                  .transcribe(url)
//                  .getText()
//                  .orElse(null);
//
//          System.out.println(transcript);
//
//          //            Optional<Course> c = courseRepository.findById(url);
//          //            if (c.isPresent()) {
//          //              Course course = c.get();
//          ////              course.setTranscript(transcript);
//          //              courseRepository.save(course);
//          //              System.out.println("worked");
//          //            } else {
//          //              throw new RuntimeException("Course not found with id: ");
//          //            }
//          System.out.println("Transcript completed for: " + url);
//        });
//  }
