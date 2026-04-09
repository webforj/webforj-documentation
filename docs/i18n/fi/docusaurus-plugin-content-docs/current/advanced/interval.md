---
sidebar_position: 20
title: Interval
_i18n_hash: 1fd4c3fc2bf38df65a68d909a6ff77a3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

Luokka <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> edustaa ajastinta, joka laukaisee [tapahtuman](../building-ui/events) kiinteällä aikaviiveellä jokaisen laukaisun välillä.

Voit [aloittaa, pysäyttää ja käynnistää uudelleen](#managing-interval-states-starting-stopping-and-restart) `Interval`:in tarpeen mukaan ja lisätä useita [kuuntelijoita](#adding-listeners) kuluneelle tapahtumalle.
webforJ:ssä `Interval` tarjoaa [paremman suorituskyvyn](#performance) verrattuna tavalliseen Java-ajastimeen tai Swing-ajastimeen.

## Käytännöt {#usages}
`Interval`-luokka laukaisee tapahtumia kiinteällä aikaviiveellä. Käyttämällä Intervaleja luovasti voit luoda dynaamisia ja mielenkiintoisia kokemuksia sovelluksessasi:

1. **Inaktiivisuuden tarkistaminen**: Näytä [`Dialog`](../components/dialog) -komponentti, jos lomakkeella ei ole ollut vuorovaikutusta tietyn ajan kuluessa.

2. **Esitelty sisältö**: Vaihda esiteltyjen artikkeleiden, tuotteiden tai kampanjoiden välillä etusivullasi jokaisella Intervalilla. Tämä pitää sisällön dynaamisena ja kiinnostavana.

3. **Reaaliaikaiset tiedot**: Päivitä tietoja sovelluksessasi, kuten osakehintoja, uutisvirtoja tai sään päivityksiä, jokaisella Intervalilla, jotta tiedot pysyvät ajantasaisina.

## `Interval`-tilojen hallinta: käynnistäminen, pysäyttäminen ja uudelleenkäynnistäminen {#managing-interval-states-starting-stopping-and-restart}
Interval tarvitsee manuaalista aktivointia; käytä `start()`-metodia käynnistääksesi sen. Pysäyttääksesi Intervalin, käytä `stop()`-metodia. `restart()`-metodia voidaan käyttää Intervalin uudelleenkäynnistämiseen.

## `Interval`-viiveen säätäminen {#adjusting-the-interval-delay}

Muokataksesi Intervalin viivettä, käytä `setDelay(float delay)`-metodia. Uusi viivearvo otetaan käyttöön sen jälkeen, kun Interval on joko pysäytetty tai käynnistetty uudelleen.

```java
//Viiveen muuttaminen
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Viive voi olla desimaalisekunteja millisekunnin tarkkuudella, mutta erittäin pieni aikaraja voi aiheuttaa tapahtumien tulvan, joka on nopeampi kuin ohjelma voi niihin reagoida.
:::

## Kuuntelijoiden lisääminen {#adding-listeners}

Voit liittää lisäkuuntelijoita Intervaliin käyttämällä `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)`-metodia. Kun kuuntelija on lisätty, se laukaisee automaattisesti seuraavalla intervalilla, jos Interval on jo käynnissä.

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

## Suorituskyky {#performance}

`Interval`-luokka on erityisesti suunniteltu tarjoamaan parempaa suorituskykyä ja luotettavuutta suurille kuormille, joita verkkosovellukset kohtaavat.
Java Swingissä sama käyttäytyminen voidaan riittävästi hallita `Timer`-luokalla tai uudella säikeellä, mutta tämä lähestymistapa ei skaalaudu hyvin verkkosovelluksille.
Verkkosovelluksilla on todennäköisesti monia samanaikaisia käyttäjiä, ja jos jokainen käyttäjä luo uuden Timerin tai säikeen, järjestelmä voi nopeasti hajota, kun säikeet loppuvat.

On useita käyttökelpoisia vaihtoehtoja, jotka toimivat tässä mittakaavassa: [**virtuaaliset säikeet**](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html), [**Spring TaskExecutor ja TaskScheduler**](https://docs.spring.io/spring-framework/reference/integration/scheduling.html), ja **`Interval`**.
Sovelluksestasi ja käyttötapauksestasi riippuen jokin näistä voi olla paras vaihtoehto sinulle.
Oletusarvoisesti `Interval` on luotettava valinta, joka on erityisesti suunniteltu toimimaan webforJ:n kanssa, eikä se vaadi lisäasetuksia.
