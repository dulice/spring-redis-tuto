package com.example.student.dto;

import com.example.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String studentId;
    private BigDecimal amount;
    private String paymentMethod;

    public static Student toMapper(PaymentRequest paymentRequest) {
        Student student = new Student();
        return student;
    }
}