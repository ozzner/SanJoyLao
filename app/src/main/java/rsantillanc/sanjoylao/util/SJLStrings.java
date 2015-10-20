package rsantillanc.sanjoylao.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by RenzoD on 24/06/2015.
 */
public class SJLStrings {

    public static final String FORMAT_MILES = "##,##0.00";

    /**
     * @param input  Cadena a formatear
     * @param format constante para indicar el formato de salida.
     */
    public static String format(Double input, String format) {
        DecimalFormat myFormatter = new DecimalFormat(format, DecimalFormatSymbols.getInstance(Locale.US));
        return myFormatter.format(input);
    }

}
