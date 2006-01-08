package org.metaz.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author E.J. Spaans
 * 
 * Represents data MetaData.
 *
 */
public class DateMetaData extends MetaData {
	
	private Date value;

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (Date) value;
	}
	
	/**
	 * Convenience method to set the value to
	 * the specified date.
	 * @param date the date in format dd-mm-yyyy.
	 * @throws ParseException if the supplied date string
	 * cannot be parsed.
	 */
	public void setDateValue(String date) throws ParseException{
		DateFormat df = new SimpleDateFormat();
		this.setValue(df.parse(date));
	}

}
