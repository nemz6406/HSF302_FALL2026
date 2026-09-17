package fu.de200319.dao;

import fu.de200319.pojo.Employee;
import jakarta.persistence.*;

import java.math.BigDecimal;
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
    // ---------- READ có điều kiện (TODO 0.5) ----------
    public Employee findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            // Dùng tham số :email để truyền giá trị
            List<Employee> result = em.createQuery(
                            "SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                    .setParameter("email", email)
                    .getResultList();

            // Nếu list rỗng thì trả về null, ngược lại lấy nhân viên đầu tiên tìm được
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public List<Employee> findBySalaryGreaterThanAndActive(BigDecimal minSalary) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT e FROM Employee e WHERE e.salary > :minSalary AND e.active = true",
                            Employee.class)
                    .setParameter("minSalary", minSalary)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ---------- UPDATE (TODO 0.6) ----------
    public Employee update(Employee e) {
        // e truyền vào có thể đang DETACHED (vì được lấy từ 1 EntityManager đã đóng ở hàm findById)
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // merge() sẽ copy dữ liệu từ e sang một object Managed mới và trả về object đó
            Employee merged = em.merge(e);
            em.getTransaction().commit();

            // BẮT BUỘC trả về object merged này để dùng tiếp, không dùng e cũ
            return merged;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}