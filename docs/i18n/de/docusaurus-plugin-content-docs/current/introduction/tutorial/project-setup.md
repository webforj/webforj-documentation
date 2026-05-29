---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 02dbd05d1fdaba50c25155904013471b
---
Um dieses Tutorial zu beginnen, benötigen Sie einen Speicherort für Ihr Projekt, an dem Sie Ihre Klassen und Ressourcen verwalten können. Die folgenden Abschnitte beschreiben die verschiedenen Möglichkeiten, wie Sie Ihr webforJ-Projekt für dieses Tutorial erstellen können.

## Verwendung des Quellcodes {#using-source-code}

Der einfachste Weg, diesem Tutorial zu folgen, besteht darin, auf den Quellcode zuzugreifen. Sie können das gesamte Projekt herunterladen oder es von GitHub klonen:

<!-- vale off -->
- ZIP herunterladen: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub-Repository: Klonen Sie das Projekt [direkt von GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

<!-- <div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div> -->

### Projektstruktur {#project-structure}

Das Projekt hat sechs Unterverzeichnisse, eines für jeden Schritt des Tutorials, und jedes enthält eine ausführbare Anwendung. Wenn Sie Schritt für Schritt folgen, können Sie sehen, wie die App von einer grundlegenden Einrichtung zu einem voll funktionsfähigen Kundenmanagementsystem fortschreitet.

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
## Verwendung von startforJ {#using-startforj}
<!-- vale on -->

Wenn Sie lieber ein neues Projekt erstellen möchten, können Sie [startforJ](https://docs.webforj.com/startforj) verwenden, um ein minimales Starterprojekt zu generieren. Siehe [Erste Schritte](/docs/introduction/getting-started) für detailliertere Informationen zur Verwendung von startforJ.

:::note Erforderliche Einstellungen
- Wählen Sie im Dropdown-Menü **webforJ-Version** die webforJ-Version **26.00 oder höher**.
- Wählen Sie im Dropdown-Menü **Flavor** **webforJ + Spring Boot**. 
:::

## Verwendung der Befehlszeile {#using-command-line}

Sie können auch ein neues Projekt mit dem folgenden Befehl generieren:

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
  <TabItem value="cmd" label="Eingabeaufforderung">
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

## Konfigurationen

Die beiden genannten Möglichkeiten zur Erstellung eines neuen Projekts verwenden webforJ [Archetypen](/docs/building-ui/archetypes/overview), die automatisch die benötigten Konfigurationen zu Ihrem Projekt hinzufügen, wie z.B. Spring [Abhängigkeiten](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) in Ihr POM und die folgenden Eigenschaften in `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Ausführen der App {#running-the-app}

Um die App aktiv zu sehen, während Sie durch das Tutorial fortschreiten:

1. Navigieren Sie zu dem Verzeichnis für den gewünschten Schritt. Dies sollte das oberste Verzeichnis für diesen Schritt sein, das die `pom.xml` enthält.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring-Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

<!-- vale Google.WordList = NO -->
Die Ausführung der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.
<!-- vale Google.WordList = YES -->
