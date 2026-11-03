---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 0d350dce3bf30a09bc54f089107b7a25
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Die `TextField`-Komponente erlaubt es Benutzern, Text in einer einzelnen Zeile einzugeben und zu bearbeiten. Sie können das Feld so konfigurieren, dass es eine bestimmte virtuelle Tastatur anzeigt, wie z.B. ein numerisches Tastenfeld, Eingabe für E-Mails, Telefonnummern oder URLs. Die Komponente bietet auch eine integrierte Validierung, um Werte abzulehnen, die nicht dem angegebenen Typ entsprechen.

<!-- INTRO_END -->

## Verwendungen {#usages}

<ParentLink parent="Field" />

Die `TextField`-Komponente eignet sich für eine Vielzahl von Szenarien, in denen Texteingaben oder -bearbeitungen erforderlich sind. Hier sind einige Beispiele, wann die `TextField` verwendet werden sollte:

1. **Formulareingaben**: Ein `TextField` wird häufig in Formularen verwendet, um Benutzereingaben wie Namen, Adressen oder Kommentare zu erfassen. Es ist am besten, ein `TextField` in einer App zu verwenden, wenn die Eingabe im Allgemeinen kurz genug ist, um in eine Zeile zu passen.

2. **Suchfunktionalität**: `TextField` kann verwendet werden, um ein Suchfeld bereitzustellen, das es Benutzern ermöglicht, Suchanfragen einzugeben.

3. **Textbearbeitung**: Ein `TextField` ist ideal für Apps, die Textbearbeitung oder die Erstellung von Inhalten erfordern, wie Dokumenteneditoren, Chat-Apps oder Notizen-Apps.

## Typen {#types}

Sie können den Typ des `TextField` mithilfe der `setType()`-Methode festlegen. Genauso können Sie den Typ mit der `getType()`-Methode abrufen, die einen Enum-Wert zurückgibt.

- `Type.TEXT`: Dies ist der Standardtyp für ein `TextField` und entfernt automatisch Zeilenumbrüche aus dem Eingabewert.

- `Type.EMAIL`: Dieser Typ ist für die Eingabe von E-Mail-Adressen gedacht. Er validiert die Eingabe gemäß der standardmäßigen E-Mail-Syntax. Darüber hinaus stellt er kompatiblen Browsern und Geräten eine dynamische Tastatur zur Verfügung, die den Tippvorgang erleichtert, indem häufig verwendete Tasten wie <kbd>.com</kbd> und <kbd>@</kbd> enthalten sind.

  :::note
  Während diese Validierung das Format der E-Mail-Adresse bestätigt, garantiert sie nicht, dass die E-Mail existiert.
  :::

- `Type.TEL`: Dieser Typ wird für die Eingabe einer Telefonnummer verwendet. Das Feld zeigt auf einigen Geräten mit dynamischen Tastaturen ein Telefon-Tastenfeld an.

- `Type.URL`: Dieser Typ ist für die Eingabe von URLs gedacht. Er überprüft, ob ein Benutzer eine URL eingibt, die ein Schema und einen Domainnamen beinhaltet, zum Beispiel: https://webforj.com. Zudem stellt er kompatiblen Browsern und Geräten eine dynamische Tastatur zur Verfügung, die den Tippvorgang erleichtert, indem häufig verwendete Tasten wie <kbd>:</kbd>, <kbd>/</kbd> und <kbd>.com</kbd> enthalten sind.

