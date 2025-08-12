---
title: Drawer
sidebar_position: 35
_i18n_hash: 73da264dca1e3f8cfd58b697e3e9d0dc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Le tiroir est un conteneur qui glisse dans la fenêtre pour exposer des options et des informations supplémentaires. Plusieurs tiroirs peuvent être créés dans une application, et ils seront empilés les uns au-dessus des autres.

Le composant Drawer peut être utilisé dans de nombreuses situations différentes, comme en fournissant un menu de navigation qui peut être activé, un panneau qui affiche des informations complémentaires ou contextuelles, ou pour optimiser l'utilisation sur un appareil mobile. L'exemple suivant montrera une application mobile qui utilise le composant webforJ AppLayout et affiche un tiroir "Welcome Popup" en bas lors de son premier chargement. De plus, un composant navigational Drawer peut être activé dans l'application en cliquant sur le menu hamburger.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Usages {#usages}

1. **Menu de Navigation** : Une utilisation courante d'un composant tiroir est en tant que menu de navigation. Il fournit un moyen efficace en termes d'espace pour afficher des liens vers diverses sections ou pages de votre application, en particulier dans des mises en page mobiles ou réactives. Les utilisateurs peuvent ouvrir et fermer le tiroir pour accéder aux options de navigation sans encombrer la zone de contenu principale.

2. **Filtre et Barre Latérale** : Un tiroir peut être utilisé comme un filtre ou une barre latérale dans des applications qui affichent une liste d'éléments. Les utilisateurs peuvent développer le tiroir pour révéler des options de filtre, des contrôles de tri ou des informations supplémentaires liées aux éléments de la liste. Cela garde le contenu principal concentré sur la liste tout en fournissant des fonctionnalités avancées de manière accessible.

3. **Profil Utilisateur ou Paramètres** : Vous pouvez utiliser un tiroir pour afficher les informations du profil utilisateur ou les paramètres de l'application. Cela garde ces informations facilement accessibles mais cachées lorsque non nécessaires, maintenant une interface propre et non encombrée. Les utilisateurs peuvent ouvrir le tiroir pour mettre à jour leurs profils ou ajuster les paramètres.

4. **Notifications** : Pour les applications avec des notifications ou des alertes, un tiroir peut glisser pour afficher de nouveaux messages ou mises à jour. Les utilisateurs peuvent rapidement vérifier et dismiss notifications sans quitter leur vue actuelle.

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## Personnalisation {#customization}

Diverses propriétés existent qui permettent de personnaliser différents attributs du composant Drawer. Cette section décrit ces propriétés avec des exemples de leur modification.

## Autofocus {#autofocus}

La propriété Auto-Focus est conçue pour améliorer l'accessibilité et l'utilisabilité en mettant automatiquement le focus sur le premier élément dans un tiroir lorsqu'il est ouvert. Cette fonctionnalité élimine le besoin pour les utilisateurs de naviguer manuellement vers l'élément souhaité, ce qui permet d'économiser du temps et des efforts.

Lorsque le tiroir est déclenché pour s'ouvrir, soit par un événement, par défaut ou par toute autre interaction, le focus de l'utilisateur est dirigé vers le premier élément dans le tiroir. Ce premier élément pourrait être un bouton, un lien, une option de menu, ou tout autre élément pouvant recevoir le focus.

:::tip
En mettant automatiquement le focus sur le premier élément, le développeur s'assure que les utilisateurs peuvent immédiatement interagir avec l'option la plus pertinente ou fréquemment utilisée sans avoir à naviguer ou faire défiler tout le tiroir. Ce comportement simplifie l'expérience utilisateur et favorise une navigation efficace dans l'UI.
:::

Cette propriété peut également être particulièrement bénéfique pour les personnes qui comptent sur la navigation au clavier ou sur des technologies d'assistance telles que les lecteurs d'écran. Elle fournit un point de départ clair à l'intérieur du tiroir et permet aux utilisateurs d'accéder à la fonctionnalité souhaitée sans saisie manuelle inutile.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

## Étiquette {#label}

