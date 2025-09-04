---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: 7b656643222d616e7c44d14ed1de7bd3
---
WebforJ Model Context Protocol (MCP) -palvelin tarjoaa AI-avustajille suoran pääsyn viralliseen webforJ-dokumentaatioon, vahvistettuihin koodiesimerkkeihin ja kehyksespesifisiin malleihin, mikä mahdollistaa tarkempia vastauksia ja automatisoituja projektigenerointeja erityisesti webforJ-kehitykselle.

## Mikä on MCP?

Model Context Protocol on avoin standardi, joka mahdollistaa AI-avustajien yhdistämisen ulkoisiin työkaluihin ja dokumentaatioon. WebforJ MCP -palvelin toteuttaa tätä protokollaa tarjoamalla:

- **Tietohaut** - Luonnollinen kielen haku webforJ-dokumentaatiossa, koodiesimerkeissä ja malleissa
- **Projektin luonti** - Luo webforJ-sovelluksia virallisista malleista oikealla rakenteella
- **Teeman luonti** - Luo saavutettavia CSS-teemoja webforJ-suunnittelumallien mukaisesti

## Miksi käyttää MCP:tä?

Vaikka AI-koodausavustajat ovat erinomaisia vastaamaan perustason kysymyksiin, ne kamppailevat monimutkaisten webforJ-spesifisten kysymysten kanssa, jotka kattavat useita dokumentaatiokappaleita. Ilman suoraa pääsyä virallisiin lähteisiin ne saattavat:

- Generoida menetelmiä, joita ei ole olemassa webforJ:ssä
- Viitata vanhentuneisiin tai virheellisiin API-malleihin  
- Tarjota koodia, joka ei käänny
- Sekoitella webforJ-syntaksia muiden Java-kehysten kanssa
- Väärinymmärrettää webforJ-spesifisiä malleja

MCP-integraation avulla AI-vastaukset perustuvat todelliseen webforJ-dokumentaatioon, koodiesimerkkeihin ja kehysmalleihin, tarjoten varmennettuja vastauksia suoran linkin virallisiin lähteisiin syvempää tutkimusta varten.

:::warning AI voi silti tehdä virheitä
Vaikka MCP parantaa merkittävästi tarkkuutta tarjoamalla pääsyn virallisiin webforJ-resursseihin, se ei takaa täydellistä koodin generointia. AI-avustajat voivat silti tehdä virheitä monimutkaisissa tilanteissa. Varmista aina generoitu koodi ja testaa huolellisesti ennen käyttöä tuotannossa.
:::

## Asennus

WebforJ MCP -palvelin on isännöity osoitteessa `https://mcp.webforj.com` kahdella päätepisteellä:

- **MCP-päätepiste** (`/mcp`) - Claude, VS Code, Cursor
- **SSE-päätepiste** (`/sse`) - Vanhemmat asiakkaat

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

Tämä konfiguroi MCP-palvelimen automaattisesti Claude Code -ympäristöösi.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Lisää tämä palvelin Claude Desktop -asetuksissa Integraatiot-paneelin kautta:

1. Avaa Claude Desktop ja siirry Asetuksiin
2. Napsauta "Integraatiot" sivupalkissa
3. Napsauta "Lisää integraatio" ja liitä URL: `https://mcp.webforj.com/mcp`
4. Seuraa asennusviisasta täydentääksesi konfiguraation

