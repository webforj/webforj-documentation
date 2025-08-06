---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: 0d5052fd2f20b391e0eaadbf7c771e5e
---
```jsx
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

<ParentLink parent="Field" />

Sie können die Komponente `NumberField` verwenden, um numerische Eingaben von einem Benutzer zu akzeptieren. Sie stellt sicher, dass nur gültige numerische Werte eingegeben werden und bietet eine bequeme Schnittstelle zum Eingeben von Zahlen.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Feldwert {#field-value}

Die Komponente `NumberField` speichert ihren Wert als `Double`, was eine genaue Handhabung sowohl von Ganzzahlen als auch von Dezimalzahlen ermöglicht.

### Aktuellen Wert abrufen {#getting-the-current-value}

Sie können den vom Benutzer eingegebenen numerischen Wert abrufen mit:

```java
Double currentValue = numberField.getValue();
```

### Einen neuen Wert festlegen {#setting-a-new-value}

Um das Feld programmgesteuert festzulegen:

```java
numberField.setValue(42.5);
```

Wenn kein Wert eingegeben wurde und kein Standardwert festgelegt ist, gibt `getValue()` `null` zurück.

:::tip
Obwohl das Feld so konzipiert ist, dass es nur gültige numerische Eingaben akzeptiert, denken Sie daran, dass der zugrunde liegende Wert null sein kann. Überprüfen Sie immer auf null, bevor Sie das Ergebnis verwenden.
:::

## Anwendungen {#usages}

Das `NumberField` ist besonders in Szenarien nützlich, in denen das Erfassen, Anzeigen oder Manipulieren von numerischen Daten für Ihre App wichtig ist. Hier sind einige Beispiele, wann Sie das `NumberField` verwenden sollten:

1. **Zahlen-Eingabeformulare**: Bei der Gestaltung von Formularen, die numerische Eingaben erfordern, vereinfacht die Verwendung eines `NumberField` den Eingabeprozess für die Benutzer. Dies ist besonders nützlich für Anwendungen, die Benutzerdaten erfassen oder numerische Werte erfordern.

2. **Datenanalyse und Berechnungen**: Ein `NumberField` ist besonders wertvoll in Apps, die Datenanalyse, Berechnungen oder mathematische Operationen beinhalten. Es ermöglicht Benutzern, numerische Werte genau einzugeben oder zu manipulieren.

3. **Finanz- und Budgetierungsanwendungen**: Apps, die finanzielle Berechnungen, Budgetierung oder das Nachverfolgen von Ausgaben beinhalten, erfordern oft präzise numerische Eingaben. Ein `NumberField` stellt die genaue Eingabe finanzieller Beträge sicher.

4. **Messung und Einheit Umrechnung**: In Apps, die sich mit Messungen oder Einheit Umrechnungen befassen, ist das `NumberField` ideal für die Eingabe numerischer Werte mit Einheiten wie Länge, Gewicht oder Volumen.

## Minimal- und Maximalwert {#min-and-max-value}

Mit der Methode `setMin()` können Sie den minimal akzeptablen Wert im Zahlenfeld festlegen. Wenn ein Benutzer einen Wert unterhalb dieses Schwellenwerts eingibt, schlägt die Komponentenvalidierung fehl und gibt entsprechendes Feedback.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Mindestwert: 0.0
```

Separat ermöglicht die Methode `setMax()`, den maximal akzeptablen Wert festzulegen. Wenn ein Benutzer einen Wert höher als dieses Limit eingibt, wird die Eingabe abgelehnt. Wenn sowohl Mindest- als auch Höchstwerte festgelegt sind, muss der Höchstwert größer oder gleich dem Mindestwert sein.

```java
numberField.setMax(100.0); // Höchstwert: 100.0
```

In dieser Konfiguration wäre die Eingabe eines Wertes wie -5 oder 150 ungültig, während Werte zwischen 0 und 100 akzeptiert werden.

## Granularität {#granularity}

Sie können die Methode `setStep()` verwenden, um die Granularität anzugeben, die der Wert einhalten muss, wenn Sie die Pfeiltasten verwenden, um den Wert zu ändern. Dies erhöht oder verringert den Wert der Komponente bei jeder Interaktion um einen bestimmten Schritt. Dies gilt nicht, wenn ein Benutzer einen Wert direkt eingibt, sondern nur, wenn er mit dem `NumberField` die Pfeiltasten verwendet.

## Platzhaltertext {#placeholder-text}

Sie können Platzhaltertext für das `NumberField` mit der Methode `setPlaceholder()` festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist, um den Benutzer zu ermutigen, die passende Eingabe in das `NumberField` einzugeben.

:::tip Geben Sie einen klaren Kontext für Genauigkeit
Wenn die numerische Eingabe sich auf eine bestimmte Einheit bezieht oder einen bestimmten Kontext hat, stellen Sie klare Beschriftungen oder zusätzliche Informationen bereit, um die Benutzer zu leiten und eine genaue Eingabe sicherzustellen.
:::

## Best Practices {#best-practices}

Um eine nahtlose Integration und ein optimales Benutzererlebnis zu gewährleisten, berücksichtigen Sie die folgenden Best Practices, wenn Sie das `NumberField` verwenden:

- **Zugänglichkeit**: Nutzen Sie die Komponente `NumberField` unter Berücksichtigung der Zugänglichkeit und beachten Sie Zugänglichkeitsstandards wie angemessene Beschriftungen, Unterstützung der Tastaturnavigation und Kompatibilität mit Hilfstools. Stellen Sie sicher, dass Benutzer mit Behinderungen effektiv mit dem `NumberField` interagieren können.

- **Inkrement-/Dekrementtaste verwenden**: Wenn es für Ihre App geeignet ist, ziehen Sie in Betracht, Inkrement- und Dekrementtasten mit dem `NumberField` zu verwenden. Dies ermöglicht es den Benutzern, den numerischen Wert bei einem Klick um einen bestimmten Schritt zu erhöhen oder zu verringern.
```
