package fu.de200319.dao;

import fu.de200319.pojo.Department;
import fu.de200319.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class DepartmentDAO {

    public void save(Department d) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(d);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public Department findById(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.find(Department.class, id);
        } finally {
            em.close();
        }
    }

    public List<Department> findAll() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d", Department.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Department update(Department d) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            // Bắt buộc dùng merge và gán lại kết quả
            Department merged = em.merge(d);
            em.getTransaction().commit();
            return merged;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            Department d = em.find(Department.class, id);
            if (d != null) {
                em.remove(d);
            }
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // ---------- TODO 2.6: JPQL JOIN FETCH ----------
    public Department findByIdWithEmployees(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            // Dùng JOIN FETCH d.employees để kéo luôn list nhân viên lên trong 1 câu SQL
            return em.createQuery(
                            "SELECT d FROM Department d JOIN FETCH d.employees WHERE d.id = :id",
                            Department.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}