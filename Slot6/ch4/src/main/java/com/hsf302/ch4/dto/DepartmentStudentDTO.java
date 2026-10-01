package com.hsf302.ch4.dto;

public record DepartmentStudentDTO(String departmentName, Long studentCount) {
    @Override
    public String toString() {
        return departmentName + " - " + studentCount;
    }
}