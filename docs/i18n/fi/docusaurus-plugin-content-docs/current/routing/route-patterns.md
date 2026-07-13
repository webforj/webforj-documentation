---
sidebar_position: 5
title: Route Patterns
description: >-
  Define dynamic URL segments, optional parameters, wildcards, and regex
  constraints to match webforJ routes precisely.
_i18n_hash: a6c1267e034c1562652cc01d0f336640
---
**Reittimallit** määrittävät, kuinka URL-osoitteet vastaavat tiettyjä näkymiä, mukaan lukien dynaamiset ja valinnaiset segmentit, säännönmukaiset lausekkeet ja wildcardit. Reittimallit mahdollistavat kehyksen ottavan URL-osoitteita huomioon, parametreja erottavan ja URL-osoitteiden dynaamisen luomisen. Ne ovat keskeisessä roolissa sovelluksen navigoinnin ja komponenttien renderöinnin jäsentelyssä selaimen sijainnin mukaan.

## Reittimallin syntaksi {#route-pattern-syntax}

Reittimallit webforJ:ssä ovat erittäin joustavia ja tukevat seuraavia ominaisuuksia:

- **Nimetyt parametrit:** Merkitty `:paramName`, ne ovat pakollisia, ellei niitä ole merkitty valinnaisiksi.
- **Valinnaiset parametrit:** Merkitty `:paramName?`, ne voidaan jättää pois URL-osoitteesta.
- **Wildcard-segmentit:** Merkitty `*`, ne sieppaavat kaikki jäljellä olevat segmentit URL-osoitteessa.
- **Säännönmukaiset lausekkeet rajoitukset:** Rajoituksia voidaan lisätä vain nimettyihin parametreihin (esimerkiksi `:id<[0-9]+>`).

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
        "Asiakas ID: " + id + "-" +
        "Nimi: " + name + "-" +
        "*: " + extra;

    console().log(result);
  }
}
```

Tässä esimerkissä:

- `:id<[0-9]+>` sieppaa numeerisen asiakas-ID:n.
- `:name` sieppaa nimen.
- `*` sieppaa kaikki lisäpolku-segmentit `named/:name` jälkeen.

## Nimettävät parametrit {#named-parameters}

Nimettävät parametrit määritellään lisäämällä kaksipiste `:` parametrin nimen eteen mallissa. Ne ovat pakollisia, ellei niitä ole merkitty valinnaisiksi. Nimettävillä parametreilla voi myös olla säännönmukaisia lauseke [rajoituksia](#regular-expression-constraints) arvojen vahvistamiseksi.

### Esimerkki: {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // Komponenttilogikka täällä
}
```

Tämä malli vastaa URL-osoitteita kuten `/product/123`, missä `id` on `123`.

## Valinnaiset parametrit {#optional-parameters}

Valinnaiset parametrit merkitään lisäämällä `?` parametrin nimen perään. Näitä segmenttejä ei vaadita ja ne voidaan jättää pois URL-osoitteesta.

### Esimerkki: {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("Tilauksen ID: " + id),
      () -> console().log("Tilauksen ID:ta ei annettu")
    );
  }
}
```

Tämä malli vastaa sekä `/order/123`, joka sisältää numeerisen arvon, että `/order`, jolloin numeerinen arvo voidaan jättää pois kun syötetään `/order`.

## Säännönmukaiset lausekkeet rajoitukset {#regular-expression-constraints}

Voit soveltaa säännönmukaisia lauseke rajoituksia parametreihin lisäämällä ne kulmarakenteisiin `<>`. Tämä mahdollistaa tiukempien vastaavien sääntöjen määrittämisen parametreille.

### Esimerkki: {#example-2}

```java
@Route("product/:code<[A-Z]{3}-[0-9]{4}>")
public class ProductView extends Composite<FlexLayout> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("code").ifPresentOrElse(
      code -> console().log("Tuotekoodi: " + code),
      () -> console().error("Tuotekoodia ei löytynyt"));
  }
}
```

Tämä malli vastaa vain tuotekoodin formaattia `ABC-1234`. Esimerkiksi `/product/XYZ-5678` vastaa, mutta `/product/abc-5678` ei.

## Wildcard-segmentit {#wildcard-segments}

Wildcardit voidaan käyttää sieppaamaan koko polku, joka seuraa tiettyä reittisegmenttiä, mutta ne voivat esiintyä vain mallin loppusegmenttinä, ratkaisten kaikki seuraavat arvot URL-osoitteessa. Parempaa luettavuutta varten wildcards-segmenttejä voidaan nimetä. Kuitenkin, toisin kuin nimettävät parametrit, wildcard-segmenteillä ei voi olla rajoituksia.

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

Tämä malli vastaa mitä tahansa URL-osoitetta, joka alkaa `/files` ja sieppaa loput polusta wildcardina.

## Reittien prioriteetti {#route-priority}

Kun useat reitit vastaavat tiettyä URL-osoitetta, reitin prioriteetti määrittää, mikä reitti valitaan ensin. Tämä on erityisen hyödyllistä, kun kaksi tai useampi reitti päällekkäin niiden polkumalleissa, ja tarvitaan tapa hallita, mikä saa etusijan. Prioriteettiattribuutti on käytettävissä sekä `@Route` että `@RouteAlias` -annotationeissa.

### Miten prioriteetijärjestelmä toimii {#how-the-priority-system-works}

Prioriteettiattribuutti mahdollistaa reitittimen määrittää, missä järjestyksessä reittejä arvioidaan, kun useat reittejä voivat vastata tiettyä URL-osoitetta. Reitit lajitellaan prioriteettiarvojensa mukaan, ja korkeampi prioriteetti (alemmat numeeriset arvot) vastaa ensin. Tämä varmistaa, että spesifimmät reitit saavat etusijan yleisempiin verrattuna.

Jos kaksi reittiä jakaa saman prioriteetin, reititin ratkaisee konfliktin valitsemalla ensin rekisteröidyn reitin. Tämä mekanismi varmistaa, että oikea reitti valitaan, vaikka useat reitit päällekkäin niiden URL-malleissa.

:::info Oletusarvoinen prioriteetti
Oletuksena kaikille reiteille asetetaan prioriteetti `10`.
:::

### Esimerkki: Konfliktireitit {#example-conflicting-routes}

Kuvitellaan tilanne, jossa kaksi reittiä vastaavat samankaltaisia URL-malleja:

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

Näin prioriteettijärjestelmä auttaa ratkaisemaan konflikteja:

- **`ProductCategoryView`** vastaa URL-osoitteita kuten `/products/electronics`.
- **`ProductView`** vastaa tarkempia URL-osoitteita kuten `/products/electronics/123`, missä `123` on tuotteen ID.

Tässä tapauksessa molemmat reitit voisivat vastata URL-osoitetta `/products/electronics`. Kuitenkin, koska `ProductCategoryView`:lla on korkeampi prioriteetti (prioriteetti = 9), se valitaan ensin, kun URL-osoitteessa ei ole `productId`:ta. URL-osoitteisiin kuten `/products/electronics/123`, `ProductView` valitaan `productId`-parametrin vuoksi.
