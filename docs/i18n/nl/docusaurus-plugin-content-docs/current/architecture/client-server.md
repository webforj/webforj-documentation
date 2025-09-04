---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: e5eafeb3f76c9a412d5a124f2eed2da8
---
De volgende sectie bespreekt verschillende prestatiedetails en best practices voor webforJ, evenals implementatiedetails voor het framework.

Bij het creëren van een applicatie in webforJ werken de client en de server samen om gegevens te manipuleren tussen client en server, welke kunnen worden onderverdeeld in de brede categorieën:

## 1. Server naar client {#1-server-to-client}

webforJ-methoden zoals `setText()` vallen in deze categorie. Een webforJ-applicatie die op de server draait, stuurt gegevens naar de client zonder te wachten op een antwoord. webforJ optimaliseert automatisch batches van bewerkingen in deze categorie om de prestaties te verbeteren.

## 2. Client naar server {#2-client-to-server}

Deze categorie behandelt evenementverkeer, zoals een `Button.onClick()`-methode. Voor het grootste deel stuurt de client evenementen naar de server zonder op een antwoord te wachten. Het evenementobject bevat doorgaans extra parameters met betrekking tot het evenement, zoals de hashcode. Omdat deze informatie aan de server wordt geleverd als onderdeel van het verzenden van het evenement, is het onmiddellijk beschikbaar voor het programma zodra het evenement is ontvangen.

## 3. Server naar client naar server (ronde reis) {#3-server-to-client-to-server-round-trip}

Ronde reizen worden uitgevoerd wanneer de applicatie de client vraagt om dynamische informatie die niet op de server kan worden gecached. Methoden zoals `Label.getText()` en `Checkbox.isChecked()` vallen in deze categorie. Wanneer een webforJ-applicatie een regel uitvoert zoals `String title = myLabel.getText()`, komt deze volledig tot stilstand terwijl de server dat verzoek naar de client stuurt en vervolgens wacht tot de client het antwoord terugstuurt.

Als de applicatie verschillende berichten naar de client stuurt die geen antwoord vereisen (categorie 1), gevolgd door een enkel bericht dat een ronde reis vereist (categorie 3), moet de applicatie wachten tot de client alle lopende berichten heeft verwerkt en vervolgens reageert op het laatste bericht dat een reactie vereist. In sommige gevallen kan dit een vertraging met zich meebrengen. Als die ronde reis niet was geïntroduceerd, zou de client in staat zijn geweest om door te gaan met het verwerken van die achterstallige berichten terwijl de applicatie op de server doorging met nieuw werk.

## Verbeter de prestaties {#improve-performance}

Het is mogelijk om de responsiviteit van de applicatie aanzienlijk te verbeteren door ronde reizen in de derde categorie zoveel mogelijk te vermijden. Bijvoorbeeld, het wijzigen van de onSelect-functionaliteit van de ComboBox van dit:

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
    // Krijgt waarde van het evenement
    int selected = ev.getSelectedIndex();
}
```

In de eerste snippet dwingt `ComboBox.getSelectedIndex()` die wordt uitgevoerd op het component een ronde reis terug naar de client, wat een vertraging met zich meebrengt. In de tweede versie haalt het gebruik van de `ListSelectEvent.getSelectedIndex()`-methode van het evenement de waarde op die aan de server is geleverd als onderdeel van het oorspronkelijke evenement.

## Caching {#caching}

webforJ optimaliseert de prestaties verder door gebruik te maken van caching. In het algemeen bestaan er twee soorten gegevens in deze context: gegevens die de gebruiker direct kan wijzigen en gegevens die niet door de gebruiker kunnen worden gewijzigd. In het eerste geval, bij het ophalen van de informatie waarmee gebruikers direct kunnen interageren, is het noodzakelijk om de server naar deze informatie te vragen.

Echter, informatie die niet door de gebruiker kan worden gewijzigd, kan worden gecached om aanvullende prestatieverliezen te vermijden. Dit zorgt ervoor dat een ronde reis niet onnodig hoeft te worden gemaakt, wat zorgt voor een efficiëntere gebruikerservaring. webforJ optimaliseert applicaties op deze manier om optimale prestaties te garanderen.

## Laadtijd {#loading-time}

Wanneer de gebruiker een webforJ-app start, laadt het slechts een klein deel (ongeveer 2,5 kB gzip) van JavaScript om de sessie op te starten. Daarna downloadt het dynamisch individuele berichten of stukken JavaScript op aanvraag zodra de applicatie de bijbehorende functionaliteit gebruikt. Bijvoorbeeld, de server stuurt alleen de JavaScript die nodig is om een webforJ `Button` te bouwen één keer — wanneer de applicatie zijn eerste `Button`-component aanmaakt. Dit resulteert in meetbare verbeteringen van de initiële laadtijd, wat resulteert in een betere gebruikerservaring.
