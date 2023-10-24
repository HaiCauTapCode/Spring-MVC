package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeCreateDTO;
import com.example.employeemanagement.dto.EmployeeSearchDTO;
import com.example.employeemanagement.dto.EmployeeUpdateDTO;
import com.example.employeemanagement.mapper.EmployeeMapper;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.IDepartmentService;
import com.example.employeemanagement.service.IEmployeeService;
import com.example.employeemanagement.service.impl.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/employees")
public class EmployeeManagementController {
    @Autowired
    @Qualifier("employeeService")
    private IEmployeeService employeeService;

    @Autowired
    @Qualifier("departmentService")
    private IDepartmentService departmentService = new DepartmentService();

    @Autowired
    private EmployeeMapper employeeMapper;

    @ModelAttribute("departmentList")
    ArrayList<Department> departmentList() {
        return departmentService.findAll();
    }

    @GetMapping("create")
    public String showCreate(Model model) {
        model.addAttribute("employeeCreateDTO", new EmployeeCreateDTO());
//        model.addAttribute("departmentList", departmentService.findAll());
        return "employee/create_employee";
    }

    @GetMapping("edit")
    public String showEdit(Model model, int id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return "error404";
        } else {
            EmployeeUpdateDTO employeeUpdateDTO = employeeMapper.toEmployeeUpdateDTOFromEmployee(employee);
//            EmployeeUpdateDTO employeeUpdateDTO = new EmployeeUpdateDTO();
//            employeeUpdateDTO.setId(String.valueOf(employee.getId()));
//            employeeUpdateDTO.setName(employee.getName());
//            employeeUpdateDTO.setBirthDate(String.valueOf(employee.getBirthDate()));
//            employeeUpdateDTO.setGender(String.valueOf(employee.isGender()));
//            employeeUpdateDTO.setSalary(String.valueOf(employee.getSalary()));
//            employeeUpdateDTO.setPhoneNumber(employee.getPhoneNumber());
//            employeeUpdateDTO.setDepartmentId(String.valueOf(employee.getDepartmentId()));
            model.addAttribute("employeeUpdateDTO", employeeUpdateDTO);
//            model.addAttribute("departmentList", departmentService.findAll());
            return "employee/edit_employee";
        }
    }

    @GetMapping("delete")
    public String delete(int id, RedirectAttributes redirectAttributes) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return "error404";
        } else {
            employeeService.delete(employee.getId());
            redirectAttributes.addFlashAttribute("message", "Xóa thành công");
            return "redirect:/employees";
        }
    }

    @GetMapping("view")
    public String view(Model model, int id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return "error404";
        } else {
            model.addAttribute("employee", employee);
//            model.addAttribute("departmentList", departmentService.findAll());
            return "employee/deltail_employee";
        }
    }

    @GetMapping("")
    public String showList(Model model, EmployeeSearchDTO employeeSearchDTO) {
        model.addAttribute("employeeSearchDTO", employeeSearchDTO);
        model.addAttribute("employeeList", employeeService.search(employeeSearchDTO));
//        model.addAttribute("departmentList", departmentService.findAll());
        return "employee/list_employee";
    }

    @PostMapping("create")
    public String create(Model model, @Validated @ModelAttribute("employeeCreateDTO")
    EmployeeCreateDTO employeeCreateDTO, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        employeeCreateDTO.validate(employeeCreateDTO, bindingResult);
        employeeService.validateNumberPhoneExists(employeeCreateDTO.getPhoneNumber(), bindingResult);

//        HashMap<String, String> errorMessage = new HashMap<>();
//        if (!employeeCreateDTO.getPhoneNumber().equals(employeeCreateDTO.getPhoneNumber())) {
//            employeeService.validateNumberPhoneExists(employeeCreateDTO, errorMessage);
//        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeCreateDTO", employeeCreateDTO);
//            model.addAttribute("errorMessage", errorMessage);
//            model.addAttribute("departmentList", departmentService.findAll());
            return "employee/create_employee";
        }

        Employee employee = employeeMapper.toEmployeeFromEmployeeCreateDTO(employeeCreateDTO);
//        employee.setName(employeeCreateDTO.getName());
//        employee.setBirthDate(LocalDate.parse(employeeCreateDTO.getBirthDate()));
//        employee.setGender(Boolean.parseBoolean(employeeCreateDTO.getGender()));
//        employee.setSalary(Double.parseDouble(employeeCreateDTO.getSalary()));
//        employee.setPhoneNumber(employeeCreateDTO.getPhoneNumber());
//        employee.setDepartmentId(Integer.parseInt(employeeCreateDTO.getDepartmentId()));
        employeeService.create(employee);
        // gọi lại trang controller và mặc định đi vào doGet
        redirectAttributes.addFlashAttribute("message", "Thêm mới thành công");
        return "redirect:/employees";
    }

    @PostMapping("edit")
    public String edit(Model model, @Validated @ModelAttribute("employeeUpdateDTO")
        EmployeeUpdateDTO employeeUpdateDTO, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        Employee employee = employeeService.findById(Integer.parseInt(employeeUpdateDTO.getId()));

        employeeUpdateDTO.validate(employeeUpdateDTO, bindingResult);

        if (employee == null) {
            return "error404";
        }

//        HashMap<String, String> errorMessage = new HashMap<>();
//        EmployeeCreateDTO employeeCreateDTO = new EmployeeCreateDTO();
//        BeanUtils.copyProperties(employeeUpdateDTO, employeeCreateDTO);
//        employeeService.validate(employeeCreateDTO, errorMessage);

        // kiểm tra xem sđt có trùng với sđt đang chỉnh sửa ko
        if (!employee.getPhoneNumber().equals(employeeUpdateDTO.getPhoneNumber())) {
            employeeService.validateNumberPhoneExists(employee.getPhoneNumber(), bindingResult);
        }

        if (bindingResult.hasErrors()) {
//            model.addAttribute("id", employeeUpdateDTO.getId());
//            model.addAttribute("name", employeeUpdateDTO.getName());
//            model.addAttribute("birthDate", employeeUpdateDTO.getBirthDate());
//            model.addAttribute("gender", employeeCreateDTO.getGender());
//            model.addAttribute("salary", employeeUpdateDTO.getSalary());
//            model.addAttribute("phoneNumber", employeeUpdateDTO.getPhoneNumber());
//            model.addAttribute("departmentId", employeeUpdateDTO.getDepartmentId());
//            model.addAttribute("departmentList", departmentService.findAll());
//            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("employeeUpdateDTO", employeeUpdateDTO);
            return "employee/edit_employee";
        }

        employee = employeeMapper.toEmployeeFromEmployeeUpdateDTO(employeeUpdateDTO);
//        employee.setName(employeeCreateDTO.getName());
//        employee.setBirthDate(LocalDate.parse(employeeCreateDTO.getBirthDate()));
//        employee.setGender(Boolean.parseBoolean(employeeCreateDTO.getGender()));
//        employee.setSalary(Double.parseDouble(employeeCreateDTO.getSalary()));
//        employee.setPhoneNumber(employeeCreateDTO.getPhoneNumber());
//        employee.setDepartmentId(Integer.parseInt(employeeCreateDTO.getDepartmentId()));

        employeeService.update(employee);
        redirectAttributes.addFlashAttribute("message", "Cập nhật thành công");
        return "redirect:/employees";
    }
}
