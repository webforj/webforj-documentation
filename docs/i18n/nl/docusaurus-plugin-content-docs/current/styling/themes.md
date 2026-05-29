---
sidebar_position: 2
title: Themes
sidebar_class_name: updated-content
description: >-
  Apply built-in light, dark, and dark-pure themes with @AppTheme or define
  custom themes through data-app-theme selectors.
_i18n_hash: 91e1a18f11aadea66df804dbaa4917d9
---
Een thema in webforJ is een genaamd stel CSS-aangepaste eigenschappen (ontwerptokens) dat bepaalt hoe elk component eruitziet. Het schakelen tussen thema's herberekent kleuren, schaduwen, oppervlakken en randen onmiddellijk door de hele app, zonder opnieuw op te bouwen.

## Ingebouwde thema's {#built-in-themes}

webforJ levert drie app-thema's out of the box:

| Thema       | Achtergrond      | Tint                        |
|-------------|------------------|-----------------------------|
| `light`     | Licht (standaard) | Subtiele primaire-kleur tint  |
| `dark`      | Donker           | Subtiele primaire-kleur tint  |
| `dark-pure` | Donker           | Geen (pure neutrale grijzen)  |

Elke app kan tijdens runtime tussen hen schakelen, en aanvullende aangepaste thema's kunnen naast de ingebouwde worden gedefinieerd.

## Een thema toepassen {#applying-a-theme}

Stel het actieve thema declaratief in met de `@AppTheme` annotatie of programmatisch met `App.setTheme()`. De themanaam moet een van de `system`, `light`, `dark`, `dark-pure`, of de naam van een aangepast thema zijn.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // app code
}

// of programmatisch
App.setTheme("dark-pure");
```

Het opnieuw aanroepen van `App.setTheme()` op elk moment schakelt de app naar een ander thema.

## Kleuren schema {#color-scheme}

De `color-scheme` CSS-declaratie vertelt de browser hoe deze zijn ingebouwde oppervlakken moet weergeven, zoals native scrollbalken, formulierelementen, autofill-hoogtepunten en de standaard achtergrond van de pagina voordat CSS laadt. De ingebouwde `dark` en `dark-pure` thema's stellen al `color-scheme: dark` voor je in, zodat de browser chrome automatisch mengt met de donkere oppervlakken.

Je hoeft hier alleen aan te denken bij het definiëren van een eigen aangepast donker thema. Voeg in dat geval `color-scheme: dark` toe aan de selector van het thema:

```css
html[data-app-theme="brand-dark"] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

Als je dit overslaat, blijven scrollbalken en autofill-rechthoeken standaard in de lichte modus en zien ze eruit als niet passend over je donkere oppervlakken. Lichte thema's hebben de declaratie niet nodig, browsers zijn standaard licht.

## Volgen van de voorkeur van de gebruiker {#following-the-users-preference}

De meeste besturingssystemen laten gebruikers een lichte of donkere uitstraling systeemwijd kiezen. webforJ kan die voorkeur respecteren en automatisch het juiste thema kiezen.

Registreer welk thema moet worden toegepast voor elke verschijningsstatus met `@AppLightTheme` en `@AppDarkTheme` (of `App.setLightTheme()` en `App.setDarkTheme()`), en geef het gereserveerde trefwoord `"system"` door aan `App.setTheme()` (of `@AppTheme("system")`) om webforJ tussen hen te laten kiezen op basis van de voorkeur van het besturingssysteem van de gebruiker.

```java
@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
class MyApp extends App {
  // app code
}
```

Equivalent programmatische vorm:

```java
App.setLightTheme("light");
App.setDarkTheme("dark");
App.setTheme("system");
```

`"system"` is een gereserveerd trefwoord. webforJ lost het tijdens runtime op naar ofwel het geregistreerde lichte of donkere thema en heroplost automatisch wanneer de voorkeur van het besturingssysteem verandert. Eenmaal opgelost, is de werkelijke `data-app-theme` attribuut op de pagina `light` of `dark`, zodat eventuele CSS-overschrijvingen deze namen moeten targeten in plaats van `"system"`.

:::info Instellingen voor uiterlijk op OS-niveau
Waar gebruikers de systeemwijde uiterlijkinstelling inschakelen varieert per platform:

- **Windows 10/11**: Instellingen > Personalisatie > Kleuren > Kies je kleur
- **macOS**: Systeeminstellingen > Uiterlijk
- **iOS**: Instellingen > Weergave & Helderheid > Uiterlijk
- **Android**: Instellingen > Weergave > Donker thema
:::

