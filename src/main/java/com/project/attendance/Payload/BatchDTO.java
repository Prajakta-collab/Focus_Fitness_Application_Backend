package com.project.attendance.Payload;

import java.time.LocalDate;
import java.util.List;

public class BatchDTO {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BatchDTO(Integer id, String batchName, LocalDate timing, List<UserDTO> users) {
        this.id = id;
        this.batchName = batchName;
        this.timing = timing;
        this.users = users;
    }

    private Integer id ;
    private String batchName ;
    private LocalDate timing ;


    List<UserDTO> users ;

    public BatchDTO(){

    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public LocalDate getTiming() {
        return timing;
    }

    public void setTiming(LocalDate timing) {
        this.timing = timing;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
