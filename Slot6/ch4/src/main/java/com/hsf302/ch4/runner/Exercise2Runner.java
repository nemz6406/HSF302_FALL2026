package com.hsf302.ch4.runner;

import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.service.CourseService;
import com.hsf302.ch4.service.EnrollmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Order(3)
@Profile("ex2")
@RequiredArgsConstructor
public class Exercise2Runner implements CommandLineRunner {

    // CHỈ inject Service interface
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;          // của Exercise 1

    @Override
    public void run(String... args) {
        partB();
        partC();
        partD();
        bonus();        // chạy trên dữ liệu gốc → trước Part E
        partE();
    }

    private void partB() { todo6(); todo7(); }
    private void partC() { todo8(); todo9(); todo10(); todo11();/*     */ }
    private void partD() { todo12(); todo13();/*   todo14(); todo15(); todo16(); todo17(); todo18(); todo19(); */ }
    private void bonus() { /* todo25(); */ }
    private void partE() { /* todo20(); todo21(); todo22(); todo23(); todo24(); */ }

    // ===== helpers =====
    private void title(String t) {
        System.out.println("\n===== " + t + " =====");
    }

    private void printList(String label, Collection<?> list) {
        System.out.println("-- " + label + ":");
        list.forEach(o -> System.out.println("   " + o));
        System.out.println("   -> " + list.size() + " record(s)");
    }

    /** Chạy 1 thao tác ghi, in [OK] hoặc [FAIL] + message (dùng cho Part E). */
    private void attempt(String label, Runnable action) {
        try {
            action.run();
            System.out.println("   [OK]   " + label);
        } catch (RuntimeException e) {
            System.out.println("   [FAIL] " + label + " -> " + e.getMessage());
        }
    }
    private void todo6() {
        title("TODO 6: count, findAll(Sort), findById");
        System.out.println("Total courses: " + courseService.count());
        printList("All courses order by code", courseService.findAllOrderByCode());
        for (long id : new long[]{2L, 99L}) {
            System.out.println("findById(" + id + "): "
                    + courseService.findById(id).map(Course::toString).orElse("Not found"));
        }
    }
    private void todo7() {
        title("TODO 7: Derived Query Methods");
        System.out.println("findByCode(PRJ301): "
                + courseService.findByCode("PRJ301").map(Course::toString).orElse("Not found"));

        printList("Courses in FA26", courseService.findBySemester("FA26"));
        printList("Courses with credits >= 3", courseService.findByCreditsGreaterThanEqual(3));
    }
    private void todo8() {
        title("TODO 8: @Query with JPQL");
        printList("Search courses containing 'development'", courseService.searchByName("development"));
        printList("Search courses containing 'system'", courseService.searchByName("system"));
    }
    private void todo9() {
        title("TODO 9: Native Query");
        printList("Courses with capacity >= 5", courseService.findCoursesWithMinCapacityNative(5));
    }
    private void todo10() {
        title("TODO 10: Pagination & Sorting");
        // Lấy trang 0 (trang đầu tiên), kích thước 3 phần tử/trang
        org.springframework.data.domain.Page<Course> page = courseService.getCoursesWithPagination(0, 3);

        System.out.println("Total elements in DB: " + page.getTotalElements());
        System.out.println("Total pages: " + page.getTotalPages());
        printList("Page 0 (size 3, sort by credits DESC)", page.getContent());
    }
    private void todo11() {
        title("TODO 11: Interface Projection");
        System.out.println("-- Projected courses in FA26 (code & name only):");
        courseService.findProjectedBySemester("FA26").forEach(c ->
                System.out.println("   " + c.getCode() + " - " + c.getName())
        );
    }
    private void todo12() {
        title("TODO 12: Enroll student to course");
        // Thử đăng ký môn MKT101 cho sinh viên SE001
        attempt("Enroll SE001 to MKT101", () -> enrollmentService.enrollStudentToCourse("SE001", "MKT101"));

        // Thử đăng ký với mã không tồn tại để kiểm tra bắt lỗi
        attempt("Enroll SE001 to INVALID", () -> enrollmentService.enrollStudentToCourse("SE001", "INVALID"));
    }
    private void todo13() {
        title("TODO 13: Unenroll student from course");
        // Hủy đăng ký môn MKT101 vừa đăng ký ở todo 12 cho sinh viên SE001
        attempt("Unenroll SE001 from MKT101", () -> enrollmentService.unenrollStudentFromCourse("SE001", "MKT101"));

        // Thử hủy đăng ký môn mà sinh viên không học để kiểm tra
        attempt("Unenroll SE001 from INVALID_COURSE", () -> enrollmentService.unenrollStudentFromCourse("SE001", "INVALID_COURSE"));
    }
}