---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 18d2a614ed2e9c53513a92017b3622e0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Die `TextField`-Komponente ermöglicht es den Benutzern, Text in einer einzelnen Zeile einzugeben und zu bearbeiten. Sie können das Feld so konfigurieren, dass eine bestimmte virtuelle Tastatur angezeigt wird, wie z. B. ein Ziffernfeld, eine E-Mail-Eingabe, eine Telefon-Eingabe oder eine URL-Eingabe. Die Komponente bietet auch eine integrierte Validierung, um Werte abzulehnen, die nicht dem angegebenen Typ entsprechen.

<!-- INTRO_END -->

## Verwendungen {#usages}

<ParentLink parent="Field" />

Das `TextField` eignet sich für eine Vielzahl von Szenarien, in denen Texteingabe oder -bearbeitung erforderlich ist. Hier sind einige Beispiele, wann das `TextField` verwendet werden sollte:

1. **Formulareingaben**: Ein `TextField` wird häufig in Formularen zur Erfassung von Benutzereingaben wie Namen, Adressen oder Kommentaren verwendet. Es ist am besten, ein `TextField` in einer App zu verwenden, wenn die Eingabe in der Regel kurz genug ist, um auf eine Zeile zu passen.

2. **Suchfunktionalität**: `TextField` kann verwendet werden, um ein Suchfeld bereitzustellen, das es den Benutzern ermöglicht, Suchanfragen einzugeben.

3. **Texteingabe**: Ein `TextField` ist ideal für Apps, die Textbearbeitung oder Inhaltserstellung erfordern, wie z. B. Dokumenteneditoren, Chat-Apps oder Notiz-Apps.

## Typen {#types}

Sie können den Typ des TextField mit der Methode `setType()` spezifizieren. Ebenso können Sie den Typ mit der Methode `getType()` abrufen, die einen Enum-Wert zurückgibt.

- `Type.TEXT`: Dies ist der Standardtyp für ein `TextField` und entfernt automatisch Zeilenumbrüche aus dem Eingabewert.

- `Type.EMAIL`: Dieser Typ dient der Eingabe von E-Mail-Adressen. Er validiert die Eingabe gemäß der standardmäßigen E-Mail-Syntax. Darüber hinaus bietet er kompatiblen Browsern und Geräten eine dynamische Tastatur, die den Tippvorgang vereinfacht, indem häufig verwendete Tasten wie <kbd>.com</kbd> und <kbd>@</kbd> eingefügt werden.

  :::note
  Während diese Validierung das Format der E-Mail-Adresse bestätigt, garantiert sie nicht, dass die E-Mail existiert.
  :::

- `Type.TEL`: Dieser Typ wird zur Eingabe einer Telefonnummer verwendet. Das Feld zeigt auf einigen Geräten mit dynamischen Tastaturen eine Telefon-Tastatur an.

- `Type.URL`: Dieser Typ dient der Eingabe von URLs. Er validiert, ob der Benutzer eine URL eingibt, die ein Schema und einen Domänennamen enthält, beispielsweise: https://webforj.com. Darüber hinaus bietet er kompatiblen Browsern und Geräten eine dynamische Tastatur, die den Tippvorgang vereinfacht, indem häufig verwendete Tasten wie <kbd>:</kbd>, <kbd>/</kbd> und <kbd>.com</kbd> eingefügt werden.

