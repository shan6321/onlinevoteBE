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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Optional;

import static com.onlinevote.constant.OnlineVoteConstant.formattedServerDateTime;
import static com.onlinevote.utils.DateTimeUtil.getFormattedUtcDateTime;

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

            Student student = modelMapper.map(studentDto, Student.class);

            Optional<Student> existingRegno = studentRepository.findByRegno(student.getRegno());
            if (existingRegno.isPresent()) {
                throw new RecordNotInserted("Registration number already exists");
            }
            Optional<Student> existingEmailId = studentRepository.findByEmail(student.getEmail());
            if (existingEmailId.isPresent()) {
                throw new RecordNotInserted("Email ID already exists");
            }
            //ZoneId userZone = ZoneId.of(request.getTimeZone()); // this will come from client side
            //ZoneId userZone = ZoneId.of("Europe/London");
            ZoneId userZone = ZoneId.of("Asia/Kolkata");
            log.info("userZone: "+userZone);
            String formattedUtcDateTime = getFormattedUtcDateTime(userZone);
            log.info("formattedUtcDateTime: " + formattedUtcDateTime);
            student.setStatus(OnlineVoteConstant.OPEN);
            student.setZoneId(userZone.toString());
            student.setUserUtcTime(formattedUtcDateTime);
            student.setCreatedAt(formattedServerDateTime);
            student.setCreatedBy(OnlineVoteConstant.USER);
            return studentRepository.save(student);
    }
    public Student updateStudent(Long id, StudentDto studentDto) {
        try {
            Optional<Student> studentData = studentRepository.findById(id);
            if (studentData.isPresent()) {
                Student studentUpdate = studentData.get();
                Optional.ofNullable(studentDto.getRegno()).ifPresent(studentUpdate::setRegno);
                Optional.ofNullable(studentDto.getPassword()).ifPresent(studentUpdate::setPassword);
                Optional.ofNullable(studentDto.getName()).ifPresent(studentUpdate::setName);
                Optional.ofNullable(studentDto.getEmail()).ifPresent(studentUpdate::setEmail);
                //ZoneId userZone = ZoneId.of(request.getTimeZone()); // this will come from client side
                ZoneId userZone = ZoneId.of("Asia/Kolkata");
                log.info("userZone: "+userZone);
                String formattedUtcDateTime = getFormattedUtcDateTime(userZone);
                log.info("formattedUtcDateTime: " + formattedUtcDateTime);
                studentUpdate.setUpdatedAt(formattedUtcDateTime);
                studentUpdate.setUpdatedBy(OnlineVoteConstant.USER);
                return studentRepository.save(studentUpdate);
            } else {
                throw new UserNotFound("User Not Found for given id : " + id);
            }
        } catch (Exception e) {
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

    public List<Student> getAllStudents() {
        log.info("Fetching all students from the database");
        return studentRepository.findAll();
    }

}
