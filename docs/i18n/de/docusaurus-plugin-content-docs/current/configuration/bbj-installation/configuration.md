---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 3f3e4285abb3b23f9427cdd7b9baa282
---
Sie können webforJ über die POM-Datei eines Projekts konfigurieren, die so gestaltet ist, dass die Bereitstellung einer App einfach ist. Die folgenden Abschnitte skizzieren die verschiedenen Optionen, die Sie ändern können, um ein gewünschtes Ergebnis zu erzielen.

## Engine-Ausschluss {#engine-exclusion}

Beim Ausführen mit `BBjServices` sollte die Abhängigkeit `webforj-engine` ausgeschlossen werden, da die vom Engine bereitgestellten Funktionen bereits verfügbar sind.

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

- **`<deployurl>`** Dieses Tag ist die URL, unter der der webforJ-Endpunkt für die Projektinstallation erreicht werden kann. Für Benutzer, die ihre App lokal ausführen, wird ein Standardport von 8888 verwendet. Für Benutzer, die Docker ausführen, sollte der Port auf den Port geändert werden, der bei [Konfiguration des Docker-Containers](./docker#2-configuration) eingegeben wurde.

- **`<classname>`** Dieses Tag sollte den Paket- und Klassennamen der App enthalten, die Sie ausführen möchten. Dies wird die einzige Klasse in Ihrem Projekt sein, die die `App`-Klasse erweitert und von der Basis-URL aus ausgeführt wird.

- **`<publishname>`** Dieses Tag spezifiziert den Namen der App in der veröffentlichten URL. Generell, um Ihr Programm auszuführen, navigieren Sie zu einer URL wie `http://localhost:8888/webapp/<publishname>`, wobei `<publishname>` durch den Wert im `<publishname>`-Tag ersetzt wird. Dann wird das durch das `<classname>`-Tag angegebene Programm ausgeführt.

- **`<debug>`** Das Debug-Tag kann auf true oder false gesetzt werden und bestimmt, ob die Konsole des Browsers Fehlermeldungen anzeigt, die von Ihrem Programm ausgegeben werden.

## Ausführen eines bestimmten Programms {#running-a-specific-program}

Es gibt zwei Möglichkeiten, ein bestimmtes Programm in Ihrer App auszuführen:

1. Platzieren Sie das Programm innerhalb der `run()`-Methode der Klasse, die `App` erweitert.
2. Nutzen Sie [Routing](../../routing/overview) in Ihrer webforJ-App, um dem Programm eine dedizierte URL zu geben.

## Wie webforJ einen Einstiegspunkt auswählt {#how-webforj-selects-an-entry-point}

Der Einstiegspunkt für eine App wird durch die im POM-Dokument angegebene `<classname>` bestimmt. 
Wenn im POM-Dokument kein Einstiegspunkt angegeben ist, beginnt das System mit einer Suche nach einem Einstiegspunkt.

### Suche nach dem Einstiegspunkt {#entry-point-search}

1. Wenn es eine einzige Klasse gibt, die die `App`-Klasse erweitert, wird diese der Einstiegspunkt.
2. Wenn mehrere Klassen `App` erweitern, überprüft das System, ob eine die Annotation `com.webforj.annotation.AppEntry` hat. Die einzige Klasse, die mit `@AppEntry` annotiert ist, wird der Einstiegspunkt.
    :::warning
    Wenn mehrere Klassen mit `@AppEntry` annotiert sind, wird eine Ausnahme ausgelöst, die alle entdeckten Klassen auflistet.
    :::

Wenn es mehrere Klassen gibt, die `App` erweitern, und keine von ihnen mit `@AppEntry` annotiert ist, wird eine Ausnahme ausgelöst, die jede Unterklasse detailliert beschreibt.

## Debug-Modus {#debug-mode}

Es ist auch möglich, Ihre App im Debug-Modus auszuführen, der es der Konsole ermöglicht, umfassende Fehlermeldungen auszugeben.

Die erste Option besteht darin, die `config.bbx`-Datei zu ändern, die sich im `cfg/`-Verzeichnis Ihrer BBj-Installation befindet. Fügen Sie die Zeile `SET DEBUG=1` in die Datei ein und speichern Sie Ihre Änderungen.

Zusätzlich können Sie im Enterprise Manager das Folgende als Programmargument hinzufügen: `DEBUG`

Durch das Abschließen einer dieser Optionen kann die Konsolenausgabe des Browsers Fehlermeldungen anzeigen.
