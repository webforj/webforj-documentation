---
title: Overview
hide_giscus_comments: true
sidebar_class_name: updated-content
_i18n_hash: 9dc213747204cb02b3b1624b39293445
---
Tämä vaihe vaiheelta -opas opastaa sinut asiakashallinta sovelluksen rakentamisen prosessissa käyttäen webforJ:ta ja Spring Bootia. Se opettaa sinulle, kuinka luodaan moderni, käyttäjäystävällinen käyttöliittymä asiakastietojen tarkasteluun, lisäämiseen ja muokkaamiseen.

Jokainen vaihe esittelee uusia käsitteitä ja johtaa toimivaan Spring Boot -sovellukseen (JAR). Voit käynnistää sovelluksesi paikallisesti käyttäen Mavenia ja vuorovaikuttaa sen kanssa verkkoselaimessa. Tämän asetuksen avulla saat nopean kehityssyklin ja tuotantovalmiin käyttöönotto-mallin käyttäen Spring Bootin upotettua palvelinta.

Et tarvitse aikaisempaa kokemusta Spring Bootista tai webforJ:sta, mutta sinulla tulisi olla perusymmärrys Javasta ja Mavenista, jotta saat eniten irti tästä oppaasta. Tämä opas kattaa Spring-käsitteitä niiden ilmestyessä, mutta niille, jotka ovat kiinnostuneita syvällisestä ymmärryksestä Springistä, kannattaa tutustua [Springin päädokumentaatioon](https://spring.io/learn) ja Springin dokumentaatioon [Spring Bootista](https://docs.spring.io/spring-boot/index.html).

## Opas käsitteet {#tutorial-concepts}

Oppaan ensimmäinen osa on omistettu [projektin määrittelylle](/docs/introduction/tutorial/project-setup) valmistelemaan Spring Boot + webforJ -ympäristöäsi. Sen jälkeen seuraavat vaiheet esittelevät uusia ominaisuuksia ja vievät projektiasi eteenpäin. Seuraamalla opasta saat selkeän käsityksen siitä, kuinka sovellus kehittyy, kun toteutat ominaisuuksia.

Jokaisella vaiheella on vastaava toimiva sovellus saatavilla GitHubissa:

| Vaihe | Dokumentaatio | GitHub |
| ----- | ----- | ----- |
| 1 | [Perussovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app)                               | [Vaihe 1 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)
| 2 | [Datan käsittely](/docs/introduction/tutorial/working-with-data)                                     | [Vaihe 2 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)
| 3 | [Reititys ja yhdisteet](/docs/introduction/tutorial/routing-and-composites)                           | [Vaihe 3 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)
| 4 | [Observerit ja reittiparametrit](/docs/introduction/tutorial/observers-and-route-parameters)           | [Vaihe 4 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)
| 5 | [Datan validoiminen ja sitominen](/docs/introduction/tutorial/validating-and-binding-data)                 | [Vaihe 5 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)

## Ehtoja {#prerequisites}

Sinulla tulisi olla seuraavat työkalut/resurssit kehityskoneellasi:

- Java 17 tai 21
- Maven
- Java IDE
- Git (suositeltavaa mutta ei pakollista)

:::info webforJ vaatimukset
Tutustu [vaatimukset-artikkeliin](/docs/introduction/prerequisites) saadaksesi tarkemman yleiskuvan vaadituista työkaluista kehitysympäristössäsi.
:::

<DocCardList className="topics-section" />
