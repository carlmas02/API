package com.courseservice.course_service.repository;

import com.courseservice.course_service.model.Embedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmbeddingRepository extends JpaRepository<Embedding, Integer> {

  //    @Query(
  //            nativeQuery = true,
  //            value = "SELECT * FROM embedding ORDER BY embed_chunk <-> cast(? as vector) LIMIT
  // 5")
  @Query(
      nativeQuery = true,
      value = "SELECT * FROM embedding where lesson_id=:lessonId ORDER BY chunk_vector <-> cast(:chunk as vector)  LIMIT 5")
  public List<Embedding> findNearestNeighbors(@Param("chunk") String chunk, @Param("lessonId") int lessonId);
}
