---
sidebar_position: 3
title: Reporters
_i18n_hash: 0cb57295142e37eff340531d120b3566
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

Validation reporters werden verwendet, um Feedback über den Validierungsprozess an die Benutzeroberfläche zu geben. Diese Funktion ist entscheidend, um die Benutzer über die Ergebnisse ihrer Eingabever validation zu informieren, insbesondere in komplexen Formularen oder datensatzintensiven Anwendungen.

## Was ist ein Validierungsreporter? {#whats-a-validation-reporter}

Ein Validierungsreporter ist eine Komponente, die die Ergebnisse von Validierungen verarbeitet und anzeigt. Er fungiert als Brücke zwischen der Validierungslogik und der Benutzeroberfläche und stellt sicher, dass Validierungsergebnisse effektiv und klar kommuniziert werden.

:::tip Kernkomponenten Standardreporter
webforJ beinhaltet den `DefaultBindingReporter`, einen standardmäßigen Bindungsreporter, der nahtlos mit allen Kernkomponenten von webforJ funktioniert. Dieser integrierte Reporter zeigt automatisch Validierungsfehler an und beseitigt die Notwendigkeit einer benutzerdefinierten Implementierung in vielen Fällen. Abhängig von der Konfiguration der Komponente zeigt der `DefaultBindingReporter` Validierungsfehler direkt als Popover oder inline, direkt unterhalb der Komponente an. Diese Funktion vereinfacht den Fehlerberichtsprozess erheblich, stellt eine klare und direkte Kommunikation von Validierungsfehlern sicher und verbessert das Benutzererlebnis, indem sofortiges, kontextsensibles Feedback zur Eingabever validation gegeben wird.
:::

## Konfigurieren von Validierungsreportern {#configuring-validation-reporters}

Sie können Validierungsreporter im Bindungskontext konfigurieren, um anzupassen, wie Nachrichten präsentiert werden. Typischerweise würden Sie einen Validierungsreporter implementieren, um Validierungsergebnisse zu aggregieren und sie dann benutzerfreundlich darzustellen, wie das Hervorheben von falschen Feldern, das Anzeigen von Fehlermeldungen oder das Aktualisieren von Statusindikatoren.

Hier ist ein Beispiel, wie Sie einen Validierungsreporter für ein Feld einrichten können:

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

Im obigen Code enthält die E-Mail-Bindung einen benutzerdefinierten Reporter, der Validierungsnachrichten direkt unter dem Eingabefeld anzeigt. Diese Einrichtung nutzt die Methode `useReporter`, die konfiguriert, wie die Bindung mit Validierungsergebnissen umgeht und sie präsentiert. Diese Methode verknüpft effektiv die Validierungslogik mit der Benutzeroberfläche und stellt sicher, dass alle Validierungsprobleme sofort für den Benutzer sichtbar sind, wodurch die Interaktivität des Formulars und das Benutzererlebnis verbessert werden.
