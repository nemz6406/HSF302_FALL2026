package fu.de200319.dao;

import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Project;
import fu.de200319.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class EmployeeDAO {

    public boolean assignEmployeeToProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            if (employee != null && project != null) {
                employee.assignToProject(project);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean unassignEmployeeFromProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            if (employee != null && project != null) {
                employee.unassignFromProject(project);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // ---------- TODO 5.10: JPQL tìm Employee tham gia > 1 project ----------
    public void printEmployeesInMultipleProjects() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            // Câu JPQL theo đúng yêu cầu[cite: 4]
            String jpql = "SELECT e FROM Employee e WHERE e.active = true AND SIZE(e.projects) > 1";
            List<Employee> results = em.createQuery(jpql, Employee.class).getResultList();

            System.out.println("--- Danh sách NV tham gia nhiều dự án (TODO 5.10) ---");
            if (results.isEmpty()) {
                System.out.println("Không có nhân viên nào đang tham gia nhiều hơn 1 dự án.");
            } else {
                for (Employee e : results) {
                    System.out.println("- Nhân viên: " + e.getFullName()
                            + " | Email: " + e.getEmail()
                            + " | Đang làm: " + e.getProjects().size() + " dự án.");
                }
            }
        } finally {
            em.close();
        }
    }
}