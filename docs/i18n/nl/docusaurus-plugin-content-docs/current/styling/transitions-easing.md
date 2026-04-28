---
sidebar_position: 10
title: Transitions & Easing
_i18n_hash: 49efb98e49f030a3c09f7e629ad95eb8
---
Transitievariabelen worden gebruikt om consistente animatieduur in uw app te bieden. Ze bepalen hoe lang een animatie duurt om te voltooien.

### Voorbeeld {#example}

```css
.element {
  transition: background-color var(--dwc-transition-slow);
}
```

### Variabelen {#variables}

| **Variabele**              | **Standaardwaarde**              |
|----------------------------|----------------------------------|
| `--dwc-transition-x-slow`  | `1000ms`                        |
| `--dwc-transition-slow`    | `300ms`                         |
| `--dwc-transition-medium`  | `250ms`                         |
| `--dwc-transition-fast`    | `150ms`                         |
| `--dwc-transition-x-fast`  | `100ms`                         |
| `--dwc-transition`         | `var(--dwc-transition-medium)`  |

<dwc-doc-transitions></dwc-doc-transitions>

---

## Easing {#easing}

Easing-variabelen definiëren hoe waarden in de loop van de tijd veranderen, waardoor overgangen natuurlijker aanvoelen.

### Voorbeeld {#example-1}

```css
.element {
  transition: transform var(--dwc-transition) var(--dwc-ease-inOutBack);
}
```

### Standaard easing {#standard-easings}

Dit zijn de algemene easing curves die door de meeste componenten worden gebruikt:

| **Variabele** | **Cubic Bezier** |
|---------------|------------------|
| `--dwc-ease` | `cubic-bezier(0.4, 0, 0.2, 1)` |
| `--dwc-ease-out` | `cubic-bezier(0, 0, 0.2, 1)` |
| `--dwc-ease-in` | `cubic-bezier(0.4, 0, 1, 1)` |
| `--dwc-ease-outGlide` | `cubic-bezier(0.32, 0.72, 0, 1)` |

### Uitgebreide easing {#extended-easings}

| **Variabele** | **Cubic Bezier** | **Testlink** |
|---------------|------------------|---------------|
| `--dwc-ease-inQuad` | `cubic-bezier(0.55, 0.085, 0.68, 0.53)` | [Test het](https://cubic-bezier.com/#0.55,0.085,0.68,0.53) |
| `--dwc-ease-outQuad` | `cubic-bezier(0.25, 0.46, 0.45, 0.94)` | [Test het](https://cubic-bezier.com/#0.25,0.46,0.45,0.94) |
| `--dwc-ease-inOutQuad` | `cubic-bezier(0.455, 0.03, 0.515, 0.955)` | [Test het](https://cubic-bezier.com/#0.455,0.03,0.515,0.955) |
| `--dwc-ease-inCubic` | `cubic-bezier(0.55, 0.055, 0.675, 0.19)` | [Test het](https://cubic-bezier.com/#0.55,0.055,0.675,0.19) |
| `--dwc-ease-outCubic` | `cubic-bezier(0.215, 0.61, 0.355, 1)` | [Test het](https://cubic-bezier.com/#0.215,0.61,0.355,1) |
| `--dwc-ease-inOutCubic` | `cubic-bezier(0.645, 0.045, 0.355, 1)` | [Test het](https://cubic-bezier.com/#0.645,0.045,0.355,1) |
| `--dwc-ease-inQuart` | `cubic-bezier(0.895, 0.03, 0.685, 0.22)` | [Test het](https://cubic-bezier.com/#0.895,0.03,0.685,0.22) |
| `--dwc-ease-outQuart` | `cubic-bezier(0.165, 0.84, 0.44, 1)` | [Test het](https://cubic-bezier.com/#0.165,0.84,0.44,1) |
| `--dwc-ease-inOutQuart` | `cubic-bezier(0.77, 0, 0.175, 1)` | [Test het](https://cubic-bezier.com/#0.77,0,0.175,1) |
| `--dwc-ease-inQuint` | `cubic-bezier(0.755, 0.05, 0.855, 0.06)` | [Test het](https://cubic-bezier.com/#0.755,0.05,0.855,0.06) |
| `--dwc-ease-outQuint` | `cubic-bezier(0.23, 1, 0.32, 1)` | [Test het](https://cubic-bezier.com/#0.23,1,0.32,1) |
| `--dwc-ease-inOutQuint` | `cubic-bezier(0.86, 0, 0.07, 1)` | [Test het](https://cubic-bezier.com/#0.86,0,0.07,1) |
| `--dwc-ease-inExpo` | `cubic-bezier(0.95, 0.05, 0.795, 0.035)` | [Test het](https://cubic-bezier.com/#0.95,0.05,0.795,0.035) |
| `--dwc-ease-outExpo` | `cubic-bezier(0.19, 1, 0.22, 1)` | [Test het](https://cubic-bezier.com/#0.19,1,0.22,1) |
| `--dwc-ease-inOutExpo` | `cubic-bezier(1, 0, 0, 1)` | [Test het](https://cubic-bezier.com/#1,0,0,1) |
| `--dwc-ease-inCirc` | `cubic-bezier(0.6, 0.04, 0.98, 0.335)` | [Test het](https://cubic-bezier.com/#0.6,0.04,0.98,0.335) |
| `--dwc-ease-outCirc` | `cubic-bezier(0.075, 0.82, 0.165, 1)` | [Test het](https://cubic-bezier.com/#0.075,0.82,0.165,1) |
| `--dwc-ease-inOutCirc` | `cubic-bezier(0.785, 0.135, 0.15, 0.86)` | [Test het](https://cubic-bezier.com/#0.785,0.135,0.15,0.86) |
| `--dwc-ease-inBack` | `cubic-bezier(0.36, 0, 0.66, -0.56)` | [Test het](https://cubic-bezier.com/#0.36,0,0.66,-0.56) |
| `--dwc-ease-outBack` | `cubic-bezier(0.34, 1.56, 0.64, 1)` | [Test het](https://cubic-bezier.com/#0.34,1.56,0.64,1) |
| `--dwc-ease-inOutBack` | `cubic-bezier(0.68, -0.6, 0.32, 1.6)` | [Test het](https://cubic-bezier.com/#0.68,-0.6,0.32,1.6) |
