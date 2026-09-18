package fu.de200319;

import fu.de200319.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu Test TODO 5.2 ---");

        // Kích hoạt Hibernate để tạo bảng theo cấu hình mới trong Employee
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        System.out.println("Đã chạy xong TODO 5.2! Hãy kiểm tra SQL Server, bảng 'employee_project' đã được tạo.");

        em.close();
        JPAUtil.close();
    }
}