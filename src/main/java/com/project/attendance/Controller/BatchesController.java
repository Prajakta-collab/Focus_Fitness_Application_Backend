package com.project.attendance.Controller;

import com.project.attendance.Payload.BatchDTO;
import com.project.attendance.ServiceImpl.BatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/v1/batch")
public class BatchesController {

    @Autowired
    BatchServiceImpl batchService ;


    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_STAFF')")
    public ResponseEntity<List<BatchDTO>> getAllBatch(){
        List<BatchDTO> allBatches = batchService.getAllBatches() ;
        return ResponseEntity.ok(allBatches) ;
    }

    @GetMapping("/{batchId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_STAFF')")
    public ResponseEntity<BatchDTO> getBatchById(@PathVariable Integer batchId){
        BatchDTO batchDTO = batchService.getBatchById(batchId) ;
        return ResponseEntity.ok(batchDTO) ;
    }

    @PutMapping("/{batchId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_STAFF')")
    public ResponseEntity<BatchDTO> updateBatch(@PathVariable Integer batchId ,
                                                @RequestBody BatchDTO batchDTO){
        BatchDTO updateBatch = batchService.updateBatch(batchId , batchDTO) ;
        return ResponseEntity.ok(updateBatch) ;
    }

//    @GetMapping("/staff/{staffId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<BatchDTO>> getBatchByStaff(@PathVariable Integer staffId){
//        List<BatchDTO> allBatches = batchService.getBatchByStaff(staffId) ;
//        return ResponseEntity.ok(allBatches) ;
//    }

//
//    @PostMapping("/enroll/batch/{batchId}/staff/{staffId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<BatchDTO> enrollStaffToBatch(@PathVariable Integer staffId ,
//                                                             @PathVariable Integer batchId){
//        BatchDTO batch = batchService.enrollStaffToBatch(staffId , batchId ) ;
//        return ResponseEntity.ok(batch) ;
//    }
}
