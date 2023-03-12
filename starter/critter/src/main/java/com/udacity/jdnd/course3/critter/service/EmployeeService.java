package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }
    public Employee findEmployeeById(Long employeeId) throws ResourceNotFoundException {
        return (employeeId!=null)?
                employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        String.format("Employee of id: %s cannot be found",employeeId)
                )):null;
    }

    public List<Employee> findAllEmployeesByIds(List<Long> employeeIds){
        return (employeeIds!=null) ? employeeRepository.findAllById(employeeIds):null;
    }

    public Employee setEmployeeAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) throws ResourceNotFoundException{
        if(employeeId!=null && employeeId !=null) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("Employee of id: %s cannot be found", employeeId)
                    ));
            employee.getDaysAvailable().addAll(daysAvailable);
            return employeeRepository.save(employee);
        } else{
            return null;
        }

    }

    public List<Employee> findEmployeeBySkillsAndDateAvailable(Set<EmployeeSkill> employeeSkills, DayOfWeek daysAvailable){
        return employeeRepository.findEmployeeBySkillsAndDateAvailable(employeeSkills, daysAvailable);
    }


}
