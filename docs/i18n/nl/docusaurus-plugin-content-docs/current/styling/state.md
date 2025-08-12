---
sidebar_position: 9
title: State
_i18n_hash: a6e594262709137318ed90066759b577
---
State-tokens definiëren hoe componenten visueel reageren op gebruikersinteractie—zoals wanneer ze zijn uitgeschakeld of gefocust. Deze variabelen helpen ervoor te zorgen dat gedrag en styling consistent zijn voor alle UI-elementen, en kunnen eenvoudig worden aangepast om bij je ontwerpsysteem te passen.

<!-- vale off -->
## Uitschakelde toestand {#disabled-state}
<!-- vale on -->
De eigenschappen van de uitgeschakelde toestand worden gebruikt om een element visueel inactief en niet-interactief te laten lijken.

### Voorbeeld {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variabelen {#variables}

| **Variabele**            | **Standaardwaarde**       |
|--------------------------|----------------------------|
| `--dwc-disabled-opacity` | 0.7                        |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) |

---

## Focus-toestand {#focus-state}

Wanneer een component focus ontvangt, wordt er een focusring rond het element weergegeven om de actieve staat aan te duiden. Je kunt het uiterlijk van de ring aanpassen met behulp van de onderstaande variabelen. Deze variabelen worden gebruikt in combinatie met de instellingen voor de focusring van het componentthema.

### Variabelen {#variables-1}

| **Variabele**            | **Standaardwaarde**  |
|---------------------------|----------------------|
| `--dwc-focus-ring-l`      | 45%                  |
| `--dwc-focus-ring-a`      | 0.4                  |
| `--dwc-focus-ring-width`  | 3px                  |
