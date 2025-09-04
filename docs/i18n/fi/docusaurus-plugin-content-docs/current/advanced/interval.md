---
sidebar_position: 15
title: Interval
_i18n_hash: dc02bb8f8bb43ee67f300071d3ab4ec7
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

Luokka <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> edustaa ajastinta, joka laukaisee [tapahtuman](../building-ui/events) kiinteällä viiveellä jokaisen laukaisun välillä.

`Interval`-luokka tarjoaa yksinkertaisen tavan laukaista tapahtumia määritellyn viiveen jälkeen. `Interval`-ajastinta voidaan aloittaa, pysäyttää ja käynnistää uudelleen tarvittaessa. Lisäksi Intervallit voivat tukea useita kuuntelijoita kuluneelle tapahtumalle.
Optimoitu webforJ-kehykselle, se tarjoaa paremman suorituskyvyn verrattuna tavalliseen Java-ajastimeen tai Swing-ajastimeen.

## Käytännöt {#usages}
`Interval`-luokka laukaisee tapahtumia kiinteällä viiveellä. Hyödyntämällä Intervalloita luovasti, voit parantaa käyttäjävuorovaikutusta ja sitoutumista verkkosivustollasi samalla kun pidät kokemuksen dynaamisena ja mielenkiintoisena.:

1. **Tarkista passiivisuus**: Näytä [`Dialog`](../components/dialog) -komponentti, jos lomakkeessa ei ole ollut mitään vuorovaikutusta tietyn ajan kuluessa.

2. **Esitelty sisältö**: Kierrä esiteltyjä artikkeleita, tuotteita tai kampanjoita etusivullasi jokaisen Intervallin aikana. Tämä pitää sisällön dynaamisena ja mukaansatempaavana.

3. **Reaaliaikaiset tiedot**: Päivitä tietoja sovelluksessasi, kuten osakehintoja, uutisvirtoja tai säätietoja, jokaisen Intervallin aikana, jotta tiedot pysyvät ajantasaisina.

## `Interval`-tilojen hallinta: käynnistys, pysäytys ja uudelleenkäynnistys {#managing-interval-states-starting-stopping-and-restart}
Interval vaatii manuaalista aktivointia; käytä `start()`-metodia käynnistääksesi sen. Pysäyttääksesi Intervalin, käytä `stop()`-metodia. Uudelleenkäynnistämiseen voit käyttää `restart()`-metodia.

## `Interval`-viiveen säätäminen {#adjusting-the-interval-delay}

Muokataksesi Intervallin viivettä, käytä `setDelay(float delay)`-metodia. Uusi viivearvo otetaan käyttöön sen jälkeen, kun Interval on joko pysäytetty tai käynnistetty uudelleen.


```java
//Viiveen muuttaminen
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Viive voi olla murto-osia sekunnista millisekuntien tarkkuudella, mutta erittäin pieni aikakatkaisu aiheuttaa tapahtumien tulvan, jota ohjelma ei pysty käsittelemään.
:::

## Kuuntelijoiden lisääminen {#adding-listeners}

Voit liittää lisäkuuntelijoita Intervallille käyttämällä `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)`-metodia. Kun kuuntelija on lisätty, se laukaisee automaattisesti seuraavalla intervallilla, jos Interval on jo käynnissä.

```java
// Kuuntelijoiden lisääminen
float delay = 2f;

EventListener<Interval.ElapsedEvent> firstListener = (e -> {
// Suoritettava koodi
});

Interval interval = new Interval(delay, firstListener);

EventListener<Interval.ElapsedEvent> secondListener = (e -> {
// Suoritettava koodi
});

interval.addElapsedListener(secondListener);
```
