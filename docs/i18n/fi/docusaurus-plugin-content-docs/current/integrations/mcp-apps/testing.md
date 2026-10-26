---
title: MCP-sovelluksen testaus
sidebar_position: 10
description: >-
  Test a webforJ MCP App with an MCP Apps-capable host, including the Codex app,
  Claude Desktop, and MCPJam.
_i18n_hash: fb9683202651a3aca86843cf27c0626e
---
webforJ MCP-sovellukset voivat toimia missä tahansa MCP-sovelluksia tukeva isäntä. Ohjeet kattavat Codex-sovelluksen ja Claude Desktopin, jotka toimivat saavutettavan HTTPS-päätteen kautta, ja MCPJam:in localhostin kautta. Miniminen ei-tulo `inventory`-työkalu [Spring Boot -asetuksen](./spring) avulla riittää vahvistamaan, että isäntä voi löytää työkalun ja renderoida Inventory-näkymän.

## Etäasiakkaat {#remote-clients}

Codex-sovellus ja Claude Desktop yhdistävät ulkoa kehityskoneelta. Ne eivät pääse käsiksi `http://localhost:8080/mcp`, joten käynnissä olevan sovelluksen on oltava julkinen HTTPS MCP-URL.

### Altista paikallinen sovellus {#expose-a-local-app}

