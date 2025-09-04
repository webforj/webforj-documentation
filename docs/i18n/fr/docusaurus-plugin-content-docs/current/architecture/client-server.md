---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: e5eafeb3f76c9a412d5a124f2eed2da8
---
La section suivante discute de diverses qualités de performance et des meilleures pratiques pour webforJ, ainsi que des détails de mise en œuvre pour le framework.

Lors de la création d'une application dans webforJ, le client et le serveur travaillent ensemble pour manipuler les données entre le client et le serveur, qui peut être classé dans les grandes catégories suivantes :

## 1. Serveur vers client {#1-server-to-client}

Les méthodes webforJ, telles que `setText()`, sont incluses dans cette catégorie. L'application webforJ exécutée sur le serveur envoie des données au client sans attendre de réponse. webforJ optimise automatiquement les lots d'opérations dans cette catégorie pour améliorer les performances.

## 2. Client vers serveur {#2-client-to-server}

Cette catégorie couvre le trafic des événements, tel que la méthode `Button.onClick()`. Pour la plupart, le client envoie des événements au serveur sans attendre de réponse. L'objet événement contient généralement des paramètres supplémentaires relatifs à l'événement, tels que le code de hachage. Comme ces informations sont envoyées au serveur dans le cadre de l'acte de livraison de l'événement, elles sont immédiatement disponibles pour le programme dès que l'événement est reçu.

## 3. Serveur vers client vers serveur (aller-retour) {#3-server-to-client-to-server-round-trip}

Les aller-retours sont effectués lorsque l'application interroge le client pour obtenir des informations dynamiques qui ne peuvent pas être mises en cache sur le serveur. Les méthodes telles que `Label.getText()` et `Checkbox.isChecked()` entrent dans cette catégorie. Lorsqu'une application webforJ exécute une ligne telle que `String title = myLabel.getText()`, elle se fige complètement pendant que le serveur envoie cette demande au client, puis attend que le client renvoie la réponse.

Si l'application envoie plusieurs messages au client qui ne nécessitent pas de réponse (catégorie 1), suivis d'un message unique qui nécessite un aller-retour (catégorie 3), l'application doit attendre que le client traite tous les messages en attente, puis réponde au message final qui nécessite une réponse. Dans certains cas, cela peut ajouter un délai. Si cet aller-retour n'avait pas été introduit, le client aurait pu continuer à travailler en traitant ces messages en retard pendant que l'application exécutée sur le serveur passait à de nouveaux travaux.

## Améliorer les performances {#improve-performance}

Il est possible d'améliorer considérablement la réactivité de l'application en évitant autant que possible les aller-retours de la troisième catégorie. Par exemple, changer la fonctionnalité onSelect du ComboBox de ceci :

```java
private void comboBoxSelect(ListSelectEvent ev){
    ComboBox component = (ComboBox) ev.getComponent();

    // Va au client
    int selected = component.getSelectedIndex();
}
```

à ce qui suit :

```java
private void comboBoxSelect(ListSelectEvent ev){
    // Obtient la valeur de l'événement
    int selected = ev.getSelectedIndex();
}
```

Dans le premier extrait, l'appel à `ComboBox.getSelectedIndex()` sur le composant force un aller-retour vers le client, introduisant un délai. Dans la seconde version, l'utilisation de la méthode `ListSelectEvent.getSelectedIndex()` de l'événement récupère la valeur qui a été livrée au serveur dans le cadre de l'événement d'origine.

## Mise en cache {#caching}

webforJ optimise davantage les performances en utilisant la mise en cache. En général, deux types de données existent dans ce contexte : les données que l'utilisateur peut modifier directement et les données qui ne peuvent pas être modifiées par l'utilisateur. Dans le premier cas, lors de la récupération des informations avec lesquelles les utilisateurs interagiront directement, il est nécessaire d'interroger le serveur pour obtenir ces informations.

Cependant, les informations qui ne peuvent pas être modifiées par l'utilisateur peuvent être mises en cache pour éviter des impacts sur les performances supplémentaires. Cela garantit qu'un aller-retour n'a pas besoin d'être effectué inutilement, offrant une expérience utilisateur plus efficace. webforJ optimise les applications de cette manière pour garantir des performances optimales.

## Temps de chargement {#loading-time}

Lorsque l'utilisateur lance une application webforJ, elle charge juste une petite portion (environ 2,5 kB gzip) de JavaScript pour initialiser la session. Après cela, elle télécharge dynamiquement des messages individuels, ou des portions de JavaScript, à la demande lorsque l'application utilise la fonctionnalité correspondante. Par exemple, le serveur envoie seulement au client le JavaScript nécessaire pour construire un `Button` de webforJ une seule fois — lorsque l'application crée son premier composant `Button`. Cela entraîne des améliorations mesurables du temps de chargement initial, ce qui se traduit par une meilleure expérience utilisateur.
