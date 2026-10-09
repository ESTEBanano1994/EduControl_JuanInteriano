
package org.kinal.evaluacion.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.kinal.evaluacion.dao.MatriculaDAO;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Matricula;
import org.kinal.evaluacion.util.Conexion;

public class MatriculaDAOImpl implements MatriculaDAO {

    @Override
    public void insertar(Matricula matricula) throws DaoException {
        String sql = "{CALL sp_insertar_matricula(?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, matricula.getIdEstudiante());

                try (ResultSet rs = cs.executeQuery()) {
                    if (rs.next()) {
                        matricula.setIdMatricula(
                                rs.getInt("id_matricula"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al insertar matrícula: " + e.getMessage());
        }
    }

    @Override
    public void actualizarEstado(int idMatricula, String estado)
            throws DaoException {

        String sql =
                "{CALL sp_actualizar_estado_matricula(?,?,?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, idMatricula);
                cs.setString(2, estado);
                cs.setNull(3, java.sql.Types.VARCHAR);

                cs.execute();
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al actualizar matrícula: "
                    + e.getMessage());
        }
    }

    @Override
    public void eliminar(int idMatricula) throws DaoException {
        String sql = "{CALL sp_eliminar_matricula(?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, idMatricula);
                cs.execute();
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al eliminar matrícula: " + e.getMessage());
        }
    }

    @Override
    public List<Matricula> listar() throws DaoException {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = "{CALL sp_listar_matriculas()}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql);
                 ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {
                    Matricula m = new Matricula();

                    m.setIdMatricula(rs.getInt("id_matricula"));
                    m.setIdEstudiante(
                            rs.getInt("id_estudiante"));
                    m.setEstado(rs.getString("estado"));
                    m.setObservaciones(
                            rs.getString("observaciones"));

                    Timestamp prematricula =
                            rs.getTimestamp("fecha_prematricula");

                    if (prematricula != null) {
                        m.setFechaPrematricula(
                                prematricula.toLocalDateTime());
                    }

                    Timestamp fechaMatricula =
                            rs.getTimestamp("fecha_matricula");

                    if (fechaMatricula != null) {
                        m.setFechaMatricula(
                                fechaMatricula.toLocalDateTime());
                    }

                    matriculas.add(m);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al listar matrículas: " + e.getMessage());
        }

        return matriculas;
    }

    @Override
    public Matricula buscarPorId(int idMatricula)
            throws DaoException {

        for (Matricula m : listar()) {
            if (m.getIdMatricula() == idMatricula) {
                return m;
            }
        }

        return null;
    }

    @Override
    public void prematricularSeccion(
            int idEstudiante, int idSeccion)
            throws DaoException {

        String sql = "{CALL sp_prematricular_seccion(?,?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, idEstudiante);
                cs.setInt(2, idSeccion);
                cs.execute();
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al prematricular sección: "
                    + e.getMessage());
        }
    }

    @Override
    public void retirarSeccion(int idDetalle)
            throws DaoException {

        String sql = "{CALL sp_retirar_seccion(?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, idDetalle);
                cs.execute();
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al retirar sección: " + e.getMessage());
        }
    }
}
