---
title: Foutopsporing
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
_i18n_hash: d3f71dbbc660b90f2f4c33fd071c9819
---
Debuggen is een essentieel onderdeel van Java-ontwikkeling en helpt ontwikkelaars problemen efficiënt te identificeren en op te lossen. In deze handleiding wordt uitgelegd hoe je debuggen configureert in webforJ voor Visual Studio Code, IntelliJ IDEA en Eclipse.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Open je webforJ-project in VS Code.
2. Druk op <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (of op <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> op een Mac) om het deelvenster Run and Debug te openen.
3. Klik op "create a launch.json file"
4. Selecteer Java als omgeving.
5. Pas `launch.json` aan zodat het overeenkomt met het volgende:

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

6. Sla het bestand op en klik op Start Debugging.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/intellij.mp4" type="video/mp4" />
      </video>
</div>

1. Open je project in IntelliJ IDEA.
2. Ga naar Run → Edit Configurations.
3. Klik op de knop <kbd>+</kbd> en selecteer Remote JVM Debug.
4. Stel de host in op `localhost` en de poort op `8000`.
5. Sla de configuratie op en klik op Debug om verbinding te maken met de actieve app.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. Open je project in Eclipse.
2. Ga naar Run → Edit Configurations.
3. Selecteer Remote Java Application.
4. Klik op New Configuration en stel het volgende in:
   - Host: `localhost`
   - Port: `8000`
5. Sla de configuratie op en start de debugger.

</TabItem>
</Tabs>

## De debugger uitvoeren {#running-the-debugger}

Zodra je je IDE hebt geconfigureerd:

1. Start je webforJ-app met `mvnDebug`.
2. Voer de debugconfiguratie uit in je IDE.
3. Stel breakpoints in en begin met debuggen.

:::tip Tips voor debuggen
1. Controleer of poort 8000 beschikbaar is en niet door een firewall wordt geblokkeerd.
2. Als je een van de webforJ-archetypen gebruikt en het poortnummer in het bestand pom.xml hebt gewijzigd, controleer dan of de poort die voor het debuggen wordt gebruikt overeenkomt met de bijgewerkte waarde.
:::

## De actieve app inspecteren {#inspecting-the-running-app}

Een debugger laat zien wat je code doet. [craftforJ](/docs/craftforj) laat de app zien die met die code is gemaakt, inclusief de componentstructuur die webforJ heeft opgebouwd, de eigenschappen van elke component, welke route actief is en wie toegang ertoe heeft. Je kunt een eigenschap wijzigen, het resultaat in de actieve app bekijken en die wijziging terugschrijven naar de Java-code waaruit deze afkomstig is.

craftforJ wordt meegeleverd met webforJ en gebruikt dezelfde debugmodus die je al hebt ingeschakeld, plus één extra eigenschap:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Zie [Aan de slag met craftforJ](/docs/craftforj/getting-started).
