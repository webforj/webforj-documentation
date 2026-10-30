---
title: AI Assistant
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: 716e267ebccf57080f5238335670d1b7
---
craftforJ sisältää täydellisen koodausagentin, joka toimii sisällä **käynnistyneessä sovelluksessa**. Se kirjoittaa Javaa vapaasti, kääntää aiemmin kirjoittamansa koodin ennen kuin näet sen, soveltaa muutokset ja jatkaa työtään sovelluksesi uudelleenkäynnistyksen jälkeen. Kaiken, mitä se tekee, se tekee suoraan sovelluksen kanssa, joka on oikeasti käytössäsi, sen sijaan että se perustuisi arvaukseen, joka on tehty arkistostasi.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/ai-conversation.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning AI voi silti tehdä virheitä
Toimiminen käynnissä olevan sovelluksen kanssa ja oman lähtökohtansa kääntäminen tekee agentista huomattavasti tarkemman kuin sokkona kirjoittavasta agentista. Se voi silti olla väärässä. Tarkista, mitä se teki ennen kuin hyväksyt sen.
:::

## Se kirjoittaa Javaa {#it-writes-java}

Agentti ei rajoitu vain niihin ominaisuuksien muutoksiin, jotka voit tehdä käsin. Kuvailu ongelmasta riittää, ja se kirjoittaa siihen liittyvän koodin, lisäten metodeja, muuttaen logiikkaa ja jäsentenäkymää tehtävän vaatimusten mukaan.

Jokainen kirjoitettu muokkaus on vaiheistettu eikä kirjoiteta suoraan levylle. Vaiheistetut muokkaukset menevät suoraan todelliseen Java-kääntäjään, ja agentti lukee palautuvat virheraportit ja korjaa omat virheensä ennen kuin muutos tarjotaan sinulle. Se, mitä saat tarkistettavaksesi, on koodi, joka jo kääntyy käynnistyvän sovelluksesi kanssa.

Kattava validointi vaatii JDK:n. JRE:llä craftforJ siirtyy koodin jäsentämiseen, merkitsee muokkauksen vahvistamattomaksi ja käskee agenttia ilmoittamaan siitä sen sijaan, että esittäisi sen tarkastettuna.

Muutosten soveltaminen käynnistää sovelluksesi uudelleen. Agentti odottaa uudelleenkäynnistystä, muodostaa yhteyden uudelleen ja jatkaa suunnitelmaansa siitä, mihin se jäi, joten useita muokkauksia ja uudelleenkäynnistyksiä kattava tehtävä etenee loppuun asti.

## Se toimii vaiheittain {#it-works-in-steps}

Annetaan agentille tavoite, ei käsky. Se suunnittelee, tarkistaa mitä tarvitsee, toimii, tarkistaa tuloksen ja korjaa itseään, suorittaen monia vaiheita yhdellä kerralla ilman, että sinun tarvitsee ohjata jokaista toimintoa. Jokainen vaihe näkyy transkriptiossa sen tapahtuessa, ja voit laajentaa mitä tahansa vaihetta nähdäksesi tarkalleen, mitä agentti kutsui ja mitä palautui.

## Mitä se voi saavuttaa {#what-it-can-reach}

Agentilla on laaja työkalupakki, joka kattaa kaiken craftforJ:n tiedon sovelluksestasi, mukaan lukien:

- **Komponenttisi** - elävä puu, oikeat ominaisuusarvot ja Java, joka rakensi jokaisen niistä. Se voi muuttaa ominaisuuksia, poistaa komponentteja ja korostaa yhtä sivulla.
- **Lähdekoodisi** - lukee mitä tahansa tiedostoa projektijuuristasi, vaiheistaa muokkauksia, näyttää eroja ja soveltaa niitä.
- **Reitit** - reititystaulukko, aktiivinen reitti, navigointi minne tahansa ja reitille määritettyjen pääsyrajoitusten muuttaminen.
- **Teemasi ja tyylit** - lukee ja asettaa suunnittelutunnuksia, tallentaa teeman ja etsii saatavilla olevia fontteja ja ikoneita.
- **Sivu itsessään** - upottaa CSS:n ja JavaScriptin elävään sivuun ja ottaa näyttökuvan komponentista, jotta voit tarkistaa sen.
- **webforJ:n tietopankki** - sama dokumentaatio, komponenttityylitason ja `--dwc-*` tunnustyökalut, jotka [webforJ MCP -palvelin](/docs/ai-tooling/mcp) tarjoaa editorillesi. Se on sisäänrakennettu ja aina käytettävissä.

Koska se pääsee kaikkeen tähän craftforJ:n kautta, se toimii saman tiedon kanssa kuin sinä. Se lukee todellisia arvoja, ei niitä, joita lähdekoodisi viittaa.

