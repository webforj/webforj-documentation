---
sidebar_position: 9
title: State
_i18n_hash: 3dc9896bce3e0577b2407f8ae4c863d0
---
State-Tokens definieren, wie Komponenten visuell auf Benutzerinteraktionen reagieren, wie beispielsweise, wenn sie deaktiviert oder fokussiert sind. Diese Variablen helfen, ein konsistentes Verhalten und Styling über alle UI-Elemente hinweg sicherzustellen und können leicht an Ihr Designsystem angepasst werden.

## Deaktivierter Zustand {#disabled-state}
Die Eigenschaften des deaktivierten Zustands werden verwendet, um ein Element visuell inaktiv und nicht interaktiv erscheinen zu lassen.

Die Opazität passt sich dem aktuellen Thema für optimale Sichtbarkeit in hellen und dunklen Modi an.

### Beispiel {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variablen {#variables}

| **Variable**             | **Standardwert**           | **Beschreibung** |
|--------------------------|----------------------------|-----------------|
| `--dwc-disabled-opacity` | Passt sich dem Licht-/Dunkel-Modus an | Reduzierte Opazität für deaktivierte Elemente |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) | |

---

## Fokuszustand {#focus-state}

Wenn eine Komponente den Fokus erhält, wird ein Fokusring darum angezeigt, um ihren aktiven Zustand anzuzeigen. Der Fokusring verwendet ein Gap-Ring-Muster mit einem oberflächenfarbigen inneren Abstand und einem farbigen äußeren Ring.

### Variablen {#variables-1}

| **Variable**              | **Standardwert** | **Beschreibung** |
|---------------------------|-------------------|-----------------|
| `--dwc-focus-ring-a`      | 0.75              | Alpha-Opazität des Fokusrings |
| `--dwc-focus-ring-width`  | 2px               | Dicke des Fokusrings |
| `--dwc-focus-ring-gap`    | 2px               | Abstand zwischen dem Komponentenkante und dem Ring |

Jede Farbpalette generiert ihre eigene Fokusring-Variable:

| Variable-Muster | Beschreibung |
|---|---|
| `--dwc-focus-ring-{name}` | Fokusring-Schatten, der mit der Farbpalette getönt ist. |

Wo `{name}` einer der folgenden ist: `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`. Siehe [Komponententhemen](./colors#theming-components-with-abstract-variables) für Details.

<dwc-doc-focus-rings></dwc-doc-focus-rings>

---

## Skalen {#scales}

Skalentransformationen werden für Druck-/Klickrückmeldungsanimationen bei interaktiven Elementen verwendet.

| **Variable**              | **Standardwert** | **Beschreibung** |
|---------------------------|-------------------|-----------------|
| `--dwc-scale-press`       | 0.97              | Standard-Druckskalierung (3% Schrumpfen) |
| `--dwc-scale-press-deep`  | 0.93              | Tiefe Druckskalierung (7% Schrumpfen) |

<dwc-doc-scales></dwc-doc-scales>
