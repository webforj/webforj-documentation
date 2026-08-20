---
title: Prerequisites
description: >-
  What a webforJ development environment needs, a Java 21 or higher JDK, Maven
  or Gradle, and an editor with Java support.
sidebar_position: 1
_i18n_hash: 038e0cf692852d650329b263c25aaf55
---
Der Einstieg in webforJ ist einfach, da es nur ein paar Voraussetzungen gibt. Verwenden Sie diesen Leitfaden, um Ihre Entwicklungsumgebung mit den wesentlichen Werkzeugen einzurichten, die Sie benötigen, um mit webforJ zu beginnen.

## Java Development Kit (JDK) {#java-development-kit-jdk-21}

webforJ benötigt Java **21** oder höher. Jede Distribution dieser Version funktioniert, wählen Sie also diejenige, die Ihr Team bereits verwendet.

:::tip Empfohlen für die Entwicklung
Entwickeln Sie auf einem [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases)-Build. Es akzeptiert die Option `-XX:+AllowEnhancedClassRedefinition`, die es einem [Hotswap-Tool](/docs/configuration/deploy-reload/hotswap) ermöglicht, eine Änderung an der Struktur einer Klasse, ein neues Feld oder eine neue Methode in die laufende Anwendung zu übertragen.

Bei jeder anderen Build werden Änderungen im Methodenkörper vor Ort angewendet, und eine Änderung an der Struktur einer Klasse wartet auf einen Neustart. Die Entscheidung betrifft nur den Computer, auf dem Sie entwickeln, und hat keine Auswirkungen auf das, was Sie paketieren oder wo Sie es bereitstellen.
:::

Ein Versionsmanager ist der einfachste Weg, ein JDK zu installieren, und der einfachste Weg, später zwischen Versionen zu wechseln. [SDKMAN!](https://sdkman.io/) deckt UNIX-Systeme ab, und [Jabba](https://github.com/Jabba-Team/jabba) deckt UNIX-Systeme und Windows ab. Unter SDKMAN! erhalten Sie mit `sdk install java 21.0.11-jbr` eine JetBrains Runtime.

Um ein Build selbst herunterzuladen:

- **Oracle JDK**: die [Java Downloads](https://www.oracle.com/java/technologies/downloads/) -Seite, mit Oracles [Installationsanleitung](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html).
- **Eclipse Temurin**: die [neueste Versionen](https://adoptium.net/temurin/releases/) -Seite, mit Adoptiums [Installationsanleitung](https://adoptium.net/installation/).
- **JetBrains Runtime**: die [Versionshinweise](https://github.com/JetBrains/JetBrainsRuntime/releases) -Seite.

Führen Sie `java -version` aus, um zu bestätigen, welche Version in Ihrem Path ist.

## Build-Tool {#build-tool}

webforJ wird mit Maven oder Gradle gebaut. [Archetypen](/docs/introduction/getting-started) generieren Maven-Projekte, sodass Maven der schnellste Weg zu einer neuen App ist, und ein bestehender Gradle-Build funktioniert auf die gleiche Weise.

<Tabs>
<TabItem value="maven" label="Maven">

Installieren Sie Maven von der [Apache Maven Download-Seite](https://maven.apache.org/download.cgi), indem Sie die [Installationsanweisungen von Maven](https://maven.apache.org/install.html) oder Baeldungs [Leitfaden für jedes Betriebssystem](https://www.baeldung.com/install-maven-on-windows-linux-mac) befolgen.

Führen Sie `mvn -v` aus, um die Installation zu bestätigen.

</TabItem>
<TabItem value="gradle" label="Gradle">

Installieren Sie Gradle, indem Sie Gradles [Installationsanleitung](https://gradle.org/install/) befolgen.

Führen Sie `gradle -v` aus, um die Installation zu bestätigen. Ein Projekt, das einen Gradle-Wrapper bereitstellt, benötigt überhaupt keine Installation, da `./gradlew` die Version abruft, die das Projekt festlegt.

</TabItem>
</Tabs>

Jeder Build führt die Arbeitszeit von webforJ über das [webforJ Build-Plugin](/docs/configuration/build-plugin) aus, das ein aus einem Archetypen erstelltes Projekt bereits hat.

## Editor {#java-ide}

Jeder Editor mit Java-Unterstützung funktioniert, verwenden Sie also denjenigen, der zu Ihrem Workflow passt. Häufige Auswahlmöglichkeiten:

- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Java-Unterstützung und ein Plugin-Ökosystem sofort verfügbar.
- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Ein leichter Editor, der seine Java-Unterstützung aus Erweiterungen bezieht.
- **[Zed](https://zed.dev/download)**: Ein Code-Editor, der Java über eine Erweiterung aufnimmt, die den Eclipse-Java-Sprachserver für Sie herunterlädt und verwaltet.
