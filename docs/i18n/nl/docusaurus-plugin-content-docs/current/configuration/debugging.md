---
title: Foutopsporing
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
_i18n_hash: 6e761f399c4a1f8d562acf85f92af615
---
Debuggen is een essentieel onderdeel van Java-ontwikkeling en helpt ontwikkelaars problemen efficiënt te vinden en op te lossen. In deze handleiding wordt uitgelegd hoe je debuggen configureert in webforJ voor Visual Studio Code, IntelliJ IDEA en Eclipse.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<VideoPlayer video="debugging/vscode" width={1920} height={1080} title="VS Code" />

1. Open je webforJ-project in VS Code.
2. Druk op <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (of op <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> op een Mac) om het paneel Uitvoeren en debuggen te openen.
3. Klik op 'create a launch.json file'.
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

6. Sla het bestand op en klik op Debuggen starten.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<VideoPlayer video="debugging/intellij" width={1920} height={1080} title="IntelliJ" />

1. Open je project in IntelliJ IDEA.
2. Ga naar Uitvoeren → Configuraties bewerken.
3. Klik op de knop <kbd>+</kbd> en selecteer Remote JVM Debug.
4. Stel de host in op `localhost` en de poort op `8000`.
5. Sla de configuratie op en klik op Debug om verbinding te maken met de actieve app.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<VideoPlayer video="debugging/eclipse" width={1920} height={1080} title="Eclipse" />

1. Open je project in Eclipse.
2. Ga naar Uitvoeren → Configuraties bewerken.
3. Selecteer Remote Java Application.
4. Klik op New Configuration en stel het volgende in:
   - Host: `localhost`
   - Poort: `8000`
5. Sla de configuratie op en start de debugger.

</TabItem>
</Tabs>

## De debugger uitvoeren {#running-the-debugger}

Nadat je je IDE hebt geconfigureerd:

1. Start je webforJ-app met `mvnDebug`.
2. Voer de debugconfiguratie uit in je IDE.
3. Stel breekpunten in en begin met debuggen.

:::tip Tips voor debuggen
1. Controleer of poort 8000 beschikbaar is en niet door een firewall wordt geblokkeerd.
2. Als je een van de webforJ-archetypen gebruikt en het poortnummer in het bestand pom.xml hebt gewijzigd, controleer dan of de poort die voor debuggen wordt gebruikt overeenkomt met de gewijzigde waarde.
:::

## De actieve app inspecteren {#inspecting-the-running-app}

Een debugger laat zien wat je code doet. [craftforJ](/docs/craftforj) laat de app zien die door die code is gemaakt, inclusief de componentenboom die webforJ heeft opgebouwd, de eigenschappen van elke component, welke route actief is en wie er toegang toe heeft. Je kunt een eigenschap wijzigen, het resultaat in de actieve app bekijken en die wijziging terugschrijven naar de Java-code waaruit deze afkomstig is.

craftforJ wordt geleverd met webforJ en gebruikt dezelfde debugmodus die je al hebt ingeschakeld, plus één extra eigenschap:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Zie [Aan de slag met craftforJ](/docs/craftforj/getting-started).
