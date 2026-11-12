---
title: Erste Schritte
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 913c105077586054f3af87216fe848e0
---
Dieser Leitfaden erstellt eine webforJ-App, die mit BBjServices ausgeführt wird, führt sie während der Entwicklung auf Ihrem lokalen BBj aus und stellt sie mit BBjShip bereit.

:::tip Voraussetzungen
Sie benötigen ein Maven-Projekt und eine BBj-Installation derselben Version wie webforJ, zum Beispiel BBj 26.03 für webforJ 26.03, auf der BBjServices ausgeführt wird. BBjShip unterstützt Gradle nicht. Die übrigen Tools finden Sie unter [Voraussetzungen](/docs/introduction/prerequisites).
:::

## Eine BBj-App erstellen {#create-a-bbj-app}

Sie haben zwei Möglichkeiten, eine neue webforJ-App zu erstellen, die mit BBjServices ausgeführt wird: mit dem grafischen Tool startforJ oder über die Maven-Befehlszeile.

<!-- vale off -->
### Option 1: startforJ verwenden {#option-1-using-startforj}
<!-- vale on -->

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie mit den folgenden Angaben anpassen:

- Grundlegende Projektmetadaten (App Name, Group ID, Artifact ID)
- webforJ-Version und Java-Version
- Designfarbe und Symbol
- Archetyp
- **Ausführung auf** – Wählen Sie **BBjServices** aus, um ein Projekt zu erstellen, das mit BBjServices ausgeführt wird

### Option 2: die Befehlszeile verwenden {#option-2-using-the-command-line}

Wenn Sie lieber die Befehlszeile verwenden möchten, generieren Sie das Projekt mit den offiziellen [webforJ-Archetypen](/docs/building-ui/archetypes/overview):

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

Mit dem Parameter `flavor` generiert der Archetyp ein Projekt, das mit BBjServices ausgeführt wird.

Dadurch wird ein Projekt mit Folgendem erstellt:
- Der Abhängigkeit `webforj` ohne die webforJ-Engine, die von BBjServices bereitgestellt wird
- Dem webforJ-Maven-Plugin, das für die Bereitstellung auf BBjShip in Ihrem lokalen BBj eingerichtet ist; als App-Name wird die Artifact ID verwendet
- HotswapAgent für Entwicklungsläufe
- Beispielansichten
- webforJ-Konfigurationsdateien für Entwicklung und Produktion

## Die App ausführen {#run-the-app}

Ein Archetyp-Projekt legt sein Standard-Maven-Ziel fest. Daher kompiliert `mvn` ohne Argumente die App und startet einen [Entwicklungslauf](/docs/configuration/deploy-reload/bbjservices) in Ihrem lokalen BBjServices:

```bash
mvn
```

Beim ersten Lauf wird Ihr lokales BBj vorbereitet. Dabei wird BBjShip installiert und HotswapAgent zu BBjServices hinzugefügt. BBjServices wird daraufhin einmal neu gestartet. Anschließend gibt der Build die Adresse Ihrer App aus:

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press <kbd>Ctrl</kbd>+<kbd>C</kbd> to stop the app and the watch
```

Kompilierte Klassen werden ohne Neustart an die laufende App übergeben. Beenden Sie den Build mit <kbd>Ctrl</kbd>+<kbd>C</kbd>, um die App zu stoppen.

## Die App bereitstellen {#deploy-the-app}

Um die paketierte App bereitzustellen, führen Sie Folgendes aus:

```bash
mvn install
```

Die App wird dann unter `http://localhost:8888/webapp/my-app` veröffentlicht, bis Sie sie erneut bereitstellen oder entfernen. Informationen zur Bereitstellung auf einem anderen BBj-Server, beispielsweise über eine CI-Pipeline, finden Sie unter [Bereitstellen](/docs/configuration/bbjship/deploying).
