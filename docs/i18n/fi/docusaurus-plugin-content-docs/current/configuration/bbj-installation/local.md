---
sidebar_position: 4
description: >-
  Install BBj locally and use the Plugin Manager to configure the webforJ plugin
  for running apps against a local BBjServices instance.
_i18n_hash: c6cfdc6b07db675d741ea4a096f286ca
---
# Paikallinen asennus

Tämä dokumentaatio-osuus kattaa vaiheet, jotka ovat tarpeen vain käyttäjille, jotka haluavat käyttää webforJ:ta verkkosovellusten ja/tai sovelluskehityksen kanssa paikallisella BBj-instantilla heidän koneessaan. Tämän asennuksen avulla käyttäjät eivät voi osallistua webforJ-säätiön koodin kehittämiseen.
<br/>

:::info
Tämä vaiheittainen opas kattaa asennuksen Windows-järjestelmässä - asennusvaiheet saattavat vaihdella Mac/Linux- käyttöjärjestelmissä.
:::
<br/>

Asennus jaetaan seuraaviin vaiheisiin:


1. BBj:n lataaminen ja asennus
2. BBj:n lisäosien hallinnan käyttäminen sovelluksesi luomiseen
3. Sovelluksesi käynnistaminen


:::tip Esivaatimukset
Ennen aloittamista varmista, että olet tarkistanut tarvittavat [esivaatimukset](../../introduction/prerequisites) webforJ:n määrittämiseksi ja käyttämiseksi. Tämä varmistaa, että sinulla on kaikki tarvittavat työkalut ja konfiguraatiot paikoillaan ennen projektisi aloittamista.
:::


## 1. BBj:n lataaminen ja asennus {#1-bbj-download-and-installation}

<b>Seuratessasi tätä vaihetta, varmista, että asennat BBj-version, joka vastaa samaa webforJ-versiota. </b><br/><br/>

[Tämä video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) voi auttaa BBj:n asennuksessa, jos tarvitset apua asetusten kanssa. BASIS-verkkosivuston asennusosio löytyy [tältä linkiltä](https://basis.cloud/download-product)

:::tip
Suositellaan, että käytät BBj:n viimeisintä vakaita rakennetta, ja valitset "BBj" vaihtoehtoluettelosta ilman Baristaa tai lisäosia.
:::

<a name='section3'></a>

## 2. Asenna ja määritä webforJ-lisäosa

Kun BBj on asennettu, voit käyttää Lisäosien hallintaa asentaaksesi työkalut, joita tarvitaan webforJ:n määrittämiseksi. Aloita kirjoittamalla "Plugin Manager" Käynnistä-valikkoon tai Finderiin.

![Plugin manager configuration](/img/bbj-installation/local/Step_1l.png#rounded-border)

Kun lisäosien hallinta on avautunut, siirry ylhäällä olevaan "Saatavilla olevat lisäosat" -välilehteen.

![Plugin manager configuration](/img/bbj-installation/local/Step_2l.png#rounded-border)

Tässä osiossa valitse "Näytä kehitysvaiheessa olevat versiot" -valintaruutu

![Plugin manager configuration](/img/bbj-installation/local/Step_3l.png#rounded-border)

DWCJ-merkkivalinta pitäisi nyt olla näkyvissä saatavilla olevien lisäosien luettelossa. Klikkaa tätä merkintää valitaksesi sen.

![Plugin manager configuration](/img/bbj-installation/local/Step_4l.png#rounded-border)

Kun DWCJ-merkkivalinta on valittuna, napsauta "Asenna" -painiketta

![Plugin manager configuration](/img/bbj-installation/local/Step_5l.png#rounded-border)

Kun lisäosa on asennettu, napsauta "Asennetut lisäosat" -välilehteä ylhäällä.

![Plugin manager configuration](/img/bbj-installation/local/Step_6l.png#rounded-border)

Tämä välilehti näyttää asennetut lisäosat, jotka pitäisi nyt sisältää DWCJ-merkinnän. Klikkaa luettelossa olevaa merkintää.

![Plugin manager configuration](/img/bbj-installation/local/Step_7l.png#rounded-border)

Kun DWCJ-merkintä on valittuna, napsauta "Määritä" -painiketta

![Plugin manager configuration](/img/bbj-installation/local/Step_8l.png#rounded-border)

Avaavassa ikkunassa napsauta "Ota Maven Remote Install käyttöön" -painiketta ikkunan vasemmassa alakulmassa.

![Plugin manager configuration](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip

Vaihtoehtoisesti siirry `bin`-hakemistoon `bbx`-kansiossasi ja suorita seuraava komento:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Viestiruudun pitäisi näyttää, että etäasennus on otettu käyttöön. Napsauta "OK" sulkeaksesi tämän valintaikkunan.

## 3. Käyttäen aloitusprojektia
Kun BBj ja tarvittu webforJ-lisäosa on asennettu ja määritetty, voit luoda uuden, alustetun projektin komentoriviltä. Tämä projekti sisältää tarvittavat työkalut ensimmäisen webforJ-ohjelmasi suorittamiseen.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Sovelluksen käynnistäminen

Kun tämä on tehty, suorita `mvn install` projektihakemistossasi. Tämä suorittaa webforJ:n asennuslisäosan ja antaa sinun käyttää sovellustasi. Näet sovelluksen siirtymällä seuraavaan URL-osoitteeseen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Korvaa `YourHostPort` Dockerille määrittämälläsi isäntäportilla, ja `YourPublishName` korvataan POM-tiedoston `<publishname>`-tagin sisällä olevalla tekstillä. Jos kaikki on tehty oikein, sinun pitäisi nähdä sovelluksesi renderöityvän.
