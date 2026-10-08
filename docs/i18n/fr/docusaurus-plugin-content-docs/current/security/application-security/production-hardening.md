---
sidebar_position: 3
title: Renforcement pour la production
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: 62e3e574855705f8b97295f4ebe5169b
---
Le modèle [piloté par le serveur](/docs/architecture/client-server) de webforJ et ses dispositifs de protection intégrés contre les [menaces courantes](/docs/security/application-security/common-threats) couvrent beaucoup de choses, mais un déploiement sécurisé dépend encore de la façon dont vous exploitez l'application. Les étapes ci-dessous complètent le tableau.

## Cryptez chaque connexion {#encrypt-every-connection}

Exécutez le trafic de production uniquement sur HTTPS. Terminez le TLS au niveau du conteneur, du proxy ou de l'équilibreur de charge devant l'application et redirigez toute demande HTTP en clair vers son équivalent sécurisé afin que les identifiants et les identifiants de session ne voyagent jamais non chiffrés.

## Ne faites confiance à rien provenant du navigateur {#trust-nothing-from-the-browser}

Un client manipulé peut envoyer n'importe quoi. Re-validez chaque valeur que votre code reçoit, même les valeurs que votre interface a déjà contraintes, avant de les persister ou d'agir sur elles. L'article [Interaction Client/Serveur](/docs/architecture/client-server) explique pourquoi le serveur est le seul endroit où une règle peut vraiment tenir.

La [liaison et la validation des données](/docs/data-binding/validation/overview) de webforJ aident ici : parce que la liaison s'exécute en Java sur le serveur, les contraintes que vous attachez à un modèle, y compris [la validation Jakarta](/docs/data-binding/validation/jakarta-validation), sont appliquées côté serveur plutôt que uniquement dans le navigateur. Considérez cela comme votre couche d'intégrité, et non comme une défense contre les attaques par injection ou par balisage, qui nécessitent toujours le traitement décrit dans l'article [Menaces Courantes](/docs/security/application-security/common-threats).

## Désactivé et caché n'est pas sécurité {#disabled-and-hidden-arent-security}

`setEnabled(false)` et `setVisible(false)` sont des indices d'interface, pas des contrôles d'accès. webforJ reflète l'état désactivé d'un contrôle vers le client, mais cela n'empêche pas un client manipulé de réactiver ce contrôle et de déclencher son action. Ne comptez jamais sur un contrôle désactivé ou caché pour empêcher quelque chose de se produire.

Mettez la vraie règle dans le gestionnaire côté serveur à la place : confirmez que l'utilisateur est autorisé et que les préconditions sont respectées avant d'effectuer l'action, exactement comme vous le feriez si le contrôle avait été activé tout le temps. L'état désactivé guide les utilisateurs honnêtes ; la règle côté serveur arrête les malhonnêtes.

## Sécurisez vos vues {#lock-down-your-views}

Protégez les vues avec la [sécurité des routes](/docs/security/overview) afin que chacune d'elles nécessite la bonne authentification et les bonnes rôles. Offrez aux gens l'accès le plus restreint qui leur permet de travailler, et privilégiez une posture sécurisée par défaut où une route non signalée nécessite toujours une connexion.

## Conservez les secrets externes {#keep-secrets-external}

Les identifiants, clés et jetons n'ont pas leur place dans le code ou dans votre référentiel. Récupérez-les plutôt à partir de l'environnement ou d'une source externe, comme indiqué dans [Gestion des Secrets](/docs/security/application-security/managing-secrets).

## Ne laissez pas les outils de développement actifs {#leave-development-tooling-off}

[craftforJ](/docs/craftforj) est l'environnement de développement qui inspecte une application en cours d'exécution et écrit les changements dans son code source Java. Il nécessite à la fois `webforj.debug` et `webforj.devtools.craftforj.enabled`, et par défaut, il ne répond qu'à la machine exécutant l'application. Les projets créés avec [startforJ](https://docs.webforj.com/startforj) ou à partir d'un [archétype](/docs/building-ui/archetypes/overview) webforJ ont les deux paramètres activés pour le développement, alors confirmez-les plutôt que de supposer.

Vérifiez que les deux propriétés sont non définies ou à `false` dans la configuration que vous déployez effectivement, y compris tout variable d'environnement ou profil qui s'applique uniquement en production. Puis chargez l'application déployée et confirmez qu'aucun déclencheur craftforJ n'apparaît sur la page. Consultez la [sécurité de craftforJ](/docs/craftforj/security) pour le tableau complet.

## Restez à jour sur les dépendances {#stay-current-on-dependencies}

Les bibliothèques que vous intégrez sont une source de risque plus importante que votre propre code. Suivez les avis, mettez à jour webforJ et vos autres dépendances régulièrement, et lorsque qu'une version corrigée d'une bibliothèque transitive est publiée avant celle qui l'intègre, fixez la version corrigée dans votre `pom.xml`.

## Échouez silencieusement {#fail-quietly}

Ne laissez pas de traces de pile, de chemins de fichiers ou d'identifiants internes atteindre les utilisateurs finaux. Enregistrez les détails dans vos journaux serveurs et présentez un message simple et générique dans l'interface. Enregistrez un gestionnaire personnalisé via la [gestion des erreurs](/docs/advanced/error-handling) de webforJ afin que les exceptions non capturées affichent une page contrôlée plutôt que des diagnostics bruts.

## Divulguez de manière responsable {#disclose-responsibly}

Vous avez trouvé un défaut possible dans webforJ lui-même ? Signalez-le en privé via le [rapport de vulnérabilité privé](https://github.com/webforj/webforj/security/advisories) de GitHub plutôt que d'ouvrir une issue ou une pull request publique, afin qu'un correctif puisse être publié avant que les détails ne soient connus.
