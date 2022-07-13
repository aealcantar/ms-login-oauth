package gob.mx.imss.mspad.oauth.util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
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
		LOGGER.info("mes2"+mes2);

//        test
        DateTimeFormatter fecha2 = DateTimeFormatter.ofPattern("MMMM");
        
       String  stringMes =fecha2.format(LocalDateTime.now());
        
        String mesNombre = stringMes.toUpperCase().charAt(0) + stringMes.substring(1, stringMes.length()).toLowerCase();
        
		LOGGER.info("FECHA2"+fecha2.format(LocalDateTime.now()));
		
		// Obtienes el mes actual
				Month mes = LocalDate.now().getMonth();
				int dia1 = LocalDate.now().getDayOfMonth();
				int anio1 = LocalDate.now().getYear();

				// Obtienes el nombre del mes
				String nombre = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
				
				
				// Convierte a mayúscula la primera letra del nombre.
				String primeraLetra = nombre.substring(0,1);
				String mayuscula = primeraLetra.toUpperCase();
				String demasLetras = nombre.substring(1, nombre.length());
				nombre = mayuscula + demasLetras;

				LOGGER.info("FECHA3"+dia1 + " de "+ mes +" de "+anio1);

//      test
		
		
	
		return dia + " de "+ mesNombre +" de "+anio;
		
	}
	
	public static void main(String[] args) {
		
		System.out.println(fechaCompleta());
		// Obtienes el mes actual
		Month mes = LocalDate.now().getMonth();
		int dia = LocalDate.now().getDayOfMonth();
		int anio = LocalDate.now().getYear();

		// Obtienes el nombre del mes
		String nombre = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
		
		
		// Convierte a mayúscula la primera letra del nombre.
		String primeraLetra = nombre.substring(0,1);
		String mayuscula = primeraLetra.toUpperCase();
		String demasLetras = nombre.substring(1, nombre.length());
		nombre = mayuscula + demasLetras;

		System.out.println(dia + " de "+ mes +" de "+anio);
		
		 
	}

}
