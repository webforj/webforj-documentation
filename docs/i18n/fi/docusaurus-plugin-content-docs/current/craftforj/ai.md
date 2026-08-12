---
title: AI Assistant
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: 863d36cce987eedd9b580968afadcc18
---
craftforJ sisältää täydellisen koodausagentin, joka toimii **käynnissä olevan sovelluksesi** sisällä. Se kirjoittaa Javaa vapaasti, kääntää kirjoittamansa koodin ennen kuin näet sen, soveltaa muutoksen ja jatkaa työskentelyä sen jälkeen, kun sovelluksesi on käynnistetty uudelleen. Kaiken, mitä se tekee, se tekee oikeassa sovelluksessa, joka on todella käynnissä edessäsi, eikä arvauksesta, joka on tehty varastostasi.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/ai-conversation.mp4" type="video/mp4" />
  </video>
</div>

:::warning AI voi silti tehdä virheitä
Työskentely käynnissä olevan sovelluksen parissa ja oman tuotoksensa kääntäminen tekee agentista huomattavasti tarkemman kuin sokeasti kirjoittava. Se voi silti olla väärässä. Tarkista, mitä se teki, ennen kuin hyväksyt sen.
:::

## Se kirjoittaa Javaa {#it-writes-java}

Agentti ei ole rajoittunut vain ominaisuuden muutoksiin, joita voit tehdä käsin. Kuvaa ongelma, ja se kirjoittaa koodin sille, lisäten metodeja, muuttaen logiikkaa ja rakentaen näkymää uudelleen tehtävän vaatimusten mukaan.

Jokainen tekemä se muutos on vaiheen alla, ei suoraan levylle kirjoitettu. Vaiheessa olevat muokkaukset siirtyvät suoraan oikeaan Java-kääntäjään, ja agentti lukee takaisin tulevat diagnostiikat ja korjaa omat virheensä ennen kuin muutos koskaan tarjotaan sinulle. Arvioitava koodi on sellaista, joka jo kääntyy käynnissä olevan sovelluksesi kanssa.

Kokonainen validointi vaatii JDK:n. JRE:ssä craftforJ turvautuu koodin analysoimiseen, merkitsee muokkauksen varmennettuna, ja ohjaa agenttia sanomaan niin sen sijaan, että esittäisi sen tarkistettuna.

Muutoksen soveltaminen käynnistää sovelluksesi uudelleen. Agentti odottaa uudelleenkäynnistystä, koppaa yhteyden ja jatkaa suunnitelmansa siitä, mihin se jäi, joten tehtävä, joka kattaa useita muokkauksia ja uudelleenkäynnistyksiä, etenee loppuun saakka.

## Se toimii vaiheittain {#it-works-in-steps}

Annat agentille tavoitteen, et käskyä. Se suunnittelee, tarkistaa mitä tarvitsee, toimii, tarkistaa tuloksen ja korjaa itseään, suorittaen monia vaiheita yhdessä vuorossa ilman, että sinun tarvitsee ohjata jokaista niistä. Jokainen vaihe näkyy transkriptiossa sen tapahtuessa, ja voit laajentaa mitä tahansa niistä nähdäksesi tarkalleen, mitä agentti kutsui ja mitä siitä tuli takaisin.

## Mitä se voi saavuttaa {#what-it-can-reach}

Agentilla on laaja työkalupakki, joka kattaa kaiken craftforJ:n tietämyksestä sovelluksestasi, mukaan lukien:

- **Omat komponenttisi** - elävä puu, todelliset ominaisuuden arvot ja Java, joka rakensi jokaisen niistä. Se voi muuttaa ominaisuuksia, poistaa komponentteja ja korostaa yhtä sivulla.
- **Oma lähdekoodisi** - lukea mitä tahansa tiedostoa projektin juuresta, asettaa muokkauksia, näyttää eroja ja soveltaa niitä.
- **Reitit** - reititystaulukko, aktiivinen reitti, navigointi mihin tahansa ja pääsääntöjen muuttaminen, jotka on ilmoitettu reitillä.
- **Teema ja tyylit** - suunnittelutunnusten lukeminen ja asettaminen, teeman tallentaminen ja saatavilla olevien fonttien ja ikonien etsiminen.
- **Sivu itse** - CSS:n ja JavaScriptin injektoiminen elävään sivuun sekä komponentin näyttökuvan ottaminen tarkasteltavaksi.
- **webforJ:n tietopohja** - sama dokumentaatio, komponenttien tyylipinta ja `--dwc-*` tunnustyökalut, joita [webforJ MCP -palvelin](/docs/ai-tooling/mcp) tarjoaa muokkaimellasi. Se on sisäänrakennettu ja aina käytettävissä.

