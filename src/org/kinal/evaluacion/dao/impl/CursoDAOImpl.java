
package org.kinal.evaluacion.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.kinal.evaluacion.dao.CursoDAO;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Curso;
import org.kinal.evaluacion.util.Conexion;

public class CursoDAOImpl implements CursoDAO {

    @Override
    public void insertar(Curso curso) throws DaoException {
        String sql = "{CALL sp_insertar_curso(?,?,?,?,?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setString(1, curso.getCodigo());
                cs.setString(2, curso.getNombre());
                cs.setString(3, curso.getDescripcion());
                cs.setInt(4, curso.getCreditos());
                cs.setInt(5, curso.getHorasSemanales());

                try (ResultSet rs = cs.executeQuery()) {
                    if (rs.next()) {
                        curso.setIdCurso(rs.getInt("id_curso"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al insertar curso: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Curso curso) throws DaoException {
        String sql = "{CALL sp_actualizar_curso(?,?,?,?,?,?,?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, curso.getIdCurso());
                cs.setString(2, curso.getCodigo());
                cs.setString(3, curso.getNombre());
                cs.setString(4, curso.getDescripcion());
                cs.setInt(5, curso.getCreditos());
                cs.setInt(6, curso.getHorasSemanales());
                cs.setString(7, curso.getEstado());

                cs.execute();
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al actualizar curso: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) throws DaoException {
        String sql = "{CALL sp_eliminar_curso(?)}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, id);
                cs.execute();
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al eliminar curso: " + e.getMessage());
        }
    }

    @Override
    public List<Curso> listar() throws DaoException {
        List<Curso> cursos = new ArrayList<>();
        String sql = "{CALL sp_listar_cursos()}";

        try {
            Connection conn = Conexion.getInstancia().getConexion();

            try (CallableStatement cs = conn.prepareCall(sql);
                 ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {
                    Curso curso = new Curso();

                    curso.setIdCurso(rs.getInt("id_curso"));
                    curso.setCodigo(rs.getString("codigo"));
                    curso.setNombre(rs.getString("nombre"));
                    curso.setDescripcion(rs.getString("descripcion"));
                    curso.setCreditos(rs.getInt("creditos"));
                    curso.setHorasSemanales(
                            rs.getInt("horas_semanales"));
                    curso.setEstado(rs.getString("estado"));

                    Timestamp fecha =
                            rs.getTimestamp("fecha_creacion");

                    if (fecha != null) {
                        curso.setFechaCreacion(
                                fecha.toLocalDateTime());
                    }

                    cursos.add(curso);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(
                    "Error al listar cursos: " + e.getMessage());
        }

        return cursos;
    }

    @Override
    public Curso buscarPorId(Integer id) throws DaoException {
        if (id == null) {
            return null;
        }

        for (Curso curso : listar()) {
            if (curso.getIdCurso() == id) {
                return curso;
            }
        }

        return null;
    }
}

