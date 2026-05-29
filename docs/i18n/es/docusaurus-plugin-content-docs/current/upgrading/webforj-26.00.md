---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
sidebar_class_name: new-content
_i18n_hash: 49ea3920d3dc3f1c76943e04f9b7e2c9
---
Esta documentación sirve como guía para actualizar aplicaciones webforJ de la versión 25.00 a 26.00.
Aquí están los cambios necesarios para que las aplicaciones existentes sigan funcionando sin problemas.
Como siempre, consulte la [vista general de lanzamientos en GitHub](https://github.com/webforj/webforj/releases) para obtener una lista más completa de los cambios entre versiones.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Cambios en el archivo POM {#pom-file-changes}

### Java 21 y 25 {#java-21-and-25}

webforJ 25.12 es la última versión que funciona con Java 17.
A partir de webforJ 26.00, necesita una versión de Java que sea Java 21 o Java 25, dependiendo de su configuración.

Instale la versión de Java requerida según se indica en los [prerrequisitos](/docs/introduction/prerequisites), luego actualice su archivo pom.xml:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### URL del repositorio de Maven {#maven-repository-url}

La ubicación donde se alojan los artefactos de snapshot ha cambiado. En el archivo pom.xml de su proyecto, debe haber descargado sus dependencias del [Central Portal](https://central.sonatype.com/).

**Antes:**
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    ....
  </repository>
</repositories>
```

**Después:**
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

### Actualización de Spring Boot {#spring-boot-upgrade}

webforJ 25.12 es la última versión que utiliza Spring Boot 3.x. 
A partir de webforJ 26.00, su proyecto debe utilizar Spring Boot 4.x.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Eliminando las sobreescrituras para la versión de Tomcat
Con Spring Boot 4.x, Tomcat 11.x ahora se incluye como una dependencia, por lo que puede eliminar cualquier sobreescritura específica del proyecto para la versión de Tomcat.
:::

## Cambios en la API de la tabla {#table-api-changes}

### Constructores basados en cadenas de `IconRenderer` {#iconrenderer- string-based-constructors}

Los siguientes constructores basados en cadenas se eliminan en 26.00; utilice los constructores basados en `IconDefinition` en su lugar:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Métodos de selección obsoletos {#deprecated-selection-methods}

A partir de webforJ 26.00, en lugar de seleccionar elementos en una `Table` basándose en índices, seleccione elementos en una tabla utilizando la clave del elemento. Puede utilizar el método `setKeyProvider()` para proporcionar claves personalizadas para los elementos en la tabla.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` o `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` o `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` o `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Eventos de selección {#selection-events}

Para reforzar aún más el cambio en la forma de seleccionar elementos en una `Table`, `TableItemSelectionChange` ya no implementa `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Opciones de arranque de Webswing no compatibles {#unsupported-webswing-bootstrap-options}

Los siguientes métodos de `WebswingOptions` están obsoletos y se eliminan en 26.00 porque ya no son compatibles con la API de Webswing.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

La clase `PingParams` también está obsoleta. Aquellos que estaban utilizando estos métodos o la clase `PingParams` deben utilizar en su lugar la Consola de Administración de Webswing para configurar directamente las opciones.

## Filtros para `Repository` {#filters-for-repository}

Las interfaces `RetrievalCriteria` y `RetrievalBuilder` se eliminan en webforJ 26.00. En lugar de utilizar la interfaz genérica `Repository`, utilice `RepositoryCriteria<T, F>` o `CollectionRepository` para filtros simples, o [`QueryableRepository`](/docs/advanced/repository/querying-data) para tipos de filtrado, clasificación y paginación más avanzados.

**Antes:**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
});
```

**Después:**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
});
```

### Métodos del repositorio obsoletos

Utilice la siguiente tabla para ver los métodos del repositorio obsoletos y qué métodos utilizar en el futuro.

| v25 | v26 |
|---|---|
| `setFilter(...)` | `setBaseFilter(...)` |
| `getFilter()` | `getBaseFilter()` |
| `getOrderBy()` | `getOrderCriteriaList()` |
| `setOrderBy(Comparator<T>)` | `getOrderCriteriaList().add(new OrderCriteria<>(keyExtractor, direction))` |
| `findOneBy(...)` | `findBy(...)` luego `.findFirst()` |
| `findBy(RetrievalCriteria<T>)` | `findBy(RepositoryCriteria<T, F>)` |
| `size(RetrievalCriteria<T>)` | `size(RepositoryCriteria<T, F>)` |
| `getIndex(T)` | `find(key)` o `findBy(criteria)` |
| `findByIndex(int)` | `find(key)` o `findBy(criteria)` |

## Eliminación de `WebforjBBjBridge` {#removal-of-webforjbbjbridge`}

A partir de webforJ 25.11, WebforjBBjBridge y todas sus API han sido eliminadas. En lugar de acceder al puente, webforJ ahora utiliza la API de Java directa para comunicarse y acceder a cualquier API de BBj requerida.
