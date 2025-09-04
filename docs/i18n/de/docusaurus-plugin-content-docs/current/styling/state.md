---
sidebar_position: 9
title: State
_i18n_hash: a6e594262709137318ed90066759b577
---
Zustands-Tokens definieren, wie Komponenten visuell auf die Benutzerinteraktion reagieren – zum Beispiel, wenn sie deaktiviert oder fokussiert sind. Diese Variablen stellen sicher, dass das Verhalten und das Styling aller UI-Elemente konsistent sind und können leicht angepasst werden, um das Designsystem zu ergänzen.

## Deaktivierter Zustand {#disabled-state}
Die Eigenschaften des deaktivierten Zustands werden verwendet, um ein Element visuell inaktiv und nicht interaktiv erscheinen zu lassen.

### Beispiel {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variablen {#variables}

| **Variable**             | **Standardwert**          |
|--------------------------|---------------------------|
| `--dwc-disabled-opacity` | 0.7                       |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) |

---

## Fokussierter Zustand {#focus-state}

Wenn eine Komponente den Fokus erhält, wird ein Fokus-Rand darum angezeigt, um ihren aktiven Zustand anzuzeigen. Sie können das Erscheinungsbild des Rings mithilfe der folgenden Variablen anpassen. Diese Variablen werden zusammen mit den Einstellungen des Komponenten-Themen für den Fokus-Rand verwendet.

### Variablen {#variables-1}

| **Variable**              | **Standardwert** |
|---------------------------|------------------|
| `--dwc-focus-ring-l`      | 45%              |
| `--dwc-focus-ring-a`      | 0.4              |
| `--dwc-focus-ring-width`  | 3px              |
