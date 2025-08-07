---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 1bdddfccaece385582aecb1b63967611
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ:n julkaisut käyvät läpi rakenteellisen ja ennakoitavan mallin, joka varmistaa vakauden, suorituskyvyn ja jatkuvan innovoinnin. Tämä asiakirja antaa yleiskuvan siitä, kuinka julkaisut suunnitellaan, mitä tyyppisiä julkaisuja odottaa ja kuinka käyttäjät voivat pysyä ajan tasalla ja valmistautuneina.

## webforJ:n julkaisutyypit {#types-of-webforj-releases}

webforJ noudattaa rakenteellista julkaisumallia, joka sisältää seuraavat julkaisutyypit:

### 1. Pääjulkaisut {#1-major-releases}
- Tapahtuu vuosittain.
- Esittelee merkittäviä uusia ominaisuuksia, parannuksia ja kehityksiä.
- Saattaa vaatia konfiguraatiomuutoksia tai olemassa olevien sovellusten sopeuttamista.
- Tunnistetaan versionumeroinnilla, kuten **webforJ 20.00, webforJ 21.00, jne.**

### 2. Pienjulkaisut {#2-minor-releases}
- Tapahtuu useita kertoja vuodessa (noin joka kuusi tai kahdeksan viikkoa).
- Tarjoaa vähittäisiä parannuksia, optimointeja ja pieniä uusia ominaisuuksia.
- Tunnistetaan versionumeroinnilla, kuten **webforJ 20.01, webforJ 20.02, jne.**

### 3. Korjaukset ja bugikorjausjulkaisut {#3-patches-and-bug-fix-releases}
- Julkaistaan tarvittaessa.
- Käsittelee kriittisiä bugeja, suorituskykyongelmia ja tietoturvahaavoittuvuuksia.
- Tunnistetaan lisänumeroilla, kuten **webforJ 20.01.1, webforJ 20.01.2, jne.**

## Mitä odottaa jokaisessa julkaisussa {#what-to-expect-with-each-release}

### Ominaisuuksien parannukset {#feature-enhancements}
- Pää- ja pienjulkaisut esittelevät uusia mahdollisuuksia, optimointeja ja integraatioita.
- Ominaisuusroadmapit jaetaan julkaisuhavainnoissa, jotta käyttäjät voivat suunnitella eteenpäin.

:::info Taaksepäin yhteensopivuus
Vaikka pyritään ylläpitämään yhteensopivuutta, pääjulkaisut saattavat sisältää muutoksia, jotka vaativat sovellusten säätämistä. Käyttäjiä kannustetaan tarkastelemaan julkaisuhavaintoja vanhentuneista ominaisuuksista.
:::

### Tietoturvapäivitykset {#security-updates}
- Tietoturva on ensisijaisen tärkeää, ja kriittiset haavoittuvuudet käsitellään korjausjulkaisuissa mahdollisimman pian.

:::tip Snapshot-rakennukset
Snapshot-rakennuksia on saatavilla ennen useimpia julkaisuja. Käyttäjiä kannustetaan testaamaan niitä aikaisessa vaiheessa ongelmien tunnistamiseksi ja palautteen antamiseksi.
:::

## Kuinka pysyä ajan tasalla {#how-to-stay-updated}

### Julkaisuhavainnot ja ilmoitukset {#release-notes-and-announcements}
- Jokainen julkaisu on mukana yksityiskohtaiset [julkaisuhavainnot](https://github.com/webforj/webforj/releases), jotka kuvaavat uusia ominaisuuksia, bugikorjauksia ja mahdollisia vaadittavia toimia.
- Käyttäjien tulisi tilata webforJ [blogi](../../blog) saadakseen ajankohtaisia päivityksiä.

:::tip Päivitys-suositukset
Asiakkaiden tulisi suunnitella päivitykset liiketoiminta- ja vakausvaatimusten mukaan. Käyttäjiä kannustetaan pysymään viimeisimmässä julkaisussa hyötyäkseen suorituskyvyn parannuksista ja uusista ominaisuuksista.
:::

### Tuki ja yhteensopivuus {#support-and-compatibility}
- webforJ tarjoaa dokumentaatiota ja päivitysohjeita pääjulkaisuille.
- Yhteisöfoorumit ja asiakastukikanavat ovat saatavilla vianetsintään ja apuun.

<DocCardList className="topics-section" />
