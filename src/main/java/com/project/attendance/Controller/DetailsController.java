package com.project.attendance.Controller;

import com.project.attendance.Payload.DTO.MembersDetails;
import com.project.attendance.ServiceImpl.MemberDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/details")
public class DetailsController {

    @Autowired
    MemberDetailsServiceImpl memberDetailsService ;

    @GetMapping
    ResponseEntity<MembersDetails> getAllDashboardDetails(){
        return ResponseEntity.ok(memberDetailsService.getAllMembersDetails()) ;
    }
}
