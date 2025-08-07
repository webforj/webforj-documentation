---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: 640740e70970d2eaa1379ce63374ed94
---
WebforJ Mallin Kontekstiprotokolla (MCP) -palvelin tarjoaa tekoälyavustajille suoran pääsyn viralliseen webforJ -dokumentaatioon, vahvistettuihin koodiesimerkkeihin ja kehyskohtaisiin malleihin, mikä mahdollistaa tarkempien vastausten antamisen ja automaattisen projektin luomisen erityisesti webforJ -kehitystä varten.

## Mikä on MCP?

Mallin kontekstiprotokolla on avoin standardi, joka mahdollistaa tekoälyavustajien yhdistämisen ulkoisiin työkaluihin ja dokumentaatioon. WebforJ MCP -palvelin toteuttaa tätä protokollaa tarjoten:

- **Tietohaku** - Luonnollisen kielen haku webforJ -dokumentaatiossa, koodiesimerkeissä ja malleissa
- **Projektin luominen** - Luo webforJ -sovelluksia virallisista malleista oikealla rakenteella
- **Teeman luominen** - Luo saavutettavia CSS-teemoja, jotka noudattavat webforJ -suunnittelumalleja

## Miksi käyttää MCP:tä?

Vaikka tekoälykoodausavustajat ovat hyviä vastaamaan peruskysymyksiin, ne kamppailevat monimutkaisissa webforJ -erityisasioissa, jotka kattaa useita dokumentaatio-osioita. Ilman suoraa pääsyä virallisiin lähteisiin, ne saattavat:

- Luoda metodeja, joita ei ole olemassa webforJ:ssa
- Viitata vanhentuneisiin tai virheellisiin API-malleihin  
- Tarjota koodia, joka ei käännä
- Sekoitella webforJ -syntaksia muiden Java-kehysten kanssa
- Ymmärtää väärin webforJ -erityismalleja

MCP-integraation myötä tekoälyn vastaukset perustuvat todellisiin webforJ -dokumentaatioon, koodiesimerkkeihin ja kehysmalleihin, jolloin ne tarjoavat varmennettuja vastauksia suoria linkkejä virallisiin lähteisiin syvempää tutkimusta varten.

:::warning Tekoäly voi silti tehdä virheitä
Vaikka MCP parantaa tarkkuutta tarjoamalla pääsyn virallisiin webforJ -resursseihin, se ei takaa täydellistä koodin generointia. Tekoälyavustajat saattavat silti tehdä virheitä monimutkaisissa skenaarioissa. Varmista aina luodun koodin toimivuus ja testaa huolellisesti ennen tuotantokäyttöä.
:::

## Asennus

WebforJ MCP -palvelin on isännöity osoitteessa `https://mcp.webforj.com` kahdella päätepisteellä:

- **MCP päätepiste** (`/mcp`) - Claude, VS Code, Cursor
- **SSE päätepiste** (`/sse`) - Vanhemmille asiakkaille

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

Lisää tämä palvelin Claude Desktop -asetusten Integraatio-paneelin kautta:

1. Avaa Claude Desktop ja siirry Asetukset
2. Napsauta sivupalkissa "Integraatiot"
3. Napsauta "Lisää integraatio" ja liitä URL: `https://mcp.webforj.com/mcp`
4. Seuraa asennusvelhoa konfiguraation loppuun saattamiseksi

