---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
slug: /upgrading/webforj-26.00
pagination_next: null
sidebar_class_name: new-content
sidebar_position: 1
---

This documentation serves as a guide to upgrade webforJ apps from 25.00 to 26.00.
Here are the changes needed for existing apps to continue running smoothly.
As always, see the [GitHub release overview](https://github.com/webforj/webforj/releases) for a more comprehensive list of changes between releases.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## POM file changes {#pom-file-changes}

### Java 21 and 25 {#java-21-and-25}

webforJ 25.12 is the last version that works with Java 17.
Starting with webforJ 26.00, you need a Java version that’s either Java 21 or Java 25, depending on your setup.

Install the required Java version as listed in the [prerequisites](/docs/introduction/prerequisites), then update your pom.xml file:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven repository URL {#maven-repository-url}

The location where the snapshot artifacts are hosted has changed. In your project’s pom.xml file, have you downloaded your dependencies from the [Central Portal](https://central.sonatype.com/).

**Before:**
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    ....
  </repository>
</repositories>
```

**After:**
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

### Spring Boot upgrade {#spring-boot-upgrade}

webforJ 25.12 is the last version that uses Spring Boot 3.x. 
Starting with webforJ 26.00, your project needs to use Spring Boot 4.x.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Removing overrides for the Tomcat version
With Spring Boot 4.x, Tomcat 11.x is now included as a dependency, so you can remove any project-specific override for the Tomcat version.
:::

## Table API changes {#table-api-changes}

### `IconRenderer` string-based constructors {#iconrenderer- string-based-constructors}

The following string-based constructors are removed in 26.00; use `IconDefinition`-based constructors instead:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Deprecated selection methods {#deprecated-selection-methods}

Starting in webforJ 26.00, instead of selecting items in a `Table` based on indices, select items in a Table using the item key. You can use the `setKeyProvider()` method to provide custom keys for the items in the table.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` or `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` or `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` or `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Selection events {#selection-events}

To further reinforce the shift in how to select items in a `Table`, `TableItemSelectionChange` no longer implements `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Unsupported Webswing bootstrap options {#unsupported-webswing-bootstrap-options}

The following `WebswingOptions` methods are deprecated and removed in 26.00 because they're no longer supported by the Webswing API.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

The `PingParams` class is also deprecated. Those who were using these methods or the `PingParams` class should instead use the Webswing Admin Console to directly configure the options.

## Filters for `Repository` {#filters-for-repository}

The `RetrievalCriteria` and `RetrievalBuilder` interfaces are removed in webforJ 26.00. Instead of using the generic `Repository` interface, use either `RepositoryCriteria<T, F>`, `CollectionRepository` for simple filters, or [`QueryableRepository`](/docs/advanced/repository/querying-data) for more advanced filtering types, sorting, and pagination.

**Before:**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

**After:**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### Deprecated repository methods {#deprecated-repository-methods}

Use the following table to see the deprecated repository methods and what methods to use going forward.

| v25 | v26 |
|---|---|
| `setFilter(...)` | `setBaseFilter(...)` |
| `getFilter()` | `getBaseFilter()` |
| `getOrderBy()` | `getOrderCriteriaList()` |
| `setOrderBy(Comparator<T>)` | `getOrderCriteriaList().add(new OrderCriteria<>(keyExtractor, direction))` |
| `findOneBy(...)` | `findBy(...)` then `.findFirst()` |
| `findBy(RetrievalCriteria<T>)` | `findBy(RepositoryCriteria<T, F>)` |
| `size(RetrievalCriteria<T>)` | `size(RepositoryCriteria<T, F>)` |
| `getIndex(T)` | `find(key)` or `findBy(criteria)` |
| `findByIndex(int)` | `find(key)` or `findBy(criteria)` |

## Removal of `WebforjBBjBridge` {#removal-of-webforjbbjbridge}

Starting with webforJ 25.11, WebforjBBjBridge and all of its APIs have been removed. Instead of accessing the bridge, webforJ now uses the direct Java API to communicate with and access any required BBj APIs.

## Design system changes (DWC 26) {#design-system-changes-dwc-26}

webforJ 26.00 ships with version 26 of the DWC design system. The update is incremental rather than a full rewrite: most v25 CSS variables remain available, the public token API is preserved, and existing customizations continue to work without changes.

This section lists the breaking changes you may need to act on. For the conceptual overview, including what the new color engine looks like, how `--dwc-dark-mode` propagates, why ripples were dropped, and the per-area mechanics, see [DWC 26 design system](/docs/upgrading/webforj-26.00/design-system).

### Quick verdict {#design-system-quick-verdict}

| Scenario | What to expect |
|---|---|
| Uses default styling | Visual refresh. Default palette hues were retuned (primary moved from `h: 211 / s: 100%` to `h: 223 / s: 91%`), shadows look more layered, and components feel rounder. No code change needed. |
| Overrides `--dwc-color-{name}-h` and `-s` | Still works. The HSL seed path is preserved. |
| Overrides individual palette steps (for example `--dwc-color-primary-40`) | Step numbers may resolve to different colors. See [Color palette mechanics](/docs/upgrading/webforj-26.00/design-system#the-color-system). |
| Relies on `--dwc-color-{name}-c` | Remove. The light/dark text flip is now computed automatically per shade. |
| References named font-size tokens (`--dwc-font-size-m`, `-l`, and so on) | The scale shifted down one bucket. `m` is now `14px` instead of `16px`. See [Typography](#design-system-typography). |
| Uses `--dwc-font-weight-semibold` to get `500`-weight | `semibold` is now `600`. Switch to the new `--dwc-font-weight-medium` for `500`. |
| Reserves padding around focusable elements with `--dwc-focus-ring-width` | The ring now has a gap. Add `--dwc-focus-ring-gap`. See [Focus ring](#design-system-focus-ring). |
| Customized button hover / ripple effects | Ripples are gone. Press feedback is now a small scale-down. |

### `--dwc-color-{name}-c` is removed {#design-system-c-removed}

If you have any `--dwc-color-{name}-c` overrides, you can delete them, they have no effect. The light/dark text flip is now computed automatically per shade.

### `--dwc-color-{name}-alt` semantics changed {#design-system-alt-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-color-{name}-alt` | Palette step `95` (near-white background) | Seed at 12% opacity (translucent tint) |

If you used `-alt` as a solid near-white background, it will now read as a translucent tinted overlay. Pick a specific step (`--dwc-color-{name}-95`) or design around the translucent semantic.

### `--dwc-border-color-{name}` semantics changed {#design-system-border-color-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-border-color-{name}` | Set per variation as `var(--dwc-color-{name})` (the saturated shade) | Computed in the generator: mode-aware lightened tone of the seed |

If your CSS reads `--dwc-border-color-primary` expecting the saturated primary color, the visual is now a subtle separator tone instead. If you specifically want the saturated look, switch to `--dwc-color-primary` directly.

### `--dwc-shadow-color` format changed {#design-system-shadow-color-changed}

|  | v25 | v26 |
|---|---|---|
| `--dwc-shadow-color` | HSL triplet (`h, s%, l%`) | Full OKLCH color |

If your CSS uses the legacy triplet form like `hsla(var(--dwc-shadow-color), 0.07)`, switch to a full shadow token (`var(--dwc-shadow-m)`) or rewrite with `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

### Typography {#design-system-typography}

The font scale was retuned so the bucket names shifted down by one step:

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

The default `--dwc-font-size` still resolves to **14px**, it just gets there via `--dwc-font-size-m` (v26) instead of `--dwc-font-size-s` (v25). If your CSS references font-size tokens by name (e.g. `font-size: var(--dwc-font-size-l)`), the visible result will be smaller in v26. Step up one bucket to preserve the v25 size.

Font weights gained three tokens (`thin`, `medium`, `black`) and one existing token shifted:

| Token | v25 | v26 |
|---|---|---|
| `--dwc-font-weight-semibold` | `500` | `600` |
| `--dwc-font-weight-medium`   | (didn't exist) | `500` |

If you used `--dwc-font-weight-semibold` to get 500-weight text, switch to `--dwc-font-weight-medium`.

### Border radius {#design-system-border-radius}

|  | v25 | v26 |
|---|---|---|
| Unit | `em` (scales with parent font-size) | `rem` (scales with root font-size) |
| Default `--dwc-border-radius` | `--dwc-border-radius-s` (`4px`) | `--dwc-border-radius-seed` (`8px`) |
| Steps available | up to `2xl` | adds `3xl`, `4xl` |

Components feel rounder out of the box. If a component nested inside larger text used to inherit a bigger radius via `em`, that scaling no longer happens, radii are now anchored to the root. If you want the v25 default size back, halve the seed:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, halves the whole scale */
}
```

### Focus ring {#design-system-focus-ring}

The focus ring now uses a double-ring pattern: a small surface-colored gap, then the colored ring.

| Variable | v25 | v26 |
|---|---|---|
| `--dwc-focus-ring-width` | `3px` | `2px` |
| `--dwc-focus-ring-a`     | `0.4` | `0.75` |
| `--dwc-focus-ring-gap`   | (none) | `2px` |
| `--dwc-focus-ring-l`     | `45%` | (removed, lightness is computed per mode) |

If you reserve space around focusable elements with `padding: var(--dwc-focus-ring-width)`, add the gap to that padding so the new ring has room to render:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

### Ripples removed {#design-system-ripples-removed}

Material-style ripple effects are no longer used by any DWC component. The new feedback for any clickable element is a small scale-down:

```css
--dwc-scale-press: 0.97;      /* Standard 3% shrink */
--dwc-scale-press-deep: 0.93; /* Deeper 7% shrink for buttons */
```

The `ripple` SCSS mixin and the `--dwc-ripple-color` CSS variable still exist in the build, but nothing imports them by default. If your own components opted into the mixin, switch to the press-scale tokens to match the new feel.

### Transition durations rebalanced {#design-system-transitions}

| Variable | v25 | v26 |
|---|---|---|
| `--dwc-transition-slow`   | `500ms` | `300ms` |
| `--dwc-transition-medium` | `250ms` | `250ms` |
| `--dwc-transition-fast`   | `150ms` | `150ms` |
| `--dwc-transition-x-fast` | `50ms`  | `100ms` |

If you depend on a specific duration, override it in `:root`.

### Pragmatic upgrade checklist {#design-system-checklist}

1. Search for `--dwc-color-*-c` and delete those declarations.
2. Search for `hsla(var(--dwc-shadow-color)` and replace with a shadow token (`var(--dwc-shadow-m)`) or rewrite as `oklch(from ...)`.
3. Search for direct palette step references (`--dwc-color-{name}-{number}`). If any feed dark-mode-specific styling, switch to variation tokens (`--dwc-color-{name}`, `-dark`, `-light`).
4. Search for named font-size references (`--dwc-font-size-m`, `-l`, and so on). If you want the v25 size, step up one bucket.
5. Search for `--dwc-font-weight-semibold`. If you wanted `500`, switch to `--dwc-font-weight-medium`.
6. If you reserve space around focusable elements with `--dwc-focus-ring-width`, add `--dwc-focus-ring-gap` to the padding.
7. Open the app, click around. Most apps need nothing else.
