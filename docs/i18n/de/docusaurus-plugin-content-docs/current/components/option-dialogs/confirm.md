---
title: Confirm
sidebar_position: 5
_i18n_hash: f55c50a799ee979b4bd4dfd24ba56a19
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Ein `ConfirmDialog` ist ein modales Dialogfeld, das es dem Benutzer ermöglicht, eine von bis zu 3 Optionen auszuwählen. Das Dialogfeld blockiert die Ausführung der Anwendung, bis der Benutzer mit ihm interagiert oder es aufgrund eines Zeitlimits geschlossen wird.

<!-- INTRO_END -->

## Usages {#usages}

Der `ConfirmDialog` bietet eine Möglichkeit, die Benutzer um Bestätigung zu bitten oder zwischen mehreren Optionen zu wählen, wie `Ja/Nein` oder `OK/Abbrechen`, und stellt sicher, dass sie ihre Handlungen anerkennen und bestätigen.

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Types {#types}

### Option type {#option-type}

Der `ConfirmDialog` unterstützt die folgenden Optionstypen, die die im Dialogfeld angezeigten Schaltflächen bestimmen:

1. **`OK`**: Zeigt eine `OK`-Schaltfläche an.
2. **`OK_CANCEL`**: Zeigt `OK`- und `Abbrechen`-Schaltflächen an.
3. **`ABORT_RETRY_IGNORE`**: Zeigt `Abbrechen`, `Wiederholen` und `Ignorieren`-Schaltflächen an.
4. **`YES_NO_CANCEL`**: Zeigt `Ja`, `Nein` und `Abbrechen`-Schaltflächen an.
5. **`YES_NO`**: Zeigt `Ja`- und `Nein`-Schaltflächen an.
6. **`RETRY_CANCEL`**: Zeigt `Wiederholen` und `Abbrechen`-Schaltflächen an.
7. **`CUSTOM`**: Zeigt benutzerdefinierte Schaltflächen an, wie spezifiziert.

### Message type {#message-type}

Der `ConfirmDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, wird ein Symbol neben der Nachricht angezeigt, und das Design des Dialogs wird gemäß den Regeln des webforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol an, verwendet das Standarddesign.
2. `ERROR`: Zeigt ein Fehlersymbol neben der Nachricht mit dem Fehlerdesign an.
3. `QUESTION`: Zeigt ein Fragezeichensymbol neben der Nachricht an, verwendet das Hauptdesign.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem Warnungsdesign an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an, verwendet das Informationsdesign.

Im folgenden Beispiel konfiguriert der Code ein Bestätigungsdialogfeld vom Typ `CUSTOM` mit einem benutzerdefinierten Titel und einer benutzerdefinierten Nachricht.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Result {#result}

Der `ConfirmDialog` gibt ein Ergebnis basierend auf der Interaktion des Benutzers mit dem Dialog zurück. Dieses Ergebnis zeigt an, welche Schaltfläche der Benutzer geklickt hat oder ob der Dialog aufgrund eines Zeitlimits geschlossen wurde.

:::important
Das Ergebnis wird von der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode wie unten gezeigt zurückgegeben. 
:::

Das `ConfirmDialog.Result`-Enum umfasst die folgenden möglichen Ergebnisse:

1. **`OK`**: Der Benutzer hat die `OK`-Schaltfläche geklickt.
2. **`CANCEL`**: Der Benutzer hat die `CANCEL`-Schaltfläche geklickt.
3. **`YES`**: Der Benutzer hat die `YES`-Schaltfläche geklickt.
4. **`NO`**: Der Benutzer hat die `NO`-Schaltfläche geklickt.
5. **`ABORT`**: Der Benutzer hat die `ABORT`-Schaltfläche geklickt.
6. **`RETRY`**: Der Benutzer hat die `RETRY`-Schaltfläche geklickt.
7. **`IGNORE`**: Der Benutzer hat die `IGNORE`-Schaltfläche geklickt.
8. **`FIRST_CUSTOM_BUTTON`**: Der Benutzer hat die erste benutzerdefinierte Schaltfläche geklickt.
9. **`SECOND_CUSTOM_BUTTON`**: Der Benutzer hat die zweite benutzerdefinierte Schaltfläche geklickt.
10. **`THIRD_CUSTOM_BUTTON`**: Der Benutzer hat die dritte benutzerdefinierte Schaltfläche geklickt.
11. **`TIMEOUT`**: Das Dialogfeld ist abgelaufen.
12. **`UNKNOWN`**: Ein unbekanntes Ergebnis, typischerweise als Standard- oder Fehlerzustand verwendet.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
  OptionDialog.showMessageDialog("Änderungen verworfen", "Verworfen", "Verstanden");
} else {
  OptionDialog.showMessageDialog(
    "Änderungen gespeichert", "Gespeichert", "Verstanden", MessageDialog.MessageType.INFO);
}
```

## Default button {#default-button}

Der `ConfirmDialog` ermöglicht es Ihnen, eine Standard-Schaltfläche anzugeben, die beim Anzeigen des Dialogs vorausgewählt ist. Dies verbessert die Benutzererfahrung, indem eine empfohlene Aktion bereitgestellt wird, die schnell durch Drücken der <kbd>Enter</kbd>-Taste bestätigt werden kann.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Sind Sie sicher?", "Bestätigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // zweite Schaltfläche
dialog.show();
```

## Buttons text {#buttons-text}

Sie können den Text der Schaltflächen mit der Methode `setButtonText(ConfirmDialog.Button button, String text)` konfigurieren.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Sind Sie sicher?", "Bestätigen", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Absolut");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Nein");
dialog.show();
```

## HTML processing {#html-processing}

Standardmäßig verarbeitet und rendert das Bestätigungsdialogfeld HTML-Inhalte. Sie können diese Funktion deaktivieren, indem Sie es so konfigurieren, dass statischen Text anstelle von HTML angezeigt wird.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "<b>Sind Sie sicher?</b>", "Bestätigen",
  ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

Der `ConfirmDialog` ermöglicht es Ihnen, eine Zeitüberschreitung zu setzen, nach der das Dialogfeld automatisch geschlossen wird. Diese Funktion ist nützlich für nicht kritische Bestätigungen oder Aktionen, die keine sofortige Interaktion des Benutzers erfordern.

Sie können die Zeitüberschreitung für das Dialogfeld mit der Methode `setTimeout(int timeout)` konfigurieren. Die Zeitüberschreitung wird in Sekunden angegeben. Wenn die angegebene Zeit vergeht, ohne dass eine Benutzerinteraktion erfolgt, wird das Dialogfeld automatisch geschlossen.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Sind Sie sicher?", "Bestätigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Sie haben sich zu lange Zeit gelassen", "Zeitüberschreitung", "Verstanden",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "Sie haben Ja geklickt", "Ja", "Verstanden",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "Sie haben Nein geklickt", "Nein", "Verstanden",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Best practices {#best-practices}

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, welche Aktion der Benutzer bestätigen soll. Vermeiden Sie Mehrdeutigkeiten.
2. **Angemessene Optionstypen**: Wählen Sie Optionstypen, die dem Kontext der Aktion entsprechen. Bei einfachen Ja/Nein-Entscheidungen verwenden Sie einfache Optionen. Bei komplexeren Szenarien bieten Sie zusätzliche Schaltflächen wie "Abbrechen" an, um Benutzern die Möglichkeit zu geben, ohne Auswahl zurückzukehren.
3. **Logische Standard-Schaltfläche**: Setzen Sie eine Standard-Schaltfläche, die mit der wahrscheinlichsten oder empfohlenen Benutzeraktion übereinstimmt, um die Entscheidungsfindung zu vereinfachen.
4. **Konsistentes Thema**: Stimmen Sie das Design des Dialogs und der Schaltflächen mit dem Design Ihrer Anwendung ab, um ein einheitliches Benutzererlebnis zu schaffen.
5. **Vorsichtiger Einsatz von Zeitlimits**: Setzen Sie Zeitlimits für nicht kritische Bestätigungen, und stellen Sie sicher, dass die Benutzer genügend Zeit haben, um die Aufforderung zu lesen und zu verstehen.
6. **Übermäßigen Einsatz minimieren**: Verwenden Sie Bestätigungsdialoge sparsam, um Benutzerfrustration zu vermeiden. Reservieren Sie sie für kritische Aktionen, die eine ausdrückliche Bestätigung des Benutzers erfordern.
