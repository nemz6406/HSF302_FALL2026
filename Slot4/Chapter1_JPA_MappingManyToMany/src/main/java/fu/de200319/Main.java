package fu.de200319;

import fu.de200319.dao.EmployeeDAO;
import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;
import fu.de200319.pojo.Project;
import fu.de200319.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu Test TODO 5.6 ---");

        EntityManager em = JPAUtil.getEMF().createEntityManager();

        // 1. Tạo 2 entity mẫu để có dữ liệu thật trong DB
        Employee emp = new Employee("Tran Van C", new BigDecimal("1500"), LocalDate.now(), "tranc@gmail.com", Gender.MALE, true);
        Project proj = new Project("PRJ_TEST", "Dự án Test DAO", new BigDecimal("10000"), LocalDate.now(), null);

        em.getTransaction().begin();
        em.persist(emp);
        em.persist(proj);
        em.getTransaction().commit();
        em.close();

        System.out.println("Đã lưu vào DB - Employee ID: " + emp.getId() + " | Project ID: " + proj.getId());

        // 2. Gọi EmployeeDAO để gán Employee vào Project
        EmployeeDAO dao = new EmployeeDAO();
        boolean isSuccess = dao.assignEmployeeToProject(emp.getId(), proj.getId());

        if (isSuccess) {
            System.out.println("=> Test 5.6 THÀNH CÔNG! Đã phân công thành công.");
            System.out.println("=> Bạn hãy mở SQL Server và select bảng 'employee_project' để xác nhận nhé!");
        } else {
            System.out.println("=> Test 5.6 THẤT BẠI! Hãy kiểm tra lại code.");
        }

        JPAUtil.close();
    }
}