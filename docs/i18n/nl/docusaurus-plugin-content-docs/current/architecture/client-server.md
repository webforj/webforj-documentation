---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: a69f444ce2e5a9dea37304d466f4e6ac
---
De volgende sectie bespreekt verschillende prestatiekwaliteiten en best practices voor webforJ, evenals implementatiedetails voor het framework.

Bij het maken van een app in webforJ werken de client en de server samen om gegevens te manipuleren, die tussen client en server kunnen worden onderverdeeld in de brede categorieën:

## 1. Server naar client {#1-server-to-client}

webforJ-methoden zoals `setText()` vallen in deze categorie. De webforJ-app die op de server draait, verzendt gegevens naar de client zonder op een antwoord te wachten. webforJ optimaliseert automatisch batches van operaties in deze categorie om de prestaties te verbeteren.

## 2. Client naar server {#2-client-to-server}

Deze categorie dekt evenementverkeer, zoals een `Button.onClick()`-methode. Voor het grootste deel stuurt de client evenementen naar de server zonder op een antwoord te wachten. Het evenementobject bevat meestal aanvullende parameters met betrekking tot het evenement, zoals de hashcode. Omdat deze informatie als onderdeel van de aflevering van het evenement aan de server wordt geleverd, is deze onmiddellijk beschikbaar voor het programma zodra het evenement is ontvangen.

## 3. Server naar client naar server (ronde reis) {#3-server-to-client-to-server-round-trip}

Ronde reizen worden uitgevoerd wanneer de app de client vraagt om dynamische informatie die niet op de server kan worden opgeslagen. Methoden zoals `Label.getText()` en `Checkbox.isChecked()` vallen in deze categorie. Wanneer een webforJ-app een regel uitvoert zoals `String title = myLabel.getText()`, komt deze volledig tot stilstand terwijl de server dat verzoek naar de client stuurt en vervolgens wacht op de client om de reactie terug te sturen.

Als de app verschillende berichten naar de client verzendt die geen antwoord vereisen (categorie 1), gevolgd door een enkel bericht dat een ronde reis vereist (categorie 3), moet de app wachten tot de client alle uitstaande berichten heeft verwerkt en vervolgens reageert op het laatste bericht dat een antwoord vereist. In sommige gevallen kan dit een vertraging toevoegen. Als die ronde reis niet was geïntroduceerd, zou de client in staat zijn geweest om door te gaan met het verwerken van die achterstallige berichten terwijl de app die op de server draait, doorging met nieuw werk.

## Verbeter prestaties {#improve-performance}

Het is mogelijk om de responsiviteit aanzienlijk te verbeteren door ronde reizen uit de derde categorie zoveel mogelijk te vermijden. Bijvoorbeeld, door de `onSelect` functionaliteit van de ComboBox te veranderen van dit:

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
  // Haalt waarde op uit het evenement
  int selected = ev.getSelectedIndex();
}
```

In de eerste snippet zorgt `ComboBox.getSelectedIndex()` die op het component wordt uitgevoerd voor een ronde reis terug naar de client, wat een vertraging introduceert. In de tweede versie haalt het gebruik van de `ListSelectEvent.getSelectedIndex()`-methode van het evenement de waarde op die als onderdeel van het originele evenement naar de server is geleverd.

## Caching {#caching}

webforJ optimaliseert de prestaties verder door gebruik te maken van caching. Over het algemeen zijn er twee soorten gegevens in deze context: gegevens die de gebruiker direct kan wijzigen en gegevens die door de gebruiker niet kunnen worden gewijzigd. In het eerste geval, wanneer informatie wordt opgehaald waar gebruikers direct mee zullen interageren, is het noodzakelijk om de server te raadplegen voor deze informatie.

Echter, informatie die niet door de gebruiker kan worden gewijzigd, kan worden gecached om extra prestatieverliezen te vermijden. Dit zorgt ervoor dat er geen onnodige ronde reizen gemaakt hoeven te worden, wat zorgt voor een efficiëntere gebruikerservaring. webforJ optimaliseert apps op deze manier om optimale prestaties te garanderen.

## Laadtijd {#loading-time}

Wanneer de gebruiker een webforJ-app start, laadt deze slechts een klein deel (ongeveer 2,5 kB gzip) van JavaScript om de sessie op te starten. Daarna downloadt het dynamisch individuele berichten, of delen van JavaScript, op aanvraag terwijl de app de bijbehorende functionaliteit gebruikt. Bijvoorbeeld, de server stuurt de client alleen de JavaScript die nodig is om een webforJ `Button` te bouwen, en dit gebeurt één keer, wanneer de app de eerste `Button`-component aanmaakt. Dit resulteert in meetbare verbeteringen van de initiële laadtijd, wat leidt tot een betere gebruikerservaring.
