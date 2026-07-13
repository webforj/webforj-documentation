---
sidebar_position: 4
title: Transformation
description: >-
  Convert between UI and model data types in webforJ bindings by implementing
  the Transformer interface and wiring it via useTransformer.
_i18n_hash: 7a8064dc7b603cd86ad965a41216c55c
---
Data-transformaties converteren tussen de gegevenstypen die gebruikt worden in UI-componenten en die in jouw datamodel. Dit houdt de gegevenstypen compatibel en correct geformatteerd wanneer gegevens tussen de frontend en backend van jouw applicaties bewegen.

:::tip
De transformer-instelling is het beste te gebruiken wanneer het gegevenstype van de bean-eigenschap niet overeenkomt met het gegevenstype dat door de UI-componenten wordt behandeld. Als je simpelweg gegevens van hetzelfde type moet transformeren, is het configureren van [de getters en setters van de bindings](bindings#binding-getters-and-setters) de aanbevolen aanpak.
:::

## Transformers configureren {#configuring-transformers}

Je configureert data-transformaties rechtstreeks binnen jouw bindings, waardoor je kunt definiëren hoe gegevens tijdens het dataverbindingsproces moeten worden getransformeerd.

Je kunt transformers aan een binding toevoegen met de `useTransformer`-methode op de `BindingBuilder`. Transformers moeten de `Transformer`-interface implementeren, wat vereist dat je methoden definieert voor beide richtingen van de gegevensstroom: van model naar UI en van UI naar model.

```java
context.bind(salaryField, "salary")
  .useTransformer(new CurrencyTransformer())
  .add();
```

In het bovenstaande voorbeeld configureert de code een `CurrencyTransformer` om conversies te verwerken tussen het modelgegevenstype (bijvoorbeeld, BigDecimal) en de UI-representatie (bijvoorbeeld, een geformatteerde string).

:::info
Elke binding is gekoppeld aan een enkele transformer. Als het transformeren van een waarde meerdere stappen vereist, wordt aanbevolen om je eigen transformer voor deze stappen te implementeren.
:::

## Een transformer implementeren {#implementing-a-transformer}

Hier is een voorbeeld van het implementeren van een eenvoudige transformer die converteert tussen een `LocalDate`-model en een `String` UI-representatie:

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
      throw new TransformationException("Ongeldig datumformaat");
    }
  }

  @Override
  public String transformToModel(LocalDate componentValue) {
    try {
      return componentValue.format(formatter);
    } catch (Exception e) {
      throw new TransformationException("Ongeldig datumformaat");
    }
  }
}
```

Deze transformer verwerkt datums, formatteert datums wanneer ze in de UI worden weergegeven en parseert ze terug naar het model.

### Transformers gebruiken in bindings {#using-transformers-in-bindings}

Zodra je een transformer hebt gedefinieerd, kun je deze toepassen op meerdere bindings binnen jouw app. Deze aanpak is bijzonder nuttig voor standaard gegevensformaten die consistente behandeling vereisen in verschillende delen van jouw app.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
  .useTransformer(new DateTransformer())
  .add();
```

:::info Het specificeren van het Bean-eigenschapstype

In de `bind`-methode is het essentieel om het type van de bean-eigenschap als het derde parameter op te geven wanneer er een discrepantie is tussen het gegevenstype dat door de UI-component wordt weergegeven en het gegevenstype dat in het model wordt gebruikt. Bijvoorbeeld, als de component `startDateField` behandelt als een Java `LocalDate` binnen de component maar is opgeslagen als een `String` in het model, dan vertelt het expliciet definiëren van het type als `String.class` de verbindingsmechanisme om de gegevens nauwkeurig te verwerken en te converteren tussen de twee verschillende types die door de component en de bean worden gebruikt met behulp van de opgegeven transformer en validators.
:::

### Transformeren vereenvoudigen met `Transformer.of` {#simplifying-transforms-with-transformerof}

Het is mogelijk om de implementatie van dergelijke transformaties te vereenvoudigen met de `Transformer.of`-methode die door de `Transformer` wordt aangeboden. Deze methode is syntactische suiker en stelt je in staat om een methode te schrijven die transformaties inline afhandelt, in plaats van een klasse door te geven die de `Transformer`-interface implementeert.

In het volgende voorbeeld verwerkt de code een checkbox-interactie binnen een reisapp waar gebruikers kunnen kiezen voor extra diensten zoals autoverhuur. De checkboxstatus `boolean` moet worden getransformeerd naar een stringrepresentatie `"yes"` of `"no"` die het backendmodel gebruikt.

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

      // in het geval de transformatie mislukt, toon het volgende
      // bericht
      "Checkbox moet aangevinkt zijn"
  )
  .add();
```

### Dynamische transformer foutmeldingen <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

Standaard is het foutbericht dat wordt weergegeven wanneer een transformatie mislukt een statische string. In apps die meerdere talen ondersteunen, kun je in plaats daarvan een `Supplier<String>` doorgeven zodat het bericht elke keer dat de transformatie mislukt, wordt opgelost:

```java {7}
context.bind(quantityField, "quantity", Integer.class)
  .useTransformer(
    Transformer.of(
      str -> Integer.parseInt(str),
      val -> String.valueOf(val)
    ),
    () -> t("validatie.quantity.invalid")
  )
  .add();
```

De leverancier wordt alleen aangeroepen wanneer de transformatie een `TransformationException` gooit. Dit betekent dat het bericht altijd de huidige locale weerspiegelt op het moment van falen.

#### Locale-bewuste transformers {#locale-aware-transformers}

Voor herbruikbare transformers die intern toegang tot de huidige locale nodig hebben (bijvoorbeeld, om nummers of datums volgens regionale conventies te formatteren), implementeer je de `LocaleAware`-interface. Wanneer de locale verandert via `BindingContext.setLocale()`, verspreidt de context automatisch de nieuwe locale naar transformers die deze interface implementeren.
