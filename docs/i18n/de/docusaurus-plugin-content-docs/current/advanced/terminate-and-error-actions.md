---
title: Terminate and Error Actions
sidebar_position: 40
_i18n_hash: d0f7532dd9019f6cd611255055c76754
---
<!-- vale off -->
# Beendigungs- und Fehleraktionen <DocChip chip='since' label='23.06' />
<!-- vale on -->

Bei der Entwicklung von Anwendungen mit webforJ ist es entscheidend, zu definieren, wie sich Ihre App verhält, wenn sie beendet wird oder auf einen Fehler stößt. Das Framework bietet Mechanismen zur Anpassung dieser Verhaltensweisen über `terminate`- und `error`-Aktionen.

## Übersicht {#overview}

Die `App`-Klasse ermöglicht es Ihnen, Aktionen zu definieren, die ausgeführt werden, wenn die App normal beendet wird oder wenn ein Fehler auftritt. Diese Aktionen sind Instanzen des `AppCloseAction`-Interfaces und können wie folgt festgelegt werden:

- `setTerminateAction(AppCloseAction action)`: Legt die Aktion fest, die beim normalen Beenden ausgeführt werden soll.
- `setErrorAction(AppCloseAction action)`: Legt die Aktion fest, die ausgeführt werden soll, wenn ein Fehler auftritt.

Verfügbare Implementierungen von `AppCloseAction` sind:

- `DefaultAction`: Löscht den Browser und zeigt eine lokalisierte Nachricht an, die den Benutzer auffordert, die App neu zu laden.
- `NoneAction`: Führt keine Aktion aus und setzt somit eine zuvor festgelegte Aktion zurück.
- `MessageAction`: Zeigt eine benutzerdefinierte Linknachricht an.
- `RedirectAction`: Leitet den Benutzer zu einer angegebenen URL weiter.

:::info Unterscheidung zwischen Beendigungsaktionen und Fehleraktionen in webforJ
webforJ behandelt die Beendigung aufgrund einer geworfenen oder nicht behandelten Ausnahme nicht als Fehleraktion, sondern als Beendigungsaktion, da die App normal heruntergefahren wird. Eine Fehleraktion tritt auf, wenn die App ein Beendigungssignal aufgrund eines externen Fehlers erhält, z. B. wenn der Browser keine Verbindung zum Server herstellen kann, auf dem die App ausgeführt wird.
:::

## Standardverhalten {#default-behavior}

In der Version `24.11` von webforJ verwendet die App standardmäßig `DefaultAction` für sowohl Beendigungs- als auch Fehlerereignisse. Das bedeutet, dass der Browser eine Nachricht anzeigt, die den Benutzer auffordert, die App neu zu laden, wenn die App beendet wird oder ein Fehler auftritt.

Ab Version `24.12` verwendet webforJ standardmäßig `NoneAction` für sowohl Beendigungs- als auch Fehlerereignisse. Diese Änderung bedeutet, dass bei der Beendigung der App oder bei einem Fehler keine Aktion ausgeführt wird, wodurch webforJ die Fehlerbehandlung an einen geeigneten `ErrorHandler` delegieren kann, falls einer konfiguriert ist, oder auf seine allgemeinen Fehlerbehandlungsmechanismen zurückgreift. Durch die Verwendung von `NoneAction` vermeidet die App die Störung des Standard-Fehlerbehandlungsflusses und ermöglicht es Ihnen, benutzerdefinierte Fehlerhandler zu definieren oder sich auf das integrierte Fehlermanagement von webforJ zu verlassen.

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
        "Danke, dass Sie unsere Anwendung genutzt haben! Klicken Sie zum Neuladen"
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
    // Setzen Sie eine Weiterleitungsaktion bei Fehlern
    setTerminateAction(new RedirectAction(
        "https://example.com/error"
    ));
  }
}
```

## Beenden der App {#terminating-the-app}

Sie können Ihre App programmatisch beenden, indem Sie die Methode `terminate()` aufrufen:

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

Beim Aufruf von `terminate()` wird die durch `setTerminateAction()` definierte Aktion ausgeführt.

## Hooks für die Beendigung {#hooks-for-termination}

Die `App`-Klasse bietet Hook-Methoden, um vor und nach der Beendigung Aktionen durchzuführen:

- `onWillTerminate()`: Wird vor der Beendigung aufgerufen.
- `onDidTerminate()`: Wird nach der Beendigung aufgerufen.

```java
public class MyApp extends App {

  @Override
  protected void onWillTerminate() {
    // Bereinigungsaufgaben durchführen
  }

  @Override
  protected void onDidTerminate() {
    // Aktionen nach der Beendigung
  }
}
```

:::tip Externe Lifecycle-Listener
Für ein fortgeschritteneres Lifecycle-Management sollten Sie `AppLifecycleListener` in Betracht ziehen, um Beendigungsereignisse von externen Komponenten zu behandeln, ohne die `App`-Klasse zu ändern. Dies ist insbesondere nützlich für Plugin-Architekturen oder wenn mehrere Komponenten auf die Beendigung der App reagieren müssen. Erfahren Sie mehr über [Lifecycle Listener](lifecycle-listeners.md).
:::

### Benutzerdefinierte Beendigungsseite {#custom-termination-page}

In einigen Fällen möchten Sie möglicherweise eine benutzerdefinierte Beendigungsseite anzeigen, wenn Ihre App endet, um den Benutzern eine personalisierte Nachricht oder zusätzliche Ressourcen zur Verfügung zu stellen. Dies kann erreicht werden, indem Sie die Methode `onDidTerminate()` in Ihrer `App`-Unterklasse überschreiben und benutzerdefiniertes HTML in die Seite einfügen.

Hier ist ein Beispiel, wie Sie eine benutzerdefinierte Beendigungsseite erstellen:

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
        <h1>Danke, dass Sie webforJ genutzt haben</h1>
        <p>Für weitere Informationen besuchen Sie <a href="https://webforj.com">webforj.com</a></p>
    </div>
    """;

    Page.getCurrent().executeJsVoidAsync(
        String.format("document.body.innerHTML = `%s`", html)
    );
  }
}
```
