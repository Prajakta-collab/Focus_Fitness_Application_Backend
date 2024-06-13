package com.project.attendance.ServiceImpl;

import com.project.attendance.Model.User;
import com.project.attendance.Payload.DTO.MembersDetails;
import com.project.attendance.Repository.AttendanceRepository;
import com.project.attendance.Repository.UserRepository;
import com.project.attendance.Service.MemberDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberDetailsServiceImpl implements MemberDetailsService {

    @Autowired
    UserRepository userRepository ;

    @Autowired
    AttendanceServiceImpl attendanceService ;

    @Override
    public MembersDetails getAllMembersDetails() {
        Integer totalDue = userRepository.countExpiredSubscriptions() ;
        Integer expireToday = userRepository.findUsersWithSubscriptionEndingToday() ;
        Integer morningBatchAttendance = attendanceService.getAllPresentUserByBatch(1 , String.valueOf(LocalDate.now())).size() ;
        Integer eveningBatchAttendance = attendanceService.getAllPresentUserByBatch(2 , String.valueOf(LocalDate.now())).size() ;
        Integer totalAttendance = morningBatchAttendance + eveningBatchAttendance ;
        Integer expiryInUpcoming3Days = userRepository.findUsersWithExpiringSubscriptions(LocalDate.now() , LocalDate.now().plusDays(3)).size() ;
        Integer totalMembers = userRepository.findAll().size() ;

        return MembersDetails.builder()
                .dueMembers(totalDue)
                .expireToday(expireToday)
                .totalMembers(totalMembers)
                .attendanceToday(totalAttendance)
                .expireInNext3Days(expiryInUpcoming3Days)
                .build() ;
    }
}
