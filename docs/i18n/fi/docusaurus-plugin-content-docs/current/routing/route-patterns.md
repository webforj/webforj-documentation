---
sidebar_position: 5
title: Route Patterns
_i18n_hash: 2f1668e34197bb2f4bb6c5b3ec6e87e5
---
**Reittimallit** käytetään määrittämään, miten URL-osoitteet vastaavat tiettyihin näkymiin, mukaan lukien dynaamiset ja valinnaiset segmentit, säännönmukaiset lausekkeet ja jokerimerkit. Reittimallit mahdollistavat kehyksen URL-osoitteiden täsmäyttämisen, parametrien erottamisen ja URL-osoitteiden dynaamisen luomisen. Ne näyttelevät kriittistä roolia sovelluksen navigoinnin ja komponenttien renderöinnin jäsentelyssä selaimen sijainnin perusteella.

## Reittimallin syntaksi {#route-pattern-syntax}

Reittimallit webforJ:ssä ovat erittäin joustavia ja tukevat seuraavia ominaisuuksia:

- **Nimetyt parametrid:** Merkitään muodossa `:paramName`, ne ovat pakollisia, ellei niitä ole merkitty valinnaisiksi.
- **Valinnaiset parametrit:** Merkitään muodossa `:paramName?`, ne voidaan jättää pois URL-osoitteesta.
- **Jokerimerkki segmentit:** Edustettuna merkillä `*`, ne sieppaavat kaikki jäljelle jäävät URL-segmentit.
- **Säännönmukaisen lausekkeen rajoitukset:** Rajoituksia voidaan lisätä vain nimettyihin parametreihin (esimerkiksi `:id<[0-9]+>`).

### Esimerkki reittimallin määrittelyistä {#example-of-route-pattern-definitions}

```java
@Route("asiakas/:id<[0-9]+>/nimeltä/:nimi/*")
public class AsiakasNäkymä extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    int id = parameters.getInt("id").orElse(0);
    String nimi = parameters.getAlpha("nimi").orElse("Tuntematon");
    String extra = parameters.getAlpha("*").orElse("");

    String tulos =
        "Asiakas ID: " + id + "-" +
        "Nimi: " + nimi + "-" +
        "*: " + extra;

    console().log(tulos);
  }
}
```

Tässä esimerkissä:

- `:id<[0-9]+>` sieppaa numeerisen asiakas-ID:n.
- `:nimi` sieppaa nimen.
- `*` sieppaa kaikki ylimääräiset polkusegmentit `nimeltä/:nimi` jälkeen.

## Nimetyt parametrit {#named-parameters}

