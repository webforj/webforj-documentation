---
sidebar_position: 4
title: Event Options
_i18n_hash: d780e41b809f0e3df55f65a1c71983a0
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` on monipuolinen webforJ-työkalu, joka on suunniteltu kapseloimaan ja hallitsemaan `Element`-tapahtumien konfigurointiasetuksia webforJ-sovelluksissa. Se toimii erilaisten vaihtoehtojen säiliönä, jolloin kehittäjät voivat tarkasti määrätä, miten elementteihin liittyvät tapahtumat käsitellään.

## Tapahtumatiedot {#event-data}

Tapahtumatiedot ovat avainominaisuus `ElementEventOptions`:ssa, jolloin kehittäjät voivat liittää tiettyjä tietoja tapahtumavaihtoehtoihin. Tämä toiminnallisuus helpottaa mukautettujen tietojen siirtämistä asiakkaalta palvelimelle, kun tapahtuma laukaistaan. Tämä kyky on tärkeä lisätiedon tai -parametrien välittämisessä, ja se mahdollistaa tietojen käyttämisen ilman lisämatkoja asiakkaalle.

Esimerkiksi, kuvittelemme tilanteen, jossa sinulla on painikkeen napsautustapahtuma, ja haluat liittää nykyisen käyttäjän käyttäjänimen tapahtuman mukana. Sen sijaan, että kysyisit käyttäjän käyttäjänimeä asiakkaalta joka kerta, lähetä tämä tieto tapahtuman mukana datana.

:::tip
Lisätietoja varten, katso [tapahtumat](../../building-ui/events) ja [Asiakkaan/Sovelluksen vuorovaikutus](../../architecture/client-server) sivut.
:::

Lisätäksesi tietoa tapahtumavaihtoehtoihin, voit käyttää `addData()`-metodia.

<!-- ### Esimerkki -->

## JavaScriptin suorittaminen {#executing-javascript}

`ElementEventOptions`-luokka sallii kehittäjien määrittää JavaScript-koodia, joka arvioidaan asiakaspäässä ennen kuin siihen liittyvä tapahtuma laukaistaan. Tämä ominaisuus mahdollistaa asiakkaille tapahtumatietojen valmistelun tai muiden tapahtumien laukaisemisen tarpeen mukaan. Tämä on hyödyllistä monissa tapauksissa, esimerkiksi kun halutaan validoida lomaketiedot asiakaspäässä ennen niiden lähettämistä lomakkeen lähettämistapahtuman kautta.

### Käyttö {#usage}
Aseta tapahtumakoodi käyttämällä `setCode()`-metodia.

## Tapahtumien suodattaminen {#filtering-events}

`ElementEventOptions` sisältää ominaisuuden, joka sallii suodatinlausekkeen asettamisen arvioitavaksi asiakkaalla ennen kuin tapahtuma laukaistaan. Tämä suodatinlauseke mahdollistaa asiakkaan päättää, tulisiko tapahtuman jatkua vai keskeyttää tietyissä olosuhteissa. Ota huomioon syötekenttä, jossa haluat laukaista tapahtuman vain, jos syötetty teksti täyttää tietyt kriteerit, kuten minimipituuden.

### Käyttö {#usage-1}
Aseta tapahtumasuodatin käyttämällä `setFilter()`-metodia.

## Debouncin ja throttlingin käyttö {#debouncing-and-throttling}

### Tarkoitus {#purpose}
`ElementEventOptions` tarjoaa mekanismeja tapahtumien debouncin ja throttlingin hallitsemiseksi. Nämä ominaisuudet ovat hyödyllisiä tapahtumakuuntelijoiden laukaisutiheyden hallitsemiseksi, varmistaen, että ne laukaistaan vain tietyissä olosuhteissa.

### Käyttö {#usage-2}
- Aseta debounce käyttämällä `setDebounce()`-metodia.
- Aseta throttle käyttämällä `setThrottle()`-metodia.

### Esimerkki {#example}
Tilanteissa, joissa haluat käsitellä nopeaa käyttäjäsyöttöä, kuten hakukenttiä, voit käyttää debouncea suorittamisen viivästämiseen, kunnes käyttäjä on lopettanut kirjoittamisen.

## Tapahtumavaihtoehtojen yhdistäminen {#merging-event-options}

`ElementEventOptions`-luokka tukee yhdistämistä muiden instanssien kanssa, jolloin kehittäjät voivat kokoontua yhteen erilaisia vaihtoehtoja. Tämä ominaisuus on hyödyllinen eri lähteistä peräisin olevien asetusten yhdistämisessä.

## Huomautukset {#annotations}

### Tarkoitus {#purpose-1}
Kätevyyden vuoksi `ElementEventOptions` voidaan konfiguroida käyttämällä huomautuksia. Nämä huomautukset tarjoavat tiiviimmän ja ilmaisullisemman tavan asettaa tapahtumavaihtoehtoja.

### Esimerkki {#example-1}
Käytä seuraavaa esimerkkihuomautusta:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
