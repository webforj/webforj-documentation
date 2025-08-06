---
sidebar_position: 5
title: Triggers
_i18n_hash: a52300a9683a08701c4e1f1f6150dd9f
---
By default, bindings automatically revalidate components when users modify their data, such as entering new text, checking a checkbox, or selecting a new option in a radio button. If you prefer to turn off automatic validations and only report them when writing to the data model, you can configure the binding to turn them off. This gives you control over when and how validations occur, allowing you to manage validations according to specific app needs or user interactions.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Mukautettu viesti virheelliselle sähköpostiosoitteelle"))
    .autoValidate(false)
    .add();
```

It's also possible to turn off the auto-validations for the whole context.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoValidate(false);
```

:::tip Arvon Muutos Tila
Some components, like the field components, implement the `ValueChangeModeAware` interface, which lets you control when the system reports a `ValueChangeEvent`. For instance, you can set field components to report value changes only on blur. This configuration reduces the frequency of validations, optimizing performance and enhancing the user experience by focusing validations on moments when the user completes an input session, rather than during active typing.

```java
 emailField.setValueChangeMode(ValueChangeMode.ON_BLUR);
```
:::

## Uudelleentodentaminen {#revalidation}

While validations typically trigger automatically during data writing, you can also invoke them manually to verify the state of data without attempting to write it to the model. This manual approach is particularly useful in scenarios where you want to enable or turn off features based on the validity of the form data without making an update.

Consider a classic example of a Trip Date Chooser, where a user must select two dates: the start date and the end date of a trip. It's not valid to choose an end date that occurs before the start date, or a start date that occurs after the end date. You can resolve these dependencies by triggering validations manually:

<Tabs>
<TabItem value="TripBooking" label="TripBooking.java">

```java showLineNumbers
public class TripBooking extends App {
  DateTimeField startDateField = new DateTimeField("Aloitus päivämäärä");
  DateTimeField endDateField = new DateTimeField("Loppupäivämäärä");
  FlexLayout layout = FlexLayout.create(startDateField, endDateField).vertical().build().setStyle("margin", "20px auto")
      .setMaxWidth("400px");

  LocalDateTime startDate;
  LocalDateTime endDate;

  @Override
  public void run() throws WebforjException {
    BindingContext<Trip> context = new BindingContext<>(Trip.class);
    context.bind(startDateField, "startDate")
        .useValidator(Objects::nonNull, "Aloitus päivämäärä on pakollinen")
        .useValidator(value -> endDate != null && value.isBefore(endDate),
            "Aloitus päivämäärän täytyy olla ennen loppupäivämäärää")
        .add();

    context.bind(endDateField, "endDate")
        .useValidator(Objects::nonNull, "Loppupäivämäärä on pakollinen")
        .useValidator(value -> startDate != null && value.isAfter(startDate),
            "Loppupäivämäärän täytyy olla jälkeen aloituspäivämäärä")
        .add();

    startDateField.setValueChangeMode(ValueChangeMode.ON_BLUR);
    startDateField.addValueChangeListener(event -> {
      startDate = event.getValue();
      context.getBinding("endDate").validate();
    });

    endDateField.setValueChangeMode(ValueChangeMode.ON_BLUR);
    endDateField.addValueChangeListener(event -> {
      endDate = event.getValue();
      context.getBinding("startDate").validate();
    });

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="Trip" label="Trip.java">

```java showLineNumbers
public class Trip {
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }
}
```

</TabItem>
</Tabs>
