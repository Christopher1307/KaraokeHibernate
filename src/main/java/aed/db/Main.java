package aed.db;

import aed.db.GenericDAO;
import aed.db.Usuario;

public class Main {
    public static void main(String[] args) {
        GenericDAO<Usuario> usuarioDAO = new GenericDAO<>(Usuario.class);

        // Crear un usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Pérez");
        usuario.setNombreUsuario("jperez");
        usuario.setApellidoUsuario("Pérez");
        usuario.setEmailUsuario("jperez@mail.com");

        usuarioDAO.create(usuario);

        // Leer un usuario
        Usuario usuarioLeido = usuarioDAO.read(usuario.getIdUsuario());
        System.out.println("Usuario leído: " + usuarioLeido.getNombre());
    }
}
