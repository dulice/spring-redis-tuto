package com.example.student.service;

import com.example.student.dto.LoginRequest;
import com.example.student.dto.PaymentRequest;
import com.example.student.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface IStudentService {
    public ResponseEntity<?> register(RegisterRequest registerRequest);
    public ResponseEntity<?> login(LoginRequest loginRequest);
    public ResponseEntity<?> logout(String studentId);
    public ResponseEntity<?> processPayment(PaymentRequest paymentRequest);
    public ResponseEntity<?> getStudent(String studentId);
}
