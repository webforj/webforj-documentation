---
sidebar_position: 5
title: Koko ja Väli
_i18n_hash: 4efe9ef910459481ca90eec87c26ebe0
---
Spacing and sizing tokens are used to provide consistent spacing and sizing in your app. All sizing and spacing properties are defined in `rem`.

:::info REM Yksikkö
`rem` on suhteellinen pituusyksikkö. Se on suhteellinen [juurielementin](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/html) fonttikokoon.
:::

## Koko {#sizing}

Käytä näitä ominaisuuksia säätääksesi komponentin kokoa (leveys, korkeus). `m` on vakio koko lähes kaikille komponenteille.

:::tip Koon valinta
Kun valitset kokoa, varmista aina, että se on tarpeeksi suuri kosketuskohteita varten.
:::

### Esimerkki {#example}

```css
.element {
  width: var(--dwc-size-m);
  height: var(--dwc-size-m);
}
```

### Muuttujat {#variables}

| **Muuttuja**     | **Oletusarvo** | **Esimerkki**                         |
| ---------------- | ----------------- | ----------------------------------- |
| `--dwc-size-3xs` | 1.125rem          | <SizingBox size="--dwc-size-3xs" /> |
| `--dwc-size-2xs` | 1.375rem          | <SizingBox size="--dwc-size-2xs" /> |
| `--dwc-size-xs`  | 1.625rem          | <SizingBox size="--dwc-size-xs" />  |
| `--dwc-size-s`   | 1.875rem          | <SizingBox size="--dwc-size-s" />   |
| `--dwc-size-m`   | 2.25rem           | <SizingBox size="--dwc-size-m" />   |
| `--dwc-size-l`   | 2.75rem           | <SizingBox size="--dwc-size-l" />   |
| `--dwc-size-xl`  | 3.5rem            | <SizingBox size="--dwc-size-xl" />  |
| `--dwc-size-2xl` | 4rem              | <SizingBox size="--dwc-size-2xl" /> |
| `--dwc-size-3xl` | 4.25rem           | <SizingBox size="--dwc-size-3xl" /> |
| `--dwc-size`     | var(--dwc-size-m) | <SizingBox size="--dwc-size" />     |

## Väli {#spacing}

Käytä näitä ominaisuuksia säätääksesi komponenttien väliä (marginaali, täyttö).

### Esimerkki {#example-1}

```css
.element {
  padding: var(--dwc-space-m);
}
```

### Muuttujat {#variables-1}

| **Muuttuja**      | **Oletusarvo**  | **Esimerkki**                            |
| ----------------- | ------------------ | -------------------------------------- |
| `--dwc-space-3xs` | 0.075rem           | <SpacingBox space="--dwc-space-3xs" /> |
| `--dwc-space-2xs` | 0.15rem            | <SpacingBox space="--dwc-space-2xs" /> |
| `--dwc-space-xs`  | 0.25rem            | <SpacingBox space="--dwc-space-xs" />  |
| `--dwc-space-s`   | 0.5rem             | <SpacingBox space="--dwc-space-s" />   |
| `--dwc-space-m`   | 1rem               | <SpacingBox space="--dwc-space-m" />   |
| `--dwc-space-l`   | 1.25rem            | <SpacingBox space="--dwc-space-l" />   |
| `--dwc-space-xl`  | 1.5rem             | <SpacingBox space="--dwc-space-xl" />  |
| `--dwc-space-2xl` | 1.75rem            | <SpacingBox space="--dwc-space-2xl" /> |
| `--dwc-space-3xl` | 2rem               | <SpacingBox space="--dwc-space-3xl" /> |
| `--dwc-space`     | var(--dwc-space-s) | <SpacingBox space="--dwc-space" />     |
