
package org.kinal.evaluacion.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.kinal.evaluacion.dao.SeccionDAO;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Seccion;
import org.kinal.evaluacion.util.Conexion;

public class SeccionDAOImpl implements SeccionDAO {

    @Override
    public void insertar(Seccion seccion) throws DaoException {
        String sql = "{CALL sp_insertar_seccion(?,?,?,?,?,?,?,?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, seccion.getIdCurso());
                cs.setInt(2, seccion.getIdDocente());
                cs.setString(3, seccion.getCodigoSeccion());
                cs.setString(4, seccion.getPeriodo());
                cs.setInt(5, seccion.getAnio());
                cs.setString(6, seccion.getHorario());
                cs.setString(7, seccion.getAula());
                cs.setInt(8, seccion.getCupoMaximo());

                try (ResultSet rs = cs.executeQuery()) {
                    if (rs.next()) {
                        seccion.setIdSeccion(
                                rs.getInt("id_seccion"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al insertar sección: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Seccion seccion) throws DaoException {
        String sql = "{CALL sp_actualizar_seccion(?,?,?,?,?,?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, seccion.getIdSeccion());
                cs.setInt(2, seccion.getIdDocente());
                cs.setString(3, seccion.getHorario());
                cs.setString(4, seccion.getAula());
                cs.setInt(5, seccion.getCupoMaximo());
                cs.setString(6, seccion.getEstado());

                cs.execute();
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al actualizar sección: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) throws DaoException {
        String sql = "{CALL sp_eliminar_seccion(?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, id);
                cs.execute();
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al eliminar sección: " + e.getMessage());
        }
    }

    @Override
    public List<Seccion> listar() throws DaoException {
        List<Seccion> secciones = new ArrayList<>();
        String sql = "{CALL sp_listar_secciones()}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql);
                 ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {
                    Seccion s = new Seccion();

                    s.setIdSeccion(rs.getInt("id_seccion"));
                    s.setIdCurso(rs.getInt("id_curso"));
                    s.setIdDocente(rs.getInt("id_docente"));
                    s.setCodigoSeccion(
                            rs.getString("codigo_seccion"));
                    s.setPeriodo(rs.getString("periodo"));
                    s.setAnio(rs.getInt("anio"));
                    s.setHorario(rs.getString("horario"));
                    s.setAula(rs.getString("aula"));
                    s.setCupoMaximo(rs.getInt("cupo_maximo"));
                    s.setCupoDisponible(
                            rs.getInt("cupo_disponible"));
                    s.setEstado(rs.getString("estado"));

                    Timestamp fecha =
                            rs.getTimestamp("fecha_creacion");

                    if (fecha != null) {
                        s.setFechaCreacion(
                                fecha.toLocalDateTime());
                    }

                    secciones.add(s);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al listar secciones: " + e.getMessage());
        }

        return secciones;
    }

    @Override
    public Seccion buscarPorId(Integer id) throws DaoException {
        if (id == null) {
            return null;
        }

        for (Seccion s : listar()) {
            if (s.getIdSeccion() == id) {
                return s;
            }
        }

        return null;
    }
}

