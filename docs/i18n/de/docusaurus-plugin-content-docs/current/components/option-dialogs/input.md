---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 1dbd6d7664b01a9c3282ff4f3df65ea8
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Ein `InputDialog` ist ein modales Dialogfeld, das dazu dient, den Benutzer zur Eingabe aufzufordern. Der Dialog blockiert die Ausführung der App, bis der Benutzer die Eingabe tätigt oder den Dialog schließt.

<!-- INTRO_END -->

## Verwendung {#usages}

Der `InputDialog` fordert Benutzer zur Eingabe auf, wie z.B. Text, Zahlen oder andere Daten. Da der Dialog modale ist, wartet die App, bis der Benutzer antwortet, bevor sie fortfährt:

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Typen {#types}

### Eingabetypen {#input-types}

Der `InputDialog` unterstützt verschiedene Arten von Eingabefeldern, die es Ihnen ermöglichen, die Eingabemethode an Ihre spezifischen Bedürfnisse anzupassen:

1. **TEXT**: Ein standardmäßiges einzeiliges Texteingabefeld.
2. **PASSWORT**: Ein Eingabefeld für Passwörter, das die Eingabe des Benutzers verbirgt.
3. **ZAHL**: Ein numerisches Eingabefeld.
4. **E-MAIL**: Ein Eingabefeld für E-Mail-Adressen.
5. **URL**: Ein Eingabefeld für URLs.
6. **SUCHEN**: Ein Texteingabefeld für Suchanfragen.
7. **DATUM**: Ein Eingabefeld zur Auswahl von Daten.
8. **UHRZEIT**: Ein Eingabefeld zur Auswahl von Uhrzeiten.
9. **DATETIME_LOCAL**: Ein Eingabefeld zur Auswahl von lokalem Datum und Uhrzeit.
10. **FARBE**: Ein Eingabefeld zur Auswahl einer Farbe.

### Nachrichtentyp {#message-type}

Der `InputDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt der Dialog ein Symbol neben der Nachricht an, und das Thema des Dialogs wird gemäß den Regeln des WebforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol an und verwendet das Standardthema.
2. `ERROR`: Zeigt ein Fehlersymbol neben der Nachricht mit dem Fehlerdesign an.
3. `QUESTION`: Zeigt ein Fragezeichen-Symbol neben der Nachricht an und verwendet das Hauptthema.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem Warn-Design an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an und verwendet das Info-Thema.

Im folgenden Beispiel wird der Benutzer aufgefordert, sein Passwort einzugeben, um auf die App zuzugreifen. Wenn die Anmeldung fehlschlägt, wird der Benutzer erneut aufgefordert.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Ergebnis {#result}

Der `InputDialog` gibt die Eingabe des Benutzers als Zeichenfolge zurück. Wenn der Benutzer den Dialog schließt, ohne eine Eingabe zu leisten, wird das Ergebnis `null` sein.

:::important
Die resultierende Zeichenfolge wird von der Methode `show()` zurückgegeben oder von der entsprechenden `OptionDialog`-Methode, wie unten gezeigt. 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
    "Bitte geben Sie Ihr Alter ein:", "Alter Eingabe", "", InputDialog.InputType.NUMBER);

if (result != null) {
    OptionDialog.showMessageDialog("Sie haben eingegeben: " + result, "Eingabe Erhalten");
} else {
    OptionDialog.showMessageDialog("Keine Eingabe erhalten", "Eingabe Abgebrochen");
}
```

## Standardwert {#default-value}

Der `InputDialog` ermöglicht es Ihnen, einen Standardwert anzugeben, der im Eingabefeld angezeigt wird, wenn der Dialog geöffnet wird. Dies kann den Benutzern einen Vorschlag oder einen zuvor eingegebenen Wert bieten.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Bitte geben Sie Ihren Namen ein:", "Name Eingabe", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Timeout {#timeout}

Der `InputDialog` ermöglicht es Ihnen, eine Timeout-Dauer festzulegen, nach der der Dialog automatisch geschlossen wird. Diese Funktion ist nützlich für nicht kritische Eingabeaufforderungen oder Aktionen, die nicht die sofortige Interaktion des Benutzers erfordern.

Sie können das Timeout für den Dialog mit der Methode `setTimeout(int timeout)` konfigurieren. Die Timeout-Dauer ist in Sekunden angegeben. Wenn die angegebene Zeit ohne Benutzerinteraktion verstreicht, schließt der Dialog automatisch.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Bitte geben Sie Ihren Namen ein:", "Name Eingabe", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "Sie haben eingegeben: " + result, "Eingabe Erhalten", "OK", MessageDialog.MessageType.INFO);
```

## Beste Praktiken {#best-practices}

1. **Klarheit und Prägnanz bei Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, welche Informationen vom Benutzer gefordert werden.
2. **Angemessene Eingabetypen**: Wählen Sie Eingabetypen, die den erforderlichen Daten entsprechen, um genaue und relevante Benutzereingaben zu gewährleisten.
3. **Logische Standardwerte**: Setzen Sie Standardwerte, die nützliche Vorschläge oder frühere Eingaben bieten, um die Benutzereingabe zu optimieren.
5. **Bedacht auf Timeout**: Setzen Sie Timeouts für nicht kritische Eingabeaufforderungen, um sicherzustellen, dass Benutzer ausreichend Zeit haben, die erforderlichen Informationen bereitzustellen.
6. **Übermäßige Nutzung minimieren**: Verwenden Sie Eingabedialoge sparsam, um Benutzerfrustration zu vermeiden. Reservieren Sie sie für Aktionen, die spezifische Benutzereingaben erfordern.
