package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public long count() {
        return courseRepository.count();
    }

    @Override
    public List<Course> findAllOrderByCode() {
        return courseRepository.findAll(Sort.by("code"));
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    // ===== TODO 7 =====
    @Override
    public Optional<Course> findByCode(String code) {
        return courseRepository.findByCode(code);
    }

    @Override
    public List<Course> findBySemester(String semester) {
        return courseRepository.findBySemester(semester);
    }

    @Override
    public List<Course> findByCreditsGreaterThanEqual(Integer credits) {
        return courseRepository.findByCreditsGreaterThanEqual(credits);
    }
    // ===== TODO 8 =====
    @Override
    public List<Course> searchByName(String keyword) {
        return courseRepository.searchByName(keyword);
    }
    // ===== TODO 9 =====
    @Override
    public List<Course> findCoursesWithMinCapacityNative(Integer cap) {
        return courseRepository.findCoursesWithMinCapacityNative(cap);
    }
}