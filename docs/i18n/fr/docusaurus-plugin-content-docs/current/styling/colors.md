---
sidebar_position: 3
title: Colors
_i18n_hash: 257b725aa7a7992bc6cedd91de9c9ca5
---
import ColorPalette from '@site/src/components/DWCTheme/ColorPalette/ColorPalette';

webforJ fournit un système de couleurs basé sur des propriétés personnalisées CSS. Ces variables de couleur maintiennent un style visuel cohérent à travers votre application tout en vous donnant un contrôle total pour personnaliser les palettes selon vos besoins de conception.

Vous pouvez référencer n'importe quelle couleur en utilisant la syntaxe `--dwc-color-{palette}-{shade}`, où `{palette}` est le nom du groupe de couleurs (par exemple, `primary`, `danger`, ..) et `{shade}` est un nombre de `5` à `95` par intervalles de `5`, représentant la clarté de la couleur.

```css
.element {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

:::tip Échelle des valeurs de teinte
Les valeurs de teinte varient de `5` (le plus foncé) à `95` (le plus clair), en augmentant par paliers de `5`.
:::

## Palettes de couleurs {#color-palettes}

Il existe plusieurs palettes de couleurs intégrées, chacune conçue pour des cas d'utilisation sémantiques tels que le branding, les messages de succès, les avertissements, et plus encore. Chaque palette est composée de teintes et d'ombres générées dynamiquement basées sur trois propriétés clés : `hue`, `saturation`, et `contrast-threshold`.

### Palettes disponibles {#available-palettes}

- **default** : Une palette neutre basée sur le gris teintée de la couleur primaire, utilisée pour la plupart des composants.
- **primary** : Représente généralement la couleur primaire de votre marque.
- **success**, **warning**, **danger** : Palettes sémantiques utilisées pour des indicateurs de statut appropriés.
- **info** : Une palette complémentaire optionnelle pour une emphase secondaire.
- **gray** : Une palette de niveaux de gris vraie, non teintée.
- **black/white** : Valeurs de couleur statiques

<ColorPalette></ColorPalette>

<br/>

:::tip DWC HueCraft
Pour simplifier le processus de génération de palettes conformes aux WCAG pour vos applications webforJ, vous pouvez utiliser l'outil [DWC HueCraft](https://webforj.github.io/huecraft/). Il prend en charge la création de palettes basées sur des couleurs de marque ou de logos et permet un export CSS rapide.
:::

### Comportement en mode sombre {#dark-mode-behavior}

webforJ prend en charge une stratégie de couleurs inversées en mode sombre. Plutôt que d'utiliser entièrement des palettes de couleurs séparées, il inverse la manière dont les valeurs de clarté sont interprétées.

Cela signifie que dans les **thèmes sombres**, les valeurs de teinte les plus basses (par exemple, `--dwc-color-primary-5`) deviennent claires et les valeurs les plus élevées (par exemple, `--dwc-color-primary-95`) deviennent sombres. La logique est inversée pour garantir un contraste et une lisibilité optimaux sur différents arrière-plans.

Votre code de composant reste le même, quel que soit le thème. Par exemple :

```css
.button {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

En mode clair, cela donne un fond de tonalité intermédiaire. En mode sombre, cela donne également une tonalité intermédiaire, mais inversée visuellement pour fonctionner sur des surfaces sombres. Cette approche évite la duplication, maintient vos styles cohérents et garantit des transitions visuelles fluides lors du passage entre les thèmes clairs et sombres.

### Variables de configuration de palette {#palette-configuration-variables}

Chaque palette est générée en fonction des variables suivantes :

| Variable               | Description |
|------------------------|-------------|
| `hue`                  | L'angle (en degrés) sur la roue des couleurs. Les valeurs sans unité sont interprétées comme des degrés. |
| `saturation`           | Un pourcentage indiquant l'intensité de la couleur. `100%` est entièrement saturé ; `0%` est en niveaux de gris. |
| `contrast-threshold`   | Une valeur entre `0` et `100` qui détermine si le texte doit être clair ou sombre en fonction de la clarté de l'arrière-plan. |

Vous pouvez ajuster une palette en redéfinissant ces variables dans vos styles racine. Par exemple, pour modifier la palette primaire :

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

## Thématisation des composants avec des variables abstraites {#theming-components-with-abstract-variables}

Pour simplifier le style et la cohérence à travers les composants, webforJ introduit une couche d'abstraction au-dessus des palettes de couleurs de base. Cette couche est construite sur des **variables de thème abstraites** - des propriétés personnalisées CSS qui font référence à des teintes spécifiques au sein d'une palette de couleurs.

Ces variables facilitent l'application de thèmes sur tous les composants sans référencer directement des valeurs de couleur ou des échantillons bruts. Vous pouvez les considérer comme des *raccourcis de stylisation sémantiques* qui reflètent l'intention de votre application plutôt que ses détails d'implémentation.

### Groupes de variables {#variable-groups}

Les variables de thème abstraites sont organisées en quatre groupes :

1. [Normal](#normal-state) Utilisé pour l'apparence par défaut, comme les fonds et le texte sur les composants inactifs.
2. [Dark](#darker-variant) Utilisé pour les états actifs ou sélectionnés.
3. [Light](#lighter-variant) Utilisé pour les états de survol et de mise au point.
4. [Alt](#alt-variant) Utilisé pour des mises en évidence secondaires, telles que la mise au point au clavier ou des accents subtils de l'UI.

Chaque groupe définit :

- Une couleur de fond
- Une couleur de premier plan (texte)
- Une couleur de bordure (pour les états de mise au point/hover/actif)
- Un anneau de mise au point (utilisé lorsque le composant reçoit un style de mise au point visible)

Chaque onglet ci-dessous montre les variables abstraites définies pour une palette spécifique (`primary`, `success`, `danger`, etc.). Ces variables extraient des valeurs de la palette sous-jacente (par exemple, `--dwc-color-primary-40`) et les rendent réutilisables à travers votre application.

Par exemple, au lieu d'utiliser directement `--dwc-color-primary-40`, vous pouvez appliquer `--dwc-color-primary`, qui abstrait le rôle de cette couleur comme le *fond par défaut* pour un composant de style primaire.

Modifier les valeurs de la palette à un seul endroit mettra à jour l'apparence de tous les composants qui dépendent de ces variables abstraites.

### État normal {#normal-state}

Utilisé pour l'apparence neutre de base d'un composant—quand il est inactif et non utilisé.

| Variable                           | Description                                                             |
| ---------------------------------- | ----------------------------------------------------------------------- |
| `--dwc-color-${name}`              | La couleur de fond par défaut. Utilisé aussi pour les bordures dans de nombreux composants. |
| `--dwc-color-on-${name}-text`      | La couleur du texte affiché au-dessus de la couleur de fond par défaut.                 |
| `--dwc-color-${name}-text`         | La couleur du texte lorsque le composant est placé sur l'arrière-plan de surface. |
| `--dwc-border-color-${name}`       | Couleur de bordure, principalement utilisée pour les états de survol, de mise au point et actifs.         |
| `--dwc-focus-ring-${name}`         | Ombre de l'anneau de mise au point lorsque le composant reçoit un style de mise au point visible.   |

---

### Variante plus sombre {#darker-variant}

Utilisé pour les états sélectionnés ou actifs—généralement une teinte plus profonde pour un contraste et une emphase plus forts.

| Variable                                | Description                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-dark`              | Une version plus sombre de la couleur de base. Souvent utilisée pour les états pressés ou sélectionnés. |
| `--dwc-color-on-${name}-text-dark`      | Couleur du texte lorsqu'il est utilisé sur un arrière-plan sombre.                               |
| `--dwc-color-${name}-text-dark`         | Une alternative de texte légèrement plus sombre lorsqu'elle est affichée sur la surface.            |

---

### Variante plus claire {#lighter-variant}

Utilisé pour les états de survol, de mise au point et des indices visuels moins dominants. Ce sont des teintes douces conçues pour un retour d'interaction subtil.

| Variable                                | Description                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-light`             | Une version plus claire de la couleur de base. Typiquement utilisée pour les fonds de survol/mise au point. |
| `--dwc-color-on-${name}-text-light`     | Couleur du texte lorsqu'elle est affichée sur un arrière-plan clair.                             |
| `--dwc-color-${name}-text-light`        | Une teinte de texte plus claire à utiliser dans des états moins proéminents.                    |

---

### Variante alternative {#alt-variant}

Utilisé pour une emphase secondaire ou des mises en évidence de l'UI—comme les contours de mise au point de navigation au clavier ou des indicateurs auxiliaires.

| Variable                                | Description                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-alt`               | Une version très claire de la couleur, principalement utilisée pour les mises en évidence ou les lueurs de fond. |
| `--dwc-color-on-${name}-text-alt`       | Couleur du texte lorsque l'arrière-plan est de la couleur alternative (`alt`).           |

<Tabs>

<TabItem value="Default / Tone">

```css
--dwc-color-default-dark: var(--dwc-color-default-85);
--dwc-color-on-default-text-dark: var(--dwc-color-default-text-85);
--dwc-color-default-text-dark: var(--dwc-color-default-35);

--dwc-color-default: var(--dwc-color-default-90);
--dwc-color-on-default-text: var(--dwc-color-default-text-90);
--dwc-color-default-text: var(--dwc-color-default-40);

--dwc-color-default-light: var(--dwc-color-default-95);
--dwc-color-on-default-text-light: var(--dwc-color-default-text-95);
--dwc-color-default-text-light: var(--dwc-color-default-45);

--dwc-color-default-alt: var(--dwc-color-primary-alt);
--dwc-color-on-default-text-alt: var(--dwc-color-on-primary-text-alt);

--dwc-border-color-default: var(--dwc-border-color-primary);
--dwc-focus-ring-default: var(--dwc-focus-ring-primary);
```

</TabItem>

<TabItem value="Primary">

```css
--dwc-color-primary-dark: var(--dwc-color-primary-35);
--dwc-color-on-primary-text-dark: var(--dwc-color-primary-text-35);
--dwc-color-primary-text-dark: var(--dwc-color-primary-30);

--dwc-color-primary: var(--dwc-color-primary-40);
--dwc-color-on-primary-text: var(--dwc-color-primary-text-40);
--dwc-color-primary-text: var(--dwc-color-primary-35);

--dwc-color-primary-light: var(--dwc-color-primary-45);
--dwc-color-on-primary-text-light: var(--dwc-color-primary-text-45);
--dwc-color-primary-text-light: var(--dwc-color-primary-40);

--dwc-color-primary-alt: var(--dwc-color-primary-95);
--dwc-color-on-primary-text-alt: var(--dwc-color-primary-text-95);

--dwc-border-color-primary: var(--dwc-color-primary);
--dwc-focus-ring-primary: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-primary-h),
    var(--dwc-color-primary-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-primary-a)
  );
```

</TabItem>

<TabItem value="Success">

```css
--dwc-color-success-dark: var(--dwc-color-success-20);
--dwc-color-on-success-text-dark: var(--dwc-color-success-text-20);
--dwc-color-success-text-dark: var(--dwc-color-success-15);

--dwc-color-success: var(--dwc-color-success-25);
--dwc-color-on-success-text: var(--dwc-color-success-text-25);
--dwc-color-success-text: var(--dwc-color-success-20);

--dwc-color-success-light: var(--dwc-color-success-30);
--dwc-color-on-success-text-light: var(--dwc-color-success-text-30);
--dwc-color-success-text-light: var(--dwc-color-success-25);

--dwc-color-success-alt: var(--dwc-color-success-95);
--dwc-color-on-success-text-alt: var(--dwc-color-success-text-95);

--dwc-border-color-success: var(--dwc-color-success);
--dwc-focus-ring-success: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-success-h),
    var(--dwc-color-success-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-success-a)
  );
```

</TabItem>

<TabItem value="Warning">

```css
--dwc-color-warning-dark: var(--dwc-color-warning-35);
--dwc-color-on-warning-text-dark: var(--dwc-color-warning-text-35);
--dwc-color-warning-text-dark: var(--dwc-color-warning-15);

--dwc-color-warning: var(--dwc-color-warning-40);
--dwc-color-on-warning-text: var(--dwc-color-warning-text-40);
--dwc-color-warning-text: var(--dwc-color-warning-20);

--dwc-color-warning-light: var(--dwc-color-warning-45);
--dwc-color-on-warning-text-light: var(--dwc-color-warning-text-45);
--dwc-color-warning-text-light: var(--dwc-color-warning-25);

--dwc-color-warning-alt: var(--dwc-color-warning-95);
--dwc-color-on-warning-text-alt: var(--dwc-color-warning-text-95);

--dwc-border-color-warning: var(--dwc-color-warning);
--dwc-focus-ring-warning: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-warning-h),
    var(--dwc-color-warning-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-warning-a)
  );
