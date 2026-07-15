---
sidebar_position: 4
title: Transformation
description: >-
  Convert between UI and model data types in webforJ bindings by implementing
  the Transformer interface and wiring it via useTransformer.
_i18n_hash: 7a8064dc7b603cd86ad965a41216c55c
---
Datenumformungen konvertieren zwischen den Datentypen, die in UI-Komponenten verwendet werden, und denen in Ihrem Datenmodell. Dies sorgt dafür, dass die Datentypen kompatibel und angemessen formatiert sind, wenn Daten zwischen dem Frontend und dem Backend Ihrer Anwendungen übertragen werden.

:::tip
Die Transformator-Einstellung wird am besten verwendet, wenn der Datentyp der Bean-Eigenschaft nicht mit dem Datentyp übereinstimmt, der von den UI-Komponenten verarbeitet wird. Wenn Sie einfach Daten desselben Typs transformieren müssen, ist es der bevorzugte Ansatz, [die Getter und Setter der Bindungen](bindings#binding-getters-and-setters) zu konfigurieren.
:::

## Konfiguration von Transformatoren {#configuring-transformers}

Sie konfigurieren Datenumformungen direkt innerhalb Ihrer Bindungen, wodurch Sie definieren können, wie Daten während des Datenbindungsprozesses transformiert werden sollen.

Sie können Transformatoren zu einer Bindung hinzufügen, indem Sie die Methode `useTransformer` auf dem `BindingBuilder` verwenden. Transformatoren müssen das `Transformer`-Interface implementieren, das erfordert, dass Methoden für beide Richtungen des Datenflusses definiert werden: vom Modell zur UI und von der UI zum Modell.

```java
context.bind(salaryField, "salary")
  .useTransformer(new CurrencyTransformer())
  .add();
```

Im obigen Beispiel konfiguriert der Code einen `CurrencyTransformer`, um Konvertierungen zwischen dem Modell-Datentyp (zum Beispiel BigDecimal) und der UI-Repräsentation (zum Beispiel einem formatierten String) zu handhaben.

:::info
Jede Bindung ist mit einem einzelnen Transformator assoziiert. Wenn das Transformieren eines Wertes mehrere Schritte erfordert, wird empfohlen, Ihren eigenen Transformator für diese Schritte zu implementieren.
:::

## Implementierung eines Transformators {#implementing-a-transformer}

Hier ist ein Beispiel zur Implementierung eines einfachen Transformators, der zwischen einem `LocalDate`-Modell und einer `String`-UI-Repräsentation konvertiert:

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

Dieser Transformator behandelt Datumsfelder, formatiert Daten, wenn sie in der UI angezeigt werden, und analysiert sie zurück in das Modell.

### Verwendung von Transformatoren in Bindungen {#using-transformers-in-bindings}

Sobald Sie einen Transformator definiert haben, können Sie ihn in mehreren Bindungen innerhalb Ihrer Anwendung anwenden. Dieser Ansatz ist besonders nützlich für standardisierte Datenformate, die eine konsistente Handhabung in verschiedenen Teilen Ihrer Anwendung erfordern.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
  .useTransformer(new DateTransformer())
  .add();
```

:::info Spezifikation des Bean-Eigenschaftstyps

Im `bind`-Method ist es wichtig, den Typ der Bean-Eigenschaft als drittes Parameter anzugeben, wenn es eine Diskrepanz zwischen dem Datentyp gibt, der von der UI-Komponente angezeigt wird, und dem Datentyp, der im Modell verwendet wird. Wenn die Komponente `startDateField` als Java `LocalDate` innerhalb der Komponente behandelt, aber als `String` im Modell gespeichert wird, teilt die explizite Definition des Typs als `String.class` dem Bindungsmechanismus mit, dass die Daten genau zwischen den beiden verschiedenen Typen, die von der Komponente und der Bean verwendet werden, mit dem bereitgestellten Transformator und Validatoren verarbeitet und konvertiert werden sollen.
:::

### Vereinfachen von Transformationen mit `Transformer.of` {#simplifying-transforms-with-transformerof}

Es ist möglich, die Implementierung solcher Transformationen mithilfe der Methode `Transformer.of` zu vereinfachen, die von `Transformer` bereitgestellt wird. Diese Methode ist syntaktischer Zucker und ermöglicht es Ihnen, eine Methode zu schreiben, die Transformationen inline behandelt, anstatt eine Klasse zu übergeben, die das Interface `Transformer` implementiert.

Im folgenden Beispiel verarbeitet der Code eine Checkbox-Interaktion innerhalb einer Reise-App, in der Benutzer zusätzliche Dienstleistungen wie Mietwagen wählen können. Der Status der Checkbox `boolean` muss in eine String-Repräsentation `"yes"` oder `"no"` umgewandelt werden, die das Backend-Modell verwendet.

```java
CheckBox carRental = new CheckBox("Mietwagen");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // Konvertieren Sie den Komponentenwert in den Modellwert
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // Konvertieren Sie den Modellwert in den Komponentenwert
        str -> str.equals("yes")
      ),

      // Falls die Transformation fehlschlägt, zeigen Sie die folgende
      // Nachricht an
      "Checkbox muss markiert werden"
  )
  .add();
```

### Dynamische Transformator-Fehlermeldungen <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

Standardmäßig ist die Fehlermeldung, die angezeigt wird, wenn eine Transformation fehlschlägt, ein statischer String. In Apps, die mehrere Sprachen unterstützen, können Sie stattdessen einen `Supplier<String>` übergeben, sodass die Nachricht jedes Mal aufgelöst wird, wenn die Transformation fehlschlägt:

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

Der Supplier wird nur aufgerufen, wenn die Transformation eine `TransformationException` auslöst. Dies bedeutet, dass die Nachricht immer die aktuelle Sprache zum Zeitpunkt des Fehlers widerspiegelt.

#### Lokalisierte Transformatoren {#locale-aware-transformers}

Für wiederverwendbare Transformatoren, die intern auf die aktuelle Lokalisierung zugreifen müssen (zum Beispiel um Zahlen oder Daten gemäß regionalen Konventionen zu formatieren), implementieren Sie das `LocaleAware`-Interface. Wenn die Lokalisierung über `BindingContext.setLocale()` geändert wird, propagiert der Kontext automatisch die neue Lokalisierung zu Transformatoren, die dieses Interface implementieren.
