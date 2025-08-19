---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: 2fcf0727f1bcfd60a2800bad252733ba
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

<ParentLink parent="Field" />

Sie können die `NumberField`-Komponente verwenden, um numerische Eingaben von einem Benutzer zu akzeptieren. Sie stellt sicher, dass nur gültige numerische Werte eingegeben werden, und bietet eine bequeme Schnittstelle für die Eingabe von Zahlen.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Wert des Feldes {#field-value}

Die `NumberField`-Komponente speichert ihren Wert als `Double`, was eine genaue Handhabung sowohl von Ganzzahlen als auch von Dezimalzahlen ermöglicht.

### Den aktuellen Wert abrufen {#getting-the-current-value}

Sie können den numerischen Wert, der vom Benutzer eingegeben wurde, wie folgt abrufen:

```java
Double currentValue = numberField.getValue();
```

### Einen neuen Wert setzen {#setting-a-new-value}

Um den Wert programmgesteuert zu setzen:

```java
numberField.setValue(42.5);
```

Wenn kein Wert eingegeben wurde und kein Standardwert festgelegt ist, gibt `getValue()` `null` zurück.

:::tip
Obwohl das Feld so konzipiert ist, dass es nur gültige numerische Eingaben akzeptiert, beachten Sie, dass der zugrunde liegende Wert nullable ist. Testen Sie immer auf null, bevor Sie das Ergebnis verwenden.
:::

## Anwendungen {#usages}

Das `NumberField` eignet sich am besten für Szenarien, in denen das Erfassen, Anzeigen oder Manipulieren von numerischen Daten für Ihre App entscheidend ist. Hier sind einige Beispiele, wann Sie das `NumberField` verwenden sollten:

1. **Formulare für numerische Eingaben**: Bei der Gestaltung von Formularen, die numerische Eingaben erfordern, vereinfacht die Verwendung eines `NumberField` den Eingabeprozess für Benutzer. Dies ist besonders nützlich für Anwendungen, die Benutzerdaten sammeln oder numerische Werte anfordern.

2. **Datenanalyse und Berechnungen**: Ein `NumberField` ist besonders wertvoll in Apps, die Datenanalysen, Berechnungen oder mathematische Operationen umfassen. Es ermöglicht Benutzern, numerische Werte genau einzugeben oder zu manipulieren.

3. **Finanz- und Budgetierungsanwendungen**: Apps, die finanzielle Berechnungen, Budgetierung oder Nachverfolgung von Ausgaben beinhalten, erfordern häufig präzise numerische Eingaben. Ein `NumberField` stellt die genaue Eingabe von Finanzzahlen sicher.

4. **Messung und EinheitConversion**: In Apps, die sich mit Messungen oder EinheitConversion befassen, ist das `NumberField` ideal zum Eingeben numerischer Werte mit Einheiten wie Länge, Gewicht oder Volumen.

## Mindest- und Höchstwert {#min-and-max-value}

Mit der `setMin()`-Methode können Sie den minimal akzeptablen Wert im Zahlenfeld angeben. Wenn ein Benutzer einen Wert eingibt, der unter diesem Schwellenwert liegt, schlägt die Komponente die Validierung des Constraints fehl und gibt entsprechendes Feedback.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Min. erlaubt: 0.0
```

Separat ermöglicht die `setMax()`-Methode das Festlegen des maximal akzeptablen Wertes. Wenn ein Benutzer einen Wert eingibt, der über diesem Limit liegt, wird die Eingabe abgelehnt. Wenn sowohl Mindest- als auch Höchstwerte festgelegt sind, muss das Maximum größer oder gleich dem Minimum sein.

```java
numberField.setMax(100.0); // Max. erlaubt: 100.0
```

In dieser Konfiguration wäre die Eingabe eines Wertes wie -5 oder 150 ungültig, während Werte zwischen 0 und 100 akzeptiert werden.

## Granularität {#granularity}

Sie können die `setStep()`-Methode verwenden, um die Granularität anzugeben, an die der Wert sich halten muss, wenn Sie die Pfeiltasten verwenden, um den Wert zu ändern. Dies erhöht oder verringert den Wert der Komponente bei jedem Schritt. Dies gilt nicht, wenn ein Benutzer einen Wert direkt eingibt, sondern nur, wenn er mit dem `NumberField` über die Pfeiltasten interagiert.

## Platzhaltertext {#placeholder-text}

Sie können Platzhaltertext für das `NumberField` mit der `setPlaceholder()`-Methode festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist und hilft, den Benutzer zur Eingabe geeigneter Informationen in das `NumberField` aufzufordern.

:::tip Geben Sie klaren Kontext für Genauigkeit
Wenn die numerische Eingabe sich auf eine bestimmte Maßeinheit bezieht oder einen bestimmten Kontext hat, bieten Sie klare Beschriftungen oder zusätzliche Informationen, um Benutzer zu leiten und eine genaue Eingabe sicherzustellen.
:::

## Best Practices {#best-practices}

Um eine nahtlose Integration und ein optimales Benutzererlebnis zu gewährleisten, sollten Sie bei der Verwendung des `NumberField` die folgenden Best Practices berücksichtigen:

- **Zugänglichkeit**: Nutzen Sie die `NumberField`-Komponente mit Blick auf die Zugänglichkeit und halten Sie sich an Zugänglichkeitsstandards wie ordnungsgemäße Beschriftung, Unterstützung der Tastaturnavigation und Kompatibilität mit unterstützenden Technologien. Stellen Sie sicher, dass Benutzer mit Behinderungen effektiv mit dem `NumberField` interagieren können.

- **Nutzen Sie Increment/Decrement-Schaltflächen**: Wenn es für Ihre App geeignet ist, ziehen Sie in Betracht, Increment- und Decrement-Schaltflächen mit dem `NumberField` zu verwenden. Dies ermöglicht es Benutzern, den numerischen Wert mit einem einzelnen Klick um einen bestimmten Betrag zu erhöhen oder zu verringern.
