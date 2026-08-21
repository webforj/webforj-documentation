---
title: MCP Apps
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
# MCP Apps <DocChip chip='since' label='26.02' /> <DocChip chip='experimental' />
<!-- vale Google.Headings = YES -->

MCP Apps laten een [MCP](https://modelcontextprotocol.io/)-capabele AI-app, ook wel een host genoemd, een geleide webforJ-weergave openen binnen zijn gesprek. De weergave blijft onderdeel van de Java-app, zodat dezelfde componenten, services, routering en staat worden gebruikt als in een browser.

De persoon en de AI kunnen met dezelfde live UI werken. De AI kan input leveren wanneer het de weergave opent, acties aanroepen die de open weergave wijzigen, en context ontvangen van keuzes die de persoon maakt in de UI. De persoon kan blijven werken met de weergegeven webforJ-componenten.

Spring Boot met Spring AI is de primaire manier om een MCP-app te publiceren. De integratie ontdekt gemarkeerde routes en voegt ze toe aan de MCP-server van Spring AI. Begin met de [Spring Boot-configuratie](./spring), en [test de verbinding](./testing) met de minimale gepubliceerde weergave. Applicaties die geen gebruik maken van Spring Boot kunnen in plaats daarvan de [standaard servlet-configuratie](./without-spring) gebruiken.

<div class="videos-container">
    <video controls>
      <source src="https://cdn.webforj.com/webforj-documentation/video/mcp-apps/webforj-mcp-app.mp4" type="video/mp4" />
    </video>
</div>

:::info[Host-ondersteuning varieert]

MCP Apps is een zich ontwikkelende uitbreiding van de MCP-specificatie, zodat hosts zijn revisies en beveiligingsbeleid op hun eigen tempo aannemen. De app verklaart de oorsprongen waaruit zijn weergave wordt geladen en verbonden, en een host die deze toestaat, rendert de weergave. Hosts kunnen ook strengere beleidsmaatregelen toepassen. De openingsinstrument retourneert altijd zijn tekstinhoud en de route blijft beschikbaar als een reguliere browserpagina. Verifieer elke host die je target met de stappen in [Testing](./testing).
:::

## Topics {#topics}

<DocCardList className="topics-section" />
