---
sidebar_position: 3
title: Reporters
_i18n_hash: 217311f203d2736071c33d6650c74ec2
---
<!-- vale off -->

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

<!-- vale on -->

Validierungsberichte werden verwendet, um Rückmeldungen über den Validierungsprozess an die Benutzeroberfläche bereitzustellen. Diese Funktion ist entscheidend, um die Benutzer über die Ergebnisse ihrer Eingangsvalidierung zu informieren, insbesondere bei komplexen Formularen oder datenintensiven Anwendungen.

## Was ist ein Validierungsbericht? {#whats-a-validation-reporter}

Ein Validierungsbericht ist eine Komponente, die die Ergebnisse von Validierungen verarbeitet und den Benutzern anzeigt. Sie fungiert als Brücke zwischen der Validierungslogik und der Benutzeroberfläche und stellt sicher, dass die Validierungsergebnisse effektiv und klar kommuniziert werden.

:::tip Core Components Default Reporter
webforJ umfasst den `DefaultBindingReporter`, einen Standardberichte-Reporter, der nahtlos mit allen KernwebforJ-Komponenten funktioniert. Dieser integrierte Reporter zeigt automatisch Validierungsfehler an und eliminiert in vielen Fällen die Notwendigkeit einer benutzerdefinierten Implementierung. Abhängig von der Konfiguration der Komponente zeigt der `DefaultBindingReporter` Validierungsfehler direkt als Popover oder inline, direkt unterhalb der Komponente an. Diese Funktion vereinfacht den Prozess der Fehlerberichterstattung erheblich, gewährleistet eine klare und direkte Kommunikation von Validierungsfehlern und verbessert die Benutzererfahrung, indem sofortige, kontextsensitive Rückmeldungen zur Eingangsvalidierung bereitgestellt werden.
:::

## Konfigurieren von Validierungsberichten {#configuring-validation-reporters}

Sie können Validierungsberichte im Bindungskontext konfigurieren, um anzupassen, wie Nachrichten präsentiert werden. Typischerweise würden Sie einen Validierungsbericht implementieren, um Validierungsergebnisse zu aggregieren und dann auf benutzerfreundliche Weise anzuzeigen, zum Beispiel durch Hervorheben fehlerhafter Felder, Anzeigen von Fehlermeldungen oder Aktualisieren von Statusanzeigen.

Hier ist ein Beispiel, wie man einen Validierungsbericht für ein Feld einrichtet

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@InlineStyleSheet("context://styles.css")
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

Im obigen Code integriert die E-Mail-Bindung einen benutzerdefinierten Reporter, der die Validierungsnachrichten direkt unter dem Eingabefeld anzeigt. Diese Konfiguration nutzt die Methode `useReporter`, die festlegt, wie die Bindung mit Validierungsergebnissen umgeht und diese präsentiert. Diese Methode verknüpft die Validierungslogik effektiv mit der Benutzeroberfläche und stellt sicher, dass alle Validierungsprobleme sofort für den Benutzer sichtbar sind, wodurch die Interaktivität des Formulars und die Benutzererfahrung verbessert werden.
