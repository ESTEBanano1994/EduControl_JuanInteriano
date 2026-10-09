
package org.kinal.evaluacion.model;

import java.time.LocalDateTime;

public class Matricula {

    private int idMatricula;
    private int idEstudiante;
    private LocalDateTime fechaPrematricula;
    private LocalDateTime fechaMatricula;
    private String estado;
    private String observaciones;

    public Matricula() {
    }

    public Matricula(int idMatricula, int idEstudiante,
            LocalDateTime fechaPrematricula,
            LocalDateTime fechaMatricula,
            String estado, String observaciones) {
        this.idMatricula = idMatricula;
        this.idEstudiante = idEstudiante;
        this.fechaPrematricula = fechaPrematricula;
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public LocalDateTime getFechaPrematricula() {
        return fechaPrematricula;
    }

    public void setFechaPrematricula(LocalDateTime fechaPrematricula) {
        this.fechaPrematricula = fechaPrematricula;
    }

    public LocalDateTime getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDateTime fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "idMatricula=" + idMatricula +
                ", idEstudiante=" + idEstudiante +
                ", estado='" + estado + '\'' +
                ", fechaMatricula=" + fechaMatricula +
                '}';
    }
}
