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

Monet Java-tiimeistä käyttää jo [Spring Bootia](https://spring.io/projects/spring-boot) sovellusten rakentamiseen. Springin integraatio webforJ:n kanssa mahdollistaa webforJ:n käyttöliittymäkomponenttien lisäämisen olemassa oleviin Spring-sovelluksiin tai Springin ominaisuuksien käyttämisen uusissa webforJ-projekteissa.

Spring-palvelusi, -varastosi ja -konfiguraatiosi toimivat normaalisti. WebforJ-komponenttisi voivat `@Autowired` käyttää mitä tahansa Spring-beania. [Spring Data](https://spring.io/projects/spring-data) -varastot yhdistyvät suoraan webforJ-tauluihin käyttämällä `SpringDataRepository`:ta. Kehitys nopeutuu automaattisella selaimen päivityksellä Spring DevToolsin ja webforJ LiveReloadin avulla.

Integraatio pitää molemmat kehykset tekemässä sitä, missä ne ovat parhaita - Spring hoitaa taustahuoltoja, kun taas webforJ hoitaa käyttöliittymän.

## Topics {#topics}

<DocCardList className="topics-section" />
