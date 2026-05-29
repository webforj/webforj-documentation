---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: a69f444ce2e5a9dea37304d466f4e6ac
---
Seuraavassa osiossa käsitellään erilaisia suorituskykyominaisuuksia ja parhaita käytäntöjä webforJ:lle sekä kehyksen toteutustietoja.

Kun luodaan sovellusta webforJ:llä, asiakas ja palvelin työskentelevät yhdessä tietojen käsittelemiseksi. Asiakkaan ja palvelimen välinen tiedonsiirto voidaan jakaa laajoihin luokkiin:

## 1. Palvelimelta asiakkaalle {#1-server-to-client}

webforJ:n menetelmät, kuten `setText()`, sisältyvät tähän luokkaan. webforJ-sovellus, joka toimii palvelimella, lähettää tietoja asiakkaalle ilman, että se odottaa vastausta. webforJ optimoi automaattisesti tämän kategorian operaatioita parantaakseen suorituskykyä. 

## 2. Asiakkaalta palvelimelle {#2-client-to-server}

Tämä kategoria kattaa tapahtumaliikenteen, kuten `Button.onClick()` -menetelmän. Suurimmaksi osaksi asiakas lähettää tapahtumia palvelimelle ilman, että se odottaa mitään vastausta. Tapahtumaobjekti sisältää yleensä lisäparametreja, jotka liittyvät tapahtumaan, kuten hashkoodin. Koska tämä tieto toimitetaan palvelimelle osana tapahtuman toimittamista, se on heti käytettävissä ohjelmassa heti kun tapahtuma on vastaanotettu.

## 3. Palvelimelta asiakkaalle ja takaisin palvelimelle (menomatka) {#3-server-to-client-to-server-round-trip}

Menomatkat suoritetaan, kun sovellus kysyy asiakkaalta joitakin dynaamisia tietoja, joita ei voida välimuistittaa palvelimelle. Menetelmät, kuten `Label.getText()` ja `Checkbox.isChecked()`, kuuluvat tähän kategoriaan. Kun webforJ-sovellus suorittaa rivin, kuten `String title = myLabel.getText()`, se pysähtyy täysin samalla, kun palvelin lähettää tuon pyynnön asiakkaalle ja odottaa sitten asiakkaan vastausta.

Jos sovellus lähettää useita viestejä asiakkaalle, jotka eivät vaadi vastausta (kategoria 1), ja sen jälkeen yhden viestin, joka vaatii menomatkan (kategoria 3), sovelluksen on odotettava asiakkaan käsittelevän kaikki keskeneräiset viestit, ennen kuin se vastaa viimeiseen viestiin, joka vaatii vastausta. Joissakin tapauksissa tämä voi aiheuttaa viivettä. Jos tuota menomatkaa ei olisi otettu huomioon, asiakas olisi voinut jatkaa työntekoa käsitellessään niitä viivästyneitä viestejä, kun taas palvelimella toimiva sovellus olisi siirtynyt uuteen työhön.

## Suorituskyvyn parantaminen {#improve-performance}

On mahdollista parantaa reagointikykyä merkittävästi välttämällä kolmannen kategorian menomatkoja mahdollisimman paljon. Esimerkiksi ComboBoxin onSelect-toiminnallisuuden muuttaminen seuraavasta:

```java
private void comboBoxSelect(ListSelectEvent ev){
  ComboBox component = (ComboBox) ev.getComponent();

  // Mene asiakkaalle
  int selected = component.getSelectedIndex();
}
```

seuraavaksi:

```java
private void comboBoxSelect(ListSelectEvent ev){
  // Hanki arvo tapahtumasta
  int selected = ev.getSelectedIndex();
}
```

Ensimmäisessä koodinpätkässä `ComboBox.getSelectedIndex()` -menetelmän kutsuminen komponentilla pakottaa menomatkan takaisin asiakkaalle, mikä tuo viivettä. Toisessa versiossa käyttö tapahtuman `ListSelectEvent.getSelectedIndex()` -menetelmää saa arvon, joka toimitettiin palvelimelle osana alkuperäistä tapahtumaa.

## Välimuisti {#caching}

webforJ optimoi suorituskykyä edelleen hyödyntämällä välimuistia. Yleisesti ottaen tässä yhteydessä on kahta tyyppiä tietoa: tietoa, jota käyttäjä voi suoraan muuttaa, ja tietoa, jota käyttäjä ei voi muuttaa. Ensimmäisessä tapauksessa, kun haetaan tietoja, joihin käyttäjät voivat suoraan vaikuttaa, on tarpeen kysyä palvelimelta näitä tietoja.

Kuitenkin tietoa, jota käyttäjä ei voi muuttaa, voidaan välimuistittaa, jotta voidaan välttää lisäsuorituskykyongelmia. Tämä varmistaa, että menomatkaa ei tarvitse tehdä tarpeettomasti, tarjoten tehokkaamman käyttäjäkokemuksen. webforJ optimoi sovelluksia tällä tavalla varmistaakseen optimaalisen suorituskyvyn.

## Latausaika {#loading-time}

Kun käyttäjä käynnistää webforJ-sovelluksen, se lataa vain pienen osan (vain noin 2,5 kB gzip) JavaScriptiä käynnistääksesi istunnon. Tämän jälkeen se lataa dynaamisesti yksittäisiä viestejä tai JavaScript-palasia tarpeen mukaan, kun sovellus käyttää vastaavaa toiminnallisuutta. Esimerkiksi palvelin lähettää asiakkaalle JavaScriptin, joka on tarpeen webforJ `Button`:in rakentamiseksi vain kerran, kun sovellus luo ensimmäisen `Button`-komponenttinsa. Tämä johtaa mitattaviin parannuksiin alkuperäisessä latausajassa, mikä parantaa käyttäjäkokemusta.
