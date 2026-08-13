---
title: Tailwind
sidebar_position: 60
sidebar_class_name: new-content
description: >-
  Turn on the webforj-tailwind extension, apply utility classes from a view, and
  understand how it generates and scans its own stylesheet.
_i18n_hash: f588624ebd738977bb8be4e9887141d1
---
[Tailwind CSS](https://tailwindcss.com/) is een utility-first CSS framework waarvan de classnamen elke map naar een kleine set CSS-declaraties. Het is de enige gecureerde extensie die wordt meegeleverd, omdat de meeste projecten het niet gebruiken. Je schakelt het in met id, op dezelfde manier zoals je elke extensie inschakelt, zie [Enabled by id](/docs/managing-resources/bundler/extensions/overview#enabled-by-id). Wanneer het aan staat, doet het iets wat de anderen niet doen: het genereert een eigen invoer.

## Hoe het werkt {#how-it-works}

In plaats van een bestand te compileren dat je hebt geschreven, scant de Tailwind-extensie je app-bronnen naar de utility classnamen die ze gebruiken, genereert een stylesheet die alleen die utilities bevat en laadt deze voor elke weergave. Een weergave past vervolgens utilities toe als classnamen zonder iets te hoeven importeren:

```java title="TailwindView.java"
@Route("/tailwind")
public class TailwindView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TailwindView() {
    Div card = new Div("Gestyled door gecompileerde Tailwind utilities");
    card.addClassName("flex", "items-center", "gap-4", "p-8", "rounded-lg",
        "bg-blue-500", "text-white");
    self.add(card);
  }
}
```

De gegenereerde stylesheet importeert het thema en de utilities van Tailwind, maar niet zijn basisreset. De reset, Tailwind's preflight, herstelt elk kaal element op de pagina, wat de styling zou overschrijven die webforJ al toepast op zijn componenten. Door het weg te laten blijven de utility classes die je toevoegt werken zonder de componenten die je niet hebt gewijzigd te verstoren.

Omdat de utilities voortkomen uit de classnamen die je weergaven gebruiken, volgt de [frontend watch](/docs/configuration/deploy-reload/frontend-watch) je app-bronnen evenals `src/main/frontend`. Voeg een utility class toe of verwijder deze in een weergave en sla op, en de stylesheet wordt opnieuw gegenereerd en op de pagina ingevoegd, hetzelfde als wanneer je een stylesheet die je hebt geschreven bewerkt.

## Waar utility classes bereiken {#where-utility-classes-reach}

:::warning Een utility class stijlt het element, niet de binnenkant van een component
webforJ-componenten renderen met een shadow DOM dat hun interne structuur privé houdt. Een utility class die aan een component wordt toegevoegd, stijlt alleen zijn buitenste doos, de ruimte, grootte en plaats in een lay-out, en bereikt nooit de elementen die binnenin zijn getekend. Utilities passen toe zoals hun classnamen lezen op een lay-outcontainer of een eenvoudige `Div` die je bouwt, waar er geen grens te kruisen is, maar niet op de interne delen van een opgebouwde component.

Om te stijlen wat binnenin een component zit, gebruik de styling die de component blootlegt: [shadow parts](/docs/styling/shadow-parts) via `::part()` en de [CSS custom properties](/docs/styling/css-variables) van de component, beide vermeld in de stylingreferentie van elke component. Houd utilities voor lay-out en voor je eigen elementen, en gebruik de eigen styling van een component om de component bij te stellen.
:::

De stylesheet bevat de utility classnamen die de scan in jouw bronnen vindt, en alleen die. Een class die je in de browserinspecteur typt om een idee uit te proberen, zal niet toepassen, omdat deze nooit is gecompileerd. Zet de class in een weergave en sla op, en de watch genereert de stylesheet opnieuw met deze class.

Wanneer dezelfde groep utilities zich herhaalt in meerdere weergaven, geef het een naam: definieer een CSS-class eenmaal en voeg die in plaats daarvan toe. Een paar utilities inline blijven leesbaar, een lange reeks die met de hand wordt herhaald, vervaagt terwijl je bewerkt.

## Opties {#options}

De Tailwind-extensie neemt geen opties van `bun.config.ts`. Het genereert en scant zijn eigen stylesheet, en Tailwind zelf is geconfigureerd via die stylesheet in plaats van via de extensie.
