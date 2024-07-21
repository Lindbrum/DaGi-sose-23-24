package it.univaq.sose.dagi.sales_analysis_prosumer_rest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class Utility {
  public static LocalDateTime toLocalDateTime(
          XMLGregorianCalendar calendar) {
      if (calendar != null) {
          ZonedDateTime zonedDateTime = calendar.toGregorianCalendar()
                  .toZonedDateTime();
          return ZonedDateTime.ofInstant(zonedDateTime.toInstant(),
                  ZoneId.systemDefault()).toLocalDateTime();
      }
      return null;
  }
  
  public static XMLGregorianCalendar toXMLCalendar(LocalDateTime localDateTime) {
	  String iso = localDateTime.toString();
	  if(localDateTime.getSecond() == 0) {
		   iso = iso + ":00";
	  }
	  XMLGregorianCalendar xml = null;
	  try {
		xml = DatatypeFactory.newInstance().newXMLGregorianCalendar(iso);
	} catch (DatatypeConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return xml;
  }
}