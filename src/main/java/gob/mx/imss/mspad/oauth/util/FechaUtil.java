package gob.mx.imss.mspad.oauth.util;

import gob.mx.imss.mspad.oauth.jwt.filter.JwtRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FechaUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

	public static String fechaHoy() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es_ES"));
		String date = sdf.format(new Date());
		return date;

	}

	public static String fechaCompleta() {
		Locale spanish = new Locale("es", "ES");

		// Fecha actual desglosada:
		Calendar fecha = Calendar.getInstance(spanish);
		int anio = fecha.get(Calendar.YEAR);
		int dia = fecha.get(Calendar.DAY_OF_MONTH);

		String mes2 = fecha.getDisplayName(Calendar.MONTH, Calendar.LONG, spanish);
		LOGGER.info("fechaHoy mes2 : " + mes2);

		String mesPrimera = mes2.substring(0, 1);
		String mayusculaMes = mesPrimera.toUpperCase();
		String demasLetras2 = mes2.substring(1, mes2.length());
		String mesU = mayusculaMes + demasLetras2;
		LOGGER.info("fechaHoy: " + mesU);
		return dia + " de " + mesU + " de " + anio;

	}

	public static void main(String[] args) {
		System.out.println(fechaCompleta());

	}

}
