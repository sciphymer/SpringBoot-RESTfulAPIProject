package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("SELECT e FROM Employee e " +
            "WHERE e.skills in :employeeSkills " +
            "AND e.daysAvailable = :daysAvailable")
    List<Employee> findEmployeeBySkillsAndDateAvailable(Set<EmployeeSkill> employeeSkills, DayOfWeek daysAvailable);

}
