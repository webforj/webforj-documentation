---
title: Integraties
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

webforJ is het full-stack Java-framework voor zakelijke webapps en is onafhankelijk van de backend. Je bouwt de hele app in Java, van schermen en routing tot formulieren, databinding en routebeveiliging, terwijl je zelf kiest voor je persistence-laag, dependency injection, services en backendarchitectuur. Daarom werkt webforJ met elke Java-technologiestack, van traditionele servlets tot Spring Boot en microservices.

## Architectuurfilosofie {#architecture-philosophy}

webforJ biedt alles wat je app nodig heeft om gegevens aan gebruikers te tonen en ermee aan de slag te gaan: componenten, lay-outs, routing, formulieren met databinding en validatie, tabellen op basis van repositories, routebeveiliging en vertaling. Het schrijft niet voor hoe de rest van je systeem werkt. Je keuze voor een persistence-laag, dependency-injectionframework, identiteitsprovider en servicearchitectuur blijft onafhankelijk van webforJ.

Deze aanpak houdt rekening met het feit dat de meeste organisaties bestaande backendpatronen, servicelagen en voorkeursstacks hebben. webforJ brengt deze apps naar het web zonder architectuurwijzigingen of technologiemigraties te vereisen. Je domeinlogica, patronen voor gegevenstoegang en beveiligingsimplementaties blijven precies werken zoals voorheen.

## Compatibiliteit met backendframeworks {#backend-framework-compatibility}

webforJ werkt met elk Java-backendframework of architectuurpatroon dat je al gebruikt. Of je nu bouwt op Jakarta EE, een microservicesarchitectuur gebruikt of met een aangepast framework werkt, webforJ kan ermee worden verbonden zonder je backendontwerp te beïnvloeden.

Voor enkele populaire frameworks biedt webforJ specifieke integraties die boilerplatecode verminderen en de ontwikkeling stroomlijnen. Deze integraties bieden handige functies, zoals automatische dependency injection in UI-componenten, vereenvoudigde configuratie en ondersteuning voor frameworkspecifieke tools. Staat je framework hieronder niet vermeld, dan betekent dat niet dat webforJ er niet mee werkt. Het betekent dat je de verbinding configureert met de standaardpatronen van je framework, in plaats van een vooraf gebouwde integratie te gebruiken.

De onderstaande integraties zijn volledig optioneel. Ze zijn bedoeld om de ontwikkelervaring te verbeteren bij het gebruik van specifieke frameworks, maar de kernfuncties van webforJ werken hetzelfde, ongeacht of je een integratie gebruikt. Je backendframework blijft verantwoordelijk voor services, gegevenstoegang en bedrijfslogica, terwijl webforJ de schermen, navigatie en gebruikersinteractie afhandelt.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
