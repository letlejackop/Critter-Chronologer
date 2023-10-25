package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {
    ScheduleDTO save(ScheduleDTO scheduleDTO);

    List<ScheduleDTO> getScheduleForPet(Long petId);

    List<ScheduleDTO> getScheduleForEmployee(long employeeId);

    List<ScheduleDTO> getScheduleForCustomer(long customerId);

    List<ScheduleDTO> getAll();
}
