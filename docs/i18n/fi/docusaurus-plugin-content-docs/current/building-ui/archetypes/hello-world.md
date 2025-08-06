---
title: HelloWorld
sidebar_position: 4
hide_table_of_contents: true
_i18n_hash: e1da494f783aca68616cd374b92e700c
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# HelloWorld arkkityyppi
<!-- vale on -->

Tämä arkkityyppi luo yksinkertaisen hello world -sovelluksen, jotta voidaan demonstroida käyttöliittymän perustaitoja webforJ:llä. Tämä malli on loistava aloittelijoille, jotka haluavat aloittaa nopeasti. Se tarjoaa yksinkertaisen esimerkin siitä, kuinka perustaa ja ajaa yksinkertainen webforJ-sovellus, mikä tekee siitä erinomaisen lähtökohdan uusille kehittäjille.

:::tip Aloittaminen tyhjältä
Tämä arkkityyppi luo minimalisen sovelluksen, jossa on muutama komponentti ja hieman tyylitystä. Kehittäjille, jotka haluavat luoda projektin vähäisellä rakennekuvalla, katso [`blank` arkkityyppi](./blank).
:::

:::tip Käyttämällä startforJ
Jos haluat enemmän kontrollia mukauttamiseen ja konfigurointiin, voit käyttää [startforJ](https://docs.webforj.com/startforj/) -projektin luomiseen - valitse vain `HelloWorld` arkkityyppi konfigurointivaihtoehtoja valitessasi.
:::

## Käyttäen `hello-world` arkkityyppiä {#using-the-hello-world-archetype}

<ComponentArchetype
project="hello-world"
/>

## Sovelluksen ajaminen {#running-the-app}

Ennen kuin suoritat sovelluksesi, asenna [esivaatimukset](../../introduction/prerequisites), jos et ole vielä tehnyt niin. 
Tämän jälkeen siirry projektin juurihakemistoon ja suorita seuraava komento:

```bash
# standardi webforJ sovellus
mvn jetty:run

# webforJ + Spring Boot
mvn spring-boot:run
```

Kun palvelin on käynnissä, avaa selain ja siirry osoitteeseen [http://localhost:8080](http://localhost:8080) nähdäksesi sovelluksen.
