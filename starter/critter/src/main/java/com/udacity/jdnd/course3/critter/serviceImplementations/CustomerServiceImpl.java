package com.udacity.jdnd.course3.critter.serviceImplementations;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.mappers.CustomerMapper;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    private final PetRepository petRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.petRepository = petRepository;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO){
        Customer customer =customerRepository.save(map(customerDTO));
        return customerMapper.map(customer);
    }

    @Override
    public CustomerDTO getCustomerByPetId(Long petId){
        return customerMapper.map(petRepository.findById(petId).get().getOwner());
    }



    @Override
    public List<CustomerDTO> getAll() {
        return customerRepository.findAll().stream().map(customerMapper::map).collect(Collectors.toList());
    }

    //couldn't implement the map method it in the mapper due to the need of
    // injecting the service into the mapper and that will be an overhead

    //Map DTO to entity
    private Customer map(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());

        List<Long> petIds = customerDTO.getPetIds();
        List<Pet> pets = new ArrayList<>();


        if (petIds != null) {
            for (Long petId : petIds) {
                petRepository.findById(petId).ifPresent(pets::add);
            }
        }
        customer.setPets(pets);
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        return customer;
    }
 }
