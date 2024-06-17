package com.project.attendance.ServiceImpl;

import com.project.attendance.Model.PTSession;
import com.project.attendance.Model.User;
import com.project.attendance.Payload.Response.PTSessionResponse;
import com.project.attendance.Repository.PTSessionRepository;
import com.project.attendance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PTSessionService {

    @Autowired
    private PTSessionRepository ptSessionRepository;

    @Autowired
    private UserRepository userRepository;

    public PTSession addPTSession(Integer trainerId, Integer traineeId, String exercise, LocalTime timeIn, LocalTime timeOut, LocalDate date) {
        Optional<User> trainerOpt = userRepository.findById(trainerId);
        Optional<User> traineeOpt = userRepository.findById(traineeId);

        if (trainerOpt.isPresent() && traineeOpt.isPresent()) {
            User trainer = trainerOpt.get();
            User trainee = traineeOpt.get();

            // Validate trainer-trainee relationship
            System.out.println("TrainerID = " + trainerId);
            System.out.println("Trainee's TrainerID = " + trainee.getTrainer().getId());
            if (trainee.getTrainer() != null && trainee.getTrainer().getId().equals(trainerId)) {
                PTSession ptSession = new PTSession();
                ptSession.setTrainer(trainer);
                ptSession.setTrainee(trainee);
                ptSession.setExercise(exercise);
                ptSession.setTimeIn(timeIn);
                ptSession.setTimeOut(timeOut);
                ptSession.setDate(date);

                return ptSessionRepository.save(ptSession);
            } else {
                throw new IllegalArgumentException("Trainee is not assigned to this trainer");
            }
        } else {
            throw new IllegalArgumentException("Trainer or Trainee not found");
        }
    }

}
