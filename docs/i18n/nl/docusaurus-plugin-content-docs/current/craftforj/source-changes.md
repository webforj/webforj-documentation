---
title: Wijzigingen toepassen
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: c421d594499c29fdae8dd88ca6e35e3d
---
Een eigenschap wijzigen in craftforJ verandert de actieve app en verder niets. Om een wijziging te behouden, controleer je die en schrijf je haar weg naar het Java-bestand waaruit ze afkomstig is. Op deze pagina wordt die stap beschreven.

<VideoPlayer video="craftforj/apply-changes" width={1920} height={992} title="Apply changes" />

:::warning craftforJ schrijft naar je project
Houd je werk onder versiebeheer. Bekijk de diff voordat je de wijzigingen toepast, en bekijk hem nogmaals voordat je commit.
:::

## Openstaande wijzigingen {#pending-changes}

Elke eigenschap die je wijzigt, wordt als openstaande wijziging vastgelegd, en craftforJ laat zien hoeveel wijzigingen er klaarstaan. Openstaande wijzigingen blijven behouden na het opnieuw laden van een pagina en na een routewijziging, omdat craftforJ ze opnieuw toepast wanneer je componenten opnieuw worden opgebouwd.

## Controleren en toepassen {#reviewing-and-applying}

Druk op <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> om de controle te openen. Wijzigingen zijn gegroepeerd per bestand waarin ze terechtkomen. Elke wijziging toont de eigenschap met de oude en nieuwe waarde, en kan worden uitgeklapt om de diff van het bestand te bekijken. Als een wijziging een berekende waarde zou vervangen door een vaste waarde, waarschuwt craftforJ je en vermeldt het de expressie die wordt vervangen. Er wordt niets weggeschreven totdat je de wijzigingen toepast. Voordat je dat doet, kun je elke wijziging afzonderlijk ongedaan maken of verwerpen.

![De controle met wijzigingen gegroepeerd per bestand en één wijziging uitgeklapt tot de diff](/img/craftforj/source-changes/review.png#rounded-border)

## Kiezen waar een wijziging wordt weggeschreven {#choosing-where-a-change-is-written}

Waar een wijziging wordt weggeschreven, bepaalt hoe ver die reikt. Wanneer een component rechtstreeks in een view wordt opgebouwd, komt de wijziging in die view terecht. Wanneer de component in een herbruikbare klasse wordt opgebouwd, heb je twee opties:

- **Het gebruik** - de plek waar de component wordt gebruikt; hiermee wijzig je alleen het scherm dat je voor je hebt. Dit is de standaardoptie.
- **De definitie** - de plek waar de component wordt opgebouwd; hiermee wijzig je elk scherm dat de component gebruikt.

Bij elke openstaande wijziging zie je welke van de twee opties van toepassing is en kun je ertussen wisselen. Sommige eigenschappen kunnen alleen bij de definitie worden weggeschreven, omdat de component ze zelf instelt in plaats van ze van de aanroeper te accepteren. craftforJ markeert die eigenschappen voordat je de wijzigingen toepast.

## Nadat je wijzigingen hebt toegepast {#after-you-apply}

Java wegschrijven zorgt ervoor dat je app opnieuw wordt opgebouwd en gestart. craftforJ meldt de herstart, wacht tot die klaar is en maakt opnieuw verbinding met behoud van je selectie en je resterende openstaande wijzigingen. Toegepaste wijzigingen verdwijnen uit de lijst met openstaande wijzigingen zodra ze in je bestanden staan.

Dit is het enige moment waarop je configuratie voor herladen van belang is. craftforJ heeft geen live reload nodig om te werken, omdat alles wat je tijdens het inspecteren wijzigt meteen effect heeft in de actieve app, zonder opnieuw opbouwen. Schrijven naar de broncode werkt anders: het wijzigt een bestand waaruit je app is opgebouwd, dus de app moet opnieuw worden opgebouwd voordat de wijziging uit je code komt in plaats van uit craftforJ. Als [live reload](/docs/configuration/deploy-reload/overview) is geconfigureerd, gebeurt dat vanzelf. Zo niet, start de app dan zelf opnieuw.

## Ongedaan maken en opnieuw uitvoeren <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

Druk in craftforJ op <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> om je laatste wijziging ongedaan te maken, en op <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd> om die opnieuw uit te voeren.

Ongedaan maken werkt voor wijzigingen die je in de actieve app aanbrengt, van [eigenschappen bewerken](/docs/craftforj/inspector#reading-and-changing-properties) tot [thema-aanpassingen](/docs/craftforj/theme#adjusting-a-theme). Het werkt ook voor wijzigingen die craftforJ naar je project heeft geschreven, zoals toegepaste eigenschapswijzigingen, een [opgeslagen thema](/docs/craftforj/theme#saving-a-theme) of een [component die uit de broncode is verwijderd](/docs/craftforj/inspector#removing-a-component). Een weggeschreven wijziging ongedaan maken zet het bestand terug naar de eerdere staat. Als dat bestand Java-code bevat, moet je app opnieuw worden opgebouwd voordat de wijziging zichtbaar wordt, net als [nadat je wijzigingen hebt toegepast](#after-you-apply).

<!-- TODO: add undo/redo recording -->

Als je meerdere keren op Ongedaan maken drukt, worden je wijzigingen één voor één teruggedraaid, te beginnen met de nieuwste. Opnieuw uitvoeren herhaalt ze in de volgorde waarin je ze hebt aangebracht. Als je na het ongedaan maken een nieuwe wijziging aanbrengt, kun je de eerdere wijzigingen niet meer opnieuw uitvoeren.

### Wanneer ongedaan maken wordt geblokkeerd {#when-an-undo-is-blocked}

Als je een bestand in je eigen editor bewerkt en opslaat nadat craftforJ erin heeft geschreven, zou het ongedaan maken of opnieuw uitvoeren van die stap je bewerking overschrijven. craftforJ weigert dat, laat het bestand ongewijzigd en vermeldt de bestandsnaam:

```
Buiten craftforJ gewijzigd: ContactFormView.java. Maak die bewerking ongedaan in je editor of sla deze stap over.
```

Je kunt op twee manieren verdergaan:

- Maak je bewerking ongedaan in je editor zodat het bestand overeenkomt met wat craftforJ heeft geschreven, en probeer het daarna opnieuw.
- Kies **Overslaan**. Het bestand blijft ongewijzigd en die stap wordt uit de geschiedenis verwijderd. Overslaan maakt niets ongedaan, dus eerdere stappen die hetzelfde bestand hebben gewijzigd, kunnen nog steeds worden geblokkeerd.

## Uitschakelen {#turning-it-off}

Je kunt het wegschrijven naar Java voor een app uitschakelen in de instellingen van craftforJ, of helemaal verwijderen met de eigenschap [`source-changes`](/docs/craftforj/configuration#feature-flags). Als een van beide is uitgeschakeld, kun je eigenschappen nog steeds bewerken, maar blijven die wijzigingen alleen actief.
