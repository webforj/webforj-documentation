---
title: Message
sidebar_position: 30
_i18n_hash: 1925f377637c75ea99d29272f31258ff
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Ein `MessageDialog` ist ein modales Dialogfeld, das entwickelt wurde, um dem Benutzer eine Nachricht anzuzeigen, mit einer `OK`-Schaltfläche, um das Dialogfeld zu schließen. Es blockiert die Ausführung der App, bis der Benutzer damit interagiert oder es aufgrund eines Timeouts geschlossen wird.

<!-- INTRO_END -->

## Anwendungen {#usages}

Verwenden Sie die statische `showMessageDialog`-Methode, um eine einfache Nachricht anzuzeigen.

```java
OptionDialog.showMessageDialog("Hallo Welt!");
```

Um mehr Kontrolle über das Erscheinungsbild und Verhalten des Dialogfelds zu haben, erstellen Sie eine `MessageDialog`-Instanz direkt.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hallo Welt", "Hallo Welt", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Nachrichtentyp {#message-type}

Der `MessageDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt das Dialogfeld ein Symbol neben der Nachricht an, und das Thema des Dialogfelds wird gemäß den Regeln des WebforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol mit dem Standardthema an.
2. `ERROR`: Zeigt ein Fehler-Symbol neben der Nachricht mit dem angewendeten Fehlerthema an.
3. `QUESTION`: Zeigt ein Fragezeichen-Symbol neben der Nachricht an, mit dem primären Thema.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem angewendeten Warnungsthema an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an, mit dem Informationsthema.

Im folgenden Beispiel konfiguriert der Code ein Nachrichten-Dialogfeld vom Typ `WARNING` mit einem benutzerdefinierten Titel und einer Nachricht.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialog- & Schaltflächenthema
Standardmäßig bestimmt das Dialogfeld das Thema basierend auf dem Nachrichtentyp. Sie können das Thema des Dialogfelds mit der Methode `setTheme(Theme theme)` anpassen und das Thema der Schaltfläche unabhängig mit der Methode `setButtonTheme(ButtonTheme theme)` anpassen, um verschiedene Variationen zu erstellen.
:::

## Schaltflächentext {#button-text}

Sie können den Text der Dialog-Schaltfläche mit `setButtonText(String text)` konfigurieren.

```java
OptionDialog.showMessageDialog("Hallo Welt!", "Titel", "Verstanden");
```

## HTML-Verarbeitung {#html-processing}

Standardmäßig verarbeitet und rendert das Nachrichten-Dialogfeld HTML-Inhalte. Sie können diese Funktion deaktivieren, indem Sie es so konfigurieren, dass statischen Text angezeigt wird.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hallo Welt</b>", "Hallo Welt", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

Der `MessageDialog` ermöglicht es Ihnen, eine Timeout-Dauer festzulegen, nach der das Dialogfeld automatisch geschlossen wird. Diese Funktion ist nützlich für nicht kritische Benachrichtigungen oder Informationen, die keine unmittelbare Interaktion des Benutzers erfordern.

Sie können das Timeout für das Dialogfeld mit der Methode `setTimeout(int timeout)` konfigurieren. Die Timeout-Dauer ist in Sekunden. Wenn die angegebene Zeit vergeht, ohne dass der Benutzer interagiert, schließt sich das Dialogfeld automatisch.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Dieses Dialogfeld wird bald timeout", "Timeout");
dialog.setTimeout(2);
dialog.show();
```

## Beste Praktiken {#best-practices}

1. **Klare und prägnante Nachrichten**: Halten Sie Nachrichten kurz und präzise und vermeiden Sie technische Fachbegriffe; verwenden Sie benutzerfreundliche Sprache.
2. **Angemessene Nachrichtentypen**:
   - Verwenden Sie `ERROR` für kritische Probleme.
   - Verwenden Sie `WARNING` für vorsorgliche Hinweise.
   - Verwenden Sie `INFO` für allgemeine Informationen.
3. **Konsistente Themen**: Richten Sie Dialog- und Schaltflächenthemen nach dem Design Ihrer Apps aus.
4. **Vorsichtiger Einsatz von Timeout**: Legen Sie Timeouts für nicht kritische Benachrichtigungen fest und stellen Sie sicher, dass die Benutzer ausreichend Zeit haben, die Nachricht zu lesen.
5. **Übermäßigen Gebrauch vermeiden**: Verwenden Sie Dialogfelder sparsam, um Benutzerfrustration zu vermeiden und reservieren Sie sie für wichtige Nachrichten, die eine Benutzeraktion oder Bestätigung erfordern.
