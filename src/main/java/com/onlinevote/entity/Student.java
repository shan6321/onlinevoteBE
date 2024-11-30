package com.onlinevote.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Students")
public class Student extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "regno")
    private String regno;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;
}