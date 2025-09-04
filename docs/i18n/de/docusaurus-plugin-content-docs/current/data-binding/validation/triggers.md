---
sidebar_position: 5
title: Triggers
_i18n_hash: b158a924f67b7141be94d56b9be8bba3
---
Importiert Tabs von '@theme/Tabs';
import TabItem von '@theme/TabItem';

Standardmäßig validieren Bindungen automatisch Komponenten, wenn Benutzer ihre Daten ändern, wie z. B. beim Eingeben von neuem Text, Ankreuzen eines Kontrollkästchens oder Auswählen einer neuen Option in einer Optionsschaltfläche. Wenn Sie die automatischen Validierungen deaktivieren und sie nur dann melden möchten, wenn Sie in das Datenmodell schreiben, können Sie die Bindung so konfigurieren, dass sie deaktiviert wird. Dies gibt Ihnen die Kontrolle darüber, wann und wie Validierungen stattfinden, sodass Sie Validierungen gemäß den spezifischen Anforderungen der App oder Benutzerinteraktionen verwalten können.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Benutzerdefinierte Nachricht für ungültige E-Mail-Adresse"))
    .autoValidate(false)
    .add();
```

Es ist auch möglich, die automatischen Validierungen für den gesamten Kontext auszuschalten.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoValidate(false);
```

:::tip Änderungsmodus für Werte
Einige Komponenten, wie die Feldkomponenten, implementieren das `ValueChangeModeAware`-Interface, das es Ihnen ermöglicht, zu steuern, wann das System ein `ValueChangeEvent` meldet. Sie können beispielsweise die Feldkomponenten so einstellen, dass sie Wertänderungen nur beim Verlassen des Feldes melden. Diese Konfiguration reduziert die Häufigkeit der Validierungen, optimiert die Leistung und verbessert die Benutzererfahrung, indem sie die Validierungen auf Momente konzentriert, in denen der Benutzer eine Eingabesitzung abgeschlossen hat, anstatt während des aktiven Tippens.

```java
emailField.setValueChangeMode(ValueChangeMode.ON_BLUR);
```
:::

## Neuvalidierung {#revalidation}

Während Validierungen typischerweise automatisch beim Schreiben von Daten ausgelöst werden, können Sie sie auch manuell aufrufen, um den Status der Daten zu überprüfen, ohne zu versuchen, sie in das Modell zu schreiben. Dieser manuelle Ansatz ist besonders nützlich in Szenarien, in denen Sie Funktionen aktivieren oder deaktivieren möchten, basierend auf der Gültigkeit der Formulardaten, ohne eine Aktualisierung vorzunehmen.

Betrachten Sie ein klassisches Beispiel für einen Reisedatum-Wähler, bei dem ein Benutzer zwei Daten auswählen muss: das Startdatum und das Enddatum einer Reise. Es ist ungültig, ein Enddatum zu wählen, das vor dem Startdatum liegt, oder ein Startdatum, das nach dem Enddatum liegt. Sie können diese Abhängigkeiten lösen, indem Sie Validierungen manuell auslösen:

<Tabs>
<TabItem value="TripBooking" label="TripBooking.java">

```java showLineNumbers
public class TripBooking extends App {
  DateTimeField startDateField = new DateTimeField("Startdatum");
  DateTimeField endDateField = new DateTimeField("Enddatum");
  FlexLayout layout = FlexLayout.create(startDateField, endDateField).vertical().build().setStyle("margin", "20px auto")
      .setMaxWidth("400px");

  LocalDateTime startDate;
  LocalDateTime endDate;

  @Override
  public void run() throws WebforjException {
    BindingContext<Trip> context = new BindingContext<>(Trip.class);
    context.bind(startDateField, "startDate")
        .useValidator(Objects::nonNull, "Startdatum ist erforderlich")
        .useValidator(value -> endDate != null && value.isBefore(endDate),
            "Startdatum muss vor dem Enddatum liegen")
        .add();

    context.bind(endDateField, "endDate")
        .useValidator(Objects::nonNull, "Enddatum ist erforderlich")
        .useValidator(value -> startDate != null && value.isAfter(startDate),
            "Enddatum muss nach dem Startdatum liegen")
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
