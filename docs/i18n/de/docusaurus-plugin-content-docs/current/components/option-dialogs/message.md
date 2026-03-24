---
title: Message
sidebar_position: 30
_i18n_hash: 4540b0f4317acc598d4970d0f16ae757
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Ein `MessageDialog` ist ein modales Dialogfeld, das entwickelt wurde, um eine Nachricht an den Benutzer anzuzeigen, mit einem `OK`-Button zum Schließen des Dialogs. Es blockiert die Ausführung der Anwendung, bis der Benutzer mit ihm interagiert oder es aufgrund eines Zeitlimits geschlossen wird.

<!-- INTRO_END -->

## Anwendungen {#usages}

Verwenden Sie die statische Methode `showMessageDialog`, um eine einfache Nachricht anzuzeigen.

```java
OptionDialog.showMessageDialog("Hallo Welt!");
```

Für mehr Kontrolle über das Aussehen und Verhalten des Dialogs erstellen Sie direkt eine Instanz von `MessageDialog`.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "Hallo Welt", "Hallo Welt", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Nachrichtentyp {#message-type}

Der `MessageDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, wird ein Symbol neben der Nachricht angezeigt und das Design des Dialogs wird gemäß den Regeln des webforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol an und verwendet das Standarddesign.
2. `ERROR`: Zeigt ein Fehler-Symbol neben der Nachricht mit dem angewendeten Fehlerdesign an.
3. `QUESTION`: Zeigt ein Fragezeichen-Symbol neben der Nachricht an, unter Verwendung des primären Designs.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem angewendeten Warnungsdesign an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an, unter Verwendung des Informationsdesigns.

Im folgenden Beispiel konfiguriert der Code einen Nachrichten-Dialog des Typs `WARNING` mit einem benutzerdefinierten Titel und einer Nachricht.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialog- und Button-Design
Standardmäßig bestimmt der Dialog das Design basierend auf dem Nachrichtentyp. Sie können das Design des Dialogs mit der Methode `setTheme(Theme theme)` anpassen und das Button-Design unabhängig mit der Methode `setButtonTheme(ButtonTheme theme)` anpassen, um verschiedene Variationen zu erstellen.
:::

## Button-Text {#button-text}

Sie können den Text des Dialog-Buttons mit `setButtonText(String text)` konfigurieren.

```java
OptionDialog.showMessageDialog("Hallo Welt!", "Titel", "Verstanden");
```

## HTML-Verarbeitung {#html-processing}

Standardmäßig verarbeitet und rendert der Nachrichten-Dialog HTML-Inhalte. Sie können diese Funktion deaktivieren, indem Sie ihn so konfigurieren, dass er stattdessen Rohtext anzeigt.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "<b>Hallo Welt</b>", "Hallo Welt", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Zeitüberschreitung {#timeout}

Der `MessageDialog` ermöglicht es Ihnen, eine Zeitüberschreitungsdauer festzulegen, nach der der Dialog automatisch geschlossen wird. Diese Funktion ist nützlich für nicht-kritische Benachrichtigungen oder Informationen, die keine sofortige Interaktion des Benutzers erfordern.

Sie können die Zeitüberschreitung für den Dialog mit der Methode `setTimeout(int timeout)` konfigurieren. Die Zeitüberschreitung dauert in Sekunden. Wenn die angegebene Zeit ohne Benutzerinteraktion vergeht, schließt sich der Dialog automatisch.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Dieser Dialog wird bald ablaufen", "Zeitüberschreitung");
dialog.setTimeout(2);
dialog.show();
```

## Best Practices {#best-practices}

1. **Klar und prägnant**: Halten Sie Nachrichten kurz und auf den Punkt und vermeiden Sie technische Begriffe; verwenden Sie eine benutzerfreundliche Sprache.
2. **Angemessene Nachrichtentypen**:
   - Verwenden Sie `ERROR` für kritische Probleme.
   - Verwenden Sie `WARNING` für Vorsichtshinweise.
   - Verwenden Sie `INFO` für allgemeine Informationen.
3. **Konsistentes Design**: Stimmen Sie die Designs von Dialogen und Schaltflächen auf das Design Ihrer App ab.
4. **Vorsichtiger Umgang mit Zeitüberschreitung**: Setzen Sie Zeitüberschreitungen für nicht-kritische Benachrichtigungen und stellen Sie sicher, dass die Benutzer genügend Zeit haben, um die Nachricht zu lesen.
5. **Vermeiden Sie Übernutzung**: Verwenden Sie Dialoge sparsam, um Benutzerfrustration zu verhindern, und reservieren Sie sie für wichtige Nachrichten, die eine Benutzeraktion oder -bestätigung erfordern.
