---
title: Debugging
sidebar_position: 1
_i18n_hash: d296f9a16ac6e5962b6962aa55e98a52
---
Debugging ist ein wesentlicher Bestandteil der Java-Entwicklung, der Entwicklern hilft, Probleme effizient zu identifizieren und zu beheben. Dieser Leitfaden erklärt, wie man das Debugging in webforJ für Visual Studio Code, IntelliJ IDEA und Eclipse konfiguriert.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Öffne dein webforJ-Projekt in VS Code.
2. Drücke <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (oder <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> auf Mac), um das Run and Debug-Panel zu öffnen.
3. Klicke auf "create a launch.json file".
4. Wähle Java als Umgebung.
5. Ändere `launch.json`, um Folgendes zu entsprechen:

```json title="launch.json"
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Attach to Jetty",
      "request": "attach",
      "hostName": "localhost",
      "port": 8000
    }
  ]
}
```

6. Speichere die Datei und klicke auf Start Debugging.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/intellij.mp4" type="video/mp4" />
      </video>
</div>

1. Öffne dein Projekt in IntelliJ IDEA.
2. Navigiere zu Run → Edit Configurations.
3. Klicke auf die <kbd>+</kbd>-Schaltfläche und wähle Remote JVM Debug.
4. Setze den Host auf `localhost` und den Port auf `8000`.
5. Speichere die Konfiguration und klicke auf Debug, um dich mit der laufenden App zu verbinden.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. Öffne dein Projekt in Eclipse.
2. Gehe zu Run → Edit Configurations.
3. Wähle Remote Java Application.
4. Klicke auf New Configuration und setze:
   - Host: `localhost`
   - Port: `8000`
5. Speichere und starte den Debugger.

</TabItem>
</Tabs>

## Ausführen des Debuggers {#running-the-debugger}

Sobald du deine IDE konfiguriert hast:

1. Starte deine webforJ-App mit `mvnDebug jetty:run`.
2. Führe die Debug-Konfiguration in deiner IDE aus.
3. Setze Breakpoints und beginne mit dem Debugging.

:::tip Debugging-Tipps
1. Stelle sicher, dass der Port 8000 verfügbar ist und nicht von einer Firewall blockiert wird.
2. Wenn du eines der webforJ-Archetypen verwendest und die Portnummer in der pom.xml-Datei geändert hast, stelle sicher, dass der für das Debugging verwendete Port mit dem aktualisierten Wert übereinstimmt.
:::
