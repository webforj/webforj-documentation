---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 31ec5b4108bae52597797c3add587e4c
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Der webforJ Freigabzyklus folgt einem strukturierten und vorhersehbaren Modell, um Stabilität, Leistung und kontinuierliche Innovation zu gewährleisten. Dieses Dokument bietet einen Überblick darüber, wie die Releases geplant werden, welche Arten von Releases zu erwarten sind und wie Benutzer informiert und vorbereitet bleiben können.

<AISkillTip skill="webforj-upgrading-versions" />

## Arten von webforJ-Releases {#types-of-webforj-releases}

webforJ folgt einem strukturierten Freigabemodell, das die folgenden Arten von Releases umfasst:

### 1. Hauptversionen {#1-major-releases}
- Treten jährlich auf.
- Bringen bedeutende neue Funktionen, Verbesserungen und Erweiterungen.
- Können Konfigurationsänderungen oder Anpassungen bestehender Apps erfordern.
- Werden mit Versionierung wie **webforJ 20.00, webforJ 21.00 usw.** gekennzeichnet.

### 2. Nebenversionen {#2-minor-releases}
- Treten mehrmals im Jahr auf (ungefähr alle sechs bis acht Wochen).
- Bieten inkrementelle Verbesserungen, Optimierungen und kleinere neue Funktionen.
- Werden mit Versionierung wie **webforJ 20.01, webforJ 20.02 usw.** gekennzeichnet.

### 3. Patches und Fehlerbehebungen {#3-patches-and-bug-fix-releases}
- Werden bei Bedarf veröffentlicht.
- Beheben kritische Fehler, Leistungsprobleme und Sicherheitsanfälligkeiten.
- Werden mit zusätzlicher Nummerierung wie **webforJ 20.01.1, webforJ 20.01.2 usw.** gekennzeichnet.

## Was mit jeder Freigabe zu erwarten ist {#what-to-expect-with-each-release}

### Funktionserweiterungen {#feature-enhancements}
- Haupt- und Nebenversionen führen neue Funktionen, Optimierungen und Integrationen ein.
- Fahrpläne für Funktionen werden in den Versionshinweisen geteilt, um Benutzern zu helfen, im Voraus zu planen.

:::info Rückwärtskompatibilität
Obwohl Anstrengungen unternommen werden, um die Kompatibilität zu gewährleisten, können Hauptversionen Änderungen enthalten, die Anpassungen an Apps erfordern. Benutzer werden ermutigt, die Versionshinweise auf veraltete Funktionen zu überprüfen.
:::

### Sicherheitsupdates {#security-updates}
- Sicherheit hat oberste Priorität, und kritische Anfälligkeiten werden in Patch-Releases so schnell wie möglich behoben.

:::tip Snapshot-Bauten
Snapshot-Bauten sind vor den meisten Releases verfügbar. Benutzer werden ermutigt, gegen diese zu testen, um frühzeitig Probleme zu identifizieren und Feedback zu geben. Siehe den Artikel [Snapshots](/docs/configuration/snapshots), um zu erfahren, wie man webforJ-Snapshots verwendet und wo man sie bekommt.
:::

## Wie man auf dem Laufenden bleibt {#how-to-stay-updated}

### Versionshinweise und Ankündigungen {#release-notes-and-announcements}
- Jede Freigabe wird von detaillierten [Versionshinweisen](https://github.com/webforj/webforj/releases) begleitet, die neue Funktionen, Bugfixes und alle erforderlichen Maßnahmen umreißen.
- Benutzer sollten den webforJ [Blog](../../blog) abonnieren, um rechtzeitige Updates zu erhalten.

:::tip Upgrade-Empfehlungen
Kunden sollten Upgrades basierend auf geschäftlichen Anforderungen und Stabilitätsbedürfnissen planen. Benutzer werden ermutigt, auf der neuesten Version zu bleiben, um von Leistungsverbesserungen und neuen Funktionen zu profitieren.
:::

### Unterstützung und Kompatibilität {#support-and-compatibility}
- webforJ bietet Dokumentationen und Upgrade-Anleitungen für Hauptversionen.
- Community-Foren und Kundensupportkanäle sind für Fehlersuche und Unterstützung verfügbar.

<DocCardList className="topics-section" />
