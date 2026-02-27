---
title: MCP Server
sidebar_position: 5
_i18n_hash: a45888cf39bbbce0002177da8fe95eba
---
webforJ Model Context Protocol (MCP) -palvelin tarjoaa AI-avustajille suoran pääsyn virallisiin webforJ-dokumentaatioihin, vahvistettuihin koodiesimerkkeihin ja kehykselle spesifisiin kaavoihin, mikä mahdollistaa tarkempien vastausten ja automaattisen projektin luomisen erityisesti webforJ-kehitykselle.

## Mikä on MCP?

Model Context Protocol on avointa standardia, joka mahdollistaa AI-avustajien yhdistämisen ulkoisiin työkaluihin ja dokumentaatioon. webforJ MCP -palvelin toteuttaa tätä protokollaa tarjoten:

- **Tietohaun** - Luonnollisen kielen haku webforJ-dokumentaatiossa, koodiesimerkeissä ja kaavoissa
- **Projektin luomisen** - Luo webforJ-sovelluksia virallisten mallien avulla oikealla rakenteella
- **Teemojen luomisen** - Luo saavutettavia CSS-teemoja webforJ:n suunnittelukaavojen mukaisesti

## Miksi käyttää MCP:tä?

Vaikka AI-koodaavustajat ovat hyviä vastaamaan peruskysymyksiin, ne kamppailevat monimutkaisten webforJ-spesifisten kysymysten kanssa, jotka ulottuvat useisiin dokumentaatiosiltoihin. Ilman suoraa pääsyä virallisiin lähteisiin ne voivat:

- Luoda metodeja, joita ei ole webforJ:ssä
- Viitata vanhentuneisiin tai virheellisiin API-kaavoihin
- Tarjota koodia, joka ei käänny
- Sekoitella webforJ-syntaksia muiden Java-kehysten kanssa
- Väärinymmärtää webforJ-spesifisiä kaavoja

MCP-integraation avulla AI-vastaukset ovat sidottuja todelliseen webforJ-dokumentaatioon, koodiesimerkkeihin ja kehyskaavoihin, tarjoten varmennettavia vastauksia ja suoria linkkejä virallisiin lähteisiin syvempää tutkimusta varten.

:::warning AI voi silti tehdä virheitä
Vaikka MCP parantaa tarkkuutta merkittävästi tarjoamalla pääsyn virallisiin webforJ-resursseihin, se ei takaa täydellistä koodin luomista. AI-avustajat voivat silti tehdä virheitä monimutkaisissa tilanteissa. Tarkista aina luotu koodi ja testaa kattavasti ennen käyttöä tuotannossa.
:::

## Asennus

webforJ MCP -palvelin isännöi osoitteessa `https://mcp.webforj.com` ja siinä on kaksi päätepistettä:

- **MCP-päätepiste** (`/mcp`) - Claude, VS Code, Cursor
- **SSE-päätepiste** (`/sse`) - Vanhoille asiakkaille

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

Lisää tämä konfiguraatio VS Code settings.json -tiedostoon:

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
<TabItem value="cursor" label="Cursor">

Lisää tämä konfiguraatio Cursor-asetuksiisi:

```json
"mcpServers": {
  "webforj-mcp": {
    "url": "https://mcp.webforj.com/mcp"
  }
}
```

</TabItem>
<TabItem value="claude-code" label="Claude Code" default>

Käytä Claude CLI -komentoa rekisteröidäksesi palvelimen:

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

Tämä konfiguroi MCP-palvelimen automaattisesti Claude Code -ympäristössäsi.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Lisää tämä palvelin Claude Desktop -asetusten Integraatiot-paneelissa:

1. Avaa Claude Desktop ja siirry asetuksiin
2. Napsauta sivupalkissa "Integraatiot"
3. Napsauta "Lisää integraatio" ja liitä URL-osoite: `https://mcp.webforj.com/mcp`
4. Seuraa asennusopasta konfiguraation täydentämiseksi

