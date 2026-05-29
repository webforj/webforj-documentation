---
title: webforJ AI Plugin
sidebar_position: 1
slug: /integrations/ai-tooling
sidebar_class_name: new-content
_i18n_hash: e02b32f83a943a803532854ffd334a9b
---
**webforJ AI - laajennus** on suositeltu tapa yhdistää AI-koodausassistentti webforJ:hen. Yksi asennus antaa assistentillesi täydet työkalut: live-pääsy webforJ-dokumentaatioon, projektin kehystämiseen, teeman luontiin, design-tokenin validoimiseen ja jäsenneltyihin työnkulkuihin, jotka opettavat sen käyttämään kaikkia näitä oikein.

## Mitä saat {#what-you-get}

Laajennuksen asentaminen yhdistää kaksi toisiaan täydentävää osaa yhdellä toimenpiteellä:

- **[webforJ MCP -palvelin](/docs/integrations/ai-tooling/mcp)** - live-työkalut, joita assistentti voi kutsua tarvittaessa: etsi asioita webforJ-tietopankista, kehystä Maven-projekteja, luo DWC-teemoja, lue minkä tahansa DWC-komponentin tyylipinta ja validoi `--dwc-*` tokenit ennen kuin ne päätyvät CSS:ään.
- **[Agentin taidot](/docs/integrations/ai-tooling/agent-skills)** - jäsennellyt työnkulut, jotka kertovat assistentille _milloin_ käyttää näitä työkaluja, minkä järjestyksen mukaan asiat tehdään ja kuinka validoida tulos. Kattaa uudelleenkäytettävien komponenttien rakentamisen ja webforJ-sovellusten tyylittelyn päästä päähän.

Yhdessä ne muuttavat AI-assistentin, joka arvaa webforJ:n käytäntöjä, sellaiseksi, joka seuraa niitä.

:::warning AI voi silti tehdä virheitä
Vaikka laajennus on asennettu, AI-assistentit voivat tuottaa virheellistä koodia monimutkaisissa tilanteissa. Tarkista ja testaa aina generoitu koodi ennen kuin julkaiset.
:::

## Asennus {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

Varmista Claude Codessa:

```
/plugin
/mcp
```

`webforj`-laajennus näkyy **Asennettu**-kohdassa. MCP-palvelin näkyy `plugin:webforj:webforj-mcp` yhteydessä.

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

```bash
copilot plugin marketplace add webforj/webforj-ai
copilot plugin install webforj@webforj-ai
```

Varmista:

```bash
copilot plugin list
```

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Komentopalvelimesta voit suorittaa `Chat: Install Plugin From Source`, ja liitä sitten:

```
webforj/webforj-ai
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

```bash
gemini extensions install https://github.com/webforj/webforj-ai
```

Varmista:

```bash
gemini extensions list
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

```bash
codex plugin marketplace add webforj/webforj-ai
```

Avaa sitten Codex-istunto, suorita `/plugins`, valitse `webforj` ja paina **Space** ottaaksesi sen käyttöön.

Codex ei lataa taitoja automaattisesti kehotteiden kohdalla kuten muut asiakkaat. Käynnistä ne eksplisiittisesti:

```
$webforj:webforj-styling-apps teemaa tämä sovellus sinisellä väripalettilla
$webforj:webforj-creating-components kääri tämä Custom Element webforJ-komponentiksi
```

MCP-työkalut toimivat automaattisesti ilman `$`-etuliitettä.

</TabItem>
</Tabs>

### Muut asiakkaat {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity ja muut Agent Skills -yhteensopivat asiakkaat tukevat myös laajennusta - ne vain käyttävät manuaalista määritystä markkinapaikan komennon sijaan. Katso [per-asiakas asennusopas](https://github.com/webforj/webforj-ai#clients) tarkkoja vaiheita varten.

## Käyttäminen {#using-it}

Kun laajennus on asennettu, useimmat assistentit lataavat oikean osan automaattisesti kehotteesi perusteella:

- *"Kääri tämä Custom Element -kirjasto webforJ-komponentiksi."* - aktivoi creating-components-taidon
- *"Tyylittele tämä näkymä DWC-design-tokeneilla."* - aktivoi styling-apps-taidon
- *"Kehystä uusi webforJ-sivupalkkiprojekti nimeltä CustomerPortal."* - kutsuu MCP-projektin kehystäjän
- *"Luo teema brändiväristä `#6366f1`."* - kutsuu MCP-teeman luontityökalua
- *"Etsi webforJ-dokumentaatiota `@Route`- ja reititykseen."* - kutsuu MCP-tietohakua

Parhaiden tulosten saavuttamiseksi mainitse aina **webforJ** kehotteissasi - se on signaali, jota assistentti käyttää ottaakseen laajennuksen käyttöön sen sijaan, että käyttäisi yleistä Java-tietoa.

## Päivittäminen ja poistaminen {#updating-and-uninstalling}

Jokaisella tuetulla asiakkaalla on omat päivitys- ja poistamiskomentonsa. Katso [webforj-ai README](https://github.com/webforj/webforj-ai#clients) asiakohtaisia ohjeita varten.
