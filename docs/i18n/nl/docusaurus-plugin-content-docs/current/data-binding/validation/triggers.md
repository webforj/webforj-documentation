---
sidebar_position: 5
title: Triggers
_i18n_hash: b158a924f67b7141be94d56b9be8bba3
---
Bij standaardinstellingen valideren bindings automatisch componenten opnieuw wanneer gebruikers hun gegevens wijzigen, zoals het invoeren van nieuwe tekst, het aanvinken van een selectievakje of het selecteren van een nieuwe optie in een radiobutton. Als u liever automatische validaties uitschakelt en alleen rapporteert wanneer u naar het gegevensmodel schrijft, kunt u de binding configureren om ze uit te zetten. Dit geeft u controle over wanneer en hoe validaties plaatsvinden, zodat u validaties kunt beheren op basis van specifieke app-behoeften of gebruikersinteracties.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Aangepaste boodschap voor ongeldig e-mailadres"))
    .autoValidate(false)
    .add();
```

Het is ook mogelijk om de automatische validaties voor de hele context uit te schakelen.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoValidate(false);
```

:::tip Waarde Wijzigingsmodus
Sommige componenten, zoals de veldcomponenten, implementeren de `ValueChangeModeAware` interface, waarmee u kunt bepalen wanneer het systeem een `ValueChangeEvent` rapporteert. U kunt bijvoorbeeld veldcomponenten instellen om waarde wijzigingen alleen bij verlies van focus te rapporteren. Deze configuratie beperkt de frequentie van validaties, optimaliseert de prestaties en verbetert de gebruikerservaring door validaties te richten op momenten waarop de gebruiker een invoersessie voltooit, in plaats van tijdens actieve typactiviteiten.

```java
 emailField.setValueChangeMode(ValueChangeMode.ON_BLUR);
```
:::

## Hervalidatie {#revalidation}

Hoewel validaties doorgaans automatisch worden geactiveerd tijdens het schrijven van gegevens, kunt u ze ook handmatig aanroepen om de toestand van gegevens te verifiëren zonder te proberen deze naar het model te schrijven. Deze handmatige benadering is bijzonder nuttig in situaties waarin u functies wilt in- of uitschakelen op basis van de geldigheid van de formuliergegevens zonder een update uit te voeren.

Overweeg een klassiek voorbeeld van een Reisdatumkiezer, waarbij een gebruiker twee data moet selecteren: de startdatum en de einddatum van een reis. Het is niet geldig om een einddatum te kiezen die voor de startdatum valt, of een startdatum die na de einddatum valt. U kunt deze afhankelijkheden oplossen door validaties handmatig te activeren:

<Tabs>
<TabItem value="TripBooking" label="TripBooking.java">

```java showLineNumbers
public class TripBooking extends App {
  DateTimeField startDateField = new DateTimeField("Startdatum");
  DateTimeField endDateField = new DateTimeField("Einddatum");
  FlexLayout layout = FlexLayout.create(startDateField, endDateField).vertical().build().setStyle("margin", "20px auto")
      .setMaxWidth("400px");

  LocalDateTime startDate;
  LocalDateTime endDate;

  @Override
  public void run() throws WebforjException {
    BindingContext<Trip> context = new BindingContext<>(Trip.class);
    context.bind(startDateField, "startDate")
        .useValidator(Objects::nonNull, "Startdatum is verplicht")
        .useValidator(value -> endDate != null && value.isBefore(endDate),
            "Startdatum moet voor de einddatum zijn")
        .add();

    context.bind(endDateField, "endDate")
        .useValidator(Objects::nonNull, "Einddatum is verplicht")
        .useValidator(value -> startDate != null && value.isAfter(startDate),
            "Einddatum moet na de startdatum zijn")
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
