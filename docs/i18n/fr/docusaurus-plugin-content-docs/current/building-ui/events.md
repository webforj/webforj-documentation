---
sidebar_position: 7
title: Événements
description: >-
  Listen for component events, read the event payload, configure element events,
  and dispatch your own custom events with the EventDispatcher.
slug: events
sidebar_class_name: new-content
_i18n_hash: 5ceda90a316ff6a1528a686565011f88
---
Les composants, qu'ils soient personnalisés ou faisant partie du framework, prennent en charge la gestion des événements. Vous pouvez ajouter des écouteurs d'événements pour capturer divers types d'événements, tels que les interactions des utilisateurs, les changements d'état ou les événements que vous définissez vous-même. Ces écouteurs vous permettent de déclencher un comportement spécifique en réponse à ce qui se passe dans votre application.

## Ajouter des événements {#adding-events}

Ajoutez un écouteur avec la méthode spécifique à l'événement sur le composant. Chaque composant expose une paire : une méthode `addXxxListener` et, dans la plupart des cas, un alias plus court `on` qui fait la même chose. Un `Button`, par exemple, expose à la fois `addClickListener` et `onClick`.

Vous pouvez passer l'écouteur sous forme de lambda :

```java
Button button = new Button("Enregistrer");
button.onClick(event -> {
  // Gérer le clic
});
```

ou comme une référence de méthode :

```java
button.onClick(this::handleSave);
```

Tous les événements n'ont pas d'alias `on`. Les changements de valeur, par exemple, ne sont ajoutés qu'avec `addValueChangeListener` :

```java
TextField name = new TextField("Nom");
name.addValueChangeListener(event -> {
  String value = event.getValue();
  // Gérer la nouvelle valeur
});
```

## Supprimer un événement {#removing-an-event}

Ajouter un écouteur renvoie un `ListenerRegistration`. Conservez-le pour supprimer l'écouteur plus tard.

```java
ListenerRegistration<ButtonClickEvent> registration =
    button.onClick(event -> {
      // Gérer le clic
    });

// Plus tard, lorsque l'écouteur n'est plus nécessaire
registration.remove();
```

## Utiliser le payload d'événements {#using-event-payload}

Les événements portent un payload avec des informations sur ce qui s'est passé. Lire ce payload dans le gestionnaire vous donne les données pertinentes sans aller faire un aller-retour au client.

Par exemple, un `ModifyEvent` d'un `TextField` porte le texte actuel du champ. Vous pouvez interroger le composant à ce sujet :

```java
TextField field = new TextField("Recherche");
field.onModify(event -> {
  String text = field.getText();
  // Utiliser le texte
});
```

La même valeur est déjà dans l'événement, donc la lire à partir du payload évite de revenir au composant :

```java
field.onModify(event -> {
  String text = event.getText();
  // Utiliser le texte
});
```

Lisez à partir du payload où qu'un événement expose les données dont vous avez besoin. Pour plus d'informations sur l'importance de cela, voir [Interaction Client/Serveur](../architecture/client-server).

## Configurer les événements d'éléments {#configuring-element-events}

Lorsque vous travaillez directement avec un <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink>, ses événements sont configurés avec <JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" code='true'>ElementEventOptions</JavadocLink>. Cela contrôle quelles données l'événement transporte, s'il est déclenché du tout, et à quelle fréquence, le tout évalué sur le client avant que l'événement n'atteigne le serveur.

### Données d'événement {#event-data}

Les données d'événement attachent des valeurs du client à l'événement, de sorte que l'information soit disponible sur le serveur sans une demande supplémentaire. Vous les ajoutez avec `addData()`, en donnant à chaque entrée une clé et une expression JavaScript qui produit la valeur.

Deux variables sont disponibles dans ces expressions : `event`, l'objet d'événement client, et `component`, l'élément auquel l'écouteur est attaché.

```java
ElementEventOptions options = new ElementEventOptions()
    .addData("value", "component.value")
    .addData("key", "event.key");
```

Sur le serveur, chaque valeur est lue à partir de l'événement par sa clé.

### Exécution de JavaScript {#executing-javascript}

`setCode()` exécute un extrait de JavaScript sur le client avant que l'événement ne se déclenche. Cela est utile pour préparer les données d'événement ou réagir sur le client sans un aller-retour au serveur.

```java
ElementEventOptions options = new ElementEventOptions()
    .setCode("event.target.value = event.target.value.trim();");
```

### Filtrage des événements {#filtering-events}

`setFilter()` définit une expression JavaScript qui décide si l'événement se déclenche. Si elle évalue à false, l'événement n'atteint jamais le serveur. Cela est utile lorsque vous ne vous souciez que d'un événement dans certaines conditions, comme lorsqu'une entrée dépasse une longueur minimale.

```java
ElementEventOptions options = new ElementEventOptions()
    .setFilter("event.target.value.length > 2");
```

### Débouncing et throttling {#debouncing-and-throttling}

Le débounce et le throttling limitent la fréquence à laquelle un événement atteint le serveur, ce qui est utile pour des événements rapides comme la saisie ou le défilement.

Le débounce attend que l'activité se stabilise avant de se déclencher. `setDebounce()` prend un délai en millisecondes et une <JavadocLink type="foundation" location="com/webforj/component/element/event/DebouncePhase" code='true'>DebouncePhase</JavadocLink> optionnelle : `LEADING` se déclenche au début de la rafale, `TRAILING` se déclenche après qu'elle se termine, et `BOTH` se déclenche à chaque bord. Lorsque vous omettez la phase, elle est par défaut `TRAILING`.

```java
ElementEventOptions options = new ElementEventOptions()
    .setDebounce(300, DebouncePhase.TRAILING);
```

