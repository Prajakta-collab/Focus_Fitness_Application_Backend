package com.project.attendance.Payload.Requests;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String username ;
    private String password ;
}