La propriété Drawer Label est une fonctionnalité conçue pour améliorer l'accessibilité et fournir un contexte descriptif pour un tiroir dans une interface utilisateur. Cette propriété permet aux développeurs d'assigner une étiquette à un tiroir, principalement à des fins d'accessibilité, garantissant que les lecteurs d'écran et autres technologies d'assistance peuvent transmettre avec précision le but et le contenu du tiroir aux utilisateurs.

Lorsqu'elle est utilisée, la propriété Drawer Label devient une partie intégrante de l'infrastructure d'accessibilité du tiroir. Elle permet aux utilisateurs qui comptent sur des technologies d'assistance de comprendre la fonction du tiroir et de naviguer dans l'interface de manière plus efficace.

En fournissant une étiquette pour le tiroir, les développeurs s'assurent que les lecteurs d'écran annoncent le but du tiroir aux utilisateurs malvoyants. Cette information permet aux individus de prendre des décisions éclairées sur l'interaction avec le tiroir, car ils peuvent comprendre son contenu et sa pertinence dans l'interface utilisateur plus large.

La propriété d'étiquette peut être personnalisée pour s'adapter au contexte spécifique et aux exigences de conception de l'application. Les développeurs ont la flexibilité de fournir des étiquettes concises et descriptives qui représentent avec précision le contenu ou la fonctionnalité du tiroir.

## Placement {#placement}

La propriété placement du composant UI Drawer permet aux développeurs de spécifier la position et l'alignement du tiroir dans la fenêtre. Cette propriété offre une gamme de valeurs enum qui fournissent de la flexibilité dans la détermination de l'emplacement du tiroir par rapport au contenu principal.

Les valeurs enum disponibles pour la propriété placement sont les suivantes :

- **HAUT** : Cette valeur place le tiroir en haut de la fenêtre, lui permettant d'occuper la région supérieure.

- **HAUT_CENTRE** : Avec cette valeur, le tiroir est positionné au centre de la partie supérieure de la fenêtre. Il est aligné horizontalement au milieu, créant une mise en page équilibrée.

- **BAS** : Lors de l'utilisation de cette valeur, le tiroir est situé en bas de la fenêtre, apparaissant en dessous du contenu principal.

- **BAS_CENTRE** : Cette valeur centre le tiroir horizontalement en bas de la fenêtre. Elle fournit une composition visuellement équilibrée.

- **GAUCHE** : Le choix de cette valeur place le tiroir sur le côté gauche de la fenêtre, adjacent au contenu principal.

- **DROITE** : En utilisant cette valeur, le tiroir est placé sur le côté droit de la fenêtre, maintenant une proximité avec le contenu principal.

La propriété placement permet aux développeurs de choisir la position la plus appropriée pour le tiroir en fonction des exigences spécifiques de conception et d'expérience utilisateur. Les valeurs enum offrent une variété d'options de placement pour accueillir différentes mises en page d'interface et hiérarchies visuelles.

En tirant parti de la propriété placement, les développeurs peuvent créer des interfaces utilisateur intuitives et efficaces. Par exemple, placer le tiroir sur le côté gauche ou droit permet un accès rapide à des fonctionnalités ou options de navigation supplémentaires, tandis que les placements en haut ou en bas sont bien adaptés aux informations contextuelles ou contenu supplémentaire.

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Styles {#styling}

<TableBuilder name="Drawer" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Drawer`, considérez les meilleures pratiques suivantes :

1. **Placement** : Déterminez si le tiroir doit glisser par la gauche, la droite, le haut ou le bas, en fonction de la mise en page de votre application et des considérations d'expérience utilisateur. Prenez en compte les préférences des utilisateurs et les conventions de design.

2. **Accessibilité** : Portez une attention particulière à l'accessibilité. Assurez-vous que les utilisateurs peuvent ouvrir et fermer le tiroir à l'aide de commandes clavier et que les lecteurs d'écran peuvent annoncer sa présence et son état. Fournissez des rôles et des étiquettes ARIA si nécessaire.

3. **Gestes de balayage** : Sur les dispositifs à écran tactile, prenez en charge les gestes de balayage pour ouvrir et fermer le tiroir. C'est un moyen intuitif pour les utilisateurs d'interagir avec lui.
