---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
sidebar_class_name: new-content
_i18n_hash: 49ea3920d3dc3f1c76943e04f9b7e2c9
---
Deze documentatie dient als een gids voor het upgraden van webforJ-apps van 25.00 naar 26.00. Hier zijn de wijzigingen die nodig zijn voor bestaande apps om soepel te blijven draaien. Zoals altijd, zie de [GitHub release-overzicht](https://github.com/webforj/webforj/releases) voor een meer uitgebreide lijst van wijzigingen tussen releases.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## POM-bestand wijzigingen {#pom-file-changes}

### Java 21 en 25 {#java-21-and-25}

webforJ 25.12 is de laatste versie die werkt met Java 17. Beginnend met webforJ 26.00 heb je een Java-versie nodig die ofwel Java 21 of Java 25 is, afhankelijk van je setup.

Installeer de vereiste Java-versie zoals vermeld in de [vereisten](/docs/introduction/prerequisites), en werk vervolgens je pom.xml-bestand bij:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven-repository-URL {#maven-repository-url}

De locatie waar de snapshot-artifacten worden gehost, is veranderd. In je project's pom.xml-bestand heb je je afhankelijkheden gedownload van het [Central Portal](https://central.sonatype.com/).

**Voorheen:**
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    ....
  </repository>
</repositories>
```

**Na:**
```xml {3-5}
<repositories>
  <repository>
    <name>Central Portal Snapshots</name>
    <id>central-portal-snapshots</id>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
    ....
  </repository>
</repositories>
```

### Spring Boot-upgrade {#spring-boot-upgrade}

webforJ 25.12 is de laatste versie die Spring Boot 3.x gebruikt. Beginnend met webforJ 26.00 moet je project Spring Boot 4.x gebruiken.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Verwijderen van overrides voor de Tomcat-versie
Met Spring Boot 4.x is Tomcat 11.x nu opgenomen als een afhankelijkheid, dus je kunt eventuele project-specifieke overrides voor de Tomcat-versie verwijderen.
:::

## Tabel API-wijzigingen {#table-api-changes}

### `IconRenderer` string-gebaseerde constructeurs {#iconrenderer-string-based-constructors}

De volgende string-gebaseerde constructeurs zijn verwijderd in 26.00; gebruik in plaats daarvan `IconDefinition`-gebaseerde constructeurs:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Deprecatie van selectiemethoden {#deprecated-selection-methods}

Vanaf webforJ 26.00, kies in plaats van items in een `Table` op basis van indices, items in een Tabel met de item-sleutel. Je kunt de methode `setKeyProvider()` gebruiken om aangepaste sleutels voor de items in de tabel te geven.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` of `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` of `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` of `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Selectie-evenementen {#selection-events}

Om de verschuiving in de manier waarop items in een `Table` worden geselecteerd verder te versterken, implementeert `TableItemSelectionChange` niet langer `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Niet-ondersteunde Webswing bootstrap-opties {#unsupported-webswing-bootstrap-options}

De volgende `WebswingOptions`-methoden zijn gedepriveerd en verwijderd in 26.00 omdat ze niet langer worden ondersteund door de Webswing API.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

De `PingParams`-klasse is ook gedepriveerd. Degenen die deze methoden of de `PingParams`-klasse gebruikten, moeten in plaats daarvan de Webswing Admin Console gebruiken om de opties direct te configureren.

## Filters voor `Repository` {#filters-for-repository}

De interfaces `RetrievalCriteria` en `RetrievalBuilder` zijn verwijderd in webforJ 26.00. In plaats van met de generieke `Repository`-interface te werken, gebruik je ofwel `RepositoryCriteria<T, F>`, `CollectionRepository` voor eenvoudige filters, of [`QueryableRepository`](/docs/advanced/repository/querying-data) voor meer geavanceerde filteringstypen, sorteren en pagineren.

**Voorheen:**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

**Na:**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### Gedepriveerde repositorymethoden

Gebruik de volgende tabel om de gedepriveerde repositorymethoden te zien en welke methoden je in de toekomst moet gebruiken.

| v25 | v26 |
|---|---|
| `setFilter(...)` | `setBaseFilter(...)` |
| `getFilter()` | `getBaseFilter()` |
| `getOrderBy()` | `getOrderCriteriaList()` |
| `setOrderBy(Comparator<T>)` | `getOrderCriteriaList().add(new OrderCriteria<>(keyExtractor, direction))` |
| `findOneBy(...)` | `findBy(...)` dan `.findFirst()` |
| `findBy(RetrievalCriteria<T>)` | `findBy(RepositoryCriteria<T, F>)` |
| `size(RetrievalCriteria<T>)` | `size(RepositoryCriteria<T, F>)` |
| `getIndex(T)` | `find(key)` of `findBy(criteria)` |
| `findByIndex(int)` | `find(key)` of `findBy(criteria)` |

## Verwijdering van `WebforjBBjBridge` {#removal-of-webforjbbjbridge`}

Beginnend met webforJ 25.11 zijn WebforjBBjBridge en al zijn API's verwijderd. In plaats van de brug te bereiken, gebruikt webforJ nu de directe Java API om te communiceren met en toegang te krijgen tot de benodigde BBj API's.
