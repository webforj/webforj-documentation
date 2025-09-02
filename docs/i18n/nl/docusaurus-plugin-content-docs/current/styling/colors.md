---
sidebar_position: 3
title: Colors
_i18n_hash: d82a6a563267933d08c081faeddf2cc0
---
webforJ biedt een kleursysteem dat is opgebouwd met CSS-variabelen. Deze kleurvariabelen zorgen voor een consistente visuele stijl in je app en geven je volledige controle om paletten aan te passen aan je ontwerpeisen.

Je kunt elke kleur verwijzen met de syntaxis `--dwc-color-{palette}-{shade}`, waarbij `{palette}` de naam van de kleurgroep is (bijv. `primary`, `danger`, ..) en `{shade}` een nummer van `5` tot `95` in stappen van `5`, dat de lichtheid van de kleur vertegenwoordigt.

```css
.element {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

:::tip Schaduwwaarde-schaal
Schaduwwaarden variëren van `5` (donkerste) tot `95` (lichtste), met toenames van `5`.
:::

## Kleurenpaletten {#color-palettes}

Er zijn verschillende ingebouwde kleurenpaletten, elk ontworpen voor semantische gebruiksdoelen, zoals branding, succesberichten, waarschuwingen en meer. Elk palet bestaat uit dynamisch gegenereerde tinten en schaduwen, gebaseerd op drie belangrijke eigenschappen: `hue`, `saturation` en `contrast-threshold`.

### Beschikbare paletten {#available-palettes}

- **default**: Een neutraal grijs-gebaseerd palet getint met de primaire kleur, gebruikt voor de meeste componenten.
- **primary**: Vertegenwoordigt doorgaans de primaire kleur van je merk.
- **success**, **warning**, **danger**: Semantische paletten die worden gebruikt voor geschikte statusindicatoren.
- **info**: Een optioneel aanvullend palet voor secundaire nadruk.
- **gray**: Een echte grijs-schaalpalet, niet getint.
- **black/white**: Statische kleurwaarden

<ColorPalette></ColorPalette>

<br/>

:::tip  DWC HueCraft
Om het proces van het genereren van WCAG-compliant paletten voor je webforJ-toepassingen te vereenvoudigen, kun je de [DWC HueCraft](https://webforj.github.io/huecraft/) tool gebruiken. Het ondersteunt het maken van paletten op basis van merkgekleurde of logo's en maakt snelle CSS-export mogelijk.
:::

### Gedrag in donkere modus {#dark-mode-behavior}

webforJ ondersteunt een omgekeerde kleurstrategie in de donkere modus. In plaats van geheel aparte kleurenpaletten te gebruiken, draait het de manier waarop lichtheidswaarden worden geïnterpreteerd om.

Dit betekent dat in **donkere thema's**, lagere schaduwwaarden (bijv. `--dwc-color-primary-5`) licht worden en hogere waarden (bijv. `--dwc-color-primary-95`) donker. De logica is omgekeerd om optimale contrast en leesbaarheid over achtergronden te waarborgen.

Je componentcode blijft hetzelfde, ongeacht het thema. Bijvoorbeeld:

```css
.button {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

In lichte modus geeft dit een middentoon achtergrond. In donkere modus geeft het nog steeds een middentoon, maar visueel omgekeerd voor gebruik op donkere oppervlakken. Deze aanpak voorkomt duplicatie, houdt je stijlen consistent en zorgt voor soepele visuele overgangen bij het schakelen tussen lichte en donkere thema's.

### Paletconfiguratievariabelen {#palette-configuration-variables}

Elk palet wordt gegenereerd op basis van de volgende variabelen:

| Variabele               | Beschrijving |
|------------------------|-------------|
| `hue`                  | De hoek (in graden) op het kleurenwiel. Waarden zonder eenheid worden geïnterpreteerd als graden. |
| `saturation`           | Een percentage dat de kleurintensiteit aangeeft. `100%` is volledig verzadigd; `0%` is grijswaarde. |
| `contrast-threshold`   | Een waarde tussen `0` en `100` die bepaalt of tekst licht of donker moet zijn, afhankelijk van de achtergrondlichtheid. |

Je kunt een palet aanpassen door deze variabelen opnieuw te definiëren in je root-stijlen. Bijvoorbeeld, om het primaire palet te wijzigen:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

## Thema-componenten met abstracte variabelen {#theming-components-with-abstract-variables}

Om styling en consistentie over componenten te vereenvoudigen, introduceert webforJ een abstractielaag boven de basis kleurenpaletten. Deze laag is opgebouwd uit **abstracte thema-variabelen** - CSS-variabelen die verwijzen naar specifieke tinten binnen een kleurenpalet.

Deze variabelen maken het gemakkelijker om thema's toe te passen op alle componenten zonder direct naar ruwe kleurwaarden of kleurstalen te verwijzen. Je kunt ze beschouwen als *semantische styling-snelkoppelingen* die de intentie van je app weerspiegelen in plaats van de implementatiedetails.

### Variabele groepen {#variable-groups}

Abstracte thema-variabelen zijn georganiseerd in vier groepen:

1. [Normaal](#normal-state) Gebruikt voor het standaard uiterlijk, zoals achtergronden en tekst op inactieve componenten.
2. [Donker](#darker-variant) Gebruikt voor actieve of geselecteerde toestanden.
3. [Licht](#lighter-variant) Gebruikt voor hover- en focus-toestanden.
4. [Alt](#alt-variant) Gebruikt voor secundaire highlights, zoals toetsenbordfocus of subtiele UI-accenten.

Elke groep definieert:

- Een achtergrondkleur
- Een foreground (tekst) kleur
- Een randkleur (voor gefocuste/gehoverde/actieve toestanden)
- Een focusring (gebruikt wanneer de component zichtbare focus ontvangt)

Elke tab hieronder toont de abstracte variabelen die zijn gedefinieerd voor een specifiek palet (`primary`, `success`, `danger`, enz.). Deze variabelen trekken waarden uit het onderliggende palet (bijv. `--dwc-color-primary-40`) en maken ze herbruikbaar in je app.

Bijvoorbeeld, in plaats van direct `--dwc-color-primary-40` te gebruiken, kun je `--dwc-color-primary` toepassen, dat de rol van die kleur als de *standaard achtergrond* voor een primaire gestileerde component abstraheert.

Het wijzigen van de paletwaarden op één plek zal de uitstraling van alle componenten die op deze abstracte variabelen vertrouwen, bijwerken.

### Normale toestand {#normal-state}

Gebruikt voor het basis-, neutrale uiterlijk van een component—wanneer deze inactief is en niet wordt geïndexeerd.

| Variabele                           | Beschrijving                                                             |
| ---------------------------------- | ----------------------------------------------------------------------- |
| `--dwc-color-${name}`              | De standaard achtergrondkleur. Ook gebruikt voor randen in veel componenten. |
| `--dwc-color-on-${name}-text`      | De tekstkleur die bovenop de standaard achtergrond wordt weergegeven.   |
| `--dwc-color-${name}-text`         | De tekstkleur wanneer de component op de oppervlakte achtergrond is geplaatst. |
| `--dwc-border-color-${name}`       | Randkleur, voornamelijk gebruikt voor hover, focus en actieve toestanden.   |
| `--dwc-focus-ring-${name}`         | Focusring schaduw wanneer de component focus-zichtbare styling ontvangt. |

---

### Donkere variant {#darker-variant}

Gebruikt voor geselecteerde of actieve toestanden—meestal een diepere toon voor sterkere contrast en nadruk.

| Variabele                                | Beschrijving                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-dark`              | Een donkerdere versie van de basiskleur. Vaak gebruikt voor ingedrukte of geselecteerde toestanden. |
| `--dwc-color-on-${name}-text-dark`      | Textkleur wanneer gebruikt op een donkere achtergrond.                   |
| `--dwc-color-${name}-text-dark`         | Een iets donkere tekstvariant wanneer weergegeven op de oppervlakte.      |

---

### Lichtere variant {#lighter-variant}

Gebruikt voor hover, focus en minder dominante visuele aanwijzingen. Dit zijn zachte tonen die zijn ontworpen voor subtiele interactie feedback.

| Variabele                                | Beschrijving                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-light`             | Een lichtere versie van de basiskleur. Typisch gebruikt voor hover/focus achtergronden. |
| `--dwc-color-on-${name}-text-light`     | Tekstkleur wanneer weergegeven op een lichte achtergrond.                  |
| `--dwc-color-${name}-text-light`        | Een lichtere teksttoon voor gebruik in minder prominente toestanden.      |

---

### Alt-variant {#alt-variant}

Gebruikt voor secundaire nadruk of UI-highlights—zoals toetsenbordnavigatie focus outlines of aanvullende indicatoren.

| Variabele                                | Beschrijving                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-alt`               | Een zeer lichte versie van de kleur, voornamelijk gebruikt voor highlights of achtergrondglow. |
| `--dwc-color-on-${name}-text-alt`       | Tekstkleur wanneer de achtergrond de alternatieve (`alt`) kleur is.     |

<Tabs>

<TabItem value="Default / Tone">

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

<TabItem value="Primary">

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

<TabItem value="Success">

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
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Warning">

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
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Danger">

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
    var(--dwc-focus-ring-a)
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
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Gray">

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
    var(--dwc-focus-ring-a)
  );
```
</TabItem>

</Tabs>