Koska se saavuttaa kaiken tämän craftforJ:n kautta, se toimii samaa tietoa käyttäen kuin sinä. Se lukee todellisia arvoja, ei niitä, joita lähdekoodisi antaa ymmärtää.

## Hyväksynnät {#approvals}

Päätät etukäteen, kuinka paljon agentti saa tehdä itsenäisesti:

- **Kysy ennen toimintaa** - jokainen vaikutusta aiheuttava toiminto pysähtyy hyväksyntääsi varten.
- **Sovella muokkauksia automaattisesti** - agentti työskentelee vapaasti, mutta kysyy silti ennen kuin poistaa jotain tai suorittaa skriptin.
- **Toimi autonomisesti** - agentti työskentelee ilman keskeytyksiä.

Kun agentti kysyy, pyyntö näkyy inline-transkriptiossa sen haluamastasi toiminnasta, ja voit sallia sen kerran tai koko keskustelun ajaksi.

![Avustaja kysyy ennen kuin se toimii, inline-transkriptiossa](/img/craftforj/ai/approval-prompt.png#rounded-border)

Jos olet uusi agentin kanssa, aloita antamalla sen kysyä kaikesta. Kun olet katsonut sen työskentelevän, sen omien muokkauksien hyväksyminen vähentää useimpia keskeytyksiä samalla kun säilyttää sinulle tärkeät päätökset.

## Työskentely sovelluksen kanssa keskustelussa {#working-with-the-app-in-a-conversation}

Agentti lukee tarvitsemaansa juuri silloin, kun sitä tarvitsee, sen sijaan että saisi heti kaikki sovelluksesi tiedot, ja craftforJ näyttää, mikä on liitetty keskusteluun. Voit antaa sen suoraan komponentin puusta tai valita yhden sivulta keskustelun keskellä. Kysymyksiin siitä, miltä jokin näyttää, agentti voi ottaa näyttökuvan komponentista. Tämä vaatii mallin, joka hyväksyy kuvia.

:::warning Näyttökuvat sisältävät kaiken näytöllä olevan
Näyttökuva kantaa kaiken datan, jota sovelluksesi näyttää tuolloin. Ota tämä huomioon ennen kuin osoitat isännöityä mallia sovellukseen, joka toimii todellisen datan parissa.
:::

## Mallin konfigurointi {#configuring-a-model}

craftforJ ei sisällä omaa mallia, joten valitset sen, joka sitä ajaa. Lisää API-avain yhdelle tuetuista tarjoajista tai osoita craftforJ paikallisesti käynnistyvään malliin. Avainsi säilytetään koneella, joka ajaa sovellustasi, ja avustaja pitää sen mielessä vain niin kauan kuin sivu on auki, eikä koskaan selaimen tallennuksessa. Se puhuu valitsemasi tarjoajan kanssa selaimen kautta, ei palvelimesi kautta, eikä kenenkään muun kanssa.

Mallin valitsin näyttää, mitä yksi malli eroaa toisesta, mukaan lukien, kuinka paljon sovelluksestasi ja keskustelustasi mahtuu yhdellä kertaa, mitä keskustelu maksaa ja hyväksyykö malli kuvia tai järkeilee ennen vastaamista. Malli, joka ei voi kutsua työkaluja, voi pitää keskustelua, mutta ei voi tarkastella tai muuttaa mitään.

![Mallin valitsin, joka näyttää, mitä erottaa käytettävissä olevat mallit](/img/craftforj/ai/model-picker.png#rounded-border)

Käynnistämällä mallin paikallisesti pidät kaiken koneellasi. Paikalliset mallit toimivat usein pienessä kontekstitilassa, joka täyttyy nopeasti keskustelusta todellisen sovelluksen kanssa, joten anna mallille niin paljon kontekstia kuin koneesi pystyy kantamaan.

## Keskustelut {#conversations}

Keskustelut säilytetään sovelluskohtaisesti, ja agentti voi katsoa aiempia keskusteluja, kun kysymys viittaa aiempaan tekemääsi työhön. Kun keskustelu kasvaa mallin kontekstin yli, craftforJ tiivistää vanhemmat viestit, jotta työ jatkuu sen sijaan, että se epäonnistuisi, ja merkitsee keskusteluun, että se teki niin.

Kun työ ylittää craftforJ:n, voit tiivistää keskustelun ja antaa sen muokkaimesi avustajalle. Se avustaja nappaa työn tarkemmin, kun [webforJ AI -lisäosa](/docs/ai-tooling) on asennettuna.

## Sen poistaminen käytöstä {#turning-it-off}

[`ai.enabled`](/docs/craftforj/configuration#feature-flags) ominaisuuden käyttö poistaa avustajan kokonaan craftforJ:stä. [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) ominaisuuden käyttö säilyttää avustajan, mutta estää sen kirjoittamasta Javaa oman halunsa mukaan.
