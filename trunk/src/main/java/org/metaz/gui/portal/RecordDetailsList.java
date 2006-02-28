package org.metaz.gui.portal;

import org.metaz.domain.Record;

import java.net.URL;

import java.util.ArrayList;

// @author: Falco Paul
/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public final class RecordDetailsList extends ArrayList<RecordDetail> {

  //~ Constructors -----------------------------------------------------------------------------------------------------

  // empty constructor
  public RecordDetailsList() {

  }

  // Constructor based on a Record
  public RecordDetailsList(Record record) {

    // Sample
    add(new RecordDetail("Naam", "Leerobject 1"));
    add(new RecordDetail("Producttype", "Video"));
    add(new RecordDetail("Doelgroep", "Leraren"));
    add(new RecordDetail("Aanmaakdatum", new java.util.Date().toString()));
    add(new RecordDetail("Beveiligd object", "Ja"));
    add(new RecordDetail("URL", "http://www.google.nl"));

  }

}
