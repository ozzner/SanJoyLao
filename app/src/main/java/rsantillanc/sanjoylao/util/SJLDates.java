package rsantillanc.sanjoylao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by RenzoD on 28/10/2015.
 */
public class SJLDates {

    /**
     * Valor: {@value} => Dias
     */
    public static final int DIAS = 4;
    /**
     * Valor: {@value} => Horas
     */
    public static final int HORAS = 3;
    /**
     * Valor: {@value} => Minutos
     */
    public static final int MINUTOS = 2;
    /**
     * Valor: {@value} => Segundos
     */
    public static final int SEGUNDOS = 1;
    /**
     * Formato: {@value}
     */
    public static final String FORMAT_DATE_FULL = "yyyy-MM-dd HH:mm:ss:SSS";

    /**
     * Formato: {@value}
     */
    public static final String FORMAT_DATE = "dd-MMM HH:mm";

    /**
     * Formato: {@value}
     */
    public static final String FORMAT_DATE_2 = "dd/MMM HH:mm";

    /**
     * Formato: {@value}
     */
    public static final String FORMAT_DATE_GENERAL = "dd/MMM/yyyy HH:mm";
    /**
     * Formato: {@value}
     */
    public static final String FORMAT_DATE_MM = "yyyy-MM-dd";
    /**
     * Formato: {@value}
     */
    public static final String FORMAT_DATE_MMM = "yyyy-MMM-dd";
    /**
     * Formato: {@value}
     */
    public static final String FORMAT_TIME_GENERAL = "HH:mm:ss";
    /**
     * Formato: {@value}
     */
    public static final String FORMAT_TIME_SHORT = "HH:mm";


    public static String getCurrentDataTime(String Myformat) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Myformat);
        String strDate = sdf.format(c.getTime());

        return strDate;
    }




    public static String customDateConverter(String Mydate, String MyformatIn, String MyformatOut) {

        SimpleDateFormat readDate = new SimpleDateFormat(MyformatIn);
        readDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = null;
        try {
            date = readDate.parse(Mydate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat writeDate = new SimpleDateFormat(MyformatOut);
        writeDate.setTimeZone(TimeZone.getDefault());
        String s = writeDate.format(date);

        return s;
    }

    public static final SimpleDateFormat SDF1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    public static final SimpleDateFormat SDF2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat SDF3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat SDF4 = new SimpleDateFormat("MM/dd HH:mm", Locale.getDefault());
    public static final SimpleDateFormat SDF5= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

}
