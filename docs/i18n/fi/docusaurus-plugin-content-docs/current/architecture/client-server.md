---
sidebar_position: 5
title: Client/Server Interaction
description: >-
  Understand how webforJ batches server-to-client calls, avoids costly round
  trips, and uses caching and on-demand chunks for performance.
_i18n_hash: 893b34ce2601ff273d03ba4091b7bc51
---
Seuraavassa osiossa käsitellään erilaisia suorituskykyominaisuuksia ja parhaimpia käytäntöjä webforJ:lle sekä toteutustietoja kehyksestä.

Kun luodaan sovellusta webforJ:llä, asiakas ja palvelin työskentelevät yhdessä manipuloiakseen tietoja, joita asiakkaan ja palvelimen välillä voidaan jakaa laajoihin kategorioihin:

## 1. Palvelimelta asiakkaalle {#1-server-to-client}

webforJ:n menetelmiä, kuten `setText()`, on sisältää tämä kategoria. webforJ-sovellus, joka toimii palvelimella, lähettää tietoja asiakkaalle odottamatta vastausta. webforJ optimoi automaattisesti toimintojen eräkäsittelyt tässä kategoriassa parantaakseen suorituskykyä.

## 2. Asiakkaalta palvelimelle {#2-client-to-server}

Tämä kategoria kattaa tapahtumaliikenteen, kuten `Button.onClick()`-menetelmän. Suurimmaksi osaksi asiakas lähettää tapahtumia palvelimelle odottamatta vastausta. Tapahtumaobjekti sisältää tyypillisesti lisäparametreja, jotka liittyvät tapahtumaan, kuten hashkoodin. Koska nämä tiedot toimitetaan palvelimelle osana tapahtuman toimittamista, ne ovat heti käytettävissä ohjelmassa heti, kun tapahtuma on vastaanotettu.

## 3. Palvelimelta asiakkaalle ja takaisin palvelimelle (hämmennys) {#3-server-to-client-to-server-round-trip}

Hämmennyksiä suoritetaan, kun sovellus kysyy asiakkaalta joitakin dynaamisia tietoja, joita ei voida välimuistittaa palvelimella. Menetelmiä, kuten `Label.getText()` ja `Checkbox.isChecked()` kuuluu tähän kategoriaan. Kun webforJ-sovellus suorittaa rivin, kuten `String title = myLabel.getText()`, se pysähtyy täysin, kun palvelin lähettää tämän pyynnön asiakkaalle ja odottaa sitten, että asiakas lähettää vastauksen takaisin.

Jos sovellus lähettää useita viestejä asiakkaalle, jotka eivät vaadi vastausta (kategoria 1), ja sen jälkeen yhden viestin, joka vaatii hämmennyksen (kategoria 3), sovelluksen on odotettava, että asiakas käsittelee kaikki odottavat viestit, ja sitten vastaa viimeiseen viestiin, joka vaatii vastausta. Joissakin tapauksissa tämä voi aiheuttaa viivästyksen. Jos tätä hämmennystä ei olisi otettu käyttöön, asiakas olisi voinut jatkaa työskentelyään käsitellessään näitä ylimääräisiä viestejä samalla kun palvelimella toimiva sovellus olisi edennyt uusiin töihin.

## Paranna suorituskykyä {#improve-performance}

Suorituskykyä voidaan parantaa merkittävästi välttämällä kolmannen kategorian hämmennyksiä mahdollisimman paljon. Esimerkiksi muuttamalla ComboBoxin onSelect-toiminnallisuutta näin:

```java
private void comboBoxSelect(ListSelectEvent ev){
  ComboBox component = (ComboBox) ev.getComponent();

  // Mene asiakkaalle
  int selected = component.getSelectedIndex();
}
```

seuraavaan muotoon:

```java
private void comboBoxSelect(ListSelectEvent ev){
  //Hakee arvon tapahtumasta
  int selected = ev.getSelectedIndex();
}
```

Ensimmäisessä koodinpätkässä `ComboBox.getSelectedIndex()`-kutsun suorittaminen komponentilla pakottaa hämmennyksen takaisin asiakkaalle, mikä aiheuttaa viivästyksen. Toisessa versiossa käytetään tapahtuman `ListSelectEvent.getSelectedIndex()`-metodia arvon noutamiseen, joka toimitettiin palvelimelle osana alkuperäistä tapahtumaa.

## Välimuisti {#caching}

webforJ optimoi suorituskykyä edelleen hyödyntämällä välimuistia. Yleisesti ottaen tässä yhteydessä on olemassa kahta tyyppistä tietoa: tietoa, jota käyttäjä voi suoraan muuttaa, ja tietoa, jota käyttäjä ei voi muuttaa. Ensimmäisessä tapauksessa, kun haetaan tietoja, joihin käyttäjät suoraan ovat vuorovaikutuksessa, on tarpeen kysyä palvelimelta näitä tietoja.

Kuitenkin tietoa, jota käyttäjä ei voi muuttaa, voidaan välimuistittaa välttämään lisäsuorituskykyhäiriöitä. Tämä varmistaa, että hämmennystä ei tarvitse tehdä tarpeettomasti, mikä tarjoaa tehokkaamman käyttäjäkokemuksen. webforJ optimoi sovellukset tällä tavalla varmistaakseen optimaalisen suorituskyvyn.

## Latausaika {#loading-time}

Kun käyttäjä käynnistää webforJ-sovelluksen, se lataa vain pienen osan (vain noin 2.5 kB gzip) JavaScriptiä käynnistääkseen istunnon. Tämän jälkeen se latautuu dynaamisesti yksittäisiä viestejä tai JavaScriptin paloja tarpeen mukaan, kun sovellus käyttää vastaavaa toiminnallisuutta. Esimerkiksi palvelin lähettää asiakkaalle JavaScriptin, joka on tarpeen webforJ `Button`:n rakentamista varten vain kerran, kun sovellus luo ensimmäisen `Button`-komponenttinsa. Tämä johtaa mitattavissa oleviin parannuksiin alkuperäisessä latausajassa, mikä parantaa käyttäjäkokemusta.
