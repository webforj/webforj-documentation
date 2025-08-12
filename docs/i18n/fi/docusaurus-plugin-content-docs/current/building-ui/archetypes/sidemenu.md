---
title: SideMenu
sidebar_position: 3
hide_table_of_contents: true
_i18n_hash: 0d0c302e47e1711d573c9bf6860547ae
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# SideMenu-malli
<!-- vale on -->

Projekteille, jotka tarvitsevat jäsenneltyä navigointijärjestelmää, `sidemenu`-malli on erinomainen lähtökohta. Tämä malli sisältää sivupalkin ja sisältöalueen, ja se on suunniteltu auttamaan sinua luomaan sovelluksia, joissa on selkeä ja intuitiivinen navigointirakenne, mikä helpottaa käyttäjien löytämistä ja pääsyä erilaisiin osiin sovellustasi.

:::tip Käyttäen startforJ
Lisäämällä mukauttamista ja konfigurointia, voit käyttää [startforJ](https://docs.webforj.com/startforj/) -sovellusta projektisi luomiseen - valitse vain `SideMenu`-malli, kun valitset konfigurointivaihtoehtoja.
:::

## Käyttäen `sidemenu`-mallia {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
/>

## Sovelluksen ajaminen {#running-the-app}

Ennen sovelluksen ajamista, asenna [esivaatimukset](../../introduction/prerequisites), jos et ole vielä tehnyt niin. 
Sitten siirry projektin pääkansioon ja suorita seuraava komento:

```bash
# standardille webforJ sovellukselle
mvn jetty:run

# webforJ + Spring Boot
mvn spring-boot:run
```

Kun palvelin on käynnissä, avaa selaimesi ja mene osoitteeseen [http://localhost:8080](http://localhost:8080) nähdäksesi sovelluksen.
