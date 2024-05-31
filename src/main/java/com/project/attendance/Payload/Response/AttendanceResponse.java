package com.project.attendance.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceResponse {
    List<LocalDate> presentDays ;
    int maxStreak ;
    private Boolean success ;
}
