package ar.edu.utn.frsf.isi.dam.reminder.repository;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.reminder.model.RecordatorioModel;

public interface RecordatorioDataSource {
    interface GuardarRecordatorioCallback {
        void resultado(final boolean exito);
    }

    interface RecuperarRecordatorioCallback {
        void resultado(final boolean exito, final List<RecordatorioModel> recordatorios);
    }

    void guardarRecordatorio(final RecordatorioModel recordatorio, final GuardarRecordatorioCallback callback);
    void recuperarRecordatorios(final RecuperarRecordatorioCallback callback);
}
