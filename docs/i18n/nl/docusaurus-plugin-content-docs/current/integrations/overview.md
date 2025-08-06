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

webforJ is ontworpen als een framework-onafhankelijke UI-laag voor Java-toepassingen. Het richt zich uitsluitend op het bouwen van rijke, componentgebaseerde gebruikersinterfaces, terwijl de beslissingen over de backend-architectuur volledig aan jou worden overgelaten. Deze duidelijke scheiding van verantwoordelijkheden stelt webforJ in staat om met elke Java-technologiestack te werken, van traditionele servlets tot moderne microservices.

## Architectuurfilosofie {#architecture-philosophy}

webforJ scheidt opzettelijk UI- en backend-zaken. In tegenstelling tot full-stack frameworks, die de complete app-structuur dicteren, biedt webforJ alleen hetgeen je nodig hebt om geavanceerde gebruikersinterfaces te bouwen. Jouw keuze van persistentielaag, dependency injection-framework, beveiligingsimplementatie, en service-architectuur blijft volledig onafhankelijk van jouw UI-technologie.

Deze benadering erkent dat de meeste organisaties gevestigde backend-patronen, bestaande servicelagen en voorkeurstechnologiestacks hebben. webforJ verbetert deze toepassingen met een modern UI-framework zonder dat architectonische wijzigingen of technologie-migraties nodig zijn. Jouw domeinlogica, gegevensaccesspatronen, en beveiligingsimplementaties blijven precies werken zoals vroeger.

## Compatibiliteit backend-framework {#backend-framework-compatibility}

webforJ werkt met elk Java backend-framework of architectuurpatroon dat je al gebruikt. Of je nu bouwt op Jakarta EE, een microservices-architectuur gebruikt, of werkt met een aangepast framework, webforJ biedt de UI-laag zonder in te grijpen op jouw backend-ontwerp.

Voor bepaalde populaire frameworks biedt webforJ specifieke integraties die boilerplate-code verminderen en de ontwikkeling versnellen. Deze integraties bieden gemakken zoals automatische dependency injection in UI-componenten, vereenvoudigde configuratie, en framework-specifieke toolingondersteuning. Als je jouw framework niet hieronder ziet staan, betekent dit niet dat webforJ er niet mee werkt - het betekent simpelweg dat je de verbinding configureert met behulp van de standaardpatronen van jouw framework in plaats van een kant-en-klare integratie te gebruiken.

De onderstaande integraties zijn volledig optioneel. Ze bestaan om de ontwikkelaarservaring te verbeteren bij het gebruik van specifieke frameworks, maar de kernfunctionaliteiten van webforJ werken identiek, of je nu een integratie gebruikt of niet. Jouw backend-framework blijft verantwoordelijk voor services, gegevensaccess en bedrijfslogica, terwijl webforJ de presentatie-laag afhandelt.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
