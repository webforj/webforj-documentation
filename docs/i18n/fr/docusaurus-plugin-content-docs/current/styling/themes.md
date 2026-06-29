---
sidebar_position: 2
title: Themes
sidebar_class_name: updated-content
description: >-
  Apply built-in light, dark, and dark-pure themes with @AppTheme or define
  custom themes through data-app-theme selectors.
_i18n_hash: 91e1a18f11aadea66df804dbaa4917d9
---
Un thème dans webforJ est un ensemble nommé de propriétés CSS personnalisées (jetons de conception) qui contrôlent l'apparence de chaque composant. Changer de thème recalculera instantanément les couleurs, les ombres, les surfaces et les bordures dans l'ensemble de l'application, sans reconstruction.

## Thèmes intégrés {#built-in-themes}

webforJ propose trois thèmes d'application dès sa sortie :

| Thème       | Arrière-plan     | Teinte                      |
|-------------|------------------|-----------------------------|
| `light`     | Clair (par défaut) | Teinte de couleur primaire subtile |
| `dark`      | Sombre           | Teinte de couleur primaire subtile |
| `dark-pure` | Sombre           | Aucune (gris neutres purs)  |

Toute application peut basculer entre eux à l'exécution, et des thèmes personnalisés supplémentaires peuvent être définis en plus des intégrés.

## Application d'un thème {#applying-a-theme}

Définissez le thème actif de manière déclarative avec l'annotation `@AppTheme` ou de manière programmatique avec `App.setTheme()`. Le nom du thème doit être l'un des suivants : `system`, `light`, `dark`, `dark-pure`, ou le nom d'un thème personnalisé.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // code de l'application
}

// ou de manière programmatique
App.setTheme("dark-pure");
```

Appeler à nouveau `App.setTheme()` à tout moment change l'application pour un thème différent.

## Schéma de couleurs {#color-scheme}

La déclaration CSS `color-scheme` indique au navigateur comment rendre ses surfaces intégrées telles que les barres de défilement natives, les widgets de contrôle de formulaire, les surlignages d'autocomplétion et l'arrière-plan de page par défaut avant le chargement de CSS. Les thèmes intégrés `dark` et `dark-pure` définissent déjà `color-scheme: dark` pour vous, de sorte que le chrome du navigateur se mélange automatiquement avec les surfaces sombres.

Vous n'avez besoin d'y penser que lors de la définition d'un thème sombre personnalisé. Dans ce cas, incluez `color-scheme: dark` dans le sélecteur du thème :

```css
html[data-app-theme="brand-dark"] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

Si vous l'omettez, les barres de défilement et les rectangles d'autocomplétion restent en mode clair par défaut et paraissent déplacés sur vos surfaces sombres. Les thèmes clairs n'ont pas besoin de la déclaration, les navigateurs sont par défaut en clair.

## Suivi des préférences de l'utilisateur {#following-the-users-preference}

La plupart des systèmes d'exploitation permettent aux utilisateurs de choisir une apparence claire ou sombre au niveau du système. webforJ peut respecter cette préférence et choisir le bon thème automatiquement.

Enregistrez quel thème appliquer pour chaque état d'apparence avec `@AppLightTheme` et `@AppDarkTheme` (ou `App.setLightTheme()` et `App.setDarkTheme()`), puis passez le mot-clé réservé `"system"` à `App.setTheme()` (ou `@AppTheme("system")`) pour permettre à webforJ de choisir entre eux en fonction de la préférence de l'OS de l'utilisateur.

```java
@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
class MyApp extends App {
  // code de l'application
}
```

Forme programmatique équivalente :

```java
App.setLightTheme("light");
App.setDarkTheme("dark");
App.setTheme("system");
```

`"system"` est un mot-clé réservé. webforJ le résout à l'exécution soit en thème clair, soit en thème sombre enregistré et se résout automatiquement chaque fois que la préférence de l'OS change. Une fois résolu, l'attribut `data-app-theme` effectif sur la page est `light` ou `dark`, donc tous les remplacements CSS devraient cibler ces noms plutôt que `"system"`.

:::info Paramètres d'apparence au niveau OS
L'endroit où les utilisateurs activent le paramètre d'apparence au niveau du système varie selon la plateforme :

- **Windows 10/11** : Paramètres > Personnalisation > Couleurs > Choisissez votre couleur
- **macOS** : Paramètres du système > Apparence
- **iOS** : Paramètres > Affichage et luminosité > Apparence
- **Android** : Paramètres > Affichage > Thème sombre
:::

