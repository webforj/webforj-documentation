---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: 8a8c4099d3bd0d4ff988038cee6a5c15
---
Der Reiter „Routes“ zeigt die Routing-Tabelle der laufenden App in der [hierarchie](/docs/routing/route-hierarchy/overview), in der der Router sie hält, mit der aktiven Route, die markiert ist. Routen, die [dynamisch](/docs/routing/routes-registration) registriert werden, erscheinen neben annotierten.

![Der Routenbaum mit der markierten aktiven Route](/img/craftforj/routes/tree.png#rounded-border)

## Routen Details {#route-details}

Die Auswahl einer Route zeigt, was der Router darüber weiß, einschließlich des Pfades, der Klasse dahinter, der angehängten Lebenszyklusbeobachter und ihrer Konfiguration. Sie können diese Klasse von hier aus im Quellansicht öffnen.

## Navigieren von craftforJ {#navigating-from-craftforj}

Sie können direkt von craftforJ zu jeder Route navigieren. Routen, die Parameter annehmen, bieten ein Feld für jede und lösen den Pfad aus, während Sie sie ausfüllen, sodass Sie bestätigen können, wo Sie landen, bevor Sie gehen.

Das Navigieren auf diese Weise ist ein echtes Navigieren, sodass die [Lebenszyklusbeobachter](/docs/routing/navigation-lifecycle/observers) Ihrer App genau so ausgeführt werden, wie es für einen Benutzer der Fall wäre. Der Baum folgt auch der App, sodass das Navigieren in der App selbst den Marker bewegt.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4" type="video/mp4" />
  </video>
</div>

## Zugriffsregeln {#access-rules}

Jede Route trägt ein Abzeichen für die [Sicherheitsannotation](/docs/security/annotations), die auf ihr deklariert ist, und Sie können den Baum über die Symbolleiste auf öffentliche oder geschützte Routen eingrenzen.

Nur `@RolesAllowed` und `@DenyAll` zählen als geschützt. `@PermitAll` benennt keine Rollen und erfordert nur, dass sich jemand angemeldet hat, sodass der Filter es als öffentlich behandelt. Bedenken Sie dies, wenn Sie prüfen, welche Routen den Zugriff nach Rolle einschränken.

![Der Routenbaum mit einem Zugriffsabzeichen auf jeder Route](/img/craftforj/routes/access-badge.png#rounded-border)

Sie können auch die Zugriffsregel einer Route von craftforJ ändern. craftforJ schreibt die Annotation in die Klasse der Route und die App startet neu, sodass die Änderung denselben Überprüfungsprozess durchläuft wie jede andere [Quelleänderung](/docs/craftforj/source-changes). Die Option ist nicht verfügbar, wenn craftforJ nicht berechtigt ist, Java zu schreiben.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4" type="video/mp4" />
  </video>
</div>
