---
title: Aan de slag
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 6cb150ca0da866a142787024af9e2bd1
---
<!-- vale off -->
# Aan de slag <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ wordt meegeleverd met webforJ, dus je hoeft niets afzonderlijk te downloaden. Op deze pagina lees je wat je app nodig heeft voordat craftforJ verschijnt en hoe je het opent.

:::tip Al ingeschakeld in gegenereerde projecten
Projecten die zijn gemaakt met [startforJ](https://docs.webforj.com/startforj) of met een webforJ-[archetype](/docs/building-ui/archetypes/overview) hebben craftforJ al ingeschakeld. Als je daarmee bent begonnen, start je app en ga je verder naar [craftforJ openen](#opening-craftforj).
:::

## Vereisten {#requirements}

craftforJ wordt alleen aan een app gekoppeld als aan alle onderstaande voorwaarden is voldaan. Als aan één ervan niet wordt voldaan, verschijnt er niets op de pagina.

### De afhankelijkheid toevoegen {#add-the-dependency}

Voeg `webforj-devtools` aan je project toe als dat nog niet is gebeurd:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Foutopsporingsmodus en de craftforJ-vlag {#debug-mode-and-the-craftforj-flag}

Voeg de volgende eigenschappen toe aan je project. Gebruik je een standaard webforJ-app, stel de eigenschappen dan in `webforj.conf` in. Stel je ze in voor een webforJ-project dat [Spring](/docs/integrations/spring/overview) gebruikt, dan gebruik je `application.properties`.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ werkt alleen wanneer beide eigenschappen zijn ingeschakeld. Een app die met ingeschakelde foutopsporingsmodus in productie wordt genomen, stelt je broncodeboom dus niet beschikbaar.

### Een lokale browser en een ontwikkelaarslicentie {#a-local-browser-and-a-developer-license}

Open de app op de machine waarop deze wordt uitgevoerd en zorg dat je een geldige ontwikkelaarslicentie hebt. Als je craftforJ vanaf een andere machine wilt gebruiken, voeg je het adres ervan toe aan [`hosts-allowed`](/docs/craftforj/configuration#access).

Zodra dit is ingesteld, start je de app opnieuw en laad je de pagina opnieuw.

## craftforJ openen {#opening-craftforj}

Wanneer craftforJ actief is, verschijnt er een activeringsknop boven je app. Klik erop om craftforJ te openen, of druk ergens in de app op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>. Met dezelfde sneltoets sluit je craftforJ weer. Je kunt de activeringsknop naar een gewenste hoek slepen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

De tabbladen geven toegang tot de [componentenboom](/docs/craftforj/inspector), [routes](/docs/craftforj/routes), het [thema](/docs/craftforj/theme) en de [assistent](/docs/craftforj/ai). Instellingen en app-informatie vind je ernaast.


- **De activeringsknop** opent en sluit craftforJ. Wanneer craftforJ gesloten is, zit de knop niet in de weg.
- **De tabbladbalk** loopt langs de rand die het dichtst bij de app ligt en laat je wisselen tussen de weergaven van craftforJ.
- **Het venstermenu** bevat alle opties voor de plaatsing van craftforJ, zoals beschreven in [De plaatsing van craftforJ](#where-craftforj-sits).

:::info Sneltoetsen op macOS
craftforJ toont sneltoetsen met de modificatietoetsen van het platform waarop je werkt. <kbd>Alt</kbd> wordt dus weergegeven als <kbd>⌥</kbd> en <kbd>Ctrl</kbd> als <kbd>⌘</kbd>. Druk in craftforJ op <kbd>Shift</kbd> + <kbd>?</kbd> om de actuele lijst te bekijken.
:::

## De plaatsing van craftforJ {#where-craftforj-sits}

Standaard zweeft craftforJ boven je app. Sleep het ergens op de pagina, wijzig het formaat via een van de randen en minimaliseer het weer tot de activeringsknop als je de app even voor jezelf wilt hebben. Als je het naar een rand van de pagina sleept, wordt het daar vastgezet over de volledige hoogte of breedte. Elke rand onthoudt het formaat dat je eraan hebt gegeven. Sleep craftforJ weg van de rand om het weer te laten zweven.

:::info Vastzetten bedekt de app, maar past de indeling niet aan
craftforJ wordt boven op de pagina weergegeven. Je app wordt niet kleiner en niets erin schuift opzij. Alles wat zich onder craftforJ bevindt, is dus verborgen zolang craftforJ daar staat. Verplaats craftforJ naar een andere rand of haal het van de pagina af om te zien wat eronder staat.
:::

![craftforJ vastgezet aan de rechterkant van een app-pagina en bedekt die rand van de app](/img/craftforj/getting-started/docking.png#rounded-border)

Je kunt de app helemaal vrijhouden door craftforJ van de pagina af te slepen naar een eigen browservenster of -tabblad, bijvoorbeeld op een tweede monitor. craftforJ inspecteert je app nog steeds via de pagina waarmee je het hebt geopend, dus laat die pagina open. Als je die pagina verlaat of sluit, heeft craftforJ niets meer om te inspecteren totdat je de app opnieuw opent.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Kies een tabblad in plaats van een venster als je de gesplitste weergave van Chrome gebruikt. Daarmee zet je je app en craftforJ naast elkaar en kun je alleen echte tabbladen gebruiken. Klik met de rechtermuisknop op het tabblad van je app, voeg het toe aan een nieuwe gesplitste weergave en kies vervolgens het craftforJ-tabblad.

:::info Gesplitste weergave is een functie van Chrome
Chrome biedt de weergave naast elkaar, niet craftforJ. Andere browsers hebben geen vergelijkbare functie, dus wordt craftforJ daarin geopend op een gewoon tabblad waarnaar je kunt overschakelen. craftforJ werkt in beide gevallen hetzelfde.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::tip Verplaatsen terwijl de assistent schrijft
Als je craftforJ naar een ander venster verplaatst, wordt een antwoord dat nog wordt gegenereerd afgebroken. craftforJ vraagt dit eerst en alles wat tot dat moment in het gesprek is geschreven, blijft behouden.
:::

## Je eerste wijziging aanbrengen {#making-a-first-change}

1. Druk op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> om een component te selecteren.
2. Beweeg de muisaanwijzer over iets in je app en klik erop.
3. De component wordt geselecteerd in de boom en de zijbalk wordt gevuld met de eigenschappen ervan.
4. Wijzig een eigenschap. De actieve app wordt direct bijgewerkt.

De wijziging geldt alleen voor de app die op dat moment voor je staat. Je bestanden blijven onaangeroerd totdat je de wijziging hebt gecontroleerd en toegepast. Dit wordt beschreven in [Wijzigingen naar de broncode schrijven](/docs/craftforj/source-changes).

![craftforJ geopend naast een actieve app met een geselecteerde component](/img/craftforj/getting-started/first-open.png#rounded-border)

Als er helemaal niets verschijnt, doorloop dan de stappen in [Problemen oplossen](/docs/craftforj/troubleshooting).
