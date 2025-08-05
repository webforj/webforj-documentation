---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
_i18n_hash: d517f6169f7ac0798ed073bb27348eb5
---
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Les composants sont des blocs de construction fondamentaux qui peuvent être ajoutés à une fenêtre, offrant des fonctionnalités d'interface utilisateur et un comportement personnalisé. Dans webforJ, la classe `Component` sert de base à tous les composants au sein du moteur.

## Gestion du cycle de vie {#lifecycle-management}

Comprendre le cycle de vie des composants est essentiel pour créer, gérer et utiliser efficacement les composants. Les deux états du cycle de vie suivants possèdent des méthodes permettant de manipuler leur comportement. Ces méthodes ne doivent pas être appelées explicitement par l'utilisateur.

### Hooks de création et de destruction {#create-and-destroy-hooks}

Toutes les classes qui étendent la classe `Component` sont responsables de l'implémentation des fonctionnalités à exécuter lorsque le `Component` est créé et lorsqu'il est détruit. Cela se fait en surchargeant respectivement les méthodes `onCreate()` et `onDestroy()`.

#### `onCreate()` {#oncreate}

La méthode `onCreate()` est appelée lorsque le composant est ajouté à une fenêtre. La création de composants implique la mise en place de leur état et de leur fonctionnalité initiale. C'est ici que vous définissez ce que le composant doit faire lorsqu'il est d'abord créé. Que ce soit pour initialiser des variables, configurer des écouteurs d'événements, ou effectuer toute autre configuration, la méthode `onCreate()` est votre point d'entrée pour personnaliser le comportement du composant.

Ce hook reçoit une instance de fenêtre qui permet l'ajout de composants contenus dans le composant.

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

Détruire des composants est une partie essentielle de la gestion des ressources et de l'assurance d'un nettoyage approprié. Détruire un composant est nécessaire lorsqu'il n'est plus nécessaire ou lorsque vous souhaitez libérer des ressources qui lui sont associées. Cela permet à un développeur d'effectuer des tâches de nettoyage, telles que l'arrêt des minuteries, la libération de mémoire ou la séparation des écouteurs d'événements. Cela permet également d'appeler la méthode `destroy()` sur tous les composants constituants.

:::tip
La méthode `onDestroy()` est responsable de l'appel de la méthode `destroy()` sur tous les composants constituants. Sinon, ces composants existeront toujours dans le DOM, mais ne seront pas accessibles via l'API.
:::

### Attachement asynchrone {#asynchronous-attachment}

La méthode `whenAttached()` permet d'exécuter des fonctionnalités après qu'un composant a été ajouté à une fenêtre. Cette méthode retourne un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet d'exécuter un comportement supplémentaire spécifié de manière asynchrone une fois le composant attaché dans le DOM.

:::tip
Contrairement aux trois méthodes précédentes, `whenAttached()` est censée être appelée explicitement par l'utilisateur.
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* Appel explicite à whenAttached() qui affichera une 
    boîte de message lorsque le bouton est attaché à la Frame. */
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("Je suis attaché!", "Attachement asynchrone");
    });
  
    // La méthode onCreate() est appelée
    window.add(button); 
  }
}
```

### Observateurs {#observers}

Les observateurs jouent un rôle vital dans le suivi des événements du cycle de vie des composants. Des observateurs peuvent être ajoutés et supprimés à l'aide des méthodes `addLifecycleObserver()` et `removeLifecycleObserver()`, et reçoivent des notifications sur des événements tels que la création et la destruction de composants.

En ajoutant des observateurs, vous pouvez agir lorsque un composant est créé ou détruit. Cela est particulièrement utile pour mettre en œuvre une logique personnalisée ou gérer des scénarios spécifiques basés sur les événements des composants.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // logique implémentée à exécuter lorsque le bouton est détruit
  }
});
```

## Propriétés des composants {#component-properties}

### Identifiants des composants {#component-identifiers}

Les ID des composants servent d'identifiants uniques pour les composants, vous permettant d'interagir avec eux et de gérer leur état efficacement.

#### ID du composant côté serveur {#server-side-component-id}

Chaque composant créé à partir de la classe `Component` se voit attribuer automatiquement un identifiant côté serveur. Les ID côté serveur sont essentiels pour le suivi interne et l'identification des composants dans le cadre. Vous pouvez récupérer l'ID du composant côté serveur à l'aide de la méthode `getComponentId()`.

Cela peut être utile dans de nombreuses situations où avoir un identifiant unique, côté serveur, est nécessaire, comme interroger un composant spécifique au sein d'un conteneur.

#### ID du composant côté client {#client-side-component-id}

Les ID côté client permettent à l'utilisateur d'obtenir la représentation client du composant serveur créé en Java. Tous les composants webforJ fournis ont une implémentation de cet ID fourni. Si vous souhaitez accéder et utiliser le composant côté client, vous pouvez exécuter `object.get()` avec l'ID client pour obtenir le composant client désiré.

:::important
Cet ID n'est **pas** l'attribut ID de l'élément dans le DOM.
:::

Dans l'exemple ci-dessous, un événement `onClick` est ajouté à un bouton, qui est ensuite déclenché en appelant la méthode sur le composant client après qu'il a été obtenu à l'aide de la méthode `object.get()`.

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

La classe `Component` vous permet d'inclure des informations supplémentaires au sein du composant à l'aide de la méthode `setUserData()`. Ces informations ne sont accessibles que côté serveur du composant via la méthode `getUserData()`, et ne sont pas envoyées au client.

Cela est très utile lorsqu'il y a des informations qui doivent être incluses avec un composant, et lorsque ces informations doivent être accessibles sans avoir besoin de se rendre chez le client pour les récupérer.
