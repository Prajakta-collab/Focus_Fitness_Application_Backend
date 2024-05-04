package com.project.attendance.ServiceImpl;

import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.Batch;
import com.project.attendance.Model.Staff;
import com.project.attendance.Payload.BatchDTO;
import com.project.attendance.Payload.StaffDTO;
import com.project.attendance.Repository.BatchRepository;
import com.project.attendance.Repository.StaffRepository;
import com.project.attendance.Service.StaffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository ;

    @Autowired
    BatchRepository batchRepository ;

    @Autowired
    ModelMapper modelMapper ;

    @Override
    public StaffDTO createStaff(StaffDTO staffDTO) {
        Staff staff = modelMapper.map(staffDTO , Staff.class) ;
        Staff createdStaff = staffRepository.save(staff) ;
        return modelMapper.map(createdStaff , StaffDTO.class) ;
    }

    @Override
    public StaffDTO updateStaff(Integer staffId, StaffDTO staffDTO) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(()-> new ResourceNotFoundException("Staff" , "staffId" , staffId));

        if (Objects.nonNull(staffDTO.getMobile_no()) && !staffDTO.getMobile_no().isEmpty()) {
            staff.setMobile_no(staffDTO.getMobile_no());
        }

        if (Objects.nonNull(staffDTO.getEmail()) && !staffDTO.getEmail().isEmpty()) {
            staff.setEmail(staffDTO.getEmail());
        }

        if (Objects.nonNull(staffDTO.getFirstName()) && !staffDTO.getFirstName().isEmpty()) {
            staff.setFirstName(staffDTO.getFirstName());
        }

        if (Objects.nonNull(staffDTO.getLastName()) && !staffDTO.getLastName().isEmpty()) {
            staff.setLastName(staffDTO.getLastName());
        }

        if (Objects.nonNull(staffDTO.getAddress()) && !staffDTO.getAddress().isEmpty()) {
            staff.setAddress(staffDTO.getAddress());
        }

        if (Objects.nonNull(staffDTO.getPassword()) && !staffDTO.getPassword().isEmpty()) {
            staff.setPassword(staffDTO.getPassword());
        }

        Staff updatedStaff = staffRepository.save(staff) ;
        return modelMapper.map(updatedStaff , StaffDTO.class) ;
    }

    @Override
    public void deleteStaff(Integer staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(()-> new ResourceNotFoundException("Staff" , "staffId" , staffId));

        List<Batch> batches = staff.getBatches() ;

        for(Batch b : batches){
            b.setStaff(null);
            batchRepository.save(b) ;
        }

        staffRepository.deleteById(staffId);
        return ;
    }

    @Override
    public List<StaffDTO> getAllStaff() {
        List<Staff> staffs = staffRepository.findAll() ;

        List<StaffDTO> staffDTOS= staffs.stream()
                .map(staff -> modelMapper.map(staff , StaffDTO.class))
                .collect(Collectors.toList()) ;

        return staffDTOS ;
    }

    @Override
    public StaffDTO getStaffById(Integer staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(()-> new ResourceNotFoundException("Staff" , "staffId" , staffId));
        return modelMapper.map(staff , StaffDTO.class) ;
    }
}
