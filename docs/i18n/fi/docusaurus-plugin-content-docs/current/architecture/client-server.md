---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: ed7cdbde8cee6b173108326dfa7fce2a
---
Seuraavassa osiossa käsitellään erilaisia suorituskykyominaisuuksia ja parhaita käytäntöjä webforJ:lle sekä kehyksen toteutuksen yksityiskohtia.

Kun luot sovellusta webforJ:ssä, asiakas ja palvelin työskentelevät yhdessä käsitelläkseen tietoja asiakkaan ja palvelimen välillä. Tämä voidaan jakaa laajoihin kategorioihin:

## 1. Palvelimelta asiakkaalle {#1-server-to-client}

webforJ:n menetelmiä, kuten `setText()`, sisältyy tähän kategoriaan. webforJ-sovellus, joka toimii palvelimella, lähettää tietoa asiakkaalle ilman, että odotetaan vastausta. webforJ optimoi automaattisesti tämän kategorian operaatioiden erät suorituskyvyn parantamiseksi.

## 2. Asiakkaalta palvelimelle {#2-client-to-server}

Tämä kategoria kattaa tapahtumaliikenteen, kuten `Button.onClick()`-menetelmän. Suurimmaksi osaksi asiakas lähettää tapahtumia palvelimelle ilman, että odotetaan mitään vastausta. Tapahtumaobjekti sisältää yleensä lisäparametreja, jotka liittyvät tapahtumaan, kuten hashkoodin. Koska tämä tieto toimitetaan palvelimelle osana tapahtuman toimitusta, se on heti käytettävissä ohjelmalle heti, kun tapahtuma vastaanotetaan.

## 3. Palvelimelta asiakkaalle ja takaisin palvelimelle (kierros) {#3-server-to-client-to-server-round-trip}

Kierroksia suoritetaan, kun sovellus kysyy asiakkaalta dynaamista tietoa, jota ei voida välimuistissa pitää palvelimella. Menetelmät kuten `Label.getText()` ja `Checkbox.isChecked()` kuuluvat tähän kategoriaan. Kun webforJ-sovellus suorittaa rivin, kuten `String title = myLabel.getText()`, se pysähtyy kokonaan, kunnes palvelin lähettää tuon pyynnön asiakkaalle ja odottaa sitten asiakkaan lähettävän vastauksen takaisin.

Jos sovellus lähettää useita viestejä asiakkaalle, jotka eivät vaadi vastausta (kategoria 1), jota seuraa yksi viesti, joka vaatii kierroksen (kategoria 3), sovelluksen on odotettava, että asiakas käsittelee kaikki odottavat viestit, ja sitten vastattava viimeiseen viestiin, joka vaatii vastausta. Joissakin tapauksissa tämä voi lisätä viivettä. Jos tuota kierrosta ei olisi otettu käyttöön, asiakas olisi voinut jatkaa työskentelyä käsittelemällä niitä myöhästyneitä viestejä, kun sovellus palvelimella eteni uusiin työtehtäviin.

## Suorituskyvyn parantaminen {#improve-performance}

Sovelluksen reagointikykyä voidaan parantaa merkittävästi välttämällä kolmannen kategorian kierroksia mahdollisimman paljon. Esimerkiksi muuttamalla ComboBoxin onSelect-toiminnallisuus seuraavaksi:

```java
private void comboBoxSelect(ListSelectEvent ev){
    ComboBox component = (ComboBox) ev.getComponent();

    // Mene asiakkaalle
    int selected = component.getSelectedIndex();
}
```

tässä:

```java
private void comboBoxSelect(ListSelectEvent ev){
    // Hae arvo tapahtumasta
    int selected = ev.getSelectedIndex();
}
```

Ensimmäisessä esimerkissä `ComboBox.getSelectedIndex()`, joka suoritetaan komponentilla, pakottaa kierroksen takaisin asiakkaalle, mikä aiheuttaa viivettä. Toisessa versiossa käytetään tapahtuman `ListSelectEvent.getSelectedIndex()`-menetelmää arvon hakemiseen, joka toimitettiin palvelimelle osana alkuperäistä tapahtumaa.

## Välimuisti {#caching}

webforJ optimoi suoritusta edelleen hyödyntämällä välimuistia. Yleisesti ottaen tässä kontekstissa on kahta tyyppiä tietoa: tietoa, jota käyttäjä voi suoraan muuttaa, ja tietoa, jota käyttäjä ei voi muuttaa. Ensimmäisessä tapauksessa, kun käsitellään tietoa, jonka käyttäjät tulevat suoraan vuorovaikuttamaan, on tarpeen kysyä palvelimelta tätä tietoa.

Kuitenkin tieto, jota ei voi muuttaa käyttäjä, voidaan välimuististaa ylimääräisten suorituskykyongelmien välttämiseksi. Tämä varmistaa, että kierrosta ei tarvitse tehdä tarpeettomasti, mikä parantaa käyttäjäkokemusta. webforJ optimoi sovelluksia tällä tavoin varmistaakseen optimaalisen suorituskyvyn.

## Latausaika {#loading-time}

Kun käyttäjä käynnistää webforJ-sovelluksen, se lataa vain pienen osan (vain noin 2.5kB gzip) JavaScriptiä istunnon käynnistämiseksi. Tämän jälkeen se lataa dynaamisesti yksittäisiä viestejä tai osia JavaScriptiä tarpeen mukaan, kun sovellus käyttää vastaavaa toiminnallisuutta. Esimerkiksi palvelin lähettää asiakkaalle JavaScriptin, joka on tarpeen webforJ `Button`-rakentamiseen vain kerran — kun sovellus luo ensimmäisen `Button`-komponenttinsa. Tämä tuo mitattavia parannuksia alkuperäiseen latausaikaan, mikä johtaa parempaan käyttäjäkokemukseen.
