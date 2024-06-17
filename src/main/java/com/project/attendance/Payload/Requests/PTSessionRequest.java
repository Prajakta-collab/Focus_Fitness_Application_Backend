package com.project.attendance.Payload.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PTSessionRequest {
    private Integer trainerId;
    private Integer traineeId;
    private String exercise;
    private String timeIn;
    private String timeOut;
    private String date;

}
