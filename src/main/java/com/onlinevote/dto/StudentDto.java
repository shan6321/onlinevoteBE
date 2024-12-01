package com.onlinevote.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentDto  {

    private Long id;

    @NotBlank(message="Registration Number must not be blank")
    @NotNull(message="Registration should not be null")
    private String regno;

    @NotBlank(message="Password should not be blank")
    @NotNull
    @Size(min=3,max=10,message="Password should be at least 3 characters long")
    private String password;

    @NotBlank(message="Name should not be blank")
    @NotNull
    @Size(min=3, message="Name should be at least 3 characters long")
    private String name;

    @NotBlank(message="Email should not be blank")
    @NotNull
    @Size(max=20, message="Email should not exceed 20 characters")
    @Email(message="Email should be valid")
    private String email;

    private String status;

    private String zoneId;

    private String userUtcTime;

    private String createdAt;

    private String createdBy;

    private String updatedAt;

    private String updatedBy;
}
