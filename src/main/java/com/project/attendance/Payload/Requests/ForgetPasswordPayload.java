package com.project.attendance.Payload.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgetPasswordPayload {
    String otp ;
    String password ;
    String confirmPassword ;
    String email ;
}
