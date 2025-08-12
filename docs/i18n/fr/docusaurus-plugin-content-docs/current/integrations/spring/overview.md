---
title: Spring Framework
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 4ef41ed3a00ca782da0bba406fd4e902
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

De nombreuses équipes Java utilisent déjà [Spring Boot](https://spring.io/projects/spring-boot) pour créer des applications. L'intégration de Spring avec webforJ vous permet désormais d'ajouter des composants UI de webforJ à des applications Spring existantes, ou d'utiliser les fonctionnalités de Spring dans de nouveaux projets webforJ.

Vos services Spring, dépôts et configurations fonctionnent normalement. Vos composants webforJ peuvent `@Autowired` n'importe quel bean Spring. Les dépôts [Spring Data](https://spring.io/projects/spring-data) se connectent directement aux tables webforJ via `SpringDataRepository`. Le développement devient plus rapide avec le rafraîchissement automatique du navigateur grâce à Spring DevTools et webforJ LiveReload.

L'intégration permet aux deux frameworks de faire ce qu'ils font le mieux - Spring gère les préoccupations backend tandis que webforJ gère l'UI.

## Topics {#topics}

<DocCardList className="topics-section" />
