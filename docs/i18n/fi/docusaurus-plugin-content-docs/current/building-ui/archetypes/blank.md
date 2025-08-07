---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 135ed95be60a01a6a5ccb297c6bcce8f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Tyhjää arkkityyppiä

`blank` -arkkityyppi on perustaalo webforJ-sovelluksille. Tämä malli tarjoaa puhtaan alkuperän, josta voit rakentaa sovelluksesi alusta alkaen. Se on ihanteellinen kehittäjille, jotka haluavat täydellisen hallinnan sovelluksensa rakenteesta ja komponenteista ilman ennaltamääriteltyjä rajoituksia.

:::tip Käyttämällä startforJ
Saat lisää hallintaa mukauttamiseen ja konfiguroimiseen käyttämällä [startforJ](https://docs.webforj.com/startforj/) projektisi luomiseen - valitse vain `Blank` -arkkityyppi, kun valitset konfigurointivaihtoehtoja.
:::

## `blank` -arkkityypin käyttäminen {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## Sovelluksen ajaminen {#running-the-app}

Ennen sovelluksesi käynnistämistä asenna [esivaatimukset](../../introduction/prerequisites), jos et ole vielä tehnyt niin. 
Sitten siirry projektin juurihakemistoon ja suorita seuraava komento:

```bash
# standardille webforJ-sovellukselle
mvn jetty:run

# webforJ + Spring Boot
mvn spring-boot:run
```

Kun palvelin on käynnissä, avaa selain ja siirry osoitteeseen [http://localhost:8080](http://localhost:8080) nähdäksesi sovelluksen.
