---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: ae7a34d844eee10906ce2230f95a05cc
---
La section suivante discute des diverses qualités de performance et des meilleures pratiques pour webforJ, ainsi que des détails d'implémentation pour le framework.

Lors de la création d'une application dans webforJ, le client et le serveur travaillent ensemble pour manipuler les données entre le client et le serveur, qui peuvent être classées en grandes catégories :

## 1. Serveur vers client {#1-server-to-client}

Les méthodes webforJ telles que `setText()` sont incluses dans cette catégorie. L'application webforJ en cours d'exécution sur le serveur envoie des données au client sans attendre de réponse. webforJ optimise automatiquement les lots d'opérations dans cette catégorie pour améliorer la performance.

## 2. Client vers serveur {#2-client-to-server}

Cette catégorie couvre le trafic d'événements, comme la méthode `Button.onClick()`. En grande partie, le client envoie des événements au serveur sans attendre de réponse. L'objet d'événement contient généralement des paramètres supplémentaires liés à l'événement, tels que le code de hachage. Comme cette information est transmise au serveur dans le cadre de l'acte de livraison de l'événement, elle est immédiatement disponible pour le programme dès que l'événement est reçu.

## 3. Serveur vers client vers serveur (aller-retour) {#3-server-to-client-to-server-round-trip}

Les aller-retours sont effectués lorsque l'application interroge le client pour obtenir des informations dynamiques qui ne peuvent pas être mises en cache sur le serveur. Les méthodes telles que `Label.getText()` et `Checkbox.isChecked()` relèvent de cette catégorie. Lorsqu'une application webforJ exécute une ligne comme `String title = myLabel.getText()`, elle s'arrête complètement pendant que le serveur envoie cette demande au client, puis attend que le client renvoie la réponse.

Si l'application envoie plusieurs messages au client qui ne nécessitent pas de réponse (catégorie 1), suivis d'un seul message qui nécessite un aller-retour (catégorie 3), l'application doit attendre que le client traite tous les messages en attente, puis réponde au message final qui nécessite une réponse. Dans certains cas, cela peut ajouter un délai. Si cet aller-retour n'avait pas été introduit, le client aurait pu continuer à travailler en traitant ces messages en retard pendant que l'application exécutée sur le serveur passait à de nouveaux travaux.

## Améliorer la performance {#improve-performance}

Il est possible d'améliorer considérablement la réactivité en évitant autant que possible les aller-retours de la troisième catégorie. Par exemple, changer la fonctionnalité onSelect de ComboBox de ceci :

```java
private void comboBoxSelect(ListSelectEvent ev){
    ComboBox component = (ComboBox) ev.getComponent();

    // Va au client
    int selected = component.getSelectedIndex();
}
```

à ceci :

```java
private void comboBoxSelect(ListSelectEvent ev){
    // Obtient la valeur de l'événement
    int selected = ev.getSelectedIndex();
}
```

Dans le premier extrait, l'appel à `ComboBox.getSelectedIndex()` sur le composant force un aller-retour au client, introduisant un délai. Dans la seconde version, l'utilisation de la méthode `ListSelectEvent.getSelectedIndex()` de l'événement récupère la valeur qui a été livrée au serveur dans le cadre de l'événement original.

## Mise en cache {#caching}

webforJ optimise encore la performance en utilisant la mise en cache. En général, deux types de données existent dans ce contexte : des données que l'utilisateur peut directement modifier, et des données qui ne peuvent pas être modifiées par l'utilisateur. Dans le premier cas, lors de la récupération des informations avec lesquelles les utilisateurs vont interagir, il est nécessaire d'interroger le serveur pour obtenir ces informations.

Cependant, les informations qui ne peuvent pas être modifiées par l'utilisateur peuvent être mises en cache pour éviter des impacts supplémentaires sur la performance. Cela garantit qu'un aller-retour n'a pas besoin d'être effectué inutilement, fournissant une expérience utilisateur plus efficace. webforJ optimise les applications de cette manière pour assurer une performance optimale.

## Temps de chargement {#loading-time}

Lorsque l'utilisateur lance une application webforJ, elle charge juste un petit morceau (environ 2,5 kB gzip) de JavaScript pour démarrer la session. Ensuite, elle télécharge dynamiquement des messages individuels ou des morceaux de JavaScript à la demande, au fur et à mesure que l'application utilise la fonctionnalité correspondante. Par exemple, le serveur envoie au client le JavaScript nécessaire pour construire un `Button` webforJ une seule fois, lorsque l'application crée son premier composant `Button`. Cela entraîne des améliorations mesurables du temps de chargement initial, ce qui se traduit par une meilleure expérience utilisateur.
