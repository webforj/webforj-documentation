---
title: AppLayout
sidebar_position: 5
description: >-
  Build dashboards and admin shells with the AppLayout component, providing a
  fixed header, footer, sliding drawer, and scrollable content area.
_i18n_hash: 559d0c63a8e61e2e3d79086aa08922c1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Le composant `AppLayout` vous fournit une structure de page prête à l'emploi avec un en-tête et un pied de page fixes, un tiroir qui glisse et qui se rétracte, et une zone de contenu défilable. Ensemble, ces sections couvrent les besoins en mise en page des tableaux de bord, des panneaux d'administration et de la plupart des interfaces multi-sections.

<!-- INTRO_END -->

## Fonctionnalités {#features}

Le layout App de webforJ est un composant qui permet de construire des mises en page d'application communes.

<ul>
    <li>Facile à utiliser et à personnaliser</li>
    <li>Conception réactive</li>
    <li>Options de mise en page multiples</li>
    <li>Fonctionne avec le mode sombre de webforJ</li>
</ul>

Il fournit un en-tête, un pied de page, un tiroir et une section de contenu, le tout intégré dans un composant réactif qui peut être facilement personnalisé pour construire rapidement des mises en page d'application communes telles qu'un tableau de bord. L'en-tête et le pied de page sont fixes, le tiroir glisse à l'intérieur et à l'extérieur du champ de vision, et le contenu est défilable.

Chaque partie de la mise en page est un `Div`, qui peut contenir tout contrôle webforJ valide. Pour de meilleurs résultats, l'application doit inclure une balise meta de viewport contenant viewport-fit=cover. La balise meta permet de mettre à l'échelle le viewport pour remplir l'affichage de l'appareil.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Vue d'ensemble {#overview}

Le code d'exemple suivant aboutira à une application avec une barre latérale rétractable contenant un logo et des onglets pour diverses options de contenu et un en-tête. La démo utilise le composant web dwc-icon-button pour créer un bouton de basculement du tiroir. Le bouton possède l'attribut data-drawer-toggle qui demande au DwcAppLayout d'écouter les événements de clic provenant de ce composant pour basculer l'état du tiroir.

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

Par défaut, l'AppLayout rend l'en-tête et le pied de page en mode hors écran. Le mode hors écran signifie que la position de l'en-tête et du pied de page sera décalée pour s'adapter à côté du tiroir ouvert. Désactiver ce mode fera en sorte que l'en-tête et le pied de page occupent tout l'espace disponible et décalera le haut et le bas du tiroir pour s'adapter à l'en-tête et au pied de page.

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
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Barres d'outils multiples {#multiple-toolbars}

La barre de navigation n'a pas de limite au nombre de barres d'outils que vous pouvez ajouter. Une `Toolbar` est un composant conteneur horizontal qui contient un ensemble de boutons d'action, d'icônes ou d'autres contrôles. Pour ajouter une barre d'outils supplémentaire, utilisez simplement la méthode `addToHeader()` pour ajouter un autre composant `Toolbar`.

