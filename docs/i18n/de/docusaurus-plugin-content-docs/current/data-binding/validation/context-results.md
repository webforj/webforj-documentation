---
sidebar_position: 4
title: Context Results
_i18n_hash: 15fc4551d1ed2f2b5e35785975e66946
---
Wenn Sie Daten von der UI auf das Modell schreiben, löst die `write`-Methode des `BindingContext` die Validierungen aus. Die Ergebnisse der Validierung bestimmen, ob die Daten akzeptabel sind.

## Verarbeitung von Validierungsergebnissen {#processing-validation-results}

Sie können Validierungsergebnisse verarbeiten, um dem Benutzer Feedback zu geben. Wenn eine Validierung fehlschlägt, können Sie das Update der Daten im Modell verhindern und Fehlermeldungen anzeigen, die mit jeder fehlgeschlagenen Validierung verbunden sind.

```java
ValidationResult result = context.write(hero);
if (!result.isValid()) {
    displayErrors(result.getMessages());
} else {
    proceedWithUpdate();
}
```

<!-- vale off -->
## Kontext-Validierungsstatus {#context-validation-state}
<!-- vale on -->

Wann immer der Kontext die Komponenten validiert, löst er ein `BindingContextValidateEvent` aus. Dieses Ereignis liefert das `ValidationResult` für alle Bindungen, die gleichzeitig geändert wurden. Sie können diese Ergebnisse nutzen, um Aktionen auszulösen und angemessen zu reagieren, z. B. durch Aktivieren oder Deaktivieren der Schaltfläche "Absenden", basierend auf der Gesamtgültigkeit des Formulars.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Hören Sie auf das BindingContextValidateEvent, das bei jeder Benutzerinteraktion ausgelöst wird.
context.addValidateListener(event -> {
    submit.setEnabled(event.isValid());
});
```

## Auto-Fokus-Verletzung {#auto-focus-violation}

Bei Formularen, die eine Validierung über mehrere Felder hinweg erfordern, kann der automatische Fokus auf das erste Feld mit einem Fehler die Benutzererfahrung erheblich verbessern. Diese Funktion hilft den Benutzern, Fehler sofort zu identifizieren und zu korrigieren, was den Abschluss des Formulars erleichtert.

Der `BindingContext` vereinfacht den Prozess, den Auto-Fokus auf die erste Komponente mit einem Validierungsfehler einzurichten. Durch die Verwendung der Methode `setAutoFocusFirstViolation` können Sie diese Funktion mit minimalem Code aktivieren, wodurch sichergestellt wird, dass die Benutzeroberfläche intuitiver und reaktionsschneller auf Eingabefehler wird.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Fokusbewusst
Diese Funktion funktioniert nur für die Komponenten, die das `FocusAcceptorAware`-Merkmal implementieren.
:::
