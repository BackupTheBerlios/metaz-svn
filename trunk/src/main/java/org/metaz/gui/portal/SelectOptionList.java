package org.metaz.gui.portal;

import java.text.Collator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Falco Paul
 * @version $Revision$
  */
public final class SelectOptionList extends ArrayList<SelectOption> {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @param options a list of options
   *
   * @return HTML representation of the given options
   */
  public static String toHtml(SelectOptionList options) {

    StringBuffer           html = new StringBuffer();
    Iterator<SelectOption> i = options.iterator();

    while (i.hasNext()) {

      SelectOption option = i.next();

      html.append(option.toHtml() + "\n");

    }

    return html.toString();

  }

  /**
   * Return HTML representation of this object
   *
   * @return HTML representation
   */
  public String toHtml() {

    return toHtml(this);

  }

  /**
   * Find the selected option (use this method for HTML "select one" dropdowns)
   *
   * @param selectedValue selected value (usually obtained from HTML form)
   *
   * @return The selected option
   */
  public SelectOption getOption(String selectedValue) {

    if (selectedValue == null)

      return null;

    Iterator<SelectOption> i = this.iterator();

    while (i.hasNext()) {

      SelectOption option = i.next();

      if (option.getValue().equals(selectedValue))

        return option;

    }

    return null;

  }

  /**
   * Find all selected options (use this method for HTML "select many" blocks)
   *
   * @param selectedValues selected values (usually obtained from HTML form)
   *
   * @return a list of all the selected options
   */
  public SelectOptionList getOptions(String[] selectedValues) {

    if (selectedValues == null)

      return null;

    // Create a collator and sort the input array (for fast binary searching)
    Collator collator = Collator.getInstance();

    Arrays.sort(selectedValues, collator);

    SelectOptionList results = new SelectOptionList();

    Iterator<SelectOption> i = this.iterator();

    while (i.hasNext()) {

      SelectOption option = i.next();

      if (Arrays.binarySearch(selectedValues, option.getValue(), collator) >= 0)
        results.add(option);

    }

    return results;

  }

  /**
   * Sets the "selected" value to a certain state
   *
   * @param value state you which to set
   */
  public void setSelected(boolean value) {

    Iterator<SelectOption> i = this.iterator();

    while (i.hasNext()) {

      SelectOption option = i.next();

      option.setSelected(value);

    }

  }

}
