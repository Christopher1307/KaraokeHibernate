package aed.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityManagerUtil {
    private static EntityManagerFactory emf;

    public static void initialize(String user, String password) {
        String jdbcUrl = "jdbc:mariadb://localhost:3306/";
        String dbName = "karaoke";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
             Statement stmt = conn.createStatement()) {

            // Verificar si la base de datos existe
            ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'");
            if (!rs.next()) {
                System.out.println("La base de datos '" + dbName + "' no existe. Creándola...");
                stmt.executeUpdate("CREATE DATABASE " + dbName);
                System.out.println("Base de datos creada con éxito.");

                // Ejecutar el script SQL
                executeSqlScript(jdbcUrl + dbName, user, password, "karaoke.sql");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al verificar o crear la base de datos: " + e.getMessage(), e);
        }

        // Configurar Hibernate con la base de datos creada
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", jdbcUrl + dbName);
        properties.put("jakarta.persistence.jdbc.user", user);
        properties.put("jakarta.persistence.jdbc.password", password);
        emf = Persistence.createEntityManagerFactory("KaraokePU", properties);
    }

    private static void executeSqlScript(String jdbcUrl, String user, String password, String scriptPath) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
             Statement stmt = conn.createStatement();
             InputStream inputStream = EntityManagerUtil.class.getClassLoader().getResourceAsStream(scriptPath)) {

            if (inputStream == null) {
                throw new RuntimeException("No se encontró el script SQL en recursos: " + scriptPath);
            }

            String sql = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));

            for (String query : sql.split(";")) {
                if (!query.trim().isEmpty()) {
                    stmt.executeUpdate(query);
                }
            }

            System.out.println("Script SQL ejecutado exitosamente.");

        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar el script SQL: " + e.getMessage(), e);
        }
    }

    public static EntityManager getEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("EntityManagerUtil no ha sido inicializado.");
        }
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }
}
