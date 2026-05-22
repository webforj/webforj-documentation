---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
slug: /upgrading/webforj-26.00
pagination_next: null
sidebar_class_name: new-content
sidebar_position: 1
_i18n_hash: e62ee79be86c51d62fe19d10af89cc1b
---
Deze documentatie dient als een gids voor het upgraden van webforJ-apps van 25.00 naar 26.00. Hier zijn de wijzigingen die nodig zijn om bestaande apps soepel te laten draaien. Zoals altijd, zie de [GitHub-release-overzicht](https://github.com/webforj/webforj/releases) voor een meer uitgebreide lijst van wijzigingen tussen releases.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## POM-bestand wijzigingen {#pom-file-changes}

### Java 21 en 25 {#java-21-and-25}

webforJ 25.12 is de laatste versie die werkt met Java 17. Vanaf webforJ 26.00 heb je een Java-versie nodig dieJava 21 of Java 25 is, afhankelijk van je setup.

Installeer de vereiste Java-versie zoals vermeld in de [vereisten](/docs/introduction/prerequisites), en update vervolgens je pom.xml-bestand:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven-repository-URL {#maven-repository-url}

De locatie waar de snapshot-artikelen worden gehost, is veranderd. In je project’s pom.xml-bestand, heb je je afhankelijkheden gedownload van de [Central Portal](https://central.sonatype.com/).

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

### Spring Boot upgrade {#spring-boot-upgrade}

webforJ 25.12 is de laatste versie die Spring Boot 3.x gebruikt. Vanaf webforJ 26.00 moet je project Spring Boot 4.x gebruiken.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Verwijder overrides voor de Tomcat-versie
Met Spring Boot 4.x is Tomcat 11.x nu inbegrepen als afhankelijkheid, zodat je elke project-specifieke override voor de Tomcat-versie kunt verwijderen.
:::

## Tabel API wijzigingen {#table-api-changes}

### `IconRenderer` string-gebaseerde constructors {#iconrenderer-string-based-constructors}

De volgende string-gebaseerde constructors zijn verwijderd in 26.00; gebruik in plaats daarvan `IconDefinition`-gebaseerde constructors:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Verouderde selectie methoden {#deprecated-selection-methods}

Vanaf webforJ 26.00, in plaats van items in een `Table` te selecteren op basis van indices, selecteer je items in een Table met behulp van de item sleutel. Je kunt de `setKeyProvider()`-methode gebruiken om aangepaste sleutels voor de items in de tabel te bieden.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` of `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` of `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` of `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Selectie evenementen {#selection-events}

Om de verschuiving in de selectie van items in een `Table` verder te benadrukken, implementeert `TableItemSelectionChange` niet langer `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Niet-ondersteunde Webswing bootstrap opties {#unsupported-webswing-bootstrap-options}

De volgende `WebswingOptions`-methoden zijn verouderd en verwijderd in 26.00 omdat ze niet langer door de Webswing API worden ondersteund.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

De `PingParams`-klasse is ook verouderd. Degenen die deze methoden of de `PingParams`-klasse gebruikten, moeten in plaats daarvan de Webswing Admin Console gebruiken om de opties rechtstreeks te configureren.

## Filters voor `Repository` {#filters-for-repository}

De `RetrievalCriteria` en `RetrievalBuilder` interfaces zijn verwijderd in webforJ 26.00. In plaats van de generieke `Repository`-interface te gebruiken, gebruik je ofwel `RepositoryCriteria<T, F>`, `CollectionRepository` voor eenvoudige filters, of [`QueryableRepository`](/docs/advanced/repository/querying-data) voor meer geavanceerde filtertypes, sorteren en paginering.

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

### Verouderde repository methoden {#deprecated-repository-methods}

Gebruik de volgende tabel om de verouderde repository methoden te zien en welke methoden je voortaan moet gebruiken.

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

## Verwijdering van `WebforjBBjBridge` {#removal-of-webforjbbjbridge}

Vanaf webforJ 25.11 zijn WebforjBBjBridge en al zijn API's verwijderd. In plaats van de brug te benaderen, gebruikt webforJ nu de directe Java API om te communiceren met en toegang te krijgen tot vereiste BBj API's.

## Wijzigingen in het ontwerpsysteem (DWC 26) {#design-system-changes-dwc-26}

webforJ 26.00 levert versie 26 van het DWC ontwerpsysteem. De update is incrementeel in plaats van een volledige herschrijving: de meeste v25 CSS-variabelen blijven beschikbaar, de openbare token-API wordt behouden en bestaande aanpassingen blijven werken zonder wijzigingen.

Dit gedeelte vermeldt de breaking changes waarmee je mogelijk moet handelen. Voor het conceptuele overzicht, inclusief hoe de nieuwe kleurengine eruit ziet, hoe `--dwc-dark-mode` zich verspreidt, waarom ripples zijn verwijderd en de mechanica per gebied, zie [DWC 26 ontwerpsysteem](/docs/upgrading/webforj-26.00/design-system).

### Snelle beoordeling {#design-system-quick-verdict}

| Scenario | Wat te verwachten |
|---|---|
| Gebruikt standaardstyling | Visuele vernieuwing. Standaardpaletkleuren zijn opnieuw afgestemd (primaire kleur is verplaatst van `h: 211 / s: 100%` naar `h: 223 / s: 91%`), schaduwen lijken gelaagder, en componenten voelen ronder aan. Geen codewijzigingen nodig. |
| Overrides `--dwc-color-{name}-h` en `-s` | Werkt nog steeds. Het HSL-zaadpad is behouden. |
| Overrides individuele paletstappen (bijvoorbeeld `--dwc-color-primary-40`) | Stapnummers kunnen naar verschillende kleuren leiden. Zie [Kleurpaletmechanica](/docs/upgrading/webforj-26.00/design-system#the-color-system). |
| Afhankelijk van `--dwc-color-{name}-c` | Verwijderen. De lichte/donkere tekstwisseling wordt nu automatisch per schaduw berekend. |
| Verwijzingen naar benoemde font-size tokens (`--dwc-font-size-m`, `-l`, enzovoorts) | De schaal is met één bucket verlaagd. `m` is nu `14px` in plaats van `16px`. Zie [Typografie](#design-system-typography). |
| Gebruikt `--dwc-font-weight-semibold` om `500`-gewicht te krijgen | `semibold` is nu `600`. Schakel over naar de nieuwe `--dwc-font-weight-medium` voor `500`. |
| Reserves padding rond focusbare elementen met `--dwc-focus-ring-width` | De ring heeft nu een gat. Voeg `--dwc-focus-ring-gap` toe. Zie [Focus ring](#design-system-focus-ring). |
| Aangepaste knop hover/ripple-effecten | Ripples zijn verdwenen. Drukfeedback is nu een kleine schaling. |

### `--dwc-color-{name}-c` is verwijderd {#design-system-c-removed}

Als je overrides hebt voor `--dwc-color-{name}-c`, kun je die verwijderen, ze hebben geen effect. De lichte/donkere tekstwisseling wordt nu automatisch per schaduw berekend.

### Semantiek van `--dwc-color-{name}-alt` veranderd {#design-system-alt-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-color-{name}-alt` | Paletstap `95` (bijna-witte achtergrond) | Zaad bij 12% opaciteit (translucente tint) |

Als je `-alt` gebruikte als een stevige bijna-witte achtergrond, wordt het nu weergegeven als een translucente getinte overlay. Kies een specifieke stap (`--dwc-color-{name}-95`) of ontwerp rond de translucente semantiek.

### Semantiek van `--dwc-border-color-{name}` veranderd {#design-system-border-color-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-border-color-{name}` | Per variatie ingesteld als `var(--dwc-color-{name})` (de verzadigde tint) | Gecomputeerd in de generator: mode-bewuste verlichte toon van het zaad |

Als je CSS `--dwc-border-color-primary` leest en de verzadigde primaire kleur verwacht, is de visuele nu een subtiele scheidingston. Als je specifiek de verzadigde uitstraling wilt, schakel dan rechtstreeks over naar `--dwc-color-primary`.

### Formaat `--dwc-shadow-color` veranderd {#design-system-shadow-color-changed}

|  | v25 | v26 |
|---|---|---|
| `--dwc-shadow-color` | HSL-driehoek (`h, s%, l%`) | Volledige OKLCH-kleur |

Als je CSS het oude driehoeksvorm gebruikt zoals `hsla(var(--dwc-shadow-color), 0.07)`, schakel dan over naar een volledige schaduwtoken (`var(--dwc-shadow-m)`) of herschrijf met `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

### Typografie {#design-system-typography}

De font-schaal is opnieuw afgestemd, zodat de bucket-namen met één stap zijn verlaagd:

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

De standaard `--dwc-font-size` blijft nog steeds **14px**, het komt gewoon via `--dwc-font-size-m` (v26) in plaats van `--dwc-font-size-s` (v25). Als je CSS naar font-size tokens verwijst op basis van naam (bijvoorbeeld `font-size: var(--dwc-font-size-l)`), zal het zichtbare resultaat kleiner zijn in v26. Stap één bucket omhoog om de v25-grootte te behouden.

Fontgewichten hebben drie tokens gekregen (`thin`, `medium`, `black`) en één bestaand token is verschoven:

| Token | v25 | v26 |
|---|---|---|
| `--dwc-font-weight-semibold` | `500` | `600` |
| `--dwc-font-weight-medium`   | (bestond niet) | `500` |

Als je `--dwc-font-weight-semibold` gebruikte om 500-gewichttekst te krijgen, schakel dan over naar `--dwc-font-weight-medium`.

### Randradius {#design-system-border-radius}

|  | v25 | v26 |
|---|---|---|
| Eenheid | `em` (schaalt met de lettergrootte van de ouder) | `rem` (schaalt met de rootlettergrootte) |
| Standaard `--dwc-border-radius` | `--dwc-border-radius-s` (`4px`) | `--dwc-border-radius-seed` (`8px`) |
| Beschikbare stappen | tot `2xl` | voegt `3xl`, `4xl` toe |

Componenten voelen ronder aan vanaf de fabriek. Als een component genest binnen grotere tekst eerder een grotere radius erfde via `em`, vindt die schaalverandering nu niet plaats, radii zijn nu verankerd aan de root. Als je de v25 standaardgrootte wilt terugkrijgen, halveer dan het zaad:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, halveert de hele schaal */
}
```

### Focus ring {#design-system-focus-ring}

De focusring gebruikt nu een dubbel-ringpatroon: een kleine oppervlakgekleurde ruimte, gevolgd door de gekleurde ring.

| Variabele | v25 | v26 |
|---|---|---|
| `--dwc-focus-ring-width` | `3px` | `2px` |
| `--dwc-focus-ring-a`     | `0.4` | `0.75` |
| `--dwc-focus-ring-gap`   | (geen) | `2px` |
| `--dwc-focus-ring-l`     | `45%` | (verwijderd, helderheid wordt per modus berekend) |

Als je ruimte rond focusbare elementen reserveert met `padding: var(--dwc-focus-ring-width)`, voeg dan de gap toe aan die padding zodat de nieuwe ring ruimte heeft om te renderen:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

### Ripples verwijderd {#design-system-ripples-removed}

Materiaalstijl ripple-effecten worden door geen enkele DWC-component meer gebruikt. De nieuwe feedback voor elk klikbaar element is een kleine schaling naar beneden:

```css
--dwc-scale-press: 0.97;      /* Standaard 3% krimp */
--dwc-scale-press-deep: 0.93; /* Diepere 7% krimp voor knoppen */
```

De `ripple` SCSS-mixins en de `--dwc-ripple-color` CSS-variabele bestaan nog steeds in de build, maar niemand importeert ze standaard. Als je eigen componenten voor de mixin kozen, schakel dan over naar de druk-schaaltokens om aan te sluiten bij het nieuwe gevoel.

### Overgangsduren opnieuw in balans brengen {#design-system-transitions}

| Variabele | v25 | v26 |
|---|---|---|
| `--dwc-transition-slow`   | `500ms` | `300ms` |
| `--dwc-transition-medium` | `250ms` | `250ms` |
| `--dwc-transition-fast`   | `150ms` | `150ms` |
| `--dwc-transition-x-fast` | `50ms`  | `100ms` |

Als je van een specifieke duur afhankelijk bent, overschrijf deze dan in `:root`.

### Praktische upgrade checklist {#design-system-checklist}

1. Zoek naar `--dwc-color-*-c` en verwijder die declaraties.
2. Zoek naar `hsla(var(--dwc-shadow-color)` en vervang die door een schaduwtoken (`var(--dwc-shadow-m)`) of herschrijf als `oklch(from ...)`.
3. Zoek naar directe paletstapverwijzingen (`--dwc-color-{name}-{number}`). Als er donkere-modus-specifieke styling feedt, schakel dan over naar variatietokens (`--dwc-color-{name}`, `-dark`, `-light`).
4. Zoek naar benoemde font-grootte verwijzingen (`--dwc-font-size-m`, `-l`, enzovoorts). Als je de v25-grootte wilt, stap dan één bucket omhoog.
5. Zoek naar `--dwc-font-weight-semibold`. Als je `500` wilde, schakel dan over naar `--dwc-font-weight-medium`.
6. Als je ruimte rond focusbare elementen reserveert met `--dwc-focus-ring-width`, voeg dan `--dwc-focus-ring-gap` toe aan de padding.
7. Open de app, klik rond. De meeste apps hebben niets anders nodig.
