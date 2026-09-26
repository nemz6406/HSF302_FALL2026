package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Department;

import java.util.List;

public interface DepartmentService {
    long count();                        // TODO 6
    boolean existsById(Long id);         // TODO 6
    // TODO 11
    List<Department> findDepartmentsWithoutStudents();  // TODO 11d
    List<com.hsf302.ch4.dto.DepartmentStatDTO> getStatistics();   // TODO 14 (dùng lại ở TODO 23)
    java.util.Optional<Department> findByCode(String code);   // TODO 16a
    Department getWithStudents(String code);                  // TODO 16b
    void deleteDepartment(String code);   // TODO 22

}