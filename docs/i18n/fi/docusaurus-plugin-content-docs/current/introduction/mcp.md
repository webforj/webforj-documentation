---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: cfe1c4447876aff3ab7ba15b26966cba
---
WebforJ Mallin Kontekstiprotokolla (MCP) palvelin tarjoaa AI-avustajille suoran pääsyn viralliseen webforJ-dokumentaatioon, vahvistettuihin koodiesimerkkeihin ja kehyskohtaisiin malleihin, mikä mahdollistaa tarkempien vastausten antamisen ja automatisoidun projektin luomisen erityisesti webforJ-kehitystä varten.

## Mikä on MCP?

Mallin Kontekstiprotokolla on avoin standardi, joka mahdollistaa AI-avustajien yhteyden ulkoisiin työkaluihin ja dokumentaatioon. WebforJ MCP -palvelin implementoi tämän protokollan tarjotakseen:

- **Tietohaku** - Luonnollisen kielen haku webforJ-dokumentaatiossa, koodiesimerkeissä ja malleissa
- **Projektin luonti** - Luo webforJ-sovelluksia virallisista malleista oikealla rakenteella
- **Teeman luonti** - Generoi esteettömiä CSS-teemoja webforJ-mallien mukaisesti

## Miksi käyttää MCP:tä?

Vaikka AI-koodausavustajat ovat erinomaisia peruskysymyksiin vastaamisessa, ne kamppailevat monimutkaisissa webforJ-erityiskysymyksissä, jotka ulottuvat useisiin dokumentaatio-osioihin. Ilman suoraa pääsyä virallisiin lähteisiin ne voivat:

- Luoda menetelmiä, joita ei ole olemassa webforJ:ssä
- Viitata vanhentuneisiin tai virheellisiin API-malleihin  
- Antaa koodia, joka ei käänny
- Sekoitella webforJ-syntaksia muiden Java-kehysten kanssa
- Ymmärtää väärin webforJ-erityisiä malleja

MCP-integraation avulla AI-vastaukset ankkuroidaan todelliseen webforJ-dokumentaatioon, koodiesimerkkeihin ja kehysmalleihin, tarjoten varmennettuja vastauksia ja suoria linkkejä virallisiin lähteisiin syvempää tutkimusta varten.

:::warning AI voi silti tehdä virheitä
Vaikka MCP parantaa tarkkuutta tarjoamalla pääsyn virallisiin webforJ-resursseihin, se ei takaa täydellistä koodin luontia. AI-avustajat voivat silti tehdä virheitä monimutkaisissa tilanteissa. Varmista aina luotu koodi ja testaa huolellisesti ennen käyttöä tuotannossa.
:::

## Asennus

WebforJ MCP -palvelin on hostattu osoitteessa `https://mcp.webforj.com` kahdella päätepisteellä:

- **MCP-päätepiste** (`/mcp`) - Claudea, VS Codea, Cursoria varten
- **SSE-päätepiste** (`/sse`) - Perinteisiä asiakkaita varten

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

Lisää tämä konfigurointi VS Code -settings.json -tiedostoon:

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

Lisää tämä konfigurointi Cursor-asetuksiisi:

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

Lisää tämä palvelin käytön integrointipaneelista Claude Desktop -asetuksissa:

1. Avaa Claude Desktop ja siirry Asetuksiin
2. Napsauta "Integraatiot" sivupalkissa
3. Napsauta "Lisää integraatio" ja liitä URL: `https://mcp.webforj.com/mcp`
4. Seuraa asetustyökalua lopettaaksesi konfiguroinnin

