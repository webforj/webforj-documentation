---
sidebar_position: 9
title: State
_i18n_hash: b7227f8d2022e7e6eab96de3f802aa20
---
Zustandtokens definieren, wie Komponenten visuell auf Benutzerinteraktionen reagieren – z. B. wenn sie deaktiviert oder fokussiert sind. Diese Variablen helfen, ein konsistentes Verhalten und Styling über alle UI-Elemente hinweg sicherzustellen und können leicht angepasst werden, um Ihrem Designsystem zu entsprechen.

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

| **Variable**              | **Standardwert**          |
|---------------------------|---------------------------|
| `--dwc-disabled-opacity`  | 0.7                       |
| `--dwc-disabled-cursor`   | var(--dwc-cursor-disabled) |

---

## Fokussierter Zustand {#focus-state}

Wenn eine Komponente den Fokus erhält, wird ein Fokusring darum angezeigt, um ihren aktiven Zustand anzuzeigen. Sie können das Erscheinungsbild des Rings mithilfe der folgenden Variablen anpassen. Diese Variablen werden in Verbindung mit den Fokusring-Einstellungen des Komponententhemes verwendet.

### Variablen {#variables-1}

| **Variable**              | **Standardwert** |
|---------------------------|-------------------|
| `--dwc-focus-ring-l`      | 45%               |
| `--dwc-focus-ring-a`      | 0.4               |
| `--dwc-focus-ring-width`  | 3px               |
