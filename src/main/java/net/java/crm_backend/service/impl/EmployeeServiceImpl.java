package net.java.crm_backend.service.impl;

import net.java.crm_backend.dto.EmployeeDto;
import net.java.crm_backend.entity.Employee;
import net.java.crm_backend.mapper.EmployeeMapper;
import net.java.crm_backend.repository.EmployeeRepository;
import net.java.crm_backend.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    // Konstruktor do wstrzyknięcia zależności
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        try {
            Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
            Employee savedEmployee = employeeRepository.save(employee);
            return EmployeeMapper.mapToEmployeeDto(savedEmployee);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save employee: " + e.getMessage(), e);
        }
    }


}
