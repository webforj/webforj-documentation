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

MCP Apps ermöglichen einer [MCP](https://modelcontextprotocol.io/)-fähigen KI-App, auch als Host bezeichnet, eine geroutete webforJ-Ansicht innerhalb ihres Gesprächs zu öffnen. Die Ansicht bleibt Teil der Java-App, sodass sie dieselben Komponenten, Dienste, Routen und Zustände verwendet wie im Browser.

Die Person und die KI können mit derselben live UI arbeiten. Die KI kann Eingaben liefern, wenn sie die Ansicht öffnet, Aktionen aufrufen, die die offene Ansicht ändern, und Kontext aus den Entscheidungen erhalten, die die Person in der UI trifft. Die Person kann weiterhin die gerenderten webforJ-Komponenten direkt verwenden.

Spring Boot mit Spring AI ist der primäre Weg, um eine MCP-App zu veröffentlichen. Die Integration entdeckt markierte Routen und fügt sie zum MCP-Server von Spring AI hinzu. Beginnen Sie mit der [Spring Boot-Konfiguration](./spring) und testen Sie dann [die Verbindung](./testing) mit der minimal veröffentlichten Ansicht. Anwendungen, die Spring Boot nicht verwenden, können stattdessen die [Standard-Servlet-Konfiguration](./without-spring) verwenden.

<div class="videos-container">
    <video controls>
      <source src="https://cdn.webforj.com/webforj-documentation/video/mcp-apps/webforj-mcp-app.mp4" type="video/mp4" />
    </video>
</div>

:::info[Die Unterstützung von Hosts variiert]

MCP Apps sind eine sich entwickelnde Erweiterung der MCP-Spezifikation, sodass Hosts ihre Überarbeitungen und Sicherheitsrichtlinien in ihrem eigenen Tempo übernehmen. Die App erklärt die Ursprünge, von denen ihre Ansicht geladen wird und zu denen sie sich verbindet, und ein Host, der diese zulässt, rendert die Ansicht. Hosts können auch strengere Richtlinien anwenden. Das Öffnungswerkzeug gibt immer seinen Textinhalt zurück, und die Route bleibt als reguläre Browserseite verfügbar. Überprüfen Sie jeden Host, den Sie anvisieren, mit den Schritten in [Testing](./testing).
:::

## Themen {#topics}

<DocCardList className="topics-section" />
