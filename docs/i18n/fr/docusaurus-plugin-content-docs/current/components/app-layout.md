---
title: AppLayout
sidebar_position: 5
description: >-
  Build dashboards and admin shells with the AppLayout component, providing a
  fixed header, footer, sliding drawer, and scrollable content area.
_i18n_hash: 4e36be1fd55d6c17c810ace6ef3f1fd8
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Le composant `AppLayout` vous fournit une structure de page prête à l'emploi avec un en-tête et un pied de page fixes, un tiroir qui glisse à l'intérieur et à l'extérieur, et une zone de contenu défilable. Ensemble, ces sections couvrent les besoins en matière de mise en page pour des tableaux de bord, des panneaux d'administration et la plupart des interfaces à sections multiples.

<!-- INTRO_END -->

## Caractéristiques {#features}

Le App Layout de webforJ est un composant qui permet de construire des mises en page d'application courantes.

<ul>
    <li>Facile à utiliser et personnaliser</li>
    <li>Design réactif</li>
    <li>Plusieurs options de mise en page</li>
    <li>Fonctionne avec le mode sombre de webforJ</li>
</ul>

Il fournit un en-tête, un pied de page, un tiroir et une section de contenu, le tout intégré dans un composant réactif qui peut être facilement personnalisé pour construire rapidement des mises en page d'application courantes telles qu'un tableau de bord. L'en-tête et le pied de page sont fixes, le tiroir glisse à l'intérieur et à l'extérieur de la zone de vue, et le contenu est défilable.

Chaque partie de la mise en page est un `Div`, qui peut contenir tout contrôle webforJ valide. Pour de meilleurs résultats, l'application doit inclure une balise méta viewport contenant viewport-fit=cover. La balise méta permet à la zone de visualisation d'être redimensionnée pour remplir l'écran de l'appareil.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Apperçu {#overview}

Le code ci-dessous donnera lieu à une application avec une barre latérale réductible contenant un logo et des onglets pour diverses options de contenu, ainsi qu'un en-tête. La démo utilise le composant web dwc-icon-button pour créer un bouton de basculement du tiroir. Le bouton a l'attribut data-drawer-toggle qui indique au DwcAppLayout d'écouter les événements de clic provenant de ce composant pour changer l'état du tiroir.

<!--vale off-->
<ComponentDemo
path='/webforj/applayout/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Barre de navigation pleine largeur {#full-width-navbar}

Par défaut, l'AppLayout rend l'en-tête et le pied de page en mode hors écran. Le mode hors écran signifie que la position de l'en-tête et du pied de page sera décalée pour s'adapter à côté du tiroir ouvert. Désactiver ce mode fera que l'en-tête et le pied de page occuperont tout l'espace disponible et décalera la position supérieure et inférieure du tiroir pour s'adapter à l'en-tête et au pied de page.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutfullnavbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/applayout.css',
]}
/>
<!--vale on-->

## Barres d'outils multiples {#multiple-toolbars}

La barre de navigation n'a pas de limite quant au nombre de barres d'outils que vous pouvez ajouter. Une `Toolbar` est un composant conteneur horizontal qui contient un ensemble de boutons d'action, d'icônes ou d'autres contrôles. Pour ajouter une barre d'outils supplémentaire, utilisez simplement la méthode `addToHeader()` pour ajouter un autre composant `Toolbar`.

La démo suivante montre comment utiliser deux barres d'outils. La première abrite le bouton de basculement du tiroir et le titre de l'application. La deuxième barre d'outils abrite un menu de navigation secondaire.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmultipleheaders/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/applayout.css',
]}
/>
<!--vale on-->

## Barres d'outils collantes {#sticky-toolbars}

Une barre d'outils collante est une barre d'outils qui reste visible en haut de la page lorsque l'utilisateur défile vers le bas, mais dont la hauteur de la barre de navigation est réduite pour libérer plus d'espace pour le contenu de la page. Généralement, ce type de barre d'outils contient un menu de navigation fixe pertinent pour la page actuellement affichée.

Il est possible de créer des barres d'outils collantes à l'aide de la propriété CSS personnalisée `--dwc-app-layout-header-collapse-height` et de l'option `AppLayout.setHeaderReveal()`.

Lorsque `AppLayout.setHeaderReveal(true)` est appelé, l'en-tête sera visible lors du premier rendu, puis caché lorsque l'utilisateur commencera à faire défiler vers le bas. Une fois que l'utilisateur commence à faire défiler à nouveau vers le haut, l'en-tête sera révélé.

