package com.project.attendance.Controller;

import com.project.attendance.Payload.BatchDTO;
import com.project.attendance.ServiceImpl.BatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/batch")
public class BatchesController {

    @Autowired
    BatchServiceImpl batchService ;

    @PostMapping("")
    public ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batchDTO){
        BatchDTO createdBatch = batchService.createBatch(batchDTO) ;
        return ResponseEntity.ok(createdBatch) ;
    }

    @GetMapping("")
    public ResponseEntity<List<BatchDTO>> getAllBatch(){
        List<BatchDTO> allBatches = batchService.getAllBatches() ;
        return ResponseEntity.ok(allBatches) ;
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<BatchDTO> getBatchById(@PathVariable Integer batchId){
        BatchDTO batchDTO = batchService.getBatchById(batchId) ;
        return ResponseEntity.ok(batchDTO) ;
    }

    @PutMapping("/{batchId}")
    public ResponseEntity<BatchDTO> updateBatch(@PathVariable Integer batchId ,
                                                @RequestBody BatchDTO batchDTO){
        BatchDTO updateBatch = batchService.updateBatch(batchId , batchDTO) ;
        return ResponseEntity.ok(updateBatch) ;
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<BatchDTO>> getBatchByStaff(@PathVariable Integer staffId){
        List<BatchDTO> allBatches = batchService.getBatchByStaff(staffId) ;
        return ResponseEntity.ok(allBatches) ;
    }


    @PostMapping("/enroll/batch/{batchId}/staff/{staffId}")
    public ResponseEntity<BatchDTO> enrollStaffToBatch(@PathVariable Integer staffId ,
                                                             @PathVariable Integer batchId){
        BatchDTO batch = batchService.enrollStaffToBatch(staffId , batchId ) ;
        return ResponseEntity.ok(batch) ;
    }
}
