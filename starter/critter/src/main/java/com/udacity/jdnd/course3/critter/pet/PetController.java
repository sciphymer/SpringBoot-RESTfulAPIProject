package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    static PetService petService;
    static CustomerService customerService;
    @Autowired
    public void setPetService(PetService petService) {
        PetController.petService = petService;
    }
    @Autowired
    public void setCustomerService(CustomerService customerService) {
        PetController.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        return convertPetToPetDTO(petService.save(pet));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        try {
            return convertPetToPetDTO(petService.getPetById(petId));
        } catch(ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getPets();
        return pets.stream().map(pet->convertPetToPetDTO(pet)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        try {
            List<Pet> pets = petService.getPetsByOwnerId(ownerId);
            return pets.stream().map(pet->convertPetToPetDTO(pet)).collect(Collectors.toList());
        } catch(ResourceNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        try {
            petDTO.setOwnerId(pet.getCustomer().getId());
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        return petDTO;
    }

    private static Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO,pet);
        try {
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            pet.setCustomer(customer);
        } catch (ResourceNotFoundException e)  {
            System.out.println(e.getMessage());
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        return pet;
    }
}
