---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: e1cde7099182ddabd898e0c5391fe8b7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

Die `NumberField`-Komponente akzeptiert numerische Eingaben und lehnt ungültige Werte automatisch ab. Sie unterstützt Min- und Max-Grenzen, Schrittintervalle und Platzhaltertext.

<!-- INTRO_END -->

## Verwendung von `NumberField` {#using-numberfield}

<ParentLink parent="Field" />

`NumberField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen für alle Feldkomponenten bereitstellt. Das folgende Beispiel erstellt ein `NumberField` mit einem Label und Platzhaltertext.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Feldwert {#field-value}

Die `NumberField`-Komponente speichert ihren Wert als `Double`, was eine präzise Handhabung sowohl von Ganzzahlen als auch von Dezimalzahlen ermöglicht.

### Abfragen des aktuellen Wertes {#getting-the-current-value}

Sie können den numerischen Wert, den der Benutzer eingegeben hat, wie folgt abrufen:

```java
Double currentValue = numberField.getValue();
```

### Setzen eines neuen Wertes {#setting-a-new-value}

Um den Wert programmgesteuert zu setzen:

```java
numberField.setValue(42.5);
```

Wenn kein Wert eingegeben wurde und kein Standardwert gesetzt ist, gibt `getValue()` `null` zurück.

:::tip
Obwohl das Feld so konzipiert ist, dass es nur gültige numerische Eingaben akzeptiert, denken Sie daran, dass der zugrunde liegende Wert nullable ist. Testen Sie immer auf null, bevor Sie das Ergebnis verwenden.
:::

## Anwendungen {#usages}

Das `NumberField` eignet sich am besten in Szenarien, in denen das Erfassen, Anzeigen oder Manipulieren von numerischen Daten für Ihre App entscheidend ist. Hier sind einige Beispiele, wann Sie das `NumberField` verwenden sollten:

1. **Numerische Eingabeformulare**: Bei der Gestaltung von Formularen, die numerische Eingaben erfordern, vereinfacht ein `NumberField` den Eingabeprozess für Benutzer. Dies ist besonders nützlich für Anwendungen, die Benutzerdaten sammeln oder numerische Werte erfordern.

2. **Datenanalyse und Berechnungen**: Ein `NumberField` ist besonders wertvoll in Apps, die Datenanalysen, Berechnungen oder mathematische Operationen beinhalten. Es ermöglicht Benutzern, numerische Werte genau einzugeben oder zu manipulieren.

3. **Finanz- und Budgetanwendungen**: Apps, die finanzielle Berechnungen, Budgetierung oder die Verfolgung von Ausgaben beinhalten, erfordern oft präzise numerische Eingaben. Ein `NumberField` gewährleistet eine genaue Eingabe finanzieller Zahlen.

4. **Messungen und Einheit umrechnung**: In Apps, die sich mit Messungen oder Einheit umrechnungen befassen, ist das `NumberField` ideal zum Eingeben numerischer Werte mit Einheiten wie Länge, Gewicht oder Volumen.

## Min- und Max-Wert {#min-and-max-value}

Mit der Methode `setMin()` können Sie den minimalen akzeptablen Wert im Zahlenfeld festlegen. Wenn ein Benutzer einen Wert eingibt, der unter diesem Schwellenwert liegt, schlägt die Komponente bei der Validierung fehl und gibt entsprechendes Feedback.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Minimal zulässig: 0.0
```

Separat ermöglicht die Methode `setMax()`, den maximalen akzeptablen Wert festzulegen. Wenn ein Benutzer einen Wert eingibt, der über diesem Limit liegt, wird die Eingabe abgelehnt. Wenn sowohl Mindest- als auch Höchstwerte gesetzt sind, muss der Höchstwert größer oder gleich dem Mindestwert sein.

```java
numberField.setMax(100.0); // Maximal zulässig: 100.0
```

In dieser Konfiguration wären Eingaben wie -5 oder 150 ungültig, während Werte zwischen 0 und 100 akzeptiert werden.

## Granularität {#granularity}

Sie können die Methode `setStep()` verwenden, um die Granularität anzugeben, die der Wert einhalten muss, wenn die Pfeiltasten verwendet werden, um den Wert zu ändern. Dies erhöht oder verringert den Wert der Komponente bei jeder Eingabe um einen bestimmten Schritt. Dies gilt nicht, wenn ein Benutzer einen Wert direkt eingibt, sondern nur, wenn er mit den Pfeiltasten mit dem `NumberField` interagiert.

## Platzhaltertext {#placeholder-text}

Sie können den Platzhaltertext für das `NumberField` mit der Methode `setPlaceholder()` festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist, und hilft, den Benutzer zu ermutigen, geeignete Eingaben in das `NumberField` einzutragen.

:::tip Geben Sie klaren Kontext für Genauigkeit
Wenn die numerische Eingabe mit einer bestimmten Einheit oder einem bestimmten Kontext in Zusammenhang steht, bieten Sie klare Beschriftungen oder zusätzliche Informationen an, um die Benutzer zu führen und eine genaue Eingabe sicherzustellen.
:::

## Beste Praktiken {#best-practices}

Um eine nahtlose Integration und ein optimales Benutzererlebnis zu gewährleisten, sollten Sie die folgenden besten Praktiken bei der Verwendung des `NumberField` beachten:

- **Barrierefreiheit**: Nutzen Sie die `NumberField`-Komponente mit Blick auf die Barrierefreiheit und halten Sie sich an Barrierefreiheitsstandards wie ordnungsgemäße Beschriftung, Unterstützung der Tastaturnavigation und Kompatibilität mit Hilfstechnologien. Stellen Sie sicher, dass Benutzer mit Behinderungen effektiv mit dem `NumberField` interagieren können.

- **Verwenden Sie Inkrement-/Dekrement-Tasten**: Wenn dies für Ihre App geeignet ist, ziehen Sie in Betracht, Inkrement- und Dekrementtasten mit dem `NumberField` zu verwenden. Dadurch können Benutzer den numerischen Wert mit einem einzelnen Klick um einen bestimmten Inkrement oder Dekrement anpassen.
