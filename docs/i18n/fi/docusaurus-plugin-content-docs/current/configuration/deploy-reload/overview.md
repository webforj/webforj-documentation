---
title: Uudelleenkäyttöönotto ja live-lataus
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Apply code changes to a running webforJ app during development, on the server
  through hotswap or a restart, and in the browser through live reload.
_i18n_hash: 1f91b81b074c81af64ded435e068729c
---
Kehityksen aikana webforJ soveltaa tallennettuja muutoksia käynnissä olevaan sovellukseen ja päivittää selaimen. Luokkamuutokset saavuttavat sovelluksen [hotswap-työkalun](/docs/configuration/deploy-reload/hotswap) tai uudelleenkäynnistyksen kautta. Live reload päivittää selaimen kummankin jälkeen.

Arkkiteetista luodut projektit ovat valmiiksi konfiguroituja. Olemassa olevalle projektille seuraa [Spring Boot](/docs/configuration/deploy-reload/spring-devtools) tai [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin).

## Miten kukin muutos soveltuu {#how-each-change-applies}

| Muutos | Tulos | Viite |
|---|---|---|
| Java-luokka, hotswap-työkalu liitetty | Luokka päivittyy käynnissä olevaan sovellukseen. Vaikuttava osa sivusta rakennetaan uudelleen ja sovellustila pysyy ennallaan. | [Hotswap](/docs/configuration/deploy-reload/hotswap) |
| Java-luokka, ei hotswap-työkalua | Sovellus käynnistetään uudelleen. Selaimeen ladataan sivu, kun sovellus on valmis. | [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) |
| Tyylitiedosto tai kuva | Sivulle sovelletaan se paikan päällä ilman latausta. | [Asetukset](#settings) |
| Lähde kansiossa `src/main/frontend` | Watch rakentaa sen uudelleen ja päivittää selaimen. | [Frontend watch](/docs/configuration/deploy-reload/frontend-watch) |

## Asetukset {#settings}

Nämä asetukset hallitsevat live reloadia kehityksen aikana:

| Ominaisuus | Oletusarvo | Kuvaus |
|----------|---------|-------------|
| `webforj.devtools.livereload.enabled` | `false` | Kytkee live reloadin päälle kehityskäytöissä. |
| `webforj.devtools.livereload.websocket-port` | `35730` | Portti selaimen yhteydelle. |
| `webforj.devtools.livereload.websocket-path` | `/webforj-devtools-ws` | Polku selaimen yhteydelle. |
| `webforj.devtools.livereload.static-resources-enabled` | `true` | Soveltaa tyylitiedosto- ja kuvamuutoksia paikan päällä ilman sivun lataamista. |
| `webforj.devtools.livereload.heartbeat-interval` | `30000` | Millisekunteina määritetty aikaväli yhteyden tarkistuksille, jotka havaitsevat uudelleenkäynnistyvän palvelimen. |

Avainasetuksilla ei ole vaikutusta pakatuissa sovelluksissa. Pakatuissa sovelluksissa ei ole kehitystyökaluja.

## Aiheet {#topics}

<DocCardList className="topics-section" />
