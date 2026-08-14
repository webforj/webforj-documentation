---
title: Troubleshooting
sidebar_position: 11
description: >-
  Fix the common cases where craftforJ doesn't appear, a feature is unavailable,
  or the assistant doesn't answer.
_i18n_hash: fcc5f7188c92523c0fb500bfc7b0ce58
---
### Rien n'apparaît sur la page {#nothing-appears-on-the-page}

craftforJ s'attache uniquement lorsque toutes les exigences dans [Commencer](/docs/craftforj/getting-started#requirements) sont remplies, et il n'affiche rien du tout lorsqu'il en manque une. Vérifiez-les dans l'ordre : la dépendance `webforj-devtools` dans le classpath, le mode debug, la propriété craftforJ, un navigateur sur la machine exécutant l'application, et une licence de développeur valide. Un fichier de configuration au mauvais emplacement, ou un profil qui remplace l'une des propriétés, produit exactement le même résultat que la propriété étant désactivée.

### Une fonctionnalité est indisponible {#a-feature-is-unavailable}

craftforJ affiche une fonctionnalité désactivée plutôt que de la cacher, donc un contrôle qui est présent mais marqué comme non pris en charge a été désactivé délibérément. Soit il a été désactivé avec un [drapeau de fonctionnalité](/docs/craftforj/configuration#feature-flags) dans la configuration de l'application, soit la version `webforj-devtools` dans votre classpath précède cette fonctionnalité.

L'écriture dans la source nécessite également un répertoire racine de projet que craftforJ peut trouver. Vérifiez celui qu'il a détecté dans [Informations sur l'application](/docs/craftforj/app-info), et définissez [`project-root`](/docs/craftforj/configuration#project-root) si ce n'est pas correct.

### La validation Java est plus faible que prévu {#java-validation-is-weaker-than-expected}

La [validation de compilation](/docs/craftforj/ai#it-writes-java) de l'assistant a besoin d'un JDK. Vérifiez la version Java dans [Informations sur l'application](/docs/craftforj/app-info), et exécutez l'application sur un JDK plutôt que sur un JRE.

### craftforJ semble obsolète après une mise à jour {#craftforj-looks-out-of-date-after-an-update}

Votre navigateur a mis en cache la version précédente. Effectuez un rechargement complet de la page, ou ouvrez l'application dans une fenêtre privée. Si le problème persiste, confirmez quelle version de `webforj-devtools` est réellement dans le classpath dans [Informations sur l'application](/docs/craftforj/app-info), car un ancien jar dans votre référentiel Maven local semble identique depuis le navigateur.

### L'assistant ne répond pas {#the-assistant-doesnt-answer}

L'assistant a besoin d'un fournisseur configuré et d'un modèle qui peut appeler des outils. Un modèle sans support d'outils peut tenir une conversation mais ne peut inspecter ni changer quoi que ce soit. Un modèle local qui perd continuellement le fil de la conversation fonctionne généralement avec une fenêtre de contexte trop petite.

Si un modèle local est configuré et accessible mais que chaque demande est refusée, le serveur de modèle rejette l'origine de la page. Pour Ollama, autorisez l'origine et redémarrez-le :

```bash
launchctl setenv OLLAMA_ORIGINS "*"
pkill ollama && ollama serve
```

Sur Linux, définissez `OLLAMA_ORIGINS` dans l'environnement à partir duquel Ollama démarre et redémarrez-le.

### craftforJ indique que l'application redémarre {#craftforj-says-the-app-is-restarting}

Votre application disparaît régulièrement en développement, chaque fois qu'elle se reconstruit. craftforJ rapporte ce qui se passe plutôt que de geler, donc il montre quand l'application redémarre ou que la page se recharge, et ses contrôles restent inertes jusqu'à ce que l'application soit de nouveau opérationnelle. Il se reconnecte de lui-même avec votre sélection et votre travail en attente intacts, donc il n'y a rien à faire sauf attendre. S'il indique qu'il ne peut pas atteindre l'application du tout, confirmez que l'application est toujours en cours d'exécution et rechargez la page.

### L'application redémarre en boucle {#the-app-keeps-restarting}

Appliquer un changement à la source redémarre l'application, comme décrit dans [Après l'application](/docs/craftforj/source-changes#after-you-apply). Les redémarrages qui se produisent sans changement appliqué proviennent du moniteur de fichiers de votre build plutôt que de craftforJ.

### Collecte des logs {#collecting-logs}

Avant de signaler un problème, activez la journalisation détaillée dans les paramètres de craftforJ, effacez le journal, reproduisez le problème, puis téléchargez le journal. Joignez-le avec le contenu de [Informations sur l'application](/docs/craftforj/app-info).
