---
title: Componenten inspecteren
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 91fbaf620dc8967a50c4a5ce6b455841
---
De Inspector toont de componentenboom die je Java-code heeft opgebouwd. Een `Composite` wordt weergegeven als de klasse die je hebt geschreven, met de onderliggende componenten die je eraan hebt toegevoegd in de volgorde waarin webforJ ze bevat. Zo komt de structuur in craftforJ overeen met de structuur in je broncode.

![De componentenboom met een geselecteerde component die in de actieve app is gemarkeerd](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Een component selecteren {#selecting-a-component}

Als je een component op de pagina wilt selecteren, druk je op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> en klik je erop. craftforJ selecteert het overeenkomende knooppunt in de boom. Als je de aanwijzer op een knooppunt in de boom plaatst, wordt die component juist op de pagina gemarkeerd. Zo kun je in beide richtingen tussen het scherm en de boom navigeren.

<VideoPlayer video="craftforj/pick-mode" width={1920} height={992} title="Pick mode" />

Druk op <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd> om in de boom te zoeken. Als je een zoekterm tussen slashes zet, wordt deze als reguliere expressie behandeld. Als je met de rechtermuisknop op een knooppunt klikt, worden de beschikbare acties weergegeven. Je kunt de broncode openen of het knooppunt doorgeven aan de [assistent](/docs/craftforj/ai).

## Eigenschappen bekijken en wijzigen {#reading-and-changing-properties}

Als je een component selecteert, worden de eigenschappen ervan gegroepeerd op basis van hun functie in de zijbalk weergegeven. Welke eigenschappen een component heeft, hangt af van de component; sommige eigenschappen zijn alleen-lezen. Eigenschappen die zich niet goed als platte tekst laten weergeven, krijgen een editor die geschikt is voor hun waarde. Als je een waarde wijzigt, wordt de wijziging onmiddellijk toegepast in de actieve app.

:::info Live bewerkingen wijzigen je bestanden niet
Een eigenschap bewerken wijzigt alleen de app die je voor je hebt. De wijziging in je broncode opnemen is een aparte stap die je bewust uitvoert. Deze wordt beschreven in [Wijzigingen naar de broncode schrijven](/docs/craftforj/source-changes).
:::

<VideoPlayer video="craftforj/property-edit" width={1920} height={1000} title="Property edit" />

## De broncode van een component bekijken {#viewing-the-source-of-a-component}

Je kunt elke component herleiden naar de Java-code waarmee deze is opgebouwd. Standaard wordt de broncode in craftforJ alleen-lezen geopend, op de regel waarmee de component is gemaakt. Je kunt craftforJ zo configureren dat de broncode in plaats daarvan in je editor wordt geopend, op dezelfde regel. Als een component niet naar een regel kan worden herleid, meldt craftforJ dat in plaats van een lege viewer te openen.

![De broncodeviewer op de regel waarmee de geselecteerde component is gemaakt](/img/craftforj/inspector/source-viewer.png#rounded-border)

## Een component verwijderen <DocChip chip='since' label='26.03' /> {#removing-a-component}

Als je met de rechtermuisknop op een component in de boom klikt en **Remove Component** kiest, krijg je twee manieren om de component te verwijderen. Met de ene verwijder je de component uit de app die je voor je hebt, met de andere uit je project.

<VideoPlayer video="craftforj/delete-components" width={1872} height={1080} title="Delete components" />

### Alleen uit de browser verwijderen {#removing-from-the-browser-only}

Met **Remove from browser only** verwijder je de component uit de actieve app, zonder je Java-broncode te wijzigen. Gebruik deze optie om te bekijken hoe een lay-out eruitziet zonder een bepaalde component, voordat je iets definitief maakt.

De verwijdering is tijdelijk. De component verschijnt weer zodra de app die weergave opnieuw opbouwt op basis van je broncode, bijvoorbeeld nadat je de pagina opnieuw hebt geladen.

### Uit de broncode verwijderen {#removing-from-source}

Met **Remove from source** bewerk je het Java-bestand waarin de component is gemaakt, zodat deze ook na het opnieuw opbouwen van de app verwijderd blijft. De verwijdering wordt direct doorgevoerd in plaats van toegevoegd aan de [in behandeling zijnde wijzigingen](/docs/craftforj/source-changes#pending-changes). De wijziging wordt dus zonder beoordelingsstap in je bestand opgenomen.

Als je Java-code schrijft, wordt de app opnieuw opgebouwd en opgestart, net als bij het [toepassen van wijzigingen](/docs/craftforj/source-changes#after-you-apply). Als [live reload](/docs/configuration/deploy-reload/overview) is geconfigureerd, gebeurt dit automatisch. Zo niet, start de app dan zelf opnieuw op.

Neem bijvoorbeeld een formulier met een knop **Save** en een knop **Cancel**, waarbij **Save** een thema en een click listener heeft:

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

Als je **Save** selecteert en **Remove from source** kiest, blijft het volgende over. De declaratie, het thema, de listener en de plek van de knop in de aanroep van `add()` worden verwijderd, net als de import van `ButtonTheme`, die nergens anders wordt gebruikt. Nadat de app opnieuw is opgebouwd, staat **Cancel** er nog steeds:

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

Een verwijdering geldt alleen voor de component zelf en niets daarbuiten:

- Als je een container verwijdert, worden ook de componenten erin verwijderd.
- Code die alleen nodig was om de component te maken, configureren en toevoegen, wordt opgeruimd. Dit omvat velden en imports die nergens anders worden gebruikt.
- Code die elders nog nodig is, blijft behouden. Als een ander deel van de klasse de component nog gebruikt, bijvoorbeeld in de listener van een andere component, koppelt craftforJ deze alleen los en laat de code staan waarmee de component wordt gemaakt en geconfigureerd.
- Als je een instantie van een herbruikbare component verwijdert, zoals een `Composite` die je zelf hebt geschreven, wordt alleen die ene toepassing verwijderd. De klasse waarin deze is gedefinieerd, wordt niet verwijderd of gewijzigd.

craftforJ schrijft het bestand alleen weg als het resultaat nog steeds compileert. Bestanden worden nooit verwijderd.

### Wanneer verwijderen niet beschikbaar is {#when-removal-isnt-available}

**Remove from source** wordt alleen aangeboden wanneer Java-code schrijven is toegestaan in zowel de [configuratie](/docs/craftforj/configuration#feature-flags) van de app als de instelling **Java source changes** in craftforJ. Deze optie wordt niet aangeboden voor componenten die in Kotlin zijn opgebouwd, of voor componenten die craftforJ niet naar een regel broncode kan herleiden.

Sommige componenten kunnen helemaal niet uit de broncode worden verwijderd:

- Routes, die door de router worden geplaatst op basis van hun `@Route`
- Het frame
- Componenten die een andere component als onderdeel van zichzelf opbouwt. Verwijder in plaats daarvan de buitenste component.

craftforJ kan een verwijdering ook weigeren als het de broncode van de component niet kan vinden, of niet kan bepalen welke code moet worden verwijderd. Als één regel bijvoorbeeld meerdere componenten van hetzelfde type maakt, geeft die regel alleen niet aan welke component je hebt geselecteerd.

Als een verwijdering wordt geweigerd, blijven je bestanden ongewijzigd en wordt de reden gemeld:

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### Een verwijdering ongedaan maken {#undoing-a-removal}

Als je een component wilt terugzetten die je uit de broncode hebt verwijderd, druk je in craftforJ op <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd>. Hiermee wordt het bestand teruggezet naar de staat van vóór de verwijdering. Als je het bestand sindsdien in je eigen editor hebt bewerkt, wordt het ongedaan maken geblokkeerd om te voorkomen dat je wijzigingen worden overschreven. Zie [Ongedaan maken en opnieuw uitvoeren](/docs/craftforj/source-changes#undoing-and-redoing) voor meer informatie over hoe ongedaan maken werkt en wat je kunt doen als dit wordt geblokkeerd.
