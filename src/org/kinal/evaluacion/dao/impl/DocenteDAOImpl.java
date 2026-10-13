
package org.kinal.evaluacion.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.kinal.evaluacion.dao.DocenteDAO;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Docente;
import org.kinal.evaluacion.util.Conexion;

public class DocenteDAOImpl implements DocenteDAO {

    @Override
    public void insertar(Docente docente) throws DaoException {

        String sql = "{CALL sp_insertar_docente(?,?,?,?,?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {

                cs.setString(1, docente.getNombres());
                cs.setString(2, docente.getApellidos());
                cs.setString(3, docente.getCorreo());
                cs.setString(4, docente.getTelefono());
                cs.setString(5, docente.getEspecialidad());

                try (ResultSet rs = cs.executeQuery()) {
                    if (rs.next()) {
                        docente.setIdDocente(rs.getInt("id_docente"));
                    }
                }
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al insertar docente: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Docente docente) throws DaoException {

        String sql = "{CALL sp_actualizar_docente(?,?,?,?,?,?,?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {

                cs.setInt(1, docente.getIdDocente());
                cs.setString(2, docente.getNombres());
                cs.setString(3, docente.getApellidos());
                cs.setString(4, docente.getCorreo());
                cs.setString(5, docente.getTelefono());
                cs.setString(6, docente.getEspecialidad());
                cs.setString(7, docente.getEstado());

                cs.execute();
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al actualizar docente: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) throws DaoException {

        String sql = "{CALL sp_eliminar_docente(?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {

                cs.setInt(1, id);
                cs.execute();
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al eliminar docente: " + e.getMessage());
        }
    }

    @Override
    public List<Docente> listar() throws DaoException {

        List<Docente> docentes = new ArrayList<>();

        String sql = "{CALL sp_listar_docentes()}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql);
                 ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {
                    docentes.add(mapearDocente(rs));
                }
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al listar docentes: " + e.getMessage());
        }

        return docentes;
    }

    @Override
    public Docente buscarPorId(Integer id) throws DaoException {

        if (id == null) {
            return null;
        }

        for (Docente docente : listar()) {
            if (docente.getIdDocente() == id) {
                return docente;
            }
        }

        return null;
    }

    private Docente mapearDocente(ResultSet rs)
            throws SQLException {

        Docente docente = new Docente();

        docente.setIdDocente(rs.getInt("id_docente"));
        docente.setNombres(rs.getString("nombres"));
        docente.setApellidos(rs.getString("apellidos"));
        docente.setCorreo(rs.getString("correo"));
        docente.setTelefono(rs.getString("telefono"));
        docente.setEspecialidad(rs.getString("especialidad"));
        docente.setEstado(rs.getString("estado"));

        Timestamp fechaRegistro =
                rs.getTimestamp("fecha_registro");

        if (fechaRegistro != null) {
            docente.setFechaRegistro(
                    fechaRegistro.toLocalDateTime());
        }

        return docente;
    }
}

