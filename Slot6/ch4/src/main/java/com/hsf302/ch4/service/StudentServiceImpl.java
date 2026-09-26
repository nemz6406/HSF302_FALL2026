package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.StudentCreateDTO;
import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.DepartmentRepository;
import com.hsf302.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    // ===== TODO 6: Built-in Methods =====
    @Override
    public long count() {
        return studentRepository.count();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    // ===== TODO 7: Sort & Pageable =====
    @Override
    public List<Student> findAllOrderByGpaDesc() {
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC, "gpa"));
    }

    @Override
    public Page<Student> findPage(int pageIndex, int size, String sortField) {
        if (pageIndex < 0 || size <= 0) {
            throw new IllegalArgumentException("pageIndex phải >= 0 và size phải > 0");
        }
        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(sortField).ascending());
        return studentRepository.findAll(pageable);
    }

    // ===== TODO 8: Derived Query (findBy, existsBy, countBy) =====
    @Override
    public Optional<Student> findByStudentCode(String studentCode) {
        return studentRepository.findByStudentCode(studentCode);
    }

    @Override
    public boolean isEmailExisted(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public long countActive() {
        return studentRepository.countByActiveTrue();
    }

    // ===== TODO 9: Derived Query (ContainingIgnoreCase, EndingWith, IsNull) =====
    @Override
    public List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        return studentRepository.findByFullNameContainingIgnoreCase(keyword.trim());
    }

    @Override
    public List<Student> findByEmailDomain(String domain) {
        String suffix = domain.startsWith("@") ? domain : "@" + domain;
        return studentRepository.findByEmailEndingWith(suffix);
    }

    @Override
    public List<Student> findWithoutEmail() {
        return studentRepository.findByEmailIsNull();
    }

    // ===== TODO 10: Derived Query (Between, And, True, After) =====
    @Override
    public List<Student> findByGpaRange(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("min GPA phải <= max GPA");
        }
        return studentRepository.findByGpaBetweenOrderByGpaDesc(min, max);
    }

    @Override
    public List<Student> findActiveByGender(Gender gender) {
        return studentRepository.findByGenderAndActiveTrue(gender);
    }

    @Override
    public List<Student> findBornAfter(LocalDate date) {
        return studentRepository.findByDobAfter(date);
    }

    // ===== TODO 11: Nested Property & Top =====
    @Override
    public List<Student> findByDepartment(String deptCode) {
        return studentRepository.findByDepartment_CodeOrderByFullNameAsc(deptCode);
    }

    @Override
    public long countByDepartment(String deptCode) {
        return studentRepository.countByDepartment_Code(deptCode);
    }

    @Override
    public List<Student> findTop3ByGpa() {
        return studentRepository.findTop3ByOrderByGpaDesc();
    }

    // ===== TODO 12: JPQL + Named Parameter =====
    @Override
    public List<Student> findGoodStudents(String deptCode, double minGpa) {
        return studentRepository.findGoodStudentsInDepartment(deptCode, minGpa);
    }

    // ===== TODO 13: JPQL LIKE =====
    @Override
    public List<Student> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        return studentRepository.searchByKeyword(keyword.trim());
    }

    // ===== TODO 15: JPQL Subquery =====
    @Override
    public List<Student> findAboveAverageGpa() {
        return studentRepository.findAboveAverageGpa();
    }

    // ===== TODO 17: Native Query =====
    @Override
    public List<Student> findStudentsNative(String deptCode, double minGpa) {
        return studentRepository.findStudentsNative(deptCode, minGpa);
    }

    // ===== TODO 18: @Modifying UPDATE =====
    @Override
    @Transactional
    public int bonusGpa(String deptCode, double bonus) {
        return studentRepository.increaseGpaForDepartment(deptCode, bonus);
    }

    // ===== TODO 19: @Modifying DELETE =====
    @Override
    @Transactional
    public int deleteInactiveLowGpa(double maxGpa) {
        return studentRepository.deleteInactiveStudentsWithLowGpa(maxGpa);
    }

    // ===== TODO 20: Register new student =====
    @Override
    @Transactional
    public Student register(StudentCreateDTO dto) {
        if (studentRepository.findByStudentCode(dto.studentCode()).isPresent()) {
            throw new IllegalArgumentException("Student code đã tồn tại: " + dto.studentCode());
        }
        if (dto.email() != null && !dto.email().isBlank() && studentRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email đã tồn tại: " + dto.email());
        }
        var dept = departmentRepository.findByCode(dto.deptCode())
                .orElseThrow(() -> new IllegalArgumentException("Department không tồn tại: " + dto.deptCode()));

        Student s = new Student();
        s.setStudentCode(dto.studentCode());
        s.setFullName(dto.fullName());
        s.setEmail(dto.email());
        s.setGender(dto.gender());
        s.setDob(dto.dob());
        s.setGpa(dto.gpa() != null ? dto.gpa() : 0.0);
        s.setActive(true);
        dept.addStudent(s);

        return studentRepository.save(s);
    }
    @Override
    @Transactional
    public void transferDepartment(Long studentId, String targetDeptCode) {
        Student s = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student không tồn tại: " + studentId));

        var targetDept = departmentRepository.findByCode(targetDeptCode)
                .orElseThrow(() -> new IllegalArgumentException("Department không tồn tại: " + targetDeptCode));

        if ("TRIGGER_ROLLBACK".equalsIgnoreCase(targetDeptCode)) {
            // Trường hợp test rollback: đã đổi đối tượng trong bộ nhớ nhưng ném exception giữa chừng
            s.getDepartment().getStudents().remove(s);
            targetDept.addStudent(s);
            throw new RuntimeException("Lỗi mô phỏng rollback giao dịch!");
        }

        // Chuyển khoa hợp lệ
        if (s.getDepartment() != null) {
            s.getDepartment().getStudents().remove(s);
        }
        targetDept.addStudent(s);
    }
}