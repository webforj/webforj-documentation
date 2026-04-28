---
sidebar_position: 3
title: Colors
_i18n_hash: cc233e97e4b7333262eb47b14bfe572a
---
webforJ bietet ein Farbsystem, das auf CSS-Custom-Properties basiert. Diese Farbvariablen sorgen für einen konsistenten visuellen Stil in Ihrer Anwendung und geben Ihnen die volle Kontrolle, um Farbpaletten gemäß Ihren Designanforderungen anzupassen.

Sie können jede Farbe mit der Syntax `--dwc-color-{palette}-{step}` referenzieren, wobei `{palette}` der Name der Farbgruppe (z.B. `primary`, `danger`, ...) und `{step}` eine Zahl von `5` bis `95` in Schritten von `5` ist, die die Helligkeit der Farbe darstellt.

```css
.element {
  background-color: var(--dwc-color-primary-50);
  color: var(--dwc-color-on-primary-text-50);
}
```

:::tip Farbton-Stufen-Skala
Die Schrittwerte reichen von `5` (dunkelste) bis `95` (hellste) und erhöhen sich in Schritten von `5`. Schritt 5 ist immer der dunkelste und Schritt 95 immer der hellste, unabhängig von Licht- oder Dunkelmodus.
:::

## Farbpaletten {#color-palettes}

DWC konfiguriert sieben Paletten zusätzlich zur Palette `schwarz/weiß`, wobei jede Palette eine Reihe von Variationen (Schattierungen und Tönungen) einer semantischen Farbe darstellt.

### Verfügbare Paletten {#available-palettes}

- **standard**: Eine neutrale Palette, die mit dem primären Farbton getönt wird, verwendet für die meisten Komponenten-Hintergründe, Ränder und neutrale UI-Elemente.
- **primär**: Repräsentiert typischerweise die Hauptfarbe Ihrer Marke.
- **erfolg**, **warnung**, **gefahr**: Semantische Paletten, die für geeignete Statusindikatoren verwendet werden.
- **info**: Eine ergänzende Palette für sekundäre Betonungen.
- **grau**: Eine reine Graustufenpalette, ungetönt.
- **schwarz/weiß**: Dynamische, modesensible Farben, die sich an das aktuelle Thema anpassen. Nahe Schwarz im Lichtmodus wird in den Dunkelmodus zu nahe Weiß und umgekehrt.

<dwc-doc-palettes></dwc-doc-palettes>

### Paletten-Samen {#palette-seeds}

Jede Palette wird aus zwei Samenvariablen generiert: `hue` und `saturation`. Durch das Setzen dieser beiden Werte werden automatisch alle 19 Schritte generiert.

| Samenvariable | Beschreibung |
|---|---|
| `--dwc-color-{name}-h` | Der Farbtonwinkel der Samenfarbe (0-360). |
| `--dwc-color-{name}-s` | Der Sättigungsprozentsatz. `100%` ist voll gesättigt, `0%` ist vollständig entsättigt (grau). |

