---
title: Toast
sidebar_position: 140
_i18n_hash: 7350867dde3a34f2c5fe2e40c4d745c4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Une notification `Toast` est une notification contextuelle subtile et non intrusive conçue pour fournir aux utilisateurs des retours d'information et des informations en temps réel. Ces notifications sont généralement utilisées pour informer les utilisateurs sur des opérations telles que des actions réussies, des avertissements ou des erreurs sans interrompre leur flux de travail. Les notifications `Toast` disparaissent généralement après un certain temps et ne nécessitent pas de réponse de l'utilisateur.

Avec le composant `Toast` de webforJ, vous pouvez facilement mettre en œuvre ces notifications pour améliorer l'expérience utilisateur en fournissant des informations pertinentes de manière familière, non intrusive et fluide.

## Basics {#basics}

webforJ offre un moyen rapide et facile de créer un composant `Toast` en une seule ligne de code avec la méthode `Toast.show()`, qui crée un composant `Toast`, l'ajoute au `Frame` et l'affiche. Vous pouvez passer des paramètres à la méthode `show` pour configurer le `Toast` affiché :

```java
Toast.show("L'opération a été réalisée avec succès !", Theme.SUCCESS);
```

Si vous souhaitez un contrôle plus précis sur le composant, vous pouvez également créer un `Toast` avec un constructeur standard et utiliser la méthode `open()` pour l'afficher.

```java
Toast toast = new Toast("L'opération a été réalisée avec succès !", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
height='200px'
/>

:::info Comportement par défaut
Contrairement à d'autres composants, un `Toast` n'a pas besoin d'être ajouté explicitement à un conteneur comme un `Frame`. Lorsque vous appelez la méthode `open()`, le `Toast` est automatiquement attaché au premier `Frame` de l'application.
:::

Les toasts sont polyvalents et fournissent des notifications subtiles pour des retours d'informations en temps réel. Par exemple :

- **Retour d'information en temps réel** pour des actions comme les soumissions de formulaires, les sauvegardes de données ou les erreurs.
- **Thèmes personnalisables** pour différencier entre les messages de succès, d'erreur, d'avertissement ou d'information.
- **Options de placement flexibles** pour montrer des notifications dans différentes zones de l'écran sans interrompre le flux de travail de l'utilisateur.

## Duration {#duration}

Vous pouvez configurer les notifications `Toast` pour disparaître après une durée définie ou persister à l'écran jusqu'à ce qu'elles soient dismissées, selon vos besoins. Vous pouvez personnaliser la durée avec la méthode `setDuration()`, ou simplement fournir un paramètre de durée au constructeur ou à la méthode `show()`.

:::info Durée par défaut
Par défaut, un `Toast` se ferme automatiquement après 5000 millisecondes.
:::

```java
Toast toast = new Toast("Notification d'exemple");
toast.setDuration(10000);
toast.open();
```

### Toasts persistants {#persistent-toasts}

Vous pouvez créer un `Toast` persistant en définissant une durée négative. Les notifications `Toast` persistantes ne se ferment pas automatiquement, ce qui peut être utile pour des alertes critiques ou dans des cas où une interaction ou une reconnaissance est requise de la part de l'utilisateur.

:::caution
Faites attention avec les notifications `Toast` persistantes, et assurez-vous de fournir un moyen pour l'utilisateur de dismiss la notification. Utilisez la méthode `close()` pour cacher le `Toast` une fois que l'utilisateur l'a reconnu ou a effectué toute interaction requise.
:::

```java
Toast toast = new Toast("L'opération a été réalisée avec succès !", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Placement {#placement}

Avec le composant `Toast` de webforJ, vous pouvez choisir où la notification apparaît sur l'écran pour convenir à la conception et aux exigences d'utilisabilité de votre application. Par défaut, les notifications `Toast` apparaissent en bas au centre de l'écran.

Vous pouvez définir le `placement` d'une notification `Toast` avec la méthode `setPlacement` en utilisant l'énumération `Toast.Placement` avec l'une des valeurs suivantes :

- **BOTTOM** : Place la notification en bas au centre de l'écran.
- **BOTTOM_LEFT** : Place la notification en bas à gauche de l'écran.
- **BOTTOM_RIGHT** : Place la notification en bas à droite de l'écran.
- **TOP** : Place la notification en haut au centre de l'écran.
- **TOP_LEFT** : Place la notification en haut à gauche de l'écran.
- **TOP_RIGHT** : Place la notification en haut à droite de l'écran.

Ces options vous permettent de contrôler le placement de la notification `Toast` en fonction des besoins de conception et d'utilisabilité de votre application.

```java
Toast toast = new Toast("Notification d'exemple");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='500px'
/>

En personnalisant le placement de vos notifications `Toast`, vous pouvez vous assurer que les utilisateurs reçoivent des informations d'une manière appropriée pour une application donnée, une mise en page d'écran et un contexte quelconque.

## Stacking {#stacking}

Le composant `Toast` peut afficher plusieurs notifications simultanément, les empilant verticalement en fonction de leur placement. Les nouvelles notifications apparaissent plus près du bord de placement, repoussant les anciennes notifications plus loin. Cela garantit que les utilisateurs ne manquent pas d'informations importantes, même lorsque beaucoup de choses se passent.

## Actions et interactivité {#actions-and-interactivity}

Bien que les notifications `Toast` ne nécessitent pas d'interaction de l'utilisateur par défaut, webforJ vous permet d'ajouter des boutons ou d'autres éléments interactifs pour les rendre plus utiles que de simples notifications.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

En ajoutant ce type d'interactivité, vous pouvez donner aux utilisateurs la possibilité de gérer des tâches et d'effectuer des actions sans quitter leur écran actuel, transformant une notification `Toast` en un canal précieux d'interaction et d'engagement.

## Styling {#styling}

Vous pouvez styliser les notifications `Toast` avec des thèmes tout comme d'autres composants webforJ, fournissant aux utilisateurs un contexte précieux sur le type d'informations affichées et créant un style cohérent dans toute votre application. Vous pouvez soit définir le thème lors de la création du `Toast`, soit utiliser la méthode `setTheme()`.

```java
Toast toast = new Toast("Notification d'exemple", Theme.INFO);
```

```java
Toast toast = new Toast("Notification d'exemple");
toast.setTheme(Theme.INFO);
```

### Thèmes personnalisés {#custom-themes}

En plus d'utiliser des thèmes intégrés, vous pouvez créer vos propres thèmes personnalisés pour les notifications `Toast`. Cela permet une expérience utilisateur plus personnalisée et de marque, vous donnant un contrôle total sur le style général du `Toast`.

Pour ajouter un thème personnalisé à un `Toast`, vous pouvez définir des variables CSS personnalisées, qui modifient l'apparence du composant. L'exemple suivant démontre comment créer un `Toast` avec un thème personnalisé en utilisant webforJ.

:::info Ciblage `Toast`
Puisque le `Toast` n'est pas situé à un endroit spécifique dans le DOM, vous pouvez le cibler à l'aide de variables CSS. Ces variables facilitent l'application de styles personnalisés cohérents à toutes les notifications `Toast`.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
