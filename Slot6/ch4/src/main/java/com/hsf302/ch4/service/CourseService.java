package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.CourseSummary;
import com.hsf302.ch4.pojo.Course;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    long count();
    List<Course> findAllOrderByCode();
    Optional<Course> findById(Long id);
    Optional<Course> findByCode(String code);
    List<Course> findBySemester(String semester);
    List<Course> findByCreditsGreaterThanEqual(Integer credits);
    List<Course> searchByName(String keyword);
    List<Course> findCoursesWithMinCapacityNative(Integer cap);
    // ===== TODO 10 =====
    org.springframework.data.domain.Page<Course> getCoursesWithPagination(int pageNo, int pageSize);
    // ===== TODO 11 =====
    List<CourseSummary> findProjectedBySemester(String semester);
    // ===== TODO 16 =====
    List<Object[]> countStudentsPerCourse();
    // ===== TODO 19 =====
    List<Course> findCoursesByStudentCode(String studentCode);
}