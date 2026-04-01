---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 3c045d4085b917bd2f338916cc61d276
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Ein `InputDialog` ist ein modales Dialogfeld, das dazu dient, den Benutzer zur Eingabe aufzufordern. Das Dialogfeld blockiert die Ausführung der App, bis der Benutzer die Eingabe bereitstellt oder das Dialogfeld schließt.

<!-- INTRO_END -->

## Verwendungen {#usages}

Der `InputDialog` fordert Benutzer zur Eingabe auf, wie z. B. Text, Zahlen oder andere Daten. Da das Dialogfeld modal ist, wartet die App darauf, dass der Benutzer reagiert, bevor sie fortsetzt:

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Typen {#types}

### Eingabetypen {#input-types}

Der `InputDialog` unterstützt verschiedene Eingabefelder, sodass Sie die Eingabemethode an Ihre speziellen Bedürfnisse anpassen können:

1. **TEXT**: Eine standardmäßige einzeilige Texteingabe.
2. **PASSWORD**: Ein Passwortfeld, das die Eingabe des Benutzers geheim hält.
3. **NUMBER**: Ein numerisches Eingabefeld.
4. **EMAIL**: Ein Eingabefeld für E-Mail-Adressen.
5. **URL**: Ein Eingabefeld für URLs.
6. **SEARCH**: Ein Texteingabefeld für Suchanfragen.
7. **DATE**: Ein Eingabefeld zur Auswahl von Daten.
8. **TIME**: Ein Eingabefeld zur Auswahl von Zeiten.
9. **DATETIME_LOCAL**: Ein Eingabefeld zur Auswahl von lokalen Datum und Uhrzeit.
10. **COLOR**: Ein Eingabefeld zur Auswahl einer Farbe.

### Nachrichtentyp {#message-type}

Der `InputDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt das Dialogfeld ein Symbol neben der Nachricht an, und das Thema des Dialogfelds wird gemäß den Regeln des webforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol an und verwendet das Standardthema.
2. `ERROR`: Zeigt ein Fehlersymbol neben der Nachricht an, mit dem dazugehörigen Fehlerdesign.
3. `QUESTION`: Zeigt ein Fragezeichensymbol neben der Nachricht an und verwendet das primäre Thema.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht an, mit dem dazugehörigen Warnungsdesign.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an und verwendet das Informationsdesign.

Im folgenden Beispiel wird der Benutzer aufgefordert, sein Passwort einzugeben, um auf die App zuzugreifen. Wenn die Anmeldung fehlschlägt, wird der Benutzer erneut aufgefordert.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Ergebnis {#result}

Der `InputDialog` gibt die Eingabe des Benutzers als Zeichenfolge zurück. Wenn der Benutzer das Dialogfeld schließt, ohne eine Eingabe zu machen, wird das Ergebnis `null` sein.

:::important
Die resultierende Zeichenfolge wird von der Methode `show()` oder der äquivalenten `OptionDialog`-Methode wie unten gezeigt zurückgegeben.
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
  "Bitte geben Sie Ihr Alter ein:", "Alterseingabe", "", InputDialog.InputType.NUMBER);

if (result != null) {
  OptionDialog.showMessageDialog("Sie haben eingegeben: " + result, "Eingabe erhalten");
} else {
  OptionDialog.showMessageDialog("Keine Eingabe erhalten", "Eingabe abgebrochen");
}
```

## Standardwert {#default-value}

Der `InputDialog` ermöglicht es Ihnen, einen Standardwert anzugeben, der im Eingabefeld angezeigt wird, wenn das Dialogfeld geöffnet wird. Dies kann den Benutzern einen Vorschlag oder einen zuvor eingegebenen Wert bieten.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Bitte geben Sie Ihren Namen ein:", "Namenseingabe", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Timeout {#timeout}

Der `InputDialog` ermöglicht es Ihnen, eine Timeout-Dauer festzulegen, nach der das Dialogfeld automatisch geschlossen wird. Diese Funktion ist nützlich für nicht kritische Eingabeaufforderungen oder Aktionen, die keine sofortige Interaktion des Benutzers erfordern.

Sie können das Timeout für das Dialogfeld mit der Methode `setTimeout(int timeout)` konfigurieren. Die Timeout-Dauer wird in Sekunden angegeben. Wenn die angegebene Zeit ohne Benutzerinteraktion verstreicht, schließt sich das Dialogfeld automatisch.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Bitte geben Sie Ihren Namen ein:", "Namenseingabe", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "Sie haben eingegeben: " + result, "Eingabe erhalten", "OK", MessageDialog.MessageType.INFO);
```

## Best Practices {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, welche Informationen der Benutzer angeben soll.
2. **Angemessene Eingabetypen**: Wählen Sie Eingabetypen, die zu den erforderlichen Daten passen, um genaue und relevante Benutzereingaben zu gewährleisten.
3. **Logische Standardwerte**: Setzen Sie Standardwerte, die nützliche Vorschläge oder vorherige Eingaben bieten, um die Benutzereingabe zu optimieren.
4. **Vorsichtiger Einsatz von Timeouts**: Setzen Sie Timeouts für nicht kritische Eingabeaufforderungen, um sicherzustellen, dass den Benutzern genügend Zeit bleibt, um die erforderlichen Informationen bereitzustellen.
5. **Übermäßigen Gebrauch minimieren**: Verwenden Sie Eingabedialoge sparsam, um die Frustration der Benutzer zu vermeiden. Reservieren Sie sie für Aktionen, die spezifische Benutzereingaben erfordern.
