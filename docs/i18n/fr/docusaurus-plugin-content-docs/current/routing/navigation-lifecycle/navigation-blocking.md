---
sidebar_position: 3
title: Navigation Blocking
_i18n_hash: c0d79c6ce266eb4b9f9fd28915dcc380
---
Le blocage de navigation ajoute une ou plusieurs couches de contrôle à l'ensemble de l'API du routeur sous-jacent. Si des gestionnaires de blocage sont présents, la navigation sera empêchée comme suit :

Si la navigation est déclenchée par quelque chose contrôlé au niveau du routeur, vous pouvez effectuer toute tâche ou afficher une invite utilisateur pour confirmer l'action. Chaque composant implémentant le `WillLeaveObserver` dans l'[arborescence des routes](../route-hierarchy/overview) sera appelé. L'implémenteur doit invoquer `accept` pour continuer la navigation ou `reject` pour la bloquer. Si plusieurs composants implémentent le `WillLeaveObserver` dans l'arborescence des routes, les gestionnaires de veto seront exécutés séquentiellement dans l'ordre inverse.

:::info Exemple pratique de gestion de veto
Pour voir comment le veto fonctionne en pratique, consultez les [Exemples d'utilisation des observateurs de cycle de vie](observers#example-handling-unsaved-changes-with-willleaveobserver).
:::

Pour les événements de page qui ne peuvent pas être contrôlés directement, le routeur n'interfère pas et n'impose pas de comportement spécifique. Cependant, les développeurs peuvent toujours écouter l'événement [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) pour tenter de prévenir l'utilisateur concernant des données non sauvegardées si nécessaire.

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Bouton retour du navigateur {#browser-back-button}

Le bouton retour fonctionne en dehors du contrôle des applications web, ce qui rend difficile l'interception ou la prévention de son action de manière cohérente dans tous les navigateurs. Au lieu d'essayer de bloquer le bouton retour, il est plus efficace de concevoir votre UI/UX de manière à atténuer l'impact. Envisagez des stratégies telles que la sauvegarde des données non sauvegardées dans le [stockage de session](../../advanced/web-storage#session-storage), afin que si un utilisateur navigue ailleurs puis revient, ses progrès soient restaurés en toute sécurité. Cette approche garantit la protection des données sans dépendre d'un comportement de navigateur peu fiable.
