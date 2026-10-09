
package org.kinal.evaluacion.model;

import java.time.LocalDateTime;

public class Docente {

    private int idDocente;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String especialidad;
    private String estado;
    private LocalDateTime fechaRegistro;

    public Docente() {
    }

    public Docente(int idDocente, String nombres,
            String apellidos, String correo, String telefono,
            String especialidad, String estado,
            LocalDateTime fechaRegistro) {
        this.idDocente = idDocente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
        return "Docente{" +
                "idDocente=" + idDocente +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}

