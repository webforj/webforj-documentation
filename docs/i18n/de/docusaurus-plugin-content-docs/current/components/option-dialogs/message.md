---
title: Message
sidebar_position: 30
_i18n_hash: b90d101884ed5ce8f6be2604ec637aee
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Ein `MessageDialog` ist ein modales Dialogfeld, das entwickelt wurde, um eine Nachricht an den Benutzer anzuzeigen, mit einer `OK`-Schaltfläche zum Schließen des Dialogs. Es blockiert die Ausführung der Anwendung, bis der Benutzer damit interagiert oder es aufgrund eines Timeouts geschlossen wird.

<!-- INTRO_END -->

## Anwendungen {#usages}

Verwenden Sie die statische Methode `showMessageDialog`, um eine grundlegende Nachricht anzuzeigen.

```java
OptionDialog.showMessageDialog("Hallo Welt!");
```

Für mehr Kontrolle über das Erscheinungsbild und das Verhalten des Dialogs erstellen Sie direkt eine Instanz von `MessageDialog`.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "Hallo Welt", "Hallo Welt", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Nachrichtentyp {#message-type}

Der `MessageDialog` unterstützt die folgenden Nachrichtentypen. Wenn Sie einen Typ konfigurieren, zeigt der Dialog ein Symbol neben der Nachricht an, und das Thema des Dialogs wird gemäß den Regeln des webforJ-Designsystems aktualisiert.

1. `PLAIN`: Zeigt die Nachricht ohne Symbol an und verwendet das Standardthema.
2. `ERROR`: Zeigt ein Fehler-Symbol neben der Nachricht mit dem angewendeten Fehler-Thema an.
3. `QUESTION`: Zeigt ein Fragezeichen-Symbol neben der Nachricht an und verwendet das primäre Thema.
4. `WARNING`: Zeigt ein Warnsymbol neben der Nachricht mit dem angewendeten Warn-Thema an.
5. `INFO`: Zeigt ein Informationssymbol neben der Nachricht an und verwendet das Informations-Thema.

Im folgenden Beispiel konfiguriert der Code einen Nachrichten-Dialog vom Typ `WARNING` mit einem benutzerdefinierten Titel und einer benutzerdefinierten Nachricht.

<ComponentDemo
path='/webforj/messagedialogtype'
files={['src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java']}
height='350px'
/>

:::tip Dialog- & Tasten-Thema
Standardmäßig bestimmt der Dialog das Thema basierend auf dem Nachrichtentyp. Sie können das Thema des Dialogs mit der Methode `setTheme(Theme theme)` anpassen und das Tasten-Thema unabhängig mit der Methode `setButtonTheme(ButtonTheme theme)` anpassen, um verschiedene Variationen zu erstellen.
:::

## Schaltflächentext {#button-text}

Sie können den Text der Dialogschaltfläche mit `setButtonText(String text)` konfigurieren.

```java
OptionDialog.showMessageDialog("Hallo Welt!", "Titel", "Verstanden");
```

## HTML-Verarbeitung {#html-processing}

Standardmäßig verarbeitet und rendert der Nachrichten-Dialog HTML-Inhalte. Sie können diese Funktion deaktivieren, indem Sie ihn so konfigurieren, dass er Rohtext anstelle von HTML anzeigt.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "<b>Hallo Welt</b>", "Hallo Welt", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

Der `MessageDialog` ermöglicht es Ihnen, eine Timeout-Dauer festzulegen, nach der der Dialog automatisch geschlossen wird. Diese Funktion ist nützlich für nicht kritische Benachrichtigungen oder Informationen, die keine sofortige Interaktion des Benutzers erfordern.

Sie können das Timeout für den Dialog mit der Methode `setTimeout(int timeout)` konfigurieren. Die Timeout-Dauer ist in Sekunden. Wenn die angegebene Zeit ohne Benutzereingabe verstreicht, wird der Dialog automatisch geschlossen.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Dieser Dialog wird bald ablaufen", "Timeout");
dialog.setTimeout(2);
dialog.show();
```

## Beste Praktiken {#best-practices}

1. **Klare und prägnante Nachrichten**: Halten Sie Nachrichten kurz und auf den Punkt, und vermeiden Sie Fachjargon; verwenden Sie benutzerfreundliche Sprache.
2. **Angemessene Nachrichtentypen**:
   - Verwenden Sie `ERROR` für kritische Probleme.
   - Verwenden Sie `WARNING` für vorsorgliche Hinweise.
   - Verwenden Sie `INFO` für allgemeine Informationen.
3. **Konsistente Gestaltung**: Stimmen Sie die Designs von Dialogen und Schaltflächen auf das Design Ihrer Anwendung ab.
4. **Vorsichtiger Einsatz von Timeout**: Setzen Sie Timeouts für nicht kritische Benachrichtigungen und stellen Sie sicher, dass Benutzer genügend Zeit haben, um die Nachricht zu lesen.
5. **Vermeidung von Übernutzung**: Verwenden Sie Dialoge sparsam, um Benutzerfrustration zu vermeiden, und reservieren Sie sie für wichtige Nachrichten, die eine Benutzeraktion oder Bestätigung erfordern.
