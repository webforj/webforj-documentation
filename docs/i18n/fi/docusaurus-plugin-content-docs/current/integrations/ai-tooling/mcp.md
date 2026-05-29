---
title: MCP Server
sidebar_position: 5
_i18n_hash: eea9d8f962b10512151bf7c6935f25e0
---
webforJ:n Malli Konteksti Protokolla (MCP) palvelin liittää tekoälykoodausavustajat webforJ:n dokumentaatioon, API:hin, suunnittelutokeneihin ja kehitystyökaluihin. Sen sijaan, että avustaja arvaisi kehyskonventioita, se kysyy palvelimelta ja saa vastauksia, jotka perustuvat oikeaan webforJiisiin.

:::tip Käytä liitintä
Ellet tiedä haluavasi vain MCP-palvelinta, asenna **[webforJ AI -liitin](/docs/integrations/ai-tooling)** sen sijaan - se pakkaa tämän palvelimen yhteen sopivien [Agent Skills](/docs/integrations/ai-tooling/agent-skills) kanssa yhteen asennukseen.
:::

## Mikä on MCP? {#whats-an-mcp}

Malli Konteksti Protokolla on avoin standardi, joka antaa tekoälyavustajille mahdollisuuden kutsua ulkoisia työkaluja tarpeen mukaan. webforJ:n MCP-palvelin toteuttaa tämän protokollan, jotta avustajasi voi:

- Etsiä asioita webforJ:n dokumenteista sen sijaan, että se kuvittelisi metodin nimiä
- Rakentaa uusia webforJ-projekteja virallisista Maven-archetypeistä
- Luoda saavutettavia DWC-teemoja brändiväristä
- Lukea DWC-komponentin todellista tyylityspintaa ja validoida mitä tahansa `--dwc-*` tokenia ennen kuin se saapuu CSS:ään

:::warning Tekoäly voi silti tehdä virheitä
MCP-palvelin parantaa merkittävästi tarkkuutta, mutta tekoälyavustajat voivat silti tuottaa virheellistä koodia monimutkaisissa skenaarioissa. Tarkista ja testaa aina luotu koodi ennen kuin julkaiset sen.
:::

## Asennus {#installation}

Täydellisen kokemuksen saamiseksi asenna **[webforJ AI -liitin](/docs/integrations/ai-tooling)** - se konfiguroi tämän palvelimen Agent Skillsien rinnalle, joita avustajasi tarvitsee sen tehokkaaseen käyttöön.

