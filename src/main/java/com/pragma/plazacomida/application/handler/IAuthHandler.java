package com.pragma.plazacomida.application.handler;

import com.pragma.plazacomida.application.dto.request.LoginRequestDto;
import com.pragma.plazacomida.application.dto.response.LoginResponseDto;

public interface IAuthHandler {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
} 