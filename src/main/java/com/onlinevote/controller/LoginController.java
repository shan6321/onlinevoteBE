package com.onlinevote.controller;

import com.onlinevote.dto.LoginDto;
import com.onlinevote.entity.ApiResponse;
import com.onlinevote.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/auth")
public class LoginController {
    @Autowired
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginDto>> loginStudent(@RequestBody LoginDto loginDto){
        LoginDto loginDtoDetail = loginService.loginStudent(loginDto);
        ApiResponse<LoginDto> response = new ApiResponse<>(loginDtoDetail, "Logged in successfully..",true);
        return ResponseEntity.ok(response);
    }
}
