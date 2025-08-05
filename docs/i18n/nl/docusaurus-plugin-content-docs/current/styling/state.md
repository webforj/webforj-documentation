---
sidebar_position: 9
title: State
_i18n_hash: b7227f8d2022e7e6eab96de3f802aa20
---
State tokens definiëren hoe componenten visueel reageren op gebruikersinteractie, zoals wanneer ze zijn uitgeschakeld of gefocust. Deze variabelen helpen ervoor te zorgen dat het gedrag en de styling consistent zijn over alle UI-elementen, en kunnen gemakkelijk worden aangepast aan uw ontwerpsysteem.

## Uitgeschakelde toestand {#disabled-state}
De eigenschappen van de uitgeschakelde toestand worden gebruikt om een element visueel inactief en niet-interactief te laten verschijnen.

### Voorbeeld {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variabelen {#variables}

| **Variabele**             | **Standaardwaarde**          |
|---------------------------|------------------------------|
| `--dwc-disabled-opacity`  | 0.7                          |
| `--dwc-disabled-cursor`   | var(--dwc-cursor-disabled)  |

---

## Focus toestand {#focus-state}

Wanneer een component focus ontvangt, wordt er een focusring rond het component weergegeven om de actieve toestand aan te geven. U kunt het uiterlijk van de ring aanpassen met behulp van de onderstaande variabelen. Deze variabelen worden gebruikt in combinatie met de instellingen van de componentthema focusring.

### Variabelen {#variables-1}

| **Variabele**             | **Standaardwaarde** |
|---------------------------|---------------------|
| `--dwc-focus-ring-l`      | 45%                 |
| `--dwc-focus-ring-a`      | 0.4                 |
| `--dwc-focus-ring-width`  | 3px                 |
