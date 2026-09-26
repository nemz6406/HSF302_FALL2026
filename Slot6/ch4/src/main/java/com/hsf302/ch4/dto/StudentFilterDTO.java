package com.hsf302.ch4.dto;

public record StudentFilterDTO(
        String keyword,
        String deptCode,
        Double minGpa,
        Double maxGpa,
        Boolean active
) {}