package fu.de200319.dao;

import fu.de200319.pojo.Employee;
import fu.de200319.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EmployeeDAO {

    public void save(Employee e) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Employee update(Employee e) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            Employee merged = em.merge(e);
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
            Employee e = em.find(Employee.class, id);
            if (e != null) {
                em.remove(e);
            }
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}