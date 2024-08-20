package com.courseservice.course_service.service;

import com.courseservice.course_service.model.Course;
import com.courseservice.course_service.model.Embedding;
import com.courseservice.course_service.model.Lesson;
import com.courseservice.course_service.repository.CourseRepository;
import com.courseservice.course_service.repository.EmbeddingRepository;
import com.courseservice.course_service.repository.LessonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LessonServiceImpl implements LessonService {
  private static final Logger logger = LoggerFactory.getLogger(LessonServiceImpl.class);

  private final EmbeddingModel embeddingModel;
  private final LessonRepository lessonRepository;

  private final CourseRepository courseRepository;

  private final EmbeddingRepository embeddingRepository;

  private final UploadService uploadService;

  private final RestTemplate restTemplate;

  public LessonServiceImpl(
      EmbeddingModel embeddingModel,
      LessonRepository lessonRepository,
      CourseRepository courseRepository,
      EmbeddingRepository embeddingRepository,
      UploadService uploadService,
      RestTemplate restTemplate) {
    this.embeddingModel = embeddingModel;
    this.lessonRepository = lessonRepository;
    this.courseRepository = courseRepository;
    this.embeddingRepository = embeddingRepository;
    this.uploadService = uploadService;
    this.restTemplate = restTemplate;
  }

  // Create a new lesson
  //    @Transactional
  public Lesson createLesson(Lesson lesson, Map<String, String> uploadResult) {
    Optional<Course> course = courseRepository.findById(lesson.getCourse().getId());
    if (course.isPresent()) {
      String url = uploadResult.get("secure_url"); // playback_url, secure_url
      lesson.setVideoUrl(url);
      lesson.setCourse(course.get());
      /* Save the lesson initially without the transcript */
      Lesson savedLesson = lessonRepository.save(lesson);
      int lessonId = savedLesson.getId();

      CompletableFuture<String> stringCompletableFuture = uploadService.performTranscript(url);
      stringCompletableFuture
          .thenAccept(
              transcript -> {
                Lesson l = lessonRepository.findById(lessonId).get();
                l.setTranscript(transcript);
                Lesson modifiedLesson = lessonRepository.save(l);

                logger.info(String.format("Received Transcript = %s", transcript));
                /* createEmbedding */
                List<String> chunks = LessonServiceImpl.createChunks(transcript);

                var embeddingResponse = embeddingModel.embedForResponse(chunks);

                logger.info(String.valueOf(embeddingResponse));

                AtomicInteger atomicInteger = new AtomicInteger();

                embeddingResponse
                    .getResults()
                    .forEach(
                        embedding -> {

                            float[] floatArray = embedding.getOutput();
                            double[] embedInDoubleType = new double[floatArray.length];

                            for (int i = 0; i < floatArray.length; i++) {
                                embedInDoubleType[i] = floatArray[i];
                            }

//                          double[] embedInDoubleType =
//                              embedding.getOutput().stream()
//                                  .mapToDouble(Double::doubleValue)
//                                  .toArray();
                          // inserting the data

                          Embedding embed = new Embedding();
                          embed.setLesson(modifiedLesson);

                          embed.setEmbedChunk(chunks.get(atomicInteger.getAndIncrement()));
                          embed.setChunkVector(embedInDoubleType);
                          embeddingRepository.save(embed);
                        });
              })
          .exceptionally(
              ex -> {
                // check the exception
                logger.error(String.format("Error occurred: %s", ex.getCause()));
                return null;
              });

      logger.info("The sync work is done");

      return savedLesson;
    } else {
      throw new IllegalArgumentException("Course not found");
    }
  }

  // Get a lesson by ID
  public Optional<Lesson> getLessonById(int id) {
    return lessonRepository.findById(id);
  }

  // Get all lessons
  public List<Lesson> getAllLessons() {
    return (List<Lesson>) lessonRepository.findAll();
  }

  // Update an existing lesson
  // @Transactional
  public Optional<Lesson> updateLesson(int id, Lesson updatedLesson) {
    return lessonRepository
        .findById(id)
        .map(
            lesson -> {
              lesson.setName(updatedLesson.getName());
              lesson.setDescription(updatedLesson.getDescription());
              lesson.setVideoUrl(updatedLesson.getVideoUrl());
              lesson.setTranscript(updatedLesson.getTranscript());
              return lessonRepository.save(lesson);
            });
  }

  // Delete a lesson by ID
  // @Transactional
  public boolean deleteLesson(int id) {
    if (lessonRepository.existsById(id)) {
      lessonRepository.deleteById(id);
      return true;
    } else {
      return false;
    }
  }

  // Get lessons by Course ID
  public List<Lesson> getLessonsByCourseId(int courseId) {
    return lessonRepository.findByCourseId(courseId);
  }

  @Override
  public boolean verifyMentorEmail(String mentorEmail) {

    String url = "http://192.168.1.102/8080/users/check-email?email=" + mentorEmail;
    try {
      Boolean isValid = restTemplate.getForObject(url, Boolean.class);
      return isValid != null && isValid;
    } catch (Exception e) {
      // Handle the exception as needed (e.g., log it, rethrow it, return false, etc.)
      return false;
    }
  }

  //  private void get

  public static List<String> createChunks(String paragraph) {
    // Define a regex pattern for matching sentence boundaries
    Pattern pattern = Pattern.compile("([^.!?]+[.!?])\\s*");

    Matcher matcher = pattern.matcher(paragraph);
    ArrayList<String> chunks = new ArrayList<>();
    StringBuilder chunk = new StringBuilder();
    int count = 0;

    while (matcher.find()) {
      // Append the matched sentence to the chunk
      chunk.append(matcher.group()).append(" ");

      // Check if two sentences have been added to the chunk
      count++;
      if (count % 6 == 0) {
        chunks.add(chunk.toString().trim());
        chunk.setLength(0); // Clear the StringBuilder for the next chunk
      }
    }

    // Add any remaining sentences to the list (if the last chunk has less than 2 sentences)
    if (!chunk.isEmpty()) {
      chunks.add(chunk.toString().trim());
    }

    return chunks;
  }
}