## Standaardthema's overschrijven {#overriding-default-themes}

Het meeste brandingwerk wordt gedaan door **de bestaande thema's te overschrijven** in plaats van nieuwe te creëren. Pas de zaadkleuren (of een ander token) aan voor de ingebouwde `light`, `dark`, en `dark-pure` thema's, en elk component pakt automatisch de nieuwe uitstraling op.

Je kunt het **lichte** thema overschrijven door CSS-aangepaste eigenschappen opnieuw te definiëren in de `:root` selector.

:::info `:root` pseudo-klasse
De `:root` CSS-pseudo-klasse richt zich op het root-element van het document. In HTML, vertegenwoordigt het het `<html>` element en heeft het hogere specificiteit dan de gewone `html` selector.
:::

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

Om de **donkere** of **dark-pure** thema's te overschrijven, gebruik je attributeselectoren op het `<html>` element:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-seed: #a855f7;
}

html[data-app-theme="dark-pure"] {
  --dwc-color-primary-seed: #a855f7;
}
```

Het schakelen van thema's met `App.setTheme("dark")` activeert het hergelabelde donkere thema, er is geen nieuwe themanaam nodig.

## Aangepaste thema's maken {#creating-custom-themes}

Maak een volledig nieuw thema alleen wanneer je er een nodig hebt die samen met de ingebouwde thema's bestaat (bijvoorbeeld, een hoge-contrast variant of een klant-specifieke skin). Kies een unieke naam en definieer het onder zijn eigen `html[data-app-theme='THEME_NAME']` selector:

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

Om een aangepast thema donker te maken, stel `--dwc-dark-mode: 1` en `color-scheme: dark` in:

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

Dan in je app:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // app code
}

// of programmatisch
App.setTheme("new-theme");
```

## Werken met DWC-tokens {#working-with-dwc-tokens}

Een paar gewoonten houden aangepaste CSS afgestemd op het ontwerpsysteem en voorkomen dat het afwijkt in de donkere modus of toekomstige versies.

### Verwijs altijd naar tokens met `var(...)` {#always-reference-tokens-with-var}

Hardcoded kleurlettertypes (`#3b82f6`, `rgb(59 130 246)`, `oklch(0.6 0.18 250)`) passen zich niet aan aan de donkere modus en volgen geen paletwijzigingen. Gebruik in plaats daarvan het token.

```css
/* vermijd */
.my-panel {
  background: #ffffff;
  color: #1f2937;
  border: 1px solid #e5e7eb;
}

/* geef de voorkeur aan */
.my-panel {
  background: var(--dwc-surface-3);
  color: var(--dwc-color-body-text);
  border: 1px solid var(--dwc-border-color);
}
```

### Geef de voorkeur aan variatietokens boven ruwe stapnummers {#prefer-variation-tokens-over-raw-step-numbers}

Variatietokens (`--dwc-color-primary`, `-dark`, `-light`, `-text`, `-alt`) lossen automatisch op naar een andere stap in de lichte versus donkere modus. Ruwe stapnummers (`--dwc-color-primary-50`) doen dat niet.

```css
/* vermijd - bevroren in stap 50 in beide modi */
.badge {
  background: var(--dwc-color-primary-50);
}

/* geef de voorkeur aan - verschuift stap in donkere modus */
.badge {
  background: var(--dwc-color-primary);
}
```

### Gebruik de suffix die overeenkomt met de rol {#use-the-suffix-that-matches-the-role}

| Suffix                          | Rol                                                              |
|---------------------------------|------------------------------------------------------------------|
| `--dwc-color-{name}`            | Volle vulling op volle sterkte (knoppen, badges, banners)        |
| `--dwc-color-{name}-dark`       | Actieve / ingedrukte staat                                        |
| `--dwc-color-{name}-light`      | Hover / focus achtergrond                                          |
| `--dwc-color-{name}-alt`        | Subtiele getinte achtergrond voor oproepen en alternatieve rijen  |
| `--dwc-color-{name}-text`       | Gekleurde tekst op een neutraal oppervlak                         |
| `--dwc-color-on-{name}-text`    | Tekst geplaatst **op** de gekleurde tint als achtergrond (auto-contrast)|
| `--dwc-border-color-{name}`     | Randen en scheidingen                                            |

### Reserveer oppervlakken en randen voor hun rollen {#reserve-surfaces-and-borders-for-their-roles}

