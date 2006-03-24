package org.metaz.util;

/**
 * @author Erik-Jan Spaans
 * 
 * Helper/utility class to parse the value of the method
 * {@link org.metaz.domain.HierarchicalStructuredTextMetaData#getValue()}.
 * Currently the return type of this method is String. It is the intention
 * to make this a 'richer' type. Until that has been implemented, this helper
 * can be used to 'hide' the raw nature of the getValue method.
 * 
 * Currently this helper class is only needed by the web client
 * 
 *
 */
public class HierarchicalStructuredMetaDataValueParser {
	
	/**
	 * Gets the value of the lowest hierarchical level of the supplied
	 * hierarchy. This means the value after the last '/'.
	 * @param fullHierarchy
	 * @return
	 */
	public static String getLowestHierarchicalLevelValue(String fullHierarchy){
		String lowestLevelValue = null;
		if(fullHierarchy != null){
			int lowestLevelSeparator = fullHierarchy.lastIndexOf("/");
			// add one to lowestLevelSeparator to exclude the '/'
			lowestLevelValue = fullHierarchy.substring(lowestLevelSeparator + 1);
		}
		return lowestLevelValue;
	}
}
