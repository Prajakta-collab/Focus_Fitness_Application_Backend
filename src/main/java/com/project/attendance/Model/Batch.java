package com.project.attendance.Model;

import com.project.attendance.Payload.UserDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "batches")
public class Batch implements Serializable {


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String batchName ;

    @OneToMany
    List<User> users ;

    public Batch(){

    }

    public Batch(Integer id, String batchName, LocalDate timing, List<User> users) {
        this.id = id;
        this.batchName = batchName;
        this.users = users;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
