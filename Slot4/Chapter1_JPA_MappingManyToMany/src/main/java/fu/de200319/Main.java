package fu.de200319;

import fu.de200319.dao.EmployeeDAO;
import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Project;
import fu.de200319.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu Test TODO 5.9 ---");

        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EmployeeDAO dao = new EmployeeDAO();

        try {
            // Lấy dữ liệu NV1 và Project A bằng email và projectCode
            Employee emp1 = em.createQuery("SELECT e FROM Employee e WHERE e.email = 'nv1@gmail.com'", Employee.class).getSingleResult();
            Project projA = em.createQuery("SELECT p FROM Project p WHERE p.projectCode = 'PRJ_A'", Project.class).getSingleResult();

            System.out.println("Trước khi gỡ: NV1 có tham gia Project A không? -> " + emp1.getProjects().contains(projA));

            // Gỡ NV1 khỏi Project A thông qua DAO
            System.out.println("\nĐang tiến hành gỡ...");
            boolean success = dao.unassignEmployeeFromProject(emp1.getId(), projA.getId());

            if (success) {
                // Clear cache để Hibernate truy vấn lại database thật
                em.clear();
                Employee empAfter = em.find(Employee.class, emp1.getId());
                Project projAfter = em.find(Project.class, projA.getId());

                System.out.println("=> Gỡ thành công! Kiểm tra lại:");
                System.out.println("- Project gốc có bị xóa không? -> " + (projAfter != null ? "Không (ĐÚNG)" : "Có (SAI)"));
                System.out.println("- Employee gốc có bị xóa không? -> " + (empAfter != null ? "Không (ĐÚNG)" : "Có (SAI)"));

                boolean stillInProject = false;
                for (Project p : empAfter.getProjects()) {
                    if (p.getProjectCode().equals("PRJ_A")) stillInProject = true;
                }
                System.out.println("- NV1 còn liên kết với Project A không? -> " + (stillInProject ? "Có (SAI)" : "Không (ĐÚNG)"));
            } else {
                System.out.println("=> Test 5.9 THẤT BẠI! Gỡ không thành công.");
            }

        } catch (Exception e) {
            System.out.println("Lỗi: Không tìm thấy dữ liệu. Hãy đảm bảo dữ liệu từ TODO 5.7 vẫn tồn tại trong DB.");
            e.printStackTrace();
        } finally {
            em.close();
            JPAUtil.close();
        }
    }
}