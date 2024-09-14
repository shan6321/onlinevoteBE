package com.onlinevote.repository;

import com.onlinevote.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Object> findByRegno(String regno);
    Student findOneByRegnoIgnoreCaseAndPassword(String regno, String password);
}
