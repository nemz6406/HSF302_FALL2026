package fu.de200319;

import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu Test TODO 5.4 ---");

        // Tạo 2 đối tượng Employee hoàn toàn khác nhau (khác object reference) nhưng CÙNG email[cite: 6]
        Employee emp1 = new Employee("Nguyen Van A", new BigDecimal("1000"), LocalDate.now(), "test@gmail.com", Gender.MALE, true);
        Employee emp2 = new Employee("Nguyen Van B", new BigDecimal("2000"), LocalDate.now(), "test@gmail.com", Gender.MALE, true);

        // Thêm cả 2 vào Set
        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(emp1);
        employeeSet.add(emp2); // Cố tình add phần tử thứ 2 trùng email

        // Kiểm tra kết quả
        System.out.println("Số lượng phần tử trong Set: " + employeeSet.size());

        if (employeeSet.size() == 1) {
            System.out.println("=> Test 5.4 THÀNH CÔNG! Set chỉ giữ 1 phần tử vì nó nhận diện trùng email.");
        } else {
            System.out.println("=> Test 5.4 THẤT BẠI! Hãy kiểm tra lại hàm equals/hashCode.");
        }
    }
}