package com.webforj.samples.verify;

// docs:start data-binding.nested.use-property
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.data.binding.BindingContext;
import com.webforj.data.binding.annotation.UseProperty;

public class PersonForm extends Composite<Div> {
  @UseProperty("address.street")
  TextField streetField = new TextField("Street");

  @UseProperty("address.city")
  TextField cityField = new TextField("City");

  BindingContext<Person> context;
      
  public PersonForm() {
    context = BindingContext.of(this, Person.class);
    getBoundComponent().add(streetField, cityField);
  }
}
// docs:end data-binding.nested.use-property
