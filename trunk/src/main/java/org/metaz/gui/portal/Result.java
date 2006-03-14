package org.metaz.gui.portal;

/**
 * This class represents EXACTLY ONE search result
 * A search result is defined of all the information that represents a match
 *
 * @author Falco Paul
 * @version $Revision$
  */
public final class Result {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String name;
  private String productType;
  private String description;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Constructor that takes the most common parameters
   *
   * @param setName Name
   * @param setProductType Product type
   * @param setDescription Description
   */
  public Result(String setName, String setProductType, String setDescription) {

    this.name = setName;
    this.productType = setProductType;
    this.description = setDescription;

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Name setter
   *
   * @param setName Name
   */
  public void setName(String setName) {

    this.name = setName;

  }

  /**
   * Name getter
   *
   * @return Name
   */
  public String getName() {

    return name;

  }

  /**
   * Product type setter
   *
   * @param setProductType Product type
   */
  public void setProductType(String setProductType) {

    this.productType = setProductType;

  }

  /**
   * Product type getter
   *
   * @return Product type
   */
  public String getProductType() {

    return productType;

  }

  /**
   * Description setter
   *
   * @param setDescription Description
   */
  public void setDescription(String setDescription) {

    this.description = setDescription;

  }

  /**
   * Description getter
   *
   * @return Description
   */
  public String getDescription() {

    return description;

  }

}
