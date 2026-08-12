---
title: craftforJ Assistant
sidebar_position: 2
sidebar_class_name: new-content
description: >-
  A coding agent inside your running webforJ app that writes Java freely,
  compiles it, and applies it with your approval.
_i18n_hash: 2c2a04b29b7b6de57e5689628cd659d0
---
craftforJ-avustaja on koodausagentti, joka toimii sisällä **käynnissä olevassa sovelluksessa**. Se kirjoittaa Javaa vapaasti, kääntää kirjoituksensa ennen kuin näet sen, soveltaa muutosta ja jatkaa toimintaa sovelluksen uudelleenkäynnistyksen jälkeen. Se tulee mukana webforJ:ssä osana [craftforJ](/docs/craftforj), kehitysympäristö, joka antaa sinulle komponenttipuun, reitit, elävät ominaisuudet ja teemat sovellukselle sen toimiessa.

## Kuinka kaksi vertailua {#how-the-two-compare}

| | [webforJ AI -lisäosa](/docs/ai-tooling) | craftforJ-avustaja |
|---|---|---|
| **Asuu** | Editorissasi | Käynnissä olevassa sovelluksessa |
| **Lukee** | Lähdetiedostosi | Sovellustasi, reaaliaikaisesti, sen todellisilla arvoilla |
| **Tekee** | Kirjoittaa koodia | Kirjoittaa koodia, tarkistaa, muuttaa, navigoi ja teemoittaa käynnissä olevaa sovellusta |
| **Vahvistaa** | Seuraavassa käännöksessäsi | Kääntämällä jokaisen muokkauksen ennen kuin näet sen, ja näyttämällä sinulle tuloksen käynnissä |
| **Soveltuu** | Jotakin uutta rakentamiseen alusta alkaen | Ymmärtämiseen, virheiden korjaamiseen, rakentamiseen ja prototyyppien tekemiseen sovelluksen kanssa, joka on edessäsi |

Kaksi ovat toisiaan täydentäviä ja voivat siirtää työtehtäviä toisilleen. Kun työ ylittää craftforJ:n rajoitukset, voit [siirtää craftforJ-keskustelun](/docs/craftforj/ai#conversations) editoriisi.

## Mitä se voi tehdä {#what-it-can-do}

Annetaan agentille tavoite sen sijaan, että annettaisiin komento. Se suunnittelee, tarkistaa mitä tarvitsee, toimii, tarkistaa tuloksen ja korjaa itseään monen vaiheen aikana yhdellä kerralla.

Se kirjoittaa Javaa vapaasti, joten se ei ole rajoitettu ominaisuuden muutoksiin, jotka voit tehdä käsin. Jokainen muokkaus on vaiheistettu eikä kirjoitettu levylle, se lähetetään todelliselle Java-kääntäjälle ja agentti korjaa sen saatujen diagnostiikan perusteella, joten se, mikä saavuttaa tarkistuksesi, kääntyy jo käynnissä olevaan sovellukseesi. Soveltaessa se käynnistää sovelluksen uudelleen, ja agentti jatkaa suunnitelmaansa jälleen, kun se on takaisin.

Samaan aikaan se käyttää kaikkea, mitä craftforJ tietää: elävä komponenttipuu ja todelliset ominaisarvot, Java-lähdekoodisi, reititystaulukko ja reitti- ja pääsääntöjä, teema ja tyylitiedosto, itse sivu CSS:lle ja skripteille, komponentin kuvakaappauksia, sekä webforJ:n tietopohjaa ja `--dwc-*` -token-työkaluja. Katso [AI Assistant](/docs/craftforj/ai) yksityiskohtia varten.

## Mallin konfigurointi {#configuring-a-model}

craftforJ ei lähetä omaa malliaan, joten valitset sen, joka sitä pyörittää. Lisää API-avain yhdelle tuetulle tarjoajalle tai osoita craftforJ malli, joka toimii paikallisesti Ollaman kanssa. Avain tallennetaan koneelle, joka pyörittää sovellustasi, ja se pidetään selaimessa vain kun sivu on avoinna, ja avustaja puhuu tarjoajasi kanssa selaimesta eikä palvelimesi kautta. Katso [Mallin konfigurointi](/docs/craftforj/ai#configuring-a-model).

:::warning AI voi silti tehdä virheitä
Työskentely käynnissä olevan sovelluksen kanssa ja oman tulosteen kokoaminen tekee agentista huomattavasti tarkemman kuin sellaista, joka kirjoittaa sokaistuna. Se voi silti olla väärässä. Tarkista mitä se teki ennen kuin hyväksyt sen.
:::

## Aloittaminen {#getting-started}

craftforJ on pois päältä, kunnes otat sen käyttöön, ja se toimii vain kehityksessä:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Avaa craftforJ painamalla <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> ja siirry AI Assistant -välilehteen. Täydellisiä asetuksia varten katso [Aloittaminen](/docs/craftforj/getting-started).
