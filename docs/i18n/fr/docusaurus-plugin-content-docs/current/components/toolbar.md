---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 99def78151a30c5c7fef7106b2efcb5b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Les barres d'outils offrent aux utilisateurs un accès rapide aux actions principales et aux éléments de navigation. Le composant webforJ `Toolbar` est un conteneur horizontal qui peut contenir un ensemble de boutons d'action, d'icônes ou d'autres composants. Il est bien adapté pour gérer les contrôles de page et héberger des fonctions clés comme une barre de recherche ou un bouton de notification.

<!-- INTRO_END -->

## Organisation du contenu de la barre d'outils {#organizing-toolbar-content}

La `Toolbar` organise les composants essentiels dans une mise en page facilement accessible et cohérente. Par défaut, elle prend toute la largeur de son élément parent et fournit quatre zones de placement, ou _slots_, pour organiser les composants :

- **Début** : Contient généralement un <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> ou un bouton d'accueil.
- **Titre** : Utilisé pour les noms ou logos d'application.
- **Contenu** : Pour des actions à fort impact comme la recherche ou la navigation.
- **Fin** : Actions moins fréquentes, telles que le profil utilisateur ou l'aide.

Chaque slot dispose d'une méthode pour ajouter des composants : `addToStart()`, `addToTitle()`, `addToContent()`, et `addToEnd()`.

La démo suivante montre comment ajouter une `Toolbar` à un [AppLayout](./app-layout) et utiliser tous les slots pris en charge de manière efficace. Pour en savoir plus sur l'implémentation des barres d'outils dans un `AppLayout`, consultez [Barres d'outils collantes](./app-layout#sticky-toolbars) et [Mise en page de navigation mobile](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java',
  'src/main/resources/static/css/toolbar/toolbar-slots-view.css',
]}
/>

## Mode compact {#compact-mode}

Utilisez `setCompact(true)` pour réduire l'espace autour d'une `Toolbar`. Cela est utile lorsque vous devez faire entrer plus de contenu à l'écran, en particulier dans les applications avec des barres d'outils empilées ou un espace limité. La barre d'outils se comporte toujours de la même manière—seule la hauteur est réduite. Ce mode est couramment utilisé dans les en-têtes, les barres latérales ou les mises en page où l'espace est limité.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<ComponentDemo
path='/webforj/toolbarcompact'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java']}
/>

## `ProgressBar` dans les barres d'outils {#progressbar-in-toolbars}

Une `ProgressBar` sert d'indicateur visuel pour les processus en cours, tels que le chargement de données, le téléchargement de fichiers ou l'achèvement d'étapes dans un flux. Lorsqu'elle est placée à l'intérieur d'une `Toolbar`, la `ProgressBar` s'aligne proprement le long du bord inférieur, la rendant discrète tout en communiquant clairement le progrès aux utilisateurs.

Vous pouvez la combiner avec d'autres composants dans la barre d'outils comme des boutons ou des étiquettes sans perturber la mise en page.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Style {#styling}

### Thèmes {#themes}

Les composants `Toolbar` incluent <JavadocLink type="foundation" location="com/webforj/component/Theme">sept thèmes intégrés</JavadocLink> pour une personnalisation visuelle rapide :

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
