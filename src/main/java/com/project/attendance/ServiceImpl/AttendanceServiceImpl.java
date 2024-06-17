package com.project.attendance.ServiceImpl;

import com.project.attendance.Exception.InternalServerException;
import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.Attendance;
import com.project.attendance.Model.Batch;
import com.project.attendance.Model.User;
import com.project.attendance.Payload.DTO.AttendanceDTO;
import com.project.attendance.Exception.SubscriptionExpireException;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.Payload.Response.AttendanceResponse;
import com.project.attendance.Repository.AttendanceRepository;
import com.project.attendance.Repository.BatchRepository;
import com.project.attendance.Repository.UserRepository;
import com.project.attendance.Service.AttendanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository ;

    @Autowired
    UserRepository userRepository ;

    @Autowired
    BatchRepository batchRepository ;

    @Autowired
    ModelMapper modelMapper ;

    @Override
    public AttendanceDTO markAttendance(Integer userId , Integer batchId) {

        User user = userRepository.findById(userId)
                    .orElseThrow(()-> new ResourceNotFoundException("User" , "userId" , userId));

            Batch batch = batchRepository.findById(batchId)
                    .orElseThrow(()-> new ResourceNotFoundException("Batch" , "batchId" , batchId));


            Attendance existingAttendance = attendanceRepository.findByUserAndPresentDate(user , LocalDate.now()) ;
            Attendance attendance = new Attendance() ;

            if(existingAttendance != null){
                throw new DuplicateKeyException("Already Attendance Marked") ;
            }

            if(user.getEnd_LocalDate().isBefore(LocalDate.now().plusDays(1))){
                throw new SubscriptionExpireException(user.getEnd_LocalDate()) ;
            }

            attendance.setUser(user);
            attendance.setBatch(batch);
            attendance.setPresentDate(LocalDate.now());

            Attendance createdAttendance = attendanceRepository.save(attendance) ;
            return  modelMapper.map(attendance , AttendanceDTO.class);

    }

    @Override
    public AttendanceResponse getAllPresentDays(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User" , "userId" , userId));

        List<Attendance> allAttendance = attendanceRepository.findByUser(user) ;

        List<LocalDate> presentDays = allAttendance.stream()
                .map(Attendance::getPresentDate)
                .toList();

        int currentStreak = 0;
        int maxStreak = 0;
        LocalDate lastDate = null;

        for (LocalDate date : presentDays) {
            if (lastDate == null || lastDate.plusDays(1).isEqual(date)) {
                currentStreak++;
            } else {
                maxStreak = Math.max(maxStreak, currentStreak);
                currentStreak = 1;
            }
            lastDate = date;
        }
        maxStreak = Math.max(maxStreak, currentStreak);

        return AttendanceResponse.builder()
                .presentDays(presentDays)
                .maxStreak(maxStreak)
                .success(true)
                .build() ;
    }

    @Override
    public List<User> getAllPresentUserByBatch(Integer batchId , String date){

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(()-> new ResourceNotFoundException("Batch" , "batchId" , batchId));

        LocalDate searchDate = LocalDate.parse(date) ;
        List<Attendance> attendances = attendanceRepository.findByBatchAndPresentDate(batch ,searchDate ) ;

        List<User> users = attendances.stream()
                .map(Attendance::getUser)
                .toList();

        return users ;
    }
}
