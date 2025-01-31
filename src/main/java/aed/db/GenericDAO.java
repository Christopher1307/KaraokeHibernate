package aed.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<Cancion> cargarTodasLasCanciones() {
        GenericDAO<Cancion> cancionDAO = new GenericDAO<>(Cancion.class);
        return cancionDAO.findAll();
    }

    public List<T> findAll() {
        EntityManager em = aed.db.EntityManagerUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
        } finally {
            em.close();
        }
    }

    // Crete log in the database
    public void save(Cancion cancion, Usuario usuario) {
        EntityManager em = aed.db.EntityManagerUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Verificar si el usuario ya está en la base de datos
            if (usuario.getIdUsuario() <= 0 || em.find(Usuario.class, usuario.getIdUsuario()) == null) {
                em.persist(usuario); // Persistir usuario si no existe
                em.flush(); // Forzar la escritura en la BD para asignar ID
            } else {
                usuario = em.merge(usuario); // Asegurar que el usuario esté gestionado
            }

            // Verificar si la canción ya está en la base de datos
            if (cancion.getIdCancion() <= 0 || em.find(Cancion.class, cancion.getIdCancion()) == null) {
                em.persist(cancion); // Persistir canción si no existe
                em.flush();
            } else {
                cancion = em.merge(cancion);
            }

            // Crear y persistir el log
            KaraokeLog log = new KaraokeLog();
            log.setCancion(cancion);
            log.setUsuario(usuario);
            log.setFechaRepro(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            em.persist(log);
            tx.commit();

            System.out.println("Log guardado correctamente.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void addRepro(Cancion cancion) {
        EntityManager em = aed.db.EntityManagerUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Obtener la entidad actualizada de la base de datos
            Cancion managedCancion = em.find(Cancion.class, cancion.getIdCancion());
            if (managedCancion != null) {
                // Incrementar el contador de reproducciones
                managedCancion.setVecesCantada((managedCancion.getVecesCantada() == null ? 0 : managedCancion.getVecesCantada()) + 1);
                em.merge(managedCancion);
            }

            tx.commit();
            System.out.println("Reproducción añadida correctamente.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}