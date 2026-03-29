---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: c5c07ac4ca0f8d88ea6ef86afd5bb408
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Les composants, qu'ils soient personnalisés ou faisant partie du cadre, prennent en charge la gestion des événements. Vous pouvez ajouter des écouteurs d'événements pour capturer divers types d'événements, tels que les interactions des utilisateurs, les changements d'état, ou d'autres événements personnalisés. Ces écouteurs d'événements peuvent être utilisés pour déclencher des actions ou des comportements spécifiques en réponse aux événements.

Dans l'exemple ci-dessous, un événement est ajouté en utilisant chacune des trois méthodes prises en charge : expressions lambda, classes anonymes et références de méthode.
## Ajouter des événements {#adding-events}

Ajouter un écouteur d'événements est possible en utilisant l'un des modèles suivants où :

- **`myComponent`** est le composant auquel vous souhaitez attacher l'écouteur d'événements.

- **`addEventListener`** est remplacé par la méthode spécifique à l'événement.

- **`EventListener`** est remplacé par le type d'événement pour lequel vous écoutez.

```java
myComponent.addEventListener(e -> {
  //Exécuté lorsque l'événement se déclenche
});

//OU

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //Exécuté lorsque l'événement se déclenche
  }
});

//OU

myComponent.addEventListener(this::eventMethod);
```

Des méthodes supplémentaires, ou alias, ont été ajoutées pour permettre l'ajout alternatif d'événements en utilisant le préfixe `on` suivi de l'événement, tel que :

```java
myComponent.onEvent(e -> {
  //Exécuté lorsque l'événement se déclenche
});
```

## Supprimer un événement {#removing-an-event}

Lors de l'ajout d'un écouteur d'événements, un objet `ListenerRegistration` sera retourné. Cela peut être utilisé, entre autres, pour supprimer l'événement plus tard.

```java
//Ajout de l'événement
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    //Exécuté lorsque l'événement se déclenche
  });

//Suppression de l'événement
listenerRegistration.remove();
```

## Utiliser le payload de l'événement {#using-event-payload}

Il est important de noter que les événements sont souvent accompagnés d'un payload, qui contient des informations supplémentaires liées à l'événement. Vous pouvez utiliser efficacement ce payload dans le gestionnaire d'événements pour accéder à des données pertinentes sans effectuer des allers-retours inutiles entre le client et le serveur. Ce faisant, vous pouvez améliorer la performance de votre application.

Le code suivant interroge le composant pour obtenir des informations qui, pour les besoins de notre démonstration, sont déjà incluses dans le payload de l'événement, représentant un code inefficace :

```java
myComponent.addEventListener(e -> {
  // Accéder aux données du composant
  String componentText = e.getComponent().getText();

  //OU si le composant est accessible dans la portée de la fonction
  String componentText = myComponent.getText();

  // Utiliser le componentText pour effectuer d'autres actions.
});
```

Au lieu de cela, en utilisant le payload de la méthode, qui pour l'exemple inclut le texte du composant, un aller-retour est évité :

```java
myComponent.addEventListener(e -> {
  // Accéder aux données du payload de l'événement
  String componentText = e.getText();
  
  // Utiliser le componentText pour effectuer d'autres actions.
});
```

Cette approche minimise la nécessité d'interroger le composant pour obtenir des informations, car les données sont immédiatement disponibles dans le payload de l'événement. En suivant cette pratique de gestion des événements efficace, vous pouvez améliorer la performance et la réactivité de vos composants. Pour plus d'informations, vous pouvez vous référer à [Interaction client/serveur](../architecture/client-server).

### Exemple {#sample}

Ci-dessous se trouve une démonstration montrant l'ajout d'un <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> à un [`Button`](#). Ce [`Button`](#) utilise également des informations provenant du payload de l'événement pour afficher des informations à l'écran.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>
