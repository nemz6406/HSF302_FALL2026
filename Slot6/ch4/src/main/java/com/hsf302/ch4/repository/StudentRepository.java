package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {

    // ===== Part C — Derived query =====
    Optional<Student> findByStudentCode(String studentCode); // WHERE student_code = ?
    boolean existsByEmail(String email);                     // kiểm tra tồn tại email
    long countByActiveTrue();                                // WHERE active = 1 (không cần tham số)
    List<Student> findByFullNameContainingIgnoreCase(String keyword);   // UPPER(full_name) LIKE UPPER('%kw%')
    List<Student> findByEmailEndingWith(String suffix);                 // email LIKE '%suffix'
    List<Student> findByEmailIsNull();                                  // email IS NULL
    List<Student> findByGpaBetweenOrderByGpaDesc(double min, double max);   // gpa BETWEEN ? AND ? ORDER BY gpa DESC
    List<Student> findByGenderAndActiveTrue(Gender gender);                  // gender = ? AND active = 1
    List<Student> findByDobAfter(LocalDate date);                            // dob > ?
    List<Student> findByDepartment_CodeOrderByFullNameAsc(String code);   // JOIN departments ... WHERE d.code = ?
    long countByDepartment_Code(String code);
    List<Student> findTop3ByOrderByGpaDesc();                              // SELECT TOP 3 ... ORDER BY gpa DESC
    // ===== Part D — Custom query =====
    @org.springframework.data.jpa.repository.Query("SELECT s FROM Student s " +
            "WHERE s.department.code = :code AND s.gpa >= :minGpa " +
            "ORDER BY s.gpa DESC")
    java.util.List<Student> findGoodStudentsInDepartment(
            @org.springframework.data.repository.query.Param("code") String code,
            @org.springframework.data.repository.query.Param("minGpa") double minGpa);
}