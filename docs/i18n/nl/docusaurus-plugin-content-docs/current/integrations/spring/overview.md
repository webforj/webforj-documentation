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

Veel Java-teams gebruiken al [Spring Boot](https://spring.io/projects/spring-boot) voor het bouwen van applicaties. De integratie van Spring met webforJ stelt je nu in staat om de UI-componenten van webforJ toe te voegen aan bestaande Spring-applicaties, of de functies van Spring te gebruiken in nieuwe webforJ-projecten.

Je Spring-diensten, repositories en configuratie werken zoals gebruikelijk. Je webforJ-componenten kunnen `@Autowired` elke Spring bean. [Spring Data](https://spring.io/projects/spring-data) repositories verbinden zich rechtstreeks met webforJ-tabellen via `SpringDataRepository`. De ontwikkeling verloopt sneller met automatische browserverversing van Spring DevTools en webforJ LiveReload.

De integratie zorgt ervoor dat beide frameworks doen waar ze het beste in zijn - Spring beheert backend-zaken terwijl webforJ de UI afhandelt.

## Topics {#topics}

<DocCardList className="topics-section" />
