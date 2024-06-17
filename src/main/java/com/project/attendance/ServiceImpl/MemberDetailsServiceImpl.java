package com.project.attendance.ServiceImpl;

import com.project.attendance.Model.User;
import com.project.attendance.Payload.DTO.MembersDetails;
import com.project.attendance.Repository.AttendanceRepository;
import com.project.attendance.Repository.UserRepository;
import com.project.attendance.Service.MemberDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberDetailsServiceImpl implements MemberDetailsService {

    @Autowired
    UserRepository userRepository ;

    @Autowired
    AttendanceServiceImpl attendanceService ;

    @Override
    public MembersDetails getAllMembersDetails() {
        // Fetch users with expired subscriptions
        List<User> expiredSubscriptionsUsers = userRepository.getExpiredSubscriptionsUsers();

        // Fetch users whose subscriptions expire today
        List<User> subscriptionsExpiringToday = userRepository.findUsersWithSubscriptionEndingToday();

        // Fetch attendance for morning batch
        List<User> morningBatchAttendance = attendanceService.getAllPresentUserByBatch(1, String.valueOf(LocalDate.now()));

        // Fetch attendance for evening batch
        List<User> eveningBatchAttendance = attendanceService.getAllPresentUserByBatch(2, String.valueOf(LocalDate.now()));

        // Combine morning and evening batch attendance
        List<User> combinedAttendance = new ArrayList<>(morningBatchAttendance);
        combinedAttendance.addAll(eveningBatchAttendance);

        // Fetch users whose subscriptions expire in the next 3 days
        List<User> subscriptionsExpiringInNext3Days = userRepository.findUsersWithExpiringSubscriptions(LocalDate.now(), LocalDate.now().plusDays(3));

        // Fetch all users
        List<User> allUsers = userRepository.findNormalUsers();

        // Build the MembersDetails object
        return MembersDetails.builder()
                .dueMembers(expiredSubscriptionsUsers)
                .expireToday(subscriptionsExpiringToday)
                .totalMembers(allUsers)
                .attendanceToday(combinedAttendance)
                .expireInNext3Days(subscriptionsExpiringInNext3Days)
                .build();
    }

}
