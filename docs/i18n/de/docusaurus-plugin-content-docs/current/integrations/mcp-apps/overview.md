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

# MCP Apps <DocChip chip='since' label='26.02' /> <DocChip chip='experimental' />

MCP Apps ermöglichen einer [MCP](https://modelcontextprotocol.io/)-fähigen KI-App, auch als Host bezeichnet, eine geroutete webforJ-Ansicht innerhalb ihres Gesprächs zu öffnen. Die Ansicht bleibt Teil der Java-App, sodass sie dieselben Komponenten, Dienste, Routing und den Zustand verwendet wie im Browser.

Die Person und die KI können mit demselben Live-UI arbeiten. Die KI kann Eingaben bereitstellen, wenn sie die Ansicht öffnet, Aktionen aufrufen, die die geöffnete Ansicht ändern, und Kontext aus den Entscheidungen der Person im UI erhalten. Die Person kann weiterhin die gerenderten webforJ-Komponenten direkt verwenden.

Spring Boot mit Spring AI ist der Hauptweg, um eine MCP-App zu veröffentlichen. Die Integration entdeckt markierte Routen und fügt sie dem MCP-Server von Spring AI hinzu. Beginnen Sie mit dem [Spring Boot-Setup](./spring), und testen Sie dann die Verbindung mit der minimal veröffentlichten Ansicht [Testen](./testing). Anwendungen, die Spring Boot nicht verwenden, können stattdessen das [standard servlet setup](./without-spring) nutzen.

:::info[Die Unterstützung durch Hosts variiert]

MCP Apps ist eine sich entwickelnde Erweiterung der MCP-Spezifikation, daher übernehmen Hosts deren Überarbeitungen und Sicherheitsrichtlinien in ihrem eigenen Tempo. Die App gibt die Ursprünge an, von denen ihre Ansicht lädt und mit denen sie sich verbindet, und ein Host, der sie zulässt, rendert die Ansicht. Hosts können auch strengere Richtlinien anwenden. Das öffnende Tool gibt immer seinen Textinhalt zurück, und die Route bleibt als reguläre Browser-Seite verfügbar. Überprüfen Sie jeden angestrebten Host mit den Schritten in [Testen](./testing).
:::

## Themen {#topics}

<DocCardList className="topics-section" />
