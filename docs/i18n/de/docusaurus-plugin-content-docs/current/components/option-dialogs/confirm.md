---
title: Confirm
sidebar_position: 5
description: >-
  Show a blocking ConfirmDialog with up to three options, configurable button
  sets, message types, and timeout behavior.
_i18n_hash: 0d5432d42be98a19b1a6938b306b0442
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Ein `ConfirmDialog` ist ein modales Dialogfeld, das entwickelt wurde, um dem Benutzer die Wahl zwischen bis zu 3 Optionen zu ermöglichen. Der Dialog blockiert die Ausführung der App, bis der Benutzer mit ihm interagiert oder er aufgrund eines Zeitlimits geschlossen wird.

<!-- INTRO_END -->

## Usages {#usages}

Der `ConfirmDialog` bietet eine Möglichkeit, Benutzer um Bestätigung zu bitten oder zwischen mehreren Optionen, wie `Ja/Nein` oder `OK/Abbrechen`, zu wählen, damit sie ihre Aktionen anerkennen und bestätigen.

<ComponentDemo
path='/webforj/confirmdialogconstructor'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java']}
height='350px'
/>

## Types {#types}

### Option type {#option-type}

Der `ConfirmDialog` unterstützt die folgenden Optionstypen, die die im Dialog angezeigten Tasten bestimmen:

1. **`OK`**: Zeigt eine `OK`-Taste an.
2. **`OK_CANCEL`**: Zeigt `OK`- und `Abbrechen`-Tasten an.
3. **`ABORT_RETRY_IGNORE`**: Zeigt `Abbrechen`, `Wiederholen` und `Ignorieren`-Tasten an.
4. **`YES_NO_CANCEL`**: Zeigt `Ja`, `Nein` und `Abbrechen`-Tasten an.
5. **`YES_NO`**: Zeigt `Ja` und `Nein`-Tasten an.
6. **`RETRY_CANCEL`**: Zeigt `Wiederholen` und `Abbrechen`-Tasten an.
7. **`CUSTOM`**: Zeigt benutzerdefinierte Tasten an, wie angegeben.

### Message type {#message-type}

Der `ConfirmDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt der Dialog ein Symbol neben der Nachricht an, und das Design des Dialogs aktualisiert sich gemäß den Regeln des webforJ-Designsystems.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol an und nutzt das Standarddesign.
2. `ERROR`: Zeigt ein Fehler-Symbol neben der Nachricht mit dem Fehlerdesign an.
3. `QUESTION`: Zeigt ein Fragezeichen-Symbol neben der Nachricht an und nutzt das primäre Design.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem Warnungsdesign an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an und nutzt das Informationsdesign.

Im folgenden Beispiel konfiguriert der Code einen Bestätigungsdialog des Typs `CUSTOM` mit einem benutzerdefinierten Titel und einer benutzerdefinierten Nachricht.

<ComponentDemo
path='/webforj/confirmdialogoptions'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java']}
height='350px'
/>

## Result {#result}

Der `ConfirmDialog` gibt ein Ergebnis zurück, das auf der Interaktion des Benutzers mit dem Dialog basiert. Dieses Ergebnis zeigt an, welche Taste der Benutzer gedrückt hat oder ob der Dialog aufgrund eines Zeitlimits geschlossen wurde.

:::important
Das Ergebnis wird aus der Methode `show()` oder der entsprechenden Methode von `OptionDialog` zurückgegeben, wie unten gezeigt.
:::

Die `ConfirmDialog.Result`-Enum umfasst die folgenden möglichen Ergebnisse:

1. **`OK`**: Der Benutzer hat die `OK`-Taste gedrückt.
2. **`CANCEL`**: Der Benutzer hat die `CANCEL`-Taste gedrückt.
3. **`YES`**: Der Benutzer hat die `YES`-Taste gedrückt.
4. **`NO`**: Der Benutzer hat die `NO`-Taste gedrückt.
5. **`ABORT`**: Der Benutzer hat die `ABORT`-Taste gedrückt.
6. **`RETRY`**: Der Benutzer hat die `RETRY`-Taste gedrückt.
7. **`IGNORE`**: Der Benutzer hat die `IGNORE`-Taste gedrückt.
8. **`FIRST_CUSTOM_BUTTON`**: Der Benutzer hat die erste benutzerdefinierte Taste gedrückt.
9. **`SECOND_CUSTOM_BUTTON`**: Der Benutzer hat die zweite benutzerdefinierte Taste gedrückt.
10. **`THIRD_CUSTOM_BUTTON`**: Der Benutzer hat die dritte benutzerdefinierte Taste gedrückt.
11. **`TIMEOUT`**: Der Dialog hat ein Zeitlimit erreicht.
12. **`UNKNOWN`**: Ein unbekanntes Ergebnis, das typischerweise als Standard- oder Fehlerzustand verwendet wird.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
  OptionDialog.showMessageDialog("Änderungen verworfen", "Verworfen", "Verstanden");
} else {
  OptionDialog.showMessageDialog(
    "Änderungen gespeichert", "Gespeichert", "Verstanden", MessageDialog.MessageType.INFO);
}
```

