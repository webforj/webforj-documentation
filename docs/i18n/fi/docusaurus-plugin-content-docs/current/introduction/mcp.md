---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: caf6cb2973387f33706be4c4416a594c
---
The webforJ Model Context Protocol (MCP) -palvelin tarjoaa AI-avustajille suoran pääsyn viralliseen webforJ-dokumentaatioon, varmennettuihin koodiesimerkkeihin ja kehyskohtaisiin malleihin, jotka mahdollistavat tarkempien vastausten antamisen ja automatisoidun projektin luomisen erityisesti webforJ-kehitykselle.

## Mikä on MCP?

Model Context Protocol on avoin standardi, joka mahdollistaa AI-avustajien yhteyden ulkoisiin työkaluihin ja dokumentaatioon. webforJ MCP -palvelin toteuttaa tämän protokollan tarjotakseen:

- **Tietohaun** - Luonnollinen kielellinen haku webforJ-dokumentaatiossa, koodiesimerkeissä ja malleissa
- **Projektin luomisen** - Luo webforJ-sovelluksia virallisista malleista oikealla rakenteella
- **Teeman luomisen** - Luo saavutettavia CSS-teemoja, jotka noudattavat webforJ-suunnittelumalleja

## Miksi käyttää MCP:tä?

Vaikka AI-koodausavustajat ovat erinomaisia peruskysymyksiin, ne kamppailevat monimutkaisten webforJ-spesifisten kysymysten kanssa, jotka ulottuvat useisiin dokumentaatiosyihin. Ilman suoraa pääsyä virallisiin lähteisiin ne saattavat:

- Luoda menetelmiä, joita ei ole olemassa webforJ:ssa
- Viitata vanhentuneisiin tai virheellisiin API-malleihin
- Antaa koodia, joka ei käänny
- Sekoitella webforJ-syntaksia muiden Java-kehysten kanssa
- Ymmärtää väärin webforJ-spesifisiä malleja

MCP-integraation avulla AI-vastaukset sitoutuvat todelliseen webforJ-dokumentaatioon, koodiesimerkkeihin ja kehysmalleihin, tarjoten varmennettavia vastauksia suoraan linkittäen virallisiin lähteisiin syvempää tutkimista varten.

:::warning AI voi silti tehdä virheitä
Vaikka MCP parantaa tarkkuutta merkittävästi tarjoamalla pääsyn virallisiin webforJ-resursseihin, se ei takaa täydellistä koodin generointia. AI-avustajat voivat silti tehdä virheitä monimutkaisissa tilanteissa. Varmista aina generoitu koodi ja testaa perusteellisesti ennen tuotantokäyttöä.
:::

## Asennus

webforJ MCP -palvelin on isännöity osoitteessa `https://mcp.webforj.com` ja siinä on kaksi päätepistettä:

- **MCP-päätepiste** (`/mcp`) - Claudea, VS Codea, Cursoria varten
- **SSE-päätepiste** (`/sse`) - Vanhat asiakkaat

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

Lisää tämä määrittely VS Code settings.json -tiedostoosi:

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

Lisää tämä määrittely Cursor-asetuksiisi:

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

Tämä konfiguroi automaattisesti MCP-palvelimen Claude Code -ympäristössäsi.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Lisää tämä palvelin Claude Desktopin asetuksissa Integraatiot-paneelin avulla:

1. Avaa Claude Desktop ja siirry Asetuksiin
2. Napsauta "Integraatiot" sivupalkissa
3. Napsauta "Lisää integraatio" ja liitä URL-osoite: `https://mcp.webforj.com/mcp`
4. Seuraa asetusten opasta suorittaaksesi määrityksen

