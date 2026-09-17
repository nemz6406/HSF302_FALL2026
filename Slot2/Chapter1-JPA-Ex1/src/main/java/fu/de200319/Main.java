package fu.de200319;

import fu.de200319.dao.EmployeeDAO;
import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println("--- Đang tạo mới nhân viên ---");
        // Tạo 1 object Employee mới (Trạng thái: NEW/TRANSIENT)
        Employee emp = new Employee("Nguyen Van A", "a3@fpt.edu.vn",
                new BigDecimal("15000000"), Gender.MALE, LocalDate.of(2022, 3, 1));

        // Gọi hàm save
        dao.save(emp);

        // Kiểm tra xem ID đã được DB tự động sinh ra chưa
        System.out.println("Đã lưu thành công! Thông tin nhân viên vừa tạo:");
        System.out.println(emp); // Sẽ gọi hàm toString() trong Employee
        System.out.println("ID được sinh ra là: " + emp.getId());
        System.out.println("\n--- Bắt đầu TODO 0.4 (READ) ---");
        // Gọi hàm findById
        Employee found = dao.findById(emp.getId());
        System.out.println("2. Đọc lại bằng findById: " + found);

        // Gọi hàm findAll
        System.out.println("3. Đọc tất cả nhân viên (findAll):");
        for (Employee e : dao.findAll()) {
            System.out.println(" - " + e);
        }
        System.out.println("\n--- Bắt đầu TODO 0.5 (READ có điều kiện) ---");

        // 1. Tìm theo Email (lấy chính email của nhân viên vừa tạo ở trên để tìm)
        System.out.println("4. Tìm nhân viên theo email '" + emp.getEmail() + "':");
        Employee byEmail = dao.findByEmail(emp.getEmail());
        System.out.println(" -> Kết quả: " + byEmail);

        // 2. Tìm nhân viên có lương lớn hơn 10.000.000 và đang active
        System.out.println("5. Tìm nhân viên có lương > 10.000.000 và đang active:");
        for (Employee e : dao.findBySalaryGreaterThanAndActive(new BigDecimal("10000000"))) {
            System.out.println(" -> " + e);
        }
    }
}