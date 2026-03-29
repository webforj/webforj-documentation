package com.webforj.samples.views.table.renderers;

public class Product {
  private String name;
  private String sku;
  private String category;
  private String description;
  private double price;
  private double rating;
  private int discount;
  private boolean inStock;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the sku
   */
  public String getSku() {
    return sku;
  }

  /**
   * @param sku the sku to set
   */
  public void setSku(String sku) {
    this.sku = sku;
  }

  /**
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * @param category the category to set
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the price
   */
  public double getPrice() {
    return price;
  }

  /**
   * @param price the price to set
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * @return the rating
   */
  public double getRating() {
    return rating;
  }

  /**
   * @param rating the rating to set
   */
  public void setRating(double rating) {
    this.rating = rating;
  }

  /**
   * @return the discount
   */
  public int getDiscount() {
    return discount;
  }

  /**
   * @param discount the discount to set
   */
  public void setDiscount(int discount) {
    this.discount = discount;
  }

  /**
   * @return the inStock
   */
  public boolean isInStock() {
    return inStock;
  }

  /**
   * @param inStock the inStock to set
   */
  public void setInStock(boolean inStock) {
    this.inStock = inStock;
  }
}