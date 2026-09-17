package fu.de200319;

import fu.de200319.dao.DepartmentDAO;
import fu.de200319.pojo.Department;
import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;
import fu.de200319.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu TODO 2.6 & 2.7 (Demo luồng 1-N) ---");

        DepartmentDAO departmentDAO = new DepartmentDAO();

        // 1) Tạo Department + 3 Employee, add qua helper method (TODO 2.4)
        Department it = new Department("HR", "Da NangS");
        Employee e1 = new Employee("Nguyen Van A", "aa2.nguyen@company.com", Gender.MALE,
                new BigDecimal("15000000"), LocalDate.of(2022, 1, 10));
        Employee e2 = new Employee("Tran Thi B", "bb2.tran@company.com", Gender.FEMALE,
                new BigDecimal("18000000"), LocalDate.of(2021, 6, 1));
        Employee e3 = new Employee("Le Van C", "cc2.le@company.com", Gender.OTHER,
                new BigDecimal("12000000"), LocalDate.of(2023, 3, 15));

        it.addEmployee(e1);
        it.addEmployee(e2);
        it.addEmployee(e3);

        // 2) Chỉ persist(department) — cascade = ALL tự lo phần Employee (TODO 2.7)
        departmentDAO.save(it);
        System.out.println("Đã lưu Department, id = " + it.getId());

        // 3) Tìm lại kèm employees bằng JOIN FETCH (TODO 2.6)
        Department found = departmentDAO.findByIdWithEmployees(it.getId());
        System.out.println("Phòng ban: " + found.getName());
        for (Employee e : found.getEmployees()) {
            System.out.println("  - " + e.getFullName() + " | Email: " + e.getEmail());
        }

        // Đóng EntityManagerFactory khi tắt app
        JPAUtil.close();
    }
}