Yksityiskohtaisia ohjeita varten katso [virallista integraatio-opasta](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Lisää tämä palvelinmäärittely Windsurf MCP -asetuksiisi:

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

Työkalut ovat erityisiä toimintoja, joita MCP-palvelin tarjoaa tekoälyavustajille. Kun esität kysymyksen tai teet pyynnön, tekoäly voi kutsua näitä työkaluja dokumentaation etsimiseksi, projektien luomiseksi tai teemojen luomiseksi. Jokainen työkalu hyväksyy erityisiä parametreja ja palauttaa jäsenneltyä dataa, joka auttaa tekoälyä antamaan tarkkoja, kontekstiin liittyviä avustuksia.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Etsi dokumentaatiota ja esimerkkejä
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Tämä työkalu tarjoaa semanttista hakukapasiteettia koko webforJ -dokumentaatioekosysteemin yli. Se ymmärtää kontekstin ja suhteet eri kehyskonseptien välillä, palauttaen asiaankuuluvia dokumentaatio-osioita, API-viittauksia ja toimivia koodiesimerkkejä.

      **Esimerkkikyselyt:**
      ```
      "Etsi webforJ -dokumentaatiosta Painike-komponentin ikonikomentoja"

      "Löydä webforJ -lomakkeiden validointimalleja uusimmasta dokumentaatiosta"

      "Näytä nykyinen webforJ -reittausasetelma @Route -annotaatiolla"

      "Etsi webforJ -dokumenteista FlexLayout -responsiivisen muotoilun malleja"

      "Löydä webforJ -web-komponenttien integrointi virallisesta dokumentaatiosta"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - Luo uusia webforJ projekteja  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Luodaan täydellisiä webforJ -sovelluksia virallisten Maven-archetyppien avulla. Työkalu luo standardoidun projektikansiorakenteen ja sisältää aloituskoodia valitun mallin perusteella. Luodut projektit sisältävät käyttövalmiin rakentamisjärjestelmän, resurssikansioita ja kokoonpanotiedostoja välitöntä kehitystä ja käyttöönottoa varten.

      **Esimerkkipromptit:**
      ```
      "Luo webforJ -projekti nimeltä CustomerPortal käyttäen hello-world -archetyppiä"

      "Generoi webforJ -Spring Boot projekti, jolla on välilehtirakenne nimeltä Dashboard"

      "Luo uusi webforJ -sovellus sidemenu-arkkitehtuurilla AdminPanel-projektia varten"

      "Generoi webforJ tyhjöprojekti nimeltä TestApp, jolla on com.example groupId"

      "Luo webforJ projekti InventorySystem käyttäen sidemenu-archetyppiä Spring Bootin kanssa"
      ```

      Tämän työkalun käyttöön voit valita useita projektimalleja:

      **Arkkitehtuurit** (projektimallit):
      - `hello-world` - Perussovellus esimerkkikomponenteilla webforJ:n ominaisuuksien esittelemiseksi
      - `blank` - Minimalistinen projektirakenne aloittamalla alusta
      - `tabs` - Esivalmisteltu välilehtiliittymärakenne moninäkymäisille sovelluksille
      - `sidemenu` - Sivunavigaatiomenurakenne ylläpitäjän paneeleille tai hallintapaneeleille

      **Maut** (kehysintegraatio):
      - `webforj` - Vakio webforJ -sovellus
      - `webforj-spring` - webforj, joka on integroitu Spring Bootin kanssa riippuvuuden injektoimista ja yritysominaisuuksia varten

      :::tip Saatavilla olevat arkkitehtuurit
      webforJ sisältää useita ennalta määriteltyjä arkkitehtuureja, jotka auttavat sinua pääsemään nopeasti alkuun. Saat täydellisen luettelon saatavilla olevista arkkitehtuureista [arkkitehtuurikatalogista](../building-ui/archetypes/overview).
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
      Luo webforJ-teeman määrityksiä käyttäen [DWC HueCraft](https://huecraft.dwc.style/). Työkalu luo täydelliset CSS:n mukautusomistusrakenteet, joilla on ensisijaisia, toissijaisia, onnistumisia, varoituksia, vaaroja ja neutraaleja värivariantteja.

      **Esimerkkipyynnöt:**
      ```
      "Generoi webforJ -teema, jonka ensisijainen väri on HSL 220, 70, 50 yritysmelootamme"

      "Luo webforJ -saavutettava teema nimeltä 'ocean' ensisijaisella värillä #0066CC"

      "Generoi webforJ -teema käyttäen brändiväriämme #FF5733"

      "Luo webforJ -teema HSL 30, 100, 50 nimeltä 'sunset' sovelluksemme"

      "Generoi saavutettava webforJ -teema ensisijaisella RGB:llä 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Saatavilla olevat komennot {#available-prompts}

Komennot ovat esikonfiguroituja tekoälyohjeita, jotka yhdistävät useita työkaluja ja työnkulkuja yleisiin tehtäviin. Ne ohjaavat tekoälyä tiettyjen vaiheiden ja parametrien läpi luoden luotettavia, toistettavia tuloksia jokaiselle tuetulle työnkululle.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Luo ja suorita webforJ -sovellus
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumentit:**
      - `appName` (pakollinen) - Sovelluksen nimi (esim. MyApp, TodoList, Dashboard)
      - `archetype` (pakollinen) - Valitse seuraavista: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (valinnainen) - Suorita kehityspalvelin automaattisesti (kyllä/ei)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Generoi webforJ -teema ensisijaisesta väristä
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumentit:**
      - `primaryColor` (pakollinen) - Väri heksamuodossa (#FF5733), rgb (255,87,51) tai hsl (9,100,60)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Kehittynyt haku autonomisella ongelmanratkaisulla
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Komento ohjaa tekoälyä:

      1. Etsimään tietopohjaa laajasti
      2. Kirjoittamaan valmiita, tuotantovalmiita koodia
      3. Kääntämään projektin käyttäen `mvn compile` varmistaakseen, ettei kokoamisvirheitä esiinny
      4. Korjaamaan virheitä toistuvasti, kunnes kaikki toimii
    </div>
  </AccordionDetails>
</Accordion>

### Kuinka käyttää komentoja

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code ja Claude Code">

1. Kirjoita <kbd>/</kbd> keskustelussa nähdäksesi saatavilla olevat komennot
2. Valitse komento pudotusvalikosta
3. Täytä vaaditut parametrit pyynnön mukaan

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Napsauta **+** (plus) -kuvaketta kehotteen syöttöalueella
2. Valitse **"Lisää webforJ:stä"** valikosta
3. Valitse haluttu komento (esim. `create-app`, `create-theme`, `search-webforj`)
4. Claude pyytää sinua syöttämään vaadittavat argumentit
5. Täytä parametrit pyydettäessä

:::tip Varmista, että MCP on yhdistetty
Etsi työkalukuvake syöttöalueen alakulmasta varmistaaksesi, että webforJ MCP -palvelin on yhdistetty.
:::

</TabItem>
</Tabs>

## Parhaat käytännöt

Saadaksesi tarkinta ja ajankohtaisinta webforJ -apua, noudata näitä ohjeita hyödyntääksesi MCP -palvelimen ominaisuuksia.

### Varmista MCP -palvelimen käyttö

Tekoälymallit saattavat ohittaa MCP -palvelimen, jos ne uskovat jo tietävänsä vastauksen. Varmistaaksesi, että MCP-palvelinta käytetään todella:

- **Ole eksplisiittinen webforJ:n suhteen**: Mainitse aina "webforJ" kyselyssäsi laukaistaksesi kehyskohtaisia hakuja
- **Pyydä ajankohtaista tietoa**: Sisällytä lauseita kuten "uusin webforJ -dokumentaatio" tai "nykyiset webforJ -mallit"
- **Kysy varmennettuja esimerkkejä**: Pyydä "toimivia webforJ -koodiesimerkkejä" pakottaaksesi dokumentaation haun
- **Viittaa erityisiin versioihin**: Mainitse webforJ -versiosi (esim. "webforJ `25.02`") saadaksesi tarkat tulokset

### Kirjoita erityisiä kehotteita

**Hyviä esimerkkejä:**
```
"Etsi webforJ -dokumentaatiosta Painike-komponentin tapahtumankäsittely esimerkkien kera"

"Luo webforJ -projekti nimeltä InventorySystem käyttäen sidemenu -archetyyppiä Spring Bootin kanssa"

"Generoi webforJ -teema, jonka ensisijainen väri on HSL 220, 70, 50 yritysmelootamme"
```

**Huonoja esimerkkejä:**
```
"Kuinka painikkeet toimivat"

"Valmista sovellus"

"Valmista se siniseksi"
```

### Pakota MCP -työkalun käyttö

Jos tekoäly antaa geneerisiä vastauksia ilman, että se käyttää MCP -palvelinta:

1. **Pyydä eksplisiittisesti**: "Käytä webforJ MCP -palvelinta etsimään `[kysely]`"
2. **Kysy dokumentaatioita viitataksesi**: "Etsi webforJ -dokumenteista, kuinka `[kysely]`"
3. **Pyydä varmennusta**: "Varmista tämä ratkaisu webforJ -dokumentaation mukaan"
4. **Ole kehyskohtainen**: Mainitse aina "webforJ" kysymyksissäsi

## Tekoälyn mukauttaminen {#ai-customization}

Määritä tekoälyavustajasi automaattisesti käyttämään MCP -palvelinta ja noudattamaan webforJ -parhaita käytäntöjä. Lisää projektiisi liittyviä ohjeita, jotta tekoälyavustajasi käyttää aina MCP -palvelinta, noudattaa webforJ -dokumentaatiostandardeja ja tarjoavat tarkkoja, ajankohtaisia vastauksia, jotka vastaavat tiimisi vaatimuksia.

### Projektin konfiguraatiotiedostot

- **VS Code ja Copilot**: Luo tiedosto `.github/copilot-instructions.md`
- **Claude Code**: Luo tiedosto `CLAUDE.md` projektin juureen

Lisää seuraava kirjaus luotuun markdown-tiedostoon:
```markdown
## Käytä webforJ MCP -palvelinta vastaamaan kaikkiin webforJ -kysymyksiin

- Kutsu aina "webforj-knowledge-base" -työkalua hakemaan kysymykseen liittyvää dokumentaatiota
- Varmista kaikki API-allekirjoitukset virallisen dokumentaation mukaan
- Älä koskaan oleta, että metodin nimet tai parametrit olemassa ilman tarkistamista

Varmista aina, että koodi kääntyy `mvn compile` ennen ehdotusta.
```

## Usein kysytyt kysymykset

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Miksi tekoäly ei käytä webforJ MCP -palvelinta?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Useimmat tekoälyavustajat tarvitsevat eksplisiittisiä ohjeita käyttääkseen MCP -palvelimia. Konfiguroi tekoälyasiakkaasi [tekoälyn mukauttaminen](#ai-customization) -kohdassa olevilla ohjeilla. Ilman näitä ohjeita tekoälyavustajat saattavat palautua koulutusdataansa sen sijaan, että kyselisivät MCP -palvelimelta.

      **Nopea ratkaisu:**
      Sisällytä "käytä webforJ MCP" pyyntöysi tai luo sopiva konfiguraatiotiedosto (`.github/copilot-instructions.md` tai `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka varmistaa, että MCP-yhteys toimii</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Käytä MCP-tarkistinta yhteyksien vianmääritykseen:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Odota viestiä: `🔍 MCP Inspector on nyt käynnissä osoitteessa http://127.0.0.1:6274` (portti voi vaihdella)

      Tarkista sitten tarkistimella:
      1. Syötä MCP -palvelimen URL: `https://mcp.webforj.com/mcp`
      2. Napsauta "Yhdistä" ottaaksesi yhteyden
      3. Näe saatavilla olevat työkalut ja testaa kyselyjä
      4. Seuraa pyyntö/vastauslokit vianmääritykselle
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p> mikä on MCP:n ja SSE:n päätepisteiden välinen ero?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      WebforJ MCP -palvelin tarjoaa kaksi päätepistettä:

      - **MCP päätepiste** (`/mcp`) - Moderni protokolla Claude, VS Code, Cursor
      - **SSE päätepiste** (`/sse`) - Server-Sent Events vanhemmille asiakkaille, kuten Windsurf

      Suurin osa käyttäjistä tulisi käyttää MCP-päätepistettä. Käytä SSE:tä vain, jos asiakaspalvelimesi ei tue vakiota MCP-protokollaa.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Onko mahdollista käyttää MCP -palvelinta ilman konfiguraatiotiedostoja?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Kyllä, mutta se ei ole suositeltavaa. Ilman konfiguraatiotiedostoja sinun on käsin kehotettava tekoälyä käyttämään MCP-palvelinta jokaisessa keskustelussa. Konfiguraatiotiedostot ohjeistavat automaattisesti tekoälyä käyttämään MCP-palvelinta jokaisessa vuorovaikutuksessa, joten sinun ei tarvitse toistaa ohjeita joka kerta.

      **Manuaalinen lähestymistapa:**
      Aloita komennot: "Käytä webforJ MCP -palvelinta..."

      **Vaihtoehto: Käytä esikonfiguroituja komentoja**
      MCP -palvelin tarjoaa komentoja, jotka toimivat ilman konfiguraatiotiedostoja:
      - `/create-app` - Generoi uusia webforJ -sovelluksia
      - `/create-theme` - Luo saavutettavia CSS-teemoja
      - `/search-webforj` - Kehittynyt dokumentaation haku

      Katso [Saatavilla olevat komennot](#available-prompts) tarkempia tietoja.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka osallistua tai raportoida ongelmia</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Raportoi ongelmat:** [webforJ MCP Palaute](https://github.com/webforj/webforj-mcp-feedback/issues)
      
      **Yleiset ongelmat, jotka kannattaa raportoida:**
      - Vanhentunut dokumentaatio hakutuloksissa
      - Puuttuvat API -menetelmät tai komponentit
      - Virheelliset koodiesimerkit
      - Työkalun suorituksen virheet

      Liitä raporttiin kyselysi, odotettu tulos ja todellinen tulos ongelmien raportoinnin yhteydessä.
    </div>
  </AccordionDetails>
</Accordion>
<br />
