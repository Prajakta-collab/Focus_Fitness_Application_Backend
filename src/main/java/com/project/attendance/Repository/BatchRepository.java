package com.project.attendance.Repository;

import com.project.attendance.Model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch , Integer> {
}
