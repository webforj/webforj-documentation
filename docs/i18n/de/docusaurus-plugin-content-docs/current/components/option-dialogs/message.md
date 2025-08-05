---
sidebar_position: 30
title: Message
_i18n_hash: 575bdfd5364ffdbd911ac0ebe0628359
---
# Nachrichten-Dialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Ein `MessageDialog` ist ein modaler Dialog, der entworfen wurde, um eine Nachricht an den Benutzer anzuzeigen, mit einer `OK`-Schaltfläche, um den Dialog zu schließen. Er blockiert die Ausführung der App, bis der Benutzer mit ihm interagiert oder er aufgrund eines Zeitlimits geschlossen wird.

```java
OptionDialog.showMessageDialog("Hallo Welt!");
```

## Verwendungen {#usages}

Der Nachrichten-Dialog bietet eine Möglichkeit, Informationswarnungen anzuzeigen, wie Benachrichtigungen, Aktualisierungen oder einfache Nachrichten, die nur erfordern, dass der Benutzer sie zur Kenntnis nimmt, ohne Eingaben bereitzustellen.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hallo Welt", "Hallo Welt", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Nachrichtentyp {#message-type}

Der `MessageDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt der Dialog ein Symbol neben der Nachricht an, und das Thema des Dialogs wird gemäß den Regeln des Designsystems von webforJ aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol an und verwendet das Standarddesign.
2. `ERROR`: Zeigt ein Fehlericon neben der Nachricht mit dem Fehlerdesign an.
3. `QUESTION`: Zeigt ein Fragezeichenicon neben der Nachricht an, verwendet das primäre Design.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem Warnungsthema an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an, verwendet das Informationsthema.

Im folgenden Beispiel konfiguriert der Code einen Nachrichten-Dialog des Typs `WARNING` mit einem benutzerdefinierten Titel und einer Nachricht.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialog- und Schaltflächendesign
Standardmäßig bestimmt der Dialog das Design basierend auf dem Nachrichtentyp. Sie können das Design des Dialogs mit der Methode `setTheme(Theme theme)` anpassen und das Schaltflächendesign unabhängig mit der Methode `setButtonTheme(ButtonTheme theme)` anpassen, um verschiedene Variationen zu erstellen.
:::

## Schaltflächentext {#button-text}

Sie können den Text der Dialogschaltfläche mit `setButtonText(String text)` konfigurieren.

```java
OptionDialog.showMessageDialog("Hallo Welt!", "Titel", "Verstanden");
```

## HTML-Verarbeitung {#html-processing}

Standardmäßig verarbeitet und rendert der Nachrichten-Dialog HTML-Inhalte. Sie können diese Funktion deaktivieren, indem Sie ihn so konfigurieren, dass er statischen Text anstelle von HTML anzeigt.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hallo Welt</b>", "Hallo Welt", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

Der `MessageDialog` ermöglicht es Ihnen, eine Timeout-Dauer festzulegen, nach der der Dialog automatisch geschlossen wird. Diese Funktion ist nützlich für nicht-kritische Benachrichtigungen oder Informationen, die keine sofortige Interaktion des Benutzers erfordern.

Sie können das Timeout für den Dialog mit der Methode `setTimeout(int timeout)` konfigurieren. Die Timeout-Dauer ist in Sekunden. Wenn die angegebene Zeit vergeht, ohne dass der Benutzer interagiert, wird der Dialog automatisch geschlossen.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Dieser Dialog wird bald ablaufen", "Timeout");
dialog.setTimeout(2);
dialog.show();
```

## Beste Praktiken {#best-practices}

1. **Klare und prägnante Nachrichten**: Halten Sie die Nachrichten kurz und prägnant und vermeiden Sie Fachjargon; verwenden Sie benutzerfreundliche Sprache.
2. **Angemessene Nachrichtentypen**:
   - Verwenden Sie `ERROR` für kritische Probleme.
   - Verwenden Sie `WARNING` für vorsichtige Hinweise.
   - Verwenden Sie `INFO` für allgemeine Informationen.
3. **Konsistentes Design**: Stimmen Sie die Designs von Dialog und Schaltflächen auf das Design Ihrer Anwendung ab.
4. **Überlegter Einsatz von Timeout**: Setzen Sie Timeouts für nicht-kritische Benachrichtigungen und stellen Sie sicher, dass die Benutzer genügend Zeit haben, die Nachricht zu lesen.
5. **Vermeidung von Übernutzung**: Verwenden Sie Dialoge sparsam, um Benutzerfrustration zu vermeiden, und reservieren Sie sie für wichtige Nachrichten, die eine Benutzeraktion oder -bestätigung erfordern.
