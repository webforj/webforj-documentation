---
title: HelloWorld
sidebar_position: 4
hide_table_of_contents: true
_i18n_hash: 145d1e89a5f688fa0c912b87056a35d1
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# HelloWorld-arkkitehtuuri
<!-- vale on -->

Tämä arkkitehtuuri luo yksinkertaisen hello world -sovelluksen, joka demonstroi käyttöliittymän (UI) rakentamisen perusteita webforJ:n avulla. Tämä malli on loistava aloittelijoille, jotka haluavat päästä vauhtiin nopeasti. Se tarjoaa selkeän esimerkin siitä, miten perustaa ja suorittaa yksinkertainen webforJ-sovellus, ja se on erinomainen lähtökohta uusille kehittäjille.

:::tip Aloittaminen tyhjältä
Tämä arkkitehtuuri luo minimalistisen sovelluksen, jossa on muutama komponentti ja hieman muotoilua. Kehittäjille, jotka haluavat luoda projektin vähäisellä rungolla, katso [`blank` arkkitehtuuria](./blank).
:::

:::tip Käyttäen startforJ:ta
Lisääntynyt hallinta mukauttamiseen ja konfigurointiin voidaan saavuttaa käyttämällä [startforJ:ta](https://docs.webforj.com/startforj/) projektin luomiseksi - valitse vain `HelloWorld` arkkitehtuuri konfigurointivaihtoehtoja valitessasi.
:::

## Käyttämällä `hello-world` arkkitehtuuria {#using-the-hello-world-archetype}

<ComponentArchetype
project="hello-world"
/>

## Sovelluksen suorittaminen {#running-the-app}

Ennen kuin suoritat sovelluksesi, asenna [vaatimukset](../../introduction/prerequisites), jos et ole vielä tehnyt niin. 
Siirry sitten projektin juuressa ja suorita seuraava komento:

```bash
# standardi webforJ-sovellus
mvn jetty:run

# webforJ + Spring Boot
mvn spring-boot:run
```

Kun palvelin on käynnissä, avaa selain ja siirry osoitteeseen [http://localhost:8080](http://localhost:8080) nähdäksesi sovelluksen.
