---
sidebar_position: 2
title: Validators
_i18n_hash: 98f40d70b15464d8c7ee48710b07d8fc
---
Validators controleren data binnen uw UI-componenten tegen gedefinieerde beperkingen voordat deze data aan het datamodel wordt toegevoegd. U kunt validators toepassen om ervoor te zorgen dat gegevens aan bepaalde criteria voldoen, zoals binnen een opgegeven bereik vallen, een patroon overeenkomen of niet leeg zijn.

Validaties worden per binding geconfigureerd, waardoor specifieke regels op elk datapunten individueel kunnen worden toegepast. Deze opzet zorgt ervoor dat elk stuk data validatie ondergaat volgens zijn eigen vereisten.

## Validators toevoegen {#adding-validators}

Voeg validators toe aan een binding met de `useValidator`-methode op de `BindingBuilder`.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Naam mag niet leeg zijn")
    .useValidator(value -> value.length() >= 3, "Naam moet minimaal 3 karakters lang zijn")
    .add();
```

In het bovenstaande voorbeeld controleren twee validators of de naam niet leeg is en of deze minstens drie karakters bevat.

:::tip Verwerking van validators
Er is geen limiet aan het aantal validators dat u per binding kunt toevoegen. De binding past de validators toe in de volgorde van invoegen en stopt bij de eerste schending.
:::

## Validators implementeren {#implementing-validators}

U kunt op maat gemaakte herbruikbare validators maken door de `Validator<T>`-interface te implementeren, waarbij `T` het type data is dat u wilt valideren. Deze opzet houdt in dat u de validate-methode definieert, die de data controleert en een `ValidationResult` retourneert.

Hier is een voorbeeld van een herbruikbare validator die controleert of een gebruikers-e-mailadres geldig is.

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

## Validators gebruiken in bindings {#using-validators-in-bindings}

Zodra u een validator hebt gedefinieerd, kunt u deze eenvoudig toepassen op relevante bindings binnen uw app. Dit is bijzonder nuttig voor componenten die gemeenschappelijke validatieregels vereisen in verschillende delen van uw app, zoals gebruikers-e-mailadressen of wachtwoordsterkte.

Om de `EmailValidator` toe te passen op een binding:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## Validatorberichten overschrijven {#overriding-validator-messages}

U kunt de foutberichten van validators aanpassen op het moment van binding aan een specifieke UI-component. Dit stelt u in staat om meer gedetailleerde of contextueel relevante informatie aan de gebruiker te geven als de validatie mislukt. Aangepaste berichten zijn bijzonder nuttig wanneer dezelfde validator op meerdere componenten van toepassing is, maar verschillende gebruikersrichtlijnen vereisen op basis van de context waarin deze wordt gebruikt.

Hier is hoe u het standaardbericht van een herbruikbare validator in een binding kunt overschrijven:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Aangepast bericht voor ongeldig e-mailadres"))
    .add();
```

In het bovenstaande voorbeeld past de code de `EmailValidator` toe op een e-mailveld met een aangepast foutbericht dat specifiek is afgestemd op dat veld. Dit zorgt voor een meer gerichte en nuttige gebruikerservaring als de validatie mislukt.

:::tip Begrijpen van `Validator.from`
De `Validator.from`-methode wikkelt een doorgegeven validator in een nieuwe, waardoor u een aangepast foutbericht kunt opgeven in het geval dat de validator geen aangepaste berichten ondersteunt. Deze techniek is bijzonder nuttig wanneer u dezelfde validatielogica op meerdere componenten moet toepassen, maar met verschillende, context-specifieke foutberichten voor elke instantie.
:::