## Remplacement des thèmes par défaut {#overriding-default-themes}

La plupart des travaux de branding se font en **remplaçant les thèmes existants** plutôt qu'en en créant de nouveaux. Réglez les couleurs de semence (ou tout autre jeton) pour les thèmes intégrés `light`, `dark` et `dark-pure`, et chaque composant adoptera automatiquement le nouveau look.

Vous pouvez remplacer le thème **light** en redéfinissant les propriétés CSS personnalisées dans le sélecteur `:root`.

:::info Pseudo-classe `:root`
La pseudo-classe CSS `:root` cible l'élément racine du document. En HTML, il représente l'élément `<html>` et a une spécificité plus élevée que le sélecteur `html` ordinaire.
:::

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

Pour remplacer les thèmes **dark** ou **dark-pure**, utilisez des sélecteurs d'attributs sur l'élément `<html>` :

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-seed: #a855f7;
}

html[data-app-theme="dark-pure"] {
  --dwc-color-primary-seed: #a855f7;
}
```

Changer de thème avec `App.setTheme("dark")` active le thème sombre remanié, aucun nouveau nom de thème n'est nécessaire.

## Création de thèmes personnalisés {#creating-custom-themes}

Créez un tout nouveau thème uniquement lorsque vous en avez besoin d'un qui coexiste avec les intégrés (par exemple, une variante à contraste élevé ou un skin spécifique à un client). Choisissez un nom unique et définissez-le sous son propre sélecteur `html[data-app-theme='THEME_NAME']` :

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

Pour rendre un thème personnalisé sombre, définissez `--dwc-dark-mode: 1` et `color-scheme: dark` :

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

Puis dans votre application :

```java
@AppTheme("new-theme")
class MyApp extends App {
  // code de l'application
}

// ou de manière programmatique
App.setTheme("new-theme");
```

## Travailler avec les jetons DWC {#working-with-dwc-tokens}

Quelques habitudes permettent de maintenir le CSS personnalisé aligné sur le système de conception et de prévenir les dérives en mode sombre ou dans les futures versions.

### Référez-vous toujours aux jetons avec `var(...)` {#always-reference-tokens-with-var}

Les littéraux de couleur codés en dur (`#3b82f6`, `rgb(59 130 246)`, `oklch(0.6 0.18 250)`) ne s'adaptent pas au mode sombre et ne suivent pas les changements de palette. Utilisez plutôt le jeton.

```css
/* éviter */
.my-panel {
  background: #ffffff;
  color: #1f2937;
  border: 1px solid #e5e7eb;
}

/* préférer */
.my-panel {
  background: var(--dwc-surface-3);
  color: var(--dwc-color-body-text);
  border: 1px solid var(--dwc-border-color);
}
```

### Préférez les jetons de variation aux numéros d'étape bruts {#prefer-variation-tokens-over-raw-step-numbers}

Les jetons de variation (`--dwc-color-primary`, `-dark`, `-light`, `-text`, `-alt`) se résolvent automatiquement à une étape différente en mode clair versus mode sombre. Les numéros d'étape bruts (`--dwc-color-primary-50`) ne le font pas.

```css
/* éviter - figé à l'étape 50 dans les deux modes */
.badge {
  background: var(--dwc-color-primary-50);
}

/* préférer - déplace l'étape en mode sombre */
.badge {
  background: var(--dwc-color-primary);
}
```

### Utilisez le suffixe qui correspond au rôle {#use-the-suffix-that-matches-the-role}

| Suffixe                         | Rôle                                                              |
|----------------------------------|-------------------------------------------------------------------|
| `--dwc-color-{name}`            | Remplissage solide à pleine force (boutons, badges, bannières)    |
| `--dwc-color-{name}-dark`       | État actif / pressé                                               |
| `--dwc-color-{name}-light`      | Arrière-plan de survol / mise au point                            |
| `--dwc-color-{name}-alt`        | Arrière-plan teinté subtil pour les appels et les lignes alt      |
| `--dwc-color-{name}-text`       | Texte coloré sur une surface neutre                               |
| `--dwc-color-on-{name}-text`    | Texte placé **sur** la teinte colorée comme arrière-plan (auto-contraste)|
| `--dwc-border-color-{name}`     | Bordures et séparateurs                                          |

### Réservez les surfaces et les bordures pour leurs rôles {#reserve-surfaces-and-borders-for-their-roles}

