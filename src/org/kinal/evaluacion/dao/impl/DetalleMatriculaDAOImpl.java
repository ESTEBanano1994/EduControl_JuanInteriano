
package org.kinal.evaluacion.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.kinal.evaluacion.dao.DetalleMatriculaDAO;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.DetalleMatricula;
import org.kinal.evaluacion.util.Conexion;

public class DetalleMatriculaDAOImpl
        implements DetalleMatriculaDAO {

    @Override
    public List<DetalleMatricula> listarPorMatricula(int idMatricula)
            throws DaoException {

        List<DetalleMatricula> detalles = new ArrayList<>();

        String sql = "{CALL sp_listar_detalle_matricula(?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, idMatricula);

                try (ResultSet rs = cs.executeQuery()) {
                    while (rs.next()) {
                        detalles.add(mapearDetalle(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al listar detalles de matrícula: "
                    + e.getMessage());
        }

        return detalles;
    }

    @Override
    public DetalleMatricula buscarPorId(int idDetalle)
            throws DaoException {

        String sql = "{CALL sp_buscar_detalle_por_id(?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, idDetalle);

                try (ResultSet rs = cs.executeQuery()) {
                    if (rs.next()) {
                        return mapearDetalle(rs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al buscar detalle: " + e.getMessage());
        }

        return null;
    }

    private DetalleMatricula mapearDetalle(ResultSet rs)
            throws SQLException {

        DetalleMatricula detalle = new DetalleMatricula();

        detalle.setIdDetalle(rs.getInt("id_detalle"));
        detalle.setIdMatricula(rs.getInt("id_matricula"));
        detalle.setIdSeccion(rs.getInt("id_seccion"));
        detalle.setEstado(rs.getString("estado"));

        Timestamp fecha = rs.getTimestamp("fecha_registro");

        if (fecha != null) {
            detalle.setFechaRegistro(
                    fecha.toLocalDateTime());
        }

        return detalle;
    }
}
