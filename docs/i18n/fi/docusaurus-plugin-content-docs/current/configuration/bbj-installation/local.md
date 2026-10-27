---
sidebar_position: 4
description: >-
  Install BBj locally and use the Plugin Manager to configure the webforJ plugin
  for running apps against a local BBjServices instance.
_i18n_hash: 5fbe602fa83b6147a07c4e2ad8ac3230
---
# Paikallinen Asennus

:::danger Vanhentunut ja poistetaan
Tällä sivulla kuvattu BBj Plugin Managerin webforJ-lisäosa on vanhentunut ja se poistetaan tulevassa julkaisussa. Älä aloita uusia projekteja sen kanssa. Siirrä olemassa olevat projektit [BBjShip](/docs/configuration/bbjship/overview), joka asentaa, ottaa käyttöön ja ajaa webforJ-sovelluksia BBjServicesissä. [Olemassa olevat projektit](/docs/configuration/bbjship/existing-projects) listaa muutokset, joita projekti tarvitsee.
:::

Tässä asiakirjan osassa käsitellään vaiheita, jotka on pakollisia vain käyttäjille, jotka haluavat käyttää webforJ:ta web- ja/tai sovelluskehityksessä paikallisella BBj-instanssilla omalla koneellaan. Tämä asennus ei salli käyttäjien myötävaikuttaa webforJ:n perustakoodiin.
<br/>

:::info
Tämä opas käsittelee asennusta Windows-järjestelmässä - asennusvaiheet voivat vaihdella Mac-/Linux-käyttöjärjestelmissä.
:::
<br/>

Asennus jaetaan seuraaviin vaiheisiin:

1. BBj:n lataaminen ja asentaminen
2. BBj Plugin Managerin käyttäminen sovelluksesi luomiseen
3. Sovelluksesi käynnistäminen

:::tip Edellytykset
Ennen kuin aloitat, varmista, että olet tarkistanut tarvittavat [edellytykset](../../introduction/prerequisites) webforJ:n asentamiseksi ja käyttämiseksi. Tämä varmistaa, että sinulla on kaikki tarvittavat työkalut ja määritykset paikoillaan ennen projektisi aloittamista.
:::


## 1. BBj:n lataaminen ja asentaminen {#1-bbj-download-and-installation}

<b>Kun seuraat tätä vaihetta, varmista, että asennat BBj-version, joka vastaa samaa webforJ-versiota. </b><br/><br/>

[Tämä video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) voi auttaa BBj:n asennuksessa, jos tarvitset apua asetuksessa. BASIS-verkkosivuston asennusosio löytyy [tästä linkistä](https://basis.cloud/download-product).

:::tip
On suositeltavaa käyttää BBj:n viimeisintä vakaata versioita ja valita "BBj" vaihtoehtolistasta ilman Baristaa tai lisäosia.
:::

<a name='section3'></a>

## 2. Asenna ja määritä webforJ-lisäosa

Kun BBj on asennettu, Plugin Manager voidaan avata asentamaan webforJ:n määrittämiseen tarvittavat työkalut. Aloita kirjoittamalla "Plugin Manager" aloitusvalikkoon tai Finderiin.

![Plugin manager configuration](/img/bbj-installation/local/Step_1l.png#rounded-border)

Kun lisäohjelman hallinta on avattu, siirry ylhäällä olevan "Saatavilla olevat lisäosat" -välilehteen.

![Plugin manager configuration](/img/bbj-installation/local/Step_2l.png#rounded-border)

Tässä osiossa valitse "Näytä kehitysvaiheessa olevat versiot" -valintaruutu.

![Plugin manager configuration](/img/bbj-installation/local/Step_3l.png#rounded-border)

DWCJ-kohteen pitäisi nyt näkyä saatavilla olevien ladattavien lisäosien luettelossa. Napsauta tätä kohdetta valitaksesi sen.

![Plugin manager configuration](/img/bbj-installation/local/Step_4l.png#rounded-border)

Kun DWCJ-kohde on valittu, napsauta "Asenna" -painiketta.

![Plugin manager configuration](/img/bbj-installation/local/Step_5l.png#rounded-border)

Kun lisäosa on asennettu, napsauta ylhäällä olevaa "Asennetut lisäosat" -välilehteä.

![Plugin manager configuration](/img/bbj-installation/local/Step_6l.png#rounded-border)

Tämä välilehti näyttää asennetut lisäosat, joiden pitäisi nyt sisältää DWCJ-kohde. Napsauta kohdetta luettelossa.

![Plugin manager configuration](/img/bbj-installation/local/Step_7l.png#rounded-border)

Kun DWCJ-kohde on valittu, napsauta "Määritä" -painiketta.

![Plugin manager configuration](/img/bbj-installation/local/Step_8l.png#rounded-border)

Aukeavassa ikkunassa napsauta ikkunan vasemmassa alareunassa olevaa "Ota käyttöön Maven Remote Install" -painiketta.

![Plugin manager configuration](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip

Vaihtoehtoisesti siirry `bbx` -kansiosi `bin` -hakemistoon ja suorita seuraava komento:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Ilmoitus tulee näkyviin, että etäasennus on otettu käyttöön. Napsauta "OK" sulkeaksesi tämän valintaikkunan.

## 3. Käynnistysprojektiin siirtyminen
Kun BBj ja tarvittava webforJ-lisäosa on asennettu ja määritetty, voit luoda uuden, valmiiksi rakennettua projektia komentoriviltä. Tämä projekti sisältää tarvittavat työkalut ensimmäisen webforJ-ohjelmasi suorittamiseen.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Sovelluksen käynnistäminen

Kun tämä on tehty, suorita `mvn install` projektikansiossasi. Tämä käynnistää webforJ-asennuslisäosan ja antaa sinulle pääsyn sovellukseesi. Näet sovelluksen menemällä seuraavaan URL-osoitteeseen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Korvaa `YourHostPort` Dockerilla määrittämälläsi isäntäportilla, ja `YourPublishName` korvataan POM-tiedoston `<publishname>` -tagin sisällä olevalla tekstillä.
Jos kaikki on tehty oikein, sinun pitäisi nähdä sovelluksesi renderoitu.
