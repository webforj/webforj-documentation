---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: e972f03e1d4deb1802bc4f56104e61b3
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Die `TextField`-Komponente ermöglicht es Benutzern, Text in einer einzelnen Zeile einzugeben und zu bearbeiten. Sie können das Feld so konfigurieren, dass eine bestimmte virtuelle Tastatur angezeigt wird, wie z. B. ein numerisches Tastenfeld, E-Mail-Eingabe, Telefon-Eingabe oder URL-Eingabe. Die Komponente bietet auch eine integrierte Validierung, um Werte abzulehnen, die nicht dem angegebenen Typ entsprechen.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="Field" />

Das `TextField` ist für eine Vielzahl von Szenarien geeignet, in denen Text eingeben oder bearbeitet werden muss. Hier sind einige Beispiele, wann das `TextField` verwendet werden sollte:

1. **Formulareingaben**: Ein `TextField` wird häufig in Formularen verwendet, um Benutzereingaben zu erfassen, wie z. B. Namen, Adressen oder Kommentare. Es ist am besten, ein `TextField` in einer App zu verwenden, wenn die Eingabe normalerweise kurz genug ist, um in eine Zeile zu passen.

2. **Suchfunktionalität**: Das `TextField` kann verwendet werden, um ein Suchfeld bereitzustellen, in das Benutzer Suchanfragen eingeben können.

3. **Textbearbeitung**: Ein `TextField` ist ideal für Apps, die Textbearbeitung oder Inhaltserstellung erfordern, wie z. B. Dokumenteneditoren, Chat-Apps oder Notiz-Apps.

## Types {#types}

Sie können den Typ des TextFields mit der Methode `setType()` festlegen. Ebenso können Sie den Typ mit der Methode `getType()` abrufen, die einen Enum-Wert zurückgibt.

- `Type.TEXT`: Dies ist der Standardtyp für ein `TextField` und entfernt automatisch Zeilenumbrüche aus dem Eingabewert.

- `Type.EMAIL`: Dieser Typ ist für die Eingabe von E-Mail-Adressen. Er validiert die Eingabe gemäß der standardmäßigen E-Mail-Syntax. Darüber hinaus stellt er kompatiblen Browsern und Geräten eine dynamische Tastatur zur Verfügung, die den Eingabeprozess vereinfacht, indem häufig verwendete Tasten wie <kbd>.com</kbd> und <kbd>@</kbd> einbezogen werden.

  :::note
  Während diese Validierung das Format der E-Mail-Adresse bestätigt, garantiert sie nicht, dass die E-Mail existiert.
  :::

- `Type.TEL`: Dieser Typ wird für die Eingabe einer Telefonnummer verwendet. Das Feld zeigt auf einigen Geräten mit dynamischen Tastenfeldern ein Telefon-Tastaturfeld an.

- `Type.URL`: Dieser Typ ist für die Eingabe von URLs. Er validiert, ob ein Benutzer eine URL eingibt, die ein Schema und einen Domainnamen enthält, z.B.: https://webforj.com. Darüber hinaus stellt er kompatiblen Browsern und Geräten eine dynamische Tastatur zur Verfügung, die den Eingabeprozess vereinfacht, indem häufig verwendete Tasten wie <kbd>:</kbd>, <kbd>/</kbd> und <kbd>.com</kbd> einbezogen werden.

