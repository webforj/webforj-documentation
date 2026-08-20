---
title: Spring
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Combine webforJ UI components with Spring Boot for dependency injection,
  Spring Data repositories, custom scopes, and live reload.
_i18n_hash: 7af3520db108b976dda9856890c61979
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Viele Java-Teams nutzen bereits [Spring Boot](https://spring.io/projects/spring-boot) zum Erstellen von Anwendungen. Die Integration von Spring mit webforJ ermöglicht es Ihnen jetzt, die UI-Komponenten von webforJ zu bestehenden Spring-Anwendungen hinzuzufügen oder die Funktionen von Spring in neuen webforJ-Projekten zu verwenden.

Ihre Spring-Dienste, -Repositorys und -Konfigurationen funktionieren wie gewohnt. Ihre webforJ-Komponenten können beliebige Spring-Beans mit `@Autowired` verwenden. [Spring Data](https://spring.io/projects/spring-data) Repositorys verbinden sich direkt mit webforJ-Tabellen über `SpringDataRepository`. Die Entwicklung wird schneller mit [live reload](/docs/configuration/deploy-reload/spring-devtools), das den Browser aktualisiert, während Sie den Code ändern.

Die Integration sorgt dafür, dass beide Frameworks in dem bleiben, was sie am besten können - Spring kümmert sich um Backend-Belange, während webforJ die UI verwaltet.

## Themen {#topics}

<DocCardList className="topics-section" />