Oppervlakken (`--dwc-surface-1` / `-2` / `-3`) bouwen de paginahierarchie. Randen (`--dwc-border-color`, `--dwc-border-color-*`) creëren scheidingslijnen. Het hergebruiken van paletstappen voor deze rollen werkt visueel, maar verliest de automatische modusaanpassing die de speciale tokens met zich meebrengen.

### Overschrijven op zaadniveau in aangepaste thema's {#override-at-the-seed-level-in-custom-themes}

Bij het bouwen van een aangepast thema, stel de zaad (`--dwc-color-{name}-h`, `-s`, of `-seed`) in in plaats van individuele stappen te overschrijven. De generator bouwt het volledige 19-stapspalet rond het zaad opnieuw op, waardoor het hele tonale bereik consistent blijft. Het overschrijven van individuele stappen laat de rest van het palet afdwalen tegenover je merk kleur.

```css
/* vermijd - laat andere stappen inconsistent */
html[data-app-theme="brand"] {
  --dwc-color-primary-50: #6366f1;
}

/* geef de voorkeur aan - regenerates het hele palet */
html[data-app-theme="brand"] {
  --dwc-color-primary-seed: #6366f1;
}
```

### Gebruik tokens voor ruimte, grootte, straal en overgangen {#use-tokens-for-spacing-sizing-radius-and-transitions}

Dezelfde regel geldt voor de rest van het ontwerpsysteem: verwijs naar tokens, nooit naar magische getallen.

```css
/* vermijd */
.my-panel {
  padding: 16px;
  border-radius: 8px;
  transition: background-color 250ms;
}

/* geef de voorkeur aan */
.my-panel {
  padding: var(--dwc-space-m);
  border-radius: var(--dwc-border-radius);
  transition: background-color var(--dwc-transition);
}
```

Hardcoded waarden omzeilen de door de gebruiker voorkeur font-grootte schaling, vergrendelen je in een vaste vormtaal en overslaan de verzachtende timingkurven van het ontwerpsysteem.

### Gebruik `::part(...)` om in componenten te geraken {#use-part-to-reach-into-components}

webforJ-componenten zijn Shadow DOM. Hun interne markup is verborgen voor buitenste selectoren, zodat een regel zoals `.dwc-button-label { ... }` niets zal matchen. Om interne stukken te stylen, selecteer je de blootgestelde delen:

```css
/* style het label binnen elke primaire knop */
dwc-button[theme="primary"]::part(label) {
  letter-spacing: 0.02em;
}
```

Zie [Shadow Parts](./shadow-parts) voor de volledige mechaniek, en de **Styling → Shadow Parts** sectie van elk component voor de delen die het blootstelt.

### Scope token-overschrijvingen met een wrapper-selector {#scope-token-overrides-with-a-wrapper-selector}

CSS-aangepaste eigenschappen cascaderen. Het instellen van een token op een wrapper-element tuneert alles erin zonder de rest van de app te beïnvloeden.

```css
.danger-section {
  --dwc-color-primary-seed: #ef4444;
}
```

Elk component binnen `.danger-section` (knoppen, links, focusringen) gebruikt nu de gevaar-rode tint, terwijl het globale thema ongewijzigd blijft.

### Test in zowel lichte als donkere modus {#test-in-both-light-and-dark-mode}

Voordat je enige aangepaste CSS verzendt, schakel je het thema in `dark` en `dark-pure` en loop je door het scherm. De meest voorkomende regressie zijn hardcoded kleurwaarden die er in één modus goed uitzagen en onleesbaar of buiten het palet in de andere werden weergeven.

### Grijp niet naar `!important` {#dont-reach-for-important}

Het ontsnapt aan de cascade en maakt elke toekomstige overschrijving moeilijker. Als een regel niet wint, is de oorzaak bijna altijd een specificiteitsprobleem met een schonere oplossing: richt je op dezelfde selector die het framework gebruikt, of voeg een ouderkwalificator toe. Reserveer `!important` voor echt derde-partij stijlen die je op geen enkele andere manier kunt verslaan.

## Componentthema's {#component-themes}

Naast app-niveau thema's ondersteunen webforJ-componenten een set van **componentthema's** gebaseerd op de standaard kleurenpaletten: `default`, `primary`, `success`, `warning`, `danger`, `info`, en `gray`. Dit is onafhankelijk van het actieve app-thema.

Elke component documenteert zijn ondersteunde thema's onder de **Styling → Thema's** sectie.
