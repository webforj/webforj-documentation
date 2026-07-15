---
sidebar_position: 5
title: Client/Server Interaction
description: >-
  Understand how webforJ batches server-to-client calls, avoids costly round
  trips, and uses caching and on-demand chunks for performance.
_i18n_hash: 893b34ce2601ff273d03ba4091b7bc51
---
De volgende sectie bespreekt verschillende prestatiekualitas en beste praktijken voor webforJ, evenals implementatiedetails voor het framework.

Bij het maken van een app in webforJ werken de client en de server samen om gegevens tussen de client en de server te manipuleren, wat kan worden onderverdeeld in de brede categorieën:

## 1. Server naar client {#1-server-to-client}

webforJ-methoden zoals `setText()` vallen in deze categorie. Een webforJ-app die op de server draait, verstuurt gegevens naar de client zonder te wachten op een reactie. webforJ optimaliseert automatisch batches van bewerkingen in deze categorie om de prestaties te verbeteren.

## 2. Client naar server {#2-client-to-server}

Deze categorie omvat evenementverkeer, zoals een `Button.onClick()`-methode. In de meeste gevallen verstuurt de client evenementen naar de server zonder te wachten op een reactie. Het evenementobject bevat doorgaans aanvullende parameters met betrekking tot het evenement, zoals de hashcode. Omdat deze informatie als onderdeel van het verzenden van het evenement naar de server wordt geleverd, is deze onmiddellijk beschikbaar voor het programma zodra het evenement is ontvangen.

## 3. Server naar client naar server (ronde reis) {#3-server-to-client-to-server-round-trip}

Ronde reizen worden uitgevoerd wanneer de app de client vraagt om enkele dynamische informatie die niet op de server kan worden opgeslagen. Methoden zoals `Label.getText()` en `Checkbox.isChecked()` vallen in deze categorie. Wanneer een webforJ-app een regel uitvoert zoals `String title = myLabel.getText()`, komt deze tot stilstand terwijl de server dat verzoek naar de client verzendt, en wacht vervolgens op de client om de reactie terug te sturen.

Als de app verschillende berichten naar de client verzendt die geen reactie vereisen (categorie 1), gevolgd door een enkel bericht dat een ronde reis vereist (categorie 3), moet de app wachten tot de client alle wachtende berichten heeft verwerkt en vervolgens reageert op het laatste bericht dat een reactie vereist. In sommige gevallen kan dit een vertraging veroorzaken. Als die ronde reis niet was geïntroduceerd, zou de client in staat zijn geweest om door te gaan met het verwerken van die achterstallige berichten terwijl de app die op de server draait, verder ging met nieuw werk.

## Verbeter de prestaties {#improve-performance}

Het is mogelijk om de responsiviteit aanzienlijk te verbeteren door de ronde reizen van de derde categorie zoveel mogelijk te vermijden. Bijvoorbeeld, het wijzigen van de onSelect-functionaliteit van de ComboBox van dit:

```java
private void comboBoxSelect(ListSelectEvent ev){
  ComboBox component = (ComboBox) ev.getComponent();

  // Gaat naar de client
  int selected = component.getSelectedIndex();
}
```

naar het volgende:

```java
private void comboBoxSelect(ListSelectEvent ev){
  //Haalt waarde op uit het evenement
  int selected = ev.getSelectedIndex();
}
```

In de eerste snippet dwingt `ComboBox.getSelectedIndex()` die op de component wordt uitgevoerd, een ronde reis terug naar de client in, wat vertraging introduceert. In de tweede versie haalt het gebruik van de methode `ListSelectEvent.getSelectedIndex()` van het evenement de waarde op die als onderdeel van het oorspronkelijke evenement naar de server is verzonden.

## Caching {#caching}

webforJ optimaliseert de prestaties verder door caching te gebruiken. In het algemeen bestaan er in deze context twee soorten gegevens: gegevens die de gebruiker direct kan veranderen en gegevens die niet door de gebruiker kunnen worden gewijzigd. In het eerste geval, wanneer je de informatie ophaalt waarmee gebruikers direct interactie hebben, is het noodzakelijk om de server om deze informatie te vragen.

Echter, informatie die niet door de gebruiker kan worden gewijzigd, kan worden cached om extra prestatieproblemen te vermijden. Dit zorgt ervoor dat er geen onnodige ronde reizen hoeven te worden gemaakt, wat zorgt voor een efficiëntere gebruikerservaring. webforJ optimaliseert apps op deze manier om optimale prestaties te verzekeren.

## Laadtijd {#loading-time}

Wanneer de gebruiker een webforJ-app start, laadt deze 
slechts een klein deel (ongeveer 2.5 kB gzip) van JavaScript om de sessie op te starten.
Daarna downloadt het dynamisch individuele berichten, of stukjes
JavaScript, op aanvraag terwijl de app de bijbehorende
functionaliteit gebruikt. Bijvoorbeeld, de server stuurt alleen de JavaScript
die nodig is om een webforJ `Button` te bouwen één keer, wanneer de app zijn
eerste `Button`-component aanmaakt. Dit resulteert in meetbare verbeteringen van de initiële
laadtijd, wat leidt tot een betere gebruikerservaring.