Les surfaces (`--dwc-surface-1` / `-2` / `-3`) construisent la hiérarchie de la page. Les bordures (`--dwc-border-color`, `--dwc-border-color-*`) dessinent des séparateurs. Réutiliser des étapes de palette pour ces rôles fonctionne visuellement mais perd l'adaptation automatique au mode que portent les jetons dédiés.

### Remplacer au niveau de la semence dans les thèmes personnalisés {#override-at-the-seed-level-in-custom-themes}

Lorsque vous construisez un thème personnalisé, définissez la semence (`--dwc-color-{name}-h`, `-s`, ou `-seed`) plutôt que de remplacer des étapes individuelles. Le générateur reconstruit l'ensemble de la palette de 19 étapes autour de la semence, maintenant la gamme tonale cohérente. Remplacer des étapes individuelles laisse le reste de la palette dériver par rapport à votre couleur de marque.

```css
/* éviter - laisse les autres étapes incohérentes */
html[data-app-theme="brand"] {
  --dwc-color-primary-50: #6366f1;
}

/* préférer - régénère toute la palette */
html[data-app-theme="brand"] {
  --dwc-color-primary-seed: #6366f1;
}
```

### Utilisez des jetons pour l'espacement, la taille, le rayon et les transitions {#use-tokens-for-spacing-sizing-radius-and-transitions}

La même règle s'applique au reste du système de design : référez-vous aux jetons, jamais aux nombres magiques.

```css
/* éviter */
.my-panel {
  padding: 16px;
  border-radius: 8px;
  transition: background-color 250ms;
}

/* préférer */
.my-panel {
  padding: var(--dwc-space-m);
  border-radius: var(--dwc-border-radius);
  transition: background-color var(--dwc-transition);
}
```

Les valeurs codées en dur contournent le redimensionnement des tailles de police en fonction des préférences utilisateur, vous enferment dans un langage de forme fixe et sautent les courbes de temps adoucies du système de design.

### Utilisez `::part(...)` pour atteindre les composants {#use-part-to-reach-into-components}

Les composants webforJ sont Shadow DOM. Leur balisage interne est caché des sélecteurs externes, donc une règle comme `.dwc-button-label { ... }` ne correspondra à rien. Pour styliser des pièces internes, ciblez les parties exposées :

```css
/* styliser l'étiquette à l'intérieur de chaque bouton principal */
dwc-button[theme="primary"]::part(label) {
  letter-spacing: 0.02em;
}
```

Voir [Shadow Parts](./shadow-parts) pour le mécanisme complet, et la section **Styling → Shadow Parts** de chaque composant pour les parties qu'il expose.

### Limitez les remplacements de jetons avec un sélecteur wrapper {#scope-token-overrides-with-a-wrapper-selector}

Les propriétés CSS personnalisées se propagent. Définir un jeton sur un élément wrapper retune tout ce qui s'y trouve sans affecter le reste de l'application.

```css
.danger-section {
  --dwc-color-primary-seed: #ef4444;
}
```

Chaque composant à l'intérieur de `.danger-section` (boutons, liens, anneaux de mise au point) utilise maintenant la teinte rouge de danger, tandis que le thème global reste inchangé.

### Testez en mode clair et sombre {#test-in-both-light-and-dark-mode}

Avant de livrer tout CSS personnalisé, passez le thème à `dark` et `dark-pure` et parcourez l'écran. La régression la plus courante est des valeurs de couleur codées en dur qui paraissaient bien dans un mode et lisibles ou hors palette dans l'autre.

### N'hésitez pas à utiliser `!important` {#dont-reach-for-important}

Cela échappe à la cascade et rend tout remplacement futur plus difficile. Si une règle ne fonctionne pas, la cause est presque toujours une discordance de spécificité avec une solution plus propre : ciblez le même sélecteur que celui utilisé par le framework ou ajoutez un qualificateur parent. Réservez `!important` pour un style de tierce partie que vous n'avez pas d'autre moyen de vaincre.

## Thèmes de composants {#component-themes}

En plus des thèmes au niveau de l'application, les composants webforJ prennent en charge un ensemble de **thèmes de composants** basés sur les palettes de couleurs par défaut : `default`, `primary`, `success`, `warning`, `danger`, `info`, et `gray`. Cela est indépendant du thème d'application actif.

Chaque composant documente ses thèmes pris en charge dans la section **Styling → Themes**.
