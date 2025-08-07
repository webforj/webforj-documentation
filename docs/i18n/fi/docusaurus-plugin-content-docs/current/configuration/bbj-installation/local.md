---
sidebar_position: 4
_i18n_hash: 9cf48c2124910b9a10a8ec4c5cd82205
---
# Paikallinen asennus

Tämä dokumentaation osa kattaa vain ne vaiheet, jotka ovat tarpeen käyttäjille, jotka haluavat käyttää webforJ:ta verkkosivujen ja/tai sovellusten kehittämiseen paikallisella BBj-instanssilla heidän koneellaan. Tämä asennus ei salli käyttäjien osallistua webforJ:n perustajakoodeihin.
<br/>

:::info
Tämä oppaasti kattaa asennuksen Windows-järjestelmässä - asennusvaiheet voivat vaihdella Mac/Linux-käyttöjärjestelmissä.
:::
<br/>

Asennus jaetaan seuraaviin vaiheisiin:

1. BBj lataaminen ja asentaminen
2. BBj Plugin Managerin käyttäminen oman sovelluksen luomiseen
3. Sovelluksen käynnistäminen

:::tip Edellytykset
Ennen kuin aloitat, varmista, että olet tarkistanut tarvittavat [edellytykset](../../introduction/prerequisites) webforJ:n käyttämiseen ja asentamiseen. Tämä varmistaa, että sinulla on kaikki tarvittavat työkalut ja asetukset kunnossa ennen projektisi aloittamista.
:::

## 1. BBj lataaminen ja asentaminen {#1-bbj-download-and-installation}

<b>Seuratessasi tätä vaihetta varmista, että asennat BBj-version, joka vastaa samaa webforJ-versiota.</b><br/><br/>

[Tämä video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) voi auttaa BBj:n asennuksessa, jos tarvitset apua asetuksissa. BASIS-sivuston asennusosio löytyy [tästä linkistä](https://basis.cloud/download-product).

:::tip
On suositeltavaa käyttää BBj:n uusinta vakaata versiota ja valita "BBj" vaihtoehtoluettelosta ilman Baristaa tai lisäosia.
:::

<a name='section3'></a>

## 2. Asenna ja määritä webforJ-laajennus

Kun BBj on asennettu, voit avata Plugin Managerin asentamaan työkaluja, joita tarvitaan webforJ:n määrittämiseen. Aloita kirjoittamalla "Plugin Manager" Käynnistä-valikkoon tai Finderiin.

![Plugin managerin asetukset](/img/bbj-installation/local/Step_1l.png#rounded-border)

Plugin managerin avautuessa siirry "Saatavilla olevat lisäosat" -välilehteen.

![Plugin managerin asetukset](/img/bbj-installation/local/Step_2l.png#rounded-border)

Tässä osiossa valitse "Näytä kehitysvaiheessa olevat versiot" -valintaruutu.

![Plugin managerin asetukset](/img/bbj-installation/local/Step_3l.png#rounded-border)

DWCJ-merkinnän pitäisi nyt näkyä saatavilla olevien ladattavien lisäosien luettelossa. Napsauta tätä merkintää valitaksesi sen.

![Plugin managerin asetukset](/img/bbj-installation/local/Step_4l.png#rounded-border)

Kun DWCJ-merkintä on valittu, napsauta "Asenna" -painiketta.

![Plugin managerin asetukset](/img/bbj-installation/local/Step_5l.png#rounded-border)

Kun lisäosa on asennettu, napsauta ylhäällä "Asennetut lisäosat" -välilehteä.

![Plugin managerin asetukset](/img/bbj-installation/local/Step_6l.png#rounded-border)

Tässä välilehdessä näkyvät asennetut lisäosat, joihin pitäisi nyt sisältyä DWCJ-merkintä. Napsauta merkintää luettelossa.

![Plugin managerin asetukset](/img/bbj-installation/local/Step_7l.png#rounded-border)

Kun DWCJ-merkintä on valittu, napsauta "Määritä" -painiketta.

![Plugin managerin asetukset](/img/bbj-installation/local/Step_8l.png#rounded-border)

Avautuvassa ikkunassa napsauta ikkunan vasemmassa alakulmassa olevaa "Ota käyttöön Maven Remote Install" -painiketta.

![Plugin managerin asetukset](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip 

Vaihtoehtoisesti voit siirtyä `bbx`-kansiosi `bin`-hakemistoon ja suorittaa seuraavan komennon:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Ilmoitusikkuna pitäisi näyttää, että etäasennus on otettu käyttöön. Napsauta "OK" sulkeaksesi tämän dialogin.

## 3. Käynnistäjän projektin käyttäminen
Kun BBj ja tarvittava webforJ-laajennus on asennettu ja määritetty, voit luoda uuden, valmiin projektin komentoriviltä. Tämä projekti sisältää tarvittavat työkalut ensimmäisen webforJ-ohjelmasi suorittamiseen.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Sovelluksen käynnistäminen

Kun tämä on tehty, suorita `mvn install` projektikansiossasi. Tämä suorittaa webforJ:n asennuslisäosan ja mahdollistaa sovelluksesi käyttämisen. Näet sovelluksen menemällä seuraavaan URL-osoitteeseen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Korvaa `YourHostPort` Dockerille määrittämälläsi isäntäsatamilla, ja `YourPublishName` vaihdetaan POM-tiedoston `<publishname>`-tunnisteen sisällöllä. 
Jos kaikki on tehty oikein, näet sovelluksesi renderöitynä.
