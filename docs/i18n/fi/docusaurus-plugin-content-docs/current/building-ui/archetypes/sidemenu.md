---
title: SideMenu
sidebar_position: 3
hide_table_of_contents: true
_i18n_hash: c5fb775f5867b54eb53b0e1e63b90e20
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# SideMenu arkkityyppi
<!-- vale on -->

Projekteille, jotka tarvitsevat rakenteellista navigointijärjestelmää, `sidemenu` arkkityyppi on loistava aloituspaikka. Tämä arkkityyppi sisältää sivuvalikon ja sisältöalueen, ja se on suunniteltu auttamaan sinua luomaan sovelluksia, joissa on selkeä ja intuitiivinen navig Structures, mikä helpottaa käyttäjien löytämistä ja pääsyä sovelluksesi eri osiin.

:::tip Käyttäen startforJ
Jos haluat enemmän hallintaa mukauttamisessa ja konfiguroinnissa, voit käyttää [startforJ](https://docs.webforj.com/startforj/) -palvelua projektisi luomiseen - valitse vain `SideMenu` arkkityyppi, kun valitset konfigurointivaihtoehtoja.
:::

## Käyttäen `sidemenu` arkkityyppiä {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
/>

## Sovelluksen ajaminen {#running-the-app}

Ennen kuin ajoit sovellustasi, asenna [esivaatimukset](../../introduction/prerequisites), jos et ole vielä tehnyt niin. 
Siirry sitten projektin juurihakemistoon ja aja seuraava komento:

```bash
# tavanomaiselle webforJ-sovellukselle
mvn jetty:run

# webforJ + Spring Boot
mvn spring-boot:run
```

Kun palvelin on käynnissä, avaa selain ja siirry osoitteeseen [http://localhost:8080](http://localhost:8080) nähdäksesi sovelluksen.