Käytä [Cloudflare Tunnelia](https://developers.cloudflare.com/tunnel/setup/) varataksesi ja saadaksesi julkisen HTTPS-alkuperän, joka ohjaa sovelluksen oletuspaikalliselle portille, `8080`. Voit aloittaa tunnelin ennen sovellusta:

```bash
cloudflared tunnel --url http://localhost:8080
```

Komento tulostaa HTTPS-alkuperän, kuten `https://example.trycloudflare.com`. Aseta tämä tulostettu alkuperä tiedostoon `src/main/resources/application.properties`:

```Ini
webforj.origin=https://example.trycloudflare.com
```

Käynnistä sovellus normaalin työnkulun kautta. Alkuperä ei sisällä `/mcp`; asiakas-URL lisää `/mcp`:

```text
https://example.trycloudflare.com/mcp
```

:::warning[Development tunnel]

Kehitystunneli tekee sovelluksesta julkisesti saavutettavan. Käytä testitietoja, odota uutta verkkonimeä joka kerta, kun pikainen tunneli käynnistetään, ja käytä vakaata hallittua tunneliä, kun verkkonimen on pysyttävä samana.
:::

### Codex-sovellus {#codex-app}

<!-- Video: Yhdistä ja testaa inventory MCP-sovellusta Codex-sovelluksessa. -->

OpenAI:n [Plugins guide](https://developers.openai.com/codex/plugins) käsittelee nykyisiä liitännäiskohtia.

1. Siirry kohtaan **Asetukset**, avaa **Liitännät** ja valitse **Lisää MCP-palvelin**.
2. Syötä julkinen MCP-URL:

```text
https://example.trycloudflare.com/mcp
```

3. Lisää palvelin, ja aloita uusi keskustelu Codexin kanssa.
4. Kehota Codex-sovellusta:

```text
Avaa inventory-sovellus.
```

5. Vahvista, että renderöity Inventory-näkymä tulee esiin.

<!-- vale Google.Headings = NO -->
### Claude Desktop {#claude-desktop}

<!-- Video: Yhdistä ja testaa inventory MCP-sovellusta Claude Desktopissa. -->
<!-- vale Google.Headings = YES -->

Claude Desktopin etämuokattava liitin tapahtuu Anthropic-infrastruktuurin kautta, joten se tarvitsee myös julkisen HTTPS MCP-URL:n. Anthropicin [liittimien opas](https://support.claude.com/en/articles/11176164-use-connectors-to-extend-claude-s-capabilities) kattaa nykyiset liittimen hallinnat.

1. Avaa **Asetukset**, valitse **Liittimet**, ja napsauta lisäysnappia.
2. Valitse **Lisää mukautettu liitin**, syötä nimi, ja käytä julkista MCP-URL:ia:

```text
https://example.trycloudflare.com/mcp
```

3. Lisää liitin.
4. Keskustelussa, kehota Claude Desktopia:

```text
Avaa inventory-sovellus.
```

6. Vahvista, että renderöity Inventory-näkymä tulee esiin.

Jos palvelin vaatii OAuth 2.0:aa, suorita sisäänkirjautumisprosessi ennen työkalun kutsumista.

:::tip[Määritä MCP-palvelimen nimi kehotteessa]

Jos Codex tai Claude ei valitse odotettua toimintoa, sisällytä MCP-palvelimen nimi kehotteeseen. Tämä voi tapahtua, kun useita työkaluja voisi käyttää tai kehotus on liian epämääräinen. Esimerkiksi: `Käytä inventory MCP-palvelinta, avaa inventory-sovellus.`
:::

## MCPJam {#mcpjam}

[MCPJam](https://github.com/MCPJam/inspector) voi yhdistää suoraan MCP-palvelimeen, joka toimii samalla koneella. Käytä paikallista tarkistinta tavalliselle HTTP-päätteelle; isännöity MCPJam-sovellus hyväksyy vain HTTPS-päätteet.

1. Käynnistä paikallinen tarkistin ja avaa localhost-URL, jonka se tulostaa:

```bash
npx @mcpjam/inspector@latest
```

2. Ennen webforJ-sovelluksen käynnistämistä, määritä sen paikallinen alkuperä ja salli MCPJam-selaimen alkuperä. Korvaa edustava MCPJam-alkuperä seuraavassa koodissa, jos tarkistin tulosti erilaisen:

```Ini
webforj.origin=http://localhost:8080
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

`webforj.origin` asettaa sijainnin, josta renderöity MCP-sovellus lataa sen webforJ-resurssit. `webforj.mcp.allowed-origins` sallii MCPJam-sivun upottaa ja kommunikoida sovelluksen kanssa.

3. Käynnistä webforJ-sovellus normaalin työnkulun kautta.

4. MCPJam:ssa avaa **Yhdistä** ja valitse **Lisää palvelin**. Syötä nimi, valitse **HTTP** kuljetusmuotona ja käytä paikallista MCP-päätettä:

```text
http://localhost:8080/mcp
```

5. Valitse **Ei todennusta**, ja yhdistä sitten palvelimeen. Onnistunut yhteys tekee palvelimen työkalut saataville MCPJam:lle.
6. Avaa **Leikkikenttä**, avaa sitten **Työkalut** vasemmassa sivupalkissa.
7. Valitse `inventory` ja napsauta **Suorita**. Työkalu ei vaadi tuloa, ja sen Inventory-näkymä renderöityy keskustelussa.

:::warning[MCPJam:n sisältöpolitiikka]

Määritä **Content Security Policy (CSP) Mode** Leikkikenttä-työkalupalkissa **Suojaa** ennen työkalun käynnistämistä. Tiukka tila estää dynaamisen JavaScriptin arvioinnin, jota käytetään nykyisessä webforJ-käynnistyksessä. Käytä suojatun tilan vain MCP-palvelimien ja sovelluskoodin kanssa, johon luotat.
:::

## Vahvista sovellus {#verify-the-app}

Käytä tätä perustaa jokaiselle asiakkaalle:

- Asiakas yhdistää MCP-päätteeseen.
- `inventory`-työkalu on näkyvissä.
- `inventory`-kutsuminen renderöi **Inventory**-otsikon.
- Renderöity käyttöliittymä on interaktiivinen.

Kun perustus toimii, lisää [avaus-tulo](./opening-apps), [toiminnot ja päivitykset](./actions-updates) ja [isäntävuorovaikutus](./host-interaction), kun MCP-sovelluksen on tarvittava näitä ominaisuuksia.

## Vianmääritys {#troubleshooting}

| Ongelma | Tarkista |
| --- | --- |
| Asiakas ei voi yhdistää | Vahvista, että sovellus toimii, että tunneli toimii etäasiakkaille, ja että koko asiakas-URL päättyy `/mcp`. |
| Työkalu näkyvissä, mutta resurssi tai avaa epäonnistuu | Vahvista, että `webforj.origin` vastaa nykyistä sovelluksen alkuperää ja että sovellus toimii. |
| MCPJam on tyhjää tai latautuu sisältöpolitiikan `eval`-virheen kanssa | Poista käytöstä **Tiukka**. |
| Metadata on vanhentunutta | Yhdistä asiakas uudelleen tai aloita uusi keskustelu. |
