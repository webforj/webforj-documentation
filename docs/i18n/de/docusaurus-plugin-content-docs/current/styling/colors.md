---
sidebar_position: 3
title: Colors
_i18n_hash: c0e3dc5c992621c0c9cb3da24ef3964f
---
import ColorPalette from '@site/src/components/DWCTheme/ColorPalette/ColorPalette';

webforJ bietet ein Farbsystem, das auf CSS-Custom-Properties basiert. Diese Farbvariablen sorgen für einen konsistenten visuellen Stil in Ihrer App und geben Ihnen die volle Kontrolle, um Farbpaletten entsprechend Ihren Designanforderungen anzupassen.

Sie können jede Farbe mit der Syntax `--dwc-color-{palette}-{shade}` referenzieren, wobei `{palette}` der Name der Farbgruppe (z. B. `primary`, `danger`, ..) und `{shade}` eine Zahl von `5` bis `95` in Schritten von `5` ist, die die Helligkeit der Farbe darstellt.

```css
.element {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

:::tip Farbwert-Skala
Die Farbwerte reichen von `5` (dunkelste) bis `95` (hellste) in Schritten von `5`.
:::

## Farbpaletten {#color-palettes}

Es gibt mehrere integrierte Farbpaletten, die für semantische Anwendungsfälle wie Branding, Erfolgsmeldungen, Warnungen und mehr konzipiert sind. Jede Palette besteht aus dynamisch generierten Farbtönen und Schattierungen, die auf drei wesentlichen Eigenschaften basieren: `hue`, `saturation` und `contrast-threshold`.

### Verfügbare Paletten {#available-palettes}

- **default**: Eine neutrale graufarbene Palette, die mit der Primärfarbe getönt ist und für die meisten Komponenten verwendet wird.
- **primary**: Stellt typischerweise die Primärfarbe Ihrer Marke dar.
- **success**, **warning**, **danger**: Semantische Paletten, die für geeignete Statusindikatoren verwendet werden.
- **info**: Eine optionale ergänzende Palette für sekundäre Akzentuierung.
- **gray**: Eine echte Grauskala-Palette, die nicht getönt ist.
- **black/white**: Statische Farbwerte

<ColorPalette></ColorPalette>

<br/>

:::tip DWC HueCraft
Um den Prozess der Erstellung von WCAG-konformen Paletten für Ihre webforJ-Anwendungen zu vereinfachen, können Sie das [DWC HueCraft](https://webforj.github.io/huecraft/) Tool verwenden. Es unterstützt die Palettenerstellung basierend auf Markenfarben oder Logos und ermöglicht einen schnellen CSS-Export.
:::

### Verhalten im Dunkelmodus {#dark-mode-behavior}

webforJ unterstützt eine umgekehrte Farbstrategie im Dunkelmodus. Anstatt vollständig separate Farbpaletten zu verwenden, invertiert es, wie Helligkeitswerte interpretiert werden.

Das bedeutet, dass in **dunklen Themen** niedrigere Farbwerte (z. B. `--dwc-color-primary-5`) hell werden und höhere Werte (z. B. `--dwc-color-primary-95`) dunkel werden. Die Logik wird umgekehrt, um optimalen Kontrast und Lesbarkeit über Hintergründe hinweg zu gewährleisten.

Ihr Komponenten-Code bleibt unabhängig vom Thema gleich. Zum Beispiel:

```css
.button {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

Im Lichtmodus ergibt dies einen mitteltönigen Hintergrund. Im Dunkelmodus ergibt es immer noch einen mitteltönigen Hintergrund, jedoch visuell umgekehrt, um auf dunklen Flächen zu funktionieren. Dieser Ansatz vermeidet Duplikation, hält Ihre Styles konsistent und ermöglicht sanfte visuelle Übergänge beim Wechsel zwischen Licht- und Dunkelmodus.

### Palette-Konfigurationsvariablen {#palette-configuration-variables}

Jede Palette wird basierend auf den folgenden Variablen generiert:

| Variable               | Beschreibung |
|------------------------|-------------|
| `hue`                  | Der Winkel (in Grad) auf dem Farbkreis. Einheitlose Werte werden als Grad interpretiert. |
| `saturation`           | Ein Prozentsatz, der die Farbintensität angibt. `100%` ist voll gesättigt; `0%` ist Graustufen. |
| `contrast-threshold`   | Ein Wert zwischen `0` und `100`, der bestimmt, ob der Text hell oder dunkel sein sollte, basierend auf der Hintergrundhelligkeit. |

Sie können eine Palette anpassen, indem Sie diese Variablen in Ihren Root-Styles neu definieren. Zum Beispiel, um die Primärpalette zu ändern:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

## Komponenten mit abstrakten Variablen gestalten {#theming-components-with-abstract-variables}

Um das Styling und die Konsistenz über Komponenten hinweg zu vereinfachen, führt webforJ eine Abstraktionsschicht über den Basisfarbpaletten ein. Diese Schicht basiert auf **abstrakten Themenvariablen** - CSS-Custom-Properties, die sich auf bestimmte Schattierungen innerhalb einer Farbpalette beziehen.

Diese Variablen erleichtern es, Themen über alle Komponenten hinweg anzuwenden, ohne direkt rohe Farbwerte oder Farbmuster zu referenzieren. Man kann sie als *semantische Styling-Abkürzungen* betrachten, die die Absicht Ihrer App widerspiegeln, anstatt deren Implementierungsdetails.

### Variablengruppen {#variable-groups}

Abstrakte Themenvariablen sind in vier Gruppen organisiert:

1. [Normal](#normal-state) Wird für das Standardaussehen verwendet, wie Hintergründe und Text auf inaktiven Komponenten.
2. [Dunkel](#darker-variant) Wird für aktive oder ausgewählte Zustände verwendet.
3. [Hell](#lighter-variant) Wird für Hover- und Fokus-Zustände verwendet.
4. [Alt](#alt-variant) Wird für sekundäre Hervorhebungen verwendet, wie Tastaturfokus oder subtile UI-Akzentuierungen.

Jede Gruppe definiert:

- Eine Hintergrundfarbe
- Eine Vordergrundfarbe (Textfarbe)
- Eine Rahmenfarbe (für fokussierte/hoverte/aktive Zustände)
- Einen Fokusring (wird verwendet, wenn die Komponente sichtbaren Fokus erhält)

Jede Registerkarte unten zeigt die abstrakten Variablen, die für eine bestimmte Palette (`primary`, `success`, `danger` usw.) definiert sind. Diese Variablen ziehen Werte aus der zugrunde liegenden Palette (z. B. `--dwc-color-primary-40`) und machen sie in Ihrer App wiederverwendbar.

Zum Beispiel, anstatt direkt `--dwc-color-primary-40` zu verwenden, können Sie `--dwc-color-primary` anwenden, was die Rolle dieser Farbe als *Standardhintergrund* für eine primär gestaltete Komponente abstrahiert.

Wenn Sie die Palettenwerte an einer Stelle ändern, wird das Aussehen aller Komponenten aktualisiert, die auf diese abstrakten Variablen angewiesen sind.

### Normalzustand {#normal-state}

Verwendet für das Basis-, neutrale Aussehen einer Komponente - wenn sie untätig ist und nicht interagiert wird.

| Variable                           | Beschreibung                                                             |
| ---------------------------------- | ----------------------------------------------------------------------- |
| `--dwc-color-${name}`              | Die Standard-Hintergrundfarbe. Wird auch für Rahmen in vielen Komponenten verwendet. |
| `--dwc-color-on-${name}-text`      | Die Textfarbe, die über dem Standardhintergrund angezeigt wird.        |
| `--dwc-color-${name}-text`         | Die Textfarbe, wenn die Komponente auf dem Hintergrund angezeigt wird.   |
| `--dwc-border-color-${name}`       | Rahmenfarbe, hauptsächlich verwendet für Hover-, Fokus- und aktive Zustände. |
| `--dwc-focus-ring-${name}`         | Fokusring-Schatten, wenn die Komponente fokussierbar gestaltet wird.    |

---

### Dunklere Variante {#darker-variant}

Verwendet für ausgewählte oder aktive Zustände - normalerweise ein tieferer Ton für stärkeren Kontrast und Betonung.

| Variable                                | Beschreibung                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-dark`              | Eine dunklere Version der Basisfarbe. Oft verwendet für gedrückte oder ausgewählte Zustände. |
| `--dwc-color-on-${name}-text-dark`      | Textfarbe, wenn sie auf einem dunklen Hintergrund verwendet wird.        |
| `--dwc-color-${name}-text-dark`         | Eine leicht dunklere Textalternative, die auf der Oberfläche angezeigt wird. |

---

### Hellere Variante {#lighter-variant}

Verwendet für Hover-, Fokus- und weniger dominante visuelle Hinweise. Diese sind sanfte Töne, die für subtile Interaktionsrückmeldungen ausgelegt sind.

| Variable                                | Beschreibung                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-light`             | Eine hellere Version der Basisfarbe. Wird typischerweise für Hover/Fokus-Hintergründe verwendet. |
| `--dwc-color-on-${name}-text-light`     | Textfarbe, wenn sie auf einem hellen Hintergrund angezeigt wird.         |
| `--dwc-color-${name}-text-light`        | Ein hellerer Textton für weniger prominente Zustände.                    |

---

### Alt-Variante {#alt-variant}

Verwendet für sekundäre Betonung oder UI-Höhepunkte - wie Tastaturnavigation Fokusumrandungen oder Hilfsanzeigen.

| Variable                                | Beschreibung                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-alt`               | Eine sehr helle Version der Farbe, hauptsächlich verwendet für Höhepunkte oder Hintergrundglühen. |
| `--dwc-color-on-${name}-text-alt`       | Textfarbe, wenn der Hintergrund die alternative (`alt`) Farbe ist.       |

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
    var(--dwc-color-gray-a)
  );
```
</TabItem>

</Tabs>
