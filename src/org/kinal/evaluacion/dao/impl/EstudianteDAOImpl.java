
package org.kinal.evaluacion.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.kinal.evaluacion.dao.EstudianteDAO;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Estudiante;
import org.kinal.evaluacion.util.Conexion;

public class EstudianteDAOImpl implements EstudianteDAO {

    @Override
    public void insertar(Estudiante estudiante)
            throws DaoException {

        String sql = "{CALL sp_insertar_estudiante(?,?,?,?,?,?,?)}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {

                if (estudiante.getIdUsuario() == null) {
                    cs.setNull(1, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(1, estudiante.getIdUsuario());
                }

                cs.setString(2, estudiante.getCarnet());
                cs.setString(3, estudiante.getNombres());
                cs.setString(4, estudiante.getApellidos());

                if (estudiante.getFechaNacimiento() == null) {
                    cs.setNull(5, java.sql.Types.DATE);
                } else {
                    cs.setDate(5, Date.valueOf(
                            estudiante.getFechaNacimiento()));
                }

                cs.setString(6, estudiante.getCorreo());
                cs.setString(7, estudiante.getTelefono());

                try (ResultSet rs = cs.executeQuery()) {
                    if (rs.next()) {
                        estudiante.setIdEstudiante(
                                rs.getInt("id_estudiante"));
                    }
                }
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al insertar estudiante: "
                    + e.getMessage());
        }
    }

    @Override
    public void actualizar(Estudiante estudiante)
            throws DaoException {

        String sql =
                "{CALL sp_actualizar_estudiante(?,?,?,?,?,?,?,?)}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {

                cs.setInt(1, estudiante.getIdEstudiante());
                cs.setString(2, estudiante.getCarnet());
                cs.setString(3, estudiante.getNombres());
                cs.setString(4, estudiante.getApellidos());

                if (estudiante.getFechaNacimiento() == null) {
                    cs.setNull(5, java.sql.Types.DATE);
                } else {
                    cs.setDate(5, Date.valueOf(
                            estudiante.getFechaNacimiento()));
                }

                cs.setString(6, estudiante.getCorreo());
                cs.setString(7, estudiante.getTelefono());
                cs.setString(8, estudiante.getEstado());

                cs.execute();

            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al actualizar estudiante: "
                    + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) throws DaoException {

        String sql = "{CALL sp_eliminar_estudiante(?)}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {

                cs.setInt(1, id);
                cs.execute();

            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al eliminar estudiante: "
                    + e.getMessage());
        }
    }

    @Override
    public List<Estudiante> listar() throws DaoException {

        List<Estudiante> estudiantes = new ArrayList<>();

        String sql = "{CALL sp_listar_estudiantes()}";

        try {
            Connection conn =
                    Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql);
                 ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {
                    estudiantes.add(mapearEstudiante(rs));
                }
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al listar estudiantes: "
                    + e.getMessage());
        }

        return estudiantes;
    }

    @Override
    public Estudiante buscarPorId(Integer id)
            throws DaoException {

        for (Estudiante estudiante : listar()) {
            if (estudiante.getIdEstudiante() == id) {
                return estudiante;
            }
        }

        return null;
    }

    private Estudiante mapearEstudiante(ResultSet rs)
            throws SQLException {

        Estudiante estudiante = new Estudiante();

        estudiante.setIdEstudiante(
                rs.getInt("id_estudiante"));

        int idUsuario = rs.getInt("id_usuario");
        estudiante.setIdUsuario(
                rs.wasNull() ? null : idUsuario);

        estudiante.setCarnet(rs.getString("carnet"));
        estudiante.setNombres(rs.getString("nombres"));
        estudiante.setApellidos(rs.getString("apellidos"));

        Date fechaNacimiento =
                rs.getDate("fecha_nacimiento");

        if (fechaNacimiento != null) {
            estudiante.setFechaNacimiento(
                    fechaNacimiento.toLocalDate());
        }

        estudiante.setCorreo(rs.getString("correo"));
        estudiante.setTelefono(rs.getString("telefono"));
        estudiante.setEstado(rs.getString("estado"));

        Timestamp fechaRegistro =
                rs.getTimestamp("fecha_registro");

        if (fechaRegistro != null) {
            estudiante.setFechaRegistro(
                    fechaRegistro.toLocalDateTime());
        }

        return estudiante;
    }
}
