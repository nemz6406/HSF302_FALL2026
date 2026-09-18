package fu.de200319.dao;

import fu.de200319.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProjectDAO {

    // ---------- TODO 5.8: Viết JPQL đếm số nhân viên active và tính tổng lương ----------
    public void printActiveEmployeeStatsPerProject() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            // Câu JPQL theo đúng yêu cầu
            String jpql = "SELECT p.projectName, COUNT(e), SUM(e.salary) " +
                    "FROM Project p JOIN p.employees e " +
                    "WHERE e.active = true " +
                    "GROUP BY p.projectName";

            List<Object[]> results = em.createQuery(jpql, Object[].class).getResultList();

            System.out.println("--- Thống kê Dự án (TODO 5.8) ---");
            if (results.isEmpty()) {
                System.out.println("Không có dữ liệu thống kê.");
            } else {
                for (Object[] row : results) {
                    String projectName = (String) row[0];
                    Long empCount = (Long) row[1];
                    BigDecimal totalSalary = (BigDecimal) row[2];

                    System.out.println("Dự án: " + projectName
                            + " | Số NV active: " + empCount
                            + " | Tổng lương: " + totalSalary);
                }
            }
        } finally {
            em.close();
        }
    }
}