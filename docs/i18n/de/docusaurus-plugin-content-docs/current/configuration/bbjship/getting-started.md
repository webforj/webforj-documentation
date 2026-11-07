---
title: Erste Schritte
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 84b3ad107540f450350f754730522e0e
---
Dieser Leitfaden erstellt eine webforJ-Anwendung, die mit BBjServices ausgeführt wird, führt sie während der Entwicklung auf Ihrem lokalen BBj aus und stellt sie mit BBjShip bereit.

:::tip Voraussetzungen
Sie benötigen ein Maven-Projekt und eine BBj-Installation derselben Version wie webforJ, zum Beispiel BBj 26.03 für webforJ 26.03, mit laufendem BBjServices. BBjShip unterstützt Gradle nicht. Die übrigen benötigten Tools finden Sie unter [Voraussetzungen](/docs/introduction/prerequisites).
:::

## BBj-Anwendung erstellen {#create-a-bbj-app}

Sie haben zwei Möglichkeiten, eine neue webforJ-Anwendung zu erstellen, die mit BBjServices ausgeführt wird: mit dem grafischen Tool startforJ oder über die Maven-Befehlszeile.

<!-- vale off -->
### Option 1: mit startforJ {#option-1-using-startforj}
<!-- vale on -->

Wenn Sie eine Anwendung mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie mit den folgenden Angaben anpassen:

- Grundlegende Projektmetadaten (App Name, Group ID, Artifact ID)
- webforJ-Version und Java-Version
- Theme Color und Icon
- Archetyp
- **Runs on** – Wählen Sie **BBjServices** aus, um ein Projekt zu erstellen, das mit BBjServices ausgeführt wird

### Option 2: über die Befehlszeile {#option-2-using-the-command-line}

Wenn Sie lieber die Befehlszeile verwenden, generieren Sie das Projekt mit den offiziellen webforJ-Archetypen:

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

Mit dem Parameter `flavor` generiert der Archetyp ein Projekt, das mit BBjServices ausgeführt wird.

Dabei wird ein Projekt mit folgenden Bestandteilen erstellt:
- Der Abhängigkeit `webforj` ohne die webforJ-Engine, die von BBjServices bereitgestellt wird
- Dem webforJ-Maven-Plugin, das für die Bereitstellung auf BBjShip in Ihrem lokalen BBj konfiguriert ist; der Artifact-ID wird dabei als App-Name verwendet
- HotswapAgent für Entwicklungsläufe
- Beispielansichten
- webforJ-Konfigurationsdateien für Entwicklung und Produktion

## Anwendung ausführen {#run-the-app}

Ein Archetyp-Projekt legt das Standard-Maven-Ziel fest. Daher kompiliert `mvn` ohne Argumente die Anwendung und startet einen [Entwicklungslauf](/docs/configuration/deploy-reload/bbjservices) in Ihrem lokalen BBjServices:

```bash
mvn
```

Beim ersten Lauf wird Ihr lokales BBj vorbereitet. Dabei wird BBjShip installiert und HotswapAgent zu BBjServices hinzugefügt. Anschließend wird BBjServices einmal neu gestartet. Der Build gibt dann die Adresse Ihrer Anwendung aus:

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press <kbd>Ctrl</kbd>+<kbd>C</kbd> to stop the app and the watch
```

Kompilierte Klassen werden ohne Neustart in der laufenden Anwendung übernommen. Beenden Sie den Build mit <kbd>Ctrl</kbd>+<kbd>C</kbd>, um die Anwendung zu stoppen.

## Anwendung bereitstellen {#deploy-the-app}

Um die gepackte Anwendung bereitzustellen, führen Sie folgenden Befehl aus:

```bash
mvn install
```

Die Anwendung wird unter `http://localhost:8888/webapp/my-app` veröffentlicht, bis Sie sie erneut bereitstellen oder entfernen. Unter [Bereitstellung](/docs/configuration/bbjship/deploying) erfahren Sie, wie Sie die Anwendung auf einem anderen BBj-Server bereitstellen, zum Beispiel über eine CI-Pipeline.
