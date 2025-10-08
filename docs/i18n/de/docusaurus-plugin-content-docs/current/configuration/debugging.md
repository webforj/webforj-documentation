---
title: Debugging
sidebar_position: 1
_i18n_hash: 057e00d21a3392bb3bf8d1fba1dea15f
---
Debugging ist ein wesentlicher Bestandteil der Java-Entwicklung, der Entwicklern hilft, Probleme effizient zu identifizieren und zu beheben. Dieser Leitfaden erklärt, wie Sie das Debugging in webforJ für Visual Studio Code, IntelliJ IDEA und Eclipse konfigurieren.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Öffnen Sie Ihr webforJ-Projekt in VS Code.
2. Drücken Sie <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (oder <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> auf Mac), um das Panel "Run and Debug" zu öffnen.
3. Klicken Sie auf "create a launch.json file".
4. Wählen Sie Java als Umgebung aus.
5. Ändern Sie `launch.json`, um Folgendes zu entsprechen:

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

6. Speichern Sie die Datei und klicken Sie auf Start Debugging.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/intellij.mp4" type="video/mp4" />
      </video>
</div>

1. Öffnen Sie Ihr Projekt in IntelliJ IDEA.
2. Navigieren Sie zu Run → Edit Configurations.
3. Klicken Sie auf die <kbd>+</kbd>-Taste und wählen Sie Remote JVM Debug aus.
4. Setzen Sie den Host auf `localhost` und den Port auf `8000`.
5. Speichern Sie die Konfiguration und klicken Sie auf Debug, um sich mit der laufenden App zu verbinden.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. Öffnen Sie Ihr Projekt in Eclipse.
2. Gehen Sie zu Run → Edit Configurations.
3. Wählen Sie Remote Java Application aus.
4. Klicken Sie auf Neue Konfiguration und setzen Sie:
   - Host: `localhost`
   - Port: `8000`
5. Speichern Sie und starten Sie den Debugger.

</TabItem>
</Tabs>

## Ausführen des Debuggers {#running-the-debugger}

Sobald Sie Ihre IDE konfiguriert haben:

1. Starten Sie Ihre webforJ-App mit dem entsprechenden Befehl: 
    - Für Jetty verwenden Sie `mvnDebug jetty:run` 
    - Für Spring Boot verwenden Sie `mvnDebug spring-boot:run`
2. Führen Sie die Debug-Konfiguration in Ihrer IDE aus.
3. Setzen Sie Haltepunkte und beginnen Sie mit dem Debugging.

:::tip Debugging-Tipps
1. Stellen Sie sicher, dass der Port 8000 verfügbar ist und nicht von einer Firewall blockiert wird.
2. Wenn Sie eines der webforJ-Archetypen verwenden und die Portnummer in der pom.xml-Datei geändert haben, stellen Sie sicher, dass der für das Debugging verwendete Port mit dem aktualisierten Wert übereinstimmt.
:::
