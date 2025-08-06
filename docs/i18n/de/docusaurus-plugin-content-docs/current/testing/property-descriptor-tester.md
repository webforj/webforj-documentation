---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: f4cd3a02cd03838a015f873a3e5143ef
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

Der `PropertyDescriptorTester` in webforJ vereinfacht das Testen von **drittanbieter Webkomponenten**, die in Ihre Anwendung integriert sind. Es validiert, dass Eigenschaften, die mit [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) definiert sind, korrekt mit ihren Getter- und Setter-Methoden verknüpft sind und stellt sicher, dass Standardverhalten konsistent behandelt wird. Dieses Tool ist besonders nützlich, um die Funktionalität von Eigenschaften zu überprüfen, die von Drittanbieterkomponenten angeboten werden, ohne dass sich wiederholende Testlogik erforderlich ist.

:::warning experimental feature
Der PropertyDescriptorTester-Adapter von webforJ ist derzeit ein experimentelles Feature. Es können jederzeit Breaking Changes eingeführt werden.
:::

## Übersicht {#overview}

Bei der Arbeit mit Drittanbieter-Webkomponenten ist es entscheidend, dass Eigenschaften wie erwartet funktionieren. Der `PropertyDescriptorTester` automatisiert diesen Prozess, indem er validiert, dass Eigenschaften:
- Korrekt ihren Getter- und Setter-Methoden zugeordnet sind.
- Erwartete Standardwerte und benutzerdefinierte Verhaltensweisen beibehalten.
- Häufige Integrationsprobleme vermeiden, wie z. B. nicht übereinstimmende Eigenschaftsnamen oder inkonsistente Standards.

Das Tool unterstützt Annotationen für komplexere Anwendungsfälle, wie das Ausschließen irrelevanter Eigenschaften oder das Definieren benutzerdefinierter Getter- und Setter-Methoden, wodurch es eine vielseitige Option für Integrationstests darstellt.

## Wie `PropertyDescriptorTester` funktioniert {#how-propertydescriptortester-works}

Der Testprozess umfasst mehrere automatisierte Schritte:

1. **Klassen-Scanning**: 
   Der `PropertyDescriptorScanner` identifiziert alle `PropertyDescriptor`-Felder innerhalb einer Komponentenklasse und schließt automatisch mit `@PropertyExclude` annotierte Felder aus.

2. **Methodenauflösung**:
   Standard-Getter- und Setter-Methoden werden basierend auf Namenskonventionen erkannt (`get<PropertyName>`/`set<PropertyName>`). Für nicht standardmäßige Implementierungen geben Annotationen wie `@PropertyMethods` benutzerdefinierte Methodennamen oder Zielklassen an.

3. **Validierung**:
   Standardwerte werden mit der Setter-Methode zugewiesen, mit dem Getter abgerufen und verglichen, um die Richtigkeit sicherzustellen. Jede Abweichung führt zu einem `AssertionError`, der das spezifische Problem hervorhebt.

4. **Fehlerberichterstattung**:
   Der Tester bietet detailliertes Feedback zu allen Validierungsfehlern, wie z. B. fehlenden Methoden, inkonsistenten Standards oder fehlerhaften Eigenschaftskonfigurationen.

## Tests mit `PropertyDescriptorTester` schreiben {#writing-tests-with-propertydescriptortester}

Hier ist ein Beispiel, das die grundlegende Eigenschaftenvalidierung für eine `AppLayout`-Komponente demonstriert:

### Beispiel: Grundlegende Validierung {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Default Title");

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
- Dass `headerTitle` auf `"Default Title"` standardmäßig gesetzt ist.

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

Definieren Sie benutzerdefinierte Getter, Setter oder Zielklassen, wenn die Standard-Namenskonventionen nicht zutreffen:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
    PropertyDescriptor.property("customProperty", "Standardwert");
```
