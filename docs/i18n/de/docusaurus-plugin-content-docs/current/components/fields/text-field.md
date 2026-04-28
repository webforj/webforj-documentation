---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 51fe8b136580a1fca9e5a918389f33bf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Die `TextField`-Komponente ermöglicht es Benutzern, Text in einer einzelnen Zeile einzugeben und zu bearbeiten. Sie können das Feld so konfigurieren, dass eine bestimmte virtuelle Tastatur angezeigt wird, z. B. eine numerische Tastatur, eine E-Mail-Eingabe, eine Telefonnummern- oder URL-Eingabe. Die Komponente bietet auch eine eingebaute Validierung, um Werte abzulehnen, die nicht dem angegebenen Typ entsprechen.

<!-- INTRO_END -->

## Verwendungen {#usages}

<ParentLink parent="Field" />

Die `TextField` eignet sich für eine Vielzahl von Szenarien, in denen Texteingaben oder -bearbeitungen erforderlich sind. Hier sind einige Beispiele, wann die `TextField` verwendet werden sollte:

1. **Formulareingaben**: Ein `TextField` wird häufig in Formularen verwendet, um Benutzereingaben wie Namen, Adressen oder Kommentare zu erfassen. Es ist am besten, ein `TextField` in einer App zu verwenden, wenn die Eingabe in der Regel kurz genug ist, um auf eine Zeile zu passen.

2. **Suchfunktionalität**: `TextField` kann genutzt werden, um ein Suchfeld bereitzustellen, das es Benutzern ermöglicht, Suchanfragen einzugeben.

3. **Textbearbeitung**: Ein `TextField` eignet sich ideal für Apps, die Textbearbeitung oder Inhaltserstellung erfordern, wie z. B. Dokumenteneditoren, Chat-Apps oder Notiz-Anwendungen.

## Typen {#types}

Sie können den Typ des `TextField` mit der Methode `setType()` festlegen. Ebenso können Sie den Typ mit der Methode `getType()` abrufen, die einen Enum-Wert zurückgibt.

- `Type.TEXT`: Dies ist der Standardtyp für ein `TextField` und entfernt automatisch Zeilenumbrüche aus dem Eingabewert.

- `Type.EMAIL`: Dieser Typ ist für die Eingabe von E-Mail-Adressen vorgesehen. Er validiert die Eingabe gemäß der standardmäßigen E-Mail-Syntax. Zusätzlich bietet er kompatiblen Browsern und Geräten mit einer dynamischen Tastatur eine Vereinfachung des Tippprozesses, indem häufig verwendete Tasten wie <kbd>.com</kbd> und <kbd>@</kbd> einbezogen werden.

  :::note
  Während diese Validierung das Format der E-Mail-Adresse bestätigt, garantiert sie nicht, dass die E-Mail existiert.
  :::

- `Type.TEL`: Dieser Typ wird für die Eingabe einer Telefonnummer verwendet. Das Feld zeigt auf einigen Geräten mit dynamischen Tastaturen eine Telefonwähltastatur an.

- `Type.URL`: Dieser Typ ist für die Eingabe von URLs vorgesehen. Er validiert, ob ein Benutzer eine URL eingibt, die ein Schema und einen Domainnamen umfasst, zum Beispiel: https://webforj.com. Zusätzlich bietet er kompatiblen Browsern und Geräten mit einer dynamischen Tastatur eine Vereinfachung des Tippprozesses, indem häufig verwendete Tasten wie <kbd>:</kbd>, <kbd>/</kbd> und <kbd>.com</kbd> einbezogen werden.

- `Type.SEARCH`: Ein einzeiliges Textfeld zur Eingabe von Suchbegriffen. Zeilenumbrüche werden automatisch aus dem Eingabewert entfernt.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
height='250px'
/>

## Feldwert {#field-value}

Der Wert eines `TextField` repräsentiert die aktuelle Benutzereingabe als String. In webforJ kann dies mit der Methode `getValue()` abgerufen oder programmgesteuert mit `setValue(String)` aktualisiert werden.

```java
//Setze den anfänglichen Inhalt
textField.setValue("Anfänglicher Inhalt");

//Rufe den aktuellen Wert ab
String text = textField.getValue();
```

Wenn die Methode `getValue()` an einem Feld ohne aktuellen Wert verwendet wird, gibt sie einen leeren String (`""`) zurück.

Dieses Verhalten ist konsistent mit der Art und Weise, wie das HTML-Element `<input type="text">` seinen Wert über JavaScript bereitstellt.

