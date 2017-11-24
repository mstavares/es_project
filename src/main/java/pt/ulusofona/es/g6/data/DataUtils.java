package pt.ulusofona.es.g6.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DataUtils {

    private DataUtils() {}

    public static Date stringToDate(String data) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(data);
    }
}
