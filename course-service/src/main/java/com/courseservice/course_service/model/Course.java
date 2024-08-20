package com.courseservice.course_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Course {
  //  @Id private String courseURL; may think about this
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(length = 20, nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Temporal(TemporalType.DATE)
  @Column(name = "created_at")
  private Date createdAt;

  @Temporal(TemporalType.DATE)
  @Column(name = "updated_at")
  private Date updatedAt;

  private String mentorEmail; //fk to user service

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Lesson> lessons;

}
