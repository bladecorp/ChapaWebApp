package com.sysdt.lock.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utilerias {

	public static Date fechaLocale(Date date){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"),new Locale("es_MX"));
		cal.setTime(date);
		return cal.getTime();
	}
	
	public static Date fechaHoyLocaleEnCeros(){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"),new Locale("es_MX"));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 3);
		return cal.getTime();
	}
	
	public static Date fechaHoyLocale(){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"),new Locale("es_MX"));
		return cal.getTime();
	}
	
	public static Date fechaSumarDias(int dias){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"),new Locale("es_MX"));
		cal.add(Calendar.DAY_OF_YEAR, dias);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 57);
		return cal.getTime();
	}
	
	public static String fechaEnString(Date fecha){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"),new Locale("es_MX"));
		cal.setTime(fecha);
		return campoConCero(cal.get(Calendar.DAY_OF_MONTH))+"/"+campoConCero(cal.get(Calendar.MONTH))+"/"+cal.get(Calendar.YEAR);
	}
	
	public static boolean excedeVigencia(Date fechaVigencia){
		return fechaHoyLocale().after(fechaVigencia);
	}
	
	private static String campoConCero(int valor){
		return valor < 10 ? "0"+valor : valor+"";
	}
	
}
