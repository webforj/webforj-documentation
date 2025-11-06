---
sidebar_position: 1
title: Security Architecture
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: df2f795c6b65edc60adb39b549cb780b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Sicherheitsarchitektur <DocChip chip='since' label='25.10' />

Das webforJ-Sicherheitssystem basiert auf einer Grundlage von Schnittstellen und Entwurfsmustern, die flexible und erweiterbare Routen-Schutzmechanismen ermöglichen. In diesem Abschnitt wird erklärt, wie das grundlegende Sicherheitsframework funktioniert und wie man benutzerdefinierte Sicherheitslösungen durch Implementierung dieser Schnittstellen erstellen kann.

:::tip[Integration mit Spring]
Die meisten Anwendungen sollten die [Spring Security-Integration](/docs/security/getting-started) verwenden, da sie dies alles automatisch konfiguriert. Implementieren Sie benutzerdefinierte Sicherheitslösungen nur, wenn Sie spezifische Anforderungen haben oder Spring Boot nicht verwenden. Die Spring-Integration basiert auf derselben grundlegenden Architektur.
:::

Sie werden die zentralen Schnittstellen, das Evaluator-Chain-Muster, wie die Navigation abgefangen und ausgewertet wird, sowie verschiedene Ansätze zur Speicherung des Authentifizierungsstatus kennenlernen.

:::info[Fokus auf Architektur und Erweiterungspunkte]
Diese Leitfäden erklären die grundlegende Architektur und die Erweiterungspunkte, die Schnittstellen, die Sie implementieren, und wie sie zusammenarbeiten. Codebeispiele zeigen **einen möglichen Ansatz**, keine verbindlichen Anforderungen. Ihre Implementierung kann verschiedene Speichermechanismen (JWT, Datenbank, LDAP), verschiedene Verdrahtungsmuster oder unterschiedliche Authentifizierungsflüsse basierend auf Ihren Bedürfnissen verwenden.
:::

## Was Sie lernen werden {#what-youll-learn}

- **Grundlagenarchitektur**: Die zentralen Schnittstellen, die das Sicherheitsverhalten definieren und wie sie zusammenarbeiten
- **Navigation abfangen**: Wie das Sicherheitssystem Navigationsanfragen abfängt und Zugriffsregeln auswertet
- **Evaluator Chain-Muster**: Wie Sicherheitsregeln in Prioritätsreihenfolge unter Verwendung des Chain-of-Responsibility-Musters ausgewertet werden
- **Authentifizierungsspeicherung**: Verschiedene Ansätze zur Speicherung des Benutzer-Authentifizierungsstatus (Sitzungen, JWT, Datenbank usw.)
- **Vollständige Implementierung**: Ein funktionierendes Beispiel, das alle Komponenten miteinander verbunden zeigt

## Für wen ist das gedacht {#who-this-is-for}

Diese Leitfäden richten sich an Entwickler, die:

- Benutzerdefinierte Sicherheitsimplementierungen für Nicht-Spring-Anwendungen erstellen möchten
- Die grundlegende Architektur verstehen möchten, um Probleme zu beheben
- Benutzerdefinierte Authentifizierungsflüsse oder Autorisierungslogik implementieren möchten
- Sicherheitsevaluatoren mit domänenspezifischer Logik erstellen möchten
- In bestehende Authentifizierungssysteme (LDAP, OAuth, benutzerdefinierte Backends) integrieren möchten

## Voraussetzungen {#prerequisites}

Bevor Sie sich in diese Leitfäden vertiefen, sollten Sie:

- Den [Getting Started-Leitfaden](/docs/security/getting-started) abschließen, um Sicherheitskonzepte zu verstehen
- Sicherheitsanmerkungen aus dem [Annotations-Leitfaden](/docs/security/annotations) verstehen
- Mit dem Chain-of-Responsibility-Entwurfsmuster vertraut sein
- Erfahrung mit Java-Schnittstellen und Vererbung haben

## Themen {#topics}

<DocCardList className="topics-section" />
