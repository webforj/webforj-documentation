---
title: Confirm
sidebar_position: 5
_i18n_hash: 712808f446f16655074e93cda2231286
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Ein `ConfirmDialog` ist ein modales Dialogfeld, das es dem Benutzer ermöglicht, eine von bis zu 3 Optionen auszuwählen. Der Dialog blockiert die Ausführung der Anwendung, bis der Benutzer damit interagiert oder er aufgrund eines Timeouts geschlossen wird.

<!-- INTRO_END -->

## Verwendungen {#usages}

Der `ConfirmDialog` bietet eine Möglichkeit, Benutzer um Bestätigung zu bitten oder zwischen mehreren Optionen, wie `Ja/Nein` oder `OK/Abbrechen`, zu wählen, um sicherzustellen, dass sie ihre Aktionen anerkennen und bestätigen.

<ComponentDemo
path='/webforj/confirmdialogconstructor'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java']}
height='350px'
/>

## Typen {#types}

### Optionstyp {#option-type}

Der `ConfirmDialog` unterstützt die folgenden Optionstypen, die die im Dialog angezeigten Schaltflächen bestimmen:

1. **`OK`**: Zeigt eine `OK`-Schaltfläche an.
2. **`OK_CANCEL`**: Zeigt `OK`- und `Abbrechen`-Schaltflächen an.
3. **`ABORT_RETRY_IGNORE`**: Zeigt `Abbrechen`, `Wiederholen` und `Ignorieren`-Schaltflächen an.
4. **`YES_NO_CANCEL`**: Zeigt `Ja`, `Nein` und `Abbrechen`-Schaltflächen an.
5. **`YES_NO`**: Zeigt `Ja` und `Nein`-Schaltflächen an.
6. **`RETRY_CANCEL`**: Zeigt `Wiederholen`- und `Abbrechen`-Schaltflächen an.
7. **`CUSTOM`**: Zeigt benutzerdefinierte Schaltflächen an, wie angegeben.

### Nachrichtentyp {#message-type}

Der `ConfirmDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt der Dialog ein Symbol neben der Nachricht an, und das Thema des Dialogs wird entsprechend den Regeln des WebforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol an, unter Verwendung des Standardthemas.
2. `ERROR`: Zeigt ein Fehler-Symbol neben der Nachricht mit dem Fehler-Thema an.
3. `QUESTION`: Zeigt ein Fragezeichen-Symbol neben der Nachricht an, unter Verwendung des primären Themas.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem Warnungs-Thema an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an, unter Verwendung des Informations-Themas.

Im folgenden Beispiel konfiguriert der Code einen Bestätigungsdialog vom Typ `CUSTOM` mit einem benutzerdefinierten Titel und einer benutzerdefinierten Nachricht.

<ComponentDemo
path='/webforj/confirmdialogoptions'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java']}
height='350px'
/>

## Ergebnis {#result}

Der `ConfirmDialog` gibt ein Ergebnis zurück, das auf der Interaktion des Benutzers mit dem Dialog basiert. Dieses Ergebnis zeigt an, welche Schaltfläche der Benutzer geklickt hat oder ob der Dialog aufgrund eines Timeouts verworfen wurde.

:::important
Das Ergebnis wird von der Methode `show()` oder der entsprechenden `OptionDialog`-Methode zurückgegeben, wie unten gezeigt.
:::

Das `ConfirmDialog.Result`-Enum enthält die folgenden möglichen Ergebnisse:

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
11. **`TIMEOUT`**: Der Dialog hat einen Timeout.
12. **`UNKNOWN`**: Ein unbekanntes Ergebnis, typischerweise als Standard- oder Fehlerstatus verwendet.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
  OptionDialog.showMessageDialog("Änderungen verworfen", "Verworfen", "Verstanden");
} else {
  OptionDialog.showMessageDialog(
    "Änderungen gespeichert", "Gespeichert", "Verstanden", MessageDialog.MessageType.INFO);
}
```

## Standard-Schaltfläche {#default-button}

Der `ConfirmDialog` ermöglicht es Ihnen, eine Standard-Schaltfläche anzugeben, die vorab ausgewählt ist, wenn der Dialog angezeigt wird. Dies verbessert das Benutzererlebnis, indem es eine empfohlene Aktion bietet, die schnell durch Drücken der <kbd>Enter</kbd>-Taste bestätigt werden kann.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Sind Sie sicher?", "Bestätigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // zweite Schaltfläche
dialog.show();
```

## Schaltflächentext {#buttons-text}

Sie können den Text der Schaltflächen mithilfe der Methode `setButtonText(ConfirmDialog.Button button, String text)` konfigurieren.

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

## Timeout {#timeout}

Der `ConfirmDialog` ermöglicht es Ihnen, eine Timeout-Dauer festzulegen, nach der der Dialog automatisch geschlossen wird. Diese Funktion ist nützlich für nicht kritische Bestätigungen oder Aktionen, die nicht sofortige Interaktion des Benutzers erfordern.

Sie können das Timeout für den Dialog mit der Methode `setTimeout(int timeout)` konfigurieren. Die Timeout-Dauer wird in Sekunden angegeben. Wenn die angegebene Zeit vergeht, ohne dass der Benutzer interagiert, schließt sich der Dialog automatisch.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Sind Sie sicher?", "Bestätigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Sie haben zu lange gebraucht, um eine Entscheidung zu treffen", "Timeout", "Verstanden",
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

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, welche Aktion der Benutzer bestätigen soll. Vermeiden Sie Mehrdeutigkeit.
2. **Angemessene Optionstypen**: Wählen Sie Optionstypen, die dem Kontext der Aktion entsprechen. Für einfache Ja/Nein-Entscheidungen verwenden Sie unkomplizierte Optionen. Für komplexere Szenarien bieten Sie zusätzliche Schaltflächen wie "Abbrechen" an, um Benutzern zu ermöglichen, ohne eine Wahl zurückzukehren.
3. **Logische Standard-Schaltfläche**: Setzen Sie eine Standard-Schaltfläche, die mit der wahrscheinlichsten oder empfohlenen Benutzeraktion übereinstimmt, um den Entscheidungsprozess zu erleichtern.
4. **Konsistente Gestaltung**: Passen Sie das Design des Dialogs und der Schaltflächen an das Design Ihrer App an, um ein kohärentes Benutzererlebnis zu gewährleisten.
5. **Überlegter Einsatz von Timeouts**: Setzen Sie Timeouts für nicht kritische Bestätigungen, und stellen Sie sicher, dass Benutzer genügend Zeit haben, um die Aufforderung zu lesen und zu verstehen.
6. **Übermäßige Nutzung minimieren**: Verwenden Sie Bestätigungsdialoge sparsam, um Frustration bei den Benutzern zu vermeiden. Reservieren Sie sie für kritische Aktionen, die eine ausdrückliche Benutzerbestätigung erfordern.
