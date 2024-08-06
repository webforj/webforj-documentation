---
sidebar_position: 1
title: Data Binding
---

<!-- vale off -->
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

 webforJ includes a data binding feature that seamlessly integrates UI components with backend data models in Java applications. This feature bridges the gap between the UI and the data layer, ensuring that changes in the UI reflect in the data model and vice versa. As a result, it enhances user experience and reduces the complexity of event handling and data synchronization.

## Concept

The following demonstration showcases a simple webforJ app for registering superheroes using webforJ data binding. The app consists of two main parts: `HeroRegistration.java` and `Hero.java`. 

In `HeroRegistration.java`, the code configures the user interface with a `TextField` for entering the hero's name, a `ComboBox` to select a superpower, and a `Button` to submit the registration.

The `Hero` class defines the data model with validation constraints on the hero's name and power, ensuring that entries are valid and adhere to specified criteria such as length and pattern.

The app utilizes the `BindingContext` to bind UI components to the properties of the `Hero` object. When a user clicks the submit button, the app writes the data entered in the form back to the `Hero` bean if they're valid.

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {
    
  private TextField name = new TextField("Text Field");
  private ComboBox power = new ComboBox("Power");
  private Button submit = new Button("Submit Application");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("Fly", "Invisible", "LaserVision", "Speed", "Teleportation");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Fly");

    // reflect the bean data in the form
    context.read(bean);

    submit.onClick(e -> {
      // write the form data back to the bean
      ValidationResult results = context.write(bean);

      if (results.isValid()) {
        // do something with the bean
        // repository.persist(bean)
      }
    });

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="Hero" label="Hero.java">

```java showLineNumbers
public class Hero {

  @NotEmpty(message = "Name cannot be empty")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Unspecified power")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Invalid power")
  private String power;

  public Hero(String name, String power) {
    this.name = name;
    this.power = power;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPower() {
    return power;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public String toString() {
    return "Name: " + name + ", Power: " + power;
  }
}
```

</TabItem>
</Tabs>

## Key features

- **Bidirectional Binding:**  Supports bidirectional data binding, allowing changes in the data model to update the UI, and user interactions in the UI to update the data model.

- **Validation Support:** Integrates comprehensive validation mechanisms that you can customize and extended. Developers can implement their own validation rules or use existing validation frameworks like Jakarta Validation to ensure data integrity before updating the model.

- **Extensibility:** Can be easily extended to support different types of UI components, data transformations, and complex validation scenarios.

- **Annotation-Driven Configuration:**  Utilizes annotations to minimize boilerplate code, making the bindings between UI components and data models declarative and easy to manage.

# Topics

<DocCardList className="topics-section" />