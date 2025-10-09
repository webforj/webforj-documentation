---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 9b0d2672241250200ed14343e57d3926
---
Les flux de travail de développement efficaces reposent sur des outils qui détectent les changements de code et mettent automatiquement à jour l'application en temps réel. Le déploiement continu et le rechargement dynamique travaillent ensemble pour simplifier le processus de développement en réduisant les étapes manuelles, vous permettant de voir vos modifications rapidement sans avoir besoin de redémarrer manuellement le serveur.

## Redeploiement {#redeployment}

Le redeploiement dans le développement Java fait référence à la détection automatique et au déploiement des changements de code, de sorte que les mises à jour soient reflétées dans l'application sans redémarrage manuel du serveur. Ce processus implique généralement la mise à jour des classes Java et des ressources web à la volée.

Dans une application webforJ, cela signifie régénérer le fichier WAR chaque fois que des modifications sont apportées au code.

Les changements apportés aux classes Java et aux ressources sur le classpath sont généralement surveillés par l'IDE. Lorsqu'une classe Java est modifiée et que le fichier est enregistré, soit automatiquement par l'IDE soit manuellement par le développeur, ces outils se déclenchent pour compiler et placer les fichiers de classe mis à jour dans le répertoire cible afin d'appliquer les modifications.

Pour la meilleure expérience, utilisez le redeploiement automatique en combinaison avec des outils ou des paramètres qui automatisent le rechargement du navigateur.

## Rechargement en direct {#live-reload}

Une fois les modifications déployées, le rechargement en direct recharge automatiquement l'application afin que le navigateur reflète les mises à jour immédiatement, sans nécessiter un rafraîchissement manuel du navigateur.

Dans une application webforJ, le rechargement en direct peut automatiquement rafraîchir la vue, rendant à nouveau les composants pour montrer l'état le plus récent de l'application, ou même appliquer des changements au besoin à la demande.

## Sujets {#topics}

<DocCardList className="topics-section" />
