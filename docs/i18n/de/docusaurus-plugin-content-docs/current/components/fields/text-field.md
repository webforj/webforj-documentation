---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 2f01abb14f66d83dd6edd3cc18ac560e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Mit der Komponente `TextField` können Benutzer Text in einer einzelnen Zeile eingeben und bearbeiten. Sie können das Feld so konfigurieren, dass eine bestimmte virtuelle Tastatur angezeigt wird, etwa ein numerisches Tastenfeld oder eine Tastatur für E-Mail-Adressen, Telefonnummern oder URLs. Die Komponente bietet außerdem eine integrierte Validierung, die Werte zurückweist, die nicht dem angegebenen Typ entsprechen.

<!-- INTRO_END -->

## Verwendung {#usages}

<ParentLink parent="Field" />

`TextField` eignet sich für viele Szenarien, in denen Texteingaben oder die Bearbeitung von Text erforderlich sind. Hier einige Beispiele für den Einsatz von `TextField`:

1. **Formulareingaben**: Ein `TextField` wird häufig in Formularen verwendet, um Benutzereingaben wie Namen, Adressen oder Kommentare zu erfassen. Verwenden Sie ein `TextField` in einer App am besten dann, wenn die Eingabe kurz genug ist, um in eine Zeile zu passen.

2. **Suchfunktion**: `TextField` kann als Suchfeld verwendet werden, in das Benutzer Suchanfragen eingeben können.

3. **Textbearbeitung**: Ein `TextField` eignet sich für Apps, in denen Text bearbeitet oder erstellt werden muss, etwa Dokumenteditoren, Chat- oder Notiz-Apps.

## Typen {#types}

Sie können den Typ des TextField mit der Methode `setType()` festlegen. Mit der Methode `getType()` können Sie den Typ abrufen; sie gibt einen Enum-Wert zurück.

- `Type.TEXT`: Dies ist der Standardtyp für ein `TextField`. Zeilenumbrüche werden automatisch aus dem Eingabewert entfernt.

- `Type.EMAIL`: Dieser Typ dient zur Eingabe von E-Mail-Adressen. Er validiert die Eingabe anhand der standardmäßigen E-Mail-Syntax. Außerdem stellt er kompatiblen Browsern und Geräten eine dynamische Tastatur bereit, die die Eingabe mit häufig verwendeten Tasten wie <kbd>.com</kbd> und <kbd>@</kbd> erleichtert.

  :::note
  Diese Validierung bestätigt zwar das Format der E-Mail-Adresse, garantiert aber nicht, dass die E-Mail-Adresse existiert.
  :::

- `Type.TEL`: Dieser Typ dient zur Eingabe einer Telefonnummer. Auf einigen Geräten mit dynamischen Tastaturen wird ein Tastenfeld für Telefonnummern angezeigt.

- `Type.URL`: Dieser Typ dient zur Eingabe von URLs. Er prüft, ob eine eingegebene URL ein Schema und einen Domainnamen enthält, zum Beispiel: https://webforj.com. Außerdem stellt er kompatiblen Browsern und Geräten eine dynamische Tastatur bereit, die die Eingabe mit häufig verwendeten Tasten wie <kbd>:</kbd>, <kbd>/</kbd> und <kbd>.com</kbd> erleichtert.

