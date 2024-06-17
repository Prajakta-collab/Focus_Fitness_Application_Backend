package com.project.attendance.Payload.DTO;

import com.project.attendance.Model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembersDetails {
    List<User> dueMembers ;
    List<User> expireToday ;
    List<User> attendanceToday ;
    List<User> expireInNext3Days ;
    List<User> totalMembers ;
}
