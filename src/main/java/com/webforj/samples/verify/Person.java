package com.webforj.samples.verify;

// docs:start data-binding.nested.person
public class Person {
  private String name;
  private Address address;

  public Person() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
// docs:end data-binding.nested.person
