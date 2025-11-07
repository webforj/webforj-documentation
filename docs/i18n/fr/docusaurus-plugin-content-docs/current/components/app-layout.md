---
title: AppLayout
sidebar_position: 5
_i18n_hash: 7bc8b2a8bfc772644cf2107199615515
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Le `AppLayout` est un composant de mise en page réactive complet qui fournit un en-tête, un pied de page, un tiroir et une section de contenu. L'en-tête et le pied de page sont fixes, le tiroir glisse à l'intérieur et à l'extérieur du champ de vision, et le contenu est déroulable.

Ce composant peut être utilisé pour construire des mises en page d'application courantes, telles qu'un tableau de bord.

## Features {#features}

Le webforJ App Layout est un composant qui permet de construire des mises en page d'application courantes.

<ul>
    <li>Facile à utiliser et à personnaliser</li>
    <li>Design réactif</li>
    <li>Options de mise en page multiples</li>
    <li>Fonctionne avec le mode sombre de webforJ</li>
</ul>

Il offre un en-tête, un pied de page, un tiroir et une section de contenu intégrés dans un composant réactif qui peut être facilement personnalisé pour construire rapidement des mises en page d'application courantes telles qu'un tableau de bord. L'en-tête et le pied de page sont fixes, le tiroir glisse à l'intérieur et à l'extérieur du champ de vision, et le contenu est déroulable.

Chaque partie de la mise en page est un `Div`, qui peut contenir tout contrôle webforJ valide. Pour de meilleurs résultats, l'application doit inclure une balise meta de viewport contenant viewport-fit=cover. La balise meta provoque le redimensionnement du viewport pour remplir l'affichage de l'appareil.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overview {#overview}

