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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.*;
import java.util.*;

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

    public Map<String, Object> parseAndSaveExcelFile(MultipartFile file) {
        List<Student> savedStudents = new ArrayList<>();
        List<Map<String, String>> validationErrors = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Skip the header row
                    continue;
                }
                try {
                    Student student = new Student();
                    student.setRegno(row.getCell(1).getStringCellValue());
                    student.setPassword(row.getCell(2).getStringCellValue());
                    student.setName(row.getCell(3).getStringCellValue());
                    student.setEmail(row.getCell(4).getStringCellValue());
                    student.setStatus(OnlineVoteConstant.OPEN);

                    // Set time zone and default values
                    ZoneId userZone = ZoneId.of("Asia/Kolkata");
                    String formattedUtcDateTime = getFormattedUtcDateTime(userZone);

                    student.setZoneId(userZone.toString());
                    student.setUserUtcTime(formattedUtcDateTime);
                    student.setCreatedAt(formattedServerDateTime);
                    student.setCreatedBy(OnlineVoteConstant.USER);

                    // Validate duplicates
                    if (studentRepository.findByRegnoOrEmail(student.getRegno(), student.getEmail()).isPresent()) {
                        Map<String, String> error = new HashMap<>();
                        error.put("row", String.valueOf(row.getRowNum() + 1));
                        error.put("message", "Duplicate regno or email: " + student.getRegno() + ", " + student.getEmail());
                        validationErrors.add(error);
                    } else {
                        // Save valid student
                        savedStudents.add(studentRepository.save(student));
                    }
                } catch (Exception e) {
                    Map<String, String> error = new HashMap<>();
                    error.put("row", String.valueOf(row.getRowNum() + 1));
                    error.put("message", "Error processing row: " + e.getMessage());
                    validationErrors.add(error);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("savedStudents", savedStudents);
        response.put("validationErrors", validationErrors);
        return response;
    }


}
