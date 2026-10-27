---
title: Plugin installieren
sidebar_position: 3
displayed_sidebar: documentationSidebar
description: >-
  Configure the webforJ install Maven plugin with deploy URL, class name,
  publish name, and debug flags for BBjServices deployments.
_i18n_hash: 1019285125db3439621951fe22047837
---
:::danger Abgelehnt und wird entfernt
Das webforJ Installationsplugin ist veraltet und wird in einer zukünftigen Veröffentlichung entfernt. Beginnen Sie keine neuen Projekte damit. Verschieben Sie bestehende Projekte zu [BBjShip](/docs/configuration/bbjship/overview), das webforJ-Apps in BBjServices installiert, bereitstellt und ausführt. [Bestehende Projekte](/docs/configuration/bbjship/existing-projects) listet die Änderungen auf, die ein Projekt benötigt.
:::

Sie können webforJ mit der POM-Datei eines Projekts konfigurieren, die so gestaltet ist, dass die Bereitstellung einer App einfach ist. Die folgenden Abschnitte umreißen die verschiedenen Optionen, die Sie ändern können, um ein gewünschtes Ergebnis zu erzielen.

## Ausschluss des Engines {#engine-exclusion}

Beim Ausführen mit `BBjServices` sollte die `webforj-engine` Abhängigkeit ausgeschlossen werden, da die von der Engine bereitgestellten Funktionen bereits verfügbar sind.

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

Tags innerhalb des `<configuration>` Tags können geändert werden, um Ihre App zu konfigurieren. Durch Bearbeiten der folgenden Zeilen in der standardmäßigen POM-Datei, die mit dem [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) Start-Repository geliefert wird, ergeben sich diese Änderungen:

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

- **`<deployurl>`** Dieses Tag ist die URL, unter der der webforJ-Endpunkt für die Projektinstallation erreicht werden kann. Für Benutzer, die ihre App lokal ausführen, wird ein Standardport von 8888 verwendet. Für Benutzer, die Docker ausführen, sollte der Port auf den Port geändert werden, der bei [der Konfiguration des Docker-Containers](./docker#2-configuration) eingegeben wurde.

- **`<classname>`** Dieses Tag sollte den Paket- und Klassennamen der App enthalten, die Sie ausführen möchten. Dies wird die einzige Klasse in Ihrem Projekt sein, die die `App` Klasse erweitert und von der Basis-URL ausläuft.

- **`<publishname>`** Dieses Tag gibt den Namen der App in der veröffentlichten URL an. Um Ihr Programm auszuführen, navigieren Sie in der Regel zu einer URL wie `http://localhost:8888/webapp/<publishname>`, wobei `<publishname>` den Wert im `<publishname>` Tag ersetzt. Dann wird das durch das `<classname>` Tag angegebene Programm ausgeführt.

- **`<debug>`** Das Debug-Tag kann auf true oder false gesetzt werden und bestimmt, ob die Konsole des Browsers Fehlermeldungen anzeigt, die von Ihrem Programm ausgegeben werden.

## Ausführen eines spezifischen Programms {#running-a-specific-program}

Es gibt zwei Möglichkeiten, ein bestimmtes Programm in Ihrer App auszuführen:

1. Platzieren Sie das Programm innerhalb der `run()` Methode der Klasse, die `App` erweitert.
2. Nutzen Sie [Routing](../../routing/overview) in Ihrer webforJ-App, um dem Programm eine dedizierte URL zu geben.

## Wie webforJ einen Einstiegspunkt auswählt {#how-webforj-selects-an-entry-point}

Der Einstiegspunkt für eine App wird durch das in der POM-Datei angegebene `<classname>` bestimmt. Wenn in der POM-Datei kein Einstiegspunkt angegeben ist, beginnt das System mit einer Suche nach einem Einstiegspunkt.

### Einstiegspunkt-Suche {#entry-point-search}

1. Wenn es eine einzige Klasse gibt, die die `App` Klasse erweitert, wird diese der Einstiegspunkt.
2. Wenn mehrere Klassen `App` erweitern, prüft das System, ob eine die `com.webforj.annotation.AppEntry` Annotation hat. Die einzelne Klasse, die mit `@AppEntry` annotiert ist, wird der Einstiegspunkt.
    :::warning
    Wenn mehrere Klassen mit `@AppEntry` annotiert sind, wird eine Ausnahme ausgelöst, die alle entdeckten Klassen auflistet.
    :::

Wenn es mehrere Klassen gibt, die `App` erweitern und keine von ihnen mit `@AppEntry` annotiert sind, wird eine Ausnahme ausgelöst, die jede Unterklasse detailliert beschreibt.

## Debug-Modus {#debug-mode}

Es ist auch möglich, Ihre App im Debug-Modus auszuführen, der es der Konsole ermöglicht, umfassende Fehlermeldungen anzuzeigen.

Die erste Möglichkeit besteht darin, die `config.bbx` Datei im `cfg/` Verzeichnis Ihrer BBj-Installation zu ändern. Fügen Sie die Zeile `SET DEBUG=1` in die Datei ein und speichern Sie Ihre Änderungen.

Außerdem können Sie im Enterprise Manager folgendes als Programmargument hinzufügen: `DEBUG`

Wenn eine dieser Optionen abgeschlossen ist, kann die Konsole des Browsers Fehlermeldungen drucken.
