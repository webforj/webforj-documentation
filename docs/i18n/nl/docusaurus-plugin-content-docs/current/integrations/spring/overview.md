---
title: Spring
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Combineer webforJ UI-componenten met Spring Boot voor
  afhankelijkheidsinjectie, Spring Data-repositories, aangepaste scopes en live
  herladen.
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

Veel Java-teams gebruiken al [Spring Boot](https://spring.io/projects/spring-boot) voor het bouwen van applicaties. Spring-integratie met webforJ stelt je nu in staat om de UI-componenten van webforJ toe te voegen aan bestaande Spring-applicaties, of de functies van Spring te gebruiken in nieuwe webforJ-projecten.

Je Spring-services, -repositories en configuraties werken zoals normaal. Je webforJ-componenten kunnen `@Autowired` elke Spring-bean. [Spring Data](https://spring.io/projects/spring-data) repositories verbinden direct met webforJ-tabellen via `SpringDataRepository`. De ontwikkeling gaat sneller met [live reload](/docs/configuration/deploy-reload/spring-devtools), dat de browser vernieuwt terwijl je de code wijzigt.

De integratie houdt beide frameworks bezig met wat ze het beste doen - Spring behandelt backend-zaken terwijl webforJ de UI afhandelt.

## Topics {#topics}

<DocCardList className="topics-section" />
