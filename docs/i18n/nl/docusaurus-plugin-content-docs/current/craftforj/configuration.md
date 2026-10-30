---
title: Configuratie
sidebar_position: 8
description: >-
  Every craftforJ configuration property, its default, and what turning each
  feature off changes.
_i18n_hash: f7e73354e4d9548e79122405c21ae275
---
craftforJ is geconfigureerd in `webforj.conf`. De eigenschapsnamen zijn hetzelfde in [Spring](/docs/integrations/spring/overview), dus stel ze in op `application.properties` als dat de locatie is van jouw configuratie.

## Vereiste eigenschappen {#required-properties}

| Eigenschap | Type | Standaard | Beschrijving |
|------------|------|-----------|--------------|
| **`webforj.debug`** | Boolean | `false` | Schakelt de debugmodus in. craftforJ vereist dit |
| **`webforj.devtools.craftforj.enabled`** | Boolean | `false` | Schakelt craftforJ in |

Beide eigenschappen moeten ingeschakeld zijn. Zie [Beveiliging](/docs/craftforj/security#two-required-settings) voor waarom craftforJ twee instellingen vereist in plaats van één.

## Toegang {#access}

| Eigenschap | Type | Standaard | Beschrijving |
|------------|------|-----------|--------------|
| **`webforj.devtools.craftforj.hosts-allowed`** | Lijst of String | alleen loopback | Clientadressen die zijn toegestaan buiten de machine waarop de app draait |

Standaard kan alleen een browser op dezelfde machine als de app toegang krijgen tot craftforJ. Om andere machines toe te staan, vermeld je hun adressen. Een invoer die eindigt op `*` komt overeen met een voorvoegsel, en een enkel `*` verwijdert de beperking volledig.

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning Een wildcard staat iedereen toe die jouw app kan bereiken
craftforJ leest en schrijft je projectbronnen. Gebruik `*` alleen op een netwerk waar je zeker weet wie de poort kan bereiken, zoals een container die alleen jij gebruikt. Gebruik het nooit op een gedeeld netwerk.
:::

## Project root {#project-root}

| Eigenschap | Type | Standaard | Beschrijving |
|------------|------|-----------|--------------|
| **`webforj.devtools.project-root`** | String | gedetecteerd | De map waarin jouw bronnen zich bevinden |

craftforJ bepaalt waar jouw project zich bevindt op basis van hoe de app is gestart. Ongebruikelijke projectindelingen en sommige containerconfiguraties maken deze detectie onmogelijk. Als [App info](/docs/craftforj/app-info) de verkeerde projectroot rapporteert, stel het hier in.

```ini title="webforj.conf"
webforj.devtools.project-root = "/home/me/projects/my-app"
```

Stel in Spring dezelfde sleutel in `application.properties` in.

:::info Hernoemd op 26.03
Deze instelling was eerder `webforj.devtools.craftforj.project-root`. De oude sleutel werkt nog steeds als fallback, maar is verouderd en logt een waarschuwing bij gebruik. Als beide sleutels zijn ingesteld, heeft `webforj.devtools.project-root` voorrang.
:::

## Functievlaggen {#feature-flags}

Elk van deze is standaard ingeschakeld. Een vlag uitschakelen beperkt wat craftforJ mag doen.

| Eigenschap | Uitschakelen voorkomt |
|------------|-----------------------|
| **`webforj.devtools.craftforj.source-changes`** | Het terugschrijven van eigenschapswijzigingen naar Java en het wijzigen van route-toegang |
| **`webforj.devtools.craftforj.stylesheet-changes`** | Het opslaan van thema's en stijlen in jouw stylesheet |
| **`webforj.devtools.craftforj.ai.enabled`** | De AI-assistent |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | De assistent die zelf Java schrijft |

Het uitschakelen van een vlag schakelt de functie uit voor iedereen die die app gebruikt. De craftforJ-instellingen zijn per ontwikkelaar en kunnen alleen verder worden beperkt, zodat een ontwikkelaar een mogelijkheid die de app heeft uitgeschakeld niet weer kan inschakelen.

:::info Functies die je uitschakelt blijven zichtbaar
Wanneer een vlag uitgeschakeld is, blijft de controle in craftforJ en wordt deze gemarkeerd als niet-ondersteund door de verbonden app.
:::

:::warning In productie
Laat `webforj.devtools.craftforj.enabled` niet ingesteld. Zie [Beveiliging](/docs/craftforj/security#in-production) voor de complete checklist.
:::
