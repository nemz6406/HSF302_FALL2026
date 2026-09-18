package fu.de200319;

import fu.de200319.dao.EmployeeDAO;
import fu.de200319.pojo.Employee;
import fu.de200319.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu Test TODO 5.11 ---");

        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EmployeeDAO dao = new EmployeeDAO();

        try {
            // Lấy nhân viên 2 (tt2@gmail.com) đã tạo từ các bước trước
            Employee emp2 = em.createQuery("SELECT e FROM Employee e WHERE e.email = 'tt2@gmail.com'", Employee.class).getSingleResult();
            System.out.println("Trạng thái trước khi deactivate: active = " + emp2.isActive());

            // Gọi hàm deactivate
            boolean success = dao.deactivateEmployee(emp2.getId());

            if (success) {
                em.clear();
                Employee checkEmp = em.find(Employee.class, emp2.getId());
                System.out.println("Trạng thái sau khi deactivate: active = " + checkEmp.isActive());
                System.out.println("Lịch sử dự án được bảo toàn: " + checkEmp.getProjects().size() + " dự án (ĐÚNG)");
                System.out.println("=> Test 5.11 THÀNH CÔNG!");
            } else {
                System.out.println("=> Test 5.11 THẤT BẠI!");
            }

        } catch (Exception e) {
            System.out.println("Lỗi: Không tìm thấy dữ liệu. Hãy đảm bảo dữ liệu từ TODO 5.7 vẫn tồn tại.");
            e.printStackTrace();
        } finally {
            em.close();
            JPAUtil.close();
        }
    }
}