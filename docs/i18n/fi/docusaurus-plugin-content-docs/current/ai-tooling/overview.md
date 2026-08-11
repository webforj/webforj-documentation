---
title: webforJ AI Plugin
sidebar_position: 1
slug: /ai-tooling
sidebar_class_name: new-content
description: >-
  Install the webforJ AI plugin to add the MCP server and Agent Skills to Claude
  Code, Copilot, Cursor, Gemini, and Codex in one step.
_i18n_hash: db80016ad151e338c6e353caaa7070d9
---
**webforJ AI -laajennus** on suositeltu tapa liittää AI-koodausassistenttisi webforJ:hen. Yksi asennus antaa assistentillesi täydet työkalut: reaaliaikaisen pääsyn webforJ-dokumentaatioon, projektin rakennekaavioon, teeman luontiin, design-tokenin validointiin ja strukturoituihin työnkulkuihin, jotka opettavat sen, kuinka käyttää kaikkia näitä oikein.

## Mitä saat {#what-you-get}

Laajennuksen asentaminen yhdistää kaksi toisiinsa täydentävää osaa yhdellä toimenpiteellä:

- **[webforJ MCP -palvelin](/docs/ai-tooling/mcp)** - reaaliaikaiset työkalut, joita assistentti voi kutsua tarpeen mukaan: etsi tietoa webforJ-tietopankista, rakenna Maven-projekteja, luo DWC-teemoja, lue minkä tahansa DWC-komponentin tyylipinta ja validoi `--dwc-*` tokenit ennen kuin ne menevät CSS:ään.
- **[Agent Skills](/docs/ai-tooling/agent-skills)** - strukturoidut työnkulut, jotka kertovat assistentille _milloin_ käyttää näitä työkaluja, missä järjestyksessä asiat tehdään ja kuinka validoida tulos. Kattaa uudelleenkäytettävien komponenttien rakentamisen ja webforJ-sovellusten tyylittelyn päästä päähän.

Yhdessä ne muuntavat AI-assistentin, joka arvaili webforJ:n käytäntöjä, sellaiseksi, joka seuraa niitä.

:::warning AI voi silti tehdä virheitä
Vaikka laajennus on asennettu, AI-assistentit voivat tuottaa virheellistä koodia monimutkaisissa tilanteissa. Tarkista ja testaa aina generoitu koodi ennen käyttöönottoa.
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

`webforj` laajennus näkyy kohdassa **Asennetut**. MCP-palvelin näkyy muodossa `plugin:webforj:webforj-mcp` yhdistetyissä palvelimissa.

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

Komennolla, suorita `Chat: Install Plugin From Source`, ja liitä sitten:

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

Avaa sitten Codex-istunto, suorita `/plugins`, valitse `webforj`, ja paina **Väli** aktivoidaksesi sen.

Codex ei lataa taitoja automaattisesti kehotteiden yhteensopivuuden perusteella kuten muut asiakkaita. Aktivoi ne nimenomaisesti:

```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

MCP-työkalut toimivat automaattisesti ilman `$`-etuliitettä.

</TabItem>
</Tabs>

### Muut asiakkaat {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity ja muut Agent Skills -yhteensopivat asiakkaat tukevat myös laajennusta - ne käyttävät vain manuaalista määritystä markkinapaikan komennon sijaan. Katso [asiakaskohtainen asennusopas](https://github.com/webforj/webforj-ai#clients) tarkkoja ohjeita varten.

## Käyttö {#using-it}

Kun se on asennettu, useimmat assistentit lataa oikean osan automaattisesti kehotteesi perusteella:

- *"Kiedo tämä Custom Element -kirjasto webforJ-komponentiksi."* - laukaisee creating-components-taidon
- *"Tyylittele tämä näkymä DWC-design tokenien avulla."* - laukaisee styling-apps-taidon
- *"Rakenna uusi webforJ-sivumenu projekti nimeltä CustomerPortal."* - kutsuu MCP-projektirakentajaa
- *"Luo teema brändiväristä `#6366f1`."* - kutsuu MCP-teemageneraattoria
- *"Etsi webforJ-dokumentit `@Route`:sta ja reitityksestä."* - kutsuu MCP-tietohaku

Parhaiden tulosten saavuttamiseksi mainitse aina **webforJ** kehotteissasi - se on se vihje, jota assistentti käyttää ottaakseen käyttöön laajennuksen sen sijaan, että se perustuisi yleiseen Java-tietoon.

## Päivitys ja poistaminen {#updating-and-uninstalling}

Jokaisella tuetulla asiakkaalla on omat päivitys- ja poistokomentonsa. Katso [webforj-ai README](https://github.com/webforj/webforj-ai#clients) asiakohtaisia ohjeita varten.
