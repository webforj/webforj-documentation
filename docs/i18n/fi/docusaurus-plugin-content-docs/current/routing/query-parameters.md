---
sidebar_position: 6
title: Query Parameters
_i18n_hash: 5a8313b16d83bfbef6e8d43589430f90
---
Kyselyparametrit mahdollistavat lisätietojen siirtämisen URL-osoitteiden kautta, käyttämällä muotoa `?key1=value1&key2=value2`. Kun reittiparametreja käytetään vaadittavan tiedon siirtämiseen URL-polussa, kyselyparametrit tarjoavat joustavan mekanismin vapaaehtoisten tai lisätietojen siirtämiseen. Ne ovat erityisen hyödyllisiä sisällön suodattamisessa, lajittelussa tai käsitellessä useita arvoja samalla avaimella.

## Kyselyparametrien yleiskatsaus {#query-parameters-overview}

Kyselyparametrit webforJ:ssä noudattavat tyypillistä URL-käytäntöä: avain-arvo-parit erotetaan `=`-merkillä ja yhdistetään `&`-merkillä. Ne liitetään URL-osoitteeseen kysymysmerkin (`?`) jälkeen ja tarjoavat joustavan tavan siirtää vaihtoehtoisia tietoja, kuten suodatus- tai lajittelutoiveita.

Esimerkiksi:

```
/products?category=electronics&sort=price
```

## Kyselyparametrien noutaminen {#retrieving-query-parameters}

Kyselyparametreja käytetään `ParametersBag`-objektin kautta. Noutaaksesi kyselyparametreja, käytä `getQueryParameters()`-metodia `Location`-objektista.

Näin voit noutaa kyselyparametreja URL-osoitteesta näkymässä:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    String category = queryParameters.get("category").orElse("all");
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Kategoria: " + category);
    console().log("Lajittelu: " + sort);
  }
}
```

Tässä esimerkissä:
- `onDidEnter`-metodi noutaa kyselyparametreja `Location`-objektista, joka on saatavilla `DidEnterEvent`:istä.
- `ParametersBag` mahdollistaa tiettyjen kyselyparametrien noutamisen `get()`-metodin avulla, joka palauttaa `Optional<String>`. Voit määrittää oletusarvon käyttämällä `orElse()`, jos parametria ei ole olemassa.

:::tip `ParametersBag`-getterit
`ParametersBag` tarjoaa useita getter-variantteja, jotka auttavat kyselyparametrien arvojen muuntamisessa tiettyihin tyyppeihin ja niiden suodattamisessa. Seuraavassa on täydellinen lista saatavilla olevista gettereistä:

- **`get(String key)`**: Noutaa parametrin arvon `String`-muodossa.
- **`getAlpha(String key)`**: Palauttaa vain aakkoselliset merkit parametrin arvosta.
- **`getAlnum(String key)`**: Palauttaa vain alfanumeeriset merkit parametrin arvosta.
- **`getDigits(String key)`**: Palauttaa vain numeeriset numerot parametrin arvosta.
- **`getInt(String key)`**: Parsii ja palauttaa parametrin arvon `Integer`-muodossa.
- **`getFloat(String key)`**: Parsii ja palauttaa parametrin arvon `Float`-muodossa.
- **`getDouble(String key)`**: Parsii ja palauttaa parametrin arvon `Double`-muodossa.
- **`getBoolean(String key)`**: Parsii ja palauttaa parametrin arvon `Boolean`-muodossa.

Nämä metodit auttavat varmistamaan, että arvot on muotoiltu ja muunnettu oikein, jolloin vältetään manuaalinen analysointi tai validointi.
:::

## Useiden arvojen käsittely kyselyparametrille {#handling-multiple-values-for-a-query-parameter}

Joskus kyselyparametrilla voi olla useita arvoja samalla avaimella, kuten seuraavassa esimerkissä:

```
/products?category=electronics,appliances&sort=price
```

`ParametersBag` tarjoaa menetelmän tämän käsittelemiseen noutamalla arvot listana:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    List<String> categories = queryParameters.getList("category").orElse(List.of("all"));
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Kategoriat: " + categories);
    console().log("Lajittelu: " + sort);
  }
}
```

Tässä esimerkissä:
- `getList("category")` noutaa kaikki arvot, jotka liittyvät `category`-avaimeen, palauttaen ne listana.

:::tip Useiden arvojen erotin
Oletuksena `getList()`-metodi käyttää pilkkua (`,`) erottimena. Voit mukauttaa erotinta välittämällä eri merkin tai säännöllisen lausekkeen toisen parametrina `getList(String key, String regex)`-metodille.
:::

## Kyselyparametrien käyttötapaukset {#use-cases-for-query-parameters}

- **Sisällön suodattaminen**: Kyselyparametreja käytetään usein suodattimien, kuten kategorioiden tai hakusanageneraattoreiden, soveltamiseen.
- **Tietojen lajittelu**: Voit siirtää lajittelutoiveita kyselyparametrien kautta, kuten lajittelu hinnan, arvostelun tai päivämäärän mukaan.
- **Valinnaisten parametrien käsittely**: Kun sinun on siirrettävä tietoa, joka ei ole osa vaadittua reittirakennetta, kyselyparametrit tarjoavat joustavuutta.
- **Useiden arvojen siirtäminen**: Kyselyparametrit mahdollistavat useiden arvojen lähettämisen yhdelle avaimelle, mikä on hyödyllistä, kun käyttäjät valitsevat useita vaihtoehtoja, kuten tuotekategorioita tai suodattimia.
