---
title: Sécurité
sidebar_position: 9
description: >-
  What craftforJ can reach in your project, how it restricts access, and how to
  confirm it's disabled in production.
_i18n_hash: 5ffbc5b5c6e6cfcf64143712a21944d5
---
craftforJ lit et écrit la source du projet auquel il est attaché. Cette page décrit les limites à cet égard et comment confirmer que craftforJ est désactivé dans les builds que vous déployez.

## Deux paramètres requis {#two-required-settings}

craftforJ nécessite que les deux éléments suivants soient activés :

- `webforj.debug`
- `webforj.devtools.craftforj.enabled`

Aucun d'eux ne fait quoi que ce soit seul. Une application qui atteint la production avec le mode débogage activé n'expose pas craftforJ, et une application portant la propriété craftforJ dans un fichier de configuration partagé ne l'expose pas en dehors du mode débogage.

Les projets créés avec [startforJ](https://docs.webforj.com/startforj) ou à partir d'un [archétype webforJ](/docs/building-ui/archetypes/overview) ont les deux paramètres activés, donc craftforJ fonctionne dès le premier lancement. Avant de déployer, passez en revue la [liste de contrôle de production](#in-production) ci-dessous.

## Accès local par défaut {#local-access-by-default}

Seul un navigateur sur la machine exécutant l'application peut accéder à craftforJ. Tout le reste est refusé, et cela s'applique sans aucune configuration de votre part. Pour accéder à craftforJ depuis une autre machine, nommez cette machine dans [`hosts-allowed`](/docs/craftforj/configuration#access). Les adresses sont comparées littéralement, donc un client ne peut pas passer en prétendant être autre chose.

:::warning Le caractère générique supprime complètement la restriction
Définir `hosts-allowed = "*"` signifie que quiconque peut atteindre le port de votre application peut lire et écrire les sources de votre projet. Cela existe pour des environnements scellés, tels qu'un conteneur qui n'est accessible que par vous. Ne l'utilisez nulle part ailleurs.
:::

## Aucune surface HTTP ajoutée {#no-added-http-surface}

craftforJ n'ajoute aucun point de terminaison HTTP, servlet ou filtre à votre application. Il fonctionne sur la connexion que votre application a déjà, donc votre application répond exactement au même ensemble de demandes avec craftforJ activé que sans.

## Les requêtes proviennent de votre page {#requests-come-from-your-page}

craftforJ n'agit que sur les requêtes provenant de la page que votre serveur a réellement servie. Un script qui trouve son chemin dans la page depuis un autre endroit, comme une dépendance compromise ou quelque chose collé dans une console, ne peut pas activer craftforJ.

## Clés API {#api-keys}

Votre clé est stockée sur la machine exécutant votre application. L'[assistant IA](/docs/craftforj/ai) s'exécute dans le navigateur, donc craftforJ doit lui transmettre la clé pour fonctionner, et il conserve cette clé en mémoire aussi longtemps que la page est ouverte. Rien n'est écrit dans le stockage du navigateur, et fermer la page ne laisse rien derrière.

L'assistant communique ensuite avec votre fournisseur depuis le navigateur plutôt que par l'intermédiaire de votre serveur. Il n'y a pas de relais, pas de proxy, pas de télémétrie, et pas de tiers entre les deux.

Ce qui atteint votre fournisseur, c'est la conversation elle-même, qui comprend les parties de votre application que l'assistant a examinées et les captures d'écran qu'il a prises. Prenez cela en compte avant de pointer un modèle hébergé vers une application fonctionnant sur des données réelles. Un modèle fonctionnant localement garde tout sur votre machine.

## Ce que craftforJ peut changer {#what-craftforj-can-change}

Avec toutes les fonctionnalités activées, craftforJ peut :

- Lire tout fichier source sous la racine de votre projet
- Écrire des fichiers source Java, y compris des annotations d'accès aux routes
- Écrire la feuille de style de votre application
- Modifier et supprimer des composants dans l'application en cours d'exécution
- Naviguer dans l'application en cours d'exécution

Chacune de ces fonctionnalités peut être [désactivée](/docs/craftforj/configuration#feature-flags) indépendamment, et chaque écriture sur le disque passe par un diff que vous approuvez.

## En production {#in-production}

Laissez craftforJ désactivé. Il est désactivé sauf si vous l'avez activé, donc dans la plupart des cas, il n'y a rien à faire. Pour confirmer :

1. `webforj.devtools.craftforj.enabled` n'est pas défini ou est `false` dans la configuration que vous déployez réellement.
2. `webforj.debug` n'est pas défini ou est `false` dans cette même configuration.
3. Aucun des deux paramètres n'est défini par une variable d'environnement ou par un profil qui s'applique uniquement en production.
4. Chargez l'application déployée et confirmez qu'il n'y a pas de déclencheur craftforJ sur la page.

Pour une vue d'ensemble, consultez [Renforcement de la production](/docs/security/application-security/production-hardening).

## Signalement d'un problème de sécurité {#reporting-a-security-issue}

Si vous trouvez un problème de sécurité dans craftforJ, signalez-le via la [politique de sécurité webforJ](https://github.com/webforj/webforj/security) plutôt que dans un problème public.
