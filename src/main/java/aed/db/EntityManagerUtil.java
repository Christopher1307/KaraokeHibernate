package aed.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class EntityManagerUtil {
    private static EntityManagerFactory emf;

    public static void initialize(String user, String password) {
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.user", user);
        properties.put("jakarta.persistence.jdbc.password", password);
        emf = Persistence.createEntityManagerFactory("KaraokePU", properties);
    }

    public static EntityManager getEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("EntityManagerUtil has not been initialized.");
        }
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }
}