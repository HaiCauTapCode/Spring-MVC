package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.IDepartmentRepository;
import com.example.employeemanagement.repository.impl.DepartmentRepository;
import com.example.employeemanagement.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("departmentService")
public class DepartmentService implements IDepartmentService {
    @Autowired
    private IDepartmentRepository departmentRepository;
    @Override
    public ArrayList<Department> findAll() {
        return departmentRepository.findAll();
    }
}
