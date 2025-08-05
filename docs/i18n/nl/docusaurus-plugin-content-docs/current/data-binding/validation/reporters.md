---
sidebar_position: 3
title: Reporters
_i18n_hash: 217311f203d2736071c33d6650c74ec2
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

Validation reporters worden gebruikt om feedback over het validatieproces naar de gebruikersinterface te geven. Deze functie is essentieel om gebruikers te informeren over de resultaten van hun invoervalidatie, vooral in complexe formulieren of gegevensintensieve applicaties.

## Wat is een validatierapporteur? {#whats-a-validation-reporter}

Een validatierapporteur is een component die de uitkomsten van validaties verwerkt en weergeeft aan gebruikers. Het fungeert als een brug tussen de validatielogica en de gebruikersinterface, waardoor ervoor gezorgd wordt dat de validatieresultaten effectief en duidelijk worden gecommuniceerd.

:::tip Kerncomponenten Standaard Rapporteur
webforJ omvat de `DefaultBindingReporter`, een standaard bindingsrapporteur die naadloos werkt met alle kerncomponenten van webforJ. Deze ingebouwde rapporteur toont automatisch validatiefouten, waardoor de noodzaak voor een aangepaste implementatie in veel gevallen vervalt. Afhankelijk van de configuratie van de component toont de `DefaultBindingReporter` validatiefouten direct als een popover of inline, direct onder de component. Deze functie vereenvoudigt het rapportageproces van fouten aanzienlijk, zorgt voor duidelijke en directe communicatie van validatiefouten, en verbetert de gebruikerservaring door onmiddellijke, contextgevoelige feedback over invoervalidatie te bieden.
:::

## Validatierapporteurs configureren {#configuring-validation-reporters}

Je kunt validatierapporteurs binnen de bindingcontext configureren om aan te passen hoe berichten worden gepresenteerd. Typisch zou je een validatierapporteur implementeren om validatieresultaten te aggregeren en deze vervolgens op een gebruiksvriendelijke manier weer te geven, zoals het markeren van onjuiste velden, het weergeven van foutmeldingen of het bijwerken van statusindicatoren.

Hier is een voorbeeld van hoe je een validatierapporteur voor een veld kunt instellen

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

In de bovenstaande code bevat de email binding een aangepaste rapporteur die direct validatiewaarschuwingen onder het invoerveld weergeeft. Deze opstelling maakt gebruik van de `useReporter`-methode, die configureert hoe de binding validatieresultaten behandelt en presenteert. Deze methode koppelt effectief de validatielogica aan de gebruikersinterface, waardoor eventuele validatieproblemen onmiddellijk zichtbaar zijn voor de gebruiker, wat de interactiviteit van het formulier en de gebruikerservaring verbetert.
