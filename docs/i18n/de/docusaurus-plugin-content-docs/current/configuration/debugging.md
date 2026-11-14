---
title: Fehlersuche
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
_i18n_hash: 6e761f399c4a1f8d562acf85f92af615
---
Debugging ist ein wesentlicher Bestandteil der Java-Entwicklung und hilft Entwicklerinnen und Entwicklern, Probleme effizient zu erkennen und zu beheben. Diese Anleitung erklärt, wie Sie das Debugging in webforJ für Visual Studio Code, IntelliJ IDEA und Eclipse konfigurieren.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<VideoPlayer video="debugging/vscode" width={1920} height={1080} title="VS Code" />

1. Öffnen Sie Ihr webforJ-Projekt in VS Code.
2. Drücken Sie <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (oder auf dem Mac <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>), um das Fenster „Run and Debug“ zu öffnen.
3. Klicken Sie auf „create a launch.json file“.
4. Wählen Sie Java als Umgebung aus.
5. Ändern Sie `launch.json` wie folgt:

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

6. Speichern Sie die Datei und klicken Sie auf „Start Debugging“.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<VideoPlayer video="debugging/intellij" width={1920} height={1080} title="IntelliJ" />

1. Öffnen Sie Ihr Projekt in IntelliJ IDEA.
2. Navigieren Sie zu „Run“ → „Edit Configurations“.
3. Klicken Sie auf die Schaltfläche <kbd>+</kbd> und wählen Sie „Remote JVM Debug“ aus.
4. Legen Sie als Host `localhost` und als Port `8000` fest.
5. Speichern Sie die Konfiguration und klicken Sie auf „Debug“, um eine Verbindung zur laufenden App herzustellen.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<VideoPlayer video="debugging/eclipse" width={1920} height={1080} title="Eclipse" />

1. Öffnen Sie Ihr Projekt in Eclipse.
2. Navigieren Sie zu „Run“ → „Edit Configurations“.
3. Wählen Sie „Remote Java Application“ aus.
4. Klicken Sie auf „New Configuration“ und legen Sie Folgendes fest:
   - Host: `localhost`
   - Port: `8000`
5. Speichern Sie die Konfiguration und starten Sie den Debugger.

</TabItem>
</Tabs>

## Debugger ausführen {#running-the-debugger}

Nachdem Sie Ihre IDE konfiguriert haben:

1. Starten Sie Ihre webforJ-App mit `mvnDebug`.
2. Führen Sie die Debug-Konfiguration in Ihrer IDE aus.
3. Setzen Sie Haltepunkte und beginnen Sie mit dem Debugging.

:::tip Debugging-Tipps
1. Vergewissern Sie sich, dass Port 8000 verfügbar und nicht durch eine Firewall blockiert ist.
2. Wenn Sie eines der webforJ-Archetypen verwenden und die Portnummer in der Datei pom.xml geändert haben, muss der für das Debugging verwendete Port dem aktualisierten Wert entsprechen.
:::

## Laufende App untersuchen {#inspecting-the-running-app}

Ein Debugger zeigt Ihnen, was Ihr Code ausführt. [craftforJ](/docs/craftforj) zeigt Ihnen die App, die aus diesem Code entstanden ist, einschließlich des von webforJ erstellten Komponentenbaums, der Eigenschaften jeder Komponente, der aktiven Route und der Personen, die darauf zugreifen dürfen. Sie können eine Eigenschaft ändern, das Ergebnis in der laufenden App sehen und die Änderung in den Java-Code zurückschreiben, aus dem sie stammt.

craftforJ wird mit webforJ ausgeliefert und verwendet denselben Debug-Modus, den Sie bereits aktiviert haben, sowie eine zusätzliche Eigenschaft:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Weitere Informationen finden Sie unter [Erste Schritte mit craftforJ](/docs/craftforj/getting-started).
