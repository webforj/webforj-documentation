---
title: Agent Skills
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: cf22942f0e73a936bef31cf8a3a9a043
---
Agenttitaidot opettavat tekoälykoodausassistentteja rakentamaan webforJ-sovelluksia oikeita API:ita, suunnittelutunnuksia ja komponenttimalleja käyttäen. Sen sijaan, että ohjelmistokehykseen liittyviä konventioita arvuuteltaisiin, tekoälyassistentti lataa taidon ja seuraa sen jäsenneltyä työprosessia tuottaakseen koodia, joka kääntyy ja noudattaa parhaita käytäntöjä ensimmäisellä yrityksellä.

Taidoilla on avoin [Agent Skills](https://agentskills.io/specification) -standardi ja ne toimivat useilla tekoälyassistenttipohjilla, mukaan lukien Claude Code, GitHub Copilot VS Code:ssa ja Cursorissa. 
Jokainen taito on yksittäinen hakemisto, jossa on `SKILL.md`-tiedosto, joka kuvaa taidon tarkoitusta ja työprosessia, sekä `references/`- ja `scripts/`-hakemistot tukidokumentaatiota ja apuskiptejä varten.

Agenttitaidot webforJ:lle ovat saatavilla GitHub-repossa [webforj/webforj-agent-skills](https://github.com/webforj/webforJ-agent-skills). 
Näiden taitojen asennuksen jälkeen tekoäly lataa nämä tiedostot automaattisesti, kun se tunnistaa relevantin tehtävän. 
Esimerkiksi, kun pyydät tekoälyä "teeman asettamista tälle sovellukselle sinisessä värimaailmassa", se aktivoi `styling-apps`-taidon, joka ohjaa tekoälyä etsimään voimassa olevia DWC-tunnuksia, kirjoittamaan rajattua CSS:ää ja validoimaan jokaisen muuttujan nimen ennen tulostamista.

## Miksi käyttää taitoja? {#why-use-skills}

Ilman taitoja tekoälyassistentit tuottavat usein webforJ-koodia, joka näyttää uskottavalta mutta epäonnistuu käytännössä. Yleisimmät ongelmat ovat:

- Keksiminen `--dwc-*` -tunnusten nimiä, joita ei ole (CSS kääntyy mutta ei vaikuta)
- Väärän perusluokan käyttö komponenttien kääreissä (`Composite` sijaan `ElementComposite` tai päinvastoin)
- Puuttuvat `PropertyDescriptor`-mallit, tapahtumaannotaatiot tai huolenaiheiden rajapinnat
- Kovakoodattujen värien käyttö, jotka rikkoo tummaa tilaa
- Validointivaiheiden ohittaminen, jotka tunnistavat hiljaisia virheitä

Taidot poistavat nämä ongelmat antamalla tekoälylle tarkat päätöstasot, hakemistuskiptejä ja validoimislistoja kutakin tehtävätyyppiä varten.

## Miten taidot eroavat MCP:stä {#how-skills-differ-from-mcp}

Taidot ja [webforJ MCP -palvelin](./mcp) palvelevat toisiaan täydentäviä rooleja. MCP tarjoaa live-työkaluja, joita tekoäly voi kutsua suoritusajossa asiakirjojen etsimiseksi tai projektien generoimiseksi. Taidot tarjoavat staattista tietoa ja vaiheittaisia työprosesseja, jotka ohjaavat tekoälyä tehtävän lähestymisessä.

| | MCP-palvelin | Agenttitaidot |
|---|---|---|
| **Mitä se tarjoaa** | Live-työkalut: asiakirjojen haku, projektin suunnittelu, teeman luominen | Staattinen tieto: työprosessit, päätöstasot, viitetiedostot, apuskiptit |
| **Milloin se toimii** | Tarpeen mukaan, kun tekoäly kutsuu työkalua | Automaattisesti, kun tekoäly tunnistaa vastaavan tehtävän |
| **Paras käyttöön** | Tiettyjen API:iden etsimiseen, aloitusprojektien luomiseen, teema-värimaailmojen luomiseen | Päätepisteet, jotka vaativat kehyksen konventioiden noudattamista ja monivaiheisia työprosesseja |

Käytännössä molemmat toimivat hyvin yhdessä. MCP-palvelimen `webforj-create-theme` -työkalu tuottaa kelvollisen värimaailman yhdestä väristä, ja `styling-apps` -taito ohjaa tekoälyä komponenttitasoiseen tyylittelyyn ja tumman tilan validointiin tämän värimaailman avulla.

Taidot ovat staattisia tiedostoja, jotka luetaan levyltä - ne eivät lisää suoritusajan ylikuormitusta tai tee ulkoisia API-pyyntöjä. Tekoäly lataa taidon viitemateriaalin kontekstiikkaluokkaansa, kun se on asiaankuuluva, mikä käyttää osaa kontekstin tokeneista, mutta lopullisen tuloksen laatu kehykselle spesifisissä töissä on merkittävästi korkeampi.

## Asennus {#installation}

Kloonaa [webforJ agenttitaidot -repositorio](https://github.com/webforj/webforJ-agent-skills), ja kopioi sitten taitohakemistot siihen paikkaan, jota tekoälytyökalusi odottaa. Jokainen työkalu tukee kahta laajuutta:

- **Projektin laajuus**: taito on käytettävissä vain tässä projektissa
- **Käyttäjän laajuus**: taito on käytettävissä kaikissa projekteissasi

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Projektin laajuus
cp -r creating-components /path/to/your/project/.claude/skills/
cp -r styling-apps /path/to/your/project/.claude/skills/

# Käyttäjän laajuus
cp -r creating-components ~/.claude/skills/
cp -r styling-apps ~/.claude/skills/
```

</TabItem>
<TabItem value="vscode" label="VS Code Copilot">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Projektin laajuus
cp -r creating-components /path/to/your/project/.github/skills/
cp -r styling-apps /path/to/your/project/.github/skills/

# Käyttäjän laajuus
cp -r creating-components ~/.copilot/skills/
cp -r styling-apps ~/.copilot/skills/
```

</TabItem>
<TabItem value="cursor" label="Cursor">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Projektin laajuus
cp -r creating-components /path/to/your/project/.cursor/skills/
cp -r styling-apps /path/to/your/project/.cursor/skills/

# Käyttäjän laajuus
cp -r creating-components ~/.cursor/skills/
cp -r styling-apps ~/.cursor/skills/
```

</TabItem>
</Tabs>

:::tip[Millä laajuudella pitäisi käyttää]
Käytä **projektin laajuutta** työskentelemisessä tiimissä, jotta kaikki projektissa hyötyvät samoista taidoista. Käytä **käyttäjän laajuutta**, kun työskentelet useiden webforJ-projektien parissa ja haluat, että taidot ovat käytettävissä kaikkialla ilman, että niitä tarvitsee kopioida jokaiseen repositorioon.
:::

## Saatavilla olevat taidot {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>creating-components</code></strong>: rakenna uudelleenkäytettäviä webforJ-komponentteja web-komponenttikirjastoista, JavaScript-kirjastoista tai olemassa olevista webforJ-komponenteista
  </AccordionSummary>
  <AccordionDetails>
    <div>

[Tämä taito](https://github.com/webforj/webforJ-agent-skills/tree/main/creating-components) ohjaa tekoälyassistenttia rakentamaan uudelleenkäytettäviä Java-komponentteja mistä tahansa lähteestä, olipa se sitten olemassa oleva web-komponenttikirjasto, pelkkä JavaScript-kirjasto tai olemassa olevien webforJ-komponenttien yhdistelmä.

**Mitä se kattaa**

Taito määrittelee viisi polkua komponenttien luomiseen, ja opettaa tekoälyä valitsemaan oikean sen mukaan, mitä tehtävää ollaan suorittamassa:

| Polku | Milloin käytetään | Perusluokka |
|---|---|---|
| Kääri olemassa oleva Custom Element -kirjasto | Kirjastossa on Custom Elementit (`<x-button>`, `<x-dialog>`) | `ElementComposite` / `ElementCompositeContainer` |
| Rakenna Custom Element, sitten kääri se | Uusi visuaalinen komponentti tai yksinkertaisen JS-kirjaston kääntäminen | `ElementComposite` / `ElementCompositeContainer` |
| Yhdistä webforJ-komponentteja | Olemassa olevien webforJ-komponenttien yhdistäminen uudelleenkäytettäväksi yksiköksi | `Composite<T>` |
| Laajenna HTML-elementtiä | Kevyt kertaluonteinen integrointi ilman Shadow DOM:ia | `Div`, `Span` jne. |
| Sivutason työkalu | Selaimen API tai globaali ominaisuus ilman DOM-widgettiä | Tavanomainen Java-luokka + `EventDispatcher` |

**Työprosessi**

Custom Elementin kääretyyli (yleisin polku) ohjeistaa tekoälyä jäsennellyssä työprosessissa:

1. **Asetus**: lataa kolmannen osapuolen JS/CSS projektin `src/main/resources/static/libs/` -hakemistoon. Taito ohjeistaa tekoälyä suosimaan paikallisia resursseja CDN-linkkien sijaan offline-turvallisuuden vuoksi.
2. **Hae komponenttidata**: käytä mukana olevaa `extract_components.mjs` -skriptiä purkamaan Custom Elements Manifest ja tuottamaan jäsennelty erittely kunkin komponentin ominaisuuksista, tapahtumista, slotista ja CSS-muokattavista ominaisuuksista.
3. **Kirjoita Java-kääreet**: luo `ElementComposite` tai `ElementCompositeContainer` -luokkia `PropertyDescriptor`-kentillä, tapahtumaluokilla, slot-menetelmillä ja huolenaiheiden rajapinnoilla, kaikki seuraavat webforJ-konventioita.
4. **Kirjoita testit**: luo JUnit 5 -testejä käyttäen `PropertyDescriptorTester` ja jäsenneltyjä testimalleja ominaisuuksia, slotteja ja tapahtumia varten.

**Viitemateriaali**

Taito sisältää kahdeksan viitetiedostoa, jotka kattavat `ElementComposite`-mallit, komponenttien koostamisen, ominaisuuksien kuvaajat, tapahtumankäsittelyn, JavaScript-integraation, testausmallit ja yleiset antipatternit.

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>styling-apps</code></strong>: teema ja tyyli webforJ-sovelluksia DWC-suunnittelu-token-järjestelmää käyttäen
  </AccordionSummary>
  <AccordionDetails>
    <div>

[Tämä taito](https://github.com/webforj/webforJ-agent-skills/tree/main/styling-apps) opettaa tekoälyassistenttia tyylittämään webforJ-sovelluksia DWC-suunnittelu-token-järjestelmää käyttäen. Keskeinen periaate on, että kaikki visuaaliset arvot käyttävät `--dwc-*` CSS-muokattavia ominaisuuksia. Taito valvoo tätä tarjoamalla validointivaiheita ja hakuskriptejä, jotka estävät tekoälyä keksimästä tunnusten nimiä tai kovakoodattuja värejä.

**Mitä se kattaa**

| Tehtävä | Lähestymistapa, jonka taito opettaa |
|------|---------------------------|
| Värin muutos | Ylikirjoita paletin sävyt, kylläisyys ja kontrasti `:root`-elementissä |
| Komponentin tyylitys | Etsi ensin komponentin CSS-muuttujat, siirry sitten `::part()`-menetelmään vain tarvittaessa |
| Asettelu ja väli | Käytä `--dwc-space-*` ja `--dwc-size-*` tunnuksia |
| Typografia | Käytä `--dwc-font-*` tunnuksia |
| Täysi teema | Palettikoostumus semanttisen tunnusten uudelleenmäärittelyn avulla |
| Taulukon tyylitys | Vain `::part()`-valitsijat (taulukot eivät paljasta CSS-muuttujia) |
| Google Charts | JSON-teeman tiedosto, joka ladataan `Assets.contentOf()` -menetelmän ja Gsonin kautta |

**Työprosessi**

Taito valvoo tiukkaa hakua ennen kirjoittamista:

1. **Luokittele tehtävä**: määritä, onko kyseessä paletin muutos, komponentin tyylitys, asettelu tai täydellinen teema.
2. **Skannaa sovellus**: lue Java-lähdekoodi löytääksesi jokainen komponentti, teemavariantti ja käytössä oleva laajennus.
3. **Etsi jokainen komponentti**: suorita mukana oleva `component_styles.py` -skripti, jotta saat tarkat CSS-muuttujat, `::part()`-nimet ja peilatut attribuutit, joita kukin komponentti tukee. Tekoäly ei kirjoita CSS:ää ennen tämän vaiheen saamista valmiiksi.
4. **Kirjoita CSS**: tuota sisäkkäisiä, tiiviitä CSS:itä, jotka noudattavat DWC-konventioita: ensin globaalit tunnukset, sitten komponentin CSS-muuttujat, ja lopuksi `::part()`-ylikirjaukset viimeisenä keinona.
5. **Validointi**: suorita hakuskripti uudelleen ja varmista, että jokainen tunnus, osa-nimi ja valitsin tulostuksessa todella existoi. Korjaa kaikki, mikä epäonnistuu.

**Taidon valvomat avain säännöt**

- **Seitsemän palettia vain**: `primary`, `success`, `warning`, `danger`, `info`, `default`, ja `gray`. Nimet kuten `secondary` tai `accent` eivät ole DWC:ssä ja epäonnistuvat hiljaa.
- **Ei kovakoodattuja värejä**: jokaisen värin on oltava `var()`-viite, myös `box-shadow` ja `border` -malleissa. Kovakoodatut arvot rikkovat tumman tilan.
- **CSS-muuttujat `::part()` yli**: komponentin CSS-muuttujat ovat tarkoitettu tyylittämisen API. `::part()` on pelastushatch, kun mikään muuttuja ei ole saatavilla.
- **Rajoitetut valitsijat**: paljaat tag-valitsijat komponentteille, joilla on `theme` tai `expanse` attribuutit, ylittävät kaikki variantit. Taito vaatii `:not([theme])` tai `[theme~="value"]` -rajoittamisen.

</div>
  </AccordionDetails>
</Accordion>
