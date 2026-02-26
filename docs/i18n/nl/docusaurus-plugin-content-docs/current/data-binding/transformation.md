---
sidebar_position: 4
title: Transformation
sidebar_class_name: updated-content
_i18n_hash: 3b1655fdbfa9c303ae1445beee9ee327
---
Data-transformaties converteren tussen de datatypes die in UI-componenten worden gebruikt en die in jouw datamodel. Dit houdt de datatypes compatibel en op de juiste manier geformatteerd bij het verplaatsen van gegevens tussen de frontend en backend van jouw applicaties.

:::tip
De transformer-instelling is het beste te gebruiken wanneer het datatype van de bean-eigenschap niet overeenkomt met het datatype dat door de UI-componenten wordt afgehandeld. Als je eenvoudig gegevens van hetzelfde type moet transformeren, is het configureren van [de getters en setters van de bindings](bindings#binding-getters-and-setters) de aanbevolen aanpak.
:::

## Transformers configureren {#configuring-transformers}

Je configureert datatransformaties direct binnen je bindings, zodat je kunt definiëren hoe gegevens moeten worden getransformeerd tijdens het data binding-proces.

Je kunt transformers aan een binding toevoegen met de `useTransformer`-methode op de `BindingBuilder`. Transformers moeten de `Transformer`-interface implementeren, die vereist dat methoden worden gedefinieerd voor beide richtingen van de gegevensstroom: van model naar UI en van UI naar model.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

In het bovenstaande voorbeeld configureert de code een `CurrencyTransformer` om de conversies tussen het model datatype (bijvoorbeeld, BigDecimal) en de UI-representatie (bijvoorbeeld, een geformatteerde string) te verwerken.

:::info
Elke binding is geassocieerd met een enkele transformer. Als het transformeren van een waarde meerdere stappen vereist, wordt aanbevolen om je eigen transformer voor deze stappen te implementeren.
:::

## Een transformer implementeren {#implementing-a-transformer}

Hier is een voorbeeld van het implementeren van een eenvoudige transformer die converteert tussen een `LocalDate`-model en een `String`-UI-representatie:

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.webforj.data.transformation.TransformationException;
import com.webforj.data.transformation.transformer.Transformer;

public class DateTransformer implements Transformer<LocalDate, String> {
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public LocalDate transformToComponent(String modelValue) {
    try {
      return LocalDate.parse(modelValue, formatter);
    } catch (Exception e) {
      throw new TransformationException("Ongeldig datumn formaat");
    }
  }

  @Override
  public String transformToModel(LocalDate componentValue) {
    try {
      return componentValue.format(formatter);
    } catch (Exception e) {
      throw new TransformationException("Ongeldig datumn formaat");
    }
  }
}
```

Deze transformer verwerkt datumvelden, formatteert datums wanneer deze in de UI worden weergegeven en parseert ze weer terug in het model.

### Transformers gebruiken in bindings {#using-transformers-in-bindings}

Zodra je een transformer hebt gedefinieerd, kun je deze toepassen op meerdere bindings binnen je app. Deze aanpak is bijzonder nuttig voor standaardgegevensformaten die consistente verwerking vereisen in verschillende delen van je app.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info De Bean-eigenschapstype specificeren

In de `bind`-methode is het essentieel om het type van de bean-eigenschap als de derde parameter op te geven wanneer er een discrepantie is tussen het datatype dat door de UI-component wordt weergegeven en het datatype dat in het model wordt gebruikt. Als de component bijvoorbeeld `startDateField` behandelt als een Java `LocalDate` binnen de component maar als een `String` in het model is opgeslagen, geeft het expliciet definiëren van het type als `String.class` aan dat het bindingmechanisme de gegevens nauwkeurig moet verwerken en converteren tussen de twee verschillende types die door de component en de bean worden gebruikt met behulp van de opgegeven transformer en validators.
:::

### Transformaties vereenvoudigen met `Transformer.of` {#simplifying-transforms-with-transformerof}

Het is mogelijk om de implementatie van dergelijke transformaties te vereenvoudigen met de `Transformer.of`-methode die door de `Transformer` wordt aangeboden. Deze methode is syntactische suiker en stelt je in staat om een methode te schrijven die inline transformaties afhandelt, in plaats van een klasse door te geven die de `Transformer`-interface implementeert.

In het volgende voorbeeld handelt de code een checkbox-interactie binnen een reisapp af waarin gebruikers kunnen kiezen voor aanvullende diensten zoals autoverhuur. De checkbox-status `boolean` moet worden getransformeerd naar een stringrepresentatie `"yes"` of `"no"` die het backend-model gebruikt.

```java
CheckBox carRental = new CheckBox("Autohuur");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // converteer componentwaarde naar modelwaarde
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // converteer modelwaarde naar componentwaarde
        str -> str.equals("yes")
      ),

      // in geval van mislukte transformatie, toon het volgende
      // bericht
      "Checkbox moet aangevinkt zijn"
  )
  .add();
```

### Dynamische transformerfoutmeldingen <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

Standaard is de foutmelding die wordt weergegeven wanneer een transformatie mislukt een statische string. In apps die meerdere talen ondersteunen, kun je in plaats daarvan een `Supplier<String>` doorgeven, zodat de boodschap steeds opnieuw wordt opgelost als de transformatie mislukt:

```java {7}
context.bind(quantityField, "quantity", Integer.class)
    .useTransformer(
        Transformer.of(
            str -> Integer.parseInt(str),
            val -> String.valueOf(val)
        ),
        () -> t("validation.quantity.invalid")
    )
    .add();
```

De leverancier wordt alleen aangeroepen wanneer de transformatie een `TransformationException` gooit. Dit betekent dat de boodschap altijd de huidige locale reflecteert op het moment van mislukking.

#### Locale-bewuste transformers {#locale-aware-transformers}

Voor herbruikbare transformers die intern toegang moeten hebben tot de huidige locale (bijvoorbeeld om getallen of datums volgens regionale conventies te formatteren), implementeer je de `LocaleAware`-interface. Wanneer de locale verandert via `BindingContext.setLocale()`, verspreidt de context automatisch de nieuwe locale naar transformers die deze interface implementeren.
