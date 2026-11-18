---
title: MCP-Apps
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Stellen Sie geroutete webforJ-Ansichten als interaktive MCP-Anwendungen
  bereit, die ein MCP-Host in seiner eigenen Oberfläche öffnen und nutzen kann.
_i18n_title_hash: 7e54d2d2941e9bd5a3793e87f245cbc4
_i18n_description_hash: b6f045cf57cc4cbac608c37f99b5d18b
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

MCP Apps ermöglichen es einer KI-App mit [MCP](https://modelcontextprotocol.io/)-Unterstützung, auch Host genannt, eine geroutete webforJ-Ansicht innerhalb ihrer Unterhaltung zu öffnen. Die Ansicht bleibt Teil der Java-App und verwendet daher dieselben Komponenten, Services, Routing- und Zustandsmechanismen wie im Browser.

Die Person und die KI können mit derselben Live-Benutzeroberfläche arbeiten. Die KI kann beim Öffnen der Ansicht Eingaben übergeben, Aktionen aufrufen, die die geöffnete Ansicht ändern, und Kontext zu den Auswahlen der Person in der Benutzeroberfläche erhalten. Die Person kann die gerenderten webforJ-Komponenten weiterhin direkt verwenden.

Spring Boot mit Spring AI ist die primäre Möglichkeit, eine MCP-App bereitzustellen. Die Integration erkennt markierte Routen und fügt sie dem MCP-Server von Spring AI hinzu. Beginnen Sie mit der [Spring-Boot-Einrichtung](./spring) und [testen Sie anschließend die Verbindung](./testing) mit der minimalen bereitgestellten Ansicht. Anwendungen ohne Spring Boot können stattdessen die [Standard-Servlet-Einrichtung](./without-spring) verwenden.

<VideoPlayer video="mcp-apps/webforj-mcp-app" width={1920} height={1080} title="WebforJ MCP app" />

:::info[Die Host-Unterstützung variiert]

MCP Apps ist eine sich weiterentwickelnde Erweiterung der MCP-Spezifikation. Hosts übernehmen ihre Überarbeitungen und Sicherheitsrichtlinien daher in unterschiedlichem Tempo. Die App gibt die Ursprünge an, von denen ihre Ansicht geladen wird und zu denen sie eine Verbindung herstellt. Ein Host, der diese zulässt, rendert die Ansicht. Hosts können auch strengere Richtlinien anwenden. Das aufrufende Tool gibt immer seinen Textinhalt zurück, und die Route bleibt als reguläre Browserseite verfügbar. Überprüfen Sie jeden Host, den Sie verwenden möchten, anhand der Schritte unter [Testen](./testing).
:::

## Themen {#topics}

<DocCardList className="topics-section" />
