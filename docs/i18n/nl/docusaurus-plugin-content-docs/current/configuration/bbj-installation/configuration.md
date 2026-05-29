---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: f6ca2e9ca82e9592c4e0c8b7726164ce
---
Je kunt webforJ configureren met behulp van een POM-bestand van een project, dat is ontworpen om het implementeren van een app eenvoudig te maken. De volgende secties schetsen de verschillende opties die je kunt wijzigen om het gewenste resultaat te bereiken.

## Uitzondering voor de engine {#engine-exclusion}

Bij het draaien met `BBjServices`, moet de `webforj-engine` afhankelijkheid worden uitgesloten, aangezien de functies die door de engine worden geboden al beschikbaar zijn.

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

## POM-bestandstag {#pom-file-tags}

Tags binnen de `<configuration>` tag kunnen worden gewijzigd om je app te configureren. Het bewerken van de volgende regels in het standaard POM-bestand dat bij het [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) startrepository wordt geleverd, zal resulteren in deze wijzigingen:

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

- **`<deployurl>`** Deze tag is de URL waar de webforJ-eindpunt voor de projectinstallatie te bereiken is. Voor gebruikers die hun app lokaal draaien, wordt een standaardpoort van 8888 gebruikt. Voor gebruikers die Docker draaien, moet de poort worden gewijzigd naar de poort die is ingevoerd tijdens het [configureren van de Docker-container](./docker#2-configuration).

- **`<classname>`** Deze tag moet de pakket- en klassennaam van de app bevatten die je wilt uitvoeren. Dit zal de enige klasse in je project zijn die de `App` klasse uitbreidt en draait vanaf de basis-URL.

- **`<publishname>`** Deze tag specificeert de naam van de app in de gepubliceerde URL. Over het algemeen, om je programma uit te voeren, navigeer je naar een URL die lijkt op `http://localhost:8888/webapp/<publishname>`, waarbij `<publishname>` wordt vervangen door de waarde in de `<publishname>` tag. Vervolgens wordt het programma dat in de `<classname>` tag is gespecificeerd, uitgevoerd.

- **`<debug>`** De debug-tag kan worden ingesteld op waar of onwaar en bepaalt of de console van de browser foutmeldingen toont die door je programma worden gegooid.

## Een specifiek programma uitvoeren {#running-a-specific-program}

Er zijn twee manieren om een specifiek programma in je app uit te voeren:

1. Plaats het programma binnen de `run()` methode van de klasse die `App` uitbreidt.
2. Gebruik [routering](../../routing/overview) in je webforJ-app om het programma een speciale URL te geven.

## Hoe webforJ een toegangspunt selecteert {#how-webforj-selects-an-entry-point}

Het toegangspunt voor een app wordt bepaald door de `<classname>` die in het POM-bestand is gespecificeerd. Als er geen toegangspunt in het POM-bestand is opgegeven, begint het systeem met het zoeken naar een toegangspunt.

### Toegangspunt zoeken {#entry-point-search}

1. Als er een enkele klasse is die de `App` klasse uitbreidt, wordt dat de toegangspunt.
2. Als meerdere klassen `App` uitbreiden, controleert het systeem of één de annotatie `com.webforj.annotation.AppEntry` heeft. De enige klasse die is geannoteerd met `@AppEntry` wordt de toegangspunt.
    :::warning
    Als meerdere klassen zijn geannoteerd met `@AppEntry`, wordt er een uitzonderingsfout gegenereerd, waarin alle ontdekte klassen worden vermeld.
    :::

Als er meerdere klassen zijn die `App` uitbreiden en geen van hen is geannoteerd met `@AppEntry`, wordt er een uitzondering gegooid met details van elke subclass.

## Debug-modus {#debug-mode}

Het is ook mogelijk om je app in debug-modus te draaien, waardoor de console uitgebreide foutmeldingen kan afdrukken.

De eerste optie is om het `config.bbx` bestand te wijzigen, dat zich in de `cfg/` directory van je BBj-installatie bevindt. Voeg de regel `SET DEBUG=1` aan het bestand toe en sla je wijzigingen op.

Bovendien kun je in de Enterprise Manager het volgende als programmacommentaar toevoegen: `DEBUG`

Een van deze opties maakt het mogelijk dat de console van de browser foutmeldingen afdrukt.
