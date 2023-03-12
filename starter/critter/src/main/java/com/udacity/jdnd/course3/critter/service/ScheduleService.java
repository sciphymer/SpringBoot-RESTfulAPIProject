package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleByPetId (Long petId) throws ResourceNotFoundException{
       Optional<List<Schedule>> schedules = scheduleRepository.findScheduleByPetId(petId);
       return schedules.orElseThrow(
               ()->new ResourceNotFoundException(
               String.format("The schedule of Pet Id: %s cannot be found",petId)
       ));
    }

    public List<Schedule> findScheduleByEmployeeId (Long employeeId) throws ResourceNotFoundException{
        Optional<List<Schedule>> schedules = scheduleRepository.findScheduleByEmployeeId(employeeId);
        return schedules.orElseThrow(
                ()->new ResourceNotFoundException(
                        String.format("The schedule of Employee Id: %s cannot be found",employeeId)
                ));
    }

    public List<Schedule> findScheduleByCustomerId (Long customerId) throws ResourceNotFoundException{
        Optional<List<Schedule>> schedules = scheduleRepository.findScheduleByCustomerId(customerId);
        return schedules.orElseThrow(
                ()->new ResourceNotFoundException(
                        String.format("The schedule of Customer Id: %s cannot be found",customerId)
                ));
    }
}
