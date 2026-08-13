---
sidebar_position: 3
title: Navigation Blocking
description: >-
  Intercept navigation with WillLeaveObserver veto handlers and the beforeunload
  event to guard unsaved changes.
_i18n_hash: 0deeb3e0583fdd425fe2a604ee1e9164
---
Le blocage de la navigation ajoute une ou plusieurs couches de contrôle à l'ensemble de l'API du routeur sous-jacent. Si des gestionnaires de blocage sont présents, la navigation sera empêchée comme suit :

Si la navigation est déclenchée par quelque chose contrôlé au niveau du routeur, vous pouvez effectuer une tâche ou afficher une invite utilisateur pour confirmer l'action. Chaque composant implémentant le `WillLeaveObserver` dans l'[arbre des routes](../route-hierarchy/overview) sera appelé. L'implémenteur doit invoquer `accept` pour continuer la navigation ou `reject` pour la bloquer. Si plusieurs composants implémentent le `WillLeaveObserver` dans l'arbre des routes, les gestionnaires de veto seront exécutés de manière séquentielle dans l'ordre inverse.

:::info Exemple pratique de gestion des veto
Pour voir comment le veto fonctionne en pratique, référez-vous aux [exemples d'utilisation des observateurs de cycle de vie](observers#example-handling-unsaved-changes-with-willleaveobserver).
:::

Pour les événements de page qui ne peuvent pas être contrôlés directement, le routeur n'interfère pas et n'impose pas un comportement spécifique. Cependant, les développeurs peuvent toujours écouter l'événement [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) pour faire une dernière tentative d'avertir l'utilisateur des données non enregistrées si nécessaire.

```java
PageEventOptions options = new PageEventOptions();
options.setCode("""
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Bouton de retour du navigateur {#browser-back-button}

Le bouton de retour fonctionne en dehors du contrôle des applications web, ce qui rend difficile d'intercepter ou d'empêcher son action de manière cohérente sur tous les navigateurs. Au lieu d'essayer de bloquer le bouton de retour, il est plus efficace de concevoir votre UI/UX de manière à atténuer l'impact. Envisagez des stratégies comme enregistrer les données non enregistrées dans [le stockage de session](../../advanced/web-storage#session-storage), afin que si un utilisateur navigue ailleurs puis revient, ses progrès soient restaurés en toute sécurité. Cette approche garantit la protection des données sans s'appuyer sur un comportement de navigateur peu fiable.
