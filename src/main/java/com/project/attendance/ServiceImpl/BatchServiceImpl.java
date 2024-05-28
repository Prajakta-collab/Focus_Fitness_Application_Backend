package com.project.attendance.ServiceImpl;

import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.Batch;
import com.project.attendance.Payload.DTO.BatchDTO;
import com.project.attendance.Repository.BatchRepository;
import com.project.attendance.Service.BatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    BatchRepository batchRepository ;


    @Autowired
    ModelMapper modelMapper ;
//
//    @Override
//    public BatchDTO createBatch(BatchDTO batchDTO) {
//        Batch batch = modelMapper.map(batchDTO , Batch.class) ;
//        Batch createdBatch = batchRepository.save(batch) ;
//        return modelMapper.map(createdBatch , BatchDTO.class) ;
//    }

    @Override
    public List<BatchDTO> getAllBatches() {
        List<Batch> batches = batchRepository.findAll() ;

        List<BatchDTO> batchDTOs= batches.stream()
                .map(batch -> modelMapper.map(batch , BatchDTO.class))
                .collect(Collectors.toList()) ;

        return batchDTOs ;
    }

    @Override
    public BatchDTO getBatchById(Integer batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(()-> new ResourceNotFoundException("Batch" , "batchId" , batchId));
        return modelMapper.map(batch , BatchDTO.class) ;
    }

    @Override
    public BatchDTO updateBatch(Integer batchId, BatchDTO batchDTO) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(()-> new ResourceNotFoundException("Batch" , "batchId" , batchId));

        if(batchDTO.getBatchName() != ""){
            batch.setBatchName(batchDTO.getBatchName());
        }


        Batch updatedBatch = batchRepository.save(batch) ;
        return modelMapper.map(updatedBatch , BatchDTO.class) ;
    }
}
