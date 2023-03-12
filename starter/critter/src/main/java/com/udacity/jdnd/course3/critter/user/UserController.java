package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    static CustomerService customerService;
    static EmployeeService employeeService;
    static PetService petService;
    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @Autowired
    public void setPetService(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerService.save(convertCustomerDTOToCustomer(customerDTO));
        return convertCustomerToCustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return customers.stream()
                .map(customer -> convertCustomerToCustomerDTO(customer))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        try {
            Customer owner = customerService.getOwnerByPetId(petId);
            return convertCustomerToCustomerDTO(owner);
        } catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.save(convertEmployeeDTOToEmployee(employeeDTO));
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        try {
            Employee employee = employeeService.findEmployeeById(employeeId);
            return convertEmployeeToEmployeeDTO(employee);
        } catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        try {
            employeeService.setEmployeeAvailability(daysAvailable, employeeId);
        } catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.findEmployeeBySkillsAndDateAvailable(employeeDTO.getSkills(),employeeDTO.getDate().getDayOfWeek());
        return employees.stream().map(employee -> convertEmployeeToEmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    private static CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        try {
            List<Long> petIds = customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        return customerDTO;
    }

    private static Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        try {
            List<Pet> pets = petService.getAllPetsByIds(customerDTO.getPetIds());
            customer.setPets(pets);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        return customer;
    }

    private static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        return employee;
    }

}
