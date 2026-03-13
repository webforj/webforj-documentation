---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: ae7a34d844eee10906ce2230f95a05cc
---
Seuraavassa osiossa käsitellään erilaisia suorituskykyominaisuuksia ja parhaita käytäntöjä webforJ:lle sekä toteutustietoja kehyksestä.

Kun luodaan sovellus webforJ:ssä, asiakas ja palvelin työskentelevät yhdessä manipuloidakseen tietoja asiakkaan ja palvelimen välillä. Tämä voidaan jakaa laajoihin kategorioihin:

## 1. Palvelimelta asiakkaalle {#1-server-to-client}

webforJ:n menetelmiä, kuten `setText()`, sisältyy tähän kategoriaan. webforJ-sovellus, joka toimii palvelimella, lähettää tietoja asiakkaalle odottamatta vastausta. webforJ optimoi automaattisesti tämän kategorian toimintojen erät parantaakseen suorituskykyä.

## 2. Asiakkaalta palvelimelle {#2-client-to-server}

Tämä kategoria kattaa tapahtumaliikenteen, kuten `Button.onClick()`-menetelmän. Suurimmaksi osaksi asiakas lähettää tapahtumia palvelimelle odottamatta vastausta. Tapahtumaobjekti sisältää tyypillisesti lisäparametreja, jotka liittyvät tapahtumaan, kuten hashtunnuksen. Koska nämä tiedot toimitetaan palvelimelle osana tapahtuman toimitusta, ne ovat heti käytettävissä ohjelmassa heti, kun tapahtuma vastaanotetaan.

## 3. Palvelimelta asiakkaalle palvelimelle (kahden matkan) {#3-server-to-client-to-server-round-trip}

Kahden matkan toteutetaan, kun sovellus kysyy asiakkaalta joitakin dynaamisia tietoja, joita ei voida välimuistittaa palvelimella. Menetelmät, kuten `Label.getText()` ja `Checkbox.isChecked()`, kuuluvat tähän kategoriaan. Kun webforJ-sovellus suorittaa rivin, kuten `String title = myLabel.getText()`, se pysähtyy kokonaan samalla, kun palvelin lähettää tuon pyynnön asiakkaalle ja sitten odottaa asiakkaan vastausta.

Jos sovellus lähettää useita viestejä asiakkaalle, jotka eivät vaadi vastausta (kategoria 1), ja sitten yhden viestin, joka vaatii kahden matkan (kategoria 3), sovelluksen on odotettava asiakkaan käsittelevän kaikki odottavat viestit, ja sitten vastattava viimeiseen viestiin, joka vaatii vastausta. Joissain tapauksissa tämä voi lisätä viivettä. Jos tuota kahden matkan pyyntöä ei olisi esitetty, asiakas olisi voinut jatkaa työntekoa käsittelemällä noita odottavia viestejä, kun palvelimella toimiva sovellus olisi siirtynyt uusiin tehtäviin.

## Suorituskyvyn parantaminen {#improve-performance}

Voit merkittävästi parantaa reagointikykyä välttämällä kolmannen kategorian kahden matkan pyyntöjä niin paljon kuin mahdollista. Esimerkiksi muuttamalla ComboBoxin onSelect-toiminnallisuutta seuraavasti:

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
    //Hae arvo tapahtumasta
    int selected = ev.getSelectedIndex();
}
```

Ensimmäisessä koodissa `ComboBox.getSelectedIndex()`-toiminnon suorittaminen komponentilla pakottaa kahden matkan paluu asiakkaalle, mikä aiheuttaa viivettä. Toisessa versiossa, käyttämällä tapahtuman `ListSelectEvent.getSelectedIndex()`-menetelmää, saadaan arvo, joka toimitettiin palvelimelle alkuperäisen tapahtuman osana.

## Välimuisti {#caching}

webforJ optimoi suorituskykyä edelleen hyödyntämällä välimuistia. Yleisesti ottaen tässä kontekstissa on kahta tyyppiä tietoja: tietoja, joita käyttäjä voi suoraan muuttaa, ja tietoja, joita käyttäjä ei voi muuttaa. Ensimmäisessä tapauksessa, kun haetaan tietoja, joihin käyttäjät suoraan vuorovaikuttavat, on tarpeen kysyä palvelimelta näitä tietoja.

Kuitenkin, tietoja, joita käyttäjä ei voi muuttaa, voidaan välimuistittaa lisätilan säästämiseksi. Tämä varmistaa, että kahden matkan pyyntöjä ei tarvitse tehdä tarpeettomasti, mikä tarjoaa tehokkaamman käyttökokemuksen. webforJ optimoi sovelluksia tällä tavalla varmistaakseen optimaalisen suorituskyvyn.

## Latausaika {#loading-time}

Kun käyttäjä käynnistää webforJ-sovelluksen, se lataa vain pienen osan (noin 2,5 kB gzip) JavaScriptistä aloittaakseen istunnon. Tämän jälkeen se lataa dynaamisesti yksittäisiä viestejä tai JavaScript-osia tarpeen mukaan, kun sovellus käyttää vastaavaa toiminnallisuutta. Esimerkiksi palvelin lähettää asiakkaalle JavaScriptin, joka on tarpeen webforJ `Button`:n rakentamiseen vain kerran, kun sovellus luo ensimmäisen `Button`-komponenttinsa. Tämä johtaa havaittaviin parannuksiin aloituslatausajassa, mikä parantaa käyttökokemusta.
