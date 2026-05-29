---
sidebar_position: 4
title: Context Results
_i18n_hash: b86bc425ad8c1537e99a44fa34a93b3a
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
## Kontext-Validierungsstatus {#context-validation-state}
<!-- vale on -->

Immer wenn der Kontext die Komponenten validiert, wird ein `BindingContextValidateEvent` ausgelöst. Dieses Ereignis liefert das `ValidationResult` für alle Bindungen, die gleichzeitig geändert wurden. Sie können diese Ergebnisse verwenden, um Aktionen auszulösen und entsprechend zu reagieren, z. B. das Aktivieren oder Deaktivieren des Absendebuttons basierend auf der allgemeinen Gültigkeit des Formulars.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Hören Sie auf das BindingContextValidateEvent, das bei jeder Benutzerinteraktion ausgelöst wird.
context.addValidateListener(event -> {
  submit.setEnabled(event.isValid());
});
```

## Auto-Fokus-Verstoß {#auto-focus-violation}

Beim Umgang mit Formularen, die eine Validierung über mehrere Felder erfordern, kann das automatische Fokussieren des ersten Feldes mit einem Fehler die Benutzererfahrung erheblich verbessern. Diese Funktion hilft den Benutzern, Fehler sofort zu identifizieren und zu korrigieren und optimiert den Prozess des Ausfüllens des Formulars.

Der `BindingContext` vereinfacht den Prozess, das Auto-Fokus auf die erste Komponente mit einem Validierungsfehler einzurichten. Durch die Verwendung der Methode `setAutoFocusFirstViolation` können Sie dieses Feature mit minimalem Code aktivieren, sodass die Benutzeroberfläche intuitiver und reaktionsschneller auf Eingabefehler reagiert.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Fokusbewusst
Diese Funktion funktioniert nur für die Komponenten, die das `FocusAcceptorAware`-Kriterium implementieren.
:::
