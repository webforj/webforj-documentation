---
sidebar_position: 4
title: Event Options
_i18n_hash: 8bf57e40eec8e571f3d62266e388f114
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` on monipuolinen webforJ-työkalu, joka on suunniteltu kapseloimaan ja hallitsemaan konfiguraatiotietoja `Element`-tapahtumille webforJ-sovelluksissa. Se toimii erilaisten vaihtoehtojen säiliönä, mikä mahdollistaa kehittäjille tarkan määrittelyn siitä, miten elementteihin liittyviä tapahtumia tulisi käsitellä.

## Event data {#event-data}

Tapahtumatiedot ovat keskeinen ominaisuus `ElementEventOptions`-luokassa, joka mahdollistaa kehittäjien liittää erityistietoja tapahtumavaihtoehtoihin. Tämä toiminto helpottaa mukautetun tiedon siirtämistä asiakaspuolelta palvelimelle, kun tapahtuma laukaistaan. Tämä kyky on tärkeä lisäkontekstiin tai parametreihin, jotka liittyvät tapahtumaan, ja mahdollistaa tiedon käytön ilman, että asiakaspuolelta tarvitaan lisää pyyntöjä.

Esimerkiksi, kuvitelkaamme skenario, jossa sinulla on painiketta klikkaava tapahtuma, ja haluat lähettää nykyisen käyttäjän käyttäjänimen tapahtuman mukana. Sen sijaan, että kysyisit käyttäjän nimeä asiakaspuolelta joka kerta, lähetä tämä tieto tapahtuman mukana datana.

:::tip
Lisätietoja on saatavilla sivuilla [events](../../building-ui/events) ja [Client/Server Interaction](../../architecture/client-server).
:::

Lisätäksesi dataa tapahtumavaihtoehtoihin, voit käyttää `addData()`-metodia.

<!-- ### Esimerkki -->

## Executing JavaScript {#executing-javascript}

`ElementEventOptions`-luokka mahdollistaa kehittäjien määrittää JavaScript-koodia, joka arvioidaan asiakaspuolella ennen kuin liitetty tapahtuma laukaistaan. Tämä ominaisuus mahdollistaa asiakkaille tapahtumatietojen valmistelun tai lisätapahtumien laukaisevan tarpeen mukaan. Tämä on hyödyllistä monissa tapauksissa, esimerkiksi kun haluat validoida lomaketietoja asiakaspuolella ennen kuin toimitat sen lomakkeen lähettämistapahtuman kautta.

### Usage {#usage}
Määrittääksesi tapahtumakoodin, käytä `setCode()`-metodia.

## Filtering events {#filtering-events}

`ElementEventOptions` sisältää ominaisuuden suodatinlausekkeiden asettamiseksi, joka arvioidaan asiakaspuolella ennen tapahtuman laukaisemista. Tämä suodatinlauseke mahdollistaa asiakkaan päättää, tuleeko tapahtuman jatkua vai estää tietyin ehdoin. Ota esimerkiksi tekstikenttä, jossa haluat laukaista tapahtuman vain, jos syötetty teksti täyttää tietyt kriteerit, kuten vähimmäispituuden.

### Usage {#usage-1}
Aseta tapahtumasuodatin käyttämällä `setFilter()`-metodia.

## Debouncing and throttling {#debouncing-and-throttling}

### Purpose {#purpose}
`ElementEventOptions` tarjoaa mekanismeja tapahtumien debouncaukseen ja throttlingiin. Nämä ominaisuudet ovat hyödyllisiä tapahtumakuuntelijoiden käytön frekvenssin hallitsemiseksi, varmistaen, että niitä laukaistaan vain tietyissä olosuhteissa.

### Usage {#usage-2}
- Aseta debounce käyttämällä `setDebounce`-metodia.
- Aseta throttle käyttämällä `setThrottle`-metodia.

### Example {#example}
Tapahtumien käsittelemisessä, joissa haluat käsitellä nopeaa käyttäjätuloa, kuten hakupalkkia, voit käyttää debouncea viivyttääksesi suorittamista, kunnes käyttäjä on lopettanut kirjoittamisen.

## Merging event options {#merging-event-options}

`ElementEventOptions`-luokka tukee yhdistämistä muiden instanssien kanssa, mikä mahdollistaa kehittäjien aggroida erilaisia vaihtoehtoja. Tämä ominaisuus on hyödyllinen, kun yhdistät asetuksia eri lähteistä.

## Annotations {#annotations}

### Purpose {#purpose-1}
Käytön mukavuuden vuoksi `ElementEventOptions` voidaan määrittää käyttämällä annotaatioita. Nämä annotaatiot tarjoavat tiiviimmän ja ilmaisevamman tavan asettaa tapahtumavaihtoehtoja.

### Example {#example-1}
Ota esimerkiksi seuraava annotaatio:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
