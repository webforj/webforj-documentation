---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: fb5cec5217d52b4e298c4d886ef95160
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

Der `PropertyDescriptorTester` in webforJ vereinfacht das Testen von **drittanbieter Webkomponenten**, die in Ihre Anwendung integriert sind. Er validiert, dass Eigenschaften, die mit [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) definiert sind, korrekt mit ihren Getter- und Setter-Methoden verknüpft sind und stellt sicher, dass Standardverhalten konsistent behandelt wird. Dieses Tool ist besonders nützlich zur Überprüfung der Funktionalität von Eigenschaften, die von Drittanbieter-Komponenten bereitgestellt werden, ohne dass wiederholte Testlogik erforderlich ist.

:::warning experimentelles Feature
Der Adapter `PropertyDescriptorTester` von webforJ ist derzeit ein experimentelles Feature. Es können jederzeit breaking changes eingeführt werden.
:::

## Übersicht {#overview}

Beim Arbeiten mit Drittanbieter-Webkomponenten ist es wichtig, sicherzustellen, dass Eigenschaften wie erwartet funktionieren. Der `PropertyDescriptorTester` automatisiert diesen Prozess, indem er validiert, dass Eigenschaften:
- Korrekt ihren Getter- und Setter-Methoden zugeordnet sind.
- Erwartete Standardwerte und benutzerdefinierte Verhalten beibehalten.
- Häufige Integrationsprobleme vermeiden, wie z. B. nicht übereinstimmende Eigenschaftsnamen oder inkonsistente Standardwerte.

Das Tool unterstützt Annotationen für komplexere Anwendungsfälle, wie das Ausschließen irrelevanter Eigenschaften oder das Definieren benutzerdefinierter Getter- und Setter-Methoden, wodurch es eine vielseitige Option für Integrationstests darstellt.

## Wie `PropertyDescriptorTester` funktioniert {#how-propertydescriptortester-works}

Der Testprozess umfasst mehrere automatisierte Schritte:

1. **Klassen-Scannen**: 
   Der `PropertyDescriptorScanner` identifiziert alle `PropertyDescriptor`-Felder innerhalb einer Komponentenklasse und schließt automatisch Felder aus, die mit `@PropertyExclude` annotiert sind.

2. **Methodenauflösung**:
   Standard-Getter- und Setter-Methoden werden basierend auf Namenskonventionen erkannt (`get<PropertyName>`/`set<PropertyName>`). Für nicht standardkonforme Implementierungen spezifizieren Annotationen wie `@PropertyMethods` benutzerdefinierte Methodennamen oder Zielklassen.

3. **Validierung**:
   Standardwerte werden mit der Setter-Methode zugewiesen, über die Getter-Methode abgerufen und verglichen, um sicherzustellen, dass sie korrekt sind. Jede Abweichung löst einen `AssertionError` aus, der das spezifische Problem hervorhebt.

4. **Fehlerberichterstattung**:
   Der Tester liefert detaillisches Feedback zu allen Validierungsfehlern, wie fehlenden Methoden, inkonsistenten Standardwerten oder fehlerhaften Eigenschaftskonfigurationen.

## Tests schreiben mit `PropertyDescriptorTester` {#writing-tests-with-propertydescriptortester}

Hier ist ein Beispiel, das die grundlegende Validierung von Eigenschaften für eine `AppLayout`-Komponente demonstriert:

### Beispiel: Grundlegende Validierung {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Default Title");

  // Setters und Getters
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
- Dass `headerTitle` standardmäßig auf `"Default Title"` gesetzt ist.

## Erweiterte Anwendungsfälle mit Annotationen {#advanced-use-cases-with-annotations}

Für komplexere Szenarien unterstützt der `PropertyDescriptorTester` Annotationen, um Eigenschaften von Tests anzupassen oder auszuschließen.

### Eigenschaften mit `@PropertyExclude` ausschließen {#exclude-properties-with-propertyexclude}

Schließen Sie Eigenschaften aus, die von externen Systemen abhängen oder für den Test nicht relevant sind. Zum Beispiel:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
  PropertyDescriptor.property("excludedProperty", "Excluded");
```

### Methoden mit `@PropertyMethods` anpassen {#customize-methods-with-propertymethods}

Definieren Sie benutzerdefinierte Getter, Setter oder Zielklassen, wenn die Standard-Namenskonventionen nicht zutreffen:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
  PropertyDescriptor.property("customProperty", "Default Value");
```
