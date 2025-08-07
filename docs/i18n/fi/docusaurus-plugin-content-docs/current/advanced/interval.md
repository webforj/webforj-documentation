---
sidebar_position: 15
title: Interval
_i18n_hash: 07054545ea64670e83423a6b11a5cce3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

Luokka <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> edustaa ajastinta, joka laukaisee [tapahtuman](../building-ui/events) määritellyn aikavälin jälkeen jokaisen laukaisun välillä.

`Interval`-luokka tarjoaa yksinkertaisen tavan laukaista tapahtumia määritellyn viiveen jälkeen. On mahdollista käynnistää, pysäyttää ja nollata `Interval` tarpeen mukaan. Lisäksi Intervals voi tukea useita kuuntelijoita kuluneelle tapahtumalle. 
Optimoitu webforJ-kehykselle, se tarjoaa paremman suorituskyvyn verrattuna standardiin Java-ajastimeen tai Swing-ajastimeen.

## Käytöt {#usages}
`Interval`-luokka laukaisee tapahtumia tietyllä aikavälillä. Hyödyntämällä Intervalsia luovasti voit parantaa käyttäjävuorovaikutusta ja sitoutumista verkkosivustollasi samalla pitäen kokemuksen dynaamisena ja mielenkiintoisena:

1. **Inaktiivisuuden tarkistaminen**: Näytä [`Dialog`](../components/dialog) -komponentti, jos lomakkeessa ei ole ollut vuorovaikutusta tietyn ajan kuluessa.

2. **Esitellyt sisällöt**: Kierrätä esiteltyjä artikkeleita, tuotteita tai kampanjoita etusivullasi jokaisen Intervalin mukaan. Tämä pitää sisällön dynaamisena ja kiinnostavana.

3. **Reaaliaikaiset tiedot**: Päivitä tietoja sovelluksessasi, kuten osakehinnat, uutisfeedit tai säätiedot, jokaisen Intervalin myötä pitämään tiedot ajantasaisina.

## `Interval`-tilojen hallinta: käynnistäminen, pysäyttäminen ja nollaaminen {#managing-interval-states-starting-stopping-and-restart}
Interval vaatii manuaalista aktivoitumista; käytä `start()`-metodia sen käynnistämiseen. Pysäyttääksesi Intervalin, käytä `stop()`-metodia. `restart()`-metodia voidaan käyttää Intervalin nollaamiseen.

## `Interval`-viiveen säätäminen {#adjusting-the-interval-delay}

Muokataksesi Intervalin viivettä, käytä `setDelay(float delay)` -metodia. Uusi viivearvo tulee voimaan, kun Interval joko pysäytetään tai nollataan.

```java
//Viiveen muuttaminen
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Viive voi olla osittaisia sekunteja millisekunnin tarkkuudella, mutta erittäin pieni aikakatkoarvo aiheuttaa tapahtumien tulvan nopeammin kuin ohjelma pystyy niihin reagoimaan.
:::

## Kuuntelijoiden lisääminen {#adding-listeners}

Voit liittää lisäkuuntelijoita Intervaliin käyttämällä `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` -metodia. Kun kuuntelija on lisätty, se laukaistaan automaattisesti seuraavalla väliin, jos Interval on jo käynnissä.

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