Le code d'exemple suivant donnera lieu à une application avec une barre latérale réductible qui contient un logo et des onglets pour diverses options de contenu et un en-tête. La démo utilise le composant web dwc-icon-button pour créer un bouton de basculement du tiroir. Le bouton a l'attribut data-drawer-toggle qui instructe le DwcAppLayout d'écouter les événements de clic provenant de ce composant pour basculer l'état du tiroir.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Full width navbar {#full-width-navbar}

Par défaut, l'AppLayout rend l'en-tête et le pied de page en mode hors écran. Le mode hors écran signifie que la position de l'en-tête et du pied de page sera décalée pour s'adapter à côté du tiroir ouvert. Désactiver ce mode fera que l'en-tête et le pied de page occuperont tout l'espace disponible et décaleront la position du tiroir en haut et en bas pour s'adapter à l'en-tête et au pied de page.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Multiple toolbars {#multiple-toolbars}

La barre de navigation n'a pas de limite quant au nombre de barres d'outils que vous pouvez ajouter. Une `Toolbar` est un composant conteneur horizontal qui contient un ensemble de boutons d'action, d'icônes ou d'autres contrôles. Pour ajouter une barre d'outils supplémentaire, utilisez simplement la méthode `addToHeader()` pour ajouter un autre composant `Toolbar`.

La démo suivante montre comment utiliser deux barres d'outils. La première contient le bouton de basculement du tiroir et le titre de l'application. La deuxième barre d'outils abrite un menu de navigation secondaire.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Sticky toolbars {#sticky-toolbars}

Une barre d'outils collante est une barre d'outils qui reste visible en haut de la page lorsque l'utilisateur fait défiler vers le bas, mais la hauteur de la barre de navigation est réduite pour libérer plus d'espace pour le contenu de la page. En général, ce type de barre d'outils contient un menu de navigation fixe qui est pertinent par rapport à la page actuelle.

Il est possible de créer des barres d'outils collantes en utilisant la propriété CSS personnalisée `--dwc-app-layout-header-collapse-height` et l'option `AppLayout.setHeaderReveal()`.

Lorsque `AppLayout.setHeaderReveal(true)` est appelé, l'en-tête sera visible lors du premier rendu, puis caché lorsque l'utilisateur commence à faire défiler vers le bas. Une fois que l'utilisateur commence à faire défiler vers le haut à nouveau, l'en-tête sera révélé.

Avec l'aide de la propriété CSS personnalisée `--dwc-app-layout-header-collapse-height`, il est possible de contrôler combien de la barre de navigation de l'en-tête sera cachée.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mobile navigation layout {#mobile-navigation-layout}

La barre de navigation inférieure peut être utilisée pour fournir une version différente de la navigation au bas de l'application. Ce type de navigation est particulièrement populaire dans les applications mobiles.

Remarquez comment le tiroir est caché dans la démo suivante. Le widget AppLayout prend en charge trois positions de tiroir : `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` et `DrawerPlacement.HIDDEN`.

Tout comme `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` est pris en charge. Lorsque `AppLayout.setFooterReveal(true)` est appelé, le pied de page sera visible lors du premier rendu puis caché lorsque l'utilisateur commence à faire défiler vers le haut. Une fois que l'utilisateur commence à faire défiler vers le bas à nouveau, le pied de page sera révélé.

Par défaut, lorsque la largeur de l'écran est de 800px ou moins, le tiroir sera basculé en mode popover. Cela s'appelle le point de rupture. Le mode popover signifie que le tiroir apparaîtra au-dessus de la zone de contenu avec une superposition. Il est possible de configurer le point de rupture en utilisant la méthode `setDrawerBreakpoint()` et le point de rupture doit être une [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) valide.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Drawer utilities {#drawer-utilities}

Les utilitaires de tiroir de `AppLayout` sont conçus pour la navigation intégrée et les menus contextuels au sein de la mise en page principale de l'application, tandis que les composants [`Drawer`](https://docs.webforj.com/docs/components/drawer) autonomes offrent des panneaux coulissants flexibles et indépendants pouvant être utilisés partout dans votre application pour un contenu supplémentaire, des filtres ou des notifications. Cette section se concentre sur les fonctionnalités et utilitaires intégrés au tiroir fournis par AppLayout.

### Drawer breakpoint {#drawer-breakpoint}

Par défaut, lorsque la largeur de l'écran est de 800px ou moins, le tiroir sera basculé en mode popover. Cela s'appelle le point de rupture. Le mode popover signifie que le tiroir apparaîtra au-dessus de la zone de contenu avec une superposition. Il est possible de configurer le point de rupture en utilisant la méthode `setDrawerBreakpoint()` et le point de rupture doit être une media query valide.

Par exemple, dans l'échantillon suivant, le point de rupture du tiroir est configuré pour être de 500px ou moins.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Drawer title {#drawer-title}

Le composant `AppLayout` fournit une méthode `addToDrawerTitle()` pour définir un titre personnalisé à afficher dans l'en-tête du tiroir.

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Drawer actions {#drawer-actions}

Le composant `AppLayout` vous permet de placer des composants personnalisés tels que des boutons ou des icônes dans la **zone des actions de l'en-tête du tiroir** à l'aide de la méthode `addToDrawerHeaderActions()`.

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
);
```

Il est possible de passer plusieurs composants en tant qu'arguments :

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
    new Button("Profile")
);
```

Les actions de tiroir apparaissent dans la **section alignée à droite** de l'en-tête du tiroir.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Le composant [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) est une classe webforJ côté serveur qui représente un bouton utilisé pour basculer la visibilité d'un tiroir de navigation dans un `AppLayout`. Il correspond à l'élément client `<dwc-app-drawer-toggle>` et est stylé par défaut pour se comporter comme une icône de menu hamburger traditionnelle, ce comportement peut être personnalisé.

### Overview {#overview-1}

Le `AppDrawerToggle` étend `IconButton` et utilise l'icône "menu-2" de l'ensemble d'icônes Tabler par défaut. Il applique automatiquement l'attribut `data-drawer-toggle` pour s'intégrer au comportement du tiroir côté client.

```java
// Pas d'enregistrement d'événement requis :
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Le basculement du tiroir fonctionnera immédiatement—sans écouteurs d'événements manuels nécessaires.
```
## Styling {#styling}

<TableBuilder name="AppLayout" />
