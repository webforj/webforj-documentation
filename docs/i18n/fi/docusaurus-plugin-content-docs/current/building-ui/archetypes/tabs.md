---
title: Tabs
sidebar_position: 2
hide_table_of_contents: true
_i18n_hash: ba161760eed1006a71d42f2d566aff54
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Välilehtien arkkityyppi

`tabs` -aloitusprojekti tuottaa sovelluksen, jossa on yksinkertainen välilehtiliittymä. Ihanteellinen projekteille, jotka vaativat useita näkymiä tai osioita, jotka ovat saatavilla välilehtien kautta, tämä arkkityyppi tarjoaa tehokkaan ja järjestäytyneen tavan hallita sovelluksen eri osia, mikä tekee navigoinnista helppoa ilman käyttäjäliittymän tukkeutumista.

:::tip Käyttäen startforJ
Jos tarvitset enemmän hallintaa muokkaamiseen ja konfigurointiin, voit käyttää [startforJ](https://docs.webforj.com/startforj/) -sovelluksen luomiseen - valitse vain `Tabs` -arkkityyppi valitessasi konfigurointi vaihtoehtoja.
:::

## Välilehtien arkkityypin käyttäminen {#using-the-tabs-archetype}

<ComponentArchetype
project="tabs"
/>

## Sovelluksen suorittaminen {#running-the-app}

Ennen sovelluksesi suorittamista asenna [esivaatimukset](../../introduction/prerequisites), jos et ole vielä tehnyt niin. 
Siirry sitten projektin juurihakemistoon ja suorita seuraava komento:

```bash
# tavanomaiselle webforJ-sovellukselle
mvn jetty:run

# webforJ + Spring Bootille
mvn spring-boot:run
```

Kun palvelin on käynnissä, avaa selain ja siirry osoitteeseen [http://localhost:8080](http://localhost:8080) nähdäksesi sovelluksen.
