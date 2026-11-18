---
title: Spring
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Yhdistä webforJ:n käyttöliittymäkomponentit Spring Bootiin ja hyödynnä
  riippuvuuksien injektointia, Spring Data -repositorioita, mukautettuja scopeja
  ja reaaliaikaista uudelleenlatausta.
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

Monet Java-tiimeistä käyttää jo [Spring Bootia](https://spring.io/projects/spring-boot) sovellusten rakentamiseen. Springin integrointi webforJ:n kanssa mahdollistaa webforJ:n käyttöliittymäkomponenttien lisäämisen olemassa oleviin Spring-sovelluksiin tai Springin ominaisuuksien hyödyntämisen uusissa webforJ-projekteissa.

Spring-palvelusi, varastosi ja konfiguraatiosi toimivat normaalisti. WebforJ-komponenttisi voivat `@Autowired` mitä tahansa Spring-beania. [Spring Data](https://spring.io/projects/spring-data) varastot yhdistävät suoraan webforJ-tauluihin `SpringDataRepository`-kautta. Kehitys nopeutuu [live reload](/docs/configuration/deploy-reload/spring-devtools), joka päivittää selainta koodimuutosten myötä.

Integraatio pitää molemmat kehykset tekemässä sitä, mitä ne osaavat parhaiten - Spring käsittelee taustahuolia samalla kun webforJ hallitsee käyttöliittymää.

## Topics {#topics}

<DocCardList className="topics-section" />
