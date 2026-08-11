---
sidebar_position: 3
title: Reporters
description: >-
  Surface validation outcomes through the DefaultBindingReporter or attach
  custom reporters to bindings with the useReporter callback.
_i18n_hash: e642fa150e90534cdaef8bb0955d4ff0
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

Validation reportern werden verwendet, um Feedback über den Validierungsprozess an die Benutzeroberfläche bereitzustellen. Diese Funktion ist entscheidend, um die Benutzer über die Ergebnisse ihrer Eingangsvalidierung zu informieren, insbesondere in komplexen Formularen oder datenintensiven Anwendungen.

## Was ist ein Validierungsreporter? {#whats-a-validation-reporter}

Ein Validierungsreporter ist eine Komponente, die die Ergebnisse von Validierungen verarbeitet und den Benutzern anzeigt. Er fungiert als Brücke zwischen der Validierungslogik und der Benutzeroberfläche und sorgt dafür, dass die Ergebnisse der Validierung effektiv und klar kommuniziert werden.

:::tip Kernkomponenten Standardreporter
webforJ enthält den `DefaultBindingReporter`, einen Standardbindingsreporter, der nahtlos mit allen KernwebforJ-Komponenten funktioniert. Dieser integrierte Reporter zeigt automatisch Validierungsfehler an, wodurch in vielen Fällen eine benutzerdefinierte Implementierung entfällt. Je nach Konfiguration der Komponente zeigt der `DefaultBindingReporter` Validierungsfehler direkt als Popover oder inline, direkt unter der Komponente an. Diese Funktion vereinfacht den Fehlerberichtsprozess erheblich, sorgt für eine klare und direkte Kommunikation von Validierungsfehlern und verbessert das Benutzererlebnis, indem sie sofortige, kontextsensitiven Feedback zur Eingangsvalidierung bietet.
:::

## Konfigurieren von Validierungsreportern {#configuring-validation-reporters}

Sie können Validierungsreporter im Bindungskontext konfigurieren, um anzupassen, wie Nachrichten dargestellt werden. Typischerweise würden Sie einen Validierungsreporter implementieren, um Validierungsergebnisse zu aggregieren und sie dann benutzerfreundlich darzustellen, beispielsweise durch Hervorheben inkorrekter Felder, Anzeigen von Fehlermeldungen oder Aktualisieren von Statusanzeigen.

Hier ist ein Beispiel, wie man einen Validierungsreporter für ein Feld einrichtet

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@StyleSheet("ws://css/styles.css")
public class UserRegistration extends App {
  Div errors = new Div();
  TextField emailField = new TextField("E-Mail-Adresse");

  FlexLayout layout = FlexLayout.create(emailField, errors).vertical().build();

  @Override
  public void run() throws WebforjException {
    errors.addClassName("error");
    errors.setVisible(false);

    layout.addClassName("form");

    BindingContext<User> context = new BindingContext<>(User.class);
    context.bind(emailField, "email")
        .useValidator(
            Validator.from(new EmailValidator(), "Benutzerdefinierte Nachricht für ungültige E-Mail-Adresse"))
        .useReporter((validationResult, binding) -> {
          errors.setVisible(!validationResult.isValid());

          if (!validationResult.isValid()) {
            errors.setText(validationResult.getMessages().stream().findFirst().orElse(""));
          }
        })
        .add();

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="User" label="User.java">

```java showLineNumbers
public class User {
  private String name;
  private String email;
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
```

</TabItem>
<TabItem value="styles" label="styles.css">

```css showLineNumbers
.error {
  border: 1px solid #f1aeb5;
  border-radius: 5px;
  background-color: #f8d7da;
  color: #58151c;
  padding: 5px;
}

.form {
  margin: 20px auto;
  max-width: 400px;
}
```

</TabItem>
</Tabs>

In dem obigen Code integriert die E-Mail-Bindung einen benutzerdefinierten Reporter, der Validierungsnachrichten direkt unter dem Eingabefeld anzeigt. Dieses Setup nutzt die Methode `useReporter`, die konfiguriert, wie die Bindung mit Validierungsergebnissen umgeht und sie präsentiert. Diese Methode verknüpft die Validierungslogik effektiv mit der Benutzeroberfläche und stellt sicher, dass alle Validierungsprobleme dem Benutzer sofort sichtbar sind, was die Interaktivität des Formulars und das Benutzererlebnis verbessert.
