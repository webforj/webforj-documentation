---
sidebar_position: 2
title: Validators
sidebar_class_name: updated-content
_i18n_hash: 4af002debda2abb59282b5c6a1bf01d7
---
Validators valideren gegevens binnen uw UI-componenten tegen gedefinieerde beperkingen voordat deze gegevens aan het datamodel worden toegekend. U kunt validators toepassen om te verifiëren of gegevens aan bepaalde criteria voldoen, zoals binnen een opgegeven bereik zijn, een patroon overeenkomen of niet leeg zijn.

Validaties worden per binding geconfigureerd, zodat specifieke regels op elk datapunt afzonderlijk van toepassing zijn. Elke gegevensverzameling ondergaat validatie volgens zijn eigen vereisten.

## Validators toevoegen {#adding-validators}

Voeg validators toe aan een binding met behulp van de `useValidator`-methode op de `BindingBuilder`.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Naam mag niet leeg zijn")
    .useValidator(value -> value.length() >= 3, "Naam moet minimaal 3 tekens lang zijn")
    .add();
```

In het bovenstaande voorbeeld verifiëren twee validators dat de naam niet leeg is en dat deze minstens drie tekens bevat.

:::tip Verwerking van validators
Er is geen limiet aan het aantal validators dat u per binding kunt toevoegen. De binding past de validators toe in de volgorde van invoer en stopt bij de eerste schending.
:::

## Validators implementeren {#implementing-validators}

U kunt aangepaste herbruikbare validators maken door de interface `Validator<T>` te implementeren, waarbij `T` het type gegevens is dat u wilt valideren. Deze opzet omvat het definiëren van de validate-methode, die de gegevens controleert en een `ValidationResult` retourneert.

Hier is een voorbeeld van een herbruikbare validator die controleert of een e-mailadres van een gebruiker geldig is.

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

Zodra u een validator hebt gedefinieerd, kunt u deze eenvoudig toepassen op relevante bindings binnen uw app. Dit is vooral nuttig voor componenten die gemeenschappelijke validatieregels vereisen in verschillende delen van uw app, zoals e-mailadressen van gebruikers of de sterkte van wachtwoorden.

Om de `EmailValidator` toe te passen op een binding:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

### Validatorberichten overschrijven {#overriding-validator-messages}

U kunt de foutmeldingen van validators op het punt van binding aan een specifiek UI-component aanpassen. Hiermee kunt u de gebruiker gedetailleerdere of contextueel relevante informatie geven als de validatie mislukt. Aangepaste berichten zijn bijzonder nuttig wanneer dezelfde validator op meerdere componenten van toepassing is, maar verschillende begeleiding vereist op basis van de context waarin deze wordt gebruikt.

Hier is hoe u het standaardbericht van een herbruikbare validator in een binding kunt overschrijven:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Aangepast bericht voor ongeldig e-mailadres"))
    .add();
```

In het bovenstaande voorbeeld past de code de `EmailValidator` toe op een e-mailveld met een aangepast foutbericht dat speciaal voor dat veld is gemaakt.

:::tip Begrijpen van `Validator.from`
De `Validator.from`-methode verpakt een doorgegeven validator met een nieuwe, waarmee u een aangepast foutbericht kunt specificeren als de validator geen aangepaste berichten ondersteunt. Deze techniek is bijzonder nuttig wanneer u dezelfde validatielogica over meerdere componenten moet toepassen, maar met verschillende, contextspecifieke foutmeldingen voor elke instantie.
:::

### Dynamische validatieberichten <DocChip chip='since' label='25.12' /> {#dynamic-validation-messages}

Standaard zijn validatieberichten statische strings die eenmaal op het moment van binding zijn ingesteld. In apps die meerdere talen ondersteunen, worden deze statische berichten niet bijgewerkt wanneer de gebruiker de lokale instellingen verandert. Om dit op te lossen, accepteren zowel `useValidator` als de factory-methoden van `Validator` een `Supplier<String>` die elke keer wordt aangeroepen als de validatie mislukt, waardoor het bericht dynamisch kan worden opgelost.

#### Inline validators met dynamische berichten {#inline-validators-with-dynamic-messages}

Geef een `Supplier<String>` door in plaats van een gewone `String` aan `useValidator`:

```java {2,3}
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), () -> t("validation.name.required"))
    .useValidator(value -> value.length() >= 3, () -> t("validation.name.minLength"))
    .add();
```

Elke keer dat de validatie wordt uitgevoerd en de predicaat mislukt, roept de supplier `t()` aan, die het bericht voor de huidige lokale instellingen oplost.

#### Factory-methoden met dynamische berichten {#factory-methods-with-dynamic-messages}

De factory-methoden `Validator.of` en `Validator.from` accepteren ook leveranciers:

```java {4,10}
// Maak een predicate-gebaseerde validator met een dynamisch bericht
Validator<String> required = Validator.of(
    value -> !value.isEmpty(),
    () -> t("validation.required")
);

// Wikkel een bestaande validator met een dynamisch override-bericht
Validator<String> email = Validator.from(
    new EmailValidator(),
    () -> t("validation.email.invalid")
);
```

#### Locale-bewuste aangepaste validators {#locale-aware-custom-validators}

Voor herbruikbare validators die intern locale-gevoelige berichten moeten genereren, implementeert u de interface `LocaleAware`. Wanneer de locale verandert via `BindingContext.setLocale()`, wordt de nieuwe locale automatisch naar alle validators die deze interface implementeren verspreid. De volgende validatieronde produceert dan berichten in de bijgewerkte locale.
