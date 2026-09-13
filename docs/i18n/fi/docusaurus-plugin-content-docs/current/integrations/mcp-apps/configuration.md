---
title: MCP-sovelluksen määrittäminen
sidebar_position: 30
description: >-
  Configure the public app origin, allowed embedding clients, and external
  browser domains.
_i18n_hash: 6d6d861d57b9a398007bd9a792e9ec1f
---
Lisää MCP-sovelluksen asetukset `application.properties`-tiedostoon tai `webforj.conf`-tiedostoon käytettäessä standardia webforJ-asetusta. Määritä osoite, josta asiakas voi käyttää sovellusta, ja lisää sitten vain ne asiakas- ja selaimen alkuperät, joita käyttöönotto vaatii.

## Aseta sovelluksen alkuperä {#app-origin}

`webforj.origin` on julkinen alkuperä, jota käytetään sovelluksen resurssissa, sisältöturvapolitiikassa ja webforJ-komponentin URL-osoitteissa. Paikallisessa testauksessa se on sovelluksen osoite:

```Ini
webforj.origin=http://localhost:8080
```

Kun tunneli tai käänteinen välityspalvelin altistaa sovelluksen, käytä julkista alkuperää, johon MCP-asiakas voi päästä:

```Ini
webforj.origin=https://example.trycloudflare.com
```

Älä sisällytä `/mcp` tähän ominaisuuteen. Polku kuuluu MCP-pisteeseen, ei sovelluksen alkuperään.

## Salli upottava asiakas {#allowed-origins}

`webforj.mcp.allowed-origins` ohjaa, mitkä selainalkuperät voivat tehdä ristiin-origin-pyyntöjä ja upottaa näkymän. Paikallista [MCPJam](./testing#mcpjam) -selainta, joka toimii edustavassa alkuperässä `http://127.0.0.1:6274`, varten käytä:

```Ini
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

Käytä alkuperää, joka näkyy asiakkaan selaimen osoitepalkissa, koska paikalliset työkalut voivat valita eri portin. Tunnelin osoite ei ole sallittu asiakasalkuperä; se kuuluu `webforj.origin`-kohtaan.

webforJ sallii jo tunnetut Codex-sovelluksen ja Claude Desktop -hiekkalaatikon alkuperäkaavat. Lisää tämä ominaisuus vain toiselle asiakasalkuperälle. Villikorttia, kuten `https://*.example.com`, käytetään isäntätunnisteiden vastaamiseen, ei satunnaisen URL-tekstin.

## Salli ulkoiset resurssit ja yhteydet {#browser-domains}

Upotettu kehys alkaa rajoitetulla sisältöturvapolitiikalla. Lisää `resource-domains`, kun käyttöliittymän on ladattava skripti, tyyli, kuva, fontti tai muu selainresurssi toisesta alkuperästä:

```Ini
webforj.mcp.resource-domains=https://cdn.example.com
```

Lisää `connect-domains`, kun kehyksen selainkoodin on yhdistettävä ulkoiseen API:hin, WebSocketiin tai vastaavaan päätepisteeseen:

```Ini
webforj.mcp.connect-domains=https://api.example.com
```

Nämä ominaisuudet laajentavat sitä, mitä upotettu kehys voi ladata tai kontaktoida. Ne eivät salli toisen asiakkaan upottaa sovellusta; käytä siihen `allowed-origins`.

## Määritä standardi käyttöönotto {#standard-deployment}

Spring Boot lukee nämä arvot `application.properties`-tiedostosta. Standardissa servlet-käyttöönottossa käytetään `webforj.conf`-tiedostoa vastaavilla arvoilla:

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
webforj.mcp.resource-domains = ["https://cdn.example.com"]
webforj.mcp.connect-domains = ["https://api.example.com"]
```

Lisää vain ne alueet, joita sovellus tarvitsee. [Asiakkaan testaus](./testing) näyttää, mistä löytää paikallisen asiakasalkuperän ja milloin julkista sovelluksen alkuperää tarvitaan.
