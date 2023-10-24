package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.EmployeeCreateDTO;
import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.dto.EmployeeSearchDTO;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.IEmployeeRepository;
import com.example.employeemanagement.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Map;

@Service("employeeService")
public class EmployeeService implements IEmployeeService {
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public ArrayList<EmployeeDTO> search(EmployeeSearchDTO employeeSearchDTO) {
        if (employeeSearchDTO.getName() == null) {
            employeeSearchDTO.setName("");
        }

        if (employeeSearchDTO.getPhoneNumber() == null) {
            employeeSearchDTO.setPhoneNumber("");
        }

        if ("".equals(employeeSearchDTO.getFromBirthDate())) {
            employeeSearchDTO.setFromBirthDate(null);
        }

        if ("".equals(employeeSearchDTO.getToBirthDate())) {
            employeeSearchDTO.setToBirthDate(null);
        }
        return employeeRepository.search(employeeSearchDTO);
    }

    @Override
    public Employee findById(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void create(Employee employee) {
        employeeRepository.create(employee);
    }

    @Override
    public void update(Employee employee) {
        employeeRepository.update(employee);
    }

    public void delete(int id) {
        employeeRepository.delete(id);
    }

//    @Override
//    public void validate(EmployeeCreateDTO employeeCreateDTO, Map<String, String> errorMessage) {
//        if (employeeCreateDTO.getName().trim().equals("")) {
//            errorMessage.put("name", "Tên không được để trống");
//            return;
//        }
//
//        if (!employeeCreateDTO.getName().trim().matches("[a-zA-ZÀ-ỹ\\s]+")) {
//            errorMessage.put("name", "Tên không được có số và ký tự đặc biệt");
//            return;
//        }
//
//        if ("".equals(employeeCreateDTO.getBirthDate())) {
//            errorMessage.put("birthDate", "Ngày sinh không được để trống");
//            return;
//        } else {
//            LocalDate birthDate = LocalDate.parse(employeeCreateDTO.getBirthDate());
//            int currentYear = LocalDate.now().getYear();
//            if (currentYear - birthDate.getYear() <= 15) {
//                errorMessage.put("birthDate", "Phải trên 15 tuổi");
//                return;
//            }
//        }
//
//        if (employeeCreateDTO.getGender().trim().equals("")) {
//            errorMessage.put("gender", "Bắt buộc phải chọn giới tính");
//            return;
//        }
//
//        if (employeeCreateDTO.getSalary().trim().equals("")) {
//            errorMessage.put("salary", "Lương không được để trống");
//            return;
//        }
//
//        if (Double.parseDouble(employeeCreateDTO.getSalary()) <= 150000) {
//            errorMessage.put("salary", "Lương phải lớn hơn 150.000");
//            return;
//        }
//
//        if (employeeCreateDTO.getPhoneNumber().trim().equals("")) {
//            errorMessage.put("phoneNumber", "Số điện thoại không được để trống");
//            return;
//        }
//
//        if (!employeeCreateDTO.getPhoneNumber().matches("09[0-9]{8}")) {
//            errorMessage.put("phoneNumber", "Vui lòng nhập đúng số điện thoại [09xxx(xxx = 8 số)]");
//            return;
//        }
//
//        if (employeeCreateDTO.getDepartmentId().equals("")) {
//            errorMessage.put("departmentId", "Bắt buộc phải chọn bộ phận");
//        }
//    }

     @Override
    public void validateNumberPhoneExists(String phoneNumber, BindingResult bindingResult) {
        if (employeeRepository.findByPhoneNumber(phoneNumber) != null) {
//            errorMessage.put("phoneNumber", "Số điện thoại đã tồn tại");
            bindingResult.rejectValue("phoneNumber", "", "Số điện thoại đã tồn tại");
        }
    }
}
