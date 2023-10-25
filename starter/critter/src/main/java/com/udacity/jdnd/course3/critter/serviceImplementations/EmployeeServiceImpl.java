package com.udacity.jdnd.course3.critter.serviceImplementations;

import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.mappers.EmployeeMapper;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }


    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.save(map(employeeDTO));
        return employeeMapper.map(employee);
    }
    @Override
    public EmployeeDTO getEmployeeById(Long employeeId){
        return employeeMapper.map(employeeRepository.findById(employeeId).get());
    }
    @Override
    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        employeeRepository.findById(employeeId).ifPresent(employee -> employee.setDaysAvailable(daysAvailable));
    }

    @Override
    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO request) {
        return employeeRepository.getAllByDaysAvailableContains(request.getDate().getDayOfWeek())
                .stream()
                .filter(employee -> employee.getSkills().containsAll(request.getSkills()))
                .map(employeeMapper::map)
                .collect(Collectors.toList());
    }

    //couldn't implement the map method it in the mapper due to the need of
    // injecting the service into the mapper and that will be an overhead

    //Map DTO to entity
    private Employee map(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }


}
