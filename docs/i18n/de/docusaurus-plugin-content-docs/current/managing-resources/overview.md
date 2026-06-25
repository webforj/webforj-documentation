---
sidebar_position: 1
title: Managing Resources
description: >-
  Manage JavaScript, CSS, and other assets in webforJ apps with declarative
  annotations, runtime injection APIs, and custom URL protocols.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: ddf6edc65adddf9e8eb952916a120e1f
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

Anwendungen verlassen sich auf verschiedene Arten von Ressourcen, wie JavaScript, CSS und Bilder. Dieses Dokument bietet eine umfassende technische Erkundung der Ressourcennutzungsmechanismen von webforJ und behandelt deklarative Annotations-, programmatische API-Methoden und die Nutzung benutzerdefinierter Protokolle.  

webforJ verfolgt einen modularen Ansatz für das Ressourcenmanagement und bietet mehrere Mechanismen, um unterschiedlichen Anforderungen von Anwendungen gerecht zu werden:  

- **Frontend-Bündler**: Bringen Sie npm-Pakete, Komponenten-Frameworks und Stylesheet-Sprachen über einen kompilierten Einstieg in die Anwendung. Dies ist der Standardweg für Frontend-Assets und erledigt alles, was die Annotations tun.  
- **Deklarative Annotations**: Binden Sie JavaScript- und CSS-Ressourcen auf Komponenten- oder App-Ebene ein, ohne dass ein Build-Schritt erforderlich ist.  
- **API-basierte dynamische Injektion**: Injezieren Sie Ressourcen zur Laufzeit, um dynamisches Verhalten der Anwendung zu ermöglichen.  
- **Benutzerdefinierte Protokolle**: Bieten Sie standardisierte Methoden für den Ressourcen Zugriff.  
- **Dateistreaming und kontrollierte Downloads**: Ermöglichen Sie die verwaltete Abrufung und Übertragung von Ressourcendateien.  

## Themen {#topics}

<DocCardList className="topics-section" />
