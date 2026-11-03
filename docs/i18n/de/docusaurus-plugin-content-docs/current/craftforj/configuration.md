---
title: Konfiguration
sidebar_position: 8
description: >-
  Every craftforJ configuration property, its default, and what turning each
  feature off changes.
_i18n_hash: f7e73354e4d9548e79122405c21ae275
---
craftforJ ist in `webforj.conf` konfiguriert. Die Eigenschaftsnamen sind die gleichen wie in [Spring](/docs/integrations/spring/overview), also setze sie in `application.properties`, wenn dort deine Konfiguration gespeichert ist.

## Erforderliche Eigenschaften {#required-properties}

| Eigenschaft | Typ | Standard | Beschreibung |
|-------------|-----|----------|--------------|
| **`webforj.debug`** | Boolean | `false` | Aktiviert den Debug-Modus. craftforJ benötigt ihn |
| **`webforj.devtools.craftforj.enabled`** | Boolean | `false` | Aktiviert craftforJ |

Beide Eigenschaften müssen aktiviert sein. Siehe [Sicherheit](/docs/craftforj/security#two-required-settings) für den Grund, warum craftforJ zwei Einstellungen anstelle von einer benötigt.

## Zugriff {#access}

| Eigenschaft | Typ | Standard | Beschreibung |
|-------------|-----|----------|--------------|
| **`webforj.devtools.craftforj.hosts-allowed`** | Liste oder String | nur Loopback | Client-Adressen, die über die Maschine hinaus erlaubt sind, auf der die App läuft |

Standardmäßig kann nur ein Browser auf derselben Maschine wie die App auf craftforJ zugreifen. Um anderen Maschinen den Zugriff zu ermöglichen, liste deren Adressen auf. Ein Eintrag, der mit `*` endet, entspricht einem Präfix, und ein einzelnes `*` entfernt die Einschränkung vollständig.

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning Ein Platzhalter erlaubt jedem, der auf deine App zugreifen kann
craftforJ liest und schreibt deine Projektquellen. Verwende `*` nur in einem Netzwerk, in dem du dir sicher bist, wer auf den Port zugreifen kann, z. B. in einem Container, den nur du nutzt. Verwende es niemals in einem gemeinsamen Netzwerk.
:::

## Projektstamm {#project-root}

| Eigenschaft | Typ | Standard | Beschreibung |
|-------------|-----|----------|--------------|
| **`webforj.devtools.project-root`** | String | erkannt | Das Verzeichnis, in dem deine Quellen gespeichert sind |

craftforJ bestimmt, wo sich dein Projekt befindet, basierend darauf, wie die App gestartet wurde. Unübliche Projektlayouts und einige Container-Setups können diese Erkennung beeinträchtigen. Wenn [App-Info](/docs/craftforj/app-info) den falschen Projektstamm meldet, setze ihn hier.

```ini title="webforj.conf"
webforj.devtools.project-root = "/home/me/projects/my-app"
```

In Spring setze denselben Schlüssel in `application.properties`.

:::info Umbenannt am 26.03
Diese Einstellung hieß zuvor `webforj.devtools.craftforj.project-root`. Der alte Schlüssel funktioniert weiterhin als Fallback, ist jedoch veraltet und gibt eine Warnung aus, wenn er verwendet wird. Wenn beide Schlüssel gesetzt sind, hat `webforj.devtools.project-root` Vorrang.
:::

## Feature-Flags {#feature-flags}

Jeder dieser Flags ist standardmäßig aktiviert. Das Deaktivieren eines Flags schränkt ein, was craftforJ tun darf.

| Eigenschaft | Das Deaktivieren entfernt |
|-------------|--------------------------|
| **`webforj.devtools.craftforj.source-changes`** | Das Zurückschreiben von Eigenschaftsänderungen nach Java und das Ändern des Zugriffs auf Routen |
| **`webforj.devtools.craftforj.stylesheet-changes`** | Das Speichern von Themen und Stilen in deinem Stylesheet |
| **`webforj.devtools.craftforj.ai.enabled`** | Den KI-Assistenten |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | Das eigenständige Schreiben von Java durch den Assistenten |

Das Deaktivieren eines Flags schaltet die Funktion für alle Benutzer dieser App aus. Die Einstellungen von craftforJ sind pro Entwickler und können nur weiter eingeschränkt werden, sodass ein Entwickler eine Fähigkeit nicht wieder aktivieren kann, die von der App deaktiviert wurde.

:::info Deaktivierte Funktionen bleiben sichtbar
Wenn ein Flag deaktiviert ist, bleibt die Steuerung in craftforJ und wird als nicht unterstützt durch die verbundene App gekennzeichnet.
:::

:::warning In der Produktion
Lasse `webforj.devtools.craftforj.enabled` ungesetzt. Siehe [Sicherheit](/docs/craftforj/security#in-production) für die komplette Checkliste.
:::
