---
sidebar_position: 1
title: Gestion des ressources
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: a18b5fd490eca0891f470c7ccdb44e94
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

webforJ adopte une approche modulaire de la gestion des ressources, offrant plusieurs mécanismes pour répondre aux différents besoins des applications :  

- **Annotations Déclaratives** : Intégrer des ressources JavaScript et CSS au niveau des composants ou de l'application.  
- **Injection Dynamique Basée sur l'API** : Injecter des ressources à l'exécution pour permettre un comportement dynamique de l'application.  
- **Protocoles Personnalisés** : Fournir des méthodologies standardisées pour l'accès aux ressources.  
- **Streaming de Fichiers et Téléchargements Contrôlés** : Faciliter la récupération et la transmission gérées des fichiers de ressources.  

## Topics {#topics}

<DocCardList className="topics-section" />
