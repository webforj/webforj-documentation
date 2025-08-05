---
title: Spring Framework
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: edf3c4087bb9491b2be06b67e32bb27e
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

De nombreuses équipes Java utilisent déjà [Spring Boot](https://spring.io/projects/spring-boot) pour créer des applications. L'intégration de Spring avec webforJ vous permet maintenant d'ajouter les composants UI de webforJ aux applications Spring existantes ou d'utiliser les fonctionnalités de Spring dans de nouveaux projets webforJ.

Vos services, dépôts et configurations Spring fonctionnent normalement. Vos composants webforJ peuvent `@Autowired` n'importe quel bean Spring. Les dépôts [Spring Data](https://spring.io/projects/spring-data) se connectent directement aux tables webforJ via `SpringDataRepository`. Le développement s'accélère avec le rafraîchissement automatique du navigateur par Spring DevTools et webforJ LiveReload.

L'intégration permet aux deux frameworks de faire ce qu'ils font le mieux - Spring gère les préoccupations de backend tandis que webforJ gère l'interface utilisateur.

## Topics {#topics}

<DocCardList className="topics-section" />
