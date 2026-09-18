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

    public void printEmployeesInMultipleProjects() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            String jpql = "SELECT e FROM Employee e WHERE e.active = true AND SIZE(e.projects) > 1";
            List<Employee> results = em.createQuery(jpql, Employee.class).getResultList();

            System.out.println("--- Danh sách NV tham gia nhiều dự án ---");
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

    // ---------- TODO 5.11: Viết method deactivateEmployee ----------
    /*
     * GIẢI THÍCH VỀ CASCADE:
     * - Khi nhân viên nghỉ việc (deactivate), ta chỉ đổi trạng thái active = false chứ KHÔNG xóa hay gỡ khỏi project.
     * - Dữ liệu trong bảng trung gian employee_project cần được giữ lại để tra cứu lịch sử làm việc.
     * - Tuyệt đối không dùng cascade = CascadeType.REMOVE hoặc ALL cho quan hệ Many-to-Many này,
     *   vì nếu xóa/thay đổi Employee có thể dẫn đến việc xóa nhầm Project của người khác.
     */
    public boolean deactivateEmployee(Long employeeId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee employee = em.find(Employee.class, employeeId);

            if (employee != null) {
                employee.setActive(false); // Đổi trạng thái thành ngừng hoạt động
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
}