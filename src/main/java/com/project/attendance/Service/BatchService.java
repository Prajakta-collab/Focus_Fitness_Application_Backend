package com.project.attendance.Service;

import com.project.attendance.Payload.DTO.BatchDTO;

import java.util.List;

public interface BatchService {
    public List<BatchDTO> getAllBatches() ;
    public BatchDTO getBatchById(Integer batchId) ;
    public BatchDTO updateBatch(Integer batchId , BatchDTO batchDTO) ;
}
