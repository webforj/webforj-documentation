---
title: Tabs
sidebar_position: 2
hide_table_of_contents: true
_i18n_hash: bd6e6de9bb8396f7926e01ac2f34cfc3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Välilehdet arkkityyppi

Välilehdet-starter-projekti luo sovelluksen, jossa on yksinkertainen välilehtikäyttöliittymä. Ihanteellinen projekteihin, jotka vaativat useita näkymiä tai osioita, joihin pääsee nopeasti välilehtien avulla. Tämä arkkityyppi tarjoaa siistin ja järjestäytyneen tavan hallita sovelluksesi eri osia, mikä helpottaa navigointia eri osioiden välillä ilman käyttöliittymän kaaosta.

:::tip Käyttäen startforJ
Jos haluat enemmän hallintaa mukauttamiseen ja konfigurointiin, voit käyttää [startforJ](https://docs.webforj.com/startforj/) -työkalua projektisi luomiseen - valitse vain `Tabs` arkkityyppi, kun valitset konfigurointivaihtoehtoja.
:::

## Välilehdet-arkkityypin käyttö {#using-the-tabs-archetype}

<ComponentArchetype
project="tabs"
/>

## Sovelluksen ajaminen {#running-the-app}

Ennen kuin ajat sovellustasi, asenna [esivaatimukset](../../introduction/prerequisites), jos et ole vielä tehnyt niin. 
Siirry sitten projektin juurihakemistoon ja suorita seuraava komento:

```bash
# standardi webforJ-sovellus
mvn jetty:run

# webforJ + Spring Boot
mvn spring-boot:run
```

Kun palvelin on käynnissä, avaa selain ja siirry osoitteeseen [http://localhost:8080](http://localhost:8080), jotta voit katsella sovellusta.
