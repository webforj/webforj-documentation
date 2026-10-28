---
title: Redeployointi ja Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Apply code changes to a running webforJ app during development, on the server
  through hotswap or a restart, and in the browser through live reload.
_i18n_hash: 243a55fe41bec8aeb4dfc9f70561d80c
---
Kehityksen aikana webforJ käyttää tallennettuja muutoksia juoksevaan sovellukseen ja päivittää selainta. Luokkamuutokset saavuttavat sovelluksen [hotswap-työkalun](/docs/configuration/deploy-reload/hotswap) tai uudelleenkäynnistyksen kautta. Live-päivitys päivittää selaimen kummankin jälkeen.

Arkkityypistä luodut projektit ovat valmiiksi konfiguroituja. Olemassa olevalle projektille seuraa [Spring Bootia](/docs/configuration/deploy-reload/spring-devtools), [Jettyä](/docs/configuration/deploy-reload/maven-jetty-plugin) tai [BBjServicesia](/docs/configuration/deploy-reload/bbjservices).

## Kuinka jokainen muutos vaikuttaa {#how-each-change-applies}

| Muutos | Tulos | Viite |
|---|---|---|
| Java-luokka, hotswap-työkalu liitetty | Luokka päivitetään juoksevaan sovellukseen. Vaikuttava osa sivusta rakennetaan uudelleen ja sovellustila pysyy. | [Hotswap](/docs/configuration/deploy-reload/hotswap) |
| Java-luokka, ilman hotswap-työkalua | Sovellus käynnistetään uudelleen. Selaimen päivitys tapahtuu, kun sovellus on valmis. | [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) |
| Java-luokka BBjServicesissa, ilman hotswap-työkalua | Muutos ei tule voimaan ennen kuin tarkkailu käynnistetään uudelleen. | [BBjServices](/docs/configuration/deploy-reload/bbjservices) |
| Tyylitiedosto tai kuva | Sivulle sovelletaan sitä paikan päällä, ilman uudelleenkäynnistystä. | [Asetukset](#settings) |
| Lähde `src/main/frontend`-kansiossa | Tarkkailu rakennetaan uudelleen ja selainta päivitetään. | [Frontend-tarkkailu](/docs/configuration/deploy-reload/frontend-watch) |

## Asetukset {#settings}

Nämä asetukset hallitsevat live-päivityksiä kehityksen aikana:

| Ominaisuus | Oletusarvo | Kuvaus |
|----------|---------|-------------|
| `webforj.devtools.livereload.enabled` | `false` | Aktivoidaan live-päivitys kehityssessioissa. |
| `webforj.devtools.livereload.websocket-port` | `35730` | Portti selainyhteydelle. |
| `webforj.devtools.livereload.websocket-path` | `/webforj-devtools-ws` | Polku selainyhteydelle. |
| `webforj.devtools.livereload.static-resources-enabled` | `true` | Soveltaa tyylitiedostoja ja kuvamuutoksia paikallaan sen sijaan, että sivu ladataan uudelleen. |
| `webforj.devtools.livereload.heartbeat-interval` | `30000` | Millisekuntiväli yhteyksien tarkistuksille, jotka havaitsevat uudelleenkäynnistyvän palvelimen. |

Avaimilla ei ole vaikutusta pakatussa sovelluksessa. Pakatut sovellukset eivät sisällä kehitystyökaluja.

## Aiheet {#topics}

<DocCardList className="topics-section" />
