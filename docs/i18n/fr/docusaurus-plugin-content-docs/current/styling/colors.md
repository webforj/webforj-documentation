---
sidebar_position: 3
title: Colors
_i18n_hash: cc233e97e4b7333262eb47b14bfe572a
---
webforJ fournit un système de couleurs basé sur les propriétés CSS personnalisées. Ces variables de couleur maintiennent un style visuel cohérent à travers votre application tout en vous donnant un contrôle total pour personnaliser les palettes selon vos besoins en design.

Vous pouvez référencer n'importe quelle couleur en utilisant la syntaxe `--dwc-color-{palette}-{step}`, où `{palette}` est le nom du groupe de couleurs (par exemple, `primary`, `danger`, ..) et `{step}` est un nombre allant de `5` à `95` par paliers de `5`, représentant la clarté de la couleur.

```css
.element {
  background-color: var(--dwc-color-primary-50);
  color: var(--dwc-color-on-primary-text-50);
}
```

:::tip Échelle des valeurs de teinte
Les valeurs des étapes vont de `5` (le plus sombre) à `95` (le plus clair), augmentant par paliers de `5`. L'étape 5 est toujours la plus sombre et l'étape 95 est toujours la plus claire, qu'il s'agisse du mode clair ou sombre.
:::

## Palettes de couleurs {#color-palettes}

DWC configure sept palettes plus la palette `noir/blanc` où chaque palette est un ensemble de variations (nuances et teintes) d'une couleur sémantique.

### Palettes disponibles {#available-palettes}

- **default** : Une palette neutre teintée de la couleur primaire, utilisée pour la plupart des arrière-plans de composants, bordures et éléments d'interface utilisateur neutres.
- **primary** : Représente généralement la couleur primaire de votre marque.
- **success**, **warning**, **danger** : Palettes sémantiques utilisées pour des indicateurs de statut appropriés.
- **info** : Une palette complémentaire pour une emphase secondaire.
- **gray** : Une palette de niveaux de gris pur, non teintée.
- **black/white** : Couleurs dynamiques conscientes du mode qui s'adaptent au thème actuel. Le presque noir en mode clair devient presque blanc en mode sombre, et vice versa.

<dwc-doc-palettes></dwc-doc-palettes>

### Semences de palette {#palette-seeds}

Chaque palette est générée à partir de deux variables de semence : `hue` et `saturation`. En définissant ces deux valeurs, toutes les 19 étapes sont générées automatiquement.

| Variable de semence | Description |
|---|---|
| `--dwc-color-{name}-h` | L'angle de teinte de la couleur de semence (0-360). |
| `--dwc-color-{name}-s` | Le pourcentage de saturation. `100%` est complètement saturé, `0%` est complètement désaturé (gris). |

Vous pouvez ajuster une palette en redéfinissant ces variables dans vos styles racine. Par exemple, pour modifier la palette primaire :

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
}
```

<Tabs>

<TabItem value="Primary">

| Variable | Valeur par défaut |
|---|---|
| `--dwc-color-primary-h` | 223 |
| `--dwc-color-primary-s` | 91% |

</TabItem>

<TabItem value="Success">

| Variable | Valeur par défaut |
|---|---|
| `--dwc-color-success-h` | 153 |
| `--dwc-color-success-s` | 60% |

</TabItem>

<TabItem value="Warning">

| Variable | Valeur par défaut |
|---|---|
| `--dwc-color-warning-h` | 35 |
| `--dwc-color-warning-s` | 90% |

</TabItem>

<TabItem value="Danger">

| Variable | Valeur par défaut |
|---|---|
| `--dwc-color-danger-h` | 4 |
| `--dwc-color-danger-s` | 90% |

</TabItem>

<TabItem value="Info">

| Variable | Valeur par défaut |
|---|---|
| `--dwc-color-info-h` | 262 |
| `--dwc-color-info-s` | 65% |

</TabItem>

<TabItem value="Gray">

| Variable | Valeur par défaut |
|---|---|
| `--dwc-color-gray-h` | 0 |
| `--dwc-color-gray-s` | 0% |

</TabItem>

<TabItem value="Default / Tone">

| Variable | Valeur par défaut |
|---|---|
| `--dwc-color-default-h` | var(--dwc-color-primary-h) |
| `--dwc-color-default-s` | 3% |

</TabItem>

</Tabs>

### Surcharge directe de semence {#direct-seed-override}

Chaque palette expose également une variable `--dwc-color-{name}-seed`. Par défaut, celle-ci est construite à partir des valeurs de teinte et de saturation, mais vous pouvez la remplacer directement par n'importe quelle couleur CSS valide pour contourner entièrement le système de teinte/saturation.

```css
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

