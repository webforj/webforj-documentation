---
title: Debuggen
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
sidebar_class_name: updated-content
_i18n_hash: c7b0a48745ef8f5793e38a3dd7691176
---
Debugging is een essentieel onderdeel van Java-ontwikkeling, dat ontwikkelaars helpt om problemen efficiënt te identificeren en op te lossen. Deze gids legt uit hoe je debugging in webforJ configureert voor Visual Studio Code, IntelliJ IDEA en Eclipse.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Open je webforJ-project in VS Code.
2. Druk op <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (of <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> op Mac) om het Run and Debug-paneel te openen.
3. Klik op "create a launch.json file"
4. Selecteer Java als de omgeving.
5. Wijzig `launch.json` om het volgende aan te passen:

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
2. Navigeer naar Run → Edit Configurations.
3. Klik op de <kbd>+</kbd> knop en selecteer Remote JVM Debug.
4. Stel de host in op `localhost` en de poort op `8000`.
5. Sla de configuratie op en klik op Debug om aan de draaiende app te verbinden.

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
4. Klik op New Configuration en stel in:
   - Host: `localhost`
   - Poort: `8000`
5. Sla op en start de debugger.

</TabItem>
</Tabs>

## Running the debugger {#running-the-debugger}

Zodra je je IDE hebt geconfigureerd:

1. Start je webforJ-app met de bijbehorende opdracht:
    - Voor Jetty, gebruik `mvnDebug jetty:run`
    - Voor Spring Boot, gebruik `mvnDebug spring-boot:run`
2. Voer de debugconfiguratie uit in je IDE.
3. Zet breakpoints en begin met debuggen.

:::tip Debugging Tips
1. Zorg ervoor dat poort 8000 beschikbaar is en niet geblokkeerd wordt door een firewall.
2. Als je een van de webforJ-archetypes gebruikt en het poortnummer in het pom.xml-bestand hebt gewijzigd, zorg er dan voor dat de poort die voor debugging wordt gebruikt overeenkomt met de bijgewerkte waarde.
:::

## Inspecting the running app {#inspecting-the-running-app}

Een debugger toont je wat je code doet. [craftforJ](/docs/craftforj) toont je de app die door de code is geproduceerd, inclusief de componentenboom die webforJ heeft gebouwd, de eigenschappen die elke component heeft, welke route actief is en wie er toegang toe heeft. Je kunt een eigenschap veranderen, het resultaat in de draaiende app zien en die wijziging terugschrijven naar de Java waar het vandaan kwam.

craftforJ wordt geleverd met webforJ en gebruikt dezelfde debugmodus die je al hebt ingeschakeld, plus één extra eigenschap:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Zie [Getting started with craftforJ](/docs/craftforj/getting-started).
