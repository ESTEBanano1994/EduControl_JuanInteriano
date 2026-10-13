
package org.kinal.evaluacion.dao;

import java.util.List;
import org.kinal.evaluacion.exceptions.DaoException;

public interface CrudDAO<T, ID> {

    void insertar(T entidad) throws DaoException;

    void actualizar(T entidad) throws DaoException;

    void eliminar(ID id) throws DaoException;

    List<T> listar() throws DaoException;

    T buscarPorId(ID id) throws DaoException;
}
