---
title: Toast
sidebar_position: 140
_i18n_hash: 0ac4df1a045e2706f2e9309327ba4683
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Un `Toast` est une petite notification temporaire qui apparaît pour donner aux utilisateurs un retour sur une action ou un événement. Les toasts affichent des messages tels que des confirmations de succès, des avertissements ou des erreurs sans interrompre le flux de travail actuel, et disparaissent automatiquement après une durée déterminée.

<!-- INTRO_END -->

## Bases {#basics}

webforJ fournit un moyen rapide et facile de créer un composant `Toast` en une seule ligne de code avec la méthode `Toast.show()`, qui crée un composant `Toast`, l'ajoute au `Frame` et l'affiche. Vous pouvez passer des paramètres à la méthode `show` pour configurer le `Toast` affiché :

```java
Toast.show("Opération terminée avec succès !", Theme.SUCCESS);
```

Si vous souhaitez un contrôle plus précis sur le composant, vous pouvez également créer un `Toast` avec un constructeur standard et utiliser la méthode `open()` pour l'afficher.

```java
Toast toast = new Toast("Opération terminée avec succès !", 3000, Theme.SUCCESS, Placement.TOP);
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

Les toasts sont polyvalents et fournissent des notifications subtiles pour un retour en temps réel. Par exemple :

- **Retour en temps réel** pour des actions comme la soumission de formulaires, l'enregistrement de données ou des erreurs.
- **Thèmes personnalisables** pour différencier les messages de succès, d'erreur, d'avertissement ou d'information.
- **Options de placement** flexibles pour afficher les notifications dans différentes zones de l'écran sans interrompre le flux de travail de l'utilisateur.

## Durée {#duration}

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

Vous pouvez créer un `Toast` persistant en définissant une durée négative. Les notifications `Toast` persistantes ne se ferment pas automatiquement, ce qui peut être utile pour les alertes critiques ou dans les cas où une interaction ou une reconnaissance de l'utilisateur est requise.

:::caution
Faites attention avec les notifications `Toast` persistantes et assurez-vous de fournir un moyen pour l'utilisateur de dismiss la notification. Utilisez la méthode `close()` pour cacher le `Toast` une fois que l'utilisateur l'a reconnu ou a complété toute interaction requise.
:::

```java
Toast toast = new Toast("Opération terminée avec succès !", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Placement {#placement}

Avec le composant `Toast` de webforJ, vous pouvez choisir où la notification apparaît sur l'écran pour répondre aux exigences de conception et d'utilisabilité de votre application. Par défaut, les notifications `Toast` apparaissent en bas au centre de l'écran.

Vous pouvez définir le `placement` d'une notification Toast avec la méthode `setPlacement` en utilisant l'énumération `Toast.Placement` avec l'une des valeurs suivantes :

- **BOTTOM** : Place la notification au centre en bas de l'écran.
- **BOTTOM_LEFT** : Place la notification dans le coin inférieur gauche de l'écran.
- **BOTTOM_RIGHT** : Place la notification dans le coin inférieur droit de l'écran.
- **TOP** : Place la notification au centre en haut de l'écran.
- **TOP_LEFT** : Place la notification dans le coin supérieur gauche de l'écran.
- **TOP_RIGHT** : Place la notification dans le coin supérieur droit de l'écran.

Ces options vous permettent de contrôler le placement de la notification `Toast` en fonction de la conception et des besoins d'utilisabilité de votre application.

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

En personnalisant le placement de vos notifications `Toast`, vous pouvez vous assurer que les utilisateurs reçoivent les informations de manière appropriée pour une application donnée, une mise en page d'écran et un contexte.

## Empilement {#stacking}

Le composant `Toast` peut afficher plusieurs notifications simultanément, les empilant verticalement en fonction de leur placement. Les notifications les plus récentes apparaissent plus près du bord de placement, poussant les notifications plus anciennes plus loin. Cela garantit que les utilisateurs ne manquent pas d'informations importantes, même lorsqu'il se passe beaucoup de choses.

## Actions et interactivité {#actions-and-interactivity}

Bien que les notifications `Toast` ne nécessitent pas d'interaction utilisateur par défaut, webforJ vous permet d'ajouter des boutons ou d'autres éléments interactifs pour les rendre plus utiles que de simples notifications.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

En ajoutant ce type d'interactivité, vous pouvez donner aux utilisateurs la possibilité de gérer des tâches et d'effectuer des actions sans quitter leur écran actuel, transformant une notification `Toast` en un canal d'interaction et d'engagement précieux.

## Style {#styling}

Vous pouvez styliser les notifications `Toast` avec des thèmes tout comme d'autres composants webforJ, fournissant aux utilisateurs un contexte précieux sur le type d'informations affichées, et créant un style cohérent dans toute votre application. Vous pouvez soit définir le thème lors de la création du `Toast`, soit utiliser la méthode `setTheme()`.

```java
Toast toast = new Toast("Notification d'exemple", Theme.INFO);
```

```java
Toast toast = new Toast("Notification d'exemple");
toast.setTheme(Theme.INFO);
```

### Thèmes personnalisés {#custom-themes}

En plus d'utiliser des thèmes intégrés, vous pouvez créer vos propres thèmes personnalisés pour les notifications `Toast`. Cela permet une expérience utilisateur plus personnalisée et de marque, vous donnant un contrôle total sur le style global du `Toast`.

Pour ajouter un thème personnalisé à un `Toast`, vous pouvez définir des variables CSS personnalisées, qui modifient l'apparence du composant. L'exemple suivant montre comment créer un `Toast` avec un thème personnalisé en utilisant webforJ.

:::info Ciblage de `Toast`
Puisque le `Toast` n'est pas situé dans une position spécifique dans le DOM, vous pouvez le cibler en utilisant des variables CSS. Ces variables facilitent l'application de styles personnalisés cohérents dans toutes les notifications Toast.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
