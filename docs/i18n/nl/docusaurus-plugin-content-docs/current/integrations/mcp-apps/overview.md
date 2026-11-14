---
title: MCP-apps
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

Met MCP Apps kan een AI-app die [MCP](https://modelcontextprotocol.io/) ondersteunt, ook wel een host genoemd, een gerouteerde webforJ-weergave openen in het gesprek. De weergave blijft onderdeel van de Java-app en gebruikt dus dezelfde componenten, services, routing en status als in een browser.

De gebruiker en de AI kunnen met dezelfde live UI werken. De AI kan invoer aanleveren wanneer de weergave wordt geopend, acties aanroepen die de geopende weergave aanpassen en context ontvangen op basis van keuzes die de gebruiker in de UI maakt. De gebruiker kan de weergegeven webforJ-componenten rechtstreeks blijven gebruiken.

Spring Boot met Spring AI is de belangrijkste manier om een MCP App te publiceren. De integratie ontdekt gemarkeerde routes en voegt deze toe aan de MCP-server van Spring AI. Begin met de [Spring Boot-configuratie](./spring) en [test vervolgens de verbinding](./testing) met de minimale gepubliceerde weergave. Applicaties die geen Spring Boot gebruiken, kunnen in plaats daarvan de [standaard servlet-configuratie](./without-spring) gebruiken.

<VideoPlayer video="mcp-apps/webforj-mcp-app" width={1920} height={1080} title="WebforJ MCP app" />

:::info[Ondersteuning door hosts varieert]

MCP Apps is een evoluerende uitbreiding van de MCP-specificatie. Hosts nemen de revisies en het beveiligingsbeleid ervan daarom in hun eigen tempo over. De app geeft aan vanaf welke origins de weergave wordt geladen en waarmee deze verbinding maakt. Een host die deze toestaat, geeft de weergave weer. Hosts kunnen ook strengere beleidsregels toepassen. De openingstool retourneert altijd de tekstuele inhoud en de route blijft beschikbaar als een gewone browserpagina. Controleer elke host waarop je je richt met de stappen in [Testen](./testing).
:::

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
