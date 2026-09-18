package fu.de200319.dao;

import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Project;
import fu.de200319.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EmployeeDAO {

    // ---------- TODO 5.6: find cả 2 entity và gọi assignToProject() ----------
    public boolean assignEmployeeToProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Tìm cả 2 entity
            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            if (employee != null && project != null) {
                // Gọi helper method ở TODO 5.5
                employee.assignToProject(project);

                // Commit transaction -> Hibernate tự phát hiện thay đổi và insert vào employee_project
                tx.commit();
                return true;
            } else {
                System.out.println("Không tìm thấy Employee hoặc Project với ID đã cho!");
                tx.rollback();
                return false;
            }
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}