Yksityiskohtaisia ohjeita varten katso [virallista integraatio-opasta](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Lisää tämä palvelinmääritys Windsurf MCP asetuksiisi:

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "serverUrl": "https://mcp.webforj.com/sse"
    }
  }
}
```

</TabItem>
</Tabs>

## Saatavilla olevat työkalut

Työkalut ovat erikoistuneita toimintoja, joita MCP-palvelin tarjoaa AI-avustajille. Kun esität kysymyksen tai teet pyynnön, AI voi kutsua näitä työkaluja hakemaan dokumentaatiota, luomaan projekteja tai luomaan teemoja. Jokainen työkalu hyväksyy tiettyjä parametreja ja palauttaa jäsenneltyä dataa, joka auttaa AI:ta tarjoamaan tarkkaa ja kontekstuaalista apua.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Hae dokumentaatiota ja esimerkkejä
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Tämä työkalu tarjoaa semanttisen hakutoiminnallisuuden koko webforJ-dokumentaatioekosysteemissä. Se ymmärtää kontekstin ja suhteen eri kehyskonseptien välillä, palauttaen olennaisia dokumentaatiosektioita, API-viittauksia ja toimivia koodiesimerkkejä.

      **Esimerkkikyselyt:**
      ```
      "Hae webforJ-dokumentaatiosta Button-komponenttia ikoniesimerkeillä"

      "Etsi webforJ-lomakevalidointikaavoja uusimmasta dokumentaatiosta"

      "Näytä nykyinen webforJ-reititysasetukset @Route-annotaatiolla"

      "Hae webforJ-dokumenteista FlexLayout-vastauksellisia suunnittelukaavoja"

      "Etsi webforJ-verkkokomponentti-integraatio virallisesta dokumentaatiosta"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - Luo uusia webforJ-projekteja  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Rakentaa täydellisiä webforJ-sovelluksia virallisten Maven-archetypejen avulla. Työkalu luo standardoidun projektikansiorakenteen ja sisältää aloituskoodin valitun mallin perusteella. Luodut projektit sisältävät valmiin rakennusjärjestelmän, resurssikansiot ja konfiguraatiotiedostot välitöntä kehittämistä ja käyttöönottoa varten.

      **Esimerkkikehotteet:**
      ```
      "Luo webforJ-projekti nimeltä CustomerPortal hello-world-archetypen avulla"

      "Generoi webforJ Spring Boot -projekti, jossa on välilehtirakenne nimeltä Dashboard"

      "Luo uusi webforJ-sovellus sidemenu-archetypelle AdminPanel-projektille"

      "Generoi tyhjää webforJ-projektia nimeltä TestApp, jossa on com.example groupId"

      "Luo webforJ-projekti InventorySystem käyttäen sidemenu-archetypea Spring Bootin kanssa"
      ```

      Kun käytät tätä työkalua, voit valita useista projektimalleista:

      **Archetypen** (projektimallit):
      - `hello-world` - Perussovellus, jossa on esimerkkikomponentteja webforJ-ominaisuuksien esittelemiseksi
      - `blank` - Minimimallin rakenne aloittamista varten tyhjältä
      - `tabs` - Esirakennettu välilehtikäyttöliittymän asettelu moninäkymäsovelluksille
      - `sidemenu` - Sivunavigaatiovalikko asettelulle hallintapaneeleille tai koontinäytöille

      **Muut** (kehysintegraatio):
      - `webforj` - Standardi webforJ-sovellus
      - `webforj-spring` - webforJ integroitu Spring Bootin kanssa riippuvuusten injektoimiseksi ja yritysominaisuuksien tarjoamiseksi

      :::tip Saatavilla olevat archetypet
      webforJ:ssä on useita ennalta määrättyjä archetypeja, jotka auttavat sinua pääsemään nopeasti alkuun. Täydellisen luettelon saatavilla olevista archetypeista katso [archetype-katalogista](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - Luo saavutettavia CSS-teemoja
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Generoi webforJ-teemakonfiguraatioita käyttämällä [DWC HueCraft](https://huecraft.dwc.style/). Työkalu luo täydelliset CSS-muuttujakokoelmat ensisijaisille, toissijaisille, menestysohjeille, varoituksille, vaarallisille ja neutraaleille värivariantteille.

      **Esimerkkipyynnöt:**
      ```
      "Generoi webforJ-teema, jossa on HSL 220, 70, 50 ensisijaisena värinä meille yritysbrändissä"

      "Luo webforJ-saavutettava teema nimeltä 'ocean' pääväri #0066CC"

      "Generoi webforJ-teema käyttäen brändiväriämme #FF5733"

      "Luo webforJ-teema, jossa on HSL 30, 100, 50 nimeltä 'sunset' sovelluksemme varten"

      "Generoi saavutettava webforJ-teema, jossa on ensisijainen RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Saatavilla olevat kehotteet {#available-prompts}

Kehoteet ovat esikonfiguroituja AI-ohjeita, jotka yhdistävät useita työkaluja ja työprosesseja yleisiin tehtäviin. Ne ohjaavat AI:ta tiettyjen vaiheiden ja parametrien kautta luodakseen luotettavia ja toistettavia tuloksia jokaiselle tuetulle työprosessille.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Luo ja aja webforJ-sovellus
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumentit:**
      - `appName` (pakollinen) - Sovelluksen nimi (esim. MyApp, TodoList, Dashboard)
      - `archetype` (pakollinen) - Valitse: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (valinnainen) - Aja kehityspalvelin automaattisesti (kyllä/ei)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Generoi webforJ-teema ensisijaisesta väriä
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumentit:**
      - `primaryColor` (pakollinen) - Väri heksadesimaalimuodossa (#FF5733), rgb (255,87,51) tai hsl (9,100,60) muodossa
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Edistynyt haku autonomisella ongelmanratkaisulla
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Kehote konfiguroi AI:n:

      1. Etsi tietopohjaa laajasti
      2. Kirjoita täydellistä tuotantovalmiina koodia
      3. Käännä projekti käyttäen `mvn compile` varmistaaksesi, että rakennusvirheitä ei ole
      4. Korjaa virheitä iteroivasti, kunnes kaikki toimii
    </div>
  </AccordionDetails>
</Accordion>

### Kuinka käyttää kehotteita

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code ja Claude Code">

1. Kirjoita <kbd>/</kbd> keskusteluun nähdäksesi saatavilla olevat kehotteet
2. Valitse kehotte tutustumisvalikosta
3. Täytä tarvittavat parametrit, kun sinua pyydetään

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Napsauta **+** (plus) kuvaketta kehotteiden syöttöalueella
2. Valitse menuista **"Lisää webforJ:stä"**
3. Valitse haluamasi kehotte (esim. `create-app`, `create-theme`, `search-webforj`)
4. Claude pyytää sinua syöttämään tarvittavat argumentit
5. Täytä parametrit pyydettäessä

:::tip Vahvista, että MCP on kytketty
Etsi työkalukuvake syöttöalueen alakulmasta vahvistaaksesi, että webforJ MCP -palvelin on kytketty.
:::

</TabItem>
</Tabs>

## Parhaat käytännöt

Saadaksesi tarkimman ja ajantasaisimman webforJ-avustuksen, seuraa näitä ohjeita, jotta voit hyödyntää MCP-palvelimen ominaisuuksia täydellisesti.

### MCC-palvelimen käytön varmistaminen

AI-mallit saattavat ohittaa MCP-palvelimen, jos ne uskovat tietävänsä vastauksen jo. Varmistaaksesi, että MCP-palvelinta todella käytetään:

- **Ole eksplisiittinen webforJ:stä**: Mainitse aina "webforJ" kysymyksesi yhteydessä laukaistaksesi kehyskohtaisia hakuja
- **Pyydä ajankohtaista tietoa**: Sisällytä lauseita kuten "uusin webforJ-dokumentaatio" tai "nykyiset webforJ-kaavat"
- **Pyydä varmennettuja esimerkkejä**: Pyydä "toimivia webforJ-koodiesimerkkejä" pakottaaksesi dokumentoinnin haun
- **Viittaa erityisiin versioihin**: Mainitse webforJ-versionisi (esim. "webforJ `25.02`") saadaksesi tarkkoja tuloksia

### Kirjoita tarkkoja kehotteita

**Hyviä esimerkkejä:**
```
"Hae webforJ-dokumentaatiosta Button-komponentin tapahtumakäsittely esimerkkien kanssa"

"Luo webforJ-projekti nimeltä InventorySystem käyttäen sidemenu-archeptypea Spring Bootin kanssa"

"Generoi webforJ-teema, jossa HSL 220, 70, 50 on ensisijainen väri yritysbrändille"
```

**Huonoja esimerkkejä:**
```
"Kuinka napit toimivat"

"Tehdään sovellus"

"Tehdään se siniseksi"
```

### Pakota MCP-työkalun käyttö

Jos AI antaa yleisiä vastauksia ilman MCP-palvelimen käyttöä:

1. **Pyydä eksplisiittisesti**: "Käytä webforJ MCP -palvelinta etsimään `[kysymys]`"
2. **Kysy dokumentaatioviittauksia**: "Etsi webforJ-dokumenteista kuinka `[kysymys]`"
3. **Pyydä varmennusta**: "Vahvista tämä ratkaisu webforJ-dokumentaation mukaan"
4. **Ole kehyskohtainen**: Mainitse aina "webforJ" kysymyksissäsi

## AI-kustomointi {#ai-customization}

Määritä AI-avustajasi käyttämään automaattisesti MCP-palvelinta ja noudattamaan webforJ:n parhaita käytäntöjä. Lisää projektikohtaisia ohjeita, jotta AI-avustajasi käyttävät aina MCP-palvelinta, noudattavat webforJ:n dokumentaatiostandardeja ja tarjoavat tarkkoja, ajantasaisia vastauksia, jotka vastaavat tiimisi vaatimuksia.

### Projektin konfiguraatiotiedostot

- **VS Code ja Copilot** -luo `.github/copilot-instructions.md`
- **Claude Code** -luo `CLAUDE.md` projektin juureen

Lisää seuraava luomasi markdown-tiedosto:
```markdown
## Käytä webforJ MCP -palvelinta vastataksesi kaikkiin webforJ-kysymyksiin

- Kutsu aina "webforj-knowledge-base" työkalua hakemaan kysymykseen liittyvää dokumentaatiota
- Vahvista kaikki API-allekirjoitukset virallisen dokumentaation mukaisesti
- Älä koskaan oleta, että metodien nimiä tai parametreja on olemassa ilman tarkistamista

Varmista aina, että koodi kääntyy komennolla `mvn compile` ennen ehdotusta.
```

## UKK

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Miksi AI ei käytä webforJ MCP -palvelinta?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Useimmat AI-avustajat tarvitsevat eksplisiittisiä ohjeita käyttääkseen MCP-palvelimia. Määritä AI-asiakasi ohjeiden avulla [AI-kustomoinnissa](#ai-customization). Ilman näitä ohjeita AI-avustajat saattavat palata koulutustietoihinsa sen sijaan, että kysyisivät MCP-palvelimelta.

      **Nopea ratkaisu:**
      Sisällytä "käytä webforJ MCP" kehotteeseesi tai luo tarvittava konfiguraatiotiedosto (`.github/copilot-instructions.md` tai `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka varmistaa, että MCP-yhteys toimii</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Käytä MCP-inspectoria debugataksesi yhteyksiä:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Odota viestiä: `🔍 MCP Inspector on käynnissä osoitteessa http://127.0.0.1:6274` (portti saattaa vaihdella)

      Sitten inspektorissa:
      1. Syötä MCP-palvelimen URL-osoite: `https://mcp.webforj.com/mcp`
      2. Napsauta "Yhdistä" muodostaaksesi yhteyden
      3. Näe saatavilla olevat työkalut ja testauskyselyt
      4. Seuraa pyyntö-/vastauslokit debugataksesi
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Onko MCP- ja SSE-päätepisteiden välillä eroa?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      webforJ MCP -palvelin tarjoaa kaksi päätepistettä:

      - **MCP-päätepiste** (`/mcp`) - Moderni protokolla Claudea, VS Codea, Cursoria varten
      - **SSE-päätepiste** (`/sse`) - Server-Sent Events vanhoille asiakkaille, kuten Windsurf

      Useimpien käyttäjien tulisi käyttää MCP-päätepistettä. Käytä vain SSE:ta, jos asiakas ei tue standardia MCP-protokollaa.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Onko mahdollista käyttää MCP-palvelinta ilman konfiguraatiotiedostoja?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Kyllä, mutta se ei ole suositeltavaa. Ilman konfiguraatiotiedostoja sinun on manuaalisesti kehotettava AI:tä käyttämään MCP-palvelinta jokaisessa keskustelussa. Konfiguraatiotiedostot ohjeistavat automaattisesti AI:ta käyttämään MCP-palvelinta jokaisessa vuorovaikutuksessa, joten sinun ei tarvitse toistaa ohjeita joka kerta.

      **Manuaalinen lähestymistapa:**
      Aloita kehotteet sanoin: "Käytä webforJ MCP -palvelinta..."

      **Vaihtoehtoisesti: Käytä esikonfiguroituja kehotteita**
      MCP-palvelin tarjoaa kehotteita, jotka toimivat ilman konfiguraatiotiedostoja:
      - `/create-app` - Generoi uusia webforJ-sovelluksia
      - `/create-theme` - Luo saavutettavia CSS-teemoja
      - `/search-webforj` - Edistynyt dokumentaation haku

      Katso [Saatavilla olevat kehotteet](#available-prompts) lisätietoja varten.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka osallistua tai raportoida ongelmia</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Raportoi ongelmat:** [webforJ MCP Ongelmatunnusmalli](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **Yleiset ongelmat, joita raportoida:** 
      - Vanha dokumentaatio hakutuloksissa
      - Puuttuvat API-metodit tai komponentit
      - Virheelliset koodiesimerkit
      - Työkalun suoritusvirheet

      Sisällytä kysymyksesi, odotettu tulos ja todellinen tulos, kun raportoit ongelmia.
    </div>
  </AccordionDetails>
</Accordion>
<br />
