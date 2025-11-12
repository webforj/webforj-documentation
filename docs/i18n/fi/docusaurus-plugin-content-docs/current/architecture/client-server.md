---
sidebar_position: 5
title: Asiakaspalvelinvuorovaikutus
_i18n_hash: e5eafeb3f76c9a412d5a124f2eed2da8
---
Seuraava osa käsittelee erilaisia suorituskykylaatuja ja parhaita käytäntöjä webforJ:lle, sekä kehysrakenteen toteutustietoja.

Kun luodaan sovellusta webforJ:ssa, asiakas ja palvelin työskentelevät yhdessä manipuloidakseen tietoja, mikä voidaan jakaa laajoihin kategorioihin:

## 1. Palvelimelta asiakkaalle {#1-server-to-client}

webforJ-menetelmät, kuten `setText()`, sisältyvät tähän kategoriaan. webforJ-sovellus, joka toimii palvelimella, lähettää tietoja asiakkaalle odottamatta vastausta. webforJ optimoi automaattisesti tämän kategorian operaatioiden erät parantaakseen suorituskykyä.

## 2. Asiakkaalta palvelimelle {#2-client-to-server}

Tämä kategoria kattaa tapahtumaliikenteen, kuten `Button.onClick()` -menetelmän. Suurimmaksi osaksi asiakas lähettää tapahtumia palvelimelle odottamatta mitään vastausta. Tapahtumaobjekti sisältää tyypillisesti lisäparametreja, jotka liittyvät tapahtumaan, kuten hashkoodin. Koska tämä tieto toimitetaan palvelimelle osana tapahtuman toimittamista, se on heti käytettävissä ohjelmassa heti, kun tapahtuma vastaanotetaan.

## 3. Palvelimelta asiakkaalle ja takaisin palvelimelle (kaksisuuntainen matka) {#3-server-to-client-to-server-round-trip}

Kaksisuuntaisia matkoja tehdään, kun sovellus kysyy asiakkaalta dynaamista tietoa, jota ei voida välimuistittaa palvelimella. Menetelmiä, kuten `Label.getText()` ja `Checkbox.isChecked()`, käytetään tässä kategoriassa. Kun webforJ-sovellus suorittaa rivin, kuten `String title = myLabel.getText()`, se pysähtyy kokonaan, kunnes palvelin lähettää tuon pyynnön asiakkaalle ja odottaa sitten asiakkaan vastausta.

Jos sovellus lähettää useita viestejä asiakkaalle, jotka eivät vaadi vastausta (kategoria 1), ja sitten yhden viestin, joka vaatii kaksisuuntaista matkaa (kategoria 3), sovelluksen on odotettava, että asiakas käsittelee kaikki odottavat viestit ja vastaa sitten viimeiseen viestiin, joka vaatii vastausta. Joissakin tapauksissa tämä voi lisätä viivettä. Jos tätä kaksisuuntaista matkaa ei olisi otettu käyttöön, asiakas olisi voinut jatkaa työskentelyä käsitellessään näitä viivästyneitä viestejä samalla kun palvelimella toimiva sovellus siirtyi uusiin tehtäviin.

## Suorituskyvyn parantaminen {#improve-performance}

Sovelluksen reagointikykyä voidaan merkittävästi parantaa välttämällä kolmannen kategorian kaksisuuntaisia matkoja mahdollisimman paljon. Esimerkiksi ComboBoxin onSelect-toiminnallisuuden muuttaminen seuraavaksi:

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
    // Hae arvo tapahtumasta
    int selected = ev.getSelectedIndex();
}
```

Ensimmäisessä osassa `ComboBox.getSelectedIndex()`, joka suoritetaan komponentilla, pakottaa kaksisuuntaisen matkan takaisin asiakkaalle, mikä aiheuttaa viivettä. Toisessa versiossa tapahtuman `ListSelectEvent.getSelectedIndex()` -menetelmä palauttaa arvon, joka toimitettiin palvelimelle osana alkuperäistä tapahtumaa.

## Välimuisti {#caching}

webforJ optimoi suorituskykyä edelleen hyödyntämällä välimuistia. Yleisesti ottaen tässä kontekstissa on olemassa kahta tyyppiä tietoja: tietoja, joita käyttäjä voi suoraan muuttaa, ja tietoja, joita käyttäjä ei voi muuttaa. Ensimmäisessä tapauksessa, kun haetaan tietoja, joihin käyttäjät suoraan vuorovaikuttavat, on tarpeen kysyä palvelimelta näitä tietoja.

Kuitenkin, tietoa, jota käyttäjä ei voi muuttaa, voidaan välimuistittaa, jotta vältetään ylimääräiset suorituskykyhäiriöt. Tämä varmistaa, että kaksisuuntaista matkaa ei tarvitse tehdä tarpeettomasti, mikä tarjoaa tehokkaamman käyttökokemuksen. webforJ optimoi sovelluksia tällä tavalla optimaalisen suorituskyvyn varmistamiseksi.

## Latausaika {#loading-time}

Kun käyttäjä käynnistää webforJ-sovelluksen, se lataa vain pienen määrän (vain noin 2,5 kB gzip) JavaScriptiä istunnon käynnistämiseksi. Tämän jälkeen se lataa dynaamisesti yksittäiset viestit tai JavaScript-osia tarpeen mukaan, kun sovellus käyttää vastaavaa toiminnallisuutta. Esimerkiksi palvelin lähettää asiakkaalle JavaScriptin, joka on tarpeen webforJ `Button`-komponentin rakentamiseen vain kerran — kun sovellus luo ensimmäisen `Button` -komponenttinsa. Tämä parantaa merkittävästi aloituslatausaikaa, mikä parantaa käyttäjäkokemusta.
