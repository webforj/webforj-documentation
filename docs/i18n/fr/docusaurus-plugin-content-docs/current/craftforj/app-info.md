---
title: Informations sur l'app
sidebar_position: 10
description: >-
  craftforJ App Info révèle ce qui fait réellement tourner votre application :
  versions de webforJ et BBj Services, environnement Java, système
  d’exploitation et racine du projet.
_i18n_title_hash: a98b1ca473ce24658a6b0d7e3da0ce51
_i18n_description_hash: 2e5f0241dcd8b09393fd3e5ddeca035c
_i18n_hash: c2bd1fec7e37fa34291d3ca88047dc04
---
Les informations sur l'application rapportent ce que votre application exécute réellement, ce qui n'est pas toujours ce que votre `pom.xml` indique qu'elle devrait exécuter. En plus des versions webforJ et BBj Services, cela couvre l'exécution Java, le système d'exploitation et l'emplacement de l'application sur le disque.

![L'onglet Informations sur l'application](/img/craftforj/app-info/app-info-tab.png#rounded-border)

Deux de ces valeurs affectent le comportement de craftforJ :

- **La racine du projet** est l'endroit où craftforJ recherche vos sources. [Écrire dans la source](/docs/craftforj/source-changes) ne peut pas fonctionner lorsque cela est incorrect, alors définissez [`project-root`](/docs/craftforj/configuration#project-root) si la valeur rapportée ne correspond pas à votre projet.
- **L'exécution Java** détermine à quel point les [changements Java](/docs/craftforj/ai#it-writes-java) de l'assistant sont validés, car une validation complète nécessite un compilateur.

:::tip Déposer un problème
Incluez tout sur cette page, ainsi qu'un journal téléchargé depuis les paramètres de dépannage de craftforJ.
:::
