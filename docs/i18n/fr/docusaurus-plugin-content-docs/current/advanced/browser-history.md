---
title: Browser History
sidebar_position: 20
_i18n_hash: 877c6513ffd8f2b3ed8d4199bc2f5b39
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

La classe `BrowserHistory` de webforJ fournit une API de haut niveau pour interagir avec l'historique du navigateur. L'historique du navigateur permet aux applications web de suivre la navigation de l'utilisateur au sein de l'application. En tirant parti de l'historique du navigateur, les développeurs peuvent activer des fonctionnalités telles que la navigation arrière et avant, la préservation de l'état et la gestion dynamique des URL sans nécessiter de reloading complet de la page.

## Naviguer dans l'historique {#navigating-through-history}

La gestion de l'historique du navigateur est une fonctionnalité essentielle pour la plupart des applications web. L'API `BrowserHistory` permet aux développeurs de contrôler comment les utilisateurs naviguent dans les pages et états de leurs applications, imitant ou modifiant le comportement standard du navigateur.

### Initialiser ou récupérer une instance d'historique {#initializing-or-retrieving-a-history-instance}

Pour utiliser l'API `BrowserHistory`, vous avez deux options principales pour obtenir une instance d'historique :

1) **Créer un nouvel objet d'historique** : Si vous travaillez indépendamment d'un contexte de routage, vous pouvez créer directement une nouvelle instance de la classe `BrowserHistory`.

```java
BrowserHistory history = new BrowserHistory();
```
Cette approche est adaptée aux scénarios où vous devez gérer l'historique explicitement en dehors d'un cadre de routage.

2) **Récupérer l'historique depuis le `Router`** : Si votre application utilise la [solution de routage](../routing/overview) de webforJ, le composant `Router` crée et gère une instance d'`BrowserHistory` partagée. Vous pouvez accéder directement à cette instance depuis le routeur, garantissant une approche cohérente dans la gestion de l'historique à travers votre application.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Cette méthode est recommandée lorsque votre application s'appuie sur le routage, car elle maintient la cohérence dans la gestion de l'historique à travers toutes les vues et actions de navigation.

### Gérer l'historique {#managing-history}
Les méthodes suivantes peuvent être utilisées pour la navigation dans l'historique dans une application webforJ :

- `back()`: Déplace l'historique du navigateur d'un pas en arrière, simulant un utilisateur appuyant sur le bouton de retour dans son navigateur. S'il n'y a plus d'entrées dans la pile d'historique, il reste sur la page actuelle.

  ```java
  history.back();
  ```

- `forward()`: Déplace l'historique du navigateur d'un pas en avant, simulant un utilisateur appuyant sur le bouton de forward dans son navigateur. Cela fonctionne uniquement s'il y a une entrée à l'avance dans la pile d'historique.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigue à un point spécifique dans la pile d'historique en fonction d'un index. Un nombre positif avance, un nombre négatif recule, et zéro recharge la page actuelle. Cette méthode fournit un contrôle plus granulaire par rapport à `back()` et `forward()`.

  ```java
  history.go(-2); // Recule de deux entrées dans la pile d'historique
  ```

- `size()`: Récupère le nombre total d'entrées dans la pile d'historique de session, y compris la page actuellement chargée. Cela peut être utile pour comprendre le chemin de navigation de l'utilisateur ou pour mettre en œuvre des contrôles de navigation personnalisés.

  ```java
  int historySize = history.size();
  System.out.println("Longueur de l'historique : " + historySize);
  ```

- `getLocation()`: Retourne le chemin URL actuel par rapport à l'origine de l'application. Cette méthode aide les développeurs à obtenir le chemin actuel, ce qui est utile pour gérer le routage basé sur l'URL dans les applications à page unique.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Chemin actuel : " + loc.getFullURI()));
  ```

Comprendre comment naviguer efficacement est la pierre angulaire de la construction d'applications dynamiques. Une fois que vous avez compris les fondamentaux de la navigation, il est essentiel de savoir comment accéder et mettre à jour les URL associées à ces événements de navigation.

## Accéder et mettre à jour l'URL {#accessing-and-updating-url}

Un aspect fondamental de la navigation et de la gestion de l'historique du navigateur est la capacité d'accéder et de mettre à jour efficacement le chemin URL actuel. Cela est essentiel dans les applications web modernes, où les changements d'URL correspondent à différentes vues ou états au sein de l'application. L'API `BrowserHistory` offre un moyen simple de récupérer et de manipuler le chemin actuel par rapport à la racine de l'application.

:::tip webforJ `Router`
Consultez l'[`article Router`](../routing/overview) pour en savoir plus sur la gestion complète des URL et des routes.
:::

`getLocation()` récupère le chemin URL actuel par rapport à l'origine de l'application. La méthode `getLocation()` retourne un `Optional<Location>`, vous permettant d'obtenir la partie chemin de l'URL sans le domaine.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Chemin actuel : " + loc.getFullURI()));
```

## Gérer l'état {#managing-state}

`BrowserHistory` vous permet de sauvegarder et de gérer des informations d'état personnalisées à l'aide des méthodes `pushState()` et `replaceState()`. En utilisant les méthodes de gestion d'état, vous pouvez contrôler quelles informations sont stockées dans l'entrée d'historique, ce qui aide à maintenir une expérience utilisateur cohérente lors de la navigation avant et arrière dans votre application. Les méthodes suivantes peuvent être utilisées pour gérer l'état dans votre application webforJ.

- `pushState(Object state, Location location)`: Ajoute une nouvelle entrée dans la pile d'historique. Accepte un objet d'état et un objet `Location` représentant la nouvelle URL.

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Remplace l'entrée d'historique actuelle. Cela ne crée pas une nouvelle entrée dans la pile comme la méthode <!-- valeoff -->au-dessus<!-- valeon -->.

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Récupère l'objet d'état associé à l'entrée d'historique actuelle. Cette méthode retourne un Optional contenant l'objet d'état, qui est désérialisé dans la classe spécifiée.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Page actuelle : " + state.getViewName()));
```

### Écoute des changements d'état {#listening-for-state-changes}
La classe `BrowserHistory` permet d'enregistrer des écouteurs d'événements qui réagissent aux changements d'état de l'historique.

La méthode `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` enregistre un écouteur qui est déclenché lorsque l'état change, par exemple lorsque l'utilisateur clique sur les boutons de retour ou d'avance du navigateur. Cette méthode configure un écouteur pour l'événement `popstate` du navigateur, permettant à votre application de réagir aux actions de l'utilisateur ou aux changements d'état déclenchés par programme.

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("L'état de l'historique a changé en : " + event.getLocation().getFullURI());
});
```

Gérer efficacement l'état vous permet de créer des applications qui réagissent dynamiquement aux actions des utilisateurs. Les utilisateurs peuvent naviguer dans votre application sans perdre le contexte, ce qui rend l'expérience plus fluide et plus intuitive. De plus, la sauvegarde de l'état permet des fonctionnalités avancées telles que la restauration des positions de vue, le maintien des paramètres de filtre ou de tri, et le support des liens profonds—chacune contribuant à une application plus engageante et fiable.
