---
sidebar_position: 4
title: PropertyDescriptorTester
description: >-
  Validate PropertyDescriptor fields on wrapped web components by checking
  getters, setters, and default values with PropertyDescriptorTester.
_i18n_hash: 5b14fba4a11a4da57a032123bd27be6b
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

De `PropertyDescriptorTester` in webforJ vereenvoudigt het testen van **derde partijen webcomponenten** die in uw app zijn geïntegreerd. Het valideert dat eigenschappen die zijn gedefinieerd met [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) correct zijn gekoppeld aan hun getter- en setter-methoden en zorgt ervoor dat standaardgedragingen consistent worden afgehandeld. Dit hulpmiddel is vooral nuttig voor het verifiëren van de functionaliteit van eigenschappen die door derde partijen worden blootgesteld, zonder dat er repetitieve testlogica vereist is.

<ExperimentalWarning />

## Overzicht {#overview}

Bij het werken met derde partijen webcomponenten is het essentieel om ervoor te zorgen dat eigenschappen zich gedragen zoals verwacht. De `PropertyDescriptorTester` automatiseert dit proces door te valideren dat eigenschappen:
- Correct zijn gekoppeld aan hun getter- en setter-methoden.
- Verwachte standaardwaarden en aangepaste gedragingen behouden.
- Veelvoorkomende integratieproblemen vermijden, zoals mismatch in eigenschapsnamen of inconsistente standaarden.

Het hulpmiddel ondersteunt annotaties voor complexere use-cases, zoals het uitsluiten van irrelevante eigenschappen of het definiëren van aangepaste getter- en setter-methoden, waardoor het een veelzijdige optie is voor integratietests.

## Hoe `PropertyDescriptorTester` werkt {#how-propertydescriptortester-works}

Het testproces omvat verschillende geautomatiseerde stappen:

1. **Klasse Scanning**:
   De `PropertyDescriptorScanner` identificeert alle `PropertyDescriptor` velden binnen een componentklasse en sluit automatisch velden uit die zijn geannoteerd met `@PropertyExclude`.

2. **Method Resolutie**:
   Standaard getter- en setter-methoden worden gedetecteerd op basis van naamgevingsconventies (`get<PropertyName>`/`set<PropertyName>`). Voor niet-standaard implementaties specificeren annotaties zoals `@PropertyMethods` aangepaste methodenamen of doelklassen.

3. **Validatie**:
   Standaardwaarden worden toegewezen met de setter-methode, opgehaald met de getter en vergeleken om de juistheid te waarborgen. Elke mismatch veroorzaakt een `AssertionError`, die het specifieke probleem benadrukt.

4. **Foutmelding**:
   De tester biedt gedetailleerde feedback over eventuele validatiefouten, zoals ontbrekende methoden, inconsistente standaarden of verkeerd geconfigureerde eigenschappen.

## Tests schrijven met `PropertyDescriptorTester` {#writing-tests-with-propertydescriptortester}

Hier is een voorbeeld dat basisvalidatie van eigenschappen voor een `AppLayout` component demonstreert:

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
      fail("PropertyDescriptor test mislukt: " + e.getMessage());
    }
  }
}
```

Deze test verifieert automatisch:
- Dat `drawerOpened` geldige getter- en setter-methoden heeft.
- Dat `headerTitle` standaard op `"Default Title"` ligt.

## Geavanceerde use-cases met annotaties {#advanced-use-cases-with-annotations}

Voor complexere scenario's ondersteunt `PropertyDescriptorTester` annotaties om eigenschappen van testen aan te passen of uit te sluiten.

### Uitsluiten van eigenschappen met `@PropertyExclude` {#exclude-properties-with-propertyexclude}

Sluit eigenschappen uit die afhankelijk zijn van externe systemen of niet relevant zijn voor de test. Bijvoorbeeld:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
  PropertyDescriptor.property("excludedProperty", "Uitsluiting");
```

### Methoden aanpassen met `@PropertyMethods` {#customize-methods-with-propertymethods}

Definieer aangepaste getter, setter of doelklasse wanneer de standaard naamgevingsconventies niet van toepassing zijn:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
  PropertyDescriptor.property("customProperty", "Standaard Waarde");
```
