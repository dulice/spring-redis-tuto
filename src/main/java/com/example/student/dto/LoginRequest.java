package com.example.student.dto;

import com.example.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;

    public static Student toMapper(LoginRequest loginRequest) {
        Student student = new Student();
        student.setEmail(loginRequest.getEmail());
        student.setPassword(loginRequest.getPassword());
        return student;
    }
}