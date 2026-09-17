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
        Employee emp = new Employee("Nguyen Van A", "a6@fpt.edu.vn",
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
        System.out.println("\n--- Bắt đầu TODO 0.6 (UPDATE) ---");
        // Giả sử sếp quyết định tăng lương cho nhân viên emp vừa tạo lúc đầu
        // emp hiện tại đang ở trạng thái DETACHED
        emp.setSalary(new BigDecimal("18500000"));

        // Gọi hàm update để đồng bộ xuống DB
        Employee updatedEmp = dao.update(emp);
        System.out.println("6. Thông tin sau khi update (đối tượng trả về từ merge):");
        System.out.println(" -> " + updatedEmp);

        // Kiểm chứng lại bằng cách đọc hẳn lại từ DB lên xem đã lưu chưa
        Employee reChecked = dao.findById(emp.getId());
        System.out.println("7. Kiểm tra lại từ DB xem lương đổi chưa:");
        System.out.println(" -> " + reChecked);
        System.out.println("\n--- Bắt đầu TODO 0.7 (DELETE) ---");
        System.out.println("8. Tiến hành xóa nhân viên có ID = " + emp.getId());

        // Gọi hàm xóa
        dao.delete(emp.getId());

        // Kiểm chứng lại bằng cách tìm lại ID đó
        Employee afterDelete = dao.findById(emp.getId());
        System.out.println("9. Tìm lại sau khi xóa (Kỳ vọng là null): " + afterDelete);
        System.out.println("\n--- Bắt đầu TODO 0.9 (Kiểm tra Unique Email) ---");
        // Khởi tạo 2 nhân viên có cùng chung 1 email "trung@fpt.edu.vn"
        Employee dup1 = new Employee("User 1", "trung@fpt.edu.vn",
                new BigDecimal("10000000"), Gender.FEMALE, LocalDate.now());
        Employee dup2 = new Employee("User 2", "trung@fpt.edu.vn",
                new BigDecimal("11000000"), Gender.MALE, LocalDate.now());

        System.out.println("Lưu nhân viên 1...");
        dao.save(dup1); // Lưu người thứ 1 thành công

        System.out.println("Lưu nhân viên 2 (trùng email)...");
        try {
            dao.save(dup2); // Cố tình lưu người thứ 2
            System.out.println("LỖI: Code chạy sai vì không bắt được exception!");
        } catch (RuntimeException ex) {
            System.out.println("THÀNH CÔNG: Đã bắt được lỗi trùng email như kỳ vọng!");
            System.out.println("Loại lỗi sinh ra: " + ex.getClass().getSimpleName());
        }
    }


}