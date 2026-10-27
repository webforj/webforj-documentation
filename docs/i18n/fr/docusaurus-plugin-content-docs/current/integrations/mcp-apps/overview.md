---
title: Apps MCP
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

# Applications MCP <DocChip chip='since' label='26.02' /> <DocChip chip='experimental' />

Les Applications MCP permettent à une application d'IA compatible avec le [MCP](https://modelcontextprotocol.io/), également appelée hôte, d'ouvrir une vue routée webforJ à l'intérieur de sa conversation. La vue reste une partie de l'application Java, donc elle utilise les mêmes composants, services, routage et état que dans un navigateur.

La personne et l'IA peuvent travailler avec la même interface utilisateur en direct. L'IA peut fournir des entrées lorsqu'elle ouvre la vue, appeler des actions qui modifient la vue ouverte et recevoir le contexte des choix que la personne fait dans l'interface utilisateur. La personne peut continuer à utiliser directement les composants webforJ rendus.

Spring Boot avec Spring AI est la manière principale de publier une Application MCP. L'intégration découvre les routes marquées et les ajoute au serveur MCP de Spring AI. Commencez par la [configuration de Spring Boot](./spring), puis [testez la connexion](./testing) avec la vue publiée minimale. Les applications qui n'utilisent pas Spring Boot peuvent utiliser la [configuration de servlet standard](./without-spring) à la place.

<div class="videos-container">
    <video controls>
      <source src="https://cdn.webforj.com/webforj-documentation/video/mcp-apps/webforj-mcp-app.mp4" type="video/mp4" />
    </video>
</div>

:::info[Soutien des hôtes variable]

Les Applications MCP sont une extension en évolution de la spécification MCP, donc les hôtes adoptent ses révisions et politiques de sécurité à leur propre rythme. L'application déclare les origines de ses vues chargées et connectées, et un hôte qui les permet rend la vue. Les hôtes peuvent également appliquer des politiques plus strictes. L'outil d'ouverture retourne toujours son contenu textuel, et la route reste disponible en tant que page de navigateur ordinaire. Vérifiez chaque hôte que vous ciblez avec les étapes dans [Test](./testing).
:::

## Thèmes {#topics}

<DocCardList className="topics-section" />