Yksityiskohtaisia ohjeita varten katso [virallinen integraatio-opas](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Lisää tämä palvelinmääritys Windsurf MCP -asetuksiisi:

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

Työkalut ovat erikoistuneita toimintoja, joita MCP-palvelin tarjoaa AI-avustajille. Kun esität kysymyksen tai teet pyynnön, AI voi kutsua näitä työkaluja dokumentaation etsimiseksi, projektien generoimiseksi tai teemojen luomiseksi. Jokainen työkalu hyväksyy erityiset parametrit ja palauttaa rakenteellista dataa, joka auttaa AI:ta tarjoamaan tarkkaa, kontekstitietoista apua.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Etsi dokumentaatiota ja esimerkkejä
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Tämä työkalu tarjoaa semanttisia hakumahdollisuuksia koko webforJ-dokumentaatioekosysteemissä. Se ymmärtää kontekstin ja suhteet erilaisten kehyskonseptien välillä, palauttaen asiaankuuluvia dokumentaatiosektioita, API-viittauksia ja toimivia koodiesimerkkejä.

      **Esimerkkikysmykset:**
      ```
      "Etsi webforJ-dokumentaatiosta Painikekomponenttia, jossa on kuvake-esimerkkejä"

      "Löydä webforJ-lomakavalian mallit uusimmasta dokumentaatiosta"

      "Näytä nykyinen webforJ-reititysasetuksia @Route-annotaatiolla"

      "Etsi webforJ-dokumenteista FlexLayoutin responsiivisia suunnittelumalleja"

      "Löydä webforJ:web-komenento-integraatio virallisesta dokumentaatiosta"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - Generoi uusia webforJ-projekteja  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Luodaan täydellinen webforJ-sovellus virallisten Maven-archetypien avulla. Työkalu luo standardoidun projektikohteen hakemistorakenteen ja sisältää aloituskoodia valitun mallin perusteella. Generoidut projektit sisältävät käyttövalmiin rakennusjärjestelmän, resurssihakemistoja ja määritystiedostoja välittömää kehitystä ja käyttöönottoa varten.

      **Esimerkkikehotukset:**
      ```
      "Luo webforJ-projekti nimeltä CustomerPortal käyttäen hello-world-archetypia"

      "Generoi webforJ Spring Boot -projekti, jossa on välilehtiasettelu nimeltä Dashboard"

      "Luo uusi webforJ-sovellus, jossa on sivunavigaatiomalja AdminPanel-projektia varten"
      
      "Generoi tyhjö webforJ-projekti nimeltä TestApp groupId:llä com.example"
      
      "Luo webforJ-projekti InventorySystem, joka käyttää sivunavigaatioarchetypia Spring Bootilla"
      ```

      Käytettäessä tätä työkalua voit valita useista projektimalleista:

      **Archetypit** (projektimallit):
      - `hello-world` - Perussovellus, jossa on näytteenomaisia komponentteja webforJ-ominaisuuksien esittelemiseksi
      - `blank` - Vähäinen projektirakenne aloittamiseen tyhjältä pöydältä
      - `tabs` - Esivalmisteltu välilehtiliittymän asettelu moninäkymä-sovelluksille
      - `sidemenu` - Sivunavigaatioasettelu hallintapaneeleille tai koontinäytöille

      **Maut** (kehykseen integrointi):
      - `webforj` - Standardi webforJ-sovellus
      - `webforj-spring` - webforJ yhdessä Spring Bootin kanssa riippuvuuden injektoimista ja yritysominaisuuksia varten

      :::tip Saatavilla olevat archetypit
      webforJ sisältää useita ennalta määriteltyjä archetypia, jotka auttavat sinua pääsemään nopeasti alkuun. Katso täydellinen luettelo saatavilla olevista archetypeista [archetype-katalogista](../building-ui/archetypes/overview).
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
      Generoi webforJ-teeman määrityksiä käyttäen [DWC HueCraft](https://huecraft.dwc.style/). Työkalu luo täydelliset CSS-mukautuspropertisetti päävärin, toissijaisen värin, onnistumisen, varoituksen, vaaran ja neutraalien värivaihtoehtojen kanssa.

      **Esimerkkipyynnöt:**
      ```
      "Generoi webforJ-teema, jossa pääväri on HSL 220, 70, 50 brändimme varten"

      "Luo webforJ-saavutettava teema nimeltä 'ocean' päävärillä #0066CC"

      "Generoi webforJ-teema brändivärillämme #FF5733"

      "Luo webforJ-teema, jossa pääväri on HSL 30, 100, 50 nimellä 'sunset' sovelluksemme varten"

      "Generoi saavutettava webforJ-teema, jossa pääväri on RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Saatavilla olevat kehoteet {#available-prompts}

Kehotteet ovat esikonfiguroituja AI-ohjeita, jotka yhdistävät useita työkaluja ja työnkulkuja yleisiin tehtäviin. Ne ohjaavat AI:ta tiettyjen vaiheiden ja parametrien kautta, jotta jokaiselle tuetulle työnkululle saadaan luotettavia, toistettavia tuloksia.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Luo ja suorita webforJ-sovellus
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumentit:**
      - `appName` (pakollinen) - Sovelluksen nimi (esim. MyApp, TodoList, Dashboard)
      - `archetype` (pakollinen) - Valitse seuraavista: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (valinnainen) - Suorita automaattisesti kehityspalvelin (kyllä/ei)
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
      - `primaryColor` (pakollinen) - Väri heksadesimaalityyppinä (#FF5733), rgb (255,87,51) tai hsl (9,100,60) muodossa
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Edistynyt haku itsenäisellä ongelmanratkaisulla
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Kehote konfiguroi AI:n niin, että se:

      1. Hakee tietopohjasta laajasti
      2. Kirjoittaa täydellistä, tuotantovalmiinta koodia
      3. Kääntää projektin käyttäen `mvn compile` varmistaakseen, ettei rakennusvirheitä ole
      4. Korjaa virheitä iteratiivisesti, kunnes kaikki toimii
    </div>
  </AccordionDetails>
</Accordion>

### Kuinka käyttää kehotteita

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code ja Claude Code">

1. Kirjoita <kbd>/</kbd> keskusteluun nähdäksesi saatavilla olevat kehotteet
2. Valitse kehotteet avattavasta valikosta
3. Täytä vaaditut parametrit, kun sinua pyydetään

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Napsauta **+** (plus) -kuvaketta kehotteen syöttöalueella
2. Valitse **"Lisää webforJ:stä"** valikosta
3. Valitse haluttu kehotus (esim. `create-app`, `create-theme`, `search-webforj`)
4. Claude pyytää sinua syöttämään vaaditut argumentit
5. Täytä parametrit pyydettäessä

:::tip Varmista MCP:n olevan liitetty
Etsi työkalukuvake syöttöalueen alakulmasta vahvistaaksesi, että webforJ MCP -palvelin on liitetty.
:::

</TabItem>
</Tabs>

## Parhaat käytännöt

Saadaksesi tarkinta ja ajankohtaisinta webforJ-apua, noudata näitä ohjeita, jotta voit hyödyntää MCP-palvelimen ominaisuuksia täysin.

### Varmista MCP-palvelimen käyttö

AI-mallit saattavat ohittaa MCP-palvelimen, jos ne uskovat tietävänsä jo vastauksen. Varmistaaksesi, että MCP-palvelinta käytetään todellakin:

- **Ole selkeä webforJ:sta**: Mainitse aina "webforJ" kysymyksessäsi, jotta käynnistyvät kehyskohtaiset haut
- **Pyydä ajankohtaista tietoa**: Sisällytä lauseita, kuten "uusin webforJ-dokumentaatio" tai "nykyiset webforJ-mallit"
- **Kysy varmennettuja esimerkkejä**: Pyydä "toimivia webforJ-koodiesimerkkejä" pakottaaksesi dokumentaation hakemisen
- **Viittaa tiettyihin versioihin**: Mainitse webforJ-versiosi (esim. "webforJ `25.02`") saadaksesi tarkkoja tuloksia

### Kirjoita erityisiä kehotteita

**Hyvät esimerkit:**
```
"Etsi webforJ-dokumentaatiosta Painikekomponentin tapahtumakäsittely esimerkein"

"Luo webforJ-projekti nimeltä InventorySystem käyttäen sivunavigaatioarchetypia Spring Bootilla"

"Generoi webforJ-teema, jossa pääväri on HSL 220, 70, 50 brändin käyttöä varten"
```

**Huonot esimerkit:**
```
"Miten painikkeet toimivat"

"Tehdään sovellus"

"Tehdään siitä sininen"
```

### Pakota MCP-työkalun käyttö

Jos AI antaa geneerisiä vastauksia ilman MCP-palvelimen käyttöä:

1. **Pyydä eksplisiittisesti**: "Käytä webforJ MCP -palvelinta etsiäksesi `[kysymys]`"
2. **Kysy dokumentaatioviitteitä**: "Etsi webforJ-dokumenteista, miten `[kysymys]`"
3. **Pyydä varmistusta**: "Varmista tämä ratkaisu webforJ-dokumentaation mukaan"
4. **Ole kehyskohtainen**: Sisällytä aina "webforJ" kysymyksiisi

## AI:n räätälöinti {#ai-customization}

Määritä AI-avustajasi käyttämään automaattisesti MCP-palvelinta ja noudattamaan webforJ:n parhaita käytäntöjä. Lisää projektikohtaisia ohjeita, jotta AI-avustajasi käyttävät aina MCP-palvelinta, noudattavat webforJ-dokumentaatiostandardeja ja tarjoavat tarkkoja, ajankohtaisia vastauksia, jotka vastaavat tiimisi vaatimuksia.

### Projektin konfiguraatiotiedostot

- **VS Code ja Copilot**: Luo tiedosto `.github/copilot-instructions.md`
- **Claude Code**: Luo tiedosto `CLAUDE.md` projektisi juureen

Lisää seuraavat tiedot luotuun markdown-tiedostoon:
```markdown
## Käytä webforJ MCP -palvelinta vastataksesi kaikkiin webforJ-kysymyksiin

- Kutsu aina "webforj-knowledge-base" -työkalua hakemaan kysymykseen liittyviä asiakirjoja
- Varmista kaikki API-signatuurit virallisen dokumentaation mukaan
- Älä koskaan oleta, että menetelmän nimet tai parametrit ovat olemassa tarkistamatta

Varmista aina, että koodi kääntyy `mvn compile` ennen ehdottamista.
```

## FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p> Miksi AI ei käytä webforJ MCP -palvelinta?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Useimmat AI-avustajat tarvitsevat eksplisiittiset ohjeet käyttää MCP-palvelimia. Määritä AI-asiakassovelluksesi ohjeilla [AI:n räätälöinti](#ai-customization) -osiosta. Ilman näitä ohjeita AI-avustajat saattavat perustua koulutusdataansa sen sijaan, että kyselisivät MCP-palvelimelta.

      **Nopea ratkaisu:**
      Sisällytä kysymykseesi "käytä webforJ MCP:tä" tai luo asianmukainen konfiguraatiotiedosto (`.github/copilot-instructions.md` tai `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Kuinka varmistaa, että MCP-yhteys toimii</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Käytä MCP-tarkistustyökalua debugataksesi yhteyksiä:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Odota viestiä: `🔍 MCP Inspector is up and running at http://127.0.0.1:6274` (portti saattaa vaihdella)

      Käyttäessäsi tarkistustyökalua:
      1. Syötä MCP-palvelimen URL-osoite: `https://mcp.webforj.com/mcp`
      2. Napsauta "Yhdistä" ottaaksesi yhteyden
      3. Tarkastele käytettävissä olevia työkaluja ja testaa kyselyitä
      4. Seuraa pyyntöjen/vastausten lokit debuggausta varten
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p> Mikä on ero MCP- ja SSE-päätepisteiden välillä?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      webforJ MCP -palvelin tarjoaa kaksi päätepistettä:

      - **MCP-päätepiste** (`/mcp`) - Uudempi protokolla Claudea, VS Codea, Cursoria varten
      - **SSE-päätepiste** (`/sse`) - Palvelin-lähetys tapahtumina vanhoille asiakkaille, kuten Windsurf

      Useimpien käyttäjien tulisi käyttää MCP-päätepistettä. Käytä SSE:tä vain, jos asiakkaasi ei tue standardia MCP-protokollaa.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Onko mahdollista käyttää MCP-palvelinta ilman konfiguraatiotiedostoja?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Kyllä, mutta se ei ole suositeltavaa. Ilman konfiguraatiotiedostoja sinun on manuaalisesti kehotettava AI:ta käyttämään MCP-palvelinta jokaisessa keskustelussa. Konfiguraatiotiedostot ohjaavat automaattisesti AI:ta käyttämään MCP-palvelinta jokaisessa vuorovaikutuksessa, jotta sinun ei tarvitse toistaa ohjeita joka kerta.

      **Manuaalinen lähestymistapa:**
      Aloita kehote: "Käytä webforJ MCP -palvelinta..."

      **Vaihtoehto: käytä esikonfiguroituja kehotteita**
      MCP-palvelin tarjoaa kehotteita, jotka toimivat ilman konfiguraatiotiedostoja:
      - `/create-app` - Luo uusia webforJ-sovelluksia
      - `/create-theme` - Luo saavutettavia CSS-teemoja
      - `/search-webforj` - Edistynyt dokumentaatiohaku

      Katso [Saatavilla olevat kehoteet](#available-prompts) lisätietoja varten.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p> Kuinka myötävaikuttaa tai raportoida ongelmia</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Ongelmien raportointi:** [webforJ MCP Palaute](https://github.com/webforj/webforj-mcp-feedback/issues)
      
      **Yleisiä ongelmia, jotka kannattaa raportoida:**
      - Vanhentunut dokumentaatio hakutuloksissa
      - Puuttuvat API-menetelmät tai komponentit
      - Virheelliset koodiesimerkit
      - Työkalujen suoritusvirheet

      Liitä kysymyksesi, odotettu tulos ja todellinen tulos ongelmia raportoidessasi.
    </div>
  </AccordionDetails>
</Accordion>
<br />
