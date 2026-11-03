---
title: Componenten inspecteren
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 585425968807283ea392911908fbd030
---
De Inspector toont de componentenstructuur die je Java-code heeft opgebouwd. Een `Composite` wordt weergegeven als de klasse die je hebt geschreven, met de onderliggende componenten die je eraan hebt toegevoegd in de volgorde waarin webforJ ze bevat. Zo komt de structuur in craftforJ overeen met de structuur in je broncode.

![De componentenstructuur met een geselecteerde en gemarkeerde component in de actieve app](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Een component selecteren {#selecting-a-component}

Druk op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> en klik op een component om deze op de pagina te selecteren. craftforJ selecteert de overeenkomende node in de structuur. Als je de muisaanwijzer op een node in de structuur plaatst, wordt de betreffende component op de pagina gemarkeerd. Zo kun je in beide richtingen tussen het scherm en de structuur navigeren.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Druk op <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd> om de structuur te doorzoeken. Als je een zoekterm tussen slashes zet, wordt deze als een reguliere expressie behandeld. Klik met de rechtermuisknop op een node om de beschikbare acties te openen. Je kunt de broncode openen of de node aan de [assistent](/docs/craftforj/ai) doorgeven.

## Eigenschappen bekijken en wijzigen {#reading-and-changing-properties}

Als je een component selecteert, worden de eigenschappen ervan in de zijbalk weergegeven, gegroepeerd op basis van hun effect. Welke eigenschappen een component heeft, hangt af van de component; sommige eigenschappen zijn alleen-lezen. Eigenschappen die niet goed als platte tekst kunnen worden weergegeven, krijgen een editor die geschikt is voor hun waarde. Een wijziging wordt direct van kracht in de actieve app.

:::info Livebewerkingen wijzigen je bestanden niet
Een bewerking van een eigenschap verandert alleen de app die je voor je hebt. De wijziging opnemen in je broncode is een afzonderlijke stap die je bewust uitvoert. Dit wordt beschreven in [Wijzigingen naar de broncode schrijven](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## De broncode van een component bekijken {#viewing-the-source-of-a-component}

Je kunt elke component terugvolgen naar de Java-code waarmee deze is opgebouwd. Standaard wordt de broncode in craftforJ alleen-lezen geopend, op de regel waarmee de component is aangemaakt. Je kunt craftforJ zo configureren dat de broncode op dezelfde regel in je editor wordt geopend. Als een component niet aan een regel kan worden gekoppeld, meldt craftforJ dat in plaats van een lege viewer te openen.

![De broncodeviewer op de regel waarmee de geselecteerde component is aangemaakt](/img/craftforj/inspector/source-viewer.png#rounded-border)

## Een component verwijderen <DocChip chip='since' label='26.03' /> {#removing-a-component}

Klik met de rechtermuisknop op een component in de structuur en kies **Component verwijderen** om twee manieren te zien waarop je de component kunt verwijderen. Met de ene manier verwijder je deze uit de app die je voor je hebt, met de andere uit je project.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/delete-components.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

### Alleen uit de browser verwijderen {#removing-from-the-browser-only}

Met **Alleen uit de browser verwijderen** verwijder je de component uit de actieve app, zonder je Java-broncode te wijzigen. Gebruik deze optie om te zien hoe een lay-out eruitziet zonder een bepaalde component, voordat je ergens definitief voor kiest.

De verwijdering is tijdelijk. De component verschijnt opnieuw zodra de app die weergave opnieuw opbouwt vanuit je broncode, bijvoorbeeld na het herladen van de pagina.

### Uit de broncode verwijderen {#removing-from-source}

Met **Uit de broncode verwijderen** bewerk je het Java-bestand waarin de component is aangemaakt, zodat deze ook na het opnieuw opbouwen van de app verwijderd blijft. De wijziging wordt meteen doorgevoerd in plaats van toegevoegd aan de [openstaande wijzigingen](/docs/craftforj/source-changes#pending-changes). Er is dus geen beoordelingsstap voordat de wijziging in je bestand wordt doorgevoerd.

Als je Java-code schrijft, wordt de app opnieuw opgebouwd en gestart, net als bij het [toepassen van wijzigingen](/docs/craftforj/source-changes#after-you-apply). Als [live reload](/docs/configuration/deploy-reload/overview) is geconfigureerd, gebeurt dit automatisch. Zo niet, dan moet je de app zelf opnieuw starten.

Neem bijvoorbeeld een formulier met een knop **Opslaan** en een knop **Annuleren**, waarbij **Opslaan** een thema en een kliklistener heeft:

```java
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button save = new Button("Save");
    save.setTheme(ButtonTheme.PRIMARY);
    save.onClick(event -> {
      save.setEnabled(false);
    });
    Button cancel = new Button("Cancel");
    layout.add(save, cancel);
  }
}
```

Als je **Opslaan** selecteert en **Uit de broncode verwijderen** kiest, blijft het volgende over. De declaratie, het thema, de listener en de plaats van de knop in de aanroep van `add()` zijn verwijderd, net als de import van `ButtonTheme`, die nergens anders meer wordt gebruikt. Nadat de app opnieuw is opgebouwd, staat **Annuleren** er nog steeds:

```java
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button cancel = new Button("Cancel");
    layout.add(cancel);
  }
}
```

Een verwijdering omvat alleen de component zelf en niets daarbuiten:

- Als je een container verwijdert, worden ook de componenten erin verwijderd.
- Code die alleen nodig was om de component aan te maken, te configureren en toe te voegen, wordt opgeschoond. Dit geldt ook voor velden en imports die nergens anders worden gebruikt.
- Code die elders nog nodig is, blijft behouden. Als een ander deel van de klasse de component nog gebruikt, bijvoorbeeld in de listener van een andere component, koppelt craftforJ de component alleen los en behoudt het de code waarmee deze wordt aangemaakt en geconfigureerd.
- Als je een instantie van een herbruikbare component verwijdert, zoals een `Composite` die je zelf hebt geschreven, wordt alleen die ene toepassing verwijderd. De klasse die de component definieert, wordt niet verwijderd of gewijzigd.

craftforJ schrijft het bestand alleen weg als het resultaat nog compileert. Bestanden worden nooit verwijderd.

### Wanneer verwijderen niet beschikbaar is {#when-removal-isnt-available}

**Uit de broncode verwijderen** is alleen beschikbaar als Java-code schrijven is toegestaan in zowel de [configuratie](/docs/craftforj/configuration#feature-flags) van de app als de instelling **Wijzigingen in Java-broncode** in craftforJ. Deze optie is niet beschikbaar voor componenten die in Kotlin zijn opgebouwd of voor componenten die craftforJ niet aan een regel broncode kan koppelen.

Sommige componenten kunnen helemaal niet uit de broncode worden verwijderd:

- Routes, die de router plaatst op basis van hun `@Route`
- Het frame
- Componenten die een andere component als onderdeel van zichzelf opbouwt. Verwijder in plaats daarvan de omhullende component.

craftforJ kan een verwijdering ook weigeren als de broncode van de component niet kan worden gevonden of als niet duidelijk is welke code moet worden verwijderd. Als één regel bijvoorbeeld meerdere componenten van hetzelfde type aanmaakt, maakt die regel niet duidelijk welke component je hebt geselecteerd.

Als een verwijdering wordt geweigerd, blijven je bestanden ongewijzigd en wordt de reden gemeld:

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### Een verwijdering ongedaan maken {#undoing-a-removal}

Druk in craftforJ op <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> om een component terug te zetten die je uit de broncode hebt verwijderd. Hiermee wordt het bestand hersteld naar de staat van vóór de verwijdering. Als je het bestand sindsdien in je eigen editor hebt bewerkt, wordt het ongedaan maken geblokkeerd in plaats van je bewerkingen te overschrijven. Zie [Ongedaan maken en opnieuw uitvoeren](/docs/craftforj/source-changes#undoing-and-redoing) voor meer informatie over hoe dit werkt en wat je kunt doen als het wordt geblokkeerd.
