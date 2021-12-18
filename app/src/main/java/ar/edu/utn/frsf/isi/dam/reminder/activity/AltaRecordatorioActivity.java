package ar.edu.utn.frsf.isi.dam.reminder.activity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ar.edu.utn.frsf.isi.dam.reminder.R;
import ar.edu.utn.frsf.isi.dam.reminder.service.NotificacionService;
import ar.edu.utn.frsf.isi.dam.reminder.service.RecordatorioReceiver;

public class AltaRecordatorioActivity extends AppCompatActivity {
    private TextInputEditText notaTextInputEditText;
    private LinearLayout fechaLinearLayout;
    private LinearLayout horaLinearLayout;
    private TextView fechaSeleccionadaTextView;
    private TextView horaSeleccionadaTextView;
    private Button guardarButton;
    private RecordatorioReceiver recordatorioReceiver;
    private SimpleDateFormat fechaSimpleDateFormat;
    private SimpleDateFormat horaSimpleDateFormat;
    private long fechaSeleccionada;
    private int horaSeleccionada;
    private int minutoSeleccionado;
    private int pendigIntentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_recordatorio);

        createRecodatorioReceiver();
        createNotificationChannel();

        notaTextInputEditText = findViewById(R.id.notaEditTextId);

        fechaSeleccionadaTextView = findViewById(R.id.fechaSeleccionadaTextViewId);
        horaSeleccionadaTextView = findViewById(R.id.horaSeleccionadaTextViewId);

        Locale locale = new Locale("es", "ES");

        fechaSimpleDateFormat = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", locale);
        horaSimpleDateFormat = new SimpleDateFormat("HH 'horas' mm 'minutos'", locale);

        fechaLinearLayout = findViewById(R.id.fechaLinearLayoutId);
        fechaLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Buenos_Aires"));
                calendar.clear();

                if (fechaSeleccionada == 0) {
                    calendar.setTimeInMillis(new Date().getTime());
                }
                else {
                    calendar.setTimeInMillis(fechaSeleccionada);
                }

                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Seleccionar fecha")
                      //  .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setSelection(calendar.getTime().getTime())
                        .build();

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override public void onPositiveButtonClick(Long selection) {
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis(selection);
                        fechaSeleccionada = calendar.getTime().getTime();
                        fechaSeleccionadaTextView.setText(fechaSimpleDateFormat.format(calendar.getTime()));
                    }
                });

                datePicker.show(getSupportFragmentManager(), null);
            }
        });

        horaLinearLayout = findViewById(R.id.horaLinearLayoutId);
        horaLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Buenos_Aires"));
                calendar.clear();

                if (fechaSeleccionada == 0) {
                    calendar.setTimeInMillis(new Date().getTime());
                }
                else {
                    calendar.setTimeInMillis(fechaSeleccionada);
                }

                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setTitleText("Seleccionar hora")
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                        .setMinute(calendar.get(Calendar.MINUTE))
                        .build();

                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String hora = "" + timePicker.getHour() + " horas " + timePicker.getMinute() + " minutos";
                        fechaSeleccionada = calendar.getTime().getTime();
                        horaSeleccionadaTextView.setText(hora);
                    }
                });

                timePicker.show(getSupportFragmentManager(), null);
            }
        });

        guardarButton = findViewById(R.id.guardarButtonId);
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(AltaRecordatorioActivity.this, RecordatorioReceiver.class);
                intent.setAction(RecordatorioReceiver.RECORDATORIO);
                //Bundle b = new Bundle();
                //b.putString("nota", notaTextInputEditText.getText().toString());
                //intent.putExtras(b);
                intent.putExtra("nota", notaTextInputEditText.getText().toString());
                //intent.putExtra("int", 14);
              //  intent = new Intent(RecordatorioReceiver.RECORDATORIO);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(AltaRecordatorioActivity.this, pendigIntentId++, intent, 0);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
              //  calendar.set(Calendar.HOUR_OF_DAY, 14);

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmIntent);
              //  sendBroadcast(new Intent(RecordatorioReceiver.RECORDATORIO));
               // System.out.println("AltaRecordatorioActivity.onClick():"+intent.getStringExtra("nota"));
            }
        });
    }

    private void createRecodatorioReceiver() {
        recordatorioReceiver = new RecordatorioReceiver();
        IntentFilter intentFilter = new IntentFilter(RecordatorioReceiver.RECORDATORIO);
        getApplication().getApplicationContext().registerReceiver(recordatorioReceiver, intentFilter);
        System.out.println("AltaRecordatorioActivity.createRecodatorioReceiver()");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NotificacionService.CHANNEL_ID, NotificacionService.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(NotificacionService.CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            System.out.println("AltaRecordatorioActivity.createNotificationChannel()");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getApplication().getApplicationContext().unregisterReceiver(recordatorioReceiver);
        System.out.println("AltaRecordatorioActivity.onDestroy()");
    }
}