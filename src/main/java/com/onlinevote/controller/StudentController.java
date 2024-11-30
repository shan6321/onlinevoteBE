package com.onlinevote.controller;

import com.onlinevote.dto.StudentDto;
import com.onlinevote.entity.ApiResponse;
import com.onlinevote.entity.Student;
import com.onlinevote.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/student")
public class StudentController {
    @Autowired
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/addStudent")
    public ResponseEntity<ApiResponse<StudentDto>> addStudent(@Valid @RequestBody StudentDto studentDto){
        log.info("========Inside Student controller of addStudent Method============");
        Student studentEntity = studentService.createStudent(studentDto);
        StudentDto studentDtoRes = modelMapper.map(studentEntity, StudentDto.class);
        ApiResponse<StudentDto> response = new ApiResponse<>(studentDtoRes, "Student Registered Successfully",true);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/updateStudent/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> updateStudent(@PathVariable Long id,
                                                                  @Valid @RequestBody StudentDto studentDto){
        Student studentEntity = studentService.updateStudent(id,studentDto);
        StudentDto studentDtoRes = modelMapper.map(studentEntity, StudentDto.class);
        ApiResponse<StudentDto> response = new ApiResponse<>(studentDtoRes, "Student Data Updated Successfully",true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findStudent/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> findStudent(@PathVariable Long id){
        Student studentEntity = studentService.findByStudentId(id);
        StudentDto studentDtoRes = modelMapper.map(studentEntity, StudentDto.class);
        ApiResponse<StudentDto> response = new ApiResponse<>(studentDtoRes, null,true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deleteStudent/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> deleteStudent(@PathVariable Long id) throws Exception {
        studentService.findByDeleteStudent(id);
        ApiResponse<StudentDto> response = new ApiResponse<>(null, "Record deleted successfully..",true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/allStudents")
    public ResponseEntity<ApiResponse<List<StudentDto>>> getAllStudents() {
        log.info("========Inside StudentController of getAllStudents Method============");
        List<Student> studentEntities = studentService.getAllStudents();
        List<StudentDto> studentDtos = studentEntities.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();
        ApiResponse<List<StudentDto>> response = new ApiResponse<>(studentDtos, "List all Students details successfully", true);
        return ResponseEntity.ok(response);
    }


}
