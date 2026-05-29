---
sidebar_position: 2
title: Validators
sidebar_class_name: updated-content
_i18n_hash: 996b617e97e439660bbe69f15d6355b9
---
Validators valideren gegevens binnen uw UI-componenten tegen gedefinieerde beperkingen voordat deze gegevens aan het datamodel worden toegevoegd. U kunt validators toepassen om te controleren of gegevens aan bepaalde criteria voldoen, zoals binnen een gespecificeerd bereik vallen, overeenkomen met een patroon of niet leeg zijn.

Validaties worden per binding geconfigureerd, zodat specifieke regels op elk datapunt individueel van toepassing zijn. Elk gegeven ondergaat validatie volgens zijn eigen vereisten.

## Validators toevoegen {#adding-validators}

Voeg validators toe aan een binding met de `useValidator`-methode op de `BindingBuilder`.

```java
context.bind(nameTextField, "name")
  .useValidator(value -> !value.isEmpty(), "Naam mag niet leeg zijn")
  .useValidator(value -> value.length() >= 3, "Naam moet minimaal 3 tekens lang zijn")
  .add();
```

In het bovenstaande voorbeeld verifiëren twee validators dat de naam niet leeg is en dat deze minimaal drie tekens bevat.

:::tip Verwerking van validators
Er is geen limiet aan het aantal validators dat u per binding kunt toevoegen. De binding past de validators toe in de volgorde van invoer en stopt bij de eerste schending.
:::

## Validators implementeren {#implementing-validators}

U kunt aangepaste herbruikbare validators maken door de `Validator<T>`-interface te implementeren, waarbij `T` het type gegevens is dat u wilt valideren. Deze opzet omvat het definiëren van de validatiemethode, die de gegevens controleert en een `ValidationResult` retourneert.

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

### Validators gebruiken in bindings {#using-validators-in-bindings}

Zodra u een validator hebt gedefinieerd, kunt u deze eenvoudig toepassen op relevante bindings in uw app. Dit is bijzonder nuttig voor componenten die gemeenschappelijke validatieregels vereisen in verschillende delen van uw app, zoals e-mailadressen van gebruikers of de sterkte van wachtwoorden.

Om de `EmailValidator` aan een binding toe te passen:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
  .useValidator(new EmailValidator())
  .add();
```

### Overschrijven van validatormeldingen {#overriding-validator-messages}

U kunt de foutmeldingen van validators aanpassen op het moment van binding aan een specifieke UI-component. Dit stelt u in staat om gebruikers meer gedetailleerde of contextueel relevante informatie te geven als de validatie faalt. Aangepaste berichten zijn bijzonder nuttig wanneer dezelfde validator op meerdere componenten van toepassing is, maar verschillende begeleiding vereist op basis van de context waarin deze wordt gebruikt.

Hier is hoe u het standaardbericht van een herbruikbare validator in een binding kunt overschrijven:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
  .useValidator(
    Validator.from(new EmailValidator(), "Aangepast bericht voor ongeldig e-mailadres"))
  .add();
```

In het bovenstaande voorbeeld past de code de `EmailValidator` toe op een e-mailveld met een aangepast foutbericht dat specifiek is afgestemd op dat veld.

:::tip Begrijpen van `Validator.from`
De `Validator.from`-methode wrapt een doorgegeven validator met een nieuwe, waarbij u een aangepast foutbericht kunt specificeren in het geval dat de validator geen aangepaste berichten ondersteunt. Deze techniek is bijzonder nuttig wanneer u dezelfde validatielogica op meerdere componenten moet toepassen, maar met verschillende, contextspecifieke foutmeldingen voor elke instantie.
:::

### Dynamische validatiemeldingen <DocChip chip='since' label='25.12' /> {#dynamic-validation-messages}

Standaard zijn validatiemeldingen statische strings die eenmaal bij de bindingstijd zijn ingesteld. In apps die meerdere talen ondersteunen, worden deze statische berichten niet bijgewerkt wanneer de gebruiker de locatie wijzigt. Om dit op te lossen, accepteren zowel `useValidator` als de factory-methoden van de `Validator` een `Supplier<String>` die elke keer wordt aangeroepen wanneer de validatie faalt, waardoor de boodschap dynamisch kan worden opgelost.

#### Inline validators met dynamische berichten {#inline-validators-with-dynamic-messages}

Geef een `Supplier<String>` door in plaats van een gewone `String` aan `useValidator`:

```java {2,3}
context.bind(nameTextField, "name")
  .useValidator(value -> !value.isEmpty(), () -> t("validation.name.required"))
  .useValidator(value -> value.length() >= 3, () -> t("validation.name.minLength"))
  .add();
```

Elke keer dat de validatie wordt uitgevoerd en de predicate faalt, roept de supplier `t()` aan, die het bericht voor de huidige locatie oplost.

#### Factory-methoden met dynamische berichten {#factory-methods-with-dynamic-messages}

De `Validator.of`- en `Validator.from`-factory-methoden accepteren ook suppliers:

```java {4,10}
// Maak een predicate-gebaseerde validator met een dynamisch bericht
Validator<String> required = Validator.of(
  value -> !value.isEmpty(),
  () -> t("validation.required")
);

// Wikkel een bestaande validator met een dynamisch overschrijfbericht
Validator<String> email = Validator.from(
  new EmailValidator(),
  () -> t("validation.email.invalid")
);
```

#### Locale-bewuste aangepaste validators {#locale-aware-custom-validators}

Voor herbruikbare validators die intern locale-gevoelige berichten moeten produceren, implementeert u de `LocaleAware`-interface. Wanneer de locatie verandert via `BindingContext.setLocale()`, wordt de nieuwe locatie automatisch doorgegeven aan alle validators die deze interface implementeren. De volgende validatietekst produceert dan berichten in de bijgewerkte locatie.
