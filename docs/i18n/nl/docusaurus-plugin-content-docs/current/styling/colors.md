---
sidebar_position: 3
title: Colors
_i18n_hash: cc233e97e4b7333262eb47b14bfe572a
---
webforJ biedt een kleurensysteem dat is opgebouwd uit CSS aangepaste eigenschappen. Deze kleurvariabelen behouden een consistente visuele stijl in je app terwijl je volledige controle hebt om paletten aan te passen aan je ontwerpeisen.

Je kunt elke kleur refereren met de syntaxis `--dwc-color-{palette}-{step}`, waarbij `{palette}` de naam van de kleuren groep is (bijvoorbeeld `primary`, `danger`, ..) en `{step}` een getal van `5` tot `95` in stappen van `5` is, dat de lichtheid van de kleur vertegenwoordigt.

```css
.element {
  background-color: var(--dwc-color-primary-50);
  color: var(--dwc-color-on-primary-text-50);
}
```

:::tip Schaduw Stap Schaal
Stapwaarden variëren van `5` (donkerste) tot `95` (lichtste), in stappen van `5`. Stap 5 is altijd de donkerste en stap 95 is altijd de lichtste, ongeacht de licht- of donkere modus.
:::

## Kleurpaletten {#color-palettes}

DWC configureert zeven paletten plus de `black/white` palette waarbij elke palette een set variaties (schaduwen en tinten) van een semantische kleur is.

### Beschikbare paletten {#available-palettes}

- **default**: Een neutrale palette getint met de primaire tint, gebruikt voor de meeste achtergrond van componenten, randen en neutrale UI-elementen.
- **primary**: Vertegenwoordigt typisch de primaire kleur van je merk.
- **success**, **warning**, **danger**: Semantische paletten die worden gebruikt voor geschikte statusindicatoren.
- **info**: Een complementaire palette voor secundaire nadruk.
- **gray**: Een pure grijs-schaal palette, ongekleurd.
- **black/white**: Dynamische mode-bewuste kleuren die zich aanpassen aan het huidige thema. Bijna zwart in de lichte modus wordt bijna wit in de donkere modus, en vice versa.

<dwc-doc-palettes></dwc-doc-palettes>

### Palette zaden {#palette-seeds}

Elke palette wordt gegenereerd uit twee zaadvariabelen: `hue` en `saturation`. Het instellen van deze twee waarden genereert automatisch alle 19 stappen.

| Zaadvariabele | Beschrijving |
|---|---|
| `--dwc-color-{name}-h` | De hue-hoek van de zaadkleur (0-360). |
| `--dwc-color-{name}-s` | Het verzadiging percentage. `100%` is volledig verzadigd, `0%` is helemaal ongekleurd (grijs). |

Je kunt een palette aanpassen door deze variabelen in je root-stijlen opnieuw te definiëren. Bijvoorbeeld, om de primaire palette te wijzigen:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
}
```

<Tabs>

<TabItem value="Primary">

| Variabele | Standaardwaarde |
|---|---|
| `--dwc-color-primary-h` | 223 |
| `--dwc-color-primary-s` | 91% |

</TabItem>

<TabItem value="Success">

| Variabele | Standaardwaarde |
|---|---|
| `--dwc-color-success-h` | 153 |
| `--dwc-color-success-s` | 60% |

</TabItem>

<TabItem value="Warning">

| Variabele | Standaardwaarde |
|---|---|
| `--dwc-color-warning-h` | 35 |
| `--dwc-color-warning-s` | 90% |

</TabItem>

<TabItem value="Danger">

| Variabele | Standaardwaarde |
|---|---|
| `--dwc-color-danger-h` | 4 |
| `--dwc-color-danger-s` | 90% |

</TabItem>

<TabItem value="Info">

| Variabele | Standaardwaarde |
|---|---|
| `--dwc-color-info-h` | 262 |
| `--dwc-color-info-s` | 65% |

</TabItem>

<TabItem value="Gray">

| Variabele | Standaardwaarde |
|---|---|
| `--dwc-color-gray-h` | 0 |
| `--dwc-color-gray-s` | 0% |

</TabItem>

<TabItem value="Default / Tone">

| Variabele | Standaardwaarde |
|---|---|
| `--dwc-color-default-h` | var(--dwc-color-primary-h) |
| `--dwc-color-default-s` | 3% |

</TabItem>

</Tabs>

### Directe zaadoverschrijving {#direct-seed-override}

Elke palette biedt ook een `--dwc-color-{name}-seed` variabele. Standaard wordt deze geconstrueerd uit de hue- en verzadiging waarden, maar je kunt deze rechtstreeks overschrijven met elke geldige CSS kleur om het hue/verzadiging systeem geheel te omzeilen.

```css
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

