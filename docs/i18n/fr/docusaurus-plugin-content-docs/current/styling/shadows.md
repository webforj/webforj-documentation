---
sidebar_position: 7
title: Shadows
_i18n_hash: 6f5378b91beaf0c663bdc9de9b67233a
---
Les propriétés d'ombre sont utilisées pour ajouter des effets d'ombre autour du cadre d'un élément. Vous pouvez définir plusieurs effets séparés par des virgules. Dans la plupart des cas, les ombres sont utilisées pour signifier des éléments qui sont superposés dans l'interface utilisateur.

<Head>
  <style>{`
  table {
    width: 100%;
    display: table;
  }
  `}</style>
</Head>

### Exemple {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

:::tip Couleur d'Ombre
Vous contrôlez la couleur de l'ombre en définissant la variable `--dwc-shadow-color`. Par défaut, la couleur de l'ombre est grise teintée de la couleur primaire.
:::

### Variables {#variables}

| **Variable**       | **Exemple**                             |
|--------------------|------------------------------------------|
| `--dwc-shadow-xs`  | <ShadowBox shadow="--dwc-shadow-xs" />  |
| `--dwc-shadow-s`   | <ShadowBox shadow="--dwc-shadow-s" />   |
| `--dwc-shadow-m`   | <ShadowBox shadow="--dwc-shadow-m" />   |
| `--dwc-shadow-l`   | <ShadowBox shadow="--dwc-shadow-l" />   |
| `--dwc-shadow-xl`  | <ShadowBox shadow="--dwc-shadow-xl" />  |
| `--dwc-shadow-2xl` | <ShadowBox shadow="--dwc-shadow-2xl" /> |
| `--dwc-shadow`     | <ShadowBox shadow="--dwc-shadow" />     |
