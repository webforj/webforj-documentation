---
title: Badge
sidebar_position: 8
_i18n_hash: 1f599f2c8a833e09f2d945ed0ead5447
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

Een `Badge` is een compacte, visueel onderscheidende label die wordt gebruikt om status, tellingen of korte stukjes contextuele informatie te communiceren. Of je nu een notificatie-aantal wilt markeren, een item als "Nieuw" wilt aanduiden, of aandacht wilt vragen voor een waarschuwing, badges bieden je een lichte manier om die informatie direct in de gebruikersinterface weer te geven.

<!-- INTRO_END -->

:::tip Gebruik van een `Badge`
Badges zijn goed voor notificatie-aantallen, statuslabels en korte metadata zoals versietags of release-statussen. Houd de tekst van de badge tot een of twee woorden zodat het label in één oogopslag leesbaar is.
:::

## Een badge maken {#creating-a-badge}

De eenvoudigste `Badge` neemt een tekststring. Je kunt ook een `BadgeTheme` direct in de constructor doorgeven om de visuele stijl direct in te stellen. De constructor zonder argumenten is beschikbaar wanneer je een badge dynamisch wilt bouwen en deze na creatie wilt configureren.

```java
Badge badge = new Badge("Nieuw");

// Met een thema
Badge primary = new Badge("Uitgelicht", BadgeTheme.SUCCESS);

// Dynamisch gebouwd
Badge status = new Badge();
status.setLabel("In afwachting");
status.setTheme(BadgeTheme.WARNING);
```

## Label {#label}

Je kunt op elk moment de tekstinhoud van een badge instellen of bijwerken met `setLabel()`. De methode `setText()` is een alias voor dezelfde bewerking; gebruik welke in context natuurlijker leest. Beide hebben bijbehorende getters, `getLabel()` en `getText()`, als je de huidige waarde wilt teruglezen.

```java
Badge badge = new Badge();
badge.setLabel("Bijgewerkt");

// Equivalent
badge.setText("Bijgewerkt");

// Waarde teruglezen
String current = badge.getLabel();
```

## Pictogrammen {#icons}

Soms is een meer visuele benadering nuttig bij het communiceren van informatie met een `Badge`. Badges ondersteunen geneste pictograminhoud. Geef een pictogram samen met tekst door met de constructor `Badge(String, Component...)`, of geef alleen een pictogram door om een pictogram-only badge te creëren. Wanneer gecombineerd met tekst, wordt het pictogram links van het label weergegeven.

Pictogram-only badges werken vooral goed voor compacte statusindicatoren in dichte lay-outs waar een kort woord rommelig zou aanvoelen. Het combineren van een pictogram met tekst is een goed midden als het pictogram alleen ambigu zou kunnen zijn. Een statusymbool is op zichzelf veelzeggend, maar het toevoegen van een kort tekstlabel verwijdert de gissing voor nieuwe gebruikers. Je kunt meerdere componenten aan de constructor doorgeven als je een rijkere prefix wilt samenstellen, hoewel in de praktijk een enkele pictogram de meest voorkomende patroon is.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons'
files={['src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java']}
height='345px'
/>
<!-- vale on -->

```java
// Pictogram met tekst
Badge done = new Badge("Klaar", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// Alleen pictogram
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## Gebruik in andere componenten {#usage-in-other-components}

### Knoppen {#buttons}

Bevestig een `Badge` aan een `Button` met `setBadge()`. De badge verschijnt op de rechterbovenhoek van de knop, overlappend met de rand van de knop. Dit is een algemeen patroon voor notificatie-aantallen op toolbar-acties of pictogramknoppen. Omdat de badge een autonome component is, is deze volledig onafhankelijk van het thema en de grootte van de knop zelf. Je kunt een primaire knop combineren met een gevaar-badge, of een ghost-knop met een succes-badge, en elke kant van de combinatie stijlt zichzelf zonder conflicten. Het later bijwerken van het aantal is net zo eenvoudig als het aanroepen van `badge.setLabel()` met een nieuwe waarde; de knop hoeft niet aangeraakt te worden.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons'
files={['src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java']}
height='290px'
/>
<!-- vale on -->

### Tabbladpaneel {#tabbed-pane}

Voeg een `Badge` toe als suffix op een `Tab` met `setSuffixComponent()`. Dit is een natuurlijke plaats voor inbox-stijl tellingen of statusindicatoren op elk tabblad. Dit is het soort patroon dat je ziet in e-mailclients of taakbeheerders waar het belangrijk is om activiteit op elk segment in één oogopslag te signaleren. De badge staat aan de achterkant van het tablabel, na enige prefixinhoud, en blijft zichtbaar, ongeacht welk tabblad momenteel actief is. Deze persistentie is opzettelijk: het verbergen van de badge op inactieve tabbladen zou het moeilijker maken om te weten welke secties aandacht nodig hebben zonder naar elk tabblad over te schakelen.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane'
files={['src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java']}
height='360px'
/>
<!-- vale on -->

## Stijlen {#styling}

Badges ondersteunen verschillende stijldimensies: thema kleuren om betekenis over te brengen, een expanse-schaal om de grootte te beheersen, en CSS-eigenschappen voor fijnmazige aanpassing.

### Thema's {#themes}

Zoals bij veel componenten in webforJ, komt de `Badge` in veertien thema's: zeven gevulde en zeven omrandde varianten.

Gevulde thema's gebruiken een effen achtergrond en berekenen automatisch een tekstkleur die voldoet aan de contrastvereisten. Omrandde varianten gebruiken in plaats daarvan een getinte achtergrond met een gekleurde rand, waardoor ze een subtielere optie zijn wanneer je wilt dat de badge de omliggende inhoud aanvult in plaats van deze te domineren.

Pas een thema toe met `setTheme()` of via de constructor.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes'
files={['src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java']}
height='260px'
/>
<!-- vale on -->

### Aangepaste kleur {#custom-color}

Als de ingebouwde thema's niet overeenkomen met je palet, stel dan een aangepaste zaadkleur in met de CSS-eigenschap `--dwc-badge-seed`. Van deze enkele waarde haalt de badge automatisch de achtergrond-, tekst- en randkleuren af, zodat elke combinatie leesbaar blijft zonder dat je elke afzonderlijk moet specificeren. Dit betekent dat je een badge kunt branden in elke kleur van je ontwerp systeem met vertrouwen. Hue, Verzadiging en Helderheid (HSL) waardes zijn hier bijzonder handig; het wisselen van alleen de tint is genoeg om een compleet andere kleurfamilie te produceren terwijl het contrast intact blijft.

```java
Badge badge = new Badge("Aangepast");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Grootte {#sizing}

Gebruik `setExpanse()` om de badge-grootte te beheersen. Negen maten zijn beschikbaar, variërend van `XXXSMALL` tot `XXXLARGE`, en de standaardmaat is `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes'
files={['src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java']}
height='300px'
/>
<!-- vale on -->

### Onderdelen en CSS-variabelen {#parts-and-css-variables}

<TableBuilder name="Badge" />
