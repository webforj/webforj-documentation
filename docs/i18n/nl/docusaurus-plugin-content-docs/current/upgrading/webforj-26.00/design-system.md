---
title: Upgrading to the v26 Design System
description: >-
  Reference for the design-system updates in DWC 26 - color engine, dark mode,
  surfaces, shadows, typography, radius, focus ring, and interaction feedback.
sidebar_position: 2
_i18n_hash: 8a36bc047ecfc90874412da4d39643fb
---
DWC 26 introduceert een vernieuwd ontwerpsysteem. De update is incrementeel in plaats van een volledige herschrijving: de meeste v25 CSS-variabelen blijven beschikbaar, de openbare API van de themamotor is behouden en bestaande aanpassingen blijven werken zonder veranderingen.

Deze gids documenteert wat er is veranderd, waar de visuele output verschilt en de upgrade stappen die nodig zijn wanneer een app afhankelijk is van een specifiek v25-gedrag.

## Snelle beslissing {#quick-verdict}

| Scenario | Wat te verwachten |
| --- | --- |
| Gebruikt standaard opmaak | Visuele vernieuwing. De standaardpaletkleuren zijn opnieuw afgesteld (bijvoorbeeld primaire kleur verplaatst van `h: 211 / s: 100%` naar `h: 223 / s: 91%`), schaduwen zien er gelaagder uit en componenten voelen ronder aan. Geen codewijziging nodig, maar de standaard kleuren van het merk verschuiven. |
| Overschrijft `--dwc-color-{name}-h` en `-s` | Blijft werken. Het HSL-zadenpad is behouden. |
| Overschrijft individuele paletstappen (bijvoorbeeld `--dwc-color-primary-40`) | Nummers kunnen naar verschillende kleuren resolven. Zie [Kleurpalet](#color-palette-step-5-is-always-darkest). |
| Vertrouwt op `--dwc-color-{name}-c` | Verwijderen. De lichten/donker tekst omkering wordt nu automatisch per tint berekend. |
| Verwijst naar benoemde lettergrootte tokens (`--dwc-font-size-m`, `-l`, enz.) | De schaal is met één stap naar beneden verschoven. `m` is nu 14px in plaats van 16px. Zie [Typografie](#typography). |
| Gebruikt `--dwc-font-weight-semibold` om 500-gewicht te krijgen | `semibold` is nu 600. Schakel over naar de nieuwe `--dwc-font-weight-medium` voor 500. |
| Reserveert padding rond focusbare elementen met `--dwc-focus-ring-width` | De ring heeft nu een opening. Voeg `--dwc-focus-ring-gap` toe aan die padding, anders zal de ring overlopen. Zie [Focusring](#focus-ring). |
| Aangepaste knophover/rimpel effecten | Rimpels zijn verdwenen. Druk feedback is nu een kleine verkleining. |

Als geen van deze van toepassing is, kun je hier stoppen met lezen. Je upgrade is voltooid.

## Wat is nieuw in een oogopslag {#whats-new-at-a-glance}

- **Modern kleurensysteem.** Paletten worden gegenereerd in OKLCH in plaats van HSL. Lichtheidstappen zijn perceptueel uniform (zodat aangrenzende stappen eruitzien als aangrenzende stappen), en de donkere modus keer de palet niet meer om.
- **Donkere modus via één variabele.** `--dwc-dark-mode: 1` keert de hele UI om. Modusadaptatie vindt plaats in de variatielaag, niet door elke stap opnieuw in te delen.
- **Automatische `on-text` kleuren.** Elke paletstap krijgt een `--dwc-color-on-{name}-text-{step}` metgezel die is geklemd voor WCAG AA-contrast op die tint. Je hoeft het contrast niet handmatig te berekenen.
- **Directe zaadoverschrijving.** Geef een CSS-kleur (hex, `rgb()`, `oklch()`, `lab()`, enz.) door in `--dwc-color-{name}-seed` en de hele palet wordt er vanuit opnieuw gegenereerd.
- **Herschikte schaduwen.** Dezelfde zes niveaus (`xs` tot `2xl`), nu met realistische laagafname en een automatische versterking van de donkere modus via `--dwc-shadow-strength`.
- **Oppervlakken en `default` gebruiken hun eigen lichtheidscurve.** Beide passen zich nu aan licht/donker aan via `--dwc-dark-mode` en een kleine primaire tint, in plaats van oppervlakken in het donkere thema opnieuw te definiëren en `default` aan paletstappen te aliaseren.
- **Schaal drukfeedback.** Rimpels zijn vervangen door een kleine verkleining bij druk. Tokens: `--dwc-scale-press`, `--dwc-scale-press-deep`.
- **Focusring met gat.** De ring heeft nu een kleine oppervlaktekleurige opening (`--dwc-focus-ring-gap`) voor de gekleurde schaduw, zodat deze zichtbaar blijft op solide knoppen en compacte lay-outs.
- **Randstraal is gezaaid.** Verander `--dwc-border-radius-seed` en de `s` tot `4xl` stappen worden proportioneel opnieuw geschaald (`2xs` en `xs` blijven op vaste pixelwaarden). Nieuwe `3xl` en `4xl` stappen.

## Het kleursysteem {#the-color-system}

Dit is de grootste verandering onder de motorkap. Het gedrag dat je ziet zou bekend moeten zijn, de interne werking is anders.

### Twee manieren om een kleur te definiëren {#two-ways-to-define-a-color}

Je kunt hue + verzadiging blijven gebruiken zoals voorheen, of de seed rechtstreeks met een willekeurige CSS-kleur overschrijven.

```css
/* Hue + verzadiging (nog steeds het standaard pad) */
:root {
  --dwc-color-primary-h: 223;
  --dwc-color-primary-s: 91%;
}

/* Directe seed - elk CSS-kleurformaat */
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

Als je al `-h` en `-s` gebruikte, hoef je niets te doen. De zaadoverwijding is het nieuwe pad voor directe merk kleuren.

### Kleurpalet: stap 5 is altijd het donkerste {#color-palette-step-5-is-always-darkest}

In v25, flipte het palet in donkere modus (stap 5 donkerste in licht, lichtste in donker). In v26 is stap 5 altijd de donkerste schaduw en stap 95 is altijd de lichtste, ongeacht de modus. Modusadaptatie vindt nu één laag hoger plaats, in de variatietokens:

```css
/* v26 - variaties wijzen naar vaste stappen */
--dwc-color-primary-dark:  var(--dwc-color-primary-45);
--dwc-color-primary:       var(--dwc-color-primary-50);
--dwc-color-primary-light: var(--dwc-color-primary-55);
```

| Scenario | Wat verandert |
| --- | --- |
| Gebruik `--dwc-color-primary` (of `-dark`, `-light`, `-text`) | Niets. Variaties gedragen zich nog steeds hetzelfde in alle modi. |
| Hardcoded een stap zoals `--dwc-color-primary-40` | Die stap lost nu op naar dezelfde OKLCH lichtheid in beide modi. De kleur die je in de donkere modus zag, kwam van een andere stap. Schakel over naar de variatietoken als je modusbewust gedrag wilt. |
| Schreef `hsl(var(--dwc-color-primary-h), ...)` rechtstreeks | Werkt nog steeds. De HSL-zaden zijn nog steeds geconstrueerd uit h + s. |

### Kleuren zijn afgeleid, niet beloofd {#colors-are-derived-not-promised}

:::info Let op
De hue die je instelt is een **zaad**, geen doel. De kleur die je doorgeeft via `--dwc-color-{name}-h` / `-s` (of `-seed`) verschijnt niet noodzakelijk op stap 50.
:::

Omdat het palet absolute OKLCH lichtheid per stap gebruikt, hangt waar jouw zaad landt af van zijn natuurlijke lichtheid. Heldere tinten (cyan, geel) hebben een hoge OKLCH lichtheid en eindigen rond stap 80-85. Donkere tinten (blauw) zitten toevallig nabij stap 50.

Als je een exacte kleur op een exacte stap nodig hebt, stel die stap dan expliciet in:

```css
:root {
  --dwc-color-primary-50: #1d4ed8;
}
```

### `--dwc-color-{name}-c` is verdwenen {#dwc-color-name-c-is-gone}

In v25, `-c` was de contrastdrempel: de achtergrondlichtheid waarop de bijbehorende tekstkleur omkeerde van wit naar zwart. Een waarde van `50` betekende dat tekst wit was op achtergronden donkerder dan 50% en zwart op achtergronden lichter dan 50%.

In v26 kies je geen omkeerpunt. Elke stap krijgt een getinte `on-text` kleur die automatisch wordt berekend op basis van de schaduw zelf. Het resultaat is altijd WCAG AA-veilig en behoudt een hint van de palet hue in plaats van te vervallen naar puur zwart of wit.

Als je enkele `--dwc-color-{name}-c` overschrijvingen hebt, kun je ze verwijderen, ze hebben geen effect.

### Tekst en `on-text` kleuren {#text-and-on-text-colors}

v25 had één per-stap tekst token, `--dwc-color-{name}-text-{step}`, die een puur zwart-of-wit kleur was die was berekend op basis van de `-c` drempel en bedoeld voor tekst **op** die stap als achtergrond.

v26 behoudt dezelfde tokennaam maar verandert de betekenis, en voegt een tweede per-stap token toe:

| Token | v25 betekenis | v26 betekenis |
| --- | --- | --- |
| `--dwc-color-{name}-text-{step}` | Puur zwart/wit, bedoeld voor tekst op de tint als achtergrond | Getinte, **oppervlak-veilige** tekst, leesbaar op neutrale pagina-achtergronden |
| `--dwc-color-on-{name}-text-{step}` | (bestond niet als een per-stap token) | Getinte tekst voor gebruik **op** die stap als achtergrond |

Beide v26 tokens zijn geklemd voor WCAG AA-contrast op hun bedoelde achtergrond. Als je `--dwc-color-{name}-text-{step}` gebruikte als de voorgrond op een gekleurde achtergrond, schakel dan over naar `--dwc-color-on-{name}-text-{step}` (de nieuwe `on-text` token) om die semantiek te behouden.

### Tinten en randen {#tints-and-borders}

De generator geeft nu drie tokens per palet uit, één echt nieuw, één nieuwe variant, en één waarvan de bron is verplaatst:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-tint` | (bestond niet) | Zaad op 12% opaciteit, voor alternatieve achtergronden |
| `--dwc-border-color-{name}-emphasis` | (bestond niet) | Sterkere modusbewuste rand voor hover/focus/actief |
| `--dwc-border-color-{name}` | Per variatie ingesteld als `var(--dwc-color-{name})` (de verzadigde tint) | Berekend in de generator: modusbewuste verlichte tint van het zaad |

Als je CSS `--dwc-border-color-primary` las in de verwachting de verzadigde primaire kleur te krijgen, is de weergave nu een subtiele scheidingstoon in plaats daarvan. Als je specifiek de verzadigde look wilt, schakel dan rechtstreeks over naar `--dwc-color-primary`.

## Donkere modus {#dark-mode}

Donkere modus wordt gecontroleerd door een enkele variabele, `--dwc-dark-mode`. Stel deze in op `1` voor donker, `0` voor licht:

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

Het maakt deel uit van `calc()`-expressies door het hele systeem, wat is hoe modusadaptatie naar oppervlakken, schaduwen, randen en tekstkleuren wordt doorgegeven.

In v25 moesten de ingebouwde `dark` en `dark-pure` thema's oppervlakken, schaduwen en veel paletvariaties handmatig opnieuw definiëren. In v26 wordt dat allemaal afgeleid van `--dwc-dark-mode` en de zaad kleuren. Een typisch aangepast donker thema dat vroeger een 20-regeloverschrijfblok was, wordt:

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  color-scheme: dark;
}
```

Als je een aangepast donker thema hebt gekopieerd van de v25-structuur, kun je meestal het meeste van het interne blok verwijderen en alleen de zaad en de donkere-modus vlag behouden.

## Oppervlakken en `default` {#surfaces-and-default}

In v25 waren oppervlakken twee keer gedefinieerd, eenmaal in `:root` voor de lichte modus (`hsl(default-h, default-s, 96%)` enz.) en opnieuw in het donkere thema (`hsl(default-h, default-s, 8%)` enz.). De `default` variatie wees naar paletstappen en had vergelijkbaar donkere thema-overuigingen nodig.

In v26 worden beide eenmaal berekend met een `--dwc-dark-mode` term die in de lichtheid berekening is gebakken, wat garandeert:

- Oppervlakken zitten altijd iets onder `default`, zodat kaarten visueel boven de pagina zweven.
- Een kleine primaire tint wordt toegepast via de chroma van het zaad in beide modi.
- Het `dark-pure` thema stelt `--dwc-color-default-s: 0%` in, wat de tint automatisch op zero laat vallen.

Als jouw app `--dwc-surface-1` (of een ander oppervlak) met een vaste kleur overschrijft, werkt het nog steeds; je doet gewoon niet mee aan de automatische modusadaptatie.

De bron van de `--dwc-color-{name}-alt` token is ook veranderd:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-alt` | Palet stap 95 (bijna witte achtergrond) | Zaad op 12% opaciteit (translucente tint) |

Als je `-alt` als een vaste bijna-witte achtergrond gebruikte, wordt het nu gezien als een translucente getinte overlay. Kies of een specifieke stap (`--dwc-color-{name}-95`) of ontwerp rond de translucente semantiek.

## Schaduwen {#shadows}

De zes-niveau schaal (`xs`, `s`, `m`, `l`, `xl`, `2xl`) is onveranderd in naam en aantal, maar de laagoffsets zijn opnieuw opgebouwd voor realistische afname en donker-modus schaduwen zijn nu automatisch 5x sterker via `--dwc-shadow-strength`, omdat donkere oppervlakken meer contrast nodig hebben om diepte over te brengen.

Als je alleen `var(--dwc-shadow)` gebruikt, krijg je de herschikte medium schaduw en verandert verder niets. De `--dwc-shadow-color` variabele wordt nog steeds uitgegeven, maar het waardeformaat is veranderd:

| | v25 | v26 |
| --- | --- | --- |
| `--dwc-shadow-color` | HSL triplet (`h, s%, l%`) | Volledige OKLCH kleur |

Als jouw CSS het legacy tripletformaat gebruikt zoals `hsla(var(--dwc-shadow-color), 0.07)`, schakel dan over naar een volledige schaduwtoken (`var(--dwc-shadow-m)`) of herschrijf met `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

## Typografie {#typography}

De componentgrootte tokens (`--dwc-size-*`) zijn onveranderd. De lettergrootte is opnieuw afgesteld om de `m` tier op dezelfde lichte bodygrootte te verankeren die door andere DWC-tokens wordt gebruikt, zodat de emmer namen met één stap naar beneden zijn verschoven:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-size-3xs` | 10px | 10px |
| `--dwc-font-size-2xs` | 12px | 11px |
| `--dwc-font-size-xs` | 13px | 12px |
| `--dwc-font-size-s` | 14px | 13px |
| `--dwc-font-size-m` | 16px | 14px |
| `--dwc-font-size-l` | 18px | 16px |
| `--dwc-font-size-xl` | 22px | 20px |
| `--dwc-font-size-2xl` | 28px | 26px |
| `--dwc-font-size-3xl` | 36px | 34px |

De standaard `--dwc-font-size` lost nog steeds op naar **14px**, het bereikt het gewoon via `--dwc-font-size-m` (v26) in plaats van `--dwc-font-size-s` (v25).

Als jouw CSS verwijst naar lettergrootte tokens bij naam (bijv. `font-size: var(--dwc-font-size-l)`), zal het zichtbare resultaat kleiner zijn in v26. Stap één emmer omhoog om de v25-grootte te behouden.

Lettergewichten hebben drie tokens gewonnen (`dun`, `medium`, `zwart`) en één bestaande token is verschoven:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-weight-semibold` | 500 | 600 |
| `--dwc-font-weight-medium` | (bestond niet) | 500 |

Als je `--dwc-font-weight-semibold` gebruikte om 500-gewicht tekst te verkrijgen, schakel dan over naar de nieuwe `--dwc-font-weight-medium`.

Regels voor lijnhoogtes zijn in dezelfde richting verschoven als lettergroottes; de standaard `--dwc-font-line-height` lost nog steeds op naar 1.25.

`--dwc-font-family-sans` en `--dwc-font-family-mono` zijn gemoderniseerd om `system-ui` en `ui-monospace` stacks te gebruiken. Als je een specifieke benoemde lettertype uit de oude stap doelde (`Dank Mono`, `Operator Mono`, `Roboto`, enz.) en je wilt deze terug, stel `--dwc-font-family` in op een stack die je beheert.

## Ruimte {#spacing}

De ruimteschaal is onveranderd vanaf `xs`. Alleen de twee kleinste tokens zijn afgerond naar hele pixelwaarden:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-space-3xs` | 1.2px | 1px |
| `--dwc-space-2xs` | 2.4px | 2px |

Geen actie nodig in bijna elke app.

## Randstraal {#border-radius}

Randstraal is nu gezaaid. Verander `--dwc-border-radius-seed` en elke stap (`s`, `m`, `l`, `xl`, `2xl`, `3xl`, `4xl`) wordt proportioneel opnieuw geschaald. De `2xs` en `xs` stappen zijn nog steeds vastgemaakt aan vaste pixelwaarden (te klein om significant af te leiden).

Drie dingen zijn veranderd:

| | v25 | v26 |
| --- | --- | --- |
| Eenheid | `em` (schaalt met de bovenliggende lettergrootte) | `rem` (schaalt met de root lettergrootte) |
| Standaard `--dwc-border-radius` | `--dwc-border-radius-s` (4px) | `--dwc-border-radius-seed` (8px) |
| Beschikbare stappen | tot `2xl` | voegt `3xl`, `4xl` toe |

Componenten voelen ronder aan uit de verpakking. Als een component genest binnen een grotere tekst eerder een grotere straal erfde via `em`, vindt die schaling niet langer plaats, stralen zijn nu verankerd aan de root. Als je de standaardgrootte in v25 wilt terugkrijgen, halveer dan het zaad:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, halveert de hele schaal */
}
```

## Versnellingen {#easings}

De versnellingcatalogus is grotendeels hetzelfde, met nieuwe snelkoppelingtokens voor de veelvoorkomende gevallen: `--dwc-ease`, `--dwc-ease-in`, `--dwc-ease-out`, `--dwc-ease-outGlide`. Zie de [Overgangen en Versnellingen](/docs/styling/transitions-easing) pagina voor de volledige lijst.

## Overgangen {#transitions}

Overgangsduren zijn opnieuw in balans gebracht voor een snappier gevoel:

| Variabele | v25 | v26 |
| --- | --- | --- |
| `--dwc-transition-slow` | 500&nbsp;ms | 300&nbsp;ms |
| `--dwc-transition-medium` | 250&nbsp;ms | 250&nbsp;ms |
| `--dwc-transition-fast` | 150&nbsp;ms | 150&nbsp;ms |
| `--dwc-transition-x-fast` | 50&nbsp;ms | 100&nbsp;ms |

Als je afhankelijk bent van een specifieke duur, overschrijf deze dan in `:root`.

## Focusring {#focus-ring}

De focusring gebruikt nu een dubbele ringpatroon: een kleine oppervlaktekleurige opening, daarna de gekleurde ring. Dit houdt de ring leesbaar op solide knoppen en dichte lay-outs.

| Variabele | v25 | v26 |
| --- | --- | --- |
| `--dwc-focus-ring-width` | 3px | 2px |
| `--dwc-focus-ring-a` | 0.4 | 0.75 |
| `--dwc-focus-ring-gap` | (geen) | 2px |
| `--dwc-focus-ring-l` | 45% | (verwijderd, lichtheid wordt per modus berekend) |

Als je ruimte reserveert rond focusbare elementen met `padding: var(--dwc-focus-ring-width)`, voeg dan de opening toe aan die padding zodat de nieuwe ring ruimte heeft om te renderen:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

## Interactie feedback {#interaction-feedback}

Material-stijl rimpel effecten worden niet langer gebruikt door een DWC component. De nieuwe feedback voor elk klikbaar element is een kleine verkleining:

```css
--dwc-scale-press: 0.97;      /* Standaard 3% krimp */
--dwc-scale-press-deep: 0.93; /* Diepere 7% krimp voor knoppen */
```

De `ripple` SCSS-mixin en de `--dwc-ripple-color` CSS-variabele bestaan nog steeds in de build, maar niets importeert ze standaard. Als jouw eigen componenten de mixin in gebruik namen, schakel dan over naar de druk-schaal tokens om te matchen met het nieuwe gevoel.

## Browserondersteuning {#browser-support}

Het nieuwe systeem gebruikt twee CSS-functies waarvan je de compatibiliteitstabellen in de browser kunt zien op MDN:

- [OKLCH kleur ruimte](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/oklch#browser_compatibility), inclusief relatieve kleur syntaxis (`oklch(from ...)`)
- [`color-mix()`](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/color-mix#browser_compatibility)

Beide zijn verscheept in evergreen Chrome, Edge, Firefox en Safari.

## Een praktische upgradechecklijst {#a-pragmatic-upgrade-checklist}

1. Zoek naar `--dwc-color-*-c` en verwijder die declaraties.
2. Zoek naar `hsla(var(--dwc-shadow-color)` en vervang deze door een schaduwtoken (`var(--dwc-shadow-m)`) of herschrijf als `oklch(from ...)`.
3. Zoek naar directe paletsteppen verwijzingen (`--dwc-color-{name}-{nummer}`). Als er een specifieke stijlen voor donkere modus is, schakel dan over naar variatietokens (`--dwc-color-{name}`, `-dark`, `-light`).
4. Zoek naar benoemde lettergrootte verwijzingen (`--dwc-font-size-m`, `-l`, enz.). Als je de v25-grootte wilt, stap dan één emmer omhoog.
5. Zoek naar `--dwc-font-weight-semibold`. Als je 500 wilde, schakel dan over naar `--dwc-font-weight-medium`.
6. Als je ruimte reserveert rond focusbare elementen met `--dwc-focus-ring-width`, voeg dan `--dwc-focus-ring-gap` toe aan de padding.
7. Open de app, klik rond. De meeste apps hebben niets anders nodig.
