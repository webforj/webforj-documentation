---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 96ced3bbe3c9ec87ebf19010833b62c5
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Ein `InputDialog` ist ein modales Dialogfeld, das dazu dient, den Benutzer nach Eingaben zu fragen. Der Dialog blockiert die Ausführung der App, bis der Benutzer die Eingabe vorlegt oder das Dialogfeld schließt.

<!-- INTRO_END -->

## Usages {#usages}

Der `InputDialog` fordert die Benutzer zur Eingabe von Informationen wie Text, Zahlen oder anderen Daten auf. Da der Dialog modal ist, wartet die App darauf, dass der Benutzer antwortet, bevor sie fortfährt:

<ComponentDemo
path='/webforj/inputdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java']}
height='500px'
/>

## Types {#types}

### Input types {#input-types}

Der `InputDialog` unterstützt verschiedene Arten von Eingabefeldern, mit denen Sie die Eingabemethode an Ihre spezifischen Bedürfnisse anpassen können:

1. **TEXT**: Ein standardmäßiges Eingabefeld für eine einzelne Zeile.
2. **PASSWORD**: Ein Eingabefeld für Passwörter, das die Eingabe des Benutzers verbirgt.
3. **NUMBER**: Ein Eingabefeld für numerische Werte.
4. **EMAIL**: Ein Eingabefeld für E-Mail-Adressen.
5. **URL**: Ein Eingabefeld für URLs.
6. **SEARCH**: Ein Eingabefeld für Suchtexte.
7. **DATE**: Ein Eingabefeld zum Auswählen von Daten.
8. **TIME**: Ein Eingabefeld zum Auswählen von Zeiten.
9. **DATETIME_LOCAL**: Ein Eingabefeld zum Auswählen von lokalem Datum und Uhrzeit.
10. **COLOR**: Ein Eingabefeld zum Auswählen einer Farbe.

### Message type {#message-type}

Der `InputDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt der Dialog ein Symbol neben der Nachricht an, und das Thema des Dialogs wird gemäß den Regeln des webforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol an und verwendet das Standardthema.
2. `ERROR`: Zeigt ein Fehler-Symbol neben der Nachricht mit dem angewendeten Fehler-Thema an.
3. `QUESTION`: Zeigt ein Fragezeichen-Symbol neben der Nachricht an und verwendet das primäre Thema.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem angewendeten Warn-Thema an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an und verwendet das Informations-Thema.

In dem folgenden Beispiel wird der Benutzer aufgefordert, sein Passwort einzugeben, um auf die App zuzugreifen. Wenn die Anmeldung fehlschlägt, wird der Benutzer erneut aufgefordert.

<ComponentDemo
path='/webforj/inputdialogtype'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java']}
height='350px'
/>

## Result {#result}

Der `InputDialog` gibt die Eingabe des Benutzers als String zurück. Wenn der Benutzer das Dialogfeld schließt, ohne eine Eingabe zu tätigen, wird das Ergebnis `null` sein.

:::important
Der resultierende String wird von der Methode `show()` oder der entsprechenden `OptionDialog`-Methode, wie unten gezeigt, zurückgegeben. 
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

## Default value {#default-value}

Der `InputDialog` ermöglicht es Ihnen, einen Standardwert anzugeben, der im Eingabefeld angezeigt wird, wenn der Dialog angezeigt wird. Dies kann den Benutzern einen Vorschlag oder einen zuvor eingegebenen Wert bieten.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Bitte geben Sie Ihren Namen ein:", "Name Eingabe", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Timeout {#timeout}

Der `InputDialog` ermöglicht es Ihnen, eine Zeitüberschreitung festzulegen, nach der der Dialog automatisch geschlossen wird. Diese Funktion ist nützlich für weniger kritische Eingabeanforderungen oder Aktionen, die nicht die sofortige Interaktion des Benutzers erfordern.

Sie können das Timeout für den Dialog mit der Methode `setTimeout(int timeout)` konfigurieren. Die Timeout-Dauer wird in Sekunden angegeben. Wenn die angegebene Zeit ohne Benutzerinteraktion verstreicht, schließt sich der Dialog automatisch.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Bitte geben Sie Ihren Namen ein:", "Name Eingabe", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "Sie haben eingegeben: " + result, "Eingabe Erhalten", "OK", MessageDialog.MessageType.INFO);
```

## Best practices {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, welche Informationen der Benutzer bereitstellen soll.
2. **Angemessene Eingabetypen**: Wählen Sie Eingabetyen, die den erforderlichen Daten entsprechen, um genaue und relevante Benutzer Eingaben sicherzustellen.
3. **Logische Standardwerte**: Setzen Sie Standardwerte, die nützliche Vorschläge oder frühere Einträge bieten, um die Benutzereingabe zu vereinfachen.
5. **Überlegter Einsatz von Timeouts**: Setzen Sie Timeouts für weniger kritische Eingabeanfragen, um sicherzustellen, dass die Benutzer genug Zeit haben, um die erforderlichen Informationen anzugeben.
6. **Minimieren Sie die Übernutzung**: Verwenden Sie Eingabedialoge sparsam, um Benutzerfrustration zu vermeiden. Reservieren Sie sie für Aktionen, die eine spezifische Benutzereingabe erfordern.