- `Type.SEARCH`: Ein einzeiliges Textfeld zur Eingabe von Suchzeichenfolgen. Zeilenumbrüche werden automatisch aus dem Eingabewert entfernt.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Field value {#field-value}

Der Wert eines `TextField` stellt die aktuelle Benutzereingabe als String dar. In webforJ kann dies mit der Methode `getValue()` abgerufen oder programmgesteuert mit `setValue(String)` aktualisiert werden.

```java
//Setze den anfänglichen Inhalt
textField.setValue("Initial content");

//Rufe den aktuellen Wert ab
String text = textField.getValue();
```

Wenn die Methode `getValue()` auf einem Feld ohne aktuellen Wert verwendet wird, gibt sie eine leere Zeichenfolge (`""`) zurück.

Dieses Verhalten entspricht dem, wie das HTML-Element `<input type="text">` seinen Wert über JavaScript bereitstellt.

:::tip Wertbehandlung mit Validierung kombinieren
Wenden Sie Einschränkungen wie ein [Muster](#pattern-matching), [minimale Länge](#setminlength) oder eine [maximale Länge](#setmaxlength) an, um zu definieren, wann ein Wert als gültig betrachtet wird. 
:::

## Placeholder text {#placeholder-text}

Sie können Platzhaltertext für das `TextField` mit der Methode `setPlaceholder()` festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist, um den Benutzer zu ermutigen, geeignete Eingaben in das `TextField` einzugeben.

## Selected text {#selected-text}

Es ist möglich, mit der `TextField`-Klasse zu interagieren, um den vom Benutzer ausgewählten Text abzurufen und Informationen über die Auswahl des Benutzers zu erhalten. Sie können den ausgewählten Text im `TextField` mit der Methode `getSelectedText()` abrufen. Dieses Verhalten wird normalerweise in Verbindung mit einem Ereignis verwendet.

```java
TextField textField = new TextField("Geben Sie etwas ein...");
Button getSelectionBtn = new Button("Ausgewählten Text abrufen");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Ausgewählter Text: " + selected);
});
```

Ebenso ist es möglich, den aktuellen Auswahlbereich des `TextField` mit der Methode `getSelectionRange()` abzurufen. Dies gibt ein `SelectionRange`-Objekt zurück, das die Start- und Endindizes des ausgewählten Texts darstellt.

:::tip `getSelectedText()` vs. Ereignis-Payload verwenden
Während Sie `getSelectedText()` manuell innerhalb eines Ereignis-Handlers aufrufen können, ist es effizienter, die Auswahldaten zu verwenden, die in der Payload des Ereignisses bereitgestellt werden – wie in einem `SelectionChangeEvent` – um zusätzliche Nachschlagen zu vermeiden.
:::

## Pattern matching {#pattern-matching}

Sie können die Methode `setPattern()` verwenden, um eine Validierungsregel für das `TextField` mithilfe eines regulären Ausdrucks zu definieren. Das Festlegen eines Musters fügt eine Einschränkungsvalidierung hinzu, die erfordert, dass der Eingabewert dem angegebenen Muster entspricht.

Das Muster muss ein gültiger [JavaScript regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, so wie es vom Browser interpretiert wird. Das `u` (Unicode) Flag wird intern angewendet, um eine genaue Übereinstimmung von Unicode-Codepunkten sicherzustellen. Wickeln Sie das Muster nicht in Schrägstriche (`/`), da diese nicht erforderlich sind und als literale Zeichen behandelt werden.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // z.B. ABC12
```

Wenn kein Muster bereitgestellt wird oder die Syntax ungültig ist, wird die Validierungsregel ignoriert.

:::tip Kontextuelle Hilfe
Bei der Verwendung komplexer Muster für ein `TextField` sollten Sie eine Kombination aus den Methoden `setLabel()`, `setHelperText()` und `setTooltipText()` verwenden,
um zusätzliche Hinweise und Anleitungen zu geben.
:::

## Minimum and maximum length {#minimum-and-maximum-length}

Die `TextField`-Komponente unterstützt die Validierung von Einschränkungen basierend auf der Anzahl der vom Benutzer eingegebenen Zeichen. Dies kann mit den Methoden `setMinLength()` und `setMaxLength()` gesteuert werden. Verwenden Sie beide Methoden, um eine klare Grenze für akzeptable Eingabelängen festzulegen.

:::info Längenanforderungen
Standardmäßig zeigt das Feld eine Nachricht an, wenn der Eingabewert außerhalb des Bereichs liegt, die den Benutzer darüber informiert, ob er seine Eingabe verkürzen oder verlängern muss. Diese Nachricht kann mit der Methode `setInvalidMessage()` überschrieben werden.
:::

### `setMinLength()` {#setminlength}

Diese Methode legt die **mindestens erforderliche Anzahl von UTF-16-Codeeinheiten** fest, die eingegeben werden müssen, damit das Feld als gültig betrachtet wird. Der Wert muss eine ganze Zahl sein und darf die konfigurierte maximale Länge nicht überschreiten.

```java
textField.setMinLength(5); // Der Benutzer muss mindestens 5 Zeichen eingeben
```

Wenn die Eingabe weniger Zeichen als die erforderliche Mindestanzahl enthält, schlägt die Eingabe die Validierung der Einschränkung fehl. Diese Regel gilt nur, wenn der Benutzer den Wert des Feldes ändert.

### `setMaxLength()` {#setmaxlength}

Diese Methode legt die **maximale Anzahl von UTF-16-Codeeinheiten** fest, die im Textfeld zulässig sind. Der Wert muss `0` oder größer sein. Wenn er nicht festgelegt ist oder auf einen ungültigen Wert festgelegt ist, wird keine maximale Länge durchgesetzt.

```java
textField.setMaxLength(20); // Der Benutzer darf nicht mehr als 20 Zeichen eingeben
```

Das Feld schlägt die Validierung der Einschränkung fehl, wenn die Eingabelänge die Mindestlänge überschreitet. Wie bei der `setMinLength()`-Methode wird diese Validierung nur ausgelöst, wenn der Wert vom Benutzer geändert wird.

## Best practices {#best-practices}

Im Folgenden sind einige empfohlene bewährte Praktiken für die Verwendung des `TextField` aufgeführt.

- **Eindeutige Beschriftungen und Anweisungen bereitstellen**: Beschriften Sie das `TextField` klar, um anzugeben, welche Art von Informationen die Benutzer eingeben sollen. Stellen Sie zusätzliche Anleitungstexte oder Platzhalterwerte bereit, um die Benutzer zu führen und Erwartungen an die erforderliche Eingabe zu setzen.

- **Rechtschreibprüfung und Autovervollständigung**: Wenn zutreffend, ziehen Sie in Betracht, die Rechtschreibprüfung mit `setSpellCheck()` zu nutzen und/oder die Autovervollständigung in einem `TextField` zu verwenden. Diese Funktionen können den Benutzern helfen, Informationen schneller einzugeben und Fehler zu reduzieren, indem sie vorgeschlagene Werte basierend auf vorherigen Eingaben oder vordefinierten Optionen bereitstellen.

- **Barrierefreiheit**: Verwenden Sie die `TextField`-Komponente mit Blick auf die Barrierefreiheit und halten Sie sich an Barrierefreiheitsstandards wie ordnungsgemäße Kennzeichnung, Unterstützung für die Tastaturnavigation und Kompatibilität mit unterstützenden Technologien. Stellen Sie sicher, dass Benutzer mit Behinderungen effektiv mit dem `TextField` interagieren können.
