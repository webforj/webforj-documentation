---
sidebar_position: 1
title: Managing Resources
description: >-
  Manage JavaScript, CSS, and other assets in webforJ apps with declarative
  annotations, runtime injection APIs, and custom URL protocols.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 7aee2ee29fd227575e12f1450422d0a1
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

Les applications s'appuient sur divers types de ressources, telles que JavaScript, CSS et images. Ce document fournit une exploration technique complète des mécanismes de gestion des ressources de webforJ, couvrant les annotations déclaratives, les méthodes API programmatiques et l'utilisation de protocoles personnalisés.

webforJ adopte une approche modulaire pour la gestion des ressources, offrant plusieurs mécanismes pour répondre à différents besoins d'application :

- **Regroupement frontend** : Apportez des packages npm, des frameworks de composants et des langages de feuilles de style dans l'application via une entrée compilée. C'est le chemin par défaut pour les actifs frontend, et il fait tout ce que font les annotations.
- **Annotations Déclaratives** : Intégrez des ressources JavaScript et CSS au niveau des composants ou de l'application, sans étape de construction.
- **Injection Dynamique Basée sur API** : Injectez des ressources à l'exécution pour permettre un comportement d'application dynamique.
- **Protocoles Personnalisés** : Fournir des méthodologies standardisées pour l'accès aux ressources.
- **Streaming de Fichiers et Téléchargements Contrôlés** : Faciliter la récupération et la transmission gérées des fichiers de ressources.

## Topics {#topics}

<DocCardList className="topics-section" />
