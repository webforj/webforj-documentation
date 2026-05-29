---
sidebar_position: 4
title: Transformation
sidebar_class_name: updated-content
_i18n_hash: e03ca3208470e53be7128ffb972c2670
---
Datenumwandlungen konvertieren zwischen den Datentypen, die in UI-Komponenten verwendet werden, und denen in Ihrem Datenmodell. Dies hält die Datentypen kompatibel und entsprechend formatiert, wenn Daten zwischen dem Frontend und Backend Ihrer Anwendungen verschoben werden.

:::tip
Die Transformer-Einstellung wird am besten verwendet, wenn der Datentyp der Bean-Eigenschaft nicht mit dem Datentyp übereinstimmt, der von den UI-Komponenten verarbeitet wird. Wenn Sie einfach Daten desselben Typs transformieren müssen, ist die Konfiguration der [Getter und Setter der Bindungen](bindings#binding-getters-and-setters) der bevorzugte Ansatz.
:::

## Transformer konfigurieren {#configuring-transformers}

Sie konfigurieren Datenumwandlungen direkt innerhalb Ihrer Bindungen, sodass Sie definieren können, wie Daten während des Datenbindungsprozesses umgewandelt werden sollen.

Sie können Transformer zu einer Bindung hinzufügen, indem Sie die Methode `useTransformer` auf dem `BindingBuilder` verwenden. Transformer müssen das `Transformer`-Interface implementieren, das die Definition von Methoden für beide Richtungen des Datenflusses erfordert: vom Modell zur UI und von der UI zum Modell.

```java
context.bind(salaryField, "salary")
  .useTransformer(new CurrencyTransformer())
  .add();
```

Im obigen Beispiel konfiguriert der Code einen `CurrencyTransformer`, um Konversionen zwischen dem Datentyp des Modells (zum Beispiel BigDecimal) und der UI-Darstellung (zum Beispiel einer formatierten Zeichenkette) zu handhaben.

:::info
Jede Bindung ist mit einem einzelnen Transformer assoziiert. Wenn die Umwandlung eines Wertes mehrere Schritte erfordert, wird empfohlen, einen eigenen Transformer für diese Schritte zu implementieren.
:::

## Einen Transformer implementieren {#implementing-a-transformer}

Hier ist ein Beispiel für die Implementierung eines einfachen Transformers, der zwischen einem `LocalDate`-Modell und einer `String`-UI-Darstellung konvertiert:

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

Dieser Transformer behandelt Datumsfelder, formatiert Daten, wenn sie in der UI angezeigt werden, und parst sie zurück in das Modell.

### Transformer in Bindungen verwenden {#using-transformers-in-bindings}

Sobald Sie einen Transformer definiert haben, können Sie ihn in mehreren Bindungen innerhalb Ihrer Anwendung anwenden. Dieser Ansatz ist besonders nützlich für standardisierte Datenformate, die konsistent in verschiedenen Teilen Ihrer Anwendung behandelt werden müssen.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
  .useTransformer(new DateTransformer())
  .add();
```

:::info Festlegen des Typen der Bean-Eigenschaft

Im `bind`-Methodenaufruf ist es wichtig, den Typ der Bean-Eigenschaft als dritten Parameter anzugeben, wenn es eine Diskrepanz zwischen dem Datentyp gibt, der von der UI-Komponente angezeigt wird, und dem Datentyp, der im Modell verwendet wird. Zum Beispiel, wenn die Komponente `startDateField` innerhalb der Komponente als Java `LocalDate` behandelt, aber im Modell als `String` gespeichert ist, teilt die explizite Definition des Typs als `String.class` dem Bindungsmechanismus mit, die Daten zwischen den beiden unterschiedlichen Typen, die von der Komponente und der Bean verwendet werden, mit dem bereitgestellten Transformer und den Validierern genau zu verarbeiten.
:::

### Umwandlungen mit `Transformer.of` vereinfachen {#simplifying-transforms-with-transformerof}

Es ist möglich, die Umsetzung solcher Umwandlungen mit der Methode `Transformer.of` zu vereinfachen, die vom `Transformer` bereitgestellt wird. Diese Methode ist syntaktischer Zucker und ermöglicht es Ihnen, eine Methode zu schreiben, die Umwandlungen inline behandelt, anstatt eine Klasse zu übergeben, die das `Transformer`-Interface implementiert. 

Im folgenden Beispiel behandelt der Code eine Checkbox-Interaktion in einer Reise-App, in der Benutzer zusätzliche Dienste wie Mietwagen auswählen können. Der Status der Checkbox `boolean` muss in eine Zeichenfolgenrepräsentation `"ja"` oder `"nein"` umgewandelt werden, die das Backend-Modell verwendet.

```java
CheckBox carRental = new CheckBox("Mietwagen");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // konvertiere den Komponentenwert in den Modellwert
        bool -> Boolean.TRUE.equals(bool) ? "ja" : "nein",
        // konvertiere den Modellwert in den Komponentenwert
        str -> str.equals("ja")
      ),

      // falls die Umwandlung fehlschlägt, zeige die folgende
      // Nachricht an
      "Checkbox muss ausgewählt sein"
  )
  .add();
```

### Dynamische Fehlermeldungen für Transformer <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

Standardmäßig ist die Fehlermeldung, die angezeigt wird, wenn eine Umwandlung fehlschlägt, eine statische Zeichenfolge. In Apps, die mehrere Sprachen unterstützen, können Sie stattdessen einen `Supplier<String>` übergeben, sodass die Nachricht jedes Mal aufgelöst wird, wenn die Umwandlung fehlschlägt:

```java {7}
context.bind(quantityField, "quantity", Integer.class)
  .useTransformer(
    Transformer.of(
      str -> Integer.parseInt(str),
      val -> String.valueOf(val)
    ),
    () -> t("validation.quantity.invalid")
  )
  .add();
```

Der Supplier wird nur aufgerufen, wenn die Umwandlung eine `TransformationException` auslöst. Das bedeutet, dass die Nachricht immer die aktuelle Sprache zum Zeitpunkt des Fehlers widerspiegelt.

#### Lokalisierungsbewusste Transformer {#locale-aware-transformers}

Für wiederverwendbare Transformer, die intern Zugriff auf die aktuelle Sprache benötigen (zum Beispiel, um Zahlen oder Daten gemäß regionalen Konventionen zu formatieren), implementieren Sie das `LocaleAware`-Interface. Wenn die Sprache über `BindingContext.setLocale()` geändert wird, propagiert der Kontext automatisch die neue Sprache an Transformer, die dieses Interface implementieren.
