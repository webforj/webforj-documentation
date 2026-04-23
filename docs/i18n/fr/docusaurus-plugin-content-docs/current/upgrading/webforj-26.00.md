---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
sidebar_class_name: new-content
_i18n_hash: 49ea3920d3dc3f1c76943e04f9b7e2c9
---
Cette documentation sert de guide pour mettre à jour les applications webforJ de la version 25.00 à la version 26.00. Voici les changements nécessaires pour que les applications existantes continuent à fonctionner correctement. Comme toujours, consultez l’[aperçu des versions GitHub](https://github.com/webforj/webforj/releases) pour une liste plus complète des changements entre les versions.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Changements du fichier POM {#pom-file-changes}

### Java 21 et 25 {#java-21-and-25}

webforJ 25.12 est la dernière version compatible avec Java 17. À partir de webforJ 26.00, vous devez utiliser une version de Java, soit Java 21, soit Java 25, en fonction de votre configuration.

Installez la version Java requise comme indiqué dans les [prérequis](/docs/introduction/prerequisites), puis mettez à jour votre fichier pom.xml :

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### URL du dépôt Maven {#maven-repository-url}

L'emplacement où les artefacts de snapshot sont hébergés a changé. Dans le fichier pom.xml de votre projet, vous devez télécharger vos dépendances à partir du [Central Portal](https://central.sonatype.com/).

**Avant :**
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    ....
  </repository>
</repositories>
```

**Après :**
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

### Mise à niveau de Spring Boot {#spring-boot-upgrade}

webforJ 25.12 est la dernière version qui utilise Spring Boot 3.x. À partir de webforJ 26.00, votre projet doit utiliser Spring Boot 4.x.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Suppression des remplacements pour la version de Tomcat
Avec Spring Boot 4.x, Tomcat 11.x est maintenant inclus en tant que dépendance, vous pouvez donc supprimer tout remplacement spécifique au projet pour la version de Tomcat.
:::

## Changements de l'API Table {#table-api-changes}

### Constructeurs basés sur des chaînes `IconRenderer` {#iconrenderer-string-based-constructors}

Les constructeurs basés sur des chaînes suivants sont supprimés dans 26.00 ; utilisez plutôt des constructeurs basés sur `IconDefinition` :

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Méthodes de sélection obsolètes {#deprecated-selection-methods}

À partir de webforJ 26.00, au lieu de sélectionner des éléments dans une `Table` en fonction des indices, sélectionnez des éléments dans une Table en utilisant la clé de l'élément. Vous pouvez utiliser la méthode `setKeyProvider()` pour fournir des clés personnalisées pour les éléments de la table.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` ou `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` ou `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` ou `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Événements de sélection {#selection-events}

Pour renforcer le changement dans la manière de sélectionner des éléments dans une `Table`, `TableItemSelectionChange` n'implémente plus `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Options de bootstrap Webswing non prises en charge {#unsupported-webswing-bootstrap-options}

Les méthodes suivantes de `WebswingOptions` sont obsolètes et supprimées dans 26.00 car elles ne sont plus prises en charge par l’API Webswing.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

La classe `PingParams` est également obsolète. Ceux qui utilisaient ces méthodes ou la classe `PingParams` doivent plutôt utiliser la console d'administration Webswing pour configurer directement les options.

## Filtres pour `Repository` {#filters-for-repository}

Les interfaces `RetrievalCriteria` et `RetrievalBuilder` ont été supprimées dans webforJ 26.00. Au lieu d'utiliser l'interface générique `Repository`, utilisez soit `RepositoryCriteria<T, F>`, soit `CollectionRepository` pour des filtres simples, ou [`QueryableRepository`](/docs/advanced/repository/querying-data) pour des types de filtrage plus avancés, le tri et la pagination.

**Avant :**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

**Après :**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### Méthodes de dépôt obsolètes

Utilisez le tableau suivant pour voir les méthodes de dépôt obsolètes et les méthodes à utiliser à l'avenir.

| v25 | v26 |
|---|---|
| `setFilter(...)` | `setBaseFilter(...)` |
| `getFilter()` | `getBaseFilter()` |
| `getOrderBy()` | `getOrderCriteriaList()` |
| `setOrderBy(Comparator<T>)` | `getOrderCriteriaList().add(new OrderCriteria<>(keyExtractor, direction))` |
| `findOneBy(...)` | `findBy(...)` puis `.findFirst()` |
| `findBy(RetrievalCriteria<T>)` | `findBy(RepositoryCriteria<T, F>)` |
| `size(RetrievalCriteria<T>)` | `size(RepositoryCriteria<T, F>)` |
| `getIndex(T)` | `find(key)` ou `findBy(criteria)` |
| `findByIndex(int)` | `find(key)` ou `findBy(criteria)` |

## Suppression de `WebforjBBjBridge` {#removal-of-webforjbbjbridge}

À partir de webforJ 25.11, WebforjBBjBridge et toutes ses API ont été supprimées. Au lieu d'accéder au pont, webforJ utilise désormais l'API Java directe pour communiquer et accéder à toutes les API BBj requises.
