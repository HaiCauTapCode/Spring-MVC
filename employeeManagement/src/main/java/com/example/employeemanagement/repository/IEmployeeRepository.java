package com.example.employeemanagement.repository;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.dto.EmployeeSearchDTO;
import com.example.employeemanagement.model.Employee;

import java.util.ArrayList;

public interface IEmployeeRepository {
    ArrayList<EmployeeDTO> search(EmployeeSearchDTO employeeSearchDTO);

    Employee findById(int id);

    void create(Employee employee);
    void update(Employee employee);
    void delete(int id);
    Employee findByPhoneNumber(String phoneNumber);
}
