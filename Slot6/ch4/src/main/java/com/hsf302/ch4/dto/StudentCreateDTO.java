package com.hsf302.ch4.dto;

import com.hsf302.ch4.pojo.Gender;
import java.time.LocalDate;

public record StudentCreateDTO(
        String studentCode,
        String fullName,
        String email,
        Gender gender,
        LocalDate dob,
        Double gpa,
        String deptCode
) {}