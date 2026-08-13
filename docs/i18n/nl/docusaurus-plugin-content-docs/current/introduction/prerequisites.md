---
title: Prerequisites
description: >-
  What a webforJ development environment needs, a Java 21 or higher JDK, Maven
  or Gradle, and an editor with Java support.
sidebar_position: 1
_i18n_hash: 038e0cf692852d650329b263c25aaf55
---
Aan de slag met webforJ is eenvoudig, omdat er maar een paar vereisten zijn. Gebruik deze gids om je ontwikkelomgeving in te stellen met de essentiële tools die je nodig hebt om aan de slag te gaan met webforJ.

## Java Development Kit (JDK) {#java-development-kit-jdk-21}

webforJ vereist Java **21** of hoger. Elke distributie van die versie werkt, dus kies degene die je team al gebruikt.

:::tip Aanbevolen voor ontwikkeling
Ontwikkel op een [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) build. Het accepteert de `-XX:+AllowEnhancedClassRedefinition` optie, wat het mogelijk maakt dat een [hotswap tool](/docs/configuration/deploy-reload/hotswap) een wijziging in de structuur van een klasse, een nieuw veld of een nieuwe methode, in de draaiende app kan aanbrengen.

Bij elke andere build gelden bewerkingen binnen een method body nog steeds in de plaats, en een wijziging in de structuur van een klasse wacht op een herstart. De keuze betreft alleen de machine waarop je ontwikkelt en heeft geen invloed op wat je verpakt of waar je het implementeert.
:::

Een versiebeheerder is de eenvoudigste manier om een JDK te installeren en de eenvoudigste manier om later tussen versies te schakelen. [SDKMAN!](https://sdkman.io/) dekt UNIX-systemen, en [Jabba](https://github.com/Jabba-Team/jabba) dekt UNIX-systemen en Windows. Onder SDKMAN! kun je met `sdk install java 21.0.11-jbr` een JetBrains Runtime verkrijgen.

Als je zelf een build wilt downloaden:

- **Oracle JDK**: de [Java Downloads](https://www.oracle.com/java/technologies/downloads/) pagina, met Oracle's [installatiehandleiding](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html).
- **Eclipse Temurin**: de [laatste releases](https://adoptium.net/temurin/releases/) pagina, met Adoptium's [installatiehandleiding](https://adoptium.net/installation/).
- **JetBrains Runtime**: de [releases](https://github.com/JetBrains/JetBrainsRuntime/releases) pagina.

Voer `java -version` uit om te bevestigen welke versie op je pad staat.

## Build tool {#build-tool}

webforJ bouwt met Maven of Gradle. [Archetypes](/docs/introduction/getting-started) genereren Maven-projecten, dus Maven is de snelste manier om een nieuwe app te maken, en een bestaande Gradle-build werkt op dezelfde manier.

<Tabs>
<TabItem value="maven" label="Maven">

Installeer Maven vanaf de [Apache Maven downloadpagina](https://maven.apache.org/download.cgi), volgens de [installatie-instructies van Maven](https://maven.apache.org/install.html) of Baeldung's [gids voor elk besturingssysteem](https://www.baeldung.com/install-maven-on-windows-linux-mac).

Voer `mvn -v` uit om de installatie te bevestigen.

</TabItem>
<TabItem value="gradle" label="Gradle">

Installeer Gradle door de [installatiehandleiding van Gradle](https://gradle.org/install/) te volgen.

Voer `gradle -v` uit om de installatie te bevestigen. Een project dat een Gradle-wrapper meegeleverd, vereist helemaal geen installatie, aangezien `./gradlew` de versie ophaalt die het project koppelt.

</TabItem>
</Tabs>

Elke build voert de bouwtijdwerkzaamheden van webforJ uit via de [webforJ build plugin](/docs/configuration/build-plugin), die een project dat is gemaakt vanuit een archetype al heeft.

## Editor {#java-ide}

Elke editor met Java-ondersteuning werkt, dus gebruik degene die het beste bij jouw workflow past. Veelvoorkomende keuzes:

- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Java-ondersteuning en een plugin-ecosysteem out of the box.
- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Een lichtgewicht editor die zijn Java-ondersteuning uit extensies haalt.
- **[Zed](https://zed.dev/download)**: Een code-editor die Java oppikt via een extensie, die de Eclipse Java-taalserver voor jou downloadt en beheert.
