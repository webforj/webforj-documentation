---
title: Spring Framework
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 2bd69e8c9fad1e483d3c087f0e00e229
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

De nombreuses équipes Java utilisent déjà [Spring Boot](https://spring.io/projects/spring-boot) pour créer des applications. L'intégration de Spring avec webforJ vous permet maintenant d'ajouter les composants UI de webforJ à des applications Spring existantes, ou d'utiliser les fonctionnalités de Spring dans de nouveaux projets webforJ.

Vos services, dépôts et configurations Spring fonctionnent normalement. Vos composants webforJ peuvent `@Autowired` n'importe quel bean Spring. Les dépôts [Spring Data](https://spring.io/projects/spring-data) se connectent directement aux tables webforJ via `SpringDataRepository`. Le développement devient plus rapide grâce au rafraîchissement automatique du navigateur avec [Spring DevTools et webforJ LiveReload](/docs/configuration/deploy-reload/spring-devtools).

L'intégration permet à chaque framework de faire ce qu'il fait de mieux - Spring gère les préoccupations backend tandis que webforJ s'occupe de l'interface utilisateur.

## Topics {#topics}

<DocCardList className="topics-section" />
