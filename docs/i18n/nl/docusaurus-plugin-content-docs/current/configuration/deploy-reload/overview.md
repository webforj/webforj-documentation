---
title: Heruitrol en Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Apply code changes to a running webforJ app during development, on the server
  through hotswap or a restart, and in the browser through live reload.
_i18n_hash: 243a55fe41bec8aeb4dfc9f70561d80c
---
Tijdens de ontwikkeling past webforJ opgeslagen wijzigingen toe op de draaiende app en werkt de browser bij. Wijzigingen in klassen bereiken de app via een [hotswap-tool](/docs/configuration/deploy-reload/hotswap) of via een herstart. Live herladen werkt de browser bij na een van beide.

Projecten die zijn gemaakt vanuit een [archetype](/docs/introduction/getting-started) zijn geconfigureerd. Voor een bestaand project volg je [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) of [BBjServices](/docs/configuration/deploy-reload/bbjservices).

## Hoe elke wijziging wordt toegepast {#how-each-change-applies}

| Wijziging | Resultaat | Verwijzing |
|---|---|---|
| Java-klasse, hotswap-tool bevestigd | De klasse wordt bijgewerkt in de draaiende app. Het aangetaste deel van de pagina wordt opnieuw opgebouwd en de app-status blijft behouden. | [Hotswap](/docs/configuration/deploy-reload/hotswap) |
| Java-klasse, geen hotswap-tool | De app wordt herstart. De browser wordt opnieuw geladen wanneer de app klaar is. | [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) |
| Java-klasse in BBjServices, geen hotswap-tool | De wijziging wordt pas toegepast wanneer de watch opnieuw begint. | [BBjServices](/docs/configuration/deploy-reload/bbjservices) |
| Stylesheet of afbeelding | De pagina past het ter plaatse toe, zonder opnieuw te laden. | [Instellingen](#settings) |
| Bron onder `src/main/frontend` | De watch bouwt het opnieuw en werkt de browser bij. | [Frontend watch](/docs/configuration/deploy-reload/frontend-watch) |

## Instellingen {#settings}

Deze instellingen regelen live herladen tijdens de ontwikkeling:

| Eigenschap | Standaard | Beschrijving |
|----------|---------|-------------|
| `webforj.devtools.livereload.enabled` | `false` | Schakelt live herladen in voor ontwikkelingsruns. |
| `webforj.devtools.livereload.websocket-port` | `35730` | Poort voor de browserverbinding. |
| `webforj.devtools.livereload.websocket-path` | `/webforj-devtools-ws` | Pad voor de browserverbinding. |
| `webforj.devtools.livereload.static-resources-enabled` | `true` | Past wijzigingen in stylesheet en afbeelding ter plaatse toe in plaats van de pagina opnieuw te laden. |
| `webforj.devtools.livereload.heartbeat-interval` | `30000` | Interval in milliseconden voor de verbindingscontroles die een herstartende server detecteren. |

De sleutels hebben geen effect in een verpakte app. Verpakte apps bevatten geen ontwikkelingshulpmiddelen.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
