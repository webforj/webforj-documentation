---
sidebar_position: 3
title: Production Hardening
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: b127e22d65b9a0ee8fc5b58b542aee36
---
Le modèle [driver de serveur](/docs/architecture/client-server) de webforJ et les protections intégrées contre les [menaces courantes](/docs/security/application-security/common-threats) couvrent beaucoup de choses, mais un déploiement sécurisé dépend encore de la façon dont vous faites fonctionner l'application. Les étapes ci-dessous complètent le tableau.

## Chiffrez chaque connexion {#encrypt-every-connection}

Faites circuler le trafic de production uniquement sur HTTPS. Terminez le TLS au niveau du conteneur, du proxy ou de l'équilibreur de charge devant l'application, et redirigez toute requête HTTP simple vers son équivalent sécurisé afin que les identifiants et les identifiants de session ne circulent jamais sans encryption.

## Ne faites confiance à rien venant du navigateur {#trust-nothing-from-the-browser}

Un client manipulé peut envoyer n'importe quoi. Revalidez chaque valeur que votre code reçoit, même les valeurs que votre interface a déjà limitées, avant de les conserver ou d’agir sur elles. L'article [Interaction Client/Serveur](/docs/architecture/client-server) explique pourquoi le serveur est le seul endroit où une règle peut véritablement tenir.

La [liaison de données et validation](/docs/data-binding/validation/overview) de webforJ aide ici : parce que la liaison s'exécute en Java sur le serveur, les contraintes que vous attachez à un modèle, y compris la [validation Jakarta](/docs/data-binding/validation/jakarta-validation), sont appliquées côté serveur plutôt que seulement dans le navigateur. Considérez cela comme votre couche d'intégrité, et non comme une défense contre les attaques d'injection ou de balisage, qui nécessitent toujours le traitement décrit dans l'article [Menaces Courantes](/docs/security/application-security/common-threats).

## Désactivé et caché ne sont pas une sécurité {#disabled-and-hidden-arent-security}

`setEnabled(false)` et `setVisible(false)` sont des indices d'interface, pas des contrôles d'accès. webforJ reflète l'état désactivé d'un contrôle au client, mais cela n'empêche pas un client manipulé de réactiver ce contrôle et de déclencher son action. Ne comptez jamais sur un contrôle désactivé ou caché pour empêcher quelque chose de se produire.

Mettez la véritable règle dans le gestionnaire côté serveur à la place : confirmez que l'utilisateur est autorisé et que les préconditions sont respectées avant d'effectuer l'action, exactement comme vous le feriez si le contrôle avait été activé tout le temps. L'état désactivé guide les utilisateurs honnêtes ; la règle côté serveur arrête les malhonnêtes.

## Verrouillez vos vues {#lock-down-your-views}

Protégez les vues avec la [sécurité des routes](/docs/security/overview) pour que chacune d'elles exige la bonne authentification et les bons rôles. Offrez aux gens le plus d'accès restreint qui leur permet de travailler, et privilégiez une posture sécurisée par défaut où une route non marquée nécessite toujours une connexion.

## Gardez les secrets externes {#keep-secrets-external}

Les identifiants, clés et tokens n'appartiennent pas au code ou à votre dépôt. Récupérez-les de l'environnement ou d'une source externe à la place, comme indiqué dans [Gestion des Secrets](/docs/security/application-security/managing-secrets).

## Restez à jour sur les dépendances {#stay-current-on-dependencies}

Les bibliothèques que vous intégrez sont une source de risque plus importante que votre propre code. Suivez les avis, mettez régulièrement à jour webforJ et vos autres dépendances, et lorsque qu'une version corrigée d'une bibliothèque transitive est publiée avant la bibliothèque qui l'intègre, fixez la version corrigée dans votre `pom.xml`.

## Échouez silencieusement {#fail-quietly}

Ne laissez pas les traces de pile, chemins de fichiers ou identifiants internes atteindre les utilisateurs finaux. Enregistrez le détail dans vos journaux de serveur et présentez un message simple et générique dans l'interface. Enregistrez un gestionnaire personnalisé via le [traitement des erreurs](/docs/advanced/error-handling) de webforJ afin que les exceptions non gérées surfacent une page contrôlée plutôt que des diagnostics bruts.

## Dévoilez de manière responsable {#disclose-responsibly}

Vous avez trouvé un défaut possible dans webforJ lui-même ? Signalez-le en privé via le [signalement de vulnérabilités privées](https://github.com/webforj/webforj/security/advisories) de GitHub plutôt que d'ouvrir un problème public ou une demande de tirage, afin qu'une correction puisse être mise en place avant que les détails ne soient connus.
