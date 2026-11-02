---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: b276ce8df35b91ed18f07c8a686fb688
---
Der Reiter "Routes" zeigt die Routing-Tabelle der laufenden App in der [Hierarchie](/docs/routing/route-hierarchy/overview), in der der Router sie verwaltet, wobei die aktive Route hervorgehoben ist. Dynamisch registrierte Routen erscheinen neben annotierten.

![Der Routenbaum mit der hervorgehobenen aktiven Route](/img/craftforj/routes/tree.png#rounded-border)

## Routen Details {#route-details}

Die Auswahl einer Route zeigt, was der Router darüber weiß, einschließlich ihres Pfades, der Klasse, die dahinter steht, den angehängten Lifecycle-Observern und ihrer Konfiguration. Sie können diese Klasse hier im Quellansichtsfenster öffnen.

## Navigation von craftforJ {#navigating-from-craftforj}

Sie können von craftforJ aus direkt zu jeder Route navigieren. Routen, die Parameter erfordern, bieten ein Feld für jeden Parameter und lösen den Pfad aus, während Sie diese ausfüllen, sodass Sie bestätigen können, wo Sie landen werden, bevor Sie losgehen.

Diese Navigation ist eine echte Navigation, sodass die [Lifecycle-Observer](/docs/routing/navigation-lifecycle/observers) Ihrer App genau so ausgeführt werden, als ob ein Benutzer navigiert. Der Baum folgt ebenfalls der App, sodass das Bewegen innerhalb der App den Marker bewegt.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Zugriffsregeln {#access-rules}

Jede Route trägt eine Auszeichnung für die [Sicherheitsannotation](/docs/security/annotations), die darauf deklariert ist, und Sie können den Baum über die Symbolleiste auf öffentliche oder geschützte Routen eingrenzen.

Nur `@RolesAllowed` und `@DenyAll` zählen als geschützt. `@PermitAll` benennt keine Rollen und erfordert nur, dass jemand angemeldet ist, sodass der Filter es als öffentlich behandelt. Beachten Sie dies, wenn Sie überprüfen, welche Routen den Zugriff nach Rolle einschränken.

![Der Routenbaum mit einer Zugriffsmarkierung auf jeder Route](/img/craftforj/routes/access-badge.png#rounded-border)

Sie können auch die Zugriffsregel einer Route von craftforJ aus ändern. craftforJ schreibt die Annotation in die Klasse der Route, und die App wird neu gestartet, sodass die Änderung denselben Überprüfungsprozess durchläuft wie jede andere [Quelländerung](/docs/craftforj/source-changes). Die Option ist nicht verfügbar, wenn craftforJ nicht berechtigt ist, Java zu schreiben.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4#t=0.5" type="video/mp4" />
  </video>
</div>
