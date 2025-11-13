---
title: Integrationen
hide_table_of_contents: true
sidebar_class_name: new-content
hide_giscus_comments: true
_i18n_hash: 987f1fb9ef8aa9e50ff4ec00320d2dd7
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ ist als framework-agnostische UI-Schicht für Java-Anwendungen konzipiert. Es konzentriert sich ausschließlich auf den Aufbau reichhaltiger, komponentenbasierter Benutzeroberflächen und überlässt die Entscheidungen zur Backend-Architektur ganz Ihnen. Diese klare Trennung der Anliegen ermöglicht es webforJ, mit jedem Java-Technologie-Stack zu arbeiten, von traditionellen Servlets bis hin zu modernen Microservices.

## Architekturphilosophie {#architecture-philosophy}

webforJ trennt bewusst UI- und Backend-Anliegen. Im Gegensatz zu Full-Stack-Frameworks, die die gesamte Struktur Ihrer App vorgeben, bietet webforJ nur das, was Sie zum Erstellen anspruchsvoller Benutzeroberflächen benötigen. Ihre Wahl der Persistenzschicht, des Frameworks für Dependency Injection, der Sicherheitsimplementierung und der Servicearchitektur bleibt vollständig unabhängig von Ihrer UI-Technologie.

Dieser Ansatz erkennt an, dass die meisten Organisationen etablierte Backend-Muster, vorhandene Service-Schichten und bevorzugte Technologie-Stacks haben. webforJ verbessert diese Anwendungen mit einem modernen UI-Framework, ohne dass architektonische Änderungen oder Technologie-Migrationen erforderlich sind. Ihre Domänenlogik, Datenzugriffsmuster und Sicherheitsimplementierungen funktionieren weiterhin genau wie zuvor.

## Kompatibilität mit Backend-Frameworks {#backend-framework-compatibility}

webforJ funktioniert mit jedem Java-Backend-Framework oder Architektur-Muster, das Sie bereits verwenden. Egal, ob Sie auf Jakarta EE aufbauen, eine Microservices-Architektur verwenden oder mit einem benutzerdefinierten Framework arbeiten, webforJ bietet die UI-Schicht, ohne Ihr Backend-Design zu stören.

Für bestimmte beliebte Frameworks bietet webforJ spezifische Integrationen, die Boilerplate-Code reduzieren und die Entwicklung vereinfachen. Diese Integrationen bieten Annehmlichkeiten wie automatische Dependency Injection in UI-Komponenten, vereinfachte Konfiguration und framework-spezifische Tool-Unterstützung. Wenn Ihr Framework nicht in der Liste unten aufgeführt ist, bedeutet das nicht, dass webforJ nicht damit funktioniert – es bedeutet lediglich, dass Sie die Verbindung mithilfe der Standardmuster Ihres Frameworks konfigurieren, anstatt eine vorgefertigte Integration zu verwenden.

Die untenstehenden Integrationen sind völlig optional. Sie existieren, um das Entwicklererlebnis beim Einsatz bestimmter Frameworks zu verbessern, aber die Kernfunktionen von webforJ funktionieren identisch, ob Sie eine Integration verwenden oder nicht. Ihr Backend-Framework verwaltet weiterhin Dienste, Datenzugriff und Geschäftslogik, während webforJ die Präsentationsschicht übernimmt.

## Themen {#topics}

<DocCardList className="topics-section" />
