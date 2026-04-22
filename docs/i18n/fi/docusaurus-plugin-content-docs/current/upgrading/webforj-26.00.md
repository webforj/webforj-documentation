---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
sidebar_class_name: new-content
_i18n_hash: 49ea3920d3dc3f1c76943e04f9b7e2c9
---
Tämä dokumentaatio toimii oppaana webforJ-sovellusten päivittämiseen versiosta 25.00 versioon 26.00. 
Tässä ovat tarvittavat muutokset, jotta olemassa olevat sovellukset voivat jatkaa sujuvaa toimintaa. 
Kuten aina, katso [GitHubin julkaisun yleiskatsaus](https://github.com/webforj/webforj/releases) saadaksesi kattavamman luettelon muutoksista julkaisujen välillä.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## POM-tiedoston muutokset {#pom-file-changes}

### Java 21 ja 25 {#java-21-and-25}

webforJ 25.12 on viimeinen versio, joka toimii Java 17:n kanssa. 
WebforJ 26.00:sta alkaen tarvitset Java-version, joka on joko Java 21 tai Java 25, riippuen asetuksistasi.

Asenna tarvittava Java-versio, kuten on lueteltu [esivaatimuksissa](/docs/introduction/prerequisites), ja päivitä sitten pom.xml-tiedostosi:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven-repositorion URL {#maven-repository-url}

Sijainti, johon snapshot-artikkelit on tallennettu, on muuttunut. Oletko ladannut riippuvuutesi projektisi pom.xml-tiedostossa [Central Portalista](https://central.sonatype.com/)?

**Ennen:**
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    ....
  </repository>
</repositories>
```

**Jälkeen:**
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

### Spring Boot -päivitys {#spring-boot-upgrade}

webforJ 25.12 on viimeinen versio, joka käyttää Spring Boot 3.x:ää. 
WebforJ 26.00:sta alkaen projektisi on käytettävä Spring Boot 4.x:ää.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Poista Tomcat-version ylikirjoitukset
Spring Boot 4.x:n myötä Tomcat 11.x on nyt sisällytetty riippuvuudeksi, joten voit poistaa kaikki projektikohtaiset ylikirjoitukset Tomcat-version osalta.
:::

## Taulukko-API:n muutokset {#table-api-changes}

### `IconRenderer` merkkijonopohjaiset konstruktorit {#iconrenderer-string-based-constructors}

Seuraavat merkkijonopohjaiset konstruktorit on poistettu versiossa 26.00; käytä sen sijaan `IconDefinition`-pohjaisia konstruktoreita:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Poistuneet valintamenetelmät {#deprecated-selection-methods}

WebforJ 26.00:sta alkaen, sen sijaan että valitsisit kohteita `Table`-komponentissa indeksien perusteella, valitse kohteita taulusta käyttäen kohteen avainta. Voit käyttää `setKeyProvider()`-metodia tarjoamaan mukautettuja avaimia taulukon kohteille.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` tai `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` tai `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` tai `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Valintatapahtumat {#selection-events}

Tehostaaksesi muutosta tavasta valita kohteita taulussa, `TableItemSelectionChange` ei enää implementoi `SelectEvent`-tapahtumaa.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Tuetut Webswingin käynnistysvaihtoehdot {#unsupported-webswing-bootstrap-options}

Seuraavat `WebswingOptions`-metodit on poistettu versiosta 26.00, koska Webswing API ei enää tue niitä.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

`PingParams`-luokka on myös poistettu. Niiden, jotka käyttivät näitä metodeja tai `PingParams`-luokkaa, tulisi sen sijaan käyttää Webswingin hallintakonsolia kokoonpanon suoraan määrittämiseen.

## Suodattimet `Repository`-luokalle {#filters-for-repository}

`RetrievalCriteria`- ja `RetrievalBuilder`-rajapinnat on poistettu webforJ 26.00:ssa. Sen sijaan, että käyttäisit yleistä `Repository`-rajapintaa, käytä joko `RepositoryCriteria<T, F>`- tai `CollectionRepository`-luokkaa yksinkertaisille suodattimille, tai [`QueryableRepository`](/docs/advanced/repository/querying-data) kehittyneempiä suodatus- ja lajiteltutyyppejä sekä sivutusta varten.

**Ennen:**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

**Jälkeen:**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### Poistetut repositorimetodit

Käytä seuraavaa taulukkoa nähdäksesi vanhentuneet repositorimetodit ja mitä menetelmiä käyttää jatkossa.

| v25 | v26 |
|---|---|
| `setFilter(...)` | `setBaseFilter(...)` |
| `getFilter()` | `getBaseFilter()` |
| `getOrderBy()` | `getOrderCriteriaList()` |
| `setOrderBy(Comparator<T>)` | `getOrderCriteriaList().add(new OrderCriteria<>(keyExtractor, direction))` |
| `findOneBy(...)` | `findBy(...)` sitten `.findFirst()` |
| `findBy(RetrievalCriteria<T>)` | `findBy(RepositoryCriteria<T, F>)` |
| `size(RetrievalCriteria<T>)` | `size(RepositoryCriteria<T, F>)` |
| `getIndex(T)` | `find(key)` tai `findBy(criteria)` |
| `findByIndex(int)` | `find(key)` tai `findBy(criteria)` |

## `WebforjBBjBridge`-poisto {#removal-of-webforjbbjbridge}

WebforJ 25.11:sta alkaen, WebforjBBjBridge ja kaikki sen API:t on poistettu. Sen sijaan, että pääsisit sillalle, webforJ käyttää nyt suoraa Java API:a viestiäkseen ja päästääkseen käyttöön kaikki tarvittavat BBj API:t.
