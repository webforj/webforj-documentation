---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 138207c2dd99dac9837172067950ab2f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Die `TextField`-Komponente ermöglicht es Benutzern, Text in einer einzelnen Zeile einzugeben und zu bearbeiten. Sie können das Feld so konfigurieren, dass eine bestimmte virtuelle Tastatur angezeigt wird, z. B. eine numerische Tastatur, eine E-Mail-Eingabe, eine Telefonnummer oder eine URL-Eingabe. Die Komponente bietet auch eine integrierte Validierung, um Werte abzulehnen, die den angegebenen Typ nicht erfüllen.

<!-- INTRO_END -->

## Verwendung {#usages}

<ParentLink parent="Field" />

Das `TextField` ist für eine Vielzahl von Szenarien geeignet, in denen Text eingegeben oder bearbeitet werden muss. Hier sind einige Beispiele, wann das `TextField` verwendet werden sollte:

1. **Formulareingaben**: Ein `TextField` wird häufig in Formularen verwendet, um Benutzereingaben wie Namen, Adressen oder Kommentare zu erfassen. Es ist am besten, ein `TextField` in einer App zu verwenden, wenn die Eingabe allgemein kurz genug ist, um in einer Zeile zu passen.

2. **Suchfunktionalität**: Das `TextField` kann verwendet werden, um ein Suchfeld bereitzustellen, in das Benutzer Suchanfragen eingeben können.

3. **Textbearbeitung**: Ein `TextField` ist ideal für Apps, die Textbearbeitung oder Inhaltserstellung erfordern, wie z. B. Dokumenteneditoren, Chat-Apps oder Notizen-Apps.

## Typen {#types}

Sie können den Typ des TextField mit der Methode `setType()` festlegen. Ebenso können Sie den Typ mit der Methode `getType()` abrufen, die einen Enum-Wert zurückgibt.

- `Type.TEXT`: Dies ist der Standardtyp für ein `TextField` und entfernt automatisch Zeilenumbrüche aus dem Eingabewert.

- `Type.EMAIL`: Dieser Typ ist für die Eingabe von E-Mail-Adressen gedacht. Er validiert die Eingabe gemäß der standardmäßigen E-Mail-Syntax. Darüber hinaus bietet er kompatiblen Browsern und Geräten eine dynamische Tastatur, die den Eingabeprozess erleichtert, indem häufig verwendete Tasten wie <kbd>.com</kbd> und <kbd>@</kbd> enthalten sind.

  :::note
  Während diese Validierung das Format der E-Mail-Adresse bestätigt, gewährleistet sie nicht, dass die E-Mail existiert.
  :::

- `Type.TEL`: Dieser Typ wird zur Eingabe einer Telefonnummer verwendet. Das Feld zeigt auf einigen Geräten mit dynamischen Tastaturen eine Telefontastatur an.

- `Type.URL`: Dieser Typ ist für die Eingabe von URLs gedacht. Er validates, ob ein Benutzer eine URL eingibt, die ein Schema und einen Domainnamen enthält, zum Beispiel: https://webforj.com. Darüber hinaus bietet er kompatiblen Browsern und Geräten eine dynamische Tastatur, die den Eingabeprozess erleichtert, indem häufig verwendete Tasten wie <kbd>:</kbd>, <kbd>/</kbd> und <kbd>.com</kbd> enthalten sind.

- `Type.SEARCH`: Ein einzeiliges Textfeld zur Eingabe von Suchbegriffen. Zeilenumbrüche werden automatisch aus dem Eingabewert entfernt.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
height='225px'
/>

## Feldwert {#field-value}

Der Wert eines `TextField` stellt die aktuelle Benutzereingabe als Zeichenfolge dar. In webforJ kann dies mit der Methode `getValue()` abgerufen oder programmgesteuert mit `setValue(String)` aktualisiert werden.

```java
//Setze den anfänglichen Inhalt
textField.setValue("Anfangsinhalt");

//Aktualisiere den aktuellen Wert
String text = textField.getValue();
```

Wenn die Methode `getValue()` auf einem Feld ohne aktuellen Wert verwendet wird, gibt sie eine leere Zeichenfolge (`""`) zurück.

Dieses Verhalten entspricht dem, wie das HTML-Element `<input type="text">` seinen Wert über JavaScript bereitstellt.

