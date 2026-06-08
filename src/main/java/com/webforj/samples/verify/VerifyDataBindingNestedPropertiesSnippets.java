package com.webforj.samples.verify;

import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.data.binding.BindingContext;
import com.webforj.data.binding.annotation.UseProperty;
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.router.annotation.Route;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

@Route("verify-data-binding-nested-properties")
public class VerifyDataBindingNestedPropertiesSnippets extends Composite<Div> {
  @UseProperty("address.street")
  private TextField autoStreetField = new TextField("Street");

  @UseProperty("address.city")
  private TextField autoCityField = new TextField("City");

  public VerifyDataBindingNestedPropertiesSnippets() {
    verifyManualDottedPathBinding();
    verifyNullIntermediateRead();
    verifyWriteCreatesMissingIntermediate();
    verifyAutomaticUsePropertyPath();
    verifyNestedRequiredState();

    getBoundComponent().add(autoStreetField, autoCityField);
  }

  private void verifyManualDottedPathBinding() {
    TextField streetField = new TextField("Street");
    TextField cityField = new TextField("City");
    TextField zipField = new TextField("ZIP");

    BindingContext<Person> context = new BindingContext<>(Person.class);
    context.bind(streetField, "address.street").add();
    context.bind(cityField, "address.city").add();
    context.bind(zipField, "address.zip").add();

    Person person = new Person();
    person.setAddress(new Address("Main Street", "Boston", "02108"));

    context.read(person);
    requireEquals("Main Street", streetField.getValue(), "street read through nested path");
    requireEquals("Boston", cityField.getValue(), "city read through nested path");
    requireEquals("02108", zipField.getValue(), "zip read through nested path");

    streetField.setValue("Oak Avenue");
    ValidationResult result = context.write(person);
    require(result.isValid(), "manual nested binding write should be valid");
    requireEquals(
        "Oak Avenue", person.getAddress().getStreet(), "street write through nested path");
  }

  private void verifyNullIntermediateRead() {
    TextField streetField = new TextField("Street");

    BindingContext<Person> context = new BindingContext<>(Person.class);
    context.bind(streetField, "address.street").add();

    Person person = new Person();
    context.read(person);

    require(person.getAddress() == null, "read should not instantiate a null nested bean");
  }

  private void verifyWriteCreatesMissingIntermediate() {
    TextField streetField = new TextField("Street");

    BindingContext<Person> context = new BindingContext<>(Person.class);
    context.bind(streetField, "address.street").add();

    Person person = new Person();
    streetField.setValue("Main Street");

    ValidationResult result = context.write(person);
    require(result.isValid(), "write into missing nested bean should be valid");
    require(person.getAddress() != null, "write should instantiate the nested bean");
    requireEquals(
        "Main Street", person.getAddress().getStreet(), "write should set the leaf value");
  }

  private void verifyAutomaticUsePropertyPath() {
    BindingContext<Person> context = BindingContext.of(this, Person.class);
    Person person = new Person();
    person.setAddress(new Address("Elm Street", "Denver", "80202"));

    context.read(person);
    requireEquals(
        "Elm Street", autoStreetField.getValue(), "@UseProperty should read dotted paths");
    requireEquals("Denver", autoCityField.getValue(), "@UseProperty should bind nested city");

    autoStreetField.setValue("Pine Street");
    ValidationResult result = context.write(person);

    require(result.isValid(), "@UseProperty dotted path write should be valid");
    requireEquals(
        "Pine Street", person.getAddress().getStreet(), "@UseProperty should write dotted paths");
  }

  private void verifyNestedRequiredState() {
    TextField streetField = new TextField("Street");

    BindingContext<Person> context = new BindingContext<>(Person.class);
    context.bind(streetField, "address.street").add();

    require(streetField.isRequired(), "nested @NotNull leaf should mark the field as required");
  }

  private static void require(boolean condition, String message) {
    if (!condition) {
      throw new IllegalStateException(message);
    }
  }

  private static void requireEquals(String expected, String actual, String message) {
    if (!Objects.equals(expected, actual)) {
      throw new IllegalStateException(message + ": expected " + expected + " but was " + actual);
    }
  }

  public static class Person {
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

  public static class Address {
    @NotNull private String street;

    @Size(min = 2)
    private String city;

    private String zip;

    public Address() {}

    public Address(String street, String city, String zip) {
      this.street = street;
      this.city = city;
      this.zip = zip;
    }

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
}
