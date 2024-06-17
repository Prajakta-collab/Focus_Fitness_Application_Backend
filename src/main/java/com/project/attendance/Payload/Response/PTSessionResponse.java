package com.project.attendance.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PTSessionResponse {
    private Integer sessionId;
    private Integer trainerId;
    private Integer traineeId;
    private String exercise;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private LocalDate date;
}
