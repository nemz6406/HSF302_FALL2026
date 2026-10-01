package com.hsf302.ch4.repository;

import com.hsf302.ch4.dto.CourseSummary;
import com.hsf302.ch4.pojo.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCode(String code);
    List<Course> findBySemester(String semester);
    List<Course> findByCreditsGreaterThanEqual(Integer credits);

    // ===== TODO 8 =====
    @Query("SELECT c FROM Course c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Course> searchByName(@Param("keyword") String keyword);
    // ===== TODO 9 =====
    @Query(value = "SELECT * FROM courses WHERE capacity >= :cap", nativeQuery = true)
    List<Course> findCoursesWithMinCapacityNative(@Param("cap") Integer cap);
    // ===== TODO 11 =====
    List<CourseSummary> findProjectedBySemester(String semester);
}