- `Type.SEARCH`: Einzeiliges Textfeld zum Eingeben von Suchzeichenfolgen. Zeilenumbrüche werden automatisch aus dem Eingabewert entfernt.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Feldwert {#field-value}

Der Wert eines `TextField` stellt die aktuelle Benutzereingabe als Zeichenfolge dar. In webforJ kann dies mit der Methode `getValue()` abgerufen oder programmgesteuert mit `setValue(String)` aktualisiert werden.

```java
//Setzen Sie den anfänglichen Inhalt
textField.setValue("Anfänglicher Inhalt");

//Aktualisieren Sie den aktuellen Wert
String text = textField.getValue();
```

Wenn die Methode `getValue()` bei einem Feld ohne aktuellen Wert verwendet wird, gibt sie eine leere Zeichenfolge (`""`) zurück.

Dieses Verhalten ist konsistent mit der Art und Weise, wie das HTML-Element `<input type="text">` seinen Wert über JavaScript bereitstellt.

:::tip Kombinieren Sie die Wertbehandlung mit der Validierung
Wenden Sie Einschränkungen wie ein [Muster](#pattern-matching), [Mindestlänge](#setminlength) oder eine [Maximallänge](#setmaxlength) an, um zu definieren, wann ein Wert als gültig angesehen wird.
:::

## Platzhaltertext {#placeholder-text}

Sie können Platzhaltertext für das `TextField` mit der Methode `setPlaceholder()` festlegen. Der Platzhaltertext wird angezeigt, wenn das Feld leer ist, und hilft, den Benutzer zur Eingabe geeigneter Informationen in das `TextField` aufzufordern.

## Ausgewählter Text {#selected-text}

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

:::tip Verwendung von `getSelectedText()` vs. Ereignislast
Obwohl Sie `getSelectedText()` manuell innerhalb eines Ereignishandlers aufrufen können, ist es effizienter, die Auswahldaten zu verwenden, die in der Nutzlast des Ereignisses bereitgestellt werden – wie in einem `SelectionChangeEvent` –, um zusätzliche Abfragen zu vermeiden.
:::

## Musterabgleich {#pattern-matching}

Sie können die Methode `setPattern()` verwenden, um eine Validierungsregel für das `TextField` mithilfe eines regulären Ausdrucks zu definieren. Das Festlegen eines Musters fügt eine Einschränkungsvalidierung hinzu, die erfordert, dass der Eingabewert dem angegebenen Muster entspricht.

Das Muster muss ein gültiger [JavaScript-Regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie er vom Browser interpretiert wird. Das `u` (Unicode)-Flag wird intern angewendet, um eine genaue Übereinstimmung von Unicode-Codepunkten sicherzustellen. Wickeln Sie das Muster nicht in Schrägstriche (`/`), da diese nicht erforderlich sind und als Literalzeichen behandelt werden.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // z. B. ABC12
```

Wenn kein Muster bereitgestellt wird oder die Syntax ungültig ist, wird die Validierungsregel ignoriert.

:::tip Kontexthilfe
Beim Einsatz komplexer Muster für ein `TextField` sollten Sie in Betracht ziehen, eine Kombination der Methoden `setLabel()`, `setHelperText()` und `setTooltipText()` zu verwenden, um zusätzliche Hinweise und Anleitungen zu geben.
:::

## Mindest- und Maximallänge {#minimum-and-maximum-length}

Die `TextField`-Komponente unterstützt die Einschränkungsvalidierung basierend auf der Anzahl der vom Benutzer eingegebenen Zeichen. Dies kann mit den Methoden `setMinLength()` und `setMaxLength()` gesteuert werden. Verwenden Sie beide Methoden, um eine klare Grenze der akzeptablen Eingabelängen festzulegen.

:::info Längenanforderungen
Standardmäßig zeigt das Feld eine Nachricht an, wenn der Eingabewert außerhalb des zulässigen Bereichs liegt, um den Benutzer darauf hinzuweisen, ob er seine Eingabe verkürzen oder verlängern muss. Diese Nachricht kann mit der Methode `setInvalidMessage()` überschrieben werden.
:::

### `setMinLength()` {#setminlength}

Diese Methode legt die **mindestens erforderliche Anzahl von UTF-16-Codeeinheiten** fest, die eingegeben werden müssen, damit das Feld als gültig betrachtet wird. Der Wert muss eine ganze Zahl sein und darf die konfigurierte Maximallänge nicht überschreiten.

```java
textField.setMinLength(5); // Der Benutzer muss mindestens 5 Zeichen eingeben
```

Wenn die Eingabe weniger Zeichen als die Mindestanforderung enthält, schlägt die Eingabe die Einschränkungsvalidierung fehl. Diese Regel gilt nur, wenn der Benutzer den Wert des Feldes ändert.

### `setMaxLength()` {#setmaxlength}

Diese Methode legt die **maximale Anzahl von UTF-16-Codeeinheiten** fest, die im Textfeld zulässig sind. Der Wert muss `0` oder größer sein. Wenn er nicht festgelegt ist oder auf einen ungültigen Wert festgelegt wird, wird keine Maximalgrenze durchgesetzt.

```java
textField.setMaxLength(20); // Der Benutzer kann nicht mehr als 20 Zeichen eingeben
```

Das Feld schlägt die Einschränkungsvalidierung fehl, wenn die Eingabelänge die minimale Länge überschreitet. Wie bei `setMinLength()` wird diese Validierung nur ausgelöst, wenn der Benutzer den Wert ändert.

## Best Practices {#best-practices}

Im folgenden Abschnitt werden einige empfohlene Best Practices für die Nutzung des `TextField` aufgeführt.

- **Bereitstellen klarer Beschriftungen und Anweisungen**: Beschriften Sie das `TextField` klar, um anzugeben, welche Art von Informationen die Benutzer eingeben sollen. Stellen Sie zusätzlich erläuternden Text oder Platzhalterwerte bereit, um die Benutzer zu leiten und Erwartungen an die erforderliche Eingabe zu setzen.

- **Rechtschreibprüfung und Auto-Vervollständigung**: Nutzen Sie, wenn möglich, die Rechtschreibprüfung mit `setSpellCheck()` und/oder verwenden Sie die Auto-Vervollständigung in einem `TextField`. Diese Funktionen können den Benutzern helfen, Informationen schneller einzugeben und Fehler zu reduzieren, indem sie vorgeschlagene Werte auf der Grundlage vorheriger Eingaben oder vordefinierter Optionen bereitstellen.

- **Barrierefreiheit**: Nutzen Sie die `TextField`-Komponente mit Fokus auf Barrierefreiheit und halten Sie sich an die Barrierefreiheitsstandards wie angemessene Beschriftung, Unterstützung für die Tastaturnavigation und Kompatibilität mit Hilfstechnologien. Stellen Sie sicher, dass Benutzer mit Behinderungen effektiv mit dem `TextField` interagieren können.
