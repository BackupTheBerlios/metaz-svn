package org.metaz.domain;

/**
 * Represents HTML text metadata
 */
public class HtmlTextMetaData extends MetaData{
    
    private String value;
    
    @Override
    public Object getValue() {
            return this.value;
    }

    @Override
    public void setValue(Object value) {
            this.value = (String) value;

    }
}
