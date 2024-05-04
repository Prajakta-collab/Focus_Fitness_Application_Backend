package com.project.attendance.Payload;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String username ;
    private String password ;
}
