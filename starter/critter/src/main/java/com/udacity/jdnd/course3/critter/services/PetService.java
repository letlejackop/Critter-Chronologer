package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PetService {
    PetDTO save(PetDTO petDTO);


    PetDTO getPetById(Long petId);

    List<PetDTO> getAll();

    List<PetDTO> getPetsByOwner(long ownerId);
}
