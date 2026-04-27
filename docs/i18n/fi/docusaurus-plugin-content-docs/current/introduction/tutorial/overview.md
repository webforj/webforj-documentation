---
title: Overview
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 781bf0258ed2366e2125e99587cda439
---
Tämä vaiheittainen opas ohjaa sinua asiakashallintasovelluksen rakentamisessa käyttäen webforJ:ta ja Spring Bootia. Se opettaa sinulle, kuinka luoda moderni ja käyttäjäystävällinen käyttöliittymä asiakastietojen tarkasteluun, lisäämiseen ja muokkaamiseen.

Jokainen vaihe esittelee uusia käsitteitä ja johtaa toimivaan Spring Boot -sovellukseen (JAR). Voit käynnistää sovelluksesi paikallisesti käyttämällä Mavenia ja käyttää sitä verkkoselaimessa. Tämän asetuksen avulla saat nopean kehityssyklin ja tuotanto-valmiin käyttöönotto-mallin hyödyntäen Spring Bootin upotettua palvelinta.

Aikaisempaa Spring Boot tai webforJ -kokemusta ei tarvita, mutta sinulla tulisi olla perustiedot Java-kielestä ja Mavenista saadaksesi parhaan hyödyn tästä oppaasta. Tämä opas kattaa Spring-käsitteitä niiden esiintyessä, mutta ne, jotka ovat kiinnostuneita syvällisestä ymmärryksestä Springistä, voivat käydä [Springin päädokumentaatiossa](https://spring.io/learn) ja Spring Bootin dokumentaatiossa [Spring Boot](https://docs.spring.io/spring-boot/index.html).

## Opas käsitteet {#tutorial-concepts}

Oppaan ensimmäinen osa on omistettu [projektin asetukselle](/docs/introduction/tutorial/project-setup) valmistellaksesi Spring Boot + webforJ -ympäristön. Sen jälkeen seuraavat vaiheet esittelevät uusia ominaisuuksia ja vievät projektiasi eteenpäin. Seuraamalla mukana saat selkeän käsityksen siitä, kuinka sovellus kehittyy, kun toteutat ominaisuuksia.

Jokaisella vaiheella on vastaava toimiva sovellus saatavilla GitHubissa:

| Vaihe | Dokumentaatio | GitHub |
| ----- | ----- | ----- |
| 1 | [Perus sovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app)                               | [Vaihe 1 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)
| 2 | [Datan käsittely](/docs/introduction/tutorial/working-with-data)                                     | [Vaihe 2 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)
| 3 | [Reititys ja yhdistelmät](/docs/introduction/tutorial/routing-and-composites)                           | [Vaihe 3 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)
| 4 | [Observerit ja reittiparametrit](/docs/introduction/tutorial/observers-and-route-parameters)           | [Vaihe 4 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)
| 5 | [Datan validoiminen ja sitominen](/docs/introduction/tutorial/validating-and-binding-data)                 | [Vaihe 5 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)
| 6 | [Sovelluksen asettelun integrointi](/docs/introduction/tutorial/integrating-an-app-layout)                     | [Vaihe 6 sovellus](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout)

## Esivaatimukset {#prerequisites}

Sinulla tulisi olla seuraavat työkalut/resurssit kehityskoneellasi:

- Java 21 tai 25
- Maven
- Java IDE
- Git (suositeltava mutta ei pakollinen)

:::info webforJ esivaatimukset
Tarkista [esivaatimukset-artikkeli](/docs/introduction/prerequisites) saadaksesi tarkemman yleiskuvan vaadituista työkaluista kehitysympäristössäsi.
:::

<DocCardList className="topics-section" />
