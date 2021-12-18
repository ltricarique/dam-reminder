package ar.edu.utn.frsf.isi.dam.reminder.model;

import java.util.Date;
import java.util.Objects;

public class RecordatorioModel {
    private int id;
    private Date fecha;
    private String texto;

    public RecordatorioModel(final Date fecha, final String texto) {
        this.fecha = fecha;
        this.texto = texto;
    }

    public RecordatorioModel(int id, Date fecha, String texto) {
        this.id = id;
        this.fecha = fecha;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(final String texto) {
        this.texto = texto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(final Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !getClass().equals(other.getClass())) {
            return false;
        }

        final RecordatorioModel that = (RecordatorioModel) other;

        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
