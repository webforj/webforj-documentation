---
title: MCP Apps
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Expose routed webforJ views as interactive MCP applications that an MCP host
  can open and use inside its own interface.
_i18n_hash: aa6dae85057948c6bbc1eae5c30e34b2
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

MCP Apps sallivat [MCP](https://modelcontextprotocol.io/)-yhteensopivan AI-sovelluksen, jota kutsutaan myös isäntäksi, avata reititetyn webforJ-näkymän sen keskustelussa. Näkymä pysyy osana Java-sovellusta, joten se käyttää samoja komponentteja, palveluita, reititystä ja tilaa kuin se tekee selaimessa.

Ihminen ja AI voivat työskennellä saman elävän käyttöliittymän parissa. AI voi antaa syötteen avatessaan näkymän, kutsua toimintoja, jotka muuttavat avattua näkymää, ja saada kontekstia valinnoista, joita ihminen tekee käyttöliittymässä. Ihminen voi jatkaa webforJ-komponenttien suoraa käyttöä.

Spring Boot Spring AIn kanssa on ensisijainen tapa julkaista MCP-sovellus. Integraatio löytää merkittyjä reittejä ja lisää ne Spring AIn MCP-palvelimeen. Aloita [Spring Boot -asetuksella](./spring), sitten [testaa yhteys](./testing) vähimmäispublished-näkymällä. Sovellukset, jotka eivät käytä Spring Bootia, voivat käyttää [standardi servlet -asetusta](./without-spring).

:::info[Isäntätuki vaihtelee]

MCP Apps on kehittyvä laajennus MCP-määritelmälle, joten isännät ottavat sen muutokset ja turvallisuuspolitiikat käyttöön omassa tahdissaan. Sovellus määrittelee, mistä näkymän kuormat tulevat ja mihin se on yhteydessä, ja isäntä, joka sallii ne, renderoi näkymän. Isännät voivat myös soveltaa tiukempia politiikkoja. Avautuva työkalu palauttaa aina sen tekstisisällön, ja reitti pysyy saatavilla tavallisena selain sivuna. Varmista jokainen isäntä, jonka kohdistat, vaiheiden avulla [Testauksessa](./testing).
:::

## Topics {#topics}

<DocCardList className="topics-section" />
