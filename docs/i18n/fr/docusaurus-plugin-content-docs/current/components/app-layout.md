---
title: AppLayout
sidebar_position: 5
_i18n_hash: 0aea09dee535e578082dd6df642503d4
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Le composant `AppLayout` vous offre une structure de page prête à l'emploi avec un en-tête et un pied de page fixes, un tiroir qui glisse à l'intérieur et à l'extérieur, et une zone de contenu défilable. Ensemble, ces sections couvrent les besoins de mise en page des tableaux de bord, des panneaux d'administration et de la plupart des interfaces à sections multiples.

<!-- INTRO_END -->

## Caractéristiques {#features}

Le webforJ App Layout est un composant qui permet de construire des mises en page d'application communes.

<ul>
    <li>Facile à utiliser et à personnaliser</li>
    <li>Conception responsive</li>
    <li>Options de mise en page multiples</li>
    <li>Fonctionne avec le mode sombre de webforJ</li>
</ul>

Il fournit un en-tête, un pied de page, un tiroir et une section de contenu, tous intégrés dans un composant responsive qui peut être facilement personnalisé pour créer rapidement des mises en page d'application courantes telles qu'un tableau de bord. L'en-tête et le pied de page sont fixes, le tiroir glisse à l'intérieur et à l'extérieur du champ de vision, et le contenu est défilable.

