---
sidebar_position: 3
title: Colors
_i18n_hash: 257b725aa7a7992bc6cedd91de9c9ca5
---
import ColorPalette from '@site/src/components/DWCTheme/ColorPalette/ColorPalette';

webforJ biedt een kleursysteem dat is gebaseerd op CSS-variabelen. Deze kleurvariabelen zorgen voor een consistente visuele stijl in je app, terwijl je volledige controle hebt om paletten aan te passen volgens je ontwerpeisen.

Je kunt elke kleur verwijzen met de syntaxis `--dwc-color-{palette}-{shade}`, waarbij `{palette}` de naam van de kleurengroep is (bijv. `primary`, `danger`, ..) en `{shade}` een getal van `5` tot `95` in stappen van `5`, die de lichtheid van de kleur vertegenwoordigt.

```css
.element {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

:::tip Schaduwwaarde-schaal
Schaduwwaarden variëren van `5` (donkerste) tot `95` (lichtste), toenemend in stappen van `5`.
:::

## Kleurpaletten {#color-palettes}

Er zijn verschillende ingebouwde kleurpaletten, elk ontworpen voor semantische gebruiksdoeleinden zoals branding, succesberichten, waarschuwingen en meer. Elke palet is samengesteld uit dynamisch gegenereerde tinten en schaduwen op basis van drie belangrijke eigenschappen: `hue`, `saturation` en `contrast-threshold`.

### Beschikbare paletten {#available-palettes}

- **default**: Een neutrale grijs-gebaseerde palet getint met de primaire kleur, gebruikt voor de meeste componenten.
- **primary**: Vertegenwoordigt doorgaans de primaire kleur van jouw merk.
- **success**, **warning**, **danger**: Semantische paletten die worden gebruikt voor geschikte statusindicatoren.
- **info**: Een optionele aanvullende palet voor secundaire nadruk.
- **gray**: Een echte grijs-schaal palet, ongekleurd.
- **black/white**: Statische kleurwaarden

<ColorPalette></ColorPalette>

<br/>

:::tip DWC HueCraft
Om het proces van het genereren van WCAG-conforme paletten voor jouw webforJ-toepassingen te vereenvoudigen, kun je de [DWC HueCraft](https://webforj.github.io/huecraft/) tool gebruiken. Het ondersteunt paletcreatie op basis van merk kleuren of logo's en maakt snelle CSS-export mogelijk.
:::

### Gedrag in de donkere modus {#dark-mode-behavior}

webforJ ondersteunt een omgekeerde kleurstrategie in de donkere modus. In plaats van volledig aparte kleurpaletten te gebruiken, draait het de manier waarop lichtheidswaarden worden geïnterpreteerd om.

Dit betekent dat in **donkere thema's** lagere schaduwwaarden (bijv. `--dwc-color-primary-5`) licht worden en hogere waarden (bijv. `--dwc-color-primary-95`) donker worden. De logica is omgekeerd om een optimale contrast en leesbaarheid te waarborgen over achtergronden.

Je componentcode blijft dezelfde, ongeacht het thema. Bijvoorbeeld:

```css
.button {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

In de lichte modus geeft dit een gemiddelde achtergrond. In de donkere modus geeft het nog steeds een gemiddelde, maar visueel omgekeerd om op donkere oppervlakken te werken. Deze aanpak voorkomt duplicatie, houdt je stijlen consistent en zorgt voor soepele visuele overgangen bij het schakelen tussen lichte en donkere thema's.

### Variabelen voor paletconfiguratie {#palette-configuration-variables}

Elke palet wordt gegenereerd op basis van de volgende variabelen:

| Variabele               | Beschrijving |
|------------------------|-------------|
| `hue`                  | De hoek (in graden) op het kleurenwiel. Waarden zonder eenheid worden als graden geïnterpreteerd. |
| `saturation`           | Een percentage dat de kleurintensiteit aangeeft. `100%` is volledig verzadigd; `0%` is grijswaarden. |
| `contrast-threshold`   | Een waarde tussen `0` en `100` die bepaalt of tekst licht of donker moet zijn op basis van de achtergrondlichtheid. |

Je kunt een palet aanpassen door deze variabelen in je root-stijlen opnieuw te definiëren. Bijvoorbeeld, om de primaire palet te wijzigen:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

## Thematiseren van componenten met abstracte variabelen {#theming-components-with-abstract-variables}

Om styling en consistentie over componenten te vereenvoudigen, introduceert webforJ een abstractielaag bovenop de basiskleurpaletten. Deze laag is opgebouwd uit **abstracte themavariabelen** - CSS-variabelen die verwijzen naar specifieke schaduwen binnen een kleurpalet.

Deze variabelen maken het gemakkelijker om thema's toepassen op alle componenten zonder direct te verwijzen naar ruwe kleurwaarden of kleurstalen. Je kunt ze zien als *semantische styling-snelkoppelingen* die de intentie van jouw app weerspiegelen in plaats van de implementatiedetails.

### Variabele groepen {#variable-groups}

Abstracte themavariabelen zijn georganiseerd in vier groepen:

1. [Normaal](#normal-state) Gebruikt voor de standaard uitstraling, zoals achtergronden en tekst op inactieve componenten.
2. [Donker](#darker-variant) Gebruikt voor actieve of geselecteerde staten.
3. [Licht](#lighter-variant) Gebruikt voor hover- en focus-staten.
4. [Alt](#alt-variant) Gebruikt voor secundaire highlights, zoals toetsenbordfocus of subtiele UI-accenten.

Elke groep definieert:

- Een achtergrondkleur
- Een voorgrond (tekst)kleur
- Een randkleur (voor gefocuste/gehoverde/actieve staten)
- Een focusring (gebruikt wanneer de component zichtbare focus ontvangt)

Elke tab hieronder toont de abstracte variabelen die zijn gedefinieerd voor een specifiek palet (`primary`, `success`, `danger`, enz.). Deze variabelen trekken waarden uit het onderliggende palet (bijv. `--dwc-color-primary-40`) en maken ze herbruikbaar in jouw app.

Bijvoorbeeld, in plaats van direct `--dwc-color-primary-40` te gebruiken, kun je `--dwc-color-primary` toepassen, wat de rol van die kleur als de *standaard achtergrond* voor een component met primaire stijl abstraheert.

Het wijzigen van de paletwaarden op één plek zal het uiterlijk van alle componenten die op deze abstracte variabelen vertrouwen bijwerken.

### Normale staat {#normal-state}

Gebruikt voor de basis, neutrale uitstraling van een component — wanneer deze inactief is en niet wordt weergegeven.

| Variabele                           | Beschrijving                                                             |
| ---------------------------------- | ----------------------------------------------------------------------- |
| `--dwc-color-${name}`              | De standaard achtergrondkleur. Wordt ook gebruikt voor randen in veel componenten. |
| `--dwc-color-on-${name}-text`      | De tekstkleur die bovenop de standaard achtergrond wordt weergegeven.   |
| `--dwc-color-${name}-text`         | De tekstkleur wanneer de component op de oppervlakte achtergrond is geplaatst. |
| `--dwc-border-color-${name}`       | Randkleur, voornamelijk gebruikt voor hover-, focus- en actieve staten. |
| `--dwc-focus-ring-${name}`         | Focusring-schaduw wanneer de component zichtbare stijlen ontvangt.     |

--- 

### Donkere variant {#darker-variant}

Gebruikt voor geselecteerde of actieve staten — meestal een diepere toon voor sterker contrast en nadruk.

| Variabele                                | Beschrijving                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-dark`              | Een donkerdere versie van de basiskleur. Vaak gebruikt voor ingedrukte of geselecteerde staten. |
| `--dwc-color-on-${name}-text-dark`      | Tekstkleur wanneer gebruikt op een donkere achtergrond.                   |
| `--dwc-color-${name}-text-dark`         | Een iets donkerdere tekstvariant wanneer getoond op het oppervlak.       |

--- 

### Lichtere variant {#lighter-variant}

Gebruikt voor hover-, focus- en minder dominante visuele aanwijzingen. Dit zijn zachte tonen die zijn ontworpen voor subtiele interactie-feedback.

| Variabele                                | Beschrijving                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-light`             | Een lichtere versie van de basiskleur. Typisch gebruikt voor hover/focus-achtergronden. |
| `--dwc-color-on-${name}-text-light`     | Tekstkleur wanneer getoond op een lichte achtergrond.                     |
| `--dwc-color-${name}-text-light`        | Een lichtere teksttoon voor gebruik in minder prominente staten.        |

--- 

### Alternatieve variant {#alt-variant}

Gebruikt voor secundaire nadruk of UI-hoogtepunten — zoals toetsenbordnavigatiefocusomtrekken of aanvullende indicatoren.

| Variabele                                | Beschrijving                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-alt`               | Een zeer lichte versie van de kleur, voornamelijk gebruikt voor hoogtepunten of achtergrondglows. |
| `--dwc-color-on-${name}-text-alt`       | Tekstkleur wanneer de achtergrond de alternatieve (`alt`) kleur is.      |

<Tabs>

<TabItem value="Standaard / Toon">

```css
--dwc-color-default-dark: var(--dwc-color-default-85);
--dwc-color-on-default-text-dark: var(--dwc-color-default-text-85);
--dwc-color-default-text-dark: var(--dwc-color-default-35);

--dwc-color-default: var(--dwc-color-default-90);
--dwc-color-on-default-text: var(--dwc-color-default-text-90);
--dwc-color-default-text: var(--dwc-color-default-40);

--dwc-color-default-light: var(--dwc-color-default-95);
--dwc-color-on-default-text-light: var(--dwc-color-default-text-95);
--dwc-color-default-text-light: var(--dwc-color-default-45);

--dwc-color-default-alt: var(--dwc-color-primary-alt);
--dwc-color-on-default-text-alt: var(--dwc-color-on-primary-text-alt);

--dwc-border-color-default: var(--dwc-border-color-primary);
--dwc-focus-ring-default: var(--dwc-focus-ring-primary);
```

</TabItem>

<TabItem value="Primair">

```css
--dwc-color-primary-dark: var(--dwc-color-primary-35);
--dwc-color-on-primary-text-dark: var(--dwc-color-primary-text-35);
--dwc-color-primary-text-dark: var(--dwc-color-primary-30);

--dwc-color-primary: var(--dwc-color-primary-40);
--dwc-color-on-primary-text: var(--dwc-color-primary-text-40);
--dwc-color-primary-text: var(--dwc-color-primary-35);

--dwc-color-primary-light: var(--dwc-color-primary-45);
--dwc-color-on-primary-text-light: var(--dwc-color-primary-text-45);
--dwc-color-primary-text-light: var(--dwc-color-primary-40);

--dwc-color-primary-alt: var(--dwc-color-primary-95);
--dwc-color-on-primary-text-alt: var(--dwc-color-primary-text-95);

--dwc-border-color-primary: var(--dwc-color-primary);
--dwc-focus-ring-primary: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-primary-h),
    var(--dwc-color-primary-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Succes">

```css
--dwc-color-success-dark: var(--dwc-color-success-20);
--dwc-color-on-success-text-dark: var(--dwc-color-success-text-20);
--dwc-color-success-text-dark: var(--dwc-color-success-15);

--dwc-color-success: var(--dwc-color-success-25);
--dwc-color-on-success-text: var(--dwc-color-success-text-25);
--dwc-color-success-text: var(--dwc-color-success-20);

--dwc-color-success-light: var(--dwc-color-success-30);
--dwc-color-on-success-text-light: var(--dwc-color-success-text-30);
--dwc-color-success-text-light: var(--dwc-color-success-25);

--dwc-color-success-alt: var(--dwc-color-success-95);
--dwc-color-on-success-text-alt: var(--dwc-color-success-text-95);

--dwc-border-color-success: var(--dwc-color-success);
--dwc-focus-ring-success: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-success-h),
    var(--dwc-color-success-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-success-a)
  );
```

</TabItem>

<TabItem value="Waarschuwing">

```css
--dwc-color-warning-dark: var(--dwc-color-warning-35);
--dwc-color-on-warning-text-dark: var(--dwc-color-warning-text-35);
--dwc-color-warning-text-dark: var(--dwc-color-warning-15);

--dwc-color-warning: var(--dwc-color-warning-40);
--dwc-color-on-warning-text: var(--dwc-color-warning-text-40);
--dwc-color-warning-text: var(--dwc-color-warning-20);

--dwc-color-warning-light: var(--dwc-color-warning-45);
--dwc-color-on-warning-text-light: var(--dwc-color-warning-text-45);
--dwc-color-warning-text-light: var(--dwc-color-warning-25);

--dwc-color-warning-alt: var(--dwc-color-warning-95);
--dwc-color-on-warning-text-alt: var(--dwc-color-warning-text-95);

--dwc-border-color-warning: var(--dwc-color-warning);
--dwc-focus-ring-warning: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-warning-h),
    var(--dwc-color-warning-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-warning-a)
  );
```

</TabItem>

<TabItem value="Gevaren">

```css
--dwc-color-danger-dark: var(--dwc-color-danger-35);
--dwc-color-on-danger-text-dark: var(--dwc-color-danger-text-35);
--dwc-color-danger-text-dark: var(--dwc-color-danger-30);

--dwc-color-danger: var(--dwc-color-danger-40);
--dwc-color-on-danger-text: var(--dwc-color-danger-text-40);
--dwc-color-danger-text: var(--dwc-color-danger-35);

--dwc-color-danger-light: var(--dwc-color-danger-45);
--dwc-color-on-danger-text-light: var(--dwc-color-danger-text-45);
--dwc-color-danger-text-light: var(--dwc-color-danger-40);

--dwc-color-danger-alt: var(--dwc-color-danger-95);
--dwc-color-on-danger-text-alt: var(--dwc-color-danger-text-95);

--dwc-border-color-danger: var(--dwc-color-danger);
--dwc-focus-ring-danger: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-danger-h),
    var(--dwc-color-danger-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-danger-a)
  );
```

</TabItem>

<TabItem value="Info">

```css
--dwc-color-info-dark: var(--dwc-color-info-35);
--dwc-color-on-info-text-dark: var(--dwc-color-info-text-35);
--dwc-color-info-text-dark: var(--dwc-color-info-35);

--dwc-color-info: var(--dwc-color-info-40);
--dwc-color-on-info-text: var(--dwc-color-info-text-40);
--dwc-color-info-text: var(--dwc-color-info-40);

--dwc-color-info-light: var(--dwc-color-info-45);
--dwc-color-on-info-text-light: var(--dwc-color-info-text-45);
--dwc-color-info-text-light: var(--dwc-color-info-45);

--dwc-color-info-alt: var(--dwc-color-info-95);
--dwc-color-on-info-text-alt: var(--dwc-color-info-text-95);

--dwc-border-color-info: var(--dwc-color-info);
--dwc-focus-ring-info: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-info-h),
    var(--dwc-color-info-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-info-a)
  );
```

</TabItem>

<TabItem value="Grijs">

```css
--dwc-color-gray-dark: var(--dwc-color-gray-10);
--dwc-color-on-gray-text-dark: var(--dwc-color-gray-text-10);
--dwc-color-gray-text-dark: var(--dwc-color-gray-15);

--dwc-color-gray: var(--dwc-color-gray-15);
--dwc-color-on-gray-text: var(--dwc-color-gray-text-15);
--dwc-color-gray-text: var(--dwc-color-gray-20);

--dwc-color-gray-light: var(--dwc-color-gray-20);
--dwc-color-on-gray-text-light: var(--dwc-color-gray-text-20);
--dwc-color-gray-text-light: var(--dwc-color-gray-25);

--dwc-color-gray-alt: var(--dwc-color-gray-95);
--dwc-color-on-gray-text-alt: var(--dwc-color-gray-text-95);

--dwc-border-color-gray: var(--dwc-color-gray);
--dwc-focus-ring-gray: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-gray-h),
    var(--dwc-color-gray-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-gray-a)
  );
```
</TabItem>

</Tabs>
