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

MCP Apps stellen een [MCP](https://modelcontextprotocol.io/)-geschikte AI-app, ook wel een host genoemd, in staat om een gerouteerde webforJ-weergave binnen zijn gesprek te openen. De weergave blijft onderdeel van de Java-app, zodat deze dezelfde componenten, diensten, routering en status gebruikt als in een browser.

De persoon en de AI kunnen met dezelfde live UI werken. De AI kan input leveren wanneer deze de weergave opent, acties aanroepen die de geopende weergave veranderen, en context ontvangen van de keuzes die de persoon in de UI maakt. De persoon kan de weergegeven webforJ-componenten direct blijven gebruiken.

Spring Boot met Spring AI is de primaire manier om een MCP-app te publiceren. De integratie ontdekt gemarkeerde routes en voegt deze toe aan de MCP-server van Spring AI. Begin met de [Spring Boot-configuratie](./spring), en test vervolgens de verbinding met de minimaal gepubliceerde weergave in [testen](./testing). Toepassingen die geen gebruik maken van Spring Boot, kunnen in plaats daarvan de [standaard servlet-configuratie](./without-spring) gebruiken.

:::info[Hostondersteuning varieert]

MCP Apps is een evoluerende uitbreiding van de MCP-specificatie, zodat hosts zijn revisies en beveiligingsbeleid in hun eigen tempo overnemen. De app verklaart de oorsprongen waaruit zijn weergave laadt en waarmee het verbinding maakt, en een host die ze toestaat, rendert de weergave. Hosts kunnen ook strengere beleidsmaatregelen toepassen. Het openings hulpmiddel retourneert altijd zijn tekstinhoud, en de route blijft beschikbaar als een reguliere browserpagina. Verifieer elke host die je target met de stappen in [testen](./testing).
:::

## Topics {#topics}

<DocCardList className="topics-section" />
