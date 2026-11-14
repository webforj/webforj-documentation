---
title: Tekoälyavustaja
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: e9d943043e52eae5368dfe4ba7795142
---
craftforJ sisältää täysiverisen koodausagentin, joka toimii **käynnissä olevan sovelluksesi** sisällä. Se kirjoittaa Javaa vapaasti, kääntää kirjoittamansa koodin ennen kuin näet sen, ottaa muutoksen käyttöön ja jatkaa työskentelyä sovelluksesi käynnistyttyä uudelleen. Kaiken se tekee juuri siinä sovelluksessa, joka on käynnissä edessäsi, eikä sen perusteella, mitä se arvelee lähdekoodivarastosi perusteella.

<VideoPlayer video="craftforj/ai-conversation" width={1920} height={1000} title="AI conversation" />

:::warning Tekoäly voi silti tehdä virheitä
Käynnissä olevan sovelluksen käyttäminen ja oman tulosteen kääntäminen tekevät agentista huomattavasti tarkemman kuin agentista, joka työskentelee sokkona. Se voi silti olla väärässä. Tarkista sen tekemät muutokset ennen niiden säilyttämistä.
:::

## Se kirjoittaa Javaa {#it-writes-java}

Agentti ei rajoitu ominaisuuksien muuttamiseen käsin. Kuvaile ongelma, niin se kirjoittaa siihen tarvittavan koodin lisäämällä metodeja, muuttamalla logiikkaa ja järjestelemällä näkymää uudelleen tehtävän vaatimalla tavalla.

Kaikki sen kirjoittamat muutokset valmistellaan levylle kirjoittamisen sijaan. Valmistellut muutokset käännetään oikealla Java-kääntäjällä. Agentti lukee kääntäjän palauttamat virheilmoitukset ja korjaa omat virheensä ennen kuin muutos tarjotaan sinulle. Tarkistettavaksesi päätyvä koodi on jo käännetty käynnissä olevaa sovellustasi vasten.

Täysi validointi vaatii JDK:n. JRE:llä craftforJ siirtyy koodin jäsentämiseen, merkitsee muutoksen vahvistamattomaksi ja ohjeistaa agenttia kertomaan tästä sen sijaan, että se esittäisi koodin tarkistetuksi.

Muutoksen käyttöönotto käynnistää sovelluksesi uudelleen. Agentti odottaa uudelleenkäynnistystä, muodostaa yhteyden uudelleen ja jatkaa siitä, mihin suunnitelmassaan jäi. Näin useita muutoksia ja uudelleenkäynnistyksiä sisältävä tehtävä valmistuu loppuun asti.

## Se työskentelee vaiheittain {#it-works-in-steps}

Annat agentille tavoitteen, et komentoa. Se suunnittelee, tutkii tarvitsemansa asiat, toimii, tarkistaa tuloksen ja korjaa itseään. Se voi suorittaa useita vaiheita yhden vuoron aikana ilman, että sinun tarvitsee ohjata jokaista niistä. Jokainen vaihe näkyy keskustelussa sitä mukaa kun se tapahtuu, ja voit avata minkä tahansa vaiheen nähdäksesi tarkalleen, mitä agentti kutsui ja mitä vastauksia se sai.

## Mihin kaikkeen se pääsee käsiksi {#what-it-can-reach}

Agentilla on laaja työkalupakki, joka kattaa kaiken, mitä craftforJ tietää sovelluksestasi, mukaan lukien:

- **Komponenttisi** – reaaliaikainen puu, todelliset ominaisuusarvot ja kunkin komponentin luonut Java-koodi. Se voi muuttaa ominaisuuksia, poistaa komponentteja ja korostaa sivulta tietyn komponentin.
- **Lähdekoodisi** – minkä tahansa projektin juurihakemistossa olevan tiedoston lukeminen, muutosten valmistelu, erojen näyttäminen ja muutosten käyttöönotto.
- **Reittisi** – reititystaulukko, aktiivinen reitti, siirtyminen minne tahansa ja reitille määritettyjen käyttöoikeussääntöjen muuttaminen.
- **Teemasi ja tyylisi** – suunnittelutunnisteiden lukeminen ja asettaminen, teeman tallentaminen sekä käytettävissä olevien fonttien ja kuvakkeiden etsiminen.
- **Itse sivu** – CSS:n ja JavaScriptin lisääminen suoraan sivulla ja komponentin kuvakaappauksen ottaminen sen tarkastelua varten.
- **webforJ-tietämyskanta** – sama dokumentaatio, komponenttien tyylitysmahdollisuudet ja `--dwc-*`-tunnistetyökalut, jotka [webforJ MCP -palvelin](/docs/ai-tooling/mcp) tarjoaa editorillesi. Se on sisäänrakennettu ja aina käytettävissä.

