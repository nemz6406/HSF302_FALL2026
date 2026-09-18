package fu.de200319;

import fu.de200319.dao.ProjectDAO;
import fu.de200319.util.JPAUtil;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu Test TODO 5.8 ---");

        ProjectDAO projectDAO = new ProjectDAO();
        // Gọi hàm thực thi câu lệnh JPQL thống kê
        projectDAO.printActiveEmployeeStatsPerProject();

        System.out.println("=> Test 5.8 hoàn tất! Kết quả phải khớp với dữ liệu bạn đã insert ở TODO 5.7.");

        JPAUtil.close();
    }
}