Chaque partie de la mise en page est un `Div`, qui peut contenir n'importe quel contrôle webforJ valide. Pour de meilleurs résultats, l'application doit inclure une balise méta viewport qui contient viewport-fit=cover. La balise méta permet de redimensionner le champ de vision pour remplir l'affichage de l'appareil.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Aperçu {#overview}

Le code d'exemple suivant produira une application avec une barre latérale rétractable qui contient un logo et des onglets pour diverses options de contenu et un en-tête. La démonstration utilise le composant web dwc-icon-button pour créer un bouton de basculement du tiroir. Le bouton a l'attribut data-drawer-toggle qui demande au DwcAppLayout d'écouter les événements de clic provenant de ce composant pour basculer l'état du tiroir.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Barre de navigation pleine largeur {#full-width-navbar}

Par défaut, l'AppLayout rend l'en-tête et le pied de page en mode hors écran. Le mode hors écran signifie que la position de l'en-tête et du pied de page sera décalée pour s'adapter à côté du tiroir ouvert. Désactiver ce mode fera en sorte que l'en-tête et le pied de page occuperont tout l'espace disponible et décaleront la position du tiroir en haut et en bas pour s'adapter à l'en-tête et au pied de page.

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

## Barres d'outils multiples {#multiple-toolbars}

La barre de navigation n'a pas de limite au nombre de barres d'outils que vous pouvez ajouter. Une `Toolbar` est un composant conteneur horizontal qui contient un ensemble de boutons d'action, d'icônes ou d'autres contrôles. Pour ajouter une barre d'outils supplémentaire, utilisez simplement la méthode `addToHeader()` pour ajouter un autre composant `Toolbar`.

La démonstration suivante montre comment utiliser deux barres d'outils. La première contient le bouton de basculement du tiroir et le titre de l'application. La deuxième barre d'outils contient un menu de navigation secondaire.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Barres d'outils collantes {#sticky-toolbars}

Une barre d'outils collante est une barre d'outils qui reste visible en haut de la page lorsque l'utilisateur défile vers le bas, mais la hauteur de la barre de navigation est réduite pour libérer plus d'espace pour le contenu de la page. En général, ce type de barre d'outils contient un menu de navigation fixe qui est pertinent pour la page actuelle.

Il est possible de créer des barres d'outils collantes en utilisant la propriété CSS personnalisée `--dwc-app-layout-header-collapse-height` et l'option `AppLayout.setHeaderReveal()`.

Lorsque `AppLayout.setHeaderReveal(true)` est appelé, l'en-tête sera visible lors du premier rendu, puis caché lorsque l'utilisateur commencera à faire défiler vers le bas. Une fois que l'utilisateur commence à faire défiler vers le haut à nouveau, l'en-tête sera révélé.

Avec l'aide de la propriété CSS personnalisée `--dwc-app-layout-header-collapse-height`, il est possible de contrôler combien de la barre de navigation de l'en-tête sera caché.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mise en page de navigation mobile {#mobile-navigation-layout}

La barre de navigation du bas peut être utilisée pour fournir une version différente de la navigation en bas de l'application. Ce type de navigation est particulièrement populaire dans les applications mobiles.

Remarquez comment le tiroir est caché dans la démonstration suivante. Le widget AppLayout prend en charge trois positions de tiroir : `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT`, et `DrawerPlacement.HIDDEN`.

Tout comme `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` est pris en charge. Lorsque `AppLayout.setFooterReveal(true)` est appelé, le pied de page sera visible lors du premier rendu, puis caché lorsque l'utilisateur commencera à faire défiler vers le haut. Une fois que l'utilisateur commencera à faire défiler vers le bas à nouveau, le pied de page sera révélé.

Par défaut, lorsque la largeur de l'écran est de 800px ou moins, le tiroir passera en mode popover. C'est ce qu'on appelle le point de rupture. Le mode popover signifie que le tiroir apparaîtra au-dessus de la zone de contenu avec un overlay. Il est possible de configurer le point de rupture en utilisant la méthode `setDrawerBreakpoint()` et le point de rupture doit être une requête [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) valide.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Utilitaires de tiroir {#drawer-utilities}

Les utilitaires de tiroir de l'`AppLayout` sont conçus pour une navigation intégrée et des menus contextuels au sein de la mise en page principale de l'application, tandis que les composants [`Drawer`](https://docs.webforj.com/docs/components/drawer) autonomes offrent des panneaux glissants flexibles et indépendants qui peuvent être utilisés n'importe où dans votre application pour un contenu supplémentaire, des filtres ou des notifications. Cette section se concentre sur les fonctionnalités et utilitaires de tiroir intégrés fournis par AppLayout.

### Point de rupture du tiroir {#drawer-breakpoint}

Par défaut, lorsque la largeur de l'écran est de 800px ou moins, le tiroir passera en mode popover. C'est ce qu'on appelle le point de rupture. Le mode popover signifie que le tiroir apparaîtra au-dessus de la zone de contenu avec un overlay. Il est possible de configurer le point de rupture en utilisant la méthode `setDrawerBreakpoint()` et le point de rupture doit être une requête media query valide.

Par exemple, dans l'échantillon suivant, le point de rupture du tiroir est configuré à 500px ou moins.

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

Le composant `AppLayout` vous permet de placer des composants personnalisés tels que des boutons ou des icônes dans la **zone des actions de l'en-tête du tiroir** à l'aide de la méthode `addToDrawerHeaderActions()`.

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

Les actions du tiroir apparaissent dans la **section alignée à droite** de l'en-tête du tiroir.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Le composant [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) est une classe webforJ côté serveur qui représente un bouton utilisé pour basculer la visibilité d'un tiroir de navigation dans un `AppLayout`. Il est mappé à l'élément client `<dwc-app-drawer-toggle>` et est stylé par défaut pour se comporter comme une icône de menu hamburger traditionnelle, ce comportement peut être personnalisé.

### Aperçu {#overview-1}

L'`AppDrawerToggle` étend `IconButton` et utilise l'icône "menu-2" du jeu d'icônes Tabler par défaut. Il applique automatiquement l'attribut `data-drawer-toggle` pour s'intégrer au comportement du tiroir côté client.

```java
// Aucune inscription d'événement requise :
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Le basculeur de tiroir fonctionnera immédiatement—aucun écouteur d'événements manuel n'est nécessaire.
```
## Style {#styling}

<TableBuilder name="AppLayout" />
