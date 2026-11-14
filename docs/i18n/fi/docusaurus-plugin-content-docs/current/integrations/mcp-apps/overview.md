---
title: MCP-sovellukset
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Expose routed webforJ views as interactive MCP applications that an MCP host
  can open and use inside its own interface.
_i18n_hash: c654b2ac1c5344132602148ba19216b3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale Google.Headings = NO -->
# MCP Apps <DocChip chip='since' label='26.02' /> <DocChip chip='experimental' />
<!-- vale Google.Headings = YES -->

MCP Appsin avulla [MCP:tä](https://modelcontextprotocol.io/) tukeva tekoälysovellus, jota kutsutaan myös isännäksi, voi avata reititetyn webforJ-näkymän keskustelussaan. Näkymä pysyy osana Java-sovellusta, joten se käyttää samoja komponentteja, palveluita, reititystä ja tilaa kuin selaimessa.

Käyttäjä ja tekoäly voivat käyttää samaa reaaliaikaista käyttöliittymää. Tekoäly voi antaa syötteen näkymää avattaessa, kutsua avoinna olevaa näkymää muuttavia toimintoja ja vastaanottaa kontekstia käyttäjän käyttöliittymässä tekemistä valinnoista. Käyttäjä voi jatkaa renderöityjen webforJ-komponenttien suoraa käyttöä.

Spring Boot ja Spring AI ovat ensisijainen tapa julkaista MCP App. Integraatio etsii merkityt reitit ja lisää ne Spring AI:n MCP-palvelimeen. Aloita [Spring Bootin määrityksistä](./spring) ja [testaa sitten yhteys](./testing) yksinkertaisella julkaistulla näkymällä. Sovellukset, joissa ei käytetä Spring Bootia, voivat sen sijaan käyttää [tavallista servlet-määritystä](./without-spring).

<VideoPlayer video="mcp-apps/webforj-mcp-app" width={1920} height={1080} title="WebforJ MCP app" />

:::info[Isäntien tuki vaihtelee]

MCP Apps on MCP-määrityksen kehittyvä laajennus, joten isännät ottavat sen eri versiot ja suojauskäytännöt käyttöön omaan tahtiinsa. Sovellus määrittää alkuperät, joista sen näkymä ladataan ja joihin se muodostaa yhteyden, ja ne salliva isäntä renderöi näkymän. Isännät voivat myös soveltaa tiukempia käytäntöjä. Avaustyökalu palauttaa aina tekstisisältönsä, ja reitti on edelleen käytettävissä tavallisena selaimen sivuna. Varmista kunkin kohdeisännän toiminta [testauksen](./testing) ohjeiden avulla.
:::

## Aiheet {#topics}

<DocCardList className="topics-section" />
