---
sidebar_position: 4
title: Transformation
_i18n_hash: fccb434a8897618a0197f9883cd94795
---
Data-transformaties zijn een cruciaal kenmerk, dat naadloze conversie tussen de gegevenstypen die in UI-componenten worden gebruikt en die in uw datamodel faciliteert. Deze mogelijkheid zorgt ervoor dat gegevenstypen compatibel en passend geformatteerd zijn bij het verplaatsen van gegevens tussen de frontend en backend van uw applicaties.

:::tip
De transformer-instelling wordt het beste gebruikt wanneer het gegevenstype van de bean-eigenschap niet overeenkomt met het gegevenstype dat door de UI-componenten wordt afgehandeld. Als u eenvoudig gegevens van hetzelfde type moet transformeren, is het configureren van [de getters en setters van de bindingen](bindings#binding-getters-and-setters) de voorkeur.
:::

## Transformers configureren {#configuring-transformers}

U configureert data-transformaties rechtstreeks binnen uw bindingen, waarmee u kunt definiëren hoe gegevens tijdens het databindingsproces moeten worden getransformeerd.

U kunt transformers toevoegen aan een binding met de `useTransformer` methode op de `BindingBuilder`. Transformers moeten de `Transformer` interface implementeren, die vereist dat u methoden definieert voor beide richtingen van de gegevensstroom: van model naar UI en van UI naar model.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

In het bovenstaande voorbeeld configureert de code een `CurrencyTransformer` om conversies tussen het gegevenstype van het model (bijvoorbeeld BigDecimal) en de UI-representatie (bijvoorbeeld een geformatteerde string) af te handelen.

:::info
Elke binding is gekoppeld aan een enkele transformer. Als het transformeren van een waarde meerdere stappen vereist, is het aan te raden uw eigen transformer voor deze stappen te implementeren.
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

Deze transformer faciliteert de behandeling van datums, en zorgt ervoor dat datums correct zijn opgemaakt wanneer ze in de UI worden weergegeven en correct worden geparsed terug in het model.

## Transformers gebruiken in bindingen {#using-transformers-in-bindings}

Zodra u een transformer hebt gedefinieerd, kunt u deze toepassen in meerdere bindingen binnen uw app. Deze aanpak is bijzonder nuttig voor standaard gegevenformaten die consistente behandeling vereisen in verschillende delen van uw app.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Het specificeren van het Bean Property Type

In de `bind`-methode is het essentieel om het type van de bean-eigenschap als derde parameter op te geven wanneer er een discrepantie is tussen het gegevenstype dat door de UI-component wordt weergegeven en het gegevenstype dat in het model wordt gebruikt. Bijvoorbeeld, als de component `startDateField` behandelt als een Java `LocalDate` binnen de component, maar als een `String` in het model is opgeslagen, zorgt het expliciet definiëren van het type als `String.class` ervoor dat het bindingsmechanisme de gegevens tussen de twee verschillende types die door de component en de bean worden gebruikt, nauwkeurig verwerkt en converteert met behulp van de opgegeven transformer en validators.
:::

## Transformaties vereenvoudigen met `Transformer.of` {#simplifying-transforms-with-transformerof}

Het is mogelijk om de implementatie van dergelijke transformaties te vereenvoudigen met de `Transformer.of`-methode die door de `Transformer` wordt aangeboden. Deze methode is syntactische suiker en stelt u in staat om een methode te schrijven die transformaties inline afhandelt, in plaats van een klasse door te geven die de `Transformer`-interface implementeert.

In het volgende voorbeeld handelt de code een checkbox-interactie binnen een reis-app af waarbij gebruikers kunnen kiezen voor aanvullende services zoals autoverhuur. De checkbox-status `boolean` moet worden getransformeerd in een stringrepresentatie `"yes"` of `"no"` die door het backend-model wordt gebruikt.

```java
CheckBox carRental = new CheckBox("Autoverhuur");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // converteer componentwaarde naar modelwaarde
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // converteer modelwaarde naar componentwaarde
        str -> str.equals("yes")
      ), 

      // als de transformatie mislukt, toon dan het volgende
      // bericht
      "Checkbox moet aangevinkt zijn"
  )
  .add();
```
