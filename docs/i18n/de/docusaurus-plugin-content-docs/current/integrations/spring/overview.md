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

Viele Java-Teams verwenden bereits [Spring Boot](https://spring.io/projects/spring-boot) zum Erstellen von Anwendungen. Die Spring-Integration mit webforJ ermöglicht es Ihnen jetzt, die UI-Komponenten von webforJ zu bestehenden Spring-Anwendungen hinzuzufügen oder die Funktionen von Spring in neuen webforJ-Projekten zu nutzen.

Ihre Spring-Services, -Repositorys und -Konfigurationen funktionieren wie gewohnt. Ihre webforJ-Komponenten können jedes Spring-Bean `@Autowired` verwenden. [Spring Data](https://spring.io/projects/spring-data) Repositorys verbinden sich direkt mit webforJ-Tabellen über `SpringDataRepository`. Die Entwicklung wird schneller mit automatischem Browser-Reload von Spring DevTools und webforJ LiveReload.

Die Integration hält beide Frameworks dabei, das Beste zu tun, was sie können – Spring kümmert sich um Backend-Angelegenheiten, während webforJ die Benutzeroberfläche verwaltet.

## Themen {#topics}

<DocCardList className="topics-section" />
