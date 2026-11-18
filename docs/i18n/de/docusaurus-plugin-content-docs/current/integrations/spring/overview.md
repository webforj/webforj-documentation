---
title: Spring
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Kombinieren Sie webforJ-UI-Komponenten mit Spring Boot für Dependency
  Injection, Spring-Data-Repositories, benutzerdefinierte Scopes und Live
  Reload.
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

Viele Java-Teams nutzen bereits [Spring Boot](https://spring.io/projects/spring-boot) zum Erstellen von Anwendungen. Die Integration von Spring mit webforJ ermöglicht es Ihnen jetzt, die UI-Komponenten von webforJ zu bestehenden Spring-Anwendungen hinzuzufügen oder die Funktionen von Spring in neuen webforJ-Projekten zu verwenden.

Ihre Spring-Dienste, -Repositorys und -Konfigurationen funktionieren wie gewohnt. Ihre webforJ-Komponenten können beliebige Spring-Beans mit `@Autowired` verwenden. [Spring Data](https://spring.io/projects/spring-data) Repositorys verbinden sich direkt mit webforJ-Tabellen über `SpringDataRepository`. Die Entwicklung wird schneller mit [live reload](/docs/configuration/deploy-reload/spring-devtools), das den Browser aktualisiert, während Sie den Code ändern.

Die Integration sorgt dafür, dass beide Frameworks in dem bleiben, was sie am besten können - Spring kümmert sich um Backend-Belange, während webforJ die UI verwaltet.

## Themen {#topics}

<DocCardList className="topics-section" />
