package com.example.student.service.impl;

import com.example.student.business.StudentCache;
import com.example.student.dto.*;
import com.example.student.entity.Student;
import com.example.student.exception.BusinessException;
import com.example.student.repository.StudentRepository;
import com.example.student.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final StudentCache studentCache;

    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        Student newStudent = RegisterRequest.toMapper(registerRequest);
        Student savedStudent = studentRepository.save(newStudent);

        RegisterResponse response = RegisterResponse.toDto(savedStudent);
        response.setMessage("Registration successful");
        response.setStudentId(Long.valueOf(savedStudent.getId().hashCode()));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {

        String email = loginRequest.getEmail();

        studentCache.checkDuplicateLogin(email);
        Student student = studentCache.getStudentCache(email);

        if(!Objects.equals(student.getPassword(), loginRequest.getPassword())) {
            studentCache.loginCounter(email);
            throw new BusinessException("Incorrect pwd");
        }
        studentCache.resetCounter(email);
        studentCache.releaseLock(email);
        LoginResponse response = LoginResponse.toDto(student, "123");
        response.setMessage("Login successful");

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> logout(String studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new BusinessException("Student not found."));
        studentCache.logout(student.getEmail());
        return ResponseEntity.ok("logout");
    }

    @Override
    public ResponseEntity<?> processPayment(PaymentRequest paymentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> getStudent(String studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentResponse response = StudentResponse.toDto(student);

        return ResponseEntity.ok(response);
    }
}
