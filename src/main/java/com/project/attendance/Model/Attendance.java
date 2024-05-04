package com.project.attendance.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @JsonIgnore
    @ManyToOne
    private Batch batch ;

    @ManyToOne
    private User user ;

    private LocalDate presentDate ;

    public Attendance() {
    }

    public Attendance(Integer id, Batch batch, User user, LocalDate presentDate) {
        this.id = id;
        this.batch = batch;
        this.user = user;
        this.presentDate = presentDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getPresentDate() {
        return presentDate;
    }

    public void setPresentDate(LocalDate presentDate) {
        this.presentDate = presentDate;
    }
}
