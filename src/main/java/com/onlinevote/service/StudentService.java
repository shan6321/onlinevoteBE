package com.onlinevote.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinevote.constant.OnlineVoteConstant;
import com.onlinevote.dto.LoginDto;
import com.onlinevote.dto.StudentDto;
import com.onlinevote.entity.Student;
import com.onlinevote.exception.RecordAlreadyExistsException;
import com.onlinevote.exception.RecordNotInserted;
import com.onlinevote.exception.UserNotFound;
import com.onlinevote.repository.StudentRepository;
//import com.onlinevote.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class StudentService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private JwtUtils jwtUtils;
    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(StudentDto studentDto) {
        try{
            Student student = modelMapper.map(studentDto, Student.class);
            student.setStatus(OnlineVoteConstant.OPEN);
            student.setCreatedAt(LocalDateTime.now());
            student.setCreatedBy(OnlineVoteConstant.USER);
            if (student != null) {
                return studentRepository.save(student);
            }
            else {
                throw new RecordNotInserted("Record not Found");
            }
        }
        catch (Exception e){
            throw e;
        }
    }



    public Student updateStudent(Long id, StudentDto studentDto) {
        try{
            Student student = modelMapper.map(studentDto, Student.class);
            Optional<Student> studentData = studentRepository.findById(id);
            if(studentData.isPresent()){
                Student studentUpdate = studentData.get();
                studentUpdate.setName(student.getName());
                studentUpdate.setPassword(student.getPassword());
                studentUpdate.setUpdatedAt(LocalDateTime.now());
                studentUpdate.setUpdatedBy(OnlineVoteConstant.USER);
                return studentRepository.save(studentUpdate);
            }
            else {
                throw new UserNotFound("User Not Found for given id : "+ id.toString());
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    public Student findByStudentId(Long id){
        try{
            Student studentJson = studentRepository.findById(id)
                    .orElseThrow(() -> new UserNotFound("User Not Found for given id : "+ id.toString()));
            return studentJson;
        }
        catch (Exception e){
            throw e;
        }

    }

    public void findByDeleteStudent(Long id) throws Exception {
        try{
            Student studentJson = studentRepository.findById(id)
                    .orElseThrow(() -> new UserNotFound("User Not Found for given id : "+ id.toString()));
                        studentRepository.deleteById(studentJson.getId());
        }
        catch(Exception e)
        {
            throw e;
        }

    }



}
