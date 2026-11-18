---
sidebar_position: 1
title: Ressourcen verwalten
description: >-
  Verwalten Sie JavaScript, CSS und andere Assets in webforJ-Anwendungen mit
  deklarativen Annotationen, APIs zur Laufzeitinjektion und benutzerdefinierten
  URL-Protokollen.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_title_hash: a71e8aa6e3aa0dbb165119f7b68682a3
_i18n_description_hash: 1c0c8794c1ad64d279c287d021118800
_i18n_hash: 7aee2ee29fd227575e12f1450422d0a1
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

Anwendungen sind auf verschiedene Arten von Ressourcen angewiesen, wie z.B. JavaScript, CSS und Bilder. Dieses Dokument bietet eine umfassende technische Untersuchung der Ressourcenverwaltungsmechanismen von webforJ und behandelt deklarative Anmerkungen, programmgesteuerte API-Methoden und die Nutzung benutzerdefinierter Protokolle.

webforJ verfolgt einen modularen Ansatz zur Ressourcenverwaltung und bietet mehrere Mechanismen, um unterschiedlichen Anforderungen von Anwendungen gerecht zu werden:

- **Frontend-Bündler**: Fügen Sie npm-Pakete, Komponentenframeworks und Stylesheet-Sprachen durch einen kompilierten Einstieg in die Anwendung ein. Dies ist der Standardweg für Frontend-Ressourcen und übernimmt alles, was die Anmerkungen tun.
- **Deklarative Anmerkungen**: Binden Sie JavaScript- und CSS-Ressourcen auf Komponenten- oder Anwendungsebene ein, ohne dass ein Build-Schritt erforderlich ist.
- **API-basierte dynamische Injektion**: Injizieren Sie Ressourcen zur Laufzeit, um dynamisches Verhalten der Anwendung zu ermöglichen.
- **Benutzerdefinierte Protokolle**: Bieten Sie standardisierte Methoden für den Ressourcen zugriff.
- **Dateistreaming und kontrollierte Downloads**: Erleichtern Sie die verwaltete Beschaffung und Übertragung von Ressourcen Dateien.

## Themen {#topics}

<DocCardList className="topics-section" />
