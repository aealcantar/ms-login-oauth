package gob.mx.imss.mspad.oauth.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FechaUtil {
	
	
	public static String fechaHoy() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");	
		String date = sdf.format(new Date());
		return date;

	}
	
	public static String fechaCompleta() {
		
		//Fecha actual desglosada:
        Calendar fecha = Calendar.getInstance();
        int anio = fecha.get(Calendar.YEAR);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        
        String mes2 = fecha.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		return dia + " de "+ mes2 +" de "+anio;
		
	}

}
