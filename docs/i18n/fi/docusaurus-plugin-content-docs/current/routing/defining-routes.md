---
sidebar_position: 3
title: Defining Routes
_i18n_hash: 6d7133c5636f63b82b13dd0a07a97620
---
Reittien määrittäminen on olennaista URL-osoitteiden yhdistämiseksi tiettyihin komponentteihin. Tämä mahdollistaa sen, että voit hallita, kuinka UI:n eri osat renderöidään URL-rakenteen perusteella. Kehys käyttää `@Route`-annotaatiota tämän prosessin tekemiseksi julkiseksi ja suoraviivaiseksi, vähentäen manuaalisen konfiguroinnin tarvetta.

:::info Reittien rekisteröinti
Reittejä voidaan rekisteröidä staattisesti käyttäen `@Route`-annotaatiota tai dynaamisesti `RouteRegistry`-rajapinnan kautta. Lisätietoja löytyy [Reittien rekisteröinti -dokumentaatiosta](./routes-registration).
:::

## Reitin määrittäminen käyttäen `@Route` {#defining-a-route-with-route}

`@Route`-annotaatiota käytetään URL-polun liittämiseen tiettyyn komponenttiin. Tämä mahdollistaa komponentin renderöinnin aina, kun sovellus navigoi kyseiseen URL-osoitteeseen. Tässä on yksinkertainen esimerkki:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponentin logiikka täällä
}
```

Tässä esimerkissä:
- `DashboardView`-komponentti on sidottu `/dashboard`-URL-osoitteeseen.
- Kun käyttäjä navigoi `/dashboard`-osoitteeseen, `DashboardView` renderöidään dynaamisesti kehyksen toimesta.

### `value`-parametri {#the-value-parameter}

`value`-parametri `@Route`-annotaatiossa määrittelee URL-polun. Tämä voi olla staattinen polku kuten `"dashboard"` tai dynaamisempi, jolloin se mahdollistaa joustavan reitityksen.

```java
@Route(value = "user/:id")
public class UserView extends Composite<Div> {
  // Komponentin logiikka täällä
}
```

Tässä tapauksessa navigointi `/user/123` näyttää `UserView`:n.

:::tip Reittimallit
`user/:id` tunnetaan reittimallina. Reititin voi käsitellä sekä yksinkertaisia malleja, jotka vastaavat yhtä staattista segmenttiä, että monimutkaisempia malleja, jotka voivat sisältää useita staattisia, pakollisia ja valinnaisia segmenttejä. Lisätietoja mallien määrittämisestä löytyy [syvällisestä käsittelystä reittimalleista](./route-patterns).
:::

## Reitti-aliasten määrittäminen {#defining-route-aliases}

Joissakin tapauksissa saatat haluta sallia useiden URL-osoitteiden osoittavan samaan komponenttiin. Esimerkiksi saatat haluta antaa käyttäjien päästä profiiliinsa joko `/profile`- tai `/user/me`-osoitteen kautta. webforJ mahdollistaa tämän **`@RouteAlias`**-annotaation kautta, jonka avulla voit määrittää useita aliaksia yhdelle reitille.

Tässä on esimerkki, jossa komponentti on saatavilla sekä `/profile`- että `/user/me`-osoitteen kautta:

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // Komponentin logiikka täällä
}
```

Reitti-aliasten määrittäminen lisää joustavuutta navigointisuunnittelussasi, jolloin käyttäjät voivat päästä samaan sisältöön eri URL-osoitteiden kautta.
