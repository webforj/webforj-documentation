---
title: MCP-palvelin
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: e51aa2e6a5a0f6c37a18c404c1104684
---
WebforJ Model Context Protocol (MCP) -palvelin liittää AI-koodin avustajat webforJ:n dokumentaatioon, API:hin, suunnittelutokeneihin ja rakennustyökaluihin. Sen sijaan, että avustaja arvailee kehyskonventioita, se kysyy palvelimelta ja saa vastauksia, jotka perustuvat todelliseen webforJ:iin.

:::tip Käytä liitintä
Ellei tiedä haluamansa olevan vain MCP-palvelin, asenna **[webforJ AI plugin](/docs/ai-tooling)** sen sijaan - se paketoi tämän palvelimen yhteen asennukseen yhdessä vastaavien [Agent Skills](/docs/ai-tooling/agent-skills) kanssa.
:::

## Mikä on MCP? {#whats-an-mcp}

Model Context Protocol on avoin standardi, joka antaa AI-avustajille mahdollisuuden kutsua ulkoisia työkaluja kysynnän mukaan. WebforJ MCP -palvelin toteuttaa tämän protokollan, jotta avustajasi voi:

- Etsiä tietoa webforJ:n dokumentaatiosta sen sijaan, että se keksii metodin nimiä
- Rakentaa uusia webforJ-projekteja virallisista Maven-archetypeista
- Tuottaa esteettömiä DWC-teemoja brändiväristä
- Lukea DWC-komponentin todellista tyylipintaa ja validoida kaikki `--dwc-*` tokenit ennen niiden päätymistä CSS:ään

:::warning AI voi silti tehdä virheitä
MCP-palvelin parantaa tarkkuutta merkittävästi, mutta AI-avustajat voivat silti tuottaa virheellistä koodia monimutkaisissa tilanteissa. Tarkista ja testaa aina generoitu koodi ennen julkaisua.
:::

## Asennus {#installation}

Koko kokemuksen saamiseksi asenna **[webforJ AI plugin](/docs/ai-tooling)** - se konfiguroi tämän palvelimen yhdessä Agent Skillsin kanssa, joita avustajasi tarvitsee sen käyttämiseen tehokkaasti.

