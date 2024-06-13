package com.project.attendance.Payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembersDetails {
    Integer dueMembers ;
    Integer expireToday ;
    Integer attendanceToday ;
    Integer expireInNext3Days ;
    Integer totalMembers ;
}
