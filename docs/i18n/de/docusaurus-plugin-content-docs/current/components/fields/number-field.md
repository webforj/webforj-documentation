---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: aa5037e2faa2968328081b1811dcabb0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

Die `NumberField`-Komponente akzeptiert numerische Eingaben und weist ungültige Werte automatisch zurück. Sie unterstützt Mindest- und Höchstgrenzen, Schrittintervalle und Platzhaltertext.

<!-- INTRO_END -->

## Verwendung der `NumberField` {#using-numberfield}

<ParentLink parent="Field" />

`NumberField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen für alle Feldkomponenten bietet. Das folgende Beispiel erstellt ein `NumberField` mit einem Label und Platzhaltertext.

<ComponentDemo
path='/webforj/numberfield'
files={['src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java']}
/>

## Feldwert {#field-value}

Die `NumberField`-Komponente speichert ihren Wert als `Double`, was eine präzise Handhabung sowohl von Ganzzahlen als auch von Dezimalzahlen ermöglicht.

### Aktuellen Wert abrufen {#getting-the-current-value}

Sie können den numerischen Wert, den der Benutzer eingegeben hat, mit folgendem Befehl abrufen:

```java
Double currentValue = numberField.getValue();
```

### Neuen Wert festlegen {#setting-a-new-value}

Um den Wert programmgesteuert festzulegen:

```java
numberField.setValue(42.5);
```

Wenn kein Wert eingegeben wurde und kein Standardwert festgelegt ist, gibt `getValue()` `null` zurück.

:::tip
Obwohl das Feld dafür ausgelegt ist, nur gültige numerische Eingaben zu akzeptieren, denken Sie daran, dass der zugrunde liegende Wert nullbar ist. Überprüfen Sie immer auf null, bevor Sie das Ergebnis verwenden.
:::

## Anwendungsfälle {#usages}

Das `NumberField` eignet sich am besten für Szenarien, in denen das Erfassen, Anzeigen oder Manipulieren von numerischen Daten für Ihre Anwendung von entscheidender Bedeutung ist. Hier sind einige Beispiele, wann Sie das `NumberField` verwenden sollten:

1. **Numerische Eingabeformulare**: Bei der Gestaltung von Formularen, die numerische Eingaben erfordern, vereinfacht die Verwendung eines `NumberField` den Eingabeprozess für die Benutzer. Dies ist insbesondere nützlich für Anwendungen, die Benutzerdaten erfassen oder numerische Werte erfordern.

2. **Datenanalyse und Berechnungen**: Ein `NumberField` ist besonders wertvoll in Apps, die Datenanalysen, Berechnungen oder mathematische Operationen umfassen. Es ermöglicht den Benutzern, numerische Werte genau einzugeben oder zu manipulieren.

3. **Finanz- und Budgetierungsanwendungen**: Apps, die finanzielle Berechnungen, Budgetierung oder die Verfolgung von Ausgaben umfassen, erfordern oft präzise numerische Eingaben. Ein `NumberField` gewährleistet die genaue Eingabe von Finanzdaten.

4. **Messung und Einheit Umrechnung**: In Apps, die sich mit Messungen oder Einheit Umrechnungen befassen, ist das `NumberField` ideal für die Eingabe numerischer Werte mit Einheiten wie Länge, Gewicht oder Volumen.

## Mindest- und Höchstwert {#min-and-max-value}

Mit der Methode `setMin()` können Sie den minimal akzeptablen Wert im Zahlenfeld angeben. Wenn ein Benutzer einen Wert unterhalb dieser Schwelle eingibt, scheitert die Komponente an der Einschränkung Validierung und gibt entsprechendes Feedback.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Mindestwert: 0.0
```

Separat ermöglicht die Methode `setMax()`, den maximal akzeptablen Wert festzulegen. Wenn ein Benutzer einen Wert über diesem Limit eingibt, wird die Eingabe abgelehnt. Wenn sowohl minimale als auch maximale Werte festgelegt sind, muss das Maximum größer oder gleich dem Minimum sein.

```java
numberField.setMax(100.0); // Höchstwert: 100.0
```

In dieser Konfiguration wäre die Eingabe eines Wertes wie -5 oder 150 ungültig, während Werte zwischen 0 und 100 akzeptiert werden.

## Granularität {#granularity}

Sie können die Methode `setStep()` verwenden, um die Granularität festzulegen, der der Wert entsprechen muss, wenn die Pfeiltasten verwendet werden, um den Wert zu ändern. Dies erhöht oder verringert den Wert der Komponente bei jedem Mal um einen bestimmten Schritt. Dies gilt nicht, wenn ein Benutzer einen Wert direkt eingibt, sondern nur, wenn mit dem `NumberField` über die Pfeiltasten interagiert wird.

## Platzhaltertext {#placeholder-text}

Sie können Platzhaltertext für das `NumberField` mit der Methode `setPlaceholder()` festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist und hilft, den Benutzer zur Eingabe geeigneter Daten in das `NumberField` zu animieren.

:::tip Geben Sie klaren Kontext für Genauigkeit
Wenn die numerische Eingabe mit einer bestimmten Maßeinheit oder einem bestimmten Kontext in Verbindung steht, geben Sie klare Beschriftungen oder zusätzliche Informationen an, um die Benutzer zu leiten und eine genaue Eingabe sicherzustellen.
:::

## Beste Praktiken {#best-practices}

Um eine nahtlose Integration und ein optimales Benutzererlebnis zu gewährleisten, sollten Sie bei der Verwendung des `NumberField` die folgenden besten Praktiken beachten:

- **Barrierefreiheit**: Verwenden Sie die `NumberField`-Komponente unter Berücksichtigung der Barrierefreiheit und halten Sie sich an Barrierefreiheitsstandards wie ordnungsgemäße Beschriftung, Unterstützung der Tastaturnavigation und Kompatibilität mit unterstützenden Technologien. Stellen Sie sicher, dass Benutzer mit Behinderungen effektiv mit dem `NumberField` interagieren können.

- **Increment-/Decrement-Tasten nutzen**: Wenn es für Ihre App sinnvoll ist, überlegen Sie, ob Sie Increment- und Decrement-Tasten mit dem `NumberField` verwenden möchten. Dies ermöglicht es den Benutzern, den numerischen Wert mit einem einzigen Klick um einen bestimmten Schritt zu erhöhen oder zu verringern.
