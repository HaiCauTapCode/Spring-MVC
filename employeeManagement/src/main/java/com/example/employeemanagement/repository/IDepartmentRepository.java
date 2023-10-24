package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Department;

import java.util.ArrayList;

public interface IDepartmentRepository {
    ArrayList<Department> findAll();
}
