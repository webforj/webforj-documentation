---
sidebar_position: 1
title: Routing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 115816519ca0212b84eb27923a74ca53
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

In moderne webtoepassingen verwijst **routering** naar het proces van het beheren van de navigatie tussen verschillende weergaven of componenten op basis van de huidige URL of pad. In webforJ stelt routering een geavanceerd raamwerk voor **client-side navigatie** in, waar UI-updates dynamisch plaatsvinden zonder volledige paginareloads, wat de prestaties van uw app verbetert.

## Traditionele vs client-side routering {#traditional-vs-client-side-routing}

Bij traditionele server-side routering, wanneer een gebruiker op een link klikt, stuurt de browser een verzoek naar de server voor een nieuw document. De server reageert door een nieuwe HTML-pagina te sturen, wat de browser dwingt om CSS en JavaScript opnieuw te evalueren, het volledige document opnieuw te renderen en de app-status te resetten. Deze cyclus introduceert vertragingen en inefficiënties, aangezien de browser middelen en de paginastatus opnieuw moet laden. Het proces omvat doorgaans:

1. **Verzoek**: De gebruiker navigeert naar een nieuwe URL, wat een verzoek naar de server activeert.
2. **Antwoord**: De server stuurt een nieuw HTML-document samen met gerelateerde middelen (CSS, JS).
3. **Renderer**: De browser rendert de hele pagina opnieuw, waarbij vaak de status van eerder geladen pagina's verloren gaat.

Deze aanpak kan leiden tot prestatieknelpunten en suboptimale gebruikerservaringen door herhaalde volledige paginareloads.

**Client-Side Routering** in webforJ lost dit op door navigatie rechtstreeks in de browser mogelijk te maken, de UI dynamisch bij te werken zonder een nieuw verzoek naar de server te sturen. Zo werkt het:

1. **Enkel Initiëel Verzoek**: De browser laadt de app één keer, inclusief alle vereiste middelen (HTML, CSS, JavaScript).
2. **URL-beheer**: De router luistert naar URL-wijzigingen en werkt de weergave bij op basis van de huidige route.
3. **Dynamische Componentrenderer**: De router koppelt de URL aan een component en rendert deze dynamisch, zonder de pagina te vernieuwen.
4. **Statusbehoud**: De status van de app wordt behouden tussen navigaties, wat zorgt voor een soepele overgang tussen weergaven.

Dit ontwerp maakt **deep linking** en **URL-gedreven statusbeheer** mogelijk, waardoor gebruikers specifieke pagina's binnen de app kunnen bookmarken en delen, terwijl ze genieten van een soepele, single-page ervaring.

## Kernprincipes {#core-principles}

- **URL-gebaseerde componentmapping**: In webforJ zijn routes direct gekoppeld aan UI-componenten. Een URL-patroon is gekoppeld aan een specifieke component, die bepaalt welke inhoud wordt weergegeven op basis van het huidige pad.
- **Declaratieve routering**: Routes worden declaratief gedefinieerd, doorgaans met behulp van annotaties. Elke route komt overeen met een component die wordt gerenderd wanneer de route overeenkomt.
- **Dynamische navigatie**: De router schakelt dynamisch tussen weergaven zonder de pagina opnieuw te laden, waardoor de app responsief en snel blijft.

## Voorbeeld van client-side routering in webforJ {#example-of-client-side-routing-in-webforj}

Hier is een voorbeeld van het definiëren van een route voor een `UserProfileView`-component om gebruikersdetails weer te geven op basis van de `id`-parameter in de URL:

```java
@Route(value = "user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("");
    refreshProfile(id);
  }
}
```

In deze opzet:

- Navigeren naar `/user/john` zou de `UserProfileView`-component renderen.
- De `id`-parameter zou `john` uit de URL vastleggen en u in staat stellen deze binnen de component te gebruiken om gebruikersgegevens op te halen en weer te geven.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
