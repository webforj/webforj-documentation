---
title: Plugin Installeren
sidebar_position: 3
displayed_sidebar: documentationSidebar
description: >-
  Configure the webforJ install Maven plugin with deploy URL, class name,
  publish name, and debug flags for BBjServices deployments.
_i18n_hash: 1019285125db3439621951fe22047837
---
:::danger Verouderd en zal worden verwijderd
De webforJ install plugin is verouderd en zal in een toekomstige release worden verwijderd. Begin geen nieuwe projecten met deze plugin. Verplaats bestaande projecten naar [BBjShip](/docs/configuration/bbjship/overview), dat webforJ-apps installeert, implementeert en uitvoert in BBjServices. [Bestaande projecten](/docs/configuration/bbjship/existing-projects) vermeldt de wijzigingen die een project nodig heeft.
:::

Je kunt webforJ configureren met behulp van het POM-bestand van een project, dat is ontworpen om het implementeren van een app eenvoudig te maken. De volgende secties beschrijven de verschillende opties die je kunt wijzigen om een gewenst resultaat te bereiken.

## Uitsluiting van de engine {#engine-exclusion}

Wanneer je draait met `BBjServices`, moet de afhankelijkheid `webforj-engine` worden uitgesloten, aangezien de functies die door de engine worden geboden al beschikbaar zijn.

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

Tags binnen de `<configuration>` tag kunnen worden gewijzigd om je app te configureren. Het aanpassen van de volgende regels in het standaard POM-bestand dat wordt geleverd met het [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) startersrepository resulteert in deze wijzigingen:

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

- **`<deployurl>`** Deze tag is de URL waar het webforJ-eindpunt voor de projectinstallatie bereikbaar is. Voor gebruikers die hun app lokaal uitvoeren, wordt standaardpoort 8888 gebruikt. Voor gebruikers die Docker uitvoeren, moet de poort worden gewijzigd naar de poort die werd ingevoerd bij het [configureren van de Docker-container](./docker#2-configuration).

- **`<classname>`** Deze tag moet de package- en klasse-naam van de app bevatten die je wilt uitvoeren. Dit zal de enkele klasse in je project zijn die de `App`-klasse uitbreidt en vanaf de basis-URL wordt uitgevoerd.

- **`<publishname>`** Deze tag specificeert de naam van de app in de gepubliceerde URL. Over het algemeen, om je programma uit te voeren, navigeer je naar een URL die lijkt op `http://localhost:8888/webapp/<publishname>`, waarbij je `<publishname>` vervangt door de waarde in de `<publishname>` tag. Vervolgens wordt het programma dat door de `<classname>` tag is opgegeven uitgevoerd.

- **`<debug>`** De debug-tag kan worden ingesteld op waar of niet waar, en bepaalt of de console van de browser foutmeldingen weergeeft die door je programma worden gegenereerd.

## Een specifiek programma uitvoeren {#running-a-specific-program}

Er zijn twee manieren om een specifiek programma in je app uit te voeren:

1. Plaats het programma binnen de `run()`-methode van de klasse die `App` uitbreidt.
2. Maak gebruik van [routing](../../routing/overview) in je webforJ-app om het programma een eigen URL te geven.

## Hoe webforJ een toegangspunt selecteert {#how-webforj-selects-an-entry-point}

Het toegangspunt voor een app wordt bepaald door de `<classname>` die in het POM-bestand is opgegeven. Als er geen toegangspunt in het POM-bestand is opgegeven, zal het systeem een toegangspuntzoektocht starten.

### Toegangspuntzoektocht {#entry-point-search}

1. Als er een enkele klasse is die de `App`-klasse uitbreidt, wordt dat het toegangspunt.
2. Als meerdere klassen `App` uitbreiden, controleert het systeem of een daarvan de annotatie `com.webforj.annotation.AppEntry` heeft. De enkele klasse die is geannoteerd met `@AppEntry` wordt het toegangspunt.
    :::warning
    Als meerdere klassen zijn geannoteerd met `@AppEntry`, wordt er een uitzondering gegenereerd, waarin alle ontdekte klassen worden vermeld.
    :::

Als er meerdere klassen zijn die `App` uitbreiden en geen van hen zijn geannoteerd met `@AppEntry`, wordt er een uitzondering gegenereerd, die elke subclassule beschrijft.

## Debugmodus {#debug-mode}

Het is ook mogelijk om je app in debugmodus uit te voeren, waarmee de console uitgebreide foutmeldingen kan weergeven.

De eerste optie is om het `config.bbx`-bestand te wijzigen, dat zich in de `cfg/`-directory van je BBj-installatie bevindt. Voeg de regel `SET DEBUG=1` toe aan het bestand en sla je wijzigingen op.

Daarnaast kun je in de Enterprise Manager het volgende als programmacommentaar toevoegen: `DEBUG`

Het voltooien van een van deze opties stelt de browserconsole in staat om foutmeldingen af te drukken.
