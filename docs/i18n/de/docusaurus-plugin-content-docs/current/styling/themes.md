---
sidebar_position: 2
title: Themes
sidebar_class_name: updated-content
description: >-
  Apply built-in light, dark, and dark-pure themes with @AppTheme or define
  custom themes through data-app-theme selectors.
_i18n_hash: 91e1a18f11aadea66df804dbaa4917d9
---
Ein Thema in webforJ ist eine benannte Menge von CSS-Custom-Properties (Design-Tokens), die steuert, wie jede Komponente aussieht. Der Wechsel zwischen Themen berechnet Farben, Schatten, Oberflächen und Grenzen sofort in der gesamten App neu, ohne dass ein neuer Build erforderlich ist.

## Eingebaute Themen {#built-in-themes}

webforJ bietet drei App-Themen standardmäßig an:

| Thema       | Hintergrund      | Farbton                   |
|-------------|-----------------|---------------------------|
| `light`     | Hell (Standard)  | Subtiler Primärfarbton   |
| `dark`      | Dunkel          | Subtiler Primärfarbton   |
| `dark-pure` | Dunkel          | Keiner (reine neutrale Grautöne) |

Jede App kann zur Laufzeit zwischen ihnen wechseln, und zusätzliche benutzerdefinierte Themen können neben den eingebauten definiert werden.

## Anwenden eines Themas {#applying-a-theme}

Setzen Sie das aktive Thema deklarativ mit der Annotation `@AppTheme` oder programmgesteuert mit `App.setTheme()`. Der Themenname muss eines von `system`, `light`, `dark`, `dark-pure` oder der Name eines benutzerdefinierten Themas sein.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // App-Code
}

// oder programmgesteuert
App.setTheme("dark-pure");
```

Der Aufruf von `App.setTheme()` zu einem späteren Zeitpunkt wechselt die App zu einem anderen Thema.

## Farbgebung {#color-scheme}

Die CSS-Deklaration `color-scheme` informiert den Browser darüber, wie er seine eingebauten Oberflächen wie native Bildlaufleisten, Formsteuerelement-Widgets, Autofill-Hervorhebungen und den Standardseitenhintergrund rendern soll, bevor CSS geladen wird. Die eingebauten Themen `dark` und `dark-pure` setzen bereits `color-scheme: dark` für Sie, sodass die Browseroberfläche automatisch mit den dunklen Oberflächen verschmilzt.

Sie müssen nur daran denken, wenn Sie ein benutzerdefiniertes dunkles Thema definieren. In diesem Fall fügen Sie `color-scheme: dark` im Selektor des Themas ein:

```css
html[data-app-theme="brand-dark"] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

Wenn Sie es überspringen, bleiben Bildlaufleisten und Autofill-Rechtecke standardmäßig im hellen Modus und wirken fehl am Platz über Ihren dunklen Oberflächen. Helle Themen benötigen die Deklaration nicht, da Browser standardmäßig auf Hell eingestellt sind.

## Benutzerpräferenzen folgen {#following-the-users-preference}

Die meisten Betriebssysteme lassen den Nutzern die Wahl zwischen einem hellen oder dunklen Erscheinungsbild systemweit. webforJ kann diese Präferenz berücksichtigen und automatisch das richtige Thema auswählen.

Registrieren Sie, welches Thema für jeden Erscheinungszustand angewendet werden soll, mit `@AppLightTheme` und `@AppDarkTheme` (oder `App.setLightTheme()` und `App.setDarkTheme()`), und übergeben Sie dann das reservierte Schlüsselwort `"system"` an `App.setTheme()` (oder `@AppTheme("system")`), damit webforJ automatisch basierend auf der OS-Präferenz zwischen ihnen auswählt.

```java
@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
class MyApp extends App {
  // App-Code
}
```

Entsprechende programmgesteuerte Form:

```java
App.setLightTheme("light");
App.setDarkTheme("dark");
App.setTheme("system");
```

`"system"` ist ein reserviertes Schlüsselwort. webforJ löst es zur Laufzeit entweder auf das registrierte helle oder dunkle Thema auf und löst es automatisch neu, wann immer sich die OS-Präferenz ändert. Nach der Auflösung ist das tatsächliche Attribut `data-app-theme` auf der Seite `light` oder `dark`, sodass alle CSS-Überschreibungen auf diese Namen abzielen sollten, anstatt auf `"system"`.

:::info OS-Level Erscheinungseinstellungen
Wo Nutzer die systemweite Erscheinungseinstellung aktivieren, variiert je nach Plattform:

- **Windows 10/11**: Einstellungen > Personalisierung > Farben > Wählen Sie Ihre Farbe
- **macOS**: Systemeinstellungen > Erscheinungsbild
- **iOS**: Einstellungen > Anzeige & Helligkeit > Erscheinungsbild
- **Android**: Einstellungen > Anzeige > Dunkles Thema
:::