- `Type.SEARCH`: Ein einzeiliges Textfeld für die Eingabe von Suchstrings. Zeilenumbrüche werden automatisch aus dem Eingabewert entfernt.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Feldwert {#field-value}

Der Wert eines `TextField` stellt die aktuelle Benutzereingabe als String dar. In webforJ kann dieser über die Methode `getValue()` abgerufen oder programmgesteuert mit `setValue(String)` aktualisiert werden.

```java
// Setze den initialen Inhalt
textField.setValue("Initialer Inhalt");

// Hole den aktuellen Wert
String text = textField.getValue();
```

Wenn die Methode `getValue()` bei einem Feld ohne aktuellen Wert verwendet wird, gibt sie einen leeren String (`""`) zurück.

Dieses Verhalten ist konsistent mit der Art und Weise, wie das HTML-Element `<input type="text">` seinen Wert über JavaScript offenlegt.

:::tip Wertbehandlung mit Validierung kombinieren
Wenden Sie Einschränkungen wie ein [Muster](#pattern-matching), eine [minimale Länge](#setminlength) oder eine [maximale Länge](#setmaxlength) an, um zu definieren, wann ein Wert als gültig betrachtet wird.
:::

## Platzhaltertext {#placeholder-text}

Sie können Platzhaltertext für das `TextField` mithilfe der `setPlaceholder()`-Methode festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist, und hilft dem Benutzer, relevante Eingaben in das `TextField` einzugeben.

## Ausgewählter Text {#selected-text}

Es ist möglich, mit der `TextField`-Klasse zu interagieren, um den ausgewählten Text des Benutzers abzurufen und Informationen über die Auswahl des Benutzers zu erhalten. Sie können den ausgewählten Text im `TextField` mit der Methode `getSelectedText()` abrufen. Dieses Verhalten würde häufig in Verbindung mit einem Ereignis verwendet werden.

```java
TextField textField = new TextField("Geben Sie etwas ein...");
Button getSelectionBtn = new Button("Ausgewählten Text abrufen");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Ausgewählter Text: " + selected);
});
```

Ebenso ist es möglich, den aktuellen Auswahlbereich des `TextField` mit der Methode `getSelectionRange()` abzurufen. Diese gibt ein `SelectionRange`-Objekt zurück, das die Start- und Endindizes des ausgewählten Textes darstellt.

:::tip Wertänderungen und Auswahl
Verwenden Sie `onValueChange()`, um Änderungen am Wert des Feldes zu behandeln. Ihr `ValueChangeEvent<String>` stellt den neuen Wert über `event.getValue()` zur Verfügung, enthält jedoch keine Auswahldaten. Um die aktuelle Auswahl zu lesen, rufen Sie `getSelectedText()` oder `getSelectionRange()` für das Feld auf.
:::

## Mustererkennung {#pattern-matching}

Sie können die Methode `setPattern()` verwenden, um eine Validierungsregel für das `TextField` mit einem regulären Ausdruck festzulegen. Die Festlegung eines Musters fügt eine Einschränkungsvalidierung hinzu, die erfordert, dass der Eingabewert dem angegebenen Muster entspricht.

Das Muster muss ein gültiger [JavaScript-regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie es vom Browser interpretiert wird. Das `u` (Unicode)-Flag wird intern angewendet, um eine genaue Übereinstimmung mit Unicode-Zeichen zu gewährleisten. Wickeln Sie das Muster nicht in Schrägstriche (`/`), da diese nicht erforderlich sind und als Literalzeichen behandelt werden.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // z.B. ABC12
```

Wenn kein Muster angegeben wird oder die Syntax ungültig ist, wird die Validierungsregel ignoriert.

:::tip Kontextuelle Hilfe
Wenn Sie komplexe Muster für ein `TextField` verwenden, ziehen Sie in Betracht, eine Kombination aus den Methoden `setLabel()`, `setHelperText()` und `setTooltipText()` zu verwenden,
um zusätzliche Hinweise und Anleitungen zu geben.
:::

## Minimale und maximale Länge {#minimum-and-maximum-length}

Die `TextField`-Komponente unterstützt die Einschränkungsvalidierung basierend auf der Anzahl der vom Benutzer eingegebenen Zeichen. Dies kann mithilfe der Methoden `setMinLength()` und `setMaxLength()` gesteuert werden. Verwenden Sie beide Methoden, um eine klare Grenze für akzeptable Eingabelängen zu definieren.

:::info Längeneinschränkungen
Standardmäßig zeigt das Feld eine Nachricht an, wenn der Eingabewert außerhalb des zulässigen Bereichs liegt und teilt dem Benutzer mit, ob er seine Eingabe verkürzen oder verlängern muss. Diese Nachricht kann mit der Methode `setInvalidMessage()` überschrieben werden.
:::

### `setMinLength()` {#setminlength}

Diese Methode legt die **minimale Anzahl von UTF-16-Codeeinheiten** fest, die eingegeben werden müssen, damit das Feld als gültig betrachtet wird. Der Wert muss eine ganze Zahl sein und darf die konfigurierte maximale Länge nicht überschreiten.

```java
textField.setMinLength(5); // Der Benutzer muss mindestens 5 Zeichen eingeben
```

Wenn die Eingabe weniger Zeichen als die minimal erforderliche Anzahl enthält, schlägt die Eingabe die Einschränkungsvalidierung fehl. Diese Regel gilt nur, wenn der Benutzer den Wert des Feldes ändert.

### `setMaxLength()` {#setmaxlength}

Diese Methode legt die **maximale Anzahl von UTF-16-Codeeinheiten** fest, die im Textfeld erlaubt sind. Der Wert muss `0` oder größer sein. Wenn er nicht festgelegt ist oder auf einen ungültigen Wert festgelegt wird, wird keine maximale Länge durchgesetzt.

```java
textField.setMaxLength(20); // Der Benutzer darf nicht mehr als 20 Zeichen eingeben
```

Das Feld schlägt die Einschränkungsvalidierung fehl, wenn die Eingabelänge die maximal zulässige Länge überschreitet. Wie bei `setMinLength()` wird diese Validierung nur ausgelöst, wenn der Wert vom Benutzer geändert wird.

## Best Practices {#best-practices}

Der folgende Abschnitt beschreibt einige empfohlene Best Practices für die Verwendung des `TextField`.

- **Klare Labels und Anweisungen bereitstellen**: Kennzeichnen Sie das `TextField` deutlich, um anzugeben, welche Art von Informationen die Benutzer eingeben sollen. Geben Sie zusätzlichen Text oder Platzhalterwerte an, um den Benutzern Hinweise zu geben und Erwartungen an die erforderliche Eingabe zu setzen.

- **Rechtschreibprüfung und Autovervollständigung**: Wenn zutreffend, ziehen Sie in Betracht, Rechtschreibprüfung mit `setSpellCheck()` und/oder die Autovervollständigung in einem `TextField` zu verwenden. Diese Funktionen können den Benutzern helfen, Informationen schneller einzugeben und Fehler zu reduzieren, indem sie vorgeschlagene Werte basierend auf früheren Eingaben oder vordefinierten Optionen bereitstellen.

- **Barrierefreiheit**: Stellen Sie klare Labels, Tastaturnavigation und Unterstützung für Hilfstechnologien bereit, damit Benutzer mit Behinderungen das Feld verwenden können.
