package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    CustomerDTO save(CustomerDTO customerDTO);

    CustomerDTO getCustomerByPetId(Long petId);

    List<CustomerDTO> getAll();
}
