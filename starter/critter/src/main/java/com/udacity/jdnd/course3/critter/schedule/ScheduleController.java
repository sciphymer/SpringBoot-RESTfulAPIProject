package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
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

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.createSchedule(convertScheduleDTOToSchedule(scheduleDTO));
        return convertScheduleToScheduleDTO(schedule);
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
        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,scheduleDTO);
        return scheduleDTO;
    }
}