Jos haluat vain MCP-palvelimen (ilman taitoja), ohjaa asiakas osoitteeseen `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

Suositeltu reitti Copilot CLI:llä on **[webforJ AI plugin](/docs/ai-tooling)** - se rekisteröi MCP-palvelimen puolestasi yhdellä askeleella. Raakamuotoista MCP-asennusta varten katso asiakaskohtaiset ohjeet [webforJ AI -repoilta](https://github.com/webforj/webforj-ai#clients).

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

Cursor, Kiro, Goose, Junie, Antigravity ja muut MCP-over-HTTP -asiakkaat toimivat myös - ne käyttävät vain omaa konfiguraatiomuotoaan. Katso [asiakaskohtainen asennusopas](https://github.com/webforj/webforj-ai#clients) jokaisen tarkkaa koodia varten.

## Mitä palvelin voi tehdä {#capabilities}

Kun MCP-palvelin on yhdistetty, AI-avustajasi saa seuraavat kyvyt. Mikä tahansa niistä voidaan laukaista luonnollisen kielen kysymyksellä - avustaja valitsee oikean automaattisesti.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Suunnata oikeaan webforJ-versioon</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Ennen vastauksen antamista versiota koskeviin kysymyksiin (mikä tahansa tyyliin tai API:hin liittyvä) avustaja selvittää, mikä webforJ-versio sinulla on. Se lukee `pom.xml`:n, kun se on saatavilla, ja kysyy muulloin. Jokainen seuraava vastaus on rajattu tähän versioon.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Etsi tietoja webforJ:n tietopankista</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avustaja voi kysyä koko webforJ:n tietopankista vastauksia, jotka perustuvat todelliseen kehykseen. Tulokset rajataan siihen, mistä kysyt - API-kysymykseen, oppaaseen, koodiesimerkkiin tai Kotlin DSL:ään.

      **Esimerkkikyselyt:**
      ```
      "Löydä webforJ:n Button-komponentin tapahtumankäsittelyesimerkit"

      "Miten asetetaan reititys @Route:lla webforJ:ssä?"

      "Näytä webforJ:lle suunniteltu lomakkeentarkistusnäyte"
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
      Avustaja generoi oikean Maven-archetype -komennon uudelle webforJ-sovellukselle vaatimustesi (archetype, Spring-integraatio, nimi, ryhmä) perusteella.

      **Archetypet:**
      - `hello-world` - aloitussovellus esimerkkikomponenteilla
      - `blank` - minimaalinen projektirakenne
      - `tabs` - välilehtikäyttöliittymän asettelu
      - `sidemenu` - sivunavigaation asettelu

      **Maut:**
      - `webforj` - standardi webforJ-sovellus
      - `webforj-spring` - webforJ integroituna Spring Bootiin

      **Esimerkkikyselyt:**
      ```
      "Luo webforJ-projekti nimeltä CustomerPortal käyttäen sidemenu-archetypea"

      "Generoi webforJ Spring Boot -projekti, jossa on välilehtiasettelu nimeltä Dashboard"
      ```

      :::tip Saatavilla olevat archetypet
      Koko luettelon archetypeista löydät [archetype-katalogista](/docs/building-ui/archetypes/overview).
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
      Yhden brändivärin perusteella avustaja tuottaa täydellisen DWC-teeman: ensisijaiset, onnistuneet, varoitus-, vaarallis-, tieto-, oletus- ja harmaat värit automaattisella tekstikontrastilla. Tuloste pitää sisällään tyylitiedoston sekä `@AppTheme` / `@StyleSheet` johdotuksen.

      **Esimerkkikyselyt:**
      ```
      "Generoi webforJ-teema brändiväristä #6366f1"

      "Luo esteetön teema, jossa HSL 220, 70, 50 on ensisijainen"
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
      Avustaja lukee jokaisen DWC-komponentin todellista tyylipintaa - CSS-muokkausprosessit, varjo-osat, heijastetut attribuutit ja slotit - ennen CSS:n kirjoittamista. Se voi myös luetella jokaisen DWC-tagin ja liittää webforJ:n Java-luokkien (`Button`, `TextField`) vastaavat DWC-vastineet.

      **Esimerkkikyselyt:**
      ```
      "Mitä CSS-muuttujia ja osia dwc-button tarjoaa?"

      "Näytä kaikki slotit, jotka ovat käytettävissä dwc-dialogissa"

      "Mihin DWC-tagiin webforJ:n TextField-luokka karttuu?"
      ```

      Yhdistä tämä [styling-apps agent skill](/docs/ai-tooling/agent-skills) kanssa päättäen tyylityönkulut.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Työskentele DWC-suunnittelutokenien kanssa</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avustaja voi luetella virallisen luettelon `--dwc-*` tokenista webforJ-versiollesi - palettisiemenet, sävyt, pinnat, väli, typografia, reunat - suodattamalla etuliitteiden tai alimerkkijonojen mukaan. Se myös validoi minkä tahansa CSS-, Java- tai Markdown-lähteen, jonka annat, todellista token-katalogia vastaan ja merkitsee tuntemattomat nimet ehdotetuilla korjauksilla.

      **Esimerkkikyselyt:**
      ```
      "Listaa kaikki --dwc-space-* tokenit"

      "Vahvista app.css tuntemattomien --dwc-* tokenien varalta"

      "Mitä ensisijaisia palettisävyjä on saatavilla?"
      ```

      Validointi löytää kirjoitusvirheet ja keksityt tokenit ennen kuin ne julkaistaan hiljaisina epäonnistuvina CSS:inä.
    </div>
  </AccordionDetails>
</Accordion>

## Hyvien kysymysten kirjoittaminen {#writing-good-prompts}

MCP-palvelinta konsultoidaan vain silloin, kun avustaja katsoo sen olevan relevanttia. Muutamat tavat pitävät sen aktiivisena:

- **Nimeä kehys.** Mainitse "webforJ" kysymyksessä, jotta avustaja käyttää MCP-palvelinta sen sijaan, että se turvautuu yleisiin Java-tietoihin.
- **Ole tarkka.** `"Luo webforJ-projekti nimeltä InventorySystem käyttäen sidemenu-archetype ja Spring Boot"` voittaa `"tee sovellus"`.
- **Pyydä vahvistusta.** Ilmaisuja kuten `"vahvista webforJ-dokumenttien mukaan"` tai `"tarkista tämä CSS huonoilta --dwc-* tokenilta"` rohkaisee avustajaa käyttämään työkaluja sen sijaan, että se arvaa.

Jos avustaja silti vastaa konsultoimatta palvelinta, asenna [webforJ AI plugin](https://github.com/webforj/webforj-ai) - se sisältää vastaavat Agent Skills, jotka ohjaavat avustajaa automaattisesti käyttämään MCP-työkaluja webforJ-tehtäville.

## FAQ {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Pourquoi l'assistant IA n'utilise-t-il pas le serveur MCP ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Useimmissa avustajissa on tapana käyttää MCP:tä vain silloin, kun ne kokevat kysymyksen tarvitsevan sitä. Kaksi ratkaisua:

      1. **Asenna [webforJ AI plugin](https://github.com/webforj/webforj-ai)**, joka yhdistää palvelimen Agent Skillsin kanssa, joka pyytää avustajaa käyttämään MCP:tä webforJ-tehtäville.
      2. **Ole eksplisiittinen kysymyksessäsi**: sisällytä "webforJ" kysymykseen, ja itsepäisten tapausten varalta sano "käytä webforJ MCP -palvelinta vastaukseen".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka tarkistaa MCP-yhteyden toimivuus?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Käytä MCP-inspektoria:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Yhdistä sitten inspektorissa osoitteeseen `https://mcp.webforj.com/mcp` ja tutki käytettävissä olevia työkaluja.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka ilmoittaa ongelmista?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Avaa tiketti käyttäen [webforJ MCP -ongelmapohjaa](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Liitä kysymys, odotettu tulos ja mitä sait.
    </div>
  </AccordionDetails>
</Accordion>
<br />
