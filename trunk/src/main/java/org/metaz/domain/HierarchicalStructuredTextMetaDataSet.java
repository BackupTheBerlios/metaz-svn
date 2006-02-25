package org.metaz.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a set of hierarchical structured text metadata
 * 
 * @author Sammy Dalewyn
 * @version 1.0
 */
public class HierarchicalStructuredTextMetaDataSet extends MetaData {
	private Set<String> value;

	/**
	 * Constructor
	 */
	public HierarchicalStructuredTextMetaDataSet() {
		value = new HashSet<String>();
	} // end HierarchicalStructuredTextMetaDataSet()

	/**
	 * Returns the set value
	 * 
	 * @return the set value
	 */
	@Override
	public Object getValue() {
		return this.value;
	} // end getValue()

	/**
	 * Sets the set value
	 * 
	 * @param value
	 *            the set value
	 */
	@Override
	public void setValue(Object value) {
		this.value = (Set<String>) value;
	} // end setValue()

	/**
	 * Adds a hierarchical metadata structure to the set.
	 * 
	 * @param hierarchy
	 *            the hierarchical metadata
	 */
	public void addHierarchy(String hierarchy) {
		value.add(hierarchy);
	} // end addHierarchy()
} // end HierarchicalStructuredTextMetaDataSet
