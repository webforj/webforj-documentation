---
title: Drawer
sidebar_position: 35
_i18n_hash: d0c9ff09e591673c99918aa6875af28a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Le composant `Drawer` dans webforJ crée un panneau coulissant qui apparaît depuis le bord de l'écran, révélant du contenu supplémentaire sans quitter la vue actuelle. Il est couramment utilisé pour la navigation latérale, les menus de filtre, les paramètres utilisateur ou les notifications compactes qui doivent apparaître temporairement sans perturber l'interface principale.

<!-- INTRO_END -->

## Empilement {#stacking}

Les tiroirs s'empilent automatiquement lorsque plusieurs sont ouverts, ce qui en fait un choix flexible pour les interfaces aux espaces contraints.

L'exemple ci-dessous montre ce comportement au sein du composant [`AppLayout`](../components/app-layout). Le tiroir de navigation déclenché par le menu hamburger est intégré au [`AppLayout`](../components/app-layout), tandis que la popup de bienvenue en bas utilise une instance de `Drawer` autonome. Les deux coexistent et s'empilent indépendamment, démontrant comment les tiroirs peuvent être intégrés dans des composants de mise en page ou utilisés comme éléments autonomes.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

Le composant `Drawer` prend en charge l'autofocus, qui place automatiquement le focus sur le premier élément pouvant recevoir le focus lorsque le `Drawer` s'ouvre. Cela améliore l'utilisabilité en attirant directement l'attention sur le premier élément actionnable.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Exemple -->

## Étiquette {#label}

La méthode `setLabel()` peut fournir une description significative du contenu à l'intérieur d'un `Drawer`. Lorsqu'une étiquette est définie, les technologies d'assistance comme les lecteurs d'écran peuvent l'annoncer, aidant les utilisateurs à comprendre l'objectif du `Drawer` sans voir son contenu visuel.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Gestionnaire de tâches");
```

:::tip Étiquettes descriptives
Utilisez des étiquettes concises et descriptives qui reflètent l'objectif du `Drawer`. Évitez les termes génériques comme « Menu » ou « Panneau » lorsqu'un nom plus spécifique peut être utilisé.
:::

## Taille

Pour contrôler la taille d'un `Drawer`, définissez une valeur pour la propriété CSS personnalisée `--dwc-drawer-size`. Cela définit la largeur du `Drawer` pour un placement à gauche/droite ou la hauteur pour un placement en haut/en bas.

Vous pouvez définir la valeur en utilisant n'importe quelle unité CSS valide, comme un pourcentage, des pixels ou des vw/vh, en utilisant soit Java soit CSS :

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

Pour empêcher le `Drawer` de devenir trop grand, utilisez `--dwc-drawer-max-size` avec lui :

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
- **HAUT** : Positionne le tiroir en haut de la fenêtre d'affichage.
- **HAUT_CENTRE** : Aligne le tiroir horizontalement centré en haut de la fenêtre d'affichage.
- **BAS** : Place le tiroir en bas de la fenêtre d'affichage.
- **BAS_CENTRE** : Recentre horizontalement le tiroir en bas de la fenêtre d'affichage.
- **GAUCHE** : Positionne le tiroir le long du bord gauche de la fenêtre d'affichage.
- **DROITE** : Positionne le tiroir le long du bord droit de la fenêtre d'affichage.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Gestion des événements

Le composant `Drawer` émet des événements de cycle de vie qui peuvent être utilisés pour déclencher une logique d'application en réponse à des changements dans son état ouvert ou fermé.

Événements pris en charge :

- `DrawerOpenEvent` : Déclenché lorsque le tiroir est complètement ouvert.
- `DrawerCloseEvent` : Déclenché lorsque le tiroir est complètement fermé.

Vous pouvez attacher des écouteurs à ces événements pour exécuter une logique lorsque l'état du `Drawer` change.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Gérer l'événement de tiroir ouvert
});

drawer.addCloseListener(e -> {
  // Gérer l'événement de tiroir fermé
});
```

## Exemple : Sélecteur de contacts

Le composant `Drawer` expose un contenu supplémentaire sans perturber la vue actuelle. Cet exemple place un tiroir au centre en bas, contenant une liste de contacts défilable.

Chaque contact affiche un avatar, un nom, un emplacement et un bouton d'action pour un accès rapide aux détails ou à la communication. Cette approche fonctionne bien pour construire des outils compacts comme des sélecteurs de contacts, des panneaux de paramètres ou des notifications.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Exemple : Gestionnaire de tâches

Cet exemple utilise un `Drawer` comme gestionnaire de tâches. Vous pouvez ajouter des tâches, les cocher et effacer celles complétées. Le pied de page du `Drawer` inclut des contrôles de formulaire pour interagir avec la liste des tâches, et le bouton « Ajouter tâche » [`Button`](../components/button) se désactive si 50 tâches sont atteintes.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Style {#styling}

<TableBuilder name="Drawer" />
