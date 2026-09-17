package fu.de200319.dao;

import fu.de200319.pojo.Employee;
import jakarta.persistence.*;

public class EmployeeDAO {

    // Lưu ý: Tên "hsf302FU" bắt buộc phải khớp với thẻ <persistence-unit name="hsf302FU"> trong persistence.xml
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hsf302FU");

    // ---------- CREATE (TODO 0.3) ----------
    public void save(Employee e) {
        // Trước dòng này: e đang ở trạng thái NEW/TRANSIENT
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e); // -> e chuyển sang trạng thái MANAGED, sẽ được chạy lệnh INSERT khi commit
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close(); // Sau khi EntityManager đóng, e chuyển sang trạng thái DETACHED
        }
    }
}