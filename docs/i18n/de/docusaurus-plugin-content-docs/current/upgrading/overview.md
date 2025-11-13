---
sidebar_position: 1
title: Upgrade-Anleitungen
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 6adbad314378e90356ad6602cc52de5a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Der webforJ Veröffentlichungszyklus folgt einem strukturierten und vorhersagbaren Modell, um Stabilität, Leistung und kontinuierliche Innovation zu gewährleisten. Dieses Dokument bietet einen Überblick darüber, wie Releases geplant werden, welche Arten von Releases zu erwarten sind und wie Benutzer informiert und vorbereitet bleiben können.

## Arten von webforJ Releases {#types-of-webforj-releases}

webforJ folgt einem strukturierten Veröffentlichungsmodell, das die folgenden Arten von Releases umfasst:

### 1. Hauptversionen {#1-major-releases}
- Finden jährlich statt.
- Führen bedeutende neue Funktionen, Verbesserungen und Erweiterungen ein.
- Können Konfigurationsänderungen oder Anpassungen bestehender Apps erfordern.
- Identifiziert mit Versionierung wie **webforJ 20.00, webforJ 21.00 usw.**

### 2. Nebenversionen {#2-minor-releases}
- Finden mehrmals im Jahr statt (etwa alle sechs bis acht Wochen).
- Bieten inkrementelle Verbesserungen, Optimierungen und kleinere neue Funktionen.
- Identifiziert mit Versionierung wie **webforJ 20.01, webforJ 20.02 usw.**

### 3. Patches und Bugfix-Releases {#3-patches-and-bug-fix-releases}
- Werden bei Bedarf veröffentlicht.
- Beheben kritische Fehler, Leistungsprobleme und Sicherheitsanfälligkeiten.
- Identifiziert mit zusätzlichen Nummerierungen wie **webforJ 20.01.1, webforJ 20.01.2 usw.**

## Was man mit jedem Release erwarten kann {#what-to-expect-with-each-release}

### Funktionserweiterungen {#feature-enhancements}
- Haupt- und Nebenversionen führen neue Funktionen, Optimierungen und Integrationen ein.
- Funktions-Roadmaps werden in den Release-Notizen geteilt, um Benutzern zu helfen, im Voraus zu planen.

:::info Abwärtskompatibilität
Während Anstrengungen unternommen werden, um die Kompatibilität aufrechtzuerhalten, können Hauptversionen Änderungen enthalten, die Anpassungen an Apps erfordern. Benutzer werden ermutigt, die Release-Notizen auf veraltete Funktionen zu überprüfen.
:::

### Sicherheitsupdates {#security-updates}
- Sicherheit hat Priorität, und kritische Schwachstellen werden in Patch-Releases so schnell wie möglich behoben.

:::tip Snapshot-Bauten
Snapshot-Bauten sind vor den meisten Releases verfügbar. Benutzer werden ermutigt, diese zu testen, um frühzeitig Probleme zu identifizieren und Feedback zu geben.
:::

## Wie man auf dem Laufenden bleibt {#how-to-stay-updated}

### Release-Notizen und Ankündigungen {#release-notes-and-announcements}
- Jeder Release wird von detaillierten [Release-Notizen](https://github.com/webforj/webforj/releases) begleitet, die neue Funktionen, Bugfixes und erforderliche Maßnahmen umreißen.
- Benutzer sollten den webforJ [Blog](../../blog) abonnieren, um rechtzeitig informiert zu bleiben.

:::tip Upgrade-Empfehlungen
Kunden sollten Upgrades basierend auf den geschäftlichen Anforderungen und Stabilitätsanforderungen planen. Benutzer werden ermutigt, auf der neuesten Version zu bleiben, um von Leistungsverbesserungen und neuen Funktionen zu profitieren.
:::

### Unterstützung und Kompatibilität {#support-and-compatibility}
- webforJ bietet Dokumentation und Upgrade-Leitfäden für Hauptversionen.
- Community-Foren und Kundensupport-Kanäle stehen für Problemlösungen und Unterstützung zur Verfügung.

<DocCardList className="topics-section" />
