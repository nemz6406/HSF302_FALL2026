package fu.de200319;

import fu.de200319.dao.EmployeeDAO;
import fu.de200319.util.JPAUtil;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu Test TODO 5.10 ---");

        EmployeeDAO dao = new EmployeeDAO();
        dao.printEmployeesInMultipleProjects();

        // Lưu ý: Ở TODO 5.9 chúng ta đã gỡ NV1 khỏi Project A,
        // nên hiện tại NV1 chỉ còn làm Project B (1 dự án).
        // Kết quả in ra sẽ là "Không có nhân viên nào..." là hoàn toàn chính xác!

        JPAUtil.close();
    }
}