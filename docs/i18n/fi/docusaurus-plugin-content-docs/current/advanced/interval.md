---
sidebar_position: 20
title: Interval
_i18n_hash: a220fb1607867630d6bfc03a1ce5d3e9
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

Luokka <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> edustaa ajastinta, joka laukaisee [tapahtuman](../building-ui/events) kiinteällä viiveellä jokaisen laukaisun välillä.

`Interval`-luokka tarjoaa suoraviivaisen tavan laukaista tapahtumia tietyn viiveen jälkeen. Sen avulla on mahdollista käynnistää, pysäyttää ja käynnistää `Interval` uudelleen tarpeen mukaan. 
webforJ:ssä `Interval` tarjoaa paremman suorituskyvyn verrattuna tavanomaiseen Java- tai Swing-ajastimeen.
Se tukee myös useita kuuntelijoita kuluneelle tapahtumalle.

## Käytöt {#usages}
`Interval`-luokka laukaisee tapahtumia kiinteällä viiveellä. Käyttämällä Intervalloita luovasti, voit luoda dynaamisia ja mielenkiintoisia kokemuksia sovellukseesi:

1. **Tarkista toimimattomuus**: Näytä [`Dialog`](../components/dialog) -komponentti, jos mitään vuorovaikutusta ei ole tapahtunut lomakkeella tietyn ajan kuluessa.

2. **Esitelty sisältö**: Kierrätä esiteltyjä artikkeleita, tuotteita tai kampanjoita etusivullasi jokaisen Intervallin myötä. Tämä pitää sisällön dynaamisena ja houkuttelevana.

3. **Reaaliaikaiset tiedot**: Päivitä sovelluksesi tietoja, kuten osakehintoja, uutisvirtoja tai säätietoja, jokaisella Intervallilla, jotta tiedot pysyvät ajantasaisina.

## `Interval`-tilojen hallinta: käynnistäminen, pysäyttäminen ja uudelleenkäynnistys {#managing-interval-states-starting-stopping-and-restart}
Interval vaatii manuaalista aktivointia; käytä `start()`-menetelmää sen käynnistämiseksi. Pysäyttääksesi Intervallin, käytä `stop()`-menetelmää. `restart()`-menetelmää voidaan käyttää Intervallin uudelleenkäynnistämiseen.

## `Interval`-viiveen säätäminen {#adjusting-the-interval-delay}

Muuttaaaksesi Intervalin viivettä, käytä `setDelay(float delay)` -menetelmää. Uusi viivearvo otetaan käyttöön sen jälkeen, kun Interval on joko pysäytetty tai käynnistetty uudelleen.

```java
//Viiveen muuttaminen
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Viive voi olla murto-osia sekunnista millisekuntien tarkkuudella, mutta liian pieni aikaraja aiheuttaa tapahtumien tulvan nopeammin kuin ohjelma ehtii niihin reagoida.
:::

## Kuuntelijoiden lisääminen {#adding-listeners}

Voit liittää lisäkuuntelijoita Intervalille käyttämällä `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` -menetelmää. Kun kuuntelija on lisätty, se laukaisee automaattisesti seuraavalla Intervallilla, jos Interval on jo käynnissä.

```java
// Kuuntelijoiden lisääminen
float delay = 2f;

EventListener<Interval.ElapsedEvent> firstListener = (e -> {
// Suoritettavaa koodia
});

Interval interval = new Interval(delay, firstListener);

EventListener<Interval.ElapsedEvent> secondListener = (e -> {
// Suoritettavaa koodia
});

interval.addElapsedListener(secondListener);
```
