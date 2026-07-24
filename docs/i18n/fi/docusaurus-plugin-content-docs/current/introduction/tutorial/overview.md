---
title: Overview
description: >-
  Follow a six-step tutorial to build a customer management app with webforJ and
  Spring Boot, adding routing, data binding, and layout.
hide_giscus_comments: true
_i18n_hash: afa15cff5443cd3aab487ad1c8e8b0de
---
Tämä vaiheittainen opas ohjaa sinut asiakashallintasovelluksen rakentamisprosessin läpi käyttäen webforJ:ta ja Spring Bootia. Se opettaa sinulle, kuinka luoda moderni, käyttäjäystävällinen käyttöliittymä asiakastietojen tarkasteluun, lisäämiseen ja muokkaamiseen.

Jokainen vaihe esittelee uusia käsitteitä ja johtaa toimivaan Spring Boot -sovellukseen (JAR). Voit käynnistää sovelluksesi paikallisesti käyttäen Mavenia ja vuorovaikuttaa sen kanssa verkkoselaimessa. Tämän asetuksen avulla saat nopean kehityssyklin etupään pakkaamisen ja tuotantovalmiin käyttömallin avulla, hyödyntäen Spring Bootin sisäistä palvelinta.

Aikaisempaa kokemusta Spring Bootista tai webforJ:sta ei tarvita, mutta perusymmärrys Javasta ja Mavenista on hyödyllistä, jotta saat kaiken irti tästä oppaasta. Tämä opas kattaa Spring-käsitteitä niiden ilmaantuessa, mutta niille, jotka ovat kiinnostuneita syvällisestä ymmärryksestä Springistä, kannattaa vierailla [Springin päädokumentaatiossa](https://spring.io/learn) ja Springin dokumentaatiossa [Spring Bootista](https://docs.spring.io/spring-boot/index.html).

## Tutorial concepts {#tutorial-concepts}

Oppaan ensimmäinen osa on omistettu [projektin asetukselle](/docs/introduction/tutorial/project-setup) valmistelemaan Spring Boot + webforJ -ympäristöäsi. Seuraavat vaiheet esittelevät uusia ominaisuuksia ja edistävät projektiasi. Seuraamalla mukana saat selkeän käsityksen siitä, kuinka sovellus kehittyy, kun toteutat ominaisuuksia.

Jokaisella vaiheella on vastaava toimiva sovellus saatavilla GitHubissa:

| Vaihe | Dokumentaatio | GitHub |
| ----- | ----- | ----- |
| 1 | [Perussovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app)                               | [Vaiheen 1 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)
| 2 | [Datan käsittely](/docs/introduction/tutorial/working-with-data)                                     | [Vaiheen 2 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)
| 3 | [Reititys ja yhdistelmät](/docs/introduction/tutorial/routing-and-composites)                           | [Vaiheen 3 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)
| 4 | [Observerit ja reitti-parametrit](/docs/introduction/tutorial/observers-and-route-parameters)           | [Vaiheen 4 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)
| 5 | [Datan validoiminen ja sitominen](/docs/introduction/tutorial/validating-and-binding-data)                 | [Vaiheen 5 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)
| 6 | [Sovelluksen käyttöliittymän integrointi](/docs/introduction/tutorial/integrating-an-app-layout)                     | [Vaiheen 6 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout)

## Prerequisites {#prerequisites}

Sinulla tulisi olla seuraavat työkalut/resurssit kehityskoneellasi:

- Java 21 tai 25
- Maven
- Java IDE
- Git (suositeltava mutta ei pakollinen)

:::info webforJ prerequisites
Tutustu [esivaatimusten artikkeliin](/docs/introduction/prerequisites) saadaksesi tarkemman yleiskuvan kehitysympäristöösi vaadituista työkaluista.
:::

<DocCardList className="topics-section" />
