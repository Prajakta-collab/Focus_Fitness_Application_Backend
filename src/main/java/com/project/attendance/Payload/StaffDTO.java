package com.project.attendance.Payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.attendance.Model.Batch;
import java.util.List;

public class StaffDTO {
    private Integer id ;
    private String firstName ;
    private String lastName ;
    private String mobile_no ;
    private String email ;
    private String password ;
    private String address ;
    private String role ;

    @JsonIgnore
    private List<BatchDTO> batches ;

    public StaffDTO(){

    }

    public StaffDTO(Integer id, String firstName, String lastName, String mobile_no, String email, String password, String address, String role, List<BatchDTO> batches) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile_no = mobile_no;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
        this.batches = batches;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<BatchDTO> getBatches() {
        return batches;
    }

    public void setBatches(List<BatchDTO> batches) {
        this.batches = batches;
    }
}

