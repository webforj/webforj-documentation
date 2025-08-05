---
sidebar_position: 4
title: Transformation
_i18n_hash: fccb434a8897618a0197f9883cd94795
---
Daten Transformationen sind eine entscheidende Funktion, die eine nahtlose Umwandlung zwischen den Datentypen, die in UI-Komponenten verwendet werden, und denen in Ihrem Datenmodell ermöglicht. Diese Fähigkeit stellt sicher, dass Datentypen kompatibel und angemessen formatiert sind, wenn Daten zwischen dem Frontend und Backend Ihrer Anwendungen übertragen werden.

:::tip
Die Transformer-Einstellung wird am besten verwendet, wenn der Datentyp der Bean-Eigenschaft nicht mit dem Datentyp übereinstimmt, der von den UI-Komponenten verarbeitet wird. Wenn Sie einfach Daten desselben Typs transformieren müssen, ist die Konfiguration der [Getter und Setter der Bindungen](bindings#binding-getters-and-setters) der bevorzugte Ansatz.
:::

## Konfigurieren von Transformatoren {#configuring-transformers}

Sie konfigurieren Daten Transformationen direkt innerhalb Ihrer Bindungen, wodurch Sie definieren können, wie Daten während des Datenbindungsprozesses transformiert werden sollen.

Sie können Transformatoren zu einer Bindung mit der Methode `useTransformer` im `BindingBuilder` hinzufügen. Transformatoren müssen das `Transformer`-Interface implementieren, das die Definition von Methoden für beide Richtungen des Datenflusses erfordert: vom Modell zur UI und von der UI zum Modell.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

Im obigen Beispiel konfiguriert der Code einen `CurrencyTransformer`, um Konversionen zwischen dem Datentyp des Modells (zum Beispiel BigDecimal) und der UI-Darstellung (zum Beispiel einem formatierten String) zu verarbeiten.

:::info
Jede Bindung ist mit einem einzelnen Transformator verbunden. Wenn das Transformieren eines Wertes mehrere Schritte erfordert, wird empfohlen, Ihren eigenen Transformator für diese Schritte zu implementieren.
:::

## Implementieren eines Transformers {#implementing-a-transformer}

Hier ist ein Beispiel, wie man einen einfachen Transformator implementiert, der zwischen einem `LocalDate`-Modell und einer `String`-UI-Darstellung konvertiert:

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
      throw new TransformationException("Ungültiges Datumsformat");
    }
  }

  @Override
  public String transformToModel(LocalDate componentValue) {
    try {
      return componentValue.format(formatter);
    } catch (Exception e) {
      throw new TransformationException("Ungültiges Datumsformat");
    }
  }
}
```

Dieser Transformator erleichtert die Handhabung von Datumsfeldern, indem er sicherstellt, dass Daten korrekt formatiert angezeigt und korrekt in das Modell zurückgeparst werden.

## Verwendung von Transformatoren in Bindungen {#using-transformers-in-bindings}

Sobald Sie einen Transformator definiert haben, können Sie ihn in mehreren Bindungen innerhalb Ihrer App anwenden. Dieser Ansatz ist besonders nützlich für standardisierte Datenformate, die konsistent in verschiedenen Teilen Ihrer App verarbeitet werden müssen.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Spezifizierung des Bean-Eigenschaftstyps

In der `bind`-Methode ist es wichtig, den Typ der Bean-Eigenschaft als dritten Parameter anzugeben, wenn es eine Diskrepanz zwischen dem Datentyp gibt, der von der UI-Komponente angezeigt wird, und dem Datentyp, der im Modell verwendet wird. Wenn beispielsweise die Komponente `startDateField` als Java `LocalDate` innerhalb der Komponente, aber als `String` im Modell gespeichert wird, gewährleistet die explizite Angabe des Typs als `String.class`, dass der Bindungsmechanismus die Daten zwischen den beiden unterschiedlichen Typen, die von der Komponente und der Bean verwendet werden, genau verarbeitet und konvertiert, mithilfe des bereitgestellten Transformers und der Validatoren.
:::

## Vereinfachung von Transformationen mit `Transformer.of` {#simplifying-transforms-with-transformerof}

Es ist möglich, die Implementierung solcher Transformationen mit der Methode `Transformer.of` zu vereinfachen, die von `Transformer` bereitgestellt wird. Diese Methode ist syntaktischer Zucker und ermöglicht es Ihnen, eine Methode zu schreiben, die Transformationen inline behandelt, anstatt eine Klasse, die das `Transformer`-Interface implementiert, zu übergeben.

Im folgenden Beispiel verarbeitet der Code eine Checkbox-Interaktion innerhalb einer Reise-App, in der Benutzer zusätzliche Dienste wie Mietwagen auswählen können. Der Checkbox-Zustand `boolean` muss in eine String-Darstellung `"yes"` oder `"no"` umgewandelt werden, die das Backend-Modell verwendet.

```java
CheckBox carRental = new CheckBox("Mietwagen");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // konvertiere Komponenteneingabewert in Modellwert
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // konvertiere Modellwert in Komponenteneingabewert
        str -> str.equals("yes")
      ), 

      // falls die Transformation fehlschlägt, zeige die folgende
      // Meldung an
      "Checkbox muss ausgewählt sein"
  )
  .add();
```
