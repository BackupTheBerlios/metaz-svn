package org.metaz.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;


/**
 * Represents hierarchical structured text MetaData.
 *
 * @author E.J. Spaans  
 */
public class HierarchicalStructuredTextMetaData extends MetaData
{
    private List<MetaData> value;

/**
         * Constructor
         */
    public HierarchicalStructuredTextMetaData()
    {
        value = new Vector<MetaData>();
    } // end HierarchicalStructuredTextMetaData()

    /**
     * Returns the hierarchical value
     *
     * @return the hierarchical value
     */
    @Override
    public Object getValue()
    {
        return this.value;
    } // end getValue()

    /**
     * Sets the hierarchical value
     *
     * @param value the hierarchical value
     */
    @Override
    public void setValue(Object value)
    {
        this.value = (List<MetaData>) value;
    } // end setValue()

    /**
     * Returns a pathlike string representation of the hierarchical
     * structured text metadata.<p>A list containing
     * &#123;"grandfather", "father", "son"&#125; will return
     * "&#47;grandfather&#47;father&#47;son"</p>
     *
     * @return the pathlike string
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        Iterator i = value.iterator();

        while (i.hasNext())
        {
            sb.append("/" + (String) ((TextMetaData) i.next()).getValue());
        } // end while

        return sb.toString();
    } // end toString()

    /**
     * Adds a child level to the hierarchical structure.
     *
     * @param child the child level
     */
    public void addChild(MetaData child)
    {
        value.add(child);
    } // end addChild()
} // end HierarchicalStructuredTextMetaData
