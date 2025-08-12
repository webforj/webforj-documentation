---
title: AppLayout
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 7f842a66a5bcca7efe7ee894a0b001b0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Le `AppLayout` est un composant de mise en page réactif complet qui fournit un en-tête, un pied de page, un tiroir et une section de contenu. L'en-tête et le pied de page sont fixes, le tiroir glisse à l'intérieur et à l'extérieur du champ de vision, et le contenu est défilable.

Ce composant peut être utilisé pour construire des mises en page d'application courantes, telles qu'un tableau de bord.

## Features {#features}

Le composant App Layout de webforJ permet de créer des mises en page d'application courantes.

<ul>
    <li>Facile à utiliser et à personnaliser</li>
    <li>Design réactif</li>
    <li>Plusieurs options de mise en page</li>
    <li>Fonctionne avec le Mode Sombre webforJ</li>
</ul>

Il fournit un en-tête, un pied de page, un tiroir et une section de contenu, le tout intégré dans un composant réactif qui peut être facilement personnalisé pour créer rapidement des mises en page d'application courantes telles qu'un tableau de bord. L'en-tête et le pied de page sont fixes, le tiroir glisse à l'intérieur et à l'extérieur du champ de vision, et le contenu est défilable.

Chaque partie de la mise en page est un `Div`, qui peut contenir tout contrôle webforJ valide. Pour de meilleurs résultats, l'application doit inclure une balise méta viewport contenant viewport-fit=cover. La balise méta permet de redimensionner le champ de vision pour remplir l'affichage de l'appareil.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overview {#overview}

Le code suivant produira une application avec une barre latérale rétractable contenant un logo et des onglets pour diverses options de contenu ainsi qu'un en-tête. La démonstration utilise le composant web dwc-icon-button pour créer un bouton de basculement du tiroir. Le bouton a l'attribut data-drawer-toggle qui indique à DwcAppLayout d'écouter les événements de clic provenant de ce composant pour basculer l'état du tiroir.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## Full width navbar {#full-width-navbar}

Par défaut, l'AppLayout rend l'en-tête et le pied de page en mode hors écran. Le mode hors écran signifie que la position de l'en-tête et du pied de page sera décalée pour s'adapter à côté du tiroir ouvert. Désactiver ce mode entraînera l'occupation de l'espace disponible entier par l'en-tête et le pied de page, décalant ainsi la position du tiroir en haut et en bas pour s'adapter avec l'en-tête et le pied de page.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Multiple toolbars {#multiple-toolbars}

La barre de navigation n'a aucune limite quant au nombre d'outils que vous pouvez ajouter. Un `Toolbar` est un composant conteneur horizontal qui contient un ensemble de boutons d'action, d'icônes ou d'autres contrôles. Pour ajouter une barre d'outils supplémentaire, utilisez simplement la méthode `addToHeader()` pour ajouter un autre composant `Toolbar`.

La démonstration suivante montre comment utiliser deux barres d'outils. La première contient le bouton de basculement du tiroir et le titre de l'application. La seconde barre d'outils contient un menu de navigation secondaire.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/>

## Sticky toolbars {#sticky-toolbars}

Une barre d'outils fixe est une barre d'outils qui reste visible en haut de la page lorsque l'utilisateur défile vers le bas, mais la hauteur de la barre de navigation est réduite pour libérer plus d'espace pour le contenu de la page. Typiquement, ce type de barre d'outils contient un menu de navigation fixe pertinent à la page actuelle.

Il est possible de créer des barres d'outils fixes en utilisant la propriété CSS personnalisée `--dwc-app-layout-header-collapse-height` et l'option `AppLayout.setHeaderReveal()`.

Lorsque `AppLayout.setHeaderReveal(true)` est appelé, l'en-tête sera visible lors du premier rendu, puis caché lorsque l'utilisateur commence à faire défiler vers le bas. Une fois que l'utilisateur commence à faire défiler vers le haut, l'en-tête sera révélé.

Avec l'aide de la propriété CSS personnalisée `--dwc-app-layout-header-collapse-height`, il est possible de contrôler combien de la barre de navigation de l'en-tête sera cachée.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Mobile navigation layout {#mobile-navigation-layout}

La barre de navigation inférieure peut être utilisée pour fournir une version différente de la navigation au bas de l'application. Ce type de navigation est particulièrement populaire dans les applications mobiles.

Remarquez comment le tiroir est caché dans la démonstration suivante. Le widget AppLayout prend en charge trois positions de tiroir : `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT`, et `DrawerPlacement.HIDDEN`.

Tout comme `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` est pris en charge. Lorsque `AppLayout.setFooterReveal(true)` est appelé, le pied de page sera visible lors du premier rendu puis caché lorsque l'utilisateur commence à faire défiler vers le haut. Une fois que l'utilisateur commence à faire défiler vers le bas, le pied de page sera révélé.

Par défaut, lorsque la largeur de l'écran est de 800px ou moins, le tiroir sera passé en mode popover. C'est ce qu'on appelle le seuil. Le mode popover signifie que le tiroir apparaîtra au-dessus de la zone de contenu avec une superposition. Il est possible de configurer le seuil en utilisant la méthode `setDrawerBreakpoint()`, et le seuil doit être une requête [média valide](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayoutMobile.css'
/>

## Drawer utilities {#drawer-utilities}

Les utilitaires de tiroir `AppLayout` sont conçus pour la navigation intégrée et les menus contextuels au sein de la mise en page principale de l'application, tandis que les composants [`Drawer`](https://docs.webforj.com/docs/components/drawer) indépendants offrent des panneaux coulissants flexibles pouvant être utilisés n'importe où dans votre application pour du contenu supplémentaire, des filtres ou des notifications. Cette section se concentre sur les fonctionnalités et utilitaires de tiroir intégrés fournis par AppLayout.

### Drawer breakpoint {#drawer-breakpoint}

Par défaut, lorsque la largeur de l'écran est de 800px ou moins, le tiroir sera passé en mode popover. C'est ce qu'on appelle le seuil. Le mode popover signifie que le tiroir apparaîtra au-dessus de la zone de contenu avec une superposition. Il est possible de configurer le seuil en utilisant la méthode `setDrawerBreakpoint()`, et le seuil doit être une requête média valide.

Par exemple, dans l'exemple suivant, le seuil du tiroir est configuré à 500px ou moins.

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

Le composant `AppLayout` vous permet de placer des composants personnalisés tels que des boutons ou des icônes dans la **zone d'actions de l'en-tête du tiroir** en utilisant la méthode `addToDrawerHeaderActions()`.

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

Les actions du tiroir apparaissent dans la **section alignée à droite** de l'en-tête du tiroir.

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Le composant [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) est une classe webforJ côté serveur qui représente un bouton utilisé pour basculer la visibilité d'un tiroir de navigation dans un `AppLayout`. Il correspond à l'élément côté client `<dwc-app-drawer-toggle>` et est stylé par défaut pour se comporter comme une icône traditionnelle de menu hamburger, ce comportement peut être personnalisé.

### Overview {#overview-1}

Le `AppDrawerToggle` étend `IconButton` et utilise par défaut l'icône "menu-2" du jeu d'icônes Tabler. Il applique automatiquement l'attribut `data-drawer-toggle` pour s'intégrer au comportement du tiroir côté client.

```java
// Aucune inscription d'événement requise :
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Le basculement du tiroir fonctionnera sans aucun problème—aucun écouteur d'événements manuel nécessaire.
```
## Styling {#styling}

<TableBuilder name="AppLayout" />
