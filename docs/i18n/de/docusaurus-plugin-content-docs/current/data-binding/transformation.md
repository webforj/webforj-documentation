---
sidebar_position: 4
title: Transformation
_i18n_hash: fe3acbd17750ab0092cbc3609b967969
---
Datenumwandlungen sind ein entscheidendes Merkmal, das nahtlose Konvertierung zwischen den Datentypen, die in UI-Komponenten verwendet werden, und denen in Ihrem Datenmodell erleichtert. Diese Fähigkeit stellt sicher, dass Datentypen kompatibel und korrekt formatiert sind, wenn Daten zwischen dem Frontend und Backend Ihrer Anwendungen verschoben werden.

:::tip
Die Einstellung für den Transformator wird am besten verwendet, wenn der Datentyp der Bean-Eigenschaft nicht mit dem Datentyp übereinstimmt, der von den UI-Komponenten verarbeitet wird. Wenn Sie lediglich Daten desselben Typs umwandeln müssen, ist die Konfiguration von [den Gettern und Settern der Bindungen](bindings#binding-getters-and-setters) der bevorzugte Ansatz.
:::

## Konfigurieren von Transformatoren {#configuring-transformers}

Sie konfigurieren Datenumwandlungen direkt innerhalb Ihrer Bindungen, wodurch Sie definieren können, wie Daten während des Datenbindungsprozesses umgewandelt werden sollen.

Sie können Transformatoren zu einer Bindung hinzufügen, indem Sie die Methode `useTransformer` auf dem `BindingBuilder` verwenden. Transformatoren müssen das `Transformer`-Interface implementieren, das die Definition von Methoden für beide Richtungen des Datenflusses erfordert: vom Modell zur UI und von der UI zum Modell.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

Im obigen Beispiel konfiguriert der Code einen `CurrencyTransformer`, um Konvertierungen zwischen dem Datentyp des Modells (zum Beispiel BigDecimal) und der UI-Darstellung (zum Beispiel, ein formatierter String) zu behandeln.

:::info
Jede Bindung ist mit einem einzigen Transformator verbunden. Wenn die Umwandlung eines Wertes mehrere Schritte erfordert, wird empfohlen, Ihren eigenen Transformator für diese Schritte zu implementieren.
:::

## Implementieren eines Transformators {#implementing-a-transformer}

Hier ist ein Beispiel für die Implementierung eines einfachen Transformators, der zwischen einem `LocalDate`-Modell und einer `String`-UI-Darstellung konvertiert:

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

Dieser Transformator erleichtert den Umgang mit Datumsfeldern, indem sichergestellt wird, dass Daten korrekt formatiert werden, wenn sie in der UI angezeigt werden, und korrekt in das Modell zurückgeparsed werden.

## Verwenden von Transformatoren in Bindungen {#using-transformers-in-bindings}

Sobald Sie einen Transformator definiert haben, können Sie ihn in mehreren Bindungen innerhalb Ihrer App anwenden. Dieser Ansatz ist besonders nützlich für standardisierte Datenformate, die in verschiedenen Teilen Ihrer App konsistent behandelt werden müssen.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Angabe des Bean-Eigenschaftstyps

Im `bind`-Methodenaufruf ist es wichtig, den Typ der Bean-Eigenschaft als drittes Argument anzugeben, wenn es eine Diskrepanz zwischen dem von der UI-Komponente angezeigten Datentyp und dem im Modell verwendeten Datentyp gibt. Wenn die Komponente `startDateField` beispielsweise als Java `LocalDate` innerhalb der Komponente behandelt, aber im Modell als `String` gespeichert wird, stellt die explizite Definition des Typs als `String.class` sicher, dass der Bindemechanismus die Daten genau verarbeitet und zwischen den beiden unterschiedlichen Typen umwandelt, die von der Komponente und der Bean unter Verwendung des bereitgestellten Transformators und Validierern verwendet werden.
:::

## Vereinfachen von Transformationen mit `Transformer.of` {#simplifying-transforms-with-transformerof}

Es ist möglich, die Implementierung solcher Transformationen mithilfe der von `Transformer` bereitgestellten Methode `Transformer.of` zu vereinfachen. Diese Methode ist syntaktischer Zucker und ermöglicht es Ihnen, eine Methode zu schreiben, die Transformationen inline behandelt, anstatt eine Klasse zu übergeben, die das `Transformer`-Interface implementiert.

Im folgenden Beispiel behandelt der Code eine Checkbox-Interaktion innerhalb einer Reise-App, in der Nutzer zusätzliche Dienste wie Mietwagen auswählen können. Der Zustand der Checkbox, `boolean`, muss in eine String-Darstellung `"yes"` oder `"no"` umgewandelt werden, die das Backend-Modell verwendet.

```java
CheckBox carRental = new CheckBox("Mietwagen");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // konvertiert den Komponentenwert in den Modellwert
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // konvertiert den Modellwert in den Komponentenwert
        str -> str.equals("yes")
      ), 

      // falls die Transformation fehlschlägt, zeigen Sie die folgende
      // Nachricht an
      "Checkbox muss ausgewählt werden"
  )
  .add();
```
