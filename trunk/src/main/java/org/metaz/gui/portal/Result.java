package org.metaz.gui.portal;


// @author: Falco Paul
/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public final class Result {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String name;
  private String productType;
  private String description;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  public Result(String name, String productType, String description) {

    this.name = name;
    this.productType = productType;
    this.description = description;

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param name DOCUMENT ME!
   */
  public void setName(String name) {

    this.name = name;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getName() {

    return name;

  }

  /**
   * DOCUMENT ME!
   *
   * @param productType DOCUMENT ME!
   */
  public void setProductType(String productType) {

    this.productType = productType;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getProductType() {

    return productType;

  }

  /**
   * DOCUMENT ME!
   *
   * @param description DOCUMENT ME!
   */
  public void setDescription(String description) {

    this.description = description;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getDescription() {

    return description;

  }

}
