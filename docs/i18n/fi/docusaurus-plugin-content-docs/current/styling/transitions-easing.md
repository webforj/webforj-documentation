---
sidebar_position: 10
title: Transitions & Easing
_i18n_hash: d2f7570b46bfa778ca967908d6e7d901
---
Siirtymämuutujat käytetään tarjoamaan yhdenmukaisia animoituvia kestoja sovelluksessasi. Ne kontrolloivat, kuinka kauan animaatio kestää valmistuakseen.

### Esimerkki {#example}

```css
.element {
  transition: var(--dwc-transition-slow) background-color;
}
```

### Muuttujat {#variables}

| **Muuttuja**              | **Oletusarvo** | **Esimerkki**                               |
|---------------------------|----------------|--------------------------------------------|
| `--dwc-transition-x-slow` | 1000ms         | <TransitionBox speed="--dwc-transition-x-slow" /> |
| `--dwc-transition-slow`   | 500ms          | <TransitionBox speed="--dwc-transition-slow" />   |
| `--dwc-transition-medium` | 250ms          | <TransitionBox speed="--dwc-transition-medium" /> |
| `--dwc-transition-fast`   | 150ms          | <TransitionBox speed="--dwc-transition-fast" />   |
| `--dwc-transition-x-fast` | 50ms           | <TransitionBox speed="--dwc-transition-x-fast" /> |
| `--dwc-transition`        | var(--dwc-transition-medium) | <TransitionBox speed="--dwc-transition" /> |

---

## Helpotukset {#easing}

Helpotusmuuttujat määrittelevät, kuinka arvot muuttuvat ajan myötä, mikä tekee siirtymistä luonnollisemman tuntuiseksi.

### Esimerkki {#example-1}

```css
.element {
  transition: transform var(--dwc-transition) var(--dwc-ease-inOutBack);
}
```

Kokeile hiiren osoittamista jokaisen helpotuksen esikatselun yli nähdäksesi sen animaatioefektin.

### Muuttujat {#variables-1}

| **Muuttuja** | **Cubic Bezier** | **Esimerkki** |
|--------------|------------------|---------------|
| `--dwc-ease-inQuad` | [cubic-bezier(0.55, 0.085, 0.68, 0.53)](https://cubic-bezier.com/#0.55,0.085,0.68,0.53) | <TransitionBox easing="--dwc-ease-inQuad" /> |
| `--dwc-ease-outQuad` | [cubic-bezier(0.25, 0.46, 0.45, 0.94)](https://cubic-bezier.com/#0.25,0.46,0.45,0.94) | <TransitionBox easing="--dwc-ease-outQuad" /> |
| `--dwc-ease-inOutQuad` | [cubic-bezier(0.455, 0.03, 0.515, 0.955)](https://cubic-bezier.com/#0.455,0.03,0.515,0.955) | <TransitionBox easing="--dwc-ease-inOutQuad" /> |
| `--dwc-ease-inCubic` | [cubic-bezier(0.55, 0.055, 0.675, 0.19)](https://cubic-bezier.com/#0.55,0.055,0.675,0.19) | <TransitionBox easing="--dwc-ease-inCubic" /> |
| `--dwc-ease-outCubic` | [cubic-bezier(0.215, 0.61, 0.355, 1)](https://cubic-bezier.com/#0.215,0.61,0.355,1) | <TransitionBox easing="--dwc-ease-outCubic" /> |
| `--dwc-ease-inQutCubic` | [cubic-bezier(0.645, 0.045, 0.355, 1)](https://cubic-bezier.com/#0.645,0.045,0.355,1) | <TransitionBox easing="--dwc-ease-inQutCubic" /> |
| `--dwc-ease-inQuart` | [cubic-bezier(0.895, 0.03, 0.685, 0.22)](https://cubic-bezier.com/#0.895,0.03,0.685,0.22) | <TransitionBox easing="--dwc-ease-inQuart" /> |
| `--dwc-ease-outQuart` | [cubic-bezier(0.165, 0.84, 0.44, 1)](https://cubic-bezier.com/#0.165,0.84,0.44,1) | <TransitionBox easing="--dwc-ease-outQuart" /> |
| `--dwc-ease-inQutQuart` | [cubic-bezier(0.77,0,0.175,1)](https://cubic-bezier.com/#0.77,0,0.175,1) | <TransitionBox easing="--dwc-ease-inQutQuart" /> |
| `--dwc-ease-inQuint` | [cubic-bezier(0.755, 0.05, 0.855, 0.06)](https://cubic-bezier.com/#0.755,0.05,0.855,0.06) | <TransitionBox easing="--dwc-ease-inQuint" /> |
| `--dwc-ease-outQuint` | [cubic-bezier(0.23, 1, 0.32, 1)](https://cubic-bezier.com/#0.23,1,0.32,1) | <TransitionBox easing="--dwc-ease-outQuint" /> |
| `--dwc-ease-inQutQuint` | [cubic-bezier(0.86, 0, 0.07, 1)](https://cubic-bezier.com/#0.86,0,0.07,1) | <TransitionBox easing="--dwc-ease-inQutQuint" /> |
| `--dwc-ease-inExpo` | [cubic-bezier(0.95, 0.05, 0.795, 0.035)](https://cubic-bezier.com/#0.95,0.05,0.795,0.035) | <TransitionBox easing="--dwc-ease-inExpo" /> |
| `--dwc-ease-outExpo` | [cubic-bezier(0.19, 1, 0.22, 1)](https://cubic-bezier.com/#0.19,1,0.22,1) | <TransitionBox easing="--dwc-ease-outExpo" /> |
| `--dwc-ease-inOutExpo` | [cubic-bezier(1, 0, 0, 1)](https://cubic-bezier.com/#1,0,0,1) | <TransitionBox easing="--dwc-ease-inOutExpo" /> |
| `--dwc-ease-inCirc` | [cubic-bezier(0.6, 0.04, 0.98, 0.335)](https://cubic-bezier.com/#0.6,0.04,0.98,0.335) | <TransitionBox easing="--dwc-ease-inCirc" /> |
| `--dwc-ease-outCirc` | [cubic-bezier(0.075, 0.82, 0.165, 1)](https://cubic-bezier.com/#0.075,0.82,0.165,1) | <TransitionBox easing="--dwc-ease-outCirc" /> |
| `--dwc-ease-inOutCirc` | [cubic-bezier(0.785, 0.135, 0.15, 0.86)](https://cubic-bezier.com/#0.785,0.135,0.15,0.86) | <TransitionBox easing="--dwc-ease-inOutCirc" /> |
| `--dwc-ease-inBack` | [cubic-bezier(0.36, 0, 0.66, -0.56)](https://cubic-bezier.com/#0.36,0,0.66,-0.56) | <TransitionBox easing="--dwc-ease-inBack" /> |
| `--dwc-ease-outBack` | [cubic-bezier(0.34, 1.56, 0.64, 1)](https://cubic-bezier.com/#0.34,1.56,0.64,1) | <TransitionBox easing="--dwc-ease-outBack" /> |
| `--dwc-ease-inOutBack` | [cubic-bezier(0.68, -0.6, 0.32, 1.6)](https://cubic-bezier.com/#0.68,-0.6,0.32,1.6) | <TransitionBox easing="--dwc-ease-inOutBack" /> |
