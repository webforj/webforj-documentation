---
sidebar_position: 25
title: Input Dialog
_i18n_hash: ba46203db1b4c35878c6509a514a70e5
---
# Eingabedialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Ein `InputDialog` ist ein modaler Dialog, der dazu entworfen wurde, den Benutzer zur Eingabe aufzufordern. Der Dialog blockiert die Ausführung der Anwendung, bis der Benutzer die Eingabe tätigt oder den Dialog schließt.

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Verwendungen {#usages}

Der `InputDialog` bietet eine Möglichkeit, Eingaben von Benutzern anzufordern, z. B. Text, Zahlen oder andere Daten, und stellt sicher, dass sie die erforderlichen Informationen bereitstellen, bevor sie fortfahren.

## Typen {#types}

### Eingabetypen {#input-types}

Der `InputDialog` unterstützt verschiedene Arten von Eingabefeldern, sodass Sie die Eingabemethode an Ihre spezifischen Bedürfnisse anpassen können:

1. **TEXT**: Eine Standard-Eingabezeile für Text.
2. **PASSWORD**: Ein Eingabefeld für Passwörter, das die Eingabe des Benutzers verbirgt.
3. **NUMBER**: Ein numerisches Eingabefeld.
4. **EMAIL**: Ein Eingabefeld für E-Mail-Adressen.
5. **URL**: Ein Eingabefeld für URLs.
6. **SEARCH**: Ein Eingabefeld für die Suche.
7. **DATE**: Ein Eingabefeld zur Auswahl von Daten.
8. **TIME**: Ein Eingabefeld zur Auswahl von Zeiten.
9. **DATETIME_LOCAL**: Ein Eingabefeld zur Auswahl von lokalen Datum und Uhrzeit.
10. **COLOR**: Ein Eingabefeld zur Farbauswahl.

### Nachrichtentyp {#message-type}

Der `InputDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt der Dialog ein Symbol neben der Nachricht an, und das Design des Dialogs wird entsprechend den Regeln des webforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol im Standard-Design an.
2. `ERROR`: Zeigt ein Fehler-Symbol neben der Nachricht mit dem Fehler-Design an.
3. `QUESTION`: Zeigt ein Fragezeichen-Symbol neben der Nachricht im primären Design an.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem Warn-Design an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht im Informations-Design an.

Im folgenden Beispiel wird der Benutzer aufgefordert, sein Passwort einzugeben, um auf die App zuzugreifen. Wenn die Anmeldung fehlschlägt, wird der Benutzer erneut aufgefordert.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Ergebnis {#result}

Der `InputDialog` gibt die Eingabe des Benutzers als Zeichenfolge zurück. Wenn der Benutzer den Dialog schließt, ohne eine Eingabe zu tätigen, wird das Ergebnis `null` sein.

:::important
Die resultierende Zeichenfolge wird aus der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode zurückgegeben, wie unten gezeigt. 
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

Der `InputDialog` ermöglicht es Ihnen, einen Standardwert festzulegen, der im Eingabefeld angezeigt wird, wenn der Dialog angezeigt wird. Dies kann den Benutzern einen Vorschlag oder einen zuvor eingegebenen Wert bieten.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Bitte geben Sie Ihren Namen ein:", "Name Eingabe", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Timeout {#timeout}

Der `InputDialog` ermöglicht es Ihnen, eine Timeout-Dauer festzulegen, nach der der Dialog automatisch geschlossen wird. Diese Funktion ist nützlich für nicht kritische Eingabeaufforderungen oder Aktionen, die keine sofortige Interaktion des Benutzers erfordern.

Sie können das Timeout für den Dialog mit der Methode `setTimeout(int timeout)` konfigurieren. Die Timeout-Dauer wird in Sekunden angegeben. Wenn die angegebene Zeit ohne Benutzerinteraktion elapsed, schließt der Dialog automatisch.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Bitte geben Sie Ihren Namen ein:", "Name Eingabe", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "Sie haben eingegeben: " + result, "Eingabe erhalten", "OK", MessageDialog.MessageType.INFO);
```

## Best Practices {#best-practices}

1. **Eindeutige und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderung klar erklärt, welche Informationen der Benutzer bereitstellen soll.
2. **Angemessene Eingabetypen**: Wählen Sie Eingabetypen, die zu den erforderlichen Daten passen, um genaue und relevante Benutzereingaben zu gewährleisten.
3. **Logische Standardwerte**: Legen Sie Standardwerte fest, die nützliche Vorschläge oder vorherige Einträge bieten, um die Benutzereingabe zu optimieren.
4. **Sorgfältige Nutzung des Timeouts**: Setzen Sie Timeouts für nicht kritische Eingabeaufforderungen, um sicherzustellen, dass die Benutzer genügend Zeit haben, um die erforderlichen Informationen bereitzustellen.
5. **Minimierung der Übernutzung**: Verwenden Sie Eingabedialoge sparsam, um Frustrationen bei den Benutzern zu vermeiden. Reservieren Sie sie für Aktionen, die spezifische Benutzereingaben erfordern.
