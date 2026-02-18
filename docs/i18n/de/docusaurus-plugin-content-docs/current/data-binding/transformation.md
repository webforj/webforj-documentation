---
sidebar_position: 4
title: Transformation
sidebar_class_name: updated-content
_i18n_hash: 3b1655fdbfa9c303ae1445beee9ee327
---
Datenumwandlungen konvertieren zwischen den Datentypen, die in UI-Komponenten verwendet werden, und denen in Ihrem Datenmodell. Dies hält die Datentypen kompatibel und angemessen formatiert, wenn Daten zwischen dem Frontend und dem Backend Ihrer Anwendungen verschoben werden.

:::tip
Die Transformer-Einstellung wird am besten verwendet, wenn der Datentyp der Bean-Eigenschaft nicht mit dem Datentyp übereinstimmt, der von den UI-Komponenten verarbeitet wird. Wenn Sie einfach Daten desselben Typs umwandeln müssen, ist die Konfiguration der [Getter und Setter der Bindungen](bindings#binding-getters-and-setters) der bevorzugte Ansatz.
:::

## Transformer konfigurieren {#configuring-transformers}

Sie konfigurieren Datenumwandlungen direkt innerhalb Ihrer Bindungen, wodurch Sie definieren können, wie Daten während des Datenbindungsprozesses umgewandelt werden sollen.

Sie können Transformer zu einer Bindung mit der Methode `useTransformer` auf dem `BindingBuilder` hinzufügen. Transformer müssen das `Transformer`-Interface implementieren, das die Definition von Methoden für beide Richtungen des Datenflusses erfordert: vom Modell zur UI und von der UI zum Modell.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

Im obigen Beispiel konfiguriert der Code einen `CurrencyTransformer`, um Konvertierungen zwischen dem Datentyp des Modells (zum Beispiel BigDecimal) und der UI-Darstellung (zum Beispiel einem formatierten String) zu handhaben.

:::info
Jede Bindung ist mit einem einzelnen Transformer verbunden. Wenn das Transformieren eines Wertes mehrere Schritte erfordert, wird empfohlen, Ihren eigenen Transformer für diese Schritte zu implementieren.
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

Dieser Transformer verarbeitet Datumsfelder, formatiert Daten beim Anzeigen in der UI und analysiert sie zurück ins Modell.

### Transformer in Bindungen verwenden {#using-transformers-in-bindings}

Sobald Sie einen Transformer definiert haben, können Sie ihn über mehrere Bindungen innerhalb Ihrer App anwenden. Dieser Ansatz ist besonders nützlich für standardisierte Datenformate, die in verschiedenen Teilen Ihrer App konsistent behandelt werden müssen.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Den Typ der Bean-Eigenschaft angeben

Im `bind`-Methodenaufruf ist es wichtig, den Typ der Bean-Eigenschaft als drittes Argument anzugeben, wenn es eine Diskrepanz zwischen dem Datentyp gibt, der von der UI-Komponente angezeigt wird, und dem Datentyp, der im Modell verwendet wird. Wenn die Komponente `startDateField` als Java `LocalDate` innerhalb der Komponente behandelt, aber als `String` im Modell gespeichert wird, signalisiert die explizite Definition des Typs als `String.class` dem Bindungsmechanismus, die Daten zwischen den beiden unterschiedlichen Typen, die von der Komponente und der Bean verwendet werden, mithilfe des bereitgestellten Transformers und der Validatoren korrekt zu verarbeiten und zu konvertieren.
:::

### Transformationen mit `Transformer.of` vereinfachen {#simplifying-transforms-with-transformerof}

Es ist möglich, die Implementierung solcher Transformationen mit der Methode `Transformer.of`, die vom `Transformer` bereitgestellt wird, zu vereinfachen. Diese Methode ist syntaktischer Zucker und ermöglicht es Ihnen, eine Methode zu schreiben, die Transformationen inline behandelt, anstatt eine Klasse zu übergeben, die das `Transformer`-Interface implementiert. 

Im folgenden Beispiel behandelt der Code eine Checkbox-Interaktion in einer Reise-App, in der Benutzer zusätzliche Dienstleistungen wie einen Mietwagen auswählen können. Der Checkbox-Zustand `boolean` muss in eine Zeichenfolgenrepräsentation `"yes"` oder `"no"` umgewandelt werden, die das Backend-Modell verwendet.

```java
CheckBox carRental = new CheckBox("Mietwagen");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // Komponentenswert in Modellwert umwandeln
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // Modellwert in Komponentenswert umwandeln
        str -> str.equals("yes")
      ),

      // Falls die Transformation fehlschlägt, folgende
      // Nachricht anzeigen
      "Checkbox muss aktiviert sein"
  )
  .add();
```

### Dynamische Fehlermeldungen für Transformer <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

Standardmäßig wird die Fehlermeldung, die angezeigt wird, wenn eine Transformation fehlschlägt, als statischer String angezeigt. In Apps, die mehrere Sprachen unterstützen, können Sie stattdessen einen `Supplier<String>` übergeben, sodass die Nachricht jedes Mal aufgelöst wird, wenn die Transformation fehlschlägt:

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

Der Supplier wird nur aufgerufen, wenn die Transformation eine `TransformationException` wirft. Das bedeutet, die Nachricht spiegelt immer die aktuelle Locale zum Zeitpunkt des Fehlers wider.

#### Locale-bewusste Transformer {#locale-aware-transformers}

Für wiederverwendbare Transformer, die intern Zugriff auf die aktuelle Locale benötigen (zum Beispiel, um Zahlen oder Daten entsprechend regionalen Konventionen zu formatieren), implementieren Sie das `LocaleAware`-Interface. Wenn sich die Locale über `BindingContext.setLocale()` ändert, wird die neue Locale automatisch an Transformer propagiert, die dieses Interface implementieren.
