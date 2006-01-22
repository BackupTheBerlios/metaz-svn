package org.metaz.domain;

import java.util.HashSet;
import java.util.Set;

public class HierarchicalStructuredTextMetaDataSet extends MetaData {
    private Set<HierarchicalStructuredTextMetaData> value;
    
    /**
     * Constructor
     */
    public HierarchicalStructuredTextMetaDataSet() {
        value = new HashSet<HierarchicalStructuredTextMetaData>();
    }
    @Override
    public Object getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(Object value) {
        this.value = (Set<HierarchicalStructuredTextMetaData>) value;
    }
    
    /**
     * Adds a hierarchical metadata structure to the set.
     * @param hierarchy the hierarchical metadata
     */
    public void addHierarchy(HierarchicalStructuredTextMetaData hierarchy) {
        value.add(hierarchy);
    }
}
