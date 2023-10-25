package com.udacity.jdnd.course3.critter.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String phoneNumber;

    private String notes;

    @OneToMany(targetEntity = Pet.class)
    private List<Pet> pets;

    public void addPet(Pet pet) {
        pets.add(pet);
    }

}
