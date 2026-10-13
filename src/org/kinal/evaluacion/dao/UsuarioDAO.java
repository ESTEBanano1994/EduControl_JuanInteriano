
package org.kinal.evaluacion.dao;

import org.kinal.evaluacion.exceptions.DaoException;
import org.kinal.evaluacion.model.Usuario;

/**
 * Interfaz DAO para gestionar los usuarios
 * del sistema EduControl.
 *
 * @author Juan Esteban Interiano Riera
 */
public interface UsuarioDAO extends CrudDAO<Usuario, Integer> {

    /**
     * Busca un usuario activo por su nombre de usuario.
     *
     * Se utiliza durante el inicio de sesion
     * para obtener los datos de autenticacion.
     *
     * @param username nombre de usuario
     * @return usuario encontrado o null si no existe
     * @throws DaoException si ocurre un error en la BD
     */
    Usuario buscarPorUsername(String username)
            throws DaoException;
}
