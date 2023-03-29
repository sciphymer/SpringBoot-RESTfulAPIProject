package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    static ScheduleService scheduleService;
    static EmployeeService employeeService;
    static PetService petService;

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @Autowired
    public void setPetService(PetService petService) {this.petService = petService;}
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();
        if(employeeIds.size()>0 && petIds.size()>0){
            Schedule schedule = scheduleService.createSchedule(convertScheduleDTOToSchedule(scheduleDTO));
            return convertScheduleToScheduleDTO(schedule);
        } else return null;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules.stream().map(s->convertScheduleToScheduleDTO(s)).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        try {
            List<Schedule> schedules = scheduleService.findScheduleByPetId(petId);
            return schedules.stream().map(s -> convertScheduleToScheduleDTO(s)).collect(Collectors.toList());
        } catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        try {
            List<Schedule> schedules = scheduleService.findScheduleByEmployeeId(employeeId);
            return schedules.stream().map(s -> convertScheduleToScheduleDTO(s)).collect(Collectors.toList());
        } catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        try {
            List<Schedule> schedules = scheduleService.findScheduleByCustomerId(customerId);
            return schedules.stream().map(s -> convertScheduleToScheduleDTO(s)).collect(Collectors.toList());
        } catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);
        try {
            List<Employee> employees = employeeService.findAllEmployeesByIds(scheduleDTO.getEmployeeIds());
            schedule.setEmployees(employees);
            List<Pet> pets = petService.getAllPetsByIds(scheduleDTO.getPetIds());
            schedule.setPets(pets);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,scheduleDTO);
        try {
            scheduleDTO.setEmployeeIds(
                    schedule.getEmployees().stream()
                            .map(employee -> employee.getId()).collect(Collectors.toList())
            );
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        try {
            scheduleDTO.setPetIds(
                    schedule.getPets().stream()
                            .map(pet -> pet.getId()).collect(Collectors.toList())
            );
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        return scheduleDTO;
    }
}
