---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: d98e2e989be9fbc147c128f8c46f905e
---
Des workflows de développement efficaces reposent sur des outils qui détectent les modifications de code et mettent automatiquement à jour l'application en temps réel. Le déploiement continu et le rechargement dynamique travaillent ensemble pour simplifier le processus de développement en réduisant les étapes manuelles, vous permettant de voir vos modifications rapidement sans avoir besoin de redémarrer manuellement le serveur.

## Redeployment {#redeployment}

Le redeploiement dans le développement Java fait référence à la détection et au déploiement automatiques des modifications de code, de sorte que les mises à jour soient reflétées dans l'application sans un redémarrage manuel du serveur. Ce processus implique généralement la mise à jour des classes Java et des ressources web à la volée.

Dans une application webforJ, cela signifie régénérer le fichier WAR chaque fois que des modifications sont apportées au code.

Les changements apportés aux classes Java et aux ressources sur le chemin de classe sont généralement surveillés par l'IDE. Lorsqu'une classe Java est modifiée et que le fichier est enregistré, soit automatiquement par l'IDE, soit manuellement par le développeur, ces outils se déclenchent pour compiler et placer les fichiers de classe mis à jour dans le répertoire cible afin d'appliquer ces changements.

Des outils et des paramètres qui automatisent ou optimisent le rechargement du navigateur peuvent être ajoutés pour une expérience plus fluide.

## Live reload {#live-reload}

Le rechargement en direct garantit qu'une fois les modifications déployées, le navigateur reflète ces mises à jour en temps réel sans avoir besoin d'un rafraîchissement manuel du navigateur.

Dans une application webforJ, le rechargement en direct peut automatiquement rafraîchir la vue, redessinant les composants pour montrer l'état le plus récent de l'application, ou même appliquer les changements au fur et à mesure qu'ils sont nécessaires à la demande.

## Topics {#topics}

<DocCardList className="topics-section" />