La démo suivante montre comment utiliser deux barres d'outils. La première contient le bouton bascule du tiroir et le titre de l'application. La seconde barre d'outils contient un menu de navigation secondaire.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmultipleheaders/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Barres d'outils collantes {#sticky-toolbars}

Une barre d'outils collante est une barre d'outils qui reste visible en haut de la page lorsque l'utilisateur fait défiler vers le bas, mais la hauteur de la barre de navigation est réduite pour libérer plus d'espace pour le contenu de la page. Normalement, ce type de barre d'outils contient un menu de navigation fixe qui est pertinent pour la page actuelle.

Il est possible de créer des barres d'outils collantes en utilisant la propriété CSS personnalisée `--dwc-app-layout-header-collapse-height` et l'option `AppLayout.setHeaderReveal()`.

Lorsque `AppLayout.setHeaderReveal(true)` est appelé, l'en-tête sera visible lors du premier rendu, puis masqué lorsque l'utilisateur commence à faire défiler vers le bas. Une fois que l'utilisateur commence à faire défiler vers le haut, l'en-tête sera révélé.

Grâce à la propriété CSS personnalisée `--dwc-app-layout-header-collapse-height`, il est possible de contrôler combien de la barre de navigation de l'en-tête sera masqué.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Mise en page de navigation mobile {#mobile-navigation-layout}

La barre de navigation inférieure peut être utilisée pour fournir une version différente de la navigation en bas de l'application. Ce type de navigation est particulièrement populaire dans les applications mobiles.

Notez comment le tiroir est masqué dans la démo suivante. Le widget AppLayout prend en charge trois positions de tiroir : `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT`, et `DrawerPlacement.HIDDEN`.

Tout comme `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` est également pris en charge. Lorsqu' `AppLayout.setFooterReveal(true)` est appelé, le pied de page sera visible lors du premier rendu puis masqué lorsque l'utilisateur commence à faire défiler vers le haut. Une fois que l'utilisateur commence à faire défiler vers le bas à nouveau, le pied de page sera révélé.

Par défaut, lorsque la largeur de l'écran est de 800px ou moins, le tiroir sera passé en mode popover. C'est ce qu'on appelle le point de rupture. Le mode popover signifie que le tiroir apparaîtra au-dessus de la zone de contenu avec une superposition. Il est possible de configurer le point de rupture en utilisant la méthode `setDrawerBreakpoint()` et le point de rupture doit être une [requête média](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) valide.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmobiledrawer/'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Utilitaires de tiroir {#drawer-utilities}

Les utilitaires de tiroir de `AppLayout` sont conçus pour offrir une navigation intégrée et des menus contextuels au sein de la mise en page principale de l'application, tandis que les composants [`Drawer`](https://docs.webforj.com/docs/components/drawer) autonomes offrent des panneaux coulissants indépendants qui peuvent être utilisés n'importe où dans votre application pour du contenu supplémentaire, des filtres ou des notifications. Cette section se concentre sur les fonctionnalités et utilitaires de tiroir intégrés fournis par AppLayout.

### Point de rupture du tiroir {#drawer-breakpoint}

Par défaut, lorsque la largeur de l'écran est de 800px ou moins, le tiroir sera passé en mode popover. C'est ce qu'on appelle le point de rupture. Le mode popover signifie que le tiroir apparaîtra au-dessus de la zone de contenu avec une superposition. Il est possible de configurer le point de rupture en utilisant la méthode `setDrawerBreakpoint()` et le point de rupture doit être une requête média valide.

Par exemple, dans l'échantillon suivant, le point de rupture du tiroir est configuré pour être de 500px ou moins.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Titre du tiroir {#drawer-title}

Le composant `AppLayout` fournit une méthode `addToDrawerTitle()` pour définir un titre personnalisé à afficher dans l'en-tête du tiroir.

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Actions de tiroir {#drawer-actions}

Le composant `AppLayout` vous permet de placer des composants personnalisés tels que des boutons ou des icônes dans la **zone d'actions de l'en-tête du tiroir** à l'aide de la méthode `addToDrawerHeaderActions()`.

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
);
```

Il est possible de passer plusieurs composants comme arguments :

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
  new Button("Profil")
);
```

Les actions de tiroir apparaissent dans la **section alignée à droite** de l'en-tête du tiroir.

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


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Le composant [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) est une classe webforJ côté serveur qui représente un bouton utilisé pour basculer la visibilité d'un tiroir de navigation dans un `AppLayout`. Il correspond à l'élément client `<dwc-app-drawer-toggle>` et est stylisé par défaut pour se comporter comme une icône de menu hamburger traditionnelle, ce comportement peut être personnalisé.

### Vue d'ensemble {#overview-1}

L' `AppDrawerToggle` étend `IconButton` et utilise par défaut l'icône "menu-2" de l'ensemble d'icônes Tabler. Il applique automatiquement l'attribut `data-drawer-toggle` pour s'intégrer au comportement du tiroir côté client.

```java
// Aucune inscription d'événement requise :
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Le bouton bascule du tiroir fonctionnera dès le départ, sans écouteurs d'événements manuels nécessaires.
```
## Stylisation {#styling}

<TableBuilder name="AppLayout" />
