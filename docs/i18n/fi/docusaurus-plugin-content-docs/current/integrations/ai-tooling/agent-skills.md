---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 98d3f61447c289339f92fc4872e734f1
---
Agent Skills opettavat AI-koodausavustajia rakentamaan webforJ-sovelluksia oikeiden APIen, suunnittelutunnusten ja komponenttimallien avulla. Sen sijaan, että avustaja arvaisi kehyskonventioita, se lataa taidon ja seuraa strukturoitua työprosessia tuottaakseen koodia, joka kääntyy ja noudattaa parhaita käytäntöjä ensimmäisellä yrityksellä.

:::tip Käytä liitintä
Alla olevat taidot toimitetaan yhdessä **[webforJ AI -liitteen](/docs/integrations/ai-tooling)** kanssa, samoin kuin [MCP-palvelimen](/docs/integrations/ai-tooling/mcp). Yksi asennus antaa avustajallesi molemmat osat.
:::

Taidot noudattavat avointa [Agent Skills](https://agentskills.io/specification) -standardia ja toimivat monilla AI-avustajilla, mukaan lukien Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex ja monet muut. Taito kertoo avustajalle, minkä tyyppistä tehtävää se käsittelee; avustaja lataa sen automaattisesti, kun kehotteesi vastaa. Esimerkiksi kysyminen "teema tämä sovellus sinisellä paletilla" laukaisee `webforj-styling-apps` -taidon, joka ohjaa avustajaa etsimään kelvollisia DWC-tunnuksia, kirjoittamaan rajattua CSS:ää ja validoimaan jokaisen muuttujan nimen ennen kuin kirjoittaa mitään levylle.

## Miksi käyttää taitoja? {#why-use-skills}

MCP-palvelin tekee tarkkaa webforJ-tietoa saataville tarvittaessa, mutta yksinään se ei kerro avustajalle _milloin_ katsotaan jotain, _mikä_ lähestymistapa sopii tehtävään tai _missä järjestyksessä_ asioita tehdään. Siinä taitot tulevat peliin.

Taidot antavat avustajalle tehtäväkohtaisen toimintasuunnitelman: miten luokitella edessä oleva työ, mitkä webforJ-mallit sopivat, mitä MCP-työkaluja konsultoida jokaisessa vaiheessa ja miten validoida tulokset ennen niiden palauttamista. Lopputuloksena on johdonmukaista, konventioita noudattavaa webforJ-koodia sen sijaan, että se olisi kokoelma teknisesti kelvollisia mutta tyyliltään yhdistelemättömiä pätkiä.

## Kuinka taidot eroavat MCP:stä {#how-skills-differ-from-mcp}

Taidot ja [webforJ MCP -palvelin](/docs/integrations/ai-tooling/mcp) palvelevat toisiaan täydentäviä rooleja. MCP-palvelin tarjoaa reaaliaikaisia työkaluja, joita avustaja voi kutsua hakiakseen tietoa tai tuottaakseen ulostuloa. Taidot tarjoavat työprosessin, joka kertoo avustajalle _milloin_ käyttää näitä työkaluja, missä järjestyksessä asioita tehdään ja miten tuloksia validoidaan.

| | MCP-palvelin | Agent-taidot |
|---|---|---|
| **Mitä se tarjoaa** | Työkalut, joita avustaja kutsuu tarpeen mukaan (dokumenttihakua, pohjarakennusta, teeman luontia, tunnusvalidointia) | Työprosessit ja päätöstaulukot, jotka ohjaavat avustajaa tehtävän lähestymisessä |
| **Milloin se toimii** | Kun avustaja päättää kutsua työkalua | Automatisoidusti, kun avustaja havaitsee vastaavan tehtävän |
| **Paras käyttö** | Tiettyjen kysymysten vastaamiseen, artefaktien luomiseen | Loppupäätehtävät, jotka tarvitsevat johdonmukaista webforJ-lähestymistapaa |

Käytännössä molemmat toimivat parhaiten yhdessä - ja [webforJ AI -liite](https://github.com/webforj/webforj-ai) toimittaa ne yhtenä asennuksena.

## Asennus {#installation}

Asenna **[webforJ AI -liite](/docs/integrations/ai-tooling)** - se tuo mukanaan molemmat taidot alla MCP-palvelimen ohella. Asiakkaille, jotka eivät tue liitintekniikkaa, [webforJ AI -varasto](https://github.com/webforj/webforj-ai#clients) listaa taidot, joita kukin työkalu lukee, jotta voit kopioida taitokansiot käsin.

## Saatavilla olevat taidot {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: rakenna uudelleenkäytettäviä webforJ-komponentteja verkkokomponenttikirjastoista, JavaScript-kirjastoista tai olemassa olevista webforJ-komponenteista
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä, kun tarvitset uudelleenkäytettävää Java-komponenttia, joka on kääritty mihin tahansa lähteeseen - joko olemassa olevaan mukautettuun elementtikirjastoon, tavalliseen JavaScript-kirjastoon tai olemassa olevien webforJ-komponenttien yhdistelmään. Avustaja valitsee oikean webforJ-pohjaklassin työhön, liittää ominaisuudet, tapahtumat ja paikat oikeilla kuvioilla, ja tuottaa testejä, jotka seuraavat webforJ-konventioita.

**Milloin se käynnistyy**

- *"Kääri tämä mukautettu elementtikirjasto webforJ-komponenteiksi."*
- *"Yhdistä nämä webforJ-komponentit uudelleenkäytettäväksi kortiksi."*
- *"Integroi tämä tavallinen JavaScript-kirjasto webforJ-komponentiksi."*
- *"Avaa tämä selaimen API webforJ-apuna."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: teema ja tyylittele webforJ-sovelluksia DWC-suunnittelutunnusjärjestelmän avulla
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä kaikessa visuaalisessa työssä webforJ-sovelluksessa: palettamuutokset, komponenttikohtaiset tyylit, asettelu ja väli, typografia, koko teemat, taulukon ulkonäkö tai koordinoidut Google Chart -värit. Avustaja kirjoittaa CSS:ää, joka noudattaa DWC-suunnittelutunnuksia, rajaa valitsimet oikein ja validoi jokaisen `--dwc-*` viittauksen oikeasta luettelosta valitsemallesi webforJ-versiolle - joten tumma tila ja teeman vaihto toimivat edelleen.

**Milloin se käynnistyy**

- *"Teema tämä sovellus sinisellä paletilla."*
- *"Tyylitä dwc-painike vastaamaan brändiohjeita."*
- *"Tee tämä asettelu tiiviimmäksi - säädä väliä ja typografiaa."*

</div>
  </AccordionDetails>
</Accordion>
