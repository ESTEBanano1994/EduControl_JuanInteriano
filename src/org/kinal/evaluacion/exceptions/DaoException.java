package org.kinal.evaluacion.exceptions;

public class DaoException extends Exception {

    public DaoException(String mensaje) {
        super(mensaje);
    }

    public DaoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}