Sie können eine Palette anpassen, indem Sie diese Variablen in Ihren Stammstilen neu definieren. Zum Beispiel, um die primäre Palette zu modifizieren:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
}
```

<Tabs>

<TabItem value="Primär">

| Variable | Standardwert |
|---|---|
| `--dwc-color-primary-h` | 223 |
| `--dwc-color-primary-s` | 91% |

</TabItem>

<TabItem value="Erfolg">

| Variable | Standardwert |
|---|---|
| `--dwc-color-success-h` | 153 |
| `--dwc-color-success-s` | 60% |

</TabItem>

<TabItem value="Warnung">

| Variable | Standardwert |
|---|---|
| `--dwc-color-warning-h` | 35 |
| `--dwc-color-warning-s` | 90% |

</TabItem>

<TabItem value="Gefahr">

| Variable | Standardwert |
|---|---|
| `--dwc-color-danger-h` | 4 |
| `--dwc-color-danger-s` | 90% |

</TabItem>

<TabItem value="Info">

| Variable | Standardwert |
|---|---|
| `--dwc-color-info-h` | 262 |
| `--dwc-color-info-s` | 65% |

</TabItem>

<TabItem value="Grau">

| Variable | Standardwert |
|---|---|
| `--dwc-color-gray-h` | 0 |
| `--dwc-color-gray-s` | 0% |

</TabItem>

<TabItem value="Standard / Ton">

| Variable | Standardwert |
|---|---|
| `--dwc-color-default-h` | var(--dwc-color-primary-h) |
| `--dwc-color-default-s` | 3% |

</TabItem>

</Tabs>

### Direkte Samenüberschreibung {#direct-seed-override}

Jede Palette hat auch eine `--dwc-color-{name}-seed`-Variable. Standardmäßig wird dieser aus den Farbton- und Sättigungswerten konstruiert, aber Sie können ihn direkt mit jeder gültigen CSS-Farbe überschreiben, um das System für Farbton und Sättigung vollständig zu umgehen.

```css
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

### Farbtonrotation {#hue-rotation}

Der Palettenerzeuger wendet eine subtile Farbtonrotation über die Stufen an, um natürlicher aussehende Paletten zu erstellen. Dunklere Schattierungen verschieben sich leicht warm, während hellere Schattierungen sich leicht kühl verschieben. Dies ahmt das Verhalten echter Pigmente nach und verhindert, dass Paletten flach oder synthetisch aussehen.

| Variable | Standardwert | Beschreibung |
|---|---|---|
| `--dwc-color-hue-rotate` | 3 | Grad der Farbverschiebung über die Palette. Auf 0 setzen, um zu deaktivieren. |

<dwc-doc-hue-rotate name="primary"></dwc-doc-hue-rotate>

### Generierte Variablen pro Schritt {#generated-variables-per-step}

Jede Palette generiert 19 Schritte (5 bis 95). Für jeden Schritt werden die folgenden Variablen erstellt:

| Variablenmuster | Beschreibung |
|---|---|
| `--dwc-color-{name}-{step}` | Die Palettenschattierung in diesem Schritt. |
| `--dwc-color-{name}-text-{step}` | Eine oberflächensichere Textfarbe, die aus diesem Schritt abgeleitet ist (WCAG AA konform). |
| `--dwc-color-on-{name}-text-{step}` | Textfarbe zur Verwendung AUF der Schattierung als Hintergrund (automatische Umkehrung hell/dunkel). |

:::tip Barrierefreiheit
Alle generierten Textfarben erfüllen automatisch die WCAG AA-Kontrastanforderungen. Sie müssen die Kontrastverhältnisse nicht selbst berechnen.
:::

Die obere Zeile zeigt die Schattierung mit ihrer `on-text`-Farbe (für Text, der direkt auf dieser Schattierung platziert ist). Die untere Zeile zeigt die `text`-Farbe auf einem Oberfläche-Hintergrund:

<dwc-doc-step-vars name="primary"></dwc-doc-step-vars>

### Weitere generierte Variablen {#additional-generated-variables}

| Variablenmuster | Beschreibung |
|---|---|
| `--dwc-color-{name}-tint` | Die Samenfarbe mit 12% Opazität, für subtile Hintergrundhervorhebungen. |
| `--dwc-border-color-{name}` | Modesensible Rahmenfarbe, die mit dem Farbton der Palette getönt ist. |
| `--dwc-border-color-{name}-emphasis` | Stärkere modesensible Rahmenfarbe für Hover-, Fokus- und aktive Zustände. |
| `--dwc-focus-ring-{name}` | Fokusring-Schatten für die Palette. |

## Globale Farben {#global-colors}

Dies sind modesensible Farben, die sich automatisch an helle und dunkle Themen anpassen.

