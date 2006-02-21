package org.metaz.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;


/**
 * Represents data MetaData.
 *
 * @author E.J. Spaans  
 */
public class DateMetaData extends MetaData
{
    private Date value;

    /**
     * Returns the date value
     *
     * @return the date value
     */
    @Override
    public Object getValue()
    {
        return this.value;
    } // end getValue()

    /**
     * Sets the data value
     *
     * @param value the date value
     */
    @Override
    public void setValue(Object value)
    {
        this.value = (Date) value;
    } // end setValue()

    /**
     * Convenience method to set the value to the specified date.
     *
     * @param date the date in format dd-mm-yyyy.
     *
     * @throws ParseException if the supplied date string cannot be parsed.
     */
    public void setDateValue(String date) throws ParseException
    {
        DateFormat df = new SimpleDateFormat();
        this.setValue(df.parse(date));
    } // end setDateValue()
} // end DateMetaData
