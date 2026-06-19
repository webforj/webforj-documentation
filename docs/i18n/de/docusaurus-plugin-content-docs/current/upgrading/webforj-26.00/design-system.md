---
title: Upgrading to the v26 Design System
description: >-
  Reference for the design-system updates in DWC 26 - color engine, dark mode,
  surfaces, shadows, typography, radius, focus ring, and interaction feedback.
sidebar_position: 2
_i18n_hash: 8a36bc047ecfc90874412da4d39643fb
---
DWC 26 bringt ein überarbeitetes Designsystem. Das Update ist inkrementell und keine vollständige Neuschreibung: Die meisten CSS-Variablen von v25 bleiben verfügbar, die öffentliche API der Themen-Engine wird beibehalten, und bestehende Anpassungen funktionieren weiterhin ohne Änderungen.

Dieser Leitfaden dokumentiert, was sich geändert hat, wo die visuelle Ausgabe abweicht und welche Upgrade-Schritte erforderlich sind, wenn eine App von einem bestimmten Verhalten in v25 abhängt.

## Schnelles Urteil {#quick-verdict}

| Szenario | Was zu erwarten ist |
| --- | --- |
| Verwendet Standardstile | Visuelle Auffrischung. Die Standardfarbpalette wurde neu abgestimmt (zum Beispiel wurde die Primärfarbe von `h: 211 / s: 100%` auf `h: 223 / s: 91%` verschoben), Schatten wirken mehrschichtig und Komponenten fühlen sich runder an. Keine Codeänderung erforderlich, aber die standardmäßigen Markenfarben ändern sich. |
| Überschreibt `--dwc-color-{name}-h` und `-s` | Funktioniert weiterhin. Der HSL-Saatweg bleibt erhalten. |
| Überschreibt einzelne Palettenstufen (zum Beispiel `--dwc-color-primary-40`) | Die Zahlen können zu anderen Farben führen. Siehe [Farbpalette](#color-palette-step-5-is-always-darkest). |
| Beruft sich auf `--dwc-color-{name}-c` | Entfernen. Der Wechsel von hellem zu dunklem Text wird jetzt automatisch pro Farbton berechnet. |
| Referenziert benannte Schriftgrößentokens (`--dwc-font-size-m`, `-l` usw.) | Die Skala wurde um eine Stufe nach unten verschoben. `m` ist jetzt 14px statt 16px. Siehe [Typografie](#typography). |
| Verwendet `--dwc-font-weight-semibold` um 500-Gewicht zu erhalten | `semibold` ist jetzt 600. Wechseln Sie zu `--dwc-font-weight-medium` für 500. |
| Reserviert Padding um fokussierbare Elemente mit `--dwc-focus-ring-width` | Der Ring hat jetzt einen Abstand. Fügen Sie `--dwc-focus-ring-gap` zu diesem Padding hinzu, oder der Ring überflutet. Siehe [Fokusring](#focus-ring). |
| Angepasste Hover-/Ripple-Effekte für Schaltflächen | Ripples sind verschwunden. Das Druck-Feedback ist jetzt ein kleines Herunterskalieren. |

Wenn keine dieser Bedingungen zutrifft, können Sie hier aufhören zu lesen. Ihr Upgrade ist abgeschlossen.

## Was gibt es Neues auf einen Blick {#whats-new-at-a-glance}

- **Modernisierte Farbengine.** Paletten werden in OKLCH statt in HSL erzeugt. Die Helligkeitsstufen sind wahrnehmungsuniform (sodass benachbarte Stufen wie benachbarte Stufen aussehen), und der Dunkelmodus verschiebt die Palette nicht mehr.
- **Dunkelmodus über eine Variable.** `--dwc-dark-mode: 1` schaltet die gesamte Benutzeroberfläche um. Die Modusanpassung erfolgt in der Variationsschicht, nicht durch Neuzuordnung jeder Stufe.
- **Automatische `on-text` Farben.** Jeder Palettenpunkt erhält eine `--dwc-color-on-{name}-text-{step}` Begleitfarbe, die für den WCAG AA-Kontrast auf diesem Farbton begrenzt ist. Sie müssen den Kontrast nicht manuell berechnen.
- **Direkte Saatübersteuerung.** Geben Sie jede CSS-Farbe (hex, `rgb()`, `oklch()`, `lab()` usw.) in `--dwc-color-{name}-seed` ein und die gesamte Palette wird neu generiert.
- **Neu abgestimmte Schatten.** Die gleichen sechs Ebenen (`xs` bis `2xl`), jetzt mit realistischem Schichtabfall und einem automatischen Dunkelmodus-Stärkepush per `--dwc-shadow-strength`.
- **Oberflächen und `default` verwenden ihre eigene Helligkeitskurve.** Beide passen sich jetzt über `--dwc-dark-mode` und einen kleinen Primärtint an, anstatt Oberflächen im dunklen Thema neu zu definieren und `default` an Palettenstufen zu aliasieren.
- **Skalierungsdruck-Feedback.** Ripples werden durch ein kleines Herunterskalieren beim Drücken ersetzt. Tokens: `--dwc-scale-press`, `--dwc-scale-press-deep`.
- **Fokusring mit Abstand.** Der Ring hat jetzt einen kleinen oberflächenfarbigen Abstand (`--dwc-focus-ring-gap`) vor dem farbigen Schatten, sodass er auf soliden Schaltflächen und engen Layouts sichtbar bleibt.
- **Randradius ist gesät.** Ändern Sie `--dwc-border-radius-seed` und die Stufen `s` bis `4xl` skalieren proportional neu. (`2xs` und `xs` bleiben bei festen Pixelwerten). Neue `3xl` und `4xl` Stufen.

## Das Farbsystem {#the-color-system}

Dies ist die größte Veränderung im Hintergrund. Das Verhalten, das Sie sehen, sollte bekannt sein, die Interna sind jedoch anders.

### Zwei Wege, eine Farbe zu definieren {#two-ways-to-define-a-color}

Sie können weiterhin Hue + Sättigung wie zuvor verwenden oder die Saat direkt mit jeder CSS-Farbe überschreiben.

```css
/* Hue + Sättigung (immer noch der Standardweg) */
:root {
  --dwc-color-primary-h: 223;
  --dwc-color-primary-s: 91%;
}

/* Direkte Saat - jedes CSS-Farbschema */
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

Wenn Sie bereits `-h` und `-s` verwendet haben, müssen Sie nichts tun. Die Saatübersteuerung ist der neue Weg für direkte Markenfarben.

### Farbpalette: Stufe 5 ist immer die dunkelste {#color-palette-step-5-is-always-darkest}

In v25 hat die Palette im Dunkelmodus umgeschaltet (Stufe 5 dunkel in hell, hellstes in dunkel). In v26 ist Stufe 5 immer der dunkelste Farbton und Stufe 95 immer der hellste, unabhängig vom Modus. Die Modusanpassung erfolgt jetzt eine Ebene höher, in den Variationstokens:

```css
/* v26 - Variationen zeigen auf feste Stufen */
--dwc-color-primary-dark:  var(--dwc-color-primary-45);
--dwc-color-primary:       var(--dwc-color-primary-50);
--dwc-color-primary-light: var(--dwc-color-primary-55);
```

| Szenario | Was sich ändert |
| --- | --- |
| Verwendet `--dwc-color-primary` (oder `-dark`, `-light`, `-text`) | Nichts. Die Variationen verhalten sich in beiden Modi gleich. |
| Hardcodierte eine Stufe wie `--dwc-color-primary-40` | Diese Stufe führt jetzt in beiden Modi zu derselben OKLCH-Helligkeit. Die Farbe, die Sie im Dunkelmodus gesehen haben, kam von einer anderen Stufe. Wechseln Sie zum Variationstoken, wenn Sie ein modussensibles Verhalten möchten. |
| Hatte `hsl(var(--dwc-color-primary-h), ...)` direkt geschrieben | Funktioniert weiterhin. Das HSL-Saat wird weiterhin aus h + s aufgebaut. |

### Farben sind abgeleitet, nicht versprochen {#colors-are-derived-not-promised}

:::info Hinweis
Der Farbton, den Sie festlegen, ist eine **Saat**, kein Ziel. Die Farbe, die Sie über `--dwc-color-{name}-h` / `-s` (oder `-seed`) übergeben, wird nicht unbedingt an Stufe 50 erscheinen.
:::

Da die Palette absolute OKLCH-Helligkeit pro Stufe verwendet, hängt der Ort, an dem Ihre Saat landet, von ihrer natürlichen Helligkeit ab. Helle Farbtöne (Cyan, Gelb) haben eine hohe OKLCH-Helligkeit und landen in der Regel rund um Stufe 80-85. Dunklere Farbtöne (Blau) liegen zufällig in der Nähe von Stufe 50.

Wenn Sie eine bestimmte Farbe an einer bestimmten Stufe benötigen, legen Sie die Stufe ausdrücklich fest:

```css
:root {
  --dwc-color-primary-50: #1d4ed8;
}
```

### `--dwc-color-{name}-c` ist verschwunden {#dwc-color-name-c-is-gone}

In v25 war `-c` die Kontrastschwelle: die Hintergrundhelligkeit, bei der die Begleit-Textfarbe von weiß zu schwarz umschaltete. Ein Wert von `50` bedeutete, dass der Text auf Hintergründen, die dunkler als 50% waren, weiß und auf Hintergründen, die heller als 50% waren, schwarz war.

In v26 wählen Sie keinen Umschaltpunkt mehr. Jede Stufe erhält eine getönte `on-text` Farbe, die automatisch aus dem Farbton selbst berechnet wird. Das Ergebnis ist immer WCAG AA-sicher und behält einen Hauch des Palette-Hues bei, anstatt in reines Schwarz oder Weiß zu fallen.

Wenn Sie `--dwc-color-{name}-c`-Übersteuerungen haben, können Sie diese löschen, sie haben keine Wirkung.

### Text- und `on-text` Farben {#text-and-on-text-colors}

v25 hatte ein per-Stufen-Text-Token, `--dwc-color-{name}-text-{step}`, das eine rein schwarze oder weiße Farbe war, die von der `-c`-Schwelle berechnet wurde und für Text **auf** dieser Stufe als Hintergrund gedacht war.

v26 behält den gleichen Namen für das Token bei, ändert jedoch seine Bedeutung und fügt ein zweites pro-Stufen-Token hinzu:

| Token | Bedeutung in v25 | Bedeutung in v26 |
| --- | --- | --- |
| `--dwc-color-{name}-text-{step}` | Rein schwarz/weiß, gedacht für Text auf dem Farbton als Hintergrund | Getönter, **oberflächen-sicherer** Text, der auf neutralen Seitenhintergründen lesbar ist |
| `--dwc-color-on-{name}-text-{step}` | (existierte nicht als per-Stufen-Token) | Getönter Text zur Verwendung **auf** dieser Stufe als Hintergrund |

Beide v26-Token sind für WCAG AA-Kontrast auf ihrem beabsichtigten Hintergrund begrenzt. Wenn Sie `--dwc-color-{name}-text-{step}` als Vordergrund auf einem farbigen Hintergrund verwendet haben, wechseln Sie zu `--dwc-color-on-{name}-text-{step}` (dem neuen `on-text`-Token), um diese Semantik zu bewahren.

### Tönungen und Rahmen {#tints-and-borders}

Der Generator gibt jetzt drei Tokens pro Palette aus, eines wirklich neu, eines neue Variante und eines, dessen Quelle sich verschoben hat:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-tint` | (existierte nicht) | Saat bei 12% Opazität, für alternative Hintergründe |
| `--dwc-border-color-{name}-emphasis` | (existierte nicht) | Stärkerer, modussensitiver Rahmen für Hover/Fokus/Aktiv |
| `--dwc-border-color-{name}` | pro Variation als `var(--dwc-color-{name})` (der gesättigte Farbton) festgelegt | Im Generator berechnet: mode-aware lighten Tone der Saat |

Wenn Ihr CSS `--dwc-border-color-primary` liest, in der Erwartung, dass die gesättigte Primärfarbe zu sehen ist, sieht die visuelle nun aus wie ein subtiler Trennungsfarbton. Wenn Sie speziell den gesättigten Look wünschen, wechseln Sie direkt zu `--dwc-color-primary`.

## Dunkelmodus {#dark-mode}

Der Dunkelmodus wird von einer einzigen Variablen, `--dwc-dark-mode`, gesteuert. Setzen Sie ihn auf `1` für dunkel, `0` für hell:

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

Er nimmt an `calc()`-Ausdrücken im gesamten System teil, was dazu führt, dass die Modusanpassung auf Oberflächen, Schatten, Rahmen und Textfarben propagiert wird.

In v25 mussten die eingebauten `dark` und `dark-pure`-Themen die Oberflächen, Schatten und viele Palettevariationen manuell neu definieren. In v26 wird all das jetzt von `--dwc-dark-mode` und den Saatfarben abgeleitet. Ein typisches benutzerdefiniertes Dunkelthema, das früher ein 20-zeiliges Override-Block war, wird:

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  color-scheme: dark;
}
```

Wenn Sie ein benutzerdefiniertes Dunkelthema haben, das von der v25-Struktur kopiert wurde, können Sie normalerweise den Großteil des inneren Blocks löschen und nur die Saat und das Dunkelmodus-Flag beibehalten.

## Oberflächen und `default` {#surfaces-and-default}

In v25 wurden Oberflächen zweimal definiert, einmal in `:root` für den hellen Modus (`hsl(default-h, default-s, 96%)` usw.) und erneut im dunklen Thema (`hsl(default-h, default-s, 8%)` usw.). Die `default`-Variation wies auf Palettenstufen hin und benötigte ähnliche Überschreibungen für das dunkle Thema.

In v26 werden beide einmal berechnet mit einem `--dwc-dark-mode`-Term, der in die Helligkeitsberechnung integriert ist, was gewährleistet:

- Oberflächen liegen immer leicht unter `default`, sodass Karten visuell über der Seite schweben.
- Ein kleiner Primärtint wird in beiden Modi über die Chromatik der Saat angewendet.
- Das `dark-pure`-Thema setzt `--dwc-color-default-s: 0%`, was den Tint automatisch auf null reduziert.

Wenn Ihre App `--dwc-surface-1` (oder eine andere Oberfläche) mit einer festen Farbe überschreibt, funktioniert sie weiterhin; Sie entscheiden sich einfach gegen die automatische Modusanpassung.

Die Quelle des Tokens `--dwc-color-{name}-alt` hat sich ebenfalls geändert:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-alt` | Palette Stufe 95 (nahe weißer Hintergrund) | Saat bei 12% Opazität (transluzenter Tint) |

Wenn Sie `-alt` als durchgehenden nahezu weißen Hintergrund verwendet haben, wird es jetzt als transluzenter getönter Überzug gelesen. Wählen Sie entweder eine spezifische Stufe (`--dwc-color-{name}-95`) oder gestalten Sie um den transluzenten semantischen Hintergrund.

## Schatten {#shadows}

Das sechsstufige Scale (`xs`, `s`, `m`, `l`, `xl`, `2xl`) hat sich in Namen und Anzahl nicht geändert, aber die Layer-Offsets wurden für realistische Falloff neu gebaut und die Schatten im Dunkelmodus sind jetzt automatisch 5x stärker per `--dwc-shadow-strength`, da dunkle Oberflächen mehr Kontrast benötigen, um Tiefe zu vermitteln.

Wenn Sie nur `var(--dwc-shadow)` verwenden, erhalten Sie den neu abgestimmten mittleren Schatten, und es ändert sich sonst nichts. Die Variable `--dwc-shadow-color` wird weiterhin ausgegeben, aber ihr Werformat hat sich geändert:

| | v25 | v26 |
| --- | --- | --- |
| `--dwc-shadow-color` | HSL-Dreifach (`h, s%, l%`) | Vollständige OKLCH-Farbe |

Wenn Ihr CSS die alte Dreifachform wie `hsla(var(--dwc-shadow-color), 0.07)` verwendet, wechseln Sie zu einem vollständigen Schatten-Token (`var(--dwc-shadow-m)`) oder schreiben Sie mit `oklch(from var(--dwc-shadow-color) l c h / 0.07)` neu.

## Typografie {#typography}

Die Komponenten-Größen-Tokens (`--dwc-size-*`) sind unverändert. Die Schriftart-Skala wurde neu abgestimmt, um die `m`-Stufe auf die gleiche leichte Körpergröße zu verankern, die auch von anderen DWC-Tokens verwendet wird, sodass die Bucket-Namen um eine Stufe nach unten verschoben wurden:

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

Die Standardgröße `--dwc-font-size` bleibt bei **14px**, sie erreicht diese nur über `--dwc-font-size-m` (v26) statt `--dwc-font-size-s` (v25).

Wenn Ihr CSS Schriftgrößen-Tokens nach Namen referenziert (z.B. `font-size: var(--dwc-font-size-l)`), wird das sichtbare Ergebnis in v26 kleiner sein. Steigen Sie um eine Stufe auf, um die v25-Größe zu erhalten.

Die Schriftgewichte erhielten drei Tokens (`thin`, `medium`, `black`) und ein bestehendes Token verschob sich:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-weight-semibold` | 500 | 600 |
| `--dwc-font-weight-medium` | (existierte nicht) | 500 |

Wenn Sie `--dwc-font-weight-semibold` verwendet haben, um 500-Gewicht-Text zu erhalten, wechseln Sie zum neuen `--dwc-font-weight-medium`.

Die Zeilenhöhen-Buckets verschoben sich in die gleiche Richtung wie die Schriftgrößen; die Standard `--dwc-font-line-height` bleibt bei 1.25.

`--dwc-font-family-sans` und `--dwc-font-family-mono` wurden modernisiert, um `system-ui` und `ui-monospace` Stacks zu verwenden. Wenn Sie aus dem alten Stack eine bestimmte benannte Schriftart (`Dank Mono`, `Operator Mono`, `Roboto` usw.) angesprochen haben und diese zurückhaben möchten, stellen Sie `--dwc-font-family` auf einen Stack ein, den Sie kontrollieren.

## Abstände {#spacing}

Die Abstands-Skala ist unverändert von `xs` an. Nur die beiden kleinsten Tokens wurden auf ganze Pixelwerte gerundet:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-space-3xs` | 1.2px | 1px |
| `--dwc-space-2xs` | 2.4px | 2px |

Keine Maßnahmen erforderlich in fast jeder App.

## Randradius {#border-radius}

Der Randradius ist jetzt gesät. Ändern Sie `--dwc-border-radius-seed` und jeder Schritt (`s`, `m`, `l`, `xl`, `2xl`, `3xl`, `4xl`) skaliert proportional neu. Die Schritte `2xs` und `xs` bleiben weiterhin bei festen Pixelwerten (zu klein, um sinnvoll abgeleitet zu werden).

Drei Dinge haben sich geändert:

| | v25 | v26 |
| --- | --- | --- |
| Einheit | `em` (skaliert mit der Schriftgröße des Elternteils) | `rem` (skaliert mit der Schriftgröße des Wurzel-Elements) |
| Standard `--dwc-border-radius` | `--dwc-border-radius-s` (4px) | `--dwc-border-radius-seed` (8px) |
| Verfügbare Schritte | bis zu `2xl` | fügt `3xl`, `4xl` hinzu |

Komponenten fühlen sich sofort runder an. Wenn eine Komponente, die sich innerhalb größeren Texts befindet, früher einen größeren Radius über `em` geerbt hat, geschieht diese Skalierung nicht mehr; Radien sind jetzt an die Wurzel angekoppelt. Wenn Sie die Standardgröße von v25 zurück möchten, halbieren Sie die Saat:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, halbiert die gesamte Skala */
}
```

## Easeings {#easings}

Der Katalog der Easing-Funktionen ist größtenteils gleich geblieben, mit neuen Abkürzungs-Token für die häufigsten Fälle: `--dwc-ease`, `--dwc-ease-in`, `--dwc-ease-out`, `--dwc-ease-outGlide`. Siehe die [Übergänge und Easing](/docs/styling/transitions-easing) Seite für die vollständige Liste.

## Übergänge {#transitions}

Die Übergangszeiten wurden neu ausgeglichen, um ein schnelles Gefühl zu vermitteln:

| Variable | v25 | v26 |
| --- | --- | --- |
| `--dwc-transition-slow` | 500&nbsp;ms | 300&nbsp;ms |
| `--dwc-transition-medium` | 250&nbsp;ms | 250&nbsp;ms |
| `--dwc-transition-fast` | 150&nbsp;ms | 150&nbsp;ms |
| `--dwc-transition-x-fast` | 50&nbsp;ms | 100&nbsp;ms |

Wenn Sie von einer bestimmten Zeit abhängen, überschreiben Sie sie in `:root`.

## Fokusring {#focus-ring}

Der Fokusring verwendet jetzt ein Doppelring-Muster: einen kleinen, oberflächenfarbigen Abstand und dann den farbigen Ring. Dies hält den Ring auf soliden Schaltflächen und dichten Layouts lesbar.

| Variable | v25 | v26 |
| --- | --- | --- |
| `--dwc-focus-ring-width` | 3px | 2px |
| `--dwc-focus-ring-a` | 0.4 | 0.75 |
| `--dwc-focus-ring-gap` | (keine) | 2px |
| `--dwc-focus-ring-l` | 45% | (entfernt, Helligkeit wird pro Modus berechnet) |

Wenn Sie Platz um fokussierbare Elemente mit `padding: var(--dwc-focus-ring-width)` reservieren, fügen Sie das Gap zu diesem Padding hinzu, damit der neue Ring Platz zum Rendern hat:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

## Interaktions-Feedback {#interaction-feedback}

Materialstil-Ripple-Effekte werden von keiner DWC-Komponente mehr verwendet. Das neue Feedback für jedes klickbare Element ist ein kleines Herunterskalieren:

```css
--dwc-scale-press: 0.97;      /* Standard 3% Schrumpfung */
--dwc-scale-press-deep: 0.93; /* Tiefere 7% Schrumpfung für Schaltflächen */
```

Der `ripple` SCSS-Mixin und die CSS-Variable `--dwc-ripple-color` existieren weiterhin im Build, aber nichts importiert sie standardmäßig. Wenn Ihre eigenen Komponenten in den Mixin integriert sind, wechseln Sie zu den Druckskalierungs-Tokens, um dem neuen Gefühl zu entsprechen.

## Browserunterstützung {#browser-support}

Das neue System verwendet zwei CSS-Funktionen, deren Kompatibilitätstabellen Sie auf MDN sehen können:

- [OKLCH-Farbraum](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/oklch#browser_compatibility), enthält relative Farbsyntax (`oklch(from ...)`)
- [`color-mix()`](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/color-mix#browser_compatibility)

Beide sind in modernen Versionen von Chrome, Edge, Firefox und Safari verfügbar.

## Eine pragmatische Upgrade-Checkliste {#a-pragmatic-upgrade-checklist}

1. Suchen Sie nach `--dwc-color-*-c` und löschen Sie diese Deklarationen.
2. Suchen Sie nach `hsla(var(--dwc-shadow-color)` und ersetzen Sie sie durch ein Schatten-Token (`var(--dwc-shadow-m)`) oder schreiben Sie als `oklch(from ...)` um.
3. Suchen Sie nach direkten Palettenstufenreferenzen (`--dwc-color-{name}-{number}`). Wenn sich davon welche auf tiefenmodusspezifische Stile beziehen, wechseln Sie zu Variationstokens (`--dwc-color-{name}`, `-dark`, `-light`).
4. Suchen Sie nach benannten Schriftgrößenreferenzen (`--dwc-font-size-m`, `-l` usw.). Wenn Sie die v25-Größe wünschen, steigen Sie um eine Stufe auf.
5. Suchen Sie nach `--dwc-font-weight-semibold`. Wenn Sie 500 wollten, wechseln Sie zu `--dwc-font-weight-medium`.
6. Wenn Sie Platz um fokussierbare Elemente mit `--dwc-focus-ring-width` reservieren, fügen Sie `--dwc-focus-ring-gap` zum Padding hinzu.
7. Öffnen Sie die App, klicken Sie herum. Die meisten Apps benötigen nichts weiter.
