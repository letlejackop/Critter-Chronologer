package com.udacity.jdnd.course3.critter.serviceImplementations;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.mappers.PetMapper;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;
    private final PetMapper petMapper;


    public PetServiceImpl(CustomerRepository customerRepository, PetRepository petRepository, PetMapper petMapper) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }


    @Override
    public PetDTO save(PetDTO petDTO){
        Pet pet = map(petDTO);
        Customer customer = customerRepository.getOne(petDTO.getOwnerId());
        pet.setOwner(customer);
        pet = petRepository.save(pet);
        customer.addPet(pet);
        customerRepository.save(customer);
        return petMapper.map(pet);
    }

    @Override
    public PetDTO getPetById(Long petId) {
        return petRepository.findById(petId)
                .map(petMapper::map)
                .orElse(null);
    }

    @Override
    public List<PetDTO> getAll() {
        return petRepository.findAll().stream()
                .map(petMapper::map)
                .collect(Collectors.toList());
    }
    @Override
    public List<PetDTO> getPetsByOwner(long ownerId) {
        return petRepository.getAllByOwnerId(ownerId).stream()
                .map(petMapper::map)
                .collect(Collectors.toList());
    }

    //couldn't implement the map method it in the mapper due to the need of
    // injecting the service into the mapper and that will be an overhead

    //Map DTO to entity
    private Pet map(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setType(petDTO.getType());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setOwner(customerRepository.getOne(petDTO.getOwnerId()));
        return pet;
    }
}
