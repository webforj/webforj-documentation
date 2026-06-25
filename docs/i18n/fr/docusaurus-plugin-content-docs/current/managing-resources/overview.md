---
sidebar_position: 1
title: Managing Resources
description: >-
  Manage JavaScript, CSS, and other assets in webforJ apps with declarative
  annotations, runtime injection APIs, and custom URL protocols.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: ddf6edc65adddf9e8eb952916a120e1f
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

webforJ adopte une approche modulaire pour la gestion des ressources, offrant plusieurs mécanismes pour répondre aux différents besoins des applications :

- **Bundler frontend** : Intégrez des packages npm, des frameworks de composants et des langages de feuilles de style dans l'application via une entrée compilée. C'est le chemin par défaut pour les actifs frontend, et il réalise tout ce que font les annotations.
- **Annotations déclaratives** : Intégrez des ressources JavaScript et CSS au niveau des composants ou de l'application, sans étape de construction.
- **Injection dynamique basée sur l'API** : Injectez des ressources à l'exécution pour permettre un comportement dynamique de l'application.
- **Protocoles personnalisés** : Fournir des méthodologies normalisées pour l'accès aux ressources.
- **Flux de fichiers et téléchargements contrôlés** : Faciliter la récupération et la transmission gérées des fichiers de ressources.

## Topics {#topics}

<DocCardList className="topics-section" />
