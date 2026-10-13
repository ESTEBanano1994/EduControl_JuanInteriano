
package org.kinal.evaluacion.dao;

import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Estudiante;

public interface EstudianteDAO
        extends CrudDAO<Estudiante, Integer> {

    Estudiante buscarPorIdUsuario(int idUsuario)
            throws DaoException;
}