:::tip Kombinieren Sie die Wertbehandlung mit der Validierung
Wenden Sie Einschränkungen wie ein [Muster](#pattern-matching), [Mindestlänge](#setminlength) oder eine [Höchstlänge](#setmaxlength) an, um zu definieren, wann ein Wert als gültig betrachtet wird. 
:::

## Platzhaltertext {#placeholder-text}

Sie können Platzhaltertext für das `TextField` mit der Methode `setPlaceholder()` festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist, und hilft, den Benutzer zu motivieren, geeignete Eingaben in das `TextField` einzugeben.

## Ausgewählter Text {#selected-text}

Es ist möglich, mit der `TextField`-Klasse zu interagieren, um den vom Benutzer ausgewählten Text abzurufen und Informationen über die Auswahl des Benutzers zu erhalten. Sie können den ausgewählten Text im `TextField` mit der Methode `getSelectedText()` abrufen. Dieses Verhalten würde üblicherweise in Verbindung mit einem Ereignis verwendet.

```java
TextField textField = new TextField("Etwas eingeben...");
Button getSelectionBtn = new Button("Ausgewählten Text abrufen");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Ausgewählter Text: " + selected);
});
```

Ebenso ist es möglich, den aktuellen Auswahlbereich des `TextField` mit der Methode `getSelectionRange()` abzurufen. Dies gibt ein `SelectionRange`-Objekt zurück, das die Start- und Endindizes des ausgewählten Textes darstellt.

:::tip Verwendung von `getSelectedText()` vs. Ereignisbelastung
Obwohl Sie `getSelectedText()` manuell in einem Ereignishandler aufrufen können, ist es effizienter, die Auswahl Daten zu verwenden, die im Payload des Ereignisses bereitgestellt werden—wie in einem `SelectionChangeEvent`—um zusätzliche Suchen zu vermeiden.
:::

## Mustererkennung {#pattern-matching}

Sie können die Methode `setPattern()` verwenden, um eine Validierungsregel für das `TextField` mithilfe eines regulären Ausdrucks zu definieren. Das Festlegen eines Musters fügt eine Eingeschränkungsvalidierung hinzu, die erfordert, dass der Eingabewert dem angegebenen Muster entspricht.

Das Muster muss ein gültiger [JavaScript-regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie er vom Browser interpretiert wird. Das `u` (Unicode)-Flag wird intern angewendet, um eine genaue Übereinstimmung von Unicode-Codepunkten sicherzustellen. Wickeln Sie das Muster nicht in Schrägstriche (`/`), da diese nicht erforderlich sind und als literale Zeichen behandelt werden.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // z.B. ABC12
```

Wenn kein Muster bereitgestellt wird oder die Syntax ungültig ist, wird die Validierungsregel ignoriert.

:::tip Kontextuelle Hilfe
Bei der Verwendung komplexer Muster für ein `TextField` sollten Sie eine Kombination der Methoden `setLabel()`, `setHelperText()` und `setTooltipText()` in Betracht ziehen,
um zusätzliche Hinweise und Anleitungen bereitzustellen.
:::

## Minimale und maximale Länge {#minimum-and-maximum-length}

Die `TextField`-Komponente unterstützt die Einschränkungsvalidierung basierend auf der Anzahl der vom Benutzer eingegebenen Zeichen. Dies kann mithilfe der Methoden `setMinLength()` und `setMaxLength()` gesteuert werden. Verwenden Sie beide Methoden, um eine klare Grenze für akzeptable Eingabelängen festzulegen.

:::info Längenanforderungen
Standardmäßig zeigt das Feld eine Nachricht an, wenn der Eingabewert außerhalb des zulässigen Bereichs liegt, um dem Benutzer anzuzeigen, ob er seine Eingabe verkürzen oder verlängern muss. Diese Nachricht kann mit der Methode `setInvalidMessage()` überschrieben werden.
:::

### `setMinLength()` {#setminlength}

Diese Methode legt die **minimale Anzahl von UTF-16-Codeeinheiten** fest, die eingegeben werden müssen, damit das Feld als gültig angesehen wird. Der Wert muss eine ganze Zahl sein und sollte die konfigurierte maximale Länge nicht überschreiten.

```java
textField.setMinLength(5); // Benutzer müssen mindestens 5 Zeichen eingeben
```

Wenn die Eingabe weniger Zeichen als die minimale Anzahl enthält, schlägt die Eingabe die Einschränkungsvalidierung fehl. Diese Regel gilt nur, wenn der Benutzer den Wert des Feldes ändert.

### `setMaxLength()` {#setmaxlength}

Diese Methode legt die **maximale Anzahl von UTF-16-Codeeinheiten** fest, die im Textfeld zulässig sind. Der Wert muss `0` oder größer sein. Wenn er nicht festgelegt oder auf einen ungültigen Wert festgelegt ist, wird keine Obergrenze durchgesetzt.

```java
textField.setMaxLength(20); // Benutzer dürfen nicht mehr als 20 Zeichen eingeben
```

Das Feld schlägt die Einschränkungsvalidierung fehl, wenn die Eingabelänge die Mindestlänge überschreitet. Wie bei `setMinLength()` wird diese Validierung nur ausgelöst, wenn der Wert vom Benutzer geändert wird.

## Best Practices {#best-practices}

Im folgenden Abschnitt werden einige empfohlene Best Practices für die Nutzung des `TextField` umrissen.

- **Klare Labels und Anweisungen bereitstellen**: Beschriften Sie das `TextField` deutlich, um anzugeben, welche Art von Informationen die Benutzer eingeben sollten. Geben Sie zusätzliche Anweisungen oder Platzhalterwerte an, um die Benutzer zu führen und die Erwartungen an die erforderliche Eingabe festzulegen.

- **Rechtschreibprüfung und Auto-Vervollständigung**: Bei Bedarf sollten Sie die Verwendung von Rechtschreibprüfung mit `setSpellCheck()` und/oder die Verwendung von Autocomplete in einem `TextField` in Betracht ziehen. Diese Funktionen können Benutzern helfen, Informationen schneller einzugeben und Fehler zu reduzieren, indem sie vorgeschlagene Werte basierend auf vorherigen Eingaben oder vordefinierten Optionen bereitstellen.

- **Barrierefreiheit**: Verwenden Sie die `TextField`-Komponente mit Barrierefreiheit im Hinterkopf, und achten Sie auf Barrierefreiheitsstandards wie korrekte Beschriftung, Unterstützung der Tastaturnavigation und Kompatibilität mit Hilfstechnologien. Stellen Sie sicher, dass Benutzer mit Behinderungen effektiv mit dem `TextField` interagieren können.
