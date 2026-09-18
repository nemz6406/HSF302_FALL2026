package fu.de200319;

import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;
import fu.de200319.pojo.Project;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu Test TODO 5.5 ---");

        Employee emp = new Employee("Nguyen Van A", new BigDecimal("1000"), LocalDate.now(), "nv.a@gmail.com", Gender.MALE, true);
        Project proj = new Project("PRJ01", "Dự án Alpha", new BigDecimal("5000"), LocalDate.now(), null);

        // Gọi helper method
        emp.assignToProject(proj);

        // Kiểm tra đồng bộ 2 chiều
        boolean checkEmp = emp.getProjects().contains(proj);
        boolean checkProj = proj.getEmployees().contains(emp);

        System.out.println("Employee có chứa Project không? " + checkEmp);
        System.out.println("Project có chứa Employee không? " + checkProj);

        if (checkEmp && checkProj) {
            System.out.println("=> Test 5.5 THÀNH CÔNG! Dữ liệu đã được thêm vào cả 2 phía.");
        } else {
            System.out.println("=> Test 5.5 THẤT BẠI! Hãy kiểm tra lại hàm assignToProject.");
        }
    }
}