---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
slug: /upgrading/webforj-26.00
pagination_next: null
sidebar_position: 1
_i18n_hash: 3b9827a67a81e207508d7db72a650b64
---
Deze documentatie dient als een gids voor het upgraden van webforJ-apps van 25.00 naar 26.00.  
Hier zijn de wijzigingen die nodig zijn voor bestaande apps om soepel te blijven draaien.  
Zoals altijd, zie de [GitHub release-overzicht](https://github.com/webforj/webforj/releases) voor een meer uitgebreide lijst van wijzigingen tussen de versies.

## POM-bestand wijzigingen {#pom-file-changes}

### Java 21 en 25 {#java-21-and-25}

webforJ 25.12 is de laatste versie die werkt met Java 17.  
Met de start van webforJ 26.00 heb je een Java-versie nodig die ofwel Java 21 of Java 25 is, afhankelijk van je opstelling.

Installeer de vereiste Java-versie zoals vermeld in de [vereisten](/docs/introduction/prerequisites), en werk vervolgens je pom.xml-bestand bij:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven-repository-URL {#maven-repository-url}

De locatie waar de snapshot-artifacten worden gehost, is veranderd. Heb je in het pom.xml-bestand van je project je afhankelijkheden gedownload van het [Central Portal](https://central.sonatype.com/)?

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

**Daarna:**
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

webforJ 25.12 is de laatste versie die Spring Boot 3.x gebruikt.  
Met de start van webforJ 26.00 moet je project Spring Boot 4.x gebruiken.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Verwijderen van overrides voor de Tomcat-versie  
Met Spring Boot 4.x is Tomcat 11.x nu inbegrepen als een afhankelijkheid, dus je kunt elke project-specifieke override voor de Tomcat-versie verwijderen.  
:::

## Wijzigingen in de Table API {#table-api-changes}

### `IconRenderer` string-gebaseerde constructeurs {#iconrenderer-string-based-constructors}

De volgende string-gebaseerde constructeurs zijn verwijderd in 26.00; gebruik in plaats daarvan `IconDefinition`-gebaseerde constructeurs:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Verlopen selectiemethoden {#deprecated-selection-methods}

Met de start van webforJ 26.00, kies in plaats van items in een `Table` op basis van indices, items in een tafel met behulp van de item sleutel. Je kunt de methode `setKeyProvider()` gebruiken om aangepaste sleutels voor de items in de tabel te verstrekken.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` of `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` of `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` of `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Selectie-events {#selection-events}

Om de verschuiving in hoe items in een `Table` te selecteren verder te benadrukken, implementeert `TableItemSelectionChange` niet langer `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Niet-ondersteunde Webswing bootstrap-opties {#unsupported-webswing-bootstrap-options}

De volgende `WebswingOptions`-methoden zijn verouderd en verwijderd in 26.00 omdat ze niet langer worden ondersteund door de Webswing API.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

De klasse `PingParams` is ook verouderd. Degenen die deze methoden of de klasse `PingParams` gebruikten, moeten in plaats daarvan de Webswing Admin Console gebruiken om de opties rechtstreeks te configureren.

## Filters voor `Repository` {#filters-for-repository}

De interfaces `RetrievalCriteria` en `RetrievalBuilder` zijn verwijderd in webforJ 26.00. In plaats van de algemene `Repository`-interface te gebruiken, gebruik je ofwel `RepositoryCriteria<T, F>`, `CollectionRepository` voor eenvoudige filters, of [`QueryableRepository`](/docs/advanced/repository/querying-data) voor meer geavanceerde filteringstypen, sorteren en paginering.

**Voorheen:**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
});
```

**Daarna:**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
});
```

### Verlopen repository-methoden {#deprecated-repository-methods}

Gebruik de volgende tabel om de verouderde repository-methoden te zien en welke methoden je in de toekomst moet gebruiken.

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

Met de start van webforJ 25.11 is WebforjBBjBridge en al zijn API's verwijderd. In plaats van de brug te benaderen, gebruikt webforJ nu de directe Java API om te communiceren met en toegang te krijgen tot benodigde BBj API's.

## Wijzigingen in het ontwerp systeem (DWC 26) {#design-system-changes-dwc-26}

webforJ 26.00 wordt geleverd met versie 26 van het DWC-ontwerp systeem. De update is incrementeel in plaats van een volledige herschrijving: de meeste v25 CSS-variabelen blijven beschikbaar, de publieke token-API wordt behouden en bestaande aanpassingen blijven werken zonder wijzigingen.

Deze sectie vermeldt de breekbare wijzigingen waarmee je rekening moet houden. Voor het conceptuele overzicht, inclusief hoe de nieuwe kleurengine eruitziet, hoe `--dwc-dark-mode` zich verspreidt, waarom ripples zijn verwijderd, en de mechanica per gebied, zie [DWC 26 ontwerpsysteem](/docs/upgrading/webforj-26.00/design-system).

### Snelle uitspraak {#design-system-quick-verdict}

| Scenario | Wat te verwachten |
|---|---|
| Gebruik standaard opmaak | Visuele vernieuwing. Standaard paletkleuren zijn opnieuw afgesteld (primaire kleur is verplaatst van `h: 211 / s: 100%` naar `h: 223 / s: 91%`), schaduwen komen meer gelaagd over, en componenten voelen ronder aan. Geen codewijziging nodig. |
| Overschrijft `--dwc-color-{name}-h` en `-s` | Werkt nog steeds. Het HSL-zaadpad is bewaard gebleven. |
| Overschrijft individuele paletstappen (bijvoorbeeld `--dwc-color-primary-40`) | Stapnummers kunnen naar verschillende kleuren verwijzen. Zie [Kleur paletmechanica](/docs/upgrading/webforj-26.00/design-system#the-color-system). |
| Vertrouwt op `--dwc-color-{name}-c` | Verwijder. De licht/donker tekstomkering wordt nu automatisch per schaduw berekend. |
| Verwijst naar benoemde font-grootte tokens (`--dwc-font-size-m`, `-l`, enzovoort) | De schaal is verlaagd met één stap. `m` is nu `14px` in plaats van `16px`. Zie [Typografie](#design-system-typography). |
| Gebruikt `--dwc-font-weight-semibold` om `500`-gewicht te krijgen | `semibold` is nu `600`. Schakel over naar de nieuwe `--dwc-font-weight-medium` voor `500`. |
| Reserves padding rond focusbare elementen met `--dwc-focus-ring-width` | De ring heeft nu een gat. Voeg `--dwc-focus-ring-gap` toe. Zie [Focusring](#design-system-focus-ring). |
| Aangepaste hover/ripple-effecten van knoppen | Ripples zijn verdwenen. De drukfeedback is nu een kleine schaalreductie. |

### `--dwc-color-{name}-c` is verwijderd {#design-system-c-removed}

Als je een of andere `--dwc-color-{name}-c` overschrijvingen hebt, kun je ze verwijderen, ze hebben geen effect. De licht/donker tekstomkering wordt nu automatisch berekend per schaduw.

### Semantiek van `--dwc-color-{name}-alt` veranderd {#design-system-alt-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-color-{name}-alt` | Paletstap `95` (bijna-witte achtergrond) | Zaad met 12% doorzichtigheid (doorzichtige tint) |

Als je `-alt` gebruikte als een solide bijna-witte achtergrond, wordt het nu weergegeven als een doorzichtige getinte overlay. Kies een specifieke stap (`--dwc-color-{name}-95`) of ontwerp rondom de doorzichtige semantiek.

### Semantiek van `--dwc-border-color-{name}` veranderd {#design-system-border-color-changed}

| Token | v25 | v26 |
|---|---|---|
| `--dwc-border-color-{name}` | Op elke variant ingesteld als `var(--dwc-color-{name})` (de verzadigde tint) | Gemaakt in de generator: modus-bewuste verlichte toon van het zaad |

Als je CSS `--dwc-border-color-primary` leest in de verwachting de verzadigde primaire kleur te krijgen, is de visuele aankleding nu een subtiele scheidingston in plaats daarvan. Als je specifiek de verzadigde uitstraling wilt, schakel dan rechtstreeks over naar `--dwc-color-primary`.

### Formaat van `--dwc-shadow-color` veranderd {#design-system-shadow-color-changed}

|  | v25 | v26 |
|---|---|---|
| `--dwc-shadow-color` | HSL-driehoek (`h, s%, l%`) | Volledige OKLCH-kleur |

Als je CSS de legacy-driehoekvorm gebruikt zoals `hsla(var(--dwc-shadow-color), 0.07)`, schakel dan over naar een volledige schaduwtoken (`var(--dwc-shadow-m)`) of herschrijf met `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

### Typografie {#design-system-typography}

De lettertype-schaal is opnieuw afgestemd, zodat de benoemingen van de bakken met één stap zijn verschoven:

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

De standaard `--dwc-font-size` blijft nog steeds resolvable op **14px**, het bereikt het gewoon via `--dwc-font-size-m` (v26) in plaats van `--dwc-font-size-s` (v25). Als je CSS font-grootte tokens bij naam verwijst (bijv. `font-size: var(--dwc-font-size-l)`), zal het zichtbare resultaat kleiner zijn in v26. Stap één bakje omhoog om de grootte van v25 te behouden.

Fontgewichten hebben drie tokens gekregen (`thin`, `medium`, `black`) en één bestaand token is verschoven:

| Token | v25 | v26 |
|---|---|---|
| `--dwc-font-weight-semibold` | `500` | `600` |
| `--dwc-font-weight-medium`   | (bestond niet) | `500` |

Als je `--dwc-font-weight-semibold` gebruikte om 500-gewichttekst te krijgen, schakel dan over naar `--dwc-font-weight-medium`.

### Border radius {#design-system-border-radius}

|  | v25 | v26 |
|---|---|---|
| Eenheid | `em` (schaalt met de lettergrootte van de ouder) | `rem` (schaalt met de lettergrootte van de wortel) |
| Standaard `--dwc-border-radius` | `--dwc-border-radius-s` (`4px`) | `--dwc-border-radius-seed` (`8px`) |
| Beschikbare stappen | tot `2xl` | voegt `3xl`, `4xl` toe |

Componenten voelen uit de doos ronder aan. Als een component genesteld in grotere tekst eerder een grotere straal erfde via `em`, gebeurt die schaling nu niet meer, radii zijn nu verankerd aan de wortel. Als je de standaardgrootte van v25 wilt behouden, halveer dan het zaad:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, halveert de hele schaal */
}
```

### Focusring {#design-system-focus-ring}

De focusring gebruikt nu een dubbel-ringpatroon: een kleine oppervlakte-kleurige opening, gevolgd door de gekleurde ring.

| Variabele | v25 | v26 |
|---|---|---|
| `--dwc-focus-ring-width` | `3px` | `2px` |
| `--dwc-focus-ring-a`     | `0.4` | `0.75` |
| `--dwc-focus-ring-gap`   | (geen) | `2px` |
| `--dwc-focus-ring-l`     | `45%` | (verwijderd, lichtheid wordt per modus berekend) |

Als je ruimte rond focusbare elementen reserveert met `padding: var(--dwc-focus-ring-width)`, voeg dan de opening toe aan die padding zodat de nieuwe ring ruimte heeft om te renderen:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

### Ripples verwijderd {#design-system-ripples-removed}

Material-stijl ripple-effecten worden niet langer gebruikt door enige DWC-component. De nieuwe feedback voor elk klikbaar element is een kleine schaalreductie:

```css
--dwc-scale-press: 0.97;      /* Standaard 3% krimp */
--dwc-scale-press-deep: 0.93; /* Diepere 7% krimp voor knoppen */
```

De `ripple` SCSS-mixin en de `--dwc-ripple-color` CSS-variabele bestaan nog steeds in de build, maar niets importeert ze standaard. Als je eigen componenten zich bij de mixin voegden, schakel dan over naar de druk-schaal tokens om bij het nieuwe gevoel te passen.

### Overgangsduur herbalanceerd {#design-system-transitions}

| Variabele | v25 | v26 |
|---|---|---|
| `--dwc-transition-slow`   | `500ms` | `300ms` |
| `--dwc-transition-medium` | `250ms` | `250ms` |
| `--dwc-transition-fast`   | `150ms` | `150ms` |
| `--dwc-transition-x-fast` | `50ms`  | `100ms` |

Als je afhankelijk bent van een specifieke duur, overschrijf deze in `:root`.

### Pragmatic upgrade checklist {#design-system-checklist}

1. Zoek naar `--dwc-color-*-c` en verwijder die declaraties.
2. Zoek naar `hsla(var(--dwc-shadow-color)` en vervang deze door een schaduwtoken (`var(--dwc-shadow-m)`) of herschrijf als `oklch(from ...)`.
3. Zoek naar directe referenties naar paletstappen (`--dwc-color-{name}-{number}`). Als er styling specifiek voor donkere modus aan wordt gevoerd, schakel dan over naar variatietokens (`--dwc-color-{name}`, `-dark`, `-light`).
4. Zoek naar benoemde font-grootte referenties (`--dwc-font-size-m`, `-l`, enzovoort). Als je de grootte van v25 wilt, stap dan één bakje omhoog.
5. Zoek naar `--dwc-font-weight-semibold`. Als je `500` wilde, schakel dan over naar `--dwc-font-weight-medium`.
6. Als je ruimte rond focusbare elementen reserveert met `--dwc-focus-ring-width`, voeg `--dwc-focus-ring-gap` toe aan de padding.
7. Open de app, klik rond. De meeste apps hebben niets anders nodig.
