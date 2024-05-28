package com.project.attendance.Payload.DTO;

import com.project.attendance.Model.Batch;
import com.project.attendance.Model.User;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class AttendanceDTO {
    private Integer id ;
    private Batch batch ;
    private User user ;
    private LocalDate presentDate ;

    public AttendanceDTO() {
    }

    public AttendanceDTO(Integer id, Batch batch, User user, LocalDate presentDate) {
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
