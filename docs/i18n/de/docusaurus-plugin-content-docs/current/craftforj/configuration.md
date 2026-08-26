---
title: Konfiguration
sidebar_position: 8
description: >-
  Every craftforJ configuration property, its default, and what turning each
  feature off changes.
_i18n_hash: 025fb1766af8cbcb741f6f353bdf6523
---
craftforJ ist in `webforj.conf` konfiguriert. Die Eigenschaftsnamen sind dieselben wie in [Spring](/docs/integrations/spring/overview), also setzen Sie sie in `application.properties`, wenn sich dort Ihre Konfiguration befindet.

## Erforderliche Eigenschaften {#required-properties}

| Eigenschaft | Typ | Standard | Beschreibung |
|-------------|-----|----------|--------------|
| **`webforj.debug`** | Boolean | `false` | Aktiviert den Debug-Modus. craftforJ benötigt dies |
| **`webforj.devtools.craftforj.enabled`** | Boolean | `false` | Aktiviert craftforJ |

Beide Eigenschaften müssen aktiviert sein. Siehe [Sicherheit](/docs/craftforj/security#two-required-settings) für den Grund, warum craftforJ zwei Einstellungen und nicht nur eine benötigt.

## Zugriff {#access}

| Eigenschaft | Typ | Standard | Beschreibung |
|-------------|-----|----------|--------------|
| **`webforj.devtools.craftforj.hosts-allowed`** | Liste oder String | nur Loopback | Client-Adressen, die über die Maschine hinaus erlaubt sind, auf der die App läuft |

Standardmäßig kann nur ein Browser auf derselben Maschine wie die App auf craftforJ zugreifen. Um anderen Maschinen den Zugriff zu ermöglichen, listen Sie deren Adressen auf. Ein Eintrag, der mit `*` endet, entspricht einem Präfix, und ein einzelnes `*` entfernt die Einschränkung vollständig.

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning Ein Wildcard erlaubt jedem, der Ihre App erreichen kann
craftforJ liest und schreibt Ihre Projektquellen. Verwenden Sie `*` nur in einem Netzwerk, von dem Sie sicher sind, dass nur autorisierte Personen den Port erreichen können, z. B. in einem Container, den nur Sie verwenden. Verwenden Sie es niemals in einem gemeinsamen Netzwerk.
:::

## Projektstamm {#project-root}

| Eigenschaft | Typ | Standard | Beschreibung |
|-------------|-----|----------|--------------|
| **`webforj.devtools.craftforj.project-root`** | String | erkannt | Das Verzeichnis, in dem sich Ihre Quellen befinden |

craftforJ bestimmt, wo sich Ihr Projekt befindet, anhand der Art und Weise, wie die App gestartet wurde. Ungewöhnliche Projektstrukturen und einige Container-Setups verhindern diese Erkennung. Wenn [App-Info](/docs/craftforj/app-info) den falschen Projektstamm meldet, setzen Sie ihn hier.

## Feature-Flags {#feature-flags}

Jedes dieser Flags ist standardmäßig aktiviert. Das Deaktivieren eines Flags schränkt ein, was craftforJ tun darf.

| Eigenschaft | Das Deaktivieren entfernt |
|-------------|---------------------------|
| **`webforj.devtools.craftforj.source-changes`** | Das Zurückschreiben von Eigenschaftsänderungen in Java und das Ändern des Zugriffes auf Routen |
| **`webforj.devtools.craftforj.stylesheet-changes`** | Das Speichern von Themen und Stilen in Ihrem Stylesheet |
| **`webforj.devtools.craftforj.ai.enabled`** | Den KI-Assistenten |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | Das eigenständige Schreiben von Java durch den Assistenten |

Das Deaktivieren eines Flags schaltet die Funktion für alle Benutzer der App aus. Die craftforJ-Einstellungen gelten pro Entwickler und können nur weiter eingeschränkt werden, sodass ein Entwickler eine Fähigkeit nicht wieder aktivieren kann, die von der App deaktiviert wurde.

:::info Deaktivierte Funktionen bleiben sichtbar
Wenn ein Flag deaktiviert ist, bleibt die Steuerung in craftforJ und wird als nicht unterstützt durch die verbundene App gekennzeichnet.
:::

:::warning In der Produktion
Lassen Sie `webforj.devtools.craftforj.enabled` ungesetzt. Siehe [Sicherheit](/docs/craftforj/security#in-production) für die vollständige Checkliste.
:::
