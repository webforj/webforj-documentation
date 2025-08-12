---
sidebar_position: 5
title: Confirm
_i18n_hash: 99babacee9e77d9376b00554e47d7ca3
---
# Bestätigungsdialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Ein `ConfirmDialog` ist ein modaler Dialog, der es dem Benutzer ermöglicht, eine der bis zu drei Optionen auszuwählen. Der Dialog blockiert die Ausführung der Anwendung, bis der Benutzer mit ihm interagiert oder er aufgrund eines Zeitlimits geschlossen wird.

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Bestätigen Sie?",
    "Bestätigung",
    ConfirmDialog.OptionType.OK_CANCEL,
    ConfirmDialog.MessageType.QUESTION);
```

## Verwendungen {#usages}

Der `ConfirmDialog` bietet eine Möglichkeit, Benutzer um Bestätigung zu bitten oder zwischen mehreren Optionen zu wählen, wie z.B. `Ja/Nein` oder `OK/Abbrechen`, um sicherzustellen, dass sie ihre Aktionen zur Kenntnis nehmen und bestätigen.

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Typen {#types}

### Optionstyp {#option-type}

Der `ConfirmDialog` unterstützt die folgenden Optionstypen, die die im Dialog angezeigten Schaltflächen bestimmen:

1. **`OK`**: Zeigt eine `OK`-Schaltfläche an.
2. **`OK_CANCEL`**: Zeigt `OK`- und `Abbrechen`-Schaltflächen an.
3. **`ABORT_RETRY_IGNORE`**: Zeigt `Abbrechen`, `Wiederholen` und `Ignorieren`-Schaltflächen an.
4. **`YES_NO_CANCEL`**: Zeigt `Ja`, `Nein` und `Abbrechen`-Schaltflächen an.
5. **`YES_NO`**: Zeigt `Ja`- und `Nein`-Schaltflächen an.
6. **`RETRY_CANCEL`**: Zeigt `Wiederholen` und `Abbrechen`-Schaltflächen an.
7. **`CUSTOM`**: Zeigt benutzerdefinierte Schaltflächen an, wie angegeben.

### Nachrichtentyp {#message-type}

Der `ConfirmDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt der Dialog ein Symbol neben der Nachricht an, und das Design des Dialogs wird gemäß den Regeln des WebforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol mit dem Standarddesign an.
2. `ERROR`: Zeigt ein Fehlericon neben der Nachricht mit dem angewandten Fehlerthema an.
3. `QUESTION`: Zeigt ein Fragezeichenicon neben der Nachricht unter Verwendung des Hauptthemas an.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem angewandten Warnungsthema an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht unter Verwendung des Informationsthemas an.

Im folgenden Beispiel konfiguriert der Code einen Bestätigungsdialog vom Typ `CUSTOM` mit einem benutzerdefinierten Titel und einer benutzerdefinierten Nachricht.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Ergebnis {#result}

Der `ConfirmDialog` gibt ein Ergebnis basierend auf der Interaktion des Benutzers mit dem Dialog zurück. Dieses Ergebnis zeigt an, welche Schaltfläche der Benutzer geklickt hat oder ob der Dialog aufgrund eines Zeitlimits verworfen wurde.

:::important
Das Ergebnis wird von der `show()`-Methode oder der entsprechenden `OptionDialog`-Methode wie unten gezeigt zurückgegeben. 
:::

Die Enum `ConfirmDialog.Result` umfasst die folgenden möglichen Ergebnisse:

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
11. **`TIMEOUT`**: Der Dialog hat ein Zeitlimit überschritten.
12. **`UNKNOWN`**: Ein unbekanntes Ergebnis, das typischerweise als Standard- oder Fehlerzustand verwendet wird.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
    OptionDialog.showMessageDialog("Änderungen verworfen", "Verworfen", "Verstanden");
} else {
    OptionDialog.showMessageDialog(
        "Änderungen gespeichert", "Gespeichert", "Verstanden", MessageDialog.MessageType.INFO);
}
```

## Standardschaltfläche {#default-button}

Der `ConfirmDialog` ermöglicht es Ihnen, eine Standardschaltfläche festzulegen, die beim Anzeigen des Dialogs vorausgewählt ist. Dies verbessert das Benutzererlebnis, indem eine vorgeschlagene Aktion bereitgestellt wird, die schnell durch Drücken der <kbd>Enter</kbd>-Taste bestätigt werden kann.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Sind Sie sicher?", "Bestätigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // zweite Schaltfläche
dialog.show();
```

## Schaltflächentext {#buttons-text}

Sie können den Text der Schaltflächen mit der Methode `setButtonText(ConfirmDialog.Button button, String text)` konfigurieren.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Sind Sie sicher?", "Bestätigen", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Absolut");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Nein");
dialog.show();
```

## HTML-Verarbeitung {#html-processing}

Standardmäßig verarbeitet und rendert der Bestätigungsdialog HTML-Inhalte. Sie können diese Funktion deaktivieren, indem Sie ihn so konfigurieren, dass er stattdessen reinen Text anzeigt.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "<b>Sind Sie sicher?</b>", "Bestätigen",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Zeitlimit {#timeout}

Der `ConfirmDialog` erlaubt es Ihnen, eine Zeitlimitdauer festzulegen, nach der der Dialog automatisch geschlossen wird. Diese Funktion ist nützlich für nicht kritische Bestätigungen oder Aktionen, die keine sofortige Interaktion des Benutzers erfordern.

Sie können das Zeitlimit für den Dialog mit der Methode `setTimeout(int timeout)` konfigurieren. Die Zeitlimitdauer ist in Sekunden angegeben. Wenn die angegebene Zeit ohne Benutzerinteraktion verstreicht, wird der Dialog automatisch geschlossen.

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

## Best Practices {#best-practices}

1. **Klarheit und Prägnanz**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, welche Aktion der Benutzer bestätigen soll. Vermeiden Sie Mehrdeutigkeiten.
2. **Angemessene Optionstypen**: Wählen Sie Optionstypen, die zum Kontext der Aktion passen. Verwenden Sie für einfache Ja/Nein-Entscheidungen einfache Optionen. Für komplexere Szenarien bieten Sie zusätzliche Schaltflächen wie "Abbrechen" an, um es den Benutzern zu ermöglichen, ohne eine Wahl zurückzukehren.
3. **Logische Standardschaltfläche**: Legen Sie eine Standardschaltfläche fest, die mit der wahrscheinlichsten oder empfohlenen Benutzeraktion übereinstimmt, um den Entscheidungsprozess zu erleichtern.
4. **Konsistentes Design**: Richten Sie das Design des Dialogs und der Schaltflächen an dem Design Ihrer Anwendung aus, um ein kohärentes Benutzererlebnis zu gewährleisten.
5. **Vernünftiger Einsatz von Zeitlimits**: Setzen Sie Zeitlimits für nicht kritische Bestätigungen und stellen Sie sicher, dass Benutzer genügend Zeit haben, um die Aufforderung zu lesen und zu verstehen.
6. **Minimierung der Überbeanspruchung**: Verwenden Sie Bestätigungsdialoge sparsam, um Benutzermüdung zu vermeiden. Reservieren Sie sie für kritische Aktionen, die eine ausdrückliche Bestätigung des Benutzers erfordern.
