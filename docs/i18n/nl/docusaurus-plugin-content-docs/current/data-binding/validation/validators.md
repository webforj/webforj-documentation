---
sidebar_position: 2
title: Validators
_i18n_hash: 3d41925977054029c22c2110455dd419
---
Validators controleren gegevens binnen uw UI-componenten op gedefinieerde beperkingen voordat deze gegevens aan het datamodel worden toegekend. U kunt validators toepassen om ervoor te zorgen dat gegevens aan bepaalde criteria voldoen, zoals binnen een bepaald bereik vallen, een patroon overeenkomen of niet leeg zijn.

Validaties zijn per binding geconfigureerd, zodat specifieke regels voor elk datapunten afzonderlijk kunnen worden toegepast. Deze opstelling zorgt ervoor dat elk gegevenselement wordt gevalideerd volgens zijn eigen vereisten.

## Adding validators {#adding-validators}

Voeg validators toe aan een binding met de `useValidator`-methode op de `BindingBuilder`.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Naam mag niet leeg zijn")
    .useValidator(value -> value.length() >= 3, "Naam moet minstens 3 tekens bevatten")
    .add();
```

In het bovenstaande voorbeeld verifiëren twee validators dat de naam niet leeg is en dat deze uit minstens drie tekens bestaat.

:::tip Validators processing
Er is geen limiet aan het aantal validators dat u per binding kunt toevoegen. De binding past de validators toe in de volgorde van invoer en stopt bij de eerste schending.
:::

## Implementing validators {#implementing-validators}

U kunt herbruikbare maatwerkvalidators maken door de interface `Validator<T>` te implementeren, waarbij `T` het type gegevens is dat u wilt valideren. Deze opstelling omvat het definiëren van de validate-methode, die de gegevens controleert en een `ValidationResult` retourneert.

Hier is een voorbeeld van een herbruikbare validator die controleert of het e-mailadres van een gebruiker geldig is.

```java
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.data.validation.server.validator.Validator;

public class EmailValidator implements Validator<String> {
  private static final String PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

  @Override
  public ValidationResult validate(String value) {
    if (value.matches(PATTERN)) {
        return ValidationResult.valid();
    } else {
        return ValidationResult.invalid("Ongeldig e-mailadres");
    }
  }
}
```

## Using validators in bindings {#using-validators-in-bindings}

Zodra u een validator hebt gedefinieerd, kunt u deze eenvoudig toepassen op relevante bindings binnen uw app. Dit is bijzonder handig voor componenten die gemeenschappelijke validatieregels vereisen in verschillende delen van uw app, zoals gebruikers-e-mailadressen of wachtwoordsterkte.

Om de `EmailValidator` op een binding toe te passen:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## Overriding validator messages {#overriding-validator-messages}

U kunt de foutmeldingen van validators aanpassen op het moment van binding aan een specifieke UI-component. Dit stelt u in staat om meer gedetailleerde of contextueel relevante informatie aan de gebruiker te bieden als de validatie faalt. Aangepaste berichten zijn bijzonder nuttig wanneer dezelfde validator op meerdere componenten wordt toegepast, maar verschillende gebruikersrichtlijnen vereist op basis van de context waarin deze wordt gebruikt.

Hier is hoe u het standaardbericht van een herbruikbare validator in een binding kunt overschrijven:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Aangepast bericht voor ongeldig e-mailadres"))
    .add();
```

In het bovenstaande voorbeeld past de code de `EmailValidator` toe op een e-mailveld met een aangepaste foutmelding die specifiek is afgestemd op dat veld. Dit zorgt voor een meer gerichte en nuttige gebruikerservaring als de validatie faalt.

:::tip Understanding `Validator.from`
De methode `Validator.from` wikkelt een doorgegeven validator met een nieuwe, waarmee u een aangepast foutbericht kunt opgeven voor het geval de validator geen aangepaste berichten ondersteunt. Deze techniek is bijzonder nuttig wanneer u dezelfde validatielogica over meerdere componenten wilt toepassen, maar met verschillende, contextspecifieke foutmeldingen voor elke instantie.
:::
