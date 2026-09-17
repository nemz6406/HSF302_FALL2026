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

        // 1) Tạo Department + 3 Employee
        Department it = new Department("IT", "Ho Chi Minh City");
        Employee e1 = new Employee("Nguyen Van A", "aa4.nguyen@company.com", Gender.MALE,
                new BigDecimal("15000000"), LocalDate.of(2022, 1, 10));
        Employee e2 = new Employee("Tran Thi B", "bb4.tran@company.com", Gender.FEMALE,
                new BigDecimal("18000000"), LocalDate.of(2021, 6, 1));
        Employee e3 = new Employee("Le Van C", "cc4.le@company.com", Gender.OTHER,
                new BigDecimal("12000000"), LocalDate.of(2023, 3, 15));

        it.addEmployee(e1);
        it.addEmployee(e2);
        it.addEmployee(e3);

        // 2) Chỉ persist(department) — cascade = ALL tự lo phần Employee
        departmentDAO.save(it);
        System.out.println("Đã lưu Department, id = " + it.getId());

        System.out.println("\n--- Bắt đầu TODO 2.8 (Tái hiện N+1 Query) ---");
        // Mở EntityManager cục bộ để test Lazy Load tránh bị lỗi LazyInitializationException
        jakarta.persistence.EntityManager em = JPAUtil.getEMF().createEntityManager();
        java.util.List<Department> depts = em.createQuery("SELECT d FROM Department d", Department.class).getResultList();
        System.out.println("Đã chạy xong SELECT Department. Bắt đầu vòng lặp:");
        for (Department d : depts) {
            // Mỗi lần gọi d.getEmployees() ở đây, Hibernate sẽ sinh ra thêm 1 câu lệnh SELECT phụ!
            System.out.println("Phòng ban " + d.getName() + " có " + d.getEmployees().size() + " nhân viên.");
        }
        em.close();

        System.out.println("\n--- Bắt đầu TODO 2.9 (Fix N+1 Query bằng JOIN FETCH) ---");
        // Dùng hàm findAllWithEmployees() đã viết ở DAO
        java.util.List<Department> deptsFixed = departmentDAO.findAllWithEmployees();
        System.out.println("Đã load xong toàn bộ. Bắt đầu vòng lặp (sẽ KHÔNG sinh thêm câu SELECT nào nữa):");
        for (Department d : deptsFixed) {
            System.out.println("Phòng ban " + d.getName() + " có " + d.getEmployees().size() + " nhân viên.");
        }


        JPAUtil.close();
    }
}