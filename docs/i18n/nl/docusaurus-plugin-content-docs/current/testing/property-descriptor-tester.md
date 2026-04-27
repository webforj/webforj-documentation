---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: 663a49d7134273428b9b7648a1fd321e
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

De `PropertyDescriptorTester` in webforJ vereenvoudigt het testen van **derde partij webcomponenten** die in uw app zijn geïntegreerd. Het valideert dat eigenschappen gedefinieerd met [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) correct zijn gekoppeld aan hun getter- en setter-methoden en zorgt ervoor dat defaultgedragingen consistent worden behandeld. Deze tool is bijzonder nuttig voor het verifiëren van de functionaliteit van eigenschappen die door derde partijen worden blootgesteld, zonder dat repetitieve testlogica vereist is.

<ExperimentalWarning />

## Overzicht {#overview}

Bij het werken met derde partij webcomponenten is het essentieel dat eigenschappen zich gedragen zoals verwacht. De `PropertyDescriptorTester` automatiseert dit proces door te valideren dat eigenschappen:
- Correct zijn gekoppeld aan hun getter- en setter-methoden.
- Verwachte defaultwaarden en aangepaste gedragingen behouden.
- Veelvoorkomende integratieproblemen vermijden, zoals mismatches in eigenschapsnamen of inconsistente defaults.

De tool ondersteunt annotaties voor complexere gebruiksscenario's, zoals het uitsluiten van irrelevante eigenschappen of het definiëren van aangepaste getter- en setter-methoden, waardoor het een veelzijdige optie is voor integratietests.

## Hoe `PropertyDescriptorTester` werkt {#how-propertydescriptortester-works}

Het testproces omvat verschillende geautomatiseerde stappen:

1. **Klasse Scannen**: 
   De `PropertyDescriptorScanner` identificeert alle `PropertyDescriptor`-velden binnen een componentklasse en sluit automatisch velden uit die zijn geannoteerd met `@PropertyExclude`.

2. **Methode Resolutie**:
   Standaard getter- en setter-methoden worden gedetecteerd op basis van naamgevingsconventies (`get<PropertyName>`/`set<PropertyName>`). Voor niet-standaard implementaties specificeren annotaties zoals `@PropertyMethods` aangepaste methodenamen of doelklassen.

3. **Validatie**:
   Defaultwaarden worden toegewezen met behulp van de setter-methode, opgehaald met de getter en vergeleken om de correctheid te waarborgen. Elke mismatch veroorzaakt een `AssertionError`, die het specifieke probleem markeert.

4. **Foutmeldingen**:
   De tester biedt gedetailleerde feedback over eventuele validatiefouten, zoals ontbrekende methoden, inconsistente defaults of foute configuraties van eigenschappen.

## Tests schrijven met `PropertyDescriptorTester` {#writing-tests-with-propertydescriptortester}

Hier is een voorbeeld dat de basisvalidatie van eigenschappen voor een `AppLayout`-component demonstreert:

### Voorbeeld: Basisvalidatie {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Default Title");

  // setters en getters
}
```

#### Testgeval {#test-case}

```java title="MyComponentTest.java"
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class MyComponentTest {

  MyComponent component = new MyComponent();

  @Test
  void validateProperties() {
    try {
      PropertyDescriptorTester.run(MyComponent.class, component);
    } catch (Exception e) {
      fail("PropertyDescriptor-test is mislukt: " + e.getMessage());
    }
  }
}
```

Deze test verifieert automatisch:
- Dat `drawerOpened` geldige getter- en setter-methoden heeft.
- Dat `headerTitle` standaard is ingesteld op `"Default Title"`.

## Geavanceerde gebruiksgevallen met annotaties {#advanced-use-cases-with-annotations}

Voor complexere scenario's ondersteunt `PropertyDescriptorTester` annotaties om eigenschappen aan te passen of uit te sluiten van testen.

### Uitsluiten van eigenschappen met `@PropertyExclude` {#exclude-properties-with-propertyexclude}

Sluit eigenschappen uit die afhankelijk zijn van externe systemen of niet relevant zijn voor de test. Bijvoorbeeld:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
  PropertyDescriptor.property("excludedProperty", "Uitgesloten");
```

### Methodes aanpassen met `@PropertyMethods` {#customize-methods-with-propertymethods}

Definieer aangepaste getter-, setter- of doelklasse wanneer de standaard naamgevingsconventies niet van toepassing zijn:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
  PropertyDescriptor.property("customProperty", "Standaardwaarde");
```
