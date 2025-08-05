---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
sidebar_class_name: updated-content
_i18n_hash: d582e67d9cfff3b1934f0e3b1a8396ab
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

<ParentLink parent="Field" />

Die `TextField`-Komponente ermöglicht es Benutzern, Text in einer einzigen Zeile einzugeben und zu bearbeiten. Sie können das Feld so konfigurieren, dass eine bestimmte virtuelle Tastatur angezeigt wird, z. B. eine numerische Tastatur, E-Mail-Eingabe, Telefon-Eingabe oder URL-Eingabe. Die Komponente bietet außerdem eine integrierte Validierung, um Werte abzulehnen, die nicht dem angegebenen Typ entsprechen.

## Usages {#usages}

Das `TextField` eignet sich für eine Vielzahl von Szenarien, in denen Textinput oder -bearbeitung erforderlich ist. Hier sind einige Beispiele, wann das `TextField` verwendet werden sollte:

1. **Formulareingaben**: Ein `TextField` wird häufig in Formularen verwendet, um Benutzereingaben wie Namen, Adressen oder Kommentare zu erfassen. Es ist am besten, ein `TextField` in einer App zu verwenden, wenn die Eingabe in der Regel kurz genug ist, um in eine Zeile zu passen.

2. **Suchfunktionalität**: Das `TextField` kann verwendet werden, um ein Suchfeld bereitzustellen, in das Benutzer Suchanfragen eingeben können.

3. **Textbearbeitung**: Ein `TextField` eignet sich ideal für Apps, die Textbearbeitung oder Inhaltserstellung erfordern, wie z. B. Dokumenteneditoren, Chat-Apps oder Notizanwendungen.

## Types {#types}

Sie können den Typ des TextFields mit der Methode `setType()` festlegen. Ebenso können Sie den Typ mit der Methode `getType()` abrufen, die einen Enum-Wert zurückgibt.

- `Type.TEXT`: Dies ist der Standardtyp für ein `TextField` und entfernt automatisch Zeilenumbrüche aus dem Eingabewert.

- `Type.EMAIL`: Dieser Typ dient zum Eingeben von E-Mail-Adressen. Er validiert die Eingabe gemäß der standardmäßigen E-Mail-Syntax. Darüber hinaus wird kompatiblen Browsern und Geräten eine dynamische Tastatur bereitgestellt, die den Eingabeprozess vereinfacht, indem häufig verwendete Tasten wie <kbd>.com</kbd> und <kbd>@</kbd> enthalten sind.

  :::note
  Während diese Validierung das Format der E-Mail-Adresse bestätigt, garantiert sie nicht, dass die E-Mail existiert.
  :::

- `Type.TEL`: Dieser Typ wird zum Eingeben einer Telefonnummer verwendet. Das Feld zeigt auf einigen Geräten mit dynamischen Tastaturen eine Telefon-Tastatur an.

- `Type.URL`: Dieser Typ dient zum Eingeben von URLs. Er validiert, ob ein Benutzer eine URL eingibt, die ein Schema und einen Domainnamen enthält, beispielsweise: https://webforj.com. Darüber hinaus wird kompatiblen Browsern und Geräten eine dynamische Tastatur bereitgestellt, die den Eingabeprozess vereinfacht, indem häufig verwendete Tasten wie <kbd>:</kbd>, <kbd>/</kbd> und <kbd>.com</kbd> enthalten sind.

