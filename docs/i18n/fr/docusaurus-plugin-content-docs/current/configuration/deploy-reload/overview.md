---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Combine automatic redeployment with live browser reload so code changes appear
  in a running webforJ app without manual restarts.
_i18n_hash: dc1833dbce97bdbf387e98fab07967ca
---
Les flux de développement efficaces reposent sur des outils qui détectent les modifications de code et mettent automatiquement à jour l'application en temps réel. Le déploiement continu et le rechargement dynamique travaillent ensemble pour simplifier le processus de développement en réduisant les étapes manuelles, vous permettant de voir rapidement vos modifications sans avoir besoin de redémarrer manuellement le serveur.

## Redeployment {#redeployment}

Le redeployment dans le développement Java fait référence à la détection et au déploiement automatiques des modifications de code, afin que les mises à jour soient reflétées dans l'application sans redémarrage manuel du serveur. Ce processus implique généralement la mise à jour des classes Java et des ressources web à la volée.

Dans une application webforJ, cela signifie régénérer le fichier WAR chaque fois que des modifications sont apportées au code.

Les modifications apportées aux classes Java et aux ressources dans le classpath sont généralement surveillées par l'IDE. Lorsqu'une classe Java est modifiée et que le fichier est enregistré, soit automatiquement par l'IDE, soit manuellement par le développeur, ces outils se mettent en marche pour compiler et placer les fichiers de classe mis à jour dans le répertoire cible pour appliquer les modifications.

Pour la meilleure expérience, utilisez le redeployment automatique en combinaison avec des outils ou des paramètres qui automatisent le rechargement du navigateur.

## Live reload {#live-reload}

Une fois les modifications déployées, le live reload recharge automatiquement l'application afin que le navigateur reflète les mises à jour immédiatement, sans nécessiter un rafraîchissement manuel du navigateur.

Dans une application webforJ, le live reload peut automatiquement rafraîchir la vue, re-rendant les composants pour montrer l'état le plus récent de l'application, ou même appliquer des modifications au besoin à la demande.

Pour les sources frontales, le [frontend watch](/docs/configuration/deploy-reload/frontend-watch) reconstruit à chaque modification et applique une feuille de style ou une image sur place, ne rechargeant la vue que lorsqu'un script change.

## Topics {#topics}

<DocCardList className="topics-section" />
