package com.udacity.jdnd.course3.critter.mappers;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {

    PetMapper MAPPER = Mappers.getMapper(PetMapper.class);

    @Mapping(target = "ownerId", source = "owner.id")
    PetDTO map(Pet Pet);


    List<PetDTO> map(List<Pet> Pets);
}