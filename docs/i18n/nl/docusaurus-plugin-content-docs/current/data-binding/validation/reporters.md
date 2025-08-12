---
sidebar_position: 3
title: Reporters
_i18n_hash: c563479cec7e1fe29d483bcd121bb5fc
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

Validation reporters worden gebruikt om feedback te geven over het validatieproces aan de gebruikersinterface. Deze functie is essentieel voor het informeren van gebruikers over de resultaten van hun invoervalidatie, vooral in complexe formulieren of data-intensieve toepassingen.

## Wat is een validatie rapporteur? {#whats-a-validation-reporter}

Een validatie rapporteur is een component die de uitkomsten van validaties verwerkt en aan gebruikers toont. Het fungeert als een brug tussen de validatielogica en de gebruikersinterface, waardoor ervoor gezorgd wordt dat validatieresultaten effectief en duidelijk worden gecommuniceerd.

:::tip Kerncomponenten Standaard Rapporteur
webforJ omvat de `DefaultBindingReporter`, een standaard bindingsrapporteur die naadloos werkt met alle kern webforJ-componenten. Deze ingebouwde rapporteur toont automatisch validatiefouten, waardoor de noodzaak voor op maat gemaakte implementatie in veel gevallen vervalt. Afhankelijk van de configuratie van de component, toont de `DefaultBindingReporter` validatiefouten rechtstreeks als een popover of inline, direct onder de component. Deze functie vereenvoudigt het proces van foutmelding aanzienlijk, zorgt voor duidelijke en directe communicatie van validatiefouten en verbetert de gebruikerservaring door onmiddellijke, contextgevoelige feedback over invoervalidatie te bieden.
:::

## Validatie rapporteurs configureren {#configuring-validation-reporters}

Je kunt validatie rapporteurs binnen de bindingcontext configureren om aan te passen hoe berichten worden gepresenteerd. Typisch zou je een validatie rapporteur implementeren om validatieresultaten te aggregeren en deze op een gebruiksvriendelijke manier weer te geven, zoals het markeren van onjuiste velden, het tonen van foutmeldingen of het bijwerken van statusindicatoren.

Hier is een voorbeeld van hoe je een validatie rapporteur voor een veld instelt.

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@InlineStyleSheet("context://styles.css")
public class UserRegistration extends App {
  Div errors = new Div();
  TextField emailField = new TextField("E-mailadres");

  FlexLayout layout = FlexLayout.create(emailField, errors).vertical().build();

  @Override
  public void run() throws WebforjException {
    errors.addClassName("error");
    errors.setVisible(false);

    layout.addClassName("form");

    BindingContext<User> context = new BindingContext<>(User.class);
    context.bind(emailField, "email")
        .useValidator(
            Validator.from(new EmailValidator(), "Aangepaste boodschap voor ongeldig e-mailadres"))
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

In de bovenstaande code omvat de e-mailbinding een aangepaste rapporteur die validatieberichten direct onder het invoerveld toont. Deze opzet maakt gebruik van de `useReporter`-methode, die configureert hoe de binding validatieresultaten behandelt en presenteert. Deze methode koppelt effectief de validatielogica aan de gebruikersinterface, waardoor eventuele validatieproblemen onmiddellijk zichtbaar zijn voor de gebruiker, wat de interactiviteit van het formulier en de gebruikerservaring verbetert.
