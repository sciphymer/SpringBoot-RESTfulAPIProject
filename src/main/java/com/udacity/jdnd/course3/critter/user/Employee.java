package com.udacity.jdnd.course3.critter.user;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee extends User{
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(joinColumns = @JoinColumn(name="employee_id"))
    private Set<EmployeeSkill> skills = new HashSet<>();
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(joinColumns = @JoinColumn(name="employee_id"))
    private Set<DayOfWeek> daysAvailable = new HashSet<>();
    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
