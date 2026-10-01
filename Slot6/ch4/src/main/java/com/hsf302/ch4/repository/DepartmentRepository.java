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
    @org.springframework.data.jpa.repository.Query(
            "SELECT d FROM Department d LEFT JOIN FETCH d.students WHERE d.code = :code"
    )
    java.util.Optional<com.hsf302.ch4.pojo.Department> findByCodeWithStudents(
            @org.springframework.data.repository.query.Param("code") String code
    );
    // Cách 1: Interface Projection (JPQL)
    @org.springframework.data.jpa.repository.Query(
            "SELECT d.name AS departmentName, COUNT(s) AS studentCount " +
                    "FROM Department d LEFT JOIN d.students s " +
                    "GROUP BY d.id, d.name " +
                    "ORDER BY d.name"
    )
    java.util.List<com.hsf302.ch4.dto.DepartmentStudentCount> countStudentsByDepartment();

    // Cách 2: DTO Class với Constructor Expression (JPQL)
    @org.springframework.data.jpa.repository.Query(
            "SELECT new com.hsf302.ch4.dto.DepartmentStudentDTO(d.name, COUNT(s)) " +
                    "FROM Department d LEFT JOIN d.students s " +
                    "GROUP BY d.id, d.name " +
                    "ORDER BY d.name"
    )
    java.util.List<com.hsf302.ch4.dto.DepartmentStudentDTO> countStudentsByDepartmentDTO();

    // Cách 3: Native SQL Query (lưu ý: bảng 'departments' và 'students')
    @org.springframework.data.jpa.repository.Query(
            value = "SELECT d.name AS departmentName, COUNT(s.id) AS studentCount " +
                    "FROM departments d LEFT JOIN students s ON s.department_id = d.id " +
                    "GROUP BY d.id, d.name " +
                    "ORDER BY d.name",
            nativeQuery = true
    )
    java.util.List<com.hsf302.ch4.dto.DepartmentStudentCount> countStudentsByDepartmentNative();
}