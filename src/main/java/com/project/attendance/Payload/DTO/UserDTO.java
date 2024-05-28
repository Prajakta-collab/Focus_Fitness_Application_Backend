package com.project.attendance.Payload.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.attendance.Payload.DTO.BatchDTO;

import java.time.LocalDate;

public class UserDTO {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id ;
    private String firstName ;
    private String lastName ;
    private String mobile_no ;
    private String email ;
    private String password ;
    private Integer age ;
    private LocalDate joining_LocalDate ;
    private Integer duration ;
    private LocalDate end_LocalDate ;
    private String height ;
    private Float weight ;
    private String shift ;


    public BatchDTO getEnrolledBatch() {
        return enrolledBatch;
    }

    public void setEnrolledBatch(BatchDTO enrolledBatch) {
        this.enrolledBatch = enrolledBatch;
    }

    @JsonIgnore
    private BatchDTO enrolledBatch ;


    public UserDTO() {
    }

    public UserDTO(Integer id , String firstName, String lastName, String mobile_no, String email, String password, Integer age, LocalDate joining_LocalDate, Integer duration, LocalDate end_LocalDate, String height, Float weight, String shift, BatchDTO enrolledBatch) {
        this.id = id ;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile_no = mobile_no;
        this.email = email;
        this.password = password;
        this.age = age;
        this.joining_LocalDate = joining_LocalDate;
        this.duration = duration;
        this.end_LocalDate = end_LocalDate;
        this.height = height;
        this.weight = weight;
        this.shift = shift;
        this.enrolledBatch = enrolledBatch;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getJoining_LocalDate() {
        return joining_LocalDate;
    }

    public void setJoining_LocalDate(LocalDate joining_LocalDate) {
        this.joining_LocalDate = joining_LocalDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getEnd_LocalDate() {
        return end_LocalDate;
    }

    public void setEnd_LocalDate(LocalDate end_LocalDate) {
        this.end_LocalDate = end_LocalDate;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
