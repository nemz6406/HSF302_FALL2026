package fu.de200319;

import fu.de200319.util.JPAUtil;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu Test TODO 5.1 ---");
        // Chỉ cần gọi EMF để Hibernate kết nối DB và tạo 2 bảng gốc
        JPAUtil.getEMF().createEntityManager().close();
        System.out.println("Test 5.1 Xong: Đã tạo bảng 'employees' và 'projects' (Chưa có bảng trung gian)");
        JPAUtil.close();
    }
}