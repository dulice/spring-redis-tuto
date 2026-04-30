package com.example.student.dto;

import com.example.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String message;
    private String studentId;
    private String name;

    public static LoginResponse toDto(Student student, String token) {
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setName(student.getName());
        response.setStudentId(student.getStudentId());
        return response;
    }
}