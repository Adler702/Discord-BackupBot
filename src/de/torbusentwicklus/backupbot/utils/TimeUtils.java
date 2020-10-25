package de.torbusentwicklus.backupbot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    /*
    Tag: dd
    Monat: MM
    Jahr: yyyy
    Stunde: HH
    Minute: mm
    Sekunde: ss
     */

    public String getDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        return time;
    }

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String time = simpleDateFormat.format(new Date());
        return time;
    }

    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String time = simpleDateFormat.format(new Date());
        return time;
    }
}
