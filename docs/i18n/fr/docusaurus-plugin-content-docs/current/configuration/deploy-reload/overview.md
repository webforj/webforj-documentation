---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: ec300e413c9fab01c4723f90f0e4c532
---
Des flux de développement efficaces reposent sur des outils qui détectent les changements de code et mettent à jour automatiquement l'application en temps réel. Le déploiement continu et le rechargement dynamique travaillent ensemble pour simplifier le processus de développement en réduisant les étapes manuelles, vous permettant de voir vos modifications rapidement sans avoir besoin de redémarrer manuellement le serveur.

## Redeployment {#redeployment}

Le redeploiement dans le développement Java fait référence à la détection et au déploiement automatiques des changements de code, de sorte que les mises à jour soient reflétées dans l'application sans redémarrage manuel du serveur. Ce processus implique généralement la mise à jour des classes Java et des ressources web à la volée.

Dans une application webforJ, cela signifie régénérer le fichier WAR chaque fois que des modifications sont apportées au code.

Les changements apportés aux classes Java et aux ressources sur le classpath sont généralement surveillés par l'IDE. Lorsqu'une classe Java est modifiée et que le fichier est enregistré, soit automatiquement par l'IDE, soit manuellement par le développeur, ces outils se mettent en route pour compiler et placer les fichiers de classe mis à jour dans le répertoire cible afin d'appliquer ces changements.

Des outils et des paramètres qui automatisent ou optimisent le rechargement du navigateur peuvent être ajoutés pour une expérience plus fluide.

## Live reload {#live-reload}

Le live reload garantit qu'une fois les modifications déployées, le navigateur reflète ces mises à jour en temps réel sans avoir besoin d'un rafraîchissement manuel du navigateur.

Dans une application webforJ, le live reload peut automatiquement rafraîchir la vue, re-rendant les composants pour afficher le dernier état de l'application, ou même corriger les changements au besoin à la demande.

## Topics {#topics}

<DocCardList className="topics-section" />
