---
title: Herdeploy en live reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Apply code changes to a running webforJ app during development, on the server
  through hotswap or a restart, and in the browser through live reload.
_i18n_hash: 1f91b81b074c81af64ded435e068729c
---
Tijdens de ontwikkeling past webforJ opgeslagen wijzigingen toe op de draaiende app en werkt het de browser bij. Wijzigingen in klassen komen de app binnen via een [hotswap-tool](/docs/configuration/deploy-reload/hotswap) of via een herstart. Live herladen werkt de browser bij na een van beide.

Projecten die zijn gemaakt vanuit een [archetype](/docs/introduction/getting-started) komen geconfigureerd. Voor een bestaand project volg je [Spring Boot](/docs/configuration/deploy-reload/spring-devtools) of [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin).

## Hoe elke wijziging toepast {#how-each-change-applies}

| Wijziging | Resultaat | Referentie |
|---|---|---|
| Java-klasse, hotswap-tool aangesloten | De klasse wordt bijgewerkt in de draaiende app. Het aangetaste deel van de pagina wordt opnieuw opgebouwd en de app-status blijft behouden. | [Hotswap](/docs/configuration/deploy-reload/hotswap) |
| Java-klasse, geen hotswap-tool | De app wordt opnieuw gestart. De browser herlaadt wanneer de app klaar is. | [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) |
| Stylesheet of afbeelding | De pagina past het ter plaatse toe, zonder een herlaadbeurt. | [Instellingen](#settings) |
| Bron onder `src/main/frontend` | De watch bouwt het opnieuw op en werkt de browser bij. | [Frontend watch](/docs/configuration/deploy-reload/frontend-watch) |

## Instellingen {#settings}

Deze instellingen regelen live herladen tijdens de ontwikkeling:

| Eigenschap | Standaard | Beschrijving |
|----------|---------|-------------|
| `webforj.devtools.livereload.enabled` | `false` | Zet live herladen aan voor ontwikkelingsruns. |
| `webforj.devtools.livereload.websocket-port` | `35730` | Poort voor de browserverbinding. |
| `webforj.devtools.livereload.websocket-path` | `/webforj-devtools-ws` | Pad voor de browserverbinding. |
| `webforj.devtools.livereload.static-resources-enabled` | `true` | Past wijzigingen in stylesheets en afbeeldingen ter plaatse toe in plaats van de pagina opnieuw te laden. |
| `webforj.devtools.livereload.heartbeat-interval` | `30000` | Interval in milliseconden voor de verbindingscontroles die een herstartende server detecteren. |

De sleutels hebben geen effect in een verpakte app. Verpakking-apps bevatten geen ontwikkeltools.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
