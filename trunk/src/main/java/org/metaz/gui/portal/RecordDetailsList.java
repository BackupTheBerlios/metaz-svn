package org.metaz.gui.portal;

import java.net.URL;

import java.util.ArrayList;
import org.metaz.domain.Record;

// @author: Falco Paul

// Represents the "Strinigfied" user readable details for some record
// Mainly used to display the record as a table in a web page

public final class RecordDetailsList extends ArrayList <RecordDetail> {

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

