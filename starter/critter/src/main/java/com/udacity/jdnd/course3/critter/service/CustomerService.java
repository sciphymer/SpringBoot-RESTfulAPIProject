package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getOwnerByPetId(Long petId) throws ResourceNotFoundException {
        return (petId!=null)?customerRepository.findByPetId(petId).orElseThrow(
                ()-> new ResourceNotFoundException(
                        String.format("No customers are the owner of this pet: %s", petId)
                )
        ):null;
    }

    public Customer getCustomerById(Long ownerId) throws ResourceNotFoundException{
        return (ownerId!=null)?customerRepository.findById(ownerId).orElseThrow(
                ()-> new ResourceNotFoundException(
                        String.format("Owner with id: %s is not found", ownerId)
                )
        ):null;
    }


}
