---
title: Voraussetzungen
sidebar_position: 1
_i18n_hash: 079539f07a72647e2faa9a9a5eda5634
---
Der Einstieg in webforJ ist einfach, da es nur einige Voraussetzungen gibt. Verwenden Sie diesen Leitfaden, um Ihre Entwicklungsumgebung mit den notwendigen Tools einzurichten, die Sie benötigen, um mit webforJ zu starten.

<!-- vale off -->
## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

<!-- vale on -->

Ein Java Development Kit (JDK) ist die wichtigste Voraussetzung für die Entwicklung mit webforJ und bietet die notwendigen Werkzeuge, um Java-Anwendungen zu kompilieren, auszuführen und zu verwalten. 
Java **21** wird benötigt, um die Kompatibilität mit webforJ sicherzustellen und Zugang zu den neuesten Funktionen sowie Sicherheitsupdates des Java-Ökosystems zu erhalten. Das webforJ-Framework ist mit den offiziellen Oracle-JDKs und den Open-Source-Eclipse-Temurin-JDKs kompatibel.
<!-- vale off -->
### JDK-Installationslinks: {#jdk-installation-links}
<!-- vale on -->
:::tip  
Wenn Sie ein UNIX-basiertes Betriebssystem verwenden, wird empfohlen, [SDKMAN!](https://sdkman.io/) zu verwenden, um Ihre Java-Umgebung zu verwalten. Es ermöglicht Ihnen, problemlos zwischen verschiedenen Java-Anbietern zu wechseln, ohne zusätzlichen Aufwand.  

Alternativ können Sie [Jabba](https://github.com/shyiko/jabba) verwenden, das sowohl auf UNIX-basierten Systemen als auch auf Windows funktioniert. Es ist eine solide plattformübergreifende Lösung zur Verwaltung von Java-Versionen.  
:::

- Offizielle Oracle-JDKs finden Sie auf der [Java Downloads](https://www.oracle.com/java/technologies/downloads/) Seite von Oracle. 
  - Wählen Sie die Java-Version **21**.
  - Klicken Sie auf die Registerkarte für Linux, macOS oder Windows.
  - Klicken Sie auf den Link, der Ihrer Computerarchitektur entspricht. 
  - Siehe Oracles [JDK-Installationsanleitung](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) für vollständige Informationen zur Installation eines Oracle JDK.
- Open-Source-JDKs finden Sie auf der [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/) Seite von Adoptium. 
  - Verwenden Sie die Dropdown-Menüs, um das Betriebssystem, die Architektur, den Pakettyp und die JDK-Version **21** auszuwählen. 
  - Klicken Sie auf den Link in der Tabelle für den Archivtyp, den Sie herunterladen möchten.
  - Siehe Adoptiums [Installationsanleitung](https://adoptium.net/installation/) für vollständige Informationen zur Installation eines Eclipse Temurin JDK.

<!-- vale off -->
### Überprüfen Sie Ihre JDK-Installation {#verify-your-jdk-installation}
<!-- vale on -->
Nach der Installation des JDKs überprüfen Sie die Installation, indem Sie den folgenden Befehl in Ihrem Terminal oder Ihrer Eingabeaufforderung ausführen:

```bash
java -version
```

Wenn Ihr JDK korrekt installiert ist, sehen Sie eine Ausgabe mit den Details Ihrer JDK-Version, die die Version **21** angibt.
<!-- vale off -->
## Apache Maven {#apache-maven}
<!-- vale on -->

[Apache Maven](https://maven.apache.org/index.html) ist ein Tool zur Automatisierung des Builds und zur Verwaltung von Abhängigkeiten, das den Prozess der Einbindung von externen Bibliotheken wie webforJ in Ihr Projekt vereinfacht. 
Neben der Unterstützung bei der Verwaltung von Abhängigkeiten kann Maven Aufgaben wie das Kompilieren von Code, das Ausführen von Tests und das Paketieren von Anwendungen automatisieren.

### Maven-Installationslinks {#maven-installation-links}
- Um die neueste Version von Maven zu installieren, besuchen Sie die [Apache Maven Download Page](https://maven.apache.org/download.cgi). 
  - Die Seite [Installing Apache Maven](https://maven.apache.org/install.html) bietet einen Überblick über den Installationsprozess. 
  - Baeldungs [How to Install Maven on Windows, Linux, and Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) ist eine ausführlichere Installationsanleitung für jedes Betriebssystem.

<!-- vale off -->
### Überprüfen Sie Ihre Maven-Installation {#verify-your-maven-installation}

<!-- vale on -->

Nachdem Sie Maven installiert haben, überprüfen Sie die Installation, indem Sie den folgenden Befehl in Ihrem Terminal oder Ihrer Eingabeaufforderung ausführen:

```bash
mvn -v
```

Wenn Maven korrekt installiert ist, sollte die Ausgabe die Maven-Version, die Java-Version und Informationen zum Betriebssystem anzeigen.

## Java IDE {#java-ide}

Eine Java IDE bietet eine umfassende Umgebung zum Schreiben, Testen und Debuggen Ihres Codes. Es gibt viele IDEs zur Auswahl, sodass Sie die auswählen können, die am besten zu Ihrem Workflow passt. Einige beliebte Optionen für die Java-Entwicklung sind:

- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Ein leichter, erweiterbarer Code-Editor mit Java-Unterstützung über Plugins.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Bekannt für seine leistungsstarke Java-Unterstützung und sein reichhaltiges Plugin-Ökosystem.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: Eine kostenlose, Open-Source-IDE für Java und andere Sprachen, die für ihre Benutzerfreundlichkeit und die integrierten Projektvorlagen bekannt ist.
