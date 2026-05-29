---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
sidebar_class_name: new-content
_i18n_hash: 49ea3920d3dc3f1c76943e04f9b7e2c9
---
Diese Dokumentation dient als Anleitung zur Aktualisierung von webforJ-Anwendungen von 25.00 auf 26.00. 
Hier sind die erforderlichen Änderungen, damit bestehende Anwendungen weiterhin reibungslos funktionieren. 
Wie immer finden Sie in der [GitHub-Versionübersicht](https://github.com/webforj/webforj/releases) eine umfassendere Liste der Änderungen zwischen den Versionen.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## POM-Dateiänderungen {#pom-file-changes}

### Java 21 und 25 {#java-21-and-25}

webforJ 25.12 ist die letzte Version, die mit Java 17 funktioniert. 
Ab webforJ 26.00 benötigen Sie eine Java-Version, die entweder Java 21 oder Java 25 ist, abhängig von Ihrer Konfiguration.

Installieren Sie die erforderliche Java-Version wie in den [Voraussetzungen](/docs/introduction/prerequisites) aufgelistet, und aktualisieren Sie dann Ihre pom.xml-Datei:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven-Repository-URL {#maven-repository-url}

Der Ort, an dem die Snapshot-Artefakte gehostet werden, hat sich geändert. In Ihrer pom.xml-Datei Ihres Projekts haben Sie Ihre Abhängigkeiten von dem [Central Portal](https://central.sonatype.com/) heruntergeladen.

**Vorher:**
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    ....
  </repository>
</repositories>
```

**Nachher:**
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

### Spring Boot-Aktualisierung {#spring-boot-upgrade}

webforJ 25.12 ist die letzte Version, die Spring Boot 3.x verwendet. 
Ab webforJ 26.00 muss Ihr Projekt Spring Boot 4.x verwenden.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Entfernen von Überschreibungen für die Tomcat-Version
Mit Spring Boot 4.x ist Tomcat 11.x jetzt als Abhängigkeit enthalten, sodass Sie jede projektspezifische Überschreibung für die Tomcat-Version entfernen können.
:::

## Änderungen der Tabellen-API {#table-api-changes}

### `IconRenderer`-zeichenbasierte Konstruktoren {#iconrenderer-string-based-constructors}

Die folgenden zeichenbasierten Konstruktoren wurden in 26.00 entfernt; verwenden Sie stattdessen die Konstruktoren auf Basis von `IconDefinition`:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Veraltete Auswahlmethoden {#deprecated-selection-methods}

Ab webforJ 26.00 sollten Sie anstelle der Auswahl von Elementen in einer `Table` basierend auf Indizes, Elemente in einer Tabelle mithilfe des Elementschlüssels auswählen. Sie können die Methode `setKeyProvider()` verwenden, um benutzerdefinierte Schlüssel für die Elemente in der Tabelle bereitzustellen.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` oder `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` oder `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` oder `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Auswahlereignisse {#selection-events}

Um den Wechsel in der Auswahlweise von Elementen in einer `Table` weiter zu verstärken, implementiert `TableItemSelectionChange` nicht mehr `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Nicht unterstützte Webswing-Bootstrap-Optionen {#unsupported-webswing-bootstrap-options}

Die folgenden `WebswingOptions`-Methoden sind in 26.00 veraltet und wurden entfernt, da sie von der Webswing-API nicht mehr unterstützt werden.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

Die Klasse `PingParams` ist ebenfalls veraltet. Benutzer, die diese Methoden oder die `PingParams`-Klasse verwendet haben, sollten stattdessen das Webswing-Administrationskonsole verwenden, um die Optionen direkt zu konfigurieren.

## Filter für `Repository` {#filters-for-repository}

Die Schnittstellen `RetrievalCriteria` und `RetrievalBuilder` wurden in webforJ 26.00 entfernt. Anstelle der Verwendung der generischen `Repository`-Schnittstelle verwenden Sie entweder `RepositoryCriteria<T, F>`, `CollectionRepository` für einfache Filter oder [`QueryableRepository`](/docs/advanced/repository/querying-data) für fortgeschrittenere Filtertypen, Sortierungen und Paginierung.

**Vorher:**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

**Nachher:**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### Veraltete Repository-Methoden

Verwenden Sie die folgende Tabelle, um die veralteten Repository-Methoden und welche Methoden Sie künftig verwenden sollten, zu sehen.

| v25 | v26 |
|---|---|
| `setFilter(...)` | `setBaseFilter(...)` |
| `getFilter()` | `getBaseFilter()` |
| `getOrderBy()` | `getOrderCriteriaList()` |
| `setOrderBy(Comparator<T>)` | `getOrderCriteriaList().add(new OrderCriteria<>(keyExtractor, direction))` |
| `findOneBy(...)` | `findBy(...)` dann `.findFirst()` |
| `findBy(RetrievalCriteria<T>)` | `findBy(RepositoryCriteria<T, F>)` |
| `size(RetrievalCriteria<T>)` | `size(RepositoryCriteria<T, F>)` |
| `getIndex(T)` | `find(key)` oder `findBy(criteria)` |
| `findByIndex(int)` | `find(key)` oder `findBy(criteria)` |

## Entfernung von `WebforjBBjBridge` {#removal-of-webforjbbjbridge}

Seit webforJ 25.11 wurden WebforjBBjBridge und alle seine APIs entfernt. Anstelle des Zugriffs auf die Brücke verwendet webforJ nun die direkte Java-API, um mit erforderlichen BBj-APIs zu kommunizieren und auf sie zuzugreifen.
