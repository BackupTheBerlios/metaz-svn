package org.metaz.domain;

/**
 * Represents HTML text metadata
 */
public class HtmlTextMetaData extends MetaData
{
    private String value;

    /**
     * Returns the string value
     *
     * @return the string value
     */
    @Override
    public Object getValue()
    {
        return this.value;
    } // end getValue()

    /**
     * Sets the string value
     *
     * @param value the string value
     */
    @Override
    public void setValue(Object value)
    {
        this.value = (String) value;
    } // end setValue()
} // end HtmlTextMetaData
