---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 91d753e882e3d6d59deef5044ee7bc4c
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup`-luokkaa käytetään ryhmittämään toisiinsa liittyviä radiopainikkeita, mikä auttaa luomaan vaihtoehtojen keskinäistä eksklusiivisuutta kyseisessä ryhmässä. Käyttäjät voivat valita vain yhden radiopainikkeen tietyssä radioryhmässä. Kun käyttäjä valitsee radiopainikkeen ryhmästä, aiemmin valittu radiopainike samassa ryhmässä poistuu automaattisesti valinnasta. Tämä varmistaa, että vain yksi vaihtoehto voidaan valita kerrallaan.

:::tip
`RadioButton`-komponentti tallentaa ryhmän, johon se kuuluu, ja tämä voidaan saavuttaa `getButtonGroup()`-metodin avulla.
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
`RadioButtonGroup`-komponentti ei renderöi HTML-elementtiä sivulle. Sen sijaan se on vain logiikka, joka varmistaa, että ryhmä Radiopainikkeita käyttäytyy ryhmänä sen sijaan, että ne käyttäytyisivät yksittäin.
:::

## Käyttötapaukset {#usages}

`RadioButtonGroup`-komponenttia käytetään parhaiten tilanteissa, joissa käyttäjä tarvitsee tehdä yksittäisen valinnan ennalta määritetystä vaihtoehtojen joukosta, joka esitetään radiopainikkeina. Tässä on joitakin esimerkkejä, milloin `RadioButtonGroup`-komponenttia kannattaa käyttää:

1. **Kyselyt tai Lomakkeet**: `RadioButtonGroup`-komponentteja käytetään yleisesti kyselyissä tai lomakkeissa, joissa käyttäjien on valittava yksi vastaus vaihtoehtojen luettelosta.

2. **Asetteluasetukset**: Sovelluksissa, jotka sisältävät asetuksiin liittyviä paneeleja, käytetään usein RadioButtonGroup-komponenttia, jotta käyttäjät voivat valita yhden vaihtoehdon keskenään poissulkevista vaihtoehdoista.

3. **Suodatus tai Lajittelu**: `RadioButton`-komponenttia voidaan käyttää sovelluksissa, joissa vaaditaan käyttäjien valitsevan yksittäinen suodatus- tai lajitteluvaihtoehto, kuten erilaisten kriteerien mukaan lajittelemalla tavaraluetteloa.

<!-- vale off -->
## Radiopainikkeiden lisääminen ja poistaminen {#adding-and-removing-radiobuttons}
<!-- vale on -->

On mahdollista lisätä ja poistaa yksittäisiä tai useita `RadioButton`-objekteja ryhmään varmistaen, että ne käyttäytyvät keskenään poissulkevalla tavalla ja liittyvät mihin tahansa nimeen, joka voi kuulua ryhmään.

## Nimeäminen {#naming}

`RadioButtonGroup`-komponentin nimi-attribuutti ryhmittelee liittyvät Radiopainikkeet yhteen, jolloin käyttäjät voivat tehdä yksittäisen valinnan tarjotuista vaihtoehdoista ja varmistaa eksklusiivisuuden Radiopainikkeiden välillä. Ryhmän nimeä ei heijasteta DOM:iin, ja se on kuitenkin kätevä työkalu Java-kehittäjälle.

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen RadioButton-komponenttia käytettäessä, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät Merkit**: Anna selkeät ja ytimekkäät nimet jokaiselle `RadioButton`-vaihtoehdolle, jotta valinta voidaan kuvata tarkasti. Nimien tulisi olla helposti ymmärrettäviä ja erottua toisistaan.

2. **Tarjoa Oletusvalinta**: Jos mahdollista, harkitse oletusvalinnan tarjoamista Radiopainikkeille ohjataksesi käyttäjiä, kun he kohtaavat vaihtoehdot ensimmäisen kerran. Oletusvalinnan tulisi vastata yleisintä tai suosituimpaa valintaa.
