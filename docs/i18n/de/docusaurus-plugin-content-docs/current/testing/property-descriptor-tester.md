---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: 9ec1cde5a7d91d75dfd454741a6e8ee3
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

Der `PropertyDescriptorTester` in webforJ vereinfacht das Testen von **drittanbieter Webkomponenten**, die in Ihre App integriert sind. Er validaert, dass Eigenschaften, die mit [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) definiert sind, korrekt mit ihren Getter- und Setter-Methoden verknüpft sind und stellt sicher, dass Standardverhalten konsistent behandelt wird. Dieses Tool ist besonders nützlich zur Überprüfung der Funktionalität von Eigenschaften, die von Drittanbieterkomponenten bereitgestellt werden, ohne dass wiederholte Testlogik erforderlich ist.

:::warning experimentelles Feature
Der webforJ PropertyDescriptorTester-Adapter ist derzeit ein experimentelles Feature. Es können jederzeit brechende Änderungen eingeführt werden.
:::

## Übersicht {#overview}

Wenn Sie mit Drittanbieter-Webkomponenten arbeiten, ist es wichtig, sicherzustellen, dass Eigenschaften wie erwartet funktionieren. Der `PropertyDescriptorTester` automatisiert diesen Prozess, indem er validiert, dass Eigenschaften:
- Korrekt auf ihre Getter- und Setter-Methoden abgebildet sind.
- Erwartete Standardwerte und benutzerdefinierte Verhaltensweisen aufrechterhalten.
- Häufige Integrationsprobleme, wie z. B. nicht übereinstimmende Eigenschaftsnamen oder inkonsistente Standards, vermeiden.

Das Tool unterstützt Annotations für komplexere Anwendungsfälle, wie das Ausschließen unwichtiger Eigenschaften oder das Definieren benutzerdefinierter Getter- und Setter-Methoden, wodurch es eine vielseitige Option für Integrationstests darstellt.

## Wie `PropertyDescriptorTester` funktioniert {#how-propertydescriptortester-works}

Der Testprozess umfasst mehrere automatisierte Schritte:

1. **Klassen-Scanning**: 
   Der `PropertyDescriptorScanner` identifiziert alle `PropertyDescriptor`-Felder innerhalb einer Komponentenklasse und schließt automatisch mit `@PropertyExclude` annotierte Felder aus.

2. **Methodenauflösung**:
   Standardgetter- und Setter-Methoden werden basierend auf Namenskonventionen erkannt (`get<PropertyName>`/`set<PropertyName>`). Für nicht standardmäßige Implementierungen geben Annotations wie `@PropertyMethods` benutzerdefinierte Methodennamen oder Zielklassen an.

3. **Validierung**:
   Standardwerte werden mit der Setter-Methode zugewiesen, mit der Getter-Methode abgerufen und verglichen, um die Korrektheit sicherzustellen. Jede Nichtübereinstimmung löst einen `AssertionError` aus, der das spezifische Problem hervorhebt.

4. **Fehlerberichterstattung**:
   Der Tester bietet detailliertes Feedback zu Validierungsfehlern, wie fehlenden Methoden, inkonsistenten Standards oder fehlerhaften Eigenschaften.

## Tests mit `PropertyDescriptorTester` schreiben {#writing-tests-with-propertydescriptortester}

Hier ist ein Beispiel, das eine grundlegende Eigenschaftsvalidierung für eine `AppLayout`-Komponente demonstriert:

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
- Dass `headerTitle` auf `"Standardtitel"` festgelegt ist.

## Fortgeschrittene Anwendungsfälle mit Annotations {#advanced-use-cases-with-annotations}

Für komplexere Szenarien unterstützt `PropertyDescriptorTester` Annotations, um Eigenschaften anzupassen oder vom Testen auszuschließen.

### Eigenschaften mit `@PropertyExclude` ausschließen {#exclude-properties-with-propertyexclude}

Schließen Sie Eigenschaften aus, die auf externe Systeme angewiesen sind oder für den Test irrelevant sind. Zum Beispiel:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
    PropertyDescriptor.property("excludedProperty", "Ausgeschlossen");
```

### Methoden mit `@PropertyMethods` anpassen {#customize-methods-with-propertymethods}

Definieren Sie benutzerdefinierte Getter-, Setter- oder Zielklasse, wenn die Standard-Namenskonventionen nicht zutreffen:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
    PropertyDescriptor.property("customProperty", "Standardwert");
```
