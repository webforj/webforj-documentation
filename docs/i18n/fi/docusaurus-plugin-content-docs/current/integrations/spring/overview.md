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

Monet Java-tiimistä käyttää jo [Spring Bootia](https://spring.io/projects/spring-boot) sovellusten rakentamiseen. Springin integrointi webforJ:hin mahdollistaa webforJ:n käyttöliittymäkomponenttien lisäämisen olemassa oleviin Spring-sovelluksiin tai Springin ominaisuuksien käyttämisen uusissa webforJ-projekteissa.

Spring-palvelusi, varastot ja konfiguraatio toimivat normaalisti. WebforJ-komponenttisi voivat `@Autowired` mihin tahansa Spring-beaniin. [Spring Data](https://spring.io/projects/spring-data) -varastot yhdistävät suoraan webforJ-tauluihin `SpringDataRepositoryn` kautta. Kehitys nopeutuu automaattisella selainpäivityksellä [Spring DevToolsin ja webforJ LiveReloadin](/docs/configuration/deploy-reload/spring-devtools) avulla.

Integraatio pitää molemmat kehykset tekemässä parasta, mitä ne osaavat - Spring hoitaa taustahuoltoa, kun taas webforJ huolehtii käyttöliittymästä.

## Topics {#topics}

<DocCardList className="topics-section" />
