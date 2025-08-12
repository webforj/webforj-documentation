---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
sidebar_class_name: updated-content
_i18n_hash: 71ebfc077bb8042752cbfea71a971e47
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

<ParentLink parent="Field" />

Der `TextField`-Komponente ermöglicht es Benutzern, Text in einer einzelnen Zeile einzugeben und zu bearbeiten. Sie können das Feld so konfigurieren, dass eine bestimmte virtuelle Tastatur angezeigt wird, wie z.B. eine numerische Tastatur, E-Mail-Eingabe,Telefoneingabe oder URL-Eingabe. Die Komponente bietet auch eingebaute Validierungen, um Werte abzulehnen, die nicht dem angegebenen Typ entsprechen.

## Usages {#usages}

Der `TextField` eignet sich für eine Vielzahl von Szenarien, in denen Texteingabe oder -bearbeitung erforderlich ist. Hier sind einige Beispiele, wann der `TextField` verwendet werden sollte:

1. **Formulareingaben**: Ein `TextField` wird häufig in Formularen verwendet, um Benutzereingaben zu erfassen, wie Namen, Adressen oder Kommentare. Es ist am besten, einen `TextField` in einer App zu verwenden, wenn die Eingabe in der Regel kurz genug ist, um auf eine Zeile zu passen.

2. **Suchfunktionalität**: Der `TextField` kann verwendet werden, um ein Suchfeld bereitzustellen, das es den Benutzern ermöglicht, Suchanfragen einzugeben.

3. **Textbearbeitung**: Ein `TextField` ist ideal für Apps, die Textbearbeitung oder Inhaltserstellung erfordern, wie Dokumenteneditoren, Chat-Apps oder Notiz-Apps.

## Types {#types}

Sie können den Typ des TextField mit der Methode `setType()` festlegen. Ebenso können Sie den Typ mit der Methode `getType()` abrufen, die einen Enumerationswert zurückgibt.

- `Type.TEXT`: Dies ist der Standardtyp für einen `TextField` und entfernt automatisch Zeilenumbrüche aus dem Eingabewert.

- `Type.EMAIL`: Dieser Typ ist für die Eingabe von E-Mail-Adressen vorgesehen. Er validiert die Eingabe gemäß der standardmäßigen E-Mail-Syntax. Darüber hinaus bietet er kompatiblen Browsern und Geräten mit einer dynamischen Tastatur eine vereinfachte Eingabe, indem häufig verwendete Tasten wie <kbd>.com</kbd> und <kbd>@</kbd> enthalten sind.

  :::note
  Während diese Validierung das Format der E-Mail-Adresse bestätigt, garantiert sie nicht, dass die E-Mail existiert.
  :::

- `Type.TEL`: Dieser Typ wird für die Eingabe einer Telefonnummer verwendet. Das Feld zeigt auf einigen Geräten mit dynamischen Tastaturen eine Telefon-Tastatur an.

- `Type.URL`: Dieser Typ ist für die Eingabe von URLs vorgesehen. Er validiert, ob ein Benutzer eine URL eingibt, die ein Schema und einen Domänennamen enthält, z.B.: https://webforj.com. Darüber hinaus bietet er kompatiblen Browsern und Geräten mit einer dynamischen Tastatur eine vereinfachte Eingabe, indem häufig verwendete Tasten wie <kbd>:</kbd>, <kbd>/</kbd> und <kbd>.com</kbd> enthalten sind.