## Default button {#default-button}

Der `ConfirmDialog` ermöglicht es Ihnen, eine Standardtaste anzugeben, die beim Anzeigen des Dialogs vorab ausgewählt ist. Dies verbessert die Benutzererfahrung, indem eine empfohlene Aktion bereitgestellt wird, die schnell durch Drücken der <kbd>Enter</kbd>-Taste bestätigt werden kann.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Sind Sie sicher?", "Bestätigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // zweite Taste
dialog.show();
```

## Buttons text {#buttons-text}

Sie können den Text der Tasten mit der Methode `setButtonText(ConfirmDialog.Button button, String text)` konfigurieren.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Sind Sie sicher?", "Bestätigen", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Absolut");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Nein");
dialog.show();
```

## HTML processing {#html-processing}

Standardmäßig verarbeitet und rendert der Bestätigungsdialog HTML-Inhalte. Sie können diese Funktion deaktivieren, indem Sie ihn so konfigurieren, dass er statischen Text anzeigt.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "<b>Sind Sie sicher?</b>", "Bestätigen",
  ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

Der `ConfirmDialog` ermöglicht es Ihnen, eine Zeitüberschreitungsdauer festzulegen, nach der der Dialog automatisch geschlossen wird. Diese Funktion ist nützlich für nicht kritische Bestätigungen oder Aktionen, die keine sofortige Interaktion des Benutzers erfordern.

Sie können die Zeitüberschreitung für den Dialog mit der Methode `setTimeout(int timeout)` konfigurieren. Die Zeitüberschreitung wird in Sekunden gemessen. Wenn die angegebene Zeit ohne Benutzerinteraktion verstreicht, schließt der Dialog automatisch.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Sind Sie sicher?", "Bestätigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Sie haben zu lange gebraucht, um zu entscheiden", "Zeitüberschreitung", "Verstanden",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "Sie haben Ja gedrückt", "Ja", "Verstanden",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "Sie haben Nein gedrückt", "Nein", "Verstanden",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Best practices {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht eindeutig erklärt, welche Aktion der Benutzer bestätigen soll. Vermeiden Sie Mehrdeutigkeiten.
2. **Angemessene Optionstypen**: Wählen Sie Optionstypen, die zum Kontext der Handlung passen. Bei einfachen Ja/Nein-Entscheidungen verwenden Sie unkomplizierte Optionen. Bei komplexeren Szenarien bieten Sie zusätzliche Tasten wie "Abbrechen" an, um den Benutzern zu ermöglichen, ohne eine Wahl zurückzutreten.
3. **Logische Standardtaste**: Legen Sie eine Standardtaste fest, die mit der wahrscheinlichsten oder empfohlenen Benutzeraktion übereinstimmt, um die Entscheidungsfindung zu optimieren.
4. **Konsistente Gestaltung**: Stimmen Sie das Design des Dialogs und der Tasten mit dem Design Ihrer App ab, um ein einheitliches Benutzererlebnis zu gewährleisten.
5. **Vorsichtiger Einsatz von Zeitüberschreitungen**: Setzen Sie Zeitüberschreitungen für nicht kritische Bestätigungen fest, um sicherzustellen, dass die Benutzer genügend Zeit haben, um die Aufforderung zu lesen und zu verstehen.
6. **Minimierung der Übernutzung**: Verwenden Sie Bestätigungsdialoge sparsam, um Benutzerfrustration zu vermeiden. Reservieren Sie sie für kritische Aktionen, die eine ausdrückliche Benutzerbestätigung erfordern.
