
package org.kinal.evaluacion.model;

import java.time.LocalDateTime;

public class DetalleMatricula {
        
    private int idDetalle;
    private int idMatricula;
    private int idSeccion;
    private String estado;
    private LocalDateTime fechaRegistro;

    public DetalleMatricula() {
    }

    public DetalleMatricula(int idDetalle, int idMatricula,
            int idSeccion, String estado,
            LocalDateTime fechaRegistro) {
        this.idDetalle = idDetalle;
        this.idMatricula = idMatricula;
        this.idSeccion = idSeccion;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "DetalleMatricula{" +
                "idDetalle=" + idDetalle +
                ", idMatricula=" + idMatricula +
                ", idSeccion=" + idSeccion +
                ", estado='" + estado + '\'' +
                '}';
    }
}
