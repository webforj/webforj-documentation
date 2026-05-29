---
sidebar_position: 3
title: Component Basics
slug: basics
draft: false
_i18n_hash: 0a9127dc9219a32aeb1eef280b386d77
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Les composants sont des éléments fondamentaux qui peuvent être ajoutés à une fenêtre, fournissant des fonctionnalités d'interface utilisateur et un comportement personnalisé. Dans webforJ, la classe `Component` sert de base pour tous les composants au sein du moteur.

## Gestion du cycle de vie {#lifecycle-management}

Comprendre le cycle de vie d'un composant est essentiel pour créer, gérer et utiliser les composants de manière efficace. Les deux états du cycle de vie suivants ont des méthodes permettant de manipuler leur comportement. Ces méthodes ne doivent pas être appelées explicitement par l'utilisateur.

### Hooks de création et de destruction {#create-and-destroy-hooks}

Toutes les classes qui étendent la classe `Component` sont responsables de la mise en œuvre de la fonctionnalité à exécuter lorsque le `Component` est créé et lorsqu'il est détruit. Cela se fait en redéfinissant les méthodes `onCreate()` et `onDestroy()`, respectivement.

#### `onCreate()` {#oncreate}

La méthode `onCreate()` est appelée lorsque le composant est ajouté à une fenêtre. La création de composants implique de configurer leur état et leur fonctionnalité initiaux. C'est ici que vous définissez ce que le composant doit faire lorsqu'il est créé pour la première fois. Que ce soit l'initialisation des variables, la configuration des écouteurs d'événements ou l'exécution de toute autre configuration, la méthode `onCreate()` est votre point d'entrée pour personnaliser le comportement du composant. 

Ce hook reçoit une instance de fenêtre qui permet d’ajouter des composants contenus dans le composant.

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
La méthode `onCreate()` est l'endroit où le composant et tous les constituants doivent être ajoutés à la fenêtre.
:::

#### `onDestroy()` {#ondestroy}

La destruction de composants est une partie essentielle de la gestion des ressources et de l'assurance d'un nettoyage approprié. La destruction d'un composant est nécessaire lorsqu'il n'est plus nécessaire ou lorsque vous souhaitez libérer les ressources qui y sont associées. Cela permet à un développeur d'effectuer des tâches de nettoyage, telles que l'arrêt des minuteries, la libération de la mémoire ou le détachement des écouteurs d'événements. Cela permet également d'appeler la méthode `destroy()` sur tous les composants constituants.

:::tip
La méthode `onDestroy()` est responsable de l'appel de la méthode `destroy()` sur tous les composants constituants. Sinon, ces composants existeront toujours dans le DOM, mais ne seront pas accessibles via l'API.
:::

### Attachement asynchrone {#asynchronous-attachment}

La méthode `whenAttached()` permet d'exécuter une fonctionnalité après qu'un composant a été ajouté à une fenêtre. Cette méthode renvoie un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet d'exécuter un comportement spécifié de manière asynchrone une fois que le composant est attaché dans le DOM. 

:::tip
Contrairement aux trois méthodes précédentes, `whenAttached()` doit être appelée explicitement par l'utilisateur.
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* Appel explicite à whenAttached() qui affichera une 
    boîte de message lorsque le bouton est attaché à la Frame.*/
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("Je suis attaché !", "Attachement asynchrone");
    });
  
    // La méthode onCreate() est appelée
    window.add(button); 
  }
}
```

### Observateurs {#observers}

Les observateurs jouent un rôle vital dans le suivi des événements du cycle de vie des composants. Les observateurs peuvent être ajoutés et supprimés à l'aide des méthodes `addLifecycleObserver()` et `removeLifecycleObserver()`, et reçoivent des notifications sur des événements tels que la création et la destruction de composants.

En ajoutant des observateurs, vous pouvez agir lorsqu'un composant est créé ou détruit. Ceci est particulièrement utile pour mettre en œuvre une logique personnalisée ou gérer des scénarios spécifiques en fonction des événements liés aux composants.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // logique mise en œuvre à exécuter lorsque le bouton est détruit
  }
});
```

## Propriétés du composant {#component-properties}

### Identifiants de composants {#component-identifiers}

Les ID de composants servent d'identifiants uniques pour les composants, vous permettant d'interagir avec eux et de gérer leur état de manière efficace.

#### ID de composant côté serveur {#server-side-component-id}

Chaque composant créé à partir de la classe `Component` se voit automatiquement attribuer un identifiant côté serveur. Les ID côté serveur sont essentiels pour le suivi et l'identification internes des composants au sein du cadre. Vous pouvez récupérer l'ID de composant côté serveur à l'aide de la méthode `getComponentId()`.

Cela peut être utile dans de nombreuses situations où avoir un identifiant unique, côté serveur, est nécessaire, comme interroger un composant spécifique dans un conteneur.

#### ID de composant côté client {#client-side-component-id}

Les ID côté client permettent à l'utilisateur d'obtenir la représentation client du composant serveur créé en Java. Tous les composants webforJ fournis ont une mise en œuvre de cet ID fournie. Si vous souhaitez accéder et utiliser le composant côté client, vous pouvez exécuter `object.get()` avec l'ID client pour obtenir le composant client souhaité.

:::important
Cet ID **n'est pas** l'attribut ID de l'élément dans le DOM.
:::

Dans l'exemple ci-dessous, un événement `onClick` est ajouté à un bouton, qui est ensuite déclenché en appelant la méthode sur le composant client après l'avoir obtenu en utilisant la méthode `object.get()`.

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("Cliquez-moi");
  btn.onClick(e -> {
    showMessageDialog("Le bouton a été cliqué", "Un événement s'est produit");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### Données utilisateur {#user-data}

La classe `Component` vous permet d'inclure des informations supplémentaires au sein du composant à l'aide de la méthode `setUserData()`. Ces informations ne sont accessibles que sur le côté serveur du composant via la méthode `getUserData()`, et ne sont pas envoyées au client. 

Cela est très utile lorsque des informations doivent être incluses avec un composant, et lorsque ces informations doivent être accessibles sans faire un voyage vers le client pour les récupérer.