- `Type.SEARCH`: Ein einzeiliges Textfeld für die Eingabe von Suchbegriffen. Zeilenumbrüche werden automatisch aus dem Eingabewert entfernt.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Field value {#field-value}

Der Wert eines `TextField` repräsentiert die aktuelle Benutzereingabe als Zeichenfolge. In webforJ kann dies mit der Methode `getValue()` abgerufen oder programmgesteuert mit `setValue(String)` aktualisiert werden.

```java
//Setze den initialen Inhalt
textField.setValue("Initialer Inhalt");

//Rufe den aktuellen Wert ab
String text = textField.getValue();
```

Wenn die Methode `getValue()` auf einem Feld ohne aktuellen Wert verwendet wird, gibt sie eine leere Zeichenfolge (`""`) zurück.

Dieses Verhalten ist konsistent mit der Art und Weise, wie das HTML `<input type="text">` Element seinen Wert über JavaScript bereitstellt.

:::tip Kombinieren Sie die Wertverarbeitung mit Validierung
Wenden Sie Einschränkungen wie ein [Muster](#pattern-matching), [Mindestlänge](#setminlength) oder eine [maximale Länge](#setmaxlength) an, um zu definieren, wann ein Wert als gültig betrachtet wird. 
:::

## Placeholder text {#placeholder-text}

Sie können Platzhaltertext für das `TextField` mit der Methode `setPlaceholder()` festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist, und hilft dem Benutzer, die Eingabe des entsprechenden Inhalts in das `TextField` anzuregen.

## Selected text {#selected-text}

Es ist möglich, mit der `TextField`-Klasse zu interagieren, um den ausgewählten Text eines Benutzers abzurufen und Informationen über die Auswahl des Benutzers zu erhalten. Sie können den ausgewählten Text im `TextField` mit der Methode `getSelectedText()` abrufen. Dieses Verhalten wird häufig in Verbindung mit einem Ereignis verwendet. 

```java
TextField textField = new TextField("Geben Sie etwas ein...");
Button getSelectionBtn = new Button("Ausgewählten Text abrufen");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Ausgewählter Text: " + selected);
});
```

Ebenso ist es möglich, den aktuellen Auswahlbereich des `TextField` mit der Methode `getSelectionRange()` abzurufen. Dies gibt ein `SelectionRange`-Objekt zurück, das die Start- und Endindizes des ausgewählten Textes darstellt.

:::tip Verwendung von `getSelectedText()` vs. Ereignis-Payload
Während Sie `getSelectedText()` manuell innerhalb eines Ereignishandlers aufrufen können, ist es effizienter, die Selektionsdaten zu verwenden, die in der Payload des Ereignisses bereitgestellt werden, wie z.B. in einem `SelectionChangeEvent`, um zusätzliche Abfragen zu vermeiden.
:::

## Pattern matching {#pattern-matching}

Sie können die Methode `setPattern()` verwenden, um eine Validierungsregel für das `TextField` mit einem regulären Ausdruck zu definieren. Das Festlegen eines Musters fügt eine Einschränkungsvalidierung hinzu, die erfordert, dass der Eingabewert mit dem angegebenen Muster übereinstimmt.

Das Muster muss ein gültiger [JavaScript-regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie er vom Browser interpretiert wird. Das `u` (Unicode) Flag wird intern angewendet, um eine genaue Übereinstimmung von Unicode-Codepunkten sicherzustellen. Wickeln Sie das Muster nicht in Schrägstriche (`/`), da diese nicht erforderlich sind und als Literalzeichen behandelt werden.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // z.B. ABC12
```

Wenn kein Muster angegeben oder die Syntax ungültig ist, wird die Validierungsregel ignoriert.

:::tip Kontextuelle Hilfe
Bei der Verwendung komplexer Muster für ein `TextField` sollten Sie in Betracht ziehen, eine Kombination der Methoden `setLabel()`, `setHelperText()` und `setTooltipText()` zu verwenden, um zusätzliche Hinweise und Anleitungen bereitzustellen.
:::

## Minimum und maximum length {#minimum-and-maximum-length}

Die `TextField`-Komponente unterstützt die Einschränkungsvalidierung basierend auf der Anzahl der vom Benutzer eingegebenen Zeichen. Dies kann mit den Methoden `setMinLength()` und `setMaxLength()` gesteuert werden. Verwenden Sie beide Methoden, um eine klare Grenze akzeptabler Eingabelängen festzulegen.

:::info Längenanforderungen
Standardmäßig zeigt das Feld eine Nachricht an, wenn der Eingabewert außerhalb des zulässigen Bereichs liegt, um den Benutzer darauf hinzuweisen, ob er seine Eingabe verkürzen oder verlängern muss. Diese Nachricht kann mit der Methode `setInvalidMessage()` überschrieben werden.
:::

### `setMinLength()` {#setminlength}

Diese Methode legt die **minimale Anzahl von UTF-16-Codeeinheiten** fest, die eingegeben werden müssen, damit das Feld als gültig betrachtet wird. Der Wert muss eine ganze Zahl sein und darf die konfigurierte maximale Länge nicht überschreiten.

```java
textField.setMinLength(5); // Benutzer muss mindestens 5 Zeichen eingeben
```

Wenn die Eingabe weniger Zeichen enthält als die erforderliche Mindestanzahl, schlägt die Eingabe die Einschränkungsvalidierung fehl. Diese Regel gilt nur, wenn der Benutzer den Wert des Feldes ändert.

### `setMaxLength()` {#setmaxlength}

Diese Methode legt die **maximale Anzahl von UTF-16-Codeeinheiten** fest, die im Textfeld zulässig sind. Der Wert muss `0` oder größer sein. Wenn er nicht festgelegt oder auf einen ungültigen Wert gesetzt wird, wird keine maximale Länge erzwingt.

```java
textField.setMaxLength(20); // Benutzer kann nicht mehr als 20 Zeichen eingeben
```

Das Feld schlägt die Einschränkungsvalidierung fehl, wenn die Eingabelänge die Mindestlänge überschreitet. Wie bei `setMinLength()` wird diese Validierung nur ausgelöst, wenn der Wert vom Benutzer geändert wird.

## Best practices {#best-practices}

Der folgende Abschnitt skizziert einige empfohlene Best Practices für die Nutzung des `TextField`.

- **Klare Beschriftungen und Anweisungen bereitstellen**: Beschriften Sie das `TextField` klar, um anzuzeigen, welche Art von Informationen Benutzer eingeben sollten. Geben Sie zusätzliche Anweisungstexte oder Platzhalterwerte an, um Benutzer zu führen und Erwartungen für die erforderliche Eingabe zu setzen.

- **Rechtschreibprüfung und Autovervollständigung**: Wenn möglich, ziehen Sie in Betracht, die Rechtschreibprüfung mit `setSpellCheck()` zu nutzen und/oder die Autovervollständigung in einem `TextField` zu verwenden. Diese Funktionen können Benutzern helfen, Informationen schneller einzugeben und Fehler zu reduzieren, indem sie vorgeschlagene Werte basierend auf vorherigen Eingaben oder vordefinierten Optionen bereitstellen.

- **Barrierefreiheit**: Nutzen Sie die `TextField`-Komponente unter Berücksichtigung der Barrierefreiheit und halten Sie sich an Barrierefreiheitsstandards wie angemessene Beschriftung, Unterstützung der Tastaturnavigation und Kompatibilität mit Hilfstechnologien. Stellen Sie sicher, dass Benutzer mit Behinderungen effektiv mit dem `TextField` interagieren können.
