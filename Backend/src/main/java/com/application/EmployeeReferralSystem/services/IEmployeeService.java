package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.dtos.EmployeeDetailsDto;

public interface IEmployeeService {

    EmployeeDetailsDto getEmployeeDetailsById(String id);

    EmployeeDetailsDto getEmployeeDetailsByUsername(String username);

    boolean deleteEmployeeById(String id);
}
