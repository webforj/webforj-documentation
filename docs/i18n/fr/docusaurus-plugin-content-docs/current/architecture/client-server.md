---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: a69f444ce2e5a9dea37304d466f4e6ac
---
La section suivante discute des diverses qualités de performance et des meilleures pratiques pour webforJ, ainsi que des détails d'implémentation pour le framework.

Lors de la création d'une application dans webforJ, le client et le serveur travaillent ensemble pour manipuler les données entre le client et le serveur, ce qui peut être divisé en grandes catégories :

## 1. Serveur vers client {#1-server-to-client}

Les méthodes webforJ telles que `setText()` sont incluses dans cette catégorie. Une application webforJ exécutée sur le serveur envoie des données au client sans attendre de réponse. webforJ optimise automatiquement les lots d'opérations dans cette catégorie pour améliorer les performances.

## 2. Client vers serveur {#2-client-to-server}

Cette catégorie couvre le trafic d'événements, comme une méthode `Button.onClick()`. Dans l'ensemble, le client envoie des événements au serveur sans attendre de réponse. L'objet événement contient généralement des paramètres supplémentaires liés à l'événement, comme le code de hachage. Comme cette information est transmise au serveur dans le cadre de l'acte de livraison de l'événement, elle est immédiatement disponible pour le programme dès que l'événement est reçu.

## 3. Serveur vers client vers serveur (aller-retour) {#3-server-to-client-to-server-round-trip}

Les aller-retours sont effectués lorsque l'application interroge le client pour obtenir des informations dynamiques qui ne peuvent pas être mises en cache sur le serveur. Des méthodes telles que `Label.getText()` et `Checkbox.isChecked()` entrent dans cette catégorie. Lorsque une application webforJ exécute une ligne comme `String title = myLabel.getText()`, elle se fige complètement pendant que le serveur envoie cette demande au client, puis attend que le client envoie la réponse.

Si l'application envoie plusieurs messages au client qui ne nécessitent pas de réponse (catégorie 1), suivis d'un message unique qui nécessite un aller-retour (catégorie 3), l'application doit attendre que le client traite tous les messages en attente, puis réponde au dernier message qui nécessite une réponse. Dans certains cas, cela peut ajouter un délai. Si cet aller-retour n'avait pas été introduit, le client aurait pu continuer à travailler en traitant ces messages en attente pendant que l'application exécutée sur le serveur passait à de nouveaux travaux.

## Améliorer les performances {#improve-performance}

Il est possible d'améliorer considérablement la réactivité en évitant autant que possible les aller-retours de la troisième catégorie. Par exemple, en changeant la fonctionnalité `onSelect` du ComboBox de ceci :

```java
private void comboBoxSelect(ListSelectEvent ev){
  ComboBox component = (ComboBox) ev.getComponent();

  // Va vers le client
  int selected = component.getSelectedIndex();
}
```

à ce qui suit :

```java
private void comboBoxSelect(ListSelectEvent ev){
  // Récupère la valeur de l'événement
  int selected = ev.getSelectedIndex();
}
```

Dans le premier extrait, `ComboBox.getSelectedIndex()` exécuté sur le composant force un aller-retour vers le client, introduisant un délai. Dans la deuxième version, l'utilisation de la méthode `ListSelectEvent.getSelectedIndex()` de l'événement récupère la valeur qui a été transmise au serveur dans le cadre de l'événement original.

## Mise en cache {#caching}

webforJ optimise encore plus les performances en utilisant la mise en cache. En général, deux types de données existent dans ce contexte : les données que l'utilisateur peut changer directement et les données qui ne peuvent pas être modifiées par l'utilisateur. Dans le premier cas, lors de la récupération des informations avec lesquelles les utilisateurs interagiront directement, il est nécessaire d'interroger le serveur pour ces informations.

Cependant, les informations qui ne peuvent pas être modifiées par l'utilisateur peuvent être mises en cache pour éviter des impacts supplémentaires sur les performances. Cela garantit qu'un aller-retour n'a pas besoin d'être effectué de manière inutile, offrant une expérience utilisateur plus efficace. webforJ optimise les applications de cette manière pour garantir des performances optimales.

## Temps de chargement {#loading-time}

Lorsque l'utilisateur lance une application webforJ, elle charge juste un petit morceau (environ 2,5 kB gzip) de JavaScript pour initialiser la session. Après cela, elle télécharge dynamiquement des messages individuels, ou des morceaux de JavaScript, à la demande au fur et à mesure que l'application utilise la fonctionnalité correspondante. Par exemple, le serveur n'envoie au client le JavaScript nécessaire pour construire un `Button` webforJ qu'une seule fois, lorsque l'application crée son premier composant `Button`. Cela entraîne des améliorations mesurables du temps de chargement initial, ce qui se traduit par une meilleure expérience utilisateur.
