package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.CourseRepository;
import com.hsf302.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // ===== TODO 12 =====
    @Override
    @Transactional
    public void enrollStudentToCourse(String studentCode, String courseCode) {
        Student student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên: " + studentCode));

        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khóa học: " + courseCode));

        // Gọi helper method đã định nghĩa ở entity Student để đồng bộ 2 chiều
        student.enroll(course);
        // Hibernate dirty checking sẽ tự động thực hiện câu lệnh INSERT vào bảng student_courses khi commit transaction
    }
}