## Standardthemen überschreiben {#overriding-default-themes}

Die meiste Markenarbeit wird durch **Überschreiben der bestehenden Themen** erledigt, anstatt neue zu erstellen. Passen Sie die Grundfarben (oder andere Tokens) für die eingebauten Themen `light`, `dark` und `dark-pure` an, und jede Komponente übernimmt das neue Aussehen automatisch.

Sie können das **light** Thema überschreiben, indem Sie CSS-Custom-Properties im Selektor `:root` neu definieren.

:::info `:root` Pseudo-Klasse
Die `:root` CSS-Pseudo-Klasse zielt auf das Wurzel-Element des Dokuments ab. In HTML repräsentiert es das `<html>`-Element und hat eine höhere Spezifität als der einfache `html`-Selektor.
:::

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

Um die **dark** oder **dark-pure** Themen zu überschreiben, verwenden Sie Attributselekoren auf dem `<html>`-Element:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-seed: #a855f7;
}

html[data-app-theme="dark-pure"] {
  --dwc-color-primary-seed: #a855f7;
}
```

Der Wechsel des Themas mit `App.setTheme("dark")` aktiviert das neu gestaltete dunkle Thema, kein neuer Themenname ist erforderlich.

## Erstellen benutzerdefinierter Themen {#creating-custom-themes}

Erstellen Sie ein völlig neues Thema nur dann, wenn Sie eines benötigen, das neben den eingebauten koexistiert (zum Beispiel eine hochkontrastierende Variante oder ein kundenspezifisches Design). Wählen Sie einen einzigartigen Namen und definieren Sie es unter seinem eigenen Selektor `html[data-app-theme='THEME_NAME']`:

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

Um ein benutzerdefiniertes Thema dunkel zu machen, setzen Sie `--dwc-dark-mode: 1` und `color-scheme: dark`:

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

Dann in Ihrer App:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // App-Code
}

// oder programmgesteuert
App.setTheme("new-theme");
```

## Arbeiten mit DWC-Tokens {#working-with-dwc-tokens}

Einige Gewohnheiten halten benutzerdefiniertes CSS im Einklang mit dem Designsystem und verhindern, dass es im Dunkelmodus oder zukünftigen Versionen abdriftet.

### Referenzieren Sie immer Tokens mit `var(...)` {#always-reference-tokens-with-var}

Hartcodierte Farbwerte (`#3b82f6`, `rgb(59 130 246)`, `oklch(0.6 0.18 250)`) passen sich nicht an den Dunkelmodus an und verfolgen keine Farbpalette-Änderungen. Verwenden Sie stattdessen das Token.

```css
/* vermeiden */
.my-panel {
  background: #ffffff;
  color: #1f2937;
  border: 1px solid #e5e7eb;
}

/* bevorzugen */
.my-panel {
  background: var(--dwc-surface-3);
  color: var(--dwc-color-body-text);
  border: 1px solid var(--dwc-border-color);
}
```

### Bevorzugen Sie Variations-Tokens über rohe Schrittzahlen {#prefer-variation-tokens-over-raw-step-numbers}

Variations-Tokens (`--dwc-color-primary`, `-dark`, `-light`, `-text`, `-alt`) lösen sich automatisch zu einem anderen Schritt im hellen bzw. dunklen Modus. Rohe Schrittzahlen (`--dwc-color-primary-50`) tun dies nicht.

```css
/* vermeiden - bleibt in beiden Modi bei Schritt 50 eingefroren */
.badge {
  background: var(--dwc-color-primary-50);
}

/* bevorzugen - verschiebt den Schritt im Dunkelmodus */
.badge {
  background: var(--dwc-color-primary);
}
```

### Verwenden Sie das Suffix, das der Rolle entspricht {#use-the-suffix-that-matches-the-role}

| Suffix                          | Rolle                                                              |
|---------------------------------|-------------------------------------------------------------------|
| `--dwc-color-{name}`            | Solide Füllung in voller Stärke (Schaltflächen, Abzeichen, Banner) |
| `--dwc-color-{name}-dark`       | Aktiver / gedrückter Zustand                                       |
| `--dwc-color-{name}-light`      | Hover / Fokus-Hintergrund                                         |
| `--dwc-color-{name}-alt`        | Subtiler getönter Hintergrund für Hervorhebungen und alternative Reihen |
| `--dwc-color-{name}-text`       | Farbiger Text auf einer neutralen Oberfläche                       |
| `--dwc-color-on-{name}-text`    | Text, der **auf** dem farbigen Hintergrund platziert ist (automatische Kontrastanpassung) |
| `--dwc-border-color-{name}`     | Grenzen und Trennstriche                                          |

### Reservieren Sie Oberflächen und Grenzen für ihre Rollen {#reserve-surfaces-and-borders-for-their-roles}

