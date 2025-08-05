---
title: Integrations
hide_table_of_contents: true
sidebar_class_name: new-content
hide_giscus_comments: true
_i18n_hash: 366829e324b872af8247a509f9c55783
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ wurde als framework-unabhängige UI-Schicht für Java-Anwendungen entwickelt. Es konzentriert sich ausschließlich auf den Aufbau von reichhaltigen, komponentenbasierten Benutzeroberflächen, während die Entscheidungen über die Backend-Architektur ganz bei Ihnen liegen. Diese klare Trennung der Anliegen ermöglicht es webforJ, mit jedem Java-Technologiestack zu arbeiten, von traditionellen Servlets bis hin zu modernen Mikrodiensten.

## Architekturphilosophie {#architecture-philosophy}

webforJ trennt absichtlich UI- und Backend-Angelegenheiten. Im Gegensatz zu Full-Stack-Frameworks, die die gesamte App-Struktur diktieren, bietet webforJ nur das, was Sie zum Erstellen anspruchsvoller Benutzeroberflächen benötigen. Ihre Wahl der Persistenzschicht, des Dependency-Injection-Frameworks, der Sicherheitsimplementierung und der Servicearchitektur bleibt völlig unabhängig von Ihrer UI-Technologie.

Dieser Ansatz erkennt an, dass die meisten Organisationen etablierte Backend-Muster, bestehende Service-Schichten und bevorzugte Technologiestacks haben. webforJ verbessert diese Anwendungen mit einem modernen UI-Framework, ohne dass architektonische Änderungen oder Technologiemigrationen erforderlich sind. Ihre Domänenlogik, Datenzugriffsmuster und Sicherheitsimplementierungen funktionieren weiterhin genau wie zuvor.

## Kompatibilität mit Backend-Frameworks {#backend-framework-compatibility}

webforJ funktioniert mit jedem Java-Backend-Framework oder Architektur-Muster, das Sie bereits verwenden. Egal, ob Sie auf Jakarta EE aufbauen, eine Mikrodienstarchitektur verwenden oder mit einem benutzerdefinierten Framework arbeiten, webforJ bietet die UI-Schicht, ohne Ihr Backend-Design zu stören.

Für bestimmte beliebte Frameworks bietet webforJ spezifische Integrationen, die Boilerplate-Code reduzieren und die Entwicklung vereinfachen. Diese Integrationen bieten Annehmlichkeiten wie automatische Dependency-Injection in UI-Komponenten, vereinfachte Konfiguration und unterstützende Werkzeuge für das jeweilige Framework. Wenn Ihr Framework unten nicht aufgeführt ist, bedeutet das nicht, dass webforJ nicht damit funktioniert - es bedeutet einfach, dass Sie die Verbindung unter Verwendung der Standardmuster Ihres Frameworks anstelle einer vorgefertigten Integration konfigurieren.

Die unten aufgeführten Integrationen sind völlig optional. Sie dienen dazu, das Entwicklererlebnis bei der Verwendung bestimmter Frameworks zu verbessern, aber die Kernfunktionen von webforJ funktionieren identisch, unabhängig davon, ob Sie eine Integration verwenden oder nicht. Ihr Backend-Framework verwaltet weiterhin Dienste, Datenzugriff und Geschäftslogik, während webforJ die Präsentationsschicht übernimmt.

## Themen {#topics}

<DocCardList className="topics-section" />
