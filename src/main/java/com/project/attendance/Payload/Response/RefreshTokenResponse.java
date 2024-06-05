package com.project.attendance.Payload.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenResponse {
    private String refreshToken ;
    private Boolean success ;
}