Oberflächen (`--dwc-surface-1` / `-2` / `-3`) bilden die Seitenhierarchie. Grenzen (`--dwc-border-color`, `--dwc-border-color-*`) zeichnen Trennlinien. Die Wiederverwendung von Palette-Schritten für diese Rollen funktioniert visuell, führt jedoch zu einem Verlust der automatischen Anpassung im Modus, die die speziellen Tokens tragen.

### Überschreiben auf der Seed-Ebene in benutzerdefinierten Themen {#override-at-the-seed-level-in-custom-themes}

Beim Erstellen eines benutzerdefinierten Themas setzen Sie den Seed (`--dwc-color-{name}-h`, `-s` oder `-seed`), anstatt einzelne Schritte zu überschreiben. Der Generator baut die gesamte 19-Schritte-Palette um den Seed herum neu auf und behält die gesamte Tonhöhe konsistent. Das Überschreiben einzelner Schritte lässt den Rest der Palette gegen Ihre Markenfarbe abdriften.

```css
/* vermeiden - lässt andere Schritte inkonsistent */
html[data-app-theme="brand"] {
  --dwc-color-primary-50: #6366f1;
}

/* bevorzugen - regeneriert die gesamte Palette */
html[data-app-theme="brand"] {
  --dwc-color-primary-seed: #6366f1;
}
```

### Verwenden Sie Tokens für Abstände, Größen, Radien und Übergänge {#use-tokens-for-spacing-sizing-radius-and-transitions}

Die gleiche Regel erstreckt sich über den Rest des Designsystems: Referenzieren Sie Tokens, niemals magische Zahlen.

```css
/* vermeiden */
.my-panel {
  padding: 16px;
  border-radius: 8px;
  transition: background-color 250ms;
}

/* bevorzugen */
.my-panel {
  padding: var(--dwc-space-m);
  border-radius: var(--dwc-border-radius);
  transition: background-color var(--dwc-transition);
}
```

Hartcodierte Werte umgehen die benutzerdefinierte Schriftgröße, verriegeln Sie in einer festen Gestaltensprache und überspringen die erleichterten Zeitkurven des Designs.

### Verwenden Sie `::part(...)`, um in Komponenten zu gelangen {#use-part-to-reach-into-components}

webforJ-Komponenten verwenden Shadow DOM. Ihr internes Markup ist von äußeren Selektoren verborgen, sodass eine Regel wie `.dwc-button-label { ... }` nichts übereinstimmt. Um interne Teile zu stylen, zielen Sie auf die freigelegten Teile:

```css
/* Stil der Beschriftung in jedem primären Button */
dwc-button[theme="primary"]::part(label) {
  letter-spacing: 0.02em;
}
```

Siehe [Shadow Parts](./shadow-parts) für die vollständige Mechanik und den Abschnitt **Styling → Shadow Parts** jeder Komponente für die Teile, die sie freilegt.

### Tokens-Überschreibungen mit einem Wrapper-Selektor begrenzen {#scope-token-overrides-with-a-wrapper-selector}

CSS-Custom-Properties kaskadieren. Das Setzen eines Tokens auf einem Wrapper-Element stimmt alles darin neu ab, ohne den Rest der App zu beeinflussen.

```css
.danger-section {
  --dwc-color-primary-seed: #ef4444;
}
```

Jede Komponente innerhalb von `.danger-section` (Schaltflächen, Links, Fokus-Ringe) verwendet jetzt den Gefahr-Rotton, während das globale Thema unverändert bleibt.

### Testen in sowohl hellem als auch dunklem Modus {#test-in-both-light-and-dark-mode}

Bevor Sie benutzerdefiniertes CSS ausliefern, wechseln Sie das Thema auf `dark` und `dark-pure` und gehen Sie durch den Bildschirm. Der häufigste Rückgang sind hartcodierte Farbwerte, die in einem Modus gut aussehen und im anderen unleserlich oder nicht im Farbschema erscheinen.

### Greifen Sie nicht nach `!important` {#dont-reach-for-important}

Es entzieht sich der Kaskade und erschwert jede zukünftige Überschreibung. Wenn eine Regel nicht erfolgreich ist, liegt die Ursache fast immer in einer Spezifitätsanpassung mit einer saubereren Lösung: zielen Sie auf dasselbe Selektor ab, das das Framework verwendet, oder fügen Sie einen übergeordneten Qualifizierer hinzu. Reservieren Sie `!important` für wirklich Drittanbieter-Stil, den Sie auf keine andere Weise besiegen können.

## Komponenten-Themen {#component-themes}

Neben den App-Level-Themen unterstützen webforJ-Komponenten eine Reihe von **Komponenten-Themen** basierend auf den Standardfarbpaletten: `default`, `primary`, `success`, `warning`, `danger`, `info` und `gray`. Dies ist unabhängig vom aktiven App-Thema.

Jede Komponente dokumentiert ihre unterstützten Themen im Abschnitt **Styling → Themen**.
