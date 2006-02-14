package org.metaz.gui.portal;

// @author: Falco Paul
// Just an example to show the workings of the displaytag library
// Real implementation will be different

public final class Result {

  private String name;
  private String productType;
  private String description;

  public Result(String name, String productType, String description) {
  
    this.name = name;
    this.productType = productType;
    this.description = description;
    
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getProductType() {
    return productType;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}