### Hue-rotatie {#hue-rotation}

De palette-generator past een subtiele hue-rotatie toe over de stappen om meer natuurlijk uitziende paletten te creëren. Donkere schaduwen verschuiven iets naar warm terwijl lichtere schaduwen iets naar koel verschuiven. Dit bootst na hoe echte pigmenten zich gedragen en voorkomt dat paletten er vlak of synthetisch uitzien.

| Variabele | Standaardwaarde | Beschrijving |
|---|---|---|
| `--dwc-color-hue-rotate` | 3 | Graden van hue verschuiving over de palette. Stel in op 0 om uit te schakelen. |

<dwc-doc-hue-rotate name="primary"></dwc-doc-hue-rotate>

### Gegenereerde variabelen per stap {#generated-variables-per-step}

Elke palette genereert 19 stappen (5 tot 95). Voor elke stap worden de volgende variabelen geproduceerd:

| Variabele patroon | Beschrijving |
|---|---|
| `--dwc-color-{name}-{step}` | De palet schaduw op die stap. |
| `--dwc-color-{name}-text-{step}` | Een oppervlakteveilige tekstkleur afgeleid van die stap (WCAG AA conforme). |
| `--dwc-color-on-{name}-text-{step}` | Tekstkleur voor gebruik OP de schaduw als achtergrond (veranderen automatisch tussen licht/donker). |

:::tip Toegankelijkheid
Alle gegenereerde tekstkleuren voldoen automatisch aan de WCAG AA contrastvereisten. Je hoeft zelf geen contrastverhoudingen te berekenen.
:::

De bovenste rij toont de schaduw met zijn `on-text` kleur (voor tekst die direct op die schaduw is geplaatst). De onderste rij toont de `text` kleur op een oppervlakte achtergrond:

<dwc-doc-step-vars name="primary"></dwc-doc-step-vars>

### Aanvullende gegenereerde variabelen {#additional-generated-variables}

| Variabele patroon | Beschrijving |
|---|---|
| `--dwc-color-{name}-tint` | De zaadkleur op 12% opaciteit, voor subtiele achtergrondaccenten. |
| `--dwc-border-color-{name}` | Mode-bewuste randkleur getint met de palette hue. |
| `--dwc-border-color-{name}-emphasis` | Sterkere mode-bewuste randkleur voor hover-, focus- en actieve toestanden. |
| `--dwc-focus-ring-{name}` | Focus ring schaduw voor de palette. |

## Global kleuren {#global-colors}

Dit zijn mode-bewuste kleuren die automatisch zich aanpassen aan lichte en donkere thema's.

| Variabele | Beschrijving |
|---|---|
| `--dwc-color-black` | Bijna zwart in de lichte modus, bijna wit in de donkere modus. |
| `--dwc-color-white` | Bijna wit in de lichte modus, bijna zwart in de donkere modus. |
| `--dwc-color-body-text` | Standaard lichaamstekstkleur (gebruik `--dwc-color-black`). |

## Component thema's {#theming-components-with-abstract-variables}

DWC abstraheert het gebruik van de beschikbare paletten met een hoger niveau van semantische variatievariabelen. Componenten gebruiken deze variaties in plaats van ruwe stapnummers, omdat variaties zich automatisch aanpassen aan de licht- en donkere modus.

De variaties zijn verdeeld in drie groepen: `normal`, `dark`, en `light`.

1. `normal` variabelen zijn de basis kleur, gebruikt voor achtergrond en primaire UI-elementen.
2. `dark` variabelen worden hoofdzakelijk gebruikt voor `actieve/ingedrukte` toestanden.
3. `light` variabelen worden hoofdzakelijk gebruikt voor `hover/focus` toestanden.

