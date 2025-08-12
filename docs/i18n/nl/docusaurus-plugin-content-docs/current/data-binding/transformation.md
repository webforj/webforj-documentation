---
sidebar_position: 4
title: Transformation
_i18n_hash: fe3acbd17750ab0092cbc3609b967969
---
Data-transformaties zijn een cruciaal kenmerk dat naadloze conversie tussen de datatypes die in UI-componenten worden gebruikt en die in uw datamodel mogelijk maakt. Deze mogelijkheid zorgt ervoor dat datatypes compatibel en op de juiste manier opgemaakt zijn bij het verplaatsen van gegevens tussen de frontend en backend van uw applicaties.

:::tip
De transformerinstelling is het beste te gebruiken wanneer het datatype van de bean-eigenschap niet overeenkomt met het datatype dat door de UI-componenten wordt behandeld. Als u gewoon gegevens van hetzelfde type moet transformeren, is het configureren van [de getters en setters van de bindings](bindings#binding-getters-and-setters) de aanbevolen aanpak.
:::

## Configureren van transformers {#configuring-transformers}

U configureert datatransformaties rechtstreeks binnen uw bindings, waarmee u definieert hoe gegevens moeten worden getransformeerd tijdens het gegevensbindingsproces.

U kunt transformers aan een binding toevoegen met behulp van de `useTransformer`-methode op de `BindingBuilder`. Transformers moeten de `Transformer`-interface implementeren, wat vereist dat methoden voor beide richtingen van gegevensstroom worden gedefinieerd: van model naar UI en van UI naar model.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

In het bovenstaande voorbeeld configureert de code een `CurrencyTransformer` om conversies tussen het datamodeltype (bijvoorbeeld BigDecimal) en de UI-weergave (bijvoorbeeld een opgemaakte string) af te handelen.

:::info
Elke binding is verbonden met een enkele transformer. Als het transformeren van een waarde meerdere stappen vereist, is het aan te raden om uw eigen transformer voor deze stappen te implementeren.
:::

## Implementeren van een transformer {#implementing-a-transformer}

Hier is een voorbeeld van het implementeren van een eenvoudige transformer die converteert tussen een `LocalDate` model en een `String` UI-weergave:

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

Deze transformer vergemakkelijkt de verwerking van datumvelden, waardoor datums correct worden opgemaakt wanneer ze in de UI worden weergegeven en correct worden teruggeparsed naar het model.

## Transformers gebruiken in bindings {#using-transformers-in-bindings}

Zodra u een transformer hebt gedefinieerd, kunt u deze toepassen op meerdere bindings binnen uw app. Deze aanpak is bijzonder nuttig voor standaardgegevensformaten die consistente verwerking vereisen in verschillende delen van uw app.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Specificeer het type van de Bean-eigenschap

Bij de `bind`-methode is het essentieel om het type van de bean-eigenschap als derde parameter op te geven wanneer er een discrepantie is tussen het datatype dat door de UI-component wordt weergegeven en het datatype dat in het model wordt gebruikt. Als het component `startDateField` als een Java `LocalDate` binnen het component behandelt, maar als een `String` in het model is opgeslagen, zorgt het expliciet definiëren van het type als `String.class` ervoor dat het bindingmechanisme de gegevens nauwkeurig verwerkt en converteert tussen de twee verschillende types die door het component en de bean worden gebruikt met behulp van de opgegeven transformer en validators.
:::

## Transformaties vereenvoudigen met `Transformer.of` {#simplifying-transforms-with-transformerof}

Het is mogelijk om de implementatie van dergelijke transformaties te vereenvoudigen met behulp van de `Transformer.of`-methode die door de `Transformer` wordt aangeboden. Deze methode is syntactische suiker en stelt u in staat om een methode te schrijven die transformaties inline afhandelt, in plaats van een klasse die de `Transformer`-interface implementeert door te geven.

In het volgende voorbeeld behandelt de code een checkbox-interactie binnen een reisapp waar gebruikers kunnen kiezen voor extra diensten zoals autoverhuur. De staat van de checkbox `boolean` moet worden omgezet in een stringrepresentatie `"yes"` of `"no"` die het backendmodel gebruikt.

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

      // in geval van falen van de transformatie, toon het volgende
      // bericht
      "Checkbox moet aangevinkt zijn"
  )
  .add();
```