### Rotation des teintes {#hue-rotation}

Le générateur de palettes applique une légère rotation des teintes à travers les étapes pour créer des palettes d'apparence plus naturelle. Les nuances plus sombres se déplacent légèrement vers le chaud tandis que les nuances plus claires se déplacent légèrement vers le frais. Cela imite le comportement des pigments réels et empêche les palettes de sembler plates ou synthétiques.

| Variable | Valeur par défaut | Description |
|---|---|---|
| `--dwc-color-hue-rotate` | 3 | Degrés de décalage de teinte à travers la palette. Réglez sur 0 pour désactiver. |

<dwc-doc-hue-rotate name="primary"></dwc-doc-hue-rotate>

### Variables générées par étape {#generated-variables-per-step}

Chaque palette génère 19 étapes (de 5 à 95). Pour chaque étape, les variables suivantes sont produites :

| Modèle de variable | Description |
|---|---|
| `--dwc-color-{name}-{step}` | La nuance de la palette à cette étape. |
| `--dwc-color-{name}-text-{step}` | Une couleur de texte sûre pour la surface dérivée de cette étape (conforme à WCAG AA). |
| `--dwc-color-on-{name}-text-{step}` | Couleur de texte à utiliser SUR la nuance comme fond (inverse automatiquement clair/foncé). |

:::tip Accessibilité
Toutes les couleurs de texte générées respectent automatiquement les exigences de contraste WCAG AA. Vous n'avez pas besoin de calculer les rapports de contraste vous-même.
:::

La première ligne montre la nuance avec sa couleur `on-text` (pour le texte placé directement sur cette nuance). La ligne du bas montre la couleur `text` sur un fond de surface :

<dwc-doc-step-vars name="primary"></dwc-doc-step-vars>

### Variables supplémentaires générées {#additional-generated-variables}

| Modèle de variable | Description |
|---|---|
| `--dwc-color-{name}-tint` | La couleur de semence à 12% d'opacité, pour des fonds de surbrillance subtils. |
| `--dwc-border-color-{name}` | Couleur de bordure consciente du mode teintée avec la teinte de la palette. |
| `--dwc-border-color-{name}-emphasis` | Couleur de bordure plus forte consciente du mode pour les états hover, focus et actifs. |
| `--dwc-focus-ring-{name}` | Ombre d'anneau de focus pour la palette. |

## Couleurs globales {#global-colors}

Ce sont des couleurs conscientes du mode qui s'adaptent automatiquement aux thèmes clairs et sombres.

| Variable | Description |
|---|---|
| `--dwc-color-black` | Presque noir en mode clair, presque blanc en mode sombre. |
| `--dwc-color-white` | Presque blanc en mode clair, presque noir en mode sombre. |
| `--dwc-color-body-text` | Couleur du texte par défaut du corps (utilise `--dwc-color-black`). |

## Thèmes de composants {#theming-components-with-abstract-variables}

DWC abstrait l'utilisation des palettes disponibles avec un ensemble de variables de variation sémantique de niveau supérieur. Les composants utilisent ces variations plutôt que les numéros d'étape bruts, car les variations s'adaptent automatiquement aux modes clairs et sombres.

Les variations sont divisées en trois groupes : `normal`, `dark` et `light`.

1. Les variables `normal` sont la couleur de base, utilisées pour les arrière-plans et les éléments d'interface utilisateur principaux.
2. Les variables `dark` sont principalement utilisées pour les états `actif/appuyé`.
3. Les variables `light` sont principalement utilisées pour les états `hover/focus`.

<Tabs>

<TabItem value="Primary">

<dwc-doc-variations name="primary"></dwc-doc-variations>

