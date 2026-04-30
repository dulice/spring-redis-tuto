package com.example.student.dto;

import com.example.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Long studentId;
    private String studentId_;
    private String name;
    private String email;
    private String message;

    public static RegisterResponse toDto(Student student) {
        RegisterResponse response = new RegisterResponse();
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setStudentId_(student.getStudentId());
        return response;
    }
}