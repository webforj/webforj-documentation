---
sidebar_position: 7
title: Event Options
_i18n_hash: 64cfa37f974517956ccb3fd75618df50
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` on monipuolinen webforJ-työkalu, joka on suunniteltu kapseloimaan ja hallitsemaan konfiguraatioasetuksia `Element`-tapahtumille webforJ-sovelluksissa. Se toimii erilaisten vaihtoehtojen säiliönä, jolloin kehittäjät voivat tarkasti määrittää, miten elementteihin liittyvät tapahtumat käsitellään.

## Tapahtumatiedot {#event-data}

Tapahtumatiedot ovat avainominaisuus `ElementEventOptions`-luokassa, joka mahdollistaa kehittäjien liittää tiettyä tietoa tapahtumavaihtoehtoihin. Tämä toiminnallisuus helpottaa mukautetun tiedon siirtämistä asiakkaalta palvelimelle tapahtuman laukaisemisen yhteydessä. Tämä kyky on oleellinen lisäämään kontekstia tai parametreja, jotka liittyvät tapahtumaan, ja mahdollistaa tiedon käytön ilman lisämatkoja asiakkaalle.

Esimerkiksi, kuvitelkaa tilanne, jossa teillä on painikkeen klikkaustapahtuma, ja haluatte välittää nykyisen käyttäjän käyttäjänimen tapahtuman mukana. Sen sijaan, että kysyisitte käyttäjän nimeä asiakkaalta joka kerta, lähettäkää tämä tieto tapahtuman mukana datana.

:::tip
Lisätietoja varten, katso [tapahtumat](/docs/building-ui/events) ja [Asiakas/Palvelin vuorovaikutus](/docs/architecture/client-server) sivut.
:::

Liittääksesi tietoa tapahtumavaihtoehtoihin, voit käyttää `addData()`-metodia.

<!-- ### Esimerkki -->

## JavaScriptin suorittaminen {#executing-javascript}

`ElementEventOptions`-luokka mahdollistaa kehittäjien määrittää JavaScript-koodin, joka arvioidaan asiakkaan puolella ennen kuin liitetty tapahtuma laukaistaan. Tämä ominaisuus sallii asiakkaille valmistaa tapahtumatietoja tai laukaista ylimääräisiä tapahtumia tarpeen mukaan. Tämä on hyödyllistä monissa tilanteissa, esimerkiksi kun halutaan validoida lomaketietoja asiakkaan puolella ennen niiden lähettämistä lomakeen lähettämistapahtuman kautta.

### Käyttö {#usage}
Asettaaksesi tapahtumakoodin, käytä `setCode()`-metodia.

## Tapahtumien suodattaminen {#filtering-events}

`ElementEventOptions` sisältää ominaisuuden suodatuslausekkeen asettamiseksi, joka arvioidaan asiakkaan puolella ennen kuin tapahtuma laukaistaan. Tämä suodatuslauseke mahdollistaa asiakkaan määrittää, tulisiko tapahtuman edetä tai keskeyttää tietyissä olosuhteissa. Ota huomioon syöttökenttä, jossa haluat laukaista tapahtuman vain, jos syötetty teksti täyttää tietyt kriteerit, kuten minimipituuden.

### Käyttö {#usage-1}
Asettaaksesi tapahtumasuodatuksen, käytä `setFilter()`-metodia.

## Deboucing ja throttling {#debouncing-and-throttling}

### Tarkoitus {#purpose}
`ElementEventOptions` tarjoaa mekanismeja tapahtumien debounce- ja throttling-asetuksiin. Nämä ominaisuudet ovat hyödyllisiä tapahtumakuuntelijoiden tapahtumien hallinnan tiheyden hallitsemiseksi, varmistaen että ne laukeavat vain tietyissä olosuhteissa.

### Käyttö {#usage-2}
- Aseta debounce, käytä `setDebounce`-metodia.
- Aseta throttle, käytä `setThrottle`-metodia.

### Esimerkki {#example}
Tilanteissa, joissa haluat käsitellä nopeaa käyttäjätietoa, kuten hakusyöttökenttiä, voit käyttää debouncea viivästyttääksesi suorittamista, kunnes käyttäjä on lopettanut kirjoittamisen.

## Tapahtumavaihtoehtojen yhdistäminen {#merging-event-options}

`ElementEventOptions`-luokka tukee yhdistämistä muiden instanssien kanssa, jolloin kehittäjät voivat yhdistää eri vaihtoehtoja. Tämä ominaisuus on hyödyllinen, kun yhdistetään asetuksia eri lähteistä.

## Annointi {#annotations}

### Tarkoitus {#purpose-1}
Mukavuuden vuoksi `ElementEventOptions` voidaan konfiguroida käyttämällä annotaatioita. Nämä annotaatiot tarjoavat tiiviimmän ja ilmeikkään tavan asettaa tapahtumavaihtoehtoja.

### Esimerkki {#example-1}
Kuvittele seuraava esimerkkannotaatio:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
debounce = @DebounceSettings(value = 200))
```
