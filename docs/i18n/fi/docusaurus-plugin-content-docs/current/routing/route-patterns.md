---
sidebar_position: 5
title: Route Patterns
_i18n_hash: d18952e5072af2c542c1459e3f65d787
---
**Reittimallit** määrittelevät, miten URL-osoitteet vastaavat tiettyjä näkymiä, mukaan lukien dynaamiset ja valinnaiset osiot, säännölliset lausekkeet ja jokerimerkit. Reittimallit mahdollistavat kehyksen URL-osoitteiden vertailun, parametrien poimimisen ja URL-osoitteiden dynaamisen generoinnin. Ne näyttelevät kriittistä roolia sovelluksen navigoinnin ja komponenttien renderöinnin rakenteessa selainikkunan sijainnin mukaan.

## Reittimallin syntaksi {#route-pattern-syntax}

Reittimallit webforJ:ssä ovat erittäin joustavia, ja ne tukevat seuraavia ominaisuuksia:

- **Nimetyt Parametrit:** Merkitty `:paramName`, ne ovat pakollisia, ellei niitä ole merkitty valinnaisiksi.
- **Valinnaiset Parametrit:** Merkitty `:paramName?`, ne voidaan jättää pois URL-osoitteesta.
- **Jokerimerkki-osat:** Esitetty `*`, ne tallentavat kaikki loput URL-osoitteen osat.
- **Säännölliset lausekkeen rajoitukset:** Rajoituksia voidaan lisätä vain nimettyihin parametreihin (esimerkiksi `:id<[0-9]+>`).

### Esimerkki reittimallin määritelmistä {#example-of-route-pattern-definitions}

```java
@Route("customer/:id<[0-9]+>/named/:name/*")
public class CustomerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    int id = parameters.getInt("id").orElse(0);
    String name = parameters.getAlpha("name").orElse("Tuntematon");
    String extra = parameters.getAlpha("*").orElse("");

    String result =
        "Asiakkaan ID: " + id + "-" +
        "Nimi: " + name + "-" +
        "*: " + extra;

    console().log(result);
  }
}
```

Tässä esimerkissä:

- `:id<[0-9]+>` tallentaa numeerisen asiakastunnuksen.
- `:name` tallentaa nimen.
- `*` tallentaa kaikki ylimääräiset polkuosat `named/:name` jälkeen.

## Nimetyt parametrit {#named-parameters}

Nimetyt parametrit määritellään lisäämällä kaksoispiste `:` parametrin nimen eteen reitissä. Ne ovat pakollisia, elleivät ne ole merkitty valinnaisiksi. Nimettömiin parametreihin voidaan myös lisätä säännöllisiä lausekkeen [rajoituksia](#regular-expression-constraints) arvojen validoimiseksi.

### Esimerkki: {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // Komponentin logiikka täällä
}
```

Tämä malli vastaa URL-osoitteita kuten `/product/123`, jossa `id` on `123`.

## Valinnaiset parametrit {#optional-parameters}

Valinnaiset parametrit merkitään lisäämällä `?` parametrin nimen perään. Nämä osiot eivät ole pakollisia ja ne voidaan jättää pois URL-osoitteesta.

### Esimerkki: {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("Tilauksen ID: " + id),
      () -> console().log("Ei tilauksen ID:tä annettu")
    );
  }
}
```

Tämä malli vastaa sekä `/order/123`, joka sisältää numeerisen arvon, että `/order`, mikä mahdollistaa numeerisen arvon jättämisen pois, kun `/order` syötetään.

## Säännöllisen lausekkeen rajoitukset {#regular-expression-constraints}

Voit soveltaa säännöllisiä lausekkeen rajoituksia parametreihin lisäämällä ne kulmasulkuihin `<>`. Tämä mahdollistaa tiukempien vastaussääntöjen määrittämisen parametreille.

### Esimerkki: {#example-2}

```java
@Route("product/:code<[A-Z]{3}-[0-9]{4}>")
public class ProductView extends Composite<FlexLayout> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("code").ifPresentOrElse(
      code -> console().log("Tuotteen koodi: " + code),
      () -> console().error("Tuotteen koodia ei löytynyt"));
  }
}
```

