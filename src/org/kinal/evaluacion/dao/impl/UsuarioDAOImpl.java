
package org.kinal.evaluacion.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.kinal.evaluacion.dao.UsuarioDAO;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Usuario;
import org.kinal.evaluacion.util.Conexion;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void insertar(Usuario usuario) throws DaoException {

        String sql = "{CALL sp_insertar_usuario(?,?,?,?)}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {

                cs.setString(1, usuario.getNombreCompleto());
                cs.setString(2, usuario.getUsername());
                cs.setString(3, usuario.getPasswordHash());
                cs.setString(4, usuario.getRol());

                try (ResultSet rs = cs.executeQuery()) {
                    if (rs.next()) {
                        usuario.setIdUsuario(
                                rs.getInt("id_usuario"));
                    }
                }
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al insertar usuario: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Usuario usuario) throws DaoException {

        String sql = "{CALL sp_actualizar_usuario(?,?,?,?,?)}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {

                cs.setInt(1, usuario.getIdUsuario());
                cs.setString(2, usuario.getNombreCompleto());
                cs.setString(3, usuario.getUsername());
                cs.setString(4, usuario.getRol());
                cs.setBoolean(5, usuario.isActivo());

                cs.execute();
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) throws DaoException {

        String sql = "{CALL sp_eliminar_usuario(?)}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {

                cs.setInt(1, id);
                cs.execute();
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al eliminar usuario: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> listar() throws DaoException {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "{CALL sp_listar_usuarios()}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql);
                 ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs));
                }
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al listar usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    @Override
    public Usuario buscarPorId(Integer id) throws DaoException {

        if (id == null) {
            return null;
        }

        for (Usuario usuario : listar()) {
            if (usuario.getIdUsuario() == id) {
                return usuario;
            }
        }

        return null;
    }

    private Usuario mapearUsuario(ResultSet rs)
            throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNombreCompleto(
                rs.getString("nombre_completo"));
        usuario.setUsername(rs.getString("username"));
        usuario.setRol(rs.getString("rol"));
        usuario.setActivo(rs.getBoolean("activo"));

        Timestamp fechaCreacion =
                rs.getTimestamp("fecha_creacion");

        if (fechaCreacion != null) {
            usuario.setFechaCreacion(
                    fechaCreacion.toLocalDateTime());
        }

        return usuario;
    }
}
