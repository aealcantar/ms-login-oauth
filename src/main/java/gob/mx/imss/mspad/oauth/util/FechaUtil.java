package gob.mx.imss.mspad.oauth.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gob.mx.imss.mspad.oauth.filter.CustomFilter;

public class FechaUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomFilter.class);

	public static String fechaHoy() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",new Locale("es_ES"));	
		String date = sdf.format(new Date());
		return date;

	}
	
	public static String fechaCompleta() {
		Locale spanish = new Locale("es", "ES");

		//Fecha actual desglosada:
        Calendar fecha = Calendar.getInstance(spanish);
        int anio = fecha.get(Calendar.YEAR);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);


        
        String mes2 = fecha.getDisplayName(Calendar.MONTH, Calendar.LONG, spanish);
//        test
        DateTimeFormatter fecha2 = DateTimeFormatter.ofPattern("MMMM");
        
       String  stringMes =fecha2.format(LocalDateTime.now());
        
        String mesNombre = stringMes.toUpperCase().charAt(0) + stringMes.substring(1, stringMes.length()).toLowerCase();
        
		LOGGER.info("FECHA2"+fecha2.format(LocalDateTime.now()));

//      test
		
		
	
		return dia + " de "+ mesNombre +" de "+anio;
		
	}
	
	public static void main(String[] args) {
	String fecha = 	fechaCompleta();
	
	System.out.println(fecha);
	
//	DateTimeFormatter fecha2 = DateTimeFormatter.ofPattern("MMMM");
//    System.out.println("-----: " + fecha2.format(LocalDateTime.now()));
//    
//	LOGGER.info("FECHA2"+fecha2.format(LocalDateTime.now()));
	}

}
