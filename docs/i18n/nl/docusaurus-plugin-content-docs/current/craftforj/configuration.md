---
title: Configuratie
sidebar_position: 8
description: >-
  Every craftforJ configuration property, its default, and what turning each
  feature off changes.
_i18n_hash: 025fb1766af8cbcb741f6f353bdf6523
---
craftforJ is geconfigureerd in `webforj.conf`. De eigenschapsnamen zijn hetzelfde op [Spring](/docs/integrations/spring/overview), dus stel ze in `application.properties` in als dat is waar je configuratie zich bevindt.

## Vereiste eigenschappen {#required-properties}

| Eigenschap | Type | Standaard | Beschrijving |
|------------|------|-----------|--------------|
| **`webforj.debug`** | Boolean | `false` | Schakelt de debugmodus in. craftforJ heeft dit nodig |
| **`webforj.devtools.craftforj.enabled`** | Boolean | `false` | Schakelt craftforJ in |

Beide eigenschappen moeten zijn ingeschakeld. Zie [Beveiliging](/docs/craftforj/security#two-required-settings) voor waarom craftforJ twee instellingen in plaats van één vereist.

## Toegang {#access}

| Eigenschap | Type | Standaard | Beschrijving |
|------------|------|-----------|--------------|
| **`webforj.devtools.craftforj.hosts-allowed`** | Lijst of String | alleen loopback | Clientadressen die zijn toegestaan buiten de machine waarop de app draait |

Standaard kan alleen een browser op dezelfde machine als de app craftforJ bereiken. Om andere machines toe te staan, lijst hun adressen op. Een vermelding die eindigt op `*` komt overeen met een prefix, en een enkele `*` verwijdert de beperking volledig.

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning Een wildcard staat iedereen toe die je app kan bereiken
craftforJ leest en schrijft je projectbronnen. Gebruik `*` alleen op een netwerk waarvan je zeker weet wie de poort kan bereiken, zoals een container die alleen jij gebruikt. Gebruik het nooit op een gedeeld netwerk.
:::

## Projectroot {#project-root}

| Eigenschap | Type | Standaard | Beschrijving |
|------------|------|-----------|--------------|
| **`webforj.devtools.craftforj.project-root`** | String | gedetecteerd | De directory waarin je bronnen zich bevinden |

craftforJ bepaalt waar je project zich bevindt op basis van hoe de app is gestart. Ongebruikelijke projectindelingen en sommige containerinstellingen kunnen die detectie verstoren. Als [App-informatie](/docs/craftforj/app-info) de verkeerde projectroot rapporteert, stel je deze hier in.

## Functievlaggen {#feature-flags}

Elk van deze is standaard ingeschakeld. Het uitschakelen van een vlag beperkt wat craftforJ mag doen.

| Eigenschap | Uitschakelen verwijdert |
|------------|--------------------------|
| **`webforj.devtools.craftforj.source-changes`** | Het terugschrijven van eigenschapswijzigingen naar Java en het wijzigen van route toegang |
| **`webforj.devtools.craftforj.stylesheet-changes`** | Het opslaan van thema's en stijlen in je stylesheet |
| **`webforj.devtools.craftforj.ai.enabled`** | De AI-assistent |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | De assistent die zelf Java schrijft |

Het uitschakelen van een vlag schakelt de functie uit voor iedereen die die app gebruikt. De craftforJ-instellingen zijn per ontwikkelaar en kunnen alleen verder worden beperkt, zodat een ontwikkelaar een mogelijkheid die de app heeft uitgeschakeld niet weer kan inschakelen.

:::info Functies die je uitschakelt blijven zichtbaar
Wanneer een vlag is uitgeschakeld, blijft de controle in craftforJ en wordt deze gemarkeerd als niet ondersteund door de verbonden app.
:::

:::warning In productie
Laat `webforj.devtools.craftforj.enabled` niet ingesteld. Zie [Beveiliging](/docs/craftforj/security#in-production) voor de volledige checklist.
:::