```css
--dwc-color-primary-dark: var(--dwc-color-primary-45)
--dwc-color-primary: var(--dwc-color-primary-50)
--dwc-color-primary-light: var(--dwc-color-primary-55)
--dwc-color-primary-alt: var(--dwc-color-primary-tint)

--dwc-color-primary-text-dark: var(--dwc-color-primary-text-40)
--dwc-color-primary-text: var(--dwc-color-primary-text-45)
--dwc-color-primary-text-light: var(--dwc-color-primary-text-50)

--dwc-color-on-primary-text-dark: var(--dwc-color-on-primary-text-45)
--dwc-color-on-primary-text: var(--dwc-color-on-primary-text-50)
--dwc-color-on-primary-text-light: var(--dwc-color-on-primary-text-55)
--dwc-color-on-primary-text-alt: var(--dwc-color-primary-text)
```

</TabItem>

<TabItem value="Success">

<dwc-doc-variations name="success"></dwc-doc-variations>

```css
--dwc-color-success-dark: var(--dwc-color-success-45)
--dwc-color-success: var(--dwc-color-success-50)
--dwc-color-success-light: var(--dwc-color-success-55)
--dwc-color-success-alt: var(--dwc-color-success-tint)

--dwc-color-success-text-dark: var(--dwc-color-success-text-40)
--dwc-color-success-text: var(--dwc-color-success-text-45)
--dwc-color-success-text-light: var(--dwc-color-success-text-50)

--dwc-color-on-success-text-dark: var(--dwc-color-on-success-text-45)
--dwc-color-on-success-text: var(--dwc-color-on-success-text-50)
--dwc-color-on-success-text-light: var(--dwc-color-on-success-text-55)
--dwc-color-on-success-text-alt: var(--dwc-color-success-text)
```

</TabItem>

<TabItem value="Warning">

<dwc-doc-variations name="warning"></dwc-doc-variations>

```css
--dwc-color-warning-dark: var(--dwc-color-warning-45)
--dwc-color-warning: var(--dwc-color-warning-50)
--dwc-color-warning-light: var(--dwc-color-warning-55)
--dwc-color-warning-alt: var(--dwc-color-warning-tint)

--dwc-color-warning-text-dark: var(--dwc-color-warning-text-40)
--dwc-color-warning-text: var(--dwc-color-warning-text-45)
--dwc-color-warning-text-light: var(--dwc-color-warning-text-50)

--dwc-color-on-warning-text-dark: var(--dwc-color-on-warning-text-45)
--dwc-color-on-warning-text: var(--dwc-color-on-warning-text-50)
--dwc-color-on-warning-text-light: var(--dwc-color-on-warning-text-55)
--dwc-color-on-warning-text-alt: var(--dwc-color-warning-text)
```

</TabItem>

<TabItem value="Danger">

<dwc-doc-variations name="danger"></dwc-doc-variations>

```css
--dwc-color-danger-dark: var(--dwc-color-danger-45)
--dwc-color-danger: var(--dwc-color-danger-50)
--dwc-color-danger-light: var(--dwc-color-danger-55)
--dwc-color-danger-alt: var(--dwc-color-danger-tint)

--dwc-color-danger-text-dark: var(--dwc-color-danger-text-40)
--dwc-color-danger-text: var(--dwc-color-danger-text-45)
--dwc-color-danger-text-light: var(--dwc-color-danger-text-50)

--dwc-color-on-danger-text-dark: var(--dwc-color-on-danger-text-45)
--dwc-color-on-danger-text: var(--dwc-color-on-danger-text-50)
--dwc-color-on-danger-text-light: var(--dwc-color-on-danger-text-55)
--dwc-color-on-danger-text-alt: var(--dwc-color-danger-text)
```

</TabItem>

<TabItem value="Info">

<dwc-doc-variations name="info"></dwc-doc-variations>

