---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: 9ec1cde5a7d91d75dfd454741a6e8ee3
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

De `PropertyDescriptorTester` in webforJ vereenvoudigt het testen van **derde-partij webcomponenten** die in uw app zijn geïntegreerd. Het valideert of de eigenschappen die zijn gedefinieerd met [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) correct zijn gekoppeld aan hun getter- en setter-methoden en zorgt ervoor dat standaardgedragingen consistent worden afgehandeld. Dit hulpmiddel is vooral nuttig voor het verifiëren van de functionaliteit van eigenschappen die worden blootgesteld door derde-partijcomponenten zonder dat repetitieve testlogica vereist is.

:::warning experimentele functie
De webforJ PropertyDescriptorTester-adapter is momenteel een experimentele functie. Brekende wijzigingen kunnen op elk moment worden geïntroduceerd.
:::

## Overzicht {#overview}

Wanneer u werkt met derde-partij webcomponenten, is het essentieel om ervoor te zorgen dat eigenschappen zich gedragen zoals verwacht. De `PropertyDescriptorTester` automatiseert dit proces door te valideren dat eigenschappen:
- Correct zijn toegewezen aan hun getter- en setter-methoden.
- Verwachte standaardwaarden en aangepaste gedragingen behouden.
- Veelvoorkomende integratieproblemen vermijden, zoals mismatched eigenschapsnamen of inconsistente standaarden.

Het hulpmiddel ondersteunt annotaties voor complexere gebruikscases, zoals het uitsluiten van irrelevante eigenschappen of het definiëren van aangepaste getter- en setter-methoden, waardoor het een veelzijdige optie voor integratietests maakt.

## Hoe `PropertyDescriptorTester` werkt {#how-propertydescriptortester-works}

Het testproces omvat verschillende geautomatiseerde stappen:

1. **Klasse Scannen**: 
   De `PropertyDescriptorScanner` identificeert alle `PropertyDescriptor`-velden binnen een componentklasse en sluit automatisch velden uit die zijn geannoteerd met `@PropertyExclude`.

2. **Methode Resolutie**:
   Standaard getter- en setter-methoden worden gedetecteerd op basis van naamgevingsconventies (`get<PropertyName>`/`set<PropertyName>`). Voor niet-standaard implementaties specificeren annotaties zoals `@PropertyMethods` aangepaste methodenamen of doelklassen.

3. **Validatie**:
   Standaardwaarden worden toegewezen met de setter-methode, opgehaald met de getter en vergeleken om de correctheid te waarborgen. Elke mismatch veroorzaakt een `AssertionError`, die het specifieke probleem benadrukt.

4. **Foutrapportage**:
   De tester biedt gedetailleerde feedback over eventuele validatiefouten, zoals ontbrekende methoden, inconsistente standaarden of misconfiguraties van eigenschappen.

## Tests schrijven met `PropertyDescriptorTester` {#writing-tests-with-propertydescriptortester}

Hier is een voorbeeld dat basisvalidatie van eigenschappen voor een `AppLayout`-component demonstreert:

### Voorbeeld: Basisvalidatie {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Standaard Titel");

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
      fail("PropertyDescriptor test is mislukt: " + e.getMessage());
    }
  }
}
```

Deze test verifieert automatisch:
- Dat `drawerOpened` geldige getter- en setter-methoden heeft.
- Dat `headerTitle` standaard is ingesteld op `"Standaard Titel"`.

## Geavanceerde gebruiksscenario's met annotaties {#advanced-use-cases-with-annotations}

Voor complexere scenario's ondersteunt `PropertyDescriptorTester` annotaties om eigenschappen te personaliseren of uit te sluiten van testen.

### Uitsluiten van eigenschappen met `@PropertyExclude` {#exclude-properties-with-propertyexclude}

Sluit eigenschappen uit die afhankelijk zijn van externe systemen of niet relevant zijn voor de test. Bijvoorbeeld:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
    PropertyDescriptor.property("excludedProperty", "Uitsluitend");
```

### Methoden aanpassen met `@PropertyMethods` {#customize-methods-with-propertymethods}

Definieer aangepaste getter, setter of doelklasse wanneer de standaardnaamgevingsconventies niet van toepassing zijn:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
    PropertyDescriptor.property("customProperty", "Standaard Waarde");
```
