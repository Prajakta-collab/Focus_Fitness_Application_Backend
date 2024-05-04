package com.project.attendance.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @UniqueElements
    private Integer id ;

    private String roleName ;

}
