package aed.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.List;

public class GenericDAO<T> {
    private final Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        EntityManager em = aed.db.EntityManagerUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public T read(Object id) {
        EntityManager em = aed.db.EntityManagerUtil.getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    public void update(T entity) {
        EntityManager em = aed.db.EntityManagerUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void delete(Object id) {
        EntityManager em = aed.db.EntityManagerUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public T findByNombreUsuario(String nombreUsuario) {
        EntityManager em = aed.db.EntityManagerUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT u FROM " + entityClass.getSimpleName() + " u WHERE u.nombreUsuario = :nombreUsuario", entityClass)
                    .setParameter("nombreUsuario", nombreUsuario)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No se encontró un usuario con el nombreUsuario: " + nombreUsuario);
            return null; // Si no encuentra resultados, devuelve null
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Si ocurre un error, devuelve null
        } finally {
            em.close();
        }
    }

    public List<T> findAll() {
        EntityManager em = aed.db.EntityManagerUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
        } finally {
            em.close();
        }
    }
}