---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: ed7cdbde8cee6b173108326dfa7fce2a
---
De volgende sectie bespreekt verschillende prestatiekwaliteiten en best practices voor webforJ, evenals implementatiedetails voor het framework.

Bij het creëren van een applicatie in webforJ werken de client en de server samen om gegevens tussen client en server te manipuleren, wat kan worden onderverdeeld in de brede categorieën:

## 1. Server naar client {#1-server-to-client}

webforJ-methoden zoals `setText()` vallen in deze categorie. Een webforJ-applicatie die op de server draait, verzendt gegevens naar de client zonder op een respons te wachten. webforJ optimaliseert automatisch batches van operaties in deze categorie om de prestaties te verbeteren.

## 2. Client naar server {#2-client-to-server}

Deze categorie dekt evenementverkeer, zoals een `Button.onClick()`-methode. Voor het grootste deel verzendt de client evenementen naar de server zonder op een respons te wachten. Het evenementobject bevat doorgaans aanvullende parameters met betrekking tot het evenement, zoals de hashcode. Aangezien deze informatie als onderdeel van de act van het afleveren van het evenement aan de server wordt geleverd, is het onmiddellijk beschikbaar voor het programma zodra het evenement is ontvangen.

## 3. Server naar client naar server (round trip) {#3-server-to-client-to-server-round-trip}

Round trips worden uitgevoerd wanneer de applicatie de client om dynamische informatie vraagt die niet op de server kan worden gecached. Methoden zoals `Label.getText()` en `Checkbox.isChecked()` vallen in deze categorie. Wanneer een webforJ-applicatie een regel uitvoert zoals `String title = myLabel.getText()`, komt het volledig tot stilstand terwijl de server dat verzoek naar de client verzendt en vervolgens wacht op de client om de respons terug te sturen.

Als de applicatie meerdere berichten naar de client verzendt die geen respons vereisen (categorie 1), gevolgd door een enkel bericht dat een round trip vereist (categorie 3), moet de applicatie wachten tot de client alle wachtende berichten verwerkt en vervolgens reageert op het laatste bericht dat een respons vereist. In sommige gevallen kan dit een vertraging toevoegen. Als die round trip niet was geïntroduceerd, zou de client door kunnen gaan met het verwerken van die achterstallige berichten terwijl de applicatie die op de server draait verder ging met nieuw werk.

## Prestaties verbeteren {#improve-performance}

Het is mogelijk om de responsiviteit van de applicatie aanzienlijk te verbeteren door round trips in de derde categorie zoveel mogelijk te vermijden. Bijvoorbeeld, het veranderen van de onSelect-functionaliteit van de ComboBox van dit:

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
    // Verkrijgt waarde van het evenement
    int selected = ev.getSelectedIndex();
}
```

In de eerste snippet dwingt `ComboBox.getSelectedIndex()` die op de component wordt uitgevoerd een round trip terug naar de client, wat een vertraging introduceert. In de tweede versie haalt het gebruik van de `ListSelectEvent.getSelectedIndex()`-methode van het evenement de waarde op die als onderdeel van het originele evenement aan de server werd geleverd.

## Caching {#caching}

webforJ optimaliseert de prestaties verder door gebruik te maken van caching. Over het algemeen bestaan er twee soorten gegevens in deze context: gegevens die de gebruiker direct kan wijzigen en gegevens die niet door de gebruiker kunnen worden gewijzigd. In het eerste geval is het noodzakelijk om de server te raadplegen voor deze informatie wanneer klanten er direct mee zullen omgaan.

Informatie die niet door de gebruiker kan worden gewijzigd, kan echter worden gecached om extra prestatieverlies te voorkomen. Dit zorgt ervoor dat er niet onnodig een round trip hoeft te worden gemaakt, wat een efficiëntere gebruikerservaring biedt. webforJ optimaliseert applicaties op deze manier om optimale prestaties te waarborgen.

## Laadtijd {#loading-time}

Wanneer de gebruiker een webforJ-app start, laadt hij slechts een klein stukje (ongeveer 2,5 kB gzip) JavaScript om de sessie op te starten. Daarna downloadt het dynamisch individuele berichten of stukjes JavaScript op aanvraag naarmate de applicatie de bijbehorende functionaliteit gebruikt. Bijvoorbeeld, de server verzendt alleen de JavaScript die nodig is om een webforJ `Button` te bouwen — wanneer de applicatie zijn eerste `Button`-component maakt. Dit resulteert in meetbare verbeteringen van de initiële laadtijd, wat resulteert in een betere gebruikerservaring.
