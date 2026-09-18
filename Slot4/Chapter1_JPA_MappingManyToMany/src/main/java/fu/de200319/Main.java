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
        System.out.println("--- Bắt đầu Test TODO 5.7 ---");

        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EmployeeDAO dao = new EmployeeDAO();

        // 1. Tạo 3 Employee điền đủ thông tin
        Employee emp1 = new Employee("Nguyen Van Mot", new BigDecimal("1000"), LocalDate.now(), "nv1@gmail.com", Gender.MALE, true);
        Employee emp2 = new Employee("Tran Thi Hai", new BigDecimal("1200"), LocalDate.now(), "tt2@gmail.com", Gender.FEMALE, true);
        Employee emp3 = new Employee("Le Van Ba", new BigDecimal("1500"), LocalDate.now(), "lv3@gmail.com", Gender.MALE, true);

        // 2. Tạo 2 Project[cite: 6]
        Project projA = new Project("PRJ_A", "Project A", new BigDecimal("50000"), LocalDate.now(), null);
        Project projB = new Project("PRJ_B", "Project B", new BigDecimal("80000"), LocalDate.now(), null);

        // Lưu tất cả vào DB để sinh ID trước khi phân công
        em.getTransaction().begin();
        em.persist(emp1);
        em.persist(emp2);
        em.persist(emp3);
        em.persist(projA);
        em.persist(projB);
        em.getTransaction().commit();

        // 3. Phân công chéo sử dụng DAO[cite: 6]
        // NV1 tham gia Project A + B[cite: 6]
        dao.assignEmployeeToProject(emp1.getId(), projA.getId());
        dao.assignEmployeeToProject(emp1.getId(), projB.getId());

        // NV2 tham gia Project B[cite: 6]
        dao.assignEmployeeToProject(emp2.getId(), projB.getId());

        // NV3 tham gia Project A[cite: 6]
        dao.assignEmployeeToProject(emp3.getId(), projA.getId());

        // 4. In ra danh sách project của từng nhân viên[cite: 6]
        System.out.println("\n--- Kết quả phân công ---");

        // Clear cache để Hibernate bắt buộc lấy dữ liệu quan hệ mới nhất từ DB
        em.clear();

        Employee e1 = em.find(Employee.class, emp1.getId());
        Employee e2 = em.find(Employee.class, emp2.getId());
        Employee e3 = em.find(Employee.class, emp3.getId());

        printEmployeeProjects(e1);
        printEmployeeProjects(e2);
        printEmployeeProjects(e3);

        em.close();
        JPAUtil.close();
    }

    private static void printEmployeeProjects(Employee e) {
        System.out.println("Nhân viên: " + e.getFullName());
        if (e.getProjects().isEmpty()) {
            System.out.println("  -> Không tham gia dự án nào.");
        } else {
            for (Project p : e.getProjects()) {
                System.out.println("  -> Tham gia dự án: " + p.getProjectName() + " (" + p.getProjectCode() + ")");
            }
        }
    }
}