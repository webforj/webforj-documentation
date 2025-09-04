---
sidebar_position: 3
title: Navigation Blocking
_i18n_hash: a08d56654914719e12d1401d263c7956
---
Le blocage de navigation ajoute une ou plusieurs couches de contrôle à l'ensemble de l'API du routeur sous-jacent. Si des gestionnaires de blocage sont présents, la navigation sera empêchée comme suit :

Si la navigation est déclenchée par quelque chose contrôlé au niveau du routeur, vous pouvez effectuer une tâche ou afficher une invite utilisateur pour confirmer l'action. Chaque composant mettant en œuvre le `WillLeaveObserver` dans l'[arbre des routes](../route-hierarchy/overview) sera appelé. L'implémenteur doit invoquer `accept` pour continuer la navigation ou `reject` pour la bloquer. Si plusieurs composants mettent en œuvre le `WillLeaveObserver` dans l'arbre de la route, les gestionnaires de veto seront exécutés séquentiellement dans l'ordre inverse.

:::info Exemple pratique de gestion de veto
Pour voir comment le veto fonctionne en pratique, reportez-vous aux [Exemples d'utilisation des observateurs de cycle de vie](observers#example-handling-unsaved-changes-with-willleaveobserver).
:::

Pour les événements de page qui ne peuvent pas être contrôlés directement, le routeur n'interfère pas ou n'impose pas un comportement spécifique. Cependant, les développeurs peuvent toujours écouter l'événement [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) pour faire une dernière tentative d'avertir l'utilisateur des données non enregistrées si nécessaire.

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Bouton de retour du navigateur {#browser-back-button}

Le bouton de retour opère en dehors du contrôle des applications web, rendant difficile l'interception ou la prévention de son action de manière cohérente à travers tous les navigateurs. Au lieu d'essayer de bloquer le bouton de retour, il est plus efficace de concevoir votre UI/UX de manière à atténuer l'impact. Envisagez des stratégies comme la sauvegarde des données non enregistrées dans [le stockage de session](../../advanced/web-storage#session-storage), afin que si un utilisateur navigue ailleurs et revient, ses progrès soient restaurés en toute sécurité. Cette approche garantit la protection des données sans s'appuyer sur un comportement de navigateur peu fiable.
