---
title: Toolbar
sidebar_position: 145
_i18n_hash: 446d71b3e376810254bbbf6ffee43aa9
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Les barres d'outils offrent aux utilisateurs un accès rapide aux actions principales et aux éléments de navigation. Le composant webforJ `Toolbar` est un conteneur horizontal qui peut contenir un ensemble de boutons d'action, d'icônes ou d'autres composants. Il est bien adapté pour gérer les contrôles de page et abriter des fonctions clés comme une barre de recherche ou un bouton de notification.

## Organisation du contenu de la barre d'outils {#organizing-toolbar-content}

La `Toolbar` organise les composants essentiels dans une mise en page facilement accessible et cohérente. Par défaut, elle occupe toute la largeur de son élément parent et fournit quatre zones de placement, ou _emplacements_, pour organiser les composants :

- **Début** : Contient généralement un <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> ou un bouton d'accueil.
- **Titre** : Utilisé pour les noms d'applications ou les logos.
- **Contenu** : Pour des actions à forte attention comme la recherche ou la navigation.
- **Fin** : Actions moins fréquentes, telles que le profil utilisateur ou l'aide.

Chaque emplacement a une méthode pour ajouter des composants : `addToStart()`, `addToTitle()`, `addToContent()`, et `addToEnd()`.

La démo suivante montre comment ajouter une `Toolbar` à un [AppLayout](./app-layout) et utiliser tous les emplacements pris en charge de manière efficace.
Pour en savoir plus sur la mise en œuvre des barres d'outils dans un `AppLayout`, consultez [Barres d'outils collantes](./app-layout#sticky-toolbars) et [Mise en page de navigation mobile](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Mode compact {#compact-mode}

Utilisez `setCompact(true)` pour réduire l'espacement autour d'une `Toolbar`. Cela est utile lorsque vous devez faire tenir plus de contenu à l'écran, en particulier dans des applications avec des barres d'outils empilées ou un espace limité. La barre d'outils se comporte toujours de la même manière—seule la hauteur est réduite. Ce mode est couramment utilisé dans les en-têtes, les barres latérales ou les mises en page où l'espace est restreint.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` dans les barres d'outils {#progressbar-in-toolbars}

Une `ProgressBar` sert d'indicateur visuel pour les processus en cours, tels que le chargement de données, le téléchargement de fichiers ou l'achèvement d'étapes dans un flux. Lorsqu'elle est placée à l'intérieur d'une `Toolbar`, la `ProgressBar` s'aligne parfaitement le long du bord inférieur, la rendant peu intrusive tout en communiquant clairement les progrès aux utilisateurs.

Vous pouvez l'associer à d'autres composants de la barre d'outils comme des boutons ou des étiquettes sans perturber la mise en page.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Style {#styling}

### Thèmes {#themes}

Les composants `Toolbar` incluent <JavadocLink type="foundation" location="com/webforj/component/Theme">sept thèmes intégrés</JavadocLink> pour une personnalisation visuelle rapide :

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
