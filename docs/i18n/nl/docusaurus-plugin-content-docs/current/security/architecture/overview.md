---
sidebar_position: 1
title: Security Architecture
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: df2f795c6b65edc60adb39b549cb780b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Beveiligingsarchitectuur <DocChip chip='since' label='25.10' />

Het webforJ-beveiligingssysteem is gebouwd op een fundament van interfaces en ontwerppatronen die flexibele, uitbreidbare routebescherming mogelijk maken. Deze sectie legt uit hoe het fundamentele beveiligingskader functioneert en hoe je op maat gemaakte beveiligingsoplossingen kunt bouwen door deze interfaces te implementeren.

:::tip[Integratie met Spring]
De meeste applicaties zouden de [Spring Security-integratie](/docs/security/getting-started) moeten gebruiken, aangezien dit alles automatisch voor je configureert. Implementeer alleen aangepaste beveiliging als je specifieke vereisten hebt of als je geen gebruikmaakt van Spring Boot. De Spring-integratie is gebouwd op dezelfde fundamentele architectuur.
:::

Je leert over de kerninterfaces, het evaluator-ketenspatroon, hoe navigatie wordt onderschept en geëvalueerd, en verschillende benaderingen voor het opslaan van authenticatiestatus.

:::info[Focus op architectuur en uitbreidingspunten]
Deze gidsen leggen de fundamentele architectuur en uitbreidingspunten uit, de interfaces die je implementeert en hoe ze samenwerken. Voorbeeldcode laat **één mogelijke benadering zien**, geen voorschrijvende vereisten. Jouw implementatie kan verschillende opslagmechanismen gebruiken (JWT, database, LDAP), verschillende bedradingpatronen of verschillende authenticatiestromen op basis van jouw behoeften.
:::

## Wat je zult leren {#what-youll-learn}

- **Fundamentele architectuur**: De kerninterfaces die beveiligingsgedrag definiëren en hoe ze samenwerken
- **Navigatie-onderschepping**: Hoe het beveiligingssysteem navigatieverzoeken onderschept en toegangsregels evalueert
- **Evaluator-ketenspatroon**: Hoe beveiligingsregels in volgorde van prioriteit worden geëvalueerd met behulp van het keten van verantwoordelijkheidspatroon
- **Authenticatieopslag**: Verschillende benaderingen voor het opslaan van de authenticatiestatus van de gebruiker (sessies, JWT, database, enz.)
- **Volledige implementatie**: Een werkend voorbeeld dat alle componenten samenbrengt

## Voor wie dit bedoeld is {#who-this-is-for}

Deze gidsen zijn voor ontwikkelaars die willen:

- Aangepaste beveiligingsimplementaties bouwen voor niet-Spring-applicaties
- De fundamentele architectuur begrijpen om problemen op te lossen
- Aangepaste authenticatiestromen of autorisatielogica implementeren
- Beveiligingsevaluators creëren met domeinspecifieke logica
- Integreren met bestaande authenticatiesystemen (LDAP, OAuth, aangepaste backend)

## Vereisten {#prerequisites}

Voordat je in deze gidsen duikt, zou je moeten:

- De [Getting Started-gids](/docs/security/getting-started) hebben voltooid om beveiligingsconcepten te begrijpen
- Beveiligingsannotaties begrijpen vanuit de [Annotaties-gids](/docs/security/annotations)
- Bekend zijn met het keten van verantwoordelijkheid ontwerppatroon
- Ervaring hebben met Java-interfaces en erfelijkheid

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
