package com.example.student.business;

import com.example.student.entity.Student;
import com.example.student.exception.BusinessException;
import com.example.student.repository.StudentRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StudentCache {
    private final StudentRepository studentRepository;

    public StudentCache(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Cacheable(value = "student", key = "#email")
    public Student getStudentCache(String email) {
        System.out.println("--- FETCHING FROM DATABASE ---");
        return studentRepository.findByEmail(email).orElseThrow(() -> new BusinessException("Student not found."));
    }

    @CacheEvict(value = "student", key = "#email")
    public void logout(String email) {
        System.out.println("--- EVICTING CACHE FOR: " + email + " ---");
    }

}