Nimetyt parametrit määritellään etuliitteellä kaksoispiste `:` parametrin nimen edessä mallissa. Ne ovat pakollisia, ellei niitä ole merkitty valinnaisiksi. Nimetyt parametrit voivat myös sisältää säännönmukaisen lausekkeen [rajoitukset](#regular-expression-constraints) arvojen validoimiseksi.

### Esimerkki: {#example}

```java
@Route("tuote/:id")
public class TuoteNäkymä extends Composite<Div> {
  // Komponenttilogiikka tähän
}
```

Tämä malli vastaa URL-osoitteita, kuten `/tuote/123`, jossa `id` on `123`.

## Valinnaiset parametrit {#optional-parameters}

Valinnaiset parametrit merkitään lisäämällä `?` parametrin nimen jälkeen. Nämä segmentit eivät ole pakollisia ja ne voidaan jättää pois URL-osoitteesta.

### Esimerkki: {#example-1}

```java
@Route("tilaus/:id?<[0-9]+>")
public class TilausNäkymä extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("Tilaus ID: " + id),
      () -> console().log("Tilaus ID:tä ei annettu")
    );
  }
}
```

Tämä malli vastaa sekä `/tilaus/123` sisältäen numeerisen arvon, että `/tilaus`, jolloin numeerista arvoa voidaan jättää pois, kun `/tilaus` syötetään.

## Säännönmukaisen lausekkeen rajoitukset {#regular-expression-constraints}

Voit soveltaa säännönmukaisen lausekkeen rajoituksia parametreihin lisäämällä ne kulmasulkeisiin `<>`. Tämä mahdollistaa tiukempien vastaavuus sääntöjen määrittämisen parametreille.

### Esimerkki: {#example-2}

```java
@Route("tuote/:koodi<[A-Z]{3}-[0-9]{4}>")
public class TuoteNäkymä extends Composite<FlexLayout> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("koodi").ifPresentOrElse(
      koodi -> console().log("Tuotekoodi: " + koodi),
      () -> console().error("Tuotekoodia ei löytynyt"));
  }
}
```

Tämä malli vastaa vain tuotekoodeja, jotka ovat muodossa `ABC-1234`. Esimerkiksi `/tuote/XYZ-5678` sopii, mutta `/tuote/abc-5678` ei sovi.

## Jokerimerkki segmentit {#wildcard-segments}

Jokerimerkkejä voidaan käyttää koko polun sieppaamiseen tietyn reittisegmentin jälkeen, mutta ne voivat olla vain mallin viimeinen segmentti, ratkaisten kaikki jäljellä olevat arvot URL-osoitteessa. Parempaa luettavuutta varten jokerimerkki segmenttejä voidaan nimetä. Kuitenkin, toisin kuin nimetyillä parametreilla, jokerimerkki segmenteillä ei voi olla rajoituksia.

### Esimerkki: {#example-3}

```java
@Route("tiedostot/:polku*")
public class TiedostoHallintaNäkymä extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("polku").ifPresentOrElse(
      polku -> console().log("TiedostoHallintaNäkymä: " + polku),
      () -> console().log("TiedostoHallintaNäkymä: Ei polkuparametria")
    );
  }
}
```

Tämä malli vastaa mitä tahansa URL-osoitetta, joka alkaa `/tiedostot` ja sieppaa loput polusta jokerimerkkinä.

## Reittien prioriteetti {#route-priority}

Kun useat reitit vastaavat tiettyä URL-osoitetta, reitin prioriteetti määrittää, mikä reitti valitaan ensin. Tämä on erityisen hyödyllistä, kun kaksi tai useampi reitti päällekkäin niiden polkumalleissa, ja tarvitset keinon hallita, mikä niistä saa etusijan. Prioriteettiattribuutti on käytettävissä sekä `@Route` että `@RouteAlias` -annotaatioissa.

### Miten prioriteettijärjestelmä toimii {#how-the-priority-system-works}

Prioriteettiattribuutti sallii reitittimen määrittää sen järjestyksen, jossa reittejä arvioidaan, kun useat reitit voisivat vastata tiettyä URL-osoitetta. Reitit lajitellaan niiden prioriteettiarvojen mukaan, ja korkeampi prioriteetti (alhaisemmat numeeriset arvot) vastaa ensin. Tämä varmistaa, että tarkemmat reitit saavat etusijan yleisempiin verrattuna.

Jos kaksi reittiä jakaa saman prioriteetin, reititin ratkaisee konfliktin valitsemalla ensin rekisteröidyn reitin. Tämä mekanismi varmistaa, että oikea reitti valitaan, jopa silloin, kun useat reitit päällekkäin niiden URL-malleissa.

:::info Oletusarvoinen prioriteetti  
Oletuksena kaikille reiteille annetaan prioriteetti `10`.  
:::

### Esimerkki: Konfliktiset reitit {#example-conflicting-routes}

Kuvitellaan tilanne, jossa kaksi reittiä vastaavat samankaltaisia URL-malleja:

```java
@Route(value = "tuotteet/:kategoria", priority = 9)
public class TuoteKategoriaNäkymä extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String kategoria = parameters.get("kategoria").orElse("tuntematon");
    console().log("Näytetään kategoria: " + kategoria);
  }
}

@Route(value = "tuotteet/:kategoria/:tuoteId?<[0-9]+>")
public class TuoteNäkymä extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String tuoteId = parameters.get("tuoteId").orElse("tuntematon");
    console().log("Näytetään tuote: " + tuoteId);
  }
}
```

Tässä on, miten prioriteettijärjestelmä auttaa ratkaisemaan konflikteja:

- **`TuoteKategoriaNäkymä`** vastaa URL-osoitteita, kuten `/tuotteet/elektroniikka`.
- **`TuoteNäkymä`** vastaa tarkempiin URL-osoitteisiin, kuten `/tuotteet/elektroniikka/123`, jossa `123` on tuote-ID.

Tässä tapauksessa molemmat reitit voisivat vastata URL-osoitteeseen `/tuotteet/elektroniikka`. Kuitenkin, koska `TuoteKategoriaNäkymä`:llä on korkeampi prioriteetti (prioriteetti = 9), se valitaan ensin, kun URL-osoitteessa ei ole `tuoteId`:tä. URL-osoitteissa, kuten `/tuotteet/elektroniikka/123`, `TuoteNäkymä` valitaan `tuoteId` parametrin vuoksi.
