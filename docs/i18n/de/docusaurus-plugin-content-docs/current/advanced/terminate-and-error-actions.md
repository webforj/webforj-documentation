---
title: Terminate and Error Actions
sidebar_position: 40
_i18n_hash: 1a250a51020b32c8b3471ae75ea8f750
---
<!-- vale off -->
# Beendigungs- und Fehleraktionen <DocChip chip='since' label='23.06' />
<!-- vale on -->

Bei der Entwicklung von Anwendungen mit webforJ ist es entscheidend, festzulegen, wie sich Ihre App verhält, wenn sie beendet wird oder auf einen Fehler stößt. Das Framework bietet Mechanismen, um diese Verhaltensweisen über `terminate`- und `error`-Aktionen anzupassen.

## Übersicht {#overview}

Die Klasse `App` ermöglicht es Ihnen, Aktionen zu definieren, die ausgeführt werden, wenn die App normal beendet wird oder einen Fehler auftritt. Diese Aktionen sind Instanzen des `AppCloseAction`-Interfaces und können mit folgenden Methoden festgelegt werden:

- `setTerminateAction(AppCloseAction action)`: Legt die Aktion fest, die bei normaler Beendigung ausgeführt werden soll.
- `setErrorAction(AppCloseAction action)`: Legt die Aktion fest, die bei einem Fehler ausgeführt werden soll.

Verfügbare Implementierungen von `AppCloseAction` sind:

- `DefaultAction`: Löscht den Browser und zeigt eine lokalisierte Nachricht an, die den Benutzer auffordert, die App neu zu laden.
- `NoneAction`: Führt keine Aktion aus und setzt damit eine zuvor festgelegte Aktion effektiv zurück.
- `MessageAction`: Zeigt eine benutzerdefinierte Link-Nachricht an.
- `RedirectAction`: Leitet den Benutzer zu einer angegebenen URL weiter.

:::info Unterscheidung zwischen Beendigungsaktionen und Fehleraktionen in webforJ
webforJ behandelt eine Beendigung aufgrund einer geworfenen oder unbehandelten Ausnahme nicht als Fehleraktion, sondern vielmehr als Beendigungsaktion, da die App normal heruntergefahren wird. Eine Fehleraktion tritt auf, wenn die App ein Beendigungssignal aufgrund eines externen Fehlers erhält, beispielsweise wenn der Browser keine Verbindung zum Server herstellen kann, der die App ausführt.
:::

## Standardverhalten {#default-behavior}

In der webforJ-Version `24.11` und früher verwendet die App standardmäßig `DefaultAction` sowohl für Beendigungs- als auch für Fehlerereignisse. Das bedeutet, dass beim Beenden oder bei einem Fehler die Nachricht angezeigt wird, die den Benutzer auffordert, die App neu zu laden.

Ab Version `24.12` verwendet webforJ standardmäßig `NoneAction` für sowohl Beendigungs- als auch Fehlerereignisse. Diese Änderung bedeutet, dass bei Beendigung oder Fehler keine Aktion ausgeführt wird, sodass webforJ die Fehlerbehandlung an einen geeigneten `ErrorHandler` delegieren kann, wenn einer konfiguriert ist, oder auf die generischen Fehlerbehandlungsmechanismen zurückgreifen kann. Durch die Verwendung von `NoneAction` vermeidet die App, den Standardfluss der Fehlerbehandlung zu stören, was es Ihnen ermöglicht, benutzerdefinierte Fehlerbehandler zu definieren oder sich auf das integrierte Fehlermanagement von webforJ zu verlassen.

## Anpassen von Aktionen {#customizing-actions}

Um das Standardverhalten zu ändern, verwenden Sie die Methoden `setTerminateAction()` und `setErrorAction()` in Ihrer `App`-Unterklasse.

### Festlegen einer benutzerdefinierten Nachrichtenaktion {#setting-a-custom-message-action}

Wenn Sie eine benutzerdefinierte Nachricht bei normaler Beendigung anzeigen möchten:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Setzen Sie eine benutzerdefinierte Nachrichtenaktion
    setTerminateAction(new MessageAction(
        "Danke, dass Sie unsere Anwendung verwendet haben! Klicken Sie hier, um neu zu laden."
    ));
  }
}
```

### Festlegen einer Weiterleitungsaktion {#setting-a-redirect-action}

Um den Benutzer bei normaler Beendigung zu einer bestimmten URL weiterzuleiten:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Setzen Sie eine Weiteraktionsaktion bei Fehlern
    setTerminateAction(new RedirectAction(
        "https://example.com/error"
    ));
  }
}
```

## Beenden der App {#terminating-the-app}

Sie können Ihre App programmgesteuert beenden, indem Sie die Methode `terminate()` aufrufen:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Beenden Sie die Anwendung unter bestimmten Bedingungen
    if (someCondition) {
      terminate();
    }
  }
}
```

Nach dem Aufruf von `terminate()` wird die durch `setTerminateAction()` definierte Aktion ausgeführt.

## Hooks für die Beendigung {#hooks-for-termination}

Die Klasse `App` bietet Hook-Methoden, um Aktionen vor und nach der Beendigung auszuführen:

- `onWillTerminate()`: Wird vor der Beendigung aufgerufen.
- `onDidTerminate()`: Wird nach der Beendigung aufgerufen.

```java
public class MyApp extends App {

  @Override
  protected void onWillTerminate() {
    // Führen Sie Bereinigungsaufgaben durch
  }

  @Override
  protected void onDidTerminate() {
    // Aktionen nach der Beendigung
  }
}
```

:::tip Externe Lebenszyklus-Listener
Für eine fortgeschrittenere Lebenszyklusverwaltung sollten Sie `AppLifecycleListener` verwenden, um Beendigungsereignisse von externen Komponenten zu behandeln, ohne die Klasse `App` zu ändern. Dies ist besonders nützlich für Plugin-Architekturen oder wenn mehrere Komponenten auf die Beendigung der App reagieren müssen. Erfahren Sie mehr über [Lifecycle-Listener](lifecycle-listeners.md).
:::

### Benutzerdefinierte Beendigungsseite {#custom-termination-page}

In einigen Fällen möchten Sie möglicherweise eine benutzerdefinierte Beendigungsseite anzeigen, wenn Ihre App endet, um den Benutzern eine personalisierte Nachricht oder zusätzliche Ressourcen bereitzustellen. Dies kann erreicht werden, indem Sie die Methode `onDidTerminate()` in Ihrer `App`-Unterklasse überschreiben und benutzerdefiniertes HTML in die Seite einfügen.

Hier ist ein Beispiel, wie Sie eine benutzerdefinierte Beendigungsseite erstellen können:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    setTerminateAction(new NoneAction());
    terminate();
  }

  @Override
  protected void onDidTerminate() {
    String html = """
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh; flex-direction: column;">
        <h1>Danke, dass Sie webforJ verwendet haben</h1>
        <p>Für weitere Informationen besuchen Sie <a href="https://webforj.com">webforj.com</a></p>
    </div>
    """;

    Page.getCurrent().executeJsVoidAsync(
        String.format("document.body.innerHTML = `%s`", html)
    );
  }
}
```
