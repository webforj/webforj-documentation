---
title: MCP Server
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: fb0e068ee7d7a489237e021b24a883b0
---
The webforJ Mallin Konteksti Protokolla (MCP) -palvelin liittää AI-koodausassistentit webforJ:n dokumentaatioon, API:hin, design-token-järjestelmiin ja tukiin. Sen sijaan, että arvattavalle frameworkin konventiot, avustaja kysyy palvelimelta ja saa vastauksia, jotka perustuvat oikeaan webforJ:iin.

:::tip Käytä liitännäistä
Ellet tiedä, että haluat vain MCP-palvelimen, asenna **[webforJ AI -liitännäinen](/docs/ai-tooling)** sen sijaan - se pakkaa tämän palvelimen yhteen yhteensopivien [Agent Skills](/docs/ai-tooling/agent-skills) kanssa yhden asennuksen aikana.
:::

## Mikä on MCP? {#whats-an-mcp}

Mallin Konteksti Protokolla on avoin standardi, joka mahdollistaa AI-avustajien kysyä ulkoisia työkaluja tarpeen mukaan. webforJ MCP -palvelin toteuttaa tätä protokollaa, jotta avustajasi voi:

- Etsiä asioita webforJ-dokumenteista sen sijaan, että keksiisi menetelmän nimiä
- Luoda uusia webforJ-projekteja virallisista Maven-arkkitehtuureista
- Luoda saavutettavia DWC-teemoja brändiväristä
- Lukea DWC-komponentin todellista tyylipintaa ja validoida kaikki `--dwc-*` tokenit ennen niiden päätymistä CSS:ään

:::warning AI voi silti tehdä virheitä
MCP-palvelin parantaa tarkkuutta merkittävästi, mutta AI-avustajat voivat silti tuottaa virheellistä koodia monimutkaisissa skenaarioissa. Tarkista ja testaa aina generoitu koodi ennen käyttöönottoa.
:::

## Asennus {#installation}

Täydellisen kokemuksen saamiseksi asenna **[webforJ AI -liitännäinen](/docs/ai-tooling)** - se konfiguroi tämän palvelimen yhdessä Agent Skillsin kanssa, joita avustajasi tarvitsee sen hyvään käyttöön.

