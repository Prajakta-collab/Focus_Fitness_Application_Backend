package com.project.attendance.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role implements Serializable {

    @Id
    private Integer id ;
    private String roleName ;
}