Tämä malli vastaa vain tuotekoodeja muodossa `ABC-1234`. Esimerkiksi `/product/XYZ-5678` vastaa, mutta `/product/abc-5678` ei.

## Jokerimerkki-osat {#wildcard-segments}

Jokerimerkkejä voidaan käyttää koko polun tallentamiseen tietyn reittiosan jälkeen, mutta ne voivat esiintyä vain mallin viimeisenä osana, joka käsittelee kaikki jäljellä olevat arvot URL-osoitteessa. Luettavuuden parantamiseksi jokerimerkki-osat voidaan nimetä. Toisin kuin nimetyillä parametreilla, jokerimerkki-osilla ei voi olla rajoituksia.

### Esimerkki: {#example-3}

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView: " + pathname),
      () -> console().log("FileManagerView: Ei pathname-parametria")
    );
  }
}
```

Tämä malli vastaa mitä tahansa URL-osoitetta, joka alkaa `/files` ja tallentaa loput polusta jokerimerkinä.

## Reittiprioriteetti {#route-priority}

Kun useat reitit vastaavat annettua URL-osoitetta, reitin prioriteetti määrittelee, mikä reitti valitaan ensinnäkin. Tämä on erityisen hyödyllistä, kun kaksi tai useampi reitti päällekkäin polkujen malleissa, ja tarvitset tavan hallita, mikä niistä saa etusijan. Prioriteettiominaisuus on saatavilla sekä `@Route` että `@RouteAlias` annotaatioissa.

### Kuinka prioriteettijärjestelmä toimii {#how-the-priority-system-works}

Prioriteettiominaisuus mahdollistaa reitinvaihtajan määrittää järjestyksen, jossa reittejä arvioidaan, kun useat reitit voisivat vastata annettuun URL-osoitteeseen. Reitit lajitellaan prioriteettiarvojensa mukaan, ja suurempi prioriteetti (alhaisemmat numeraaliset arvot) vastataan ensin. Tämä varmistaa, että tarkemmat reitit saavat etusijan yleisempiin verrattuna.

Jos kaksi reittiä jakavat saman prioriteetin, reitinvaihtaja ratkaisee konfliktin valitsemalla ensin rekisteröidyn reitin. Tämä mekanismi varmistaa, että oikea reitti valitaan, vaikka useat reitit päällekkäisyyksissä URL-malleissaan.

:::info Oletusarvoinen prioriteetti  
Oletusarvoisesti kaikille reiteille on määritelty prioriteetti `10`.  
:::

### Esimerkki: Konfliktireitit {#example-conflicting-routes}

Kuvittele skenaariota, jossa kaksi reittiä vastaavat samanlaisia URL-malleja:

```java
@Route(value = "products/:category", priority = 9)
public class ProductCategoryView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String category = parameters.get("category").orElse("tuntematon");
    console().log("Näytetään kategoria: " + category);
  }
}

@Route(value = "products/:category/:productId?<[0-9]+>")
public class ProductView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String productId = parameters.get("productId").orElse("tuntematon");
    console().log("Näytetään tuote: " + productId);
  }
}
```

Tässä on, miten prioriteettijärjestelmä auttaa ratkaisemaan konflikteja:

- **`ProductCategoryView`** vastaa URL-osoitteita kuten `/products/electronics`.
- **`ProductView`** vastaa tarkemmille URL-osoitteille kuten `/products/electronics/123`, jossa `123` on tuotetunnus.

Tässä tapauksessa molemmat reitit voisivat vastata URL-osoitetta `/products/electronics`. Kuitenkin, koska `ProductCategoryView`:lla on korkeampi prioriteetti (prioriteetti = 9), se valitaan ensin, kun URL-osoitteesta ei löydy `productId`. URL-osoitteille kuten `/products/electronics/123`, `ProductView` valitaan `productId`-parametrin olemassaolon vuoksi.
