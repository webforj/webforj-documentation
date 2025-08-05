---
title: Toolbar
sidebar_position: 145
_i18n_hash: 171c46f92903112a08194d130d89f2c7
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Les barres d'outils offrent aux utilisateurs un accès rapide aux actions principales et aux éléments de navigation. Le composant `Toolbar` de webforJ est un conteneur horizontal qui peut contenir un ensemble de boutons d'action, d'icônes ou d'autres composants. Il est bien adapté pour gérer les contrôles de page et abriter des fonctions clés comme une barre de recherche ou un bouton de notification.

## Organiser le contenu de la barre d'outils {#organizing-toolbar-content}

La `Toolbar` organise les composants essentiels dans une mise en page facilement accessible et cohérente. Par défaut, elle occupe toute la largeur de son élément parent et fournit quatre zones de placement, ou _slots_, pour organiser les composants :

- **Début** : Contient généralement un <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> ou un bouton d'accueil.
- **Titre** : Utilisé pour les noms d'application ou les logos.
- **Contenu** : Pour des actions à forte attention comme la recherche ou la navigation.
- **Fin** : Actions moins fréquentes, telles que le profil utilisateur ou l'aide.

Chaque slot a une méthode pour ajouter des composants : `addToStart()`, `addToTitle()`, `addToContent()`, et `addToEnd()`.

La démo suivante montre comment ajouter une `Toolbar` à un [AppLayout](./app-layout) et utiliser efficacement tous les slots pris en charge.
Pour en savoir plus sur l'implémentation des barres d'outils au sein d'un `AppLayout`, voir [Barres d'outils collantes](./app-layout#sticky-toolbars) et [Disposition de navigation mobile](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Mode compact {#compact-mode}

Utilisez `setCompact(true)` pour réduire le rembourrage autour d'une `Toolbar`. Cela est utile lorsque vous devez faire tenir plus de contenu à l'écran, en particulier dans les applications avec des barres d'outils empilées ou un espace limité. La barre d'outils se comporte toujours de la même manière—seule la hauteur est réduite. Ce mode est couramment utilisé dans les en-têtes, les barres latérales ou les mises en page où l'espace est restreint.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` dans les barres d'outils {#progressbar-in-toolbars}

Une `ProgressBar` sert d'indicateur visuel pour les processus en cours, tels que le chargement de données, le téléchargement de fichiers ou l'achèvement d'étapes dans un flux. Lorsqu'elle est placée à l'intérieur d'une `Toolbar`, la `ProgressBar` s'aligne proprement le long du bord inférieur, la rendant discrète tout en communiquant clairement les progrès aux utilisateurs.

Vous pouvez la combiner avec d'autres composants dans la barre d'outils comme des boutons ou des étiquettes sans perturber la mise en page.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Styliser {#styling}

### Thèmes {#themes}

Les composants `Toolbar` incluent <JavadocLink type="foundation" location="com/webforj/component/Theme">sept thèmes intégrés</JavadocLink> pour une personnalisation visuelle rapide :

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
