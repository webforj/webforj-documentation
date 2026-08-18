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
# Applications MCP <DocChip chip='since' label='26.02' /> <DocChip chip='experimental' />
<!-- vale Google.Headings = YES -->

Les applications MCP permettent à une application AI compatible avec [MCP](https://modelcontextprotocol.io/), également appelée hôte, d'ouvrir une vue webforJ routée à l'intérieur de sa conversation. La vue reste partie intégrante de l'application Java, donc elle utilise les mêmes composants, services, routage et état que dans un navigateur.

La personne et l'AI peuvent travailler avec la même interface utilisateur en direct. L'AI peut fournir des entrées lorsqu'elle ouvre la vue, appeler des actions qui modifient la vue ouverte et recevoir le contexte des choix que la personne fait dans l'interface utilisateur. La personne peut continuer à utiliser directement les composants webforJ rendus.

Spring Boot avec Spring AI est la principale méthode pour publier une application MCP. L'intégration découvre les routes marquées et les ajoute au serveur MCP de Spring AI. Commencez par la [configuration de Spring Boot](./spring), puis [testez la connexion](./testing) avec la vue publiée minimale. Les applications qui n'utilisent pas Spring Boot peuvent utiliser la [configuration servlet standard](./without-spring) à la place.

:::info[Le support des hôtes varie]

Les applications MCP sont une extension évolutive de la spécification MCP, donc les hôtes adoptent ses révisions et politiques de sécurité à leur propre rythme. L'application déclare les origines depuis lesquelles sa vue se charge et se connecte, et un hôte qui les autorise rend la vue. Les hôtes peuvent également appliquer des politiques plus strictes. L'outil d'ouverture renvoie toujours son contenu texte, et la route reste disponible comme une page de navigateur normale. Vérifiez chaque hôte que vous ciblez avec les étapes dans [Tests](./testing).
:::

## Sujets {#topics}

<DocCardList className="topics-section" />