Koska agentti käyttää kaikkea tätä craftforJ:n kautta, se saa käyttöönsä samat tiedot kuin sinäkin. Se lukee todellisia arvoja, ei niitä, joihin lähdekoodisi antaa olettaa.

## Hyväksynnät {#approvals}

Päätät etukäteen, kuinka paljon agentti saa tehdä itsenäisesti:

- **Kysy ennen toimimista** – jokainen vaikutuksia aiheuttava toiminto keskeytyy hyväksyntääsi varten.
- **Ota muutokset käyttöön automaattisesti** – agentti työskentelee vapaasti, mutta pyytää silti lupaa ennen jonkin poistamista tai komentosarjan suorittamista.
- **Suorita itsenäisesti** – agentti työskentelee keskeytyksettä.

Kun agentti pyytää lupaa, pyyntö näkyy keskustelussa sen toiminnon yhteydessä. Voit sallia toiminnon kerran tai koko keskustelun ajaksi.

![Avustaja pyytää lupaa ennen toimimista keskustelussa](/img/craftforj/ai/approval-prompt.png#rounded-border)

Jos agentti on sinulle uusi, aloita pyytämällä sitä kysymään lupa kaikkeen. Kun olet nähnyt, miten se toimii, sen omien muutosten salliminen vähentää keskeytyksiä ja jättää silti tärkeät päätökset sinulle.

## Sovelluksen kanssa työskentely keskustelussa {#working-with-the-app-in-a-conversation}

Agentti lukee tarvitsemansa tiedot tarpeen mukaan sen sijaan, että sille annettaisiin koko sovellus heti alussa. craftforJ näyttää, mitkä tiedot keskusteluun on liitetty. Voit antaa sille komponentin suoraan puusta tai valita komponentin sivulta kesken keskustelun. Jos kysyt jonkin asian ulkoasusta, agentti voi ottaa komponentista kuvakaappauksen. Tämä edellyttää mallia, joka pystyy käsittelemään kuvia.

:::warning Kuvakaappaukset sisältävät kaiken näytöllä näkyvän
Kuvakaappaus sisältää kaikki tiedot, joita sovelluksesi näyttää kuvaushetkellä. Ota tämä huomioon ennen kuin käytät isännöityä mallia todellista dataa käyttävän sovelluksen kanssa.
:::

## Mallin määrittäminen {#configuring-a-model}

craftforJ ei sisällä omaa mallia, joten valitset itse, mikä malli sitä käyttää. Lisää jonkin tuetun palveluntarjoajan API-avain tai määritä craftforJ käyttämään paikallisesti suoritettavaa mallia. Avain tallennetaan sovellustasi suorittavalle koneelle, ja avustaja pitää sitä muistissa vain niin kauan kuin sivu on avoinna – sitä ei koskaan tallenneta selaimen tallennustilaan. Avustaja kommunikoi valitsemasi palveluntarjoajan kanssa suoraan selaimesta, ei palvelimesi kautta, eikä kenenkään muun kanssa.

Mallivalitsin kertoo, mikä erottaa mallit toisistaan: kuinka paljon sovelluksestasi ja keskustelustasi mahtuu kerralla mallin käsiteltäväksi, mitä keskustelu maksaa sekä pystyykö malli käsittelemään kuvia tai päättelemään ennen vastaamista. Malli, joka ei pysty kutsumaan työkaluja, voi osallistua keskusteluun, mutta se ei voi tutkia tai muuttaa mitään.

![Mallivalitsin näyttää, mikä erottaa käytettävissä olevat mallit toisistaan](/img/craftforj/ai/model-picker.png#rounded-border)

Paikallisesti suoritettava malli pitää kaiken koneellasi. Paikallisten mallien konteksti-ikkuna on usein oletusarvoisesti pieni, ja todellista sovellusta koskeva keskustelu täyttää sen nopeasti. Siksi anna mallille niin suuri konteksti kuin koneesi pystyy käsittelemään.

## Keskustelut {#conversations}

Keskustelut tallennetaan sovelluskohtaisesti, ja agentti voi palata aiempiin keskusteluihin, jos kysymyksesi liittyy aiemmin tekemääsi työhön. Kun keskustelu kasvaa mallin konteksti-ikkunaa suuremmaksi, craftforJ tiivistää vanhemmat viestit, jotta työ voi jatkua virheen sijaan, ja kertoo chatissa tehneensä niin.

Jos työ kasvaa craftforJ:n kykyjä suuremmaksi, voit tiivistää keskustelun ja siirtää sen editorisi avustajalle. Tämä avustaja pystyy jatkamaan työtä tarkemmin, kun [webforJ AI -lisäosa](/docs/ai-tooling) on asennettuna.

## Poistaminen käytöstä {#turning-it-off}

Ominaisuus [`ai.enabled`](/docs/craftforj/configuration#feature-flags) poistaa avustajan craftforJ:stä kokonaan. Ominaisuus [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) säilyttää avustajan, mutta estää sitä kirjoittamasta Javaa itse.
