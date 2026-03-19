---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 5b67f3c7842c20cbef9c77df8f3dd69a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ:n julkaisusykli noudattaa strukturoitua ja ennakoitavaa mallia varmistaakseen vakauden, suorituskyvyn ja jatkuvan innovaation. Tämä asiakirja antaa yleiskuvan siitä, kuinka julkaisuja suunnitellaan, mitä julkaisutyyppisiä odottaa ja kuinka käyttäjät voivat pysyä ajan tasalla ja valmistautua.

## webforJ:n julkaisutyypit {#types-of-webforj-releases}

webforJ noudattaa strukturoitua julkaisumallia, johon sisältyy seuraavat julkaisutyypit:

### 1. Pääjulkaisut {#1-major-releases}
- Tapahtuvat vuosittain.
- Esittelee merkittäviä uusia ominaisuuksia, parannuksia ja kehityksiä.
- Saattaa vaatia konfiguraatiomuutoksia tai nykyisten sovellusten mukauttamista.
- Tunnistettu versioinnilla, kuten **webforJ 20.00, webforJ 21.00 jne.**

### 2. Sivujulkaisut {#2-minor-releases}
- Tapahtuvat useita kertoja vuodessa (noin joka kuusi- kahdeksan viikkoa).
- Tarjoaa asteittaisia parannuksia, optimointeja ja pieniä uusia ominaisuuksia.
- Tunnistettu versioinnilla, kuten **webforJ 20.01, webforJ 20.02 jne.**

### 3. Korjaustiedostot ja virheenkorjausjulkaisut {#3-patches-and-bug-fix-releases}
- Julkaistaan tarvittaessa.
- Käsittelee kriittisiä virheitä, suorituskykyongelmia ja tietoturvahaavoittuvuuksia.
- Tunnistettu lisänumeroilla, kuten **webforJ 20.01.1, webforJ 20.01.2 jne.**

## Mitä odottaa jokaisessa julkaisussa {#what-to-expect-with-each-release}

### Ominaisuuksien parannukset {#feature-enhancements}
- Pää- ja sivujulkaisut esittelevät uusia ominaisuuksia, optimointeja ja integraatioita.
- Ominaisuuskehityskartat jaetaan julkaisumuistiinpanoissa käyttäjien ennakoimiseksi.

:::info Taaksepäin yhteensopivuus
Vaikka pyritään säilyttämään yhteensopivuus, pääjulkaisut saattavat sisältää muutoksia, jotka vaativat sovellusten säätämistä. Käyttäjiä kehottavat tarkistamaan julkaisu muistutuksia poistetuista ominaisuuksista.
:::

### Tietoturvapäivitykset {#security-updates}
- Tietoturva on etusijalla, ja kriittiset haavoittuvuudet käsitellään korjausjulkaisuissa mahdollisimman pian.

:::tip Otantarakennukset
Otantarakennukset ovat saatavilla ennen useimpia julkaisuja. Käyttäjiä kehotetaan testaamaan niitä ongelmien tunnistamiseksi varhaisessa vaiheessa ja antamaan palautetta. Katso artikkeli [Otantarakennukset](/docs/configuration/snapshots) oppiaksesi, kuinka käyttää webforJ:n otantarakennuksia ja mistä niitä saa.
:::

## Kuinka pysyä ajan tasalla {#how-to-stay-updated}

### Julkaisumuistiinpanot ja ilmoitukset {#release-notes-and-announcements}
- Jokaisen julkaisun mukana on yksityiskohtaiset [julkaisumuistiinpanot](https://github.com/webforj/webforj/releases), jotka kuvaavat uusia ominaisuuksia, virheenkorjauksia ja tarvittavia toimenpiteitä.
- Käyttäjien tulisi tilata webforJ:n [blogi](../../blog) saadakseen ajankohtaisia päivityksiä.

:::tip Päivitysehdotukset
Asiakkaiden tulisi suunnitella päivitykset liiketoimintatarpeiden ja vakausvaatimusten perusteella. Käyttäjiä kehottavat pysymään viimeisimmässä julkaisussa hyötyäkseen suorituskyvyn parannuksista ja uusista ominaisuuksia.
:::

### Tuki ja yhteensopivuus {#support-and-compatibility}
- webforJ tarjoaa dokumentaatiota ja päivitysohjeita pääjulkaisuille.
- Yhteisöfoorumit ja asiakastukikanavat ovat saatavilla vianetsintään ja apuun.

<DocCardList className="topics-section" />
