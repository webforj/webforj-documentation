---
sidebar_position: 9
title: State
_i18n_hash: 3dc9896bce3e0577b2407f8ae4c863d0
---
State tokens definiëren hoe componenten visueel reageren op gebruikersinteractie, zoals wanneer ze zijn uitgeschakeld of gefocust. Deze variabelen helpen ervoor te zorgen dat er consistent gedrag en styling is over alle UI-elementen, en kunnen gemakkelijk worden aangepast om overeen te komen met uw ontwerpsysteem.

## Uitgeschakelde staat {#disabled-state}
De eigenschappen van de uitgeschakelde staat worden gebruikt om een element visueel inactief en niet-interactief te laten lijken.

De opaciteit past zich aan het huidige thema aan voor optimale zichtbaarheid in zowel lichte als donkere modus.

### Voorbeeld {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variabelen {#variables}

| **Variabele**            | **Standaardwaarde**       | **Beschrijving** |
|--------------------------|---------------------------|-----------------|
| `--dwc-disabled-opacity` | Past zich aan lichte/donkere modus aan | Verminderde opaciteit voor uitgeschakelde elementen |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) | |

---

## Focusstaat {#focus-state}

Wanneer een component focus ontvangt, wordt er een focusring rond het element weergegeven om de actieve staat aan te geven. De focusring gebruikt een gap-ringpatroon met een oppervlakgekleurde binnenste ruimte en een gekleurde buitenste ring.

### Variabelen {#variables-1}

| **Variabele**            | **Standaardwaarde** | **Beschrijving** |
|---------------------------|---------------------|-----------------|
| `--dwc-focus-ring-a`      | 0.75                | Alpha-opaciteit van de focusring |
| `--dwc-focus-ring-width`  | 2px                 | Dikte van de focusring |
| `--dwc-focus-ring-gap`    | 2px                 | Ruimte tussen de rand van het component en de ring |

Elke kleurenpalet genereert zijn eigen focusringvariabele:

| Variabele patroon        | Beschrijving |
|---|---|
| `--dwc-focus-ring-{name}` | Focusring schaduw getint met de kleur van het palet. |

Waar `{name}` een van de volgende is: `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`. Zie [Component Themas](./colors#theming-components-with-abstract-variables) voor details.

<dwc-doc-focus-rings></dwc-doc-focus-rings>

---

## Schalen {#scales}

Schaaltransformaties worden gebruikt voor druk/klik feedbackanimaties op interactieve elementen.

| **Variabele**              | **Standaardwaarde** | **Beschrijving** |
|---------------------------|---------------------|-----------------|
| `--dwc-scale-press`       | 0.97                | Standaard druk schaal (3% krimp) |
| `--dwc-scale-press-deep`  | 0.93                | Diepe druk schaal (7% krimp) |

<dwc-doc-scales></dwc-doc-scales>
