---
title: Toolbar
sidebar_position: 145
_i18n_hash: a0f2d1a3d39ff0d195a5150ea6130710
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Les barres d'outils offrent aux utilisateurs un accès rapide aux actions principales et aux éléments de navigation. Le composant webforJ `Toolbar` est un conteneur horizontal qui peut contenir un ensemble de boutons d'action, d'icônes ou d'autres composants. Il est bien adapté à la gestion des contrôles de page et au logement de fonctions clés comme une barre de recherche ou un bouton de notification.

<!-- INTRO_END -->

## Organisation du contenu de la barre d'outils {#organizing-toolbar-content}

La `Toolbar` organise les composants essentiels dans une disposition facilement accessible et cohérente. Par défaut, elle prend toute la largeur de son élément parent et fournit quatre zones de placement, ou _slots_, pour organiser les composants :

- **Début** : Contient habituellement un <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> ou un bouton d'accueil.
- **Titre** : Utilisé pour les noms ou logos d'application.
- **Contenu** : Pour des actions de haute attention comme la recherche ou la navigation.
- **Fin** : Actions moins fréquentes, telles que le profil utilisateur ou l'aide.

Chaque slot dispose d'une méthode pour ajouter des composants : `addToStart()`, `addToTitle()`, `addToContent()` et `addToEnd()`.

La démonstration suivante montre comment ajouter une `Toolbar` à un [AppLayout](./app-layout) et utiliser efficacement tous les slots pris en charge.
Pour en savoir plus sur l'implémentation des barres d'outils dans un `AppLayout`, consultez [Barres d'outils collantes](./app-layout#sticky-toolbars) et [Disposition de navigation mobile](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
cssURL='/css/toolbar/toolbar-slots-view.css'
height='300px'
/>

## Mode compact {#compact-mode}

Utilisez `setCompact(true)` pour réduire le padding autour d'une `Toolbar`. Ceci est utile lorsque vous devez faire tenir plus de contenu à l'écran, surtout dans des applications avec des barres d'outils empilées ou un espace limité. La barre d'outils se comporte toujours de la même manière—seule la hauteur est réduite. Ce mode est couramment utilisé dans les en-têtes, les barres latérales ou les dispositions où l'espace est restreint.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` dans les barres d'outils {#progressbar-in-toolbars}

Une `ProgressBar` sert d'indicateur visuel pour les processus en cours, tels que le chargement de données, le téléchargement de fichiers ou l'achèvement des étapes d'un flux. Lorsqu'elle est placée à l'intérieur d'une `Toolbar`, la `ProgressBar` s'aligne joliment le long du bord inférieur, la rendant peu intrusive tout en communiquant clairement les progrès aux utilisateurs.

Vous pouvez la combiner avec d'autres composants dans la barre d'outils comme des boutons ou des étiquettes sans perturber la disposition.

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
