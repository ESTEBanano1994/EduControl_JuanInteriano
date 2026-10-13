
package org.kinal.evaluacion.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.kinal.evaluacion.dao.PrerrequisitoDAO;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Prerrequisito;
import org.kinal.evaluacion.util.Conexion;

public class PrerrequisitoDAOImpl implements PrerrequisitoDAO {

    @Override
    public void insertar(Prerrequisito prerrequisito)
            throws DaoException {

        String sql = "{CALL sp_insertar_prerrequisito(?,?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, prerrequisito.getIdCurso());
                cs.setInt(2,
                        prerrequisito.getIdCursoPrerrequisito());

                try (ResultSet rs = cs.executeQuery()) {
                    if (rs.next()) {
                        prerrequisito.setIdPrerrequisito(
                                rs.getInt("id_prerrequisito"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al insertar prerrequisito: "
                    + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) throws DaoException {
        String sql = "{CALL sp_eliminar_prerrequisito(?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, id);
                cs.execute();
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al eliminar prerrequisito: "
                    + e.getMessage());
        }
    }

    @Override
    public List<Prerrequisito> listar() throws DaoException {
        List<Prerrequisito> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_prerrequisitos()}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql);
                 ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {
                    Prerrequisito p = new Prerrequisito();

                    p.setIdPrerrequisito(
                            rs.getInt("id_prerrequisito"));
                    p.setIdCurso(rs.getInt("id_curso"));
                    p.setIdCursoPrerrequisito(
                            rs.getInt("id_curso_prerrequisito"));

                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al listar prerrequisitos: "
                    + e.getMessage());
        }

        return lista;
    }

    @Override
    public Prerrequisito buscarPorId(Integer id)
            throws DaoException {

        if (id == null) {
            return null;
        }

        for (Prerrequisito p : listar()) {
            if (p.getIdPrerrequisito() == id) {
                return p;
            }
        }

        return null;
    }
}
