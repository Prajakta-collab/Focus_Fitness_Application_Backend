package com.project.attendance.Controller;

import com.project.attendance.Payload.BatchDTO;
import com.project.attendance.Payload.StaffDTO;
import com.project.attendance.ServiceImpl.StaffServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    @Autowired
    StaffServiceImpl staffService ;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StaffDTO> createStaff(@RequestBody StaffDTO staffDTO){
        StaffDTO createdStaff = staffService.createStaff(staffDTO) ;
        return ResponseEntity.ok(createdStaff) ;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<StaffDTO>> getAllStaff(){
        List<StaffDTO> allStaffs = staffService.getAllStaff() ;
        return ResponseEntity.ok(allStaffs) ;
    }

    @GetMapping("/{staffId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable Integer staffId){
        StaffDTO staffDTO = staffService.getStaffById(staffId) ;
        return ResponseEntity.ok(staffDTO) ;
    }

    @DeleteMapping("/{staffId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteStaffById(@PathVariable Integer staffId){
        staffService.deleteStaff(staffId); ;
        return ResponseEntity.ok("Staff Deleted Successfully") ;
    }


    @PutMapping("/{staffId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StaffDTO> updateStaff(@PathVariable Integer staffId ,
                                                @RequestBody StaffDTO staffDTO){
        StaffDTO updatedStaff = staffService.updateStaff(staffId , staffDTO) ;
        return ResponseEntity.ok(updatedStaff) ;
    }

}
