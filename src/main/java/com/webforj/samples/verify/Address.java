package com.webforj.samples.verify;

// docs:start data-binding.nested.address
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Address {
  @NotNull private String street;

  @Size(min = 2)
  private String city;

  private String zip;

  public Address() {}

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }
}
// docs:end data-binding.nested.address
