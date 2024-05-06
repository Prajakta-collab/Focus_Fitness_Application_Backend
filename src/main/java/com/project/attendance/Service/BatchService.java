package com.project.attendance.Service;

import com.project.attendance.Payload.BatchDTO;
import com.project.attendance.Payload.UserDTO;

import java.util.List;

public interface BatchService {
    public List<BatchDTO> getAllBatches() ;
    public BatchDTO getBatchById(Integer batchId) ;
    public BatchDTO updateBatch(Integer batchId , BatchDTO batchDTO) ;
}
