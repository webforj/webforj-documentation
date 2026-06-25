---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 0e0bfbd737ce384055397a7ff18b6579
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Die `TextField`-Komponente ermöglicht es Benutzern, Text in einer einzelnen Zeile einzugeben und zu bearbeiten. Sie können das Feld so konfigurieren, dass eine bestimmte virtuelle Tastatur angezeigt wird, wie z. B. ein Ziffernblock, E-Mail-Eingabe, Telefonnummern-Eingabe oder URL-Eingabe. Die Komponente bietet auch eine integrierte Validierung, um Werte abzulehnen, die nicht dem festgelegten Typ entsprechen.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="Field" />

Die `TextField`-Komponente eignet sich für eine Vielzahl von Szenarien, in denen Texteingabe oder -bearbeitung erforderlich ist. Hier sind einige Beispiele, wann die `TextField`-Komponente verwendet werden sollte:

1. **Formulareingaben**: Eine `TextField`-Komponente wird häufig in Formularen verwendet, um Benutzereingaben wie Namen, Adressen oder Kommentare zu erfassen. Es ist am besten, eine `TextField`-Komponente in einer App zu verwenden, wenn die Eingabe im Allgemeinen kurz genug ist, um in einer Zeile zu passen.

2. **Suchfunktionalität**: `TextField` kann verwendet werden, um ein Suchfeld bereitzustellen, das es Benutzern ermöglicht, Suchanfragen einzugeben.

3. **Textbearbeitung**: Eine `TextField`-Komponente ist ideal für Apps, die eine Textbearbeitung oder Inhaltserstellung erfordern, wie Dokumenten-Editoren, Chat-Apps oder Notiz-Apps.

## Types {#types}

Sie können den Typ des TextField mit der Methode `setType()` festlegen. Ebenso können Sie den Typ mit der Methode `getType()` abrufen, die einen Enum-Wert zurückgibt.

- `Type.TEXT`: Dies ist der Standardtyp für ein `TextField` und entfernt automatisch Zeilenumbrüche aus dem Eingabewert.

- `Type.EMAIL`: Dieser Typ dient zur Eingabe von E-Mail-Adressen. Er validiert die Eingabe gemäß der standardmäßigen E-Mail-Syntax. Darüber hinaus bietet er kompatiblen Browsern und Geräten eine dynamische Tastatur, die den Eingabeprozess erleichtert, indem häufig verwendete Tasten wie <kbd>.com</kbd> und <kbd>@</kbd> enthalten sind.

  :::note
  Während diese Validierung das Format der E-Mail-Adresse bestätigt, garantiert sie nicht, dass die E-Mail-Adresse existiert.
  :::

- `Type.TEL`: Dieser Typ wird zur Eingabe einer Telefonnummer verwendet. Das Feld zeigt auf einigen Geräten mit dynamischen Tastaturen eine Telefon-Tastatur an.

- `Type.URL`: Dieser Typ dient zur Eingabe von URLs. Er validiert, ob ein Benutzer eine URL eingibt, die ein Schema und einen Domainnamen enthält, z. B.: https://webforj.com. Darüber hinaus bietet er kompatiblen Browsern und Geräten eine dynamische Tastatur, die den Eingabeprozess erleichtert, indem häufig verwendete Tasten wie <kbd>:</kbd>, <kbd>/</kbd> und <kbd>.com</kbd> enthalten sind.

