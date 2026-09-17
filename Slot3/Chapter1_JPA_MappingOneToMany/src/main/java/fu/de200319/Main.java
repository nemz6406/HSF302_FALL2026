package fu.de200319;

import fu.de200319.pojo.Department;
import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu TODO 2.4 (Test Helper Method) ---");

        // Tạo 1 phòng ban và 1 nhân viên
        Department dept = new Department("IT", "Ha Noi");
        Employee emp = new Employee("Test Name", "test@company.com", Gender.OTHER,
                new BigDecimal("1000"), LocalDate.now());

        // Dùng helper method để nối chúng lại với nhau
        dept.addEmployee(emp);

        // Kiểm chứng
        System.out.println("Phòng ban đã có nhân viên này chưa? -> " + dept.getEmployees().contains(emp)); // Kỳ vọng: true
        System.out.println("Nhân viên đã nhận đúng phòng ban chưa? -> " + (emp.getDepartment() == dept)); // Kỳ vọng: true
    }
}