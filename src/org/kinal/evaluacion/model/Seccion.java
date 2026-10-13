
package org.kinal.evaluacion.model;

import java.time.LocalDateTime;

public class Seccion {

    private int idSeccion;
    private int idCurso;
    private int idDocente;
    private String codigoSeccion;
    private String periodo;
    private int anio;
    private String horario;
    private String aula;
    private int cupoMaximo;
    private int cupoDisponible;
    private String estado;
    private LocalDateTime fechaCreacion;

    public Seccion() {
    }

    public Seccion(int idSeccion, int idCurso, int idDocente,
            String codigoSeccion, String periodo, int anio,
            String horario, String aula, int cupoMaximo,
            int cupoDisponible, String estado,
            LocalDateTime fechaCreacion) {
        this.idSeccion = idSeccion;
        this.idCurso = idCurso;
        this.idDocente = idDocente;
        this.codigoSeccion = codigoSeccion;
        this.periodo = periodo;
        this.anio = anio;
        this.horario = horario;
        this.aula = aula;
        this.cupoMaximo = cupoMaximo;
        this.cupoDisponible = cupoDisponible;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public String getCodigoSeccion() {
        return codigoSeccion;
    }

    public void setCodigoSeccion(String codigoSeccion) {
        this.codigoSeccion = codigoSeccion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public int getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(int cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Seccion{" +
                "idSeccion=" + idSeccion +
                ", idCurso=" + idCurso +
                ", codigoSeccion='" + codigoSeccion + '\'' +
                ", cupoMaximo=" + cupoMaximo +
                ", cupoDisponible=" + cupoDisponible +
                ", estado='" + estado + '\'' +
                '}';
    }
}

