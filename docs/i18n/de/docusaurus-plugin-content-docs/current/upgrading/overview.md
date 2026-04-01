---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 5b67f3c7842c20cbef9c77df8f3dd69a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Der Release-Zyklus von webforJ folgt einem strukturierten und vorhersehbaren Modell, um Stabilität, Leistung und kontinuierliche Innovation zu gewährleisten. Dieses Dokument bietet einen Überblick darüber, wie Releases geplant werden, welche Arten von Releases zu erwarten sind und wie Benutzer informiert und vorbereitet bleiben können.

## Arten von webforJ-Releases {#types-of-webforj-releases}

webforJ folgt einem strukturierten Release-Modell, das die folgenden Arten von Releases umfasst:

### 1. Haupt-Releases {#1-major-releases}
- Erscheinen jährlich.
- Führen bedeutende neue Funktionen, Verbesserungen und Erweiterungen ein.
- Können Konfigurationsänderungen oder Anpassungen bestehender Apps erfordern.
- Mit Versionierung wie **webforJ 20.00, webforJ 21.00 usw.** gekennzeichnet.

### 2. Neben-Releases {#2-minor-releases}
- Erscheinen mehrmals im Jahr (ungefähr alle sechs bis acht Wochen).
- Bieten inkrementelle Verbesserungen, Optimierungen und geringfügige neue Funktionen.
- Mit Versionierung wie **webforJ 20.01, webforJ 20.02 usw.** gekennzeichnet.

### 3. Patches und Fehlerbehebungs-Releases {#3-patches-and-bug-fix-releases}
- Werden veröffentlicht, wenn erforderlich.
- Behandeln kritische Fehler, Leistungsprobleme und Sicherheitsanfälligkeiten.
- Mit zusätzlichen Nummerierungen wie **webforJ 20.01.1, webforJ 20.01.2 usw.** gekennzeichnet.

## Was bei jedem Release zu erwarten ist {#what-to-expect-with-each-release}

### Verbesserungen der Funktionen {#feature-enhancements}
- Haupt- und Neben-Releases führen neue Funktionen, Optimierungen und Integrationen ein.
- Funktions-Roadmaps werden in den Release-Notizen geteilt, um Benutzern bei der Planung zu helfen.

:::info Abwärtskompatibilität
Während versucht wird, die Kompatibilität aufrechtzuerhalten, können Haupt-Releases Änderungen umfassen, die Anpassungen an Apps erfordern. Benutzer werden ermutigt, die Release-Notizen bezüglich abgelegter Funktionen zu überprüfen.
:::

### Sicherheitsupdates {#security-updates}
- Sicherheit hat Priorität, und kritische Sicherheitsanfälligkeiten werden in Patch-Releases so schnell wie möglich behoben.

:::tip Snapshot-Bauten
Snapshot-Bauten sind vor den meisten Releases verfügbar. Benutzer werden ermutigt, diese zu testen, um frühzeitig Probleme zu identifizieren und Feedback zu geben. Siehe den Artikel [Snapshots](/docs/configuration/snapshots), um zu erfahren, wie man webforJ-Snapshots verwendet und wo man sie erhält.
:::

## So bleiben Sie auf dem Laufenden {#how-to-stay-updated}

### Release-Notizen und Ankündigungen {#release-notes-and-announcements}
- Jedes Release wird von detaillierten [Release-Notizen](https://github.com/webforj/webforj/releases) begleitet, die neue Funktionen, Fehlerbehebungen und erforderliche Aktionen umreißen.
- Benutzer sollten den webforJ [Blog](../../blog) abonnieren, um rechtzeitige Updates zu erhalten.

:::tip Upgrade-Empfehlungen
Kunden sollten Upgrades basierend auf geschäftlichen Bedürfnissen und Stabilitätsanforderungen planen. Benutzer werden ermutigt, die neueste Version zu verwenden, um von Leistungsverbesserungen und neuen Funktionen zu profitieren.
:::

### Unterstützung und Kompatibilität {#support-and-compatibility}
- webforJ bietet Dokumentation und Upgrade-Anleitungen für Haupt-Releases.
- Community-Foren und Kundenservicetools stehen für Fehlersuche und Unterstützung zur Verfügung.

<DocCardList className="topics-section" />
