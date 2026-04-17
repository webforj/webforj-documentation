---
sidebar_position: 3
title: Reporters
_i18n_hash: 0cb57295142e37eff340531d120b3566
---
Importeren van Tabs uit '@theme/Tabs';
Importeren van TabItem uit '@theme/TabItem';

Validatierapporten worden gebruikt om feedback te geven over het validatieproces aan de gebruikersinterface. Deze functie is essentieel om gebruikers te informeren over de resultaten van hun invoervalidatie, vooral in complexe formulieren of datarijke applicaties.

## Wat is een validatierapport? {#whats-a-validation-reporter}

Een validatierapport is een component die de uitkomsten van de validaties verwerkt en aan gebruikers toont. Het fungeert als een brug tussen de validatielogica en de gebruikersinterface, en zorgt ervoor dat validatieresultaten effectief en duidelijk worden gecommuniceerd.

:::tip Kerncomponenten Standaard Rapporteur
webforJ bevat de `DefaultBindingReporter`, een standaard bindingsrapporteur die naadloos werkt met alle kerncomponenten van webforJ. Deze ingebouwde rapporteur toont automatisch validatiefouten, waardoor de noodzaak voor maatwerkimplementaties in veel gevallen wordt geëlimineerd. Afhankelijk van de configuratie van de component, toont de `DefaultBindingReporter` validatiefouten rechtstreeks als een popover of inline, direct onder de component. Deze functie vereenvoudigt het rapporteren van fouten aanzienlijk, zorgt voor duidelijke en directe communicatie van validatiefouten en verbetert de gebruikerservaring door onmiddellijke, contextgevoelige feedback over invoervalidatie te bieden.
:::

## Configureren van validatierapporten {#configuring-validation-reporters}

Je kunt validatierapporten binnen de bindingcontext configureren om de presentatie van berichten aan te passen. Typisch zou je een validatierapporteur implementeren om validatieresultaten te aggregeren en deze op een gebruiksvriendelijke manier weer te geven, zoals het markeren van onjuiste velden, het tonen van foutmeldingen of het bijwerken van statusindicatoren.

Hier is een voorbeeld van hoe je een validatierapport kunt opzetten voor een veld

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@StyleSheet("ws://css/styles.css")
public class UserRegistration extends App {
  Div errors = new Div();
  TextField emailField = new TextField("Emailadres");

  FlexLayout layout = FlexLayout.create(emailField, errors).vertical().build();

  @Override
  public void run() throws WebforjException {
    errors.addClassName("error");
    errors.setVisible(false);

    layout.addClassName("form");

    BindingContext<User> context = new BindingContext<>(User.class);
    context.bind(emailField, "email")
        .useValidator(
            Validator.from(new EmailValidator(), "Aangepaste melding voor ongeldig e-mailadres"))
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

In de bovenstaande code incorporeert de e-mailbinding een aangepaste rapporteur die validatiemeldingen direct onder het invoerveld weergeeft. Deze opzet maakt gebruik van de `useReporter`-methode, die configureert hoe de binding validatieresultaten afhandelt en presenteert. Deze methode koppelt effectief de validatielogica aan de gebruikersinterface, waardoor eventuele validatieproblemen onmiddellijk zichtbaar zijn voor de gebruiker, wat de interactiviteit en gebruikerservaring van het formulier verbetert.