- `Type.SEARCH`: Ein einzeiliges Texteingabefeld zur Eingabe von Suchstrings. Zeilenumbrüche werden automatisch aus dem Eingabewert entfernt.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Field value {#field-value}

Der Wert eines `TextField` repräsentiert die aktuelle Benutzereingabe als String. In webforJ kann dieser über die Methode `getValue()` abgerufen oder programmgesteuert mit `setValue(String)` aktualisiert werden.

```java
//Setzen Sie den anfänglichen Inhalt
textField.setValue("Anfänglicher Inhalt");

//Rufen Sie den aktuellen Wert ab
String text = textField.getValue();
```

Wenn die Methode `getValue()` auf einem Feld ohne aktuellen Wert verwendet wird, gibt sie einen leeren String (`""`) zurück.

Dieses Verhalten entspricht dem, wie das HTML-Element `<input type="text">` seinen Wert über JavaScript bereitstellt.

:::tip Kombinieren Sie die Wertbehandlung mit der Validierung
Wenden Sie Einschränkungen wie ein [Muster](#pattern-matching), [Mindestlänge](#setminlength) oder eine [maximale Länge](#setmaxlength) an, um zu definieren, wann ein Wert als gültig angesehen wird. 
:::

## Placeholder text {#placeholder-text}

Sie können einen Platzhaltertext für das `TextField` mit der Methode `setPlaceholder()` festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist und hilft, den Benutzer zur Eingabe geeigneter Informationen in das `TextField` zu animieren.

## Selected text {#selected-text}

Es ist möglich, mit der `TextField`-Klasse zu interagieren, um den ausgewählten Text des Benutzers abzurufen und Informationen über die Auswahl des Benutzers zu erhalten. Sie können den ausgewählten Text im `TextField` mit der Methode `getSelectedText()` abrufen. Dieses Verhalten wird häufig in Verbindung mit einem Ereignis verwendet.

```java
TextField textField = new TextField("Geben Sie etwas ein...");
Button getSelectionBtn = new Button("Ausgewählten Text abrufen");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Ausgewählter Text: " + selected);
});
```

Ebenso ist es möglich, den aktuellen Auswahlbereich des `TextField` mit der Methode `getSelectionRange()` abzurufen. Dies gibt ein `SelectionRange`-Objekt zurück, das die Start- und Endindizes des ausgewählten Texts darstellt.

:::tip Verwendung von `getSelectedText()` vs. Ereignis-Payload
Obwohl Sie `getSelectedText()` manuell innerhalb eines Ereignishandlers aufrufen können, ist es effizienter, die Auswahldaten zu verwenden, die im Payload des Ereignisses bereitgestellt werden—wie in einem `SelectionChangeEvent`—um zusätzliche Abfragen zu vermeiden.
:::

## Pattern matching {#pattern-matching}

Sie können die Methode `setPattern()` verwenden, um eine Validierungsregel für das `TextField` mit einem regulären Ausdruck festzulegen. Das Festlegen eines Musters fügt eine Einschränkungsvalidierung hinzu, die erfordert, dass der Eingabewert mit dem angegebenen Muster übereinstimmt.

Das Muster muss ein gültiger [JavaScript-Regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie er vom Browser interpretiert wird. Das `u` (Unicode)-Flag wird intern angewendet, um eine genaue Übereinstimmung der Unicode-Codepunkte sicherzustellen. Wickeln Sie das Muster nicht in Schrägstriche (`/`), da diese nicht erforderlich sind und als reguläre Zeichen behandelt werden.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // z.B. ABC12
```

Wenn kein Muster bereitgestellt wird oder die Syntax ungültig ist, wird die Validierungsregel ignoriert.

:::tip Kontextuelle Hilfe
Bei Verwendung komplexer Muster für ein `TextField` sollten Sie in Erwägung ziehen, eine Kombination aus den Methoden `setLabel()`, `setHelperText()` und `setTooltipText()` zu verwenden, um zusätzliche Hinweise und Anleitungen bereitzustellen.
:::

## Minimum and maximum length {#minimum-and-maximum-length}

Die `TextField`-Komponente unterstützt die Einschränkungsvalidierung basierend auf der Anzahl der vom Benutzer eingegebenen Zeichen. Dies kann mit den Methoden `setMinLength()` und `setMaxLength()` gesteuert werden. Verwenden Sie beide Methoden, um eine klare Grenze für akzeptable Eingabelängen zu definieren.

:::info Längenvoraussetzungen
Standardmäßig zeigt das Feld eine Nachricht an, wenn der Eingabewert außerhalb des zulässigen Bereichs liegt, um den Benutzer darauf hinzuweisen, ob er seine Eingabe verkürzen oder verlängern muss. Diese Nachricht kann mit der Methode `setInvalidMessage()` überschrieben werden.
:::

### `setMinLength()` {#setminlength}

Diese Methode legt die **minimale Anzahl von UTF-16-Codeeinheiten** fest, die eingegeben werden müssen, damit das Feld als gültig betrachtet wird. Der Wert muss eine ganze Zahl sein und darf die festgelegte maximale Länge nicht überschreiten.

```java
textField.setMinLength(5); // Der Benutzer muss mindestens 5 Zeichen eingeben
```

Wenn die Eingabe weniger Zeichen als die erforderliche Mindestanzahl enthält, schlägt die Eingabe die Einschränkungsvalidierung fehl. Diese Regel gilt nur, wenn der Benutzer den Wert des Feldes ändert.

### `setMaxLength()` {#setmaxlength}

Diese Methode legt die **maximale Anzahl von UTF-16-Codeeinheiten** fest, die im Texteingabefeld zulässig sind. Der Wert muss `0` oder größer sein. Wenn nicht festgelegt oder auf einen ungültigen Wert festgelegt, wird keine maximale Grenze durchgesetzt.

```java
textField.setMaxLength(20); // Der Benutzer kann nicht mehr als 20 Zeichen eingeben
```

Das Feld schlägt bei der Einschränkungsvalidierung fehl, wenn die Eingabelänge die Mindestlänge überschreitet. Wie bei `setMinLength()` wird diese Validierung nur ausgelöst, wenn der Wert vom Benutzer geändert wird.

## Best practices {#best-practices}

Im Folgenden sind einige empfohlene Best Practices für die Verwendung des `TextField` skizziert.

- **Klarheit durch Beschriftungen und Anweisungen**: Beschriften Sie das `TextField` klar, um den Benutzern anzuzeigen, welche Informationen sie eingeben sollen. Geben Sie zusätzliche Anweisungen oder Platzhalterwerte an, um den Benutzern Führung zu bieten und die Erwartungen an die erforderliche Eingabe festzulegen.

- **Rechtschreibprüfung und Auto-Vervollständigung**: Bei diesen Möglichkeiten sollten Sie in Betracht ziehen, die Rechtschreibprüfung mit `setSpellCheck()` und/oder die Auto-Vervollständigung in einem `TextField` zu nutzen. Diese Funktionen können Benutzern helfen, Informationen schneller einzugeben und Fehler zu reduzieren, indem sie vorgeschlagene Werte basierend auf vorherigen Eingaben oder vordefinierten Optionen anbieten.

- **Barrierefreiheit**: Nutzen Sie die `TextField`-Komponente unter Berücksichtigung der Barrierefreiheit, indem Sie die Standards für Barrierefreiheit einhalten, z. B. durch angemessene Beschriftungen, Unterstützung der Tastaturnavigation und Kompatibilität mit unterstützenden Technologien. Stellen Sie sicher, dass Benutzer mit Behinderungen effektiv mit dem `TextField` interagieren können.
