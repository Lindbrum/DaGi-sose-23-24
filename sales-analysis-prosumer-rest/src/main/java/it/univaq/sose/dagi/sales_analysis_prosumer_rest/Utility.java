package it.univaq.sose.dagi.sales_analysis_prosumer_rest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class Utility {
  //It is designed to convert an XMLGregorianCalendar object to a LocalDateTime object. 
  //If the supplied XMLGregorianCalendar object is not null, the method transforms it into a GregorianCalendar,
  //which is then converted to ZonedDateTime. Next, the ZonedDateTime is transformed into a LocalDateTime using the system time zone.
  //If the XMLGregorianCalendar object is null, the method returns null.
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
  
  //This method is used to convert a LocalDateTime into an XMLGregorianCalendar. 
  //It starts by converting the LocalDateTime to an ISO-8601 string.
  //If seconds are zero, appends ":00" to the string to ensure the format is correct for XMLGregorianCalendar.
  //Then, use DatatypeFactory to create a new instance of XMLGregorianCalendar from the ISO-8601 string.
  //If there is an exception during creation, it is printed on the stack trace.  
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