<Tabs>

<TabItem value="Primary">

<dwc-doc-variations name="primary"></dwc-doc-variations>

```css
--dwc-color-primary-dark: var(--dwc-color-primary-45)
--dwc-color-primary: var(--dwc-color-primary-50)
--dwc-color-primary-light: var(--dwc-color-primary-55)
--dwc-color-primary-alt: var(--dwc-color-primary-tint)

--dwc-color-primary-text-dark: var(--dwc-color-primary-text-40)
--dwc-color-primary-text: var(--dwc-color-primary-text-45)
--dwc-color-primary-text-light: var(--dwc-color-primary-text-50)

--dwc-color-on-primary-text-dark: var(--dwc-color-on-primary-text-45)
--dwc-color-on-primary-text: var(--dwc-color-on-primary-text-50)
--dwc-color-on-primary-text-light: var(--dwc-color-on-primary-text-55)
--dwc-color-on-primary-text-alt: var(--dwc-color-primary-text)
```

</TabItem>

<TabItem value="Success">

<dwc-doc-variations name="success"></dwc-doc-variations>

```css
--dwc-color-success-dark: var(--dwc-color-success-45)
--dwc-color-success: var(--dwc-color-success-50)
--dwc-color-success-light: var(--dwc-color-success-55)
--dwc-color-success-alt: var(--dwc-color-success-tint)

--dwc-color-success-text-dark: var(--dwc-color-success-text-40)
--dwc-color-success-text: var(--dwc-color-success-text-45)
--dwc-color-success-text-light: var(--dwc-color-success-text-50)

--dwc-color-on-success-text-dark: var(--dwc-color-on-success-text-45)
--dwc-color-on-success-text: var(--dwc-color-on-success-text-50)
--dwc-color-on-success-text-light: var(--dwc-color-on-success-text-55)
--dwc-color-on-success-text-alt: var(--dwc-color-success-text)
```

</TabItem>

<TabItem value="Warning">

<dwc-doc-variations name="warning"></dwc-doc-variations>

```css
--dwc-color-warning-dark: var(--dwc-color-warning-45)
--dwc-color-warning: var(--dwc-color-warning-50)
--dwc-color-warning-light: var(--dwc-color-warning-55)
--dwc-color-warning-alt: var(--dwc-color-warning-tint)

--dwc-color-warning-text-dark: var(--dwc-color-warning-text-40)
--dwc-color-warning-text: var(--dwc-color-warning-text-45)
--dwc-color-warning-text-light: var(--dwc-color-warning-text-50)

--dwc-color-on-warning-text-dark: var(--dwc-color-on-warning-text-45)
--dwc-color-on-warning-text: var(--dwc-color-on-warning-text-50)
--dwc-color-on-warning-text-light: var(--dwc-color-on-warning-text-55)
--dwc-color-on-warning-text-alt: var(--dwc-color-warning-text)
```

</TabItem>

<TabItem value="Danger">

<dwc-doc-variations name="danger"></dwc-doc-variations>

```css
--dwc-color-danger-dark: var(--dwc-color-danger-45)
--dwc-color-danger: var(--dwc-color-danger-50)
--dwc-color-danger-light: var(--dwc-color-danger-55)
--dwc-color-danger-alt: var(--dwc-color-danger-tint)

--dwc-color-danger-text-dark: var(--dwc-color-danger-text-40)
--dwc-color-danger-text: var(--dwc-color-danger-text-45)
--dwc-color-danger-text-light: var(--dwc-color-danger-text-50)

--dwc-color-on-danger-text-dark: var(--dwc-color-on-danger-text-45)
--dwc-color-on-danger-text: var(--dwc-color-on-danger-text-50)
--dwc-color-on-danger-text-light: var(--dwc-color-on-danger-text-55)
--dwc-color-on-danger-text-alt: var(--dwc-color-danger-text)
```

</TabItem>

<TabItem value="Info">

<dwc-doc-variations name="info"></dwc-doc-variations>

