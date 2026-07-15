---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
description: >-
  Configure the webforJ install Maven plugin with deploy URL, class name,
  publish name, and debug flags for BBjServices deployments.
_i18n_hash: b01357f571ce256abb8b390cebdbf5cc
---
Je kunt webforJ configureren met behulp van het POM-bestand van een project, dat is ontworpen om het implementeren van een app eenvoudig te maken. De volgende secties schetsen de verschillende opties die je kunt aanpassen om het gewenste resultaat te bereiken.

## Uitsluiting van de engine {#engine-exclusion}

Bij het draaien met `BBjServices` moet de afhankelijkheid `webforj-engine` worden uitgesloten, aangezien de functies die door de engine worden geboden al beschikbaar zijn.

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

## POM-bestandstags {#pom-file-tags}

Tags binnen de `<configuration>` tag kunnen worden gewijzigd om je app te configureren. Het bewerken van de volgende regels in het standaard POM-bestand dat wordt geleverd met de [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) startrepository zal resulteren in deze wijzigingen:

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

- **`<deployurl>`** Deze tag is de URL waar het webforJ-eindpunt voor de projectinstallatie kan worden bereikt. Voor gebruikers die hun app lokaal uitvoeren, wordt een standaardpoort van 8888 gebruikt. Voor gebruikers die Docker gebruiken, moet de poort worden gewijzigd naar de poort die is ingevoerd bij het [configureren van de Docker-container](./docker#2-configuration).

- **`<classname>`** Deze tag moet de package- en klassennaam van de app bevatten die je wilt uitvoeren. Dit zal de enige klasse in je project zijn die de `App`-klasse uitbreidt en draait vanaf de basis-URL.

- **`<publishname>`** Deze tag specificeert de naam van de app in de gepubliceerde URL. Over het algemeen, om je programma uit te voeren, navigeer je naar een URL die lijkt op `http://localhost:8888/webapp/<publishname>`, waarbij `<publishname>` wordt vervangen door de waarde in de `<publishname>` tag. Vervolgens wordt het programma dat is gespecificeerd door de `<classname>` tag uitgevoerd.

- **`<debug>`** De debug-tag kan worden ingesteld op waar of niet waar, en bepaalt of de console van de browser foutmeldingen weergeeft die door je programma zijn gegooid.

## Een specifiek programma uitvoeren {#running-a-specific-program}

Er zijn twee manieren om een specifiek programma in je app uit te voeren:

1. Plaats het programma binnen de `run()`-methode van de klasse die `App` uitbreidt.
2. Gebruik [routering](../../routing/overview) in je webforJ-app om het programma een specifieke URL te geven.

## Hoe webforJ een toegangspunt selecteert {#how-webforj-selects-an-entry-point}

Het toegangspunt voor een app wordt bepaald door de `<classname>` die in het POM-bestand is gespecificeerd. Als er geen toegangspunt in het POM-bestand is gespecificeerd, begint het systeem met het zoeken naar een toegangspunt.

### Zoektocht naar een toegangspunt {#entry-point-search}

1. Als er een enkele klasse is die de `App`-klasse uitbreidt, wordt dat het toegangspunt.
2. Als meerdere klassen `App` uitbreiden, controleert het systeem of een van hen de annotatie `com.webforj.annotation.AppEntry` heeft. De enige klasse die is geannoteerd met `@AppEntry` zal het toegangspunt worden.
    :::warning
    Als meerdere klassen geannoteerd zijn met `@AppEntry`, wordt er een uitzondering gegooid, waarin alle ontdekte klassen worden vermeld.
    :::

Als er meerdere klassen zijn die `App` uitbreiden en geen van hen zijn geannoteerd met `@AppEntry`, wordt er een uitzondering gegooid, waarin elke subclass wordt gedetailleerd.

## Debugmodus {#debug-mode}

Het is ook mogelijk om je app in debugmodus uit te voeren, waarmee de console uitgebreide foutmeldingen kan afdrukken.

De eerste optie is om het `config.bbx`-bestand te wijzigen, dat zich bevindt in de `cfg/`-directory van je BBj-installatie. Voeg de regel `SET DEBUG=1` toe aan het bestand en sla je wijzigingen op.

Bovendien kun je in de Enterprise Manager het volgende toevoegen als programmaargument: `DEBUG`

Het voltooien van een van deze opties stelt de browserconsole in staat om foutmeldingen weer te geven.
