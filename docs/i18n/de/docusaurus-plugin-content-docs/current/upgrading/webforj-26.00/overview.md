---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
slug: /upgrading/webforj-26.00
pagination_next: null
sidebar_class_name: new-content
sidebar_position: 1
_i18n_hash: e62ee79be86c51d62fe19d10af89cc1b
---
Diese Dokumentation dient als Leitfaden zum Upgrade von webforJ-Anwendungen von 25.00 auf 26.00. Hier sind die erforderlichen Änderungen, damit bestehende Anwendungen weiterhin reibungslos funktionieren. Wie immer finden Sie in der [GitHub Release-Übersicht](https://github.com/webforj/webforj/releases) eine umfassendere Liste der Änderungen zwischen den Versionen.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## POM-Datei Änderungen {#pom-file-changes}

### Java 21 und 25 {#java-21-and-25}

webforJ 25.12 ist die letzte Version, die mit Java 17 funktioniert. Ab webforJ 26.00 benötigen Sie eine Java-Version, die entweder Java 21 oder Java 25 ist, abhängig von Ihrem Setup.

Installieren Sie die erforderliche Java-Version, wie in den [Voraussetzungen](/docs/introduction/prerequisites) aufgeführt, und aktualisieren Sie dann Ihre pom.xml-Datei:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven-Repository-URL {#maven-repository-url}

Der Speicherort, an dem die Snapshot-Artefakte gehostet werden, hat sich geändert. In der pom.xml-Datei Ihres Projekts haben Sie Ihre Abhängigkeiten vom [Central Portal](https://central.sonatype.com/) heruntergeladen.

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

### Spring Boot Upgrade {#spring-boot-upgrade}

webforJ 25.12 ist die letzte Version, die Spring Boot 3.x verwendet. Ab webforJ 26.00 muss Ihr Projekt Spring Boot 4.x verwenden.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Entfernen von Überschreibungen für die Tomcat-Version
Mit Spring Boot 4.x ist Tomcat 11.x jetzt als Abhängigkeit enthalten, sodass Sie spezielle Überschreibungen für die Tomcat-Version in Ihrem Projekt entfernen können.
:::

## Änderungen der Tabellen-API {#table-api-changes}

### `IconRenderer`-String-basierte Konstruktoren {#iconrenderer-string-based-constructors}

Die folgenden string-basierten Konstruktoren wurden in 26.00 entfernt; verwenden Sie stattdessen `IconDefinition`-basierte Konstruktoren:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Veraltete Auswahlmethoden {#deprecated-selection-methods}

Ab webforJ 26.00 wählen Sie anstelle von Indizes Gegenstände in einer `Table` aus, indem Sie den Gegenstandsschlüssel verwenden. Sie können die Methode `setKeyProvider()` verwenden, um benutzerdefinierte Schlüssel für die Elemente in der Tabelle bereitzustellen.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` oder `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` oder `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` oder `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Auswahlereignisse {#selection-events}

Um den Wechsel in der Auswahl von Elementen in einer `Table` weiter zu stärken, implementiert `TableItemSelectionChange` nicht mehr `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Nicht unterstützte Webswing-Bootstraßoptionen {#unsupported-webswing-bootstrap-options}

Die folgenden Methoden von `WebswingOptions` sind in 26.00 veraltet und wurden entfernt, da sie von der Webswing-API nicht mehr unterstützt werden.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

Die Klasse `PingParams` ist ebenfalls veraltet. Benutzer, die diese Methoden oder die Klasse `PingParams` verwendet haben, sollten stattdessen das Webswing-Admin-Konsole verwenden, um die Optionen direkt zu konfigurieren.

## Filter für `Repository` {#filters-for-repository}

Die Schnittstellen `RetrievalCriteria` und `RetrievalBuilder` wurden in webforJ 26.00 entfernt. Anstelle der generischen `Repository`-Schnittstelle verwenden Sie entweder `RepositoryCriteria<T, F>`, `CollectionRepository` für einfache Filter oder [`QueryableRepository`](/docs/advanced/repository/querying-data) für fortgeschrittenere Filtertypen, Sortierungen und Paginierung.

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

### Veraltete Repository-Methoden {#deprecated-repository-methods}

Verwenden Sie die folgende Tabelle, um die veralteten Repository-Methoden und welche Methoden Sie in Zukunft verwenden sollten, zu sehen.

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

Ab webforJ 25.11 wurden WebforjBBjBridge und alle seine APIs entfernt. Anstelle des Zugriffs auf die Brücke verwendet webforJ nun die direkte Java-API, um mit erforderlichen BBj-APIs zu kommunizieren und darauf zuzugreifen.

## Änderungen im Designsystem (DWC 26) {#design-system-changes-dwc-26}

webforJ 26.00 wird mit Version 26 des DWC-Designsystems ausgeliefert. Das Update ist inkrementell und kein vollständiger Rewrite: die meisten CSS-Variablen von v25 bleiben verfügbar, die öffentliche Token-API ist erhalten geblieben und vorhandene Anpassungen funktionieren weiterhin ohne Änderungen.

In diesem Abschnitt sind die wesentlichen Änderungen aufgelistet, auf die Sie möglicherweise reagieren müssen. Für die konzeptionelle Übersicht, einschließlich wie der neue Farben-Engine aussieht, wie `--dwc-dark-mode` propagiert wird, warum Ripples fallen gelassen wurden und die Mechanik pro Bereich, siehe [DWC 26 Designsystem](/docs/upgrading/webforj-26.00/design-system).

### Schnelles Urteil {#design-system-quick-verdict}

| Szenario | Was zu erwarten ist |
|---|---|
| Verwendet Standardstyling | Visuelle Auffrischung. Die Standardpalette wurde angepasst (primär wurde von `h: 211 / s: 100%` auf `h: 223 / s: 91%` verschoben), Schatten sehen geschichteter aus und Komponenten wirken runder. Keine Codeänderung erforderlich. |
| Überschreibt `--dwc-color-{name}-h` und `-s` | Funktioniert weiterhin. Der HSL-Seed-Pfad ist erhalten geblieben. |
| Überschreibt individuelle Palettenstufen (zum Beispiel `--dwc-color-primary-40`) | Die Schrittzahlen können zu anderen Farben führen. Siehe [Farbenpalettenmechanik](/docs/upgrading/webforj-26.00/design-system#the-color-system). |
| Verwendet `--dwc-color-{name}-c` | Entfernen. Der Licht/Dunkel-Textwechsel wird jetzt automatisch pro Farbton berechnet. |
| Verweist auf benannte Schriftgrößentoken (`--dwc-font-size-m`, `-l` und so weiter) | Der Maßstab wurde um einen Schritt nach unten verschoben. `m` ist jetzt `14px` statt `16px`. Siehe [Typografie](#design-system-typography). |
| Verwendet `--dwc-font-weight-semibold`, um `500`-Gewicht zu erhalten | `semibold` ist jetzt `600`. Wechseln Sie zu dem neuen `--dwc-font-weight-medium` für `500`. |
| Reserviert Platz um fokussierbare Elemente mit `--dwc-focus-ring-width` | Der Ring hat jetzt einen Abstand. Fügen Sie `--dwc-focus-ring-gap` hinzu. Siehe [Fokusring](#design-system-focus-ring). |
| Angepasste Schaltflächen-Hover-/Ripple-Effekte | Ripples sind verschwunden. Der Druck-Feedback ist jetzt ein kleines Herunterskalieren. |

### `--dwc-color-{name}-c` wurde entfernt {#design-system-c-removed}

Wenn Sie irgendwelche `--dwc-color-{name}-c`-Überschreibungen haben, können Sie diese löschen, sie haben keine Wirkung. Der Licht/Dunkel-Textwechsel wird jetzt automatisch pro Farbton berechnet.

### Semantik von `--dwc-color-{name}-alt` geändert {#design-system-alt-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-color-{name}-alt` | Palettenstufe `95` (nahezu weiße Hintergrund) | Seed bei 12% Opazität (durchscheinender Farbton) |

Wenn Sie `-alt` als soliden nahezu weißen Hintergrund verwendet haben, wird dies jetzt als durchscheinender getönter Overlay angezeigt. Wählen Sie eine spezifische Stufe (`--dwc-color-{name}-95`) oder gestalten Sie um die durchscheinende Semantik.

### Semantik von `--dwc-border-color-{name}` geändert {#design-system-border-color-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-border-color-{name}` | Pro Variation als `var(--dwc-color-{name})` (der gesättigte Farbton) festgelegt | Im Generator berechnet: modusbewusster aufgehellter Farbton des Seeds |

Wenn Ihr CSS `--dwc-border-color-primary` liest und den gesättigten Primärfarbton erwartet, ist die visuelle Darstellung jetzt ein subtiler Trennungsfarbton. Wenn Sie den gesättigten Look speziell wollen, wechseln Sie direkt zu `--dwc-color-primary`.

### Format von `--dwc-shadow-color` geändert {#design-system-shadow-color-changed}

|  | v25 | v26 |
|---|---|---|
| `--dwc-shadow-color` | HSL-Triplett (`h, s%, l%`) | Volles OKLCH-Farbsystem |

Wenn Ihr CSS das alte Triplett-Format wie `hsla(var(--dwc-shadow-color), 0.07)` verwendet, wechseln Sie zu einem vollständigen Schatten-Token (`var(--dwc-shadow-m)`) oder schreiben Sie mit `oklch(from var(--dwc-shadow-color) l c h / 0.07)` um.

### Typografie {#design-system-typography}

Der Schriftmaßstab wurde angepasst, sodass die Bucket-Namen um einen Schritt nach unten verschoben wurden:

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

Die Standardgröße `--dwc-font-size` bleibt **14px**, erreicht wird sie jetzt über `--dwc-font-size-m` (v26) anstelle von `--dwc-font-size-s` (v25). Wenn Ihr CSS Schriftgrößentoken nach Namen referenziert (z. B. `font-size: var(--dwc-font-size-l)`), wird das sichtbare Ergebnis in v26 kleiner sein. Steigen Sie um einen Schritt auf, um die Größe von v25 beizubehalten.

Schriftstärken erhielten drei Token (`thin`, `medium`, `black`) und ein vorhandenes Token wurde verschoben:

| Token | v25 | v26 |
|---|---|---|
| `--dwc-font-weight-semibold` | `500` | `600` |
| `--dwc-font-weight-medium`   | (existierte nicht) | `500` |

Wenn Sie `--dwc-font-weight-semibold` verwendet haben, um 500-Gewichtstext zu erhalten, wechseln Sie zu `--dwc-font-weight-medium`.

### Border-Radius {#design-system-border-radius}

|  | v25 | v26 |
|---|---|---|
| Einheit | `em` (skaliert mit der Schriftgröße des übergeordneten Elements) | `rem` (skaliert mit der Schriftgröße des Wurzel-Elements) |
| Standard `--dwc-border-radius` | `--dwc-border-radius-s` (`4px`) | `--dwc-border-radius-seed` (`8px`) |
| Verfügbare Schritte | Bis zu `2xl` | Fügt `3xl`, `4xl` hinzu |

Komponenten wirken out of the box runder. Wenn eine Komponente, die sich in größerem Text befindet, über `em` einen größeren Radius geerbt hat, geschieht diese Skalierung nicht mehr, die Radien sind jetzt an der Wurzel verankert. Wenn Sie die Standardgröße von v25 zurückhaben möchten, halbieren Sie den Seed:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, halbiert die gesamte Skala */
}
```

### Fokusring {#design-system-focus-ring}

Der Fokusring verwendet jetzt ein Doppelringmuster: einen kleinen, oberflächenfarbenen Abstand, gefolgt vom farbigen Ring.

| Variable | v25 | v26 |
|---|---|---|
| `--dwc-focus-ring-width` | `3px` | `2px` |
| `--dwc-focus-ring-a`     | `0.4` | `0.75` |
| `--dwc-focus-ring-gap`   | (keine) | `2px` |
| `--dwc-focus-ring-l`     | `45%` | (entfernt, Helligkeit wird pro Modus berechnet) |

Wenn Sie Platz um fokussierbare Elemente mit `padding: var(--dwc-focus-ring-width)` reservieren, fügen Sie den Abstand zu diesem Padding hinzu, damit der neue Ring Platz zum Rendern hat:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

### Ripples entfernt {#design-system-ripples-removed}

Material-ähnliche Ripple-Effekte werden von keiner DWC-Komponente mehr verwendet. Das neue Feedback für jedes klickbare Element ist ein kleines Herunterskalieren:

```css
--dwc-scale-press: 0.97;      /* Standard 3% Verkleinerung */
--dwc-scale-press-deep: 0.93; /* Tiefere 7% Verkleinerung für Schaltflächen */
```

Der `ripple` SCSS-Mixin und die CSS-Variable `--dwc-ripple-color` existieren weiterhin im Build, aber nichts importiert sie standardmäßig. Wenn Ihre eigenen Komponenten das Mixin verwendet haben, wechseln Sie zu den Druck-Skalierungs-Token, um sich an das neue Gefühl anzupassen.

### Übergangsdauern neu austariert {#design-system-transitions}

| Variable | v25 | v26 |
|---|---|---|
| `--dwc-transition-slow`   | `500ms` | `300ms` |
| `--dwc-transition-medium` | `250ms` | `250ms` |
| `--dwc-transition-fast`   | `150ms` | `150ms` |
| `--dwc-transition-x-fast` | `50ms`  | `100ms` |

Wenn Sie von einer bestimmten Dauer abhängen, überschreiben Sie sie in `:root`.

### Praktische Upgrade-Checkliste {#design-system-checklist}

1. Suchen Sie nach `--dwc-color-*-c` und löschen Sie diese Deklarationen.
2. Suchen Sie nach `hsla(var(--dwc-shadow-color)` und ersetzen Sie sie durch ein Schatten-Token (`var(--dwc-shadow-m)`) oder schreiben Sie als `oklch(from ...)` um.
3. Suchen Sie nach direkten Palettenstufenreferenzen (`--dwc-color-{name}-{number}`). Wenn diese spezifisches Styling für den Dunkelmodus fördern, wechseln Sie zu Variations-Token (`--dwc-color-{name}`, `-dark`, `-light`).
4. Suchen Sie nach benannten Schriftgrößenreferenzen (`--dwc-font-size-m`, `-l` und so weiter). Wenn Sie die Größe von v25 möchten, gehen Sie um einen Schritt hoch.
5. Suchen Sie nach `--dwc-font-weight-semibold`. Wenn Sie `500` möchten, wechseln Sie zu `--dwc-font-weight-medium`.
6. Wenn Sie Platz um fokussierbare Elemente mit `--dwc-focus-ring-width` reservieren, fügen Sie `--dwc-focus-ring-gap` dem Padding hinzu.
7. Öffnen Sie die App und klicken Sie herum. Die meisten Apps benötigen nichts Weiteres.
