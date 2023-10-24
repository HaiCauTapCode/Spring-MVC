package com.example.employeemanagement.repository.impl;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.dto.EmployeeSearchDTO;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.IEmployeeRepository;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class EmployeeRepository implements IEmployeeRepository {
    @Override
    public ArrayList<EmployeeDTO> search(EmployeeSearchDTO employeeSearchDTO) {
        ArrayList<EmployeeDTO> employeeDTOList = new ArrayList();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select\n" +
                    "    e.id, e.full_name, e.birth_date, e.gender,\n" +
                    "    e.salary, e.phone_number, e.department_id, d.name department_name\n" +
                    "from\n" +
                    "    employee e left join department d on e.department_id = d.id ");

            // Tìm kiếm theo tên
            stringBuilder.append("where e.full_name like concat('%', ?, '%') "); // null

            // Tìm kiếm theo ngày sinh
            if (employeeSearchDTO.getFromBirthDate() != null || employeeSearchDTO.getToBirthDate() != null) {
                if (employeeSearchDTO.getFromBirthDate() == null) {
                    stringBuilder.append(String.format
                            ("and e.birth_date <= '%s' ", employeeSearchDTO.getToBirthDate()));
                } else if (employeeSearchDTO.getToBirthDate() == null) {
                    stringBuilder.append(String.format
                            ("and e.birth_date >= '%s' ", employeeSearchDTO.getFromBirthDate()));
                } else {
                    stringBuilder.append(String.format
                            ("and e.birth_date between '%s' and '%s' ", employeeSearchDTO.getFromBirthDate(), employeeSearchDTO.getToBirthDate()));
                }
            }

            // Tìm kiếm theo giới tính
            if (employeeSearchDTO.getGender() != null && !employeeSearchDTO.getGender().isEmpty()) {
                stringBuilder.append(String.format("and e.gender = %s ", employeeSearchDTO.getGender()));
            }

            // Tìm kiếm theo mức lương
            if ("lt5".equals(employeeSearchDTO.getSalary())) {
                stringBuilder.append("and e.salary < 5000000 ");
            } else if ("5-10".equals(employeeSearchDTO.getSalary())) {
                stringBuilder.append("and e.salary >= 5000000 and e.salary < 10000000 ");
            } else if ("10-15".equals(employeeSearchDTO.getSalary())) {
                stringBuilder.append("and e.salary >= 10000000 and e.salary < 15000000 ");
            } else if ("gt15".equals(employeeSearchDTO.getSalary())) {
                stringBuilder.append("and e.salary >= 15000000 ");
            }

            // Tìm kiếm sđt
            stringBuilder.append("and e.phone_number like concat('%', ?, '%') ");

            // Tìm kiếm bộ phận
            if (employeeSearchDTO.getDepartmentId() != null && !employeeSearchDTO.getDepartmentId().isEmpty()) {
                stringBuilder.append(String.format("and e.department_id = %s ", employeeSearchDTO.getDepartmentId()));
            }

            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(
                    stringBuilder.toString()
            );

            preparedStatement.setString(1, employeeSearchDTO.getName());
            preparedStatement.setString(2, employeeSearchDTO.getPhoneNumber());
            ResultSet resultSet = preparedStatement.executeQuery();

            EmployeeDTO employeeDTO;
            while (resultSet.next()) {
                employeeDTO = new EmployeeDTO();
                employeeDTO.setId(resultSet.getInt("id"));
                employeeDTO.setName(resultSet.getString("full_name"));
                employeeDTO.setBirthDate(LocalDate.parse(resultSet.getString("birth_date")));
                employeeDTO.setGender(resultSet.getBoolean("gender"));
                employeeDTO.setSalary(resultSet.getDouble("salary"));
                employeeDTO.setPhoneNumber(resultSet.getString("phone_number"));
                employeeDTO.setDepartmentId(resultSet.getInt("department_id"));
                employeeDTO.setDeparmentName(resultSet.getString("department_name"));
                employeeDTOList.add(employeeDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeDTOList;
    }

    @Override
    public Employee findById(int id) {
        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(
                    "select id, full_name, birth_date, gender, salary, phone_number, department_id from employee where id = ?;"
            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Employee employee;
            if (resultSet.next()) {
                employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("full_name"));
                employee.setBirthDate(LocalDate.parse(resultSet.getString("birth_date")));
                employee.setGender(resultSet.getBoolean("gender"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setPhoneNumber(resultSet.getString("phone_number"));
                employee.setDepartmentId(resultSet.getInt("department_id"));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Employee employee) {
        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(
                    "insert into employee(full_name, birth_date, gender, salary, phone_number, department_id) values (?, ?, ?, ?, ?, ?);"
            );
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getBirthDate().toString());
            preparedStatement.setBoolean(3, employee.isGender());
            preparedStatement.setDouble(4, employee.getSalary());
            preparedStatement.setString(5, employee.getPhoneNumber());
            preparedStatement.setInt(6, employee.getDepartmentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Employee employee) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("update\n" +
                "    employee\n" +
                "set\n" +
                "    full_name  = ?,\n" +
                "    birth_date = ?,\n" +
                "    gender     = ?,\n" +
                "    salary     = ?,\n" +
                "    phone_number     = ?,\n" +
                "    department_id     = ?\n" +
                "where id = ?;");

        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(
                    stringBuilder.toString()
            );
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getBirthDate().toString());
            preparedStatement.setBoolean(3, employee.isGender());
            preparedStatement.setDouble(4, employee.getSalary());
            preparedStatement.setString(5, employee.getPhoneNumber());
            preparedStatement.setInt(6, employee.getDepartmentId());
            preparedStatement.setInt(7, employee.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(
                    "delete from employee where id = ?;"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee findByPhoneNumber(String phoneNumber) {
        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement("" +
                    "select id, full_name, birth_date, gender, " +
                    "salary, phone_number, department_id " +
                    "from employee where phone_number = ?;"
            );
            preparedStatement.setString(1, phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            Employee employee;
            if (resultSet.next()) {
                employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("full_name"));
                employee.setBirthDate(LocalDate.parse(resultSet.getString("birth_date")));
                employee.setGender(resultSet.getBoolean("gender"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setPhoneNumber(resultSet.getString("phone_number"));
                employee.setDepartmentId(resultSet.getInt("department_id"));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
