package ar.edu.utn.frsf.isi.dam.reminder.service;

public class NotificacionService {
    public static final String CHANNEL_ID = "ar.edu.utn.frsf.utn.isi.dam.reminder.CHANNEL_ID";
    public static final String CHANNEL_NAME = "reminder";
    public static final String CHANNEL_DESCRIPTION = "Canal de notificación";

    private static int ID = 1;

    public static int getNotificacionId() {
        return ID++;
    }
}