```

</TabItem>

<TabItem value="Danger">

```css
--dwc-color-danger-dark: var(--dwc-color-danger-35);
--dwc-color-on-danger-text-dark: var(--dwc-color-danger-text-35);
--dwc-color-danger-text-dark: var(--dwc-color-danger-30);

--dwc-color-danger: var(--dwc-color-danger-40);
--dwc-color-on-danger-text: var(--dwc-color-danger-text-40);
--dwc-color-danger-text: var(--dwc-color-danger-35);

--dwc-color-danger-light: var(--dwc-color-danger-45);
--dwc-color-on-danger-text-light: var(--dwc-color-danger-text-45);
--dwc-color-danger-text-light: var(--dwc-color-danger-40);

--dwc-color-danger-alt: var(--dwc-color-danger-95);
--dwc-color-on-danger-text-alt: var(--dwc-color-danger-text-95);

--dwc-border-color-danger: var(--dwc-color-danger);
--dwc-focus-ring-danger: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-danger-h),
    var(--dwc-color-danger-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-danger-a)
  );
```

</TabItem>

<TabItem value="Info">

```css
--dwc-color-info-dark: var(--dwc-color-info-35);
--dwc-color-on-info-text-dark: var(--dwc-color-info-text-35);
--dwc-color-info-text-dark: var(--dwc-color-info-35);

