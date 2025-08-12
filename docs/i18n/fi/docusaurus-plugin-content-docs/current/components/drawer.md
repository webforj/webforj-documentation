---
title: Drawer
sidebar_position: 35
_i18n_hash: 73da264dca1e3f8cfd58b697e3e9d0dc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Laatikko on säiliö, joka liukuu näkymään paljastaen lisävalintoja ja tietoa. Sovelluksessa voidaan luoda useita laatikoita, ja ne asetetaan toistensa päälle.

Laatikkokomponenttia voidaan käyttää monissa eri tilanteissa, kuten tarjoamalla navigointivalikko, jonka voi vaihtaa päälle tai pois, paneeli, joka näyttää lisätietoja tai kontekstuaalista tietoa, tai optimoimaan käyttöä mobiililaitteella. Seuraava esimerkki näyttää mobiilisovelluksen, joka käyttää webforJ AppLayout-komponenttia ja näyttää "Tervetuloa Popup" -laatikon alareunassa, kun se ladataan ensimmäistä kertaa. Lisäksi navigointilaatikkokomponenttia voidaan vaihtaa sovelluksessa napsauttamalla hampurilaisvalikkoa.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Käyttötavat {#usages}

1. **Navigointivalikko**: Yksi yleinen laatikkokomponentin käyttötapa on navigointivalikkona. Se tarjoaa tilatehokkaan tavan näyttää linkkejä sovelluksen eri osiin tai sivuille, erityisesti mobiili- tai responsiivisissa asetteluissa. Käyttäjät voivat avata ja sulkea laatikon päästäkseen navigointivaihtoehtoihin ilman, että pääsisältöalue on tukossa.

2. **Suodatin ja sivupalkki**: Laatikkoa voidaan käyttää suodattimena tai sivupalkkina sovelluksissa, jotka näyttävät luetteloita kohteista. Käyttäjät voivat laajentaa laatikkoa paljastaakseen suodatusvaihtoehdot, lajittelukontrollit tai lisätietoja liittyen luettelon kohteisiin. Tämä pitää pääsisällön keskittyneenä luetteloon, samalla kun tarjotaan kehittyneitä ominaisuuksia helposti saavutettavassa muodossa.

3. **Käyttäjäprofiili tai asetukset**: Voit käyttää laatikkoa näyttämään käyttäjäprofiilitietoja tai sovellusasetuksia. Tämä pitää tällaiset tiedot helposti saatavilla mutta piilossa, kun niitä ei tarvita, säilyttäen siistin ja järjestäytyneen käyttöliittymän. Käyttäjät voivat avata laatikon päivittääkseen profiilejaan tai säätelemään asetuksia.

4. **Ilmoitukset**: Sovelluksille, joilla on ilmoituksia tai hälytyksiä, laatikko voi liukua esiin näyttämään uusia viestejä tai päivityksiä. Käyttäjät voivat nopeasti tarkistaa ja hylätä ilmoituksia ilman, että heidän tarvitsee poistua nykyisestä näkymästään.

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## Mukauttaminen {#customization}

Erilaisia ominaisuuksia on olemassa, jotka mahdollistavat Laatikon komponentin eri attribuuttien mukauttamisen. Tämä osio käsittelee näitä ominaisuuksia esimerkkien avulla.

## Autofocus {#autofocus}

Autofocus-ominaisuus on suunniteltu parantamaan saavutettavuutta ja käytettävyyttä automaattisesti keskittymällä laatikon ensimmäiseen kohteeseen, kun se avataan. Tämä toiminto poistaa tarpeen käyttäjien siirtyä manuaalisesti haluttuun kohteeseen, säästäen aikaa ja vaivaa.

Kun laatikko avautuu joko tapahtuman tai muun vuorovaikutuksen kautta, käyttäjän keskittyminen ohjataan laatikon ensimmäiseen kohteeseen. Tämä ensimmäinen kohde voi olla painike, linkki, valintavaihtoehto tai mikä tahansa muokattava elementti.

:::tip
Automaattisesti keskittymällä ensimmäiseen kohteeseen kehittäjä varmistaa, että käyttäjät voivat heti sitoutua merkittävimpään tai eniten käytettyyn vaihtoehtoon ilman, että heidän tarvitsee siirtyä tai vierittää koko laatikon läpi. Tämä käyttäytyminen sujuvoittaa käyttäjäkokemusta ja edistää tehokasta navigointia käyttöliittymässä.
:::

Tämä ominaisuus voi myös olla erityisen hyödyllinen henkilöille, jotka luottavat näppäimistön navigointiin tai apuvälineisiin, kuten ruudunlukijoihin. Se tarjoaa selkeän aloituspaikan laatikossa ja antaa käyttäjien käyttää haluttua toiminnallisuutta ilman tarpeetonta manuaalista syöttöä.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Esimerkki -->

## Otsikko {#label}

Laatikon Otsikko-ominaisuus on suunniteltu parantamaan saavutettavuutta ja tarjoamaan kuvaileva konteksti laatikolle käyttöliittymässä. Tämä ominaisuus mahdollistaa kehittäjien määrittää laatikolle otsikon, ensisijaisesti saavutettavuussyistä, varmistaen, että ruudunlukijat ja muut apuvälineet voivat tarkasti välittää laatikon tarkoituksen ja sisällön käyttäjille.

Kun Laatikon Otsikko -ominaisuutta käytetään, määritetty otsikko tulee olemaan olennainen osa laatikon saavutettavuusinfrastruktuuria. Se mahdollistaa apuvälineisiin turvautuvien käyttäjien ymmärtää laatikon toiminto ja navigoida käyttöliittymässä tehokkaammin.

Antamalla otsikon laatikolle kehittäjät varmistavat, että ruudunlukijat ilmoittavat laatikon tarkoituksen näkörajoitteisille käyttäjille. Tämä tieto antaa yksilöille mahdollisuuden tehdä tietoisia päätöksiä vuorovaikutuksessa laatikon kanssa, sillä he ymmärtävät sen sisällön ja merkityksen laajemmassa käyttöliittymässä.

Otsikko-ominaisuutta voidaan mukauttaa sopimaan sovelluksen erityisiin konteksteihin ja suunnittelutarpeisiin. Kehittäjillä on joustavuutta tarjota tiiviitä ja kuvailevia otsikoita, jotka tarkasti edustavat laatikon sisältöä tai toiminnallisuutta.

<!-- Esimerkki -->

## Sijoittaminen {#placement}

Laatikkokomponentin sijoittamisominaisuus mahdollistaa kehittäjien määrittää laatikon aseman ja kohdistuksen näkymässä. Tämä ominaisuus tarjoaa joukon enum-arvoja, jotka tarjoavat joustavuutta sen määrittämisessä, missä laatikko ilmestyy pääsisällön suhteessa.

Saatavilla olevat enum-arvot sijoittamisominaisuudelle ovat seuraavat:

- **YLÄ**: Tämä arvo asettaa laatikon näkyvän alueen yläosaan, jolloin se vie ylimmän alueen.

- **YLÄ_KESKI**: Tällä arvolla laatikko sijoitetaan näkymän yläosan keskelle. Se on kohdistettu vaaka-asennossa keskelle, luoden tasapainoisen asettelun.

- **ALHA**: Tämän arvon avulla laatikko sijaitsee näkymän alareunassa, pääsisällön alapuolella.

- **ALHA_KESKI**: Tämä arvo keskittää laatikon vaakasuunnassa näkymän alareunassa. Se tarjoaa visuaalisesti tasapainoisen koostumuksen.

- **VASEN**: Tämän arvon valitsemisella laatikko sijaitsee näkymän vasemmalla puolella, pääsisällön viereen.

- **OIKEA**: Käyttämällä tätä arvoa laatikko sijoitetaan näkymän oikealle puolelle, pitäen läheistä läheisyyttä pääsisältöön.

Sijoittamisominaisuus mahdollistaa kehittäjien valita laatikon parhaimman aseman ottaen huomioon erityiset suunnittelu- ja käyttäjäkokemusvaatimukset. Enum-arvot tarjoavat erilaisia sijoitusvaihtoehtoja eri käyttöliittymäasettelujen ja visuaalisten hierarkioiden mahtamiseen.

Hyödyntämällä sijoittamisominaisuutta kehittäjät voivat luoda intuitiivisia ja tehokkaita käyttöliittymiä. Esimerkiksi sijoittamalla laatikon vasemmalle tai oikealle puolelle voidaan nopeasti päästä lisätoimintoihin tai navigointivaihtoehtoihin, kun taas ylä- tai alhaisijat ovat hyvin soveltuvia kontekstuaaliselle informaatiolle tai lisäsisällölle.

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Tyylit {#styling}

<TableBuilder name="Laatikko" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen käyttäessäsi `Laatikko`-komponenttia, harkitse seuraavia parhaita käytäntöjä:

1. **Sijoittaminen**: Päätä, pitäisikö laatikon liukua vasemmalta, oikealta, ylhäältä tai alhaalta sovelluksesi asettelun ja käyttäjäkokemusnäkökohtien perusteella. Ota huomioon käyttäjien mieltymykset ja suunnittelukäytännöt.

2. **Saavutettavuus**: Kiinnitä erityistä huomiota saavutettavuuteen. Varmista, että käyttäjät voivat avata ja sulkea laatikon näppäimistöohjaimilla, ja että ruudunlukijat voivat ilmoittaa sen läsnäolosta ja tilasta. Tarjoa ARIA-rooleja ja -etikettejä tarpeen mukaan.

3. **Pyyhkäisy eleet**: Kosketustoiminnolla varustetuilla laitteilla, tue pyyhkäisyliikkeitä laatikon avaamiseksi ja sulkemiseksi. Tämä on intuitiivinen tapa, jonka avulla käyttäjät voivat vuorovaikuttaa sen kanssa.
