---
title: Plugin Installeren
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 1a3e48999554631e4f15a67c80385111
---
Je kunt webforJ configureren met het POM-bestand van een project, dat is ontworpen om het implementeren van een app eenvoudig te maken. De volgende secties geven een overzicht van de verschillende opties die je kunt wijzigen om het gewenste resultaat te bereiken.

## Engine uitsluiting {#engine-exclusion}

Wanneer je draait met `BBjServices`, moet de `webforj-engine` afhankelijkheid worden uitgesloten, aangezien de functies die door de engine worden geleverd al beschikbaar zijn.

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

## POM-bestand tags {#pom-file-tags}

Tags binnen de `<configuration>` tag kunnen worden gewijzigd om je app te configureren. Het bewerken van de volgende regels in het standaard POM-bestand dat bij de [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) startrepository wordt geleverd, resulteert in deze wijzigingen:

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

- **`<deployurl>`** Deze tag is de URL waar het webforJ-eindpunt voor de projectinstallatie kan worden bereikt. Voor gebruikers die hun app lokaal uitvoeren, wordt een standaardpoort van 8888 gebruikt. Voor gebruikers die Docker draaien, moet de poort worden gewijzigd naar de poort die is ingevoerd tijdens [het configureren van de Docker-container](./docker#2-configuration).

- **`<classname>`** Deze tag moet de pakket- en klassenaam van de app bevatten die je wilt uitvoeren. Dit zal de enkele klasse in je project zijn die de `App` klasse uitbreidt en vanaf de basis-URL draait.

- **`<publishname>`** Deze tag specificeert de naam van de app in de gepubliceerde URL. Om je programma uit te voeren, navigeer je doorgaans naar een URL zoals `http://localhost:8888/webapp/<publishname>`, waarbij `<publishname>` wordt vervangen door de waarde in de `<publishname>` tag. Vervolgens wordt het programma dat is opgegeven in de `<classname>` tag uitgevoerd.

- **`<debug>`** De debug-tag kan worden ingesteld op true of false, en bepaalt of de console van de browser foutmeldingen weergeeft die door je programma worden gegenereerd.

## Een specifiek programma uitvoeren {#running-a-specific-program}

Er zijn twee manieren om een specifiek programma in je app uit te voeren:

1. Plaats het programma binnen de `run()`-methode van de klasse die `App` uitbreidt.
2. Maak gebruik van [routing](../../routing/overview) in je webforJ-app om het programma een specifieke URL te geven.

## Hoe webforJ een ingangspunt selecteert {#how-webforj-selects-an-entry-point}

Het ingangspunt voor een app wordt bepaald door de `<classname>` die in het POM-bestand is opgegeven. Als er geen ingangspunt is opgegeven in het POM-bestand, begint het systeem een zoekopdracht naar een ingangspunt.

### Zoektocht naar een ingangspunt {#entry-point-search}

1. Als er een enkele klasse is die de `App` klasse uitbreidt, wordt dat het ingangspunt.
2. Als meerdere klassen `App` uitbreiden, controleert het systeem of een van hen de annotatie `com.webforj.annotation.AppEntry` heeft. De enkele klasse die is geannoteerd met `@AppEntry` wordt het ingangspunt.
    :::warning
    Als meerdere klassen zijn geannoteerd met `@AppEntry`, wordt er een uitzondering opgegooid waarin alle ontdekte klassen worden vermeld.
    :::

Als er meerdere klassen zijn die `App` uitbreiden en geen van hen zijn geannoteerd met `@AppEntry`, wordt er een uitzondering opgegooid met een gedetailleerde beschrijving van elke subclass.

## Debugmodus {#debug-mode}

Het is ook mogelijk om je app in debugmodus uit te voeren, waarmee de console uitgebreide foutmeldingen kan afdrukken.

De eerste optie is om het `config.bbx` bestand te wijzigen, dat te vinden is in de `cfg/` directory van je BBj-installatie. Voeg de regel `SET DEBUG=1` toe aan het bestand en sla je wijzigingen op.

Daarnaast kun je in de Enterprise Manager het volgende toevoegen als programmainvoerveld: `DEBUG`

Het voltooien van een van deze opties zorgt ervoor dat de browserconsole foutmeldingen kan afdrukken.
