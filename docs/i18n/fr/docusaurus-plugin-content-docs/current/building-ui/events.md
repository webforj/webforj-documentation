---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 35a5057106e5fe7f270cbadaff74b924
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Les composants, qu'ils soient personnalisés ou faisant partie du cadre, prennent en charge la gestion des événements. Vous pouvez ajouter des écouteurs d'événements pour capturer divers types d'événements, tels que les interactions utilisateur, les changements d'état ou d'autres événements personnalisés. Ces écouteurs d'événements peuvent être utilisés pour déclencher des actions ou des comportements spécifiques en réponse aux événements.

Dans l'exemple ci-dessous, un événement est ajouté en utilisant chacune des trois méthodes prises en charge : expressions lambda, classes anonymes et références de méthode.
## Ajouter des événements {#adding-events}

Ajouter un écouteur d'événements est possible en utilisant l'un des schémas suivants où :

- **`myComponent`** est le composant auquel vous souhaitez attacher l'écouteur d'événements.

- **`addEventListener`** est remplacé par la méthode spécifique à l'événement.

- **`EventListener`** est remplacé par le type d'événement écouté.

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

Des méthodes supplémentaires de sucre syntaxique, ou des alias, ont été ajoutées pour permettre l'ajout alternatif d'événements en utilisant le préfixe `on` suivi de l'événement, tel que :

```java
myComponent.onEvent(e -> {
  //Exécuté lorsque l'événement se déclenche
});
```

## Supprimer un événement {#removing-an-event}

Lors de l'ajout d'un écouteur d'événements, un objet `ListenerRegistration` sera renvoyé. Cela peut être utilisé, entre autres, pour supprimer l'événement plus tard.

```java
//Ajouter l'événement
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        //Exécuté lorsque l'événement se déclenche
    });

//Supprimer l'événement
listenerRegistration.remove();
```

## Utiliser la charge utile de l'événement {#using-event-payload}

Il est important de noter que les événements sont souvent accompagnés d'une charge utile, qui contient des informations supplémentaires relatives à l'événement. Vous pouvez utiliser efficacement cette charge utile dans le gestionnaire d'événements pour accéder aux données pertinentes sans faire de trajets aller-retour inutiles entre le client et le serveur. Ce faisant, vous pouvez améliorer les performances de votre application.

Le code suivant interroge le composant pour obtenir des informations qui, pour les besoins de notre démonstration, sont déjà incluses dans la charge utile de l'événement, représentant un code inefficace :

```java
myComponent.addEventListener(e -> {
  // Accéder aux données du composant
  String componentText = e.getComponent().getText();

  //OU si le composant est accessible dans le champ de la fonction
  String componentText = myComponent.getText();

  // Utiliser le componentText pour effectuer d'autres actions.
});
```

Au lieu de cela, en utilisant la charge utile de la méthode, qui pour les besoins de l'exemple inclut le texte du composant, un aller-retour est évité :

```java
myComponent.addEventListener(e -> {
  // Accéder aux données de la charge utile de l'événement
  String componentText = e.getText();
  
  // Utiliser le componentText pour effectuer d'autres actions.
});
```

Cette approche minimise le besoin d'interroger le composant pour obtenir des informations, car les données sont directement disponibles dans la charge utile de l'événement. En suivant cette pratique de gestion des événements efficace, vous pouvez améliorer les performances et la réactivité de vos composants. Pour plus d'informations, vous pouvez vous référer à [Interaction Client/Serveur](../architecture/client-server).

### Exemple {#sample}

Voici une démonstration montrant l'ajout d'un <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> à un [`Button`](#). Ce [`Button`](#) utilise également les informations fournies avec la charge utile de l'événement pour afficher des informations à l'écran.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>

<!-- <EventTable base events={['drawerOpen', 'drawerClose']} /> -->
