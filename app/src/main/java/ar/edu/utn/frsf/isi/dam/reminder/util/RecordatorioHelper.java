package ar.edu.utn.frsf.isi.dam.reminder.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ar.edu.utn.frsf.isi.dam.reminder.model.RecordatorioModel;

public class RecordatorioHelper {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
    private static final String SEPARATOR = "@";

    public static String convertir(RecordatorioModel recordatorio) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(recordatorio.getId());
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(DATE_FORMAT.format(recordatorio.getFecha()));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(recordatorio.getTexto());

        return stringBuilder.toString();
    }

    public static RecordatorioModel parse(String fuente) {
        RecordatorioModel recordatorio = null;

        if (fuente != null) {
            String[] valores = fuente.split(SEPARATOR);

            try {
                recordatorio = new RecordatorioModel(Integer.parseInt(valores[0]), DATE_FORMAT.parse(valores[1]), valores[2]);
            }
            catch (ParseException exception) {
                Log.e("Error al parsear fecha", String.valueOf(exception));
            }
        }

        return recordatorio;
    }
}