Avec l'aide de la propriété CSS personnalisée `--dwc-app-layout-header-collapse-height`, il est possible de contrôler combien de la barre de navigation de l'en-tête sera cachée.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/applayout.css',
]}
/>
<!--vale on-->

## Mise en page de navigation mobile {#mobile-navigation-layout}

La barre de navigation inférieure peut être utilisée pour fournir une version différente de la navigation au bas de l'application. Ce type de navigation est particulièrement populaire dans les applications mobiles.

Remarquez comment le tiroir est caché dans la démo suivante. Le widget AppLayout prend en charge trois positions de tiroir : `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` et `DrawerPlacement.HIDDEN`.

Comme `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` est également pris en charge. Lorsque `AppLayout.setFooterReveal(true)` est appelé, le pied de page sera visible lors du premier rendu, puis caché lorsque l'utilisateur commencera à faire défiler vers le haut. Une fois que l'utilisateur commence à défiler vers le bas à nouveau, le pied de page sera révélé.

Par défaut, lorsque la largeur de l'écran est de 800px ou moins, le tiroir passera en mode popover. C'est ce qu'on appelle le point d'arrêt. Le mode popover signifie que le tiroir apparaîtra au-dessus de la zone de contenu avec une superposition. Il est possible de configurer le point d'arrêt en utilisant la méthode `setDrawerBreakpoint()` et le point d'arrêt doit être une requête média valide [media query](https://developer.mozilla.org/fr/docs/Web/CSS/Media_Queries/Using_media_queries).

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmobiledrawer/'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/applayout.css',
]}
/>
<!--vale on-->

## Utilitaires de tiroir {#drawer-utilities}

Les utilitaires de tiroir de `AppLayout` sont conçus pour une navigation intégrée et des menus contextuels au sein de la mise en page principale de l'application, tandis que les composants [`Drawer`](https://docs.webforj.com/docs/components/drawer) autonomes offrent des panneaux coulissants flexibles et indépendants qui peuvent être utilisés n'importe où dans votre application pour un contenu supplémentaire, des filtres ou des notifications. Cette section se concentre sur les fonctionnalités et utilitaires intégrés du tiroir fournis par AppLayout.

### Point d'arrêt du tiroir {#drawer-breakpoint}

Par défaut, lorsque la largeur de l'écran est de 800px ou moins, le tiroir passera en mode popover. C'est ce qu'on appelle le point d'arrêt. Le mode popover signifie que le tiroir apparaîtra au-dessus de la zone de contenu avec une superposition. Il est possible de configurer le point d'arrêt en utilisant la méthode `setDrawerBreakpoint()` et le point d'arrêt doit être une requête média valide.

Par exemple, dans l'exemple suivant, le point d'arrêt du tiroir est configuré à 500px ou moins.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Titre du tiroir {#drawer-title}

Le composant `AppLayout` fournit une méthode `addToDrawerTitle()` pour définir un titre personnalisé à afficher dans l'en-tête du tiroir.

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Actions du tiroir {#drawer-actions}

Le composant `AppLayout` vous permet de placer des composants personnalisés tels que des boutons ou des icônes dans la **zone d'actions de l'en-tête du tiroir** en utilisant la méthode `addToDrawerHeaderActions()`.

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
);
```

Il est possible de passer plusieurs composants comme arguments :

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
  new Button("Profile")
);
```

Les actions du tiroir apparaissent dans la **section alignée à droite** de l'en-tête du tiroir.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutdrawerutility/content/Dashboard/'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='name' label="dwc-icon-button" /> <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Le [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) est une classe webforJ côté serveur qui représente un bouton utilisé pour basculer la visibilité d'un tiroir de navigation dans un `AppLayout`. Il correspond à l'élément client `<dwc-app-drawer-toggle>` et est stylé par défaut pour se comporter comme une icône de menu hamburger traditionnelle, ce comportement peut être personnalisé.

### Aperçu {#overview-1}

Le `AppDrawerToggle` étend `IconButton` et utilise par défaut l'icône "menu-2" de l'ensemble d'icônes Tabler. Il applique automatiquement l'attribut `data-drawer-toggle` pour s'intégrer au comportement de tiroir côté client.

```java
// Aucune inscription d'événements requise :
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Le bouton de basculement du tiroir fonctionnera sans problème - aucune écoute d'événements manuelle nécessaire.
```
## Stylisation {#styling}

<TableBuilder name="AppLayout" />
