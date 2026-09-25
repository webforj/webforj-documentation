---
title: Integraties
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

webforJ is ontworpen als een framework-onafhankelijke UI-laag voor Java-toepassingen. Het richt zich uitsluitend op het bouwen van rijke, component-gebaseerde gebruikersinterfaces, terwijl de beslissingen over de backend-architectuur volledig aan jou worden overgelaten. Deze duidelijke scheiding van verantwoordelijkheden stelt webforJ in staat om te werken met elke Java-technologiestack, van traditionele servlets tot moderne microservices.

## Architectuurfilosofie {#architecture-philosophy}

webforJ scheidt opzettelijk UI- en backend-zorg. In tegenstelling tot full-stack frameworks, die de structuur van je gehele app dicteren, biedt webforJ alleen wat je nodig hebt om geavanceerde gebruikersinterfaces te bouwen. Je keuze van persistentielaag, dependency injection-framework, beveiligingsimplementatie en service-architectuur blijft volledig onafhankelijk van je UI-technologie.

Deze aanpak erkent dat de meeste organisaties gevestigde backend-patronen, bestaande servicelagen en favoriete technologiestacks hebben. webforJ verbetert deze toepassingen met een modern UI-framework zonder architecturale veranderingen of technologie-migraties te vereisen. Je domeinlogica, datatoegangspatronen en beveiligingsimplementaties blijven exact werken zoals voorheen.

## Compatibiliteit backend-framework {#backend-framework-compatibility}

webforJ werkt met elk Java backend-framework of architectuurpatroon dat je al gebruikt. Of je nu bouwt op Jakarta EE, gebruik maakt van een microservices-architectuur, of werkt met een op maat gemaakt framework, webforJ biedt de UI-laag zonder je backend-ontwerp te verstoren.

Voor bepaalde populaire frameworks biedt webforJ specifieke integraties die de boilerplate-code verminderen en de ontwikkeling stroomlijnen. Deze integraties bieden voorzieningen zoals automatische dependency injection in UI-componenten, vereenvoudigde configuratie en framework-specifieke tooling-ondersteuning. Als je jouw framework hieronder niet in de lijst ziet staan, betekent dat niet dat webforJ daar niet mee werkt - het betekent simpelweg dat je de verbinding configureert met behulp van de standaard patronen van jouw framework en niet met een vooraf gebouwde integratie.

De integraties hieronder zijn geheel optioneel. Ze zijn er om de ontwikkelaarservaring te verbeteren bij het gebruik van specifieke frameworks, maar de kernfunctionaliteiten van webforJ werken identiek, ongeacht of je een integratie gebruikt of niet. Je backend-framework blijft verantwoordelijk voor het beheren van services, datatoegang en bedrijfslogica, terwijl webforJ de presentatielaag afhandelt.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
