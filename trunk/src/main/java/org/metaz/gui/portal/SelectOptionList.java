package org.metaz.gui.portal;

import java.text.Collator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

// @author: Falco Paul
/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public final class SelectOptionList extends ArrayList<SelectOption> {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param options DOCUMENT ME!
   *
   * @return DOCUMENT ME!
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
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String toHtml() {

    return toHtml(this);

  }

  // This method is usefull for obtaining a selected options if
  /**
   * DOCUMENT ME!
   *
   * @param selectedValue DOCUMENT ME!
   *
   * @return DOCUMENT ME!
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

  // This method is usefull for obtaining all selected options if
  /**
   * DOCUMENT ME!
   *
   * @param selectedValues DOCUMENT ME!
   *
   * @return DOCUMENT ME!
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

  // Sets all "selected" values of the list to "value" (true/false)
  /**
   * DOCUMENT ME!
   *
   * @param value DOCUMENT ME!
   */
  public void setSelected(boolean value) {

    Iterator<SelectOption> i = this.iterator();

    while (i.hasNext()) {

      SelectOption option = i.next();

      option.setSelected(value);

    }

  }

}
