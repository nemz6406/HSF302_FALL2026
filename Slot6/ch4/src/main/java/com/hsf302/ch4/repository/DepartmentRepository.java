package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByCode(String code);        // Dùng lại ở TODO 16, 22
    List<Department> findByStudentsIsEmpty();            // WHERE NOT EXISTS (SELECT ... FROM students ...)
    @org.springframework.data.jpa.repository.Query(
            "SELECT new com.hsf302.ch4.dto.DepartmentStatDTO(d.code, d.name, COUNT(s), AVG(s.gpa)) " +
                    "FROM Department d LEFT JOIN d.students s " +
                    "GROUP BY d.code, d.name " +
                    "ORDER BY d.code"
    )
    java.util.List<com.hsf302.ch4.dto.DepartmentStatDTO> getDepartmentStats();
}