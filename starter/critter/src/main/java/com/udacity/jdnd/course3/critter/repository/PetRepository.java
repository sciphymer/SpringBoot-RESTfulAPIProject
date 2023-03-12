package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    @Query("SELECT p FROM Pet p WHERE p.customer.id = :ownerId")
    Optional<List<Pet>> getPetsByOwnerId(long ownerId);
}
