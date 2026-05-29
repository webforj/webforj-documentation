---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: 663a49d7134273428b9b7648a1fd321e
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

Der `PropertyDescriptorTester` in webforJ vereinfacht das Testen von **drittanbieter Webkomponenten**, die in Ihre App integriert sind. Er validiert, dass die mit [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) definierten Eigenschaften korrekt mit ihren Getter- und Setter-Methoden verknüpft sind und stellt sicher, dass Standardverhalten konsistent gehandhabt wird. Dieses Tool ist besonders nützlich, um die Funktionsfähigkeit von Eigenschaften zu überprüfen, die von Drittanbieterkomponenten bereitgestellt werden, ohne dass sich wiederholende Testlogik erforderlich ist.

<ExperimentalWarning />

## Überblick {#overview}

Bei der Arbeit mit Drittanbieter Webkomponenten ist es wichtig, sicherzustellen, dass Eigenschaften wie erwartet funktionieren. Der `PropertyDescriptorTester` automatisiert diesen Prozess, indem er validiert, dass Eigenschaften:
- Korrekt an ihre Getter- und Setter-Methoden gebunden sind.
- Erwarten, dass Standardwerte und benutzerdefinierte Verhaltensweisen erhalten bleiben.
- Häufige Integrationsprobleme vermeiden, wie z. B. nicht übereinstimmende Eigenschaftsnamen oder inkonsistente Standards.

Das Tool unterstützt Annotations für komplexere Anwendungsfälle, wie das Ausschließen irrelevanter Eigenschaften oder das Definieren benutzerdefinierter Getter- und Setter-Methoden, wodurch es eine vielseitige Option für Integrationstests darstellt.

## Wie `PropertyDescriptorTester` funktioniert {#how-propertydescriptortester-works}

Der Testprozess umfasst mehrere automatisierte Schritte:

1. **Klassenscan**: 
   Der `PropertyDescriptorScanner` identifiziert alle `PropertyDescriptor`-Felder innerhalb einer Komponentenklasse und schließt automatisch mit `@PropertyExclude` annotierte Felder aus.

2. **Methodenauflösung**:
   Standard-Getter- und Setter-Methoden werden basierend auf Namenskonventionen (`get<PropertyName>`/`set<PropertyName>`) erkannt. Für nicht-standardisierte Implementierungen spezifizieren Annotations wie `@PropertyMethods` benutzerdefinierte Methodennamen oder Zielklassen.

3. **Validierung**:
   Standardwerte werden mit der Setter-Methode zugewiesen, über den Getter abgerufen und zum Vergleich herangezogen, um die Richtigkeit sicherzustellen. Jede Abweichung führt zu einem `AssertionError`, das das spezifische Problem hervorhebt.

4. **Fehlerberichterstattung**:
   Der Tester liefert detailliertes Feedback zu allen Validierungsfehlern, wie fehlenden Methoden, inkonsistenten Standards oder falschen Eigenschaftenkonfigurationen.

## Tests mit `PropertyDescriptorTester` schreiben {#writing-tests-with-propertydescriptortester}

Hier ist ein Beispiel, das die grundlegende Eigenschaftsvalidierung für eine `AppLayout`-Komponente demonstriert:

### Beispiel: Grundlegende Validierung {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Standardtitel");

  // Setter und Getter
}
```

#### Testfall {#test-case}

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
      fail("PropertyDescriptor-Test fehlgeschlagen: " + e.getMessage());
    }
  }
}
```

Dieser Test überprüft automatisch:
- Ob `drawerOpened` gültige Getter- und Setter-Methoden hat.
- Ob `headerTitle` auf `"Standardtitel"` standardmäßig gesetzt ist.

## Erweiterte Anwendungsfälle mit Annotations {#advanced-use-cases-with-annotations}

Für komplexere Szenarien unterstützt `PropertyDescriptorTester` Annotations, um Eigenschaften anzupassen oder auszuschließen.

### Eigenschaften mit `@PropertyExclude` ausschließen {#exclude-properties-with-propertyexclude}

Schließen Sie Eigenschaften aus, die auf externen Systemen basieren oder für den Test irrelevant sind. Zum Beispiel:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
  PropertyDescriptor.property("excludedProperty", "Ausgeschlossen");
```

### Methoden mit `@PropertyMethods` anpassen {#customize-methods-with-propertymethods}

Definieren Sie benutzerdefinierte Getter, Setter oder Zielklassen, wenn die Standardbenennungskonventionen nicht zutreffen:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
  PropertyDescriptor.property("customProperty", "Standardwert");
```