Yksityiskohtaisia ohjeita varten katso [virallista integraatio-opasta](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Lisää tämä palvelinkonfiguraatio Windsurf MCP -asetuksiisi:

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

Työkalut ovat erikoistuneita toimintoja, joita MCP-palvelin tarjoaa AI-avustajille. Kun esität kysymyksen tai teet pyynnön, AI voi kutsua näitä työkaluja etsiäkseen dokumentaatiota, generoida projekteja tai luoda teemoja. Kukin työkalu hyväksyy erityiset parametrit ja palauttaa jäsenneltyä dataa, joka auttaa AI:ta tarjoamaan tarkkaa, kontekstitietoista apua.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Hae dokumentaatiota ja esimerkkejä
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Tämä työkalu tarjoaa semanttisia hakumahdollisuuksia koko webforJ-dokumentaatioekosysteemissä. Se ymmärtää kontekstin ja suhteet eri kehyskonsepteihin, palauttaen olennaisia dokumentaatiosektioita, API-viittauksia ja toimivia koodiesimerkkejä.

      **Esimerkkikyselyt:**
      ```
      "Hae webforJ-dokumentaatiosta Button-komponentin kuvake-esimerkkejä"

      "Etsi webforJ-lomakkeen validointimalleja uusimmasta dokumentaatiosta"

      "Näytä nykyinen webforJ-reitityksen asetelma @Route-annotaatiolla"

      "Hae webforJ-dokumenteista FlexLayout-vastauksellisia suunnittelumalleja"

      "Etsi webforJ-verkkokomponentin integraatio virallisesta dokumentaatiosta"
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
      Luo täydellisiä webforJ-sovelluksia käyttäen virallisia Maven-archetypia. Työkalu luo standardoidun projektikansion rakenteen ja sisältää aloituskoodin valitun mallin perusteella. Generoidut projektit sisältävät valmiiksi käytettävän rakennusjärjestelmän, resurssikansiot ja konfigurointitiedostot välittömälle kehitykselle ja käyttöönotolle.

      **Esimerkkikehotteet:**
      ```
      "Luo webforJ-projekti nimeltä CustomerPortal käyttäen hello-world-archetypia"

      "Generoi webforJ Spring Boot -projekti, jossa on välilehtirakenne, nimeltä Dashboard"

      "Luo uusi webforJ-sovellus, jossa on sivuvalikkoarchetyyppi AdminPanel-projektille"

      "Generoi webforJ tyhjää projektia nimeltä TestApp, jossa on com.example groupId"

      "Luo webforJ-projekti InventorySystem käyttäen sivuvalikkoarchetypia Spring Bootin kanssa"
      ```

      Käyttäessäsi tätä työkalua voit valita useista projektimalleista:

      **Archetyypit** (projektimallit):
      - `hello-world` - Perussovellus esimerkkikomponenteilla, jotka havainnollistavat webforJ-ominaisuuksia
      - `blank` - Minimalistinen projektirakenne aloittamiseen tyhjältä
      - `tabs` - Valmiiksi rakennettu välilehtirajapinta moninäyttöisille sovelluksille
      - `sidemenu` - Sivunavigaatio-menu rakenne hallintapaneeleille tai ohjauspaneeleille

      **Makuut** (kehysintegraatio):
      - `webforj` - Vakioversion webforJ-sovellus
      - `webforj-spring` - webforJ integroitu Spring Bootiin riippuvuuksien injektointiin ja yritysominaisuuksiin

      :::tip Saatavilla olevat Archetyypit
      webforJ sisältää useita ennalta määritettyjä archetyyppejä, jotka auttavat sinua pääsemään nopeasti alkuun. Täydellisen listan saatavilla olevista archetyypeistä katso [archetyypit-katalogi](../building-ui/archetypes/overview).
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
      Generoi webforJ-teemakonfiguraatioita käyttäen [DWC HueCraft](https://huecraft.dwc.style/). Työkalu luo täydelliset CSS-muuttujakokonaisuudet pää-, toissijaiset, onnistuminen, varoitus, vaara ja neutraaliväriversiot.

      **Esimerkkipyynnöt:**
      ```
      "Generoi webforJ-teema, jossa HSL 220, 70, 50 on pääväri yritysbrändillemme"

      "Luo webforJ-saavutettava teema nimeltä 'ocean' päävärillä #0066CC"

      "Generoi webforJ-teema käyttäen brändiväriämme #FF5733"

      "Luo webforJ-teema, jossa HSL 30, 100, 50 nimeltä 'sunset' sovelluksemme"

      "Generoi saavutettava webforJ-teema, jossa on pääväri RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Saatavilla olevat kehykkeet {#available-prompts}

Kehykkeet ovat esikonfiguroituja AI-ohjeita, jotka yhdistävät useita työkaluja ja työnkulkuja yleisiin tehtäviin. Ne ohjaavat AI:ta tiettyjen vaiheiden ja parametrien läpi luodakseen luotettavia, toistettavia tuloksia jokaiselle tuetetulle työnkululle.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Luo ja suorita webforJ-sovellus
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumentit:**
      - `appName` (pakollinen) - Sovelluksen nimi (esim. MyApp, TodoList, Dashboard)
      - `archetype` (pakollinen) - Valitse vaihtoehdoista: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (valinnainen) - Suorita kehityspalvelin automaattisesti (kyllä/ei)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Generoi webforJ-teema pääväristä
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumentit:**
      - `primaryColor` (pakollinen) - Väri heksadesimaali (#FF5733), rgb (255,87,51) tai hsl (9,100,60) muodossa
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Edistynyt haku itsenäisellä ongelmanratkaisulla
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Kehys konfiguroi AI:n:

      1. Etsimään tietopankkia laajasti
      2. Kirjoittamaan täydellistä, tuotantovalmiita koodia
      3. Kääntämään projektin käyttäen `mvn compile` varmistaakseen, ettei rakennusvirheitä ole
      4. Korjaamaan virheitä iteratiivisesti, kunnes kaikki toimii
    </div>
  </AccordionDetails>
</Accordion>

### Kuinka käyttää kehykkeitä

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code ja Claude Code">

1. Kirjoita <kbd>/</kbd> keskusteluun nähdäksesi saatavilla olevat kehykkeet
2. Valitse kehys pudotusvalikosta
3. Täytä vaaditut parametrit pyydettäessä

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Napsauta **+** (plus) kuvaketta kehysalueella
2. Valitse **"Lisää webforJ:stä"** valikosta
3. Valitse haluamasi kehys (esim. `create-app`, `create-theme`, `search-webforj`)
4. Claude pyytää sinua syöttämään vaaditut argumentit
5. Täytä parametrit pyydettäessä

:::tip Varmista, että MCP on kytketty
Etsi työkalujen kuvake syöttöalueen alakulmassa varmistaaksesi, että webforJ MCP -palvelin on kytketty.
:::

</TabItem>
</Tabs>

## Parhaat käytännöt

Saadaksesi tarkinta ja ajankohtaista webforJ-apua, seuraa näitä ohjeita, jotta voit hyödyntää MCP-palvelimen ominaisuuksia täysimääräisesti.

### Varmista MCP-palvelimen käyttö

AI-mallit saattavat ohittaa MCP-palvelimen, jos ne uskovat jo tietävänsä vastauksen. Varmistaaksesi, että MCP-palvelinta todella käytetään:

- **Ole eksplisiittinen webforJ:stä**: Mainitse aina "webforJ" kysymyksessäsi käynnistääksesi kehyksespesifiset haut
- **Pyydä ajankohtaista tietoa**: Sisällytä lauseet kuten "uusin webforJ-dokumentaatio" tai "nykyiset webforJ-mallit"
- **Kysy varmennettuja esimerkkejä**: Pyydä "toimivia webforJ-koodiesimerkkejä" pakottaaksesi dokumentaatiokatselmoinnin
- **Viitaten tiettyihin versioihin**: Mainitse webforJ-versioni (esim. "webforJ `25.02`") saadaksesi tarkkoja tuloksia

### Kirjoita spesifisiä kehyksiä

**Hyviä esimerkkejä:**
```
"Hae webforJ-dokumentaatiosta Button-komponentin tapahtumankäsittelyä esimerkeillä"

"Luo webforJ-projekti nimeltä InventorySystem käyttämällä sidemenu-archetypia Spring Bootin kanssa"

"Generoi webforJ-teema, jossa HSL 220, 70, 50 on pääväri yritysbrändille"
```

**Huonoja esimerkkejä:**
```
"Kuinka napit toimivat"

"Tehdään sovellus"

"Tehdään siitä sininen"
```

### Pakota MCP-työkalun käyttö

Jos AI antaa geneerisiä vastauksia ilman MCP-palvelimen käyttöä:

1. **Pyydä eksplisiittisesti**: "Käytä webforJ MCP -palvelinta etsiäksesi `[kysymys]`"
2. **Kysy dokumentaatioviittauksista**: "Etsi webforJ-dokumenteista, kuinka `[kysymys]`"
3. **Pyydä varmennusta**: "Vahvista tämä ratkaisu webforJ-dokumentaation mukaan"
4. **Ole kehyksespesifinen**: Sisällytä aina "webforJ" kysymyksiisi

## AI:n mukauttaminen {#ai-customization}

Määritä AI-avustajasi käyttämään automaattisesti MCP-palvelinta ja noudattamaan webforJ:n parhaita käytäntöjä. Lisää projektikohtaisia ohjeita, jotta AI-avustajasi käyttävät aina MCP-palvelinta, noudattavat webforJ-dokumentaatiostandardeja ja antavat tarkkoja, ajankohtaisia vastauksia, jotka vastaavat tiimisi vaatimuksia.

### Projektin konfiguraatiotiedostot

- **VS Code** ja **Copilot**: Luo tiedosto `.github/copilot-instructions.md`
- **Claude Code**: Luo `CLAUDE.md` projektisi juureen

Lisää luotuun markdown-tiedostoon seuraavat tiedot:
```markdown
## Käytä webforJ MCP -palvelinta vastataksesi kaikkiin webforJ-kysymyksiin

- Kutsu aina "webforj-knowledge-base" työkalua hakemaan kysymykseen liittyvää dokumentaatiota
- Varmista kaikki API-signatuurit virallisen dokumentaation mukaan
- Älä koskaan oleta, että menetelmiä tai parametreja on olemassa ilman tarkistamista

Varmista aina, että koodi kääntyy käyttämällä `mvn compile` ennen ehdottamista.
```

## UKK

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Miksi AI ei käytä webforJ MCP -palvelinta?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Useimmat AI-avustajat vaativat eksplisiittisiä ohjeita MCP-palvelimen käyttämiseksi. Määritä AI-asiakasohjelmasi ohjeet [AI:n mukauttaminen](#ai-customization) -osiossa. Ilman näitä ohjeita AI-avustajat saattavat oletusarvoisesti käyttää koulutusdataansa sen sijaan, että kyselisivät MCP-palvelimelta.

      **Nopea korjaus:**
      Sisällytä pyyntöön "käytä webforJ MCP:tä" tai luo sopiva konfiguraatiotiedosto (`.github/copilot-instructions.md` tai `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka varmistaa, että MCP-yhteys toimii</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Käytä MCP-inspektoria virheenkorjaukseen:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Odota viestiä: `🔍 MCP Inspector on käynnissä osoitteessa http://127.0.0.1:6274` (portti voi vaihdella)

      Sitten inspektorissa:
      1. Syötä MCP-palvelimen URL: `https://mcp.webforj.com/mcp`
      2. Napsauta "Yhdistä" ottaaksesi yhteyden
      3. Tarkastele saatavilla olevia työkaluja ja testaa kyselyitä
      4. Seuraa pyyntö-/vastauslokit virheenkorjauksessa
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
      - **SSE-päätepiste** (`/sse`) - Server-Sent Events vanhemmille asiakkaille, kuten Windsurf

      Useimpien käyttäjien tulisi käyttää MCP-päätepistettä. Käytä SSE:tä vain, jos asiakkaasi ei tue vakiomääritystä MCP-protokollasta.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Onko mahdollista käyttää MCP-palvelinta ilman konfiguraatiotiedostoja?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Kyllä, mutta sitä ei suositella. Ilman konfiguraatiotiedostoja sinun on manuaalisesti kehotettava AI:ta käyttämään MCP-palvelinta jokaisessa keskustelussa. Konfiguraatiotiedostot ohjeistavat automaattisesti AI:ta käyttämään MCP-palvelinta jokaisessa vuorovaikutuksessa, joten sinun ei tarvitse toistaa ohjeita joka kerta.

      **Manuaalinen lähestymistapa:**
      Aloita kehykset lauseilla: "Käytä webforJ MCP -palvelinta..."

      **Vaihtoehto: Käytä esikonfiguroituja kehykkeitä**
      MCP-palvelin tarjoaa kehykkeitä, jotka toimivat ilman konfiguraatiotiedostoja:
      - `/create-app` - Generoi uusia webforJ-sovelluksia
      - `/create-theme` - Luo saavutettavia CSS-teemoja
      - `/search-webforj` - Edistynyt dokumentaatiohaku

      Katso [Saatavilla olevat kehykkeet](#available-prompts) lisätietoja varten.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka osallistua tai raportoida ongelmia</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Raportoi ongelmia:** [webforJ MCP -ongelma-template](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **Yleisimmät raportoitavat ongelmat:**
      - Vanhentunut dokumentaatio hakutuloksissa
      - Puuttuvat API-menetelmät tai komponentit
      - Virheelliset koodiesimerkit
      - Työkalun suorituksen virheet

      Sisällytä pyyntösi, odotettu tulos ja todellinen tulos ongelmia raportoitaessa.
    </div>
  </AccordionDetails>
</Accordion>
<br />
