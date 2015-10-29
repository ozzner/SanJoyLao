package rsantillanc.sanjoylao.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by RenzoD on 24/06/2015.
 */
public class SJLStrings {

    public static final String FORMAT_MILES_EN = "##,##0.00";
    public static final String FORMAT_MILES_ES = "##.##0,00";
    public static final String PARSE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String MAIN_DATE_FORMAT = "yyyy/MM/dd'T'HH:mm:ssZ";



    /**
     * @param input  Cadena a formatear
     * @param format constante para indicar el formato de salida.
     */
    public static String format(Double input, String format) {
        DecimalFormat myFormatter = new DecimalFormat(format, DecimalFormatSymbols.getInstance(Locale.US));
        return myFormatter.format(input);
    }


    /**
     * Permite obtener el formato apropiado para dar formato a numeros,
     * teniendo en cuenta si es en español o ingles.
     * @param input numero a formatear.
     * @return String formateado.
     */
    public static String format(Double input){
        DecimalFormat myFormatter;
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        if (Android.getLocalLanguage().contains("es")){
            dfs.setDecimalSeparator(Const.CHAR_COMMA);
            dfs.setPerMill(Const.CHAR_DOT);
            myFormatter = new DecimalFormat(FORMAT_MILES_ES,DecimalFormatSymbols.getInstance(Locale.getDefault()));
        }
        else{
            dfs.setDecimalSeparator(Const.CHAR_DOT);
            dfs.setPerMill(Const.CHAR_COMMA);
            myFormatter = new DecimalFormat(FORMAT_MILES_EN,DecimalFormatSymbols.getInstance(Locale.US));
        }

        myFormatter.setDecimalFormatSymbols(dfs);
        String output = myFormatter.format(input);

        return output;
    }

}