- `Type.SEARCH`: Ein einzeiliges Textfeld zur Eingabe von Suchbegriffen. Zeilenumbrüche werden automatisch aus dem Eingabewert entfernt.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Feldwert {#field-value}

Der Wert eines `TextField` entspricht der aktuellen Benutzereingabe als Zeichenfolge. In webforJ kann er mit der Methode `getValue()` abgerufen oder programmgesteuert mit `setValue(String)` aktualisiert werden.

```java
// Anfangsinhalt festlegen
textField.setValue("Initial content");

// Aktuellen Wert abrufen
String text = textField.getValue();
```

Wird die Methode `getValue()` für ein Feld ohne aktuellen Wert aufgerufen, gibt sie eine leere Zeichenfolge (`""`) zurück.

Dieses Verhalten entspricht dem HTML-Element `<input type="text">`, dessen Wert über JavaScript ausgelesen wird.

:::tip Wertverwaltung mit Validierung kombinieren
Legen Sie mit Einschränkungen wie einem [Muster](#pattern-matching), einer [Mindestlänge](#setminlength) oder einer [Höchstlänge](#setmaxlength) fest, wann ein Wert als gültig gilt.
:::

## Platzhaltertext {#placeholder-text}

Mit der Methode `setPlaceholder()` können Sie Platzhaltertext für das `TextField` festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist, und fordert den Benutzer so zur Eingabe eines passenden Werts auf.

## Markierter Text {#selected-text}

Sie können mit der Klasse `TextField` den vom Benutzer markierten Text abrufen und Informationen zur Markierung erhalten. Den markierten Text können Sie mit der Methode `getSelectedText()` aus dem `TextField` abrufen. Dieses Verhalten wird häufig in Verbindung mit einem Ereignis verwendet.

```java
TextField textField = new TextField("Geben Sie etwas ein...");
Button getSelectionBtn = new Button("Markierten Text abrufen");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Markierter Text: " + selected);
});
```

Ebenso können Sie mit der Methode `getSelectionRange()` den aktuellen Auswahlbereich des `TextField` abrufen. Sie gibt ein `SelectionRange`-Objekt zurück, das den Start- und Endindex des markierten Texts enthält.

:::tip Wertänderungen und Auswahl
Verwenden Sie `onValueChange()`, um Änderungen am Wert des Felds zu verarbeiten. Das zugehörige `ValueChangeEvent<String>` stellt den neuen Wert über `event.getValue()` bereit, enthält jedoch keine Auswahldaten. Um die aktuelle Auswahl auszulesen, rufen Sie `getSelectedText()` oder `getSelectionRange()` für das Feld auf.
:::

## Musterabgleich {#pattern-matching}

Mit der Methode `setPattern()` können Sie eine Validierungsregel für das `TextField` mithilfe eines regulären Ausdrucks festlegen. Ein festgelegtes Muster fügt eine Validierungsbedingung hinzu, nach der der Eingabewert dem angegebenen Muster entsprechen muss.

Das Muster muss ein gültiger [regulärer JavaScript-Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie er vom Browser interpretiert wird. Intern wird das Flag `u` (Unicode) angewendet, um einen präzisen Abgleich von Unicode-Codepoints sicherzustellen. Setzen Sie das Muster nicht in Schrägstriche (`/`), da diese nicht erforderlich sind und als literale Zeichen behandelt werden.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // z. B. ABC12
```

Wenn kein Muster angegeben ist oder die Syntax ungültig ist, wird die Validierungsregel ignoriert.

:::tip Kontextbezogene Hilfe
Wenn Sie komplexe Muster für ein `TextField` verwenden, können Sie zusätzliche Hinweise und Erläuterungen mit einer Kombination der Methoden `setLabel()`, `setHelperText()` und `setTooltipText()` bereitstellen.
:::

## Mindest- und Höchstlänge {#minimum-and-maximum-length}

Die Komponente `TextField` unterstützt eine Validierung anhand der Anzahl der vom Benutzer eingegebenen Zeichen. Diese lässt sich mit den Methoden `setMinLength()` und `setMaxLength()` steuern. Verwenden Sie beide Methoden, um einen klaren Bereich zulässiger Eingabelängen festzulegen.

:::info Längenbeschränkungen
Standardmäßig zeigt das Feld eine Meldung an, wenn der Eingabewert außerhalb des zulässigen Bereichs liegt. So erfährt der Benutzer, ob er die Eingabe kürzen oder verlängern muss. Diese Meldung kann mit der Methode `setInvalidMessage()` überschrieben werden.
:::

### `setMinLength()` {#setminlength}

Mit dieser Methode legen Sie die **Mindestanzahl an UTF-16-Codeeinheiten** fest, die eingegeben werden müssen, damit das Feld als gültig gilt. Der Wert muss eine ganze Zahl sein und sollte die konfigurierte Höchstlänge nicht überschreiten.

```java
textField.setMinLength(5); // Der Benutzer muss mindestens 5 Zeichen eingeben
```

Enthält die Eingabe weniger Zeichen als die erforderliche Mindestanzahl, schlägt die Validierung fehl. Diese Regel wird nur angewendet, wenn der Benutzer den Wert des Felds ändert.

### `setMaxLength()` {#setmaxlength}

Mit dieser Methode legen Sie die **Höchstanzahl an UTF-16-Codeeinheiten** fest, die im Textfeld zulässig sind. Der Wert muss `0` oder größer sein. Wenn keine Höchstlänge festgelegt ist oder der angegebene Wert ungültig ist, wird keine Höchstlänge erzwungen.

```java
textField.setMaxLength(20); // Der Benutzer kann nicht mehr als 20 Zeichen eingeben
```

Die Validierung schlägt fehl, wenn die Eingabelänge die Höchstlänge überschreitet. Wie bei `setMinLength()` wird die Validierung nur ausgelöst, wenn der Benutzer den Wert ändert.

## Bewährte Vorgehensweisen {#best-practices}

Im folgenden Abschnitt finden Sie einige Empfehlungen für den Einsatz von `TextField`.

- **Klare Beschriftungen und Anweisungen bereitstellen**: Beschriften Sie das `TextField` eindeutig, damit klar ist, welche Art von Informationen Benutzer eingeben sollen. Geben Sie zusätzliche Anweisungen oder Platzhalterwerte an, um Benutzer anzuleiten und ihre Erwartungen an die erforderliche Eingabe zu verdeutlichen.

- **Rechtschreibprüfung und Autovervollständigung**: Ziehen Sie bei Bedarf eine Rechtschreibprüfung mit `setSpellCheck()` und/oder die Autovervollständigung in einem `TextField` in Betracht. Diese Funktionen helfen Benutzern, Informationen schneller einzugeben und Fehler zu vermeiden, indem sie auf Grundlage früherer Eingaben oder vordefinierter Optionen Vorschläge machen.

- **Barrierefreiheit**: Sorgen Sie für klare Beschriftungen, Tastaturnavigation und Unterstützung für assistive Technologien, damit auch Menschen mit Behinderungen das Feld nutzen können.
