package com.example.employeemanagement.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EmployeeCreateDTO implements Validator {
    @NotBlank(message = "Tên không được để trống")
    @Length(max = 50, message = "Tên quá 50 kí tự")
    private String name;
    @NotBlank(message = "Ngày sinh không được để trống")
    private String birthDate;
    @NotBlank(message = "Bắt buộc phải chọn giới tính")
    private String gender;
    @NotBlank(message = "Lương không được để trống")

    private String salary;
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "09[0-9]{8}", message = "Vui lòng nhập đúng số điện thoại [09xxx(xxx = 8 số)]")
    private String phoneNumber;
    @NotBlank(message = "Bắt buộc chọn bộ phận")
    private String departmentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeCreateDTO employeeCreateDTO = (EmployeeCreateDTO) target;

        if (!employeeCreateDTO.getName().trim().matches("[a-zA-ZÀ-ỹ\\s]*")) {
            errors.rejectValue("name", "", "Tên không được có số và ký tự đặc biệt");
        }

        if (!employeeCreateDTO.getBirthDate().equals("")) {
            try {
                LocalDate birthDate = LocalDate.parse(employeeCreateDTO.getBirthDate());
                int currentYear = LocalDate.now().getYear();
                if (currentYear - birthDate.getYear() <= 15) {
                    errors.rejectValue("birthDate", "", "Phải trên 15 tuổi");
                }
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }

//        if (employeeCreateDTO.getGender().trim().equals("")) {
//            errors.rejectValue("gender", "", "Bắt buộc phải chọn giới tính");
//        }

//        if (employeeCreateDTO.getSalary().trim().equals("")) {
//            errors.rejectValue("salary", "", "Lương không được để trống");
//        }

        if (!employeeCreateDTO.getSalary().trim().equals("")) {
            try {
                double salary = Double.parseDouble(employeeCreateDTO.getSalary());
                if (salary <= 150000) {
                    errors.rejectValue("salary", "", "Lương phải lớn hơn 150.000");
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("salary", "", "Lương phải là số");
            }
        }

//        if (employeeCreateDTO.getPhoneNumber().trim().equals("")) {
//            errors.rejectValue("phoneNumber", "", "Số điện thoại không được để trống");
//        }

//        if (!employeeCreateDTO.getPhoneNumber().matches("09[0-9]{8}")) {
//            errors.rejectValue("phoneNumber", "", "Vui lòng nhập đúng số điện thoại [09xxx(xxx = 8 số)]");
//        }

//        if (employeeCreateDTO.getDepartmentId().equals("")) {
//            errors.rejectValue("departmentId", "", "Bắt buộc phải chọn bộ phận");
//        }
    }
}

