---
title: webforJ AI Plugin
sidebar_position: 1
slug: /ai-tooling
description: >-
  Install the webforJ AI plugin to add the MCP server and Agent Skills to Claude
  Code, Copilot, Cursor, Gemini, and Codex in one step.
_i18n_hash: 44bdaad98af3599ab5fcf57c6a4756c1
---
**webforJ AI plugin** on suositeltu tapa yhdistää AI-koodausassistenttiisi webforJ:ään. Yksi asennus antaa assistentillesi täydet työkalut: live-pääsyn webforJ-dokumentaatioon, projektin rakenteet, teeman luomisen, design-tokenin validoinnin ja rakenteelliset työnkulut, jotka opettavat sen käyttämään kaikkea tätä oikein.

## Mitä saat {#what-you-get}

Lähetä plugin yhdistää kaksi täydentävää osaa yhdellä askeleella:

- **[webforJ MCP server](/docs/ai-tooling/mcp)** - live-työkalut, joita assistentti voi kutsua pyynnöstä: tarkista asioita webforJ-tietokannasta, rakenna Maven-projekteja, luo DWC-teemoja, lue mitä tahansa DWC-komponentin tyylipintaa ja validoi `--dwc-*` tokenit ennen kuin ne päätyvät CSS:ään.
- **[Agent Skills](/docs/ai-tooling/agent-skills)** - rakenteelliset työnkulut, jotka kertovat assistentille _milloin_ käyttää näitä työkaluja, missä järjestyksessä tehdä asioita ja miten validoida tulos. Kattaa uudelleenkäytettävien komponenttien rakentamisen ja webforJ-sovellusten tyylittelyn päästä päähän.

Yhdessä ne muuttavat AI-assistentin, joka arvaillee webforJ:n sääntöjä, sellaiseksi, joka noudattaa niitä.

Näiden lisäksi webforJ tarjoaa erilaista apua:

- **[craftforJ Assistant](/docs/ai-tooling/craftforj-assistant)** - koodausagentti, joka toimii sisällä *käynnissä olevassa* sovelluksessa sen sijaan, että se toimisi editorissasi. Se kirjoittaa Javaa vapaasti, kääntää jokaisen muokkauksen ennen kuin näet sen, soveltaa sitä ja jatkaa työskentelyä sovelluksesi käynnistyksen jälkeen, lukiessaan elävää komponenttipuuta, muuttaen ominaisuuksia, navigoiden reittejä ja säätäen teemaa. Asennettavaa ei ole, koska se tulee webforJ:n mukana.

:::warning AI voi silti tehdä virheitä
Vaikka plugin on asennettu, AI-assistentit voivat tuottaa virheellistä koodia monimutkaisissa skenaarioissa. Tarkista ja testaa aina generoitu koodi ennen julkaisemista.
:::

## Asennus {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

Vahvista Claude Codessa:

```
/plugin
/mcp
```

`webforj` plugin näkyy kohdassa **Asennetut**. MCP-palvelin näkyy muodossa `plugin:webforj:webforj-mcp` yhdistetyissä palvelimissa.

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

```bash
copilot plugin marketplace add webforj/webforj-ai
copilot plugin install webforj@webforj-ai
```

Vahvista:

```bash
copilot plugin list
```

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Suorita komentovalikosta `Chat: Install Plugin From Source`, ja liitä:

```
webforj/webforj-ai
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

```bash
gemini extensions install https://github.com/webforj/webforj-ai
```

Vahvista:

```bash
gemini extensions list
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

```bash
codex plugin marketplace add webforj/webforj-ai
```

Avaamalla Codex-istunnon, suorita `/plugins`, valitse `webforj` ja paina **Space** ottaaksesi sen käyttöön.

Codex ei lataa taitoja automaattisesti kysymyksen perusteella kuten muut asiakkaita. Käytä niitä eksplisiittisesti:
Codex ei lataa taitoja automaattisesti kysymyksen perusteella kuten muut asiakkaita. Käytä niitä eksplisiittisesti:

```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

MCP-työkalut toimivat automaattisesti ilman `$`-prefiksiä.

</TabItem>
</Tabs>

### Muut asiakkaat {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity ja muut Agent Skills -yhteensopivat asiakkaat tukevat myös pluginia - ne käyttävät vain manuaalista konfigurointia markkinapaikkakomennon sijaan. Katso [asiakaskohtainen asennusopas](https://github.com/webforj/webforj-ai#clients) tarkkoja ohjeita varten.

## Käyttäminen {#using-it}

Kun plugin on asennettu, useimmat assistentit lataavat oikean osan automaattisesti perustuen kysymykseesi:

- *"Kääri tämä Custom Element -kirjasto webforJ-komponentiksi."* - laukaisee creating-components-taidon
- *"Tyylittele tämä näkymä DWC-design-tokeneilla."* - laukaisee styling-apps-taidon
- *"Rakenna uusi webforJ-sivumenu-projekti nimeltä CustomerPortal."* - kutsuu MCP-projektin rakenteen
- *"Luo teema brändiväristä `#6366f1`."* - kutsuu MCP-teeman generoijaa
- *"Etsi webforJ-dokumentaatio `@Route`:sta ja reitityksestä."* - kutsuu MCP-tietokannan hakua

Parhaiden tulosten saavuttamiseksi mainitse aina **webforJ** kysymyksissäsi - se on vihje, jota assistentti käyttää ottaakseen pluginin käyttöön sen sijaan, että se käyttäisi yleistä Java-tietoa.

## Päivitys ja poistaminen {#updating-and-uninstalling}

Jokaisella tuetulla asiakkaalla on omat päivitys- ja poistolaatikkonsa. Katso [webforj-ai README](https://github.com/webforj/webforj-ai#clients) asiakohtaisia ohjeita varten.
