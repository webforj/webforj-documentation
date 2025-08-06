---
sidebar_position: 6
title: Query Parameters
_i18n_hash: c3b57611c46f7cd4fa9946ff704213cc
---
Query-parameterit mahdollistavat lisätietojen lähettämisen URL-osoitteiden kautta käyttäen muotoa `?key1=value1&key2=value2`. Kun reittiparametrit käytetään vaadittujen tietojen lähettämiseen URL-polun sisällä, query-parametrit tarjoavat joustavan mekanismin valinnaisten tai lisätietojen lähettämiseen. Ne ovat erityisen hyödyllisiä sisällön suodattamisessa, lajittelussa tai useiden arvojen käsittelyssä samalla avaimella.

## Query-parametrien yleiskatsaus {#query-parameters-overview}

Query-parametrit webforJ:ssa noudattavat tyypillistä URL-käytäntöä: avain-arvo-parit, jotka on erotettu `=`:llä ja yhdistetty `&`:llä. Ne liitetään URL-osoitteen perään `?`-merkin jälkeen ja tarjoavat joustavan tavan välittää valinnaisia tietoja, kuten suodatus- tai lajittelupreferenssejä.

Esimerkiksi:

```
/products?category=electronics&sort=price
```

## Query-parametrien hakeminen {#retrieving-query-parameters}

Query-parametreihin päästään käsiksi `ParametersBag`-objektin kautta. Query-parametrien hakemiseen käytetään `getQueryParameters()`-metodia `Location`-objektilta.

Tässä on esimerkki siitä, kuinka voit hakea query-parametreja URL-osoitteesta näkymässä:

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
- `onDidEnter`-metodi hakee query-parametreja `Location`-objektilta, joka on saatu `DidEnterEvent`-tapahtumasta.
- `ParametersBag` mahdollistaa erityisten query-parametrien hakemisen `get()`-metodin avulla, joka palauttaa `Optional<String>`-tyyppisen arvon. Voit määrittää oletusarvon käyttämällä `orElse()`-metodia, jos parametria ei ole saatavilla.

:::tip `ParametersBag`-getterit
`ParametersBag` tarjoaa useita getter-muunnoksia, jotka auttavat query-parametrin arvon tyypin muuntamisessa ja suodattamisessa. Alla on täydellinen luettelo saatavilla olevista gettereistä:

- **`get(String key)`**: Hakee parametrin arvon `String`-tyyppisenä.
- **`getAlpha(String key)`**: Palauttaa vain aakkosnumeeriset merkit parametrin arvosta.
- **`getAlnum(String key)`**: Palauttaa vain aakkosnumeeriset merkit parametrin arvosta.
- **`getDigits(String key)`**: Palauttaa vain numerot parametrin arvosta.
- **`getInt(String key)`**: Parsii ja palauttaa parametrin arvon `Integer`-tyyppisenä.
- **`getFloat(String key)`**: Parsii ja palauttaa parametrin arvon `Float`-tyyppisenä.
- **`getDouble(String key)`**: Parsii ja palauttaa parametrin arvon `Double`-tyyppisenä.
- **`getBoolean(String key)`**: Parsii ja palauttaa parametrin arvon `Boolean`-tyyppisenä.

Nämä metodit auttavat varmistamaan, että arvot ovat muotoiltuja ja tyypitettyjä oikein, välttäen manuaalisen alustamisen tai vahvistamisen tarpeen.
:::

## Useiden arvojen käsittely query-parametrille {#handling-multiple-values-for-a-query-parameter}

Joskus query-parametrilla voi olla useita arvoja samalla avaimella, kuten seuraavassa esimerkissä:

```
/products?category=electronics,appliances&sort=price
```

`ParametersBag` tarjoaa menetelmän tämän käsittelemiseksi hakemalla arvojen luettelo:

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
- `getList("category")` hakee kaikki arvot, jotka liittyvät `category`-avaimeen, palauttaen ne luettelona.

:::tip Useiden arvojen erotin
Oletusarvoisesti `getList()`-metodi käyttää pilkkua (`,`) erottimena. Voit mukauttaa erottimen lähettämällä eri merkin tai säännöllisen lausekkeen toisena parametrina `getList(String key, String regex)`-metodiin.
:::

## Käyttötapaukset query-parametreille {#use-cases-for-query-parameters}

- **Sisällön suodatus**: Query-parametreja käytetään usein suodattimien soveltamiseen, kuten kategorioiden tai hakusanojen.
- **Datan lajittelu**: Voit välittää lajittelupreferenssejä query-parametrien avulla, kuten lajittelu hintojen, arvioiden tai päivämäärien mukaan.
- **Valinnaisten parametrien käsittely**: Kun tarvitset tietojen välittämistä, joka ei ole osa vaadittua reittirakennetta, query-parametrit tarjoavat joustavuutta.
- **Useiden arvojen välittäminen**: Query-parametrit mahdollistavat useiden arvojen lähettämisen yhdelle avaimelle, mikä on hyödyllistä, kun käyttäjät valitsevat useita vaihtoehtoja, kuten tuotekategorioita tai suodattimia.
