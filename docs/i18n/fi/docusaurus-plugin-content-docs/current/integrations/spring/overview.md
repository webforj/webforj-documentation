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

Monet Java-tiimit käyttävät jo [Spring Bootia](https://spring.io/projects/spring-boot) sovellusten rakentamiseen. Springin integraatio webforJ:n kanssa mahdollistaa nyt webforJ:n käyttöliittymäkomponenttien lisäämisen olemassa oleviin Spring-sovelluksiin tai Springin ominaisuuksien käyttämisen uusissa webforJ-projekteissa.

Spring-palvelusi, reposi ja konfiguraatio toimivat normaalisti. WebforJ-komponenttisi voivat `@Autowired` käyttää mitä tahansa Spring-beania. [Spring Data](https://spring.io/projects/spring-data) -repositoriot yhdistyvät suoraan webforJ-taulukoihin `SpringDataRepositoryn` kautta. Kehitys nopeutuu automaattisella selaimen päivityksellä Spring DevToolsista ja webforJ LiveReloadista.

Integraatio pitää molemmat kehykset parhaimmillaan - Spring käsittelee taustahuolia, kun taas webforJ käsittelee käyttöliittymän.

## Topics {#topics}

<DocCardList className="topics-section" />
