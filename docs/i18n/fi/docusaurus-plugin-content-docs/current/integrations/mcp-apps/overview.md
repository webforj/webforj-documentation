---
title: MCP-sovellukset
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Expose routed webforJ views as interactive MCP applications that an MCP host
  can open and use inside its own interface.
_i18n_hash: 27896fdcd80b0f7414e1e41f1087d848
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale Google.Headings = NO -->
# MCP-sovellukset <DocChip chip='since' label='26.02' /> <DocChip chip='experimental' />
<!-- vale Google.Headings = YES -->

MCP-sovellukset antavat [MCP](https://modelcontextprotocol.io/)-yhteensopivalle AI-sovellukselle, jota kutsutaan myös isännäksi, avata ohjatun webforJ-näkymän keskustelussaan. Näkymä pysyy osana Java-sovellusta, joten se käyttää samoja komponentteja, palveluita, reititystä ja tilaa kuin selainversiossa.

Henkilö ja AI voivat työskennellä saman elävän käyttöliittymän kanssa. AI voi tarjota syötteitä avatessaan näkymän, kutsua toimintoja, jotka muuttavat avattua näkymää, ja vastaanottaa asiayhteyttä henkilöltä saatujen valintojen perusteella käyttöliittymässä. Henkilö voi jatkaa renderöityjen webforJ-komponenttien käyttöä suoraan.

Spring Boot ja Spring AI ovat ensisijainen tapa julkaista MCP-sovellus. Integraatio löytää merkittyjä reittejä ja lisää ne Spring AI:n MCP-palvelimeen. Aloita [Spring Boot -asetuksesta](./spring), sitten [testaa yhteys](./testing) vähintään julkaistulla näkymällä. Sovellukset, jotka eivät käytä Spring Bootia, voivat käyttää [standardi servlet-asetusta](./without-spring) sen sijaan.

<div class="videos-container">
    <video controls>
      <source src="https://cdn.webforj.com/webforj-documentation/video/mcp-apps/webforj-mcp-app.mp4" type="video/mp4" />
    </video>
</div>

:::info[Isäntätuet vaihtelevat]

MCP-sovellukset ovat kehittyvä laajennus MCP-määritykselle, joten isännät omaksuvat sen muutokset ja turvallisuuspolitiikat omaan tahtiinsa. Sovellus ilmoittaa lähteet, joista sen näkymä lataa ja johon se yhdistää, ja isäntä, joka sallii ne, renderöi näkymän. Isännät voivat myös soveltaa tiukempia käytäntöjä. Avautuva työkalu palauttaa aina tekstisisältönsä, ja reitti pysyy saatavilla tavallisena selainversiona. Varmista jokainen isäntä, johon kohdistut, vaiheiden avulla [Testauksessa](./testing).
:::

## Aihealueet {#topics}

<DocCardList className="topics-section" />
