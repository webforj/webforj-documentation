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

Viele Java-Teams verwenden bereits [Spring Boot](https://spring.io/projects/spring-boot) zum Erstellen von Anwendungen. Die Integration von Spring mit webforJ ermöglicht es Ihnen jetzt, die UI-Komponenten von webforJ in bestehenden Spring-Anwendungen hinzuzufügen oder die Funktionen von Spring in neuen webforJ-Projekten zu nutzen.

Ihre Spring-Dienste, -Repositorys und -Konfigurationen funktionieren wie gewohnt. Ihre webforJ-Komponenten können jede Spring-Bean mit `@Autowired` verwenden. [Spring Data](https://spring.io/projects/spring-data) Repositorys verbinden sich direkt mit den webforJ-Tabellen über `SpringDataRepository`. Die Entwicklung wird schneller durch die automatische Browseraktualisierung von [Spring DevTools und webforJ LiveReload](/docs/configuration/deploy-reload/spring-devtools).

Die Integration sorgt dafür, dass beide Frameworks das tun, was sie am besten können - Spring kümmert sich um Backend-Angelegenheiten, während webforJ die UI verarbeitet.

## Themen {#topics}

<DocCardList className="topics-section" />