Yksityiskohtaisia ohjeita varten katso [virallista integraatio-opasta](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Lisää tämä palvelin konfigurointi Windsurf MCP -asetuksiisi:

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

Työkalut ovat erikoistuneita toimintoja, joita MCP-palvelin tarjoaa AI-avustajille. Kun kysyt kysymyksen tai teet pyynnön, AI voi kutsua näitä työkaluja hakiakseen dokumentaatiota, luodakseen projekteja tai luodakseen teemoja. Jokainen työkalu hyväksyy tietyt parametrit ja palauttaa jäsenneltyä dataa, mikä auttaa AI:tä tarjoamaan tarkkaa, kontekstitietoista apua.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Hae dokumentaatiota ja esimerkkejä
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Tämä työkalu tarjoaa semanttisia hakumahdollisuuksia koko webforJ-dokumentaatioekosysteemin läpi. Se ymmärtää kontekstin ja suhteet eri kehyskonsepteihin, palauttaen asiaankuuluvia dokumentaatiosektioita, API-viittauksia ja toimivia koodiesimerkkejä.

      **Esimerkkikysymykset:**
      ```
      "Etsi webforJ-dokumentaatiosta Painike-komponentin kuvakkeiden kanssa esimerkkejä"

      "Löydä webforJ-lomakkeen validointimalleja uusimmasta dokumentaatiosta"

      "Näytä nykyinen webforJ-reitityskonfiguraatio @Route-annotaatiolla"

      "Etsi webforJ-dokumenteista FlexLayoutin responsiivisia suunnittelumalleja"

      "Löydä webforJ-verkkokomponentin integraatio virallisesta dokumentaatiosta"
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
      Luo täydellisiä webforJ-sovelluksia virallisten Maven-archetypien avulla. Työkalu luo standardoidun projektikansiorakenteen ja sisältää aloituskoodia valitun mallin mukaan. Luodut projektit sisältävät valmiin build-järjestelmän, resurssikansiot ja konfigurointitiedostot välitöntä kehitystä ja käyttöönottoa varten.

      **Esimerkkikehotteet:**
      ```
      "Luo webforJ-projekti nimeltä CustomerPortal käyttäen hello-world-archetypia"

      "Generoi webforJ Spring Boot -projekti, jossa on välilehtinäyttö nimeltä Dashboard"

      "Luo uusi webforJ-sovellus, jossa on sivupalkkiarchetypia AdminPanel-projektille"

      "Generoi webforJ tyhjät projekti nimeltä TestApp, jossa on com.example groupId"

      "Luo webforJ-projekti InventorySystem käyttäen sivupalkkiaarchetypia Spring Bootin kanssa"
      ```

      Tämän työkalun käytön yhteydessä voit valita useita projektimalleja:

      **Archetyypit** (projektimallit):
      - `hello-world` - Perussovellus, jossa on esimerkkikomponentteja webforJ-ominaisuuksien esittelyyn
      - `blank` - Minimiprojektirakenne, josta aloittaa alusta
      - `tabs` - Esirakennettu välilehtinäyttöasettelu moninäkymäsovelluksiin
      - `sidemenu` - Sivupalkkinahtavalikko asennuspaneeleille tai hallintapaneeleille

      **Maut** (kehysintegraatio):
      - `webforj` - Vakiovarusteinen webforJ-sovellus
      - `webforj-spring` - webforJ, joka on integroitu Spring Bootin kanssa riippuvuuksien injektoimista ja yritysominaisuuksia varten

      :::tip Saatavilla olevat archetypit
      webforJ tulee useiden ennalta määritettyjen archetypien kanssa, jotta voit aloittaa nopeasti. Täydellisen listan saatavilla olevista archetypeista löydät [archetyyppiluettelosta](../building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - Luo esteettömiä CSS-teemoja
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Generoi webforJ-teeman konfiguraatiot käyttäen [DWC HueCraft](https://huecraft.dwc.style/). Työkalu luo täydelliset CSS-muuttujasetukset ensisijaiselle, toissijaiselle, onnistumisen, varoituksen, vaaran ja neutraalisen värivaihtoehdolle.

      **Esimerkkipyynnöt:**
      ```
      "Generoi webforJ-teema, jossa HSL 220, 70, 50 on ensisijainen väri yritysbrändillemme"

      "Luo webforJ esteetön teema nimeltä 'ocean' ensisijaisella värillä #0066CC"

      "Generoi webforJ-teema käyttäen meidän brändiväriämme #FF5733"

      "Luo webforJ-teema, jossa HSL 30, 100, 50 on nimeltään 'sunset' sovelluksemme varten"

      "Generoi esteetön webforJ-teema ensisijaisella RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Saatavilla olevat kehotteet {#available-prompts}

Kehotteet ovat esikonfiguroituja AI-ohjeita, jotka yhdistävät useita työkaluja ja työprosesseja tavallisiin tehtäviin. Ne ohjaavat AI:tä spesifisten vaiheiden ja parametritietojen läpi, jotta saavutetaan luotettavia ja toistettavia tuloksia jokaiselle tuetulle työnkululle.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Luo ja aja webforJ-sovellus
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumentit:**
      - `appName` (vaaditaan) - Sovelluksen nimi (esim., MyApp, TodoList, Dashboard)
      - `archetype` (vaaditaan) - Valitse: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (valinnainen) - Aja kehityspalvelin automaattisesti (kyllä/ei)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Generoi webforJ-teema ensisijaisesta väri
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumentit:**
      - `primaryColor` (vaaditaan) - Väri heksadesimaaleissa (#FF5733), rgb (255,87,51) tai hsl (9,100,60) muodossa
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Edistynyt haku itseohjaavalla ongelmanratkaisulla
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Kehote konfiguroi AI:n:

      1. Etsimään tietopankkia laajasti
      2. Kirjoittamaan täydellä tuotantovalmiin koodin
      3. Kääntämään projektin käyttäen `mvn compile` varmistaakseen, ettei käännösvirheitä ole
      4. Korjaamaan virheitä vaiheittain, kunnes kaikki toimii
    </div>
  </AccordionDetails>
</Accordion>

### Kuinka käyttää kehotteita

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code ja Claude Code">

1. Kirjoita <kbd>/</kbd> keskustelussa nähdäksesi saatavilla olevat kehotteet
2. Valitse kehotteita pudotusvalikosta
3. Täytä vaaditut parametrit, kun sinua pyydetään

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Napsauta **+** (plus) -ikonia kehotteen syöttöalueella
2. Valitse **"Lisää webforJ:stä"** valikosta
3. Valitse haluamasi kehotteet (esim., `create-app`, `create-theme`, `search-webforj`)
4. Claude pyytää sinua syöttämään vaaditut argumentit
5. Täytä parametrit pyynnön mukaan

:::tip Varmista, että MCP on kytketty
Etsi työkalun kuvake syöttöalueen oikeassa alakulmassa vahvistaaksesi, että webforJ MCP -palvelin on kytketty.
:::

</TabItem>
</Tabs>

## Paras käytäntö

Saadaksesi tarkimmat ja ajantasaisimmat webforJ-apuvälineet, noudata näitä ohjeita hyödyntääksesi MCP-palvelimen ominaisuuksia.

### Varmista MCP-palvelimen käyttö

AI-mallit voivat ohittaa MCP-palvelimen, jos ne uskovat jo tietävänsä vastauksen. Varmistaaksesi, että MCP-palvelinta todella käytetään:

- **Ole selkeä webforJ:stä**: Mainitse aina "webforJ" kysymyksessäsi, jotta se laukaisee kehyskohtaisia hakuja
- **Pyydä ajankohtaista informaatiota**: Sisällytä ilmauksia kuten "uusin webforJ-dokumentaatio" tai "nykyiset webforJ-mallit"
- **Pyydä varmennettuja esimerkkejä**: Pyydä "toimivia webforJ-koodiesimerkkejä" pakottaaksesi dokumentaation hakuun
- **Viittaa spesifisiin versioihin**: Mainitse webforJ-versio (esim., "webforJ `25.02`"), jotta saat tarkkoja tuloksia

### Kirjoita spesifisiä kehotteita

**Hyviä esimerkkejä:**
```
"Etsi webforJ-dokumentaatiosta Painike-komponentin tapahtumankäsittelyä esimerkkien kanssa"

"Luo webforJ-projekti nimeltä InventorySystem käyttäen sivupalkkiaarchetypia Spring Bootin kanssa"

"Generoi webforJ-teema, jossa HSL 220, 70, 50 on ensisijainen väri yritysbrändille"
```

**Huonoja esimerkkejä:**
```
"Kuinka napit toimivat"

"Maakaa sovellus"

"Maakaa se siniseksi"
```

### Pakota MCP-työkalun käyttö

Jos AI antaa yleisiä vastauksia ilman MCP-palvelimen käyttöä:

1. **Pyydä selkeästi**: "Käytä webforJ MCP -palvelinta etsimään `[kysymys]`"
2. **Pyydä dokumentaatioviittauksia**: "Etsi webforJ-dokumenteista, kuinka `[kysymys]`"
3. **Pyydä varmennusta**: "Varmista tämä ratkaisu webforJ-dokumentaatiota vasten"
4. **Ole kehyskohtainen**: Sisällytä aina "webforJ" kysymyksiisi

## AI:n räätälöinti {#ai-customization}

Konfiguroi AI-avustajasi käyttämään MCP-palvelinta automaattisesti ja noudattamaan webforJ:n parhaita käytäntöjä. Lisää projektikohtaisia ohjeita niin, että AI-avustajasi käyttää aina MCP-palvelinta, noudattaa webforJ-dokumentaatiostandardia ja tarjoaa tarkkoja, ajantasaisia vastauksia, jotka vastaavat tiimisi vaatimuksia.

### Projektin konfigurointitiedostot

- **VS Code ja Copilot**: Luo `.github/copilot-instructions.md`
- **Claude Code**: Luo `CLAUDE.md` projektin juureen

Lisää seuraavat tiedot luotuun markdown-tiedostoon:
```markdown
## Käytä webforJ MCP -palvelinta vastataksesi kaikkiin webforJ-kysymyksiin

- Kutsu aina "webforj-knowledge-base" -työkalua hakemaan asiakirjoja, jotka liittyvät kysymykseen
- Varmista kaikki API-signatuurit virallista dokumentaatiota vasten
- Älä koskaan oleta, että menetelmän nimet tai parametrit ovat olemassa ilman tarkistamista

Varmista aina, että koodi kääntyy käyttäen `mvn compile` ennen ehdottamista.
```

## Usein kysytyt kysymykset

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p> miksi AI ei käytä webforJ MCP -palvelinta?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Useimmat AI-avustajat vaativat selkeitä ohjeita MCP-palvelimien käyttämiseksi. Konfiguroi AI-asiakaspalvelusi [AI:n räätälöinti](#ai-customization) -osiossa annetuilla ohjeilla. Ilman näitä ohjeita AI-avustajat saattavat oletusarvoisesti käyttää koulutusdataansa sen sijaan, että kysyisivät MCP-palvelimelta.

      **Nopea ratkaisu:**
      Sisällytä "käytä webforJ MCP" kysymykseesi tai luo sopiva konfigurointitiedosto (`.github/copilot-instructions.md` tai `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka tarkistaa, että MCP-yhteys toimii</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Käytä MCP-tarkastajaa yhteyksien vianmäärityksessä:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Odota viestiä: `🔍 MCP Inspector on käynnistetty osoitteessa http://127.0.0.1:6274` (portti voi vaihdella)

      Sitten tarkastajassa:
      1. Syötä MCP-palvelimen URL: `https://mcp.webforj.com/mcp`
      2. Napsauta "Yhdistä" ottaaksesi yhteyden
      3. Tarkista saatavilla olevat työkalut ja testaa kyselyitä
      4. Seuraa pyynnön/vastauksen lokit vianmääritykselle
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p> Mikä on ero MCP- ja SSE-päätepisteiden välillä?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      WebforJ MCP -palvelin tarjoaa kaksi päätepistettä:

      - **MCP-päätepiste** (`/mcp`) - Moderni protokolla Claudea, VS Codea, Cursoria varten
      - **SSE-päätepiste** (`/sse`) - Server-Sent Events vanhoille asiakkaille, kuten Windsurf

      Useimpien käyttäjien tulisi käyttää MCP-päätepistettä. Käytä SSE:tä vain, jos asiakasi ei tue standardia MCP-protokollaa.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Onko mahdollista käyttää MCP-palvelinta ilman konfigurointitiedostoja?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Kyllä, mutta sitä ei suositella. Ilman konfigurointitiedostoja sinun täytyy manuaalisesti kehottaa AI:ta käyttämään MCP-palvelinta jokaisessa keskustelussa. Konfigurointitiedostot ohjeistavat automaattisesti AI:n käyttämään MCP-palvelinta jokaisessa vuorovaikutuksessa, joten sinun ei tarvitse toistaa ohjeita joka kerta.

      **Manuaalinen lähestymistapa:**
      Aloita kehotteesi seuraavasti: "Käytä webforJ MCP -palvelinta..."

      **Vaihtoehto: Käytä esikonfiguroituja kehotteita**
      MCP-palvelin tarjoaa kehotteita, jotka toimivat ilman konfigurointitiedostoja:
      - `/create-app` - Generoi uusia webforJ-sovelluksia
      - `/create-theme` - Luo esteettömiä CSS-teemoja
      - `/search-webforj` - Edistynyt asiakirjahaku

      Katso [Saatavilla olevat kehotteet](#available-prompts) yksityiskohtia varten.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka osallistua tai raportoida ongelmista</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Raportoi ongelmat:** [webforJ MCP Ongelmatemplate](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)

      **Yleiset ongelmat, jotka kannattaa raportoida:**
      - Vanhentunut dokumentaatio hakutuloksissa
      - Puuttuvat API-menetelmät tai komponentit
      - Väärät koodiesimerkit
      - Työkalujen suorituksen virheet

      Sisällytä kyselysi, odotettu tulos ja todellinen tulos raportointiisi.
    </div>
  </AccordionDetails>
</Accordion>
<br />
