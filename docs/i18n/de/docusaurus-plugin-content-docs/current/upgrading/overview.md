---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 1bdddfccaece385582aecb1b63967611
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

webforJ folgt einem strukturierten Veröffentlichungsmodell, das die folgenden Arten von Releases umfasst:

### 1. Hauptversionen {#1-major-releases}
- Finden jährlich statt.
- Führen bedeutende neue Funktionen, Verbesserungen und Erweiterungen ein.
- Können Änderungen an der Konfiguration oder Anpassungen bestehender Apps erfordern.
- Werden mit Versionierungen wie **webforJ 20.00, webforJ 21.00 usw.** gekennzeichnet.

### 2. Nebenversionen {#2-minor-releases}
- Finden mehrmals im Jahr statt (ungefähr alle sechs bis acht Wochen).
- Bieten inkrementelle Verbesserungen, Optimierungen und kleinere neue Funktionen.
- Werden mit Versionierungen wie **webforJ 20.01, webforJ 20.02 usw.** gekennzeichnet.

### 3. Patches und Fehlerbehebungs-Releases {#3-patches-and-bug-fix-releases}
- Werden bei Bedarf veröffentlicht.
- Beheben kritische Bugs, Leistungsprobleme und Sicherheitsanfälligkeiten.
- Werden mit zusätzlicher Nummerierung wie **webforJ 20.01.1, webforJ 20.01.2 usw.** gekennzeichnet.

## Was mit jedem Release zu erwarten ist {#what-to-expect-with-each-release}

### Funktionserweiterungen {#feature-enhancements}
- Haupt- und Nebenversionen führen neue Funktionen, Optimierungen und Integrationen ein.
- Fahrpläne für Funktionen werden in den Veröffentlichungsnotizen geteilt, um den Benutzern bei der Planung zu helfen.

:::info Rückwärtskompatibilität
Obwohl Anstrengungen unternommen werden, die Kompatibilität aufrechtzuerhalten, können Hauptversionen Änderungen enthalten, die Anpassungen an Apps erfordern. Benutzer werden ermutigt, die Veröffentlichungsnotizen auf deprecated Funktionen zu überprüfen.
:::

### Sicherheitsupdates {#security-updates}
- Die Sicherheit hat Priorität, und kritische Sicherheitsanfälligkeiten werden in Patch-Releases so schnell wie möglich behoben.

:::tip Snapshot-Bauten
Snapshot-Bauten sind vor den meisten Releases verfügbar. Benutzer werden ermutigt, diese zu testen, um Probleme frühzeitig zu identifizieren und Feedback zu geben.
:::

## Wie man auf dem Laufenden bleibt {#how-to-stay-updated}

### Veröffentlichungsnotizen und Ankündigungen {#release-notes-and-announcements}
- Jedes Release wird von detaillierten [Veröffentlichungsnotizen](https://github.com/webforj/webforj/releases) begleitet, die neue Funktionen, Bugfixes und erforderliche Maßnahmen auflisten.
- Benutzer sollten den webforJ [Blog](../../blog) abonnieren, um rechtzeitig informiert zu werden.

:::tip Upgrade-Empfehlungen
Kunden sollten Upgrades basierend auf den geschäftlichen Bedürfnissen und Stabilitätsanforderungen planen. Benutzer werden ermutigt, die neueste Version zu verwenden, um von Leistungsverbesserungen und neuen Funktionen zu profitieren.
:::

### Unterstützung und Kompatibilität {#support-and-compatibility}
- webforJ bietet Dokumentationen und Upgrade-Leitfäden für Hauptversionen.
- Community-Foren und Kunden-Support-Kanäle stehen für Problemlösungen und Unterstützung zur Verfügung.

<DocCardList className="topics-section" />
