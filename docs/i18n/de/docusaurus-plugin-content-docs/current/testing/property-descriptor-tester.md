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

Der `PropertyDescriptorTester` in webforJ vereinfacht das Testen von **drittanbieter Webkomponenten**, die in Ihre Anwendung integriert sind. Er validiert, dass die mit [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) definierten Eigenschaften korrekt mit ihren Getter- und Setter-Methoden verbunden sind und stellt sicher, dass Standardverhalten konsistent behandelt wird. Dieses Tool ist besonders nützlich, um die Funktionalität von Eigenschaften zu überprüfen, die von Drittanbieterkomponenten exponiert werden, ohne dass wiederholte Testlogik erforderlich ist.

<ExperimentalWarning />

## Übersicht {#overview}

Beim Arbeiten mit drittanbieter Webkomponenten ist es entscheidend, sicherzustellen, dass Eigenschaften wie erwartet funktionieren. Der `PropertyDescriptorTester` automatisiert diesen Prozess, indem er validiert, dass Eigenschaften:
- Richtig mit ihren Getter- und Setter-Methoden verknüpft sind.
- Erwartete Standardwerte und benutzerdefinierte Verhaltensweisen beibehalten.
- Häufige Integrationsprobleme vermeiden, wie z. B. nicht übereinstimmende Eigenschaftsnamen oder inkonsistente Standards.

Das Tool unterstützt Annotationen für komplexere Anwendungsfälle, wie das Ausschließen irrelevanter Eigenschaften oder das Definieren benutzerdefinierter Getter- und Setter-Methoden, was es zu einer vielseitigen Option für Integrationstests macht.

## So funktioniert `PropertyDescriptorTester` {#how-propertydescriptortester-works}

Der Testprozess umfasst mehrere automatisierte Schritte:

1. **Klassen-Scanning**:
   Der `PropertyDescriptorScanner` identifiziert alle `PropertyDescriptor`-Felder innerhalb einer Komponentenklasse und schließt automatisch Felder aus, die mit `@PropertyExclude` annotiert sind.

2. **Methodenauflösung**:
   Standardgetter- und -settermethoden werden basierend auf Namenskonventionen (z. B. `get<PropertyName>`/`set<PropertyName>`) erkannt. Für nicht-standardisierte Implementierungen geben Annotationen wie `@PropertyMethods` benutzerdefinierte Methodennamen oder Zielklassen an.

3. **Validierung**:
   Standardwerte werden unter Verwendung der Setter-Methode zugewiesen, mit dem Getter abgerufen und verglichen, um die Richtigkeit sicherzustellen. Jede Abweichung löst einen `AssertionError` aus, der das spezifische Problem hervorhebt.

4. **Fehlerberichterstattung**:
   Der Tester liefert detailliertes Feedback zu Validierungsfehlern, wie fehlenden Methoden, inkonsistenten Standardwerten oder fehlerhaften Eigenschaftenkonfigurationen.

## Schreiben von Tests mit `PropertyDescriptorTester` {#writing-tests-with-propertydescriptortester}

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
- Dass `drawerOpened` gültige Getter- und Setter-Methoden hat.
- Dass `headerTitle` standardmäßig auf `"Standardtitel"` gesetzt ist.

## Erweiterte Anwendungsfälle mit Annotationen {#advanced-use-cases-with-annotations}

Für komplexere Szenarien unterstützt `PropertyDescriptorTester` Annotationen, um Eigenschaften vom Testen anzupassen oder auszuschließen.

### Eigenschaften mit `@PropertyExclude` ausschließen {#exclude-properties-with-propertyexclude}

Schließen Sie Eigenschaften aus, die von externen Systemen abhängen oder für den Test nicht relevant sind. Zum Beispiel:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
  PropertyDescriptor.property("excludedProperty", "Ausgeschlossen");
```

### Methoden mit `@PropertyMethods` anpassen {#customize-methods-with-propertymethods}

Definieren Sie benutzerdefinierte Getter, Setter oder Zielklassen, wenn die Standardnamenskonventionen nicht zutreffen:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
  PropertyDescriptor.property("customProperty", "Standardwert");
```
