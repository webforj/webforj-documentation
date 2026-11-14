---
title: Applications MCP
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

Les applications d’IA compatibles avec [MCP](https://modelcontextprotocol.io/), également appelées hôtes, peuvent ouvrir une vue webforJ routée au sein de leur conversation. La vue fait toujours partie de l’application Java et utilise donc les mêmes composants, services, routes et état que dans un navigateur.

La personne et l’IA peuvent interagir avec la même interface utilisateur en direct. L’IA peut fournir des données à l’ouverture de la vue, appeler des actions qui modifient la vue ouverte et recevoir le contexte des choix effectués par la personne dans l’interface. La personne peut continuer à utiliser directement les composants webforJ affichés.

Spring Boot avec Spring AI est la méthode principale pour publier une application MCP. L’intégration détecte les routes marquées et les ajoute au serveur MCP de Spring AI. Commencez par la [configuration Spring Boot](./spring), puis [testez la connexion](./testing) avec la vue publiée minimale. Les applications qui n’utilisent pas Spring Boot peuvent plutôt utiliser la [configuration standard de servlet](./without-spring).

<VideoPlayer video="mcp-apps/webforj-mcp-app" width={1920} height={1080} title="WebforJ MCP app" />

:::info[La prise en charge varie selon l’hôte]

MCP Apps est une extension évolutive de la spécification MCP. Les hôtes adoptent donc ses révisions et ses politiques de sécurité à leur propre rythme. L’application déclare les origines depuis lesquelles sa vue se charge et auxquelles elle se connecte, et un hôte qui les autorise affiche la vue. Les hôtes peuvent également appliquer des politiques plus strictes. L’outil d’ouverture renvoie toujours son contenu textuel, et la route reste accessible comme une page de navigateur classique. Vérifiez chaque hôte que vous ciblez en suivant les étapes de la section [Tests](./testing).
:::

## Sujets {#topics}

<DocCardList className="topics-section" />