- `Type.SEARCH`: Ein einzeiliges Textfeld zum Eingeben von Suchstrings. Zeilenumbrüche werden automatisch aus dem Eingabewert entfernt.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Field value {#field-value}

Der Wert eines `TextField` repräsentiert die aktuelle Benutzereingabe als String. In webforJ kann dies mit der Methode `getValue()` abgerufen oder programmgesteuert mit `setValue(String)` aktualisiert werden.

```java
//Setze den anfänglichen Inhalt
textField.setValue("Anfänglicher Inhalt");

//Rufe den aktuellen Wert ab
String text = textField.getValue();
```

Wenn die Methode `getValue()` auf einem Feld ohne aktuellen Wert verwendet wird, gibt sie einen leeren String (`""`) zurück.

Dieses Verhalten ist konsistent mit dem, wie das HTML `<input type="text">`-Element seinen Wert über JavaScript bereitstellt.

:::tip Kombinieren Sie die Wertbehandlung mit der Validierung
Wenden Sie Einschränkungen wie ein [Muster](#pattern-matching), [Mindestlänge](#setminlength) oder eine [Maximallänge](#setmaxlength) an, um festzulegen, wann ein Wert als gültig angesehen wird. 
:::

## Placeholder text {#placeholder-text}

Sie können Platzhaltertext für das `TextField` mit der Methode `setPlaceholder()` festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist, und hilft, den Benutzer dazu anzuregen, geeignete Eingaben in das `TextField` einzugeben.

## Selected text {#selected-text}

Es ist möglich, mit der `TextField`-Klasse zu interagieren, um den ausgewählten Text des Benutzers abzurufen und Informationen über die Auswahl des Benutzers zu erhalten. Sie können den ausgewählten Text im `TextField` mit der Methode `getSelectedText()` abrufen. Dieses Verhalten wird üblicherweise in Verbindung mit einem Ereignis verwendet.

```java
TextField textField = new TextField("Geben Sie etwas ein...");
Button getSelectionBtn = new Button("Ausgewählten Text abrufen");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Ausgewählter Text: " + selected);
});
```

Ähnlich ist es möglich, den aktuellen Auswahlbereich des `TextField` mit der Methode `getSelectionRange()` abzurufen. Dies gibt ein `SelectionRange`-Objekt zurück, das die Start- und Endindizes des ausgewählten Texts darstellt.

:::tip Verwendung von `getSelectedText()` vs. Ereignis-Payload
Während Sie `getSelectedText()` manuell innerhalb eines Ereignis-Handlers aufrufen können, ist es effizienter, die Auswahldaten zu verwenden, die im Payload des Ereignisses bereitgestellt werden — z. B. in einem `SelectionChangeEvent` — um zusätzliche Nachschlagevorgänge zu vermeiden.
:::

## Pattern matching {#pattern-matching}

Sie können die Methode `setPattern()` verwenden, um eine Validierungsregel für das `TextField` mithilfe eines regulären Ausdrucks zu definieren. Das Festlegen eines Musters fügt eine Einschränkungsvalidierung hinzu, die erfordert, dass der Eingabewert mit dem angegebenen Muster übereinstimmt.

Das Muster muss ein gültiger [JavaScript-Regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie er vom Browser interpretiert wird. Das `u` (Unicode)-Flag wird intern angewendet, um eine genaue Übereinstimmung von Unicode-Codepunkten sicherzustellen. Wickeln Sie das Muster nicht in Schrägstriche (`/`), da diese nicht erforderlich sind und als Literalzeichen behandelt werden.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // z.B. ABC12
```

Wenn kein Muster bereitgestellt wird oder die Syntax ungültig ist, wird die Validierungsregel ignoriert.

:::tip Kontextbezogene Hilfe
Wenn Sie komplexe Muster für ein `TextField` verwenden, ziehen Sie in Betracht, eine Kombination der Methoden `setLabel()`, `setHelperText()` und `setTooltipText()` zu verwenden, um zusätzliche Hinweise und Anleitungen bereitzustellen.
:::

## Minimum and maximum length {#minimum-and-maximum-length}

Die `TextField`-Komponente unterstützt die Einschränkungsvalidierung auf der Grundlage der vom Benutzer eingegebenen Anzahl von Zeichen. Dies kann mit den Methoden `setMinLength()` und `setMaxLength()` gesteuert werden. Verwenden Sie beide Methoden, um eine klare Grenze der akzeptablen Eingabelängen festzulegen.

:::info Längenvoraussetzungen
Standardmäßig zeigt das Feld eine Nachricht an, wenn der Eingabewert außerhalb des Bereichs liegt, die dem Benutzer mitteilt, ob er seine Eingabe verkürzen oder verlängern muss. Diese Nachricht kann mit der Methode `setInvalidMessage()` überschrieben werden.
:::

### `setMinLength()` {#setminlength}

Diese Methode setzt die **minimale Anzahl von UTF-16-Codeeinheiten**, die eingegeben werden müssen, damit das Feld als gültig angesehen wird. Der Wert muss eine ganze Zahl sein und darf nicht die konfigurierte Maximallänge überschreiten.

```java
textField.setMinLength(5); // Der Benutzer muss mindestens 5 Zeichen eingeben
```

Wenn die Eingabe weniger Zeichen enthält als die erforderliche Mindestanzahl, schlägt die Eingabe die Einschränkungsvalidierung fehl. Diese Regel gilt nur, wenn der Benutzer den Wert des Feldes ändert.

### `setMaxLength()` {#setmaxlength}

Diese Methode setzt die **maximale Anzahl von UTF-16-Codeeinheiten**, die im Textfeld erlaubt sind. Der Wert muss `0` oder größer sein. Wenn er nicht gesetzt oder auf einen ungültigen Wert gesetzt wird, wird keine Maximaleinschränkung durchgesetzt.

```java
textField.setMaxLength(20); // Der Benutzer kann nicht mehr als 20 Zeichen eingeben
```

Das Feld schlägt die Einschränkungsvalidierung fehl, wenn die Eingabelänge die maximale Länge überschreitet. Wie bei `setMinLength()` wird diese Validierung nur ausgelöst, wenn der Wert vom Benutzer geändert wird.

## Best practices {#best-practices}

Der folgende Abschnitt beschreibt einige empfohlene Best Practices für die Nutzung des `TextField`.

- **Bereitstellung klarer Labels und Anleitungen**: Beschriften Sie das `TextField` klar, um anzugeben, welche Informationen Benutzer eingeben sollen. Geben Sie zusätzlichen Anleitungstext oder Platzhalterwerte an, um Benutzer zu führen und Erwartungen für die erforderlichen Eingaben festzulegen.

- **Rechtschreibprüfung und Auto-Vervollständigung**: Wenn möglich, ziehen Sie in Betracht, eine Rechtschreibprüfung mit `setSpellCheck()` und/oder die Verwendung von Auto-Vervollständigung in einem `TextField` zu nutzen. Diese Funktionen können Benutzern helfen, Informationen schneller einzugeben und Fehler zu reduzieren, indem sie vorgeschlagene Werte basierend auf früheren Eingaben oder vordefinierten Optionen bereitstellen.

- **Barrierefreiheit**: Nutzen Sie die `TextField`-Komponente unter Berücksichtigung der Barrierefreiheit, indem Sie die Barrierefreiheitsstandards, wie z.B. ordnungsgemäße Beschriftung, Unterstützung der Tastaturnavigation und Kompatibilität mit Hilfst Technologien, einhalten. Stellen Sie sicher, dass Benutzer mit Behinderungen effektiv mit dem `TextField` interagieren können.
