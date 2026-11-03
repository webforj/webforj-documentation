---
title: Wijzigingen toepassen
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: de9ef40848946a35567c308c132dce0d
---
Een eigenschap wijzigen in craftforJ verandert de draaiende app en verder niets. Als je een wijziging wilt behouden, controleer je die en schrijf je deze naar het Java-bestand waar de eigenschap vandaan komt. Op deze pagina wordt die stap beschreven.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ schrijft naar je project
Houd je werk onder versiebeheer. Lees de diff voordat je de wijzigingen toepast, en lees deze opnieuw voordat je ze commit.
:::

## In afwachting van toepassing {#pending-changes}

Elke eigenschap die je wijzigt, wordt vastgelegd als een wijziging die nog moet worden toegepast. craftforJ toont hoeveel wijzigingen er klaarstaan. Deze wijzigingen blijven bewaard nadat je een pagina opnieuw laadt of van route verandert, omdat craftforJ ze opnieuw toepast wanneer je componenten opnieuw worden opgebouwd.

## Controleren en toepassen {#reviewing-and-applying}

Druk op <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> om het overzicht te openen. Wijzigingen zijn gegroepeerd op basis van het bestand waarin ze terechtkomen. Elke wijziging toont de eigenschap met de oude en nieuwe waarde en kan worden uitgevouwen om de diff van het bestand te bekijken. Als een wijziging een berekende waarde zou vervangen door een vaste waarde, waarschuwt craftforJ je en vermeldt het de expressie die wordt vervangen. Er wordt niets geschreven totdat je de wijzigingen toepast. Voordat je dat doet, kun je elke wijziging afzonderlijk terugdraaien of verwijderen.

![Het overzicht met wijzigingen gegroepeerd per bestand en één wijziging uitgevouwen om de diff te tonen](/img/craftforj/source-changes/review.png#rounded-border)

## Kiezen waar een wijziging wordt geschreven {#choosing-where-a-change-is-written}

Waar een wijziging wordt geschreven, bepaalt hoe ver deze reikt. Wanneer een component rechtstreeks in een view wordt opgebouwd, komt de wijziging in die view terecht. Wanneer de component in een herbruikbare klasse wordt opgebouwd, heb je twee opties:

- **Het gebruik** - de plek waar de component wordt gebruikt. Dit verandert alleen het scherm dat je op dat moment bekijkt. Dit is de standaardinstelling.
- **De definitie** - de plek waar de component wordt opgebouwd. Dit verandert elk scherm waarin de component wordt gebruikt.

Bij elke wijziging die nog moet worden toegepast, staat welke van de twee opties van toepassing is. Je kunt ook tussen beide opties schakelen. Sommige eigenschappen kunnen alleen in de definitie worden geschreven, omdat de component ze zelf instelt in plaats van ze van de aanroeper te ontvangen. craftforJ markeert deze eigenschappen voordat je de wijzigingen toepast.

## Nadat je wijzigingen hebt toegepast {#after-you-apply}

Het schrijven naar Java zorgt ervoor dat je app opnieuw wordt opgebouwd en gestart. craftforJ meldt de herstart, wacht tot deze is voltooid en maakt opnieuw verbinding met behoud van je selectie en de resterende wijzigingen die nog moeten worden toegepast. Toegepaste wijzigingen verdwijnen uit de lijst zodra ze in je bestanden staan.

Alleen op dit punt is je reload-configuratie van belang. craftforJ heeft geen live reload nodig om te werken, omdat alles wat je tijdens het inspecteren wijzigt meteen effect heeft in de draaiende app, zonder dat er opnieuw wordt opgebouwd. Schrijven naar de broncode werkt anders: het verandert een bestand waaruit je app is opgebouwd, dus de app moet opnieuw worden opgebouwd voordat de wijziging uit je code komt in plaats van uit craftforJ. Als [live reload](/docs/configuration/deploy-reload/overview) is geconfigureerd, gebeurt dit automatisch. Zo niet, start de app dan zelf opnieuw.

## Ongedaan maken en opnieuw uitvoeren <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

Druk in craftforJ op <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> om je laatste wijziging ongedaan te maken en op <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd> om deze opnieuw uit te voeren.

Ongedaan maken werkt voor wijzigingen die je aanbrengt in de draaiende app, van [eigenschappen bewerken](/docs/craftforj/inspector#reading-and-changing-properties) tot [thema-aanpassingen](/docs/craftforj/theme#adjusting-a-theme). Het werkt ook voor wijzigingen die craftforJ naar je project heeft geschreven, zoals toegepaste eigenschapswijzigingen, een [opgeslagen thema](/docs/craftforj/theme#saving-a-theme) of een [component die uit de broncode is verwijderd](/docs/craftforj/inspector#removing-a-component). Als je een geschreven wijziging ongedaan maakt, wordt het bestand teruggezet naar de eerdere versie. Is dat bestand Java, dan moet je app opnieuw worden opgebouwd voordat de wijziging zichtbaar is, net als [nadat je wijzigingen hebt toegepast](#after-you-apply).

<!-- TODO: add undo/redo recording -->

Als je meerdere keren op Ongedaan maken drukt, worden je wijzigingen één voor één teruggedraaid, van nieuw naar oud. Opnieuw uitvoeren herhaalt ze in de volgorde waarin je ze hebt gemaakt. Als je na het ongedaan maken een nieuwe wijziging aanbrengt, worden alle wijzigingen verwijderd die je opnieuw had kunnen uitvoeren.

### Wanneer ongedaan maken wordt geblokkeerd {#when-an-undo-is-blocked}

Als je een bestand in je eigen editor bewerkt en opslaat nadat craftforJ ernaar heeft geschreven, zou het ongedaan maken of opnieuw uitvoeren van die stap je bewerking overschrijven. craftforJ weigert dit, laat het bestand ongewijzigd en vermeldt:

```
Buiten craftforJ gewijzigd: ContactFormView.java. Draai die bewerking terug in je editor of sla deze stap over.
```

Je kunt op twee manieren verdergaan:

- Maak je bewerking ongedaan in je editor, zodat het bestand overeenkomt met wat craftforJ heeft geschreven, en probeer het opnieuw.
- Kies **Overslaan**. Het bestand blijft ongewijzigd en de stap wordt uit de geschiedenis verwijderd. Overslaan draait niets terug, dus eerdere stappen die hetzelfde bestand hebben gewijzigd, kunnen nog steeds worden geblokkeerd.

## Uitschakelen {#turning-it-off}

Je kunt het schrijven naar Java voor een app uitschakelen in de instellingen van craftforJ, of het helemaal verwijderen met de eigenschap [`source-changes`](/docs/craftforj/configuration#feature-flags). Als deze functie is uitgeschakeld, kun je eigenschappen nog steeds bewerken, maar blijven de wijzigingen alleen live.
