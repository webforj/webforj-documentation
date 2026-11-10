---
title: Integrationen
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
description: >-
  Combine webforJ with Spring Boot, Spring Data, Kotlin DSL, Webswing, JBang,
  and AI tooling while keeping your backend stack independent.
_i18n_hash: 2272fe4265260266e667c1b22703a3b6
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ ist das Full-Stack-Java-Framework für geschäftliche Web-Apps und unabhängig vom Backend. Sie entwickeln die gesamte App in Java – von Bildschirmen und Routing bis hin zu Formularen, Datenbindung und Routensicherheit. Persistenzschicht, Dependency Injection, Services und Backend-Architektur bleiben dabei Ihnen überlassen. Deshalb funktioniert webforJ mit jedem Java-Technologie-Stack, von traditionellen Servlets bis hin zu Spring Boot und Microservices.

## Architekturphilosophie {#architecture-philosophy}

webforJ deckt alles ab, was Ihre App benötigt, um Daten für Benutzer bereitzustellen und darauf zu reagieren: Komponenten, Layouts, Routing, Formulare mit Bindung und Validierung, durch Repositories gestützte Tabellen, Routensicherheit und Übersetzungen. Wie der Rest Ihres Systems funktioniert, gibt webforJ nicht vor. Die Wahl Ihrer Persistenzschicht, Ihres Dependency-Injection-Frameworks, Ihres Identitätsanbieters und Ihrer Service-Architektur bleibt unabhängig von webforJ.

Dieser Ansatz berücksichtigt, dass die meisten Unternehmen etablierte Backend-Muster, vorhandene Service-Schichten und bevorzugte Technologie-Stacks haben. webforJ bringt diese Apps ins Web, ohne Architekturänderungen oder Technologiemigrationen zu erfordern. Ihre Domänenlogik, Datenzugriffsmuster und Sicherheitsimplementierungen funktionieren weiterhin genau wie bisher.

## Kompatibilität mit Backend-Frameworks {#backend-framework-compatibility}

webforJ funktioniert mit jedem Java-Backend-Framework und jedem Architekturansatz, den Sie bereits verwenden. Ob Sie mit Jakarta EE entwickeln, eine Microservices-Architektur nutzen oder mit einem benutzerdefinierten Framework arbeiten – webforJ lässt sich integrieren, ohne Ihr Backend-Design zu beeinträchtigen.

Für einige beliebte Frameworks bietet webforJ spezielle Integrationen, die Boilerplate-Code reduzieren und die Entwicklung vereinfachen. Diese Integrationen bieten praktische Funktionen wie die automatische Dependency Injection in UI-Komponenten, eine vereinfachte Konfiguration und Unterstützung für Framework-spezifische Tools. Wenn Ihr Framework unten nicht aufgeführt ist, bedeutet das nicht, dass webforJ damit nicht funktioniert. Die Verbindung wird dann nach den Standardmustern Ihres Frameworks konfiguriert, statt eine vorgefertigte Integration zu verwenden.

Die folgenden Integrationen sind vollständig optional. Sie verbessern die Entwicklererfahrung bei der Verwendung bestimmter Frameworks, aber die Kernfunktionen von webforJ funktionieren unabhängig davon, ob Sie eine Integration verwenden. Ihr Backend-Framework verwaltet weiterhin Services, Datenzugriff und Geschäftslogik, während webforJ Bildschirme, Navigation und Benutzerinteraktionen übernimmt.

## Themen {#topics}

<DocCardList className="topics-section" />
