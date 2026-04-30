package com.example.student.dto;

import com.example.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String studentId;
    private String name;
    private String email;
    private String password;
    private String major;
    private int age;

    public static Student toMapper(RegisterRequest registerRequest) {
        Student student = new Student();
        student.setActive(false);
        student.setName(registerRequest.getName());
        student.setEmail(registerRequest.getEmail());
        student.setMajor(registerRequest.getMajor());
        student.setPassword(registerRequest.getPassword());
        student.setStudentId(registerRequest.getStudentId());

        return student;
    }
}