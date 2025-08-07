---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 8e58efd7b052a00eaf8cfce276cda92e
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup`-luokkaa käytetään ryhmittämään liittyvät radiopainikkeet yhteen, mikä auttaa määrittämään vaihtoehtojen keskinäisen ekskluusion kyseisessä ryhmässä. Käyttäjät voivat valita vain yhden radiopainikkeen tietyssä radioryhmässä. Kun käyttäjä valitsee radiopainikkeen ryhmässä, aiemmin valittu radiopainike samassa ryhmässä poistuu automaattisesti valinnasta. Tämä varmistaa, että vain yksi vaihtoehto voidaan valita kerrallaan.

:::tip
`RadioButton`-komponentti tallentaa ryhmän, johon se kuuluu, ja se voidaan saada käyttöön `getButtonGroup()`-menetelmällä.
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
`RadioButtonGroup`-komponentti ei renderöi HTML-elementtiä sivulle. Pikemminkin se on vain
logiikka, joka varmistaa, että joukko radiopainikkeita käyttäytyy ryhmänä sen sijaan, että ne käyttäytyisivät yksilöllisesti.
:::

## Käytöt {#usages}

`RadioButtonGroup` soveltuu parhaiten tilanteisiin, joissa käyttäjien tarvitsee tehdä yksi valinta ennalta määritellystä vaihtoehtojen joukosta, joka esitetään radiopainikkeina. Tässä on joitakin esimerkkejä siitä, milloin `RadioButtonGroup`-komponenttia tulisi käyttää:

1. **Kyselyt tai kyselylomakkeet**: `RadioButtonGroup`-komponentteja käytetään yleisesti kyselyissä tai kyselylomakkeissa, joissa käyttäjien tulee valita yksi vastaus vaihtoehtoluettelosta.

2. **Asetusten määrittäminen**: Sovelluksissa, jotka sisältävät asetuksia tai asetuspaneeleja, käytetään usein `RadioButtonGroup`-komponenttia, jotta käyttäjät voivat valita yhden vaihtoehdon keskinäisesti eksklusiivisista valinnoista.

3. **Suodatus tai lajittelu**: `RadioButton`-komponenttia voidaan käyttää sovelluksissa, joissa käyttäjien on valittava yksi suodatus- tai lajitteluvaihtoehto, kuten lajitteluvaihtoehto eri kriteerien perusteella.

<!-- vale off -->
## Radiopainikkeiden lisääminen ja poistaminen {#adding-and-removing-radiobuttons}
<!-- vale on -->

On mahdollista lisätä ja poistaa yksittäisiä tai useita `RadioButton`-objekteja ryhmästä varmistaen, että ne osoittavat keskinäisesti eksklusiivista valintakäyttäytymistä, ja ne voivat liittyä mihin tahansa ryhmään kuuluvaan nimeen.

## Nimeäminen {#naming}

Nimiattribuutti `RadioButtonGroup`-komponentissa ryhmittelee liittyvät radiopainikkeet yhteen, jolloin käyttäjät voivat tehdä yhden valinnan annetuista vaihtoehdoista ja vähentää radiopainikkeiden keskinäistä eksklusiivisuutta. Ryhmän nimeä ei heijasteta DOM:iin, mutta se on mukavuustyökalu Java-kehittäjälle.

## Parhaat käytännöt {#best-practices}

Optimaalisen käyttäjäkokemuksen varmistamiseksi `RadioButton`-komponentin käytössä kannattaa harkita seuraavia parhaita käytäntöjä:

1. **Selkeät vaihtoehtojen merkinnät**: Tarjoa selkeät ja ytimekkäät merkinnät jokaiselle `RadioButton`-vaihtoehdolle kuvaamaan valintaa tarkasti. Merkintöjen tulee olla helposti ymmärrettäviä ja erotettavissa toisistaan.

2. **Tarjoa oletusvalinta**: Mikäli mahdollista, harkitse oletusvalinnan tarjoamista radiopainikkeille ohjataksesi käyttäjiä, kun he kohtaavat vaihtoehdot ensimmäistä kertaa. Oletusvalinnan tulisi vastata yleisintä tai toivottua valintaa.
