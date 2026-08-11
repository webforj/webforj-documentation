---
title: Input Dialog
sidebar_position: 25
description: >-
  Prompt users for text, numbers, dates, colors, or other typed values with the
  modal InputDialog and message-type styling.
_i18n_hash: b797a58a2e413b1be6d2cfd814d74efa
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Ein `InputDialog` ist ein modales Dialogfeld, das dazu dient, den Benutzer zur Eingabe von Informationen aufzufordern. Das Dialogfeld blockiert die Anwendung, bis der Benutzer die Eingabe bereitstellt oder das Dialogfeld schließt.

<!-- INTRO_END -->

## Verwendungen {#usages}

Das `InputDialog` fordert die Benutzer zur Eingabe auf, wie z.B. Text, Zahlen oder andere Daten. Da das Dialogfeld modal ist, wartet die Anwendung, bis der Benutzer reagiert, bevor sie fortfährt:

<ComponentDemo
path='/webforj/inputdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java']}
height='500px'
/>

## Typen {#types}

### Eingabetypen {#input-types}

Das `InputDialog` unterstützt verschiedene Arten von Eingabefeldern, die es Ihnen ermöglichen, die Eingabemethode an Ihre spezifischen Bedürfnisse anzupassen:

1. **TEXT**: Ein standardmäßiges Eingabefeld für eine einzelne Zeile.
2. **PASSWORD**: Ein Passwortfeld, das die Eingabe des Benutzers verbirgt.
3. **NUMBER**: Ein numerisches Eingabefeld.
4. **EMAIL**: Ein Eingabefeld für E-Mail-Adressen.
5. **URL**: Ein Eingabefeld für URLs.
6. **SEARCH**: Ein Suchtext-Eingabefeld.
7. **DATE**: Ein Eingabefeld zur Auswahl von Daten.
8. **TIME**: Ein Eingabefeld zur Auswahl von Zeiten.
9. **DATETIME_LOCAL**: Ein Eingabefeld zur Auswahl von lokalem Datum und Uhrzeit.
10. **COLOR**: Ein Eingabefeld zur Auswahl einer Farbe.

### Nachrichtentyp {#message-type}

Das `InputDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt das Dialogfeld ein Symbol neben der Nachricht an, und das Design des Dialogfelds wird gemäß den Regeln des webforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol mit dem Standarddesign an.
2. `ERROR`: Zeigt ein Fehler-Symbol neben der Nachricht mit dem Fehlerdesign an.
3. `QUESTION`: Zeigt ein Fragezeichen-Symbol neben der Nachricht an, verwendet das primäre Design.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem Warnungsdesign an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an, verwendet das Informationsdesign.

Im folgenden Beispiel wird der Benutzer aufgefordert, sein Passwort einzugeben, um auf die Anwendung zuzugreifen. Wenn die Anmeldung fehlschlägt, wird der Benutzer erneut aufgefordert.

<ComponentDemo
path='/webforj/inputdialogtype'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java']}
height='350px'
/>

## Ergebnis {#result}

Das `InputDialog` gibt die Eingabe des Benutzers als String zurück. Wenn der Benutzer das Dialogfeld schließt, ohne eine Eingabe bereitzustellen, wird das Ergebnis `null` sein.

:::important
Der resultierende String wird von der Methode `show()` oder der entsprechenden Methode des `OptionDialog` zurückgegeben, wie unten dargestellt.
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
  "Bitte geben Sie Ihr Alter ein:", "Alter Eingabe", "", InputDialog.InputType.NUMBER);

if (result != null) {
  OptionDialog.showMessageDialog("Sie haben eingegeben: " + result, "Eingabe erhalten");
} else {
  OptionDialog.showMessageDialog("Keine Eingabe erhalten", "Eingabe abgebrochen");
}
```

## Standardwert {#default-value}

Das `InputDialog` ermöglicht es Ihnen, einen Standardwert anzugeben, der im Eingabefeld angezeigt wird, wenn das Dialogfeld angezeigt wird. Dies kann den Benutzern einen Vorschlag oder einen zuvor eingegebenen Wert geben.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Bitte geben Sie Ihren Namen ein:", "Name Eingabe", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Timeout {#timeout}

Das `InputDialog` ermöglicht es Ihnen, eine Zeitüberschreitung zu setzen, nach der das Dialogfeld automatisch geschlossen wird. Diese Funktion ist nützlich für nicht-kritische Eingabeaufforderungen oder Aktionen, die keine sofortige Interaktion des Benutzers erfordern.

Sie können das Timeout für das Dialogfeld mit der Methode `setTimeout(int timeout)` konfigurieren. Die Timeout-Dauer ist in Sekunden angegeben. Wenn die angegebene Zeit vergeht, ohne dass der Benutzer eingreift, wird das Dialogfeld automatisch geschlossen.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Bitte geben Sie Ihren Namen ein:", "Name Eingabe", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "Sie haben eingegeben: " + result, "Eingabe erhalten", "OK", MessageDialog.MessageType.INFO);
```

## Best Practices {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, welche Informationen der Benutzer bereitstellen soll.
2. **Angemessene Eingabetypen**: Wählen Sie Eingabetypen, die zu den erforderlichen Daten passen, um genaue und relevante Benutzereingaben sicherzustellen.
3. **Logische Standardwerte**: Setzen Sie Standardwerte, die nützliche Vorschläge oder vorherige Eingaben bieten, um die Benutzereingabe zu optimieren.
5. **Vorsichtiger Umgang mit Timeouts**: Setzen Sie Timeouts für nicht kritische Eingabeaufforderungen, um sicherzustellen, dass Benutzer genügend Zeit haben, die erforderlichen Informationen bereitzustellen.
6. **Minimieren Sie die Übernutzung**: Verwenden Sie Eingabedialoge sparsam, um die Frustration der Benutzer zu vermeiden. Reservieren Sie sie für Aktionen, die spezifische Benutzereingaben erfordern.
