package ar.edu.utn.frsf.isi.dam.reminder.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.utn.frsf.isi.dam.reminder.model.RecordatorioModel;
import ar.edu.utn.frsf.isi.dam.reminder.util.RecordatorioHelper;

public class RecordatorioPreferencesDataSource implements RecordatorioDataSource {
    private final SharedPreferences sharedPreferences;
    private final String claveIdRecordatorio = "id";
    private final String claveSetRecordatorio = "recordatorios";

    RecordatorioPreferencesDataSource(final Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void guardarRecordatorio(RecordatorioModel recordatorio, GuardarRecordatorioCallback callback) {
        HashSet<String> recordatorios = (HashSet<String>) sharedPreferences.getStringSet(claveSetRecordatorio, new HashSet<String>());

        if (recordatorio.getId() <= 0) {
            int id = sharedPreferences.getInt(claveIdRecordatorio, 1);
            sharedPreferences.edit().putInt(claveIdRecordatorio, id + 1);

            recordatorio.setId(id);
        }
        else {
            Optional<String> optional = recordatorios.stream().filter(r -> recordatorio.getId() == RecordatorioHelper.parse(r).getId()).findFirst();

            if (optional.isPresent())
                recordatorios.remove(optional.get());
        }

        recordatorios.add(RecordatorioHelper.convertir(recordatorio));
        sharedPreferences.edit().putStringSet(claveSetRecordatorio, recordatorios);
        callback.resultado(sharedPreferences.edit().commit());
    }

    @Override
    public void recuperarRecordatorios(RecuperarRecordatorioCallback callback) {
        HashSet<String> recordatorios = (HashSet<String>) sharedPreferences.getStringSet(claveSetRecordatorio, new HashSet<String>());
        callback.resultado(true, recordatorios.stream().map(r -> RecordatorioHelper.parse(r)).collect(Collectors.toList()));
    }
}
