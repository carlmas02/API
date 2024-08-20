package com.courseservice.course_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Lesson {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(length = 40, nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(length = 150, nullable = false)
  private String videoUrl;

  @Column(columnDefinition = "TEXT")
  private String transcript;

  @ManyToOne
  @JoinColumn(name = "course_id", nullable = false)
//  @JsonIgnore
  private Course course;

  @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Embedding> embeddings;
}
