---
sidebar_position: 3
title: Reporters
_i18n_hash: c563479cec7e1fe29d483bcd121bb5fc
---
Validation Reporter werden verwendet, um Feedback über den Validierungsprozess an die Benutzeroberfläche bereitzustellen. Diese Funktion ist entscheidend, um die Benutzer über die Ergebnisse ihrer Eingabevalidierung zu informieren, insbesondere in komplexen Formularen oder datenintensiven Anwendungen.

## Was ist ein Validation Reporter? {#whats-a-validation-reporter}

Ein Validation Reporter ist eine Komponente, die die Ergebnisse von Validierungen verarbeitet und den Benutzern anzeigt. Er fungiert als Brücke zwischen der Validierungslogik und der Benutzeroberfläche und sorgt dafür, dass die Validierungsergebnisse effektiv und klar kommuniziert werden.

:::tip Kernkomponenten Standard-Reporter
webforJ enthält den `DefaultBindingReporter`, einen standardmäßigen Bindungsreporter, der nahtlos mit allen Kernkomponenten von webforJ funktioniert. Dieser integrierte Reporter zeigt automatisch Validierungsfehler an, wodurch in vielen Fällen die Notwendigkeit einer benutzerdefinierten Implementierung entfällt. Abhängig von der Konfiguration der Komponente zeigt der `DefaultBindingReporter` Validierungsfehler direkt als Popover oder inline direkt unterhalb der Komponente an. Diese Funktion vereinfacht den Prozess der Fehlerberichterstattung erheblich, sorgt für eine klare und direkte Kommunikation von Validierungsfehlern und verbessert die Benutzererfahrung, indem sie sofortiges, kontextsensitive Feedback zur Eingabevalidierung bereitstellt.
:::

## Konfigurieren von Validation Reportern {#configuring-validation-reporters}

Sie können Validation Reporter innerhalb des Bindungskontexts konfigurieren, um anzupassen, wie Nachrichten präsentiert werden. Typischerweise würden Sie einen Validation Reporter implementieren, um die Validierungsergebnisse zu aggregieren und diese dann benutzerfreundlich anzuzeigen, z. B. durch Hervorheben falscher Felder, Anzeigen von Fehlermeldungen oder Aktualisieren von Statusanzeigen.

Hier ist ein Beispiel, wie Sie einen Validation Reporter für ein Feld einrichten können:

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

In dem obigen Code integriert die E-Mail-Bindung einen benutzerdefinierten Reporter, der Validierungsnachrichten direkt unter dem Eingabefeld anzeigt. Diese Einrichtung nutzt die Methode `useReporter`, die konfiguriert, wie die Bindung Validierungsergebnisse behandelt und präsentiert. Diese Methode verbindet die Validierungslogik effektiv mit der Benutzeroberfläche und stellt sicher, dass Validierungsprobleme dem Benutzer sofort sichtbar sind, wodurch die Interaktivität und Benutzererfahrung des Formulars verbessert werden.
