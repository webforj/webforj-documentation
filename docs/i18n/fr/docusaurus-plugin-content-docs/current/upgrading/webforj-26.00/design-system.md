---
title: Upgrading to the v26 Design System
description: >-
  Reference for the design-system updates in DWC 26 - color engine, dark mode,
  surfaces, shadows, typography, radius, focus ring, and interaction feedback.
sidebar_position: 2
_i18n_hash: 8a36bc047ecfc90874412da4d39643fb
---
DWC 26 introduit un système de design rafraîchi. La mise à jour est incrémentale plutôt qu'une réécriture complète : la plupart des variables CSS de la v25 restent disponibles, l'API publique du moteur de thème est préservée et les personnalisations existantes continuent de fonctionner sans modifications.

Ce guide documente ce qui a changé, où la sortie visuelle diffère, et les étapes de mise à niveau requises lorsque votre application dépend d'un comportement spécifique à la v25.

## Verdict rapide {#quick-verdict}

| Scénario | À quoi s'attendre |
| --- | --- |
| Utilise le style par défaut | Rafraîchissement visuel. Les teintes de la palette par défaut ont été réajustées (par exemple, le primaire est passé de `h: 211 / s: 100%` à `h: 223 / s: 91%`), les ombres semblent plus superposées et les composants ont un aspect plus arrondi. Aucun changement de code nécessaire, mais les couleurs par défaut de la marque évoluent. |
| Remplace `--dwc-color-{name}-h` et `-s` | Fonctionne toujours. Le chemin de semence HSL est préservé. |
| Remplace des étapes de palette individuelles (par exemple `--dwc-color-primary-40`) | Les chiffres peuvent correspondre à des couleurs différentes. Voir [Palette de couleurs](#color-palette-step-5-is-always-darkest). |
| Dépend de `--dwc-color-{name}-c` | Supprimez. La bascule texte clair/sombre est désormais calculée automatiquement par nuance. |
| Fait référence à des jetons de taille de police nommés (`--dwc-font-size-m`, `-l`, etc.) | L'échelle a descendu d'un palier. `m` est maintenant 14px au lieu de 16px. Voir [Typographie](#typography). |
| Utilise `--dwc-font-weight-semibold` pour obtenir un poids de 500 | `semibold` est maintenant 600. Passez au nouveau `--dwc-font-weight-medium` pour 500. |
| Réserve de l'espace autour des éléments focusables avec `--dwc-focus-ring-width` | L'anneau a désormais un écart. Ajoutez `--dwc-focus-ring-gap` à cet espacement, sinon l'anneau débordera. Voir [Anneau de focus](#focus-ring). |
| Effets de survol / d'ondulation de bouton personnalisés | Les ondulations ont disparu. Le retour tactile est maintenant une petite réduction d'échelle. |

Si aucune de celles-ci ne s'applique, vous pouvez arrêter de lire ici. Votre mise à niveau est terminée.

## Quoi de neuf en un coup d'œil {#whats-new-at-a-glance}

- **Moteur de couleur moderne.** Les palettes sont générées en OKLCH au lieu de HSL. Les étapes de luminosité sont perceptuellement uniformes (les étapes adjacentes ressemblent à des étapes adjacentes), et le mode sombre ne retourne plus la palette.
- **Mode sombre via une variable.** `--dwc-dark-mode: 1` inverse toute l'interface utilisateur. L'adaptation de mode se fait dans la couche de variation, pas en remappant chaque étape.
- **Couleurs `on-text` automatiques.** Chaque étape de la palette obtient un compagnon `--dwc-color-on-{name}-text-{step}` clampé pour un contraste sûr selon les normes WCAG AA sur cette nuance. Vous n'avez pas à calculer le contraste manuellement.
- **Remplacement direct de la semence.** Passez n'importe quelle couleur CSS (hex, `rgb()`, `oklch()`, `lab()`, etc.) dans `--dwc-color-{name}-seed` et toute la palette se régénère à partir de celle-ci.
- **Ombres réajustées.** Les mêmes six niveaux (`xs` à `2xl`), maintenant avec un déclin d'eau réaliste et un renforcement automatique de la force en mode sombre via `--dwc-shadow-strength`.
- **Surfaces et `default` utilisent leur propre courbe de luminosité.** Les deux s'adaptent désormais à la lumière/sombre via `--dwc-dark-mode` et une petite teinte primaire, au lieu de redéfinir les surfaces dans le thème sombre et d'aligner le `default` sur des étapes de palette.
- **Retour tactile d'échelle.** Les ondulations sont remplacées par une petite réduction d'échelle lors de l'appui. Jetons : `--dwc-scale-press`, `--dwc-scale-press-deep`.
- **Anneau de focus avec écart.** L'anneau a désormais un petit espace coloré de surface (`--dwc-focus-ring-gap`) avant l'ombre colorée, afin de rester visible sur des boutons solides et des mises en page serrées.
- **Le rayon de bord est semé.** Changez `--dwc-border-radius-seed` et les étapes `s` à `4xl` se redimensionnent proportionnellement (`2xs` et `xs` restent à des valeurs en pixels fixes). Nouvelles étapes `3xl` et `4xl`.

## Le système de couleur {#the-color-system}

C'est le plus grand changement en interne. Le comportement que vous voyez devrait être familier, les internes sont différents.

### Deux façons de définir une couleur {#two-ways-to-define-a-color}

Vous pouvez continuer à utiliser la teinte + saturation comme avant, ou remplacer directement la semence par n'importe quelle couleur CSS.

```css
/* Teinte + saturation (toujours le chemin par défaut) */
:root {
  --dwc-color-primary-h: 223;
  --dwc-color-primary-s: 91%;
}

/* Semence directe - n'importe quel format de couleur CSS */
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

Si vous utilisiez déjà `-h` et `-s`, vous n'avez rien à faire. Le remplacement de semence est le nouveau chemin pour les couleurs de marque directes.

### Palette de couleurs : l'étape 5 est toujours la plus sombre {#color-palette-step-5-is-always-darkest}

Dans la v25, la palette était inversée en mode sombre (étape 5 la plus sombre en clair, la plus claire en sombre). Dans la v26, l'étape 5 est toujours la marron la plus sombre et l'étape 95 est toujours la plus claire, indépendamment du mode. L'adaptation de mode se fait désormais une couche plus haut, dans les jetons de variation :

```css
/* v26 - variations pointent vers des étapes fixes */
--dwc-color-primary-dark:  var(--dwc-color-primary-45);
--dwc-color-primary:       var(--dwc-color-primary-50);
--dwc-color-primary-light: var(--dwc-color-primary-55);
```

| Scénario | Qu'est-ce qui change |
| --- | --- |
| Utilisez `--dwc-color-primary` (ou `-dark`, `-light`, `-text`) | Rien. Les variations se comportent toujours de la même manière selon les modes. |
| Codé en dur une étape comme `--dwc-color-primary-40` | Cette étape correspond désormais à la même luminosité OKLCH dans les deux modes. La couleur que vous avez vue en mode sombre provenait d'une étape différente. Passez au jeton de variation si vous souhaitez un comportement conscient du mode. |
| Écrit `hsl(var(--dwc-color-primary-h), ...)` directement | Cela fonctionne toujours. La semence HSL est toujours construite à partir de h + s. |

### Les couleurs sont dérivées, pas promises {#colors-are-derived-not-promised}

:::info Attention
La teinte que vous définissez est une **semence**, pas une cible. La couleur que vous passez via `--dwc-color-{name}-h` / `-s` (ou `-seed`) ne apparaîtra pas nécessairement à l'étape 50.
:::

Parce que la palette utilise une luminosité OKLCH absolue par étape, où votre semence atterrit dépend de sa luminosité naturelle. Les teintes vives (cyan, jaune) ont une haute luminosité OKLCH et se retrouvent autour de l'étape 80-85. Les teintes plus sombres (bleu) se situent près de l'étape 50 par coïncidence.

Si vous avez besoin d'une couleur exacte à une étape exacte, définissez l'étape explicitement :

```css
:root {
  --dwc-color-primary-50: #1d4ed8;
}
```

### `--dwc-color-{name}-c` est supprimé {#dwc-color-name-c-is-gone}

Dans la v25, `-c` était le seuil de contraste : la luminosité de fond à laquelle la couleur de texte compagnon passait du blanc au noir. Une valeur de 50 signifiait que le texte était blanc sur des fonds plus sombres que 50 % et noir sur des fonds plus clairs que 50 %.

Dans la v26, vous ne choisissez plus de point de retournement. Chaque étape obtient une couleur `on-text` teintée calculée automatiquement à partir de la nuance elle-même. Le résultat est toujours conforme à la norme WCAG AA et conserve une touche de la teinte de la palette au lieu de tomber dans le noir ou le blanc pur.

Si vous avez des remplacements `--dwc-color-{name}-c`, vous pouvez les supprimer, ils n'ont aucun effet.

### Couleurs de texte et `on-text` {#text-and-on-text-colors}

La v25 avait un jeton de texte par étape, `--dwc-color-{name}-text-{step}`, qui était une couleur noire ou blanche pure calculée à partir du seuil `-c` et destinée au texte **sur** cette étape en tant que fond.

La v26 conserve le même nom de jeton mais en change sa signification, et ajoute un second jeton par étape :

| Jeton | Signification v25 | Signification v26 |
| --- | --- | --- |
| `--dwc-color-{name}-text-{step}` | Noir/blanc pur, destiné au texte sur l'ombre en tant que fond | Texte teinté, **sûr de surface**, lisible sur des fonds de page neutres |
| `--dwc-color-on-{name}-text-{step}` | (n'existait pas en tant que jeton par étape) | Texte teinté à utiliser **sur** cette étape en tant que fond |

Les deux jetons de la v26 sont clampés pour un contraste WCAG AA sûr sur leur fond prévu. Si vous avez utilisé `--dwc-color-{name}-text-{step}` comme premier plan sur un fond coloré, passez à `--dwc-color-on-{name}-text-{step}` (le nouveau jeton `on-text`) pour préserver cette sémantique.

### Teintes et bordures {#tints-and-borders}

Le générateur émet désormais trois jetons par palette, un véritablement nouveau, une variante nouvelle et une dont la source a changé :

| Jeton | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-tint` | (n'existait pas) | Semence à 12 % d'opacité, pour des fonds alternatifs |
| `--dwc-border-color-{name}-emphasis` | (n'existait pas) | Bordure plus forte consciente du mode pour survol/focus/actif |
| `--dwc-border-color-{name}` | Défini par variation en tant que `var(--dwc-color-{name})` (la nuance saturée) | Calculé dans le générateur : ton assourdi conscient du mode de la semence |

Si votre CSS lisait `--dwc-border-color-primary` s'attendant à la couleur primaire saturée, la visuelle est désormais une tonalité de séparation subtile à la place. Si vous voulez spécifiquement le look saturé, passez directement à `--dwc-color-primary`.

## Mode sombre {#dark-mode}

Le mode sombre est contrôlé par une seule variable, `--dwc-dark-mode`. Réglez-le à `1` pour le sombre, `0` pour le clair :

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

Il participe aux expressions `calc()` dans tout le système, ce qui est la façon dont l'adaptation de mode se propage aux surfaces, ombres, bordures et couleurs de texte.

Dans la v25, les thèmes intégrés `dark` et `dark-pure` devaient redéfinir manuellement les surfaces, les ombres et de nombreuses variations de palette. Dans la v26, tout cela est dérivé de `--dwc-dark-mode` et les couleurs de semence. Un thème sombre personnalisé typique qui avait besoin d'un bloc de remplacement de 20 lignes devient :

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  color-scheme: dark;
}
```

Si vous avez un thème sombre personnalisé copié à partir de la structure v25, vous pouvez généralement supprimer la plupart du bloc intérieur et garder uniquement la semence et le drapeau de mode sombre.

## Surfaces et `default` {#surfaces-and-default}

Dans la v25, les surfaces étaient définies deux fois, une fois dans `:root` pour le mode clair (`hsl(default-h, default-s, 96%)`, etc.) et à nouveau dans le thème sombre (`hsl(default-h, default-s, 8%)`, etc.). La variation `default` pointait vers des étapes de palette et nécessitait également des remplacements spécifiques au thème sombre.

Dans la v26, les deux sont calculés une fois avec un terme `--dwc-dark-mode` intégré dans le calcul de luminosité, ce qui garantit :

- Les surfaces se situent toujours légèrement en dessous de `default`, de sorte que les cartes flottent visuellement au-dessus de la page.
- Une petite teinte primaire est appliquée via la chroma de la semence dans les deux modes.
- Le thème `dark-pure` fixe `--dwc-color-default-s: 0%`, ce qui réduit automatiquement la teinte à zéro.

Si votre application remplace `--dwc-surface-1` (ou toute autre surface) par une couleur fixe, cela fonctionne toujours ; vous optez simplement pour l'adaptation automatique au mode.

La source du jeton `--dwc-color-{name}-alt` a également changé :

| Jeton | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-alt` | Étape de palette 95 (fond presque blanc) | Semence à 12 % d'opacité (teinte translucide) |

Si vous utilisiez `-alt` comme fond solide presque blanc, cela se lira désormais comme un superposition teintée translucide. Soit choisissez une étape spécifique (`--dwc-color-{name}-95`), soit concevez autour du sens translucide.

## Ombres {#shadows}

La échelle de six niveaux (`xs`, `s`, `m`, `l`, `xl`, `2xl`) est inchangée en nom et en nombre, mais les décalages de couche ont été reconstruits pour un déclin réaliste et les ombres en mode sombre sont désormais automatiquement 5x plus fortes via `--dwc-shadow-strength` parce que les surfaces sombres ont besoin de plus de contraste pour transmettre la profondeur.

Si vous utilisez uniquement `var(--dwc-shadow)`, vous obtenez l'ombre moyenne réajustée et rien d'autre ne change. La variable `--dwc-shadow-color` est toujours émise, mais son format de valeur a changé :

| | v25 | v26 |
| --- | --- | --- |
| `--dwc-shadow-color` | Triplet HSL (`h, s%, l%`) | Couleur OKLCH complète |

Si votre CSS utilise l'ancienne forme de triplet comme `hsla(var(--dwc-shadow-color), 0.07)`, passez à un token d'ombre complet (`var(--dwc-shadow-m)`) ou réécrivez avec `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

## Typographie {#typography}

Les jetons de taille de composant (`--dwc-size-*`) sont inchangés. L'échelle de police a été réajustée pour ancrer la taille `m` à la même taille de corps légère utilisée par d'autres jetons DWC, donc les noms des catégories ont baissé d'un palier :

| Jeton | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-size-3xs` | 10px | 10px |
| `--dwc-font-size-2xs` | 12px | 11px |
| `--dwc-font-size-xs` | 13px | 12px |
| `--dwc-font-size-s` | 14px | 13px |
| `--dwc-font-size-m` | 16px | 14px |
| `--dwc-font-size-l` | 18px | 16px |
| `--dwc-font-size-xl` | 22px | 20px |
| `--dwc-font-size-2xl` | 28px | 26px |
| `--dwc-font-size-3xl` | 36px | 34px |

La valeur par défaut de `--dwc-font-size` reste **14px**, elle y parvient simplement via `--dwc-font-size-m` (v26) au lieu de `--dwc-font-size-s` (v25).

Si votre CSS fait référence aux jetons de taille de police par nom (par exemple `font-size: var(--dwc-font-size-l)`), le résultat visible sera plus petit en v26. Passez d'un palier pour préserver la taille de la v25.

Les poids de police ont gagné trois jetons (`thin`, `medium`, `black`) et un jeton existant a changé :

| Jeton | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-weight-semibold` | 500 | 600 |
| `--dwc-font-weight-medium` | (n'existait pas) | 500 |

Si vous avez utilisé `--dwc-font-weight-semibold` pour obtenir un texte de 500 poids, passez au nouveau `--dwc-font-weight-medium`.

Les catégories d'interligne ont également été ajustées dans la même direction que les tailles de police ; la valeur par défaut de `--dwc-font-line-height` reste à 1.25.

Les `--dwc-font-family-sans` et `--dwc-font-family-mono` ont été modernisés pour utiliser les piles `system-ui` et `ui-monospace`. Si vous avez ciblé une police nommée spécifique de l'ancienne pile (`Dank Mono`, `Operator Mono`, `Roboto`, etc.) et souhaitez la récupérer, définissez `--dwc-font-family` sur une pile que vous contrôlez.

## Espacement {#spacing}

L'échelle d'espacement est inchangée depuis `xs`. Seules les deux plus petites jetons ont été arrondis à des valeurs en pixels entiers :

| Jeton | v25 | v26 |
| --- | --- | --- |
| `--dwc-space-3xs` | 1.2px | 1px |
| `--dwc-space-2xs` | 2.4px | 2px |

Aucune action nécessaire dans presque n'importe quelle application.

## Rayon de bord {#border-radius}

Le rayon de bord est maintenant semé. Changez `--dwc-border-radius-seed` et chaque étape (`s`, `m`, `l`, `xl`, `2xl`, `3xl`, `4xl`) se redimensionne proportionnellement. Les étapes `2xs` et `xs` sont toujours fixées à des valeurs en pixels fixes (trop petites pour dériver significativement).

Trois choses ont changé :

| | v25 | v26 |
| --- | --- | --- |
| Unité | `em` (évolue avec la taille de police parente) | `rem` (évolue avec la taille de police racine) |
| Valeur par défaut `--dwc-border-radius` | `--dwc-border-radius-s` (4px) | `--dwc-border-radius-seed` (8px) |
| Étapes disponibles | jusqu'à `2xl` | ajoute `3xl`, `4xl` |

Les composants semblent plus arrondis par défaut. Si un composant imbriqué dans un texte plus grand devait hériter d'un rayon plus grand via `em`, cet ajustement n'a plus lieu ; les rayons sont maintenant ancrés à la racine. Si vous souhaitez revenir à la taille par défaut de la v25, réduisez la semence de moitié :

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, réduit toute l'échelle de moitié */
}
```

## Ease {#easings}

Le catalogue d'assouplissements est principalement le même, avec de nouveaux jetons de raccourci pour les cas courants : `--dwc-ease`, `--dwc-ease-in`, `--dwc-ease-out`, `--dwc-ease-outGlide`. Voir la page [Transitions et Assouplissements](/docs/styling/transitions-easing) pour la liste complète.

## Transitions {#transitions}

Les durées des transitions ont été réajustées pour une sensation plus rapide :

| Variable | v25 | v26 |
| --- | --- | --- |
| `--dwc-transition-slow` | 500&nbsp;ms | 300&nbsp;ms |
| `--dwc-transition-medium` | 250&nbsp;ms | 250&nbsp;ms |
| `--dwc-transition-fast` | 150&nbsp;ms | 150&nbsp;ms |
| `--dwc-transition-x-fast` | 50&nbsp;ms | 100&nbsp;ms |

Si vous dépendez d'une durée spécifique, remplacez-la dans `:root`.

## Anneau de focus {#focus-ring}

L'anneau de focus utilise désormais un motif à double anneau : un petit espace coloré de surface, puis l'anneau coloré. Cela permet à l'anneau d'être lisible sur des boutons solides et des mises en page denses.

| Variable | v25 | v26 |
| --- | --- | --- |
| `--dwc-focus-ring-width` | 3px | 2px |
| `--dwc-focus-ring-a` | 0.4 | 0.75 |
| `--dwc-focus-ring-gap` | (aucun) | 2px |
| `--dwc-focus-ring-l` | 45% | (supprimé, la luminosité est calculée par mode) |

Si vous réservez de l'espace autour des éléments focusables avec `padding: var(--dwc-focus-ring-width)`, ajoutez l'écart à cet espacement afin que le nouvel anneau ait de la place pour se rendre :

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

## Retour d'interaction {#interaction-feedback}

Les effets d'ondulation de style matérial ne sont plus utilisés par aucun composant DWC. Le nouveau retour pour tout élément cliquable est une petite réduction d'échelle :

```css
--dwc-scale-press: 0.97;      /* Réduction standard de 3 % */
--dwc-scale-press-deep: 0.93; /* Réduction plus profonde de 7 % pour les boutons */
```

Le mixin SCSS `ripple` et la variable CSS `--dwc-ripple-color` existent toujours dans la construction, mais rien ne les importe par défaut. Si vos propres composants ont choisi d'avoir le mixin, passez aux jetons de réduction d'échelle pour correspondre à la nouvelle sensation.

## Support des navigateurs {#browser-support}

Le nouveau système utilise deux fonctionnalités CSS dont vous pouvez voir les tableaux de compatibilité des navigateurs sur MDN :

- [Espace de couleur OKLCH](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/oklch#browser_compatibility), inclut la syntaxe de couleur relative (`oklch(from ...)`)
- [`color-mix()`](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/color-mix#browser_compatibility)

Les deux ont été intégrés dans les versions de Chrome, Edge, Firefox et Safari.

## Une liste de contrôle de mise à niveau pragmatique {#a-pragmatic-upgrade-checklist}

1. Recherchez `--dwc-color-*-c` et supprimez ces déclarations.
2. Recherchez `hsla(var(--dwc-shadow-color)` et remplacez par un token d'ombre (`var(--dwc-shadow-m)`) ou réécrivez comme `oklch(from ...)`.
3. Recherchez les références directes aux étapes de palette (`--dwc-color-{name}-{number}`). Si l'une d'entre elles alimente un style spécifique au mode sombre, passez aux jetons de variation (`--dwc-color-{name}`, `-dark`, `-light`).
4. Recherchez les références aux tailles de police nommées (`--dwc-font-size-m`, `-l`, etc.). Si vous voulez la taille de la v25, montrez d'un palier.
5. Recherchez `--dwc-font-weight-semibold`. Si vous vouliez 500, passez à `--dwc-font-weight-medium`.
6. Si vous réservez de l'espace autour des éléments focusables avec `--dwc-focus-ring-width`, ajoutez `--dwc-focus-ring-gap` à l'espacement.
7. Ouvrez l'application, cliquez autour. La plupart des applications n'ont besoin de rien d'autre.
