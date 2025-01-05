package it.univaq.sose.dagi.organizer_client;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import de.vandermeer.asciitable.AT_ColumnWidthCalculator;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import de.vandermeer.asciitable.CWC_LongestLine;

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
  /**
   * Create an ASCII table using a ColumnWidtchCalculator object
   * @param textAlignment
   * @param cwc
   * @return
   */
  public static AsciiTable createAsciiTable(AT_ColumnWidthCalculator cwc) {
	  AsciiTable at = new AsciiTable();
	  at.getRenderer().setCWC(cwc);
	  
	  return at;
  }
  
  /**
   * Create an ASCII table using fixed widths for the columns
   * @param textAlignment
   * @param widths
   * @return
   */
  public static AsciiTable createAsciiTable(int... widths) {
	  AsciiTable at = new AsciiTable();		  
	  CWC_FixedWidth cwc = new CWC_FixedWidth();
	  for (int i = 0; i < widths.length; i++) {
		  cwc.add(widths[i]);  
	  }
	  at.getRenderer().setCWC(cwc);
	  return at;
  }
  
  /**
   * Create an ASCII table using responsive sizes for the columns with minimum and maximum widths.
   * @param textAlignment
   * @param widths
   * @return
   */
  public static AsciiTable createAsciiTable(int[] minWidths, int[] maxWidths) {
	  if(maxWidths.length > minWidths.length) {
		  throw new RuntimeException("You are missing some max width values for this table. Falling back to use the last provided max width value!");
	  }
	  AsciiTable at = new AsciiTable();		  
	  CWC_LongestLine cwc = new CWC_LongestLine();
	  for (int i = 0; i < minWidths.length; i++) {
		  int max = i < maxWidths.length ? maxWidths[i] : maxWidths[maxWidths.length - 1]; //fallback to use the last provided value if values are missing.
		  cwc.add(minWidths[i], max);
	  }
	  at.getRenderer().setCWC(cwc);
	  return at;
  }
}