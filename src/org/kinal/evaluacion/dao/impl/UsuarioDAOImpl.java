
package org.kinal.evaluacion.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.kinal.evaluacion.dao.UsuarioDAO;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Usuario;
import org.kinal.evaluacion.util.Conexion;

/**
 * Implementacion DAO para gestionar usuarios.
 * Utiliza Stored Procedures de MySQL.
 *
 * @author Juan Esteban Interiano Riera
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    /**
     * Inserta un usuario en la base de datos.
     */
    @Override
    public void insertar(Usuario usuario) throws DaoException {

        String sql = "{CALL sp_insertar_usuario(?, ?, ?, ?)}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement stmt =
                    conn.prepareCall(sql)) {

                stmt.setString(1, usuario.getNombreCompleto());
                stmt.setString(2, usuario.getUsername());
                stmt.setString(3, usuario.getPasswordHash());
                stmt.setString(4, usuario.getRol());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        usuario.setIdUsuario(
                                rs.getInt("id_usuario"));
                    }
                }
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al insertar usuario: "
                    + e.getMessage());
        }
    }

    /**
     * Actualiza los datos de un usuario.
     */
    @Override
    public void actualizar(Usuario usuario) throws DaoException {

        String sql =
                "{CALL sp_actualizar_usuario(?, ?, ?, ?, ?)}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement stmt =
                    conn.prepareCall(sql)) {

                stmt.setInt(1, usuario.getIdUsuario());
                stmt.setString(2, usuario.getNombreCompleto());
                stmt.setString(3, usuario.getUsername());
                stmt.setString(4, usuario.getRol());
                stmt.setBoolean(5, usuario.isActivo());

                stmt.execute();
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al actualizar usuario: "
                    + e.getMessage());
        }
    }

    /**
     * Elimina un usuario por su ID.
     */
    @Override
    public void eliminar(Integer id) throws DaoException {

        String sql = "{CALL sp_eliminar_usuario(?)}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement stmt =
                    conn.prepareCall(sql)) {

                stmt.setInt(1, id);
                stmt.execute();
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al eliminar usuario: "
                    + e.getMessage());
        }
    }

    /**
     * Lista todos los usuarios registrados.
     */
    @Override
    public List<Usuario> listar() throws DaoException {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "{CALL sp_listar_usuarios()}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement stmt =
                    conn.prepareCall(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs, false));
                }
            }

            return usuarios;

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al listar usuarios: "
                    + e.getMessage());
        }
    }

    /**
     * Busca un usuario por su ID.
     */
    @Override
    public Usuario buscarPorId(Integer id) throws DaoException {

        for (Usuario usuario : listar()) {

            if (usuario.getIdUsuario() == id) {
                return usuario;
            }
        }

        return null;
    }

    /**
     * Busca un usuario activo por su username.
     * Este metodo se utiliza para el login.
     */
    @Override
    public Usuario buscarPorUsername(String username)
            throws DaoException {

        String sql = "{CALL sp_buscar_usuario_login(?)}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement stmt =
                    conn.prepareCall(sql)) {

                stmt.setString(1, username);

                try (ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {
                        return mapearUsuario(rs, true);
                    }
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al buscar usuario para login: "
                    + e.getMessage());
        }
    }

    /**
     * Convierte un registro SQL en un objeto Usuario.
     *
     * @param rs resultado de la consulta
     * @param incluirHash indica si se debe leer
     * el hash de la contrasena
     */
    private Usuario mapearUsuario(
            ResultSet rs, boolean incluirHash)
            throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setIdUsuario(
                rs.getInt("id_usuario"));

        usuario.setNombreCompleto(
                rs.getString("nombre_completo"));

        usuario.setUsername(
                rs.getString("username"));

        usuario.setRol(
                rs.getString("rol"));

        usuario.setActivo(
                rs.getBoolean("activo"));

        if (incluirHash) {
            usuario.setPasswordHash(
                    rs.getString("password_hash"));
        }

        return usuario;
    }
}
