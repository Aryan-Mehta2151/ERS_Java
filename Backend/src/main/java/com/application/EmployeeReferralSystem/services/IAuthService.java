package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.dtos.LoginResponseDto;
import com.application.EmployeeReferralSystem.dtos.RegisterRequestDto;
import org.apache.tika.exception.TikaException;

import java.io.IOException;

public interface IAuthService {
    LoginResponseDto LoginUser(String username, String password);

    String RegisterUser(RegisterRequestDto registerRequestDto) throws TikaException, IOException;
}
