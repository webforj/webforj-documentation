---
title: Aan de slag
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 913c105077586054f3af87216fe848e0
---
Deze handleiding maakt een webforJ-app die met BBjServices wordt uitgevoerd, laat je de app tijdens de ontwikkeling op je lokale BBj uitvoeren en zet de app met BBjShip in productie.

:::tip Vereisten
Je hebt een Maven-project nodig en een BBj-installatie met dezelfde versie als webforJ, bijvoorbeeld BBj 26.03 voor webforJ 26.03, waarop BBjServices actief is. BBjShip ondersteunt Gradle niet. Bekijk de [vereisten](/docs/introduction/prerequisites) voor de overige tools.
:::

## Een BBj-app maken {#create-a-bbj-app}

Je hebt twee opties om een nieuwe webforJ-app te maken die met BBjServices wordt uitgevoerd: de grafische tool startforJ gebruiken of de Maven-opdrachtregel.

<!-- vale off -->
### Optie 1: startforJ gebruiken {#option-1-using-startforj}
<!-- vale on -->

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie op te geven:

- Basisgegevens van het project (appnaam, groeps-ID, artefact-ID)
- webforJ-versie en Java-versie
- Themakleur en pictogram
- Archetype
- **Wordt uitgevoerd op** - Selecteer **BBjServices** om een project te maken dat met BBjServices wordt uitgevoerd

### Optie 2: de opdrachtregel gebruiken {#option-2-using-the-command-line}

Als je liever de opdrachtregel gebruikt, genereer je het project met de officiële [webforJ-archetypen](/docs/building-ui/archetypes/overview):

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
    ```bash {8}
    mvn -B archetype:generate \
    -DarchetypeGroupId=com.webforj \
    -DarchetypeArtifactId=webforj-archetype-hello-world \
    -DarchetypeVersion=LATEST \
    -DgroupId=org.example \
    -DartifactId=my-app \
    -Dversion=1.0-SNAPSHOT \
    -Dflavor=webforj-bbj
    ```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
    ```powershell {8}
    mvn -B archetype:generate `
    -DarchetypeGroupId="com.webforj" `
    -DarchetypeArtifactId="webforj-archetype-blank" `
    -DarchetypeVersion="LATEST" `
    -DgroupId="org.example" `
    -DartifactId="my-app" `
    -Dversion="1.0-SNAPSHOT" `
    -Dflavor="webforj-bbj"
    ```
  </TabItem>
  <TabItem value="cmd" label="Command Prompt">
    ```console {8}
    mvn -B archetype:generate ^
    -DarchetypeGroupId="com.webforj" ^
    -DarchetypeArtifactId="webforj-archetype-blank" ^
    -DarchetypeVersion="LATEST" ^
    -DgroupId="org.example" ^
    -DartifactId="my-app" ^
    -Dversion="1.0-SNAPSHOT" ^
    -Dflavor="webforj-bbj"
    ```
  </TabItem>
</Tabs>
<!-- vale on -->

Met de parameter `flavor` genereert het archetype een project dat met BBjServices wordt uitgevoerd.

Dit maakt een project met:
- De afhankelijkheid `webforj`, zonder de webforJ-engine die door BBjServices wordt geleverd
- De webforJ Maven-plug-in, ingesteld om naar BBjShip op je lokale BBj te implementeren, met de artefact-ID als appnaam
- HotswapAgent voor ontwikkelruns
- Voorbeeldweergaven
- webforJ-configuratiebestanden voor ontwikkeling en productie

## De app uitvoeren {#run-the-app}

Een archetypeproject stelt het standaard Maven-doel in. Daarom compileert `mvn` zonder argumenten de app en start het een [ontwikkelrun](/docs/configuration/deploy-reload/bbjservices) in je lokale BBjServices:

```bash
mvn
```

De eerste run bereidt je lokale BBj voor. BBjShip wordt geïnstalleerd en HotswapAgent wordt toegevoegd aan BBjServices, waarna BBjServices één keer opnieuw wordt gestart. Vervolgens toont de build het adres van je app:

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press <kbd>Ctrl</kbd>+<kbd>C</kbd> to stop the app and the watch
```

De klassen die je compileert, worden beschikbaar in de actieve app zonder dat deze opnieuw hoeft te worden gestart. Stop de build met <kbd>Ctrl</kbd>+<kbd>C</kbd> om de app te stoppen.

## De app implementeren {#deploy-the-app}

Voer de volgende opdracht uit om de verpakte app te implementeren:

```bash
mvn install
```

De app wordt vervolgens gepubliceerd op `http://localhost:8888/webapp/my-app`, totdat je deze opnieuw implementeert of verwijdert. Zie [Implementeren](/docs/configuration/bbjship/deploying) voor informatie over implementatie naar een andere BBj-server, bijvoorbeeld vanuit een CI-pipeline.
