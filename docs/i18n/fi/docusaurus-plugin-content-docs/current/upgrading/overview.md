---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 31ec5b4108bae52597797c3add587e4c
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ:n julkaisusykli seuraa strukturoitua ja ennakoitavaa mallia, joka varmistaa vakauden, suorituskyvyn ja jatkuvan innovoinnin. Tämä asiakirja tarjoaa yleiskatsauksen siitä, kuinka julkaisuja suunnitellaan, mitä tyyppisiä julkaisuja odottaa ja kuinka käyttäjät voivat pysyä ajan tasalla ja valmistautuneina.

<AISkillTip skill="webforj-upgrading-versions" />

## Tyypit webforJ julkaisuja {#types-of-webforj-releases}

webforJ noudattaa strukturoitua julkaisumallia, johon sisältyy seuraavat julkaisut:

### 1. Suuret julkaisut {#1-major-releases}
- Tapahtuu vuosittain.
- Esittelee merkittäviä uusia ominaisuuksia, parannuksia ja kehityksiä.
- Saattaa vaatia kokoonpanomuutoksia tai olemassa olevien sovellusten mukauttamista.
- Tunnistetaan versioinnilla, kuten **webforJ 20.00, webforJ 21.00 jne.**

### 2. Pienet julkaisut {#2-minor-releases}
- Tapahtuu useita kertoja vuodessa (noin kuuden tai kahdeksan viikon välein).
- Tarjoaa vähäisiä parannuksia, optimointeja ja pieniä uusia ominaisuuksia.
- Tunnistetaan versioinnilla, kuten **webforJ 20.01, webforJ 20.02 jne.**

### 3. Korjaus- ja bugikorjausjulkaisut {#3-patches-and-bug-fix-releases}
- Julkaistaan tarvittaessa.
- Käsittelee kriittiset virheet, suorituskykyongelmat ja tietoturva-aukot.
- Tunnistetaan lisänumeroilla, kuten **webforJ 20.01.1, webforJ 20.01.2 jne.**

## Mitä odottaa jokaiselta julkaisulta {#what-to-expect-with-each-release}

### Ominaisuuspäivitykset {#feature-enhancements}
- Suuret ja pienet julkaisut tuovat uusia ominaisuuksia, optimointeja ja integrointeja.
- Ominaisuusroadmapit jaetaan julkaisumuistiinpanoissa, jotta käyttäjät voivat suunnitella eteenpäin.

:::info Takaisin yhteensopivuus
Vaikka yhteensopivuuden säilyttämiseksi tehdään ponnistuksia, suurissa julkaisuissa saattaa olla muutoksia, jotka vaativat sovellusten säätämistä. Käyttäjiä kannustetaan tarkastelemaan julkaisumuistiinpanoja poistetuista ominaisuuksista.
:::

### Tietoturvapäivitykset {#security-updates}
- Tietoturva on etusijalla, ja kriittisiä haavoittuvuuksia käsitellään korjausjulkaisuissa mahdollisimman pian.

:::tip Snapshot-rakenteet
Snapshot-rakenteet ovat saatavilla ennen useimpia julkaisuja. Käyttäjiä kannustetaan testaamaan niitä ongelmien varhaista tunnistamista varten ja palautteen antamiseksi. Katso [Snapshots](/docs/configuration/snapshots) artikkelista, miten käyttää webforJ snapshotteja ja mistä ne voi saada.
:::

## Kuinka pysyä ajan tasalla {#how-to-stay-updated}

### Julkaisumuistiinpanot ja ilmoitukset {#release-notes-and-announcements}
- Jokaisen julkaisun mukana on yksityiskohtaiset [julkaisumuistiinpanot](https://github.com/webforj/webforj/releases), jotka kuvaavat uusia ominaisuuksia, bugikorjauksia ja mahdollisia vaadittavia toimia.
- Käyttäjien tulisi tilata webforJ [blogi](../../blog) ajankohtaisia päivityksiä varten.

:::tip Päivitys-suositukset
Asiakkaiden tulisi suunnitella päivitykset liiketoimintatarpeiden ja vakausvaatimusten mukaan. Käyttäjiä kannustetaan pysymään viimeisimmässä julkaisuissa hyötyäkseen suorituskyvyn parannuksista ja uusista ominaisuuksista.
:::

### Tuki ja yhteensopivuus {#support-and-compatibility}
- webforJ tarjoaa asiakirjoja ja päivitysohjeita suurille julkaisuilla.
- Yhteisöfoorumit ja asiakastukikanavat ovat saatavilla ongelmien ratkaisemiseksi ja avustamiseksi.

<DocCardList className="topics-section" />
