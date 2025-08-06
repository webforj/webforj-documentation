---
sidebar_position: 5
title: Triggers
_i18n_hash: a52300a9683a08701c4e1f1f6150dd9f
---
Bij standaardinstellingen valideren bindings automatisch componenten wanneer gebruikers hun gegevens wijzigen, zoals het invoeren van nieuwe tekst, het aanvinken van een selectievakje of het selecteren van een nieuwe optie in een radioknop. Als je de voorkeur geeft aan het uitschakelen van automatische validaties en ze alleen wilt rapporteren wanneer je naar het datamodel schrijft, kun je de binding zo configureren dat deze wordt uitgeschakeld. Dit geeft je controle over wanneer en hoe validaties plaatsvinden, zodat je validaties kunt beheren op basis van specifieke app-behoeften of gebruikersinteracties.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Aangepaste melding voor ongeldig e-mailadres"))
    .autoValidate(false)
    .add();
```

Het is ook mogelijk om de automatische validaties voor de hele context uit te schakelen.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoValidate(false);
```

:::tip Waarde Wijziging Modus
Sommige componenten, zoals de veldcomponenten, implementeren de `ValueChangeModeAware` interface, waarmee je kunt regelen wanneer het systeem een `ValueChangeEvent` rapporteert. Je kunt bijvoorbeeld veldcomponenten instellen om waarde wijzigingen alleen te rapporteren bij verlies van focus. Deze configuratie vermindert de frequentie van validaties, optimaliseert de prestaties en verbetert de gebruikerservaring door validaties te concentreren op momenten waarop de gebruiker een invoersessie voltooit, in plaats van tijdens actief typen.

```java
 emailField.setValueChangeMode(ValueChangeMode.ON_BLUR);
```
:::

## Hervalidatie {#revalidation}

Hoewel validaties meestal automatisch worden geactiveerd tijdens het schrijven van gegevens, kun je ze ook handmatig aanroepen om de staat van de gegevens te verifiëren zonder te proberen deze naar het model te schrijven. Deze handmatige benadering is bijzonder nuttig in scenario's waarin je functies wilt inschakelen of uitschakelen op basis van de geldigheid van de formuliergegevens zonder een update uit te voeren.

Beschouw een klassiek voorbeeld van een Reisdatum Keuze, waarbij een gebruiker twee datums moet selecteren: de startdatum en de einddatum van een reis. Het is niet geldig om een einddatum te kiezen die vóór de startdatum ligt, of een startdatum die na de einddatum ligt. Je kunt deze afhankelijkheden oplossen door validaties handmatig te activeren:

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
        .useValidator(Objects::nonNull, "Startdatum is vereist")
        .useValidator(value -> endDate != null && value.isBefore(endDate),
            "Startdatum moet vóór de einddatum liggen")
        .add();

    context.bind(endDateField, "endDate")
        .useValidator(Objects::nonNull, "Einddatum is vereist")
        .useValidator(value -> startDate != null && value.isAfter(startDate),
            "Einddatum moet na de startdatum liggen")
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