Le throttling se déclenche à un rythme maximal constant pendant que l'activité continue. `setThrottle()` prend un délai en millisecondes.

```java
ElementEventOptions options = new ElementEventOptions()
    .setThrottle(300);
```

Un événement utilise l'un ou l'autre. La définition d'un debounce annule tout throttle sur les mêmes options, et la définition d'un throttle annule tout debounce.

### Annotations {#annotations}

Les options d'événements d'élément peuvent également être définies avec des annotations, ce qui est une façon plus concise de configurer un écouteur. L'annotation `@EventOptions` contient les entrées de données, ainsi que les paramètres de filtrage, de débounce et de throttle.

```java
@EventOptions(
    data = {@EventData(key = "value", exp = "component.value")},
    debounce = @DebounceSettings(value = 200))
```

Lorsque vous passez également un `ElementEventOptions` au site d'appel, ses données se combinent avec les données de l'annotation, et son code, filtre, débounce et throttle remplacent ceux de l'annotation.

## Dispatcher vos propres événements {#dispatching-your-own-events}

Les événements couverts jusqu'à présent proviennent du composant auquel vous vous abonnez. Un composant que vous écrivez peut publier ses propres événements de la même manière, afin que le code qui l'utilise puisse réagir sans atteindre les internals du composant.

:::tip Quand dispatcher un événement personnalisé
Déclenchez un événement personnalisé lorsque votre composant décide que quelque chose s'est produit, comme un formulaire rapportant une soumission complète ou un éditeur rapportant un enregistrement enregistré. Les événements qui proviennent d'une interaction client sur un `Element` sont configurés avec [options d'événement d'élément](#configuring-element-events) à la place.
:::

Les composants ne viennent pas avec un dispatcher d'événements, donc un composant qui publie ses propres événements maintient son propre <JavadocLink type="foundation" location="com/webforj/dispatcher/EventDispatcher" code='true'>EventDispatcher</JavadocLink> et publie à travers lui.

### Définir l'événement {#defining-the-event}

Définissez l'événement comme une classe étendant `EventObject`. Passez la source, l'objet publiant l'événement, à la superclasse et ajoutez des accesseurs pour les données dont les écouteurs ont besoin.

```java
public class OrderSubmittedEvent extends EventObject {
  private final String orderId;
  private final double total;

  public OrderSubmittedEvent(Object source, String orderId, double total) {
    super(source);
    this.orderId = orderId;
    this.total = total;
  }

  public String getOrderId() {
    return orderId;
  }

  public double getTotal() {
    return total;
  }
}
```

Lire les données à partir de l'événement suit le même raisonnement que [utiliser le payload d'événements](#using-event-payload). Les écouteurs obtiennent ce dont ils ont besoin à partir de l'événement plutôt que de requêter la source par la suite.

### Enregistrer et dispatcher {#registering-and-dispatching}

Créez un dispatcher, enregistrez des écouteurs pour un type d'événement et déclenchez une instance de ce type lorsque l'événement se produit. L'enregistrement renvoie un `ListenerRegistration`, que vous conservez pour supprimer l'écouteur plus tard.

```java
EventDispatcher dispatcher = new EventDispatcher();

ListenerRegistration<OrderSubmittedEvent> registration =
    dispatcher.addListener(OrderSubmittedEvent.class, event -> {
      String id = event.getOrderId();
      // Gérer l'événement
    });

dispatcher.dispatchEvent(new OrderSubmittedEvent(this, "ORD-1001", 49.99));
```

Chaque écouteur enregistré pour ce type d'événement s'exécute lorsque l'événement est déclenché.

Un composant qui publie un événement détient le dispatcher en interne et expose une méthode `onXxx` plutôt que le dispatcher lui-même, de sorte que les appelants s'inscrivent de la même manière qu'ils le feraient pour un événement intégré :

```java
public ListenerRegistration<OrderSubmittedEvent> onSubmit(
    EventListener<OrderSubmittedEvent> listener) {
  return dispatcher.addListener(OrderSubmittedEvent.class, listener);
}
```

### Supprimer des écouteurs {#removing-listeners}

Supprimez un écouteur via son enregistrement, ou en passant l'écouteur au dispatcher :

```java
registration.remove();

//OU

dispatcher.removeListener(OrderSubmittedEvent.class, registration.getListener());
```

Pour supprimer tous les écouteurs enregistrés pour un type d'événement en une seule fois :

```java
dispatcher.removeAllListeners(OrderSubmittedEvent.class);
```

### Éviter les fuites de mémoire {#avoiding-memory-leaks}

Un dispatcher conserve ses écouteurs, et chaque écouteur conserve ce qu'il a capturé. Une lambda ou une classe interne capture implicitement `this` ainsi que toute variable locale qu'elle utilise, donc les objets derrière un écouteur restent accessibles aussi longtemps que le dispatcher les conserve.

Cela devient un problème lorsqu'un écouteur survit à ce qu'il référence. Si une boîte de dialogue enregistre un écouteur qui lit son propre modèle et se ferme sans le retirer, le dispatcher conserve toujours l'écouteur, l'écouteur conserve toujours la boîte de dialogue, et aucun ne peut être collecté par le ramasse-miettes. Dans une application qui crée de nombreuses vues éphémères, les écouteurs retenus s'accumulent de cette manière.

Supprimez un écouteur lorsque :

- L'objet qui l'a enregistré est terminé, comme une boîte de dialogue fermée ou une vue dont on s'est éloigné.
- La souscription était liée à une tâche éphémère ou à un flux unique.

Conservez le `ListenerRegistration` retourné là où vous pouvez y accéder pendant le nettoyage plutôt que d'enregistrer un écouteur que vous ne pouvez pas retirer ensuite. Dans un composant, `onDidDestroy()` est le point de nettoyage.
