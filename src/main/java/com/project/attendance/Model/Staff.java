package com.project.attendance.Model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "staffs")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    private String firstName ;
    private String lastName ;
    private String mobile_no ;
    private String email ;
    private String password ;
    private String address ;
    private String role ;

    @OneToMany
    private List<Batch> batches ;

    public Staff(){

    }

    public Staff(Integer id, String firstName, String lastName, String mobile_no, String email, String password, String address, String role, List<Batch> batches) {
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

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }
}
