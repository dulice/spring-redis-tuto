package com.example.student.dto;

import com.example.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private String id;
    private String studentId;
    private String name;
    private String email;
    private String major;
    private int age;
    private boolean active;

    public static StudentResponse toDto(Student student) {
        StudentResponse response = new StudentResponse();
        response.setStudentId(student.getStudentId());
        response.setId(student.getId());
        response.setActive(student.isActive());
        response.setAge(student.getAge());
        response.setEmail(student.getEmail());
        response.setName(student.getName());
        response.setMajor(student.getMajor());

        return response;
    }
}