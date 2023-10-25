package com.udacity.jdnd.course3.critter.serviceImplementations;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.mappers.ScheduleMapper;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper, PetRepository petRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public ScheduleDTO save(ScheduleDTO scheduleDTO){
        return scheduleMapper.map(scheduleRepository.save(map(scheduleDTO)));
    }

    @Override
    public List<ScheduleDTO> getScheduleForPet(Long petId){
        return scheduleRepository.getAllByPetsContains(petRepository.findById(petId).get()).stream()
                .map(scheduleMapper::map)
                .collect(Collectors.toList());
    }
    @Override
    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        return scheduleRepository.getAllByEmployeesContains(employee).stream()
                .map(scheduleMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {

        return scheduleRepository.getAllByPetsIn(customerRepository.findById(customerId).get().getPets()).stream()
                .map(scheduleMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getAll() {
        return scheduleRepository.findAll().stream()
                .map(scheduleMapper::map)
                .collect(Collectors.toList());
    }

    //couldn't implement the map method it in the mapper due to the need of
    // injecting the service into the mapper and that will be an overhead

    //Map DTO to entity
    private Schedule map(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDTO.getId());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setPets(scheduleDTO.getPetIds().stream().map(id -> petRepository.findById(id).get()).collect(Collectors.toList()));
        schedule.setEmployees(scheduleDTO.getEmployeeIds().stream().map(id -> employeeRepository.findById(id).get()).collect(Collectors.toList()));
        schedule.setActivities(scheduleDTO.getActivities());
        return schedule;
    }

}
