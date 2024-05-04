package com.project.attendance.Service;

import com.project.attendance.Payload.BatchDTO;
import com.project.attendance.Payload.UserDTO;

import java.util.List;

public interface BatchService {
    public BatchDTO createBatch(BatchDTO batchDTO) ;
    public List<BatchDTO> getAllBatches() ;
    public BatchDTO getBatchById(Integer batchId) ;
    public BatchDTO updateBatch(Integer batchId , BatchDTO batchDTO) ;
    public List<BatchDTO> getBatchByStaff(Integer staffId) ;
    public BatchDTO enrollStaffToBatch(Integer staffId , Integer batchId) ;
}
