---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 5e7b116f0fea5cee2aa0d880d6fee05a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Tyhjät arkkityyppi

`blank` arkkityyppi on perustavanlaatuinen aloitusprojekti webforJ-sovelluksille. Tämä malli tarjoaa puhtaan alustan sovelluksesi rakentamiseen alusta alkaen. Se on ihanteellinen kehittäjille, jotka haluavat täydellisen hallinnan sovelluksensa rakenteeseen ja komponentteihin ilman ennalta määriteltyjä rajoituksia.

:::tip Käytä startforJ
Enemmän hallintaa mukauttamiseen ja konfigurointiin varten voit käyttää [startforJ](https://docs.webforj.com/startforj/) luodaksesi projektisi - valitse vain `Blank` arkkityyppi konfigurointivaihtoehtoja valitessasi.
:::

## Käyttäminen `blank` arkkityyppi {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## Sovelluksen suorittaminen {#running-the-app}

Ennen sovelluksen suorittamista asenna [esivaatimukset](../../introduction/prerequisites), jos et ole vielä tehnyt niin. 
Sitten siirry projektin juurihakemistoon ja suorita seuraava komento:

```bash
# standardille webforJ sovellukselle
mvn jetty:run

# webforJ + Spring Boot
mvn spring-boot:run
```

Kun palvelin on käynnissä, avaa selain ja siirry osoitteeseen [http://localhost:8080](http://localhost:8080) nähdäksesi sovelluksen.
