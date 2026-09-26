package com.hsf302.ch4.runner;

import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import com.hsf302.ch4.pojo.Student;
import java.util.Collection;

@Component
@Order(2)
@RequiredArgsConstructor
public class ExerciseRunner implements CommandLineRunner {

    // Runner CHỈ phụ thuộc vào Service (interface), KHÔNG inject Repository trực tiếp
    private final DepartmentService departmentService;
    private final StudentService studentService;

    @Override
    public void run(String... args) {
        partB();
        partC();
        partD();
        bonus();
        partE();
    }

    private void partB() {
        todo6();
        todo7();
    }

    private void partC() {
        todo8();
        todo9();
        todo10();
        todo11();
    }

    private void partD() {
        todo12();
        todo13();
        todo14();
        todo15();
        todo16();
        todo17();
        todo18();
        todo19();
    }

    private void bonus() {
        // todo24();
    }

    private void partE() {
        todo20();
        todo21();
        todo22();
        // todo23();
    }

    // ===== helper functions =====
    public void title(String t) {
        System.out.println("\n===== " + t + " =====");
    }

    public void printList(String label, Collection<?> list) {
        System.out.println("-- " + label + ":");
        list.forEach(o -> System.out.println("   " + o));
        System.out.println("   -> " + list.size() + " record(s)");
    }
    private void todo6() {
        title("TODO 6: count / findById / existsById");
        System.out.println("Departments: " + departmentService.count());
        System.out.println("Students   : " + studentService.count());

        studentService.findById(1L).ifPresentOrElse(
                s -> System.out.println("findById(1)  -> " + s),
                () -> System.out.println("findById(1)  -> Not found"));

        System.out.println("findById(99) -> " + studentService.findById(99L)
                .map(Object::toString)
                .orElse("Not found"));

        System.out.println("existsById(4) department -> " + departmentService.existsById(4L));
    }
    private void todo7() {
        title("TODO 7: Sort & Pageable");

        // (a) GPA giảm dần
        printList("All students order by GPA desc", studentService.findAllOrderByGpaDesc());

        // (b) Trang THỨ 2 -> index 1 (Spring Data đánh số trang từ 0)
        Page<Student> page = studentService.findPage(1, 3, "fullName");
        printList("Page index " + page.getNumber() + " (size " + page.getSize() + ")", page.getContent());
        System.out.println("totalElements=" + page.getTotalElements()
                + ", totalPages=" + page.getTotalPages()
                + ", hasNext=" + page.hasNext()
                + ", hasPrevious=" + page.hasPrevious());
    }
    private void todo8() {
        title("TODO 8: findBy / existsBy / countBy");
        for (String code : java.util.List.of("AI002", "XX999")) {
            System.out.println("findByStudentCode(" + code + ") -> " +
                    studentService.findByStudentCode(code).map(Object::toString).orElse("Not found"));
        }
        System.out.println("isEmailExisted(binh.tt@fpt.edu.vn) -> "
                + studentService.isEmailExisted("binh.tt@fpt.edu.vn"));
        System.out.println("countActive -> " + studentService.countActive());
    }
    private void todo9() {
        title("TODO 9: Containing / EndingWith / IsNull");
        printList("fullName contains 'nguyen'", studentService.searchByName("nguyen"));
        printList("email domain 'gmail.com'", studentService.findByEmailDomain("gmail.com"));
        printList("email is null", studentService.findWithoutEmail());
    }
    private void todo10() {
        title("TODO 10: Between / And / True / After");
        printList("GPA in [3.0, 3.6] desc", studentService.findByGpaRange(3.0, 3.6));
        printList("MALE & active", studentService.findActiveByGender(com.hsf302.ch4.pojo.Gender.MALE));
        printList("dob after 2005-01-01", studentService.findBornAfter(java.time.LocalDate.of(2005, 1, 1)));
    }
    private void todo11() {
        title("TODO 11: Nested property / Top / IsEmpty");
        printList("Students of SE (order by name)", studentService.findByDepartment("SE"));
        System.out.println("count students of AI -> " + studentService.countByDepartment("AI"));
        printList("Top 3 GPA", studentService.findTop3ByGpa());
        printList("Departments without students", departmentService.findDepartmentsWithoutStudents());
    }
    private void todo12() {
        title("TODO 12: JPQL + named parameter");
        printList("SE, GPA >= 3.0", studentService.findGoodStudents("SE", 3.0));
    }
    private void todo13() {
        title("TODO 13: JPQL LIKE");
        printList("keyword 'hoa'", studentService.searchByKeyword("hoa"));
        printList("keyword 'gmail'", studentService.searchByKeyword("gmail"));
    }
    private void todo14() {
        title("TODO 14: Statistics by department (DTO)");
        printList("code | name | total | avgGpa", departmentService.getStatistics());
    }
    private void todo15() {
        title("TODO 15: Subquery - GPA above average");
        printList("GPA > AVG", studentService.findAboveAverageGpa());
    }
    private void todo16() {
        title("TODO 16: LazyInitializationException & JOIN FETCH");

        // (a) Tái hiện lỗi LazyInitializationException
        com.hsf302.ch4.pojo.Department ai = departmentService.findByCode("AI").orElseThrow();
        try {
            System.out.println("AI has " + ai.getStudents().size() + " students");
        } catch (org.hibernate.LazyInitializationException e) {
            System.out.println("(a) Caught: " + e.getClass().getSimpleName());
            System.out.println("    " + e.getMessage());
        }

        // (b) Sửa lỗi bằng JOIN FETCH
        com.hsf302.ch4.pojo.Department aiFull = departmentService.getWithStudents("AI");
        System.out.println("(b) " + aiFull);
        aiFull.getStudents().forEach(s -> System.out.println("     " + s));
    }
    private void todo17() {
        title("TODO 17: Native SQL");
        printList("Native query: AI, GPA >= 3.0", studentService.findStudentsNative("AI", 3.0));
    }
    private void todo18() {
        title("TODO 18: @Modifying UPDATE");
        int updated = studentService.bonusGpa("IA", 0.5);
        System.out.println("Updated " + updated + " students in IA department");
        printList("IA students after bonus", studentService.findByDepartment("IA"));
    }
    private void todo19() {
        title("TODO 19: @Modifying DELETE");
        System.out.println("Total before delete: " + studentService.count());
        int deleted = studentService.deleteInactiveLowGpa(2.6);
        System.out.println("Deleted " + deleted + " inactive student(s) with GPA < 2.6");
        System.out.println("Total after delete : " + studentService.count());
    }
    private void todo20() {
        title("TODO 20: Register new student");

        var newStudent = new com.hsf302.ch4.dto.StudentCreateDTO(
                "SE005", "Vu Thi Kim", "kim.vt@fpt.edu.vn",
                com.hsf302.ch4.pojo.Gender.FEMALE,
                java.time.LocalDate.of(2005, 6, 20), 3.7, "SE"
        );
        Student created = studentService.register(newStudent);
        System.out.println("Registered: " + created);

        // Thử đăng ký trùng mã sinh viên để test validation
        try {
            studentService.register(newStudent);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected: " + e.getMessage());
        }
    }
    private void todo21() {
        title("TODO 21: Transfer department & Rollback test");

        // (a) Chuyển sinh viên id=1 sang AI
        System.out.println("Trước khi chuyển: " + studentService.findById(1L).map(Student::getFullName).orElse(""));
        studentService.transferDepartment(1L, "AI");
        System.out.println("Đã chuyển thành công sinh viên id=1 sang khoa AI");

        // (b) Test rollback: Chuyển sang khoa không tồn tại -> bắt ngoại lệ
        try {
            studentService.transferDepartment(1L, "NON_EXISTING");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected rollback error: " + e.getMessage());
        }

        // Kiểm tra danh sách sinh viên khoa AI để xác nhận sinh viên 1 đã ở khoa AI
        printList("Sinh vien khoa AI hien tai", studentService.findByDepartment("AI"));
    }
    private void todo22() {
        title("TODO 22: Delete department with constraint check");

        // (a) Xoá SE -> Thất bại do còn sinh viên
        try {
            departmentService.deleteDepartment("SE");
        } catch (IllegalStateException e) {
            System.out.println("Caught expected: " + e.getMessage());
        }

        // (b) Xoá GD -> Thành công do không có sinh viên
        System.out.println("Departments before delete: " + departmentService.count());
        departmentService.deleteDepartment("GD");
        System.out.println("Departments after delete : " + departmentService.count());
        System.out.println("exists GD -> " + departmentService.findByCode("GD").isPresent());
    }
}