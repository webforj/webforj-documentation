---
title: Prerequisites
description: >-
  Set up a webforJ development environment with JDK 21, Apache Maven, and a
  supported Java IDE such as IntelliJ, VS Code, or NetBeans.
sidebar_position: 1
_i18n_hash: 03fdfcdc58e52eabd51a8f9dbda568e6
---
Der Einstieg in webforJ ist einfach, da es nur einige Voraussetzungen gibt. Verwenden Sie diesen Leitfaden, um Ihre Entwicklungsumgebung mit den erforderlichen Tools einzurichten, die Sie benötigen, um mit webforJ zu starten.

## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

Ein Java Development Kit (JDK) ist die wichtigste Voraussetzung für die Entwicklung mit webforJ, da es die notwendigen Tools bereitstellt, um Java-Apps zu erstellen, auszuführen und zu verwalten. Java **21** ist erforderlich, um die Kompatibilität mit webforJ sicherzustellen und Zugang zu den neuesten Funktionen und Sicherheitsupdates des Java-Ökosystems zu erhalten. Das webforJ-Framework ist mit den offiziellen Oracle JDKs und den Open-Source Eclipse Temurin JDKs kompatibel.

### JDK-Installationslinks: {#jdk-installation-links}
:::tip
Wenn Sie ein UNIX-basiertes Betriebssystem verwenden, wird empfohlen, [SDKMAN!](https://sdkman.io/) zu verwenden, um Ihre Java-Umgebung zu verwalten. Es ermöglicht Ihnen, problemlos zwischen verschiedenen Java-Anbietern zu wechseln.

Alternativ können Sie [Jabba](https://github.com/Jabba-Team/jabba) verwenden, das sowohl auf UNIX-basierten Systemen als auch auf Windows funktioniert. Es ist eine solide plattformübergreifende Lösung zur Verwaltung von Java-Versionen.
:::

- Offizielle Oracle JDKs finden Sie auf der [Java Downloads](https://www.oracle.com/java/technologies/downloads/) Seite von Oracle.
  - Wählen Sie die Java-Version **21** aus.
  - Klicken Sie auf den Tab für Linux, macOS oder Windows.
  - Klicken Sie auf den Link, der zu der Architektur Ihres Computers passt.
  - Siehe die [JDK-Installationsanleitung](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) von Oracle für vollständige Informationen zur Installation eines Oracle JDK.
- Open-Source JDKs finden Sie auf der [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/) Seite von Adoptium.
  - Verwenden Sie die Dropdown-Menüs, um das Betriebssystem, die Architektur, den Pakettyp und die JDK-Version **21** auszuwählen.
  - Klicken Sie auf den Link in der Tabelle für den Archivtyp, den Sie herunterladen möchten.
  - Siehe die [Installationsanleitung](https://adoptium.net/installation/) von Adoptium für vollständige Informationen zur Installation eines Eclipse Temurin JDK.

### Überprüfen Sie Ihre JDK-Installation {#verify-your-jdk-installation}
Nachdem Sie das JDK installiert haben, überprüfen Sie die Installation, indem Sie den folgenden Befehl in Ihrem Terminal oder in der Eingabeaufforderung ausführen:

```bash
java -version
```

Wenn Ihr JDK korrekt installiert ist, sehen Sie eine Ausgabe mit den Einzelheiten zu Ihrer JDK-Version, die die Version **21** angibt.

## Apache Maven {#apache-maven}

[Apache Maven](https://maven.apache.org/index.html) ist ein Buildautomatisierungs- und Abhängigkeitsmanagement-Tool, das den Prozess der Einbindung externer Bibliotheken wie webforJ in Ihr Projekt vereinfacht. Neben der Unterstützung beim Abhängigkeitsmanagement kann Maven Aufgaben wie das Kompilieren von Code, das Ausführen von Tests und das Verpacken von Anwendungen automatisieren.

### Maven-Installationslinks {#maven-installation-links}
- Um die neueste Version von Maven zu installieren, gehen Sie zur [Apache Maven Download Page](https://maven.apache.org/download.cgi).
  - Die Seite [Installing Apache Maven](https://maven.apache.org/install.html) von Maven bietet einen Überblick über den Installationsprozess.
  - Baeldungs [Wie man Maven auf Windows, Linux und Mac installiert](https://www.baeldung.com/install-maven-on-windows-linux-mac) ist ein ausführlicherer Installationsleitfaden für jedes Betriebssystem.

### Überprüfen Sie Ihre Maven-Installation {#verify-your-maven-installation}

Nachdem Sie Maven installiert haben, überprüfen Sie die Installation, indem Sie den folgenden Befehl in Ihrem Terminal oder in der Eingabeaufforderung ausführen:

```bash
mvn -v
```

Wenn Maven korrekt installiert ist, sollte die Ausgabe die Maven-Version, die Java-Version und Informationen zum Betriebssystem anzeigen.

## Java IDE {#java-ide}

Eine Java-IDE bietet eine umfassende Umgebung zum Schreiben, Testen und Debuggen Ihres Codes. Es gibt viele IDEs zur Auswahl, sodass Sie die wählen können, die am besten zu Ihrem Workflow passt. Einige beliebte Optionen für die Java-Entwicklung sind:

- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Ein leichter, erweiterbarer Code-Editor mit Java-Unterstützung über Plugins.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Bekannt für seine leistungsstarke Java-Unterstützung und sein reichhaltiges Plugin-Ökosystem.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: Eine kostenlose, Open-Source-IDE für Java und andere Sprachen, bekannt für ihre Benutzerfreundlichkeit und integrierten Projektvorlagen.
