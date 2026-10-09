

package org.kinal.evaluacion.model;

public class Prerrequisito {

    private int idPrerrequisito;
    private int idCurso;
    private int idCursoPrerrequisito;

    public Prerrequisito() {
    }

    public Prerrequisito(int idPrerrequisito,
            int idCurso, int idCursoPrerrequisito) {
        this.idPrerrequisito = idPrerrequisito;
        this.idCurso = idCurso;
        this.idCursoPrerrequisito = idCursoPrerrequisito;
    }

    public int getIdPrerrequisito() {
        return idPrerrequisito;
    }

    public void setIdPrerrequisito(int idPrerrequisito) {
        this.idPrerrequisito = idPrerrequisito;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdCursoPrerrequisito() {
        return idCursoPrerrequisito;
    }

    public void setIdCursoPrerrequisito(int idCursoPrerrequisito) {
        this.idCursoPrerrequisito = idCursoPrerrequisito;
    }

    @Override
    public String toString() {
        return "Prerrequisito{" +
                "idPrerrequisito=" + idPrerrequisito +
                ", idCurso=" + idCurso +
                ", idCursoPrerrequisito=" + idCursoPrerrequisito +
                '}';
    }
}
