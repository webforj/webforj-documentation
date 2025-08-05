---
sidebar_position: 5
title: Confirm
_i18n_hash: 1a5d5c10371b3d751853eb3c3bcbe66f
---
# Bestätigungsdialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Ein `ConfirmDialog` ist ein modaler Dialog, der es dem Benutzer ermöglicht, eine von bis zu 3 Optionen auszuwählen. Der Dialog blockiert die Ausführung der App, bis der Benutzer mit ihm interagiert oder er aufgrund eines Timeouts geschlossen wird.

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Bestätigen Sie?", 
    "Bestätigung", 
    ConfirmDialog.OptionType.OK_CANCEL, 
    ConfirmDialog.MessageType.QUESTION);
```

## Verwendungen {#usages}

Der `ConfirmDialog` bietet eine Möglichkeit, die Benutzer um eine Bestätigung zu bitten oder zwischen mehreren Optionen wie `Ja/Nein` oder `OK/Abbrechen` zu wählen, sodass sie ihre Aktionen zur Kenntnis nehmen und bestätigen.

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

Der `ConfirmDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt der Dialog ein Symbol neben der Nachricht an, und das Design des Dialogs wird gemäß den Regeln des webforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne ein Symbol an, unter Verwendung des Standarddesigns.
2. `ERROR`: Zeigt ein Fehler-Symbol neben der Nachricht mit dem Fehlerdesign an.
3. `QUESTION`: Zeigt ein Fragezeichen-Symbol neben der Nachricht an, unter Verwendung des primären Designs.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem Warnungsdesign an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an, unter Verwendung des Informationsdesigns.

Im folgenden Beispiel konfiguriert der Code einen Bestätigungsdialog des Typs `CUSTOM` mit einem benutzerdefinierten Titel und einer benutzerdefinierten Nachricht.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Ergebnis {#result}

Der `ConfirmDialog` gibt ein Ergebnis zurück, basierend auf der Interaktion des Benutzers mit dem Dialog. Dieses Ergebnis zeigt an, welche Schaltfläche der Benutzer geklickt hat oder ob der Dialog aufgrund eines Timeouts geschlossen wurde.

:::important
Das Ergebnis wird von der Methode `show()` oder der entsprechenden `OptionDialog`-Methode wie unten dargestellt zurückgegeben. 
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
11. **`TIMEOUT`**: Der Dialog hat einen Timeout.
12. **`UNKNOWN`**: Ein unbekanntes Ergebnis, das typischerweise als Standard- oder Fehlerzustand verwendet wird.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
    OptionDialog.showMessageDialog("Änderungen verworfen", "Verworfen", "Verstanden");
} else {
    OptionDialog.showMessageDialog(
        "Änderungen gespeichert", "Gespeichert", "Verstanden", MessageDialog.MessageType.INFO);
}
```

## Standard-Schaltfläche {#default-button}

Der `ConfirmDialog` ermöglicht es Ihnen, eine Standard-Schaltfläche anzugeben, die vorab ausgewählt ist, wenn der Dialog angezeigt wird. Dies verbessert das Benutzererlebnis, indem eine empfohlene Aktion bereitgestellt wird, die schnell durch Drücken der <kbd>Enter</kbd>-Taste bestätigt werden kann.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Sind Sie sich sicher?", "Bestätigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // zweite Schaltfläche
dialog.show();
```

## Schaltflächentexte {#buttons-text}

Sie können den Text der Schaltflächen mit der Methode `setButtonText(ConfirmDialog.Button button, String text)` konfigurieren.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Sind Sie sich sicher?", "Bestätigen", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Absolut");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Nein");
dialog.show();
```

## HTML-Verarbeitung {#html-processing}

Standardmäßig verarbeitet der Bestätigungsdialog HTML-Inhalte und stellt diese dar. Sie können diese Funktion deaktivieren, indem Sie ihn so konfigurieren, dass er statischen Text anzeigt.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "<b>Sind Sie sich sicher?</b>", "Bestätigen",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

Der `ConfirmDialog` ermöglicht es Ihnen, eine Timeout-Dauer festzulegen, nach der der Dialog automatisch geschlossen wird. Diese Funktion ist nützlich für nicht kritische Bestätigungen oder Aktionen, die nicht die sofortige Interaktion des Benutzers erfordern.

Sie können das Timeout für den Dialog mit der Methode `setTimeout(int timeout)` konfigurieren. Die Timeout-Dauer wird in Sekunden angegeben. Wenn die angegebene Zeit ohne Benutzerinteraktion verstrichen ist, wird der Dialog automatisch geschlossen.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Sind Sie sich sicher?", "Bestätigen", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Sie haben zu lange gebraucht, um zu entscheiden", "Timeout", "Verstanden",
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

1. **Klare und prägnante Aufforderungen**: Stellen Sie sicher, dass die Aufforderungsnachricht klar erklärt, welche Aktion vom Benutzer bestätigt werden soll. Vermeiden Sie Mehrdeutigkeiten.
2. **Angemessene Optionstypen**: Wählen Sie Optionstypen, die dem Kontext der Aktion entsprechen. Verwenden Sie für einfache Ja/Nein-Entscheidungen unkomplizierte Optionen. Für komplexere Szenarien sollten zusätzliche Schaltflächen wie "Abbrechen" angeboten werden, um den Benutzern zu ermöglichen, ohne Auswahl zurückzutreten.
3. **Logische Standard-Schaltfläche**: Legen Sie eine Standard-Schaltfläche fest, die mit der wahrscheinlichsten oder empfohlenen Benutzeraktion übereinstimmt, um die Entscheidungsfindung zu erleichtern.
4. **Konsistente Gestaltung**: Richten Sie die Gestaltung des Dialogs und der Schaltflächen an dem Design Ihrer App aus, um ein einheitliches Benutzererlebnis zu gewährleisten.
5. **Überlegter Einsatz von Timeouts**: Legen Sie Timeouts für nicht kritische Bestätigungen fest, um sicherzustellen, dass die Benutzer genügend Zeit haben, um die Aufforderung zu lesen und zu verstehen.
6. **Übermäßigen Einsatz minimieren**: Verwenden Sie Bestätigungsdialoge sparsam, um Benutzerfrustration zu vermeiden. Reservieren Sie sie für kritische Aktionen, die eine ausdrückliche Bestätigung des Benutzers erfordern.
