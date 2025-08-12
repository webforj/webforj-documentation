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

Veel Java-teams gebruiken al [Spring Boot](https://spring.io/projects/spring-boot) om applicaties te bouwen. De integratie van Spring met webforJ stelt je nu in staat om de UI-componenten van webforJ toe te voegen aan bestaande Spring-applicaties, of om de functies van Spring te gebruiken in nieuwe webforJ-projecten.

Je Spring-diensten, repositories en configuratie werken zoals gewoonlijk. Je webforJ-componenten kunnen `@Autowired` elke Spring-bean. [Spring Data](https://spring.io/projects/spring-data) repositories verbinden zich rechtstreeks met webforJ-tabellen via `SpringDataRepository`. De ontwikkeling gaat sneller met automatische browserrefresh vanuit Spring DevTools en webforJ LiveReload.

De integratie laat beide frameworks doen waar ze het beste in zijn - Spring behandelt backend-zaken terwijl webforJ de UI afhandelt.

## Topics {#topics}

<DocCardList className="topics-section" />
