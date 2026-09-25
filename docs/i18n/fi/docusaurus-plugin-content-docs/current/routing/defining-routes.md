---
sidebar_position: 3
title: Reittien määrittäminen
_i18n_hash: 4f7189d5ef27386506e9ecf950f145ed
---
Reittien määrittäminen on olennaista URL-osoitteiden yhdistämiseksi tiettyihin komponentteihin. Tämä mahdollistaa sen hallitsemisen, kuinka eri osat käyttöliittymästäsi renderöidään URL-rakenteen perusteella. Kehys käyttää `@Route`-annotaatiota tehdäksesi tämän prosessin deklaratiiviseksi ja suoraviivaiseksi, mikä vähentää manuaalisen konfiguroinnin tarvetta.

:::info Reittien rekisteröinti
Reittejä voidaan rekisteröidä staattisesti käyttämällä `@Route`-annotaatiota tai dynaamisesti `RouteRegistry`-API:n kautta. Lisätietoja saat [Reittien rekisteröinti -dokumentaatiosta](./routes-registration).
:::

## Reitin määrittäminen `@Route`:lla {#defining-a-route-with-route}

`@Route`-annotaatiota käytetään URL-polun sitomiseen tiettyyn komponenttiin. Tämä mahdollistaa komponentin renderöinnin aina, kun sovellus navigoi kyseiselle URL-osoitteelle. Tässä on yksinkertainen esimerkki:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponenttilogiikka täällä
}
```

Tässä esimerkissä:
- `DashboardView`-komponentti on sidottu `/dashboard`-URL-osoitteeseen.
- Kun käyttäjä navigoi `/dashboard`, `DashboardView` renderöidään dynaamisesti kehyksen toimesta.

### `value`-parametri {#the-value-parameter}

`@Route`-annotaatiossa oleva `value`-parametri määrittää URL-polun. Tämä voi olla staattinen polku, kuten `"dashboard"`, tai enemmän dynaaminen, mikä sallii joustavan reitittämisen.

```java
@Route(value = "user/:id")
public class UserView extends Composite<Div> {
  // Komponenttilogiikka täällä
}
```

Tässä tapauksessa navigointi `/user/123` näyttää `UserView`-komponentin.

:::tip Reittimallit
`user/:id` tunnetaan reittimallina. Reititin voi käsitellä sekä yksinkertaisia malleja, jotka vastaavat yhtä staattista segmenttiä, että monimutkaisempia malleja, jotka voivat vastata useita staattisia, pakollisia ja valinnaisia segmenttejä. Lisätietoja mallien konfiguroinnista saat [syvällisestä katsauksesta reittimalleihin](./route-patterns).
:::

## Reitti aliasien määrittäminen {#defining-route-aliases}

Jotkin tapaukset saattavat vaatia, että haluat antaa useiden URL-osoitteiden osoittaa samaan komponenttiin. Esimerkiksi saatat haluta käyttäjien pääsevän profiiliinsa joko `/profile` tai `/user/me` kautta. webforJ mahdollistaa tämän **`@RouteAlias`**-annotaation avulla, mikä sallii useiden aliasien määrittäminen yhdelle reitille.

Tässä on esimerkki, jossa komponentti on saavutettavissa sekä `/profile` että `/user/me` kautta:

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // Komponenttilogiikka täällä
}
```

Reitti aliasien määrittäminen lisää joustavuutta navigointisuunnittelussasi, jolloin käyttäjät voivat päästä samaan sisältöön eri URL-osoitteiden kautta.