```css
--dwc-color-info-dark: var(--dwc-color-info-45)
--dwc-color-info: var(--dwc-color-info-50)
--dwc-color-info-light: var(--dwc-color-info-55)
--dwc-color-info-alt: var(--dwc-color-info-tint)

--dwc-color-info-text-dark: var(--dwc-color-info-text-40)
--dwc-color-info-text: var(--dwc-color-info-text-45)
--dwc-color-info-text-light: var(--dwc-color-info-text-50)

--dwc-color-on-info-text-dark: var(--dwc-color-on-info-text-45)
--dwc-color-on-info-text: var(--dwc-color-on-info-text-50)
--dwc-color-on-info-text-light: var(--dwc-color-on-info-text-55)
--dwc-color-on-info-text-alt: var(--dwc-color-info-text)
```

</TabItem>

<TabItem value="Default / Tone">

<dwc-doc-variations name="default"></dwc-doc-variations>

La variation par défaut est utilisée pour des éléments d'interface utilisateur neutres tels que les arrière-plans et les bordures des composants. Elle hérite de sa teinte de la palette primaire avec une saturation très faible. Contrairement aux palettes chromatiques, la valeur par défaut utilise ses propres calculs de clarté OKLCH au lieu des étapes de palette.

```css
--dwc-color-default-dark
--dwc-color-default
--dwc-color-default-light
--dwc-color-default-alt: var(--dwc-color-primary-alt)

--dwc-color-default-text-dark: var(--dwc-color-default-text-40)
--dwc-color-default-text: var(--dwc-color-default-text-45)
--dwc-color-default-text-light: var(--dwc-color-default-text-50)

--dwc-color-on-default-text-dark
--dwc-color-on-default-text
--dwc-color-on-default-text-light
--dwc-color-on-default-text-alt: var(--dwc-color-primary-text)

--dwc-focus-ring-default: var(--dwc-focus-ring-primary)
```

</TabItem>

<TabItem value="Gray">

<dwc-doc-variations name="gray"></dwc-doc-variations>

La variation grise utilise des nuances de gris pur et est consciente du mode, choisissant parmi les étapes foncées en mode clair et les étapes claires en mode sombre.

```css
--dwc-color-gray-dark
--dwc-color-gray
--dwc-color-gray-light
--dwc-color-gray-alt: var(--dwc-color-gray-tint)

--dwc-color-gray-text-dark: var(--dwc-color-gray-text-40)
--dwc-color-gray-text: var(--dwc-color-gray-text-45)
--dwc-color-gray-text-light: var(--dwc-color-gray-text-50)

--dwc-color-on-gray-text-dark
--dwc-color-on-gray-text
--dwc-color-on-gray-text-light
--dwc-color-on-gray-text-alt: var(--dwc-color-gray-text)
```

</TabItem>

</Tabs>

### Référence des variations {#variation-reference}

| Variable | Description |
|---|---|
| `--dwc-color-{name}` | La couleur de base. Utilisée pour les arrière-plans, remplissages et bordures. |
| `--dwc-color-{name}-dark` | Une version plus sombre. Utilisée pour les états actifs/appuyés. |
| `--dwc-color-{name}-light` | Une version plus claire. Utilisée pour les états hover/focus. |
| `--dwc-color-{name}-alt` | La couleur de semence à 12% d'opacité. Utilisée pour des états de surbrillance subtils. |
| `--dwc-color-{name}-text` | Couleur de texte sûre sur les surfaces de l'application (WCAG AA). |
| `--dwc-color-{name}-text-dark` | Variation de texte plus sombre. |
| `--dwc-color-{name}-text-light` | Variation de texte plus claire. |
| `--dwc-color-on-{name}-text` | Couleur de texte à utiliser SUR `--dwc-color-{name}` comme fond. |
| `--dwc-color-on-{name}-text-dark` | Couleur de texte à utiliser SUR `--dwc-color-{name}-dark`. |
| `--dwc-color-on-{name}-text-light` | Couleur de texte à utiliser SUR `--dwc-color-{name}-light`. |
| `--dwc-color-on-{name}-text-alt` | Couleur de texte à utiliser SUR `--dwc-color-{name}-alt`. |
| `--dwc-border-color-{name}` | Couleur de bordure consciente du mode. |
| `--dwc-border-color-{name}-emphasis` | Couleur de bordure plus forte consciente du mode. |
| `--dwc-focus-ring-{name}` | Ombre de l'anneau de focus. |
