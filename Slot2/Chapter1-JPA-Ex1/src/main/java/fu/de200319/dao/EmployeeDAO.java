package fu.de200319.dao;

import fu.de200319.pojo.Employee;
import jakarta.persistence.*;
import java.util.List;

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
    // ---------- READ (TODO 0.4) ----------
    public Employee findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            // Hàm find trả về null nếu không tìm thấy
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    // Nhớ import java.util.List;
    public List<Employee> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}