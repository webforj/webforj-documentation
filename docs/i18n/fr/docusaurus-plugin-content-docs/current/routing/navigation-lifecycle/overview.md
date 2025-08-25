---
sidebar_position: 1
title: Navigation Lifecycle
hide_giscus_comments: true
_i18n_hash: 14d81d1a9ff86af17370e0a7eb50608b
---
Naviguer à travers différentes vues dans une application web implique plusieurs étapes, offrant des opportunités d'effectuer des actions avant, pendant ou après une transition. Le cycle de navigation fournit un système basé sur des événements qui permet aux développeurs de gérer les aspects clés de la navigation, tels que la validation des données, le contrôle d'accès, la mise à jour de l'interface utilisateur et la gestion des tâches de nettoyage.

Ce système flexible permet aux développeurs de gérer les transitions de manière explicite en s'accrochant à des points critiques du processus de navigation. Que vous ayez besoin de bloquer la navigation, de récupérer des données lorsqu'un composant est affiché ou de gérer des modifications non enregistrées, vous avez un contrôle total sur le flux de navigation grâce à ses événements de cycle de vie et ses observateurs.

## Vue d'ensemble des événements de cycle de vie {#lifecycle-events-overview}

Le processus de navigation est gouverné par une série d'événements qui sont déclenchés lors des transitions de route. Ces événements vous permettent de réagir à des moments spécifiques dans le cycle de vie :

1. **WillEnter** : Déclenché avant de naviguer vers une route et avant que son composant soit attaché au DOM. C'est idéal pour des tâches telles que des vérifications d'authentification ou le blocage de la navigation si nécessaire.
2. **DidEnter** : Déclenché après la navigation complétée et que le composant est attaché au DOM. Cet événement convient pour des actions telles que récupérer des données, exécuter des animations ou définir le focus sur des éléments de l'interface utilisateur.
3. **WillLeave** : Déclenché avant de naviguer loin de la route actuelle et avant que son composant ne soit retiré du DOM. Il est utile pour gérer des données non enregistrées, amener l'utilisateur à confirmer ou gérer des tâches de nettoyage.
4. **DidLeave** : Déclenché après le passage à une autre route et après que le composant ait été retiré du DOM. Cet événement est idéal pour libérer des ressources ou réinitialiser l'interface utilisateur pour une utilisation future.
5. **Activate** (depuis `25.03`) : Déclenché lorsque les composants mis en cache sont réactivés au lieu d'être recréés. Cela se produit lors de la navigation vers la même route avec différents paramètres ou lors du retour à une route précédemment visitée. L'événement se déclenche pour tous les composants mis en cache dans la hiérarchie de route qui restent dans le chemin actuel, permettant à la fois aux mises en page parents et aux composants enfants de rafraîchir leurs données ou de mettre à jour l'interface utilisateur en fonction des nouveaux paramètres tout en maintenant l'état du composant.

Ces événements offrent un contrôle granulaire sur le cycle de vie de la navigation, permettant aux développeurs de coordonner la validation des données, les mises à jour de l'interface utilisateur et la gestion des ressources lors des transitions de route.

## Sujets {#topics}

<DocCardList className="topics-section" />
