package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.CourseSummary;
import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    // Nhớ thêm các import này ở đầu file nếu chưa có:
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;

    // ===== TODO 10 =====
    @Override
    public Page<Course> getCoursesWithPagination(int pageNo, int pageSize) {
        // Tạo request phân trang: lấy trang số pageNo, mỗi trang pageSize phần tử, sắp xếp credits giảm dần
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("credits").descending());
        return courseRepository.findAll(pageable);
    }
    // Nhớ import com.hsf302.ch4.dto.CourseSummary;

    // ===== TODO 11 =====
    @Override
    public List<CourseSummary> findProjectedBySemester(String semester) {
        return courseRepository.findProjectedBySemester(semester);
    }
}