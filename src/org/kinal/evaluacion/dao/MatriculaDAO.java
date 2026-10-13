
package org.kinal.evaluacion.dao;

import java.util.List;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Matricula;

public interface MatriculaDAO {

    void insertar(Matricula matricula) throws DaoException;

    void actualizarEstado(int idMatricula, String estado)
            throws DaoException;

    void eliminar(int idMatricula) throws DaoException;

    List<Matricula> listar() throws DaoException;

    Matricula buscarPorId(int idMatricula)
            throws DaoException;

    void prematricularSeccion(int idEstudiante, int idSeccion)
            throws DaoException;

    void retirarSeccion(int idDetalle)
            throws DaoException;
}
