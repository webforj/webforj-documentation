---
sidebar_position: 4
title: Transformation
sidebar_class_name: updated-content
_i18n_hash: e03ca3208470e53be7128ffb972c2670
---
Data-transformaties zetten de datatypes die in UI-componenten worden gebruikt om naar die in uw datamodel. Dit houdt de datatypes compatibel en goed geformatteerd wanneer data tussen de frontend en backend van uw applicaties wordt verplaatst.

:::tip
De transformerinstelling wordt het best gebruikt wanneer het datatype van de bean-eigenschap niet overeenkomt met het datatype dat door de UI-componenten wordt behandeld. Als u simpelweg data van hetzelfde type moet transformeren, is het configureren van [de getters en setters van de bindings](bindings#binding-getters-and-setters) de aanbevolen aanpak.
:::

## Transformers configureren {#configuring-transformers}

U configureert data-transformaties rechtstreeks binnen uw bindings, waarmee u kunt definiëren hoe gegevens moeten worden getransformeerd tijdens het data-bindingsproces.

U kunt transformers toevoegen aan een binding met de `useTransformer`-methode op de `BindingBuilder`. Transformers moeten de `Transformer`-interface implementeren, wat vereist dat u methoden definieert voor beide richtingen van de datastroom: van model naar UI en van UI naar model.

```java
context.bind(salaryField, "salary")
  .useTransformer(new CurrencyTransformer())
  .add();
```

In het bovenstaande voorbeeld configureert de code een `CurrencyTransformer` om conversies tussen het modeldatatype (bijvoorbeeld BigDecimal) en de UI-representatie (bijvoorbeeld een geformatteerde string) af te handelen.

:::info
Elke binding is gekoppeld aan een enkele transformer. Als het transformeren van een waarde meerdere stappen vereist, is het aan te raden om uw eigen transformer voor deze stappen te implementeren.
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

Deze transformer behandelt datumvelden, formatteert data wanneer deze in de UI wordt weergegeven en parseert ze terug naar het model.

### Transformers gebruiken in bindings {#using-transformers-in-bindings}

Nadat u een transformer hebt gedefinieerd, kunt u deze toepassen op meerdere bindings binnen uw app. Deze aanpak is bijzonder nuttig voor standaarddataformaten die consistente behandeling vereisen in verschillende delen van uw app.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
  .useTransformer(new DateTransformer())
  .add();
```

:::info Het specificeren van het bean-eigenschapstype

In de `bind`-methode is het essentieel om het type van de bean-eigenschap als derde parameter op te geven wanneer er een discrepantie is tussen het datatype dat door de UI-component wordt weergegeven en het datatype dat in het model wordt gebruikt. Bijvoorbeeld, als de component `startDateField` behandelt als een Java `LocalDate` binnen de component, maar als een `String` in het model is opgeslagen, geeft u expliciet het type als `String.class` op om het bindingmechanisme te vertellen de data tussen de twee verschillende types die door de component en de bean worden gebruikt, nauwkeurig te verwerken en te converteren met behulp van de opgegeven transformer en validatoren.
:::

### Transformeren vereenvoudigen met `Transformer.of` {#simplifying-transforms-with-transformerof}

Het is mogelijk om de implementatie van dergelijke transformaties te vereenvoudigen met de `Transformer.of`-methode die door de `Transformer` wordt aangeboden. Deze methode is syntactische suiker en stelt u in staat om een methode te schrijven die de transformaties inline afhandelt, in plaats van een klasse door te geven die de `Transformer`-interface implementeert.

In het volgende voorbeeld behandelt de code een checkbox-interactie binnen een reisapp waar gebruikers kunnen kiezen voor extra services zoals autoverhuur. De checkbox-toestand `boolean` moet worden getransformeerd in een stringrepresentatie `"ja"` of `"nee"` die het backendmodel gebruikt.

```java
CheckBox carRental = new CheckBox("Autoverhuur");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // converteer componentwaarde naar modelwaarde
        bool -> Boolean.TRUE.equals(bool) ? "ja" : "nee",
        // converteer modelwaarde naar componentwaarde
        str -> str.equals("ja")
      ),

      // in het geval het transformeren mislukt, toon het volgende
      // bericht
      "Checkbox moet aangevinkt zijn"
  )
  .add();
```

### Dynamische transformer foutmeldingen <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

Standaard is het foutbericht dat wordt getoond wanneer een transformatie mislukt een statische string. In apps die meerdere talen ondersteunen, kunt u in plaats daarvan een `Supplier<String>` doorgeven zodat het bericht elke keer dat de transformatie mislukt, wordt opgelost:

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

De leverancier wordt alleen aangeroepen wanneer de transformatie een `TransformationException` gooit. Dit betekent dat het bericht altijd de huidige locale op het moment van falen weerspiegelt.

#### Locale-bewuste transformers {#locale-aware-transformers}

Voor herbruikbare transformers die toegang nodig hebben tot de huidige locale intern (bijvoorbeeld om getallen of datums volgens regionale conventies te formatteren), implementeert u de `LocaleAware`-interface. Wanneer de locale verandert via `BindingContext.setLocale()`, wordt de nieuwe locale automatisch naar transformers die deze interface implementeren doorgegeven.