| Variable | Beschreibung |
|---|---|
| `--dwc-color-black` | Nahe Schwarz im Lichtmodus, nahe Weiß im Dunkelmodus. |
| `--dwc-color-white` | Nahe Weiß im Lichtmodus, nahe Schwarz im Dunkelmodus. |
| `--dwc-color-body-text` | Standardfarbe für den Textkörper (verwendet `--dwc-color-black`). |

## Komponenten-Themen {#theming-components-with-abstract-variables}

DWC abstrahiert die Verwendung der verfügbaren Paletten mit einer höherwertigen Reihe von semantischen Variationsvariablen. Komponenten verwenden diese Variationen anstelle der Roh-Schrittnummern, da sich die Variationen automatisch an helle und dunkle Modi anpassen.

Die Variationen sind in drei Gruppen unterteilt: `normal`, `dunkel` und `hell`.

1. `normal` Variablen sind die Basisfarbe, die für Hintergründe und primäre UI-Elemente verwendet wird.
2. `dunkel` Variablen werden hauptsächlich für `aktive/gedrückte` Zustände verwendet.
3. `hell` Variablen werden hauptsächlich für `hover/focus` Zustände verwendet.

<Tabs>

<TabItem value="Primär">

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

<TabItem value="Erfolg">

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

<TabItem value="Warnung">

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

<TabItem value="Gefahr">

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

<TabItem value="Standard / Ton">

<dwc-doc-variations name="default"></dwc-doc-variations>

Die Standardvariation wird für neutrale UI-Elemente wie Komponenten-Hintergründe und -Ränder verwendet. Sie erbt ihren Farbton von der primären Palette mit sehr niedriger Sättigung. Im Gegensatz zu chromatischen Paletten verwendet Standard ihre eigenen OKLCH-Helligkeitsberechnungen anstelle von Palettenschritten.

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

<TabItem value="Grau">

<dwc-doc-variations name="gray"></dwc-doc-variations>

Die Grau-Variation verwendet reine Grauschattierungen und ist modesensibel, wobei im Lichtmodus dunkle Schritte und im Dunkelmodus helle Schritte ausgewählt werden.

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

### Referenz der Variationen {#variation-reference}

| Variable | Beschreibung |
|---|---|
| `--dwc-color-{name}` | Die Grundfarbe. Wird für Hintergründe, Füllungen und Ränder verwendet. |
| `--dwc-color-{name}-dark` | Eine dunklere Version. Wird für aktive/gedrückte Zustände verwendet. |
| `--dwc-color-{name}-light` | Eine hellere Version. Wird für Hover/Fokus-Zustände verwendet. |
| `--dwc-color-{name}-alt` | Der Samen mit 12% Opazität. Wird für subtile Hervorhebungszustände verwendet. |
| `--dwc-color-{name}-text` | Textfarbe, die auf Anwendungsoberflächen sicher ist (WCAG AA). |
| `--dwc-color-{name}-text-dark` | Dunklere Textvariationen. |
| `--dwc-color-{name}-text-light` | Hellere Textvariationen. |
| `--dwc-color-on-{name}-text` | Textfarbe, die AUF `--dwc-color-{name}` als Hintergrund verwendet wird. |
| `--dwc-color-on-{name}-text-dark` | Textfarbe, die AUF `--dwc-color-{name}-dark` verwendet wird. |
| `--dwc-color-on-{name}-text-light` | Textfarbe, die AUF `--dwc-color-{name}-light` verwendet wird. |
| `--dwc-color-on-{name}-text-alt` | Textfarbe, die AUF `--dwc-color-{name}-alt` verwendet wird. |
| `--dwc-border-color-{name}` | Modesensible Rahmenfarbe. |
| `--dwc-border-color-{name}-emphasis` | Stärkere modesensible Rahmenfarbe. |
| `--dwc-focus-ring-{name}` | Fokusring-Schatten. |
