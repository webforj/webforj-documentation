---
title: Drawer
sidebar_position: 35
_i18n_hash: e3b531e5fb7f1554e035f4d05aad8512
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Laatikko on säiliö, joka liukuu näkymään paljastaakseen lisävaihtoehtoja ja tietoja. Sovelluksessa voidaan luoda useita laatikoita, ja ne pinotaan toistensa päälle.

Laatikkokomponenttia voidaan käyttää monenlaisissa tilanteissa, kuten tarjoamalla navigointivalikko, jota voidaan vaihtaa, paneeli, joka näyttää lisätietoja tai kontekstuaalista tietoa, tai optimoida käyttöä mobiililaitteessa. Seuraava esimerkki näyttää mobiilisovelluksen, joka käyttää webforJ AppLayout-komponenttia ja näyttää "Tervetuloa Popup" -laatikon alareunassa ensimmäisen latauksen yhteydessä. Lisäksi sovelluksessa voidaan vaihtaa navigointilaatikkokomponenttia napsauttamalla hampurilaisvalikkoa.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Käyttötarkoitukset {#usages}

1. **Navigointivalikko**: Yksi yleinen laatikkokomponentin käyttö on navigointivalikkona. Se tarjoaa tilaa säästävän tavan näyttää linkkejä sovelluksesi eri osiin tai sivuille, erityisesti mobiili- tai responsiivisissa asetteluissa. Käyttäjät voivat avata ja sulkea laatikon päästäkseen navigointivaihtoehtoihin ilman, että pääsisältöalue on liian täynnä.

2. **Suodatin ja sivupaneeli**: Laatikkona voidaan käyttää suodatinta tai sivupaneelia sovelluksissa, jotka näyttävät luettelon kohteista. Käyttäjät voivat laajentaa laatikon paljastaakseen suodatusvaihtoehtoja, lajittelukontrolleja tai muita tietoja, jotka liittyvät luettelon kohteisiin. Tämä pitää pääsisällön keskittyneenä luetteloon samalla, kun tarjoat edistyneitä toimintoja helposti saavutettavassa muodossa.

3. **Käyttäjäprofiili tai asetukset**: Voit käyttää laatikkoa näyttämään käyttäjäprofiilitiedot tai sovelluksen asetukset. Tämä pitää nämä tiedot helposti saavutettavina, mutta piilossa, kun niitä ei tarvita, säilyttäen siistin ja järjestelmällisen käyttöliittymän. Käyttäjät voivat avata laatikon päivittääkseen profiilejaan tai säädöksiään.

4. **Ilmoitukset**: Ilmoituksia tai hälytyksiä sisältävissä sovelluksissa laatikko voi liukua sisään näyttämään uusia viestejä tai päivityksiä. Käyttäjät voivat nopeasti tarkistaa ja hylätä ilmoituksia jättämättä nykyistä näkymäänsä.

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## Mukauttaminen {#customization}

Erilaisia ominaisuuksia on käytettävissä, jotka mahdollistavat laatikkokomponentin eri attribuuttien mukauttamisen. Tässä osiossa käytetään esimerkkejä näiden ominaisuuksien muokkaamisesta.

## Autofocus {#autofocus}

Automaattisesti tarkennusominaisuus on suunniteltu parantamaan saavutettavuutta ja käytettävyyttä automaattisesti keskittymällä ensimmäiseen kohteeseen laatikossa, kun se avataan. Tämä ominaisuus poistaa tarpeen käyttäjien navigoida manuaalisesti haluttuun kohteeseen, säästäen aikaa ja vaivannäköä.

Kun laatikko avataan, joko tapahtuman, oletusarvon tai muun vuorovaikutuksen kautta, käyttäjän tarkennus ohjataan laatikon ensimmäiseen kohteeseen. Tämä ensimmäinen kohde voi olla painike, linkki, valikkovaihtoehto tai muu tarkennettavissa oleva elementti.

:::tip
Automaattisesti ensimmäiseen kohteeseen keskittymällä kehittäjä varmistaa, että käyttäjät voivat heti engage käyttää tärkeintä tai eniten käytettyä vaihtoehtoa ilman, että heidän tarvitsee siirtyä tai vierittää koko laatikon läpi. Tämä käyttäytyminen sujuvoittaa käyttäjäkokemusta ja edistää tehokasta navigointia käyttöliittymässä.
:::

Tämä ominaisuus voi olla erityisen hyödyllinen henkilöille, jotka luottavat näppäimistön navigointiin tai apuvälineisiin, kuten näytönlukuohjelmiin. Se tarjoaa selkeän aloituspisteen laatikossa ja sallii käyttäjien päästä haluttuun toiminnallisuuteen ilman tarpeetonta manuaalista syöttöä.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Esimerkki -->

## Tunnus {#label}

Laatikon Tunnus-ominaisuus on toiminto, joka on suunniteltu parantamaan saavutettavuutta ja tarjoamaan kuvailevaa kontekstia laatikolle käyttöliittymässä. Tämä ominaisuus mahdollistaa kehittäjien liittää tunnuksen laatikolle, ensisijaisesti saavutettavuustarkoituksiin, varmistaen, että näytönlukuohjelmat ja muut apuvälineet voivat tarkasti välittää laatikon tarkoituksen ja sisällön käyttäjille.

Kun Laatikkotunnus-ominaisuus otetaan käyttöön, liitetystä tunnuksesta tulee olennainen osa laatikon saavutettavuusinfrastruktuuria. Se mahdollistaa niille käyttäjille, jotka luottavat apuvälineisiin, ymmärtää laatikon toiminto ja navigoida käyttöliittymässä tehokkaammin.

Tarjoamalla tunnuksen laatikolle kehittäjät varmistavat, että näytönlukuohjelmat ilmoittavat laatikon tarkoituksen näkövammaisille käyttäjille. Tämä tieto auttaa yksilöitä tekemään tietoisia päätöksiä vuorovaikutuksessa laatikon kanssa, sillä he voivat ymmärtää sen sisällön ja merkityksen laajemmassa käyttöliittymässä.

Tunnusominaisuutta voidaan mukauttaa sovelluksen erityiseen kontekstiin ja suunnittelutarpeisiin. Kehittäjillä on joustavuutta antaa tiiviitä ja kuvailevia tunnuksia, jotka tarkasti kuvaavat laatikon sisältöä tai toiminnallisuutta.

<!-- Esimerkki -->

## Asettelu {#placement}

Laatikon asetteluominaisuus antaa kehittäjille mahdollisuuden määrittää laatikon sijainti ja suunta näkymässä. Tämä ominaisuus tarjoaa joukon enum-arvoja, jotka antavat joustavuutta laatikon sijoittamiseen suhteessa pääsisältöön.

Saatavilla olevat enum-arvot asetteluominaisuudelle ovat seuraavat:

- **YLÄ**: Tämä arvo sijoittaa laatikon näkymän yläosaan, jolloin se täyttää ylimmän alueen.

- **YLÄ_KESKITE**: Tällä arvolla laatikko sijoitetaan yläosan keskipisteeseen. Se on vaakasuunnassa keskitetty, luoden tasapainoisen asettelun.

- **ALHAALLA**: Tämän arvon käyttäminen sijoittaa laatikon näkymän alaosaan, jolloin se näkyy pääsisällön alapuolella.

- **ALHAALLA_KESKITE**: Tämä arvo keskittyy laatikon vaakasuunnassa näkymän alaosassa. Se tarjoaa visuaalisesti tasapainoisen koostumuksen.

- **VASEN**: Tämän arvon valitseminen aiheuttaa laatikon sijoittuvan näkymän vasemmalle puolelle pääsisällön viereen.

- **OIKELLE**: Käyttämällä tätä arvoa laatikko sijoitetaan näkymän oikealle puolelle, pitäen läheistä yhteyttä pääsisältöön.

Asetteluominaisuus antaa kehittäjille mahdollisuuden valita sopivin sijainti laatikolle perustuen tiettyihin suunnittelu- ja käyttäjäkokemusvaatimuksiin. Enum-arvot tarjoavat monia asettelu vaihtoehtoja erilaisten käyttöliittymän asettelu- ja visuaalisten hierarkioiden mukauttamiseksi.

Hyödyntämällä asetteluominaisuutta kehittäjät voivat luoda intuitiivisia ja tehokkaita käyttöliittymiä. Esimerkiksi laatikon asettaminen vasemmalle tai oikealle puolelle mahdollistaa nopean pääsyn lisätoimintoihin tai navigointivaihtoehtoihin, kun taas ylös- tai alas-asettelut soveltuvat hyvin kontekstuaaliseen tietoon tai lisäsisältöön.

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Tyylit {#styling}

<TableBuilder name="Laatikko" />

## Parhaat käytännöt {#best-practices}

Varmista optimaalinen käyttäjäkokemus käytettäessä `Laatikko`-komponenttia, ota huomioon seuraavat parhaat käytännöt:

1. **Asettelu**: Päätä, pitäisikö laatikon liukua sisään vasemmalta, oikealta, ylhäältä tai alhaalta, sovelluksesi asettelun ja käyttäjäkokemusperusteiden perusteella. Ota huomioon käyttäjien mieltymykset ja suunnittelukäytännöt.

2. **Saavutettavuus**: Kiinnitä erityistä huomiota saavutettavuuteen. Varmista, että käyttäjät voivat avata ja sulkea laatikon näppäimistökontrollilla ja että näytönlukuohjelmat voivat ilmoittaa sen olemassaolosta ja tilasta. Tarjoa ARIA-rooleja ja -tunnuksia tarpeen mukaan.

3. **Pyyhkäisy eleet**: Kosketusnäyttölaiteilla tue pyyhkäisy eleitä laatikon avaamiseen ja sulkemiseen. Tämä on käyttäjille intuitiivinen tapa vuorovaikuttaa sen kanssa.
