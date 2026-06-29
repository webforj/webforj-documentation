---
title: Prerequisites
description: >-
  Set up a webforJ development environment with JDK 21, Apache Maven, and a
  supported Java IDE such as IntelliJ, VS Code, or NetBeans.
sidebar_position: 1
_i18n_hash: d5a639b85898cdb73710fdbbd8ff8033
---
Der Einstieg in webforJ ist einfach, da es nur einige wenige Voraussetzungen gibt. Verwenden Sie diesen Leitfaden, um Ihre Entwicklungsumgebung mit den wesentlichen Tools einzurichten, die Sie benötigen, um mit webforJ zu starten.

<!-- vale off -->
## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

<!-- vale on -->

Ein Java Development Kit (JDK) ist die wichtigste Voraussetzung für die Entwicklung mit webforJ, da es die notwendigen Werkzeuge zum Kompilieren, Ausführen und Verwalten von Java-Anwendungen bereitstellt.
Java **21** ist erforderlich, um die Kompatibilität mit webforJ sicherzustellen und Zugang zu den neuesten Funktionen und Sicherheitsupdates des Java-Ökosystems zu erhalten. Das webforJ-Framework ist mit offiziellen Oracle JDKs und den Open-Source Eclipse Temurin JDKs kompatibel.
<!-- vale off -->
### JDK-Installationslinks: {#jdk-installation-links}
<!-- vale on -->
:::tip  
Wenn Sie ein UNIX-basiertes Betriebssystem verwenden, wird empfohlen, [SDKMAN!](https://sdkman.io/) zur Verwaltung Ihrer Java-Umgebung zu nutzen. Es ermöglicht Ihnen, einfach zwischen verschiedenen Java-Anbietern zu wechseln, ohne zusätzlichen Aufwand.  

Alternativ können Sie [Jabba](https://github.com/Jabba-Team/jabba) verwenden, das sowohl auf UNIX-basierten Systemen als auch auf Windows funktioniert. Es ist eine solide plattformübergreifende Lösung zur Verwaltung von Java-Versionen.  
:::

- Offizielle Oracle JDKs finden Sie auf der Oracle-Website unter den [Java Downloads](https://www.oracle.com/java/technologies/downloads/). 
  - Wählen Sie die Java-Version **21** aus.
  - Klicken Sie auf die Registerkarte für Linux, macOS oder Windows.
  - Klicken Sie auf den Link, der Ihrer Computerarchitektur entspricht. 
  - Siehe Oracle's [JDK Installationsanleitung](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) für vollständige Informationen zur Installation eines Oracle JDK.
- Open-Source-JDKs finden Sie auf der Adoptium-Website unter den [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/). 
  - Verwenden Sie die Dropdown-Menüs, um das Betriebssystem, die Architektur, den Pakettyp und die JDK-Version **21** auszuwählen. 
  - Klicken Sie auf den Link in der Tabelle für den Archivtyp, den Sie herunterladen möchten.
  - Siehe Adoptium's [Installationsanleitung](https://adoptium.net/installation/) für vollständige Informationen zur Installation eines Eclipse Temurin JDK.

<!-- vale off -->
### Überprüfen Sie Ihre JDK-Installation {#verify-your-jdk-installation}
<!-- vale on -->
Nachdem Sie das JDK installiert haben, überprüfen Sie die Installation, indem Sie den folgenden Befehl in Ihrem Terminal oder Eingabeaufforderung ausführen:

```bash
java -version
```

Wenn Ihr JDK korrekt installiert ist, sehen Sie eine Ausgabe mit den Details Ihrer JDK-Version, die auf Version **21** hinweist.
<!-- vale off -->
## Apache Maven {#apache-maven}
<!-- vale on -->

[Apache Maven](https://maven.apache.org/index.html) ist ein Werkzeug zur Automatisierung von Builds und zur Verwaltung von Abhängigkeiten, das den Prozess der Einbindung externer Bibliotheken wie webforJ in Ihr Projekt vereinfacht. 
Neben der Hilfe bei der Verwaltung von Abhängigkeiten kann Maven Aufgaben wie das Kompilieren von Code, das Ausführen von Tests und das Verpacken von Anwendungen automatisieren.

### Maven-Installationslinks {#maven-installation-links}
- Um die neueste Version von Maven zu installieren, gehen Sie zur [Apache Maven Download-Seite](https://maven.apache.org/download.cgi). 
  - Die Seite [Installing Apache Maven](https://maven.apache.org/install.html) bietet einen Überblick über den Installationsprozess. 
  - Baeldungs [Wie man Maven auf Windows, Linux und Mac installiert](https://www.baeldung.com/install-maven-on-windows-linux-mac) ist ein ausführlicher Leitfaden zur Installation für jedes Betriebssystem.

<!-- vale off -->
### Überprüfen Sie Ihre Maven-Installation {#verify-your-maven-installation}

<!-- vale on -->

Nachdem Sie Maven installiert haben, überprüfen Sie die Installation, indem Sie den folgenden Befehl in Ihrem Terminal oder Eingabeaufforderung ausführen:

```bash
mvn -v
```

Wenn Maven korrekt installiert ist, sollte die Ausgabe die Maven-Version, die Java-Version und die Betriebssysteminformationen anzeigen.

## Java IDE {#java-ide}

Eine Java IDE bietet eine umfassende Umgebung zum Schreiben, Testen und Debuggen Ihres Codes. Es gibt viele IDEs zur Auswahl, sodass Sie diejenige wählen können, die am besten zu Ihrem Arbeitsablauf passt. Einige beliebte Optionen für die Java-Entwicklung sind:

- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Ein leichtgewichtiger, erweiterbarer Code-Editor mit Java-Unterstützung über Plugins.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Bekannt für seine umfangreiche Java-Unterstützung und das reiche Plugin-Ökosystem.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: Eine kostenlose, Open-Source-IDE für Java und andere Sprachen, die für ihre Benutzerfreundlichkeit und die integrierten Projektvorlagen bekannt ist.
