---
sidebar_position: 7
title: Event Options
_i18n_hash: 4311668d9a6bb9e9cebcf988e515d91a
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` on monipuolinen webforJ-työkalu, joka on suunniteltu kapseloimaan ja hallitsemaan `Element`-tapahtumien konfiguraatioasetuksia webforJ-sovelluksissa. Se toimii erilaisten vaihtoehtojen säiliönä, mikä mahdollistaa kehittäjille tarkasti määritellä, miten elementteihin liittyviä tapahtumia tulisi käsitellä.

## Tapahtumadata {#event-data}

Tapahtumadata on keskeinen ominaisuus `ElementEventOptions`:ssa, joka antaa kehittäjille mahdollisuuden liittää tiettyjä tietoja tapahtuma-asetuksiin. Tämä toiminnallisuus helpottaa räätälöidyn datan siirtämistä asiakkaalta palvelimelle tapahtuman muodostuessa. Tämä kyky on tärkeä ylimääräisen kontekstin tai tapahtumaan liittyvien parametrien välittämisessä, ja se mahdollistaa tiedon käytön ilman lisämatkoja asiakkaalle.

Esimerkiksi, kuvitelkaamme tilanne, jossa sinulla on napin klikkaustapahtuma ja haluat siirtää nykyisen käyttäjän käyttäjätunnuksen tapahtuman mukana. Sen sijaan, että kysyisit käyttäjän käyttäjätunnusta asiakkaalta jokaisella kerralla, lähetä tämä tieto tapahtuman myötä datana.

:::tip
Lisätietoja on saatavilla [tapahtumista](/docs/building-ui/events) ja [asiakkaan/palvelimen vuorovaikutuksesta](/docs/architecture/client-server) -sivuilla.
:::

Lisätäksesi tietoa tapahtuma-asetuksiin, voit käyttää `addData()`-metodia.

<!-- ### Esimerkki -->

## JavaScriptin suorittaminen {#executing-javascript}

`ElementEventOptions` -luokka antaa kehittäjille mahdollisuuden määrittää JavaScript-koodia, joka arvioidaan asiakaspuolella ennen liittyvän tapahtuman laukaisevaa tapahtumaa. Tämä ominaisuus mahdollistaa asiakkaille tapahtumadatan valmistelun tai lisätapahtumien laukaisemisen tarpeen mukaan. Tämä on hyödyllistä monissa tapauksissa, esimerkiksi kun halutaan validoida lomakedataa asiakaspuolella ennen kuin se lähetetään lomakkeen lähetys-tapahtuman kautta.

### Käyttö {#usage}
Asettaaksesi tapahtumakoodin, käytä `setCode()`-metodia.

## Tapahtumien suodatus {#filtering-events}

`ElementEventOptions` sisältää ominaisuuden suodatustilauksen määrittämiseksi, joka arvioidaan asiakkaalla ennen tapahtuman laukaisemista. Tämä suodatustilaeus mahdollistaa asiakkaan päättää, pitäisikö tapahtuman edetä vai pysäyttää tiettyjen ehtojen perusteella. Kuvittele syötekenttä, jossa haluat laukaista tapahtuman vain, jos syötetyt tekstit täyttävät tietyt kriteerit, kuten minimipituuden.

### Käyttö {#usage-1}
Asettaaksesi tapahtumasuodattimen, käytä `setFilter()`-metodia.

## Debouncaaminen ja throttling {#debouncing-and-throttling}

### Tavoite {#purpose}
`ElementEventOptions` tarjoaa mekanismeja tapahtumien debouncaamiseen ja throttlingiin. Nämä ominaisuudet ovat hyödyllisiä tapahtumakuuntelijoiden taajuuden hallitsemiseksi, varmistaen, että ne laukaistaan vain tietyissä olosuhteissa.

### Käyttö {#usage-2}
- Aseta debounce käyttämällä `setDebounce`-metodia.
- Aseta throttle käyttämällä `setThrottle`-metodia.

### Esimerkki {#example}
Tilanteissa, joissa haluat käsitellä nopeaa käyttäjän syötettä, kuten hakusyötekenttiä, voit käyttää debouncea viivyttääksesi suorittamista, kunnes käyttäjä on lopettanut kirjoittamisen.

## Tapahtuma-asetusten yhdistäminen {#merging-event-options}

`ElementEventOptions` -luokka tukee yhdistämistä muiden instanssien kanssa, jolloin kehittäjät voivat kerätä erilaisia vaihtoehtoja. Tämä ominaisuus on hyödyllinen yhdistettäessä asetuksia eri lähteistä.

## Annotoinnit {#annotations}

### Tavoite {#purpose-1}
Käytön helpottamiseksi `ElementEventOptions` voidaan konfiguroida käyttämällä annotaatioita. Nämä annotaatiot tarjoavat tiiviimmän ja ilmaisuvoimaisemman tavan määrittää tapahtuma-asetuksia.

### Esimerkki {#example-1}
Kuvittele seuraava esimerkkiannotaatio:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
