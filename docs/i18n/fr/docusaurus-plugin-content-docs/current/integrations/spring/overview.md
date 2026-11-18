---
title: Spring
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Associez les composants d’interface webforJ à Spring Boot pour l’injection de
  dépendances, les dépôts Spring Data, les portées personnalisées et le
  rechargement à chaud.
_i18n_title_hash: 38008dd81c2f4d7985ecf6e0ce8af1d1
_i18n_description_hash: cbe9423c66534c89100879333bf08fda
_i18n_hash: 7af3520db108b976dda9856890c61979
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

De nombreuses équipes Java utilisent déjà [Spring Boot](https://spring.io/projects/spring-boot) pour créer des applications. L'intégration de Spring avec webforJ vous permet désormais d'ajouter les composants UI de webforJ aux applications Spring existantes, ou d'utiliser les fonctionnalités de Spring dans de nouveaux projets webforJ.

Vos services, dépôts et configurations Spring fonctionnent normalement. Vos composants webforJ peuvent `@Autowired` n'importe quel bean Spring. Les dépôts [Spring Data](https://spring.io/projects/spring-data) se connectent directement aux tables webforJ via `SpringDataRepository`. Le développement devient plus rapide avec [live reload](/docs/configuration/deploy-reload/spring-devtools), qui actualise le navigateur lorsque vous modifiez le code.

L'intégration permet aux deux frameworks de faire ce qu'ils font le mieux : Spring gère les préoccupations backend tandis que webforJ s'occupe de l'interface utilisateur.

## Topics {#topics}

<DocCardList className="topics-section" />
