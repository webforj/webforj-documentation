---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 3f3e4285abb3b23f9427cdd7b9baa282
---
Je kunt webforJ configureren met behulp van het POM-bestand van een project, dat is ontworpen om het deployen van een app eenvoudig te maken. De volgende secties schetsen de verschillende opties die je kunt wijzigen om het gewenste resultaat te bereiken.

## Uitsluiting van de engine {#engine-exclusion}

Wanneer je met `BBjServices` draait, moet de `webforj-engine` afhankelijkheid worden uitgesloten, aangezien de functies die door de engine worden aangeboden al beschikbaar zijn.

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

Tags binnen de `<configuration>` tag kunnen worden gewijzigd om je app te configureren. Het bewerken van de volgende regels in het standaard POM-bestand dat wordt geleverd met de [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) startrepository zal leiden tot deze wijzigingen:

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

- **`<deployurl>`** Deze tag is de URL waar het webforJ-eindpunt voor de projectinstallatie kan worden bereikt. Voor gebruikers die hun app lokaal draaien, wordt een standaardpoort van 8888 gebruikt. Voor gebruikers die Docker draaien, moet de poort worden gewijzigd naar de poort die is ingevoerd bij het [configureren van de Docker-container](./docker#2-configuration).

- **`<classname>`** Deze tag moet de pakket- en klassenaam van de app bevatten die je wilt uitvoeren. Dit zal de enige klasse in je project zijn die de `App` klasse uitbreidt en uitvoert vanaf de basis-URL.

- **`<publishname>`** Deze tag specificeert de naam van de app in de gepubliceerde URL. Over het algemeen, om je programma uit te voeren, navigeer je naar een URL die lijkt op `http://localhost:8888/webapp/<publishname>`, waarbij je `<publishname>` vervangt door de waarde in de `<publishname>` tag. Vervolgens wordt het programma dat is gespecificeerd door de `<classname>` tag uitgevoerd.

- **`<debug>`** De debug-tag kan worden ingesteld op true of false, en bepaalt of de console van de browser foutmeldingen die door je programma worden gegenereerd, weergeeft. 

## Een specifiek programma uitvoeren {#running-a-specific-program}

Er zijn twee manieren om een specifiek programma in je app uit te voeren:

1. Plaats het programma binnen de `run()`-methode van de klasse die `App` uitbreidt.
2. Maak gebruik van [routering](../../routing/overview) in je webforJ-app om het programma een specifieke URL te geven.

## Hoe webforJ een ingangspunt selecteert {#how-webforj-selects-an-entry-point}

Het ingangspunt voor een app wordt bepaald door de `<classname>` die in het POM-bestand is gespecificeerd. 
Als er geen ingangspunt in het POM-bestand is gespecificeerd, zal het systeem beginnen met het zoeken naar een ingangspunt.

### Zoektocht naar een ingangspunt {#entry-point-search}

1. Als er een enkele klasse is die de `App` klasse uitbreidt, wordt dat het ingangspunt.
2. Als meerdere klassen `App` uitbreiden, controleert het systeem of een van hen de annotatie `com.webforj.annotation.AppEntry` heeft. De enige klasse die is geannoteerd met `@AppEntry` wordt het ingangspunt.
    :::warning
    Als meerdere klassen zijn geannoteerd met `@AppEntry`, wordt er een uitzondering gegooid die alle ontdekte klassen opsomt.
    :::

Als er meerdere klassen zijn die `App` uitbreiden en geen van hen is geannoteerd met `@AppEntry`, wordt er een uitzondering gegooid met details over elke subclass.

## Debugmodus {#debug-mode}

Het is ook mogelijk om je app in debugmodus uit te voeren, wat de console in staat stelt uitgebreide foutmeldingen af te drukken.

De eerste optie is om het `config.bbx` bestand te wijzigen, dat zich in de `cfg/` directory van je BBj-installatie bevindt. Voeg de regel `SET DEBUG=1` aan het bestand toe en sla je wijzigingen op.

Daarnaast kun je in de Enterprise Manager het volgende toevoegen als een programma-argument: `DEBUG`

Het voltooien van een van deze opties stelt de browserconsole in staat om foutmeldingen weer te geven.
