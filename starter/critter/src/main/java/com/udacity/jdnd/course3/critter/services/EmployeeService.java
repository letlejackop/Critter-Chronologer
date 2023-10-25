package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeRequestDTO;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Service
public interface EmployeeService {
    EmployeeDTO save(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(Long employeeId);

    void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId);

    List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO);
}
