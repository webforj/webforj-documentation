---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
slug: /upgrading/webforj-26.00
pagination_next: null
sidebar_position: 1
_i18n_hash: 3b9827a67a81e207508d7db72a650b64
---
Esta documentación sirve como una guía para actualizar aplicaciones webforJ de 25.00 a 26.00. Aquí están los cambios necesarios para que las aplicaciones existentes sigan funcionando sin problemas. Como siempre, consulte el [resumen de lanzamientos de GitHub](https://github.com/webforj/webforj/releases) para una lista más completa de cambios entre versiones.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Cambios en el archivo POM {#pom-file-changes}

### Java 21 y 25 {#java-21-and-25}

webforJ 25.12 es la última versión que funciona con Java 17. A partir de webforJ 26.00, necesita una versión de Java que sea Java 21 o Java 25, dependiendo de su configuración.

Instale la versión de Java requerida como se indica en los [prerrequisitos](/docs/introduction/prerequisites), luego actualice su archivo pom.xml:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### URL del repositorio de Maven {#maven-repository-url}

La ubicación donde se alojan los artefactos de instantáneas ha cambiado. En el archivo pom.xml de su proyecto, ha descargado sus dependencias desde el [Portal Central](https://central.sonatype.com/).

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
    <name>Instantáneas del Portal Central</name>
    <id>central-portal-snapshots</id>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
    ....
  </repository>
</repositories>
```

### Actualización de Spring Boot {#spring-boot-upgrade}

webforJ 25.12 es la última versión que utiliza Spring Boot 3.x. A partir de webforJ 26.00, su proyecto debe utilizar Spring Boot 4.x.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Eliminando sobreeescrituras para la versión de Tomcat
Con Spring Boot 4.x, Tomcat 11.x se incluye ahora como una dependencia, por lo que puede eliminar cualquier sobreeescritura específica del proyecto para la versión de Tomcat.
:::

## Cambios en la API de Tabla {#table-api-changes}

### Constructores basados en cadenas de `IconRenderer` {#iconrenderer- string-based-constructors}

Los siguientes constructores basados en cadenas se han eliminado en 26.00; use constructores basados en `IconDefinition` en su lugar:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Métodos de selección obsoletos {#deprecated-selection-methods}

A partir de webforJ 26.00, en lugar de seleccionar elementos en una `Tabla` en función de índices, seleccione elementos en una Tabla utilizando la clave del elemento. Puede usar el método `setKeyProvider()` para proporcionar claves personalizadas para los elementos en la tabla.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` o `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` o `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` o `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Eventos de selección {#selection-events}

Para reforzar aún más el cambio en cómo seleccionar elementos en una `Tabla`, `TableItemSelectionChange` ya no implementa `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Opciones de arranque de Webswing no soportadas {#unsupported-webswing-bootstrap-options}

Los siguientes métodos `WebswingOptions` están obsoletos y se han eliminado en 26.00 porque ya no son compatibles con la API de Webswing.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

La clase `PingParams` también está obsoleta. Quienes usaban estos métodos o la clase `PingParams` deben usar la Consola de Administración de Webswing para configurar directamente las opciones.

## Filtros para `Repository` {#filters-for-repository}

Las interfaces `RetrievalCriteria` y `RetrievalBuilder` se han eliminado en webforJ 26.00. En lugar de usar la interfaz genérica `Repository`, use `RepositoryCriteria<T, F>`, `CollectionRepository` para filtros simples, o [`QueryableRepository`](/docs/advanced/repository/querying-data) para tipos de filtrado más avanzados, ordenamiento y paginación.

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

### Métodos de repositorio obsoletos {#deprecated-repository-methods}

Use la siguiente tabla para ver los métodos de repositorio obsoletos y qué métodos usar en el futuro.

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

## Eliminación de `WebforjBBjBridge` {#removal-of-webforjbbjbridge}

A partir de webforJ 25.11, WebforjBBjBridge y todas sus API han sido eliminadas. En lugar de acceder al puente, webforJ ahora utiliza la API de Java directa para comunicarse y acceder a cualquier API de BBj requerida.

## Cambios en el sistema de diseño (DWC 26) {#design-system-changes-dwc-26}

webforJ 26.00 se entrega con la versión 26 del sistema de diseño DWC. La actualización es incremental en lugar de una reescritura completa: la mayoría de las variables CSS v25 siguen estando disponibles, se preserva la API de tokens pública y las personalizaciones existentes continúan funcionando sin cambios.

Esta sección enumera los cambios importantes sobre los que puede necesitar actuar. Para una visión conceptual, que incluye cómo se ve el nuevo motor de colores, cómo se propaga `--dwc-dark-mode`, por qué se eliminaron los efectos de ondas, y la mecánica por área, consulte [DWC 26 sistema de diseño](/docs/upgrading/webforj-26.00/design-system).

### Veredicto rápido {#design-system-quick-verdict}

| Escenario | Qué esperar |
|---|---|
| Usa el estilo predeterminado | Actualización visual. Los tonos de la paleta predeterminada han sido ajustados (el primario pasó de `h: 211 / s: 100%` a `h: 223 / s: 91%`), las sombras se ven más superpuestas y los componentes se sienten más redondeados. No se necesita cambio en el código. |
| Sobreescribe `--dwc-color-{name}-h` y `-s` | Aún funciona. Se preserva la ruta de semilla HSL. |
| Sobreescribe pasos individuales de paleta (por ejemplo `--dwc-color-primary-40`) | Los números de paso pueden resultar en diferentes colores. Consulte [Mecánica de la paleta de colores](/docs/upgrading/webforj-26.00/design-system#the-color-system). |
| Depende de `--dwc-color-{name}-c` | Eliminar. El cambio de texto claro/oscurito ahora se calcula automáticamente por tono. |
| Referencias tokens de tamaño de fuente nombrados (`--dwc-font-size-m`, `-l`, y así sucesivamente) | La escala ha bajado un nivel. `m` ahora es `14px` en lugar de `16px`. Consulte [Tipografía](#design-system-typography). |
| Usa `--dwc-font-weight-semibold` para obtener peso `500` | `semibold` ahora es `600`. Cambie a `--dwc-font-weight-medium` para `500`. |
| Reserva espacio alrededor de elementos enfocados con `--dwc-focus-ring-width` | El anillo ahora tiene un espacio. Agregue `--dwc-focus-ring-gap`. Consulte [Anillo de enfoque](#design-system-focus-ring). |
| Personalizó efectos de hover / onda de botones | Las ondas han desaparecido. La respuesta al presionar ahora es un pequeño escalado a la baja. |

### `--dwc-color-{name}-c` se elimina {#design-system-c-removed}

Si tiene alguna sobreescritura de `--dwc-color-{name}-c`, puede eliminarlas, no tienen efecto. El cambio de texto claro/oscurito ahora se calcula automáticamente por tono.

### Semántica de `--dwc-color-{name}-alt` cambió {#design-system-alt-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-color-{name}-alt` | Paso de paleta `95` (fondo cercano al blanco) | Semilla al 12% de opacidad (tinte translúcido) |

Si usó `-alt` como fondo cercano al blanco sólido, ahora se leerá como una superposición de tinte translúcido. Elija un paso específico (`--dwc-color-{name}-95`) o diseñe alrededor de la semántica translúcida.

### Semántica de `--dwc-border-color-{name}` cambió {#design-system-border-color-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-border-color-{name}` | Establecido por variación como `var(--dwc-color-{name})` (el tono saturado) | Calculado en el generador: tono aclarado del modo de la semilla |

Si su CSS lee `--dwc-border-color-primary` esperando el color primario saturado, la visualización ahora es un tono de separación sutil en su lugar. Si desea la apariencia saturada, cambie directamente a `--dwc-color-primary`.

### Formato de `--dwc-shadow-color` cambiado {#design-system-shadow-color-changed}

|  | v25 | v26 |
|---|---|---|
| `--dwc-shadow-color` | Triplete HSL (`h, s%, l%`) | Color OKLCH completo |

Si su CSS utiliza la forma de triplete heredada como `hsla(var(--dwc-shadow-color), 0.07)`, cambie a un token de sombra completo (`var(--dwc-shadow-m)`) o reescriba con `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

### Tipografía {#design-system-typography}

La escala de fuentes se ajustó, por lo que los nombres de los bloques se desplazaron hacia abajo un nivel:

| Token | v25 | v26 |
|---|---|---|
| `--dwc-font-size-3xs` | `10px` | `10px` |
| `--dwc-font-size-2xs` | `12px` | `11px` |
| `--dwc-font-size-xs`  | `13px` | `12px` |
| `--dwc-font-size-s`   | `14px` | `13px` |
| `--dwc-font-size-m`   | `16px` | `14px` |
| `--dwc-font-size-l`   | `18px` | `16px` |
| `--dwc-font-size-xl`  | `22px` | `20px` |
| `--dwc-font-size-2xl` | `28px` | `26px` |
| `--dwc-font-size-3xl` | `36px` | `34px` |

El `--dwc-font-size` predeterminado sigue resolviéndose a **14px**, simplemente lo alcanza a través de `--dwc-font-size-m` (v26) en lugar de `--dwc-font-size-s` (v25). Si su CSS referencia tokens de tamaño de fuente por nombre (por ejemplo, `font-size: var(--dwc-font-size-l)`), el resultado visible será más pequeño en v26. Aumente un nivel para preservar el tamaño v25.

Los pesos de fuente ganaron tres tokens (`thin`, `medium`, `black`) y un token existente se desplazó:

| Token | v25 | v26 |
|---|---|---|
| `--dwc-font-weight-semibold` | `500` | `600` |
| `--dwc-font-weight-medium`   | (no existía) | `500` |

Si usó `--dwc-font-weight-semibold` para obtener texto de peso 500, cambie a `--dwc-font-weight-medium`.

### Radio de borde {#design-system-border-radius}

|  | v25 | v26 |
|---|---|---|
| Unidad | `em` (se escala con el tamaño de fuente padre) | `rem` (se escala con el tamaño de fuente raíz) |
| `--dwc-border-radius` predeterminado | `--dwc-border-radius-s` (`4px`) | `--dwc-border-radius-seed` (`8px`) |
| Pasos disponibles | hasta `2xl` | agrega `3xl`, `4xl` |

Los componentes se sienten más redondeados de forma predeterminada. Si un componente anidado dentro de un texto más grande solía heredar un radio más grande mediante `em`, ese escalado ya no ocurre, los radios ahora están anclados a la raíz. Si desea el tamaño predeterminado de la v25 nuevamente, reduzca a la mitad la semilla:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, reduce a la mitad toda la escala */
}
```

### Anillo de enfoque {#design-system-focus-ring}

El anillo de enfoque ahora utiliza un patrón de doble anillo: un pequeño espacio del color de superficie, seguido del anillo de color.

| Variable | v25 | v26 |
|---|---|---|
| `--dwc-focus-ring-width` | `3px` | `2px` |
| `--dwc-focus-ring-a`     | `0.4` | `0.75` |
| `--dwc-focus-ring-gap`   | (ninguno) | `2px` |
| `--dwc-focus-ring-l`     | `45%` | (eliminado, la claridad se calcula por modo) |

Si reserva espacio alrededor de elementos enfocados con `padding: var(--dwc-focus-ring-width)`, agregue el espacio a ese relleno para que el nuevo anillo tenga espacio para renderizar:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

### Ondas eliminadas {#design-system-ripples-removed}

Los efectos de onda estilo material ya no son utilizados por ningún componente de DWC. La nueva respuesta para cualquier elemento clickeable es un pequeño escalado a la baja:

```css
--dwc-scale-press: 0.97;      /* Reducción estándar del 3% */
--dwc-scale-press-deep: 0.93; /* Reducción mayor del 7% para botones */
```

El mixin SCSS `ripple` y la variable CSS `--dwc-ripple-color` siguen existiendo en la construcción, pero nada las importa por defecto. Si sus propios componentes optarán por el mixin, cambie a los tokens de escala de presión para coincidir con la nueva sensación.

### Duraciones de transición reequilibradas {#design-system-transitions}

| Variable | v25 | v26 |
|---|---|---|
| `--dwc-transition-slow`   | `500ms` | `300ms` |
| `--dwc-transition-medium` | `250ms` | `250ms` |
| `--dwc-transition-fast`   | `150ms` | `150ms` |
| `--dwc-transition-x-fast` | `50ms`  | `100ms` |

Si depende de una duración específica, sobrescríbala en `:root`.

### Lista de verificación pragmática de actualización {#design-system-checklist}

1. Busque `--dwc-color-*-c` y elimine esas declaraciones.
2. Busque `hsla(var(--dwc-shadow-color)` y reemplace con un token de sombra (`var(--dwc-shadow-m)`) o reescriba como `oklch(from ...)`.
3. Busque referencias directas de pasos de paleta (`--dwc-color-{name}-{number}`). Si alguna alimenta estilos específicos del modo oscuro, cambie a tokens de variación (`--dwc-color-{name}`, `-dark`, `-light`).
4. Busque referencias de tamaño de fuente nombradas (`--dwc-font-size-m`, `-l`, etc.). Si desea el tamaño v25, suba un nivel.
5. Busque `--dwc-font-weight-semibold`. Si quería `500`, cambie a `--dwc-font-weight-medium`.
6. Si reserva espacio alrededor de elementos enfocados con `--dwc-focus-ring-width`, agregue `--dwc-focus-ring-gap` al relleno.
7. Abra la aplicación, haga clic. La mayoría de las aplicaciones no necesitan nada más.
