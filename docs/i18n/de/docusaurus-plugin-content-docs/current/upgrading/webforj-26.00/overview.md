---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
slug: /upgrading/webforj-26.00
pagination_next: null
sidebar_position: 1
_i18n_hash: 3b9827a67a81e207508d7db72a650b64
---
Diese Dokumentation dient als Leitfaden zum Upgrade von webforJ-Apps von 25.00 auf 26.00. Hier sind die erforderlichen Änderungen, damit bestehende Apps weiterhin reibungslos funktionieren. Wie immer finden Sie die [GitHub-Release-Übersicht](https://github.com/webforj/webforj/releases) für eine umfassendere Liste der Änderungen zwischen den Releases.

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

Der Ort, an dem die Snapshot-Artefakte gehostet werden, hat sich geändert. In der pom.xml-Datei Ihres Projekts haben Sie Ihre Abhängigkeiten vom [Central Portal](https://central.sonatype.com/) heruntergeladen.

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
Mit Spring Boot 4.x ist Tomcat 11.x jetzt als Abhängigkeit enthalten, sodass Sie alle projektspezifischen Überschreibungen für die Tomcat-Version entfernen können.
:::

## Änderungen der Tabelle API {#table-api-changes}

### `IconRenderer` stringbasierte Konstruktoren {#iconrenderer-string-based-constructors}

Die folgenden stringbasierten Konstruktoren wurden in 26.00 entfernt; verwenden Sie stattdessen die Konstruktoren auf Basis von `IconDefinition`:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Veraltete Auswahlmethoden {#deprecated-selection-methods}

Ab webforJ 26.00 wählen Sie anstelle von Elementen in einer `Table` basierend auf Indizes Elemente in einer Tabelle mithilfe des Schlüssel des Elements aus. Sie können die Methode `setKeyProvider()` verwenden, um benutzerdefinierte Schlüssel für die Elemente in der Tabelle bereitzustellen.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` oder `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` oder `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` oder `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Auswahlereignisse {#selection-events}

Um den Wandel in der Auswahl von Elementen in einer `Table` weiter zu verstärken, implementiert `TableItemSelectionChange` nicht mehr `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Nicht unterstützte Webswing-Boot-Optionen {#unsupported-webswing-bootstrap-options}

Die folgenden `WebswingOptions`-Methoden sind in 26.00 veraltet und wurden entfernt, weil sie von der Webswing-API nicht mehr unterstützt werden.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

Die Klasse `PingParams` ist ebenfalls veraltet. Wer diese Methoden oder die Klasse `PingParams` verwendet hat, sollte stattdessen das Webswing Admin Console verwenden, um die Optionen direkt zu konfigurieren.

## Filter für `Repository` {#filters-for-repository}

Die `RetrievalCriteria`- und `RetrievalBuilder`-Schnittstellen wurden in webforJ 26.00 entfernt. Verwenden Sie anstelle der allgemeinen `Repository`-Schnittstelle entweder `RepositoryCriteria<T, F>`, `CollectionRepository` für einfache Filter oder [`QueryableRepository`](/docs/advanced/repository/querying-data) für komplexere Filtertypen, Sortierung und Seitennummerierung.

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

Verwenden Sie die folgende Tabelle, um die veralteten Repository-Methoden und welche Methoden Sie zukünftig verwenden sollten, zu sehen.

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

Ab webforJ 25.11 wurden WebforjBBjBridge und alle seine APIs entfernt. Anstelle des Zugriffs auf die Brücke verwendet webforJ jetzt die direkte Java-API, um mit etwaigen benötigten BBj-APIs zu kommunizieren und darauf zuzugreifen.

## Änderungen des Designsystems (DWC 26) {#design-system-changes-dwc-26}

webforJ 26.00 wird mit Version 26 des DWC-Designsystems ausgeliefert. Das Upgrade ist inkrementell und keine vollständige Neuschreibung: Die meisten CSS-Variablen von v25 bleiben verfügbar, die öffentliche Token-API wird beibehalten, und bestehende Anpassungen funktionieren ohne Änderungen.

In diesem Abschnitt werden die Breaking Changes aufgeführt, auf die Sie möglicherweise reagieren müssen. Für den konzeptionellen Überblick, einschließlich der Darstellung des neuen Farb-Engines, wie `--dwc-dark-mode` propagiert, warum Ripples entfernt wurden und die Mechanik pro Bereich, siehe [DWC 26 Designsystem](/docs/upgrading/webforj-26.00/design-system).

### Schnelles Urteil {#design-system-quick-verdict}

| Szenario | Was zu erwarten ist |
|---|---|
| Verwendet Standardstil | Visuelle Auffrischung. Die Standardpalette wurde neu abgestimmt (primär verschoben von `h: 211 / s: 100%` zu `h: 223 / s: 91%`), Schatten wirken mehrschichtig, und Komponenten fühlen sich runder an. Es sind keine Codeänderungen erforderlich. |
| Überschreibt `--dwc-color-{name}-h` und `-s` | Funktioniert weiterhin. Der HSL-Saatpfad bleibt erhalten. |
| Überschreibt einzelne Palettenstufen (zum Beispiel `--dwc-color-primary-40`) | Schrittzahlen können auf andere Farben verweist werden. Siehe [Farbpalettenmechanik](/docs/upgrading/webforj-26.00/design-system#the-color-system). |
| Verfügt über `--dwc-color-{name}-c` | Entfernen. Der hell/dunkel-Textwechsel wird jetzt automatisch pro Farbton berechnet. |
| Verweist auf benannte Schriftgrößen-Token (`--dwc-font-size-m`, `-l` usw.) | Die Skala wurde um einen Schritt nach unten verschoben. `m` ist jetzt `14px` statt `16px`. Siehe [Typografie](#design-system-typography). |
| Verwendet `--dwc-font-weight-semibold`, um `500`-Gewicht zu erhalten | `semibold` ist jetzt `600`. Wechseln Sie zu `--dwc-font-weight-medium` für `500`. |
| Reserviert Platz um fokussierbare Elemente mit `--dwc-focus-ring-width` | Der Ring hat jetzt eine Lücke. Fügen Sie `--dwc-focus-ring-gap` hinzu. Siehe [Fokusring](#design-system-focus-ring). |
| Angepasste Hover-/Rippleeffekte von Schaltflächen | Ripples sind verschwunden. Die Druckrückmeldung ist jetzt eine kleine Verkleinerung. |

### `--dwc-color-{name}-c` wurde entfernt {#design-system-c-removed}

Wenn Sie Überschreibungen für `--dwc-color-{name}-c` haben, können Sie diese entfernen, sie haben keine Wirkung. Der hell/dunkel-Textwechsel wird jetzt automatisch pro Farbton berechnet.

### Semantik von `--dwc-color-{name}-alt` geändert {#design-system-alt-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-color-{name}-alt` | Palettenstufe `95` (nahezu weißer Hintergrund) | Saat bei 12% Opazität (durchscheinender Farbton) |

Wenn Sie `-alt` als soliden nahezu weißen Hintergrund verwendet haben, wird es jetzt als durchscheinender getönter Overlay angesehen. Wählen Sie eine bestimmte Stufe (`--dwc-color-{name}-95`) oder entwerfen Sie um die durchscheinende Semantik.

### Semantik von `--dwc-border-color-{name}` geändert {#design-system-border-color-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-border-color-{name}` | Per Variation als `var(--dwc-color-{name})` (der gesättigte Farbton) festgelegt | Im Generator berechnet: modusbewusster, aufgehellter Farbton des Samens |

Wenn Ihr CSS `--dwc-border-color-primary` liest und den gesättigten Primärton erwartet, ist die Darstellung jetzt ein subtiler Separator-Farbton. Wenn Sie den gesättigten Look speziell wollen, wechseln Sie direkt zu `--dwc-color-primary`.

### Format von `--dwc-shadow-color` geändert {#design-system-shadow-color-changed}

|  | v25 | v26 |
|---|---|---|
| `--dwc-shadow-color` | HSL-Triade (`h, s%, l%`) | Vollständige OKLCH-Farbe |

Wenn Ihr CSS das Legacy-Triadenformat wie `hsla(var(--dwc-shadow-color), 0.07)` verwendet, wechseln Sie zu einem vollständigen Schatten-Token (`var(--dwc-shadow-m)`) oder schreiben Sie mit `oklch(from var(--dwc-shadow-color) l c h / 0.07)` um.

### Typografie {#design-system-typography}

Die Schriftartskala wurde neu abgestimmt, sodass die Bucketsnamen um einen Schritt nach unten verschoben wurden:

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

Die Standardgröße `--dwc-font-size` entspricht weiterhin **14px**, sie erreicht dies jedoch über `--dwc-font-size-m` (v26) anstelle von `--dwc-font-size-s` (v25). Wenn Ihr CSS auf Schriftgrößentoken nach Name verweist (z. B. `font-size: var(--dwc-font-size-l)`), wird das sichtbare Ergebnis in v26 kleiner sein. Steigen Sie um einen Bucket auf, um die v25-Größe beizubehalten.

Schriftgewichte haben drei Tokens (`thin`, `medium`, `black`) gewonnen, und ein bestehendes Token hat sich verschoben:

| Token | v25 | v26 |
|---|---|---|
| `--dwc-font-weight-semibold` | `500` | `600` |
| `--dwc-font-weight-medium`   | (existierte nicht) | `500` |

Wenn Sie `--dwc-font-weight-semibold` verwendet haben, um 500-Gewichtstext zu erhalten, wechseln Sie zu `--dwc-font-weight-medium`.

### Rahmenradius {#design-system-border-radius}

|  | v25 | v26 |
|---|---|---|
| Einheit | `em` (skaliert mit der Elternschriftgröße) | `rem` (skaliert mit der Schriftgröße der Wurzel) |
| Standard `--dwc-border-radius` | `--dwc-border-radius-s` (`4px`) | `--dwc-border-radius-seed` (`8px`) |
| Verfügbare Schritte | bis `2xl` | fügt `3xl`, `4xl` hinzu |

Komponenten fühlen sich von Grund auf runder an. Wenn eine Komponente, die in größeren Texten eingebettet ist, zuvor einen größeren Radius über `em` geerbt hat, geschieht dieses Scaling jetzt nicht mehr; Radien sind jetzt an die Wurzel gebunden. Wenn Sie die v25-Standardgröße zurückhaben möchten, halbieren Sie das Saatgut:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, halbiert die gesamte Skala */
}
```

### Fokusring {#design-system-focus-ring}

Der Fokusring verwendet jetzt ein Doppelringmuster: eine kleine, oberflächliche Lücke, dann den farbigen Ring.

| Variable | v25 | v26 |
|---|---|---|
| `--dwc-focus-ring-width` | `3px` | `2px` |
| `--dwc-focus-ring-a`     | `0.4` | `0.75` |
| `--dwc-focus-ring-gap`   | (keine) | `2px` |
| `--dwc-focus-ring-l`     | `45%` | (entfernt, Helligkeit wird pro Modus berechnet) |

Wenn Sie Platz um fokussierbare Elemente mit `padding: var(--dwc-focus-ring-width)` reservieren, fügen Sie die Lücke zu diesem Padding hinzu, sodass der neue Ring Platz hat, um gerendert zu werden:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

### Ripples entfernt {#design-system-ripples-removed}

Materialstil-Rippleeffekte werden von keiner DWC-Komponente mehr verwendet. Das neue Feedback für jedes klickbare Element ist eine kleine Verkleinerung:

```css
--dwc-scale-press: 0.97;      /* Standardmäßige 3% Verkleinerung */
--dwc-scale-press-deep: 0.93; /* Tiefe 7% Verkleinerung für Schaltflächen */
```

Der `ripple` SCSS-Mixin und die CSS-Variable `--dwc-ripple-color` existieren weiterhin im Build, aber nichts importiert sie standardmäßig. Wenn Ihre eigenen Komponenten auf den Mixin gesetzt haben, wechseln Sie zu den Press-Skala-Token, um das neue Gefühl anzupassen.

### Übergangsdauern neu ausbalanciert {#design-system-transitions}

| Variable | v25 | v26 |
|---|---|---|
| `--dwc-transition-slow`   | `500ms` | `300ms` |
| `--dwc-transition-medium` | `250ms` | `250ms` |
| `--dwc-transition-fast`   | `150ms` | `150ms` |
| `--dwc-transition-x-fast` | `50ms`  | `100ms` |

Wenn Sie von einer bestimmten Dauer abhängen, überschreiben Sie sie in `:root`.

### Praktische Upgrade-Checkliste {#design-system-checklist}

1. Suchen Sie nach `--dwc-color-*-c` und löschen Sie diese Deklarationen.
2. Suchen Sie nach `hsla(var(--dwc-shadow-color)` und ersetzen Sie dies durch ein Schatten-Token (`var(--dwc-shadow-m)`) oder schreiben Sie als `oklch(from ...)` um.
3. Suchen Sie nach direkten Palettenstufenreferenzen (`--dwc-color-{name}-{number}`). Wenn diese dunkelspezifische Stile bedienen, wechseln Sie zu Variations-Token (`--dwc-color-{name}`, `-dark`, `-light`).
4. Suchen Sie nach benannten Schriftgrößenreferenzen (`--dwc-font-size-m`, `-l` usw.). Wenn Sie die v25-Größe wünschen, steigen Sie um einen Bucket auf.
5. Suchen Sie nach `--dwc-font-weight-semibold`. Wenn Sie `500` wollten, wechseln Sie zu `--dwc-font-weight-medium`.
6. Wenn Sie Platz um fokussierbare Elemente mit `--dwc-focus-ring-width` reservieren, fügen Sie `--dwc-focus-ring-gap` zum Padding hinzu.
7. Öffnen Sie die App, klicken Sie herum. Die meisten Apps benötigen nichts anderes.
