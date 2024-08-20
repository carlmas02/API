package com.project.userservice.model;

//import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer instructorId;

    //    @Column(name = "hire_date")
    //    private LocalDate hireDate;

    private String department;

    private String title;

    private Double salary;

    @OneToOne
    @JoinColumn(name = "mentor_email", referencedColumnName = "email")
    private User user;

    

    public Instructor() {
    }

    // Getters and Setters
    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    //    public LocalDate getHireDate() {
    //        return hireDate;
    //    }

    //    public void setHireDate(LocalDate hireDate) {
    //        this.hireDate = hireDate;
    //    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}