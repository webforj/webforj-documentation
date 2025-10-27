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

Veel Java-teams gebruiken al [Spring Boot](https://spring.io/projects/spring-boot) voor het bouwen van applicaties. De integratie van Spring met webforJ stelt je nu in staat om de UI-componenten van webforJ toe te voegen aan bestaande Spring-applicaties, of om de functies van Spring te gebruiken in nieuwe webforJ-projecten.

Je Spring-diensten, repositories en configuraties werken zoals normaal. Je webforJ-componenten kunnen `@Autowired` elke Spring-bean. [Spring Data](https://spring.io/projects/spring-data) repositories verbinden direct met webforJ-tabellen via `SpringDataRepository`. De ontwikkeling wordt versneld met automatische browserverversing van [Spring DevTools en webforJ LiveReload](/docs/configuration/deploy-reload/spring-devtools).

De integratie houdt beide frameworks bezig met waar ze het beste in zijn - Spring behandelt backend-zaken terwijl webforJ de UI behandelt.

## Topics {#topics}

<DocCardList className="topics-section" />