## Hyväksynnät {#approvals}

Päätät etukäteen, kuinka paljon agentti voi toimia itsenäisesti:

- **Kysy ennen toimintaa** - jokainen vaikutusta aiheuttava toiminta pysähtyy hyväksyntääsi varten.
- **Sovella muokkauksia automaattisesti** - agentti toimii vapaasti, mutta kysyy silti ennen kuin se poistaa jotain tai suorittaa skriptin.
- **Toimi itsenäisesti** - agentti toimii ilman keskeytyksiä.

Kun agentti kysyy, pyyntö näkyy rivissä transkriptiossa sen toiminnan kanssa, jonka se haluaa suorittaa, ja voit sallia sen kerran tai loppukeskustelun ajaksi.

![Avustaja kysyy ennen kuin se toimii, rivissä transkriptiossa](/img/craftforj/ai/approval-prompt.png#rounded-border)

Jos olet uusi agentin käytössä, aloita antamalla sen kysyä kaikesta. Kun olet katsellut sen toimintaa, sen omien muokkausten hyväksyminen vähentää suurinta osaa keskeytyksistä ja pitää tärkeät päätökset mukanasi.

## Työskentely sovelluksen kanssa keskustelussa {#working-with-the-app-in-a-conversation}

Agentti lukee kaiken tarvitsemansa sen mukaan, mitä se tarvitsee, sen sijaan että sille annettaisiin koko sovelluksesi etukäteen, ja craftforJ näyttää, mitä keskusteluun on liitetty. Voit antaa sille komponentin suoraan puusta tai valita yhden sivulta keskustelun aikana. Kysymyksiin siitä, miltä jokin näyttää, agentti voi ottaa näyttökuvan komponentista. Tämä vaatii mallin, joka hyväksyy kuvia.

:::warning Näyttökuvat sisältävät kaiken näytöllä olevan
Näyttökuva sisältää kaiken datan, jonka sovelluksesi näyttää sinä hetkenä. Ota tämä huomioon ennen kuin osoitat isännöityä mallia oikeita tietoja näyttävään sovellukseen.
:::

## Mallin määrittäminen {#configuring-a-model}

craftforJ ei toimita omaa mallia, joten valitset sen, joka sen ajaa. Lisää API-avain yhden tuetun toimittajan toimesta tai osoita craftforJ paikallisesti ajettavaan malliin. Avainsi on tallennettu koneelle, jolla sovelluksesi toimii, ja avustaja pitää sen muistissa vain niin kauan kuin sivu on auki, ei koskaan selainvarastossa. Se kommunikoi valitsemasi toimittajan kanssa selaimesta sen sijaan, että se menisi palvelimesi kautta, eikä sen kanssa keskustella ketään muuta.

Mallivalitsin näyttää, mikä erottaa mallin toisista, mukaan lukien kuinka suuren osan sovelluksesta ja keskustelusta se kykenee käsittelemään kerralla, mitä keskustelu maksaa ja hyväksyykö malli kuvia tai järkeilee ennen vastaamista. Malli, joka ei voi kutsua työkaluja, voi pitää keskustelun mutta ei voi tarkastella tai muuttaa mitään.

![Mallivalitsin näyttää, mikä erottaa käytettävissä olevat mallit](/img/craftforj/ai/model-picker.png#rounded-border)

Mallin paikallinen ajaminen pitää kaiken koneellasi. Paikalliset mallit täyttävät usein pienen konteksti-ikkunan, jonka todellinen sovelluskeskustelu täyttää nopeasti, joten anna mallille niin paljon kontekstia kuin koneesi pystyy käsittelemään.

## Keskustelut {#conversations}

Keskustelut pidetään per sovellus, ja agentti voi palata aikaisempiin keskusteluihin, kun kysymys viittaa aikaisempiin töihisi. Kun keskustelu ylittää mallin kontekstin, craftforJ tiivistää vanhemmat viestit, jotta työ jatkuu eikä epäonnistu, ja mainitsee sen keskustelussa.

Kun työ ylittää craftforJ:n rajoitukset, voit tiivistää keskustelun ja antaa sen editorisi avustajalle. Se avustaja ottaa työn tarkemmin vastaan asennettuna [webforJ AI -laajennuksella](/docs/ai-tooling).

## Sammuttaminen {#turning-it-off}

[`ai.enabled`](/docs/craftforj/configuration#feature-flags) -ominaisuus poistaa avustajan craftforJ:stä kokonaan. [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) -ominaisuus pitää avustajan käytössä, mutta estää sen kirjoittamasta omaa Java-koodiaan.
