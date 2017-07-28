package mx.gob.sat.v33.binding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeCustomBinder {
	public static Date parseDateTime(String s) {
	    try {
	      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	      return formatter.parse(s);
	    } catch (ParseException e) {
	      throw new RuntimeException(e);
	    }
	  }
	  
	  public static String printDateTime(Date dt) {
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    return formatter.format(dt);
	  }
}
