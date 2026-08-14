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

Monet Java-tiimeistä käyttää jo [Spring Bootia](https://spring.io/projects/spring-boot) sovellusten rakentamiseen. Springin integrointi webforJ:n kanssa mahdollistaa webforJ:n käyttöliittymäkomponenttien lisäämisen olemassa oleviin Spring-sovelluksiin tai Springin ominaisuuksien hyödyntämisen uusissa webforJ-projekteissa.

Spring-palvelusi, varastosi ja konfiguraatiosi toimivat normaalisti. WebforJ-komponenttisi voivat `@Autowired` mitä tahansa Spring-beania. [Spring Data](https://spring.io/projects/spring-data) varastot yhdistävät suoraan webforJ-tauluihin `SpringDataRepository`-kautta. Kehitys nopeutuu [live reload](/docs/configuration/deploy-reload/spring-devtools), joka päivittää selainta koodimuutosten myötä.

Integraatio pitää molemmat kehykset tekemässä sitä, mitä ne osaavat parhaiten - Spring käsittelee taustahuolia samalla kun webforJ hallitsee käyttöliittymää.

## Topics {#topics}

<DocCardList className="topics-section" />
