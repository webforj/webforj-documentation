---
title: Einstieg
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 06ef0b29df76b835ecd12d7268e4eca6
---
Dieser Leitfaden erstellt eine webforJ-App, die mit BBjServices läuft, sie auf Ihrem lokalen BBj während der Entwicklung ausführt und sie mit BBjShip bereitstellt.

:::tip Voraussetzungen
Sie benötigen ein Maven-Projekt und eine BBj-Installation in der gleichen Version wie webforJ, zum Beispiel BBj 26.03 für webforJ 26.03, mit laufenden BBjServices. BBjShip unterstützt kein Gradle. Überprüfen Sie die [Voraussetzungen](/docs/introduction/prerequisites) für den Rest der Tools.
:::

## Erstellen einer BBj-App {#create-a-bbj-app}

Sie haben zwei Optionen zum Erstellen einer neuen webforJ-App, die mit BBjServices läuft: die grafische StartforJ-Anwendung oder die Maven-Befehlszeile zu verwenden.

### Option 1: Verwendung von StartforJ {#option-1-using-startforj}

Wenn Sie eine App mit [StartforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektdaten (App-Name, Gruppen-ID, Artefakt-ID)
- webforJ-Version und Java-Version
- Farbthema und Symbol
- Archetyp
- **Läuft auf** - Wählen Sie **BBjServices**, um ein Projekt zu erstellen, das mit BBjServices läuft

### Option 2: Verwendung der Befehlszeile {#option-2-using-the-command-line}

Wenn Sie die Befehlszeile bevorzugen, generieren Sie das Projekt mit den offiziellen webforJ-Archetypen:

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

Mit dem Parameter `flavor` generiert der Archetyp ein Projekt, das mit BBjServices läuft.

Dies erstellt ein Projekt mit:
- Der `webforj`-Abhängigkeit ohne die webforJ-Engine, die von BBjServices bereitgestellt wird
- Dem webforJ-Maven-Plugin, das eingerichtet ist, um zu BBjShip auf Ihrem lokalen BBj unter der Artefakt-ID als App-Name zu deployen
- HotswapAgent für Entwicklungsdurchläufe
- Beispielansichten
- webforJ-Konfigurationsdateien für Entwicklung und Produktion

## Die App ausführen {#run-the-app}

Ein Archetyp-Projekt legt sein standardmäßiges Maven-Ziel fest, sodass `mvn` ohne Argumente die App kompiliert und einen [Entwicklungsdurchlauf](/docs/configuration/deploy-reload/bbjservices) in Ihren lokalen BBjServices startet:

```bash
mvn
```

Der erste Durchlauf bereitet Ihren lokalen BBj vor. Es installiert BBjShip und fügt HotswapAgent zu BBjServices hinzu, was BBjServices einmal neu startet. Der Build gibt dann die Adresse Ihrer App aus:

```text
[INFO] webforJ watch: my-app läuft unter http://localhost:8888/webapp/my-app
[INFO] webforJ watch: drücken Sie Strg+C, um die App und die Überwachung zu stoppen
```

Klassen, die Sie kompilieren, erreichen die laufende App ohne einen Neustart. Beenden Sie den Build mit `Strg+C`, um die App zu stoppen.

## Die App bereitstellen {#deploy-the-app}

Um die verpackte App bereitzustellen, führen Sie aus:

```bash
mvn install
```

Die App wird dann unter `http://localhost:8888/webapp/my-app` veröffentlicht, bis Sie sie erneut bereitstellen oder entfernen. Siehe [Bereitstellung](/docs/configuration/bbjship/deploying), um auf einen anderen BBj-Server bereitzustellen, zum Beispiel von einer CI-Pipeline.
