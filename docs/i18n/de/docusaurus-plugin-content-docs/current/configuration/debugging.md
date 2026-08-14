---
title: Debugging
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
sidebar_class_name: updated-content
_i18n_hash: c7b0a48745ef8f5793e38a3dd7691176
---
Debugging ist ein wesentlicher Bestandteil der Java-Entwicklung, der Entwicklern hilft, Probleme effizient zu identifizieren und zu beheben. Diese Anleitung erklärt, wie man das Debugging in webforJ für Visual Studio Code, IntelliJ IDEA und Eclipse konfiguriert.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Öffnen Sie Ihr webforJ-Projekt in VS Code.
2. Drücken Sie <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (oder <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> auf Mac), um das Panel Ausführen und Debuggen zu öffnen.
3. Klicken Sie auf "create a launch.json file".
4. Wählen Sie Java als das Umfeld aus.
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
2. Navigieren Sie zu Ausführen → Konfigurationen bearbeiten.
3. Klicken Sie auf die <kbd>+</kbd>-Schaltfläche und wählen Sie Remote JVM Debug.
4. Setzen Sie den Host auf `localhost` und den Port auf `8000`.
5. Speichern Sie die Konfiguration und klicken Sie auf Debug, um sich an die laufende App anzuhängen.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. Öffnen Sie Ihr Projekt in Eclipse.
2. Gehen Sie zu Ausführen → Konfigurationen bearbeiten.
3. Wählen Sie Remote Java Application.
4. Klicken Sie auf Neue Konfiguration und setzen Sie:
   - Host: `localhost`
   - Port: `8000`
5. Speichern Sie und starten Sie den Debugger.

</TabItem>
</Tabs>

## Ausführen des Debuggers {#running-the-debugger}

Sobald Sie Ihre IDE konfiguriert haben:

1. Starten Sie Ihre webforJ-App mit dem entsprechenden Befehl:
    - Für Jetty verwenden Sie `mvnDebug jetty:run`.
    - Für Spring Boot verwenden Sie `mvnDebug spring-boot:run`.
2. Führen Sie die Debug-Konfiguration in Ihrer IDE aus.
3. Setzen Sie Haltepunkte und beginnen Sie mit dem Debuggen.

:::tip Debugging Tipps
1. Stellen Sie sicher, dass der Port 8000 verfügbar ist und nicht durch eine Firewall blockiert wird.
2. Wenn Sie eines der webforJ-Archetypen verwenden und die Portnummer in der pom.xml-Datei geändert haben, stellen Sie sicher, dass der für das Debugging verwendete Port mit dem aktualisierten Wert übereinstimmt.
:::

## Überprüfen der laufenden App {#inspecting-the-running-app}

Ein Debugger zeigt Ihnen, was Ihr Code tut. [craftforJ](/docs/craftforj) zeigt Ihnen die App, die der Code produziert hat, einschließlich des Komponentenbaums, den webforJ erstellt hat, die Eigenschaften, die jede Komponente hat, welcher Pfad aktiv ist und wer darauf zugreifen darf. Sie können eine Eigenschaft ändern, das Ergebnis in der laufenden App sehen und diese Änderung in den ursprünglichen Java-Code zurückschreiben.

craftforJ wird mit webforJ ausgeliefert und verwendet den gleichen Debug-Modus, den Sie bereits aktiviert haben, plus eine zusätzliche Eigenschaft:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Siehe [Erste Schritte mit craftforJ](/docs/craftforj/getting-started).
