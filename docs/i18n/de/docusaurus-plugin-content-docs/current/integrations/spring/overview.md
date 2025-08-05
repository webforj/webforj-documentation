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

Viele Java-Teams verwenden bereits [Spring Boot](https://spring.io/projects/spring-boot) zum Erstellen von Anwendungen. Die Spring-Integration mit webforJ ermöglicht es Ihnen jetzt, die UI-Komponenten von webforJ zu bestehenden Spring-Anwendungen hinzuzufügen oder die Funktionen von Spring in neuen webforJ-Projekten zu nutzen.

Ihre Spring-Services, Repositories und Konfigurationen funktionieren wie gewohnt. Ihre webforJ-Komponenten können `@Autowired` beliebige Spring-Beans verwenden. [Spring Data](https://spring.io/projects/spring-data) Repositories verbinden sich direkt mit webforJ-Tabellen über `SpringDataRepository`. Die Entwicklung wird schneller mit automatischen Browseraktualisierungen von Spring DevTools und webforJ LiveReload.

Die Integration sorgt dafür, dass beide Frameworks das tun, was sie am besten können - Spring kümmert sich um Backend-Anliegen, während webforJ die UI verwaltet.

## Themen {#topics}

<DocCardList className="topics-section" />
