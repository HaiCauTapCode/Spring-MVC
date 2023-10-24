package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeCreateDTO;
import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.dto.EmployeeSearchDTO;
import com.example.employeemanagement.model.Employee;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

public interface IEmployeeService {
    ArrayList<EmployeeDTO> search(EmployeeSearchDTO employeeSearchDTO);

    Employee findById(int id);

    void create(Employee employee);

    void update(Employee employee);

    void delete(int id);

//    void validate(EmployeeCreateDTO employeeCreateDTO, Map<String, String> errorMessage);
    void validateNumberPhoneExists(String phoneNumber, BindingResult bindingResult);
}
