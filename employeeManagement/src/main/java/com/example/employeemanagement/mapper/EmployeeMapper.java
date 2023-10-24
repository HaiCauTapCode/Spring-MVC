package com.example.employeemanagement.mapper;

import com.example.employeemanagement.dto.EmployeeCreateDTO;
import com.example.employeemanagement.dto.EmployeeUpdateDTO;
import com.example.employeemanagement.model.Employee;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component("employeeMapper")
public interface EmployeeMapper {
    Employee toEmployeeFromEmployeeCreateDTO(EmployeeCreateDTO employeeCreateDTO);
    EmployeeUpdateDTO toEmployeeUpdateDTOFromEmployee(Employee employee);
    Employee toEmployeeFromEmployeeUpdateDTO(EmployeeUpdateDTO employeeUpdateDTO);

}
