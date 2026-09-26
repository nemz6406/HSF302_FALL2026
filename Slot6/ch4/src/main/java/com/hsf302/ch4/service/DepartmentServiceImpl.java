package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.DepartmentStatDTO;
import com.hsf302.ch4.pojo.Department;
import com.hsf302.ch4.repository.DepartmentRepository;
import com.hsf302.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository; // dùng cho TODO 22

    @Override
    public long count() {
        return departmentRepository.count();
    }

    @Override
    public boolean existsById(Long id) {
        return departmentRepository.existsById(id);
    }
    @Override
    public List<Department> findDepartmentsWithoutStudents() {
        return departmentRepository.findByStudentsIsEmpty();
    }
    @Override
    public List<DepartmentStatDTO> getStatistics() {
        return departmentRepository.getDepartmentStats();
    }
    @Override
    public java.util.Optional<Department> findByCode(String code) {
        return departmentRepository.findByCode(code);
    }

    @Override
    public Department getWithStudents(String code) {
        return departmentRepository.findByCodeWithStudents(code)
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + code));
    }
    @Override
    @Transactional
    public void deleteDepartment(String code) {
        var dept = departmentRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Department không tồn tại: " + code));

        long studentCount = studentRepository.countByDepartment_Code(code);
        if (studentCount > 0) {
            throw new IllegalStateException("Không thể xoá department " + code
                    + " vì vẫn còn " + studentCount + " sinh viên");
        }

        departmentRepository.delete(dept);
    }
}