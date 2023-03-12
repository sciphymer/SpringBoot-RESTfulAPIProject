package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    public Pet save(Pet pet){
        return petRepository.save(pet);
    }

    public Pet getPetById(Long petId) throws ResourceNotFoundException {
        return petRepository.findById(petId).orElseThrow(
                ()-> new ResourceNotFoundException(
                        String.format("Pet id: %s cannot be found", petId)
                )
        );
    }

    public List<Pet> getPets(){
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) throws ResourceNotFoundException{
        return petRepository.getPetsByOwnerId(ownerId).orElseThrow(
                ()-> new ResourceNotFoundException(
                        String.format("No pets are not on this owner id: %s", ownerId)
                )
        );
    }
}
