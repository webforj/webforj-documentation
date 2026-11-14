---
title: Aan de slag
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 5d30f5dd80df008a05be5d4617c40369
---
<!-- vale off -->
# Aan de slag <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ wordt meegeleverd met webforJ, dus je hoeft niets afzonderlijk te downloaden. Op deze pagina lees je wat je app nodig heeft voordat craftforJ verschijnt en hoe je het opent.

:::tip Al ingeschakeld in gegenereerde projecten
Projecten die zijn gemaakt met [startforJ](https://docs.webforj.com/startforj) of met een webforJ [archetype](/docs/building-ui/archetypes/overview) hebben craftforJ al ingeschakeld. Als je met een van deze projecten bent begonnen, start je app en ga je verder naar [craftforJ openen](#opening-craftforj).
:::

## Vereisten {#requirements}

craftforJ wordt alleen aan een app gekoppeld als aan alle volgende voorwaarden is voldaan. Als ook maar aan één voorwaarde niet wordt voldaan, verschijnt er niets op de pagina.

### De afhankelijkheid toevoegen {#add-the-dependency}

Voeg `webforj-devtools` aan je project toe als dat er nog niet in staat:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Debugmodus en de craftforJ-vlag {#debug-mode-and-the-craftforj-flag}

Voeg de volgende eigenschappen aan je project toe. Als je een standaard webforJ-app hebt, stel je de eigenschappen in `webforj.conf` in. Voor een webforJ-project dat [Spring](/docs/integrations/spring/overview) gebruikt, stel je de eigenschappen in `application.properties` in.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ werkt alleen als beide eigenschappen zijn ingeschakeld. Een app die in productie wordt genomen terwijl de debugmodus is ingeschakeld, stelt je broncodeboom dus niet bloot.

### Een lokale browser en een ontwikkelaarslicentie {#a-local-browser-and-a-developer-license}

Open de app op de machine waarop deze wordt uitgevoerd en zorg ervoor dat je een geldige ontwikkelaarslicentie hebt. Als je craftforJ vanaf een andere machine wilt bereiken, voeg je het adres daarvan toe aan [`hosts-allowed`](/docs/craftforj/configuration#access).

Zodra dit is ingesteld, start je de app opnieuw en laad je de pagina opnieuw.

## craftforJ openen {#opening-craftforj}

Wanneer craftforJ actief is, verschijnt er een knop boven je app. Klik erop om craftforJ te openen, of druk ergens in de app op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>. Met dezelfde sneltoets sluit je craftforJ weer. Je kunt de knop naar een gewenste hoek slepen.

<VideoPlayer video="craftforj/getting-started" width={1920} height={984} title="Aan de slag" />

De tabbladen geven toegang tot de [componentenboom](/docs/craftforj/inspector), [routes](/docs/craftforj/routes), het [thema](/docs/craftforj/theme) en de [assistent](/docs/craftforj/ai). De instellingen en app-informatie vind je ernaast.

- **De knop** opent en sluit craftforJ. Wanneer craftforJ gesloten is, zit de knop niet in de weg.
- **De tabstrook** loopt langs de rand die het dichtst bij de app ligt en laat je wisselen tussen de weergaven van craftforJ.
- **Het venstermenu** bevat alle opties voor de positie van craftforJ, die worden beschreven in [De positie van craftforJ](#where-craftforj-sits).

:::info Sneltoetsen op macOS
craftforJ gebruikt de modificatietoetsen van het platform waarop je werkt. <kbd>Alt</kbd> wordt daarom weergegeven als <kbd>⌥</kbd> en <kbd>Ctrl</kbd> als <kbd>⌘</kbd>. Druk in craftforJ op <kbd>Shift</kbd> + <kbd>?</kbd> om de actuele lijst te bekijken.
:::

## De positie van craftforJ {#where-craftforj-sits}

Standaard zweeft craftforJ boven je app. Sleep het naar een willekeurige plek op de pagina, wijzig de grootte via een rand en minimaliseer het weer naar de knop wanneer je de app voor jezelf wilt hebben. Als je het naar een paginarand sleept, wordt het daar vastgezet over de volledige hoogte of breedte. Aan elke rand blijft de grootte behouden die je eraan hebt gegeven. Sleep het weg van de rand om het weer te laten zweven.

:::info Vastzetten bedekt de app, maar past de indeling niet aan
craftforJ wordt boven op de pagina weergegeven. Je app wordt niet kleiner en niets erin schuift opzij. Alles wat onder craftforJ staat, is dus verborgen zolang het daar staat. Verplaats craftforJ naar een andere rand of haal het van de pagina om te zien wat eronder staat.
:::

![craftforJ vastgezet aan de rechterkant van een app-pagina, waardoor die rand van de app wordt bedekt](/img/craftforj/getting-started/docking.png#rounded-border)

Om de app helemaal niet meer te bedekken, verplaats je craftforJ van de pagina naar een eigen browservenster of -tabblad. Dat is handig als je een tweede monitor gebruikt. craftforJ inspecteert je app nog steeds via de pagina waarmee het is geopend, dus laat die pagina open. Navigeer weg van die pagina of sluit deze, en craftforJ heeft niets meer om te inspecteren totdat je de app opnieuw opent.

<VideoPlayer video="craftforj/undock-window" width={1920} height={1048} title="Venster losmaken" />

Kies een tabblad in plaats van een venster als je de gesplitste weergave van Chrome gebruikt. Hiermee worden je app en craftforJ naast elkaar weergegeven en er kunnen alleen echte tabbladen in worden geplaatst. Klik met de rechtermuisknop op het tabblad van je app, voeg het toe aan een nieuwe gesplitste weergave en kies vervolgens het craftforJ-tabblad.

:::info Gesplitste weergave is een functie van Chrome
Chrome biedt de weergave naast elkaar, niet craftforJ. Andere browsers hebben geen vergelijkbare functie, dus wordt craftforJ daarin geopend in een gewoon tabblad waarnaar je kunt overschakelen. craftforJ zelf werkt in beide gevallen hetzelfde.
:::

<VideoPlayer video="craftforj/split-view" width={1920} height={1080} title="Gesplitste weergave" />

:::tip Verplaatsen terwijl de assistent schrijft
Als je craftforJ naar een ander venster verplaatst, wordt een antwoord dat nog wordt gegenereerd afgebroken. craftforJ vraagt het eerst, en alles wat tot dat moment is geschreven, blijft in de chat staan.
:::

## Je eerste wijziging aanbrengen {#making-a-first-change}

1. Druk op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> om een component te selecteren.
2. Beweeg de aanwijzer over iets in je app en klik erop.
3. De boom selecteert die component en de zijbalk toont de eigenschappen ervan.
4. Wijzig een eigenschap. De actieve app wordt direct bijgewerkt.

De wijziging geldt alleen voor de app die op dat moment voor je staat. Je bestanden blijven onaangeroerd totdat je de wijziging controleert en toepast. Dit wordt beschreven in [Wijzigingen naar de broncode schrijven](/docs/craftforj/source-changes).

![craftforJ geopend naast een actieve app met een geselecteerde component](/img/craftforj/getting-started/first-open.png#rounded-border)

Als er helemaal niets verschijnt, volg je de stappen in [Probleemoplossing](/docs/craftforj/troubleshooting).
