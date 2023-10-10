package com.example.ems.service;

import com.example.ems.dto.EmployeeDto;
import com.example.ems.entity.Employee;
import com.example.ems.exception.ResourceNotFoundException;
import com.example.ems.mapper.EmployeeMapper;
import com.example.ems.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service // tells spring container to create spring bean for this class
public class EmployeeServiceImplementation implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee does not exist with the given id: " + employeeId)
        );

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::mapToEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployeeDto) {
        employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));
        Employee updatedEmployee = employeeRepository.save(EmployeeMapper.mapToEmployee(updatedEmployeeDto));
        return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));
        employeeRepository.deleteById(employeeId);
    }
}
