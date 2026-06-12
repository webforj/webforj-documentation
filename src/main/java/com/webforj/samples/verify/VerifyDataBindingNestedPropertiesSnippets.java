package com.webforj.samples.verify;

import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.data.binding.BindingContext;
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.router.annotation.Route;
import java.util.Objects;

@Route("verify-data-binding-nested-properties")
public class VerifyDataBindingNestedPropertiesSnippets extends Composite<Div> {
  public VerifyDataBindingNestedPropertiesSnippets() {
    verifyManualDottedPathBinding();
    verifyNullIntermediateRead();
    verifyWriteCreatesMissingIntermediate();
    PersonForm personForm = verifyAutomaticUsePropertyPath();
    verifyNestedRequiredState();

    getBoundComponent().add(personForm);
  }

  private void verifyManualDottedPathBinding() {
    // docs:start data-binding.nested.manual
    TextField streetField = new TextField("Street");
    TextField cityField = new TextField("City");
    TextField zipField = new TextField("ZIP");

    BindingContext<Person> context = new BindingContext<>(Person.class);
    context.bind(streetField, "address.street").add();
    context.bind(cityField, "address.city").add();
    context.bind(zipField, "address.zip").add();
    // docs:end data-binding.nested.manual

    Person person = new Person();
    person.setAddress(address("Main Street", "Boston", "02108"));

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

    // docs:start data-binding.nested.read-null
    Person person = new Person();
    context.read(person);
    // docs:end data-binding.nested.read-null

    require(person.getAddress() == null, "read should not instantiate a null nested bean");
  }

  private void verifyWriteCreatesMissingIntermediate() {
    TextField streetField = new TextField("Street");

    BindingContext<Person> context = new BindingContext<>(Person.class);
    context.bind(streetField, "address.street").add();

    // docs:start data-binding.nested.write-create
    Person person = new Person();
    streetField.setValue("Main Street");

    ValidationResult result = context.write(person);
    if (result.isValid()) {
      String street = person.getAddress().getStreet();
    }
    // docs:end data-binding.nested.write-create

    require(result.isValid(), "write into missing nested bean should be valid");
    require(person.getAddress() != null, "write should instantiate the nested bean");
    requireEquals(
        "Main Street", person.getAddress().getStreet(), "write should set the leaf value");
  }

  private PersonForm verifyAutomaticUsePropertyPath() {
    PersonForm form = new PersonForm();
    Person person = new Person();
    person.setAddress(address("Elm Street", "Denver", "80202"));

    form.context.read(person);
    requireEquals(
        "Elm Street", form.streetField.getValue(), "@UseProperty should read dotted paths");
    requireEquals("Denver", form.cityField.getValue(), "@UseProperty should bind nested city");

    form.streetField.setValue("Pine Street");
    ValidationResult result = form.context.write(person);

    require(result.isValid(), "@UseProperty dotted path write should be valid");
    requireEquals(
        "Pine Street", person.getAddress().getStreet(), "@UseProperty should write dotted paths");

    return form;
  }

  private void verifyNestedRequiredState() {
    // docs:start data-binding.nested.required
    TextField streetField = new TextField("Street");

    BindingContext<Person> context = new BindingContext<>(Person.class, true);
    context.bind(streetField, "address.street").add();

    boolean required = streetField.isRequired();
    // docs:end data-binding.nested.required

    require(required, "nested @NotNull leaf should mark the field as required");
  }

  private static Address address(String street, String city, String zip) {
    Address address = new Address();
    address.setStreet(street);
    address.setCity(city);
    address.setZip(zip);
    return address;
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
}
