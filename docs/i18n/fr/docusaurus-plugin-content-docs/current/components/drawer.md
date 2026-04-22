---
title: Drawer
sidebar_position: 35
_i18n_hash: 51577f27568214c5d39e43b7e6ce42d0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Le composant `Drawer` dans webforJ crée un panneau coulissant qui apparaît sur le bord de l'écran, révélant un contenu supplémentaire sans quitter la vue actuelle. Il est couramment utilisé pour la navigation latérale, les menus de filtre, les réglages des utilisateurs ou les notifications compactes qui doivent apparaître temporairement sans perturber l'interface principale.

<!-- INTRO_END -->

## Stacking {#stacking}

Les tiroirs s'empilent automatiquement lorsque plusieurs sont ouverts, ce qui en fait un choix flexible pour les interfaces à espace restreint.

L'exemple ci-dessous montre ce comportement au sein du composant [`AppLayout`](../components/app-layout). Le tiroir de navigation déclenché par le menu hamburger est intégré dans [`AppLayout`](../components/app-layout), tandis que le popup de bienvenue en bas utilise une instance de `Drawer` autonome. Les deux coexistent et s'empilent indépendamment, démontrant comment les tiroirs peuvent être intégrés dans des composants de mise en page ou utilisés comme éléments autonomes.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

Le composant `Drawer` prend en charge l'autofocus, ce qui place automatiquement le focus sur le premier élément focalisable lorsque le `Drawer` s'ouvre. Cela améliore l'ergonomie en attirant l'attention directement sur le premier élément actionnable.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Exemple -->

## Label {#label}

La méthode `setLabel()` peut fournir une description significative du contenu à l'intérieur d'un `Drawer`. Lorsqu'une étiquette est définie, les technologies d'assistance telles que les lecteurs d'écran peuvent l'annoncer, aidant les utilisateurs à comprendre le but du `Drawer` sans voir son contenu visuel.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Gestionnaire de Tâches");
```

:::tip Étiquettes Descriptives
Utilisez des étiquettes concises et descriptives qui reflètent le but du `Drawer`. Évitez les termes génériques comme "Menu" ou "Panneau" lorsque un nom plus spécifique peut être utilisé.
:::

## Size

Pour contrôler la taille d'un `Drawer`, définissez une valeur pour la propriété CSS personnalisée `--dwc-drawer-size`. Cela définit la largeur du `Drawer` pour un placement gauche/droit ou la hauteur pour un placement haut/bas.

Vous pouvez définir la valeur en utilisant n'importe quelle unité CSS valide, comme un pourcentage, des pixels ou vw/vh, en utilisant soit Java ou CSS :

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
}
```

Pour empêcher le `Drawer` de devenir trop grand, utilisez `--dwc-drawer-max-size` à côté :

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
drawer.setStyle("--dwc-drawer-max-size", "800px");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
  --dwc-drawer-max-size: 800px;
}
```

## Placement {#placement}

La méthode `setPlacement()` contrôle où le `Drawer` apparaît dans la fenêtre d'affichage.

Options de placement disponibles :

<!-- vale off -->
- **TOP** : Positionne le tiroir en haut de la fenêtre d'affichage.
- **TOP_CENTER** : Centre horizontalement le tiroir en haut de la fenêtre d'affichage.
- **BOTTOM** : Place le tiroir en bas de la fenêtre d'affichage.
- **BOTTOM_CENTER** : Centre horizontalement le tiroir en bas de la fenêtre d'affichage.
- **LEFT** : Positionne le tiroir sur le bord gauche de la fenêtre d'affichage.
- **RIGHT** : Positionne le tiroir sur le bord droit de la fenêtre d'affichage.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Gestion des événements

Le composant `Drawer` émet des événements de cycle de vie qui peuvent être utilisés pour déclencher la logique de l'application en réponse aux changements de son état ouvert ou fermé.

Événements pris en charge :

- `DrawerOpenEvent` : Déclenché lorsque le tiroir est complètement ouvert.
- `DrawerCloseEvent` : Déclenché lorsque le tiroir est complètement fermé.

Vous pouvez attacher des écouteurs à ces événements pour exécuter une logique lorsque l'état du `Drawer` change.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Gérer l'événement d'ouverture du tiroir
});

drawer.addCloseListener(e -> {
  // Gérer l'événement de fermeture du tiroir
});
```

## Exemple : Sélecteur de contacts

Le composant `Drawer` expose un contenu supplémentaire sans perturber la vue actuelle. Cet exemple place un tiroir au bas centre, contenant une liste de contacts défilable.

Chaque contact affiche un avatar, un nom, un emplacement et un bouton d'action pour un accès rapide aux détails ou à la communication. Cette approche fonctionne bien pour créer des outils compacts comme des sélecteurs de contacts, des panneaux de réglages ou des notifications.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Exemple : Gestionnaire de tâches

Cet exemple utilise un `Drawer` comme gestionnaire de tâches. Vous pouvez ajouter des tâches, les cocher et effacer celles qui sont complètes. Le pied de page du `Drawer` inclut des contrôles de formulaire pour interagir avec la liste des tâches, et le bouton "Ajouter une tâche" [`Button`](../components/button) se désactive si 50 tâches sont atteintes.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
cssURL='/css/drawer/drawer-task-view.css'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />
