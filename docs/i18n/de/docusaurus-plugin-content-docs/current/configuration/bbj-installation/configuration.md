---
title: Plugin installieren
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 1a3e48999554631e4f15a67c80385111
---
Sie können webforJ mit der POM-Datei eines Projekts konfigurieren, die darauf ausgelegt ist, das Bereitstellen einer Anwendung zu erleichtern. Die folgenden Abschnitte skizzieren die verschiedenen Optionen, die Sie ändern können, um ein gewünschtes Ergebnis zu erzielen.

## Engine-Ausschluss {#engine-exclusion}

Beim Ausführen mit `BBjServices` sollte die Abhängigkeit `webforj-engine` ausgeschlossen werden, da die von der Engine bereitgestellten Funktionen bereits verfügbar sind.

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

Tags innerhalb des `<configuration>`-Tags können geändert werden, um Ihre Anwendung zu konfigurieren. Das Bearbeiten der folgenden Zeilen in der Standard-POM-Datei, die mit dem [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) Start-Repository geliefert wird, führt zu diesen Änderungen:

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

- **`<deployurl>`** Dieses Tag ist die URL, unter der der webforJ-Endpunkt für die Projektinstallation erreicht werden kann. Für Benutzer, die ihre Anwendung lokal ausführen, wird ein Standardport von 8888 verwendet. Für Benutzer, die Docker ausführen, sollte der Port auf den Port geändert werden, der bei der [Konfiguration des Docker-Containers](./docker#2-configuration) eingegeben wurde.

- **`<classname>`** Dieses Tag sollte den Paket- und Klassennamen der Anwendung enthalten, die Sie ausführen möchten. Dies wird die einzige Klasse in Ihrem Projekt sein, die die `App`-Klasse erweitert und von der Basis-URL ausgeführt wird.

- **`<publishname>`** Dieses Tag gibt den Namen der Anwendung in der veröffentlichten URL an. Im Allgemeinen müssen Sie, um Ihr Programm auszuführen, zu einer URL navigieren, die ähnlich wie `http://localhost:8888/webapp/<publishname>` aussieht, wobei `<publishname>` mit dem Wert im `<publishname>`-Tag ersetzt wird. Dann wird das im `<classname>`-Tag angegebene Programm ausgeführt.

- **`<debug>`** Das Debug-Tag kann auf true oder false gesetzt werden und bestimmt, ob die Konsole des Browsers Fehlermeldungen anzeigt, die von Ihrem Programm erzeugt werden. 

## Ausführen eines bestimmten Programms {#running-a-specific-program}

Es gibt zwei Möglichkeiten, ein bestimmtes Programm in Ihrer Anwendung auszuführen:

1. Platzieren Sie das Programm innerhalb der `run()`-Methode der Klasse, die `App` erweitert.
2. Verwenden Sie [Routing](../../routing/overview) in Ihrer webforJ-Anwendung, um dem Programm eine dedizierte URL zu geben.

## Wie webforJ einen Einstiegspunkt auswählt {#how-webforj-selects-an-entry-point}

Der Einstiegspunkt für eine Anwendung wird durch die im POM-Dokument angegebene `<classname>` bestimmt. Wenn kein Einstiegspunkt im POM-Dokument angegeben ist, beginnt das System mit einer Suche nach Einstiegspunkten.

### Suche nach Einstiegspunkten {#entry-point-search}

1. Wenn es eine einzelne Klasse gibt, die die `App`-Klasse erweitert, wird diese der Einstiegspunkt.
2. Wenn mehrere Klassen `App` erweitern, überprüft das System, ob eine die Annotation `com.webforj.annotation.AppEntry` hat. Die einzige Klasse, die mit `@AppEntry` annotiert ist, wird der Einstiegspunkt.
    :::warning
    Wenn mehrere Klassen mit `@AppEntry` annotiert sind, wird eine Ausnahme ausgelöst, die alle entdeckten Klassen auflistet.
    :::

Wenn es mehrere Klassen gibt, die `App` erweitern und keine von ihnen mit `@AppEntry` annotiert ist, wird eine Ausnahme ausgelöst, die jede Unterklasse detailliert beschreibt.

## Debug-Modus {#debug-mode}

Es ist auch möglich, Ihre Anwendung im Debug-Modus auszuführen, der es der Konsole ermöglicht, umfassende Fehlermeldungen anzuzeigen.

Die erste Möglichkeit besteht darin, die Datei `config.bbx`, die sich im Verzeichnis `cfg/` Ihrer BBj-Installation befindet, zu ändern. Fügen Sie die Zeile `SET DEBUG=1` zur Datei hinzu und speichern Sie Ihre Änderungen.

Zusätzlich können Sie im Enterprise Manager Folgendes als Programmargument hinzufügen: `DEBUG`

Das Abschließen einer dieser Optionen ermöglicht es der Browser-Konsole, Fehlermeldungen anzuzeigen.
