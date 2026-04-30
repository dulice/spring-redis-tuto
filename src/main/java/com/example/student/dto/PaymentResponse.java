package com.example.student.dto;

import com.example.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String transactionId;
    private String studentId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime timestamp;

    public static PaymentResponse toDto(Student student, String transactionId, BigDecimal amount, String status, LocalDateTime timestamp) {
        PaymentResponse response = new PaymentResponse();
        response.setStudentId(student.getStudentId());
        response.setTransactionId(transactionId);
        response.setAmount(amount);
        response.setStatus(status);
        response.setTimestamp(timestamp);
        return response;
    }
}