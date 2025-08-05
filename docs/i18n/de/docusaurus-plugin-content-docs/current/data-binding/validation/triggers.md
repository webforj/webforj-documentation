---
sidebar_position: 5
title: Triggers
_i18n_hash: a52300a9683a08701c4e1f1f6150dd9f
---
<!-- vale off -->

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

<!-- vale on -->

Standardmäßig validieren Bindungen automatisch die Komponenten, wenn Benutzer ihre Daten ändern, z. B. beim Eingeben neuen Textes, beim Markieren eines Kontrollkästchens oder beim Auswählen einer neuen Option in einem Auswahlfeld. Wenn Sie die automatischen Validierungen deaktivieren und diese nur melden möchten, wenn Sie in das Datenmodell schreiben, können Sie die Bindung so konfigurieren, dass sie deaktiviert wird. Dies gibt Ihnen die Kontrolle darüber, wann und wie Validierungen erfolgen, sodass Sie Validierungen gemäß den spezifischen Anforderungen der App oder Benutzerinteraktionen verwalten können.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Benutzerdefinierte Nachricht für ungültige E-Mail-Adresse"))
    .autoValidate(false)
    .add();
```

Es ist auch möglich, die automatischen Validierungen für den gesamten Kontext zu deaktivieren.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoValidate(false);
```

:::tip Wertänderungsmodus
Einige Komponenten, wie die Feldkomponenten, implementieren das `ValueChangeModeAware`-Interface, mit dem Sie steuern können, wann das System ein `ValueChangeEvent` meldet. Zum Beispiel können Sie Feldkomponenten so einstellen, dass Wertänderungen nur bei Verlust des Fokus gemeldet werden. Diese Konfiguration reduziert die Häufigkeit von Validierungen, optimiert die Leistung und verbessert das Benutzererlebnis, indem Validierungen auf Momente konzentriert werden, in denen der Benutzer eine Eingabesitzung abschließt, anstatt während der aktiven Eingabe.

```java
 emailField.setValueChangeMode(ValueChangeMode.ON_BLUR);
```
:::

## Revalidierung {#revalidation}

Während Validierungen normalerweise automatisch während des Schreibens von Daten ausgelöst werden, können Sie sie auch manuell aufrufen, um den Zustand der Daten zu überprüfen, ohne zu versuchen, sie in das Modell zu schreiben. Dieser manuelle Ansatz ist besonders nützlich in Szenarien, in denen Sie Funktionen basierend auf der Gültigkeit der Formulardaten aktivieren oder deaktivieren möchten, ohne ein Update vorzunehmen.

Betrachten Sie ein klassisches Beispiel eines Reise-Datenauswählers, bei dem der Benutzer zwei Daten auswählen muss: das Startdatum und das Enddatum einer Reise. Es ist nicht gültig, ein Enddatum zu wählen, das vor dem Startdatum liegt, oder ein Startdatum, das nach dem Enddatum liegt. Sie können diese Abhängigkeiten lösen, indem Sie die Validierungen manuell auslösen:

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