Jos haluat vain MCP-palvelimen (ilman taitoja), osoita asiakas ohjelmaan `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Suositeltu polku Copilot CLI:ssä on **[webforJ AI -liitännäinen](/docs/ai-tooling)** - se rekisteröi MCP-palvelimen puolestasi yhdellä askelella. Raw MCP-setupista ilman taitoja, katso per-asiakasohjeet [webforJ AI -repo](/docs/ai-tooling).

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

Lisää `~/.gemini/settings.json`-tiedostoon:

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

Lisää `~/.codex/config.toml`-tiedostoon:

```toml
[mcp_servers.webforj-mcp]
url = "https://mcp.webforj.com/mcp"
```

</TabItem>
</Tabs>

### Muut asiakkaat {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity ja muut MCP-over-HTTP -asiakkaat toimivat myös - ne käyttävät vain omaa konfiguraatioformaattiaan. Katso [per-asiakas asennusopas](https://github.com/webforj/webforj-ai#clients) jokaisen tarkka pätkä.

## Mitä palvelin voi tehdä {#capabilities}

Kun MCP-palvelin on yhdistetty, AI-avustajasi saa seuraavat kyvyt. Mikä tahansa niistä voidaan laukaista luonnollisen kielen pyynnöllä - avustaja valitsee automaattisesti oikean.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Suunnata oikeaan webforJ-versioon</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ennen kuin vastaa versioon liittyviin kysymyksiin (kaikki tyylitykseen tai API:iin liittyvät), avustaja selvittää, mikä webforJ-versio sinulla on. Se lukee `pom.xml`-tiedostosta, kun se on saatavilla ja muuten kysyy sinulta. Jokainen seuraava vastaus kohdistuu kyseiseen versioon.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Etsi asioita webforJ-tietopankista</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avustaja voi kysyä koko webforJ-tietopankista vastauksia, jotka perustuvat oikeaan kehykseen. Tulokset rajaavat sen, mistä kysyt - API-kysymyksestä, oppaasta, koodinäytteestä tai Kotlin DSL:stä.

      **Esimerkki pyynnöistä:**
      ```
      "Etsi webforJ:n painike komponentin tapahtumankäsittelyesimerkit"

      "Kuinka asetan reitityksen @Route:ssa webforJ:ssa?"

      "Näytä minulle webforJ:n lomakkeiden validointiesimerkki"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Rakenna uusi webforJ-projekti</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avustaja luo oikean Maven-arkkitehtuuri-komennon uudelle webforJ-sovellukselle vaatimustesi perusteella (arkkitehtuuri, Spring-integraatio, nimi, ryhmä).

      **Arkkitehtuurit:**
      - `hello-world` - käynnistysapp, jossa on esimerkkikomponentteja
      - `blank` - minimaalinen projektirakenne
      - `tabs` - välilehtirajaliittymät
      - `sidemenu` - sivunavigaatiorajaliittymät

      **Maut:**
      - `webforj` - normaali webforJ-sovellus
      - `webforj-spring` - webforJ, joka on yhdistetty Spring Bootin kanssa

      **Esimerkki pyynnöistä:**
      ```
      "Luo webforJ-projekti nimeltä CustomerPortal käyttäen sidemenu-arkkitehtuuria"

      "Tuota webforJ Spring Boot -projekti, jossa on tabs-rakenne nimeltä Dashboard"
      ```

      :::tip Saatavilla olevat arkkitehtuurit
      Täydellisen luettelon arkkitehtuureista katso [arkkitehtuuri-katalogista](/docs/building-ui/archetypes/overview).
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
      Yhden brändivärin perusteella avustaja tuottaa täydellisen DWC-teeman: pääväri, onnistuminen, varoitus, vaara, info, oletus ja harmaa paletti automaattisella tekstikontrastilla. Tuotteen mukana tulee tyylitiedosto sekä `@AppTheme` / `@StyleSheet` -kytkentä.

      **Esimerkki pyynnöistä:**
      ```
      "Generoi webforJ-teema brändiväristä #6366f1"

      "Luo saavutettava teema HSL 220, 70, 50 päävärinä"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Stailaa DWC-komponentit oikein</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avustaja lukee jokaisen DWC-komponentin todellista tyylipintaa - CSS-muokattuja ominaisuuksia, varjopartti, heijastetut ominaisuudet ja slotit - ennen kuin kirjoittaa mitään CSS:ää. Se voi myös luetella jokaisen DWC-tagin ja ratkaista webforJ Java -luokkien (`Button`, `TextField`) vastaavuuksia DWC:n kanssa.

      **Esimerkki pyynnöistä:**
      ```
      "Mitä CSS-muuttujia ja osia dwc-button paljastaa?"

      "Näytä minulle kaikki slotit, jotka ovat saatavilla dwc-dialogilla"

      "Mikä DWC-tag vastaa webforJ:n TextField-luokkaa?"
      ```

      Yhdistä tämä [styling-apps agent skill](/docs/ai-tooling/agent-skills) kanssa kattavaan stailaustyöhön.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Työskentele DWC-design-tokenien kanssa</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avustaja voi listata virallisen luettelon `--dwc-*` tokenista webforJ-versiollesi - palettiseemojen, sävyjen, pintojen, välisten etäisyyksien, typografian, reunojen - suodattamalla etuliitteen tai osajoukon mukaan. Se validoi myös kaiken CSS:n, Java:n tai Markdownin, jota annat sen todellisen token-katalogin mukaan ja merkitsee tuntemattomat nimet ehdotetuilla korjauksilla.

      **Esimerkki pyynnöistä:**
      ```
      "Listaa kaikki --dwc-space-* tokenit"

      "Validoi app.css tuntemattomista --dwc-* tokenista"

      "Mitkä pääpaletti sävyt ovat saatavilla?"
      ```

      Validointi poimii kirjoitusvirheet ja keksityt tokenit ennen kuin ne pääsevät hiljaisesti epäonnistuvaksi CSS:ksi.
    </div>
  </AccordionDetails>
</Accordion>

## Hyvien pyyntöjen kirjoittaminen {#writing-good-prompts}

MCP-palvelinta konsultoidaan vain, kun avustajasi katsoo sen olevan relevantti. Muutamat tavat pitävät sen sitoutuneena:

- **Nimeä kehys.** Mainitse "webforJ" pyynnössä, jotta avustaja hakee MCP-palvelinta sen sijaan, että se käyttäisi yleistä Java-tietämystään.
- **Ole tarkka.** `"Luo webforJ-projekti nimeltä InventorySystem käyttämällä sidemenu-arkkitehtuuria ja Spring Bootia"` voittaa `"tee sovellus"`.
- **Pyydä vahvistusta.** Fraasit kuten `"vahvista webforJ-dokumenttien perusteella"` tai `"tarkista tämä CSS huonoista --dwc-* tokenista"` ohjaavat avustajaa käyttämään työkaluja sen sijaan, että se arvaisi.

Jos avustajasi silti vastaa ilman palvelimen konsultointia, asenna [webforJ AI -liitännäinen](https://github.com/webforj/webforj-ai) - se toimittaa vastaavat Agent Skillsit, jotka ohjaavat avustajaa käyttämään MCP-työkaluja automaattisesti webforJ-tehtävissä.

## UKK {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Miksi AI-avustaja ei käytä MCP-palvelinta?</p>
    <p>Miksi AI-avustaja ei käytä MCP-palvelinta?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Useimmat avustajat ottavat MCP:n käyttöön vain, kun he ajattelevat kysymyksen tarvitsevan sitä. Kaksi ratkaisua:

      1. **Asenna [webforJ AI -liitännäinen](https://github.com/webforj/webforj-ai)**, joka yhdistää palvelimen Agent Skilleihin, jotka kertovat avustajalle käyttää MCP:tä webforJ-tehtävissä.
      2. **Ole eksplisiittinen pyynnössäsi**: sisällytä "webforJ" kysymykseen, ja hankalille tapauksille sano "käytä webforJ MCP -palvelinta vastaukseen".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka vahvistaa, että MCP-yhteys toimii?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Käytä MCP-inspektoria:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Yhdistä sitten inspektorissa `https://mcp.webforj.com/mcp` ja tutki käytettävissä olevia työkaluja.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka raportoida ongelmista?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avaa tiketti käyttäen [webforJ MCP -ongelmatunnistetta](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Liitä pyyntö, odotettu tulos ja mitä sait.
    </div>
  </AccordionDetails>
</Accordion>
<br />
