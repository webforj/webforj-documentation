---
title: Routen
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: ec7a4521cd9c4328f9cd6550ebc9d90b
---
Der Tab „Routes“ zeigt die Routing-Tabelle der laufenden App in der [Hierarchie](/docs/routing/route-hierarchy/overview), in der der Router sie speichert, wobei die aktive Route markiert ist. [Dynamisch](/docs/routing/routes-registration) registrierte Routes werden neben den annotierten angezeigt.

![Der Route-Baum mit markierter aktiver Route](/img/craftforj/routes/tree.png#rounded-border)

## Routendetails {#route-details}

Wenn du eine Route auswählst, werden die Informationen angezeigt, die dem Router zu ihr vorliegen, darunter ihr Pfad, die zugrunde liegende Klasse, die angefügten Lifecycle-Beobachter und ihre Konfiguration. Von hier aus kannst du die Klasse im Quellcode-Viewer öffnen.

## Navigation über craftforJ {#navigating-from-craftforj}

Du kannst direkt über craftforJ zu jeder Route navigieren. Bei Routes mit Parametern gibt es für jeden Parameter ein Feld. Während du die Felder ausfüllst, wird der Pfad aufgelöst, sodass du vor dem Navigieren prüfen kannst, wo du landen wirst.

Auf diese Weise wird eine echte Navigation ausgeführt, sodass die [Lifecycle-Beobachter](/docs/routing/navigation-lifecycle/observers) deiner App genau wie bei einer Navigation durch einen Benutzer ausgeführt werden. Der Baum folgt ebenfalls der App. Wenn du also in der App selbst navigierst, wird die Markierung verschoben.

<VideoPlayer video="craftforj/route-navigate" width={1920} height={1000} title="Route navigate" />

## Zugriffsregeln {#access-rules}

Jede Route ist mit einem Badge für die darauf deklarierte [Sicherheitsannotation](/docs/security/annotations) versehen. Über die Symbolleiste kannst du den Baum auf öffentliche oder geschützte Routes eingrenzen.

Nur `@RolesAllowed` und `@DenyAll` gelten als geschützt. `@PermitAll` legt keine Rollen fest und erfordert lediglich, dass jemand angemeldet ist. Daher behandelt der Filter diese Annotation als öffentlich. Berücksichtige das, wenn du prüfst, welche Routes den Zugriff nach Rolle einschränken.

![Der Route-Baum mit einem Zugriffs-Badge für jede Route](/img/craftforj/routes/access-badge.png#rounded-border)

Du kannst die Zugriffsregel einer Route auch über craftforJ ändern. craftforJ schreibt die Annotation in die Klasse der Route und startet die App neu. Die Änderung durchläuft also dieselbe Überprüfung wie jede andere [Quellcodeänderung](/docs/craftforj/source-changes). Diese Option ist nicht verfügbar, wenn craftforJ keine Java-Dateien schreiben darf.

<VideoPlayer video="craftforj/route-security" width={1920} height={1000} title="Route security" />
