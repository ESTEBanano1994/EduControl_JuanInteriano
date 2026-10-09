
package org.kinal.evaluacion.dao;

import java.util.List;
import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.DetalleMatricula;

public interface DetalleMatriculaDAO {

    List<DetalleMatricula> listarPorMatricula(int idMatricula)
            throws DaoException;

    DetalleMatricula buscarPorId(int idDetalle)
            throws DaoException;
}
