package com.project.attendance.Payload.Response;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApiResponse {
    private String message ;
    private Boolean success ;
    private String className ;
}
