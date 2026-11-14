---
title: Projekteinrichtung
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 95bc825bd041018765a732bf464d77b7
---
Um mit diesem Tutorial zu beginnen, benötigen Sie einen Speicherort für Ihr Projekt, an dem Sie Ihre Klassen und Ressourcen verwalten können. In den folgenden Abschnitten werden die verschiedenen Möglichkeiten beschrieben, wie Sie Ihr webforJ-Projekt für dieses Tutorial erstellen können.

## Quellcode verwenden {#using-source-code}

Am einfachsten können Sie diesem Tutorial folgen, indem Sie auf den Quellcode zurückgreifen. Sie können das gesamte Projekt herunterladen oder von GitHub klonen:

<!-- vale off -->
- ZIP-Datei herunterladen: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub-Repository: Klonen Sie das Projekt [direkt von GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

<!-- <VideoPlayer video="tutorial/project-setup" width={1920} height={1080} title="Project setup" /> -->

### Projektstruktur {#project-structure}

Das Projekt enthält sechs Unterverzeichnisse, eines für jeden Schritt des Tutorials. Jedes enthält eine ausführbare App. Wenn Sie den Schritten folgen, können Sie beobachten, wie sich die App von einer einfachen Einrichtung zu einem voll funktionsfähigen Kundenverwaltungssystem entwickelt.

```
webforj-tutorial
│   .gitignore
│   LICENSE
│   README.md
│
├───1-creating-a-basic-app
├───2-working-with-data
├───3-routing-and-composites
├───4-observers-and-route-parameters
├───5-validating-and-binding-data
└───6-integrating-an-app-layout
```

<!-- vale off -->
## startforJ verwenden {#using-startforj}
<!-- vale on -->

Wenn Sie lieber ein neues Projekt erstellen möchten, können Sie mit [startforJ](https://docs.webforj.com/startforj) ein minimales Starterprojekt generieren. Weitere Informationen zur Verwendung von startforJ finden Sie unter [Erste Schritte](/docs/introduction/getting-started).

:::note Erforderliche Einstellungen
- Wählen Sie im Dropdown-Menü **webforJ version** die webforJ-Version **26.01 oder höher** aus.
- Wählen Sie unter **Runs on** die Option **Spring Boot** aus.

## Befehlszeile verwenden {#using-command-line}

Sie können auch mit dem folgenden Befehl ein neues Projekt generieren:

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=com.webforj.tutorial \
  -DartifactId=customer-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
```powershell
mvn -B archetype:generate `
  -DarchetypeGroupId="com.webforj" `
  -DarchetypeArtifactId="webforj-archetype-hello-world" `
  -DarchetypeVersion="LATEST" `
  -DgroupId="com.webforj.tutorial" `
  -DartifactId="customer-app" `
  -Dversion="1.0-SNAPSHOT" `
  -Dflavor="webforj-spring"
```
  </TabItem>
  <TabItem value="cmd" label="Command Prompt">
```
mvn -B archetype:generate ^
  -DarchetypeGroupId="com.webforj" ^
  -DarchetypeArtifactId="webforj-archetype-hello-world" ^
  -DarchetypeVersion="LATEST" ^
  -DgroupId="com.webforj.tutorial" ^
  -DartifactId="customer-app" ^
  -Dversion="1.0-SNAPSHOT" ^
  -Dflavor="webforj-spring"
```
  </TabItem>
</Tabs>
<!-- vale on -->

## Konfigurationen {#configurations}

Die beiden genannten Möglichkeiten zum Erstellen eines neuen Projekts verwenden webforJ-[Archetypen](/docs/building-ui/archetypes/overview), die Ihrem Projekt automatisch die erforderlichen Konfigurationen hinzufügen. Dazu gehören Spring-[Abhängigkeiten](/docs/integrations/spring/spring-boot), das webforJ-Maven-Plugin zum Erstellen und Überwachen von Frontend-Quelldateien sowie die folgenden Eigenschaften in `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## App ausführen {#running-the-app}

So können Sie die App während des Tutorials ausführen:

1. Wechseln Sie in das Verzeichnis des gewünschten Schritts. Dies sollte das oberste Verzeichnis dieses Schritts sein und die Datei `pom.xml` enthalten.

2. Führen Sie den Spring-Boot-App lokal mit dem folgenden Maven-Befehl aus:
    ```bash
    mvn
    ```

   Die generierte POM-Datei konfiguriert diesen Standardbefehl so, dass er die App kompiliert, den webforJ-Frontend-Watcher startet und Spring Boot ausführt.

<!-- vale Google.WordList = NO -->
Beim Ausführen der App wird automatisch ein neues Browserfenster mit der Adresse `http://localhost:8080` geöffnet.
<!-- vale Google.WordList = YES -->
