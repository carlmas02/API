package com.project.userservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    private String major;

    @Column(name = "total_credits")
    private Integer totalCredits;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Marked change: Added cascade to automatically save User
    @JoinColumn(name = "student_mail", referencedColumnName = "email")
    private User user;

    // Getters and Setters
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

/*
package com.project.userservice.model;
 

import jakarta.persistence.*;

//import java.sql.Date;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    //    @Column(name = "enrollment_date")
    //    private Date enrollmentDate;

    private String major;

    @Column(name = "total_credits")
    private Integer totalCredits;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_mail", referencedColumnName = "email")
    private User user;

    // Getters and Setters
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    //    public Date getEnrollmentDate() {
    //        return enrollmentDate;
    //    }
    //
    //    public void setEnrollmentDate(Date enrollmentDate) {
    //        this.enrollmentDate = enrollmentDate;
    //    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

*/