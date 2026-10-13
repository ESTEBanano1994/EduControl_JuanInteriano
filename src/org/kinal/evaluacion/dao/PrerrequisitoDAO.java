
package org.kinal.evaluacion.dao;

import org.kinal.evaluacion.model.Prerrequisito;
import org.kinal.evaluacion.exceptions.DaoException;

public interface PrerrequisitoDAO {

    void insertar(Prerrequisito prerrequisito)
            throws DaoException;

    void eliminar(Integer id) throws DaoException;

    java.util.List<Prerrequisito> listar()
            throws DaoException;

    Prerrequisito buscarPorId(Integer id)
            throws DaoException;
}