:::tip Kombinieren Sie die Wertbehandlung mit der Validierung
Wenden Sie Einschränkungen wie ein [Muster](#pattern-matching), [Mindestlängen](#setminlength) oder eine [maximale Länge](#setmaxlength) an, um zu definieren, wann ein Wert als gültig angesehen wird.
:::

## Platzhaltertext {#placeholder-text}

Sie können Platzhaltertext für das `TextField` mit der Methode `setPlaceholder()` festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist, und hilft, den Benutzer zur Eingabe geeigneter Informationen in das `TextField` zu bewegen.

## Ausgewählter Text {#selected-text}

Es ist möglich, mit der `TextField`-Klasse zu interagieren, um den vom Benutzer ausgewählten Text abzurufen und Informationen über die Benutzerauswahl zu erhalten. Sie können den ausgewählten Text im `TextField` mit der Methode `getSelectedText()` abrufen. Dieses Verhalten wird normalerweise in Verbindung mit einem Ereignis verwendet. 

```java
TextField textField = new TextField("Geben Sie etwas ein...");
Button getSelectionBtn = new Button("Ausgewählten Text abrufen");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Ausgewählter Text: " + selected);
});
```

Ebenso ist es möglich, den aktuellen Auswahlbereich des `TextField` mit der Methode `getSelectionRange()` abzurufen. Diese gibt ein `SelectionRange`-Objekt zurück, das die Start- und Endindizes des ausgewählten Textes darstellt.

:::tip Verwendung von `getSelectedText()` vs. Ereignisnutzlast
Während Sie `getSelectedText()` manuell innerhalb eines Ereignishandlers aufrufen können, ist es effizienter, die Auswahldaten zu verwenden, die in der Nutzlast des Ereignisses bereitgestellt werden – wie in einem `SelectionChangeEvent` – um zusätzliche Nachschlagvorgänge zu vermeiden.
:::

## Mustererkennung {#pattern-matching}

Sie können die Methode `setPattern()` verwenden, um eine Validierungsregel für das `TextField` mit einem regulären Ausdruck zu definieren. Das Festlegen eines Musters fügt eine Einschränkungsvalidierung hinzu, die erfordert, dass der Eingabewert dem angegebenen Muster entspricht.

Das Muster muss ein gültiger [JavaScript-Regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie es vom Browser interpretiert wird. Der `u` (Unicode) Flag wird intern angewendet, um eine genaue Übereinstimmung von Unicode-Codepunkten sicherzustellen. Wickeln Sie das Muster nicht in Schrägstriche (`/`), da diese nicht erforderlich sind und als literale Zeichen behandelt werden.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // z.B. ABC12
```

Wenn kein Muster bereitgestellt wird oder die Syntax ungültig ist, wird die Validierungsregel ignoriert.

:::tip Kontexthilfe
Bei der Verwendung komplexer Muster für ein `TextField` sollten Sie eine Kombination aus den Methoden `setLabel()`, `setHelperText()` und `setTooltipText()` in Betracht ziehen,
um zusätzliche Hinweise und Anleitungen bereitzustellen.
:::

## Mindest- und Höchstlängen {#minimum-and-maximum-length}

Die `TextField`-Komponente unterstützt die Einschränkungsvalidierung basierend auf der Anzahl der vom Benutzer eingegebenen Zeichen. Dies kann mit den Methoden `setMinLength()` und `setMaxLength()` gesteuert werden. Verwenden Sie beide Methoden, um eine klare Grenze für die akzeptablen Eingabelängen festzulegen.

:::info Längenanforderungen
Standardmäßig zeigt das Feld eine Nachricht an, wenn der Eingabewert außerhalb des Bereichs liegt, die den Benutzern mitteilt, ob sie ihre Eingabe verkürzen oder verlängern müssen. Diese Nachricht kann mit der Methode `setInvalidMessage()` überschrieben werden.
:::

### `setMinLength()` {#setminlength}

Diese Methode legt die **minimale Anzahl von UTF-16-Codeeinheiten** fest, die eingegeben werden müssen, damit das Feld als gültig angesehen wird. Der Wert muss eine ganze Zahl sein und darf die konfigurierte maximale Länge nicht überschreiten.

```java
textField.setMinLength(5); // Der Benutzer muss mindestens 5 Zeichen eingeben
```

Wenn die Eingabe weniger Zeichen als die erforderliche Mindestanzahl enthält, schlägt die Eingabe die Einschränkungsvalidierung fehl. Diese Regel gilt nur, wenn der Benutzer den Wert des Feldes ändert.

### `setMaxLength()` {#setmaxlength}

Diese Methode legt die **maximale Anzahl von UTF-16-Codeeinheiten** fest, die im Textfeld zulässig sind. Der Wert muss `0` oder größer sein. Wenn er nicht festgelegt ist oder auf einen ungültigen Wert gesetzt wird, wird keine maximale Grenze enforced.

```java
textField.setMaxLength(20); // Der Benutzer kann nicht mehr als 20 Zeichen eingeben
```

Das Feld schlägt die Einschränkungsvalidierung fehl, wenn die Eingabelänge die Mindestlänge überschreitet. Wie bei `setMinLength()` wird diese Validierung nur aktiviert, wenn der Wert vom Benutzer geändert wird.

## Best Practices {#best-practices}

Im folgenden Abschnitt finden Sie einige empfohlene Best Practices für die Verwendung des `TextField`.

- **Deutliche Beschriftungen und Anweisungen bereitstellen**: Beschriften Sie das `TextField` klar, um anzuzeigen, welche Art von Informationen Benutzer eingeben sollten. Stellen Sie zusätzliche erklärende Texte oder Platzhalterwerte bereit, um Benutzer zu leiten und Erwartungen an die erforderliche Eingabe zu setzen.

- **Rechtschreibprüfung und Autocomplete**: Wenn zutreffend, sollten Sie die Rechtschreibprüfung mit `setSpellCheck()` nutzen und/oder Autocomplete in einem `TextField` verwenden. Diese Funktionen können Benutzern helfen, Informationen schneller einzugeben und Fehler zu reduzieren, indem sie vorgeschlagene Werte basierend auf früheren Eingaben oder vordefinierten Optionen bereitstellen.

- **Barrierefreiheit**: Verwenden Sie die `TextField`-Komponente mit Blick auf die Barrierefreiheit und halten Sie sich an die Barrierefreiheitsstandards, wie z. B. ordnungsgemäße Beschriftung, Unterstützung der Tastaturnavigation und Kompatibilität mit Hilfstechnologien. Stellen Sie sicher, dass Benutzer mit Behinderungen effektiv mit dem `TextField` interagieren können.
