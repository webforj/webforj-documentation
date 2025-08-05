---
title: Drawer
sidebar_position: 35
_i18n_hash: e3b531e5fb7f1554e035f4d05aad8512
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Le tiroir est un conteneur qui glisse dans le champ de vision pour exposer des options et des informations supplémentaires. Plusieurs tiroirs peuvent être créés dans une application, et ils seront empilés les uns sur les autres.

Le composant Drawer peut être utilisé dans de nombreuses situations différentes, comme en fournissant un menu de navigation qui peut être activé, un panneau qui affiche des informations supplémentaires ou contextuelles, ou pour optimiser l'utilisation sur un appareil mobile. L'exemple suivant montrera une application mobile qui utilise le composant webforJ AppLayout et affiche un tiroir "Welcome Popup" en bas lorsqu'elle est chargée pour la première fois. De plus, un composant Drawer de navigation peut être activé dans l'application en cliquant sur le menu hamburger.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Usages {#usages}

1. **Menu de Navigation** : Une utilisation courante d'un composant tiroir est en tant que menu de navigation. Il fournit un moyen efficace en espace d'afficher des liens vers diverses sections ou pages de votre application, en particulier dans les mises en page mobiles ou responsives. Les utilisateurs peuvent ouvrir et fermer le tiroir pour accéder aux options de navigation sans encombrer la zone de contenu principale.

2. **Filtrer et Barre Latérale** : Un tiroir peut être utilisé comme filtre ou barre latérale dans les applications qui affichent une liste d'éléments. Les utilisateurs peuvent agrandir le tiroir pour révéler des options de filtrage, des contrôles de tri ou des informations supplémentaires liées aux éléments de la liste. Cela permet de garder le contenu principal axé sur la liste tout en fournissant des fonctionnalités avancées de manière accessible.

3. **Profil Utilisateur ou Paramètres** : Vous pouvez utiliser un tiroir pour afficher des informations de profil utilisateur ou des paramètres de l'application. Cela permet de garder ces informations facilement accessibles mais cachées lorsqu'elles ne sont pas nécessaires, maintenant ainsi une interface propre et dégagée. Les utilisateurs peuvent ouvrir le tiroir pour mettre à jour leurs profils ou ajuster leurs paramètres.

4. **Notifications** : Pour les applications avec des notifications ou des alertes, un tiroir peut glisser pour afficher de nouveaux messages ou mises à jour. Les utilisateurs peuvent rapidement vérifier et rejeter les notifications sans quitter leur vue actuelle.

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## Personnalisation {#customization}

Diverses propriétés existent permettant la personnalisation des différents attributs du composant Drawer. Cette section décrit ces propriétés avec des exemples de modification.

## Autofocus {#autofocus}

La propriété Auto-Focus est conçue pour améliorer l'accessibilité et l'utilisation en se concentrant automatiquement sur le premier élément d'un tiroir lorsqu'il est ouvert. Cette fonctionnalité élimine le besoin pour les utilisateurs de naviguer manuellement vers l'élément souhaité, leur faisant gagner du temps et de l'effort.

Lorsque le tiroir est déclenché pour s'ouvrir, que ce soit par un événement, par défaut ou toute autre interaction, le focus de l'utilisateur est dirigé vers le premier élément à l'intérieur du tiroir. Cet élément peut être un bouton, un lien, une option de menu ou tout autre élément pouvant recevoir le focus.

:::tip
En mettant automatiquement le focus sur le premier élément, le développeur s'assure que les utilisateurs peuvent immédiatement interagir avec l'option la plus pertinente ou fréquemment utilisée sans avoir à tabuler ou faire défiler l'ensemble du tiroir. Ce comportement rationalise l'expérience utilisateur et favorise une navigation efficace dans l'interface utilisateur.
:::

Cette propriété peut également être particulièrement bénéfique pour les personnes qui s'appuient sur la navigation au clavier ou les technologies d'assistance telles que les lecteurs d'écran. Elle fournit un point de départ clair au sein du tiroir et permet aux utilisateurs d'accéder à la fonctionnalité souhaitée sans saisie manuelle inutile.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Exemple -->

## Étiquette {#label}

La propriété Étiquette du tiroir est une fonctionnalité conçue pour améliorer l'accessibilité et fournir un contexte descriptif pour un tiroir au sein d'une interface utilisateur. Cette propriété permet aux développeurs d'attribuer une étiquette à un tiroir, principalement dans un but d'accessibilité, garantissant que les lecteurs d'écran et autres technologies d'assistance peuvent transmettre avec précision le but et le contenu du tiroir aux utilisateurs.

Lorsque la propriété Étiquette du tiroir est utilisée, l'étiquette assignée devient une partie intégrante de l'infrastructure d'accessibilité du tiroir. Cela permet aux utilisateurs qui s'appuient sur les technologies d'assistance de comprendre la fonction du tiroir et de naviguer dans l'interface plus efficacement.

En fournissant une étiquette pour le tiroir, les développeurs s'assurent que les lecteurs d'écran annoncent le but du tiroir aux utilisateurs malvoyants. Cette information permet aux individus de prendre des décisions éclairées concernant leur interaction avec le tiroir, car ils peuvent comprendre son contenu et sa pertinence au sein de l'interface utilisateur plus large.

La propriété Étiquette peut être personnalisée pour s'adapter au contexte spécifique et aux exigences de conception de l'application. Les développeurs ont la flexibilité de fournir des étiquettes concises et descriptives qui représentent avec précision le contenu ou la fonctionnalité du tiroir.

<!-- Exemple -->

<!-- ## Taille

La propriété `size` du composant Drawer permet aux développeurs de contrôler et de spécifier les dimensions du tiroir au sein de l'interface utilisateur. Cette propriété permet d'ajuster la taille du tiroir, garantissant qu'elle s'aligne sur les exigences de mise en page et de conception souhaitées.

Lors de l'utilisation de la propriété `size`, les développeurs ont la flexibilité de définir la largeur et la hauteur du tiroir en fonction de leurs besoins spécifiques. Contrairement à la propriété `maxSize`, qui définit une limite maximale, la propriété `size` offre un contrôle explicite sur la taille réelle du tiroir.

Les développeurs peuvent personnaliser la propriété `size` en fonction de l'espace disponible à l'écran, de la quantité de contenu à afficher et de l'esthétique globale de la conception. Ce niveau de contrôle permet de créer des interfaces visuellement équilibrées et fonctionnelles.

La propriété `size` peut être définie en utilisant diverses unités telles que des pixels, des pourcentages ou d'autres valeurs de mesure CSS appropriées. Cette polyvalence garantit que la taille du tiroir peut être ajustée précisément pour s'adapter à différentes tailles d'écran, résolutions et types d'appareils.

En utilisant efficacement la propriété `size`, les développeurs peuvent créer des interfaces réactives qui s'adaptent à différents écrans et orientations. Par exemple, une taille plus petite peut être choisie pour les appareils mobiles afin d'optimiser l'utilisation de l'espace, tandis que des tailles plus grandes peuvent être utilisées pour les écrans de bureau pour tirer parti de la superficie d'écran disponible. -->

### Taille Maximale {#max-size}

La propriété taille maximale du tiroir est une fonctionnalité polyvalente conçue pour contrôler la largeur ou la hauteur maximum d'un tiroir au sein d'une interface utilisateur, en fonction du placement spécifié. Cette propriété permet aux développeurs de définir la taille maximale du tiroir, garantissant une présentation et une mise en page optimales tout en s'adaptant à différentes tailles d'écran et résolutions de dispositifs.

:::info
Pour définir la taille du tiroir, modifiez la propriété `size` - `maxSize` est utilisé pour garantir qu'un tiroir ne dépasse jamais une certaine valeur.
:::

Lors de l'utilisation de la propriété taille maximale du tiroir, les développeurs peuvent définir une valeur de taille maximale exprimée en pixels, en pourcentages ou d'autres valeurs de mesure CSS appropriées. Cette valeur représente la largeur maximale lorsque le tiroir est placé sur le côté gauche ou droit de l'interface ou la hauteur maximale lorsqu'il est placé en haut ou en bas.

En définissant une taille maximale pour le tiroir, les développeurs maintiennent le contrôle sur ses dimensions et empêchent qu'il ne devienne excessivement large ou haut, ce qui pourrait entraver l'expérience utilisateur globale. L'approche de mesure CSS permet une réactivité, adaptant la taille du tiroir dynamiquement en fonction de l'espace disponible à l'écran.

La propriété taille maximale du tiroir est particulièrement bénéfique lors du traitement de conceptions réactives et adaptatives. Elle garantit que le tiroir reste visuellement agréable et fonctionnel sur différents dispositifs, orientations d'écran et fenêtres.

Lorsque le contenu d'un tiroir dépasse la taille maximale définie, les développeurs peuvent mettre en œuvre des techniques appropriées pour gérer le débordement, telles que le défilement au sein du tiroir ou l'utilisation de modèles d'interface utilisateur supplémentaires comme des onglets ou des accordéons. Cela aide à maintenir une interface propre et organisée tout en accueillant de plus grandes quantités de contenu. 

## Placement {#placement}

La propriété placement du composant UI Drawer permet aux développeurs de spécifier la position et l'alignement du tiroir au sein du champ de vision. Cette propriété offre une gamme de valeurs énumérées qui fournissent de la flexibilité pour déterminer où le tiroir apparaît par rapport au contenu principal.

Les valeurs énumérées disponibles pour la propriété de placement sont les suivantes :

- **HAUT** : Cette valeur place le tiroir en haut du champ de vision, lui permettant d'occuper la région supérieure.

- **HAUT_CENTRE** : Avec cette valeur, le tiroir est positionné au centre de la partie supérieure du champ de vision. Il est aligné horizontalement au milieu, créant une mise en page équilibrée.

- **BAS** : Lors de l'utilisation de cette valeur, le tiroir est situé en bas du champ de vision, apparaissant sous le contenu principal.

- **BAS_CENTRE** : Cette valeur centre le tiroir horizontalement en bas du champ de vision. Elle offre une composition visuellement équilibrée.

- **GAUCHE** : Le choix de cette valeur positionne le tiroir sur le côté gauche du champ de vision, adjacent au contenu principal.

- **DROITE** : En utilisant cette valeur, le tiroir est placé sur le côté droit du champ de vision, maintenant une proximité étroite avec le contenu principal.

La propriété placement permet aux développeurs de choisir la position la plus appropriée pour le tiroir en fonction des exigences de conception et d'expérience utilisateur spécifiques. Les valeurs énumérées offrent une variété d'options de placement pour s'adapter à différentes mises en page d'interface et hiérarchies visuelles.

En exploitant la propriété placement, les développeurs peuvent créer des interfaces utilisateur intuitives et efficaces. Par exemple, placer le tiroir sur le côté gauche ou droit permet d'accéder rapidement à des fonctionnalités supplémentaires ou à des options de navigation, tandis que les placements en haut ou en bas sont bien adaptés pour des informations contextuelles ou un contenu supplémentaire.

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Style {#styling}

<TableBuilder name="Drawer" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Drawer`, considérez les meilleures pratiques suivantes :

1. **Placement** : Décidez si le tiroir doit glisser depuis la gauche, la droite, le haut ou le bas, en fonction de la mise en page de votre application et des considérations d'expérience utilisateur. Tenez compte des préférences des utilisateurs et des conventions de conception.

2. **Accessibilité** : Accordez une attention particulière à l'accessibilité. Assurez-vous que les utilisateurs peuvent ouvrir et fermer le tiroir en utilisant des commandes clavier et que les lecteurs d'écran peuvent annoncer sa présence et son état. Fournissez des rôles et des étiquettes ARIA si nécessaire.

3. **Gestes de Glissement** : Sur les dispositifs tactiles, prenez en charge les gestes de glissement pour ouvrir et fermer le tiroir. C'est un moyen intuitif pour les utilisateurs d'interagir avec celui-ci.
