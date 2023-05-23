package com.ua.glebkorobov.seventhpractice.controller;

import com.ua.glebkorobov.seventhpractice.model.Student;
import com.ua.glebkorobov.seventhpractice.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "Students", description = "Methods for working with students")
public class StudentController {

    @Autowired
    StudentService studentService;


    @GetMapping("/student")
    @Operation(summary = "Get all students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/student/{ipn}")
    @Operation(summary = "Get student by ipn")
    public ResponseEntity<String> getStudentById(
            @Parameter(description = "Ipn of student")
            @PathVariable("ipn") long ipn) {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Found: " + studentService.getStudentByIpn(ipn));
    }

    @DeleteMapping("/student/{ipn}")
    @Operation(summary = "Deleting student by ipn")
    public ResponseEntity<String> deleteStudentById(
            @Parameter(description = "Ipn of student")
            @PathVariable("ipn") long ipn) {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Deleted" + studentService.deleteByIpn(ipn));
    }

    @DeleteMapping("/student")
    @Operation(summary = "Deleting all students")
    public ResponseEntity<String> deleteAllStudents() {
        return ResponseEntity.status(HttpStatus.OK).body("Deleted " + studentService.deleteAllStudents());
    }

    @PostMapping("/student")
    @Operation(summary = "Adding new student")
    public ResponseEntity<String> saveStudent(
            @Parameter(description = "Body of added student")
            @Valid @RequestBody Student student) {

        studentService.save(student);
        return ResponseEntity.status(HttpStatus.OK).body("Posted " + student);
    }

    @PutMapping("/student/{ipn}")
    @Operation(summary = "Updating a student")
    public ResponseEntity<String> updateStudent(
            @PathVariable("ipn") long ipn,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String gender,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false) LocalDate dateOfBirthday) {

        Student student = studentService.getStudentByIpn(ipn);

        Student changedStudent =
                studentService.updateStudentByIpn(student, name, surname, email, gender, dateOfBirthday);

        return ResponseEntity.status(HttpStatus.OK).body(student + " change to " + changedStudent);
    }
}
