---
sidebar_position: 4
title: Context Results
_i18n_hash: f7eeb60ff21b1d5dff27b17cc82cdf50
---
Wenn Sie Daten von der Benutzeroberfläche in das Modell schreiben, löst die `write`-Methode des `BindingContext` die Validierungen aus. Die Validierungsergebnisse bestimmen, ob die Daten akzeptabel sind.

## Verarbeitung von Validierungsergebnissen {#processing-validation-results}

Sie können Validierungsergebnisse verarbeiten, um dem Benutzer Feedback zu geben. Wenn eine Validierung fehlschlägt, können Sie die Datenaktualisierung im Modell verhindern und Fehlermeldungen anzeigen, die mit jeder fehlgeschlagenen Validierung verbunden sind.

```java
ValidationResult result = context.write(hero);
if (!result.isValid()) {
    displayErrors(result.getMessages());
} else {
    proceedWithUpdate();
}
```

<!-- vale off -->
## Kontextvalidierungsstatus {#context-validation-state}
<!-- vale on -->

Immer wenn der Kontext die Komponenten validiert, wird ein `BindingContextValidateEvent` ausgelöst. Dieses Ereignis liefert das `ValidationResult` für alle Bindungen, die gleichzeitig geändert wurden. Sie können diese Ergebnisse verwenden, um Aktionen auszulösen und angemessen zu reagieren, z. B. um die Schaltfläche zum Absenden basierend auf der Gesamtgültigkeit des Formulars zu aktivieren oder zu deaktivieren.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Hören Sie auf das BindingContextValidateEvent, das bei jeder Benutzerinteraktion ausgelöst wird.
context.addValidateListener(event -> {
    submit.setEnabled(event.isValid());
});
```

## Auto-Fokus-Verletzung {#auto-focus-violation}

Beim Umgang mit Formularen, die eine Validierung über mehrere Felder erfordern, kann das automatische Fokussieren des ersten Feldes mit einem Fehler die Benutzererfahrung erheblich verbessern. Dieses Feature hilft Benutzern, Fehler sofort zu identifizieren und zu korrigieren, wodurch der Abschluss des Formulars erleichtert wird.

Der `BindingContext` vereinfacht den Prozess des Einstellens des Auto-Fokus auf die erste Komponente mit einem Validierungsfehler. Durch die Verwendung der Methode `setAutoFocusFirstViolation` können Sie dieses Feature mit minimalem Code aktivieren, sodass die Benutzeroberfläche intuitiver und reaktionsschneller auf Eingabefehler wird.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Fokusbewusst
Dieses Feature funktioniert nur für die Komponenten, die das `FocusAcceptorAware`-Anliegen implementieren.
:::