```css
--dwc-color-info-dark: var(--dwc-color-info-45)
--dwc-color-info: var(--dwc-color-info-50)
--dwc-color-info-light: var(--dwc-color-info-55)
--dwc-color-info-alt: var(--dwc-color-info-tint)

--dwc-color-info-text-dark: var(--dwc-color-info-text-40)
--dwc-color-info-text: var(--dwc-color-info-text-45)
--dwc-color-info-text-light: var(--dwc-color-info-text-50)

--dwc-color-on-info-text-dark: var(--dwc-color-on-info-text-45)
--dwc-color-on-info-text: var(--dwc-color-on-info-text-50)
--dwc-color-on-info-text-light: var(--dwc-color-on-info-text-55)
--dwc-color-on-info-text-alt: var(--dwc-color-info-text)
```

</TabItem>

<TabItem value="Default / Tone">

<dwc-doc-variations name="default"></dwc-doc-variations>

De standaardvariatie wordt gebruikt voor neutrale UI-elementen zoals component achtergrond en randen. Het erfde zijn hue van de primaire palette met zeer lage verzadiging. In tegenstelling tot chromatische paletten, gebruikt standaard zijn eigen OKLCH lichtheidsberekeningen in plaats van palette stappen.

```css
--dwc-color-default-dark
--dwc-color-default
--dwc-color-default-light
--dwc-color-default-alt: var(--dwc-color-primary-alt)

--dwc-color-default-text-dark: var(--dwc-color-default-text-40)
--dwc-color-default-text: var(--dwc-color-default-text-45)
--dwc-color-default-text-light: var(--dwc-color-default-text-50)

--dwc-color-on-default-text-dark
--dwc-color-on-default-text
--dwc-color-on-default-text-light
--dwc-color-on-default-text-alt: var(--dwc-color-primary-text)

--dwc-focus-ring-default: var(--dwc-focus-ring-primary)
```

</TabItem>

<TabItem value="Gray">

<dwc-doc-variations name="gray"></dwc-doc-variations>

De grijze variatie gebruikt pure grijze schaduwen en is mode-bewust, en kiest uit donkere stappen in de lichte modus en lichte stappen in de donkere modus.

```css
--dwc-color-gray-dark
--dwc-color-gray
--dwc-color-gray-light
--dwc-color-gray-alt: var(--dwc-color-gray-tint)

--dwc-color-gray-text-dark: var(--dwc-color-gray-text-40)
--dwc-color-gray-text: var(--dwc-color-gray-text-45)
--dwc-color-gray-text-light: var(--dwc-color-gray-text-50)

--dwc-color-on-gray-text-dark
--dwc-color-on-gray-text
--dwc-color-on-gray-text-light
--dwc-color-on-gray-text-alt: var(--dwc-color-gray-text)
```

</TabItem>

</Tabs>

### Variatie referentie {#variation-reference}

| Variabele | Beschrijving |
|---|---|
| `--dwc-color-{name}` | De basis kleur. Gebruikt voor achtergronden, vullingen en randen. |
| `--dwc-color-{name}-dark` | Een donkere versie. Gebruikt voor actieve/ingedrukte toestanden. |
| `--dwc-color-{name}-light` | Een lichtere versie. Gebruikt voor hover/focus toestanden. |
| `--dwc-color-{name}-alt` | De zaadkleur op 12% opaciteit. Gebruikt voor subtiele accenttoestanden. |
| `--dwc-color-{name}-text` | Tekstkleur veilig op app-oppervlakken (WCAG AA). |
| `--dwc-color-{name}-text-dark` | Donkere tekstvariatie. |
| `--dwc-color-{name}-text-light` | Lichtere tekstvariatie. |
| `--dwc-color-on-{name}-text` | Tekstkleur voor gebruik OP `--dwc-color-{name}` als achtergrond. |
| `--dwc-color-on-{name}-text-dark` | Tekstkleur voor gebruik OP `--dwc-color-{name}-dark`. |
| `--dwc-color-on-{name}-text-light` | Tekstkleur voor gebruik OP `--dwc-color-{name}-light`. |
| `--dwc-color-on-{name}-text-alt` | Tekstkleur voor gebruik OP `--dwc-color-{name}-alt`. |
| `--dwc-border-color-{name}` | Mode-bewuste randkleur. |
| `--dwc-border-color-{name}-emphasis` | Sterkere mode-bewuste randkleur. |
| `--dwc-focus-ring-{name}` | Focus ring schaduw. |