Jos haluat vain MCP-palvelimen (ei taitoja), osoita asiakas ohjelmasi `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Suositeltu tapa Copilot CLI:ssä on **[webforJ AI -liitin](/docs/integrations/ai-tooling)** - se rekisteröi MCP-palvelimen puolestasi yhdellä askelella. Raakaa MCP-vain-asennusta varten katso per-asiakasohjeet [webforJ AI -repo](https://github.com/webforj/webforj-ai#clients).

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Lisää VS Code -asetuksiisi:

```json
"mcp": {
  "servers": {
    "webforj-mcp": {
      "url": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

Lisää `~/.gemini/settings.json`:

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "httpUrl": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

Lisää `~/.codex/config.toml`:

```toml
[mcp_servers.webforj-mcp]
url = "https://mcp.webforj.com/mcp"
```

</TabItem>
</Tabs>

### Muut asiakkaat {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity ja muut MCP-yli-HTTP-asiakkaat toimivat myös - ne käyttävät vain omaa konfiguraatiomuotoaan. Katso [per-asiakas asennusopas](https://github.com/webforj/webforj-ai#clients) saadaksesi tarkka koodi jokaiselle.

## Mitä palvelin voi tehdä {#capabilities}

Kun MCP-palvelin on yhdistetty, tekoälyavustajasi saa seuraavat kyvykkyydet. Mikä tahansa niistä voidaan laukaista luonnolliskielisen pyynnön avulla - avustaja valitsee oikean automaattisesti.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Tavoita oikea webforJ-versio</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ennen kuin se vastaa versioon liittyviin kysymyksiin (mikä tahansa tyylitykseen tai API:hin liittyvä), avustaja selvittää, mikä webforJ-versio sinulla on. Se lukee `pom.xml`-tiedoston, kun se on saatavilla, ja kysyy muuten sinulta. Jokainen seuraava vastaus on rajattu tuohon versioon.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Etsi asioita webforJ:n tietopohjasta</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avustaja voi kysyä koko webforJ:n tietopohjasta vastauksia, jotka perustuvat todelliseen kehykseen. Tulokset rajaantuvat siihen, mitä kysyt - API-kysymykseen, oppaaseen, koodinäytteeseen tai Kotlin DSL:ään.

      **Esimerkki pyynnöistä:**
      ```
      "Löydä webforJ:n Button-komponentin tapahtumankäsittelyesimerkit"

      "Miten asetetaan reititys käyttäen @Route webforJ:ssä?"

      "Näytä webforJ:n lomakevalidointiesimerkki"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Rakentaa uusi webforJ-projekti</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avustaja luo oikean Maven-archetype-komennon uutena webforJ-sovelluksena vaatimustesi perusteella (archetype, Spring-integrointi, nimi, ryhmä).

      **Archetypet:**
      - `hello-world` - aloitussovellus esimerkkikomponenteilla
      - `blank` - minimaalinen projektirakenne
      - `tabs` - välilehtien käyttöliittymämalli
      - `sidemenu` - sivunavigointimalli

      **Maut:**
      - `webforj` - standardi webforJ-sovellus
      - `webforj-spring` - webforJ integraationa Spring Bootin kanssa

      **Esimerkki pyynnöistä:**
      ```
      "Luo webforJ-projekti nimeltä CustomerPortal käyttäen sidemenu-archetypea"

      "Generoi webforJ Spring Boot -projekti, jossa on välilehtikenttä nimeltä Dashboard"
      ```

      :::tip Saatavilla olevat Archetypet
      Koko luettelon archetypeista saat [archetype-katalogista](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Generoi DWC-teema</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Yhdestä brändiväristä avustaja tuottaa täydellisen DWC-teeman: pää-, onnistuminen, varoitus, vaara, tieto, oletus ja harmaa väripaletti automaattisella tekstikontrastilla. Tuloste sisältää tyylitiedoston sekä `@AppTheme` / `@StyleSheet` -kytkentä.

      **Esimerkki pyynnöistä:**
      ```
      "Generoi webforJ-teema brändiväristä #6366f1"

      "Luo saavutettava teema, jossa on HSL 220, 70, 50 päävärinä"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Tyylittele DWC-komponentit oikein</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avustaja lukee jokaisen DWC-komponentin todellista tyylityspintaa - CSS-käyttäjämuuttujat, varjo-osat, heijastetut attribuutit ja slotit - ennen kuin kirjoittaa mitään CSS:ää. Se voi myös luetella jokaisen DWC-tagin ja ratkaista webforJ Java-luokkien (`Button`, `TextField`) vastaavuudet DWC:ssä.

      **Esimerkki pyynnöistä:**
      ```
      "Mitä CSS-muuttujia ja osia dwc-button altistaa?"

      "Näytä kaikki slotit, jotka ovat saatavilla dwc-dialogissa"

      "Mihin DWC-tagiin webforJ:n TextField-luokka kartoittaa?"
      ```

      Yhdistä tämä [styling-apps agent skill](/docs/integrations/ai-tooling/agent-skills) -toimintaan end-to-end tyylityöprosesseille.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Työskentele DWC-suunnittelutokenien kanssa</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avustaja voi luetella arvovaltaisen luettelon `--dwc-*` tokeista webforJ-versiollesi - väripaletti siemenet, sävyt, pinnat, väli, typografia, reunat - suodatettuna etuliitteen tai osajonon mukaan. Se myös validoi minkä tahansa CSS-, Java- tai Markdown-lähteen, jonka annat sen oikeaan token-katalogiin, ja merkitsee tuntemattomat nimet ehdotetuilla korjauksilla.

      **Esimerkki pyynnöistä:**
      ```
      "Luettele jokainen --dwc-space-* token"

      "Validoi app.css tuntemattomille --dwc-* tokeille"

      "Mitkä pääväripaletti sävyt ovat saatavilla?"
      ```

      Vaatimusten tarkistus löytää kirjoitusvirheitä ja keksittyjä tokeja ennen kuin ne toimitetaan hiljaisesti epäonnistuvana CSS:nä.
    </div>
  </AccordionDetails>
</Accordion>

## Hyvien pyyntöjen kirjoittaminen {#writing-good-prompts}

MCP-palvelinta konsultoidaan vain, kun avustaja ajattelee sen olevan relevanttia. Muutamat tavat pitävät sen mukana:

- **Nimeä kehys.** Mainitse "webforJ" pyynnössä, jotta avustaja ottaa MCP-palvelimen käyttöön sen sijaan, että se käyttäisi yleistä Java-tietämystään.
- **Ole tarkka.** `"Luo webforJ-projekti nimeltä InventorySystem käyttäen sidemenu-archetypea ja Spring Bootia"` on parempi kuin `"tee sovellus"`.
- **Kysy varmistusta.** Kuten `"varmista webforJ-dokumenttien perusteella"` tai `"tarkista tämä CSS huonoista --dwc-* tokeista"` kehottavat avustajaa käyttämään työkaluja sen sijaan, että se arvaisi.

Jos avustajasi silti vastaa ilman, että se konsultoi palvelinta, asenna [webforJ AI -liitin](https://github.com/webforj/webforj-ai) - se toimittaa vastaavat Agent Skillsit, jotka kehottavat avustajaa käyttämään MCP-työkaluja automaattisesti webforJ-tehtävissä.

## UKK {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p> Miksi AI-avustaja ei käytä MCP-palvelinta? </p>
    <p> Miksi AI-avustaja ei käytä MCP-palvelinta? </p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Useimmat avustajat hakevat MCP:ltä vain, kun ajattelevat kysymyksen tarvitsevan sitä. Kaksi ratkaisua:

      1. **Asenna [webforJ AI -liitin](https://github.com/webforj/webforj-ai)**, joka yhdistää palvelimen Agent Skillsien kanssa, jotka kertovat avustajalle käyttää MCP:tä webforJ-tehtäviin.
      2. **Ole eksplisiittinen pyynnössäsi**: sisällytä "webforJ" kysymykseen, ja itsepäisissä tapauksissa sano "käytä webforJ MCP-palvelinta vastataksesi".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p> Kuinka vahvistaa MCP-yhteyden toimivan? </p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Käytä MCP-tarkastinta:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Liity sitten tarkastimessa `https://mcp.webforj.com/mcp` ja tutki käytettävissä olevia työkaluja.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p> Kuinka ilmoittaa ongelmista? </p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avaa tiketti käyttäen [webforJ MCP -ongelmapsidetta](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Liitä pyyntö, odotettu tulos ja mitä sait.
    </div>
  </AccordionDetails>
</Accordion>
<br />
