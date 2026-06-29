---
title: Frontend watch
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild frontend sources while a webforJ app runs and refresh the browser,
  with stylesheet and image changes applied in place and script changes
  reloading the view.
_i18n_hash: efb22f8bcac71567979d21178e62ba7c
---
De frontend-watcher bouwt je `src/main/frontend`-bronnen opnieuw op terwijl de app draait en vernieuwt de browser, zodat een frontend-wijziging zichtbaar is zonder een handmatige build of serverherstart. Het is de ontwikkelingszijde van de [frontend bundler](/docs/managing-resources/bundler/overview).

## Het uitvoeren van de watcher {#running-the-watch}

Voer het parallel aan je server uit, als het doel vóór degene die de app start. Een Spring-project stelt dit in als zijn standaarddoel, dus `mvn` zonder argumenten start beide:

```bash
mvn compile webforj:watch spring-boot:run
```

Tegen de [Maven Jetty-plugin](/docs/configuration/deploy-reload/maven-jetty-plugin) start je het op dezelfde manier:

```bash
mvn compile webforj:watch jetty:run
```

Voor de watcher als een build-stap, zie [Build en tests](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Wat gebeurt er bij een wijziging {#what-happens-on-a-change}

Wanneer je een bron opslaat, bouwt de watcher opnieuw en verzendt de gewijzigde bestanden naar de browser. Wat de browser doet, hangt af van de output, niet van de bron die je hebt bewerkt:

- Een **stylesheet** wordt ter plaatse aangepast, zonder herladen, zodat formuliergegevens en scrollpositie behouden blijven. Een bewerking van `.css`, `.scss`, `.sass` of `.less` valt hier onder.
- Een **afbeelding** wordt ter plaatse verwisseld, zonder herladen.
- **Iets anders** herlaadt de weergave. Een bewerking van `.ts`, `.tsx` of `.js` valt hier onder, omdat nieuw gedrag een frisse pagina vereist.

Als een rebuild meerdere bestanden aanraakt en al deze ter plaatse kunnen worden aangepast, blijft de browser staan. Als zelfs eentje dat niet kan, herlaadt het, zodat je nooit een wijziging halverwege ziet.

## Bij een serverherstart {#across-a-server-restart}

Een wijziging in Java herstart de server, via [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools), de [Maven Jetty-plugin](/docs/configuration/deploy-reload/maven-jetty-plugin) of [JRebel](/docs/configuration/deploy-reload/jrebel). De watcher houdt de frontend in stap met de server:

- **Je stijlen blijven toegepast** tijdens de herstart, in plaats van te knipperen zonder stijlen.
- **De pagina herlaadt zodra de app klaar is**, niet eerder, zodat je geen fout tegenkomt door te vroeg te herladen. Een kleine indicator toont terwijl de server herstart; een handmatige herlaad toont niets.
- **Het toevoegen of verwijderen van een `@BundleEntry` heeft effect bij de volgende herstart.**

## Configuratie {#configuration}

Live herladen is een webforJ-instelling, dus het is van toepassing op welke server je ook draait. Een Spring-app leest deze sleutels uit `application.properties`; een zelfstandige server, zoals de [Maven Jetty-plugin](/docs/configuration/deploy-reload/maven-jetty-plugin), leest dezelfde sleutels uit `webforj.conf`.

```ini title="application.properties"
# Vernieuw de browser bij een wijziging
webforj.devtools.livereload.enabled=true

# Patch stylesheets en afbeeldingen ter plaatse in plaats van te herladen (standaard: true)
webforj.devtools.livereload.static-resources-enabled=true

# Poort en pad waarop de browser verbinding maakt (standaard: 35730, /webforj-devtools-ws)
webforj.devtools.livereload.websocket-port=35730
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Hartslaginterval in milliseconden (standaard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000
```
