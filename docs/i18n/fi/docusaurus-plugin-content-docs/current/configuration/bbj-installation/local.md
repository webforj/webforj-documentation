---
sidebar_position: 4
_i18n_hash: a41d592f84a4dcd32f5398f3e57621a4
---
# Paikallinen asennus

Tässä asiakirjan osassa käsitellään vain niitä vaiheita, joita käyttäjät, jotka haluavat käyttää webforJ:ta verkkosivujen ja/tai sovellusten kehittämiseen paikallisen BBj-instanssin kanssa omalla koneellaan, tarvitsevat. Tämä asennus ei salli käyttäjien myötävaikuttaa webforJ-säätiön koodiin itseensä. 
<br/>

:::info
Tämä opas kattaa asennuksen Windows-järjestelmälle - asennusvaiheet voivat vaihdella Mac/Linux-käyttöjärjestelmissä.
:::
<br/>

Asennus jaetaan seuraaviin vaiheisiin:

1. BBj:n lataaminen ja asentaminen
2. BBj Plugin Managerin käyttäminen sovelluksesi luomiseen
3. Sovelluksesi käynnistäminen

:::tip Esivaatimukset
Ennen kuin aloitat, varmista, että olet tarkastellut tarvittavat [esivaatimukset](../../introduction/prerequisites) webforJ:n asentamiseen ja käyttöön. Tämä varmistaa, että sinulla on kaikki vaadittavat työkalut ja konfiguraatiot kunnossa ennen projektisi aloittamista.
:::

## 1. BBj:n lataaminen ja asentaminen {#1-bbj-download-and-installation}

<b>Seuratessasi tätä vaihetta varmista, että asennat BBj-version, joka vastaa samaa webforJ-versiota. </b><br/><br/>

[Tämä video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) voi auttaa BBj:n asennuksessa, jos tarvitset apua asetusten kanssa. Asennusosioon BASISin verkkosivustolla pääsee [tämän linkin](https://basis.cloud/download-product) kautta.

:::tip
Suositellaan käyttämään uusinta vakaata BBj:n rakennetta, ja valitsemaan "BBj" vaihtoehdoista, ilman Baristaa tai Addonia.
:::

<a name='section3'></a>

## 2. webforJ-liitännäisen asentaminen ja konfigurointi

Kun BBj on asennettu, voit käyttää Plugin Manageria asentaaksesi työkalut, jotka ovat tarpeen webforJ:n konfigurointiin. Aloita kirjoittamalla "Plugin Manager" käynnistysvalikkoon tai Finderiin. 

![Plugin managerin konfigurointi](/img/bbj-installation/local/Step_1l.png#rounded-border)

Kun plugin manager on avattu, siirry ylhäällä olevaan "Available Plugins" -välilehteen.

![Plugin managerin konfigurointi](/img/bbj-installation/local/Step_2l.png#rounded-border)

Tässä osiossa valitse "Show versions under development" -valintaruutu

![Plugin managerin konfigurointi](/img/bbj-installation/local/Step_3l.png#rounded-border)

DWCJ-kohteen pitäisi nyt näkyä saatavilla olevien ladattavien liitännäisten luettelossa. Napsauta tätä kohdetta valitaksesi sen.

![Plugin managerin konfigurointi](/img/bbj-installation/local/Step_4l.png#rounded-border)

Valittuasi DWCJ-kohteen, napsauta "Install"-painiketta

![Plugin managerin konfigurointi](/img/bbj-installation/local/Step_5l.png#rounded-border)

Kun liitännäinen on asennettu, napsauta ylhäällä olevaa "Installed Plugins" -välilehteä.

![Plugin managerin konfigurointi](/img/bbj-installation/local/Step_6l.png#rounded-border)

Tämä välilehti näyttää asennetut liitännäiset, joiden pitäisi nyt sisältää DWCJ-kohde. Napsauta luettelossa olevaa kohdetta.

![Plugin managerin konfigurointi](/img/bbj-installation/local/Step_7l.png#rounded-border)

Valittuasi DWCJ-kohteen, napsauta "Configure"-painiketta

![Plugin managerin konfigurointi](/img/bbj-installation/local/Step_8l.png#rounded-border)

Aukeavassa ikkunassa napsauta ikkunan vasemmassa alalaidassa olevaa "Enable Maven Remote Install" -painiketta.

:::tip 

Vaihtoehtoisesti voit navigoida `bin`-hakemistoon `bbx`-kansiossasi ja suorittaa seuraavan komennon:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Dialogi ilmoittaa, että etäasennus on otettu käyttöön. Napsauta "OK" sulkeaksesi tämän dialogin.

![Plugin managerin konfigurointi](/img/bbj-installation/local/Step_9l.png#rounded-border)

## 3. Alustavan projektin käyttäminen
Kun BBj ja tarvittava webforJ-liitännäinen on asennettu ja konfiguroitu, voit luoda uuden, valmisprojektin komentoriviltä. Tämä projekti sisältää tarvittavat työkalut ensimmäisen webforJ-ohjelmasi ajamiseen.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Sovelluksen käynnistäminen

Kun tämä on tehty, suorita `mvn install` projektin hakemistossasi. Tämä ajaa webforJ:n asennusliitännäisen ja mahdollistaa pääsyn sovellukseesi. Näet sovelluksen menemällä seuraavaan URL-osoitteeseen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Korvaa `YourHostPort` Dockerissa määrittämälläsi isäntäportilla ja `YourPublishName` korvataan `<publishname>`-tagin sisällä olevalla tekstillä POM-tiedostossa. Jos kaikki on tehty oikein, näet sovelluksesi renderöitynä.
