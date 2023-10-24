package com.example.employeemanagement.repository.impl;

import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.IDepartmentRepository;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class DepartmentRepository implements IDepartmentRepository {
    @Override
    public ArrayList<Department> findAll() {
        ArrayList<Department> departmentList = new ArrayList();
        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(
                    "select id, name from department;"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            Department department;
            while (resultSet.next()) {
                department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                departmentList.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departmentList;
    }
}
