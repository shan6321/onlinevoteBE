package com.onlinevote.service;

import com.onlinevote.dto.LoginDto;
import com.onlinevote.entity.Student;
import com.onlinevote.exception.UserNotFound;
import com.onlinevote.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {

    @Autowired
    private final StudentRepository studentRepository;

    public LoginService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Autowired
    private ModelMapper modelMapper;

    public LoginDto loginStudent(LoginDto loginDto) {
        try{
            Student student = studentRepository
                    .findOneByRegnoIgnoreCaseAndPassword(loginDto.getRegno(),loginDto.getPassword());
            LoginDto loginDtoRes = modelMapper.map(student, LoginDto.class);
            if(loginDtoRes==null){
                throw new UserNotFound("Invalid Regno or Password");
            }
            return loginDtoRes;

        }
        catch(Exception e)
        {
            throw e;
        }

    }
}
