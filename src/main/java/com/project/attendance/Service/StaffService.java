package com.project.attendance.Service;

import com.project.attendance.Payload.StaffDTO;

import java.util.List;

public interface StaffService {
    public StaffDTO createStaff(StaffDTO staffDTO) ;
    public StaffDTO updateStaff(Integer staffId , StaffDTO staffDTO) ;
    public void deleteStaff(Integer staffId) ;
    public List<StaffDTO> getAllStaff() ;
    public StaffDTO getStaffById(Integer staffId) ;
}
