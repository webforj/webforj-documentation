---
sidebar_position: 30
title: Message
_i18n_hash: 633e8c1297144da8b39cfd7ca2e77e5c
---
# Nachrichten-Dialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Ein `MessageDialog` ist ein modaler Dialog, der entwickelt wurde, um eine Nachricht an den Benutzer anzuzeigen, mit einer `OK`-Schaltfläche, um den Dialog zu schließen. Er blockiert die Ausführung der Anwendung, bis der Benutzer mit ihm interagiert oder er aufgrund eines Zeitlimits geschlossen wird.

```java
OptionDialog.showMessageDialog("Hallo Welt!");
```

## Verwendungen {#usages}

Der Nachrichten-Dialog bietet eine Möglichkeit, informative Warnungen anzuzeigen, wie z.B. Benachrichtigungen, Updates oder einfache Nachrichten, die nur eine Bestätigung des Benutzers erfordern, ohne dass Eingaben erforderlich sind.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hallo Welt", "Hallo Welt", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Nachrichtentyp {#message-type}

Der `MessageDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt der Dialog ein Symbol neben der Nachricht an, und das Thema des Dialogs wird gemäß den Regeln des WebforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol an und verwendet das Standardthema.
2. `ERROR`: Zeigt ein Fehler-Symbol neben der Nachricht mit dem Fehler-Thema an.
3. `QUESTION`: Zeigt ein Fragezeichen-Symbol neben der Nachricht an und verwendet das primäre Thema.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem Warn-Thema an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an und verwendet das Informations-Thema.

Im folgenden Beispiel konfiguriert der Code einen Nachrichten-Dialog vom Typ `WARNING` mit einem benutzerdefinierten Titel und einer Nachricht.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialog- & Schaltflächen-Thema
Standardmäßig bestimmt der Dialog das Thema basierend auf dem Nachrichtentyp. Sie können das Thema des Dialogs mit der Methode `setTheme(Theme theme)` anpassen und das Schaltflächen-Thema unabhängig mit der Methode `setButtonTheme(ButtonTheme theme)` anpassen, um unterschiedliche Variationen zu erstellen.
:::

## Schaltflächentext {#button-text}

Sie können den Text der Dialogschaltfläche mit `setButtonText(String text)` konfigurieren.

```java
OptionDialog.showMessageDialog("Hallo Welt!", "Titel", "Verstanden");
```

## HTML-Verarbeitung {#html-processing}

Standardmäßig verarbeitet der Nachrichten-Dialog und rendert HTML-Inhalte. Sie können diese Funktion deaktivieren, indem Sie ihn so konfigurieren, dass er rohen Text anzeigt.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hallo Welt</b>", "Hallo Welt", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Zeitlimit {#timeout}

Der `MessageDialog` ermöglicht es Ihnen, eine Zeitlimitsdauer festzulegen, nach der der Dialog automatisch geschlossen wird. Diese Funktion ist nützlich für nicht kritische Benachrichtigungen oder Informationen, die keine sofortige Interaktion des Benutzers erfordern.

Sie können das Zeitlimit für den Dialog mit der Methode `setTimeout(int timeout)` konfigurieren. Die Zeitlimitsdauer wird in Sekunden angegeben. Wenn die angegebene Zeit vergeht, ohne dass der Benutzer mit dem Dialog interagiert, schließt er sich automatisch.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Dieser Dialog wird bald ablaufen", "Zeitlimit");
dialog.setTimeout(2);
dialog.show();
```

## Beste Praktiken {#best-practices}

1. **Klare und prägnante Nachrichten**: Halten Sie die Nachrichten kurz und prägnant und vermeiden Sie technischen Jargon; verwenden Sie benutzerfreundliche Sprache.
2. **Geeignete Nachrichtentypen**:
   - Verwenden Sie `ERROR` für kritische Probleme.
   - Verwenden Sie `WARNING` für vorsichtige Hinweise.
   - Verwenden Sie `INFO` für allgemeine Informationen.
3. **Konsistente Themen**: Richten Sie die Dialog- und Schaltflächenthemen nach dem Design Ihrer Anwendung aus.
4. **Sinnvoller Einsatz von Zeitlimits**: Setzen Sie Zeitlimits für nicht kritische Benachrichtigungen und stellen Sie sicher, dass die Benutzer genügend Zeit haben, um die Nachricht zu lesen.
5. **Vermeidung von Übernutzung**: Verwenden Sie Dialoge sparsam, um Benutzerfrustration zu vermeiden, und reservieren Sie sie für wichtige Nachrichten, die eine Benutzeraktion oder Bestätigung erfordern.
