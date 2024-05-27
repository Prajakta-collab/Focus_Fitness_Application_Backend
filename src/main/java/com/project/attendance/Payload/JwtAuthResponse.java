package com.project.attendance.Payload;

import com.project.attendance.Model.RefreshToken;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthResponse {
    private String token ;
    private String refreshToken ;
}