--dwc-color-info: var(--dwc-color-info-40);
--dwc-color-on-info-text: var(--dwc-color-info-text-40);
--dwc-color-info-text: var(--dwc-color-info-40);

--dwc-color-info-light: var(--dwc-color-info-45);
--dwc-color-on-info-text-light: var(--dwc-color-info-text-45);
--dwc-color-info-text-light: var(--dwc-color-info-45);

--dwc-color-info-alt: var(--dwc-color-info-95);
--dwc-color-on-info-text-alt: var(--dwc-color-info-text-95);

--dwc-border-color-info: var(--dwc-color-info);
--dwc-focus-ring-info: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-info-h),
    var(--dwc-color-info-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-info-a)
  );
```

</TabItem>

<TabItem value="Gray">

```css
--dwc-color-gray-dark: var(--dwc-color-gray-10);
--dwc-color-on-gray-text-dark: var(--dwc-color-gray-text-10);
--dwc-color-gray-text-dark: var(--dwc-color-gray-15);

--dwc-color-gray: var(--dwc-color-gray-15);
--dwc-color-on-gray-text: var(--dwc-color-gray-text-15);
--dwc-color-gray-text: var(--dwc-color-gray-20);

--dwc-color-gray-light: var(--dwc-color-gray-20);
--dwc-color-on-gray-text-light: var(--dwc-color-gray-text-20);
--dwc-color-gray-text-light: var(--dwc-color-gray-25);

--dwc-color-gray-alt: var(--dwc-color-gray-95);
--dwc-color-on-gray-text-alt: var(--dwc-color-gray-text-95);

--dwc-border-color-gray: var(--dwc-color-gray);
--dwc-focus-ring-gray: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-gray-h),
    var(--dwc-color-gray-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-gray-a)
  );
```
</TabItem>

</Tabs>
