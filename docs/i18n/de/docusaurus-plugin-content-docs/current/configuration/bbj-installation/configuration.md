---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
description: >-
  Configure the webforJ install Maven plugin with deploy URL, class name,
  publish name, and debug flags for BBjServices deployments.
_i18n_hash: b01357f571ce256abb8b390cebdbf5cc
---
Sie können webforJ über die POM-Datei eines Projekts konfigurieren, die dafür entwickelt wurde, die Bereitstellung einer Anwendung zu erleichtern. Die folgenden Abschnitte skizzieren die verschiedenen Optionen, die Sie ändern können, um ein gewünschtes Ergebnis zu erzielen.

## Engine-Ausschluss {#engine-exclusion}

Beim Ausführen mit `BBjServices` sollte die `webforj-engine`-Abhängigkeit ausgeschlossen werden, da die von der Engine bereitgestellten Funktionen bereits verfügbar sind.

```xml
<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
    <exclusions>
      <exclusion>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-engine</artifactId>
      </exclusion>
    </exclusions>
  </dependency>
</dependencies>
```

## POM-Datei-Tags {#pom-file-tags}

Tags innerhalb des `<configuration>`-Tags können geändert werden, um Ihre App zu konfigurieren. Das Bearbeiten der folgenden Zeilen in der Standard-POM-Datei, die mit dem [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) Start-Repository geliefert wird, führt zu diesen Änderungen:

```xml {13-16} showLineNumbers
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-install-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <executions>
    <execution>
      <goals>
        <goal>install</goal>
      </goals>
  </execution>
  </executions>
  <configuration>
    <deployurl>http://localhost:8888/webforj-install</deployurl>
    <classname>samples.HelloWorldApp</classname>
    <publishname>hello-world</publishname>
    <debug>true</debug>
  </configuration>
</plugin>
```

- **`<deployurl>`** Dieses Tag ist die URL, unter der der webforJ-Endpunkt für die Projektinstallation erreichbar ist. Für Benutzer, die ihre App lokal ausführen, wird ein Standardport von 8888 verwendet. Für Benutzer, die Docker verwenden, sollte der Port auf den Port geändert werden, der bei [der Konfiguration des Docker-Containers](./docker#2-configuration) eingegeben wurde.

- **`<classname>`** Dieses Tag sollte den Paket- und Klassennamen der App enthalten, die Sie ausführen möchten. Dies wird die einzige Klasse in Ihrem Projekt sein, die die `App`-Klasse erweitert und von der Basis-URL aus ausgeführt wird.

- **`<publishname>`** Dieses Tag gibt den Namen der App in der veröffentlichten URL an. Um Ihr Programm auszuführen, navigieren Sie in der Regel zu einer URL wie `http://localhost:8888/webapp/<publishname>`, wobei `<publishname>` durch den Wert im `<publishname>`-Tag ersetzt wird. Dann wird das durch das `<classname>`-Tag angegebene Programm ausgeführt.

- **`<debug>`** Das Debug-Tag kann auf true oder false gesetzt werden und bestimmt, ob die Konsole des Browsers Fehlermeldungen anzeigt, die von Ihrem Programm ausgelöst werden.

## Ausführen eines bestimmten Programms {#running-a-specific-program}

Es gibt zwei Möglichkeiten, ein spezifisches Programm in Ihrer App auszuführen:

1. Platzieren Sie das Programm innerhalb der `run()`-Methode der Klasse, die `App` erweitert.
2. Nutzen Sie [Routing](../../routing/overview) in Ihrer webforJ-App, um dem Programm eine dedizierte URL zu geben.

## Wie webforJ einen Einstiegspunkt auswählt {#how-webforj-selects-an-entry-point}

Der Einstiegspunkt für eine App wird durch das `<classname>` im POM-Datei bestimmt. Wenn im POM-Datei kein Einstiegspunkt angegeben ist, startet das System eine Suche nach einem Einstiegspunkt.

### Einstiegspunkt-Suche {#entry-point-search}

1. Wenn es eine einzelne Klasse gibt, die die `App`-Klasse erweitert, wird dies der Einstiegspunkt.
2. Wenn mehrere Klassen `App` erweitern, prüft das System, ob eine die `com.webforj.annotation.AppEntry`-Annotation hat. Die einzelne Klasse, die mit `@AppEntry` annotiert ist, wird der Einstiegspunkt.
    :::warning
    Wenn mehrere Klassen mit `@AppEntry` annotiert sind, wird eine Ausnahme ausgelöst, in der alle entdeckten Klassen aufgelistet werden.
    :::

Wenn es mehrere Klassen gibt, die `App` erweitern und keine von ihnen mit `@AppEntry` annotiert ist, wird eine Ausnahme ausgelöst, die jede Unterklasse auflistet.

## Debug-Modus {#debug-mode}

Es ist auch möglich, Ihre App im Debug-Modus auszuführen, der es der Konsole ermöglicht, umfassende Fehlermeldungen auszugeben.

Die erste Option besteht darin, die `config.bbx`-Datei zu ändern, die sich im `cfg/`-Verzeichnis Ihrer BBj-Installation befindet. Fügen Sie die Zeile `SET DEBUG=1` zur Datei hinzu und speichern Sie Ihre Änderungen.

Darüber hinaus können Sie im Enterprise Manager das Folgende als Programmargument hinzufügen: `DEBUG`

Das Abschließen einer dieser Optionen ermöglicht es der Browserkonsole, Fehlermeldungen auszugeben.
