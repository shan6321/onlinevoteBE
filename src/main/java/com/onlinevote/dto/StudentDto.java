package com.onlinevote.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentDto  {
    private Long id;

    @NotBlank(message="Registration Number must not be blank")
    @NotNull(message="Registration should not be null")
    @Column(updatable = false)
    private String regno;

    @NotBlank(message="Name must not be blank")
    @NotNull
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Password must not be blank")
    @NotNull
    @Size(min=3,max=10,message="Password must be at least 3 characters long")
    private String password;

    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;
}
