---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 620635097d0620cc0cd4a639b0d29d97
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Les composants, qu'ils soient personnalisés ou faisant partie du framework, prennent en charge la gestion des événements. Vous pouvez ajouter des auditeurs d'événements pour capturer différents types d'événements, tels que les interactions des utilisateurs, les changements d'état ou d'autres événements personnalisés. Ces auditeurs d'événements peuvent être utilisés pour déclencher des actions ou des comportements spécifiques en réponse aux événements.

Dans l'exemple ci-dessous, un événement est ajouté en utilisant chacun des trois méthodes prises en charge : expressions lambda, classes anonymes et références de méthode.
## Ajout d'événements {#adding-events}

Ajouter un auditeur d'événements est possible en utilisant l'un des motifs suivants où :

- **`myComponent`** est le composant auquel vous souhaitez attacher l'auditeur d'événements.

- **`addEventListener`** est remplacé par la méthode spécifique à l'événement.

- **`EventListener`** est remplacé par le type d'événement écouté.

```java
myComponent.addEventListener(e -> {
  // Exécuté lorsque l'événement se déclenche
});

//OU

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    // Exécuté lorsque l'événement se déclenche
  }
});

//OU

myComponent.addEventListener(this::eventMethod);
```

Des méthodes syntaxiques supplémentaires, ou alias, ont été ajoutées pour permettre un ajout alternatif des événements en utilisant le préfixe `on` suivi de l'événement, tel que :

```java
myComponent.onEvent(e -> {
  // Exécuté lorsque l'événement se déclenche
});
```

## Suppression d'un événement {#removing-an-event}

Lors de l'ajout d'un auditeur d'événements, un objet `ListenerRegistration` sera retourné. Cela peut être utilisé, entre autres, pour supprimer l'événement plus tard.

```java
// Ajout de l'événement
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        // Exécuté lorsque l'événement se déclenche
    });

// Suppression de l'événement
listenerRegistration.remove();
```

## Utilisation de la charge utile d'événement {#using-event-payload}

Il est important de noter que les événements sont souvent accompagnés d'une charge utile, qui contient des informations supplémentaires relatives à l'événement. Vous pouvez utiliser efficacement cette charge utile dans le gestionnaire d'événements pour accéder aux données pertinentes sans effectuer des allers-retours inutiles entre le client et le serveur. Ce faisant, vous pouvez améliorer les performances de votre application.

L'extrait de code suivant interroge le composant pour obtenir des informations qui, pour les besoins de notre démonstration, sont déjà incluses dans la charge utile de l'événement, représentant un code inefficace :

```java
myComponent.addEventListener(e -> {
  // Accéder aux données du composant
  String componentText = e.getComponent().getText();

  //OU si le composant est accessible dans la portée de la fonction
  String componentText = myComponent.getText();

  // Utiliser le componentText pour effectuer d'autres actions.
});
```

Au lieu de cela, en utilisant la charge utile de la méthode, qui pour l'exemple inclut le texte du composant, un aller-retour est évité :

```java
myComponent.addEventListener(e -> {
  // Accéder aux données de la charge utile de l'événement
  String componentText = e.getText();
  
  // Utiliser le componentText pour effectuer d'autres actions.
});
```

Cette approche minimise le besoin d'interroger le composant pour obtenir des informations, car les données sont facilement disponibles dans la charge utile de l'événement. En suivant cette pratique de gestion des événements efficace, vous pouvez améliorer les performances et la réactivité de vos composants. Pour plus d'informations, vous pouvez vous référer à [Interaction Client/Serveur](../architecture/client-server).

### Exemple {#sample}

Ci-dessous se trouve une démonstration montrant l'ajout d'un <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> à un [`Button`](#). Ce [`Button`](#) utilise également des informations provenant de la charge utile de l'événement pour afficher des informations à l'écran.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>
