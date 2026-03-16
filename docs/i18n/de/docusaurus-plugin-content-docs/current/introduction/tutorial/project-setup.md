---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: f9a028daac660f61634ad84d00cb0130
---
Um dieses Tutorial zu beginnen, benötigen Sie einen Standort für Ihr Projekt, an dem Sie Ihre Klassen und Ressourcen verwalten können. Die folgenden Abschnitte beschreiben die verschiedenen Möglichkeiten, wie Sie Ihr webforJ-Projekt für dieses Tutorial erstellen können.

## Verwendung des Quellcodes {#using-source-code}

Der einfachste Weg, diesem Tutorial zu folgen, besteht darin, sich auf den Quellcode zu beziehen. Sie können das gesamte Projekt herunterladen oder von GitHub klonen:

<!-- vale off -->
- ZIP herunterladen: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub-Repository: Klonen Sie das Projekt [direkt von GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Projektstruktur {#project-structure}

Das Projekt hat fünf Unterverzeichnisse, eines für jeden Schritt des Tutorials, und jedes enthält eine lauffähige App. Wenn Sie den Fortschritt verfolgen, können Sie sehen, wie die App von einem grundlegenden Setup zu einem vollständig funktionalen Kundenverwaltungssystem fortschreitet.

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
└───5-validating-and-binding-data
```

## Verwendung von startforJ {#using-startforj}

Wenn Sie lieber ein neues Projekt erstellen möchten, können Sie [startforJ](https://docs.webforj.com/startforj) verwenden, um ein minimales Starterprojekt zu generieren. Siehe [Erste Schritte](/docs/introduction/getting-started) für detailliertere Informationen zur Verwendung von startforJ.

:::note Erforderliche Einstellungen
- Wählen Sie im Dropdown-Menü **webforJ-Version** die webforJ-Version **25.10 oder höher**.
- Wählen Sie im Dropdown-Menü **Flavor** **webforJ + Spring Boot**. 
:::

## Verwendung der Befehlszeile {#using-command-line}

Sie können auch ein neues Projekt mit dem folgenden Befehl generieren:

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

## Konfigurationen

Die beiden genannten Möglichkeiten zur Erstellung eines neuen Projekts verwenden webforJ [Archetypen](/docs/building-ui/archetypes/overview), die automatisch die benötigten Konfigurationen zu Ihrem Projekt hinzufügen, wie z.B. Spring [Abhängigkeiten](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) zu Ihrem POM und die folgenden Eigenschaften in `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Ausführen der App {#running-the-app}

Um die App in Aktion zu sehen, während Sie durch das Tutorial fortschreiten:

1. Navigieren Sie zu dem Verzeichnis des gewünschten Schrittes. Dies sollte das Hauptverzeichnis für diesen Schritt sein, das die `pom.xml` enthält.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Das Ausführen der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.
