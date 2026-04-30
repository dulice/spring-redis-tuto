package com.example.student.controller;

import com.example.student.dto.*;
import com.example.student.service.IStudentService;
import com.example.student.service.impl.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final IStudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return studentService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return studentService.login(request);
    }

    @PostMapping("/logout/{studentId}")
    public ResponseEntity<?> logout(@PathVariable String studentId) {
        return studentService.logout(studentId);
    }

    @PostMapping("/payment")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request) {
        return studentService.processPayment(request);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudent(@PathVariable String studentId) {
        return studentService.getStudent(studentId);
    }
}