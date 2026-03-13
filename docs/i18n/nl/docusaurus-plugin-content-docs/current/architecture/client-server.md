---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: ae7a34d844eee10906ce2230f95a05cc
---
De volgende sectie bespreekt verschillende prestatiekwaliteiten en beste praktijken voor webforJ, evenals implementatiedetails voor het framework.

Bij het maken van een app in webforJ werken de client en de server samen om gegevens tussen de client en server te manipuleren, wat kan worden onderverdeeld in de brede categorieën:

## 1. Server naar client {#1-server-to-client}

webforJ-methoden zoals `setText()` vallen onder deze categorie. Een webforJ-app die op de server draait, stuurt gegevens naar de client zonder op een antwoord te wachten. webforJ optimaliseert automatisch batches van operaties in deze categorie om de prestaties te verbeteren.

## 2. Client naar server {#2-client-to-server}

Deze categorie omvat evenementverkeer, zoals een `Button.onClick()`-methode. Over het algemeen stuurt de client evenementen naar de server zonder op een antwoord te wachten. Het evenementobject bevat meestal aanvullende parameters die betrekking hebben op het evenement, zoals de hashcode. Omdat deze informatie aan de server wordt geleverd als onderdeel van de verzending van het evenement, is deze onmiddellijk beschikbaar voor het programma zodra het evenement is ontvangen.

## 3. Server naar client naar server (ronde reis) {#3-server-to-client-to-server-round-trip}

Ronde reizen worden uitgevoerd wanneer de app de client vraagt om dynamische informatie die niet op de server kan worden gecacht. Methoden zoals `Label.getText()` en `Checkbox.isChecked()` vallen in deze categorie. Wanneer een webforJ-app een regel uitvoert zoals `String title = myLabel.getText()`, komt deze volledig tot stilstand terwijl de server dat verzoek naar de client verzendt, en wacht deze vervolgens op de client om de reactie terug te sturen.

Als de app meerdere berichten naar de client verzendt die geen reactie vereisen (categorie 1), gevolgd door een enkel bericht dat een ronde reis vereist (categorie 3), moet de app wachten tot de client alle hangende berichten heeft verwerkt en vervolgens reageert op het laatste bericht dat een reactie vereist. In sommige gevallen kan dit een vertraging toevoegen. Als die ronde reis niet was geïntroduceerd, zou de client in staat zijn geweest om door te gaan met het verwerken van die achterstallige berichten terwijl de app die op de server draait verderging met nieuw werk.

## Verbeter prestaties {#improve-performance}

Het is mogelijk om de responsiviteit aanzienlijk te verbeteren door ronde reizen uit de derde categorie zoveel mogelijk te vermijden. Bijvoorbeeld, het veranderen van de onSelect-functionaliteit van de ComboBox van dit:

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
    // Haalt waarde uit het evenement
    int selected = ev.getSelectedIndex();
}
```

In de eerste snippet dwingt `ComboBox.getSelectedIndex()` die op het component wordt uitgevoerd, een ronde reis terug naar de client, wat een vertraging introduceert. In de tweede versie haalt het gebruik van de `ListSelectEvent.getSelectedIndex()`-methode van het evenement de waarde op die als onderdeel van het originele evenement aan de server werd geleverd.

## Caching {#caching}

webforJ optimaliseert de prestaties verder door gebruik te maken van caching. In het algemeen bestaan er twee soorten gegevens in deze context: gegevens die de gebruiker direct kan wijzigen en gegevens die niet door de gebruiker kunnen worden gewijzigd. In het eerste geval is het noodzakelijk om de server te raadplegen voor de informatie waarmee gebruikers direct zullen interageren.

Echter, informatie die niet door de gebruiker kan worden gewijzigd, kan worden gecached om extra prestatieverlies te voorkomen. Dit zorgt ervoor dat er onnodig geen ronde reis hoeft te worden gemaakt, wat zorgt voor een efficiëntere gebruikerservaring. webforJ optimaliseert apps op deze manier om optimale prestaties te waarborgen.

## Laadtijd {#loading-time}

Wanneer de gebruiker een webforJ-app start, laadt deze slechts een klein stuk (ongeveer 2,5 kB gzip) JavaScript om de sessie op te starten. Daarna downloadt het dynamisch individuele berichten, of stukken JavaScript, op aanvraag naarmate de app de bijbehorende functionaliteit gebruikt. Bijvoorbeeld, de server stuurt de client alleen de JavaScript die nodig is om een webforJ `Button` te bouwen, wanneer de app zijn eerste `Button`-component maakt. Dit resulteert in meetbare verbeteringen van de initiële laadtijd, wat leidt tot een betere gebruikerservaring.
